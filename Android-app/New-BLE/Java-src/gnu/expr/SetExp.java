package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.functions.AddOp;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class SetExp extends AccessExp {
    public static final int BAD_SHORT = 65536;
    public static final int DEFINING_FLAG = 2;
    public static final int GLOBAL_FLAG = 4;
    public static final int HAS_VALUE = 64;
    public static final int PREFER_BINDING2 = 8;
    public static final int PROCEDURE = 16;
    public static final int SET_IF_UNBOUND = 32;
    Expression new_value;

    public SetExp(Object symbol, Expression val) {
        this.symbol = symbol;
        this.new_value = val;
    }

    public SetExp(Declaration decl, Expression val) {
        this.binding = decl;
        this.symbol = decl.getSymbol();
        this.new_value = val;
    }

    public static SetExp makeDefinition(Object symbol, Expression val) {
        SetExp sexp = new SetExp(symbol, val);
        sexp.setDefining(true);
        return sexp;
    }

    public static SetExp makeDefinition(Declaration decl, Expression val) {
        SetExp sexp = new SetExp(decl, val);
        sexp.setDefining(true);
        return sexp;
    }

    public final Expression getNewValue() {
        return this.new_value;
    }

    public final boolean isDefining() {
        return (this.flags & 2) != 0;
    }

    public final void setDefining(boolean value) {
        if (value) {
            this.flags |= 2;
        } else {
            this.flags &= -3;
        }
    }

    public final boolean getHasValue() {
        return (this.flags & 64) != 0;
    }

    public final void setHasValue(boolean value) {
        if (value) {
            this.flags |= 64;
        } else {
            this.flags &= -65;
        }
    }

    public final boolean isFuncDef() {
        return (this.flags & 16) != 0;
    }

    public final void setFuncDef(boolean value) {
        if (value) {
            this.flags |= 16;
        } else {
            this.flags &= -17;
        }
    }

    public final boolean isSetIfUnbound() {
        return (this.flags & 32) != 0;
    }

    public final void setSetIfUnbound(boolean value) {
        if (value) {
            this.flags |= 32;
        } else {
            this.flags &= -33;
        }
    }

    protected boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        Symbol sym;
        Environment env = Environment.getCurrent();
        if (this.symbol instanceof Symbol) {
            sym = (Symbol) this.symbol;
        } else {
            sym = env.getSymbol(this.symbol.toString());
        }
        Object property = null;
        Language language = Language.getDefaultLanguage();
        if (isFuncDef() && language.hasSeparateFunctionNamespace()) {
            property = EnvironmentKey.FUNCTION;
        }
        if (isSetIfUnbound()) {
            Location loc = env.getLocation(sym, property);
            if (!loc.isBound()) {
                loc.set(this.new_value.eval(env));
            }
            if (getHasValue()) {
                ctx.writeValue(loc);
                return;
            }
            return;
        }
        Object new_val = this.new_value.eval(env);
        if (this.binding != null && !(this.binding.context instanceof ModuleExp)) {
            Object[] evalFrame = ctx.evalFrames[ScopeExp.nesting(this.binding.context)];
            if (this.binding.isIndirectBinding()) {
                if (isDefining()) {
                    evalFrame[this.binding.evalIndex] = Location.make(sym);
                }
                ((Location) evalFrame[this.binding.evalIndex]).set(this.new_value);
            } else {
                evalFrame[this.binding.evalIndex] = new_val;
            }
        } else if (isDefining()) {
            env.define(sym, property, new_val);
        } else {
            env.put(sym, property, new_val);
        }
        if (getHasValue()) {
            ctx.writeValue(new_val);
        }
    }

    public void compile(Compilation comp, Target target) {
        if (!(this.new_value instanceof LambdaExp) || !(target instanceof IgnoreTarget) || !((LambdaExp) this.new_value).getInlineOnly()) {
            CodeAttr code = comp.getCode();
            boolean needValue = getHasValue() && !(target instanceof IgnoreTarget);
            boolean valuePushed = false;
            Declaration decl = this.binding;
            Expression declValue = decl.getValue();
            if ((declValue instanceof LambdaExp) && (decl.context instanceof ModuleExp) && !decl.ignorable() && ((LambdaExp) declValue).getName() != null && declValue == this.new_value) {
                ((LambdaExp) this.new_value).compileSetField(comp);
            } else if ((decl.shouldEarlyInit() || decl.isAlias()) && (decl.context instanceof ModuleExp) && isDefining() && !decl.ignorable()) {
                if (decl.shouldEarlyInit()) {
                    BindingInitializer.create(decl, this.new_value, comp);
                }
                if (needValue) {
                    decl.load(this, 0, comp, Target.pushObject);
                    valuePushed = true;
                }
            } else {
                AccessExp access = this;
                Declaration owner = contextDecl();
                if (!isDefining()) {
                    while (decl != null && decl.isAlias()) {
                        declValue = decl.getValue();
                        if (!(declValue instanceof ReferenceExp)) {
                            break;
                        }
                        AccessExp rexp = (ReferenceExp) declValue;
                        Declaration orig = rexp.binding;
                        if (orig == null || (owner != null && orig.needsContext())) {
                            break;
                        }
                        owner = rexp.contextDecl();
                        access = rexp;
                        decl = orig;
                    }
                }
                if (decl.ignorable()) {
                    this.new_value.compile(comp, Target.Ignore);
                } else if (decl.isAlias() && isDefining()) {
                    decl.load(this, 2, comp, Target.pushObject);
                    ClassType locType = ClassType.make("gnu.mapping.IndirectableLocation");
                    code.emitCheckcast(locType);
                    this.new_value.compile(comp, Target.pushObject);
                    code.emitInvokeVirtual(locType.getDeclaredMethod("setAlias", 1));
                } else if (decl.isIndirectBinding()) {
                    decl.load(access, 2, comp, Target.pushObject);
                    if (isSetIfUnbound()) {
                        if (needValue) {
                            code.emitDup();
                            valuePushed = true;
                        }
                        code.pushScope();
                        code.emitDup();
                        Variable symLoc = code.addLocal(Compilation.typeLocation);
                        code.emitStore(symLoc);
                        code.emitInvokeVirtual(Compilation.typeLocation.getDeclaredMethod("isBound", 0));
                        code.emitIfIntEqZero();
                        code.emitLoad(symLoc);
                    }
                    this.new_value.compile(comp, Target.pushObject);
                    if (needValue && !isSetIfUnbound()) {
                        code.emitDupX();
                        valuePushed = true;
                    }
                    code.emitInvokeVirtual(Compilation.typeLocation.getDeclaredMethod("set", 1));
                    if (isSetIfUnbound()) {
                        code.emitFi();
                        code.popScope();
                    }
                } else if (decl.isSimple()) {
                    type = decl.getType();
                    Variable var = decl.getVariable();
                    if (var == null) {
                        var = decl.allocateVariable(code);
                    }
                    int delta = canUseInc(this.new_value, decl);
                    if (delta != 65536) {
                        comp.getCode().emitInc(var, (short) delta);
                        if (needValue) {
                            code.emitLoad(var);
                            valuePushed = true;
                        }
                    } else {
                        this.new_value.compile(comp, decl);
                        if (needValue) {
                            code.emitDup(type);
                            valuePushed = true;
                        }
                        code.emitStore(var);
                    }
                } else if ((decl.context instanceof ClassExp) && decl.field == null && !getFlag(16) && ((ClassExp) decl.context).isMakingClassPair()) {
                    ClassExp cl = decl.context;
                    Method setter = cl.type.getDeclaredMethod(ClassExp.slotToMethodName("set", decl.getName()), 1);
                    cl.loadHeapFrame(comp);
                    this.new_value.compile(comp, decl);
                    if (needValue) {
                        code.emitDupX();
                        valuePushed = true;
                    }
                    code.emitInvoke(setter);
                } else {
                    Field field = decl.field;
                    if (!field.getStaticFlag()) {
                        decl.loadOwningObject(owner, comp);
                    }
                    type = field.getType();
                    this.new_value.compile(comp, decl);
                    comp.usedClass(field.getDeclaringClass());
                    if (field.getStaticFlag()) {
                        if (needValue) {
                            code.emitDup(type);
                            valuePushed = true;
                        }
                        code.emitPutStatic(field);
                    } else {
                        if (needValue) {
                            code.emitDupX();
                            valuePushed = true;
                        }
                        code.emitPutField(field);
                    }
                }
            }
            if (needValue && !valuePushed) {
                throw new Error("SetExp.compile: not implemented - return value");
            } else if (needValue) {
                target.compileFromStack(comp, getType());
            } else {
                comp.compileConstant(Values.empty, target);
            }
        }
    }

    public static int canUseInc(Expression rhs, Declaration target) {
        Variable var = target.getVariable();
        if (target.isSimple() && var.getType().getImplementationType().promote() == Type.intType && (rhs instanceof ApplyExp)) {
            ApplyExp aexp = (ApplyExp) rhs;
            if (aexp.getArgCount() == 2) {
                int sign;
                AddOp func = aexp.getFunction().valueIfConstant();
                if (func == AddOp.$Pl) {
                    sign = 1;
                } else if (func == AddOp.$Mn) {
                    sign = -1;
                }
                Expression arg0 = aexp.getArg(0);
                Expression arg1 = aexp.getArg(1);
                if ((arg0 instanceof QuoteExp) && sign > 0) {
                    Expression tmp = arg1;
                    arg1 = arg0;
                    arg0 = tmp;
                }
                if (arg0 instanceof ReferenceExp) {
                    ReferenceExp ref0 = (ReferenceExp) arg0;
                    if (ref0.getBinding() == target && !ref0.getDontDereference()) {
                        IntNum value1 = arg1.valueIfConstant();
                        if (value1 instanceof Integer) {
                            short val1 = ((Integer) value1).intValue();
                            if (sign < 0) {
                                val1 = -val1;
                            }
                            if (((short) val1) == val1) {
                                return val1;
                            }
                        } else if (value1 instanceof IntNum) {
                            IntNum int1 = value1;
                            int hi = 32767;
                            int lo = -32767;
                            if (sign > 0) {
                                lo--;
                            } else {
                                hi = 32767 + 1;
                            }
                            if (IntNum.compare(int1, (long) lo) >= 0 && IntNum.compare(int1, (long) hi) <= 0) {
                                return sign * int1.intValue();
                            }
                        }
                    }
                }
            }
        }
        return 65536;
    }

    public final Type getType() {
        if (getHasValue()) {
            return this.binding == null ? Type.pointer_type : this.binding.getType();
        } else {
            return Type.voidType;
        }
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitSetExp(this, d);
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.new_value = visitor.visitAndUpdate(this.new_value, d);
    }

    public void print(OutPort out) {
        out.startLogicalBlock(isDefining() ? "(Define" : "(Set", ")", 2);
        out.writeSpaceFill();
        printLineColumn(out);
        if (this.binding == null || this.symbol.toString() != this.binding.getName()) {
            out.print('/');
            out.print(this.symbol);
        }
        if (this.binding != null) {
            out.print('/');
            out.print(this.binding);
        }
        out.writeSpaceLinear();
        this.new_value.print(out);
        out.endLogicalBlock(")");
    }

    public String toString() {
        return "SetExp[" + this.symbol + ":=" + this.new_value + ']';
    }
}

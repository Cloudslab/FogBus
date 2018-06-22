package gnu.expr;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Label;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.math.IntNum;
import gnu.text.Char;
import gnu.text.SourceLocator;

public class Declaration implements SourceLocator {
    static final int CAN_CALL = 4;
    static final int CAN_READ = 2;
    static final int CAN_WRITE = 8;
    public static final long CLASS_ACCESS_FLAGS = 25820135424L;
    public static final int EARLY_INIT = 536870912;
    public static final long ENUM_ACCESS = 8589934592L;
    public static final int EXPORT_SPECIFIED = 1024;
    public static final int EXTERNAL_ACCESS = 524288;
    public static final long FIELD_ACCESS_FLAGS = 32463912960L;
    public static final int FIELD_OR_METHOD = 1048576;
    public static final long FINAL_ACCESS = 17179869184L;
    static final int INDIRECT_BINDING = 1;
    public static final int IS_ALIAS = 256;
    public static final int IS_CONSTANT = 16384;
    public static final int IS_DYNAMIC = 268435456;
    static final int IS_FLUID = 16;
    public static final int IS_IMPORTED = 131072;
    public static final int IS_NAMESPACE_PREFIX = 2097152;
    static final int IS_SIMPLE = 64;
    public static final int IS_SINGLE_VALUE = 262144;
    public static final int IS_SYNTAX = 32768;
    public static final int IS_UNKNOWN = 65536;
    public static final long METHOD_ACCESS_FLAGS = 17431527424L;
    public static final int MODULE_REFERENCE = 1073741824;
    public static final int NONSTATIC_SPECIFIED = 4096;
    public static final int NOT_DEFINING = 512;
    public static final int PACKAGE_ACCESS = 134217728;
    static final int PRIVATE = 32;
    public static final int PRIVATE_ACCESS = 16777216;
    public static final String PRIVATE_PREFIX = "$Prvt$";
    public static final int PRIVATE_SPECIFIED = 16777216;
    static final int PROCEDURE = 128;
    public static final int PROTECTED_ACCESS = 33554432;
    public static final int PUBLIC_ACCESS = 67108864;
    public static final int STATIC_SPECIFIED = 2048;
    public static final long TRANSIENT_ACCESS = 4294967296L;
    public static final int TYPE_SPECIFIED = 8192;
    static final String UNKNOWN_PREFIX = "loc$";
    public static final long VOLATILE_ACCESS = 2147483648L;
    static int counter;
    public Declaration base;
    public ScopeExp context;
    int evalIndex;
    public Field field;
    String filename;
    public ApplyExp firstCall;
    protected long flags;
    protected int id;
    Method makeLocationMethod;
    Declaration next;
    Declaration nextCapturedVar;
    int position;
    Object symbol;
    protected Type type;
    protected Expression typeExp;
    protected Expression value;
    Variable var;

    public void setCode(int code) {
        if (code >= 0) {
            throw new Error("code must be negative");
        }
        this.id = code;
    }

    public int getCode() {
        return this.id;
    }

    public final Expression getTypeExp() {
        if (this.typeExp == null) {
            setType(Type.objectType);
        }
        return this.typeExp;
    }

    public final Type getType() {
        if (this.type == null) {
            setType(Type.objectType);
        }
        return this.type;
    }

    public final void setType(Type type) {
        this.type = type;
        if (this.var != null) {
            this.var.setType(type);
        }
        this.typeExp = QuoteExp.getInstance(type);
    }

    public final void setTypeExp(Expression typeExp) {
        Type t;
        this.typeExp = typeExp;
        if (typeExp instanceof TypeValue) {
            t = ((TypeValue) typeExp).getImplementationType();
        } else {
            t = Language.getDefaultLanguage().getTypeFor(typeExp, false);
        }
        if (t == null) {
            t = Type.pointer_type;
        }
        this.type = t;
        if (this.var != null) {
            this.var.setType(t);
        }
    }

    public final String getName() {
        if (this.symbol == null) {
            return null;
        }
        return this.symbol instanceof Symbol ? ((Symbol) this.symbol).getName() : this.symbol.toString();
    }

    public final void setName(Object symbol) {
        this.symbol = symbol;
    }

    public final Object getSymbol() {
        return this.symbol;
    }

    public final void setSymbol(Object symbol) {
        this.symbol = symbol;
    }

    public final Declaration nextDecl() {
        return this.next;
    }

    public final void setNext(Declaration next) {
        this.next = next;
    }

    public Variable getVariable() {
        return this.var;
    }

    public final boolean isSimple() {
        return (this.flags & 64) != 0;
    }

    public final void setSimple(boolean b) {
        setFlag(b, 64);
        if (this.var != null && !this.var.isParameter()) {
            this.var.setSimple(b);
        }
    }

    public final void setSyntax() {
        setSimple(false);
        setFlag(536920064);
    }

    public final ScopeExp getContext() {
        return this.context;
    }

    void loadOwningObject(Declaration owner, Compilation comp) {
        if (owner == null) {
            owner = this.base;
        }
        if (owner != null) {
            owner.load(null, 0, comp, Target.pushObject);
        } else {
            getContext().currentLambda().loadHeapFrame(comp);
        }
    }

    public void load(AccessExp access, int flags, Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            Declaration owner = access == null ? null : access.contextDecl();
            if (isAlias() && (this.value instanceof ReferenceExp)) {
                AccessExp rexp = (ReferenceExp) this.value;
                Declaration orig = rexp.binding;
                if (orig != null && (((flags & 2) == 0 || orig.isIndirectBinding()) && (owner == null || !orig.needsContext()))) {
                    orig.load(rexp, flags, comp, target);
                    return;
                }
            }
            if (isFluid() && (this.context instanceof FluidLetExp)) {
                this.base.load(access, flags, comp, target);
                return;
            }
            CodeAttr code = comp.getCode();
            Type rtype = getType();
            if (isIndirectBinding() || (flags & 2) == 0) {
                if (this.field != null) {
                    comp.usedClass(this.field.getDeclaringClass());
                    comp.usedClass(this.field.getType());
                    if (this.field.getStaticFlag()) {
                        code.emitGetStatic(this.field);
                    } else {
                        loadOwningObject(owner, comp);
                        code.emitGetField(this.field);
                    }
                } else if (isIndirectBinding() && comp.immediate && getVariable() == null) {
                    Environment env = Environment.getCurrent();
                    Symbol sym = this.symbol instanceof Symbol ? (Symbol) this.symbol : env.getSymbol(this.symbol.toString());
                    Object property = null;
                    if (isProcedureDecl() && comp.getLanguage().hasSeparateFunctionNamespace()) {
                        property = EnvironmentKey.FUNCTION;
                    }
                    comp.compileConstant(env.getLocation(sym, property), Target.pushValue(Compilation.typeLocation));
                } else {
                    if (comp.immediate) {
                        Object val = getConstantValue();
                        if (val != null) {
                            comp.compileConstant(val, target);
                            return;
                        }
                    }
                    if (this.value == QuoteExp.undefined_exp || !ignorable() || ((this.value instanceof LambdaExp) && (((LambdaExp) this.value).outer instanceof ModuleExp))) {
                        Variable var = getVariable();
                        if ((this.context instanceof ClassExp) && var == null && !getFlag(128)) {
                            ClassExp cl = this.context;
                            if (cl.isMakingClassPair()) {
                                Method getter = cl.type.getDeclaredMethod(ClassExp.slotToMethodName("get", getName()), 0);
                                cl.loadHeapFrame(comp);
                                code.emitInvoke(getter);
                            }
                        }
                        if (var == null) {
                            var = allocateVariable(code);
                        }
                        code.emitLoad(var);
                    } else {
                        this.value.compile(comp, target);
                        return;
                    }
                }
                if (isIndirectBinding() && (flags & 2) == 0) {
                    if (access != null) {
                        String filename = access.getFileName();
                        if (filename != null) {
                            int line = access.getLineNumber();
                            if (line > 0) {
                                Type typeUnboundLocationException = ClassType.make("gnu.mapping.UnboundLocationException");
                                boolean isInTry = code.isInTry();
                                int column = access.getColumnNumber();
                                Label label = new Label(code);
                                label.define(code);
                                code.emitInvokeVirtual(Compilation.getLocationMethod);
                                Label endTry = new Label(code);
                                endTry.define(code);
                                Label endLabel = new Label(code);
                                endLabel.setTypes(code);
                                if (isInTry) {
                                    code.emitGoto(endLabel);
                                } else {
                                    code.setUnreachable();
                                }
                                int fragment_cookie = 0;
                                if (!isInTry) {
                                    fragment_cookie = code.beginFragment(endLabel);
                                }
                                code.addHandler(label, endTry, typeUnboundLocationException);
                                code.emitDup(typeUnboundLocationException);
                                code.emitPushString(filename);
                                code.emitPushInt(line);
                                code.emitPushInt(column);
                                code.emitInvokeVirtual(typeUnboundLocationException.getDeclaredMethod("setLine", 3));
                                code.emitThrow();
                                if (isInTry) {
                                    endLabel.define(code);
                                } else {
                                    code.endFragment(fragment_cookie);
                                }
                                rtype = Type.pointer_type;
                            }
                        }
                    }
                    code.emitInvokeVirtual(Compilation.getLocationMethod);
                    rtype = Type.pointer_type;
                }
            } else if (this.field == null) {
                throw new Error("internal error: cannot take location of " + this);
            } else {
                Type ltype;
                Method meth;
                boolean immediate = comp.immediate;
                if (this.field.getStaticFlag()) {
                    ltype = ClassType.make("gnu.kawa.reflect.StaticFieldLocation");
                    meth = ltype.getDeclaredMethod("make", immediate ? 1 : 2);
                } else {
                    ltype = ClassType.make("gnu.kawa.reflect.FieldLocation");
                    meth = ltype.getDeclaredMethod("make", immediate ? 2 : 3);
                    loadOwningObject(owner, comp);
                }
                if (immediate) {
                    comp.compileConstant(this);
                } else {
                    comp.compileConstant(this.field.getDeclaringClass().getName());
                    comp.compileConstant(this.field.getName());
                }
                code.emitInvokeStatic(meth);
                rtype = ltype;
            }
            target.compileFromStack(comp, rtype);
        }
    }

    public void compileStore(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (isSimple()) {
            code.emitStore(getVariable());
        } else if (this.field.getStaticFlag()) {
            code.emitPutStatic(this.field);
        } else {
            loadOwningObject(null, comp);
            code.emitSwap();
            code.emitPutField(this.field);
        }
    }

    public final Expression getValue() {
        if (this.value == QuoteExp.undefined_exp) {
            if (!(this.field == null || (this.field.getModifiers() & 24) != 24 || isIndirectBinding())) {
                try {
                    this.value = new QuoteExp(this.field.getReflectField().get(null));
                } catch (Throwable th) {
                }
            }
        } else if ((this.value instanceof QuoteExp) && getFlag(8192) && this.value.getType() != this.type) {
            try {
                Object val = ((QuoteExp) this.value).getValue();
                Type t = getType();
                this.value = new QuoteExp(t.coerceFromObject(val), t);
            } catch (Throwable th2) {
            }
        }
        return this.value;
    }

    public final void setValue(Expression value) {
        this.value = value;
    }

    public final Object getConstantValue() {
        Expression v = getValue();
        if (!(v instanceof QuoteExp) || v == QuoteExp.undefined_exp) {
            return null;
        }
        return ((QuoteExp) v).getValue();
    }

    public final boolean hasConstantValue() {
        Expression v = getValue();
        return (v instanceof QuoteExp) && v != QuoteExp.undefined_exp;
    }

    boolean shouldEarlyInit() {
        return getFlag(536870912) || isCompiletimeConstant();
    }

    public boolean isCompiletimeConstant() {
        return getFlag(16384) && hasConstantValue();
    }

    public final boolean needsExternalAccess() {
        return (this.flags & 524320) == 524320 || (this.flags & 2097184) == 2097184;
    }

    public final boolean needsContext() {
        return (this.base != null || this.field == null || this.field.getStaticFlag()) ? false : true;
    }

    public final boolean getFlag(long flag) {
        return (this.flags & flag) != 0;
    }

    public final void setFlag(boolean setting, long flag) {
        if (setting) {
            this.flags |= flag;
        } else {
            this.flags &= -1 ^ flag;
        }
    }

    public final void setFlag(long flag) {
        this.flags |= flag;
    }

    public final boolean isPublic() {
        return (this.context instanceof ModuleExp) && (this.flags & 32) == 0;
    }

    public final boolean isPrivate() {
        return (this.flags & 32) != 0;
    }

    public final void setPrivate(boolean isPrivate) {
        setFlag(isPrivate, 32);
    }

    public short getAccessFlags(short defaultFlags) {
        short flags;
        if (getFlag(251658240)) {
            flags = (short) 0;
            if (getFlag(16777216)) {
                flags = (short) 2;
            }
            if (getFlag(33554432)) {
                flags = (short) (flags | 4);
            }
            if (getFlag(67108864)) {
                flags = (short) (flags | 1);
            }
        } else {
            flags = defaultFlags;
        }
        if (getFlag(VOLATILE_ACCESS)) {
            flags = (short) (flags | 64);
        }
        if (getFlag(TRANSIENT_ACCESS)) {
            flags = (short) (flags | 128);
        }
        if (getFlag(ENUM_ACCESS)) {
            flags = (short) (flags | 16384);
        }
        if (getFlag(FINAL_ACCESS)) {
            return (short) (flags | 16);
        }
        return flags;
    }

    public final boolean isAlias() {
        return (this.flags & 256) != 0;
    }

    public final void setAlias(boolean flag) {
        setFlag(flag, 256);
    }

    public final boolean isFluid() {
        return (this.flags & 16) != 0;
    }

    public final void setFluid(boolean fluid) {
        setFlag(fluid, 16);
    }

    public final boolean isProcedureDecl() {
        return (this.flags & 128) != 0;
    }

    public final void setProcedureDecl(boolean val) {
        setFlag(val, 128);
    }

    public final boolean isNamespaceDecl() {
        return (this.flags & 2097152) != 0;
    }

    public final boolean isIndirectBinding() {
        return (this.flags & 1) != 0;
    }

    public final void setIndirectBinding(boolean indirectBinding) {
        setFlag(indirectBinding, 1);
    }

    public void maybeIndirectBinding(Compilation comp) {
        if ((isLexical() && !(this.context instanceof ModuleExp)) || this.context == comp.mainLambda) {
            setIndirectBinding(true);
        }
    }

    public final boolean getCanRead() {
        return (this.flags & 2) != 0;
    }

    public final void setCanRead(boolean read) {
        setFlag(read, 2);
    }

    public final void setCanRead() {
        setFlag(true, 2);
        if (this.base != null) {
            this.base.setCanRead();
        }
    }

    public final boolean getCanCall() {
        return (this.flags & 4) != 0;
    }

    public final void setCanCall(boolean called) {
        setFlag(called, 4);
    }

    public final void setCanCall() {
        setFlag(true, 4);
        if (this.base != null) {
            this.base.setCanRead();
        }
    }

    public final boolean getCanWrite() {
        return (this.flags & 8) != 0;
    }

    public final void setCanWrite(boolean written) {
        if (written) {
            this.flags |= 8;
        } else {
            this.flags &= -9;
        }
    }

    public final void setCanWrite() {
        this.flags |= 8;
        if (this.base != null) {
            this.base.setCanRead();
        }
    }

    public final boolean isThisParameter() {
        return this.symbol == ThisExp.THIS_NAME;
    }

    public boolean ignorable() {
        if (getCanRead() || isPublic()) {
            return false;
        }
        if (getCanWrite() && getFlag(65536)) {
            return false;
        }
        if (!getCanCall()) {
            return true;
        }
        Expression value = getValue();
        if (value == null || !(value instanceof LambdaExp)) {
            return false;
        }
        LambdaExp lexp = (LambdaExp) value;
        if (!lexp.isHandlingTailCalls() || lexp.getInlineOnly()) {
            return true;
        }
        return false;
    }

    public boolean needsInit() {
        return !ignorable() && (this.value != QuoteExp.nullExp || this.base == null);
    }

    public boolean isStatic() {
        if (this.field != null) {
            return this.field.getStaticFlag();
        }
        if (getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) || isCompiletimeConstant()) {
            return true;
        }
        if (getFlag(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM)) {
            return false;
        }
        LambdaExp lambda = this.context.currentLambda();
        if ((lambda instanceof ModuleExp) && ((ModuleExp) lambda).isStatic()) {
            return true;
        }
        return false;
    }

    public final boolean isLexical() {
        return (this.flags & 268501008) == 0;
    }

    public static final boolean isUnknown(Declaration decl) {
        return decl == null || decl.getFlag(65536);
    }

    public void noteValue(Expression value) {
        if (this.value == QuoteExp.undefined_exp) {
            if (value instanceof LambdaExp) {
                ((LambdaExp) value).nameDecl = this;
            }
            this.value = value;
        } else if (this.value != value) {
            if (this.value instanceof LambdaExp) {
                ((LambdaExp) this.value).nameDecl = null;
            }
            this.value = null;
        }
    }

    protected Declaration() {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.value = QuoteExp.undefined_exp;
        this.flags = 64;
        this.makeLocationMethod = null;
    }

    public Declaration(Variable var) {
        this(var.getName(), var.getType());
        this.var = var;
    }

    public Declaration(Object name) {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.value = QuoteExp.undefined_exp;
        this.flags = 64;
        this.makeLocationMethod = null;
        setName(name);
    }

    public Declaration(Object name, Type type) {
        int i = counter + 1;
        counter = i;
        this.id = i;
        this.value = QuoteExp.undefined_exp;
        this.flags = 64;
        this.makeLocationMethod = null;
        setName(name);
        setType(type);
    }

    public Declaration(Object name, Field field) {
        this(name, field.getType());
        this.field = field;
        setSimple(false);
    }

    public void pushIndirectBinding(Compilation comp) {
        CodeAttr code = comp.getCode();
        code.emitPushString(getName());
        if (this.makeLocationMethod == null) {
            this.makeLocationMethod = Compilation.typeLocation.addMethod("make", new Type[]{Type.pointer_type, Type.string_type}, Compilation.typeLocation, 9);
        }
        code.emitInvokeStatic(this.makeLocationMethod);
    }

    public final Variable allocateVariable(CodeAttr code) {
        if (!isSimple() || this.var == null) {
            String vname = null;
            if (this.symbol != null) {
                vname = Compilation.mangleNameIfNeeded(getName());
            }
            if (isAlias() && (getValue() instanceof ReferenceExp)) {
                Declaration base = followAliases(this);
                this.var = base == null ? null : base.var;
            } else {
                this.var = this.context.getVarScope().addVariable(code, isIndirectBinding() ? Compilation.typeLocation : getType().getImplementationType(), vname);
            }
        }
        return this.var;
    }

    public final void setLocation(SourceLocator location) {
        this.filename = location.getFileName();
        setLine(location.getLineNumber(), location.getColumnNumber());
    }

    public final void setFile(String filename) {
        this.filename = filename;
    }

    public final void setLine(int lineno, int colno) {
        if (lineno < 0) {
            lineno = 0;
        }
        if (colno < 0) {
            colno = 0;
        }
        this.position = (lineno << 12) + colno;
    }

    public final void setLine(int lineno) {
        setLine(lineno, 0);
    }

    public final String getFileName() {
        return this.filename;
    }

    public String getPublicId() {
        return null;
    }

    public String getSystemId() {
        return this.filename;
    }

    public final int getLineNumber() {
        int line = this.position >> 12;
        return line == 0 ? -1 : line;
    }

    public final int getColumnNumber() {
        int column = this.position & 4095;
        return column == 0 ? -1 : column;
    }

    public boolean isStableSourceLocation() {
        return true;
    }

    public void printInfo(OutPort out) {
        StringBuffer sbuf = new StringBuffer();
        printInfo(sbuf);
        out.print(sbuf.toString());
    }

    public void printInfo(StringBuffer sbuf) {
        sbuf.append(this.symbol);
        sbuf.append('/');
        sbuf.append(this.id);
        sbuf.append("/fl:");
        sbuf.append(Long.toHexString(this.flags));
        if (ignorable()) {
            sbuf.append("(ignorable)");
        }
        Expression tx = this.typeExp;
        Type t = getType();
        if (tx != null && !(tx instanceof QuoteExp)) {
            sbuf.append("::");
            sbuf.append(tx);
        } else if (!(this.type == null || t == Type.pointer_type)) {
            sbuf.append("::");
            sbuf.append(t.getName());
        }
        if (this.base != null) {
            sbuf.append("(base:#");
            sbuf.append(this.base.id);
            sbuf.append(')');
        }
    }

    public String toString() {
        return "Declaration[" + this.symbol + '/' + this.id + ']';
    }

    public static Declaration followAliases(Declaration decl) {
        while (decl != null && decl.isAlias()) {
            Expression declValue = decl.getValue();
            if (!(declValue instanceof ReferenceExp)) {
                break;
            }
            Declaration orig = ((ReferenceExp) declValue).binding;
            if (orig == null) {
                break;
            }
            decl = orig;
        }
        return decl;
    }

    public void makeField(Compilation comp, Expression value) {
        setSimple(false);
        makeField(comp.mainClass, comp, value);
    }

    public void makeField(ClassType frameType, Compilation comp, Expression value) {
        boolean external_access = needsExternalAccess();
        int fflags = 0;
        boolean isConstant = getFlag(16384);
        boolean typeSpecified = getFlag(8192);
        if (comp.immediate && (this.context instanceof ModuleExp) && !isConstant && !typeSpecified) {
            setIndirectBinding(true);
        }
        if (isPublic() || external_access || comp.immediate) {
            fflags = 0 | 1;
        }
        if (isStatic() || ((getFlag(268501008) && isIndirectBinding() && !isAlias()) || ((value instanceof ClassExp) && !((LambdaExp) value).getNeedsClosureEnv()))) {
            fflags |= 8;
        }
        if ((isIndirectBinding() || (isConstant && (shouldEarlyInit() || ((this.context instanceof ModuleExp) && ((ModuleExp) this.context).staticInitRun())))) && ((this.context instanceof ClassExp) || (this.context instanceof ModuleExp))) {
            fflags |= 16;
        }
        Type ftype = getType().getImplementationType();
        if (isIndirectBinding() && !ftype.isSubtype(Compilation.typeLocation)) {
            ftype = Compilation.typeLocation;
        }
        if (!ignorable()) {
            int nlength;
            String fname = getName();
            if (fname == null) {
                fname = "$unnamed$0";
                nlength = fname.length() - 2;
            } else {
                fname = Compilation.mangleNameIfNeeded(fname);
                if (getFlag(65536)) {
                    fname = UNKNOWN_PREFIX + fname;
                }
                if (external_access && !getFlag(1073741824)) {
                    fname = PRIVATE_PREFIX + fname;
                }
                nlength = fname.length();
            }
            int counter = 0;
            while (frameType.getDeclaredField(fname) != null) {
                counter++;
                fname = fname.substring(0, nlength) + '$' + counter;
            }
            this.field = frameType.addField(fname, ftype, fflags);
            if (value instanceof QuoteExp) {
                Object value2 = ((QuoteExp) value).getValue();
                if (this.field.getStaticFlag() && value2.getClass().getName().equals(ftype.getName())) {
                    Literal literal = comp.litTable.findLiteral(value2);
                    if (literal.field == null) {
                        literal.assign(this.field, comp.litTable);
                    }
                } else if ((ftype instanceof PrimType) || "java.lang.String".equals(ftype.getName())) {
                    if (value2 instanceof Char) {
                        value2 = IntNum.make(((Char) value2).intValue());
                    }
                    this.field.setConstantValue(value2, frameType);
                    return;
                }
            }
        }
        if (!shouldEarlyInit()) {
            if (isIndirectBinding() || !(value == null || (value instanceof ClassExp))) {
                BindingInitializer.create(this, value, comp);
            }
        }
    }

    Location makeIndirectLocationFor() {
        return Location.make(this.symbol instanceof Symbol ? (Symbol) this.symbol : Namespace.EmptyNamespace.getSymbol(this.symbol.toString().intern()));
    }

    public static Declaration getDeclarationFromStatic(String cname, String fname) {
        Declaration decl = new Declaration((Object) fname, ClassType.make(cname).getDeclaredField(fname));
        decl.setFlag(18432);
        return decl;
    }

    public static Declaration getDeclarationValueFromStatic(String className, String fieldName, String name) {
        try {
            Object value = Class.forName(className).getDeclaredField(fieldName).get(null);
            Declaration decl = new Declaration((Object) name, ClassType.make(className).getDeclaredField(fieldName));
            decl.noteValue(new QuoteExp(value));
            decl.setFlag(18432);
            return decl;
        } catch (Throwable ex) {
            throw new WrappedException(ex);
        }
    }

    public static Declaration getDeclaration(Named proc) {
        return getDeclaration(proc, proc.getName());
    }

    public static Declaration getDeclaration(Object proc, String name) {
        Field procField = null;
        if (name != null) {
            Class procClass = PrimProcedure.getProcedureClass(proc);
            if (procClass != null) {
                procField = ((ClassType) Type.make(procClass)).getDeclaredField(Compilation.mangleNameIfNeeded(name));
            }
        }
        if (procField != null) {
            int fflags = procField.getModifiers();
            if ((fflags & 8) != 0) {
                Declaration decl = new Declaration((Object) name, procField);
                decl.noteValue(new QuoteExp(proc));
                if ((fflags & 16) == 0) {
                    return decl;
                }
                decl.setFlag(16384);
                return decl;
            }
        }
        return null;
    }
}

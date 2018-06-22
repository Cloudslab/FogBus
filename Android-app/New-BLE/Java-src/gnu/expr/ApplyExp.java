package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.util.IdentityHashTable;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.text.SourceMessages;

public class ApplyExp extends Expression {
    public static final int INLINE_IF_CONSTANT = 4;
    public static final int MAY_CONTAIN_BACK_JUMP = 8;
    public static final int TAILCALL = 2;
    Expression[] args;
    LambdaExp context;
    Expression func;
    public ApplyExp nextCall;
    protected Type type;

    public final Expression getFunction() {
        return this.func;
    }

    public final Expression[] getArgs() {
        return this.args;
    }

    public final int getArgCount() {
        return this.args.length;
    }

    public void setFunction(Expression func) {
        this.func = func;
    }

    public void setArgs(Expression[] args) {
        this.args = args;
    }

    public Expression getArg(int i) {
        return this.args[i];
    }

    public void setArg(int i, Expression arg) {
        this.args[i] = arg;
    }

    public final boolean isTailCall() {
        return getFlag(2);
    }

    public final void setTailCall(boolean tailCall) {
        setFlag(tailCall, 2);
    }

    public final Object getFunctionValue() {
        return this.func instanceof QuoteExp ? ((QuoteExp) this.func).getValue() : null;
    }

    public ApplyExp(Expression f, Expression[] a) {
        this.func = f;
        this.args = a;
    }

    public ApplyExp(Procedure p, Expression[] a) {
        this.func = new QuoteExp(p);
        this.args = a;
    }

    public ApplyExp(Method m, Expression[] a) {
        this.func = new QuoteExp(new PrimProcedure(m));
        this.args = a;
    }

    protected boolean mustCompile() {
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        Object proc = this.func.eval(ctx);
        int n = this.args.length;
        Object[] vals = new Object[n];
        for (int i = 0; i < n; i++) {
            vals[i] = this.args[i].eval(ctx);
        }
        ((Procedure) proc).checkN(vals, ctx);
    }

    public static void compileToArray(Expression[] args, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (args.length == 0) {
            code.emitGetStatic(Compilation.noArgsField);
            return;
        }
        code.emitPushInt(args.length);
        code.emitNewArray(Type.pointer_type);
        for (int i = 0; i < args.length; i++) {
            Expression arg = args[i];
            if (!comp.usingCPStyle() || (arg instanceof QuoteExp) || (arg instanceof ReferenceExp)) {
                code.emitDup(Compilation.objArrayType);
                code.emitPushInt(i);
                arg.compileWithPosition(comp, Target.pushObject);
            } else {
                arg.compileWithPosition(comp, Target.pushObject);
                code.emitSwap();
                code.emitDup(1, 1);
                code.emitSwap();
                code.emitPushInt(i);
                code.emitSwap();
            }
            code.emitArrayStore(Type.pointer_type);
        }
    }

    public void compile(Compilation comp, Target target) {
        compile(this, comp, target, true);
    }

    public static void compile(ApplyExp exp, Compilation comp, Target target) {
        compile(exp, comp, target, false);
    }

    static void compile(ApplyExp exp, Compilation comp, Target target, boolean checkInlineable) {
        Procedure procedure;
        Method method;
        Expression exp_func = exp.func;
        LambdaExp func_lambda = null;
        Declaration owner = null;
        Procedure quotedValue = null;
        String func_name;
        if (exp_func instanceof LambdaExp) {
            func_lambda = (LambdaExp) exp_func;
            if (func_lambda.getName() == null) {
                func_name = "<lambda>";
                procedure = null;
            }
            procedure = null;
        } else if (exp_func instanceof ReferenceExp) {
            ReferenceExp func_ref = (ReferenceExp) exp_func;
            owner = func_ref.contextDecl();
            Declaration func_decl = func_ref.binding;
            while (func_decl != null && func_decl.isAlias() && (func_decl.value instanceof ReferenceExp)) {
                func_ref = func_decl.value;
                if (owner != null || func_decl.needsContext() || func_ref.binding == null) {
                    break;
                }
                func_decl = func_ref.binding;
                owner = func_ref.contextDecl();
            }
            if (!func_decl.getFlag(65536)) {
                Expression value = func_decl.getValue();
                func_name = func_decl.getName();
                if (value != null && (value instanceof LambdaExp)) {
                    func_lambda = (LambdaExp) value;
                }
                if (value != null && (value instanceof QuoteExp)) {
                    quotedValue = ((QuoteExp) value).getValue();
                }
            }
            procedure = quotedValue;
        } else {
            if (exp_func instanceof QuoteExp) {
                procedure = ((QuoteExp) exp_func).getValue();
            }
            procedure = null;
        }
        if (checkInlineable && (procedure instanceof Procedure)) {
            Procedure proc = procedure;
            if ((target instanceof IgnoreTarget) && proc.isSideEffectFree()) {
                for (Expression compile : exp.args) {
                    compile.compile(comp, target);
                }
                return;
            }
            try {
                if (inlineCompile(proc, exp, comp, target)) {
                    return;
                }
            } catch (Throwable ex) {
                comp.getMessages().error('e', "caught exception in inline-compiler for " + procedure + " - " + ex, ex);
                return;
            }
        }
        CodeAttr code = comp.getCode();
        if (func_lambda != null) {
            if ((func_lambda.max_args < 0 || args_length <= func_lambda.max_args) && args_length >= func_lambda.min_args) {
                int conv = func_lambda.getCallConvention();
                if (comp.inlineOk((Expression) func_lambda) && (conv <= 2 || (conv == 3 && !exp.isTailCall()))) {
                    method = func_lambda.getMethod(args_length);
                    if (method != null) {
                        Type type;
                        PrimProcedure primProcedure = new PrimProcedure(method, func_lambda);
                        boolean is_static = method.getStaticFlag();
                        boolean extraArg = false;
                        if (!(is_static && func_lambda.declareClosureEnv() == null)) {
                            if (is_static) {
                                extraArg = true;
                            }
                            if (comp.curLambda == func_lambda) {
                                code.emitLoad(func_lambda.closureEnv != null ? func_lambda.closureEnv : func_lambda.thisVariable);
                            } else if (owner != null) {
                                owner.load(null, 0, comp, Target.pushObject);
                            } else {
                                func_lambda.getOwningLambda().loadHeapFrame(comp);
                            }
                        }
                        if (extraArg) {
                            type = Type.voidType;
                        } else {
                            type = null;
                        }
                        primProcedure.compile(type, exp, comp, target);
                        return;
                    }
                }
            }
            throw new Error("internal error - wrong number of parameters for " + func_lambda);
        }
        boolean tail_recurse = exp.isTailCall() && func_lambda != null && func_lambda == comp.curLambda;
        if (func_lambda != null && func_lambda.getInlineOnly() && !tail_recurse && func_lambda.min_args == args_length) {
            pushArgs(func_lambda, exp.args, null, comp);
            if (func_lambda.getFlag(128)) {
                popParams(code, func_lambda, null, false);
                code.emitTailCall(false, func_lambda.getVarScope());
                return;
            }
            func_lambda.flags |= 128;
            LambdaExp saveLambda = comp.curLambda;
            comp.curLambda = func_lambda;
            func_lambda.allocChildClasses(comp);
            func_lambda.allocParameters(comp);
            popParams(code, func_lambda, null, false);
            func_lambda.enterFunction(comp);
            func_lambda.body.compileWithPosition(comp, target);
            func_lambda.compileEnd(comp);
            func_lambda.generateApplyMethods(comp);
            code.popScope();
            comp.curLambda = saveLambda;
        } else if (!comp.curLambda.isHandlingTailCalls() || (!(exp.isTailCall() || (target instanceof ConsumerTarget)) || comp.curLambda.getInlineOnly())) {
            boolean toArray;
            if (!tail_recurse) {
                exp_func.compile(comp, new StackTarget(Compilation.typeProcedure));
            }
            if (tail_recurse) {
                toArray = func_lambda.min_args != func_lambda.max_args;
            } else {
                toArray = args_length > 4;
            }
            int[] incValues = null;
            if (toArray) {
                compileToArray(exp.args, comp);
                method = Compilation.applyNmethod;
            } else if (tail_recurse) {
                incValues = new int[exp.args.length];
                pushArgs(func_lambda, exp.args, incValues, comp);
                method = null;
            } else {
                for (Expression compile2 : exp.args) {
                    compile2.compileWithPosition(comp, Target.pushObject);
                    if (!code.reachableHere()) {
                        break;
                    }
                }
                method = Compilation.applymethods[args_length];
            }
            if (!code.reachableHere()) {
                comp.error('e', "unreachable code");
            } else if (tail_recurse) {
                popParams(code, func_lambda, incValues, toArray);
                code.emitTailCall(false, func_lambda.getVarScope());
            } else {
                code.emitInvokeVirtual(method);
                target.compileFromStack(comp, Type.pointer_type);
            }
        } else {
            ClassType typeContext = Compilation.typeCallContext;
            exp_func.compile(comp, new StackTarget(Compilation.typeProcedure));
            if (args_length <= 4) {
                for (Expression compile22 : exp.args) {
                    compile22.compileWithPosition(comp, Target.pushObject);
                }
                comp.loadCallContext();
                code.emitInvoke(Compilation.typeProcedure.getDeclaredMethod("check" + args_length, args_length + 1));
            } else {
                compileToArray(exp.args, comp);
                comp.loadCallContext();
                code.emitInvoke(Compilation.typeProcedure.getDeclaredMethod("checkN", 2));
            }
            if (exp.isTailCall()) {
                code.emitReturn();
            } else if (((ConsumerTarget) target).isContextTarget()) {
                comp.loadCallContext();
                code.emitInvoke(typeContext.getDeclaredMethod("runUntilDone", 0));
            } else {
                comp.loadCallContext();
                code.emitLoad(((ConsumerTarget) target).getConsumerVariable());
                code.emitInvoke(typeContext.getDeclaredMethod("runUntilValue", 1));
            }
        }
    }

    public Expression deepCopy(IdentityHashTable mapper) {
        Expression f = Expression.deepCopy(this.func, mapper);
        Expression[] a = Expression.deepCopy(this.args, mapper);
        if ((f == null && this.func != null) || (a == null && this.args != null)) {
            return null;
        }
        Expression copy = new ApplyExp(f, a);
        copy.flags = getFlags();
        return copy;
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitApplyExp(this, d);
    }

    public void visitArgs(InlineCalls visitor) {
        this.args = visitor.visitExps(this.args, this.args.length, null);
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        this.func = visitor.visitAndUpdate(this.func, d);
        if (visitor.exitValue == null) {
            this.args = visitor.visitExps(this.args, this.args.length, d);
        }
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(Apply", ")", 2);
        if (isTailCall()) {
            out.print(" [tailcall]");
        }
        if (!(this.type == null || this.type == Type.pointer_type)) {
            out.print(" => ");
            out.print(this.type);
        }
        out.writeSpaceFill();
        printLineColumn(out);
        this.func.print(out);
        for (Expression print : this.args) {
            out.writeSpaceLinear();
            print.print(out);
        }
        out.endLogicalBlock(")");
    }

    private static void pushArgs(LambdaExp lexp, Expression[] args, int[] incValues, Compilation comp) {
        Declaration param = lexp.firstDecl();
        int args_length = args.length;
        for (int i = 0; i < args_length; i++) {
            Expression arg = args[i];
            if (param.ignorable()) {
                arg.compile(comp, Target.Ignore);
            } else {
                if (incValues != null) {
                    int canUseInc = SetExp.canUseInc(arg, param);
                    incValues[i] = canUseInc;
                    if (canUseInc != 65536) {
                    }
                }
                arg.compileWithPosition(comp, StackTarget.getInstance(param.getType()));
            }
            param = param.nextDecl();
        }
    }

    private static void popParams(CodeAttr code, LambdaExp lexp, int[] incValues, boolean toArray) {
        Variable vars = lexp.getVarScope().firstVar();
        Declaration decls = lexp.firstDecl();
        if (vars != null && vars.getName() == "this") {
            vars = vars.nextVar();
        }
        if (vars != null && vars.getName() == "$ctx") {
            vars = vars.nextVar();
        }
        if (vars != null && vars.getName() == "argsArray") {
            if (toArray) {
                popParams(code, 0, 1, null, decls, vars);
                return;
            }
            vars = vars.nextVar();
        }
        popParams(code, 0, lexp.min_args, incValues, decls, vars);
    }

    private static void popParams(CodeAttr code, int paramNo, int count, int[] incValues, Declaration decl, Variable vars) {
        if (count > 0) {
            popParams(code, paramNo + 1, count - 1, incValues, decl.nextDecl(), decl.getVariable() == null ? vars : vars.nextVar());
            if (!decl.ignorable()) {
                if (incValues == null || incValues[paramNo] == 65536) {
                    code.emitStore(vars);
                } else {
                    code.emitInc(vars, (short) incValues[paramNo]);
                }
            }
        }
    }

    public final Type getTypeRaw() {
        return this.type;
    }

    public final void setType(Type type) {
        this.type = type;
    }

    public boolean side_effects() {
        Object value = derefFunc(this.func).valueIfConstant();
        if (!(value instanceof Procedure) || !((Procedure) value).isSideEffectFree()) {
            return true;
        }
        for (Expression side_effects : this.args) {
            if (side_effects.side_effects()) {
                return true;
            }
        }
        return false;
    }

    static Expression derefFunc(Expression afunc) {
        if (!(afunc instanceof ReferenceExp)) {
            return afunc;
        }
        Declaration func_decl = Declaration.followAliases(((ReferenceExp) afunc).binding);
        if (func_decl == null || func_decl.getFlag(65536)) {
            return afunc;
        }
        return func_decl.getValue();
    }

    public final Type getType() {
        if (this.type != null) {
            return this.type;
        }
        Expression afunc = derefFunc(this.func);
        this.type = Type.objectType;
        if (afunc instanceof QuoteExp) {
            Object value = ((QuoteExp) afunc).getValue();
            if (value instanceof Procedure) {
                this.type = ((Procedure) value).getReturnType(this.args);
            }
        } else if (afunc instanceof LambdaExp) {
            this.type = ((LambdaExp) afunc).getReturnType();
        }
        return this.type;
    }

    public static Inlineable asInlineable(Procedure proc) {
        if (proc instanceof Inlineable) {
            return (Inlineable) proc;
        }
        return (Inlineable) Procedure.compilerKey.get(proc);
    }

    static boolean inlineCompile(Procedure proc, ApplyExp exp, Compilation comp, Target target) throws Throwable {
        Inlineable compiler = asInlineable(proc);
        if (compiler == null) {
            return false;
        }
        compiler.compile(exp, comp, target);
        return true;
    }

    public final Expression inlineIfConstant(Procedure proc, InlineCalls visitor) {
        return inlineIfConstant(proc, visitor.getMessages());
    }

    public final Expression inlineIfConstant(Procedure proc, SourceMessages messages) {
        int len = this.args.length;
        Object[] vals = new Object[len];
        int i = len;
        while (true) {
            i--;
            if (i >= 0) {
                Expression arg = this.args[i];
                if (arg instanceof ReferenceExp) {
                    Declaration decl = ((ReferenceExp) arg).getBinding();
                    if (decl != null) {
                        arg = decl.getValue();
                        if (arg == QuoteExp.undefined_exp) {
                            return this;
                        }
                    }
                }
                if (!(arg instanceof QuoteExp)) {
                    return this;
                }
                vals[i] = ((QuoteExp) arg).getValue();
            } else {
                try {
                    return new QuoteExp(proc.applyN(vals), this.type);
                } catch (Throwable ex) {
                    if (messages == null) {
                        return this;
                    }
                    messages.error('w', "call to " + proc + " throws " + ex);
                    return this;
                }
            }
        }
    }

    public String toString() {
        if (this == LambdaExp.unknownContinuation) {
            return "ApplyExp[unknownContinuation]";
        }
        return "ApplyExp/" + (this.args == null ? 0 : this.args.length) + '[' + this.func + ']';
    }
}

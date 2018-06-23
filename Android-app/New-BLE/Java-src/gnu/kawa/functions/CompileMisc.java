package gnu.kawa.functions;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.ConsumerTarget;
import gnu.expr.Declaration;
import gnu.expr.ExpVisitor;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.IgnoreTarget;
import gnu.expr.InlineCalls;
import gnu.expr.Inlineable;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.expr.TryExp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.LList;
import gnu.mapping.Namespace;
import gnu.mapping.Procedure;
import gnu.mapping.WrongArguments;
import kawa.standard.Scheme;

public class CompileMisc implements Inlineable {
    static final int CONVERT = 2;
    static final int NOT = 3;
    static Method coerceMethod;
    public static final ClassType typeContinuation = ClassType.make("kawa.lang.Continuation");
    static ClassType typeType;
    int code;
    Procedure proc;

    static class ExitThroughFinallyChecker extends ExpVisitor<Expression, TryExp> {
        Declaration decl;

        ExitThroughFinallyChecker() {
        }

        public static boolean check(Declaration decl, Expression body) {
            ExitThroughFinallyChecker visitor = new ExitThroughFinallyChecker();
            visitor.decl = decl;
            visitor.visit(body, null);
            return visitor.exitValue != null;
        }

        protected Expression defaultValue(Expression r, TryExp d) {
            return r;
        }

        protected Expression visitReferenceExp(ReferenceExp exp, TryExp currentTry) {
            if (this.decl == exp.getBinding() && currentTry != null) {
                this.exitValue = Boolean.TRUE;
            }
            return exp;
        }

        protected Expression visitTryExp(TryExp exp, TryExp currentTry) {
            if (exp.getFinallyClause() != null) {
                currentTry = exp;
            }
            visitExpression(exp, currentTry);
            return exp;
        }
    }

    public CompileMisc(Procedure proc, int code) {
        this.proc = proc;
        this.code = code;
    }

    public static CompileMisc forConvert(Object proc) {
        return new CompileMisc((Procedure) proc, 2);
    }

    public static CompileMisc forNot(Object proc) {
        return new CompileMisc((Procedure) proc, 3);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        switch (this.code) {
            case 2:
                compileConvert((Convert) this.proc, exp, comp, target);
                return;
            case 3:
                compileNot((Not) this.proc, exp, comp, target);
                return;
            default:
                throw new Error();
        }
    }

    public static Expression validateApplyConstantFunction0(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        int nargs = exp.getArgCount();
        if (nargs == 0 || visitor == null) {
            return ((ConstantFunction0) proc).constant;
        }
        return visitor.noteError(WrongArguments.checkArgCount(proc, nargs));
    }

    public static Expression validateApplyConvert(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Compilation comp = visitor.getCompilation();
        Language language = comp.getLanguage();
        Expression[] args = exp.getArgs();
        if (args.length == 2) {
            args[0] = visitor.visit(args[0], null);
            Type type = language.getTypeFor(args[0]);
            if (type instanceof Type) {
                args[0] = new QuoteExp(type);
                args[1] = visitor.visit(args[1], type);
                CompileReflect.checkKnownClass(type, comp);
                exp.setType(type);
                return exp;
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static Expression validateApplyNot(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        exp.setType(visitor.getCompilation().getLanguage().getTypeFor(Boolean.TYPE));
        return exp.inlineIfConstant(proc, visitor);
    }

    public static Expression validateApplyFormat(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Type retType = Type.objectType;
        Expression[] args = exp.getArgs();
        if (args.length > 0) {
            ClassType typeFormat = ClassType.make("gnu.kawa.functions.Format");
            Boolean f = args[0].valueIfConstant();
            Type ftype = args[0].getType();
            Expression[] xargs;
            ApplyExp ae;
            if (f == Boolean.FALSE || ftype.isSubtype(LangObjType.stringType)) {
                int skip = f == Boolean.FALSE ? 1 : 0;
                xargs = new Expression[((args.length + 1) - skip)];
                xargs[0] = new QuoteExp(Integer.valueOf(0), Type.intType);
                System.arraycopy(args, skip, xargs, 1, xargs.length - 1);
                ae = new ApplyExp(typeFormat.getDeclaredMethod("formatToString", 2), xargs);
                ae.setType(Type.javalangStringType);
                return ae;
            } else if (f == Boolean.TRUE || ftype.isSubtype(ClassType.make("java.io.Writer"))) {
                if (f == Boolean.TRUE) {
                    xargs = new Expression[args.length];
                    xargs[0] = QuoteExp.nullExp;
                    System.arraycopy(args, 1, xargs, 1, args.length - 1);
                    args = xargs;
                }
                ae = new ApplyExp(typeFormat.getDeclaredMethod("formatToWriter", 3), args);
                ae.setType(Type.voidType);
                return ae;
            } else if (ftype.isSubtype(ClassType.make("java.io.OutputStream"))) {
                retType = Type.voidType;
            }
        }
        exp.setType(retType);
        return null;
    }

    public static Expression validateApplyAppendValues(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            return args[0];
        }
        if (args.length == 0) {
            return QuoteExp.voidExp;
        }
        Expression folded = exp.inlineIfConstant(proc, visitor);
        if (folded == exp) {
            return exp;
        }
        return folded;
    }

    public static Expression validateApplyMakeProcedure(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        Object key;
        String keyword;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        int alen = args.length;
        Expression method = null;
        int countMethods = 0;
        String name = null;
        int i = 0;
        while (i < alen) {
            Expression next;
            Expression arg = args[i];
            if (arg instanceof QuoteExp) {
                key = ((QuoteExp) arg).getValue();
                if (key instanceof Keyword) {
                    keyword = ((Keyword) key).getName();
                    i++;
                    next = args[i];
                    if (keyword == "name") {
                        if (next instanceof QuoteExp) {
                            name = ((QuoteExp) next).getValue().toString();
                        }
                    } else if (keyword == "method") {
                        countMethods++;
                        method = next;
                    }
                    i++;
                }
            }
            countMethods++;
            method = arg;
            i++;
        }
        if (countMethods != 1 || !(method instanceof LambdaExp)) {
            return exp;
        }
        LambdaExp lexp = (LambdaExp) method;
        i = 0;
        while (i < alen) {
            arg = args[i];
            if (arg instanceof QuoteExp) {
                key = ((QuoteExp) arg).getValue();
                if (key instanceof Keyword) {
                    keyword = ((Keyword) key).getName();
                    i++;
                    next = args[i];
                    if (keyword == "name") {
                        lexp.setName(name);
                    } else if (keyword != "method") {
                        lexp.setProperty(Namespace.EmptyNamespace.getSymbol(keyword), next);
                    }
                }
            }
            i++;
        }
        return method;
    }

    public static Expression validateApplyValuesMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        exp.visitArgs(visitor);
        LambdaExp lexp = ValuesMap.canInline(exp, (ValuesMap) proc);
        if (lexp != null) {
            lexp.setInlineOnly(true);
            lexp.returnContinuation = exp;
            lexp.inlineHome = visitor.getCurrentLambda();
        }
        return exp;
    }

    public static void compileConvert(Convert proc, ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        if (args.length != 2) {
            throw new Error("wrong number of arguments to " + proc.getName());
        }
        CodeAttr code = comp.getCode();
        Type type = Scheme.getTypeValue(args[0]);
        if (type != null) {
            args[1].compile(comp, Target.pushValue(type));
            if (code.reachableHere()) {
                target.compileFromStack(comp, type);
                return;
            }
            return;
        }
        if (typeType == null) {
            typeType = ClassType.make("gnu.bytecode.Type");
        }
        if (coerceMethod == null) {
            coerceMethod = typeType.addMethod("coerceFromObject", Compilation.apply1args, Type.pointer_type, 1);
        }
        args[0].compile(comp, LangObjType.typeClassType);
        args[1].compile(comp, Target.pushObject);
        code.emitInvokeVirtual(coerceMethod);
        target.compileFromStack(comp, Type.pointer_type);
    }

    public void compileNot(Not proc, ApplyExp exp, Compilation comp, Target target) {
        Expression arg = exp.getArgs()[0];
        Language language = proc.language;
        if (target instanceof ConditionalTarget) {
            ConditionalTarget ctarget = (ConditionalTarget) target;
            Target sub_target = new ConditionalTarget(ctarget.ifFalse, ctarget.ifTrue, language);
            sub_target.trueBranchComesFirst = !ctarget.trueBranchComesFirst;
            arg.compile(comp, sub_target);
            return;
        }
        CodeAttr code = comp.getCode();
        Type type = target.getType();
        if ((target instanceof StackTarget) && type.getSignature().charAt(0) == 'Z') {
            arg.compile(comp, target);
            code.emitNot(target.getType());
            return;
        }
        IfExp.compile(arg, QuoteExp.getInstance(language.booleanObject(false)), QuoteExp.getInstance(language.booleanObject(true)), comp, target);
    }

    public static Expression validateApplyCallCC(ApplyExp exp, InlineCalls visitor, Type required, Procedure proc) {
        LambdaExp lexp = canInlineCallCC(exp);
        if (lexp != null) {
            lexp.setInlineOnly(true);
            lexp.returnContinuation = exp;
            lexp.inlineHome = visitor.getCurrentLambda();
            Declaration contDecl = lexp.firstDecl();
            if (!contDecl.getFlag(8192)) {
                contDecl.setType(typeContinuation);
            }
        }
        exp.visitArgs(visitor);
        return exp;
    }

    public static void compileCallCC(ApplyExp exp, Compilation comp, Target target, Procedure proc) {
        Expression lambda = canInlineCallCC(exp);
        if (lambda == null) {
            ApplyExp.compile(exp, comp, target);
            return;
        }
        CodeAttr code = comp.getCode();
        Declaration param = lambda.firstDecl();
        if (!param.isSimple() || param.getCanRead() || param.getCanWrite()) {
            Variable contVar = code.pushScope().addVariable(code, typeContinuation, null);
            Declaration contDecl = new Declaration(contVar);
            code.emitNew(typeContinuation);
            code.emitDup(typeContinuation);
            comp.loadCallContext();
            code.emitInvokeSpecial(typeContinuation.getDeclaredMethod("<init>", 1));
            code.emitStore(contVar);
            Type type = ((target instanceof IgnoreTarget) || (target instanceof ConsumerTarget)) ? null : Type.objectType;
            code.emitTryStart(false, type);
            new ApplyExp(lambda, new Expression[]{new ReferenceExp(contDecl)}).compile(comp, target);
            if (code.reachableHere()) {
                code.emitLoad(contVar);
                code.emitPushInt(1);
                code.emitPutField(typeContinuation.getField("invoked"));
            }
            code.emitTryEnd();
            code.emitCatchStart(null);
            code.emitLoad(contVar);
            if (target instanceof ConsumerTarget) {
                comp.loadCallContext();
                code.emitInvokeStatic(typeContinuation.getDeclaredMethod("handleException$X", 3));
            } else {
                code.emitInvokeStatic(typeContinuation.getDeclaredMethod("handleException", 2));
                target.compileFromStack(comp, Type.objectType);
            }
            code.emitCatchEnd();
            code.emitTryCatchEnd();
            code.popScope();
            return;
        }
        CompileTimeContinuation contProxy = new CompileTimeContinuation();
        contProxy.exitableBlock = code.startExitableBlock(target instanceof StackTarget ? target.getType() : null, ExitThroughFinallyChecker.check(param, lambda.body));
        contProxy.blockTarget = target;
        param.setValue(new QuoteExp(contProxy));
        lambda.body.compile(comp, target);
        code.endExitableBlock();
    }

    private static LambdaExp canInlineCallCC(ApplyExp exp) {
        Expression[] args = exp.getArgs();
        if (args.length == 1) {
            Expression arg0 = args[0];
            if (arg0 instanceof LambdaExp) {
                LambdaExp lexp = (LambdaExp) arg0;
                if (lexp.min_args == 1 && lexp.max_args == 1 && !lexp.firstDecl().getCanWrite()) {
                    return lexp;
                }
            }
        }
        return null;
    }

    public static Expression validateApplyMap(ApplyExp exp, InlineCalls visitor, Type required, Procedure xproc) {
        Map mproc = (Map) xproc;
        boolean collect = mproc.collect;
        exp.visitArgs(visitor);
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        if (nargs < 2) {
            return exp;
        }
        int i;
        int i2;
        nargs--;
        Expression proc = args[0];
        boolean procSafeForMultipleEvaluation = !proc.side_effects();
        Expression letExp = new LetExp(new Expression[]{proc});
        Declaration procDecl = letExp.addDeclaration("%proc", Compilation.typeProcedure);
        procDecl.noteValue(args[0]);
        Expression[] inits2 = new Expression[1];
        letExp = new LetExp(inits2);
        letExp.setBody(letExp);
        if (collect) {
            i = nargs + 1;
        } else {
            i = nargs;
        }
        letExp = new LambdaExp(i);
        inits2[0] = letExp;
        Declaration loopDecl = letExp.addDeclaration((Object) "%loop");
        loopDecl.noteValue(letExp);
        Expression[] inits3 = new Expression[nargs];
        letExp = new LetExp(inits3);
        Declaration[] largs = new Declaration[nargs];
        Declaration[] pargs = new Declaration[nargs];
        for (i2 = 0; i2 < nargs; i2++) {
            String argName = "arg" + i2;
            largs[i2] = letExp.addDeclaration((Object) argName);
            pargs[i2] = letExp.addDeclaration(argName, Compilation.typePair);
            inits3[i2] = new ReferenceExp(largs[i2]);
            pargs[i2].noteValue(inits3[i2]);
        }
        Declaration resultDecl = collect ? letExp.addDeclaration((Object) "result") : null;
        Expression[] doArgs = new Expression[(nargs + 1)];
        if (collect) {
            i = nargs + 1;
        } else {
            i = nargs;
        }
        Expression[] recArgs = new Expression[i];
        for (i2 = 0; i2 < nargs; i2++) {
            doArgs[i2 + 1] = visitor.visitApplyOnly(SlotGet.makeGetField(new ReferenceExp(pargs[i2]), "car"), null);
            recArgs[i2] = visitor.visitApplyOnly(SlotGet.makeGetField(new ReferenceExp(pargs[i2]), "cdr"), null);
        }
        if (!procSafeForMultipleEvaluation) {
            proc = new ReferenceExp(procDecl);
        }
        doArgs[0] = proc;
        Expression doit = visitor.visitApplyOnly(new ApplyExp(new ReferenceExp(mproc.applyFieldDecl), doArgs), null);
        if (collect) {
            recArgs[nargs] = Invoke.makeInvokeStatic(Compilation.typePair, "make", new Expression[]{doit, new ReferenceExp(resultDecl)});
        }
        Expression rec = visitor.visitApplyOnly(new ApplyExp(new ReferenceExp(loopDecl), recArgs), null);
        if (!collect) {
            rec = new BeginExp(doit, rec);
        }
        letExp.body = rec;
        letExp.setBody(letExp.body);
        letExp.body = letExp;
        if (collect) {
            i = nargs + 1;
        } else {
            i = nargs;
        }
        Expression[] initArgs = new Expression[i];
        QuoteExp empty = new QuoteExp(LList.Empty);
        i2 = nargs;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            letExp.body = new IfExp(visitor.visitApplyOnly(new ApplyExp(mproc.isEq, new Expression[]{new ReferenceExp(largs[i2]), empty}), null), collect ? new ReferenceExp(resultDecl) : QuoteExp.voidExp, letExp.body);
            initArgs[i2] = args[i2 + 1];
        }
        if (collect) {
            initArgs[nargs] = empty;
        }
        Expression body = visitor.visitApplyOnly(new ApplyExp(new ReferenceExp(loopDecl), initArgs), null);
        if (collect) {
            body = Invoke.makeInvokeStatic(Compilation.scmListType, "reverseInPlace", new Expression[]{body});
        }
        letExp.setBody(body);
        if (procSafeForMultipleEvaluation) {
            return letExp;
        }
        return letExp;
    }
}

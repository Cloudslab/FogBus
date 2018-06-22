package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.kawa.reflect.Invoke;

/* compiled from: CompilationHelpers */
class SetListExp extends ApplyExp {
    public SetListExp(Expression func, Expression[] args) {
        super(func, args);
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        exp.visitArgs(visitor);
        if (exp.getArgs().length != 2) {
            return exp;
        }
        return Compilation.makeCoercion(visitor.visitApplyOnly(new ApplyExp(Invoke.invoke, new Expression[]{getArgs()[0], QuoteExp.getInstance("set"), Compilation.makeCoercion(exp.getArgs()[0], Type.intType), exp.getArgs()[1]}), required), Type.voidType);
    }
}

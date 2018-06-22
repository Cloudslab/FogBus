package gnu.expr;

public class PushApply extends ExpVisitor<Expression, Void> {
    public static void pushApply(Expression exp) {
        new PushApply().visit(exp, null);
    }

    protected Expression update(Expression exp, Expression r) {
        return r;
    }

    protected Expression defaultValue(Expression r, Void ignored) {
        return r;
    }

    protected Expression visitApplyExp(ApplyExp exp, Void ignored) {
        Expression func = exp.func;
        if ((func instanceof LetExp) && !(func instanceof FluidLetExp)) {
            LetExp let = (LetExp) func;
            Expression body = let.body;
            let.body = exp;
            exp.func = body;
            return (Expression) visit(let, ignored);
        } else if (func instanceof BeginExp) {
            BeginExp begin = (BeginExp) func;
            Expression[] stmts = begin.exps;
            int last_index = begin.exps.length - 1;
            exp.func = stmts[last_index];
            stmts[last_index] = exp;
            return (Expression) visit(begin, ignored);
        } else {
            exp.visitChildren(this, ignored);
            return exp;
        }
    }
}

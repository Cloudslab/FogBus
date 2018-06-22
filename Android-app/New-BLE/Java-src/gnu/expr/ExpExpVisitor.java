package gnu.expr;

public abstract class ExpExpVisitor<D> extends ExpVisitor<Expression, D> {
    protected Expression update(Expression exp, Expression r) {
        return r;
    }

    protected Expression defaultValue(Expression r, D d) {
        return r;
    }
}

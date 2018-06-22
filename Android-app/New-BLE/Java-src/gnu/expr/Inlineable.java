package gnu.expr;

public interface Inlineable {
    void compile(ApplyExp applyExp, Compilation compilation, Target target);
}

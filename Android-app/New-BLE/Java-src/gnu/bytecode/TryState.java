package gnu.bytecode;

public class TryState {
    Label end_label;
    Label end_try;
    Variable exception;
    ExitableBlock exitCases;
    Variable finally_ret_addr;
    Label finally_subr;
    TryState previous;
    Variable[] savedStack;
    Type[] savedTypes;
    Variable saved_result;
    Label start_try;
    boolean tryClauseDone;
    ClassType try_type;

    public TryState(CodeAttr code) {
        this.previous = code.try_stack;
        this.start_try = code.getLabel();
    }

    static TryState outerHandler(TryState innerTry, TryState outerTry) {
        while (innerTry != outerTry && (innerTry.finally_subr == null || innerTry.tryClauseDone)) {
            innerTry = innerTry.previous;
        }
        return innerTry;
    }
}

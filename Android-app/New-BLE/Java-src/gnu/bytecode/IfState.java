package gnu.bytecode;

public class IfState {
    boolean doing_else;
    Label end_label;
    IfState previous;
    int stack_growth;
    int start_stack_size;
    Type[] then_stacked_types;

    public IfState(CodeAttr code) {
        this(code, new Label(code));
    }

    public IfState(CodeAttr code, Label endLabel) {
        this.previous = code.if_stack;
        code.if_stack = this;
        this.end_label = endLabel;
        this.start_stack_size = code.SP;
    }
}

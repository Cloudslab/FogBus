package gnu.kawa.functions;

import gnu.expr.QuoteExp;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure0;

public class ConstantFunction0 extends Procedure0 {
    final QuoteExp constant;
    final Object value;

    public ConstantFunction0(String name, Object value) {
        this(name, QuoteExp.getInstance(value));
    }

    public ConstantFunction0(String name, QuoteExp constant) {
        super(name);
        this.value = constant.getValue();
        this.constant = constant;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyConstantFunction0");
    }

    public Object apply0() {
        return this.value;
    }
}

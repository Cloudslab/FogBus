package gnu.expr;

import gnu.mapping.InPort;
import gnu.mapping.Procedure1;

/* compiled from: Language */
class SimplePrompter extends Procedure1 {
    public String prefix = "[";
    public String suffix = "] ";

    SimplePrompter() {
    }

    public Object apply1(Object arg) {
        if (arg instanceof InPort) {
            int line = ((InPort) arg).getLineNumber() + 1;
            if (line >= 0) {
                return this.prefix + line + this.suffix;
            }
        }
        return this.suffix;
    }
}

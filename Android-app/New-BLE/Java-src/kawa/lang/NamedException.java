package kawa.lang;

import gnu.mapping.Procedure;
import gnu.mapping.Symbol;

public class NamedException extends RuntimeException {
    Object[] args;
    Symbol name;

    public NamedException(Symbol name, Object[] args) {
        this.name = name;
        this.args = args;
    }

    public void checkMatch(Object key) {
        if (key != this.name && key != Boolean.TRUE) {
            throw this;
        }
    }

    public Object applyHandler(Object key, Procedure handler) throws Throwable {
        checkMatch(key);
        return handler.applyN(this.args);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("#<ERROR");
        int i = 0;
        int len = this.args.length;
        if (len > 1 && this.args[0] == "misc-error") {
            i = 0 + 1;
        }
        while (i < len) {
            buf.append(' ');
            buf.append(this.args[i]);
            i++;
        }
        buf.append(">");
        return buf.toString();
    }
}

package kawa.lang;

import gnu.lists.Consumer;
import gnu.mapping.Future;
import gnu.mapping.Procedure;
import gnu.text.Printable;

public class Promise implements Printable {
    Object result;
    Procedure thunk;

    public Promise(Procedure thunk) {
        this.thunk = thunk;
    }

    public Object force() throws Throwable {
        if (this.result == null) {
            Object x = this.thunk.apply0();
            if (this.result == null) {
                this.result = x;
            }
        }
        return this.result;
    }

    public static Object force(Object arg) throws Throwable {
        if (arg instanceof Promise) {
            return ((Promise) arg).force();
        }
        if (arg instanceof Future) {
            return ((Future) arg).waitForResult();
        }
        if (arg instanceof java.util.concurrent.Future) {
            return ((java.util.concurrent.Future) arg).get();
        }
        return arg;
    }

    public void print(Consumer out) {
        if (this.result == null) {
            out.write("#<promise - not forced yet>");
            return;
        }
        out.write("#<promise - forced to a ");
        out.write(this.result.getClass().getName());
        out.write(62);
    }
}

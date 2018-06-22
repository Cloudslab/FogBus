package kawa.lang;

import gnu.lists.Consumer;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class AnyPat extends Pattern implements Printable, Externalizable {
    public static AnyPat make() {
        return new AnyPat();
    }

    public void print(Consumer out) {
        out.write("#<match any>");
    }

    public boolean match(Object obj, Object[] vars, int start_vars) {
        vars[start_vars] = obj;
        return true;
    }

    public int varCount() {
        return 1;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }
}

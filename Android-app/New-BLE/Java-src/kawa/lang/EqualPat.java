package kawa.lang;

import gnu.lists.Consumer;
import gnu.mapping.Symbol;
import gnu.text.Printable;
import gnu.text.ReportFormat;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class EqualPat extends Pattern implements Printable, Externalizable {
    Object value;

    public EqualPat(Object obj) {
        this.value = obj;
    }

    public static EqualPat make(Object obj) {
        return new EqualPat(obj);
    }

    public boolean match(Object obj, Object[] vars, int start_vars) {
        if ((this.value instanceof String) && (obj instanceof Symbol)) {
            obj = ((Symbol) obj).getName();
        }
        return this.value.equals(obj);
    }

    public int varCount() {
        return 0;
    }

    public void print(Consumer out) {
        out.write("#<equals: ");
        ReportFormat.print(this.value, out);
        out.write(62);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.value);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.value = in.readObject();
    }
}

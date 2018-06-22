package gnu.mapping;

import gnu.lists.Consumer;
import gnu.lists.TreeList;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.List;

public class Values extends TreeList implements Printable, Externalizable {
    public static final Values empty = new Values(noArgs);
    public static final Object[] noArgs = new Object[0];

    public Values(Object[] values) {
        for (Object writeObject : values) {
            writeObject(writeObject);
        }
    }

    public Object[] getValues() {
        return isEmpty() ? noArgs : toArray();
    }

    public static Object values(Object... vals) {
        return make(vals);
    }

    public static Values make() {
        return new Values();
    }

    public static Object make(Object[] vals) {
        if (vals.length == 1) {
            return vals[0];
        }
        if (vals.length == 0) {
            return empty;
        }
        return new Values(vals);
    }

    public static Object make(List seq) {
        int count = seq == null ? 0 : seq.size();
        if (count == 0) {
            return empty;
        }
        if (count == 1) {
            return seq.get(0);
        }
        Object vals = new Values();
        for (Object writeObject : seq) {
            vals.writeObject(writeObject);
        }
        return vals;
    }

    public static Object make(TreeList list) {
        return make(list, 0, list.data.length);
    }

    public static Object make(TreeList list, int startPosition, int endPosition) {
        if (startPosition != endPosition) {
            int next = list.nextDataIndex(startPosition);
            if (next > 0) {
                if (next == endPosition || list.nextDataIndex(next) < 0) {
                    return list.getPosNext(startPosition << 1);
                }
                Object vals = new Values();
                list.consumeIRange(startPosition, endPosition, vals);
                return vals;
            }
        }
        return empty;
    }

    public final Object canonicalize() {
        if (this.gapEnd != this.data.length) {
            return this;
        }
        if (this.gapStart == 0) {
            return empty;
        }
        if (nextDataIndex(0) == this.gapStart) {
            return getPosNext(0);
        }
        return this;
    }

    public Object call_with(Procedure proc) throws Throwable {
        return proc.applyN(toArray());
    }

    public void print(Consumer out) {
        if (this == empty) {
            out.write("#!void");
            return;
        }
        int size = toArray().length;
        if (1 != null) {
            out.write("#<values");
        }
        int i = 0;
        while (true) {
            int next = nextDataIndex(i);
            if (next < 0) {
                break;
            }
            out.write(32);
            if (i >= this.gapEnd) {
                i -= this.gapEnd - this.gapStart;
            }
            Object val = getPosNext(i << 1);
            if (val instanceof Printable) {
                ((Printable) val).print(out);
            } else {
                out.writeObject(val);
            }
            i = next;
        }
        if (1 != null) {
            out.write(62);
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(len);
        for (Object writeObject : toArray()) {
            out.writeObject(writeObject);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int len = in.readInt();
        for (int i = 0; i < len; i++) {
            writeObject(in.readObject());
        }
    }

    public Object readResolve() throws ObjectStreamException {
        return isEmpty() ? empty : this;
    }

    public static int nextIndex(Object values, int curIndex) {
        if (values instanceof Values) {
            return ((Values) values).nextDataIndex(curIndex);
        }
        return curIndex == 0 ? 1 : -1;
    }

    public static Object nextValue(Object values, int curIndex) {
        if (!(values instanceof Values)) {
            return values;
        }
        Values v = (Values) values;
        if (curIndex >= v.gapEnd) {
            curIndex -= v.gapEnd - v.gapStart;
        }
        return ((Values) values).getPosNext(curIndex << 1);
    }

    public static void writeValues(Object value, Consumer out) {
        if (value instanceof Values) {
            ((Values) value).consume(out);
        } else {
            out.writeObject(value);
        }
    }

    public static int countValues(Object value) {
        return value instanceof Values ? ((Values) value).size() : 1;
    }
}

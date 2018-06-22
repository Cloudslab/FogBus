package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class U64Vector extends SimpleVector implements Externalizable, Comparable {
    long[] data;

    public U64Vector() {
        this.data = S64Vector.empty;
    }

    public U64Vector(int size, long value) {
        long[] array = new long[size];
        this.data = array;
        this.size = size;
        while (true) {
            size--;
            if (size >= 0) {
                array[size] = value;
            } else {
                return;
            }
        }
    }

    public U64Vector(int size) {
        this.data = new long[size];
        this.size = size;
    }

    public U64Vector(long[] data) {
        this.data = data;
        this.size = data.length;
    }

    public U64Vector(Sequence seq) {
        this.data = new long[seq.size()];
        addAll(seq);
    }

    public int getBufferLength() {
        return this.data.length;
    }

    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            long[] tmp = new long[length];
            Object obj = this.data;
            if (oldLength >= length) {
                oldLength = length;
            }
            System.arraycopy(obj, 0, tmp, 0, oldLength);
            this.data = tmp;
        }
    }

    protected Object getBuffer() {
        return this.data;
    }

    public final int intAtBuffer(int index) {
        return (int) this.data[index];
    }

    public final long longAt(int index) {
        if (index <= this.size) {
            return this.data[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public final long longAtBuffer(int index) {
        return this.data[index];
    }

    public final Object get(int index) {
        if (index <= this.size) {
            return Convert.toObjectUnsigned(this.data[index]);
        }
        throw new IndexOutOfBoundsException();
    }

    public final Object getBuffer(int index) {
        return Convert.toObjectUnsigned(this.data[index]);
    }

    public Object setBuffer(int index, Object value) {
        long old = this.data[index];
        this.data[index] = Convert.toLongUnsigned(value);
        return Convert.toObjectUnsigned(old);
    }

    public final void setLongAt(int index, long value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setLongAtBuffer(int index, long value) {
        this.data[index] = value;
    }

    protected void clearBuffer(int start, int count) {
        int start2 = start;
        while (true) {
            count--;
            if (count >= 0) {
                start = start2 + 1;
                this.data[start2] = 0;
                start2 = start;
            } else {
                return;
            }
        }
    }

    public int getElementKind() {
        return 23;
    }

    public String getTag() {
        return "u64";
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeLong(this.data[index]);
        return true;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int i = iposStart >>> 1;
            int end = iposEnd >>> 1;
            if (end > this.size) {
                end = this.size;
            }
            while (i < end) {
                out.writeLong(this.data[i]);
                i++;
            }
        }
    }

    public int compareTo(Object obj) {
        int n;
        U64Vector vec2 = (U64Vector) obj;
        long[] arr1 = this.data;
        long[] arr2 = vec2.data;
        int n1 = this.size;
        int n2 = vec2.size;
        if (n1 > n2) {
            n = n2;
        } else {
            n = n1;
        }
        int i = 0;
        while (i < n) {
            long v1 = arr1[i];
            long v2 = arr2[i];
            if (v1 != v2) {
                return (Long.MIN_VALUE ^ v1) > (Long.MIN_VALUE ^ v2) ? 1 : -1;
            } else {
                i++;
            }
        }
        return n1 - n2;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeLong(this.data[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        long[] data = new long[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readLong();
        }
        this.data = data;
        this.size = size;
    }
}

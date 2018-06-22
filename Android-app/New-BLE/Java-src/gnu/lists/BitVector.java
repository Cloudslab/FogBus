package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class BitVector extends SimpleVector implements Externalizable {
    protected static boolean[] empty = new boolean[0];
    boolean[] data;

    public BitVector() {
        this.data = empty;
    }

    public BitVector(int size, boolean value) {
        boolean[] array = new boolean[size];
        this.data = array;
        this.size = size;
        if (value) {
            while (true) {
                size--;
                if (size >= 0) {
                    array[size] = true;
                } else {
                    return;
                }
            }
        }
    }

    public BitVector(int size) {
        this.data = new boolean[size];
        this.size = size;
    }

    public BitVector(boolean[] data) {
        this.data = data;
        this.size = data.length;
    }

    public BitVector(Sequence seq) {
        this.data = new boolean[seq.size()];
        addAll(seq);
    }

    public int getBufferLength() {
        return this.data.length;
    }

    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            boolean[] tmp = new boolean[length];
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

    public final boolean booleanAt(int index) {
        if (index <= this.size) {
            return this.data[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public final boolean booleanAtBuffer(int index) {
        return this.data[index];
    }

    public final Object get(int index) {
        if (index <= this.size) {
            return Convert.toObject(this.data[index]);
        }
        throw new IndexOutOfBoundsException();
    }

    public final Object getBuffer(int index) {
        return Convert.toObject(this.data[index]);
    }

    public Object setBuffer(int index, Object value) {
        boolean old = this.data[index];
        this.data[index] = Convert.toBoolean(value);
        return Convert.toObject(old);
    }

    public final void setBooleanAt(int index, boolean value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setBooleanAtBuffer(int index, boolean value) {
        this.data[index] = value;
    }

    protected void clearBuffer(int start, int count) {
        int start2 = start;
        while (true) {
            count--;
            if (count >= 0) {
                start = start2 + 1;
                this.data[start2] = false;
                start2 = start;
            } else {
                return;
            }
        }
    }

    public int getElementKind() {
        return 27;
    }

    public String getTag() {
        return "b";
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeBoolean(this.data[index]);
        return true;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int end = iposEnd >>> 1;
            for (int i = iposStart >>> 1; i < end; i++) {
                out.writeBoolean(this.data[i]);
            }
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeBoolean(this.data[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        boolean[] data = new boolean[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readBoolean();
        }
        this.data = data;
        this.size = size;
    }
}

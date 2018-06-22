package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class S16Vector extends SimpleVector implements Externalizable, Comparable {
    protected static short[] empty = new short[0];
    short[] data;

    public S16Vector() {
        this.data = empty;
    }

    public S16Vector(int size, short value) {
        short[] array = new short[size];
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

    public S16Vector(int size) {
        this.data = new short[size];
        this.size = size;
    }

    public S16Vector(short[] data) {
        this.data = data;
        this.size = data.length;
    }

    public S16Vector(Sequence seq) {
        this.data = new short[seq.size()];
        addAll(seq);
    }

    public int getBufferLength() {
        return this.data.length;
    }

    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            short[] tmp = new short[length];
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

    public final short shortAt(int index) {
        if (index <= this.size) {
            return this.data[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public final short shortAtBuffer(int index) {
        return this.data[index];
    }

    public final int intAtBuffer(int index) {
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
        short old = this.data[index];
        this.data[index] = Convert.toShort(value);
        return Convert.toObject(old);
    }

    public final void setShortAt(int index, short value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setShortAtBuffer(int index, short value) {
        this.data[index] = value;
    }

    protected void clearBuffer(int start, int count) {
        int start2 = start;
        while (true) {
            count--;
            if (count >= 0) {
                start = start2 + 1;
                this.data[start2] = (short) 0;
                start2 = start;
            } else {
                return;
            }
        }
    }

    public int getElementKind() {
        return 20;
    }

    public String getTag() {
        return "s16";
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeInt(this.data[index]);
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
                out.writeInt(this.data[i]);
                i++;
            }
        }
    }

    public int compareTo(Object obj) {
        return SimpleVector.compareToInt(this, (S16Vector) obj);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeShort(this.data[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        short[] data = new short[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readShort();
        }
        this.data = data;
        this.size = size;
    }
}

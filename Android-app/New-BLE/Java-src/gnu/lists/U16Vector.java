package gnu.lists;

import android.support.v4.internal.view.SupportMenu;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class U16Vector extends SimpleVector implements Externalizable, Comparable {
    short[] data;

    public U16Vector() {
        this.data = S16Vector.empty;
    }

    public U16Vector(int size, short value) {
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

    public U16Vector(int size) {
        this.data = new short[size];
        this.size = size;
    }

    public U16Vector(short[] data) {
        this.data = data;
        this.size = data.length;
    }

    public U16Vector(Sequence seq) {
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
        return this.data[index] & SupportMenu.USER_MASK;
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
        short old = this.data[index];
        this.data[index] = Convert.toShortUnsigned(value);
        return Convert.toObjectUnsigned(old);
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
        return 19;
    }

    public String getTag() {
        return "u16";
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeInt(this.data[index] & SupportMenu.USER_MASK);
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
                out.writeInt(this.data[i] & SupportMenu.USER_MASK);
                i++;
            }
        }
    }

    public int compareTo(Object obj) {
        return SimpleVector.compareToInt(this, (U16Vector) obj);
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

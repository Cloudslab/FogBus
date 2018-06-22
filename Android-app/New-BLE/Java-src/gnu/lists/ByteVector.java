package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public abstract class ByteVector extends SimpleVector implements Externalizable, Comparable {
    protected static byte[] empty = new byte[0];
    byte[] data;

    public int getBufferLength() {
        return this.data.length;
    }

    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            byte[] tmp = new byte[length];
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

    public final byte byteAt(int index) {
        if (index <= this.size) {
            return this.data[index];
        }
        throw new IndexOutOfBoundsException();
    }

    public final byte byteAtBuffer(int index) {
        return this.data[index];
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeInt(intAtBuffer(index));
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
                out.writeInt(intAtBuffer(i));
                i++;
            }
        }
    }

    public final void setByteAt(int index, byte value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setByteAtBuffer(int index, byte value) {
        this.data[index] = value;
    }

    protected void clearBuffer(int start, int count) {
        int start2 = start;
        while (true) {
            count--;
            if (count >= 0) {
                start = start2 + 1;
                this.data[start2] = (byte) 0;
                start2 = start;
            } else {
                return;
            }
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int size = this.size;
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeByte(this.data[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readByte();
        }
        this.data = data;
        this.size = size;
    }
}

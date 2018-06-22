package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class F32Vector extends SimpleVector implements Externalizable, Comparable {
    protected static float[] empty = new float[0];
    float[] data;

    public F32Vector() {
        this.data = empty;
    }

    public F32Vector(int size, float value) {
        float[] array = new float[size];
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

    public F32Vector(int size) {
        this.data = new float[size];
        this.size = size;
    }

    public F32Vector(float[] data) {
        this.data = data;
        this.size = data.length;
    }

    public F32Vector(Sequence seq) {
        this.data = new float[seq.size()];
        addAll(seq);
    }

    public int getBufferLength() {
        return this.data.length;
    }

    public void setBufferLength(int length) {
        int oldLength = this.data.length;
        if (oldLength != length) {
            float[] tmp = new float[length];
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

    public final float floatAt(int index) {
        if (index < this.size) {
            return this.data[index];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public final float floatAtBuffer(int index) {
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

    public final void setFloatAt(int index, float value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.data[index] = value;
    }

    public final void setFloatAtBuffer(int index, float value) {
        this.data[index] = value;
    }

    public final Object setBuffer(int index, Object value) {
        Object old = Convert.toObject(this.data[index]);
        this.data[index] = Convert.toFloat(value);
        return old;
    }

    protected void clearBuffer(int start, int count) {
        int start2 = start;
        while (true) {
            count--;
            if (count >= 0) {
                start = start2 + 1;
                this.data[start2] = 0.0f;
                start2 = start;
            } else {
                return;
            }
        }
    }

    public int getElementKind() {
        return 25;
    }

    public String getTag() {
        return "f32";
    }

    public boolean consumeNext(int ipos, Consumer out) {
        int index = ipos >>> 1;
        if (index >= this.size) {
            return false;
        }
        out.writeFloat(this.data[index]);
        return true;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int end = iposEnd >>> 1;
            for (int i = iposStart >>> 1; i < end; i++) {
                out.writeFloat(this.data[i]);
            }
        }
    }

    public int compareTo(Object obj) {
        int n;
        F32Vector vec2 = (F32Vector) obj;
        float[] arr1 = this.data;
        float[] arr2 = vec2.data;
        int n1 = this.size;
        int n2 = vec2.size;
        if (n1 > n2) {
            n = n2;
        } else {
            n = n1;
        }
        int i = 0;
        while (i < n) {
            float v1 = arr1[i];
            float v2 = arr2[i];
            if (v1 != v2) {
                return v1 > v2 ? 1 : -1;
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
            out.writeFloat(this.data[i]);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int size = in.readInt();
        float[] data = new float[size];
        for (int i = 0; i < size; i++) {
            data[i] = in.readFloat();
        }
        this.data = data;
        this.size = size;
    }
}

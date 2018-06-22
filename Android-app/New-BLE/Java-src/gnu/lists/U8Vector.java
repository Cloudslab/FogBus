package gnu.lists;

public class U8Vector extends ByteVector {
    public U8Vector() {
        this.data = ByteVector.empty;
    }

    public U8Vector(int size, byte value) {
        byte[] array = new byte[size];
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

    public U8Vector(int size) {
        this.data = new byte[size];
        this.size = size;
    }

    public U8Vector(byte[] data) {
        this.data = data;
        this.size = data.length;
    }

    public U8Vector(Sequence seq) {
        this.data = new byte[seq.size()];
        addAll(seq);
    }

    public final int intAtBuffer(int index) {
        return this.data[index] & 255;
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
        byte old = this.data[index];
        this.data[index] = Convert.toByteUnsigned(value);
        return Convert.toObjectUnsigned(old);
    }

    public int getElementKind() {
        return 17;
    }

    public String getTag() {
        return "u8";
    }

    public int compareTo(Object obj) {
        return SimpleVector.compareToInt(this, (U8Vector) obj);
    }
}

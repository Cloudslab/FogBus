package gnu.kawa.xml;

public abstract class BinaryObject {
    byte[] data;

    public byte[] getBytes() {
        return this.data;
    }
}

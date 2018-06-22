package gnu.mapping;

import gnu.lists.Consumer;

public class CharArrayOutPort extends OutPort {
    public CharArrayOutPort() {
        super(null, false, CharArrayInPort.stringPath);
    }

    public int length() {
        return this.bout.bufferFillPointer;
    }

    public void setLength(int length) {
        this.bout.bufferFillPointer = length;
    }

    public void reset() {
        this.bout.bufferFillPointer = 0;
    }

    public char[] toCharArray() {
        int length = this.bout.bufferFillPointer;
        char[] result = new char[length];
        System.arraycopy(this.bout.buffer, 0, result, 0, length);
        return result;
    }

    public void close() {
    }

    protected boolean closeOnExit() {
        return false;
    }

    public String toString() {
        return new String(this.bout.buffer, 0, this.bout.bufferFillPointer);
    }

    public String toSubString(int beginIndex, int endIndex) {
        if (endIndex <= this.bout.bufferFillPointer) {
            return new String(this.bout.buffer, beginIndex, endIndex - beginIndex);
        }
        throw new IndexOutOfBoundsException();
    }

    public String toSubString(int beginIndex) {
        return new String(this.bout.buffer, beginIndex, this.bout.bufferFillPointer - beginIndex);
    }

    public void writeTo(Consumer out) {
        out.write(this.bout.buffer, 0, this.bout.bufferFillPointer);
    }

    public void writeTo(int start, int count, Consumer out) {
        out.write(this.bout.buffer, start, count);
    }
}

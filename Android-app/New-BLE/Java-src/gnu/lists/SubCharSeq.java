package gnu.lists;

import java.io.IOException;
import java.util.List;

public class SubCharSeq extends SubSequence implements CharSeq {
    public SubCharSeq(AbstractSequence base, int startPos, int endPos) {
        super(base, startPos, endPos);
    }

    public int length() {
        return size();
    }

    public char charAt(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return ((CharSeq) this.base).charAt(this.base.nextIndex(this.ipos0) + index);
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        int i = srcBegin;
        int dstBegin2 = dstBegin;
        while (i < srcEnd) {
            dstBegin = dstBegin2 + 1;
            dst[dstBegin2] = charAt(i);
            i++;
            dstBegin2 = dstBegin;
        }
    }

    public void setCharAt(int index, char ch) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ((CharSeq) this.base).setCharAt(this.base.nextIndex(this.ipos0) + index, ch);
    }

    public void fill(char value) {
        ((CharSeq) this.base).fill(this.base.nextIndex(this.ipos0), this.base.nextIndex(this.ipos0), value);
    }

    public void fill(int fromIndex, int toIndex, char value) {
        int index0 = this.base.nextIndex(this.ipos0);
        int index1 = this.base.nextIndex(this.ipos0);
        if (fromIndex < 0 || toIndex < fromIndex || index0 + toIndex > index1) {
            throw new IndexOutOfBoundsException();
        }
        ((CharSeq) this.base).fill(index0 + fromIndex, index0 + toIndex, value);
    }

    public void writeTo(int start, int count, Appendable dest) throws IOException {
        int index0 = this.base.nextIndex(this.ipos0);
        int index1 = this.base.nextIndex(this.ipos0);
        if (start < 0 || count < 0 || (index0 + start) + count > index1) {
            throw new IndexOutOfBoundsException();
        }
        ((CharSeq) this.base).writeTo(index0 + start, count, dest);
    }

    public void writeTo(Appendable dest) throws IOException {
        ((CharSeq) this.base).writeTo(this.base.nextIndex(this.ipos0), size(), dest);
    }

    public void consume(int start, int count, Consumer out) {
        int index0 = this.base.nextIndex(this.ipos0);
        int index1 = this.base.nextIndex(this.ipos0);
        if (start < 0 || count < 0 || (index0 + start) + count > index1) {
            throw new IndexOutOfBoundsException();
        }
        ((CharSeq) this.base).consume(index0 + start, count, out);
    }

    public String toString() {
        int sz = size();
        StringBuffer sbuf = new StringBuffer(sz);
        for (int i = 0; i < sz; i++) {
            sbuf.append(charAt(i));
        }
        return sbuf.toString();
    }

    private SubCharSeq subCharSeq(int start, int end) {
        int sz = size();
        if (start >= 0 && end >= start && end <= sz) {
            return new SubCharSeq(this.base, this.base.createRelativePos(this.ipos0, start, false), this.base.createRelativePos(this.ipos0, end, true));
        }
        throw new IndexOutOfBoundsException();
    }

    public List subList(int fromIx, int toIx) {
        return subCharSeq(fromIx, toIx);
    }

    public CharSequence subSequence(int start, int end) {
        return subCharSeq(start, end);
    }
}

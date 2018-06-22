package gnu.lists;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

public class CharBuffer extends StableVector implements CharSeq, Serializable {
    private FString string;

    public CharBuffer(FString str) {
        super(str);
        this.string = str;
    }

    public CharBuffer(int initialSize) {
        this(new FString(initialSize));
    }

    protected CharBuffer() {
    }

    public int length() {
        return size();
    }

    public char charAt(int index) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return this.string.charAt(index);
    }

    public int indexOf(int ch, int fromChar) {
        char c1;
        char c2;
        if (ch >= 65536) {
            c1 = (char) (((ch - 65536) >> 10) + 55296);
            c2 = (char) ((ch & 1023) + 56320);
        } else {
            c1 = (char) ch;
            c2 = '\u0000';
        }
        char[] arr = getArray();
        int i = fromChar;
        int limit = this.gapStart;
        if (i >= limit) {
            i += this.gapEnd - this.gapStart;
            limit = arr.length;
        }
        while (true) {
            if (i == limit) {
                limit = arr.length;
                if (i >= limit) {
                    return -1;
                }
                i = this.gapEnd;
            }
            if (arr[i] == c1) {
                if (c2 != '\u0000') {
                    if (i + 1 >= limit) {
                        if (this.gapEnd < arr.length && arr[this.gapEnd] == c2) {
                            break;
                        }
                    } else if (arr[i + 1] == c2) {
                        break;
                    }
                } else {
                    break;
                }
            }
            i++;
        }
        return i > this.gapStart ? i - (this.gapEnd - this.gapStart) : i;
    }

    public int lastIndexOf(int ch, int fromChar) {
        char c1;
        char c2;
        if (ch >= 65536) {
            c1 = (char) (((ch - 65536) >> 10) + 55296);
            c2 = (char) ((ch & 1023) + 56320);
        } else {
            c1 = '\u0000';
            c2 = (char) ch;
        }
        int i = fromChar;
        while (true) {
            i--;
            if (i < 0) {
                return -1;
            }
            if (charAt(i) == c2) {
                if (c1 == '\u0000') {
                    return i;
                }
                if (i > 0 && charAt(i - 1) == c1) {
                    return i - 1;
                }
            }
        }
    }

    public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
        int count;
        char[] array = this.string.data;
        if (srcBegin < this.gapStart) {
            count = (srcEnd < this.gapStart ? srcEnd : this.gapStart) - srcBegin;
            if (count > 0) {
                System.arraycopy(array, srcBegin, dst, dstBegin, count);
                srcBegin += count;
                dstBegin += count;
            }
        }
        int gapSize = this.gapEnd - this.gapStart;
        srcBegin += gapSize;
        count = (srcEnd + gapSize) - srcBegin;
        if (count > 0) {
            System.arraycopy(array, srcBegin, dst, dstBegin, count);
        }
    }

    public void setCharAt(int index, char value) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        this.string.setCharAt(index, value);
    }

    public String substring(int start, int end) {
        int sz = size();
        if (start < 0 || end < start || end > sz) {
            throw new IndexOutOfBoundsException();
        }
        int len = end - start;
        return new String(getArray(), getSegment(start, len), len);
    }

    public CharSequence subSequence(int start, int end) {
        int sz = size();
        if (start >= 0 && end >= start && end <= sz) {
            return new SubCharSeq(this, this.base.createPos(start, false), this.base.createPos(end, true));
        }
        throw new IndexOutOfBoundsException();
    }

    public void fill(int fromIndex, int toIndex, char value) {
        int limit;
        char[] array = this.string.data;
        int i = fromIndex;
        if (this.gapStart < toIndex) {
            limit = this.gapStart;
        } else {
            limit = toIndex;
        }
        while (i < limit) {
            array[i] = value;
            i++;
        }
        limit += toIndex;
        for (i = limit + (this.gapEnd - this.gapStart); i < limit; i++) {
            array[i] = value;
        }
    }

    public final void fill(char value) {
        char[] array = this.string.data;
        int i = array.length;
        while (true) {
            i--;
            if (i < this.gapEnd) {
                break;
            }
            array[i] = value;
        }
        i = this.gapStart;
        while (true) {
            i--;
            if (i >= 0) {
                array[i] = value;
            } else {
                return;
            }
        }
    }

    public char[] getArray() {
        return (char[]) this.base.getBuffer();
    }

    public void delete(int where, int count) {
        int ipos = createPos(where, false);
        removePos(ipos, count);
        releasePos(ipos);
    }

    public void insert(int where, String str, boolean beforeMarkers) {
        int len = str.length();
        gapReserve(where, len);
        str.getChars(0, len, this.string.data, where);
        this.gapStart += len;
    }

    public void consume(int start, int count, Consumer dest) {
        char[] array = this.string.data;
        if (start < this.gapStart) {
            int count0 = this.gapStart - start;
            if (count0 > count) {
                count0 = count;
            }
            dest.write(array, start, count0);
            count -= count0;
            start += count;
        }
        if (count > 0) {
            dest.write(array, start + (this.gapEnd - this.gapStart), count);
        }
    }

    public String toString() {
        int len = size();
        return new String(getArray(), getSegment(0, len), len);
    }

    public void writeTo(int start, int count, Appendable dest) throws IOException {
        if (dest instanceof Writer) {
            writeTo(start, count, (Writer) dest);
        } else {
            dest.append(this, start, start + count);
        }
    }

    public void writeTo(Appendable dest) throws IOException {
        writeTo(0, size(), dest);
    }

    public void writeTo(int start, int count, Writer dest) throws IOException {
        char[] array = this.string.data;
        if (start < this.gapStart) {
            int count0 = this.gapStart - start;
            if (count0 > count) {
                count0 = count;
            }
            dest.write(array, start, count0);
            count -= count0;
            start += count;
        }
        if (count > 0) {
            dest.write(array, start + (this.gapEnd - this.gapStart), count);
        }
    }

    public void writeTo(Writer dest) throws IOException {
        char[] array = this.string.data;
        dest.write(array, 0, this.gapStart);
        dest.write(array, this.gapEnd, array.length - this.gapEnd);
    }

    public void dump() {
        int i;
        int poslen = 0;
        System.err.println("Buffer Content dump.  size:" + size() + "  buffer:" + getArray().length);
        System.err.print("before gap: \"");
        System.err.print(new String(getArray(), 0, this.gapStart));
        System.err.println("\" (gapStart:" + this.gapStart + " gapEnd:" + this.gapEnd + ')');
        System.err.print("after gap: \"");
        System.err.print(new String(getArray(), this.gapEnd, getArray().length - this.gapEnd));
        System.err.println("\"");
        if (this.positions != null) {
            poslen = this.positions.length;
        }
        System.err.println("Positions (size: " + poslen + " free:" + this.free + "):");
        boolean[] isFree = null;
        if (this.free != -2) {
            isFree = new boolean[this.positions.length];
            i = this.free;
            while (i >= 0) {
                isFree[i] = true;
                i = this.positions[i];
            }
        }
        for (i = 0; i < poslen; i++) {
            int pos = this.positions[i];
            if (this.free == -2) {
                if (pos == -2) {
                }
            } else if (isFree[i]) {
            }
            System.err.println("position#" + i + ": " + (pos >> 1) + " isAfter:" + (pos & 1));
        }
    }
}

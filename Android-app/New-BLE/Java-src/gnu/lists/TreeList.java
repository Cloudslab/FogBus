package gnu.lists;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.MotionEventCompat;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.text.Char;
import java.io.PrintWriter;

public class TreeList extends AbstractSequence implements Appendable, XConsumer, PositionConsumer, Consumable {
    protected static final int BEGIN_ATTRIBUTE_LONG = 61705;
    public static final int BEGIN_ATTRIBUTE_LONG_SIZE = 5;
    protected static final int BEGIN_DOCUMENT = 61712;
    protected static final int BEGIN_ELEMENT_LONG = 61704;
    protected static final int BEGIN_ELEMENT_SHORT = 40960;
    protected static final int BEGIN_ELEMENT_SHORT_INDEX_MAX = 4095;
    public static final int BEGIN_ENTITY = 61714;
    public static final int BEGIN_ENTITY_SIZE = 5;
    static final char BOOL_FALSE = '';
    static final char BOOL_TRUE = '';
    static final int BYTE_PREFIX = 61440;
    static final int CDATA_SECTION = 61717;
    static final int CHAR_FOLLOWS = 61702;
    static final int COMMENT = 61719;
    protected static final int DOCUMENT_URI = 61720;
    static final int DOUBLE_FOLLOWS = 61701;
    static final int END_ATTRIBUTE = 61706;
    public static final int END_ATTRIBUTE_SIZE = 1;
    protected static final int END_DOCUMENT = 61713;
    protected static final int END_ELEMENT_LONG = 61708;
    protected static final int END_ELEMENT_SHORT = 61707;
    protected static final int END_ENTITY = 61715;
    static final int FLOAT_FOLLOWS = 61700;
    public static final int INT_FOLLOWS = 61698;
    static final int INT_SHORT_ZERO = 49152;
    static final int JOINER = 61718;
    static final int LONG_FOLLOWS = 61699;
    public static final int MAX_CHAR_SHORT = 40959;
    static final int MAX_INT_SHORT = 8191;
    static final int MIN_INT_SHORT = -4096;
    static final char OBJECT_REF_FOLLOWS = '';
    static final int OBJECT_REF_SHORT = 57344;
    static final int OBJECT_REF_SHORT_INDEX_MAX = 4095;
    protected static final char POSITION_PAIR_FOLLOWS = '';
    static final char POSITION_REF_FOLLOWS = '';
    protected static final int PROCESSING_INSTRUCTION = 61716;
    public int attrStart;
    int currentParent;
    public char[] data;
    public int docStart;
    public int gapEnd;
    public int gapStart;
    public Object[] objects;
    public int oindex;

    public TreeList() {
        this.currentParent = -1;
        resizeObjects();
        this.gapEnd = HttpRequestContext.HTTP_OK;
        this.data = new char[this.gapEnd];
    }

    public TreeList(TreeList list, int startPosition, int endPosition) {
        this();
        list.consumeIRange(startPosition, endPosition, this);
    }

    public TreeList(TreeList list) {
        this(list, 0, list.data.length);
    }

    public void clear() {
        this.gapStart = 0;
        this.gapEnd = this.data.length;
        this.attrStart = 0;
        if (this.gapEnd > 1500) {
            this.gapEnd = HttpRequestContext.HTTP_OK;
            this.data = new char[this.gapEnd];
        }
        this.objects = null;
        this.oindex = 0;
        resizeObjects();
    }

    public void ensureSpace(int needed) {
        int avail = this.gapEnd - this.gapStart;
        if (needed > avail) {
            int oldSize = this.data.length;
            int neededSize = (oldSize - avail) + needed;
            int newSize = oldSize * 2;
            if (newSize < neededSize) {
                newSize = neededSize;
            }
            char[] tmp = new char[newSize];
            if (this.gapStart > 0) {
                System.arraycopy(this.data, 0, tmp, 0, this.gapStart);
            }
            int afterGap = oldSize - this.gapEnd;
            if (afterGap > 0) {
                System.arraycopy(this.data, this.gapEnd, tmp, newSize - afterGap, afterGap);
            }
            this.gapEnd = newSize - afterGap;
            this.data = tmp;
        }
    }

    public final void resizeObjects() {
        Object[] tmp;
        if (this.objects == null) {
            tmp = new Object[100];
        } else {
            int oldLength = this.objects.length;
            tmp = new Object[(oldLength * 2)];
            System.arraycopy(this.objects, 0, tmp, 0, oldLength);
        }
        this.objects = tmp;
    }

    public int find(Object arg1) {
        if (this.oindex == this.objects.length) {
            resizeObjects();
        }
        this.objects[this.oindex] = arg1;
        int i = this.oindex;
        this.oindex = i + 1;
        return i;
    }

    protected final int getIntN(int index) {
        return (this.data[index] << 16) | (this.data[index + 1] & SupportMenu.USER_MASK);
    }

    protected final long getLongN(int index) {
        char[] data = this.data;
        return ((((((long) data[index]) & 65535) << 48) | ((((long) data[index + 1]) & 65535) << 32)) | ((((long) data[index + 2]) & 65535) << 16)) | (((long) data[index + 3]) & 65535);
    }

    public final void setIntN(int index, int i) {
        this.data[index] = (char) (i >> 16);
        this.data[index + 1] = (char) i;
    }

    public void consume(SeqPosition position) {
        ensureSpace(3);
        int index = find(position.copy());
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = POSITION_REF_FOLLOWS;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        ensureSpace(5);
        this.data[this.gapStart] = POSITION_PAIR_FOLLOWS;
        setIntN(this.gapStart + 1, find(seq));
        setIntN(this.gapStart + 3, ipos);
        this.gapStart += 5;
    }

    public void writeObject(Object v) {
        ensureSpace(3);
        int index = find(v);
        if (index < 4096) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) (OBJECT_REF_SHORT | index);
            return;
        }
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = OBJECT_REF_FOLLOWS;
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writeDocumentUri(Object uri) {
        ensureSpace(3);
        int index = find(uri);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = '';
        setIntN(this.gapStart, index);
        this.gapStart += 2;
    }

    public void writeComment(char[] chars, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = '';
        setIntN(i2, length);
        i = i2 + 2;
        System.arraycopy(chars, offset, this.data, i, length);
        this.gapStart = i + length;
    }

    public void writeComment(String comment, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = '';
        setIntN(i2, length);
        i = i2 + 2;
        comment.getChars(offset, offset + length, this.data, i);
        this.gapStart = i + length;
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        ensureSpace(length + 5);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = '';
        setIntN(i2, find(target));
        setIntN(i2 + 2, length);
        i = i2 + 4;
        System.arraycopy(content, offset, this.data, i, length);
        this.gapStart = i + length;
    }

    public void writeProcessingInstruction(String target, String content, int offset, int length) {
        ensureSpace(length + 5);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = '';
        setIntN(i2, find(target));
        setIntN(i2 + 2, length);
        i = i2 + 4;
        content.getChars(offset, offset + length, this.data, i);
        this.gapStart = i + length;
    }

    public void startElement(Object type) {
        startElement(find(type));
    }

    public void startDocument() {
        int i = -1;
        ensureSpace(6);
        this.gapEnd--;
        int p = this.gapStart;
        this.data[p] = '';
        if (this.docStart != 0) {
            throw new Error("nested document");
        }
        this.docStart = p + 1;
        setIntN(p + 1, this.gapEnd - this.data.length);
        int i2 = p + 3;
        if (this.currentParent != -1) {
            i = this.currentParent - p;
        }
        setIntN(i2, i);
        this.currentParent = p;
        this.gapStart = p + 5;
        this.currentParent = p;
        this.data[this.gapEnd] = '';
    }

    public void endDocument() {
        if (this.data[this.gapEnd] == '' && this.docStart > 0 && this.data[this.currentParent] == '') {
            this.gapEnd++;
            setIntN(this.docStart, (this.gapStart - this.docStart) + 1);
            this.docStart = 0;
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = '';
            int parent = getIntN(this.currentParent + 3);
            if (parent < -1) {
                parent += this.currentParent;
            }
            this.currentParent = parent;
            return;
        }
        throw new Error("unexpected endDocument");
    }

    public void beginEntity(Object base) {
        int i = -1;
        if (this.gapStart == 0) {
            ensureSpace(6);
            this.gapEnd--;
            int p = this.gapStart;
            this.data[p] = '';
            setIntN(p + 1, find(base));
            int i2 = p + 3;
            if (this.currentParent != -1) {
                i = this.currentParent - p;
            }
            setIntN(i2, i);
            this.gapStart = p + 5;
            this.currentParent = p;
            this.data[this.gapEnd] = '';
        }
    }

    public void endEntity() {
        if (this.gapEnd + 1 != this.data.length || this.data[this.gapEnd] != '') {
            return;
        }
        if (this.data[this.currentParent] != '') {
            throw new Error("unexpected endEntity");
        }
        this.gapEnd++;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = '';
        int parent = getIntN(this.currentParent + 3);
        if (parent < -1) {
            parent += this.currentParent;
        }
        this.currentParent = parent;
    }

    public void startElement(int index) {
        ensureSpace(10);
        this.gapEnd -= 7;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = '';
        setIntN(this.gapStart, this.gapEnd - this.data.length);
        this.gapStart += 2;
        this.data[this.gapEnd] = '';
        setIntN(this.gapEnd + 1, index);
        setIntN(this.gapEnd + 3, this.gapStart - 3);
        setIntN(this.gapEnd + 5, this.currentParent);
        this.currentParent = this.gapStart - 3;
    }

    public void setElementName(int elementIndex, int nameIndex) {
        if (this.data[elementIndex] == '') {
            int j = getIntN(elementIndex + 1);
            if (j < 0) {
                elementIndex = this.data.length;
            }
            elementIndex += j;
        }
        if (elementIndex < this.gapEnd) {
            throw new Error("setElementName before gapEnd");
        }
        setIntN(elementIndex + 1, nameIndex);
    }

    public void endElement() {
        if (this.data[this.gapEnd] != '') {
            throw new Error("unexpected endElement");
        }
        int index = getIntN(this.gapEnd + 1);
        int begin = getIntN(this.gapEnd + 3);
        int parent = getIntN(this.gapEnd + 5);
        this.currentParent = parent;
        this.gapEnd += 7;
        int offset = this.gapStart - begin;
        int parentOffset = begin - parent;
        if (index >= 4095 || offset >= 65536 || parentOffset >= 65536) {
            this.data[begin] = '';
            setIntN(begin + 1, offset);
            this.data[this.gapStart] = '';
            setIntN(this.gapStart + 1, index);
            setIntN(this.gapStart + 3, -offset);
            if (parent >= this.gapStart || begin <= this.gapStart) {
                parent -= this.gapStart;
            }
            setIntN(this.gapStart + 5, parent);
            this.gapStart += 7;
            return;
        }
        this.data[begin] = (char) (BEGIN_ELEMENT_SHORT | index);
        this.data[begin + 1] = (char) offset;
        this.data[begin + 2] = (char) parentOffset;
        this.data[this.gapStart] = '';
        this.data[this.gapStart + 1] = (char) offset;
        this.gapStart += 2;
    }

    public void startAttribute(Object attrType) {
        startAttribute(find(attrType));
    }

    public void startAttribute(int index) {
        ensureSpace(6);
        this.gapEnd--;
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = '';
        if (this.attrStart != 0) {
            throw new Error("nested attribute");
        }
        this.attrStart = this.gapStart;
        setIntN(this.gapStart, index);
        setIntN(this.gapStart + 2, this.gapEnd - this.data.length);
        this.gapStart += 4;
        this.data[this.gapEnd] = '';
    }

    public void setAttributeName(int attrIndex, int nameIndex) {
        setIntN(attrIndex + 1, nameIndex);
    }

    public void endAttribute() {
        if (this.attrStart > 0) {
            if (this.data[this.gapEnd] != '') {
                throw new Error("unexpected endAttribute");
            }
            this.gapEnd++;
            setIntN(this.attrStart + 2, (this.gapStart - this.attrStart) + 1);
            this.attrStart = 0;
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = '';
        }
    }

    public Consumer append(char c) {
        write((int) c);
        return this;
    }

    public void write(int c) {
        ensureSpace(3);
        char[] cArr;
        int i;
        if (c <= MAX_CHAR_SHORT) {
            cArr = this.data;
            i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) c;
        } else if (c < 65536) {
            cArr = this.data;
            i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = '';
            cArr = this.data;
            i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = (char) c;
        } else {
            Char.print(c, this);
        }
    }

    public void writeBoolean(boolean v) {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = v ? BOOL_TRUE : BOOL_FALSE;
    }

    public void writeByte(int v) {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) (BYTE_PREFIX + (v & 255));
    }

    public void writeInt(int v) {
        ensureSpace(3);
        if (v < MIN_INT_SHORT || v > MAX_INT_SHORT) {
            char[] cArr = this.data;
            int i = this.gapStart;
            this.gapStart = i + 1;
            cArr[i] = '';
            setIntN(this.gapStart, v);
            this.gapStart += 2;
            return;
        }
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) (INT_SHORT_ZERO + v);
    }

    public void writeLong(long v) {
        ensureSpace(5);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = '';
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((int) (v >>> 48));
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((int) (v >>> 32));
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((int) (v >>> 16));
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((int) v);
    }

    public void writeFloat(float v) {
        ensureSpace(3);
        int i = Float.floatToIntBits(v);
        char[] cArr = this.data;
        int i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr[i2] = '';
        cArr = this.data;
        i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr[i2] = (char) (i >>> 16);
        cArr = this.data;
        i2 = this.gapStart;
        this.gapStart = i2 + 1;
        cArr[i2] = (char) i;
    }

    public void writeDouble(double v) {
        ensureSpace(5);
        long l = Double.doubleToLongBits(v);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = '';
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((int) (l >>> 48));
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((int) (l >>> 32));
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((int) (l >>> 16));
        cArr = this.data;
        i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = (char) ((int) l);
    }

    public boolean ignoring() {
        return false;
    }

    public void writeJoiner() {
        ensureSpace(1);
        char[] cArr = this.data;
        int i = this.gapStart;
        this.gapStart = i + 1;
        cArr[i] = '';
    }

    public void write(char[] buf, int off, int len) {
        if (len == 0) {
            writeJoiner();
        }
        ensureSpace(len);
        int off2 = off;
        while (len > 0) {
            off = off2 + 1;
            int ch = buf[off2];
            len--;
            if (ch <= '鿿') {
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = ch;
            } else {
                write(ch);
                ensureSpace(len);
            }
            off2 = off;
        }
    }

    public void write(String str) {
        write((CharSequence) str, 0, str.length());
    }

    public void write(CharSequence str, int start, int length) {
        if (length == 0) {
            writeJoiner();
        }
        ensureSpace(length);
        int start2 = start;
        while (length > 0) {
            start = start2 + 1;
            int ch = str.charAt(start2);
            length--;
            if (ch <= '鿿') {
                char[] cArr = this.data;
                int i = this.gapStart;
                this.gapStart = i + 1;
                cArr[i] = ch;
            } else {
                write(ch);
                ensureSpace(length);
            }
            start2 = start;
        }
    }

    public void writeCDATA(char[] chars, int offset, int length) {
        ensureSpace(length + 3);
        int i = this.gapStart;
        int i2 = i + 1;
        this.data[i] = '';
        setIntN(i2, length);
        i = i2 + 2;
        System.arraycopy(chars, offset, this.data, i, length);
        this.gapStart = i + length;
    }

    public Consumer append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        return append(csq, 0, csq.length());
    }

    public Consumer append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        for (int i = start; i < end; i++) {
            append(csq.charAt(i));
        }
        return this;
    }

    public boolean isEmpty() {
        int pos;
        if (this.gapStart == 0) {
            pos = this.gapEnd;
        } else {
            pos = 0;
        }
        if (pos == this.data.length) {
            return true;
        }
        return false;
    }

    public int size() {
        int size = 0;
        int i = 0;
        while (true) {
            i = nextPos(i);
            if (i == 0) {
                return size;
            }
            size++;
        }
    }

    public int createPos(int index, boolean isAfter) {
        return createRelativePos(0, index, isAfter);
    }

    public final int posToDataIndex(int ipos) {
        if (ipos == -1) {
            return this.data.length;
        }
        int index = ipos >>> 1;
        if ((ipos & 1) != 0) {
            index--;
        }
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if ((ipos & 1) == 0) {
            return index;
        }
        index = nextDataIndex(index);
        if (index < 0) {
            return this.data.length;
        }
        if (index == this.gapStart) {
            return index + (this.gapEnd - this.gapStart);
        }
        return index;
    }

    public int firstChildPos(int ipos) {
        int index = gotoChildrenStart(posToDataIndex(ipos));
        if (index < 0) {
            return 0;
        }
        return index << 1;
    }

    public final int gotoChildrenStart(int index) {
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if ((datum >= 'ꀀ' && datum <= '꿿') || datum == '') {
            index += 3;
        } else if (datum != '' && datum != '') {
            return -1;
        } else {
            index += 5;
        }
        while (true) {
            if (index >= this.gapStart) {
                index += this.gapEnd - this.gapStart;
            }
            datum = this.data[index];
            if (datum == '') {
                int end = getIntN(index + 3);
                if (end < 0) {
                    index = this.data.length;
                }
                index += end;
            } else if (datum == '' || datum == '') {
                index++;
            } else if (datum != '') {
                return index;
            } else {
                index += 3;
            }
        }
    }

    public int parentPos(int ipos) {
        int index = posToDataIndex(ipos);
        do {
            index = parentOrEntityI(index);
            if (index == -1) {
                return -1;
            }
        } while (this.data[index] == '');
        return index << 1;
    }

    public int parentOrEntityPos(int ipos) {
        int index = parentOrEntityI(posToDataIndex(ipos));
        return index < 0 ? -1 : index << 1;
    }

    public int parentOrEntityI(int index) {
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        int parent_offset;
        if (datum == '' || datum == '') {
            parent_offset = getIntN(index + 3);
            if (parent_offset >= -1) {
                return parent_offset;
            }
            return index + parent_offset;
        } else if (datum >= 'ꀀ' && datum <= '꿿') {
            parent_offset = this.data[index + 2];
            if (parent_offset != 0) {
                return index - parent_offset;
            }
            return -1;
        } else if (datum == '') {
            int length;
            int end_offset = getIntN(index + 1);
            if (end_offset < 0) {
                length = this.data.length;
            } else {
                length = index;
            }
            end_offset += length;
            parent_offset = getIntN(end_offset + 5);
            if (parent_offset == 0) {
                return -1;
            }
            if (parent_offset < 0) {
                parent_offset += end_offset;
            }
            return parent_offset;
        } else {
            while (true) {
                if (index == this.gapStart) {
                    index = this.gapEnd;
                }
                if (index != this.data.length) {
                    switch (this.data[index]) {
                        case END_ATTRIBUTE /*61706*/:
                            index++;
                            break;
                        case END_ELEMENT_SHORT /*61707*/:
                            return index - this.data[index + 1];
                        case END_ELEMENT_LONG /*61708*/:
                            int begin_offset = getIntN(index + 3);
                            if (begin_offset < 0) {
                                begin_offset += index;
                            }
                            return begin_offset;
                        case END_DOCUMENT /*61713*/:
                            return -1;
                        default:
                            index = nextDataIndex(index);
                            if (index >= 0) {
                                break;
                            }
                            return -1;
                    }
                }
                return -1;
            }
        }
    }

    public int getAttributeCount(int parent) {
        int n = 0;
        int attr = firstAttributePos(parent);
        while (attr != 0 && getNextKind(attr) == 35) {
            n++;
            attr = nextPos(attr);
        }
        return n;
    }

    public boolean gotoAttributesStart(TreePosition pos) {
        int index = gotoAttributesStart(pos.ipos >> 1);
        if (index < 0) {
            return false;
        }
        pos.push(this, index << 1);
        return true;
    }

    public int firstAttributePos(int ipos) {
        int index = gotoAttributesStart(posToDataIndex(ipos));
        return index < 0 ? 0 : index << 1;
    }

    public int gotoAttributesStart(int index) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        if (index == this.data.length) {
            return -1;
        }
        char datum = this.data[index];
        if ((datum < 'ꀀ' || datum > '꿿') && datum != '') {
            return -1;
        }
        return index + 3;
    }

    public Object get(int index) {
        int i = 0;
        do {
            index--;
            if (index < 0) {
                return getPosNext(i);
            }
            i = nextPos(i);
        } while (i != 0);
        throw new IndexOutOfBoundsException();
    }

    public boolean consumeNext(int ipos, Consumer out) {
        if (!hasNext(ipos)) {
            return false;
        }
        int start = posToDataIndex(ipos);
        int end = nextNodeIndex(start, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (end == start) {
            end = nextDataIndex(start);
        }
        if (end >= 0) {
            consumeIRange(start, end, out);
        }
        return true;
    }

    public void consumePosRange(int startPos, int endPos, Consumer out) {
        consumeIRange(posToDataIndex(startPos), posToDataIndex(endPos), out);
    }

    public int consumeIRange(int startPosition, int endPosition, Consumer out) {
        int limit;
        int pos = startPosition;
        if (startPosition > this.gapStart || endPosition <= this.gapStart) {
            limit = endPosition;
        } else {
            limit = this.gapStart;
        }
        while (true) {
            if (pos >= limit) {
                if (pos != this.gapStart || endPosition <= this.gapEnd) {
                    return pos;
                }
                pos = this.gapEnd;
                limit = endPosition;
            }
            int pos2 = pos + 1;
            char datum = this.data[pos];
            if (datum <= '鿿') {
                int start = pos2 - 1;
                int lim = limit;
                while (pos2 < lim) {
                    pos = pos2 + 1;
                    if (this.data[pos2] > '鿿') {
                        pos--;
                        out.write(this.data, start, pos - start);
                    } else {
                        pos2 = pos;
                    }
                }
                pos = pos2;
                out.write(this.data, start, pos - start);
            } else if (datum >= '' && datum <= '') {
                out.writeObject(this.objects[datum - OBJECT_REF_SHORT]);
                pos = pos2;
            } else if (datum >= 'ꀀ' && datum <= '꿿') {
                out.startElement(this.objects[datum - BEGIN_ELEMENT_SHORT]);
                pos = pos2 + 2;
            } else if (datum < '뀀' || datum > '?') {
                int index;
                int length;
                switch (datum) {
                    case '':
                    case '':
                        out.writeBoolean(datum != BOOL_FALSE);
                        pos = pos2;
                        break;
                    case INT_FOLLOWS /*61698*/:
                        out.writeInt(getIntN(pos2));
                        pos = pos2 + 2;
                        break;
                    case LONG_FOLLOWS /*61699*/:
                        out.writeLong(getLongN(pos2));
                        pos = pos2 + 4;
                        break;
                    case FLOAT_FOLLOWS /*61700*/:
                        out.writeFloat(Float.intBitsToFloat(getIntN(pos2)));
                        pos = pos2 + 2;
                        break;
                    case DOUBLE_FOLLOWS /*61701*/:
                        out.writeDouble(Double.longBitsToDouble(getLongN(pos2)));
                        pos = pos2 + 4;
                        break;
                    case CHAR_FOLLOWS /*61702*/:
                        out.write(this.data, pos2, (datum + 1) - CHAR_FOLLOWS);
                        pos = pos2 + 1;
                        break;
                    case BEGIN_ELEMENT_LONG /*61704*/:
                        index = getIntN(pos2);
                        pos = pos2 + 2;
                        out.startElement(this.objects[getIntN((index + (index >= 0 ? pos2 - 1 : this.data.length)) + 1)]);
                        break;
                    case BEGIN_ATTRIBUTE_LONG /*61705*/:
                        out.startAttribute(this.objects[getIntN(pos2)]);
                        pos = pos2 + 4;
                        break;
                    case END_ATTRIBUTE /*61706*/:
                        out.endAttribute();
                        pos = pos2;
                        break;
                    case END_ELEMENT_SHORT /*61707*/:
                        pos = pos2 + 1;
                        out.endElement();
                        break;
                    case END_ELEMENT_LONG /*61708*/:
                        index = getIntN(pos2);
                        out.endElement();
                        pos = pos2 + 6;
                        break;
                    case '':
                        if (out instanceof PositionConsumer) {
                            ((PositionConsumer) out).consume((SeqPosition) this.objects[getIntN(pos2)]);
                            pos = pos2 + 2;
                            break;
                        }
                    case '':
                        out.writeObject(this.objects[getIntN(pos2)]);
                        pos = pos2 + 2;
                        break;
                    case '':
                        AbstractSequence seq = this.objects[getIntN(pos2)];
                        int ipos = getIntN(pos2 + 2);
                        if (out instanceof PositionConsumer) {
                            ((PositionConsumer) out).writePosition(seq, ipos);
                        } else {
                            out.writeObject(seq.getIteratorAtPos(ipos));
                        }
                        pos = pos2 + 4;
                        break;
                    case BEGIN_DOCUMENT /*61712*/:
                        out.startDocument();
                        pos = pos2 + 4;
                        break;
                    case END_DOCUMENT /*61713*/:
                        out.endDocument();
                        pos = pos2;
                        break;
                    case BEGIN_ENTITY /*61714*/:
                        if (out instanceof TreeList) {
                            ((TreeList) out).beginEntity(this.objects[getIntN(pos2)]);
                        }
                        pos = pos2 + 4;
                        break;
                    case END_ENTITY /*61715*/:
                        if (!(out instanceof TreeList)) {
                            pos = pos2;
                            break;
                        }
                        ((TreeList) out).endEntity();
                        pos = pos2;
                        break;
                    case PROCESSING_INSTRUCTION /*61716*/:
                        String target = this.objects[getIntN(pos2)];
                        length = getIntN(pos2 + 2);
                        pos = pos2 + 4;
                        if (out instanceof XConsumer) {
                            ((XConsumer) out).writeProcessingInstruction(target, this.data, pos, length);
                        }
                        pos += length;
                        break;
                    case CDATA_SECTION /*61717*/:
                        length = getIntN(pos2);
                        pos = pos2 + 2;
                        if (out instanceof XConsumer) {
                            ((XConsumer) out).writeCDATA(this.data, pos, length);
                        } else {
                            out.write(this.data, pos, length);
                        }
                        pos += length;
                        break;
                    case JOINER /*61718*/:
                        out.write("");
                        pos = pos2;
                        break;
                    case COMMENT /*61719*/:
                        length = getIntN(pos2);
                        pos = pos2 + 2;
                        if (out instanceof XConsumer) {
                            ((XConsumer) out).writeComment(this.data, pos, length);
                        }
                        pos += length;
                        break;
                    case DOCUMENT_URI /*61720*/:
                        if (out instanceof TreeList) {
                            ((TreeList) out).writeDocumentUri(this.objects[getIntN(pos2)]);
                        }
                        pos = pos2 + 2;
                        break;
                    default:
                        throw new Error("unknown code:" + datum);
                }
            } else {
                out.writeInt(datum - INT_SHORT_ZERO);
                pos = pos2;
            }
        }
    }

    public void toString(String sep, StringBuffer sbuf) {
        int pos = 0;
        int limit = this.gapStart;
        boolean seen = false;
        boolean inStartTag = false;
        while (true) {
            if (pos >= limit) {
                if (pos == this.gapStart) {
                    pos = this.gapEnd;
                    limit = this.data.length;
                    if (pos == limit) {
                        return;
                    }
                }
                return;
            }
            int pos2 = pos + 1;
            char datum = this.data[pos];
            if (datum <= '鿿') {
                int start = pos2 - 1;
                int lim = limit;
                while (pos2 < lim) {
                    pos = pos2 + 1;
                    if (this.data[pos2] > '鿿') {
                        pos--;
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        sbuf.append(this.data, start, pos - start);
                        seen = false;
                    } else {
                        pos2 = pos;
                    }
                }
                pos = pos2;
                if (inStartTag) {
                    sbuf.append('>');
                    inStartTag = false;
                }
                sbuf.append(this.data, start, pos - start);
                seen = false;
            } else if (datum >= '' && datum <= '') {
                if (inStartTag) {
                    sbuf.append('>');
                    inStartTag = false;
                }
                if (seen) {
                    sbuf.append(sep);
                } else {
                    seen = true;
                }
                sbuf.append(this.objects[datum - OBJECT_REF_SHORT]);
                pos = pos2;
            } else if (datum >= 'ꀀ' && datum <= '꿿') {
                if (inStartTag) {
                    sbuf.append('>');
                }
                index = datum - BEGIN_ELEMENT_SHORT;
                if (seen) {
                    sbuf.append(sep);
                }
                sbuf.append('<');
                sbuf.append(this.objects[index].toString());
                pos = pos2 + 2;
                seen = false;
                inStartTag = true;
            } else if (datum < '뀀' || datum > '?') {
                switch (datum) {
                    case '':
                    case '':
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(datum != BOOL_FALSE);
                        pos = pos2;
                        break;
                    case INT_FOLLOWS /*61698*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(getIntN(pos2));
                        pos = pos2 + 2;
                        break;
                    case LONG_FOLLOWS /*61699*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(getLongN(pos2));
                        pos = pos2 + 4;
                        break;
                    case FLOAT_FOLLOWS /*61700*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(Float.intBitsToFloat(getIntN(pos2)));
                        pos = pos2 + 2;
                        break;
                    case DOUBLE_FOLLOWS /*61701*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(Double.longBitsToDouble(getLongN(pos2)));
                        pos = pos2 + 4;
                        break;
                    case CHAR_FOLLOWS /*61702*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        sbuf.append(this.data, pos2, (datum + 1) - CHAR_FOLLOWS);
                        seen = false;
                        pos = pos2 + 1;
                        break;
                    case BEGIN_ELEMENT_LONG /*61704*/:
                        index = getIntN(pos2);
                        pos = pos2 + 2;
                        index = getIntN((index + (index >= 0 ? pos2 - 1 : this.data.length)) + 1);
                        if (inStartTag) {
                            sbuf.append('>');
                        } else if (seen) {
                            sbuf.append(sep);
                        }
                        sbuf.append('<');
                        sbuf.append(this.objects[index]);
                        seen = false;
                        inStartTag = true;
                        break;
                    case BEGIN_ATTRIBUTE_LONG /*61705*/:
                        index = getIntN(pos2);
                        sbuf.append(' ');
                        sbuf.append(this.objects[index]);
                        sbuf.append("=\"");
                        inStartTag = false;
                        pos = pos2 + 4;
                        break;
                    case END_ATTRIBUTE /*61706*/:
                        sbuf.append('\"');
                        inStartTag = true;
                        seen = false;
                        pos = pos2;
                        break;
                    case END_ELEMENT_SHORT /*61707*/:
                    case END_ELEMENT_LONG /*61708*/:
                        if (datum == '') {
                            pos = pos2 + 1;
                            index = this.data[(pos - 2) - this.data[pos2]] - BEGIN_ELEMENT_SHORT;
                        } else {
                            index = getIntN(pos2);
                            pos = pos2 + 6;
                        }
                        if (inStartTag) {
                            sbuf.append("/>");
                        } else {
                            sbuf.append("</");
                            sbuf.append(this.objects[index]);
                            sbuf.append('>');
                        }
                        inStartTag = false;
                        seen = true;
                        break;
                    case '':
                    case '':
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(this.objects[getIntN(pos2)]);
                        pos = pos2 + 2;
                        break;
                    case '':
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        if (seen) {
                            sbuf.append(sep);
                        } else {
                            seen = true;
                        }
                        sbuf.append(this.objects[getIntN(pos2)].getIteratorAtPos(getIntN(pos2 + 2)));
                        pos = pos2 + 4;
                        break;
                    case BEGIN_DOCUMENT /*61712*/:
                    case BEGIN_ENTITY /*61714*/:
                        pos = pos2 + 4;
                        break;
                    case END_DOCUMENT /*61713*/:
                    case END_ENTITY /*61715*/:
                        pos = pos2;
                        break;
                    case PROCESSING_INSTRUCTION /*61716*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        sbuf.append("<?");
                        pos = pos2 + 2;
                        sbuf.append(this.objects[getIntN(pos2)]);
                        index = getIntN(pos);
                        pos += 2;
                        if (index > 0) {
                            sbuf.append(' ');
                            sbuf.append(this.data, pos, index);
                            pos += index;
                        }
                        sbuf.append("?>");
                        break;
                    case CDATA_SECTION /*61717*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        index = getIntN(pos2);
                        pos = pos2 + 2;
                        sbuf.append("<![CDATA[");
                        sbuf.append(this.data, pos, index);
                        sbuf.append("]]>");
                        pos += index;
                        break;
                    case JOINER /*61718*/:
                        pos = pos2;
                        break;
                    case COMMENT /*61719*/:
                        if (inStartTag) {
                            sbuf.append('>');
                            inStartTag = false;
                        }
                        index = getIntN(pos2);
                        pos = pos2 + 2;
                        sbuf.append("<!--");
                        sbuf.append(this.data, pos, index);
                        sbuf.append("-->");
                        pos += index;
                        break;
                    case DOCUMENT_URI /*61720*/:
                        pos = pos2 + 2;
                        break;
                    default:
                        throw new Error("unknown code:" + datum);
                }
            } else {
                if (inStartTag) {
                    sbuf.append('>');
                    inStartTag = false;
                }
                if (seen) {
                    sbuf.append(sep);
                } else {
                    seen = true;
                }
                sbuf.append(datum - INT_SHORT_ZERO);
                pos = pos2;
            }
        }
    }

    public boolean hasNext(int ipos) {
        int index = posToDataIndex(ipos);
        if (index == this.data.length) {
            return false;
        }
        char ch = this.data[index];
        if (ch == '' || ch == '' || ch == '' || ch == '') {
            return false;
        }
        return true;
    }

    public int getNextKind(int ipos) {
        return getNextKindI(posToDataIndex(ipos));
    }

    public int getNextKindI(int index) {
        if (index == this.data.length) {
            return 0;
        }
        char datum = this.data[index];
        if (datum <= '鿿') {
            return 29;
        }
        if (datum >= '' && datum <= '') {
            return 32;
        }
        if (datum >= 'ꀀ' && datum <= '꿿') {
            return 33;
        }
        if ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & datum) == BYTE_PREFIX) {
            return 28;
        }
        if (datum >= '뀀' && datum <= '?') {
            return 22;
        }
        switch (datum) {
            case '':
            case '':
                return 27;
            case INT_FOLLOWS /*61698*/:
                return 22;
            case LONG_FOLLOWS /*61699*/:
                return 24;
            case FLOAT_FOLLOWS /*61700*/:
                return 25;
            case DOUBLE_FOLLOWS /*61701*/:
                return 26;
            case CHAR_FOLLOWS /*61702*/:
            case BEGIN_DOCUMENT /*61712*/:
                return 34;
            case BEGIN_ELEMENT_LONG /*61704*/:
                return 33;
            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                return 35;
            case END_ATTRIBUTE /*61706*/:
            case END_ELEMENT_SHORT /*61707*/:
            case END_ELEMENT_LONG /*61708*/:
            case END_DOCUMENT /*61713*/:
            case END_ENTITY /*61715*/:
                return 0;
            case BEGIN_ENTITY /*61714*/:
                return getNextKind((index + 5) << 1);
            case PROCESSING_INSTRUCTION /*61716*/:
                return 37;
            case CDATA_SECTION /*61717*/:
                return 31;
            case COMMENT /*61719*/:
                return 36;
            default:
                return 32;
        }
    }

    public Object getNextTypeObject(int ipos) {
        int index = posToDataIndex(ipos);
        while (index != this.data.length) {
            char datum = this.data[index];
            if (datum != '') {
                if (datum >= 'ꀀ' && datum <= '꿿') {
                    index = datum - BEGIN_ELEMENT_SHORT;
                } else if (datum == '') {
                    int j = getIntN(index + 1);
                    if (j < 0) {
                        index = this.data.length;
                    }
                    index = getIntN((j + index) + 1);
                } else if (datum == '') {
                    index = getIntN(index + 1);
                } else if (datum != '') {
                    return null;
                } else {
                    index = getIntN(index + 1);
                }
                if (index >= 0) {
                    return this.objects[index];
                }
                return null;
            }
            index += 5;
        }
        return null;
    }

    public String getNextTypeName(int ipos) {
        Object type = getNextTypeObject(ipos);
        return type == null ? null : type.toString();
    }

    public Object getPosPrevious(int ipos) {
        if ((ipos & 1) == 0 || ipos == -1) {
            return super.getPosPrevious(ipos);
        }
        return getPosNext(ipos - 3);
    }

    private Object copyToList(int startPosition, int endPosition) {
        return new TreeList(this, startPosition, endPosition);
    }

    public int getPosNextInt(int ipos) {
        int index = posToDataIndex(ipos);
        if (index < this.data.length) {
            char datum = this.data[index];
            if (datum >= '뀀' && datum <= '?') {
                return datum - INT_SHORT_ZERO;
            }
            if (datum == '') {
                return getIntN(index + 1);
            }
        }
        return ((Number) getPosNext(ipos)).intValue();
    }

    public Object getPosNext(int ipos) {
        int index = posToDataIndex(ipos);
        if (index == this.data.length) {
            return Sequence.eofValue;
        }
        char datum = this.data[index];
        if (datum <= '鿿') {
            return Convert.toObject(datum);
        }
        if (datum >= '' && datum <= '') {
            return this.objects[datum - OBJECT_REF_SHORT];
        }
        if (datum >= 'ꀀ' && datum <= '꿿') {
            return copyToList(index, (this.data[index + 1] + index) + 2);
        }
        if (datum >= '뀀' && datum <= '?') {
            return Convert.toObject(datum - INT_SHORT_ZERO);
        }
        int end_offset;
        int length;
        switch (datum) {
            case '':
            case '':
                return Convert.toObject(datum != BOOL_FALSE);
            case INT_FOLLOWS /*61698*/:
                return Convert.toObject(getIntN(index + 1));
            case LONG_FOLLOWS /*61699*/:
                return Convert.toObject(getLongN(index + 1));
            case FLOAT_FOLLOWS /*61700*/:
                return Convert.toObject(Float.intBitsToFloat(getIntN(index + 1)));
            case DOUBLE_FOLLOWS /*61701*/:
                return Convert.toObject(Double.longBitsToDouble(getLongN(index + 1)));
            case CHAR_FOLLOWS /*61702*/:
                return Convert.toObject(this.data[index + 1]);
            case BEGIN_ELEMENT_LONG /*61704*/:
                end_offset = getIntN(index + 1);
                if (end_offset < 0) {
                    length = this.data.length;
                } else {
                    length = index;
                }
                return copyToList(index, (end_offset + length) + 7);
            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                end_offset = getIntN(index + 3);
                if (end_offset < 0) {
                    length = this.data.length;
                } else {
                    length = index;
                }
                return copyToList(index, (end_offset + length) + 1);
            case END_ATTRIBUTE /*61706*/:
            case END_ELEMENT_SHORT /*61707*/:
            case END_ELEMENT_LONG /*61708*/:
            case END_DOCUMENT /*61713*/:
                return Sequence.eofValue;
            case '':
            case '':
                return this.objects[getIntN(index + 1)];
            case '':
                return this.objects[getIntN(index + 1)].getIteratorAtPos(getIntN(index + 3));
            case BEGIN_DOCUMENT /*61712*/:
                end_offset = getIntN(index + 1);
                return copyToList(index, (end_offset + (end_offset < 0 ? this.data.length : index)) + 1);
            case JOINER /*61718*/:
                return "";
            default:
                throw unsupported("getPosNext, code=" + Integer.toHexString(datum));
        }
    }

    public void stringValue(int startIndex, int endIndex, StringBuffer sbuf) {
        int index = startIndex;
        while (index < endIndex && index >= 0) {
            index = stringValue(false, index, sbuf);
        }
    }

    public int stringValue(int index, StringBuffer sbuf) {
        int next = nextNodeIndex(index, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
        if (next <= index) {
            return stringValue(false, index, sbuf);
        }
        stringValue(index, next, sbuf);
        return index;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int stringValue(boolean r13, int r14, java.lang.StringBuffer r15) {
        /*
        r12 = this;
        r8 = 0;
        r1 = 0;
        r9 = r12.gapStart;
        if (r14 < r9) goto L_0x000c;
    L_0x0006:
        r9 = r12.gapEnd;
        r10 = r12.gapStart;
        r9 = r9 - r10;
        r14 = r14 + r9;
    L_0x000c:
        r9 = r12.data;
        r9 = r9.length;
        if (r14 != r9) goto L_0x0013;
    L_0x0011:
        r9 = -1;
    L_0x0012:
        return r9;
    L_0x0013:
        r9 = r12.data;
        r0 = r9[r14];
        r14 = r14 + 1;
        r7 = 0;
        r9 = 40959; // 0x9fff float:5.7396E-41 double:2.02364E-319;
        if (r0 > r9) goto L_0x0024;
    L_0x001f:
        r15.append(r0);
        r9 = r14;
        goto L_0x0012;
    L_0x0024:
        r9 = 57344; // 0xe000 float:8.0356E-41 double:2.83317E-319;
        if (r0 < r9) goto L_0x004f;
    L_0x0029:
        r9 = 61439; // 0xefff float:8.6094E-41 double:3.0355E-319;
        if (r0 > r9) goto L_0x004f;
    L_0x002e:
        if (r7 == 0) goto L_0x0035;
    L_0x0030:
        r9 = 32;
        r15.append(r9);
    L_0x0035:
        r9 = r12.objects;
        r10 = 57344; // 0xe000 float:8.0356E-41 double:2.83317E-319;
        r10 = r0 - r10;
        r8 = r9[r10];
        r7 = 0;
    L_0x003f:
        if (r8 == 0) goto L_0x0044;
    L_0x0041:
        r15.append(r8);
    L_0x0044:
        if (r1 <= 0) goto L_0x004d;
    L_0x0046:
        r9 = 1;
        r1 = r12.stringValue(r9, r1, r15);
        if (r1 >= 0) goto L_0x0046;
    L_0x004d:
        r9 = r14;
        goto L_0x0012;
    L_0x004f:
        r9 = 40960; // 0xa000 float:5.7397E-41 double:2.0237E-319;
        if (r0 < r9) goto L_0x0063;
    L_0x0054:
        r9 = 45055; // 0xafff float:6.3136E-41 double:2.226E-319;
        if (r0 > r9) goto L_0x0063;
    L_0x0059:
        r1 = r14 + 2;
        r9 = r12.data;
        r9 = r9[r14];
        r9 = r9 + r14;
        r14 = r9 + 1;
        goto L_0x003f;
    L_0x0063:
        r9 = 65280; // 0xff00 float:9.1477E-41 double:3.22526E-319;
        r9 = r9 & r0;
        r10 = 61440; // 0xf000 float:8.6096E-41 double:3.03554E-319;
        if (r9 != r10) goto L_0x0073;
    L_0x006c:
        r9 = r0 & 255;
        r15.append(r9);
        r9 = r14;
        goto L_0x0012;
    L_0x0073:
        r9 = 45056; // 0xb000 float:6.3137E-41 double:2.22606E-319;
        if (r0 < r9) goto L_0x0087;
    L_0x0078:
        r9 = 57343; // 0xdfff float:8.0355E-41 double:2.8331E-319;
        if (r0 > r9) goto L_0x0087;
    L_0x007d:
        r9 = 49152; // 0xc000 float:6.8877E-41 double:2.42843E-319;
        r9 = r0 - r9;
        r15.append(r9);
        r9 = r14;
        goto L_0x0012;
    L_0x0087:
        switch(r0) {
            case 61696: goto L_0x00cd;
            case 61697: goto L_0x00cd;
            case 61698: goto L_0x00e3;
            case 61699: goto L_0x00f6;
            case 61700: goto L_0x0109;
            case 61701: goto L_0x0120;
            case 61702: goto L_0x0137;
            case 61703: goto L_0x008a;
            case 61704: goto L_0x014d;
            case 61705: goto L_0x0167;
            case 61706: goto L_0x0164;
            case 61707: goto L_0x0164;
            case 61708: goto L_0x0164;
            case 61709: goto L_0x008a;
            case 61710: goto L_0x008a;
            case 61711: goto L_0x017b;
            case 61712: goto L_0x0143;
            case 61713: goto L_0x0164;
            case 61714: goto L_0x0143;
            case 61715: goto L_0x0164;
            case 61716: goto L_0x00b5;
            case 61717: goto L_0x00b7;
            case 61718: goto L_0x0161;
            case 61719: goto L_0x00b7;
            case 61720: goto L_0x00b1;
            default: goto L_0x008a;
        };
    L_0x008a:
        r9 = new java.lang.Error;
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "unimplemented: ";
        r10 = r10.append(r11);
        r11 = java.lang.Integer.toHexString(r0);
        r10 = r10.append(r11);
        r11 = " at:";
        r10 = r10.append(r11);
        r10 = r10.append(r14);
        r10 = r10.toString();
        r9.<init>(r10);
        throw r9;
    L_0x00b1:
        r9 = r14 + 2;
        goto L_0x0012;
    L_0x00b5:
        r14 = r14 + 2;
    L_0x00b7:
        r5 = r12.getIntN(r14);
        r14 = r14 + 2;
        if (r13 == 0) goto L_0x00c4;
    L_0x00bf:
        r9 = 61717; // 0xf115 float:8.6484E-41 double:3.04922E-319;
        if (r0 != r9) goto L_0x00c9;
    L_0x00c4:
        r9 = r12.data;
        r15.append(r9, r14, r5);
    L_0x00c9:
        r9 = r14 + r5;
        goto L_0x0012;
    L_0x00cd:
        if (r7 == 0) goto L_0x00d4;
    L_0x00cf:
        r9 = 32;
        r15.append(r9);
    L_0x00d4:
        r9 = 61696; // 0xf100 float:8.6455E-41 double:3.0482E-319;
        if (r0 == r9) goto L_0x00e1;
    L_0x00d9:
        r9 = 1;
    L_0x00da:
        r15.append(r9);
        r7 = 1;
        r9 = r14;
        goto L_0x0012;
    L_0x00e1:
        r9 = 0;
        goto L_0x00da;
    L_0x00e3:
        if (r7 == 0) goto L_0x00ea;
    L_0x00e5:
        r9 = 32;
        r15.append(r9);
    L_0x00ea:
        r9 = r12.getIntN(r14);
        r15.append(r9);
        r7 = 1;
        r9 = r14 + 2;
        goto L_0x0012;
    L_0x00f6:
        if (r7 == 0) goto L_0x00fd;
    L_0x00f8:
        r9 = 32;
        r15.append(r9);
    L_0x00fd:
        r10 = r12.getLongN(r14);
        r15.append(r10);
        r7 = 1;
        r9 = r14 + 4;
        goto L_0x0012;
    L_0x0109:
        if (r7 == 0) goto L_0x0110;
    L_0x010b:
        r9 = 32;
        r15.append(r9);
    L_0x0110:
        r9 = r12.getIntN(r14);
        r9 = java.lang.Float.intBitsToFloat(r9);
        r15.append(r9);
        r7 = 1;
        r9 = r14 + 2;
        goto L_0x0012;
    L_0x0120:
        if (r7 == 0) goto L_0x0127;
    L_0x0122:
        r9 = 32;
        r15.append(r9);
    L_0x0127:
        r10 = r12.getLongN(r14);
        r10 = java.lang.Double.longBitsToDouble(r10);
        r15.append(r10);
        r7 = 1;
        r9 = r14 + 4;
        goto L_0x0012;
    L_0x0137:
        r7 = 0;
        r9 = r12.data;
        r9 = r9[r14];
        r15.append(r9);
        r9 = r14 + 1;
        goto L_0x0012;
    L_0x0143:
        r1 = r14 + 4;
        r9 = r14 + -1;
        r14 = r12.nextDataIndex(r9);
        goto L_0x003f;
    L_0x014d:
        r7 = 0;
        r1 = r14 + 2;
        r4 = r12.getIntN(r14);
        if (r4 >= 0) goto L_0x015e;
    L_0x0156:
        r9 = r12.data;
        r9 = r9.length;
    L_0x0159:
        r4 = r4 + r9;
        r14 = r4 + 7;
        goto L_0x003f;
    L_0x015e:
        r9 = r14 + -1;
        goto L_0x0159;
    L_0x0161:
        r7 = 0;
        goto L_0x003f;
    L_0x0164:
        r9 = -1;
        goto L_0x0012;
    L_0x0167:
        if (r13 != 0) goto L_0x016b;
    L_0x0169:
        r1 = r14 + 4;
    L_0x016b:
        r9 = r14 + 2;
        r2 = r12.getIntN(r9);
        if (r2 >= 0) goto L_0x0178;
    L_0x0173:
        r9 = r12.data;
        r9 = r9.length;
        r14 = r9 + 1;
    L_0x0178:
        r14 = r14 + r2;
        goto L_0x003f;
    L_0x017b:
        r9 = r12.objects;
        r10 = r12.getIntN(r14);
        r6 = r9[r10];
        r6 = (gnu.lists.AbstractSequence) r6;
        r9 = r14 + 2;
        r3 = r12.getIntN(r9);
        r6 = (gnu.lists.TreeList) r6;
        r9 = r3 >> 1;
        r6.stringValue(r13, r9, r15);
        r14 = r14 + 4;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.TreeList.stringValue(boolean, int, java.lang.StringBuffer):int");
    }

    public int createRelativePos(int istart, int offset, boolean isAfter) {
        if (isAfter) {
            if (offset == 0) {
                if ((istart & 1) != 0) {
                    return istart;
                }
                if (istart == 0) {
                    return 1;
                }
            }
            offset--;
        }
        if (offset < 0) {
            throw unsupported("backwards createRelativePos");
        }
        int pos = posToDataIndex(istart);
        do {
            offset--;
            if (offset >= 0) {
                pos = nextDataIndex(pos);
            } else {
                if (pos >= this.gapEnd) {
                    pos -= this.gapEnd - this.gapStart;
                }
                return isAfter ? ((pos + 1) << 1) | 1 : pos << 1;
            }
        } while (pos >= 0);
        throw new IndexOutOfBoundsException();
    }

    public final int nextNodeIndex(int pos, int limit) {
        if ((Integer.MIN_VALUE | limit) == -1) {
            limit = this.data.length;
        }
        while (true) {
            if (pos == this.gapStart) {
                pos = this.gapEnd;
            }
            if (pos < limit) {
                char datum = this.data[pos];
                if (datum <= '鿿' || ((datum >= '' && datum <= '') || ((datum >= '뀀' && datum <= '?') || (MotionEventCompat.ACTION_POINTER_INDEX_MASK & datum) == BYTE_PREFIX))) {
                    pos++;
                } else if (datum < 'ꀀ' || datum > '꿿') {
                    switch (datum) {
                        case BEGIN_ELEMENT_LONG /*61704*/:
                        case BEGIN_ATTRIBUTE_LONG /*61705*/:
                        case END_ATTRIBUTE /*61706*/:
                        case END_ELEMENT_SHORT /*61707*/:
                        case END_ELEMENT_LONG /*61708*/:
                        case BEGIN_DOCUMENT /*61712*/:
                        case END_DOCUMENT /*61713*/:
                        case END_ENTITY /*61715*/:
                        case PROCESSING_INSTRUCTION /*61716*/:
                        case COMMENT /*61719*/:
                            break;
                        case BEGIN_ENTITY /*61714*/:
                            pos += 5;
                            continue;
                        case JOINER /*61718*/:
                            pos++;
                            continue;
                        case DOCUMENT_URI /*61720*/:
                            pos += 3;
                            continue;
                        default:
                            pos = nextDataIndex(pos);
                            continue;
                    }
                }
            }
            return pos;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int nextMatching(int r15, gnu.lists.ItemPredicate r16, int r17, boolean r18) {
        /*
        r14 = this;
        r10 = r14.posToDataIndex(r15);
        r0 = r17;
        r7 = r14.posToDataIndex(r0);
        r9 = r10;
        r0 = r16;
        r11 = r0 instanceof gnu.lists.NodePredicate;
        if (r11 == 0) goto L_0x0015;
    L_0x0011:
        r9 = r14.nextNodeIndex(r9, r7);
    L_0x0015:
        r1 = 0;
        r0 = r16;
        r11 = r0 instanceof gnu.lists.ElementPredicate;
        if (r11 == 0) goto L_0x002c;
    L_0x001c:
        r3 = 1;
        r2 = 1;
        r4 = 0;
    L_0x001f:
        r11 = r14.gapStart;
        if (r9 != r11) goto L_0x0025;
    L_0x0023:
        r9 = r14.gapEnd;
    L_0x0025:
        if (r9 < r7) goto L_0x003a;
    L_0x0027:
        r11 = -1;
        if (r7 == r11) goto L_0x003a;
    L_0x002a:
        r11 = 0;
    L_0x002b:
        return r11;
    L_0x002c:
        r0 = r16;
        r11 = r0 instanceof gnu.lists.AttributePredicate;
        if (r11 == 0) goto L_0x0036;
    L_0x0032:
        r3 = 1;
        r2 = 0;
        r4 = 0;
        goto L_0x001f;
    L_0x0036:
        r3 = 1;
        r2 = 1;
        r4 = 1;
        goto L_0x001f;
    L_0x003a:
        r11 = r14.data;
        r5 = r11[r9];
        r11 = 40959; // 0x9fff float:5.7396E-41 double:2.02364E-319;
        if (r5 <= r11) goto L_0x0057;
    L_0x0043:
        r11 = 57344; // 0xe000 float:8.0356E-41 double:2.83317E-319;
        if (r5 < r11) goto L_0x004d;
    L_0x0048:
        r11 = 61439; // 0xefff float:8.6094E-41 double:3.0355E-319;
        if (r5 <= r11) goto L_0x0057;
    L_0x004d:
        r11 = 45056; // 0xb000 float:6.3137E-41 double:2.22606E-319;
        if (r5 < r11) goto L_0x0074;
    L_0x0052:
        r11 = 57343; // 0xdfff float:8.0355E-41 double:2.8331E-319;
        if (r5 > r11) goto L_0x0074;
    L_0x0057:
        if (r4 == 0) goto L_0x0070;
    L_0x0059:
        r11 = r9 << 1;
        r0 = r16;
        r11 = r0.isInstancePos(r14, r11);
        if (r11 == 0) goto L_0x0070;
    L_0x0063:
        r11 = r14.gapEnd;
        if (r9 < r11) goto L_0x006d;
    L_0x0067:
        r11 = r14.gapEnd;
        r12 = r14.gapStart;
        r11 = r11 - r12;
        r9 = r9 - r11;
    L_0x006d:
        r11 = r9 << 1;
        goto L_0x002b;
    L_0x0070:
        r8 = r9 + 1;
    L_0x0072:
        r9 = r8;
        goto L_0x001f;
    L_0x0074:
        switch(r5) {
            case 61696: goto L_0x00e9;
            case 61697: goto L_0x00e9;
            case 61698: goto L_0x00ab;
            case 61699: goto L_0x00f1;
            case 61700: goto L_0x00ab;
            case 61701: goto L_0x00f1;
            case 61702: goto L_0x00b0;
            case 61703: goto L_0x0077;
            case 61704: goto L_0x011f;
            case 61705: goto L_0x00d0;
            case 61706: goto L_0x00c8;
            case 61707: goto L_0x00b3;
            case 61708: goto L_0x00c0;
            case 61709: goto L_0x00ab;
            case 61710: goto L_0x00ab;
            case 61711: goto L_0x00bb;
            case 61712: goto L_0x00a3;
            case 61713: goto L_0x00c8;
            case 61714: goto L_0x00a8;
            case 61715: goto L_0x00cd;
            case 61716: goto L_0x00f6;
            case 61717: goto L_0x0111;
            case 61718: goto L_0x00ee;
            case 61719: goto L_0x0103;
            case 61720: goto L_0x00a0;
            default: goto L_0x0077;
        };
    L_0x0077:
        r11 = 40960; // 0xa000 float:5.7397E-41 double:2.0237E-319;
        if (r5 < r11) goto L_0x0143;
    L_0x007c:
        r11 = 45055; // 0xafff float:6.3136E-41 double:2.226E-319;
        if (r5 > r11) goto L_0x0143;
    L_0x0081:
        if (r18 == 0) goto L_0x0138;
    L_0x0083:
        r8 = r9 + 3;
    L_0x0085:
        if (r2 == 0) goto L_0x0072;
    L_0x0087:
        if (r9 <= r10) goto L_0x0072;
    L_0x0089:
        r11 = r9 << 1;
        r0 = r16;
        r11 = r0.isInstancePos(r14, r11);
        if (r11 == 0) goto L_0x0072;
    L_0x0093:
        r11 = r14.gapEnd;
        if (r9 < r11) goto L_0x009d;
    L_0x0097:
        r11 = r14.gapEnd;
        r12 = r14.gapStart;
        r11 = r11 - r12;
        r9 = r9 - r11;
    L_0x009d:
        r11 = r9 << 1;
        goto L_0x002b;
    L_0x00a0:
        r8 = r9 + 3;
        goto L_0x0072;
    L_0x00a3:
        r8 = r9 + 5;
        if (r3 == 0) goto L_0x0072;
    L_0x00a7:
        goto L_0x0087;
    L_0x00a8:
        r8 = r9 + 5;
        goto L_0x0072;
    L_0x00ab:
        r8 = r9 + 3;
        if (r4 == 0) goto L_0x0072;
    L_0x00af:
        goto L_0x0087;
    L_0x00b0:
        r8 = r9 + 2;
        goto L_0x0072;
    L_0x00b3:
        if (r18 != 0) goto L_0x00b8;
    L_0x00b5:
        r11 = 0;
        goto L_0x002b;
    L_0x00b8:
        r8 = r9 + 2;
        goto L_0x0072;
    L_0x00bb:
        r8 = r9 + 5;
        if (r4 == 0) goto L_0x0072;
    L_0x00bf:
        goto L_0x0087;
    L_0x00c0:
        if (r18 != 0) goto L_0x00c5;
    L_0x00c2:
        r11 = 0;
        goto L_0x002b;
    L_0x00c5:
        r8 = r9 + 7;
        goto L_0x0072;
    L_0x00c8:
        if (r18 != 0) goto L_0x00cd;
    L_0x00ca:
        r11 = 0;
        goto L_0x002b;
    L_0x00cd:
        r8 = r9 + 1;
        goto L_0x0072;
    L_0x00d0:
        if (r3 == 0) goto L_0x00e6;
    L_0x00d2:
        r11 = r9 + 3;
        r6 = r14.getIntN(r11);
        r12 = r6 + 1;
        if (r6 >= 0) goto L_0x00e4;
    L_0x00dc:
        r11 = r14.data;
        r11 = r11.length;
    L_0x00df:
        r8 = r12 + r11;
    L_0x00e1:
        if (r1 == 0) goto L_0x0072;
    L_0x00e3:
        goto L_0x0087;
    L_0x00e4:
        r11 = r9;
        goto L_0x00df;
    L_0x00e6:
        r8 = r9 + 5;
        goto L_0x00e1;
    L_0x00e9:
        r8 = r9 + 1;
        if (r4 == 0) goto L_0x0072;
    L_0x00ed:
        goto L_0x0087;
    L_0x00ee:
        r8 = r9 + 1;
        goto L_0x0072;
    L_0x00f1:
        r8 = r9 + 5;
        if (r4 == 0) goto L_0x0072;
    L_0x00f5:
        goto L_0x0087;
    L_0x00f6:
        r11 = r9 + 5;
        r12 = r9 + 3;
        r12 = r14.getIntN(r12);
        r8 = r11 + r12;
        if (r3 == 0) goto L_0x0072;
    L_0x0102:
        goto L_0x0087;
    L_0x0103:
        r11 = r9 + 3;
        r12 = r9 + 1;
        r12 = r14.getIntN(r12);
        r8 = r11 + r12;
        if (r3 == 0) goto L_0x0072;
    L_0x010f:
        goto L_0x0087;
    L_0x0111:
        r11 = r9 + 3;
        r12 = r9 + 1;
        r12 = r14.getIntN(r12);
        r8 = r11 + r12;
        if (r4 == 0) goto L_0x0072;
    L_0x011d:
        goto L_0x0087;
    L_0x011f:
        if (r18 == 0) goto L_0x0127;
    L_0x0121:
        r8 = r9 + 3;
    L_0x0123:
        if (r2 == 0) goto L_0x0072;
    L_0x0125:
        goto L_0x0087;
    L_0x0127:
        r11 = r9 + 1;
        r6 = r14.getIntN(r11);
        if (r6 >= 0) goto L_0x0136;
    L_0x012f:
        r11 = r14.data;
        r11 = r11.length;
    L_0x0132:
        r11 = r11 + r6;
        r8 = r11 + 7;
        goto L_0x0123;
    L_0x0136:
        r11 = r9;
        goto L_0x0132;
    L_0x0138:
        r11 = r14.data;
        r12 = r9 + 1;
        r11 = r11[r12];
        r11 = r11 + r9;
        r8 = r11 + 2;
        goto L_0x0085;
    L_0x0143:
        r11 = new java.lang.Error;
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = "unknown code:";
        r12 = r12.append(r13);
        r12 = r12.append(r5);
        r12 = r12.toString();
        r11.<init>(r12);
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.lists.TreeList.nextMatching(int, gnu.lists.ItemPredicate, int, boolean):int");
    }

    public int nextPos(int position) {
        int index = posToDataIndex(position);
        if (index == this.data.length) {
            return 0;
        }
        if (index >= this.gapEnd) {
            index -= this.gapEnd - this.gapStart;
        }
        return (index << 1) + 3;
    }

    public final int nextDataIndex(int pos) {
        if (pos == this.gapStart) {
            pos = this.gapEnd;
        }
        if (pos == this.data.length) {
            return -1;
        }
        int pos2 = pos + 1;
        char datum = this.data[pos];
        if (datum <= '鿿' || ((datum >= '' && datum <= '') || (datum >= '뀀' && datum <= '?'))) {
            pos = pos2;
            return pos2;
        } else if (datum < 'ꀀ' || datum > '꿿') {
            int j;
            switch (datum) {
                case '':
                case '':
                case JOINER /*61718*/:
                    pos = pos2;
                    return pos2;
                case INT_FOLLOWS /*61698*/:
                case FLOAT_FOLLOWS /*61700*/:
                case '':
                case '':
                    pos = pos2;
                    return pos2 + 2;
                case LONG_FOLLOWS /*61699*/:
                case DOUBLE_FOLLOWS /*61701*/:
                    pos = pos2;
                    return pos2 + 4;
                case CHAR_FOLLOWS /*61702*/:
                    pos = pos2;
                    return pos2 + 1;
                case BEGIN_ELEMENT_LONG /*61704*/:
                    j = getIntN(pos2);
                    pos = pos2;
                    return (j + (j < 0 ? this.data.length : pos2 - 1)) + 7;
                case BEGIN_ATTRIBUTE_LONG /*61705*/:
                    j = getIntN(pos2 + 2);
                    pos = pos2;
                    return (j + (j < 0 ? this.data.length : pos2 - 1)) + 1;
                case END_ATTRIBUTE /*61706*/:
                case END_ELEMENT_SHORT /*61707*/:
                case END_ELEMENT_LONG /*61708*/:
                case END_DOCUMENT /*61713*/:
                case END_ENTITY /*61715*/:
                    return -1;
                case '':
                    pos = pos2;
                    return pos2 + 4;
                case BEGIN_DOCUMENT /*61712*/:
                    j = getIntN(pos2);
                    pos = pos2;
                    return (j + (j < 0 ? this.data.length : pos2 - 1)) + 1;
                case BEGIN_ENTITY /*61714*/:
                    j = pos2 + 4;
                    while (true) {
                        if (j == this.gapStart) {
                            j = this.gapEnd;
                        }
                        if (j == this.data.length) {
                            pos = pos2;
                            return -1;
                        } else if (this.data[j] == '') {
                            pos = pos2;
                            return j + 1;
                        } else {
                            j = nextDataIndex(j);
                        }
                    }
                case PROCESSING_INSTRUCTION /*61716*/:
                    pos = pos2 + 2;
                    break;
                case CDATA_SECTION /*61717*/:
                case COMMENT /*61719*/:
                    pos = pos2;
                    break;
                default:
                    throw new Error("unknown code:" + Integer.toHexString(datum));
            }
            return (pos + 2) + getIntN(pos);
        } else {
            pos = pos2;
            return (this.data[pos2] + pos2) + 1;
        }
    }

    public Object documentUriOfPos(int pos) {
        int index = posToDataIndex(pos);
        if (index == this.data.length || this.data[index] != '') {
            return null;
        }
        int next = index + 5;
        if (next == this.gapStart) {
            next = this.gapEnd;
        }
        if (next >= this.data.length || this.data[next] != '') {
            return null;
        }
        return this.objects[getIntN(next + 1)];
    }

    public int compare(int ipos1, int ipos2) {
        int i1 = posToDataIndex(ipos1);
        int i2 = posToDataIndex(ipos2);
        if (i1 < i2) {
            return -1;
        }
        return i1 > i2 ? 1 : 0;
    }

    protected int getIndexDifference(int ipos1, int ipos0) {
        int i;
        int i0 = posToDataIndex(ipos0);
        int i1 = posToDataIndex(ipos1);
        boolean negate = false;
        if (i0 > i1) {
            negate = true;
            i = i1;
            i1 = i0;
            i0 = i;
        }
        i = 0;
        while (i0 < i1) {
            i0 = nextDataIndex(i0);
            i++;
        }
        return negate ? -i : i;
    }

    public int hashCode() {
        return System.identityHashCode(this);
    }

    public void consume(Consumer out) {
        consumeIRange(0, this.data.length, out);
    }

    public void statistics() {
        PrintWriter out = new PrintWriter(System.out);
        statistics(out);
        out.flush();
    }

    public void statistics(PrintWriter out) {
        out.print("data array length: ");
        out.println(this.data.length);
        out.print("data array gap: ");
        out.println(this.gapEnd - this.gapStart);
        out.print("object array length: ");
        out.println(this.objects.length);
    }

    public void dump() {
        PrintWriter out = new PrintWriter(System.out);
        dump(out);
        out.flush();
    }

    public void dump(PrintWriter out) {
        out.println(getClass().getName() + " @" + Integer.toHexString(System.identityHashCode(this)) + " gapStart:" + this.gapStart + " gapEnd:" + this.gapEnd + " length:" + this.data.length);
        dump(out, 0, this.data.length);
    }

    public void dump(PrintWriter out, int start, int limit) {
        int toskip = 0;
        int i = start;
        while (i < limit) {
            if (i < this.gapStart || i >= this.gapEnd) {
                int ch = this.data[i];
                out.print("" + i + ": 0x" + Integer.toHexString(ch) + '=' + ((short) ch));
                toskip--;
                if (toskip < 0) {
                    if (ch <= MAX_CHAR_SHORT) {
                        if (ch >= 32 && ch < TransportMediator.KEYCODE_MEDIA_PAUSE) {
                            out.print("='" + ((char) ch) + "'");
                        } else if (ch == 10) {
                            out.print("='\\n'");
                        } else {
                            out.print("='\\u" + Integer.toHexString(ch) + "'");
                        }
                    } else if (ch >= OBJECT_REF_SHORT && ch <= 61439) {
                        ch -= OBJECT_REF_SHORT;
                        Object obj = this.objects[ch];
                        out.print("=Object#" + ch + '=' + obj + ':' + obj.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(obj)));
                    } else if (ch >= BEGIN_ELEMENT_SHORT && ch <= 45055) {
                        ch -= BEGIN_ELEMENT_SHORT;
                        out.print("=BEGIN_ELEMENT_SHORT end:" + (this.data[i + 1] + i) + " index#" + ch + "=<" + this.objects[ch] + '>');
                        toskip = 2;
                    } else if (ch < 45056 || ch > 57343) {
                        int j;
                        switch (ch) {
                            case 61696:
                                out.print("= false");
                                break;
                            case 61697:
                                out.print("= true");
                                break;
                            case INT_FOLLOWS /*61698*/:
                                out.print("=INT_FOLLOWS value:" + getIntN(i + 1));
                                toskip = 2;
                                break;
                            case LONG_FOLLOWS /*61699*/:
                                out.print("=LONG_FOLLOWS value:" + getLongN(i + 1));
                                toskip = 4;
                                break;
                            case FLOAT_FOLLOWS /*61700*/:
                                out.write("=FLOAT_FOLLOWS value:" + Float.intBitsToFloat(getIntN(i + 1)));
                                toskip = 2;
                                break;
                            case DOUBLE_FOLLOWS /*61701*/:
                                out.print("=DOUBLE_FOLLOWS value:" + Double.longBitsToDouble(getLongN(i + 1)));
                                toskip = 4;
                                break;
                            case CHAR_FOLLOWS /*61702*/:
                                out.print("=CHAR_FOLLOWS");
                                toskip = 1;
                                break;
                            case BEGIN_ELEMENT_LONG /*61704*/:
                                j = getIntN(i + 1);
                                j += j < 0 ? this.data.length : i;
                                out.print("=BEGIN_ELEMENT_LONG end:");
                                out.print(j);
                                j = getIntN(j + 1);
                                out.print(" -> #");
                                out.print(j);
                                if (j < 0 || j + 1 >= this.objects.length) {
                                    out.print("=<out-of-bounds>");
                                } else {
                                    out.print("=<" + this.objects[j] + '>');
                                }
                                toskip = 2;
                                break;
                            case BEGIN_ATTRIBUTE_LONG /*61705*/:
                                int length;
                                j = getIntN(i + 1);
                                out.print("=BEGIN_ATTRIBUTE name:" + j + "=" + this.objects[j]);
                                j = getIntN(i + 3);
                                if (j < 0) {
                                    length = this.data.length;
                                } else {
                                    length = i;
                                }
                                out.print(" end:" + (j + length));
                                toskip = 4;
                                break;
                            case END_ATTRIBUTE /*61706*/:
                                out.print("=END_ATTRIBUTE");
                                break;
                            case END_ELEMENT_SHORT /*61707*/:
                                out.print("=END_ELEMENT_SHORT begin:");
                                j = i - this.data[i + 1];
                                out.print(j);
                                j = this.data[j] - BEGIN_ELEMENT_SHORT;
                                out.print(" -> #");
                                out.print(j);
                                out.print("=<");
                                out.print(this.objects[j]);
                                out.print('>');
                                toskip = 1;
                                break;
                            case END_ELEMENT_LONG /*61708*/:
                                j = getIntN(i + 1);
                                out.print("=END_ELEMENT_LONG name:" + j + "=<" + this.objects[j] + '>');
                                j = getIntN(i + 3);
                                if (j < 0) {
                                    j += i;
                                }
                                out.print(" begin:" + j);
                                j = getIntN(i + 5);
                                if (j < 0) {
                                    j += i;
                                }
                                out.print(" parent:" + j);
                                toskip = 6;
                                break;
                            case 61709:
                            case 61710:
                                toskip = 2;
                                break;
                            case 61711:
                                out.print("=POSITION_PAIR_FOLLOWS seq:");
                                j = getIntN(i + 1);
                                out.print(j);
                                out.print('=');
                                Object seq = this.objects[j];
                                out.print(seq == null ? null : seq.getClass().getName());
                                out.print('@');
                                if (seq == null) {
                                    out.print("null");
                                } else {
                                    out.print(Integer.toHexString(System.identityHashCode(seq)));
                                }
                                out.print(" ipos:");
                                out.print(getIntN(i + 3));
                                toskip = 4;
                                break;
                            case BEGIN_DOCUMENT /*61712*/:
                                j = getIntN(i + 1);
                                j += j < 0 ? this.data.length : i;
                                out.print("=BEGIN_DOCUMENT end:");
                                out.print(j);
                                out.print(" parent:");
                                out.print(getIntN(i + 3));
                                toskip = 4;
                                break;
                            case END_DOCUMENT /*61713*/:
                                out.print("=END_DOCUMENT");
                                break;
                            case BEGIN_ENTITY /*61714*/:
                                j = getIntN(i + 1);
                                out.print("=BEGIN_ENTITY base:");
                                out.print(j);
                                out.print(" parent:");
                                out.print(getIntN(i + 3));
                                toskip = 4;
                                break;
                            case END_ENTITY /*61715*/:
                                out.print("=END_ENTITY");
                                break;
                            case PROCESSING_INSTRUCTION /*61716*/:
                                out.print("=PROCESSING_INSTRUCTION: ");
                                out.print(this.objects[getIntN(i + 1)]);
                                out.print(" '");
                                j = getIntN(i + 3);
                                out.write(this.data, i + 5, j);
                                out.print('\'');
                                toskip = j + 4;
                                break;
                            case CDATA_SECTION /*61717*/:
                                out.print("=CDATA: '");
                                j = getIntN(i + 1);
                                out.write(this.data, i + 3, j);
                                out.print('\'');
                                toskip = j + 2;
                                break;
                            case JOINER /*61718*/:
                                out.print("= joiner");
                                break;
                            case COMMENT /*61719*/:
                                out.print("=COMMENT: '");
                                j = getIntN(i + 1);
                                out.write(this.data, i + 3, j);
                                out.print('\'');
                                toskip = j + 2;
                                break;
                            case DOCUMENT_URI /*61720*/:
                                out.print("=DOCUMENT_URI: ");
                                out.print(this.objects[getIntN(i + 1)]);
                                toskip = 2;
                                break;
                            default:
                                break;
                        }
                    } else {
                        out.print("= INT_SHORT:" + (ch - INT_SHORT_ZERO));
                    }
                }
                out.println();
                if (true && toskip > 0) {
                    i += toskip;
                    toskip = 0;
                }
            }
            i++;
        }
    }
}

package gnu.text;

import android.support.v4.media.TransportMediator;
import gnu.lists.LList;
import gnu.mapping.ThreadLocation;
import java.io.IOException;
import java.io.Writer;

public class PrettyWriter extends Writer {
    private static final int BLOCK_PER_LINE_PREFIX_END = -3;
    private static final int BLOCK_PREFIX_LENGTH = -4;
    private static final int BLOCK_SECTION_COLUMN = -2;
    private static final int BLOCK_SECTION_START_LINE = -6;
    private static final int BLOCK_START_COLUMN = -1;
    private static final int BLOCK_SUFFIX_LENGTH = -5;
    private static final int LOGICAL_BLOCK_LENGTH = 6;
    public static final int NEWLINE_FILL = 70;
    public static final int NEWLINE_LINEAR = 78;
    public static final int NEWLINE_LITERAL = 76;
    public static final int NEWLINE_MANDATORY = 82;
    public static final int NEWLINE_MISER = 77;
    public static final int NEWLINE_SPACE = 83;
    static final int QITEM_BASE_SIZE = 2;
    static final int QITEM_BLOCK_END_SIZE = 2;
    static final int QITEM_BLOCK_END_TYPE = 5;
    static final int QITEM_BLOCK_START_BLOCK_END = 4;
    static final int QITEM_BLOCK_START_PREFIX = 5;
    static final int QITEM_BLOCK_START_SIZE = 7;
    static final int QITEM_BLOCK_START_SUFFIX = 6;
    static final int QITEM_BLOCK_START_TYPE = 4;
    static final int QITEM_INDENTATION_AMOUNT = 3;
    static final char QITEM_INDENTATION_BLOCK = 'B';
    static final char QITEM_INDENTATION_CURRENT = 'C';
    static final int QITEM_INDENTATION_KIND = 2;
    static final int QITEM_INDENTATION_SIZE = 4;
    static final int QITEM_INDENTATION_TYPE = 3;
    static final int QITEM_NEWLINE_KIND = 4;
    static final int QITEM_NEWLINE_SIZE = 5;
    static final int QITEM_NEWLINE_TYPE = 2;
    static final int QITEM_NOP_TYPE = 0;
    static final int QITEM_POSN = 1;
    static final int QITEM_SECTION_START_DEPTH = 2;
    static final int QITEM_SECTION_START_SECTION_END = 3;
    static final int QITEM_SECTION_START_SIZE = 4;
    static final int QITEM_TAB_COLINC = 4;
    static final int QITEM_TAB_COLNUM = 3;
    static final int QITEM_TAB_FLAGS = 2;
    static final int QITEM_TAB_IS_RELATIVE = 2;
    static final int QITEM_TAB_IS_SECTION = 1;
    static final int QITEM_TAB_SIZE = 5;
    static final int QITEM_TAB_TYPE = 6;
    static final int QITEM_TYPE_AND_SIZE = 0;
    static final int QUEUE_INIT_ALLOC_SIZE = 300;
    public static ThreadLocation indentLoc = new ThreadLocation("indent");
    public static int initialBufferSize = TransportMediator.KEYCODE_MEDIA_PLAY;
    public static ThreadLocation lineLengthLoc = new ThreadLocation("line-length");
    public static ThreadLocation miserWidthLoc = new ThreadLocation("miser-width");
    int blockDepth;
    int[] blocks;
    public char[] buffer;
    public int bufferFillPointer;
    int bufferOffset;
    int bufferStartColumn;
    int currentBlock;
    int lineLength;
    int lineNumber;
    int miserWidth;
    protected Writer out;
    public int pendingBlocksCount;
    char[] prefix;
    int prettyPrintingMode;
    int[] queueInts;
    int queueSize;
    String[] queueStrings;
    int queueTail;
    char[] suffix;
    boolean wordEndSeen;

    public PrettyWriter(Writer out) {
        this.lineLength = 80;
        this.miserWidth = 40;
        this.buffer = new char[initialBufferSize];
        this.blocks = new int[60];
        this.blockDepth = 6;
        this.prefix = new char[initialBufferSize];
        this.suffix = new char[initialBufferSize];
        this.queueInts = new int[QUEUE_INIT_ALLOC_SIZE];
        this.queueStrings = new String[QUEUE_INIT_ALLOC_SIZE];
        this.currentBlock = -1;
        this.out = out;
        this.prettyPrintingMode = 1;
    }

    public PrettyWriter(Writer out, int lineLength) {
        int i = 1;
        this.lineLength = 80;
        this.miserWidth = 40;
        this.buffer = new char[initialBufferSize];
        this.blocks = new int[60];
        this.blockDepth = 6;
        this.prefix = new char[initialBufferSize];
        this.suffix = new char[initialBufferSize];
        this.queueInts = new int[QUEUE_INIT_ALLOC_SIZE];
        this.queueStrings = new String[QUEUE_INIT_ALLOC_SIZE];
        this.currentBlock = -1;
        this.out = out;
        this.lineLength = lineLength;
        if (lineLength <= 1) {
            i = 0;
        }
        this.prettyPrintingMode = i;
    }

    public PrettyWriter(Writer out, boolean prettyPrintingMode) {
        this.lineLength = 80;
        this.miserWidth = 40;
        this.buffer = new char[initialBufferSize];
        this.blocks = new int[60];
        this.blockDepth = 6;
        this.prefix = new char[initialBufferSize];
        this.suffix = new char[initialBufferSize];
        this.queueInts = new int[QUEUE_INIT_ALLOC_SIZE];
        this.queueStrings = new String[QUEUE_INIT_ALLOC_SIZE];
        this.currentBlock = -1;
        this.out = out;
        this.prettyPrintingMode = prettyPrintingMode ? 1 : 0;
    }

    public void setPrettyPrintingMode(int mode) {
        this.prettyPrintingMode = mode;
    }

    public int getPrettyPrintingMode() {
        return this.prettyPrintingMode;
    }

    public boolean isPrettyPrinting() {
        return this.prettyPrintingMode > 0;
    }

    public void setPrettyPrinting(boolean mode) {
        this.prettyPrintingMode = mode ? 0 : 1;
    }

    private int indexPosn(int index) {
        return this.bufferOffset + index;
    }

    private int posnIndex(int posn) {
        return posn - this.bufferOffset;
    }

    private int posnColumn(int posn) {
        return indexColumn(posnIndex(posn));
    }

    private int getQueueType(int index) {
        return this.queueInts[index] & 255;
    }

    private int getQueueSize(int index) {
        return this.queueInts[index] >> 16;
    }

    private int getSectionColumn() {
        return this.blocks[this.blockDepth - 2];
    }

    private int getStartColumn() {
        return this.blocks[this.blockDepth - 1];
    }

    private int getPerLinePrefixEnd() {
        return this.blocks[this.blockDepth - 3];
    }

    private int getPrefixLength() {
        return this.blocks[this.blockDepth - 4];
    }

    private int getSuffixLength() {
        return this.blocks[this.blockDepth - 5];
    }

    private int getSectionStartLine() {
        return this.blocks[this.blockDepth - 6];
    }

    public void writeWordEnd() {
        this.wordEndSeen = true;
    }

    public void writeWordStart() {
        if (this.wordEndSeen) {
            write(32);
        }
        this.wordEndSeen = false;
    }

    public void clearWordEnd() {
        this.wordEndSeen = false;
    }

    public void write(int ch) {
        this.wordEndSeen = false;
        if (ch != 10 || this.prettyPrintingMode <= 0) {
            ensureSpaceInBuffer(1);
            int fillPointer = this.bufferFillPointer;
            this.buffer[fillPointer] = (char) ch;
            this.bufferFillPointer = fillPointer + 1;
            if (ch == 32 && this.prettyPrintingMode > 1 && this.currentBlock < 0) {
                enqueueNewline(83);
                return;
            }
            return;
        }
        enqueueNewline(76);
    }

    public void write(String str) {
        write(str, 0, str.length());
    }

    public void write(String str, int start, int count) {
        this.wordEndSeen = false;
        while (count > 0) {
            int cnt = count;
            int available = ensureSpaceInBuffer(count);
            if (cnt > available) {
                cnt = available;
            }
            count -= cnt;
            int fillPointer = this.bufferFillPointer;
            int start2 = start;
            while (true) {
                cnt--;
                if (cnt < 0) {
                    break;
                }
                int fillPointer2;
                start = start2 + 1;
                char ch = str.charAt(start2);
                if (ch != '\n' || this.prettyPrintingMode <= 0) {
                    fillPointer2 = fillPointer + 1;
                    this.buffer[fillPointer] = ch;
                    if (ch == ' ' && this.prettyPrintingMode > 1 && this.currentBlock < 0) {
                        this.bufferFillPointer = fillPointer2;
                        enqueueNewline(83);
                        fillPointer2 = this.bufferFillPointer;
                    }
                } else {
                    this.bufferFillPointer = fillPointer;
                    enqueueNewline(76);
                    fillPointer2 = this.bufferFillPointer;
                }
                fillPointer = fillPointer2;
                start2 = start;
            }
            this.bufferFillPointer = fillPointer;
            start = start2;
        }
    }

    public void write(char[] str) {
        write(str, 0, str.length);
    }

    public void write(char[] str, int start, int count) {
        this.wordEndSeen = false;
        int end = start + count;
        while (count > 0) {
            int i;
            int start2;
            for (i = start; i < end; i++) {
                if (this.prettyPrintingMode > 0) {
                    int c = str[i];
                    if (c == '\n' || (c == ' ' && this.currentBlock < 0)) {
                        write(str, start, i - start);
                        write(c);
                        start = i + 1;
                        count = end - start;
                        break;
                    }
                }
            }
            while (true) {
                int cnt;
                int available = ensureSpaceInBuffer(count);
                if (available < count) {
                    cnt = available;
                } else {
                    cnt = count;
                }
                int fillPointer = this.bufferFillPointer;
                int newFillPtr = fillPointer + cnt;
                i = fillPointer;
                start2 = start;
                while (i < newFillPtr) {
                    start = start2 + 1;
                    this.buffer[i] = str[start2];
                    i++;
                    start2 = start;
                }
                this.bufferFillPointer = newFillPtr;
                count -= cnt;
                if (count == 0) {
                    break;
                }
                start = start2;
            }
            start = start2;
        }
    }

    private void pushLogicalBlock(int column, int perLineEnd, int prefixLength, int suffixLength, int sectionStartLine) {
        int newLength = this.blockDepth + 6;
        if (newLength >= this.blocks.length) {
            int[] newBlocks = new int[(this.blocks.length * 2)];
            System.arraycopy(this.blocks, 0, newBlocks, 0, this.blockDepth);
            this.blocks = newBlocks;
        }
        this.blockDepth = newLength;
        this.blocks[this.blockDepth - 1] = column;
        this.blocks[this.blockDepth - 2] = column;
        this.blocks[this.blockDepth - 3] = perLineEnd;
        this.blocks[this.blockDepth - 4] = prefixLength;
        this.blocks[this.blockDepth - 5] = suffixLength;
        this.blocks[this.blockDepth - 6] = sectionStartLine;
    }

    void reallyStartLogicalBlock(int column, String prefix, String suffix) {
        int perLineEnd = getPerLinePrefixEnd();
        int prefixLength = getPrefixLength();
        int suffixLength = getSuffixLength();
        pushLogicalBlock(column, perLineEnd, prefixLength, suffixLength, this.lineNumber);
        setIndentation(column);
        if (prefix != null) {
            this.blocks[this.blockDepth - 3] = column;
            int plen = prefix.length();
            prefix.getChars(0, plen, this.suffix, column - plen);
        }
        if (suffix != null) {
            char[] totalSuffix = this.suffix;
            int totalSuffixLen = totalSuffix.length;
            int additional = suffix.length();
            int newSuffixLen = suffixLength + additional;
            if (newSuffixLen > totalSuffixLen) {
                int newTotalSuffixLen = enoughSpace(totalSuffixLen, additional);
                this.suffix = new char[newTotalSuffixLen];
                System.arraycopy(totalSuffix, totalSuffixLen - suffixLength, this.suffix, newTotalSuffixLen - suffixLength, suffixLength);
                totalSuffixLen = newTotalSuffixLen;
            }
            suffix.getChars(0, additional, totalSuffix, totalSuffixLen - newSuffixLen);
            this.blocks[this.blockDepth - 5] = newSuffixLen;
        }
    }

    int enqueueTab(int flags, int colnum, int colinc) {
        int addr = enqueue(6, 5);
        this.queueInts[addr + 2] = flags;
        this.queueInts[addr + 3] = colnum;
        this.queueInts[addr + 4] = colinc;
        return addr;
    }

    private static int enoughSpace(int current, int want) {
        int doubled = current * 2;
        int enough = current + ((want * 5) >> 2);
        return doubled > enough ? doubled : enough;
    }

    public void setIndentation(int column) {
        char[] prefix = this.prefix;
        int prefixLen = prefix.length;
        int current = getPrefixLength();
        int minimum = getPerLinePrefixEnd();
        if (minimum > column) {
            column = minimum;
        }
        if (column > prefixLen) {
            prefix = new char[enoughSpace(prefixLen, column - prefixLen)];
            System.arraycopy(this.prefix, 0, prefix, 0, current);
            this.prefix = prefix;
        }
        if (column > current) {
            for (int i = current; i < column; i++) {
                prefix[i] = ' ';
            }
        }
        this.blocks[this.blockDepth - 4] = column;
    }

    void reallyEndLogicalBlock() {
        int oldIndent = getPrefixLength();
        this.blockDepth -= 6;
        int newIndent = getPrefixLength();
        if (newIndent > oldIndent) {
            for (int i = oldIndent; i < newIndent; i++) {
                this.prefix[i] = ' ';
            }
        }
    }

    public int enqueue(int kind, int size) {
        int oldLength = this.queueInts.length;
        int endAvail = (oldLength - this.queueTail) - this.queueSize;
        if (endAvail > 0 && size > endAvail) {
            enqueue(0, endAvail);
        }
        if (this.queueSize + size > oldLength) {
            int newLength = enoughSpace(oldLength, size);
            int[] newInts = new int[newLength];
            String[] newStrings = new String[newLength];
            int queueHead = (this.queueTail + this.queueSize) - oldLength;
            if (queueHead > 0) {
                System.arraycopy(this.queueInts, 0, newInts, 0, queueHead);
                System.arraycopy(this.queueStrings, 0, newStrings, 0, queueHead);
            }
            int part1Len = oldLength - this.queueTail;
            int deltaLength = newLength - oldLength;
            System.arraycopy(this.queueInts, this.queueTail, newInts, this.queueTail + deltaLength, part1Len);
            System.arraycopy(this.queueStrings, this.queueTail, newStrings, this.queueTail + deltaLength, part1Len);
            this.queueInts = newInts;
            this.queueStrings = newStrings;
            if (this.currentBlock >= this.queueTail) {
                this.currentBlock += deltaLength;
            }
            this.queueTail += deltaLength;
        }
        int addr = this.queueTail + this.queueSize;
        if (addr >= this.queueInts.length) {
            addr -= this.queueInts.length;
        }
        this.queueInts[addr + 0] = (size << 16) | kind;
        if (size > 1) {
            this.queueInts[addr + 1] = indexPosn(this.bufferFillPointer);
        }
        this.queueSize += size;
        return addr;
    }

    public void enqueueNewline(int kind) {
        boolean z;
        this.wordEndSeen = false;
        int depth = this.pendingBlocksCount;
        int newline = enqueue(2, 5);
        this.queueInts[newline + 4] = kind;
        this.queueInts[newline + 2] = this.pendingBlocksCount;
        this.queueInts[newline + 3] = 0;
        int entry = this.queueTail;
        int todo = this.queueSize;
        while (todo > 0) {
            if (entry == this.queueInts.length) {
                entry = 0;
            }
            if (entry == newline) {
                break;
            }
            int type = getQueueType(entry);
            if ((type == 2 || type == 4) && this.queueInts[entry + 3] == 0 && depth <= this.queueInts[entry + 2]) {
                int delta = newline - entry;
                if (delta < 0) {
                    delta += this.queueInts.length;
                }
                this.queueInts[entry + 3] = delta;
            }
            int size = getQueueSize(entry);
            todo -= size;
            entry += size;
        }
        if (kind == 76 || kind == 82) {
            z = true;
        } else {
            z = false;
        }
        maybeOutput(z, false);
    }

    public final void writeBreak(int kind) {
        if (this.prettyPrintingMode > 0) {
            enqueueNewline(kind);
        }
    }

    public int enqueueIndent(char kind, int amount) {
        int result = enqueue(3, 4);
        this.queueInts[result + 2] = kind;
        this.queueInts[result + 3] = amount;
        return result;
    }

    public void addIndentation(int amount, boolean current) {
        if (this.prettyPrintingMode > 0) {
            enqueueIndent(current ? 'C' : QITEM_INDENTATION_BLOCK, amount);
        }
    }

    public void startLogicalBlock(String prefix, boolean perLine, String suffix) {
        if (this.queueSize == 0 && this.bufferFillPointer == 0) {
            Object llen = lineLengthLoc.get(null);
            if (llen == null) {
                this.lineLength = 80;
            } else {
                this.lineLength = Integer.parseInt(llen.toString());
            }
            Boolean mwidth = miserWidthLoc.get(null);
            if (mwidth == null || mwidth == Boolean.FALSE || mwidth == LList.Empty) {
                this.miserWidth = -1;
            } else {
                this.miserWidth = Integer.parseInt(mwidth.toString());
            }
            indentLoc.get(null);
        }
        if (prefix != null) {
            write(prefix);
        }
        if (this.prettyPrintingMode != 0) {
            int start = enqueue(4, 7);
            this.queueInts[start + 2] = this.pendingBlocksCount;
            String[] strArr = this.queueStrings;
            int i = start + 5;
            if (!perLine) {
                prefix = null;
            }
            strArr[i] = prefix;
            this.queueStrings[start + 6] = suffix;
            this.pendingBlocksCount++;
            int outerBlock = this.currentBlock;
            if (outerBlock < 0) {
                outerBlock = 0;
            } else {
                outerBlock -= start;
                if (outerBlock > 0) {
                    outerBlock -= this.queueInts.length;
                }
            }
            this.queueInts[start + 4] = outerBlock;
            this.queueInts[start + 3] = 0;
            this.currentBlock = start;
        }
    }

    public void endLogicalBlock() {
        int end = enqueue(5, 2);
        this.pendingBlocksCount--;
        if (this.currentBlock < 0) {
            int suffixLength = this.blocks[this.blockDepth - 5];
            int suffixPreviousLength = this.blocks[(this.blockDepth - 6) - 5];
            if (suffixLength > suffixPreviousLength) {
                write(this.suffix, this.suffix.length - suffixLength, suffixLength - suffixPreviousLength);
            }
            this.currentBlock = -1;
            return;
        }
        int start = this.currentBlock;
        int outerBlock = this.queueInts[start + 4];
        if (outerBlock == 0) {
            this.currentBlock = -1;
        } else {
            int qtailFromStart = this.queueTail - start;
            if (qtailFromStart > 0) {
                qtailFromStart -= this.queueInts.length;
            }
            if (outerBlock < qtailFromStart) {
                this.currentBlock = -1;
            } else {
                outerBlock += start;
                if (outerBlock < 0) {
                    outerBlock += this.queueInts.length;
                }
                this.currentBlock = outerBlock;
            }
        }
        String suffix = this.queueStrings[start + 6];
        if (suffix != null) {
            write(suffix);
        }
        int endFromStart = end - start;
        if (endFromStart < 0) {
            endFromStart += this.queueInts.length;
        }
        this.queueInts[start + 4] = endFromStart;
    }

    public void endLogicalBlock(String suffix) {
        if (this.prettyPrintingMode > 0) {
            endLogicalBlock();
        } else if (suffix != null) {
            write(suffix);
        }
    }

    int computeTabSize(int tab, int sectionStart, int column) {
        boolean isSection;
        boolean isRelative;
        int origin = 0;
        int flags = this.queueInts[tab + 2];
        if ((flags & 1) != 0) {
            isSection = true;
        } else {
            isSection = false;
        }
        if ((flags & 2) != 0) {
            isRelative = true;
        } else {
            isRelative = false;
        }
        if (isSection) {
            origin = sectionStart;
        }
        int colnum = this.queueInts[tab + 3];
        int colinc = this.queueInts[tab + 4];
        if (isRelative) {
            if (colinc > 1) {
                int rem = (column + colnum) % colinc;
                if (rem != 0) {
                    colinc = rem;
                    colnum += rem;
                }
            }
            return colnum;
        } else if (column <= colnum + origin) {
            return (column + origin) - column;
        } else {
            return colinc - ((column - origin) % colinc);
        }
    }

    int indexColumn(int index) {
        int column = this.bufferStartColumn;
        int sectionStart = getSectionColumn();
        int endPosn = indexPosn(index);
        int op = this.queueTail;
        int todo = this.queueSize;
        while (todo > 0) {
            if (op >= this.queueInts.length) {
                op = 0;
            }
            int type = getQueueType(op);
            if (type != 0) {
                int posn = this.queueInts[op + 1];
                if (posn >= endPosn) {
                    break;
                } else if (type == 6) {
                    column += computeTabSize(op, sectionStart, posnIndex(posn) + column);
                } else if (type == 2 || type == 4) {
                    sectionStart = column + posnIndex(this.queueInts[op + 1]);
                }
            }
            int size = getQueueSize(op);
            todo -= size;
            op += size;
        }
        return column + index;
    }

    void expandTabs(int through) {
        int numInsertions = 0;
        int additional = 0;
        int column = this.bufferStartColumn;
        int sectionStart = getSectionColumn();
        int op = this.queueTail;
        int todo = this.queueSize;
        int blocksUsed = this.pendingBlocksCount * 6;
        while (todo > 0) {
            if (op == this.queueInts.length) {
                op = 0;
            }
            if (op == through) {
                break;
            }
            if (getQueueType(op) == 6) {
                int index = posnIndex(this.queueInts[op + 1]);
                int tabsize = computeTabSize(op, sectionStart, column + index);
                if (tabsize != 0) {
                    if (((numInsertions * 2) + blocksUsed) + 1 >= this.blocks.length) {
                        Object newBlocks = new int[(this.blocks.length * 2)];
                        System.arraycopy(this.blocks, 0, newBlocks, 0, this.blocks.length);
                        this.blocks = newBlocks;
                    }
                    this.blocks[(numInsertions * 2) + blocksUsed] = index;
                    this.blocks[((numInsertions * 2) + blocksUsed) + 1] = tabsize;
                    numInsertions++;
                    additional += tabsize;
                    column += tabsize;
                }
            } else if (op == 2 || op == 4) {
                sectionStart = column + posnIndex(this.queueInts[op + 1]);
            }
            int size = getQueueSize(op);
            todo -= size;
            op += size;
        }
        if (numInsertions > 0) {
            int fillPtr = this.bufferFillPointer;
            int newFillPtr = fillPtr + additional;
            Object buffer = this.buffer;
            Object newBuffer = buffer;
            int end = fillPtr;
            if (newFillPtr > buffer.length) {
                newBuffer = new char[enoughSpace(fillPtr, additional)];
                this.buffer = newBuffer;
            }
            this.bufferFillPointer = newFillPtr;
            this.bufferOffset -= additional;
            int i = numInsertions;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int srcpos = this.blocks[(i * 2) + blocksUsed];
                int amount = this.blocks[((i * 2) + blocksUsed) + 1];
                int dstpos = srcpos + additional;
                System.arraycopy(buffer, srcpos, newBuffer, dstpos, end - srcpos);
                for (int j = dstpos - amount; j < dstpos; j++) {
                    newBuffer[j] = ' ';
                }
                additional -= amount;
                end = srcpos;
            }
            if (newBuffer != buffer) {
                System.arraycopy(buffer, 0, newBuffer, 0, end);
            }
        }
    }

    int ensureSpaceInBuffer(int want) {
        char[] buffer = this.buffer;
        int length = buffer.length;
        int fillPtr = this.bufferFillPointer;
        int available = length - fillPtr;
        if (available > 0) {
            return available;
        }
        if (this.prettyPrintingMode <= 0 || fillPtr <= this.lineLength) {
            int newLength = enoughSpace(length, want);
            char[] newBuffer = new char[newLength];
            this.buffer = newBuffer;
            int i = fillPtr;
            while (true) {
                i--;
                if (i < 0) {
                    return newLength - fillPtr;
                }
                newBuffer[i] = buffer[i];
            }
        } else {
            if (!maybeOutput(false, false)) {
                outputPartialLine();
            }
            return ensureSpaceInBuffer(want);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean maybeOutput(boolean r19, boolean r20) {
        /*
        r18 = this;
        r10 = 0;
    L_0x0001:
        r0 = r18;
        r0 = r0.queueSize;
        r16 = r0;
        if (r16 <= 0) goto L_0x01ca;
    L_0x0009:
        r0 = r18;
        r0 = r0.queueTail;
        r16 = r0;
        r0 = r18;
        r0 = r0.queueInts;
        r17 = r0;
        r0 = r17;
        r0 = r0.length;
        r17 = r0;
        r0 = r16;
        r1 = r17;
        if (r0 < r1) goto L_0x0028;
    L_0x0020:
        r16 = 0;
        r0 = r16;
        r1 = r18;
        r1.queueTail = r0;
    L_0x0028:
        r0 = r18;
        r9 = r0.queueTail;
        r0 = r18;
        r15 = r0.getQueueType(r9);
        switch(r15) {
            case 2: goto L_0x005a;
            case 3: goto L_0x00de;
            case 4: goto L_0x0120;
            case 5: goto L_0x01be;
            case 6: goto L_0x01c3;
            default: goto L_0x0035;
        };
    L_0x0035:
        r0 = r18;
        r0 = r0.queueTail;
        r16 = r0;
        r0 = r18;
        r1 = r16;
        r12 = r0.getQueueSize(r1);
        r0 = r18;
        r0 = r0.queueSize;
        r16 = r0;
        r16 = r16 - r12;
        r0 = r16;
        r1 = r18;
        r1.queueSize = r0;
        r16 = r9 + r12;
        r0 = r16;
        r1 = r18;
        r1.queueTail = r0;
        goto L_0x0001;
    L_0x005a:
        r6 = -1;
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r17 = r9 + 4;
        r16 = r16[r17];
        switch(r16) {
            case 70: goto L_0x0082;
            case 77: goto L_0x007d;
            case 83: goto L_0x009a;
            default: goto L_0x0068;
        };
    L_0x0068:
        r2 = 1;
    L_0x0069:
        if (r2 == 0) goto L_0x0035;
    L_0x006b:
        r10 = 1;
        if (r20 == 0) goto L_0x00d7;
    L_0x006e:
        if (r6 != 0) goto L_0x00d7;
    L_0x0070:
        r18.outputPartialLine();	 Catch:{ IOException -> 0x0074 }
        goto L_0x0035;
    L_0x0074:
        r5 = move-exception;
        r16 = new java.lang.RuntimeException;
        r0 = r16;
        r0.<init>(r5);
        throw r16;
    L_0x007d:
        r2 = r18.isMisering();
        goto L_0x0069;
    L_0x0082:
        r16 = r18.isMisering();
        if (r16 != 0) goto L_0x0098;
    L_0x0088:
        r0 = r18;
        r0 = r0.lineNumber;
        r16 = r0;
        r17 = r18.getSectionStartLine();
        r0 = r16;
        r1 = r17;
        if (r0 <= r1) goto L_0x009a;
    L_0x0098:
        r2 = 1;
        goto L_0x0069;
    L_0x009a:
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r17 = r9 + 3;
        r3 = r16[r17];
        if (r3 != 0) goto L_0x00b3;
    L_0x00a6:
        r3 = -1;
    L_0x00a7:
        r0 = r18;
        r1 = r19;
        r6 = r0.fitsOnLine(r3, r1);
        if (r6 <= 0) goto L_0x00d1;
    L_0x00b1:
        r2 = 0;
        goto L_0x0069;
    L_0x00b3:
        r3 = r3 + r9;
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r0 = r16;
        r0 = r0.length;
        r16 = r0;
        r0 = r16;
        if (r3 < r0) goto L_0x00a7;
    L_0x00c3:
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r0 = r16;
        r0 = r0.length;
        r16 = r0;
        r3 = r3 - r16;
        goto L_0x00a7;
    L_0x00d1:
        if (r6 < 0) goto L_0x00d5;
    L_0x00d3:
        if (r20 == 0) goto L_0x01ca;
    L_0x00d5:
        r2 = 1;
        goto L_0x0069;
    L_0x00d7:
        r0 = r18;
        r0.outputLine(r9);	 Catch:{ IOException -> 0x0074 }
        goto L_0x0035;
    L_0x00de:
        r16 = r18.isMisering();
        if (r16 != 0) goto L_0x0035;
    L_0x00e4:
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r17 = r9 + 2;
        r8 = r16[r17];
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r17 = r9 + 3;
        r7 = r16[r17];
        r16 = 66;
        r0 = r16;
        if (r8 != r0) goto L_0x010b;
    L_0x00fe:
        r16 = r18.getStartColumn();
        r7 = r7 + r16;
    L_0x0104:
        r0 = r18;
        r0.setIndentation(r7);
        goto L_0x0035;
    L_0x010b:
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r17 = r9 + 1;
        r16 = r16[r17];
        r0 = r18;
        r1 = r16;
        r16 = r0.posnColumn(r1);
        r7 = r7 + r16;
        goto L_0x0104;
    L_0x0120:
        r13 = r9;
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r17 = r9 + 3;
        r3 = r16[r17];
        if (r3 <= 0) goto L_0x018a;
    L_0x012d:
        r16 = r3 + r9;
        r0 = r18;
        r0 = r0.queueInts;
        r17 = r0;
        r0 = r17;
        r0 = r0.length;
        r17 = r0;
        r3 = r16 % r17;
    L_0x013c:
        r0 = r18;
        r1 = r19;
        r6 = r0.fitsOnLine(r3, r1);
        if (r6 <= 0) goto L_0x018c;
    L_0x0146:
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r17 = r9 + 4;
        r4 = r16[r17];
        r16 = r4 + r9;
        r0 = r18;
        r0 = r0.queueInts;
        r17 = r0;
        r0 = r17;
        r0 = r0.length;
        r17 = r0;
        r9 = r16 % r17;
        r0 = r18;
        r0.expandTabs(r9);
        r0 = r18;
        r0.queueTail = r9;
        r0 = r18;
        r0 = r0.queueSize;
        r16 = r0;
        r16 = r16 - r4;
        r0 = r16;
        r1 = r18;
        r1.queueSize = r0;
    L_0x0176:
        r0 = r18;
        r0 = r0.currentBlock;
        r16 = r0;
        r0 = r16;
        if (r0 != r13) goto L_0x0035;
    L_0x0180:
        r16 = -1;
        r0 = r16;
        r1 = r18;
        r1.currentBlock = r0;
        goto L_0x0035;
    L_0x018a:
        r3 = -1;
        goto L_0x013c;
    L_0x018c:
        if (r6 < 0) goto L_0x0190;
    L_0x018e:
        if (r20 == 0) goto L_0x01ca;
    L_0x0190:
        r0 = r18;
        r0 = r0.queueStrings;
        r16 = r0;
        r17 = r9 + 5;
        r11 = r16[r17];
        r0 = r18;
        r0 = r0.queueStrings;
        r16 = r0;
        r17 = r9 + 6;
        r14 = r16[r17];
        r0 = r18;
        r0 = r0.queueInts;
        r16 = r0;
        r17 = r9 + 1;
        r16 = r16[r17];
        r0 = r18;
        r1 = r16;
        r16 = r0.posnColumn(r1);
        r0 = r18;
        r1 = r16;
        r0.reallyStartLogicalBlock(r1, r11, r14);
        goto L_0x0176;
    L_0x01be:
        r18.reallyEndLogicalBlock();
        goto L_0x0035;
    L_0x01c3:
        r0 = r18;
        r0.expandTabs(r9);
        goto L_0x0035;
    L_0x01ca:
        return r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.text.PrettyWriter.maybeOutput(boolean, boolean):boolean");
    }

    protected int getMiserWidth() {
        return this.miserWidth;
    }

    boolean isMisering() {
        int mwidth = getMiserWidth();
        return mwidth > 0 && this.lineLength - getStartColumn() <= mwidth;
    }

    int getMaxLines() {
        return -1;
    }

    boolean printReadably() {
        return true;
    }

    int fitsOnLine(int sectionEnd, boolean forceNewlines) {
        int available = this.lineLength;
        if (!printReadably() && getMaxLines() == this.lineNumber) {
            available = (available - 3) - getSuffixLength();
        }
        if (sectionEnd >= 0) {
            if (posnColumn(this.queueInts[sectionEnd + 1]) <= available) {
                return 1;
            }
            return -1;
        } else if (forceNewlines || indexColumn(this.bufferFillPointer) > available) {
            return -1;
        } else {
            return 0;
        }
    }

    public void lineAbbreviationHappened() {
    }

    void outputLine(int newline) throws IOException {
        int amountToPrint;
        char[] buffer = this.buffer;
        boolean isLiteral = this.queueInts[newline + 4] == 76;
        int amountToConsume = posnIndex(this.queueInts[newline + 1]);
        if (isLiteral) {
            amountToPrint = amountToConsume;
        } else {
            int i = amountToConsume;
            do {
                i--;
                if (i < 0) {
                    amountToPrint = 0;
                    break;
                }
            } while (buffer[i] == ' ');
            amountToPrint = i + 1;
        }
        this.out.write(buffer, 0, amountToPrint);
        int lineNumber = this.lineNumber + 1;
        if (!printReadably()) {
            int maxLines = getMaxLines();
            if (maxLines > 0 && lineNumber >= maxLines) {
                this.out.write(" ..");
                int suffixLength = getSuffixLength();
                if (suffixLength != 0) {
                    char[] suffix = this.suffix;
                    this.out.write(suffix, suffix.length - suffixLength, suffixLength);
                }
                lineAbbreviationHappened();
            }
        }
        this.lineNumber = lineNumber;
        this.out.write(10);
        this.bufferStartColumn = 0;
        int fillPtr = this.bufferFillPointer;
        int prefixLen = isLiteral ? getPerLinePrefixEnd() : getPrefixLength();
        int shift = amountToConsume - prefixLen;
        int newFillPtr = fillPtr - shift;
        char[] newBuffer = buffer;
        int bufferLength = buffer.length;
        if (newFillPtr > bufferLength) {
            newBuffer = new char[enoughSpace(bufferLength, newFillPtr - bufferLength)];
            this.buffer = newBuffer;
        }
        System.arraycopy(buffer, amountToConsume, newBuffer, prefixLen, fillPtr - amountToConsume);
        System.arraycopy(this.prefix, 0, newBuffer, 0, prefixLen);
        this.bufferFillPointer = newFillPtr;
        this.bufferOffset += shift;
        if (!isLiteral) {
            this.blocks[this.blockDepth - 2] = prefixLen;
            this.blocks[this.blockDepth - 6] = lineNumber;
        }
    }

    void outputPartialLine() {
        int count;
        int tail = this.queueTail;
        while (this.queueSize > 0 && getQueueType(tail) == 0) {
            int size = getQueueSize(tail);
            this.queueSize -= size;
            tail += size;
            if (tail == this.queueInts.length) {
                tail = 0;
            }
            this.queueTail = tail;
        }
        int fillPtr = this.bufferFillPointer;
        if (this.queueSize > 0) {
            count = posnIndex(this.queueInts[tail + 1]);
        } else {
            count = fillPtr;
        }
        int newFillPtr = fillPtr - count;
        if (count <= 0) {
            throw new Error("outputPartialLine called when nothing can be output.");
        }
        try {
            this.out.write(this.buffer, 0, count);
            this.bufferFillPointer = count;
            this.bufferStartColumn = getColumnNumber();
            System.arraycopy(this.buffer, count, this.buffer, 0, newFillPtr);
            this.bufferFillPointer = newFillPtr;
            this.bufferOffset += count;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void forcePrettyOutput() throws IOException {
        maybeOutput(false, true);
        if (this.bufferFillPointer > 0) {
            outputPartialLine();
        }
        expandTabs(-1);
        this.bufferStartColumn = getColumnNumber();
        this.out.write(this.buffer, 0, this.bufferFillPointer);
        this.bufferFillPointer = 0;
        this.queueSize = 0;
    }

    public void flush() {
        if (this.out != null) {
            try {
                forcePrettyOutput();
                this.out.flush();
            } catch (IOException ex) {
                throw new RuntimeException(ex.toString());
            }
        }
    }

    public void close() throws IOException {
        if (this.out != null) {
            forcePrettyOutput();
            this.out.close();
            this.out = null;
        }
        this.buffer = null;
    }

    public void closeThis() throws IOException {
        if (this.out != null) {
            forcePrettyOutput();
            this.out = null;
        }
        this.buffer = null;
    }

    public int getColumnNumber() {
        int i = this.bufferFillPointer;
        char ch;
        do {
            i--;
            if (i >= 0) {
                ch = this.buffer[i];
                if (ch == '\n') {
                    break;
                }
            } else {
                return this.bufferStartColumn + this.bufferFillPointer;
            }
        } while (ch != '\r');
        return this.bufferFillPointer - (i + 1);
    }

    public void setColumnNumber(int column) {
        this.bufferStartColumn += column - getColumnNumber();
    }

    public void clearBuffer() {
        this.bufferStartColumn = 0;
        this.bufferFillPointer = 0;
        this.lineNumber = 0;
        this.bufferOffset = 0;
        this.blockDepth = 6;
        this.queueTail = 0;
        this.queueSize = 0;
        this.pendingBlocksCount = 0;
    }
}

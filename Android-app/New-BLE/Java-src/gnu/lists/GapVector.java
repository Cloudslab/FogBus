package gnu.lists;

public class GapVector extends AbstractSequence implements Sequence {
    public SimpleVector base;
    public int gapEnd;
    public int gapStart;

    public GapVector(SimpleVector base) {
        this.base = base;
        this.gapStart = 0;
        this.gapEnd = base.size;
    }

    protected GapVector() {
    }

    public int size() {
        return this.base.size - (this.gapEnd - this.gapStart);
    }

    public boolean hasNext(int ipos) {
        int index = ipos >>> 1;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return index < this.base.size;
    }

    public int getNextKind(int ipos) {
        return hasNext(ipos) ? this.base.getElementKind() : 0;
    }

    public Object get(int index) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return this.base.get(index);
    }

    public Object set(int index, Object value) {
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return this.base.set(index, value);
    }

    public void fill(Object value) {
        this.base.fill(this.gapEnd, this.base.size, value);
        this.base.fill(0, this.gapStart, value);
    }

    public void fillPosRange(int fromPos, int toPos, Object value) {
        int limit;
        int i;
        int from = fromPos == -1 ? this.base.size : fromPos >>> 1;
        int to = toPos == -1 ? this.base.size : toPos >>> 1;
        if (this.gapStart < to) {
            limit = this.gapStart;
        } else {
            limit = to;
        }
        for (i = from; i < limit; i++) {
            this.base.setBuffer(i, value);
        }
        for (i = this.gapEnd; i < to; i++) {
            this.base.setBuffer(i, value);
        }
    }

    protected void shiftGap(int newGapStart) {
        int delta = newGapStart - this.gapStart;
        if (delta > 0) {
            this.base.shift(this.gapEnd, this.gapStart, delta);
        } else if (delta < 0) {
            this.base.shift(newGapStart, this.gapEnd + delta, -delta);
        }
        this.gapEnd += delta;
        this.gapStart = newGapStart;
    }

    protected final void gapReserve(int size) {
        gapReserve(this.gapStart, size);
    }

    protected void gapReserve(int where, int needed) {
        int newLength = 16;
        if (needed > this.gapEnd - this.gapStart) {
            int oldLength = this.base.size;
            if (oldLength >= 16) {
                newLength = oldLength * 2;
            }
            int size = oldLength - (this.gapEnd - this.gapStart);
            int minLength = size + needed;
            if (newLength < minLength) {
                newLength = minLength;
            }
            int newGapEnd = (newLength - size) + where;
            this.base.resizeShift(this.gapStart, this.gapEnd, where, newGapEnd);
            this.gapStart = where;
            this.gapEnd = newGapEnd;
        } else if (where != this.gapStart) {
            shiftGap(where);
        }
    }

    public int getSegment(int where, int len) {
        int length = size();
        if (where < 0 || where > length) {
            return -1;
        }
        if (len < 0) {
            len = 0;
        } else if (where + len > length) {
            len = length - where;
        }
        if (where + len <= this.gapStart) {
            return where;
        }
        if (where >= this.gapStart) {
            return where + (this.gapEnd - this.gapStart);
        }
        if (this.gapStart - where > (len >> 1)) {
            shiftGap(where + len);
            return where;
        }
        shiftGap(where);
        return where + (this.gapEnd - this.gapStart);
    }

    protected int addPos(int ipos, Object value) {
        int index = ipos >>> 1;
        if (index >= this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        add(index, value);
        return ((index + 1) << 1) | 1;
    }

    public void add(int index, Object o) {
        gapReserve(index, 1);
        this.base.set(index, o);
        this.gapStart++;
    }

    protected void removePosRange(int ipos0, int ipos1) {
        ipos0 >>>= 1;
        ipos1 >>>= 1;
        if (ipos0 > this.gapEnd) {
            shiftGap((ipos0 - this.gapEnd) + this.gapStart);
        } else if (ipos1 < this.gapStart) {
            shiftGap(ipos1);
        }
        if (ipos0 < this.gapStart) {
            this.base.clearBuffer(ipos0, this.gapStart - ipos0);
            this.gapStart = ipos0;
        }
        if (ipos1 > this.gapEnd) {
            this.base.clearBuffer(this.gapEnd, ipos1 - this.gapEnd);
            this.gapEnd = ipos1;
        }
    }

    public int createPos(int index, boolean isAfter) {
        if (index > this.gapStart) {
            index += this.gapEnd - this.gapStart;
        }
        return (isAfter ? 1 : 0) | (index << 1);
    }

    protected boolean isAfterPos(int ipos) {
        return (ipos & 1) != 0;
    }

    protected int nextIndex(int ipos) {
        int index = ipos == -1 ? this.base.size : ipos >>> 1;
        if (index > this.gapStart) {
            return index - (this.gapEnd - this.gapStart);
        }
        return index;
    }

    public void consumePosRange(int iposStart, int iposEnd, Consumer out) {
        if (!out.ignoring()) {
            int i = iposStart >>> 1;
            int end = iposEnd >>> 1;
            if (i < this.gapStart) {
                consumePosRange(iposStart, (end > this.gapStart ? end : this.gapStart) << 1, out);
            }
            if (end > this.gapEnd) {
                if (i < this.gapEnd) {
                    i = this.gapEnd;
                }
                consumePosRange(i << 1, iposEnd, out);
            }
        }
    }
}

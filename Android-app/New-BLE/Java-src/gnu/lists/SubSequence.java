package gnu.lists;

public class SubSequence extends AbstractSequence implements Sequence {
    AbstractSequence base;
    int ipos0;
    int ipos1;

    public SubSequence(AbstractSequence base, int startPos, int endPos) {
        this.base = base;
        this.ipos0 = startPos;
        this.ipos1 = endPos;
    }

    public SubSequence(AbstractSequence base) {
        this.base = base;
    }

    public Object get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        return this.base.get(this.base.nextIndex(this.ipos0) + index);
    }

    public int size() {
        return this.base.getIndexDifference(this.ipos1, this.ipos0);
    }

    public void removePosRange(int istart, int iend) {
        AbstractSequence abstractSequence = this.base;
        if (istart == 0) {
            istart = this.ipos0;
        } else if (istart == -1) {
            istart = this.ipos1;
        }
        if (iend == -1) {
            iend = this.ipos1;
        } else if (iend == 0) {
            iend = this.ipos0;
        }
        abstractSequence.removePosRange(istart, iend);
    }

    protected boolean isAfterPos(int ipos) {
        return this.base.isAfterPos(ipos);
    }

    public int createPos(int offset, boolean isAfter) {
        return this.base.createRelativePos(this.ipos0, offset, isAfter);
    }

    public int createRelativePos(int pos, int offset, boolean isAfter) {
        return this.base.createRelativePos(pos, offset, isAfter);
    }

    protected int getIndexDifference(int ipos1, int ipos0) {
        return this.base.getIndexDifference(ipos1, ipos0);
    }

    public void releasePos(int ipos) {
        this.base.releasePos(ipos);
    }

    protected int nextIndex(int ipos) {
        return getIndexDifference(ipos, this.ipos0);
    }

    public int compare(int ipos1, int ipos2) {
        return this.base.compare(ipos1, ipos2);
    }

    public Object getPosNext(int ipos) {
        if (this.base.compare(ipos, this.ipos1) >= 0) {
            return eofValue;
        }
        return this.base.getPosNext(ipos);
    }

    public int getNextKind(int ipos) {
        if (this.base.compare(ipos, this.ipos1) >= 0) {
            return 0;
        }
        return this.base.getNextKind(ipos);
    }

    public int startPos() {
        return this.ipos0;
    }

    public int endPos() {
        return this.ipos1;
    }

    public Object getPosPrevious(int ipos) {
        if (this.base.compare(ipos, this.ipos0) <= 0) {
            return eofValue;
        }
        return this.base.getPosPrevious(ipos);
    }

    protected Sequence subSequencePos(int ipos0, int ipos1) {
        return new SubSequence(this.base, ipos0, ipos1);
    }

    public void clear() {
        removePosRange(this.ipos0, this.ipos1);
    }

    public void finalize() {
        this.base.releasePos(this.ipos0);
        this.base.releasePos(this.ipos1);
    }
}

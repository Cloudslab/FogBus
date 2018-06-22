package gnu.lists;

import gnu.kawa.functions.GetNamedPart;
import java.util.Enumeration;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SeqPosition implements ListIterator, Enumeration {
    public int ipos;
    public AbstractSequence sequence;

    public SeqPosition(AbstractSequence seq) {
        this.sequence = seq;
    }

    public SeqPosition(AbstractSequence seq, int offset, boolean isAfter) {
        this.sequence = seq;
        this.ipos = seq.createPos(offset, isAfter);
    }

    public SeqPosition(AbstractSequence seq, int ipos) {
        this.sequence = seq;
        this.ipos = ipos;
    }

    public static SeqPosition make(AbstractSequence seq, int ipos) {
        return new SeqPosition(seq, seq.copyPos(ipos));
    }

    public SeqPosition copy() {
        return new SeqPosition(this.sequence, this.sequence.copyPos(getPos()));
    }

    public final void gotoStart(AbstractSequence seq) {
        setPos(seq, seq.startPos());
    }

    public final void gotoEnd(AbstractSequence seq) {
        setPos(seq, seq.endPos());
    }

    public boolean gotoChildrenStart() {
        int child = this.sequence.firstChildPos(getPos());
        if (child == 0) {
            return false;
        }
        this.ipos = child;
        return true;
    }

    public final boolean hasMoreElements() {
        return hasNext();
    }

    public boolean hasNext() {
        return this.sequence.hasNext(getPos());
    }

    public int getNextKind() {
        return this.sequence.getNextKind(getPos());
    }

    public String getNextTypeName() {
        return this.sequence.getNextTypeName(getPos());
    }

    public Object getNextTypeObject() {
        return this.sequence.getNextTypeObject(getPos());
    }

    public boolean hasPrevious() {
        return this.sequence.hasPrevious(getPos());
    }

    public Object next() {
        Object result = getNext();
        if (result != Sequence.eofValue && gotoNext()) {
            return result;
        }
        throw new NoSuchElementException();
    }

    public boolean gotoNext() {
        int next = this.sequence.nextPos(this.ipos);
        if (next != 0) {
            this.ipos = next;
            return true;
        }
        this.ipos = -1;
        return false;
    }

    public boolean gotoPrevious() {
        int prev = this.sequence.previousPos(this.ipos);
        if (prev != -1) {
            this.ipos = prev;
            return true;
        }
        this.ipos = 0;
        return false;
    }

    public Object previous() {
        Object result = getPrevious();
        if (result != Sequence.eofValue && gotoPrevious()) {
            return result;
        }
        throw new NoSuchElementException();
    }

    public final Object nextElement() {
        return next();
    }

    public Object getNext() {
        return this.sequence.getPosNext(getPos());
    }

    public Object getPrevious() {
        return this.sequence.getPosPrevious(getPos());
    }

    public int nextIndex() {
        return this.sequence.nextIndex(getPos());
    }

    public final int fromEndIndex() {
        return this.sequence.fromEndIndex(getPos());
    }

    public int getContainingSequenceSize() {
        return this.sequence.getContainingSequenceSize(getPos());
    }

    public final int previousIndex() {
        return this.sequence.nextIndex(getPos()) - 1;
    }

    public boolean isAfter() {
        return this.sequence.isAfterPos(getPos());
    }

    public final void set(Object value) {
        if (isAfter()) {
            setPrevious(value);
        } else {
            setNext(value);
        }
    }

    public void setNext(Object value) {
        this.sequence.setPosNext(getPos(), value);
    }

    public void setPrevious(Object value) {
        this.sequence.setPosPrevious(getPos(), value);
    }

    public void remove() {
        this.sequence.removePos(getPos(), isAfter() ? -1 : 1);
    }

    public void add(Object o) {
        setPos(this.sequence.addPos(getPos(), o));
    }

    public int getPos() {
        return this.ipos;
    }

    public void setPos(AbstractSequence seq, int ipos) {
        if (this.sequence != null) {
            this.sequence.releasePos(getPos());
        }
        this.ipos = ipos;
        this.sequence = seq;
    }

    public void setPos(int ipos) {
        if (this.sequence != null) {
            this.sequence.releasePos(getPos());
        }
        this.ipos = ipos;
    }

    public void set(AbstractSequence seq, int index, boolean isAfter) {
        if (this.sequence != null) {
            this.sequence.releasePos(this.ipos);
        }
        this.sequence = seq;
        this.ipos = seq.createPos(index, isAfter);
    }

    public void set(SeqPosition pos) {
        if (this.sequence != null) {
            this.sequence.releasePos(this.ipos);
        }
        this.sequence = pos.sequence;
        pos.ipos = this.sequence.copyPos(pos.ipos);
    }

    public void release() {
        if (this.sequence != null) {
            this.sequence.releasePos(getPos());
            this.sequence = null;
        }
    }

    public void finalize() {
        release();
    }

    public String toString() {
        if (this.sequence == null) {
            return toInfo();
        }
        Object item = this.sequence.getPosNext(this.ipos);
        return GetNamedPart.CAST_METHOD_NAME + nextIndex() + ": " + (item == null ? "(null)" : item.toString());
    }

    public String toInfo() {
        StringBuffer sbuf = new StringBuffer(60);
        sbuf.append('{');
        if (this.sequence == null) {
            sbuf.append("null sequence");
        } else {
            sbuf.append(this.sequence.getClass().getName());
            sbuf.append('@');
            sbuf.append(System.identityHashCode(this.sequence));
        }
        sbuf.append(" ipos: ");
        sbuf.append(this.ipos);
        sbuf.append('}');
        return sbuf.toString();
    }
}

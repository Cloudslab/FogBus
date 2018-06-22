package gnu.xquery.util;

import gnu.kawa.xml.SortedNodes;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.lists.FilterConsumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;

public class RelativeStepFilter extends FilterConsumer implements PositionConsumer {
    char seen;
    SortedNodes snodes;

    public RelativeStepFilter(Consumer base) {
        super(base);
    }

    public void consume(SeqPosition position) {
        writePosition(position.sequence, position.ipos);
    }

    public void writeObject(Object v) {
        if (v instanceof SeqPosition) {
            SeqPosition n = (SeqPosition) v;
            writePosition(n.sequence, n.ipos);
            return;
        }
        super.writeObject(v);
    }

    protected void beforeContent() {
        if (this.seen == 'N') {
            throw new Error("path returns mix of atoms and nodes");
        }
        this.seen = 'A';
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        if (this.seen == 'A') {
            throw new Error("path returns mix of atoms and nodes");
        }
        this.seen = 'N';
        if (this.snodes == null) {
            this.snodes = new SortedNodes();
        }
        this.snodes.writePosition(seq, ipos);
    }

    public void finish() {
        if (this.snodes != null) {
            this.snodes.consume(this.base);
        }
        this.snodes = null;
    }
}

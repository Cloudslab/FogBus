package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class ParentAxis extends TreeScanner {
    public static ParentAxis make(NodePredicate type) {
        ParentAxis axis = new ParentAxis();
        axis.type = type;
        return axis;
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        ipos = seq.parentPos(ipos);
        if (ipos != seq.endPos() && this.type.isInstancePos(seq, ipos)) {
            out.writePosition(seq, ipos);
        }
    }
}

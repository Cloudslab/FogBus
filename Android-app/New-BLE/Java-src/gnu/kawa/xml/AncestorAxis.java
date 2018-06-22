package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class AncestorAxis extends TreeScanner {
    public static AncestorAxis make(NodePredicate type) {
        AncestorAxis axis = new AncestorAxis();
        axis.type = type;
        return axis;
    }

    private static void scan(AbstractSequence seq, int ipos, int end, NodePredicate type, PositionConsumer out) {
        ipos = seq.parentPos(ipos);
        if (ipos != end) {
            scan(seq, ipos, end, type, out);
            if (type.isInstancePos(seq, ipos)) {
                out.writePosition(seq, ipos);
            }
        }
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        scan(seq, ipos, seq.endPos(), this.type, out);
    }
}

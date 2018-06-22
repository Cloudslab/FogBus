package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class AncestorOrSelfAxis extends TreeScanner {
    public static AncestorOrSelfAxis make(NodePredicate type) {
        AncestorOrSelfAxis axis = new AncestorOrSelfAxis();
        axis.type = type;
        return axis;
    }

    private static void scan(AbstractSequence seq, int ipos, int end, NodePredicate type, PositionConsumer out) {
        if (ipos != end) {
            scan(seq, seq.parentPos(ipos), end, type, out);
            if (type.isInstancePos(seq, ipos)) {
                out.writePosition(seq, ipos);
            }
        }
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        scan(seq, ipos, seq.endPos(), this.type, out);
    }
}

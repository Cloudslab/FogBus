package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class PrecedingAxis extends TreeScanner {
    public static PrecedingAxis make(NodePredicate type) {
        PrecedingAxis axis = new PrecedingAxis();
        axis.type = type;
        return axis;
    }

    private static void scan(AbstractSequence seq, int ipos, int end, NodePredicate type, PositionConsumer out) {
        int parent = seq.parentPos(ipos);
        if (parent != end) {
            scan(seq, parent, end, type, out);
            int child = seq.firstChildPos(parent);
            if (child != 0) {
                if (type.isInstancePos(seq, child)) {
                    out.writePosition(seq, child);
                }
                while (true) {
                    child = seq.nextMatching(child, type, ipos, true);
                    if (child != 0) {
                        out.writePosition(seq, child);
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        scan(seq, ipos, seq.endPos(), this.type, out);
    }
}

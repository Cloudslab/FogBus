package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;
import gnu.lists.TreeList;

public class DescendantAxis extends TreeScanner {
    public static DescendantAxis make(NodePredicate type) {
        DescendantAxis axis = new DescendantAxis();
        axis.type = type;
        return axis;
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        if (seq instanceof TreeList) {
            int limit = seq.nextPos(ipos);
            int child = ipos;
            while (true) {
                child = seq.nextMatching(child, this.type, limit, true);
                if (child != 0) {
                    out.writePosition(seq, child);
                } else {
                    return;
                }
            }
        }
        ipos = seq.firstChildPos(ipos);
        while (ipos != 0) {
            if (this.type.isInstancePos(seq, ipos)) {
                out.writePosition(seq, ipos);
            }
            scan(seq, ipos, out);
            ipos = seq.nextPos(ipos);
        }
    }
}

package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;
import gnu.lists.TreeList;

public class DescendantOrSelfAxis extends TreeScanner {
    public static final DescendantOrSelfAxis anyNode = new DescendantOrSelfAxis(NodeType.anyNodeTest);

    private DescendantOrSelfAxis(NodePredicate type) {
        this.type = type;
    }

    public static DescendantOrSelfAxis make(NodePredicate type) {
        if (type == NodeType.anyNodeTest) {
            return anyNode;
        }
        return new DescendantOrSelfAxis(type);
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        if (this.type.isInstancePos(seq, ipos)) {
            out.writePosition(seq, ipos);
        }
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
            scan(seq, ipos, out);
            ipos = seq.nextPos(ipos);
        }
    }
}

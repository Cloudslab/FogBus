package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class FollowingAxis extends TreeScanner {
    public static FollowingAxis make(NodePredicate type) {
        FollowingAxis axis = new FollowingAxis();
        axis.type = type;
        return axis;
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        int limit = seq.endPos();
        ipos = seq.nextPos(ipos);
        if (ipos != 0 && this.type.isInstancePos(seq, ipos)) {
            out.writePosition(seq, ipos);
        }
        while (true) {
            ipos = seq.nextMatching(ipos, this.type, limit, true);
            if (ipos != 0) {
                out.writePosition(seq, ipos);
            } else {
                return;
            }
        }
    }
}

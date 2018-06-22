package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class SelfAxis extends TreeScanner {
    public static SelfAxis make(NodePredicate type) {
        SelfAxis axis = new SelfAxis();
        axis.type = type;
        return axis;
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        if (this.type.isInstancePos(seq, ipos)) {
            out.writePosition(seq, ipos);
        }
    }
}

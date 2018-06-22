package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.PositionConsumer;

public class AttributeAxis extends TreeScanner {
    public static AttributeAxis make(NodePredicate type) {
        AttributeAxis axis = new AttributeAxis();
        axis.type = type;
        return axis;
    }

    public void scan(AbstractSequence seq, int ipos, PositionConsumer out) {
        ipos = seq.firstAttributePos(ipos);
        while (ipos != 0 && seq.getNextKind(ipos) == 35) {
            if (this.type.isInstancePos(seq, ipos)) {
                out.writePosition(seq, ipos);
            } else if (seq.getNextKind(ipos) != 35) {
                return;
            }
            ipos = seq.nextPos(ipos);
        }
    }
}

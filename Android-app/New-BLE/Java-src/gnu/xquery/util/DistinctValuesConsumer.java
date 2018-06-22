package gnu.xquery.util;

import gnu.kawa.xml.KNode;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.lists.FilterConsumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.mapping.Values;
import gnu.xml.NodeTree;

/* compiled from: DistinctValues */
class DistinctValuesConsumer extends FilterConsumer implements PositionConsumer {
    DistinctValuesHashTable table;

    public DistinctValuesConsumer(NamedCollator collator, Consumer out) {
        super(out);
        this.table = new DistinctValuesHashTable(collator);
    }

    public void consume(SeqPosition position) {
        writeObject(position);
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        writeObject(((NodeTree) seq).typedValue(ipos));
    }

    public void writeBoolean(boolean v) {
        writeObject(v ? Boolean.TRUE : Boolean.FALSE);
    }

    public void writeObject(Object value) {
        if (value instanceof Values) {
            Values.writeValues(value, this);
        } else if (value instanceof KNode) {
            KNode node = (KNode) value;
            writeObject(((NodeTree) node.sequence).typedValue(node.ipos));
        } else if (this.table.get(value, null) == null) {
            this.table.put(value, value);
            this.base.writeObject(value);
        }
    }
}

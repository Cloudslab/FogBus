package gnu.xml;

import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.lists.FilterConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.mapping.Symbol;

public class NamedChildrenFilter extends FilterConsumer {
    int level;
    String localName;
    int matchLevel;
    String namespaceURI;

    public static NamedChildrenFilter make(String namespaceURI, String localName, Consumer out) {
        return new NamedChildrenFilter(namespaceURI, localName, out);
    }

    public NamedChildrenFilter(String namespaceURI, String localName, Consumer out) {
        super(out);
        this.namespaceURI = namespaceURI;
        this.localName = localName;
        this.skipping = true;
    }

    public void startDocument() {
        this.level++;
        super.startDocument();
    }

    public void endDocument() {
        this.level--;
        super.endDocument();
    }

    public void startElement(Object type) {
        if (this.skipping && this.level == 1) {
            String curLocalName;
            String curNamespaceURI;
            if (type instanceof Symbol) {
                Symbol qname = (Symbol) type;
                curNamespaceURI = qname.getNamespaceURI();
                curLocalName = qname.getLocalName();
            } else {
                curNamespaceURI = "";
                curLocalName = type.toString().intern();
            }
            if ((this.localName == curLocalName || this.localName == null) && (this.namespaceURI == curNamespaceURI || this.namespaceURI == null)) {
                this.skipping = false;
                this.matchLevel = this.level;
            }
        }
        super.startElement(type);
        this.level++;
    }

    public void endElement() {
        this.level--;
        super.endElement();
        if (!this.skipping && this.matchLevel == this.level) {
            this.skipping = true;
        }
    }

    public void writeObject(Object val) {
        if (val instanceof SeqPosition) {
            SeqPosition pos = (SeqPosition) val;
            if (pos.sequence instanceof TreeList) {
                ((TreeList) pos.sequence).consumeNext(pos.ipos, this);
                return;
            }
        }
        if (val instanceof Consumable) {
            ((Consumable) val).consume(this);
        } else {
            super.writeObject(val);
        }
    }
}

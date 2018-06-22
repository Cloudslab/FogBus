package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

public class KAttr extends KNode implements Attr {
    public KAttr(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    public String getName() {
        return this.sequence.getNextTypeName(this.ipos);
    }

    public short getNodeType() {
        return (short) 2;
    }

    public String getValue() {
        return getNodeValue();
    }

    public static Object getObjectValue(NodeTree sequence, int ipos) {
        return sequence.getPosNext(ipos + 10);
    }

    public Object getObjectValue() {
        return getObjectValue((NodeTree) this.sequence, this.ipos);
    }

    public void setValue(String value) throws DOMException {
        throw new DOMException((short) 7, "setValue not supported");
    }

    public Node getParentNode() {
        return null;
    }

    public Element getOwnerElement() {
        return (Element) super.getParentNode();
    }

    public boolean getSpecified() {
        return true;
    }

    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    public boolean isId() {
        return false;
    }
}

package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.DOMException;
import org.w3c.dom.Text;

public class KText extends KCharacterData implements Text {
    public KText(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    public static KText make(String text) {
        NodeTree tree = new NodeTree();
        tree.append((CharSequence) text);
        return new KText(tree, 0);
    }

    public short getNodeType() {
        return (short) 3;
    }

    public String getNodeName() {
        return "#text";
    }

    public Text splitText(int offset) throws DOMException {
        throw new DOMException((short) 7, "splitText not supported");
    }

    public String getWholeText() {
        throw new UnsupportedOperationException("getWholeText not implemented yet");
    }

    public Text replaceWholeText(String content) throws DOMException {
        throw new DOMException((short) 7, "splitText not supported");
    }

    public boolean hasAttributes() {
        return false;
    }

    public boolean isElementContentWhitespace() {
        return false;
    }
}

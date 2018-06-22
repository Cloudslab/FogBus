package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.DOMException;
import org.w3c.dom.ProcessingInstruction;

public class KProcessingInstruction extends KNode implements ProcessingInstruction {
    public KProcessingInstruction(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    public short getNodeType() {
        return (short) 7;
    }

    public String getNodeName() {
        return getTarget();
    }

    public String getData() {
        return getNodeValue();
    }

    public void setData(String data) throws DOMException {
        throw new DOMException((short) 7, "setData not supported");
    }

    public String getTarget() {
        return ((NodeTree) this.sequence).posTarget(this.ipos);
    }

    public static KProcessingInstruction valueOf(String target, String content) {
        NodeTree tree = new NodeTree();
        tree.writeProcessingInstruction(target, content, 0, content.length());
        return new KProcessingInstruction(tree, 0);
    }
}

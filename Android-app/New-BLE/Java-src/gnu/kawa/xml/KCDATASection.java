package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.CDATASection;

public class KCDATASection extends KText implements CDATASection {
    public KCDATASection(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    public short getNodeType() {
        return (short) 4;
    }

    public String getNodeName() {
        return "#cdata-section";
    }

    public String getData() {
        return getNodeValue();
    }

    public int getLength() {
        StringBuffer sbuf = new StringBuffer();
        NodeTree tlist = this.sequence;
        tlist.stringValue(tlist.posToDataIndex(this.ipos), sbuf);
        return sbuf.length();
    }
}

package gnu.kawa.xml;

import gnu.xml.NodeTree;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public class KDocument extends KNode implements Document {
    public KDocument(NodeTree seq, int ipos) {
        super(seq, ipos);
    }

    public String getNodeName() {
        return "#document";
    }

    public DOMImplementation getImplementation() {
        throw new UnsupportedOperationException("getImplementation not implemented");
    }

    public DocumentType getDoctype() {
        return null;
    }

    public Node getParentNode() {
        return null;
    }

    public KElement getDocumentElement() {
        int child = ((NodeTree) this.sequence).posFirstChild(this.ipos);
        while (child != -1) {
            if (this.sequence.getNextKind(child) != 36) {
                return (KElement) KNode.make((NodeTree) this.sequence, child);
            }
            child = this.sequence.nextPos(child);
        }
        return null;
    }

    public short getNodeType() {
        return (short) 9;
    }

    public String getNodeValue() {
        return null;
    }

    public String getTextContent() {
        return null;
    }

    protected void getTextContent(StringBuffer sbuf) {
    }

    public Element createElement(String tagName) {
        throw new UnsupportedOperationException("createElement not implemented");
    }

    public DocumentFragment createDocumentFragment() {
        throw new UnsupportedOperationException("createDocumentFragment not implemented");
    }

    public Text createTextNode(String data) {
        throw new UnsupportedOperationException("createTextNode not implemented");
    }

    public Comment createComment(String data) {
        throw new UnsupportedOperationException("createComment not implemented");
    }

    public CDATASection createCDATASection(String data) {
        throw new UnsupportedOperationException("createCDATASection not implemented");
    }

    public ProcessingInstruction createProcessingInstruction(String target, String data) {
        throw new UnsupportedOperationException("createProcessingInstruction not implemented");
    }

    public Attr createAttribute(String name) {
        throw new UnsupportedOperationException("createAttribute not implemented");
    }

    public EntityReference createEntityReference(String name) {
        throw new UnsupportedOperationException("createEntityReference implemented");
    }

    public Node importNode(Node importedNode, boolean deep) {
        throw new UnsupportedOperationException("importNode not implemented");
    }

    public Element createElementNS(String namespaceURI, String qualifiedName) {
        throw new UnsupportedOperationException("createElementNS not implemented");
    }

    public Attr createAttributeNS(String namespaceURI, String qualifiedName) {
        throw new UnsupportedOperationException("createAttributeNS not implemented");
    }

    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        throw new UnsupportedOperationException("getElementsByTagNameNS not implemented yet");
    }

    public Element getElementById(String elementId) {
        return null;
    }

    public boolean hasAttributes() {
        return false;
    }

    public String getInputEncoding() {
        return null;
    }

    public String getXmlEncoding() {
        return null;
    }

    public boolean getXmlStandalone() {
        return false;
    }

    public void setXmlStandalone(boolean xmlStandalone) {
    }

    public String getXmlVersion() {
        return "1.1";
    }

    public void setXmlVersion(String xmlVersion) {
    }

    public boolean getStrictErrorChecking() {
        return false;
    }

    public void setStrictErrorChecking(boolean strictErrorChecking) {
    }

    public String getDocumentURI() {
        return null;
    }

    public void setDocumentURI(String documentURI) {
    }

    public Node renameNode(Node n, String namespaceURI, String qualifiedname) throws DOMException {
        throw new DOMException((short) 9, "renameNode not implemented");
    }

    public Node adoptNode(Node source) throws DOMException {
        throw new DOMException((short) 9, "adoptNode not implemented");
    }

    public void normalizeDocument() {
    }

    public DOMConfiguration getDomConfig() {
        throw new DOMException((short) 9, "getDomConfig not implemented");
    }
}

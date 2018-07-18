package gnu.xml;

import android.support.v4.media.TransportMediator;
import gnu.expr.Keyword;
import gnu.kawa.xml.XmlNamespace;
import gnu.lists.AbstractSequence;
import gnu.lists.Consumable;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.UnescapedData;
import gnu.lists.XConsumer;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.ThreadLocation;
import gnu.math.DFloNum;
import gnu.math.RealNum;
import gnu.text.Char;
import gnu.text.Path;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;

public class XMLPrinter extends OutPort implements PositionConsumer, XConsumer {
    private static final int COMMENT = -5;
    private static final int ELEMENT_END = -4;
    private static final int ELEMENT_START = -3;
    static final String HtmlEmptyTags = "/area/base/basefont/br/col/frame/hr/img/input/isindex/link/meta/para/";
    private static final int KEYWORD = -6;
    private static final int PROC_INST = -7;
    private static final int WORD = -2;
    public static final ThreadLocation doctypePublic = new ThreadLocation("doctype-public");
    public static final ThreadLocation doctypeSystem = new ThreadLocation("doctype-system");
    public static final ThreadLocation indentLoc = new ThreadLocation("xml-indent");
    boolean canonicalize;
    public boolean canonicalizeCDATA;
    Object[] elementNameStack;
    int elementNesting;
    public boolean escapeNonAscii;
    public boolean escapeText;
    boolean inAttribute;
    int inComment;
    boolean inDocument;
    boolean inStartTag;
    public boolean indentAttributes;
    boolean isHtml;
    boolean isHtmlOrXhtml;
    NamespaceBinding namespaceBindings;
    NamespaceBinding[] namespaceSaveStack;
    boolean needXMLdecl;
    int prev;
    public int printIndent;
    boolean printXMLdecl;
    char savedHighSurrogate;
    public boolean strict;
    Object style;
    boolean undeclareNamespaces;
    public int useEmptyElementTag;

    public void setPrintXMLdecl(boolean value) {
        this.printXMLdecl = value;
    }

    public XMLPrinter(OutPort out, boolean autoFlush) {
        super(out, autoFlush);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(Writer out, boolean autoFlush) {
        super(out, autoFlush);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(OutputStream out, boolean autoFlush) {
        super(new OutputStreamWriter(out), true, autoFlush);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(Writer out) {
        super(out);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(OutputStream out) {
        super(new OutputStreamWriter(out), false, false);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public XMLPrinter(OutputStream out, Path path) {
        super(new OutputStreamWriter(out), true, false, path);
        this.printIndent = -1;
        this.printXMLdecl = false;
        this.inAttribute = false;
        this.inStartTag = false;
        this.needXMLdecl = false;
        this.canonicalize = true;
        this.useEmptyElementTag = 2;
        this.escapeText = true;
        this.escapeNonAscii = true;
        this.isHtml = false;
        this.isHtmlOrXhtml = false;
        this.undeclareNamespaces = false;
        this.namespaceBindings = NamespaceBinding.predefinedXML;
        this.namespaceSaveStack = new NamespaceBinding[20];
        this.elementNameStack = new Object[20];
        this.prev = 32;
    }

    public static XMLPrinter make(OutPort out, Object style) {
        XMLPrinter xout = new XMLPrinter(out, true);
        xout.setStyle(style);
        return xout;
    }

    public static String toString(Object value) {
        Writer stringWriter = new StringWriter();
        new XMLPrinter(stringWriter).writeObject(value);
        return stringWriter.toString();
    }

    public void setStyle(Object style) {
        this.style = style;
        this.useEmptyElementTag = this.canonicalize ? 0 : 1;
        if ("html".equals(style)) {
            this.isHtml = true;
            this.isHtmlOrXhtml = true;
            this.useEmptyElementTag = 2;
            if (this.namespaceBindings == NamespaceBinding.predefinedXML) {
                this.namespaceBindings = XmlNamespace.HTML_BINDINGS;
            }
        } else if (this.namespaceBindings == XmlNamespace.HTML_BINDINGS) {
            this.namespaceBindings = NamespaceBinding.predefinedXML;
        }
        if ("xhtml".equals(style)) {
            this.isHtmlOrXhtml = true;
            this.useEmptyElementTag = 2;
        }
        if ("plain".equals(style)) {
            this.escapeText = false;
        }
    }

    boolean mustHexEscape(int v) {
        return (v >= TransportMediator.KEYCODE_MEDIA_PAUSE && (v <= 159 || this.escapeNonAscii)) || v == 8232 || (v < 32 && (this.inAttribute || !(v == 9 || v == 10)));
    }

    public void write(int v) {
        closeTag();
        if (this.printIndent >= 0 && (v == 13 || v == 10)) {
            if (!(v == 10 && this.prev == 13)) {
                writeBreak(82);
            }
            if (this.inComment > 0) {
                this.inComment = 1;
            }
        } else if (!this.escapeText) {
            this.bout.write(v);
            this.prev = v;
        } else if (this.inComment > 0) {
            if (v != 45) {
                this.inComment = 1;
            } else if (this.inComment == 1) {
                this.inComment = 2;
            } else {
                this.bout.write(32);
            }
            super.write(v);
        } else {
            this.prev = 59;
            if (v == 60 && (!this.isHtml || !this.inAttribute)) {
                this.bout.write("&lt;");
            } else if (v == 62) {
                this.bout.write("&gt;");
            } else if (v == 38) {
                this.bout.write("&amp;");
            } else if (v == 34 && this.inAttribute) {
                this.bout.write("&quot;");
            } else if (mustHexEscape(v)) {
                int i = v;
                if (v >= 55296) {
                    if (v < 56320) {
                        this.savedHighSurrogate = (char) v;
                        return;
                    } else if (v < 57344) {
                        i = (((this.savedHighSurrogate - 55296) * 1024) + (i - 56320)) + 65536;
                        this.savedHighSurrogate = '\u0000';
                    }
                }
                this.bout.write("&#x" + Integer.toHexString(i).toUpperCase() + ";");
            } else {
                this.bout.write(v);
                this.prev = v;
            }
        }
    }

    private void startWord() {
        closeTag();
        writeWordStart();
    }

    public void writeBoolean(boolean v) {
        startWord();
        super.print(v);
        writeWordEnd();
    }

    protected void startNumber() {
        startWord();
    }

    protected void endNumber() {
        writeWordEnd();
    }

    public void closeTag() {
        if (this.inStartTag && !this.inAttribute) {
            if (this.printIndent >= 0 && this.indentAttributes) {
                endLogicalBlock("");
            }
            this.bout.write(62);
            this.inStartTag = false;
            this.prev = -3;
        } else if (this.needXMLdecl) {
            this.bout.write("<?xml version=\"1.0\"?>\n");
            if (this.printIndent >= 0) {
                startLogicalBlock("", "", 2);
            }
            this.needXMLdecl = false;
            this.prev = 62;
        }
    }

    void setIndentMode() {
        String indent = null;
        Object xmlIndent = indentLoc.get(null);
        if (xmlIndent != null) {
            indent = xmlIndent.toString();
        }
        if (indent == null) {
            this.printIndent = -1;
        } else if (indent.equals("pretty")) {
            this.printIndent = 0;
        } else if (indent.equals("always") || indent.equals("yes")) {
            this.printIndent = 1;
        } else {
            this.printIndent = -1;
        }
    }

    public void startDocument() {
        if (this.printXMLdecl) {
            this.needXMLdecl = true;
        }
        setIndentMode();
        this.inDocument = true;
        if (this.printIndent >= 0 && !this.needXMLdecl) {
            startLogicalBlock("", "", 2);
        }
    }

    public void endDocument() {
        this.inDocument = false;
        if (this.printIndent >= 0) {
            endLogicalBlock("");
        }
        freshLine();
    }

    public void beginEntity(Object base) {
    }

    public void endEntity() {
    }

    protected void writeQName(Object name) {
        if (name instanceof Symbol) {
            Symbol sname = (Symbol) name;
            String prefix = sname.getPrefix();
            if (prefix != null && prefix.length() > 0) {
                this.bout.write(prefix);
                this.bout.write(58);
            }
            this.bout.write(sname.getLocalPart());
            return;
        }
        this.bout.write(name == null ? "{null name}" : (String) name);
    }

    public void startElement(Object type) {
        closeTag();
        if (this.elementNesting == 0) {
            if (!this.inDocument) {
                setIndentMode();
            }
            if (this.prev == -7) {
                write(10);
            }
            Object systemIdentifier = doctypeSystem.get(null);
            if (systemIdentifier != null) {
                String systemId = systemIdentifier.toString();
                if (systemId.length() > 0) {
                    Object publicIdentifier = doctypePublic.get(null);
                    this.bout.write("<!DOCTYPE ");
                    this.bout.write(type.toString());
                    String publicId = publicIdentifier == null ? null : publicIdentifier.toString();
                    if (publicId == null || publicId.length() <= 0) {
                        this.bout.write(" SYSTEM \"");
                    } else {
                        this.bout.write(" PUBLIC \"");
                        this.bout.write(publicId);
                        this.bout.write("\" \"");
                    }
                    this.bout.write(systemId);
                    this.bout.write("\">");
                    println();
                }
            }
        }
        if (this.printIndent >= 0) {
            if (this.prev == -3 || this.prev == -4 || this.prev == -5) {
                writeBreak(this.printIndent > 0 ? 82 : 78);
            }
            startLogicalBlock("", "", 2);
        }
        this.bout.write(60);
        writeQName(type);
        if (this.printIndent >= 0 && this.indentAttributes) {
            startLogicalBlock("", "", 2);
        }
        this.elementNameStack[this.elementNesting] = type;
        NamespaceBinding[] namespaceBindingArr = this.namespaceSaveStack;
        int i = this.elementNesting;
        this.elementNesting = i + 1;
        namespaceBindingArr[i] = this.namespaceBindings;
        if (type instanceof XName) {
            NamespaceBinding ns;
            String uri;
            String prefix;
            NamespaceBinding elementBindings = ((XName) type).namespaceNodes;
            NamespaceBinding join = NamespaceBinding.commonAncestor(elementBindings, this.namespaceBindings);
            NamespaceBinding[] sortedBindings = new NamespaceBinding[(elementBindings == null ? 0 : elementBindings.count(join))];
            int i2 = 0;
            boolean sortNamespaces = this.canonicalize;
            for (ns = elementBindings; ns != join; ns = ns.next) {
                int j = i2;
                uri = ns.getUri();
                prefix = ns.getPrefix();
                while (true) {
                    j--;
                    if (j < 0) {
                        break;
                    }
                    NamespaceBinding ns_j = sortedBindings[j];
                    String prefix_j = ns_j.getPrefix();
                    if (prefix != prefix_j) {
                        if (sortNamespaces) {
                            if (prefix == null || (prefix_j != null && prefix.compareTo(prefix_j) <= 0)) {
                                break;
                            }
                            sortedBindings[j + 1] = ns_j;
                        }
                    } else {
                        break;
                    }
                }
                if (sortNamespaces) {
                    j++;
                } else {
                    j = i2;
                }
                sortedBindings[j] = ns;
                i2++;
            }
            i2 = i2;
            while (true) {
                i2--;
                if (i2 < 0) {
                    break;
                }
                ns = sortedBindings[i2];
                prefix = ns.prefix;
                uri = ns.uri;
                if (uri != this.namespaceBindings.resolve(prefix) && (uri != null || prefix == null || this.undeclareNamespaces)) {
                    this.bout.write(32);
                    if (prefix == null) {
                        this.bout.write("xmlns");
                    } else {
                        this.bout.write("xmlns:");
                        this.bout.write(prefix);
                    }
                    this.bout.write("=\"");
                    this.inAttribute = true;
                    if (uri != null) {
                        write(uri);
                    }
                    this.inAttribute = false;
                    this.bout.write(34);
                }
            }
            if (this.undeclareNamespaces) {
                for (ns = this.namespaceBindings; ns != join; ns = ns.next) {
                    prefix = ns.prefix;
                    if (ns.uri != null && elementBindings.resolve(prefix) == null) {
                        this.bout.write(32);
                        if (prefix == null) {
                            this.bout.write("xmlns");
                        } else {
                            this.bout.write("xmlns:");
                            this.bout.write(prefix);
                        }
                        this.bout.write("=\"\"");
                    }
                }
            }
            this.namespaceBindings = elementBindings;
        }
        if (this.elementNesting >= this.namespaceSaveStack.length) {
            NamespaceBinding[] nstmp = new NamespaceBinding[(this.elementNesting * 2)];
            System.arraycopy(this.namespaceSaveStack, 0, nstmp, 0, this.elementNesting);
            this.namespaceSaveStack = nstmp;
            Object[] nmtmp = new Object[(this.elementNesting * 2)];
            System.arraycopy(this.elementNameStack, 0, nmtmp, 0, this.elementNesting);
            this.elementNameStack = nmtmp;
        }
        this.inStartTag = true;
        String typeName = getHtmlTag(type);
        if ("script".equals(typeName) || "style".equals(typeName)) {
            this.escapeText = false;
        }
    }

    public static boolean isHtmlEmptyElementTag(String name) {
        int index = HtmlEmptyTags.indexOf(name);
        return index > 0 && HtmlEmptyTags.charAt(index - 1) == '/' && HtmlEmptyTags.charAt(name.length() + index) == '/';
    }

    protected String getHtmlTag(Object type) {
        if (type instanceof Symbol) {
            Symbol sym = (Symbol) type;
            String uri = sym.getNamespaceURI();
            if (uri == "http://www.w3.org/1999/xhtml" || (this.isHtmlOrXhtml && uri == "")) {
                return sym.getLocalPart();
            }
        } else if (this.isHtmlOrXhtml) {
            return type.toString();
        }
        return null;
    }

    public void endElement() {
        if (this.useEmptyElementTag == 0) {
            closeTag();
        }
        Symbol type = this.elementNameStack[this.elementNesting - 1];
        String typeName = getHtmlTag(type);
        if (this.inStartTag) {
            boolean isEmpty;
            if (this.printIndent >= 0 && this.indentAttributes) {
                endLogicalBlock("");
            }
            String end = null;
            if (typeName == null || !isHtmlEmptyElementTag(typeName)) {
                isEmpty = false;
            } else {
                isEmpty = true;
            }
            if ((this.useEmptyElementTag == 0 || !(typeName == null || isEmpty)) && (type instanceof Symbol)) {
                Symbol sym = type;
                String prefix = sym.getPrefix();
                String uri = sym.getNamespaceURI();
                String local = sym.getLocalName();
                if (prefix != "") {
                    end = "></" + prefix + ":" + local + ">";
                } else if (uri == "" || uri == null) {
                    end = "></" + local + ">";
                }
            }
            if (end == null) {
                end = (isEmpty && this.isHtml) ? ">" : this.useEmptyElementTag == 2 ? " />" : "/>";
            }
            this.bout.write(end);
            this.inStartTag = false;
        } else {
            if (this.printIndent >= 0) {
                setIndentation(0, false);
                if (this.prev == -4) {
                    writeBreak(this.printIndent > 0 ? 82 : 78);
                }
            }
            this.bout.write("</");
            writeQName(type);
            this.bout.write(">");
        }
        if (this.printIndent >= 0) {
            endLogicalBlock("");
        }
        this.prev = -4;
        if (!(typeName == null || this.escapeText || (!"script".equals(typeName) && !"style".equals(typeName)))) {
            this.escapeText = true;
        }
        NamespaceBinding[] namespaceBindingArr = this.namespaceSaveStack;
        int i = this.elementNesting - 1;
        this.elementNesting = i;
        this.namespaceBindings = namespaceBindingArr[i];
        this.namespaceSaveStack[this.elementNesting] = null;
        this.elementNameStack[this.elementNesting] = null;
    }

    public void startAttribute(Object attrType) {
        if (!this.inStartTag && this.strict) {
            error("attribute not in element", "SENR0001");
        }
        if (this.inAttribute) {
            this.bout.write(34);
        }
        this.inAttribute = true;
        this.bout.write(32);
        if (this.printIndent >= 0) {
            writeBreakFill();
        }
        this.bout.write(attrType.toString());
        this.bout.write("=\"");
        this.prev = 32;
    }

    public void endAttribute() {
        if (this.inAttribute) {
            if (this.prev != -6) {
                this.bout.write(34);
                this.inAttribute = false;
            }
            this.prev = 32;
        }
    }

    public void writeDouble(double d) {
        startWord();
        this.bout.write(formatDouble(d));
    }

    public void writeFloat(float f) {
        startWord();
        this.bout.write(formatFloat(f));
    }

    public static String formatDouble(double d) {
        if (Double.isNaN(d)) {
            return "NaN";
        }
        boolean neg = d < 0.0d;
        if (Double.isInfinite(d)) {
            return neg ? "-INF" : "INF";
        } else {
            double dabs;
            if (neg) {
                dabs = -d;
            } else {
                dabs = d;
            }
            String dstr = Double.toString(d);
            if ((dabs >= 1000000.0d || dabs < 1.0E-6d) && dabs != 0.0d) {
                return RealNum.toStringScientific(dstr);
            }
            return formatDecimal(RealNum.toStringDecimal(dstr));
        }
    }

    public static String formatFloat(float f) {
        if (Float.isNaN(f)) {
            return "NaN";
        }
        boolean neg = f < 0.0f;
        if (Float.isInfinite(f)) {
            return neg ? "-INF" : "INF";
        } else {
            float fabs;
            if (neg) {
                fabs = -f;
            } else {
                fabs = f;
            }
            String fstr = Float.toString(f);
            if ((fabs >= 1000000.0f || ((double) fabs) < 1.0E-6d) && ((double) fabs) != 0.0d) {
                return RealNum.toStringScientific(fstr);
            }
            return formatDecimal(RealNum.toStringDecimal(fstr));
        }
    }

    public static String formatDecimal(BigDecimal dec) {
        return formatDecimal(dec.toPlainString());
    }

    static String formatDecimal(String str) {
        if (str.indexOf(46) < 0) {
            return str;
        }
        int len = str.length();
        int pos = len;
        char ch;
        do {
            pos--;
            ch = str.charAt(pos);
        } while (ch == '0');
        if (ch != '.') {
            pos++;
        }
        if (pos == len) {
            return str;
        }
        return str.substring(0, pos);
    }

    public void print(Object v) {
        String v2;
        if (v instanceof BigDecimal) {
            v2 = formatDecimal((BigDecimal) v);
        } else if ((v instanceof Double) || (v instanceof DFloNum)) {
            v2 = formatDouble(((Number) v).doubleValue());
        } else if (v instanceof Float) {
            v2 = formatFloat(((Float) v).floatValue());
        }
        write(v2 == null ? "(null)" : v2.toString());
    }

    public void writeObject(Object v) {
        if (v instanceof SeqPosition) {
            this.bout.clearWordEnd();
            SeqPosition pos = (SeqPosition) v;
            pos.sequence.consumeNext(pos.ipos, this);
            if (pos.sequence instanceof NodeTree) {
                this.prev = 45;
            }
        } else if ((v instanceof Consumable) && !(v instanceof UnescapedData)) {
            ((Consumable) v).consume(this);
        } else if (v instanceof Keyword) {
            startAttribute(((Keyword) v).getName());
            this.prev = -6;
        } else {
            closeTag();
            if (v instanceof UnescapedData) {
                this.bout.clearWordEnd();
                this.bout.write(((UnescapedData) v).getData());
                this.prev = 45;
            } else if (v instanceof Char) {
                Char.print(((Char) v).intValue(), this);
            } else {
                startWord();
                this.prev = 32;
                print(v);
                writeWordEnd();
                this.prev = -2;
            }
        }
    }

    public boolean ignoring() {
        return false;
    }

    public void write(String str, int start, int length) {
        if (length > 0) {
            closeTag();
            int limit = start + length;
            int count = 0;
            int start2 = start;
            while (start2 < limit) {
                start = start2 + 1;
                char c = str.charAt(start2);
                if (mustHexEscape(c) || (this.inComment <= 0 ? c == '<' || c == '>' || c == '&' || (this.inAttribute && (c == '\"' || c < ' ')) : c == '-' || this.inComment == 2)) {
                    if (count > 0) {
                        this.bout.write(str, (start - 1) - count, count);
                    }
                    write(c);
                    count = 0;
                } else {
                    count++;
                }
                start2 = start;
            }
            if (count > 0) {
                this.bout.write(str, limit - count, count);
            }
            start = start2;
        }
        this.prev = 45;
    }

    public void write(char[] buf, int off, int len) {
        if (len > 0) {
            closeTag();
            int limit = off + len;
            int count = 0;
            int off2 = off;
            while (off2 < limit) {
                off = off2 + 1;
                char c = buf[off2];
                if (mustHexEscape(c) || (this.inComment <= 0 ? c == '<' || c == '>' || c == '&' || (this.inAttribute && (c == '\"' || c < ' ')) : c == '-' || this.inComment == 2)) {
                    if (count > 0) {
                        this.bout.write(buf, (off - 1) - count, count);
                    }
                    write(c);
                    count = 0;
                } else {
                    count++;
                }
                off2 = off;
            }
            if (count > 0) {
                this.bout.write(buf, limit - count, count);
            }
            off = off2;
        }
        this.prev = 45;
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        seq.consumeNext(ipos, this);
    }

    public void writeBaseUri(Object uri) {
    }

    public void beginComment() {
        closeTag();
        if (this.printIndent >= 0 && (this.prev == -3 || this.prev == -4 || this.prev == -5)) {
            writeBreak(this.printIndent > 0 ? 82 : 78);
        }
        this.bout.write("<!--");
        this.inComment = 1;
    }

    public void endComment() {
        this.bout.write("-->");
        this.prev = -5;
        this.inComment = 0;
    }

    public void writeComment(String chars) {
        beginComment();
        write(chars);
        endComment();
    }

    public void writeComment(char[] chars, int offset, int length) {
        beginComment();
        write(chars, offset, length);
        endComment();
    }

    public void writeCDATA(char[] chars, int offset, int length) {
        if (this.canonicalizeCDATA) {
            write(chars, offset, length);
            return;
        }
        closeTag();
        this.bout.write("<![CDATA[");
        int limit = offset + length;
        int i = offset;
        while (i < limit - 2) {
            if (chars[i] == ']' && chars[i + 1] == ']' && chars[i + 2] == '>') {
                if (i > offset) {
                    this.bout.write(chars, offset, i - offset);
                }
                print("]]]><![CDATA[]>");
                offset = i + 3;
                length = limit - offset;
                i += 2;
            }
            i++;
        }
        this.bout.write(chars, offset, length);
        this.bout.write("]]>");
        this.prev = 62;
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        if ("xml".equals(target)) {
            this.needXMLdecl = false;
        }
        closeTag();
        this.bout.write("<?");
        print(target);
        print(' ');
        this.bout.write(content, offset, length);
        this.bout.write("?>");
        this.prev = -7;
    }

    public void consume(SeqPosition position) {
        position.sequence.consumeNext(position.ipos, this);
    }

    public void error(String msg, String code) {
        throw new RuntimeException("serialization error: " + msg + " [" + code + ']');
    }
}

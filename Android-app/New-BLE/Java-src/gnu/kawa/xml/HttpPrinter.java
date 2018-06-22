package gnu.kawa.xml;

import com.google.appinventor.components.runtime.util.NanoHTTPD;
import gnu.lists.Consumable;
import gnu.lists.FilterConsumer;
import gnu.lists.UnescapedData;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.xml.XMLPrinter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class HttpPrinter extends FilterConsumer {
    Object currentHeader;
    private int elementNesting;
    Vector headers = new Vector();
    protected OutputStream ostream;
    protected String sawContentType;
    StringBuilder sbuf = new StringBuilder(100);
    private int seenStartDocument;
    boolean seenXmlHeader;
    OutPort writer;

    public HttpPrinter(OutputStream out) {
        super(null);
        this.ostream = out;
    }

    public HttpPrinter(OutPort out) {
        super(null);
        this.writer = out;
    }

    public static HttpPrinter make(OutPort out) {
        return new HttpPrinter(out);
    }

    private void writeRaw(String str) throws IOException {
        if (this.writer != null) {
            this.writer.write(str);
            return;
        }
        int len = str.length();
        for (int i = 0; i < len; i++) {
            this.ostream.write((byte) str.charAt(i));
        }
    }

    protected void beforeNode() {
        if (this.sawContentType == null) {
            addHeader("Content-type", NanoHTTPD.MIME_XML);
        }
        beginData();
    }

    public void printHeader(String label, String value) throws IOException {
        writeRaw(label);
        writeRaw(": ");
        writeRaw(value);
        writeRaw("\n");
    }

    public void printHeaders() throws IOException {
        int num = this.headers.size();
        for (int i = 0; i < num; i += 2) {
            printHeader(this.headers.elementAt(i).toString(), this.headers.elementAt(i + 1).toString());
        }
        writeRaw("\n");
    }

    public void addHeader(String label, String value) {
        if (label.equalsIgnoreCase("Content-type")) {
            this.sawContentType = value;
        }
        this.headers.addElement(label);
        this.headers.addElement(value);
    }

    public void startAttribute(Object attrType) {
        if (this.base == null) {
            this.currentHeader = attrType;
        } else {
            this.base.startAttribute(attrType);
        }
    }

    public void endAttribute() {
        if (this.currentHeader != null) {
            addHeader(this.currentHeader.toString(), this.sbuf.toString());
            this.sbuf.setLength(0);
            this.currentHeader = null;
            return;
        }
        this.base.endAttribute();
    }

    public void beginData() {
        if (this.base == null) {
            if (this.sawContentType == null) {
                addHeader("Content-type", NanoHTTPD.MIME_PLAINTEXT);
            }
            if (this.writer == null) {
                this.writer = new OutPort(this.ostream);
            }
            String style = null;
            if (NanoHTTPD.MIME_HTML.equalsIgnoreCase(this.sawContentType)) {
                style = "html";
            } else if ("application/xhtml+xml".equalsIgnoreCase(this.sawContentType)) {
                style = "xhtml";
            } else if (NanoHTTPD.MIME_PLAINTEXT.equalsIgnoreCase(this.sawContentType)) {
                style = "plain";
            }
            this.base = XMLPrinter.make(this.writer, style);
            if (this.seenStartDocument == 0) {
                this.base.startDocument();
                this.seenStartDocument = 1;
            }
            try {
                printHeaders();
            } catch (Throwable ex) {
                RuntimeException runtimeException = new RuntimeException(ex.toString());
            }
        }
        append(this.sbuf);
        this.sbuf.setLength(0);
    }

    public void startElement(Object type) {
        if (this.sawContentType == null) {
            String mimeType;
            if (!this.seenXmlHeader) {
                mimeType = NanoHTTPD.MIME_HTML;
            } else if ((type instanceof Symbol) && "html".equals(((Symbol) type).getLocalPart())) {
                mimeType = "text/xhtml";
            } else {
                mimeType = NanoHTTPD.MIME_XML;
            }
            addHeader("Content-type", mimeType);
        }
        beginData();
        this.base.startElement(type);
        this.elementNesting++;
    }

    public void endElement() {
        super.endElement();
        this.elementNesting--;
        if (this.elementNesting == 0 && this.seenStartDocument == 1) {
            endDocument();
        }
    }

    public void writeObject(Object v) {
        if (!(v instanceof Consumable) || (v instanceof UnescapedData)) {
            beginData();
            super.writeObject(v);
            return;
        }
        ((Consumable) v).consume(this);
    }

    public void write(CharSequence str, int start, int length) {
        if (this.base == null) {
            this.sbuf.append(str, start, start + length);
        } else {
            this.base.write(str, start, length);
        }
    }

    public void write(char[] buf, int off, int len) {
        if (this.base == null) {
            this.sbuf.append(buf, off, len);
        } else {
            this.base.write(buf, off, len);
        }
    }

    public void startDocument() {
        if (this.base != null) {
            this.base.startDocument();
        }
        this.seenStartDocument = 2;
    }

    public void endDocument() {
        if (this.base != null) {
            this.base.endDocument();
        }
        try {
            if (this.sawContentType == null) {
                addHeader("Content-type", NanoHTTPD.MIME_PLAINTEXT);
            }
            if (this.sbuf.length() > 0) {
                String str = this.sbuf.toString();
                this.sbuf.setLength(0);
                if (this.writer != null) {
                    this.writer.write(str);
                } else {
                    this.ostream.write(str.getBytes());
                }
            }
            if (this.writer != null) {
                this.writer.close();
            }
            if (this.ostream != null) {
                this.ostream.flush();
            }
        } catch (Throwable th) {
        }
    }

    public boolean reset(boolean headersAlso) {
        if (headersAlso) {
            this.headers.clear();
            this.sawContentType = null;
            this.currentHeader = null;
            this.elementNesting = 0;
        }
        this.sbuf.setLength(0);
        this.base = null;
        boolean ok = true;
        if (this.ostream != null) {
            if (this.writer == null) {
                ok = true;
            } else {
                ok = false;
            }
            this.writer = null;
        }
        return ok;
    }
}

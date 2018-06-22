package gnu.xml;

import android.support.v4.internal.view.SupportMenu;
import gnu.expr.Keyword;
import gnu.lists.AbstractSequence;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.lists.UnescapedData;
import gnu.lists.XConsumer;
import gnu.mapping.Symbol;
import gnu.text.Char;
import gnu.text.LineBufferedReader;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import java.util.List;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class XMLFilter implements DocumentHandler, ContentHandler, SourceLocator, XConsumer, PositionConsumer {
    public static final int COPY_NAMESPACES_INHERIT = 2;
    public static final int COPY_NAMESPACES_PRESERVE = 1;
    private static final int SAW_KEYWORD = 1;
    private static final int SAW_WORD = 2;
    int attrCount = -1;
    String attrLocalName;
    String attrPrefix;
    Consumer base;
    public transient int copyNamespacesMode = 1;
    String currentNamespacePrefix;
    protected int ignoringLevel;
    LineBufferedReader in;
    boolean inStartTag;
    SourceLocator locator;
    MappingInfo[] mappingTable = new MappingInfo[128];
    int mappingTableMask = (this.mappingTable.length - 1);
    private SourceMessages messages;
    boolean mismatchReported;
    NamespaceBinding namespaceBindings;
    public boolean namespacePrefixes = false;
    protected int nesting;
    public Consumer out;
    int previous = 0;
    int[] startIndexes = null;
    protected int stringizingElementNesting = -1;
    protected int stringizingLevel;
    TreeList tlist;
    Object[] workStack;

    public void setSourceLocator(LineBufferedReader in) {
        this.in = in;
        this.locator = this;
    }

    public void setSourceLocator(SourceLocator locator) {
        this.locator = locator;
    }

    public void setMessages(SourceMessages messages) {
        this.messages = messages;
    }

    public NamespaceBinding findNamespaceBinding(String prefix, String uri, NamespaceBinding oldBindings) {
        int hash = uri == null ? 0 : uri.hashCode();
        if (prefix != null) {
            hash ^= prefix.hashCode();
        }
        int bucket = hash & this.mappingTableMask;
        MappingInfo info = this.mappingTable[bucket];
        while (info != null) {
            if (info.tagHash == hash && info.prefix == prefix) {
                NamespaceBinding namespaces = info.namespaces;
                if (namespaces != null && namespaces.getNext() == this.namespaceBindings && namespaces.getPrefix() == prefix && info.uri == uri) {
                    return info.namespaces;
                }
            }
            info = info.nextInBucket;
        }
        info = new MappingInfo();
        info.nextInBucket = this.mappingTable[bucket];
        this.mappingTable[bucket] = info;
        info.tagHash = hash;
        info.prefix = prefix;
        info.local = uri;
        info.uri = uri;
        if (uri == "") {
            uri = null;
        }
        info.namespaces = new NamespaceBinding(prefix, uri, oldBindings);
        return info.namespaces;
    }

    public MappingInfo lookupNamespaceBinding(String prefix, char[] uriChars, int uriStart, int uriLength, int uriHash, NamespaceBinding oldBindings) {
        int hash;
        if (prefix == null) {
            hash = uriHash;
        } else {
            hash = prefix.hashCode() ^ uriHash;
        }
        int bucket = hash & this.mappingTableMask;
        MappingInfo info = this.mappingTable[bucket];
        while (info != null) {
            if (info.tagHash == hash && info.prefix == prefix) {
                NamespaceBinding namespaces = info.namespaces;
                if (namespaces != null && namespaces.getNext() == this.namespaceBindings && namespaces.getPrefix() == prefix && MappingInfo.equals(info.uri, uriChars, uriStart, uriLength)) {
                    return info;
                }
            }
            info = info.nextInBucket;
        }
        info = new MappingInfo();
        info.nextInBucket = this.mappingTable[bucket];
        this.mappingTable[bucket] = info;
        String uri = new String(uriChars, uriStart, uriLength).intern();
        info.tagHash = hash;
        info.prefix = prefix;
        info.local = uri;
        info.uri = uri;
        if (uri == "") {
            uri = null;
        }
        info.namespaces = new NamespaceBinding(prefix, uri, oldBindings);
        return info;
    }

    public void endAttribute() {
        if (this.attrLocalName != null) {
            if (this.previous == 1) {
                this.previous = 0;
                return;
            }
            if (this.stringizingElementNesting >= 0) {
                this.ignoringLevel--;
            }
            int i = this.stringizingLevel - 1;
            this.stringizingLevel = i;
            if (i == 0) {
                char[] data;
                int i2;
                char datum;
                StringBuffer sbuf;
                if (this.attrLocalName == "id" && this.attrPrefix == "xml") {
                    int valStart = this.startIndexes[this.attrCount - 1] + 5;
                    int valEnd = this.tlist.gapStart;
                    data = this.tlist.data;
                    int i3 = valStart;
                    while (i3 < valEnd) {
                        i2 = i3 + 1;
                        datum = data[i3];
                        if ((SupportMenu.USER_MASK & datum) > TreeList.MAX_CHAR_SHORT || datum == '\t' || datum == '\r' || datum == '\n' || (datum == ' ' && (i2 == valEnd || data[i2] == ' '))) {
                            sbuf = new StringBuffer();
                            this.tlist.stringValue(valStart, valEnd, sbuf);
                            this.tlist.gapStart = valStart;
                            this.tlist.write(TextUtils.replaceWhitespace(sbuf.toString(), true));
                            break;
                        }
                        i3 = i2;
                    }
                }
                this.attrLocalName = null;
                this.attrPrefix = null;
                if (this.currentNamespacePrefix == null || this.namespacePrefixes) {
                    this.tlist.endAttribute();
                }
                if (this.currentNamespacePrefix != null) {
                    int attrStart = this.startIndexes[this.attrCount - 1];
                    int uriStart = attrStart;
                    int uriEnd = this.tlist.gapStart;
                    int uriLength = uriEnd - uriStart;
                    data = this.tlist.data;
                    int uriHash = 0;
                    for (i2 = uriStart; i2 < uriEnd; i2++) {
                        datum = data[i2];
                        if ((SupportMenu.USER_MASK & datum) > TreeList.MAX_CHAR_SHORT) {
                            sbuf = new StringBuffer();
                            this.tlist.stringValue(uriStart, uriEnd, sbuf);
                            uriHash = sbuf.hashCode();
                            uriStart = 0;
                            uriLength = sbuf.length();
                            data = new char[sbuf.length()];
                            sbuf.getChars(0, uriLength, data, 0);
                            break;
                        }
                        uriHash = (uriHash * 31) + datum;
                    }
                    this.tlist.gapStart = attrStart;
                    this.namespaceBindings = lookupNamespaceBinding(this.currentNamespacePrefix == "" ? null : this.currentNamespacePrefix, data, uriStart, uriLength, uriHash, this.namespaceBindings).namespaces;
                    this.currentNamespacePrefix = null;
                }
            }
        }
    }

    private String resolve(String prefix, boolean isAttribute) {
        if (isAttribute && prefix == null) {
            return "";
        }
        String uri = this.namespaceBindings.resolve(prefix);
        if (uri != null) {
            return uri;
        }
        if (prefix != null) {
            error('e', "unknown namespace prefix '" + prefix + '\'');
        }
        return "";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void closeStartTag() {
        /*
        r34 = this;
        r0 = r34;
        r0 = r0.attrCount;
        r30 = r0;
        if (r30 < 0) goto L_0x0010;
    L_0x0008:
        r0 = r34;
        r0 = r0.stringizingLevel;
        r30 = r0;
        if (r30 <= 0) goto L_0x0011;
    L_0x0010:
        return;
    L_0x0011:
        r30 = 0;
        r0 = r30;
        r1 = r34;
        r1.inStartTag = r0;
        r30 = 0;
        r0 = r30;
        r1 = r34;
        r1.previous = r0;
        r0 = r34;
        r0 = r0.attrLocalName;
        r30 = r0;
        if (r30 == 0) goto L_0x002c;
    L_0x0029:
        r34.endAttribute();
    L_0x002c:
        r0 = r34;
        r0 = r0.nesting;
        r30 = r0;
        if (r30 != 0) goto L_0x0088;
    L_0x0034:
        r19 = gnu.xml.NamespaceBinding.predefinedXML;
    L_0x0036:
        r0 = r34;
        r3 = r0.namespaceBindings;
        r7 = 0;
    L_0x003b:
        r0 = r34;
        r0 = r0.attrCount;
        r30 = r0;
        r0 = r30;
        if (r7 > r0) goto L_0x014c;
    L_0x0045:
        r0 = r34;
        r0 = r0.workStack;
        r30 = r0;
        r0 = r34;
        r0 = r0.nesting;
        r31 = r0;
        r31 = r31 + r7;
        r31 = r31 + -1;
        r21 = r30[r31];
        r0 = r21;
        r0 = r0 instanceof gnu.mapping.Symbol;
        r30 = r0;
        if (r30 == 0) goto L_0x0085;
    L_0x005f:
        r23 = r21;
        r23 = (gnu.mapping.Symbol) r23;
        r20 = r23.getPrefix();
        r30 = "";
        r0 = r20;
        r1 = r30;
        if (r0 != r1) goto L_0x0071;
    L_0x006f:
        r20 = 0;
    L_0x0071:
        r28 = r23.getNamespaceURI();
        r30 = "";
        r0 = r28;
        r1 = r30;
        if (r0 != r1) goto L_0x007f;
    L_0x007d:
        r28 = 0;
    L_0x007f:
        if (r7 <= 0) goto L_0x009d;
    L_0x0081:
        if (r20 != 0) goto L_0x009d;
    L_0x0083:
        if (r28 != 0) goto L_0x009d;
    L_0x0085:
        r7 = r7 + 1;
        goto L_0x003b;
    L_0x0088:
        r0 = r34;
        r0 = r0.workStack;
        r30 = r0;
        r0 = r34;
        r0 = r0.nesting;
        r31 = r0;
        r31 = r31 + -2;
        r30 = r30[r31];
        r30 = (gnu.xml.NamespaceBinding) r30;
        r19 = r30;
        goto L_0x0036;
    L_0x009d:
        r11 = 0;
        r15 = r3;
    L_0x009f:
        r0 = r19;
        if (r15 != r0) goto L_0x00a4;
    L_0x00a3:
        r11 = 1;
    L_0x00a4:
        if (r15 != 0) goto L_0x00b5;
    L_0x00a6:
        if (r20 != 0) goto L_0x00aa;
    L_0x00a8:
        if (r28 == 0) goto L_0x0085;
    L_0x00aa:
        r0 = r34;
        r1 = r20;
        r2 = r28;
        r3 = r0.findNamespaceBinding(r1, r2, r3);
        goto L_0x0085;
    L_0x00b5:
        r0 = r15.prefix;
        r30 = r0;
        r0 = r30;
        r1 = r20;
        if (r0 != r1) goto L_0x0148;
    L_0x00bf:
        r0 = r15.uri;
        r30 = r0;
        r0 = r30;
        r1 = r28;
        if (r0 == r1) goto L_0x0085;
    L_0x00c9:
        if (r11 == 0) goto L_0x00d6;
    L_0x00cb:
        r0 = r34;
        r1 = r20;
        r2 = r28;
        r3 = r0.findNamespaceBinding(r1, r2, r3);
        goto L_0x0085;
    L_0x00d6:
        r16 = r3;
    L_0x00d8:
        if (r16 != 0) goto L_0x0127;
    L_0x00da:
        r12 = 1;
    L_0x00db:
        r30 = new java.lang.StringBuilder;
        r30.<init>();
        r31 = "_ns_";
        r30 = r30.append(r31);
        r0 = r30;
        r30 = r0.append(r12);
        r30 = r30.toString();
        r14 = r30.intern();
        r30 = r3.resolve(r14);
        if (r30 != 0) goto L_0x0124;
    L_0x00fa:
        r0 = r34;
        r1 = r28;
        r3 = r0.findNamespaceBinding(r14, r1, r3);
        r13 = r23.getLocalName();
        if (r28 != 0) goto L_0x010a;
    L_0x0108:
        r28 = "";
    L_0x010a:
        r0 = r34;
        r0 = r0.workStack;
        r30 = r0;
        r0 = r34;
        r0 = r0.nesting;
        r31 = r0;
        r31 = r31 + r7;
        r31 = r31 + -1;
        r0 = r28;
        r32 = gnu.mapping.Symbol.make(r0, r13, r14);
        r30[r31] = r32;
        goto L_0x0085;
    L_0x0124:
        r12 = r12 + 1;
        goto L_0x00db;
    L_0x0127:
        r0 = r16;
        r0 = r0.uri;
        r30 = r0;
        r0 = r30;
        r1 = r28;
        if (r0 != r1) goto L_0x0141;
    L_0x0133:
        r0 = r16;
        r14 = r0.prefix;
        r30 = r3.resolve(r14);
        r0 = r30;
        r1 = r28;
        if (r0 == r1) goto L_0x00fa;
    L_0x0141:
        r0 = r16;
        r0 = r0.next;
        r16 = r0;
        goto L_0x00d8;
    L_0x0148:
        r15 = r15.next;
        goto L_0x009f;
    L_0x014c:
        r7 = 0;
    L_0x014d:
        r0 = r34;
        r0 = r0.attrCount;
        r30 = r0;
        r0 = r30;
        if (r7 > r0) goto L_0x0458;
    L_0x0157:
        r0 = r34;
        r0 = r0.workStack;
        r30 = r0;
        r0 = r34;
        r0 = r0.nesting;
        r31 = r0;
        r31 = r31 + r7;
        r31 = r31 + -1;
        r21 = r30[r31];
        r10 = 0;
        r0 = r21;
        r0 = r0 instanceof gnu.xml.MappingInfo;
        r30 = r0;
        if (r30 != 0) goto L_0x0184;
    L_0x0172:
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r0 = r34;
        r0 = r0.tlist;
        r31 = r0;
        r0 = r30;
        r1 = r31;
        if (r0 != r1) goto L_0x0335;
    L_0x0184:
        r0 = r21;
        r0 = r0 instanceof gnu.xml.MappingInfo;
        r30 = r0;
        if (r30 == 0) goto L_0x0299;
    L_0x018c:
        r9 = r21;
        r9 = (gnu.xml.MappingInfo) r9;
        r0 = r9.prefix;
        r20 = r0;
        r13 = r9.local;
        if (r7 <= 0) goto L_0x0286;
    L_0x0198:
        if (r20 != 0) goto L_0x01a0;
    L_0x019a:
        r30 = "xmlns";
        r0 = r30;
        if (r13 == r0) goto L_0x01a8;
    L_0x01a0:
        r30 = "xmlns";
        r0 = r20;
        r1 = r30;
        if (r0 != r1) goto L_0x0286;
    L_0x01a8:
        r10 = 1;
        r28 = "(namespace-node)";
    L_0x01ab:
        r6 = r9.tagHash;
        r0 = r34;
        r0 = r0.mappingTableMask;
        r30 = r0;
        r4 = r6 & r30;
        r0 = r34;
        r0 = r0.mappingTable;
        r30 = r0;
        r9 = r30[r4];
        r26 = 0;
    L_0x01bf:
        if (r9 != 0) goto L_0x02b1;
    L_0x01c1:
        r9 = r26;
        r9 = new gnu.xml.MappingInfo;
        r9.<init>();
        r9.tagHash = r6;
        r0 = r20;
        r9.prefix = r0;
        r9.local = r13;
        r0 = r34;
        r0 = r0.mappingTable;
        r30 = r0;
        r30 = r30[r4];
        r0 = r30;
        r9.nextInBucket = r0;
        r0 = r34;
        r0 = r0.mappingTable;
        r30 = r0;
        r30[r4] = r9;
        r0 = r28;
        r9.uri = r0;
        r0 = r28;
        r1 = r20;
        r30 = gnu.mapping.Symbol.make(r0, r13, r1);
        r0 = r30;
        r9.qname = r0;
        if (r7 != 0) goto L_0x020b;
    L_0x01f6:
        r29 = new gnu.xml.XName;
        r0 = r9.qname;
        r30 = r0;
        r0 = r29;
        r1 = r30;
        r0.<init>(r1, r3);
        r27 = r29;
        r0 = r29;
        r9.type = r0;
        r9.namespaces = r3;
    L_0x020b:
        r0 = r34;
        r0 = r0.workStack;
        r30 = r0;
        r0 = r34;
        r0 = r0.nesting;
        r31 = r0;
        r31 = r31 + r7;
        r31 = r31 + -1;
        r30[r31] = r9;
    L_0x021d:
        r12 = 1;
    L_0x021e:
        if (r12 >= r7) goto L_0x0356;
    L_0x0220:
        r0 = r34;
        r0 = r0.workStack;
        r30 = r0;
        r0 = r34;
        r0 = r0.nesting;
        r31 = r0;
        r31 = r31 + r12;
        r31 = r31 + -1;
        r18 = r30[r31];
        r0 = r18;
        r0 = r0 instanceof gnu.mapping.Symbol;
        r30 = r0;
        if (r30 == 0) goto L_0x0344;
    L_0x023a:
        r17 = r18;
        r17 = (gnu.mapping.Symbol) r17;
    L_0x023e:
        r30 = r17.getLocalPart();
        r0 = r30;
        if (r13 != r0) goto L_0x0283;
    L_0x0246:
        r30 = r17.getNamespaceURI();
        r0 = r28;
        r1 = r30;
        if (r0 != r1) goto L_0x0283;
    L_0x0250:
        r0 = r34;
        r0 = r0.workStack;
        r30 = r0;
        r0 = r34;
        r0 = r0.nesting;
        r31 = r0;
        r31 = r31 + -1;
        r25 = r30[r31];
        r0 = r25;
        r0 = r0 instanceof gnu.xml.MappingInfo;
        r30 = r0;
        if (r30 == 0) goto L_0x0270;
    L_0x0268:
        r25 = (gnu.xml.MappingInfo) r25;
        r0 = r25;
        r0 = r0.qname;
        r25 = r0;
    L_0x0270:
        r30 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r0 = r17;
        r1 = r25;
        r31 = duplicateAttributeMessage(r0, r1);
        r0 = r34;
        r1 = r30;
        r2 = r31;
        r0.error(r1, r2);
    L_0x0283:
        r12 = r12 + 1;
        goto L_0x021e;
    L_0x0286:
        if (r7 <= 0) goto L_0x0296;
    L_0x0288:
        r30 = 1;
    L_0x028a:
        r0 = r34;
        r1 = r20;
        r2 = r30;
        r28 = r0.resolve(r1, r2);
        goto L_0x01ab;
    L_0x0296:
        r30 = 0;
        goto L_0x028a;
    L_0x0299:
        r24 = r21;
        r24 = (gnu.mapping.Symbol) r24;
        r0 = r34;
        r1 = r24;
        r9 = r0.lookupTag(r1);
        r0 = r9.prefix;
        r20 = r0;
        r13 = r9.local;
        r28 = r24.getNamespaceURI();
        goto L_0x01ab;
    L_0x02b1:
        r0 = r9.tagHash;
        r30 = r0;
        r0 = r30;
        if (r0 != r6) goto L_0x0318;
    L_0x02b9:
        r0 = r9.local;
        r30 = r0;
        r0 = r30;
        if (r0 != r13) goto L_0x0318;
    L_0x02c1:
        r0 = r9.prefix;
        r30 = r0;
        r0 = r30;
        r1 = r20;
        if (r0 != r1) goto L_0x0318;
    L_0x02cb:
        r0 = r9.uri;
        r30 = r0;
        if (r30 != 0) goto L_0x030e;
    L_0x02d1:
        r0 = r28;
        r9.uri = r0;
        r0 = r28;
        r1 = r20;
        r30 = gnu.mapping.Symbol.make(r0, r13, r1);
        r0 = r30;
        r9.qname = r0;
    L_0x02e1:
        if (r7 != 0) goto L_0x032f;
    L_0x02e3:
        r0 = r9.namespaces;
        r30 = r0;
        r0 = r30;
        if (r0 == r3) goto L_0x02f1;
    L_0x02eb:
        r0 = r9.namespaces;
        r30 = r0;
        if (r30 != 0) goto L_0x0318;
    L_0x02f1:
        r0 = r9.type;
        r27 = r0;
        r9.namespaces = r3;
        if (r27 != 0) goto L_0x020b;
    L_0x02f9:
        r29 = new gnu.xml.XName;
        r0 = r9.qname;
        r30 = r0;
        r0 = r29;
        r1 = r30;
        r0.<init>(r1, r3);
        r27 = r29;
        r0 = r29;
        r9.type = r0;
        goto L_0x020b;
    L_0x030e:
        r0 = r9.uri;
        r30 = r0;
        r0 = r30;
        r1 = r28;
        if (r0 == r1) goto L_0x031c;
    L_0x0318:
        r9 = r9.nextInBucket;
        goto L_0x01bf;
    L_0x031c:
        r0 = r9.qname;
        r30 = r0;
        if (r30 != 0) goto L_0x02e1;
    L_0x0322:
        r0 = r28;
        r1 = r20;
        r30 = gnu.mapping.Symbol.make(r0, r13, r1);
        r0 = r30;
        r9.qname = r0;
        goto L_0x02e1;
    L_0x032f:
        r0 = r9.qname;
        r27 = r0;
        goto L_0x020b;
    L_0x0335:
        r23 = r21;
        r23 = (gnu.mapping.Symbol) r23;
        r28 = r23.getNamespaceURI();
        r13 = r23.getLocalName();
        r9 = 0;
        goto L_0x021d;
    L_0x0344:
        r0 = r18;
        r0 = r0 instanceof gnu.xml.MappingInfo;
        r30 = r0;
        if (r30 == 0) goto L_0x0283;
    L_0x034c:
        r18 = (gnu.xml.MappingInfo) r18;
        r0 = r18;
        r0 = r0.qname;
        r17 = r0;
        goto L_0x023e;
    L_0x0356:
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r0 = r34;
        r0 = r0.tlist;
        r31 = r0;
        r0 = r30;
        r1 = r31;
        if (r0 != r1) goto L_0x03dc;
    L_0x0368:
        if (r7 != 0) goto L_0x03b5;
    L_0x036a:
        r0 = r9.type;
        r27 = r0;
    L_0x036e:
        r8 = r9.index;
        if (r8 <= 0) goto L_0x0386;
    L_0x0372:
        r0 = r34;
        r0 = r0.tlist;
        r30 = r0;
        r0 = r30;
        r0 = r0.objects;
        r30 = r0;
        r30 = r30[r8];
        r0 = r30;
        r1 = r27;
        if (r0 == r1) goto L_0x0396;
    L_0x0386:
        r0 = r34;
        r0 = r0.tlist;
        r30 = r0;
        r0 = r30;
        r1 = r27;
        r8 = r0.find(r1);
        r9.index = r8;
    L_0x0396:
        if (r7 != 0) goto L_0x03ba;
    L_0x0398:
        r0 = r34;
        r0 = r0.tlist;
        r30 = r0;
        r0 = r34;
        r0 = r0.tlist;
        r31 = r0;
        r0 = r31;
        r0 = r0.gapEnd;
        r31 = r0;
        r0 = r30;
        r1 = r31;
        r0.setElementName(r1, r8);
    L_0x03b1:
        r7 = r7 + 1;
        goto L_0x014d;
    L_0x03b5:
        r0 = r9.qname;
        r27 = r0;
        goto L_0x036e;
    L_0x03ba:
        if (r10 == 0) goto L_0x03c4;
    L_0x03bc:
        r0 = r34;
        r0 = r0.namespacePrefixes;
        r30 = r0;
        if (r30 == 0) goto L_0x03b1;
    L_0x03c4:
        r0 = r34;
        r0 = r0.tlist;
        r30 = r0;
        r0 = r34;
        r0 = r0.startIndexes;
        r31 = r0;
        r32 = r7 + -1;
        r31 = r31[r32];
        r0 = r30;
        r1 = r31;
        r0.setAttributeName(r1, r8);
        goto L_0x03b1;
    L_0x03dc:
        if (r9 != 0) goto L_0x03f0;
    L_0x03de:
        r27 = r21;
    L_0x03e0:
        if (r7 != 0) goto L_0x03fc;
    L_0x03e2:
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r0 = r30;
        r1 = r27;
        r0.startElement(r1);
        goto L_0x03b1;
    L_0x03f0:
        if (r7 != 0) goto L_0x03f7;
    L_0x03f2:
        r0 = r9.type;
        r27 = r0;
        goto L_0x03e0;
    L_0x03f7:
        r0 = r9.qname;
        r27 = r0;
        goto L_0x03e0;
    L_0x03fc:
        if (r10 == 0) goto L_0x0406;
    L_0x03fe:
        r0 = r34;
        r0 = r0.namespacePrefixes;
        r30 = r0;
        if (r30 == 0) goto L_0x03b1;
    L_0x0406:
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r0 = r30;
        r1 = r27;
        r0.startAttribute(r1);
        r0 = r34;
        r0 = r0.startIndexes;
        r30 = r0;
        r31 = r7 + -1;
        r22 = r30[r31];
        r0 = r34;
        r0 = r0.attrCount;
        r30 = r0;
        r0 = r30;
        if (r7 >= r0) goto L_0x044d;
    L_0x0427:
        r0 = r34;
        r0 = r0.startIndexes;
        r30 = r0;
        r5 = r30[r7];
    L_0x042f:
        r0 = r34;
        r0 = r0.tlist;
        r30 = r0;
        r31 = r22 + 5;
        r32 = r5 + -1;
        r0 = r34;
        r0 = r0.out;
        r33 = r0;
        r30.consumeIRange(r31, r32, r33);
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r30.endAttribute();
        goto L_0x03b1;
    L_0x044d:
        r0 = r34;
        r0 = r0.tlist;
        r30 = r0;
        r0 = r30;
        r5 = r0.gapStart;
        goto L_0x042f;
    L_0x0458:
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r0 = r30;
        r0 = r0 instanceof gnu.kawa.sax.ContentConsumer;
        r30 = r0;
        if (r30 == 0) goto L_0x0471;
    L_0x0466:
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r30 = (gnu.kawa.sax.ContentConsumer) r30;
        r30.endStartTag();
    L_0x0471:
        r7 = 1;
    L_0x0472:
        r0 = r34;
        r0 = r0.attrCount;
        r30 = r0;
        r0 = r30;
        if (r7 > r0) goto L_0x0493;
    L_0x047c:
        r0 = r34;
        r0 = r0.workStack;
        r30 = r0;
        r0 = r34;
        r0 = r0.nesting;
        r31 = r0;
        r31 = r31 + r7;
        r31 = r31 + -1;
        r32 = 0;
        r30[r31] = r32;
        r7 = r7 + 1;
        goto L_0x0472;
    L_0x0493:
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r0 = r34;
        r0 = r0.tlist;
        r31 = r0;
        r0 = r30;
        r1 = r31;
        if (r0 == r1) goto L_0x04ba;
    L_0x04a5:
        r0 = r34;
        r0 = r0.out;
        r30 = r0;
        r0 = r30;
        r1 = r34;
        r1.base = r0;
        r0 = r34;
        r0 = r0.tlist;
        r30 = r0;
        r30.clear();
    L_0x04ba:
        r30 = -1;
        r0 = r30;
        r1 = r34;
        r1.attrCount = r0;
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLFilter.closeStartTag():void");
    }

    protected boolean checkWriteAtomic() {
        this.previous = 0;
        if (this.ignoringLevel > 0) {
            return false;
        }
        closeStartTag();
        return true;
    }

    public void write(int v) {
        if (checkWriteAtomic()) {
            this.base.write(v);
        }
    }

    public void writeBoolean(boolean v) {
        if (checkWriteAtomic()) {
            this.base.writeBoolean(v);
        }
    }

    public void writeFloat(float v) {
        if (checkWriteAtomic()) {
            this.base.writeFloat(v);
        }
    }

    public void writeDouble(double v) {
        if (checkWriteAtomic()) {
            this.base.writeDouble(v);
        }
    }

    public void writeInt(int v) {
        if (checkWriteAtomic()) {
            this.base.writeInt(v);
        }
    }

    public void writeLong(long v) {
        if (checkWriteAtomic()) {
            this.base.writeLong(v);
        }
    }

    public void writeDocumentUri(Object uri) {
        if (this.nesting == 2 && (this.base instanceof TreeList)) {
            ((TreeList) this.base).writeDocumentUri(uri);
        }
    }

    public void consume(SeqPosition position) {
        writePosition(position.sequence, position.ipos);
    }

    public void writePosition(AbstractSequence seq, int ipos) {
        if (this.ignoringLevel <= 0) {
            if (this.stringizingLevel > 0 && this.previous == 2) {
                if (this.stringizingElementNesting < 0) {
                    write(32);
                }
                this.previous = 0;
            }
            seq.consumeNext(ipos, this);
            if (this.stringizingLevel > 0 && this.stringizingElementNesting < 0) {
                this.previous = 2;
            }
        }
    }

    public void writeObject(Object v) {
        if (this.ignoringLevel <= 0) {
            if (v instanceof SeqPosition) {
                SeqPosition pos = (SeqPosition) v;
                writePosition(pos.sequence, pos.getPos());
            } else if (v instanceof TreeList) {
                ((TreeList) v).consume((Consumer) this);
            } else if ((v instanceof List) && !(v instanceof CharSeq)) {
                int i = 0;
                for (Object writeObject : (List) v) {
                    writeObject(writeObject);
                    i++;
                }
            } else if (v instanceof Keyword) {
                startAttribute(((Keyword) v).asSymbol());
                this.previous = 1;
            } else {
                closeStartTag();
                if (v instanceof UnescapedData) {
                    this.base.writeObject(v);
                    this.previous = 0;
                    return;
                }
                if (this.previous == 2) {
                    write(32);
                }
                TextUtils.textValue(v, this);
                this.previous = 2;
            }
        }
    }

    public XMLFilter(Consumer out) {
        this.base = out;
        this.out = out;
        if (out instanceof NodeTree) {
            this.tlist = (NodeTree) out;
        } else {
            this.tlist = new TreeList();
        }
        this.namespaceBindings = NamespaceBinding.predefinedXML;
    }

    public void write(char[] data, int start, int length) {
        if (length == 0) {
            writeJoiner();
        } else if (checkWriteAtomic()) {
            this.base.write(data, start, length);
        }
    }

    public void write(String str) {
        write((CharSequence) str, 0, str.length());
    }

    public void write(CharSequence str, int start, int length) {
        if (length == 0) {
            writeJoiner();
        } else if (checkWriteAtomic()) {
            this.base.write(str, start, length);
        }
    }

    final boolean inElement() {
        int i = this.nesting;
        while (i > 0 && this.workStack[i - 1] == null) {
            i -= 2;
        }
        return i != 0;
    }

    public void linefeedFromParser() {
        if (inElement() && checkWriteAtomic()) {
            this.base.write(10);
        }
    }

    public void textFromParser(char[] data, int start, int length) {
        if (!inElement()) {
            int i = 0;
            while (i != length) {
                if (Character.isWhitespace(data[start + i])) {
                    i++;
                } else {
                    error('e', "text at document level");
                    return;
                }
            }
        } else if (length > 0 && checkWriteAtomic()) {
            this.base.write(data, start, length);
        }
    }

    protected void writeJoiner() {
        this.previous = 0;
        if (this.ignoringLevel == 0) {
            ((TreeList) this.base).writeJoiner();
        }
    }

    public void writeCDATA(char[] data, int start, int length) {
        if (!checkWriteAtomic()) {
            return;
        }
        if (this.base instanceof XConsumer) {
            ((XConsumer) this.base).writeCDATA(data, start, length);
        } else {
            write(data, start, length);
        }
    }

    protected void startElementCommon() {
        closeStartTag();
        if (this.stringizingLevel == 0) {
            ensureSpaceInWorkStack(this.nesting);
            this.workStack[this.nesting] = this.namespaceBindings;
            this.tlist.startElement(0);
            this.base = this.tlist;
            this.attrCount = 0;
        } else {
            if (this.previous == 2 && this.stringizingElementNesting < 0) {
                write(32);
            }
            this.previous = 0;
            if (this.stringizingElementNesting < 0) {
                this.stringizingElementNesting = this.nesting;
            }
        }
        this.nesting += 2;
    }

    public void emitStartElement(char[] data, int start, int count) {
        closeStartTag();
        MappingInfo info = lookupTag(data, start, count);
        startElementCommon();
        ensureSpaceInWorkStack(this.nesting - 1);
        this.workStack[this.nesting - 1] = info;
    }

    public void startElement(Object type) {
        startElementCommon();
        if (this.stringizingLevel == 0) {
            ensureSpaceInWorkStack(this.nesting - 1);
            this.workStack[this.nesting - 1] = type;
            if (this.copyNamespacesMode == 0) {
                this.namespaceBindings = NamespaceBinding.predefinedXML;
            } else if (this.copyNamespacesMode == 1 || this.nesting == 2) {
                this.namespaceBindings = type instanceof XName ? ((XName) type).getNamespaceNodes() : NamespaceBinding.predefinedXML;
            } else {
                NamespaceBinding inherited;
                for (int i = 2; i != this.nesting; i += 2) {
                    if (this.workStack[i + 1] != null) {
                        inherited = (NamespaceBinding) this.workStack[i];
                        break;
                    }
                }
                inherited = null;
                if (inherited == null) {
                    this.namespaceBindings = type instanceof XName ? ((XName) type).getNamespaceNodes() : NamespaceBinding.predefinedXML;
                } else if (this.copyNamespacesMode == 2) {
                    this.namespaceBindings = inherited;
                } else if (type instanceof XName) {
                    NamespaceBinding preserved = ((XName) type).getNamespaceNodes();
                    if (NamespaceBinding.commonAncestor(inherited, preserved) == inherited) {
                        this.namespaceBindings = preserved;
                    } else {
                        this.namespaceBindings = mergeHelper(inherited, preserved);
                    }
                } else {
                    this.namespaceBindings = inherited;
                }
            }
        }
    }

    private NamespaceBinding mergeHelper(NamespaceBinding list, NamespaceBinding node) {
        if (node == NamespaceBinding.predefinedXML) {
            return list;
        }
        list = mergeHelper(list, node.next);
        String uri = node.uri;
        if (list == null) {
            if (uri == null) {
                return list;
            }
            list = NamespaceBinding.predefinedXML;
        }
        String prefix = node.prefix;
        String found = list.resolve(prefix);
        if (found != null ? found.equals(uri) : uri == null) {
            return list;
        }
        return findNamespaceBinding(prefix, uri, list);
    }

    private boolean startAttributeCommon() {
        if (this.stringizingElementNesting >= 0) {
            this.ignoringLevel++;
        }
        int i = this.stringizingLevel;
        this.stringizingLevel = i + 1;
        if (i > 0) {
            return false;
        }
        if (this.attrCount < 0) {
            this.attrCount = 0;
        }
        ensureSpaceInWorkStack(this.nesting + this.attrCount);
        ensureSpaceInStartIndexes(this.attrCount);
        this.startIndexes[this.attrCount] = this.tlist.gapStart;
        this.attrCount++;
        return true;
    }

    public void startAttribute(Object attrType) {
        this.previous = 0;
        if (attrType instanceof Symbol) {
            Symbol sym = (Symbol) attrType;
            String local = sym.getLocalPart();
            this.attrLocalName = local;
            this.attrPrefix = sym.getPrefix();
            String uri = sym.getNamespaceURI();
            if (uri == "http://www.w3.org/2000/xmlns/" || (uri == "" && local == "xmlns")) {
                error('e', "arttribute name cannot be 'xmlns' or in xmlns namespace");
            }
        }
        if (this.nesting == 2 && this.workStack[1] == null) {
            error('e', "attribute not allowed at document level");
        }
        if (this.attrCount < 0 && this.nesting > 0) {
            error('e', "attribute '" + attrType + "' follows non-attribute content");
        }
        if (startAttributeCommon()) {
            this.workStack[(this.nesting + this.attrCount) - 1] = attrType;
            if (this.nesting == 0) {
                this.base.startAttribute(attrType);
            } else {
                this.tlist.startAttribute(0);
            }
        }
    }

    public void emitStartAttribute(char[] data, int start, int count) {
        if (this.attrLocalName != null) {
            endAttribute();
        }
        if (startAttributeCommon()) {
            MappingInfo info = lookupTag(data, start, count);
            this.workStack[(this.nesting + this.attrCount) - 1] = info;
            String prefix = info.prefix;
            String local = info.local;
            this.attrLocalName = local;
            this.attrPrefix = prefix;
            if (prefix != null) {
                if (prefix == "xmlns") {
                    this.currentNamespacePrefix = local;
                }
            } else if (local == "xmlns" && prefix == null) {
                this.currentNamespacePrefix = "";
            }
            if (this.currentNamespacePrefix == null || this.namespacePrefixes) {
                this.tlist.startAttribute(0);
            }
        }
    }

    public void emitEndAttributes() {
        if (this.attrLocalName != null) {
            endAttribute();
        }
        closeStartTag();
    }

    public void emitEndElement(char[] data, int start, int length) {
        if (this.attrLocalName != null) {
            error('e', "unclosed attribute");
            endAttribute();
        }
        if (inElement()) {
            if (data != null) {
                MappingInfo info = lookupTag(data, start, length);
                MappingInfo old = this.workStack[this.nesting - 1];
                if ((old instanceof MappingInfo) && !this.mismatchReported) {
                    MappingInfo mold = old;
                    if (!(info.local == mold.local && info.prefix == mold.prefix)) {
                        StringBuffer sbuf = new StringBuffer("</");
                        sbuf.append(data, start, length);
                        sbuf.append("> matching <");
                        String oldPrefix = mold.prefix;
                        if (oldPrefix != null) {
                            sbuf.append(oldPrefix);
                            sbuf.append(':');
                        }
                        sbuf.append(mold.local);
                        sbuf.append('>');
                        error('e', sbuf.toString());
                        this.mismatchReported = true;
                    }
                }
            }
            closeStartTag();
            if (this.nesting > 0) {
                endElement();
                return;
            }
            return;
        }
        error('e', "unmatched end element");
    }

    public void endElement() {
        closeStartTag();
        this.nesting -= 2;
        this.previous = 0;
        if (this.stringizingLevel == 0) {
            this.namespaceBindings = (NamespaceBinding) this.workStack[this.nesting];
            this.workStack[this.nesting] = null;
            this.workStack[this.nesting + 1] = null;
            this.base.endElement();
        } else if (this.stringizingElementNesting == this.nesting) {
            this.stringizingElementNesting = -1;
            this.previous = 2;
        }
    }

    public void emitEntityReference(char[] name, int start, int length) {
        char c0 = name[start];
        int ch = '?';
        if (length == 2 && name[start + 1] == 't') {
            if (c0 == 'l') {
                ch = '<';
            } else if (c0 == 'g') {
                ch = '>';
            }
        } else if (length == 3) {
            if (c0 == 'a' && name[start + 1] == 'm' && name[start + 2] == 'p') {
                ch = '&';
            }
        } else if (length == 4) {
            char c1 = name[start + 1];
            char c2 = name[start + 2];
            char c3 = name[start + 3];
            if (c0 == 'q' && c1 == 'u' && c2 == 'o' && c3 == 't') {
                ch = '\"';
            } else if (c0 == 'a' && c1 == 'p' && c2 == 'o' && c3 == 's') {
                ch = '\'';
            }
        }
        write(ch);
    }

    public void emitCharacterReference(int value, char[] name, int start, int length) {
        if (value >= 65536) {
            Char.print(value, this);
        } else {
            write(value);
        }
    }

    protected void checkValidComment(char[] chars, int offset, int length) {
        int i = length;
        boolean sawHyphen = true;
        while (true) {
            i--;
            if (i >= 0) {
                boolean curHyphen = chars[offset + i] == '-';
                if (sawHyphen && curHyphen) {
                    error('e', "consecutive or final hyphen in XML comment");
                    return;
                }
                sawHyphen = curHyphen;
            } else {
                return;
            }
        }
    }

    public void writeComment(char[] chars, int start, int length) {
        checkValidComment(chars, start, length);
        commentFromParser(chars, start, length);
    }

    public void commentFromParser(char[] chars, int start, int length) {
        if (this.stringizingLevel == 0) {
            closeStartTag();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeComment(chars, start, length);
            }
        } else if (this.stringizingElementNesting < 0) {
            this.base.write(chars, start, length);
        }
    }

    public void writeProcessingInstruction(String target, char[] content, int offset, int length) {
        target = TextUtils.replaceWhitespace(target, true);
        int i = offset + length;
        while (true) {
            i--;
            if (i < offset) {
                break;
            }
            char ch = content[i];
            while (ch == '>') {
                i--;
                if (i < offset) {
                    break;
                }
                ch = content[i];
                if (ch == '?') {
                    error('e', "'?>' is not allowed in a processing-instruction");
                    break;
                }
            }
        }
        if ("xml".equalsIgnoreCase(target)) {
            error('e', "processing-instruction target may not be 'xml' (ignoring case)");
        }
        if (!XName.isNCName(target)) {
            error('e', "processing-instruction target '" + target + "' is not a valid Name");
        }
        processingInstructionCommon(target, content, offset, length);
    }

    void processingInstructionCommon(String target, char[] content, int offset, int length) {
        if (this.stringizingLevel == 0) {
            closeStartTag();
            if (this.base instanceof XConsumer) {
                ((XConsumer) this.base).writeProcessingInstruction(target, content, offset, length);
            }
        } else if (this.stringizingElementNesting < 0) {
            this.base.write(content, offset, length);
        }
    }

    public void processingInstructionFromParser(char[] buffer, int tstart, int tlength, int dstart, int dlength) {
        if (tlength != 3 || inElement() || buffer[tstart] != 'x' || buffer[tstart + 1] != 'm' || buffer[tstart + 2] != 'l') {
            processingInstructionCommon(new String(buffer, tstart, tlength), buffer, dstart, dlength);
        }
    }

    public void startDocument() {
        closeStartTag();
        if (this.stringizingLevel > 0) {
            writeJoiner();
            return;
        }
        if (this.nesting == 0) {
            this.base.startDocument();
        } else {
            writeJoiner();
        }
        ensureSpaceInWorkStack(this.nesting);
        this.workStack[this.nesting] = this.namespaceBindings;
        this.workStack[this.nesting + 1] = null;
        this.nesting += 2;
    }

    public void endDocument() {
        if (this.stringizingLevel > 0) {
            writeJoiner();
            return;
        }
        this.nesting -= 2;
        this.namespaceBindings = (NamespaceBinding) this.workStack[this.nesting];
        this.workStack[this.nesting] = null;
        this.workStack[this.nesting + 1] = null;
        if (this.nesting == 0) {
            this.base.endDocument();
        } else {
            writeJoiner();
        }
    }

    public void emitDoctypeDecl(char[] buffer, int target, int tlength, int data, int dlength) {
    }

    public void beginEntity(Object baseUri) {
        if (this.base instanceof XConsumer) {
            ((XConsumer) this.base).beginEntity(baseUri);
        }
    }

    public void endEntity() {
        if (this.base instanceof XConsumer) {
            ((XConsumer) this.base).endEntity();
        }
    }

    public XMLFilter append(char c) {
        write((int) c);
        return this;
    }

    public XMLFilter append(CharSequence csq) {
        if (csq == null) {
            csq = "null";
        }
        append(csq, 0, csq.length());
        return this;
    }

    public XMLFilter append(CharSequence csq, int start, int end) {
        if (csq == null) {
            csq = "null";
        }
        write(csq, start, end - start);
        return this;
    }

    MappingInfo lookupTag(Symbol qname) {
        String local = qname.getLocalPart();
        String prefix = qname.getPrefix();
        if (prefix == "") {
            prefix = null;
        }
        String uri = qname.getNamespaceURI();
        int hash = MappingInfo.hash(prefix, local);
        int index = hash & this.mappingTableMask;
        MappingInfo first = this.mappingTable[index];
        MappingInfo info = first;
        while (info != null) {
            if (qname == info.qname) {
                return info;
            }
            if (local == info.local && info.qname == null && ((uri == info.uri || info.uri == null) && prefix == info.prefix)) {
                info.uri = uri;
                info.qname = qname;
                return info;
            }
            info = info.nextInBucket;
        }
        info = new MappingInfo();
        info.qname = qname;
        info.prefix = prefix;
        info.uri = uri;
        info.local = local;
        info.tagHash = hash;
        info.nextInBucket = first;
        this.mappingTable[index] = first;
        return info;
    }

    MappingInfo lookupTag(char[] data, int start, int length) {
        int hash = 0;
        int prefixHash = 0;
        int colon = -1;
        for (int i = 0; i < length; i++) {
            char ch = data[start + i];
            if (ch != ':' || colon >= 0) {
                hash = (hash * 31) + ch;
            } else {
                colon = i;
                prefixHash = hash;
                hash = 0;
            }
        }
        hash ^= prefixHash;
        int index = hash & this.mappingTableMask;
        MappingInfo first = this.mappingTable[index];
        MappingInfo info = first;
        while (info != null) {
            if (hash == info.tagHash && info.match(data, start, length)) {
                return info;
            }
            info = info.nextInBucket;
        }
        info = new MappingInfo();
        info.tagHash = hash;
        if (colon >= 0) {
            info.prefix = new String(data, start, colon).intern();
            colon++;
            info.local = new String(data, start + colon, length - colon).intern();
        } else {
            info.prefix = null;
            info.local = new String(data, start, length).intern();
        }
        info.nextInBucket = first;
        this.mappingTable[index] = first;
        return info;
    }

    private void ensureSpaceInWorkStack(int oldSize) {
        if (this.workStack == null) {
            this.workStack = new Object[20];
        } else if (oldSize >= this.workStack.length) {
            Object[] tmpn = new Object[(this.workStack.length * 2)];
            System.arraycopy(this.workStack, 0, tmpn, 0, oldSize);
            this.workStack = tmpn;
        }
    }

    private void ensureSpaceInStartIndexes(int oldSize) {
        if (this.startIndexes == null) {
            this.startIndexes = new int[20];
        } else if (oldSize >= this.startIndexes.length) {
            int[] tmpn = new int[(this.startIndexes.length * 2)];
            System.arraycopy(this.startIndexes, 0, tmpn, 0, oldSize);
            this.startIndexes = tmpn;
        }
    }

    public static String duplicateAttributeMessage(Symbol attrSymbol, Object elementName) {
        StringBuffer sbuf = new StringBuffer("duplicate attribute: ");
        String uri = attrSymbol.getNamespaceURI();
        if (uri != null && uri.length() > 0) {
            sbuf.append('{');
            sbuf.append('}');
            sbuf.append(uri);
        }
        sbuf.append(attrSymbol.getLocalPart());
        if (elementName != null) {
            sbuf.append(" in <");
            sbuf.append(elementName);
            sbuf.append('>');
        }
        return sbuf.toString();
    }

    public void error(char severity, String message) {
        if (this.messages == null) {
            throw new RuntimeException(message);
        } else if (this.locator != null) {
            this.messages.error(severity, this.locator, message);
        } else {
            this.messages.error(severity, message);
        }
    }

    public boolean ignoring() {
        return this.ignoringLevel > 0;
    }

    public void setDocumentLocator(Locator locator) {
        if (locator instanceof SourceLocator) {
            this.locator = (SourceLocator) locator;
        }
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
        startElement(Symbol.make(namespaceURI, localName));
        int numAttributes = atts.getLength();
        for (int i = 0; i < numAttributes; i++) {
            startAttribute(Symbol.make(atts.getURI(i), atts.getLocalName(i)));
            write(atts.getValue(i));
            endAttribute();
        }
    }

    public void endElement(String namespaceURI, String localName, String qName) {
        endElement();
    }

    public void startElement(String name, AttributeList atts) {
        startElement(name.intern());
        int attrLength = atts.getLength();
        for (int i = 0; i < attrLength; i++) {
            name = atts.getName(i).intern();
            String type = atts.getType(i);
            String value = atts.getValue(i);
            startAttribute(name);
            write(value);
            endAttribute();
        }
    }

    public void endElement(String name) throws SAXException {
        endElement();
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        write(ch, start, length);
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        write(ch, start, length);
    }

    public void processingInstruction(String target, String data) {
        char[] chars = data.toCharArray();
        processingInstructionCommon(target, chars, 0, chars.length);
    }

    public void startPrefixMapping(String prefix, String uri) {
        this.namespaceBindings = findNamespaceBinding(prefix.intern(), uri.intern(), this.namespaceBindings);
    }

    public void endPrefixMapping(String prefix) {
        this.namespaceBindings = this.namespaceBindings.getNext();
    }

    public void skippedEntity(String name) {
    }

    public String getPublicId() {
        return null;
    }

    public String getSystemId() {
        return this.in == null ? null : this.in.getName();
    }

    public String getFileName() {
        return this.in == null ? null : this.in.getName();
    }

    public int getLineNumber() {
        if (this.in == null) {
            return -1;
        }
        int line = this.in.getLineNumber();
        if (line >= 0) {
            return line + 1;
        }
        return -1;
    }

    public int getColumnNumber() {
        if (this.in != null) {
            int col = this.in.getColumnNumber();
            if (col > 0) {
                return col;
            }
        }
        return -1;
    }

    public boolean isStableSourceLocation() {
        return false;
    }
}

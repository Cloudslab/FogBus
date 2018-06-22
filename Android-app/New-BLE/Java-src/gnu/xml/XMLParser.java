package gnu.xml;

import gnu.lists.Consumer;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.SourceMessages;
import java.io.IOException;
import java.io.InputStream;

public class XMLParser {
    private static final int ATTRIBUTE_SEEN_EQ_STATE = 11;
    private static final int ATTRIBUTE_SEEN_NAME_STATE = 8;
    static final String BAD_ENCODING_SYNTAX = "bad 'encoding' declaration";
    static final String BAD_STANDALONE_SYNTAX = "bad 'standalone' declaration";
    private static final int BEGIN_ELEMENT_STATE = 2;
    private static final int DOCTYPE_NAME_SEEN_STATE = 16;
    private static final int DOCTYPE_SEEN_STATE = 13;
    private static final int END_ELEMENT_STATE = 4;
    private static final int EXPECT_NAME_MODIFIER = 1;
    private static final int EXPECT_RIGHT_STATE = 27;
    private static final int INIT_LEFT_QUEST_STATE = 30;
    private static final int INIT_LEFT_STATE = 34;
    private static final int INIT_STATE = 0;
    private static final int INIT_TEXT_STATE = 31;
    private static final int INVALID_VERSION_DECL = 35;
    private static final int MAYBE_ATTRIBUTE_STATE = 10;
    private static final int PREV_WAS_CR_STATE = 28;
    private static final int SAW_AMP_SHARP_STATE = 26;
    private static final int SAW_AMP_STATE = 25;
    private static final int SAW_ENTITY_REF = 6;
    private static final int SAW_EOF_ERROR = 37;
    private static final int SAW_ERROR = 36;
    private static final int SAW_LEFT_EXCL_MINUS_STATE = 22;
    private static final int SAW_LEFT_EXCL_STATE = 20;
    private static final int SAW_LEFT_QUEST_STATE = 21;
    private static final int SAW_LEFT_SLASH_STATE = 19;
    private static final int SAW_LEFT_STATE = 14;
    private static final int SKIP_SPACES_MODIFIER = 2;
    private static final int TEXT_STATE = 1;

    public static void parse(Object uri, SourceMessages messages, Consumer out) throws IOException {
        parse(Path.openInputStream(uri), uri, messages, out);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.text.LineInputStreamReader XMLStreamReader(java.io.InputStream r15) throws java.io.IOException {
        /*
        r14 = 60;
        r13 = 2;
        r12 = 63;
        r11 = 0;
        r4 = -1;
        r6 = new gnu.text.LineInputStreamReader;
        r6.<init>(r15);
        r1 = r6.getByte();
        if (r1 >= 0) goto L_0x002f;
    L_0x0012:
        r2 = r4;
    L_0x0013:
        if (r2 >= 0) goto L_0x0034;
    L_0x0015:
        r3 = r4;
    L_0x0016:
        r10 = 239; // 0xef float:3.35E-43 double:1.18E-321;
        if (r1 != r10) goto L_0x0039;
    L_0x001a:
        r10 = 187; // 0xbb float:2.62E-43 double:9.24E-322;
        if (r2 != r10) goto L_0x0039;
    L_0x001e:
        r10 = 191; // 0xbf float:2.68E-43 double:9.44E-322;
        if (r3 != r10) goto L_0x0039;
    L_0x0022:
        r10 = 3;
        r6.resetStart(r10);
        r10 = "UTF-8";
        r6.setCharset(r10);
    L_0x002b:
        r6.setKeepFullLines(r11);
        return r6;
    L_0x002f:
        r2 = r6.getByte();
        goto L_0x0013;
    L_0x0034:
        r3 = r6.getByte();
        goto L_0x0016;
    L_0x0039:
        r10 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        if (r1 != r10) goto L_0x004c;
    L_0x003d:
        r10 = 254; // 0xfe float:3.56E-43 double:1.255E-321;
        if (r2 != r10) goto L_0x004c;
    L_0x0041:
        if (r3 == 0) goto L_0x004c;
    L_0x0043:
        r6.resetStart(r13);
        r10 = "UTF-16LE";
        r6.setCharset(r10);
        goto L_0x002b;
    L_0x004c:
        r10 = 254; // 0xfe float:3.56E-43 double:1.255E-321;
        if (r1 != r10) goto L_0x005f;
    L_0x0050:
        r10 = 255; // 0xff float:3.57E-43 double:1.26E-321;
        if (r2 != r10) goto L_0x005f;
    L_0x0054:
        if (r3 == 0) goto L_0x005f;
    L_0x0056:
        r6.resetStart(r13);
        r10 = "UTF-16BE";
        r6.setCharset(r10);
        goto L_0x002b;
    L_0x005f:
        if (r3 >= 0) goto L_0x0079;
    L_0x0061:
        r10 = 76;
        if (r1 != r10) goto L_0x007e;
    L_0x0065:
        r10 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        if (r2 != r10) goto L_0x007e;
    L_0x0069:
        r10 = 167; // 0xa7 float:2.34E-43 double:8.25E-322;
        if (r3 != r10) goto L_0x007e;
    L_0x006d:
        r10 = 148; // 0x94 float:2.07E-43 double:7.3E-322;
        if (r4 != r10) goto L_0x007e;
    L_0x0071:
        r10 = new java.lang.RuntimeException;
        r11 = "XMLParser: EBCDIC encodings not supported";
        r10.<init>(r11);
        throw r10;
    L_0x0079:
        r4 = r6.getByte();
        goto L_0x0061;
    L_0x007e:
        r6.resetStart(r11);
        if (r1 != r14) goto L_0x0093;
    L_0x0083:
        if (r2 != r12) goto L_0x008d;
    L_0x0085:
        r10 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r3 != r10) goto L_0x008d;
    L_0x0089:
        r10 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        if (r4 == r10) goto L_0x009b;
    L_0x008d:
        if (r2 != 0) goto L_0x0093;
    L_0x008f:
        if (r3 != r12) goto L_0x0093;
    L_0x0091:
        if (r4 == 0) goto L_0x009b;
    L_0x0093:
        if (r1 != 0) goto L_0x00d3;
    L_0x0095:
        if (r2 != r14) goto L_0x00d3;
    L_0x0097:
        if (r3 != 0) goto L_0x00d3;
    L_0x0099:
        if (r4 != r12) goto L_0x00d3;
    L_0x009b:
        r5 = r6.buffer;
        if (r5 != 0) goto L_0x00a5;
    L_0x009f:
        r10 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r5 = new char[r10];
        r6.buffer = r5;
    L_0x00a5:
        r7 = 0;
        r9 = 0;
    L_0x00a7:
        r0 = r6.getByte();
        if (r0 == 0) goto L_0x00a7;
    L_0x00ad:
        if (r0 >= 0) goto L_0x00b5;
    L_0x00af:
        r6.pos = r11;
        r6.limit = r7;
        goto L_0x002b;
    L_0x00b5:
        r8 = r7 + 1;
        r10 = r0 & 255;
        r10 = (char) r10;
        r5[r7] = r10;
        if (r9 != 0) goto L_0x00cf;
    L_0x00be:
        r10 = 62;
        if (r0 != r10) goto L_0x00c4;
    L_0x00c2:
        r7 = r8;
        goto L_0x00af;
    L_0x00c4:
        r10 = 39;
        if (r0 == r10) goto L_0x00cc;
    L_0x00c8:
        r10 = 34;
        if (r0 != r10) goto L_0x00cd;
    L_0x00cc:
        r9 = r0;
    L_0x00cd:
        r7 = r8;
        goto L_0x00a7;
    L_0x00cf:
        if (r0 != r9) goto L_0x00cd;
    L_0x00d1:
        r9 = 0;
        goto L_0x00cd;
    L_0x00d3:
        r10 = "UTF-8";
        r6.setCharset(r10);
        goto L_0x002b;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLParser.XMLStreamReader(java.io.InputStream):gnu.text.LineInputStreamReader");
    }

    public static void parse(InputStream strm, Object uri, SourceMessages messages, Consumer out) throws IOException {
        LineBufferedReader in = XMLStreamReader(strm);
        if (uri != null) {
            in.setName(uri);
        }
        parse(in, messages, out);
        in.close();
    }

    public static void parse(LineBufferedReader in, SourceMessages messages, Consumer out) throws IOException {
        XMLFilter filter = new XMLFilter(out);
        filter.setMessages(messages);
        filter.setSourceLocator(in);
        filter.startDocument();
        Path uri = in.getPath();
        if (uri != null) {
            filter.writeDocumentUri(uri);
        }
        parse(in, filter);
        filter.endDocument();
    }

    public static void parse(LineBufferedReader in, SourceMessages messages, XMLFilter filter) throws IOException {
        filter.setMessages(messages);
        filter.setSourceLocator(in);
        filter.startDocument();
        Path uri = in.getPath();
        if (uri != null) {
            filter.writeDocumentUri(uri);
        }
        parse(in, filter);
        filter.endDocument();
        in.close();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void parse(gnu.text.LineBufferedReader r26, gnu.xml.XMLFilter r27) {
        /*
        r0 = r26;
        r3 = r0.buffer;
        r0 = r26;
        r0 = r0.pos;
        r18 = r0;
        r0 = r26;
        r0 = r0.limit;
        r16 = r0;
        r22 = 0;
        r23 = 60;
        r10 = 14;
        r9 = 32;
        r5 = 0;
        r6 = -1;
        r17 = 0;
        r4 = r16;
    L_0x001e:
        switch(r22) {
            case 0: goto L_0x002e;
            case 1: goto L_0x008a;
            case 2: goto L_0x030b;
            case 3: goto L_0x01a8;
            case 4: goto L_0x07e5;
            case 5: goto L_0x01a8;
            case 6: goto L_0x02c3;
            case 7: goto L_0x01a8;
            case 8: goto L_0x076f;
            case 9: goto L_0x01a8;
            case 10: goto L_0x0734;
            case 11: goto L_0x07ab;
            case 12: goto L_0x017c;
            case 13: goto L_0x06d0;
            case 14: goto L_0x02e3;
            case 15: goto L_0x017c;
            case 16: goto L_0x06d6;
            case 17: goto L_0x01a8;
            case 18: goto L_0x0021;
            case 19: goto L_0x07df;
            case 20: goto L_0x087c;
            case 21: goto L_0x031c;
            case 22: goto L_0x0021;
            case 23: goto L_0x017c;
            case 24: goto L_0x01a8;
            case 25: goto L_0x02af;
            case 26: goto L_0x0880;
            case 27: goto L_0x07f6;
            case 28: goto L_0x0151;
            case 29: goto L_0x017c;
            case 30: goto L_0x031c;
            case 31: goto L_0x0035;
            case 32: goto L_0x017c;
            case 33: goto L_0x01a8;
            case 34: goto L_0x0041;
            case 35: goto L_0x004f;
            case 36: goto L_0x0053;
            case 37: goto L_0x007a;
            default: goto L_0x0021;
        };
    L_0x0021:
        r19 = r18;
    L_0x0023:
        r0 = r19;
        r1 = r16;
        if (r0 >= r1) goto L_0x0806;
    L_0x0029:
        r18 = r19 + 1;
        r9 = r3[r19];
        goto L_0x001e;
    L_0x002e:
        r22 = 1;
        r22 = 31;
        r19 = r18;
        goto L_0x0023;
    L_0x0035:
        r2 = 60;
        if (r9 != r2) goto L_0x003e;
    L_0x0039:
        r22 = 34;
        r19 = r18;
        goto L_0x0023;
    L_0x003e:
        r22 = 1;
        goto L_0x001e;
    L_0x0041:
        r2 = 63;
        if (r9 != r2) goto L_0x004c;
    L_0x0045:
        r4 = r18;
        r22 = 33;
        r19 = r18;
        goto L_0x0023;
    L_0x004c:
        r22 = 14;
        goto L_0x001e;
    L_0x004f:
        r18 = r6;
        r17 = "invalid xml version specifier";
    L_0x0053:
        r0 = r18;
        r1 = r26;
        r1.pos = r0;
        r2 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r0 = r27;
        r1 = r17;
        r0.error(r2, r1);
    L_0x0062:
        r19 = r18;
        r0 = r19;
        r1 = r16;
        if (r0 < r1) goto L_0x006d;
    L_0x006a:
        r18 = r19;
    L_0x006c:
        return;
    L_0x006d:
        r18 = r19 + 1;
        r9 = r3[r19];
        r2 = 62;
        if (r9 != r2) goto L_0x0062;
    L_0x0075:
        r22 = 1;
        r19 = r18;
        goto L_0x0023;
    L_0x007a:
        r0 = r18;
        r1 = r26;
        r1.pos = r0;
        r2 = 102; // 0x66 float:1.43E-43 double:5.04E-322;
        r7 = "unexpected end-of-file";
        r0 = r27;
        r0.error(r2, r7);
        goto L_0x006c;
    L_0x008a:
        r4 = r18 + -1;
        r5 = r18;
        r19 = r18;
    L_0x0090:
        r0 = r23;
        if (r9 != r0) goto L_0x00ac;
    L_0x0094:
        r22 = r10;
        r18 = r19;
    L_0x0098:
        r5 = r18 - r5;
        if (r5 <= 0) goto L_0x00a7;
    L_0x009c:
        r0 = r18;
        r1 = r26;
        r1.pos = r0;
        r0 = r27;
        r0.textFromParser(r3, r4, r5);
    L_0x00a7:
        r4 = r3.length;
        r19 = r18;
        goto L_0x0023;
    L_0x00ac:
        r2 = 38;
        if (r9 != r2) goto L_0x00b5;
    L_0x00b0:
        r22 = 25;
        r18 = r19;
        goto L_0x0098;
    L_0x00b5:
        r2 = 13;
        if (r9 != r2) goto L_0x0115;
    L_0x00b9:
        r5 = r19 - r5;
        r0 = r19;
        r1 = r26;
        r1.pos = r0;
        if (r5 <= 0) goto L_0x00c8;
    L_0x00c3:
        r0 = r27;
        r0.textFromParser(r3, r4, r5);
    L_0x00c8:
        r0 = r19;
        r1 = r16;
        if (r0 >= r1) goto L_0x010e;
    L_0x00ce:
        r9 = r3[r19];
        r2 = 10;
        if (r9 != r2) goto L_0x00ef;
    L_0x00d4:
        r4 = r19;
        r18 = r19 + 1;
        r5 = r18;
    L_0x00da:
        r2 = 1;
        r0 = r26;
        r1 = r18;
        r0.incrLineNumber(r2, r1);
        r19 = r18;
    L_0x00e4:
        r0 = r19;
        r1 = r16;
        if (r0 != r1) goto L_0x0149;
    L_0x00ea:
        r5 = r5 + -1;
        r18 = r19;
        goto L_0x0098;
    L_0x00ef:
        r27.linefeedFromParser();
        r2 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        if (r9 != r2) goto L_0x00fd;
    L_0x00f6:
        r18 = r19 + 1;
        r4 = r19;
        r5 = r18 + 1;
        goto L_0x00da;
    L_0x00fd:
        r2 = 1;
        r0 = r26;
        r1 = r19;
        r0.incrLineNumber(r2, r1);
        r4 = r19;
        r18 = r19 + 1;
        r5 = r18;
        r19 = r18;
        goto L_0x0090;
    L_0x010e:
        r27.linefeedFromParser();
        r22 = 28;
        goto L_0x0023;
    L_0x0115:
        r2 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        if (r9 == r2) goto L_0x011d;
    L_0x0119:
        r2 = 8232; // 0x2028 float:1.1535E-41 double:4.067E-320;
        if (r9 != r2) goto L_0x013c;
    L_0x011d:
        r5 = r19 - r5;
        r2 = r19 + -1;
        r0 = r26;
        r0.pos = r2;
        if (r5 <= 0) goto L_0x012c;
    L_0x0127:
        r0 = r27;
        r0.textFromParser(r3, r4, r5);
    L_0x012c:
        r27.linefeedFromParser();
        r2 = 1;
        r0 = r26;
        r1 = r19;
        r0.incrLineNumber(r2, r1);
        r5 = r19 + 1;
        r4 = r19;
        goto L_0x00e4;
    L_0x013c:
        r2 = 10;
        if (r9 != r2) goto L_0x00e4;
    L_0x0140:
        r2 = 1;
        r0 = r26;
        r1 = r19;
        r0.incrLineNumber(r2, r1);
        goto L_0x00e4;
    L_0x0149:
        r18 = r19 + 1;
        r9 = r3[r19];
        r19 = r18;
        goto L_0x0090;
    L_0x0151:
        r22 = 1;
        r2 = 10;
        if (r9 != r2) goto L_0x016d;
    L_0x0157:
        r2 = 1;
        r7 = r2;
    L_0x0159:
        r2 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        if (r9 != r2) goto L_0x0170;
    L_0x015d:
        r2 = 1;
    L_0x015e:
        r2 = r2 | r7;
        if (r2 == 0) goto L_0x0172;
    L_0x0161:
        r2 = 1;
        r0 = r26;
        r1 = r18;
        r0.incrLineNumber(r2, r1);
        r19 = r18;
        goto L_0x0023;
    L_0x016d:
        r2 = 0;
        r7 = r2;
        goto L_0x0159;
    L_0x0170:
        r2 = 0;
        goto L_0x015e;
    L_0x0172:
        r2 = 1;
        r7 = r18 + -1;
        r0 = r26;
        r0.incrLineNumber(r2, r7);
        goto L_0x001e;
    L_0x017c:
        r2 = 32;
        if (r9 == r2) goto L_0x0021;
    L_0x0180:
        r2 = 9;
        if (r9 != r2) goto L_0x0188;
    L_0x0184:
        r19 = r18;
        goto L_0x0023;
    L_0x0188:
        r2 = 10;
        if (r9 == r2) goto L_0x0198;
    L_0x018c:
        r2 = 13;
        if (r9 == r2) goto L_0x0198;
    L_0x0190:
        r2 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        if (r9 == r2) goto L_0x0198;
    L_0x0194:
        r2 = 8232; // 0x2028 float:1.1535E-41 double:4.067E-320;
        if (r9 != r2) goto L_0x01a4;
    L_0x0198:
        r2 = 1;
        r0 = r26;
        r1 = r18;
        r0.incrLineNumber(r2, r1);
        r19 = r18;
        goto L_0x0023;
    L_0x01a4:
        r22 = r22 + -2;
        goto L_0x001e;
    L_0x01a8:
        r5 = r4 + 1;
        r19 = r18;
    L_0x01ac:
        r2 = 97;
        if (r9 < r2) goto L_0x01b4;
    L_0x01b0:
        r2 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        if (r9 <= r2) goto L_0x022b;
    L_0x01b4:
        r2 = 65;
        if (r9 < r2) goto L_0x01bc;
    L_0x01b8:
        r2 = 90;
        if (r9 <= r2) goto L_0x022b;
    L_0x01bc:
        r2 = 95;
        if (r9 == r2) goto L_0x022b;
    L_0x01c0:
        r2 = 58;
        if (r9 == r2) goto L_0x022b;
    L_0x01c4:
        r2 = 192; // 0xc0 float:2.69E-43 double:9.5E-322;
        if (r9 < r2) goto L_0x0203;
    L_0x01c8:
        r2 = 767; // 0x2ff float:1.075E-42 double:3.79E-321;
        if (r9 <= r2) goto L_0x022b;
    L_0x01cc:
        r2 = 880; // 0x370 float:1.233E-42 double:4.35E-321;
        if (r9 < r2) goto L_0x0203;
    L_0x01d0:
        r2 = 8191; // 0x1fff float:1.1478E-41 double:4.047E-320;
        if (r9 > r2) goto L_0x01d8;
    L_0x01d4:
        r2 = 894; // 0x37e float:1.253E-42 double:4.417E-321;
        if (r9 != r2) goto L_0x022b;
    L_0x01d8:
        r2 = 8204; // 0x200c float:1.1496E-41 double:4.0533E-320;
        if (r9 < r2) goto L_0x0203;
    L_0x01dc:
        r2 = 8205; // 0x200d float:1.1498E-41 double:4.054E-320;
        if (r9 <= r2) goto L_0x022b;
    L_0x01e0:
        r2 = 8304; // 0x2070 float:1.1636E-41 double:4.1027E-320;
        if (r9 < r2) goto L_0x01e8;
    L_0x01e4:
        r2 = 8591; // 0x218f float:1.2039E-41 double:4.2445E-320;
        if (r9 <= r2) goto L_0x022b;
    L_0x01e8:
        r2 = 11264; // 0x2c00 float:1.5784E-41 double:5.565E-320;
        if (r9 < r2) goto L_0x01f0;
    L_0x01ec:
        r2 = 12271; // 0x2fef float:1.7195E-41 double:6.0627E-320;
        if (r9 <= r2) goto L_0x022b;
    L_0x01f0:
        r2 = 12289; // 0x3001 float:1.722E-41 double:6.0716E-320;
        if (r9 < r2) goto L_0x01f9;
    L_0x01f4:
        r2 = 55295; // 0xd7ff float:7.7485E-41 double:2.73194E-319;
        if (r9 <= r2) goto L_0x022b;
    L_0x01f9:
        r2 = 63744; // 0xf900 float:8.9324E-41 double:3.14937E-319;
        if (r9 < r2) goto L_0x0203;
    L_0x01fe:
        r2 = 65533; // 0xfffd float:9.1831E-41 double:3.23776E-319;
        if (r9 <= r2) goto L_0x022b;
    L_0x0203:
        r0 = r19;
        if (r0 <= r5) goto L_0x020f;
    L_0x0207:
        r2 = 48;
        if (r9 < r2) goto L_0x020f;
    L_0x020b:
        r2 = 57;
        if (r9 <= r2) goto L_0x022b;
    L_0x020f:
        r2 = 46;
        if (r9 == r2) goto L_0x022b;
    L_0x0213:
        r2 = 45;
        if (r9 == r2) goto L_0x022b;
    L_0x0217:
        r2 = 183; // 0xb7 float:2.56E-43 double:9.04E-322;
        if (r9 == r2) goto L_0x022b;
    L_0x021b:
        r2 = 768; // 0x300 float:1.076E-42 double:3.794E-321;
        if (r9 <= r2) goto L_0x0239;
    L_0x021f:
        r2 = 879; // 0x36f float:1.232E-42 double:4.343E-321;
        if (r9 <= r2) goto L_0x022b;
    L_0x0223:
        r2 = 8255; // 0x203f float:1.1568E-41 double:4.0785E-320;
        if (r9 < r2) goto L_0x0239;
    L_0x0227:
        r2 = 8256; // 0x2040 float:1.1569E-41 double:4.079E-320;
        if (r9 > r2) goto L_0x0239;
    L_0x022b:
        r0 = r19;
        r1 = r16;
        if (r0 >= r1) goto L_0x0023;
    L_0x0231:
        r18 = r19 + 1;
        r9 = r3[r19];
        r19 = r18;
        goto L_0x01ac;
    L_0x0239:
        r22 = r22 + -1;
        r5 = r19 - r5;
        if (r5 != 0) goto L_0x0878;
    L_0x023f:
        r2 = 8;
        r0 = r22;
        if (r0 != r2) goto L_0x024d;
    L_0x0245:
        r17 = "missing or invalid attribute name";
    L_0x0247:
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x024d:
        r2 = 2;
        r0 = r22;
        if (r0 == r2) goto L_0x0257;
    L_0x0252:
        r2 = 4;
        r0 = r22;
        if (r0 != r2) goto L_0x025a;
    L_0x0257:
        r17 = "missing or invalid element name";
        goto L_0x0247;
    L_0x025a:
        r17 = "missing or invalid name";
        goto L_0x0247;
    L_0x025d:
        r2 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r9 != r2) goto L_0x0287;
    L_0x0261:
        if (r6 != 0) goto L_0x0287;
    L_0x0263:
        r6 = 16;
    L_0x0265:
        r0 = r19;
        r1 = r16;
        if (r0 >= r1) goto L_0x0023;
    L_0x026b:
        r18 = r19 + 1;
        r9 = r3[r19];
        r19 = r18;
    L_0x0271:
        r2 = 59;
        if (r9 != r2) goto L_0x025d;
    L_0x0275:
        r0 = r19;
        r1 = r26;
        r1.pos = r0;
        r2 = r19 + -1;
        r2 = r2 - r4;
        r0 = r27;
        r0.emitCharacterReference(r5, r3, r4, r2);
        r22 = 1;
        goto L_0x0023;
    L_0x0287:
        r2 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
        if (r5 < r2) goto L_0x029e;
    L_0x028b:
        r0 = r19;
        r1 = r26;
        r1.pos = r0;
        r2 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r7 = "invalid character reference";
        r0 = r27;
        r0.error(r2, r7);
        r22 = 1;
        goto L_0x0023;
    L_0x029e:
        if (r6 != 0) goto L_0x02ad;
    L_0x02a0:
        r8 = 10;
    L_0x02a2:
        r11 = java.lang.Character.digit(r9, r8);
        if (r11 < 0) goto L_0x028b;
    L_0x02a8:
        r2 = r5 * r8;
        r5 = r2 + r11;
        goto L_0x0265;
    L_0x02ad:
        r8 = r6;
        goto L_0x02a2;
    L_0x02af:
        r2 = 35;
        if (r9 != r2) goto L_0x02bd;
    L_0x02b3:
        r22 = 26;
        r4 = r18;
        r5 = 0;
        r6 = 0;
        r19 = r18;
        goto L_0x0023;
    L_0x02bd:
        r4 = r18 + -1;
        r22 = 7;
        goto L_0x001e;
    L_0x02c3:
        r0 = r18;
        r1 = r26;
        r1.pos = r0;
        r2 = 59;
        if (r9 == r2) goto L_0x02d6;
    L_0x02cd:
        r2 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r7 = "missing ';'";
        r0 = r27;
        r0.error(r2, r7);
    L_0x02d6:
        r0 = r27;
        r0.emitEntityReference(r3, r4, r5);
        r4 = r16;
        r22 = 1;
        r19 = r18;
        goto L_0x0023;
    L_0x02e3:
        r2 = 47;
        if (r9 != r2) goto L_0x02ed;
    L_0x02e7:
        r22 = 19;
        r19 = r18;
        goto L_0x0023;
    L_0x02ed:
        r2 = 63;
        if (r9 != r2) goto L_0x02f9;
    L_0x02f1:
        r4 = r18;
        r22 = 24;
        r19 = r18;
        goto L_0x0023;
    L_0x02f9:
        r2 = 33;
        if (r9 != r2) goto L_0x0305;
    L_0x02fd:
        r22 = 20;
        r4 = r18;
        r19 = r18;
        goto L_0x0023;
    L_0x0305:
        r4 = r18 + -1;
        r22 = 3;
        goto L_0x001e;
    L_0x030b:
        r2 = r18 - r5;
        r0 = r26;
        r0.pos = r2;
        r0 = r27;
        r0.emitStartElement(r3, r4, r5);
        r22 = 12;
        r4 = r16;
        goto L_0x001e;
    L_0x031c:
        if (r6 >= 0) goto L_0x0874;
    L_0x031e:
        r6 = r18 + -1;
        r19 = r18;
    L_0x0322:
        r2 = 62;
        if (r9 != r2) goto L_0x05e0;
    L_0x0326:
        r13 = r19 + -2;
        r2 = r3[r13];
        r7 = 63;
        if (r2 != r7) goto L_0x05e0;
    L_0x032e:
        if (r13 < r6) goto L_0x05e0;
    L_0x0330:
        r0 = r19;
        r1 = r26;
        r1.pos = r0;
        r2 = 3;
        if (r5 != r2) goto L_0x05d2;
    L_0x0339:
        r2 = r3[r4];
        r7 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r2 != r7) goto L_0x05d2;
    L_0x033f:
        r2 = r4 + 1;
        r2 = r3[r2];
        r7 = 109; // 0x6d float:1.53E-43 double:5.4E-322;
        if (r2 != r7) goto L_0x05d2;
    L_0x0347:
        r2 = r4 + 2;
        r2 = r3[r2];
        r7 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        if (r2 != r7) goto L_0x05d2;
    L_0x034f:
        r2 = 30;
        r0 = r22;
        if (r0 != r2) goto L_0x05ca;
    L_0x0355:
        r2 = r6 + 7;
        if (r13 <= r2) goto L_0x038f;
    L_0x0359:
        r2 = r3[r6];
        r7 = 118; // 0x76 float:1.65E-43 double:5.83E-322;
        if (r2 != r7) goto L_0x038f;
    L_0x035f:
        r2 = r6 + 1;
        r2 = r3[r2];
        r7 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r2 != r7) goto L_0x038f;
    L_0x0367:
        r2 = r6 + 2;
        r2 = r3[r2];
        r7 = 114; // 0x72 float:1.6E-43 double:5.63E-322;
        if (r2 != r7) goto L_0x038f;
    L_0x036f:
        r2 = r6 + 3;
        r2 = r3[r2];
        r7 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        if (r2 != r7) goto L_0x038f;
    L_0x0377:
        r2 = r6 + 4;
        r2 = r3[r2];
        r7 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        if (r2 != r7) goto L_0x038f;
    L_0x037f:
        r2 = r6 + 5;
        r2 = r3[r2];
        r7 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        if (r2 != r7) goto L_0x038f;
    L_0x0387:
        r2 = r6 + 6;
        r2 = r3[r2];
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        if (r2 == r7) goto L_0x0397;
    L_0x038f:
        r18 = r6;
        r17 = "xml declaration without version";
        r22 = 36;
        goto L_0x001e;
    L_0x0397:
        r6 = r6 + 7;
        r9 = r3[r6];
    L_0x039b:
        r2 = java.lang.Character.isWhitespace(r9);
        if (r2 == 0) goto L_0x03a8;
    L_0x03a1:
        r6 = r6 + 1;
        if (r6 >= r13) goto L_0x03a8;
    L_0x03a5:
        r9 = r3[r6];
        goto L_0x039b;
    L_0x03a8:
        r2 = 61;
        if (r9 == r2) goto L_0x03b2;
    L_0x03ac:
        r22 = 35;
        r18 = r19;
        goto L_0x001e;
    L_0x03b2:
        r6 = r6 + 1;
        r9 = r3[r6];
    L_0x03b6:
        r2 = java.lang.Character.isWhitespace(r9);
        if (r2 == 0) goto L_0x03c3;
    L_0x03bc:
        r6 = r6 + 1;
        if (r6 >= r13) goto L_0x03c3;
    L_0x03c0:
        r9 = r3[r6];
        goto L_0x03b6;
    L_0x03c3:
        r2 = 39;
        if (r9 == r2) goto L_0x03d1;
    L_0x03c7:
        r2 = 34;
        if (r9 == r2) goto L_0x03d1;
    L_0x03cb:
        r22 = 35;
        r18 = r19;
        goto L_0x001e;
    L_0x03d1:
        r20 = r9;
        r6 = r6 + 1;
        r15 = r6;
    L_0x03d6:
        if (r15 != r13) goto L_0x03de;
    L_0x03d8:
        r22 = 35;
        r18 = r19;
        goto L_0x001e;
    L_0x03de:
        r9 = r3[r15];
        r0 = r20;
        if (r9 != r0) goto L_0x0411;
    L_0x03e4:
        r2 = r6 + 3;
        if (r15 != r2) goto L_0x03fe;
    L_0x03e8:
        r2 = r3[r6];
        r7 = 49;
        if (r2 != r7) goto L_0x03fe;
    L_0x03ee:
        r2 = r6 + 1;
        r2 = r3[r2];
        r7 = 46;
        if (r2 != r7) goto L_0x03fe;
    L_0x03f6:
        r2 = r6 + 2;
        r9 = r3[r2];
        r2 = 48;
        if (r9 == r2) goto L_0x0402;
    L_0x03fe:
        r2 = 49;
        if (r9 != r2) goto L_0x0414;
    L_0x0402:
        r6 = r15 + 1;
    L_0x0404:
        if (r6 >= r13) goto L_0x041a;
    L_0x0406:
        r2 = r3[r6];
        r2 = java.lang.Character.isWhitespace(r2);
        if (r2 == 0) goto L_0x041a;
    L_0x040e:
        r6 = r6 + 1;
        goto L_0x0404;
    L_0x0411:
        r15 = r15 + 1;
        goto L_0x03d6;
    L_0x0414:
        r22 = 35;
        r18 = r19;
        goto L_0x001e;
    L_0x041a:
        r2 = r6 + 7;
        if (r13 <= r2) goto L_0x04d5;
    L_0x041e:
        r2 = r3[r6];
        r7 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r2 != r7) goto L_0x04d5;
    L_0x0424:
        r2 = r6 + 1;
        r2 = r3[r2];
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        if (r2 != r7) goto L_0x04d5;
    L_0x042c:
        r2 = r6 + 2;
        r2 = r3[r2];
        r7 = 99;
        if (r2 != r7) goto L_0x04d5;
    L_0x0434:
        r2 = r6 + 3;
        r2 = r3[r2];
        r7 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        if (r2 != r7) goto L_0x04d5;
    L_0x043c:
        r2 = r6 + 4;
        r2 = r3[r2];
        r7 = 100;
        if (r2 != r7) goto L_0x04d5;
    L_0x0444:
        r2 = r6 + 5;
        r2 = r3[r2];
        r7 = 105; // 0x69 float:1.47E-43 double:5.2E-322;
        if (r2 != r7) goto L_0x04d5;
    L_0x044c:
        r2 = r6 + 6;
        r2 = r3[r2];
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        if (r2 != r7) goto L_0x04d5;
    L_0x0454:
        r2 = r6 + 7;
        r2 = r3[r2];
        r7 = 103; // 0x67 float:1.44E-43 double:5.1E-322;
        if (r2 != r7) goto L_0x04d5;
    L_0x045c:
        r6 = r6 + 8;
        r9 = r3[r6];
    L_0x0460:
        r2 = java.lang.Character.isWhitespace(r9);
        if (r2 == 0) goto L_0x046d;
    L_0x0466:
        r6 = r6 + 1;
        if (r6 >= r13) goto L_0x046d;
    L_0x046a:
        r9 = r3[r6];
        goto L_0x0460;
    L_0x046d:
        r2 = 61;
        if (r9 == r2) goto L_0x0479;
    L_0x0471:
        r17 = "bad 'encoding' declaration";
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x0479:
        r6 = r6 + 1;
        r9 = r3[r6];
    L_0x047d:
        r2 = java.lang.Character.isWhitespace(r9);
        if (r2 == 0) goto L_0x048a;
    L_0x0483:
        r6 = r6 + 1;
        if (r6 >= r13) goto L_0x048a;
    L_0x0487:
        r9 = r3[r6];
        goto L_0x047d;
    L_0x048a:
        r2 = 39;
        if (r9 == r2) goto L_0x049a;
    L_0x048e:
        r2 = 34;
        if (r9 == r2) goto L_0x049a;
    L_0x0492:
        r17 = "bad 'encoding' declaration";
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x049a:
        r20 = r9;
        r6 = r6 + 1;
        r15 = r6;
    L_0x049f:
        if (r15 != r13) goto L_0x04a9;
    L_0x04a1:
        r17 = "bad 'encoding' declaration";
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x04a9:
        r9 = r3[r15];
        r0 = r20;
        if (r9 != r0) goto L_0x04d2;
    L_0x04af:
        r12 = new java.lang.String;
        r2 = r15 - r6;
        r12.<init>(r3, r6, r2);
        r0 = r26;
        r2 = r0 instanceof gnu.text.LineInputStreamReader;
        if (r2 == 0) goto L_0x04c3;
    L_0x04bc:
        r2 = r26;
        r2 = (gnu.text.LineInputStreamReader) r2;
        r2.setCharset(r12);
    L_0x04c3:
        r6 = r15 + 1;
    L_0x04c5:
        if (r6 >= r13) goto L_0x04d5;
    L_0x04c7:
        r2 = r3[r6];
        r2 = java.lang.Character.isWhitespace(r2);
        if (r2 == 0) goto L_0x04d5;
    L_0x04cf:
        r6 = r6 + 1;
        goto L_0x04c5;
    L_0x04d2:
        r15 = r15 + 1;
        goto L_0x049f;
    L_0x04d5:
        r2 = r6 + 9;
        if (r13 <= r2) goto L_0x05c0;
    L_0x04d9:
        r2 = r3[r6];
        r7 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        if (r2 != r7) goto L_0x05c0;
    L_0x04df:
        r2 = r6 + 1;
        r2 = r3[r2];
        r7 = 116; // 0x74 float:1.63E-43 double:5.73E-322;
        if (r2 != r7) goto L_0x05c0;
    L_0x04e7:
        r2 = r6 + 2;
        r2 = r3[r2];
        r7 = 97;
        if (r2 != r7) goto L_0x05c0;
    L_0x04ef:
        r2 = r6 + 3;
        r2 = r3[r2];
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        if (r2 != r7) goto L_0x05c0;
    L_0x04f7:
        r2 = r6 + 4;
        r2 = r3[r2];
        r7 = 100;
        if (r2 != r7) goto L_0x05c0;
    L_0x04ff:
        r2 = r6 + 5;
        r2 = r3[r2];
        r7 = 97;
        if (r2 != r7) goto L_0x05c0;
    L_0x0507:
        r2 = r6 + 6;
        r2 = r3[r2];
        r7 = 108; // 0x6c float:1.51E-43 double:5.34E-322;
        if (r2 != r7) goto L_0x05c0;
    L_0x050f:
        r2 = r6 + 7;
        r2 = r3[r2];
        r7 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        if (r2 != r7) goto L_0x05c0;
    L_0x0517:
        r2 = r6 + 8;
        r2 = r3[r2];
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        if (r2 != r7) goto L_0x05c0;
    L_0x051f:
        r2 = r6 + 9;
        r2 = r3[r2];
        r7 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r2 != r7) goto L_0x05c0;
    L_0x0527:
        r6 = r6 + 10;
        r9 = r3[r6];
    L_0x052b:
        r2 = java.lang.Character.isWhitespace(r9);
        if (r2 == 0) goto L_0x0538;
    L_0x0531:
        r6 = r6 + 1;
        if (r6 >= r13) goto L_0x0538;
    L_0x0535:
        r9 = r3[r6];
        goto L_0x052b;
    L_0x0538:
        r2 = 61;
        if (r9 == r2) goto L_0x0544;
    L_0x053c:
        r17 = "bad 'standalone' declaration";
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x0544:
        r6 = r6 + 1;
        r9 = r3[r6];
    L_0x0548:
        r2 = java.lang.Character.isWhitespace(r9);
        if (r2 == 0) goto L_0x0555;
    L_0x054e:
        r6 = r6 + 1;
        if (r6 >= r13) goto L_0x0555;
    L_0x0552:
        r9 = r3[r6];
        goto L_0x0548;
    L_0x0555:
        r2 = 39;
        if (r9 == r2) goto L_0x0565;
    L_0x0559:
        r2 = 34;
        if (r9 == r2) goto L_0x0565;
    L_0x055d:
        r17 = "bad 'standalone' declaration";
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x0565:
        r20 = r9;
        r6 = r6 + 1;
        r15 = r6;
    L_0x056a:
        if (r15 != r13) goto L_0x0574;
    L_0x056c:
        r17 = "bad 'standalone' declaration";
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x0574:
        r9 = r3[r15];
        r0 = r20;
        if (r9 != r0) goto L_0x05a3;
    L_0x057a:
        r2 = r6 + 3;
        if (r15 != r2) goto L_0x05a6;
    L_0x057e:
        r2 = r3[r6];
        r7 = 121; // 0x79 float:1.7E-43 double:6.0E-322;
        if (r2 != r7) goto L_0x05a6;
    L_0x0584:
        r2 = r6 + 1;
        r2 = r3[r2];
        r7 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r2 != r7) goto L_0x05a6;
    L_0x058c:
        r2 = r6 + 2;
        r2 = r3[r2];
        r7 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        if (r2 != r7) goto L_0x05a6;
    L_0x0594:
        r6 = r15 + 1;
    L_0x0596:
        if (r6 >= r13) goto L_0x05c0;
    L_0x0598:
        r2 = r3[r6];
        r2 = java.lang.Character.isWhitespace(r2);
        if (r2 == 0) goto L_0x05c0;
    L_0x05a0:
        r6 = r6 + 1;
        goto L_0x0596;
    L_0x05a3:
        r15 = r15 + 1;
        goto L_0x056a;
    L_0x05a6:
        r2 = r6 + 2;
        if (r15 != r2) goto L_0x05b8;
    L_0x05aa:
        r2 = r3[r6];
        r7 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        if (r2 != r7) goto L_0x05b8;
    L_0x05b0:
        r2 = r6 + 1;
        r2 = r3[r2];
        r7 = 111; // 0x6f float:1.56E-43 double:5.5E-322;
        if (r2 == r7) goto L_0x0594;
    L_0x05b8:
        r17 = "bad 'standalone' declaration";
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x05c0:
        if (r13 == r6) goto L_0x05d9;
    L_0x05c2:
        r17 = "junk at end of xml declaration";
        r18 = r6;
        r22 = 36;
        goto L_0x001e;
    L_0x05ca:
        r17 = "<?xml must be at start of file";
        r22 = 36;
        r18 = r19;
        goto L_0x001e;
    L_0x05d2:
        r7 = r13 - r6;
        r2 = r27;
        r2.processingInstructionFromParser(r3, r4, r5, r6, r7);
    L_0x05d9:
        r4 = r16;
        r6 = -1;
        r22 = 1;
        goto L_0x0023;
    L_0x05e0:
        r0 = r19;
        r1 = r16;
        if (r0 >= r1) goto L_0x0023;
    L_0x05e6:
        r18 = r19 + 1;
        r9 = r3[r19];
        r19 = r18;
        goto L_0x0322;
    L_0x05ee:
        r0 = r19;
        r1 = r16;
        if (r0 >= r1) goto L_0x0023;
    L_0x05f4:
        r18 = r19 + 1;
        r9 = r3[r19];
        r19 = r18;
    L_0x05fa:
        r2 = 62;
        if (r9 != r2) goto L_0x0692;
    L_0x05fe:
        r2 = r19 + -1;
        r5 = r2 - r4;
        r2 = 4;
        if (r5 < r2) goto L_0x0638;
    L_0x0605:
        r2 = r3[r4];
        r7 = 45;
        if (r2 != r7) goto L_0x0638;
    L_0x060b:
        r2 = r4 + 1;
        r2 = r3[r2];
        r7 = 45;
        if (r2 != r7) goto L_0x0638;
    L_0x0613:
        r2 = r19 + -2;
        r2 = r3[r2];
        r7 = 45;
        if (r2 != r7) goto L_0x05ee;
    L_0x061b:
        r2 = r19 + -3;
        r2 = r3[r2];
        r7 = 45;
        if (r2 != r7) goto L_0x05ee;
    L_0x0623:
        r0 = r19;
        r1 = r26;
        r1.pos = r0;
        r2 = r4 + 2;
        r7 = r5 + -4;
        r0 = r27;
        r0.commentFromParser(r3, r2, r7);
    L_0x0632:
        r4 = r16;
        r22 = 1;
        goto L_0x0023;
    L_0x0638:
        r2 = 6;
        if (r5 < r2) goto L_0x0632;
    L_0x063b:
        r2 = r3[r4];
        r7 = 91;
        if (r2 != r7) goto L_0x0632;
    L_0x0641:
        r2 = r4 + 1;
        r2 = r3[r2];
        r7 = 67;
        if (r2 != r7) goto L_0x0632;
    L_0x0649:
        r2 = r4 + 2;
        r2 = r3[r2];
        r7 = 68;
        if (r2 != r7) goto L_0x0632;
    L_0x0651:
        r2 = r4 + 3;
        r2 = r3[r2];
        r7 = 65;
        if (r2 != r7) goto L_0x0632;
    L_0x0659:
        r2 = r4 + 4;
        r2 = r3[r2];
        r7 = 84;
        if (r2 != r7) goto L_0x0632;
    L_0x0661:
        r2 = r4 + 5;
        r2 = r3[r2];
        r7 = 65;
        if (r2 != r7) goto L_0x0632;
    L_0x0669:
        r2 = r4 + 6;
        r2 = r3[r2];
        r7 = 91;
        if (r2 != r7) goto L_0x0632;
    L_0x0671:
        r2 = r19 + -2;
        r2 = r3[r2];
        r7 = 93;
        if (r2 != r7) goto L_0x05ee;
    L_0x0679:
        r2 = r19 + -3;
        r2 = r3[r2];
        r7 = 93;
        if (r2 != r7) goto L_0x05ee;
    L_0x0681:
        r0 = r19;
        r1 = r26;
        r1.pos = r0;
        r2 = r4 + 7;
        r7 = r19 + -10;
        r7 = r7 - r4;
        r0 = r27;
        r0.writeCDATA(r3, r2, r7);
        goto L_0x0632;
    L_0x0692:
        r2 = r4 + 7;
        r0 = r19;
        if (r0 != r2) goto L_0x05ee;
    L_0x0698:
        r2 = r3[r4];
        r7 = 68;
        if (r2 != r7) goto L_0x05ee;
    L_0x069e:
        r2 = r4 + 1;
        r2 = r3[r2];
        r7 = 79;
        if (r2 != r7) goto L_0x05ee;
    L_0x06a6:
        r2 = r4 + 2;
        r2 = r3[r2];
        r7 = 67;
        if (r2 != r7) goto L_0x05ee;
    L_0x06ae:
        r2 = r4 + 3;
        r2 = r3[r2];
        r7 = 84;
        if (r2 != r7) goto L_0x05ee;
    L_0x06b6:
        r2 = r4 + 4;
        r2 = r3[r2];
        r7 = 89;
        if (r2 != r7) goto L_0x05ee;
    L_0x06be:
        r2 = r4 + 5;
        r2 = r3[r2];
        r7 = 80;
        if (r2 != r7) goto L_0x05ee;
    L_0x06c6:
        r2 = 69;
        if (r9 != r2) goto L_0x05ee;
    L_0x06ca:
        r4 = r16;
        r22 = 15;
        goto L_0x0023;
    L_0x06d0:
        r22 = 17;
        r4 = r18 + -1;
        goto L_0x001e;
    L_0x06d6:
        if (r6 >= 0) goto L_0x0870;
    L_0x06d8:
        r6 = r18 + -1;
        r6 = r6 - r4;
        r6 = r6 << 1;
        r23 = 0;
        r19 = r18;
    L_0x06e1:
        r2 = 39;
        if (r9 == r2) goto L_0x06e9;
    L_0x06e5:
        r2 = 34;
        if (r9 != r2) goto L_0x0701;
    L_0x06e9:
        if (r23 != 0) goto L_0x06fa;
    L_0x06eb:
        r23 = r9;
    L_0x06ed:
        r0 = r19;
        r1 = r16;
        if (r0 >= r1) goto L_0x0023;
    L_0x06f3:
        r18 = r19 + 1;
        r9 = r3[r19];
        r19 = r18;
        goto L_0x06e1;
    L_0x06fa:
        r0 = r23;
        if (r0 != r9) goto L_0x06ed;
    L_0x06fe:
        r23 = 0;
        goto L_0x06ed;
    L_0x0701:
        if (r23 != 0) goto L_0x06ed;
    L_0x0703:
        r2 = 91;
        if (r9 != r2) goto L_0x070a;
    L_0x0707:
        r6 = r6 | 1;
        goto L_0x06ed;
    L_0x070a:
        r2 = 93;
        if (r9 != r2) goto L_0x0711;
    L_0x070e:
        r6 = r6 & -2;
        goto L_0x06ed;
    L_0x0711:
        r2 = 62;
        if (r9 != r2) goto L_0x06ed;
    L_0x0715:
        r2 = r6 & 1;
        if (r2 != 0) goto L_0x06ed;
    L_0x0719:
        r0 = r19;
        r1 = r26;
        r1.pos = r0;
        r6 = r6 >> 1;
        r6 = r6 + r4;
        r2 = r19 + -1;
        r7 = r2 - r6;
        r2 = r27;
        r2.emitDoctypeDecl(r3, r4, r5, r6, r7);
        r23 = 60;
        r4 = r16;
        r6 = -1;
        r22 = 1;
        goto L_0x0023;
    L_0x0734:
        r23 = 60;
        r10 = 14;
        r2 = 47;
        if (r9 != r2) goto L_0x0756;
    L_0x073c:
        r0 = r18;
        r1 = r26;
        r1.pos = r0;
        r27.emitEndAttributes();
        r2 = 0;
        r7 = 0;
        r25 = 0;
        r0 = r27;
        r1 = r25;
        r0.emitEndElement(r2, r7, r1);
        r22 = 27;
        r19 = r18;
        goto L_0x0023;
    L_0x0756:
        r2 = 62;
        if (r9 != r2) goto L_0x0769;
    L_0x075a:
        r0 = r18;
        r1 = r26;
        r1.pos = r0;
        r27.emitEndAttributes();
        r22 = 1;
        r19 = r18;
        goto L_0x0023;
    L_0x0769:
        r4 = r18 + -1;
        r22 = 9;
        goto L_0x001e;
    L_0x076f:
        r2 = 32;
        if (r9 == r2) goto L_0x0021;
    L_0x0773:
        r2 = 9;
        if (r9 == r2) goto L_0x0021;
    L_0x0777:
        r2 = 13;
        if (r9 == r2) goto L_0x0021;
    L_0x077b:
        r2 = 10;
        if (r9 == r2) goto L_0x0021;
    L_0x077f:
        r2 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        if (r9 == r2) goto L_0x0021;
    L_0x0783:
        r2 = 8232; // 0x2028 float:1.1535E-41 double:4.067E-320;
        if (r9 != r2) goto L_0x078b;
    L_0x0787:
        r19 = r18;
        goto L_0x0023;
    L_0x078b:
        r2 = r18 - r5;
        r0 = r26;
        r0.pos = r2;
        r0 = r27;
        r0.emitStartAttribute(r3, r4, r5);
        r4 = r16;
        r2 = 61;
        if (r9 != r2) goto L_0x07a2;
    L_0x079c:
        r22 = 11;
        r19 = r18;
        goto L_0x0023;
    L_0x07a2:
        r27.emitEndAttributes();
        r17 = "missing or misplaced '=' after attribute name";
        r22 = 36;
        goto L_0x001e;
    L_0x07ab:
        r2 = 39;
        if (r9 == r2) goto L_0x07b3;
    L_0x07af:
        r2 = 34;
        if (r9 != r2) goto L_0x07bd;
    L_0x07b3:
        r23 = r9;
        r10 = 12;
        r22 = 1;
        r19 = r18;
        goto L_0x0023;
    L_0x07bd:
        r2 = 32;
        if (r9 == r2) goto L_0x0021;
    L_0x07c1:
        r2 = 9;
        if (r9 == r2) goto L_0x0021;
    L_0x07c5:
        r2 = 13;
        if (r9 == r2) goto L_0x0021;
    L_0x07c9:
        r2 = 10;
        if (r9 == r2) goto L_0x0021;
    L_0x07cd:
        r2 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        if (r9 == r2) goto L_0x0021;
    L_0x07d1:
        r2 = 8232; // 0x2028 float:1.1535E-41 double:4.067E-320;
        if (r9 != r2) goto L_0x07d9;
    L_0x07d5:
        r19 = r18;
        goto L_0x0023;
    L_0x07d9:
        r17 = "missing or unquoted attribute value";
        r22 = 36;
        goto L_0x001e;
    L_0x07df:
        r4 = r18 + -1;
        r22 = 5;
        goto L_0x001e;
    L_0x07e5:
        r0 = r18;
        r1 = r26;
        r1.pos = r0;
        r0 = r27;
        r0.emitEndElement(r3, r4, r5);
        r4 = r16;
        r22 = 29;
        goto L_0x001e;
    L_0x07f6:
        r2 = 62;
        if (r9 == r2) goto L_0x0800;
    L_0x07fa:
        r17 = "missing '>'";
        r22 = 36;
        goto L_0x001e;
    L_0x0800:
        r22 = 1;
        r19 = r18;
        goto L_0x0023;
    L_0x0806:
        r21 = r19 - r4;
        if (r21 <= 0) goto L_0x0815;
    L_0x080a:
        r0 = r26;
        r0.pos = r4;	 Catch:{ IOException -> 0x0862 }
        r2 = r21 + 1;
        r0 = r26;
        r0.mark(r2);	 Catch:{ IOException -> 0x0862 }
    L_0x0815:
        r0 = r19;
        r1 = r26;
        r1.pos = r0;	 Catch:{ IOException -> 0x0862 }
        r24 = r26.read();	 Catch:{ IOException -> 0x0862 }
        if (r24 >= 0) goto L_0x0836;
    L_0x0821:
        r2 = 1;
        r0 = r22;
        if (r0 == r2) goto L_0x082c;
    L_0x0826:
        r2 = 28;
        r0 = r22;
        if (r0 != r2) goto L_0x0830;
    L_0x082c:
        r18 = r19;
        goto L_0x006c;
    L_0x0830:
        r22 = 37;
        r18 = r19;
        goto L_0x001e;
    L_0x0836:
        if (r21 <= 0) goto L_0x085e;
    L_0x0838:
        r26.reset();	 Catch:{ IOException -> 0x0862 }
        r0 = r26;
        r1 = r21;
        r0.skip(r1);	 Catch:{ IOException -> 0x0862 }
    L_0x0842:
        r0 = r26;
        r0 = r0.pos;
        r18 = r0;
        r0 = r26;
        r3 = r0.buffer;
        r0 = r26;
        r0 = r0.limit;
        r16 = r0;
        if (r21 <= 0) goto L_0x086d;
    L_0x0854:
        r4 = r18 - r21;
    L_0x0856:
        r19 = r18 + 1;
        r9 = r3[r18];
        r18 = r19;
        goto L_0x001e;
    L_0x085e:
        r26.unread_quick();	 Catch:{ IOException -> 0x0862 }
        goto L_0x0842;
    L_0x0862:
        r14 = move-exception;
        r2 = new java.lang.RuntimeException;
        r7 = r14.getMessage();
        r2.<init>(r7);
        throw r2;
    L_0x086d:
        r4 = r16;
        goto L_0x0856;
    L_0x0870:
        r19 = r18;
        goto L_0x06e1;
    L_0x0874:
        r19 = r18;
        goto L_0x0322;
    L_0x0878:
        r18 = r19;
        goto L_0x001e;
    L_0x087c:
        r19 = r18;
        goto L_0x05fa;
    L_0x0880:
        r19 = r18;
        goto L_0x0271;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xml.XMLParser.parse(gnu.text.LineBufferedReader, gnu.xml.XMLFilter):void");
    }
}

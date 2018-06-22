package gnu.kawa.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class PreProcess {
    static final String JAVA4_FEATURES = "+JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio";
    static final String JAVA5_FEATURES = "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName";
    static final String NO_JAVA4_FEATURES = "-JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android";
    static final String NO_JAVA6_FEATURES = "-JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer";
    static String[] version_features = new String[]{"java1", "-JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java2", "+JAVA2 -JAVA5 -use:java.util.IdentityHashMap -use:java.lang.CharSequence -use:java.lang.Throwable.getCause -use:java.net.URI -use:java.util.regex -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAVA5 -JAVA6 -JAVA6COMPAT5 -JAXP-QName -use:java.text.Normalizer -SAX2 -use:java.nio -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio -use:org.w3c.dom.Node -JAXP-1.3 -use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java4x", "-JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 +use:javax.xml.transform -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6COMPAT5 -Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer", "java6compat5", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName -JAVA6 -JAVA7 +JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java6", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 -JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer -use:java.dyn -Android", "java7", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +use:javax.xml.transform +JAXP-1.3 -JAXP-QName +JAVA6 +JAVA7 -JAVA6COMPAT5 +use:java.text.Normalizer +use:java.dyn -Android", "android", "+JAVA5 +JAVA2 +use:java.util.IdentityHashMap +use:java.lang.CharSequence +use:java.lang.Throwable.getCause +use:java.net.URI +use:java.util.regex +SAX2 +use:java.nio +use:org.w3c.dom.Node +JAXP-1.3 -JAXP-QName -use:javax.xml.transform -JAVA6 -JAVA6COMPAT5 +Android -JAVA6 -JAVA7 -use:java.dyn -use:java.text.Normalizer"};
    String filename;
    Hashtable keywords = new Hashtable();
    int lineno;
    byte[] resultBuffer;
    int resultLength;

    void error(String msg) {
        System.err.println(this.filename + ':' + this.lineno + ": " + msg);
        System.exit(-1);
    }

    public void filter(String filename) throws Throwable {
        if (filter(filename, new BufferedInputStream(new FileInputStream(filename)))) {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(this.resultBuffer, 0, this.resultLength);
            out.close();
            System.err.println("Pre-processed " + filename);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean filter(java.lang.String r32, java.io.BufferedInputStream r33) throws java.lang.Throwable {
        /*
        r31 = this;
        r0 = r32;
        r1 = r31;
        r1.filename = r0;
        r7 = 0;
        r28 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
        r0 = r28;
        r5 = new byte[r0];
        r20 = 0;
        r22 = 0;
        r15 = -1;
        r10 = 0;
        r28 = 1;
        r0 = r28;
        r1 = r31;
        r1.lineno = r0;
        r12 = -1;
        r13 = 0;
        r24 = 0;
        r26 = 0;
        r9 = 0;
        r8 = 0;
    L_0x0023:
        r6 = r33.read();
        if (r6 >= 0) goto L_0x0056;
    L_0x0029:
        if (r24 == 0) goto L_0x004b;
    L_0x002b:
        r0 = r31;
        r0.lineno = r10;
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r29 = "unterminated ";
        r28 = r28.append(r29);
        r0 = r28;
        r28 = r0.append(r9);
        r28 = r28.toString();
        r0 = r31;
        r1 = r28;
        r0.error(r1);
    L_0x004b:
        r0 = r31;
        r0.resultBuffer = r5;
        r0 = r20;
        r1 = r31;
        r1.resultLength = r0;
        return r7;
    L_0x0056:
        r28 = r20 + 10;
        r0 = r5.length;
        r29 = r0;
        r0 = r28;
        r1 = r29;
        if (r0 < r1) goto L_0x007a;
    L_0x0061:
        r28 = r20 * 2;
        r0 = r28;
        r0 = new byte[r0];
        r23 = r0;
        r28 = 0;
        r29 = 0;
        r0 = r28;
        r1 = r23;
        r2 = r29;
        r3 = r20;
        java.lang.System.arraycopy(r5, r0, r1, r2, r3);
        r5 = r23;
    L_0x007a:
        r28 = 10;
        r0 = r28;
        if (r6 != r0) goto L_0x0098;
    L_0x0080:
        if (r20 <= 0) goto L_0x0098;
    L_0x0082:
        r28 = r20 + -1;
        r28 = r5[r28];
        r29 = 13;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x0098;
    L_0x008e:
        r21 = r20 + 1;
        r0 = (byte) r6;
        r28 = r0;
        r5[r20] = r28;
        r20 = r21;
        goto L_0x0023;
    L_0x0098:
        if (r12 < 0) goto L_0x038d;
    L_0x009a:
        if (r15 >= 0) goto L_0x038d;
    L_0x009c:
        if (r8 > 0) goto L_0x038d;
    L_0x009e:
        r28 = 13;
        r0 = r28;
        if (r6 == r0) goto L_0x038d;
    L_0x00a4:
        r28 = 10;
        r0 = r28;
        if (r6 == r0) goto L_0x038d;
    L_0x00aa:
        if (r12 == r13) goto L_0x00b8;
    L_0x00ac:
        r28 = 32;
        r0 = r28;
        if (r6 == r0) goto L_0x038d;
    L_0x00b2:
        r28 = 9;
        r0 = r28;
        if (r6 == r0) goto L_0x038d;
    L_0x00b8:
        r28 = 47;
        r0 = r28;
        if (r6 != r0) goto L_0x0135;
    L_0x00be:
        r28 = 100;
        r0 = r33;
        r1 = r28;
        r0.mark(r1);
        r14 = r33.read();
        r28 = 47;
        r0 = r28;
        if (r14 != r0) goto L_0x0110;
    L_0x00d1:
        r16 = 0;
    L_0x00d3:
        r33.reset();
    L_0x00d6:
        if (r16 == 0) goto L_0x038d;
    L_0x00d8:
        r21 = r20 + 1;
        r28 = 47;
        r5[r20] = r28;
        r20 = r21 + 1;
        r28 = 47;
        r5[r21] = r28;
        r21 = r20 + 1;
        r28 = 32;
        r5[r20] = r28;
        r8 = 1;
        r7 = 1;
    L_0x00ec:
        r28 = 32;
        r0 = r28;
        if (r6 == r0) goto L_0x0389;
    L_0x00f2:
        r28 = 9;
        r0 = r28;
        if (r6 == r0) goto L_0x0389;
    L_0x00f8:
        if (r15 >= 0) goto L_0x0389;
    L_0x00fa:
        r15 = r21;
        if (r24 <= 0) goto L_0x0389;
    L_0x00fe:
        if (r12 == r13) goto L_0x0389;
    L_0x0100:
        r28 = 47;
        r0 = r28;
        if (r6 != r0) goto L_0x0389;
    L_0x0106:
        r6 = r33.read();
        if (r6 >= 0) goto L_0x0138;
    L_0x010c:
        r20 = r21;
        goto L_0x0029;
    L_0x0110:
        r28 = 42;
        r0 = r28;
        if (r14 != r0) goto L_0x0132;
    L_0x0116:
        r14 = r33.read();
        r28 = 32;
        r0 = r28;
        if (r14 == r0) goto L_0x0116;
    L_0x0120:
        r28 = 9;
        r0 = r28;
        if (r14 == r0) goto L_0x0116;
    L_0x0126:
        r28 = 35;
        r0 = r28;
        if (r14 == r0) goto L_0x012f;
    L_0x012c:
        r16 = 1;
    L_0x012e:
        goto L_0x00d3;
    L_0x012f:
        r16 = 0;
        goto L_0x012e;
    L_0x0132:
        r16 = 1;
        goto L_0x00d3;
    L_0x0135:
        r16 = 1;
        goto L_0x00d6;
    L_0x0138:
        r28 = 47;
        r0 = r28;
        if (r6 == r0) goto L_0x0182;
    L_0x013e:
        r20 = r21 + 1;
        r28 = 47;
        r5[r21] = r28;
    L_0x0144:
        r0 = (byte) r6;
        r28 = r0;
        r5[r20] = r28;
        r20 = r20 + 1;
        r28 = 13;
        r0 = r28;
        if (r6 == r0) goto L_0x0157;
    L_0x0151:
        r28 = 10;
        r0 = r28;
        if (r6 != r0) goto L_0x0378;
    L_0x0157:
        r17 = -1;
        r19 = 0;
        r18 = r22;
    L_0x015d:
        r28 = r20 + -1;
        r0 = r18;
        r1 = r28;
        if (r0 >= r1) goto L_0x01a8;
    L_0x0165:
        r28 = r5[r18];
        r29 = 32;
        r0 = r28;
        r1 = r29;
        if (r0 == r1) goto L_0x017f;
    L_0x016f:
        r28 = r5[r18];
        r29 = 9;
        r0 = r28;
        r1 = r29;
        if (r0 == r1) goto L_0x017f;
    L_0x0179:
        r19 = r18;
        if (r17 >= 0) goto L_0x017f;
    L_0x017d:
        r17 = r18;
    L_0x017f:
        r18 = r18 + 1;
        goto L_0x015d;
    L_0x0182:
        r6 = r33.read();
        if (r6 >= 0) goto L_0x018c;
    L_0x0188:
        r20 = r21;
        goto L_0x0029;
    L_0x018c:
        r8 = -1;
        r7 = 1;
        r28 = 32;
        r0 = r28;
        if (r6 != r0) goto L_0x0389;
    L_0x0194:
        r6 = r33.read();
        r28 = 32;
        r0 = r28;
        if (r6 == r0) goto L_0x01a4;
    L_0x019e:
        r28 = 9;
        r0 = r28;
        if (r6 != r0) goto L_0x0389;
    L_0x01a4:
        r15 = -1;
        r20 = r21;
        goto L_0x0144;
    L_0x01a8:
        r28 = r19 - r17;
        r29 = 4;
        r0 = r28;
        r1 = r29;
        if (r0 < r1) goto L_0x02a4;
    L_0x01b2:
        r28 = r5[r17];
        r29 = 47;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x02a4;
    L_0x01bc:
        r28 = r17 + 1;
        r28 = r5[r28];
        r29 = 42;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x02a4;
    L_0x01c8:
        r28 = r19 + -1;
        r28 = r5[r28];
        r29 = 42;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x02a4;
    L_0x01d4:
        r28 = r5[r19];
        r29 = 47;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x02a4;
    L_0x01de:
        r17 = r17 + 2;
    L_0x01e0:
        r0 = r17;
        r1 = r19;
        if (r0 >= r1) goto L_0x01f3;
    L_0x01e6:
        r28 = r5[r17];
        r29 = 32;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x01f3;
    L_0x01f0:
        r17 = r17 + 1;
        goto L_0x01e0;
    L_0x01f3:
        r19 = r19 + -2;
    L_0x01f5:
        r0 = r19;
        r1 = r17;
        if (r0 <= r1) goto L_0x0208;
    L_0x01fb:
        r28 = r5[r19];
        r29 = 32;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x0208;
    L_0x0205:
        r19 = r19 + -1;
        goto L_0x01f5;
    L_0x0208:
        r28 = r5[r17];
        r29 = 35;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x02a4;
    L_0x0212:
        r11 = new java.lang.String;
        r28 = r19 - r17;
        r28 = r28 + 1;
        r29 = "ISO-8859-1";
        r0 = r17;
        r1 = r28;
        r2 = r29;
        r11.<init>(r5, r0, r1, r2);
        r28 = 32;
        r0 = r28;
        r27 = r11.indexOf(r0);
        r0 = r31;
        r10 = r0.lineno;
        if (r27 <= 0) goto L_0x02b9;
    L_0x0231:
        r28 = 0;
        r0 = r28;
        r1 = r27;
        r9 = r11.substring(r0, r1);
        r0 = r27;
        r28 = r11.substring(r0);
        r25 = r28.trim();
        r0 = r31;
        r0 = r0.keywords;
        r28 = r0;
        r0 = r28;
        r1 = r25;
        r4 = r0.get(r1);
    L_0x0253:
        r28 = "#ifdef";
        r0 = r28;
        r28 = r0.equals(r9);
        if (r28 != 0) goto L_0x0267;
    L_0x025d:
        r28 = "#ifndef";
        r0 = r28;
        r28 = r0.equals(r9);
        if (r28 == 0) goto L_0x02e8;
    L_0x0267:
        if (r4 != 0) goto L_0x029f;
    L_0x0269:
        r28 = java.lang.System.err;
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r0 = r29;
        r1 = r32;
        r29 = r0.append(r1);
        r30 = ":";
        r29 = r29.append(r30);
        r0 = r31;
        r0 = r0.lineno;
        r30 = r0;
        r29 = r29.append(r30);
        r30 = ": warning - undefined keyword: ";
        r29 = r29.append(r30);
        r0 = r29;
        r1 = r25;
        r29 = r0.append(r1);
        r29 = r29.toString();
        r28.println(r29);
        r4 = java.lang.Boolean.FALSE;
    L_0x029f:
        r24 = r24 + 1;
        if (r26 <= 0) goto L_0x02be;
    L_0x02a3:
        r12 = r13;
    L_0x02a4:
        r22 = r20;
        r15 = -1;
        r13 = 0;
        r0 = r31;
        r0 = r0.lineno;
        r28 = r0;
        r28 = r28 + 1;
        r0 = r28;
        r1 = r31;
        r1.lineno = r0;
        r8 = 0;
        goto L_0x0023;
    L_0x02b9:
        r9 = r11;
        r25 = "";
        r4 = 0;
        goto L_0x0253;
    L_0x02be:
        r28 = 3;
        r0 = r28;
        r28 = r9.charAt(r0);
        r29 = 110; // 0x6e float:1.54E-43 double:5.43E-322;
        r0 = r28;
        r1 = r29;
        if (r0 != r1) goto L_0x02e2;
    L_0x02ce:
        r28 = 1;
    L_0x02d0:
        r29 = java.lang.Boolean.FALSE;
        r0 = r29;
        if (r4 != r0) goto L_0x02e5;
    L_0x02d6:
        r29 = 1;
    L_0x02d8:
        r0 = r28;
        r1 = r29;
        if (r0 == r1) goto L_0x02a4;
    L_0x02de:
        r12 = r13;
        r26 = r24;
        goto L_0x02a4;
    L_0x02e2:
        r28 = 0;
        goto L_0x02d0;
    L_0x02e5:
        r29 = 0;
        goto L_0x02d8;
    L_0x02e8:
        r28 = "#else";
        r0 = r28;
        r28 = r0.equals(r9);
        if (r28 == 0) goto L_0x0321;
    L_0x02f2:
        if (r24 != 0) goto L_0x0311;
    L_0x02f4:
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r29 = "unexpected ";
        r28 = r28.append(r29);
        r0 = r28;
        r28 = r0.append(r9);
        r28 = r28.toString();
        r0 = r31;
        r1 = r28;
        r0.error(r1);
        goto L_0x02a4;
    L_0x0311:
        r0 = r24;
        r1 = r26;
        if (r0 != r1) goto L_0x031b;
    L_0x0317:
        r12 = -1;
        r26 = 0;
        goto L_0x02a4;
    L_0x031b:
        r12 = r13;
        if (r26 != 0) goto L_0x02a4;
    L_0x031e:
        r26 = r24;
        goto L_0x02a4;
    L_0x0321:
        r28 = "#endif";
        r0 = r28;
        r28 = r0.equals(r9);
        if (r28 == 0) goto L_0x035a;
    L_0x032b:
        if (r24 != 0) goto L_0x0349;
    L_0x032d:
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r29 = "unexpected ";
        r28 = r28.append(r29);
        r0 = r28;
        r28 = r0.append(r9);
        r28 = r28.toString();
        r0 = r31;
        r1 = r28;
        r0.error(r1);
    L_0x0349:
        r0 = r24;
        r1 = r26;
        if (r0 != r1) goto L_0x0356;
    L_0x034f:
        r26 = 0;
        r12 = -1;
    L_0x0352:
        r24 = r24 + -1;
        goto L_0x02a4;
    L_0x0356:
        if (r26 <= 0) goto L_0x0352;
    L_0x0358:
        r12 = r13;
        goto L_0x0352;
    L_0x035a:
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r29 = "unknown command: ";
        r28 = r28.append(r29);
        r0 = r28;
        r28 = r0.append(r11);
        r28 = r28.toString();
        r0 = r31;
        r1 = r28;
        r0.error(r1);
        goto L_0x02a4;
    L_0x0378:
        if (r15 >= 0) goto L_0x0023;
    L_0x037a:
        r28 = 9;
        r0 = r28;
        if (r6 != r0) goto L_0x0386;
    L_0x0380:
        r28 = r13 + 8;
        r13 = r28 & -8;
    L_0x0384:
        goto L_0x0023;
    L_0x0386:
        r13 = r13 + 1;
        goto L_0x0384;
    L_0x0389:
        r20 = r21;
        goto L_0x0144;
    L_0x038d:
        r21 = r20;
        goto L_0x00ec;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.util.PreProcess.filter(java.lang.String, java.io.BufferedInputStream):boolean");
    }

    void handleArg(String arg) {
        int i = 1;
        if (arg.charAt(0) == '%') {
            arg = arg.substring(1);
            int i2 = 0;
            while (true) {
                if (i2 >= version_features.length) {
                    System.err.println("Unknown version: " + arg);
                    System.exit(-1);
                }
                if (arg.equals(version_features[i2])) {
                    break;
                }
                i2 += 2;
            }
            String features = version_features[i2 + 1];
            System.err.println("(variant " + arg + " maps to: " + features + ")");
            StringTokenizer tokenizer = new StringTokenizer(features);
            while (tokenizer.hasMoreTokens()) {
                handleArg(tokenizer.nextToken());
            }
        } else if (arg.charAt(0) == '+') {
            this.keywords.put(arg.substring(1), Boolean.TRUE);
        } else if (arg.charAt(0) == '-') {
            int eq = arg.indexOf(61);
            if (eq > 1) {
                if (arg.charAt(1) == '-') {
                    i = 2;
                }
                String keyword = arg.substring(i, eq);
                String value = arg.substring(eq + 1);
                Boolean b = Boolean.FALSE;
                if (value.equalsIgnoreCase("true")) {
                    b = Boolean.TRUE;
                } else if (!value.equalsIgnoreCase("false")) {
                    System.err.println("invalid value " + value + " for " + keyword);
                    System.exit(-1);
                }
                this.keywords.put(keyword, b);
                return;
            }
            this.keywords.put(arg.substring(1), Boolean.FALSE);
        } else {
            try {
                filter(arg);
            } catch (Throwable ex) {
                System.err.println("caught " + ex);
                ex.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public static void main(String[] args) {
        PreProcess pp = new PreProcess();
        pp.keywords.put("true", Boolean.TRUE);
        pp.keywords.put("false", Boolean.FALSE);
        for (String handleArg : args) {
            pp.handleArg(handleArg);
        }
    }
}

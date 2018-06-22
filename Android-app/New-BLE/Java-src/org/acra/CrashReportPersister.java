package org.acra;

import android.content.Context;
import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import gnu.kawa.servlet.HttpRequestContext;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;
import org.acra.collector.CrashReportData;

final class CrashReportPersister {
    private static final int CONTINUE = 3;
    private static final int IGNORE = 5;
    private static final int KEY_DONE = 4;
    private static final String LINE_SEPARATOR = "\n";
    private static final int NONE = 0;
    private static final int SLASH = 1;
    private static final int UNICODE = 2;
    private final Context context;

    CrashReportPersister(Context context) {
        this.context = context;
    }

    public CrashReportData load(String fileName) throws IOException {
        FileInputStream in = this.context.openFileInput(fileName);
        if (in == null) {
            throw new IllegalArgumentException("Invalid crash report fileName : " + fileName);
        }
        try {
            CrashReportData load;
            BufferedInputStream bis = new BufferedInputStream(in, 8192);
            bis.mark(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
            boolean isEbcdic = isEbcdic(bis);
            bis.reset();
            if (isEbcdic) {
                load = load(new InputStreamReader(bis));
                in.close();
            } else {
                load = load(new InputStreamReader(bis, "ISO8859-1"));
            }
            return load;
        } finally {
            in.close();
        }
    }

    public void store(CrashReportData crashData, String fileName) throws IOException {
        OutputStream out = this.context.openFileOutput(fileName, 0);
        try {
            StringBuilder buffer = new StringBuilder(HttpRequestContext.HTTP_OK);
            OutputStreamWriter writer = new OutputStreamWriter(out, "ISO8859_1");
            for (Entry<ReportField, String> entry : crashData.entrySet()) {
                dumpString(buffer, ((ReportField) entry.getKey()).toString(), true);
                buffer.append('=');
                dumpString(buffer, (String) entry.getValue(), false);
                buffer.append(LINE_SEPARATOR);
                writer.write(buffer.toString());
                buffer.setLength(0);
            }
            writer.flush();
        } finally {
            out.close();
        }
    }

    private boolean isEbcdic(BufferedInputStream in) throws IOException {
        byte b;
        do {
            b = (byte) in.read();
            if (b == (byte) -1 || b == (byte) 35 || b == (byte) 10 || b == Opcode.MOVEF_16) {
                return false;
            }
        } while (b != (byte) 21);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized org.acra.collector.CrashReportData load(java.io.Reader r22) throws java.io.IOException {
        /*
        r21 = this;
        monitor-enter(r21);
        r11 = 0;
        r17 = 0;
        r4 = 0;
        r19 = 40;
        r0 = r19;
        r3 = new char[r0];	 Catch:{ all -> 0x003d }
        r14 = 0;
        r10 = -1;
        r7 = 1;
        r5 = new org.acra.collector.CrashReportData;	 Catch:{ all -> 0x003d }
        r5.<init>();	 Catch:{ all -> 0x003d }
        r2 = new java.io.BufferedReader;	 Catch:{ all -> 0x003d }
        r19 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = r22;
        r1 = r19;
        r2.<init>(r0, r1);	 Catch:{ all -> 0x003d }
        r15 = r14;
    L_0x001f:
        r8 = r2.read();	 Catch:{ all -> 0x003d }
        r19 = -1;
        r0 = r19;
        if (r8 != r0) goto L_0x0040;
    L_0x0029:
        r19 = 2;
        r0 = r19;
        if (r11 != r0) goto L_0x0181;
    L_0x002f:
        r19 = 4;
        r0 = r19;
        if (r4 > r0) goto L_0x0181;
    L_0x0035:
        r19 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x003d }
        r20 = "luni.08";
        r19.<init>(r20);	 Catch:{ all -> 0x003d }
        throw r19;	 Catch:{ all -> 0x003d }
    L_0x003d:
        r19 = move-exception;
        monitor-exit(r21);
        throw r19;
    L_0x0040:
        r13 = (char) r8;
        r0 = r3.length;	 Catch:{ all -> 0x003d }
        r19 = r0;
        r0 = r19;
        if (r15 != r0) goto L_0x005d;
    L_0x0048:
        r0 = r3.length;	 Catch:{ all -> 0x003d }
        r19 = r0;
        r19 = r19 * 2;
        r0 = r19;
        r12 = new char[r0];	 Catch:{ all -> 0x003d }
        r19 = 0;
        r20 = 0;
        r0 = r19;
        r1 = r20;
        java.lang.System.arraycopy(r3, r0, r12, r1, r15);	 Catch:{ all -> 0x003d }
        r3 = r12;
    L_0x005d:
        r19 = 2;
        r0 = r19;
        if (r11 != r0) goto L_0x00a0;
    L_0x0063:
        r19 = 16;
        r0 = r19;
        r6 = java.lang.Character.digit(r13, r0);	 Catch:{ all -> 0x003d }
        if (r6 < 0) goto L_0x0091;
    L_0x006d:
        r19 = r17 << 4;
        r17 = r19 + r6;
        r4 = r4 + 1;
        r19 = 4;
        r0 = r19;
        if (r4 < r0) goto L_0x001f;
    L_0x0079:
        r11 = 0;
        r14 = r15 + 1;
        r0 = r17;
        r0 = (char) r0;	 Catch:{ all -> 0x003d }
        r19 = r0;
        r3[r15] = r19;	 Catch:{ all -> 0x003d }
        r19 = 10;
        r0 = r19;
        if (r13 == r0) goto L_0x009f;
    L_0x0089:
        r19 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        r0 = r19;
        if (r13 == r0) goto L_0x009f;
    L_0x008f:
        r15 = r14;
        goto L_0x001f;
    L_0x0091:
        r19 = 4;
        r0 = r19;
        if (r4 > r0) goto L_0x0079;
    L_0x0097:
        r19 = new java.lang.IllegalArgumentException;	 Catch:{ all -> 0x003d }
        r20 = "luni.09";
        r19.<init>(r20);	 Catch:{ all -> 0x003d }
        throw r19;	 Catch:{ all -> 0x003d }
    L_0x009f:
        r15 = r14;
    L_0x00a0:
        r19 = 1;
        r0 = r19;
        if (r11 != r0) goto L_0x00d5;
    L_0x00a6:
        r11 = 0;
        switch(r13) {
            case 10: goto L_0x00bd;
            case 13: goto L_0x00ba;
            case 98: goto L_0x00c0;
            case 102: goto L_0x00c3;
            case 110: goto L_0x00c6;
            case 114: goto L_0x00c9;
            case 116: goto L_0x00cc;
            case 117: goto L_0x00cf;
            case 133: goto L_0x00bd;
            default: goto L_0x00aa;
        };	 Catch:{ all -> 0x003d }
    L_0x00aa:
        r7 = 0;
        r19 = 4;
        r0 = r19;
        if (r11 != r0) goto L_0x00b3;
    L_0x00b1:
        r10 = r15;
        r11 = 0;
    L_0x00b3:
        r14 = r15 + 1;
        r3[r15] = r13;	 Catch:{ all -> 0x003d }
        r15 = r14;
        goto L_0x001f;
    L_0x00ba:
        r11 = 3;
        goto L_0x001f;
    L_0x00bd:
        r11 = 5;
        goto L_0x001f;
    L_0x00c0:
        r13 = 8;
        goto L_0x00aa;
    L_0x00c3:
        r13 = 12;
        goto L_0x00aa;
    L_0x00c6:
        r13 = 10;
        goto L_0x00aa;
    L_0x00c9:
        r13 = 13;
        goto L_0x00aa;
    L_0x00cc:
        r13 = 9;
        goto L_0x00aa;
    L_0x00cf:
        r11 = 2;
        r4 = 0;
        r17 = r4;
        goto L_0x001f;
    L_0x00d5:
        switch(r13) {
            case 10: goto L_0x0119;
            case 13: goto L_0x0122;
            case 33: goto L_0x00f8;
            case 35: goto L_0x00f8;
            case 58: goto L_0x0168;
            case 61: goto L_0x0168;
            case 92: goto L_0x015e;
            case 133: goto L_0x0122;
            default: goto L_0x00d8;
        };	 Catch:{ all -> 0x003d }
    L_0x00d8:
        r19 = java.lang.Character.isWhitespace(r13);	 Catch:{ all -> 0x003d }
        if (r19 == 0) goto L_0x0172;
    L_0x00de:
        r19 = 3;
        r0 = r19;
        if (r11 != r0) goto L_0x00e5;
    L_0x00e4:
        r11 = 5;
    L_0x00e5:
        if (r15 == 0) goto L_0x001f;
    L_0x00e7:
        if (r15 == r10) goto L_0x001f;
    L_0x00e9:
        r19 = 5;
        r0 = r19;
        if (r11 == r0) goto L_0x001f;
    L_0x00ef:
        r19 = -1;
        r0 = r19;
        if (r10 != r0) goto L_0x0172;
    L_0x00f5:
        r11 = 4;
        goto L_0x001f;
    L_0x00f8:
        if (r7 == 0) goto L_0x00d8;
    L_0x00fa:
        r8 = r2.read();	 Catch:{ all -> 0x003d }
        r19 = -1;
        r0 = r19;
        if (r8 == r0) goto L_0x001f;
    L_0x0104:
        r13 = (char) r8;	 Catch:{ all -> 0x003d }
        r19 = 13;
        r0 = r19;
        if (r13 == r0) goto L_0x001f;
    L_0x010b:
        r19 = 10;
        r0 = r19;
        if (r13 == r0) goto L_0x001f;
    L_0x0111:
        r19 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
        r0 = r19;
        if (r13 != r0) goto L_0x00fa;
    L_0x0117:
        goto L_0x001f;
    L_0x0119:
        r19 = 3;
        r0 = r19;
        if (r11 != r0) goto L_0x0122;
    L_0x011f:
        r11 = 5;
        goto L_0x001f;
    L_0x0122:
        r11 = 0;
        r7 = 1;
        if (r15 > 0) goto L_0x012a;
    L_0x0126:
        if (r15 != 0) goto L_0x0159;
    L_0x0128:
        if (r10 != 0) goto L_0x0159;
    L_0x012a:
        r19 = -1;
        r0 = r19;
        if (r10 != r0) goto L_0x0131;
    L_0x0130:
        r10 = r15;
    L_0x0131:
        r16 = new java.lang.String;	 Catch:{ all -> 0x003d }
        r19 = 0;
        r0 = r16;
        r1 = r19;
        r0.<init>(r3, r1, r15);	 Catch:{ all -> 0x003d }
        r19 = org.acra.ReportField.class;
        r20 = 0;
        r0 = r16;
        r1 = r20;
        r20 = r0.substring(r1, r10);	 Catch:{ all -> 0x003d }
        r19 = java.lang.Enum.valueOf(r19, r20);	 Catch:{ all -> 0x003d }
        r0 = r16;
        r20 = r0.substring(r10);	 Catch:{ all -> 0x003d }
        r0 = r19;
        r1 = r20;
        r5.put(r0, r1);	 Catch:{ all -> 0x003d }
    L_0x0159:
        r10 = -1;
        r14 = 0;
        r15 = r14;
        goto L_0x001f;
    L_0x015e:
        r19 = 4;
        r0 = r19;
        if (r11 != r0) goto L_0x0165;
    L_0x0164:
        r10 = r15;
    L_0x0165:
        r11 = 1;
        goto L_0x001f;
    L_0x0168:
        r19 = -1;
        r0 = r19;
        if (r10 != r0) goto L_0x00d8;
    L_0x016e:
        r11 = 0;
        r10 = r15;
        goto L_0x001f;
    L_0x0172:
        r19 = 5;
        r0 = r19;
        if (r11 == r0) goto L_0x017e;
    L_0x0178:
        r19 = 3;
        r0 = r19;
        if (r11 != r0) goto L_0x00aa;
    L_0x017e:
        r11 = 0;
        goto L_0x00aa;
    L_0x0181:
        r19 = -1;
        r0 = r19;
        if (r10 != r0) goto L_0x018a;
    L_0x0187:
        if (r15 <= 0) goto L_0x018a;
    L_0x0189:
        r10 = r15;
    L_0x018a:
        if (r10 < 0) goto L_0x01d1;
    L_0x018c:
        r16 = new java.lang.String;	 Catch:{ all -> 0x003d }
        r19 = 0;
        r0 = r16;
        r1 = r19;
        r0.<init>(r3, r1, r15);	 Catch:{ all -> 0x003d }
        r19 = org.acra.ReportField.class;
        r20 = 0;
        r0 = r16;
        r1 = r20;
        r20 = r0.substring(r1, r10);	 Catch:{ all -> 0x003d }
        r9 = java.lang.Enum.valueOf(r19, r20);	 Catch:{ all -> 0x003d }
        r9 = (org.acra.ReportField) r9;	 Catch:{ all -> 0x003d }
        r0 = r16;
        r18 = r0.substring(r10);	 Catch:{ all -> 0x003d }
        r19 = 1;
        r0 = r19;
        if (r11 != r0) goto L_0x01cc;
    L_0x01b5:
        r19 = new java.lang.StringBuilder;	 Catch:{ all -> 0x003d }
        r19.<init>();	 Catch:{ all -> 0x003d }
        r0 = r19;
        r1 = r18;
        r19 = r0.append(r1);	 Catch:{ all -> 0x003d }
        r20 = "\u0000";
        r19 = r19.append(r20);	 Catch:{ all -> 0x003d }
        r18 = r19.toString();	 Catch:{ all -> 0x003d }
    L_0x01cc:
        r0 = r18;
        r5.put(r9, r0);	 Catch:{ all -> 0x003d }
    L_0x01d1:
        monitor-exit(r21);
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.CrashReportPersister.load(java.io.Reader):org.acra.collector.CrashReportData");
    }

    private void dumpString(StringBuilder buffer, String string, boolean key) {
        int i = 0;
        if (!key && 0 < string.length() && string.charAt(0) == ' ') {
            buffer.append("\\ ");
            i = 0 + 1;
        }
        while (i < string.length()) {
            char ch = string.charAt(i);
            switch (ch) {
                case '\t':
                    buffer.append("\\t");
                    break;
                case '\n':
                    buffer.append("\\n");
                    break;
                case '\f':
                    buffer.append("\\f");
                    break;
                case '\r':
                    buffer.append("\\r");
                    break;
                default:
                    if ("\\#!=:".indexOf(ch) >= 0 || (key && ch == ' ')) {
                        buffer.append('\\');
                    }
                    if (ch >= ' ' && ch <= '~') {
                        buffer.append(ch);
                        break;
                    }
                    String hex = Integer.toHexString(ch);
                    buffer.append("\\u");
                    for (int j = 0; j < 4 - hex.length(); j++) {
                        buffer.append("0");
                    }
                    buffer.append(hex);
                    break;
                    break;
            }
            i++;
        }
    }
}

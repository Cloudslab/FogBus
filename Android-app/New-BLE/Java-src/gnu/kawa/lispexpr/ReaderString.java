package gnu.kawa.lispexpr;

import gnu.text.Lexer;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderString extends ReadTableEntry {
    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        return readString(in, ch, count);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String readString(gnu.text.Lexer r11, int r12, int r13) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r10 = 10;
        r5 = r11.tokenBufferLength;
        r2 = r11.getPort();
        r4 = 0;
        r1 = r12;
        r7 = r2 instanceof gnu.mapping.InPort;
        if (r7 == 0) goto L_0x0019;
    L_0x000e:
        r7 = r2;
        r7 = (gnu.mapping.InPort) r7;
        r4 = r7.readState;
        r7 = r2;
        r7 = (gnu.mapping.InPort) r7;
        r8 = (char) r12;
        r7.readState = r8;
    L_0x0019:
        r3 = r1;
        r7 = 13;
        if (r3 != r7) goto L_0x003f;
    L_0x001e:
        r1 = r2.read();	 Catch:{ all -> 0x0065 }
        if (r1 == r10) goto L_0x0019;
    L_0x0024:
        if (r1 != r12) goto L_0x0057;
    L_0x0026:
        r7 = new java.lang.String;	 Catch:{ all -> 0x0065 }
        r8 = r11.tokenBuffer;	 Catch:{ all -> 0x0065 }
        r9 = r11.tokenBufferLength;	 Catch:{ all -> 0x0065 }
        r9 = r9 - r5;
        r7.<init>(r8, r5, r9);	 Catch:{ all -> 0x0065 }
        r7 = r7.intern();	 Catch:{ all -> 0x0065 }
        r11.tokenBufferLength = r5;
        r8 = r2 instanceof gnu.mapping.InPort;
        if (r8 == 0) goto L_0x003e;
    L_0x003a:
        r2 = (gnu.mapping.InPort) r2;
        r2.readState = r4;
    L_0x003e:
        return r7;
    L_0x003f:
        r7 = r2.pos;	 Catch:{ all -> 0x0065 }
        r8 = r2.limit;	 Catch:{ all -> 0x0065 }
        if (r7 >= r8) goto L_0x0052;
    L_0x0045:
        if (r3 == r10) goto L_0x0052;
    L_0x0047:
        r7 = r2.buffer;	 Catch:{ all -> 0x0065 }
        r8 = r2.pos;	 Catch:{ all -> 0x0065 }
        r9 = r8 + 1;
        r2.pos = r9;	 Catch:{ all -> 0x0065 }
        r1 = r7[r8];	 Catch:{ all -> 0x0065 }
        goto L_0x0024;
    L_0x0052:
        r1 = r2.read();	 Catch:{ all -> 0x0065 }
        goto L_0x0024;
    L_0x0057:
        switch(r1) {
            case 13: goto L_0x0071;
            case 92: goto L_0x0082;
            default: goto L_0x005a;
        };	 Catch:{ all -> 0x0065 }
    L_0x005a:
        if (r1 >= 0) goto L_0x0061;
    L_0x005c:
        r7 = "unexpected EOF in string literal";
        r11.eofError(r7);	 Catch:{ all -> 0x0065 }
    L_0x0061:
        r11.tokenBufferAppend(r1);	 Catch:{ all -> 0x0065 }
        goto L_0x0019;
    L_0x0065:
        r7 = move-exception;
        r11.tokenBufferLength = r5;
        r8 = r2 instanceof gnu.mapping.InPort;
        if (r8 == 0) goto L_0x0070;
    L_0x006c:
        r2 = (gnu.mapping.InPort) r2;
        r2.readState = r4;
    L_0x0070:
        throw r7;
    L_0x0071:
        r7 = r2.getConvertCR();	 Catch:{ all -> 0x0065 }
        if (r7 == 0) goto L_0x007d;
    L_0x0077:
        r6 = 10;
    L_0x0079:
        r11.tokenBufferAppend(r6);	 Catch:{ all -> 0x0065 }
        goto L_0x0019;
    L_0x007d:
        r6 = 13;
        r1 = 32;
        goto L_0x0079;
    L_0x0082:
        r7 = r11 instanceof gnu.kawa.lispexpr.LispReader;	 Catch:{ all -> 0x0065 }
        if (r7 == 0) goto L_0x0094;
    L_0x0086:
        r0 = r11;
        r0 = (gnu.kawa.lispexpr.LispReader) r0;	 Catch:{ all -> 0x0065 }
        r7 = r0;
        r1 = r7.readEscape();	 Catch:{ all -> 0x0065 }
    L_0x008e:
        r7 = -2;
        if (r1 != r7) goto L_0x005a;
    L_0x0091:
        r1 = 10;
        goto L_0x0019;
    L_0x0094:
        r1 = r2.read();	 Catch:{ all -> 0x0065 }
        goto L_0x008e;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderString.readString(gnu.text.Lexer, int, int):java.lang.String");
    }
}

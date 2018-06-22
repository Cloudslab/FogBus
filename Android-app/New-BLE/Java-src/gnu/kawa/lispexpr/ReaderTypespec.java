package gnu.kawa.lispexpr;

public class ReaderTypespec extends ReadTableEntry {
    public int getKind() {
        return 6;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object read(gnu.text.Lexer r12, int r13, int r14) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r11 = this;
        r7 = r12.tokenBufferLength;
        r3 = r12.getPort();
        r5 = gnu.kawa.lispexpr.ReadTable.getCurrent();
        r6 = 0;
        r12.tokenBufferAppend(r13);
        r1 = r13;
        r8 = r3 instanceof gnu.mapping.InPort;
        if (r8 == 0) goto L_0x001e;
    L_0x0013:
        r8 = r3;
        r8 = (gnu.mapping.InPort) r8;
        r6 = r8.readState;
        r8 = r3;
        r8 = (gnu.mapping.InPort) r8;
        r9 = (char) r13;
        r8.readState = r9;
    L_0x001e:
        r2 = 0;
    L_0x001f:
        r4 = r1;
        r8 = r3.pos;	 Catch:{ all -> 0x0071 }
        r9 = r3.limit;	 Catch:{ all -> 0x0071 }
        if (r8 >= r9) goto L_0x0045;
    L_0x0026:
        r8 = 10;
        if (r4 == r8) goto L_0x0045;
    L_0x002a:
        r8 = r3.buffer;	 Catch:{ all -> 0x0071 }
        r9 = r3.pos;	 Catch:{ all -> 0x0071 }
        r10 = r9 + 1;
        r3.pos = r10;	 Catch:{ all -> 0x0071 }
        r1 = r8[r9];	 Catch:{ all -> 0x0071 }
    L_0x0034:
        r8 = 92;
        if (r1 != r8) goto L_0x004f;
    L_0x0038:
        r8 = r12 instanceof gnu.kawa.lispexpr.LispReader;	 Catch:{ all -> 0x0071 }
        if (r8 == 0) goto L_0x004a;
    L_0x003c:
        r0 = r12;
        r0 = (gnu.kawa.lispexpr.LispReader) r0;	 Catch:{ all -> 0x0071 }
        r8 = r0;
        r1 = r8.readEscape();	 Catch:{ all -> 0x0071 }
        goto L_0x001f;
    L_0x0045:
        r1 = r3.read();	 Catch:{ all -> 0x0071 }
        goto L_0x0034;
    L_0x004a:
        r1 = r3.read();	 Catch:{ all -> 0x0071 }
        goto L_0x001f;
    L_0x004f:
        if (r2 != 0) goto L_0x0059;
    L_0x0051:
        r8 = 91;
        if (r1 != r8) goto L_0x0059;
    L_0x0055:
        r8 = 1;
        r2 = 1;
        if (r8 == r2) goto L_0x006d;
    L_0x0059:
        if (r2 == 0) goto L_0x0062;
    L_0x005b:
        r8 = 93;
        if (r1 != r8) goto L_0x0062;
    L_0x005f:
        r2 = 0;
        if (r2 == 0) goto L_0x006d;
    L_0x0062:
        r8 = r5.lookup(r1);	 Catch:{ all -> 0x0071 }
        r8 = r8.getKind();	 Catch:{ all -> 0x0071 }
        r9 = 2;
        if (r8 != r9) goto L_0x007d;
    L_0x006d:
        r12.tokenBufferAppend(r1);	 Catch:{ all -> 0x0071 }
        goto L_0x001f;
    L_0x0071:
        r8 = move-exception;
        r12.tokenBufferLength = r7;
        r9 = r3 instanceof gnu.mapping.InPort;
        if (r9 == 0) goto L_0x007c;
    L_0x0078:
        r3 = (gnu.mapping.InPort) r3;
        r3.readState = r6;
    L_0x007c:
        throw r8;
    L_0x007d:
        r12.unread(r1);	 Catch:{ all -> 0x0071 }
        r8 = new java.lang.String;	 Catch:{ all -> 0x0071 }
        r9 = r12.tokenBuffer;	 Catch:{ all -> 0x0071 }
        r10 = r12.tokenBufferLength;	 Catch:{ all -> 0x0071 }
        r10 = r10 - r7;
        r8.<init>(r9, r7, r10);	 Catch:{ all -> 0x0071 }
        r8 = r8.intern();	 Catch:{ all -> 0x0071 }
        r12.tokenBufferLength = r7;
        r9 = r3 instanceof gnu.mapping.InPort;
        if (r9 == 0) goto L_0x0098;
    L_0x0094:
        r3 = (gnu.mapping.InPort) r3;
        r3.readState = r6;
    L_0x0098:
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderTypespec.read(gnu.text.Lexer, int, int):java.lang.Object");
    }
}

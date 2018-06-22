package gnu.kawa.lispexpr;

import gnu.lists.Pair;
import gnu.text.Lexer;
import gnu.text.LineBufferedReader;
import gnu.text.SyntaxException;
import java.io.IOException;

public class ReaderParens extends ReadTableEntry {
    private static ReaderParens instance;
    char close;
    Object command;
    int kind;
    char open;

    public int getKind() {
        return this.kind;
    }

    public static ReaderParens getInstance(char open, char close) {
        return getInstance(open, close, 5);
    }

    public static ReaderParens getInstance(char open, char close, int kind) {
        if (open != '(' || close != ')' || kind != 5) {
            return new ReaderParens(open, close, kind, null);
        }
        if (instance == null) {
            instance = new ReaderParens(open, close, kind, null);
        }
        return instance;
    }

    public static ReaderParens getInstance(char open, char close, int kind, Object command) {
        if (command == null) {
            return getInstance(open, close, kind);
        }
        return new ReaderParens(open, close, kind, command);
    }

    public ReaderParens(char open, char close, int kind, Object command) {
        this.open = open;
        this.close = close;
        this.kind = kind;
        this.command = command;
    }

    public Object read(Lexer in, int ch, int count) throws IOException, SyntaxException {
        Object r = readList((LispReader) in, ch, count, this.close);
        if (this.command == null) {
            return r;
        }
        LineBufferedReader port = in.getPort();
        Pair p = ((LispReader) in).makePair(this.command, port.getLineNumber(), port.getColumnNumber());
        ((LispReader) in).setCdr(p, r);
        return p;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object readList(gnu.kawa.lispexpr.LispReader r20, int r21, int r22, int r23) throws java.io.IOException, gnu.text.SyntaxException {
        /*
        r10 = r20.getPort();
        r18 = 93;
        r0 = r23;
        r1 = r18;
        if (r0 != r1) goto L_0x0041;
    L_0x000c:
        r18 = 91;
    L_0x000e:
        r0 = r20;
        r1 = r18;
        r12 = r0.pushNesting(r1);
        r16 = r10.getLineNumber();
        r15 = r10.getColumnNumber();
        r6 = 0;
        r8 = r20.makeNil();	 Catch:{ all -> 0x00a8 }
        r13 = 0;
        r14 = 0;
        r11 = gnu.kawa.lispexpr.ReadTable.getCurrent();	 Catch:{ all -> 0x00a8 }
    L_0x0029:
        r7 = r10.getLineNumber();	 Catch:{ all -> 0x00a8 }
        r3 = r10.getColumnNumber();	 Catch:{ all -> 0x00a8 }
        r21 = r10.read();	 Catch:{ all -> 0x00a8 }
        r0 = r21;
        r1 = r23;
        if (r0 != r1) goto L_0x0044;
    L_0x003b:
        r0 = r20;
        r0.popNesting(r12);
        return r8;
    L_0x0041:
        r18 = 40;
        goto L_0x000e;
    L_0x0044:
        if (r21 >= 0) goto L_0x0053;
    L_0x0046:
        r18 = "unexpected EOF in list starting here";
        r19 = r16 + 1;
        r0 = r20;
        r1 = r18;
        r2 = r19;
        r0.eofError(r1, r2, r15);	 Catch:{ all -> 0x00a8 }
    L_0x0053:
        r18 = 46;
        r0 = r21;
        r1 = r18;
        if (r0 != r1) goto L_0x00eb;
    L_0x005b:
        r21 = r10.peek();	 Catch:{ all -> 0x00a8 }
        r0 = r21;
        r4 = r11.lookup(r0);	 Catch:{ all -> 0x00a8 }
        r5 = r4.getKind();	 Catch:{ all -> 0x00a8 }
        r18 = 1;
        r0 = r18;
        if (r5 == r0) goto L_0x0077;
    L_0x006f:
        r18 = 5;
        r0 = r18;
        if (r5 == r0) goto L_0x0077;
    L_0x0075:
        if (r5 != 0) goto L_0x00e4;
    L_0x0077:
        r10.skip();	 Catch:{ all -> 0x00a8 }
        r3 = r3 + 1;
        r0 = r21;
        r1 = r23;
        if (r0 != r1) goto L_0x00af;
    L_0x0082:
        r18 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00a8 }
        r18.<init>();	 Catch:{ all -> 0x00a8 }
        r19 = "unexpected '";
        r18 = r18.append(r19);	 Catch:{ all -> 0x00a8 }
        r0 = r23;
        r0 = (char) r0;	 Catch:{ all -> 0x00a8 }
        r19 = r0;
        r18 = r18.append(r19);	 Catch:{ all -> 0x00a8 }
        r19 = "' after '.'";
        r18 = r18.append(r19);	 Catch:{ all -> 0x00a8 }
        r18 = r18.toString();	 Catch:{ all -> 0x00a8 }
        r0 = r20;
        r1 = r18;
        r0.error(r1);	 Catch:{ all -> 0x00a8 }
        goto L_0x003b;
    L_0x00a8:
        r18 = move-exception;
        r0 = r20;
        r0.popNesting(r12);
        throw r18;
    L_0x00af:
        if (r21 >= 0) goto L_0x00be;
    L_0x00b1:
        r18 = "unexpected EOF in list starting here";
        r19 = r16 + 1;
        r0 = r20;
        r1 = r18;
        r2 = r19;
        r0.eofError(r1, r2, r15);	 Catch:{ all -> 0x00a8 }
    L_0x00be:
        if (r13 == 0) goto L_0x00cf;
    L_0x00c0:
        r18 = "multiple '.' in list";
        r0 = r20;
        r1 = r18;
        r0.error(r1);	 Catch:{ all -> 0x00a8 }
        r14 = 0;
        r8 = r20.makeNil();	 Catch:{ all -> 0x00a8 }
        r6 = 0;
    L_0x00cf:
        r13 = 1;
    L_0x00d0:
        r9 = r8;
    L_0x00d1:
        r0 = r20;
        r1 = r21;
        r17 = r0.readValues(r1, r4, r11);	 Catch:{ all -> 0x00a8 }
        r18 = gnu.mapping.Values.empty;	 Catch:{ all -> 0x00a8 }
        r0 = r17;
        r1 = r18;
        if (r0 != r1) goto L_0x00f3;
    L_0x00e1:
        r8 = r9;
        goto L_0x0029;
    L_0x00e4:
        r21 = 46;
        r4 = gnu.kawa.lispexpr.ReadTableEntry.getConstituentInstance();	 Catch:{ all -> 0x00a8 }
        goto L_0x00d0;
    L_0x00eb:
        r0 = r21;
        r4 = r11.lookup(r0);	 Catch:{ all -> 0x00a8 }
        r9 = r8;
        goto L_0x00d1;
    L_0x00f3:
        r0 = r20;
        r1 = r17;
        r17 = r0.handlePostfix(r1, r11, r7, r3);	 Catch:{ all -> 0x00a8 }
        if (r14 == 0) goto L_0x010e;
    L_0x00fd:
        r18 = "multiple values after '.'";
        r0 = r20;
        r1 = r18;
        r0.error(r1);	 Catch:{ all -> 0x00a8 }
        r6 = 0;
        r8 = r20.makeNil();	 Catch:{ all -> 0x00a8 }
        r14 = 0;
        goto L_0x0029;
    L_0x010e:
        if (r13 == 0) goto L_0x011a;
    L_0x0110:
        r14 = 1;
        r8 = r17;
    L_0x0113:
        if (r6 != 0) goto L_0x012b;
    L_0x0115:
        r9 = r8;
    L_0x0116:
        r6 = r8;
        r8 = r9;
        goto L_0x0029;
    L_0x011a:
        if (r6 != 0) goto L_0x0120;
    L_0x011c:
        r7 = r16;
        r3 = r15 + -1;
    L_0x0120:
        r0 = r20;
        r1 = r17;
        r17 = r0.makePair(r1, r7, r3);	 Catch:{ all -> 0x00a8 }
        r8 = r17;
        goto L_0x0113;
    L_0x012b:
        r0 = r20;
        r0.setCdr(r6, r8);	 Catch:{ all -> 0x00a8 }
        goto L_0x0116;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.lispexpr.ReaderParens.readList(gnu.kawa.lispexpr.LispReader, int, int, int):java.lang.Object");
    }
}

package kawa.standard;

import gnu.expr.Expression;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class fluid_let extends Syntax {
    public static final fluid_let fluid_let = new fluid_let();
    Expression defaultInit;
    boolean star;

    public gnu.expr.Expression rewrite(java.lang.Object r16, java.lang.Object r17, kawa.lang.Translator r18) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r15 = this;
        r13 = r15.star;
        if (r13 == 0) goto L_0x006a;
    L_0x0004:
        r5 = 1;
    L_0x0005:
        r8 = new gnu.expr.Expression[r5];
        r9 = new gnu.expr.FluidLetExp;
        r9.<init>(r8);
        r7 = 0;
    L_0x000d:
        if (r7 >= r5) goto L_0x0127;
    L_0x000f:
        r2 = r16;
        r2 = (gnu.lists.Pair) r2;
        r0 = r18;
        r11 = r0.pushPositionOf(r2);
        r10 = r2.getCar();	 Catch:{ all -> 0x0120 }
        r13 = r10 instanceof java.lang.String;	 Catch:{ all -> 0x0120 }
        if (r13 != 0) goto L_0x0025;	 Catch:{ all -> 0x0120 }
    L_0x0021:
        r13 = r10 instanceof gnu.mapping.Symbol;	 Catch:{ all -> 0x0120 }
        if (r13 == 0) goto L_0x006f;	 Catch:{ all -> 0x0120 }
    L_0x0025:
        r12 = r15.defaultInit;	 Catch:{ all -> 0x0120 }
    L_0x0027:
        r4 = r9.addDeclaration(r10);	 Catch:{ all -> 0x0120 }
        r0 = r18;	 Catch:{ all -> 0x0120 }
        r13 = r0.lexical;	 Catch:{ all -> 0x0120 }
        r14 = 0;	 Catch:{ all -> 0x0120 }
        r6 = r13.lookup(r10, r14);	 Catch:{ all -> 0x0120 }
        if (r6 == 0) goto L_0x0045;	 Catch:{ all -> 0x0120 }
    L_0x0036:
        r0 = r18;	 Catch:{ all -> 0x0120 }
        r6.maybeIndirectBinding(r0);	 Catch:{ all -> 0x0120 }
        r4.base = r6;	 Catch:{ all -> 0x0120 }
        r13 = 1;	 Catch:{ all -> 0x0120 }
        r6.setFluid(r13);	 Catch:{ all -> 0x0120 }
        r13 = 1;	 Catch:{ all -> 0x0120 }
        r6.setCanWrite(r13);	 Catch:{ all -> 0x0120 }
    L_0x0045:
        r13 = 1;	 Catch:{ all -> 0x0120 }
        r4.setCanWrite(r13);	 Catch:{ all -> 0x0120 }
        r13 = 1;	 Catch:{ all -> 0x0120 }
        r4.setFluid(r13);	 Catch:{ all -> 0x0120 }
        r13 = 1;	 Catch:{ all -> 0x0120 }
        r4.setIndirectBinding(r13);	 Catch:{ all -> 0x0120 }
        if (r12 != 0) goto L_0x0058;	 Catch:{ all -> 0x0120 }
    L_0x0053:
        r12 = new gnu.expr.ReferenceExp;	 Catch:{ all -> 0x0120 }
        r12.<init>(r10);	 Catch:{ all -> 0x0120 }
    L_0x0058:
        r8[r7] = r12;	 Catch:{ all -> 0x0120 }
        r13 = 0;	 Catch:{ all -> 0x0120 }
        r4.noteValue(r13);	 Catch:{ all -> 0x0120 }
        r16 = r2.getCdr();	 Catch:{ all -> 0x0120 }
        r0 = r18;
        r0.popPositionOf(r11);
        r7 = r7 + 1;
        goto L_0x000d;
    L_0x006a:
        r5 = gnu.lists.LList.length(r16);
        goto L_0x0005;
    L_0x006f:
        r13 = r10 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0120 }
        if (r13 == 0) goto L_0x00f7;	 Catch:{ all -> 0x0120 }
    L_0x0073:
        r0 = r10;	 Catch:{ all -> 0x0120 }
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0120 }
        r3 = r0;	 Catch:{ all -> 0x0120 }
        r13 = r3.getCar();	 Catch:{ all -> 0x0120 }
        r13 = r13 instanceof java.lang.String;	 Catch:{ all -> 0x0120 }
        if (r13 != 0) goto L_0x008f;	 Catch:{ all -> 0x0120 }
    L_0x007f:
        r13 = r3.getCar();	 Catch:{ all -> 0x0120 }
        r13 = r13 instanceof gnu.mapping.Symbol;	 Catch:{ all -> 0x0120 }
        if (r13 != 0) goto L_0x008f;	 Catch:{ all -> 0x0120 }
    L_0x0087:
        r13 = r3.getCar();	 Catch:{ all -> 0x0120 }
        r13 = r13 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0120 }
        if (r13 == 0) goto L_0x00f7;	 Catch:{ all -> 0x0120 }
    L_0x008f:
        r10 = r3.getCar();	 Catch:{ all -> 0x0120 }
        r13 = r10 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0120 }
        if (r13 == 0) goto L_0x009d;	 Catch:{ all -> 0x0120 }
    L_0x0097:
        r10 = (kawa.lang.SyntaxForm) r10;	 Catch:{ all -> 0x0120 }
        r10 = r10.getDatum();	 Catch:{ all -> 0x0120 }
    L_0x009d:
        r13 = r3.getCdr();	 Catch:{ all -> 0x0120 }
        r14 = gnu.lists.LList.Empty;	 Catch:{ all -> 0x0120 }
        if (r13 != r14) goto L_0x00a8;	 Catch:{ all -> 0x0120 }
    L_0x00a5:
        r12 = r15.defaultInit;	 Catch:{ all -> 0x0120 }
        goto L_0x0027;	 Catch:{ all -> 0x0120 }
    L_0x00a8:
        r13 = r3.getCdr();	 Catch:{ all -> 0x0120 }
        r13 = r13 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0120 }
        if (r13 == 0) goto L_0x00be;	 Catch:{ all -> 0x0120 }
    L_0x00b0:
        r3 = r3.getCdr();	 Catch:{ all -> 0x0120 }
        r3 = (gnu.lists.Pair) r3;	 Catch:{ all -> 0x0120 }
        r13 = r3.getCdr();	 Catch:{ all -> 0x0120 }
        r14 = gnu.lists.LList.Empty;	 Catch:{ all -> 0x0120 }
        if (r13 == r14) goto L_0x00eb;	 Catch:{ all -> 0x0120 }
    L_0x00be:
        r13 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0120 }
        r13.<init>();	 Catch:{ all -> 0x0120 }
        r14 = "bad syntax for value of ";	 Catch:{ all -> 0x0120 }
        r13 = r13.append(r14);	 Catch:{ all -> 0x0120 }
        r13 = r13.append(r10);	 Catch:{ all -> 0x0120 }
        r14 = " in ";	 Catch:{ all -> 0x0120 }
        r13 = r13.append(r14);	 Catch:{ all -> 0x0120 }
        r14 = r15.getName();	 Catch:{ all -> 0x0120 }
        r13 = r13.append(r14);	 Catch:{ all -> 0x0120 }
        r13 = r13.toString();	 Catch:{ all -> 0x0120 }
        r0 = r18;	 Catch:{ all -> 0x0120 }
        r9 = r0.syntaxError(r13);	 Catch:{ all -> 0x0120 }
        r0 = r18;
        r0.popPositionOf(r11);
    L_0x00ea:
        return r9;
    L_0x00eb:
        r13 = r3.getCar();	 Catch:{ all -> 0x0120 }
        r0 = r18;	 Catch:{ all -> 0x0120 }
        r12 = r0.rewrite(r13);	 Catch:{ all -> 0x0120 }
        goto L_0x0027;	 Catch:{ all -> 0x0120 }
    L_0x00f7:
        r13 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0120 }
        r13.<init>();	 Catch:{ all -> 0x0120 }
        r14 = "invalid ";	 Catch:{ all -> 0x0120 }
        r13 = r13.append(r14);	 Catch:{ all -> 0x0120 }
        r14 = r15.getName();	 Catch:{ all -> 0x0120 }
        r13 = r13.append(r14);	 Catch:{ all -> 0x0120 }
        r14 = " syntax";	 Catch:{ all -> 0x0120 }
        r13 = r13.append(r14);	 Catch:{ all -> 0x0120 }
        r13 = r13.toString();	 Catch:{ all -> 0x0120 }
        r0 = r18;	 Catch:{ all -> 0x0120 }
        r9 = r0.syntaxError(r13);	 Catch:{ all -> 0x0120 }
        r0 = r18;
        r0.popPositionOf(r11);
        goto L_0x00ea;
    L_0x0120:
        r13 = move-exception;
        r0 = r18;
        r0.popPositionOf(r11);
        throw r13;
    L_0x0127:
        r0 = r18;
        r0.push(r9);
        r13 = r15.star;
        if (r13 == 0) goto L_0x0142;
    L_0x0130:
        r13 = gnu.lists.LList.Empty;
        r0 = r16;
        if (r0 == r13) goto L_0x0142;
    L_0x0136:
        r13 = r15.rewrite(r16, r17, r18);
        r9.body = r13;
    L_0x013c:
        r0 = r18;
        r0.pop(r9);
        goto L_0x00ea;
    L_0x0142:
        r0 = r18;
        r1 = r17;
        r13 = r0.rewrite_body(r1);
        r9.body = r13;
        goto L_0x013c;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.fluid_let.rewrite(java.lang.Object, java.lang.Object, kawa.lang.Translator):gnu.expr.Expression");
    }

    static {
        fluid_let.setName("fluid-set");
    }

    public fluid_let(boolean star, Expression defaultInit) {
        this.star = star;
        this.defaultInit = defaultInit;
    }

    public fluid_let() {
        this.star = false;
    }

    public Expression rewrite(Object obj, Translator tr) {
        if (!(obj instanceof Pair)) {
            return tr.syntaxError("missing let arguments");
        }
        Pair pair = (Pair) obj;
        return rewrite(pair.getCar(), pair.getCdr(), tr);
    }
}

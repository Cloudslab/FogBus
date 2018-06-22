package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.ErrorExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.mapping.Procedure1;
import gnu.text.Printable;
import gnu.text.ReportFormat;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SyntaxRules extends Procedure1 implements Printable, Externalizable {
    Object[] literal_identifiers;
    int maxVars;
    SyntaxRule[] rules;

    public SyntaxRules(java.lang.Object[] r26, java.lang.Object r27, kawa.lang.Translator r28) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r25 = this;
        r25.<init>();
        r8 = 0;
        r0 = r25;
        r0.maxVars = r8;
        r0 = r26;
        r1 = r25;
        r1.literal_identifiers = r0;
        r14 = kawa.lang.Translator.listLength(r27);
        if (r14 >= 0) goto L_0x001c;
    L_0x0014:
        r14 = 0;
        r8 = "missing or invalid syntax-rules";
        r0 = r28;
        r0.syntaxError(r8);
    L_0x001c:
        r8 = new kawa.lang.SyntaxRule[r14];
        r0 = r25;
        r0.rules = r8;
        r16 = 0;
        r10 = 0;
    L_0x0025:
        if (r10 >= r14) goto L_0x01a4;
    L_0x0027:
        r0 = r27;
        r8 = r0 instanceof kawa.lang.SyntaxForm;
        if (r8 == 0) goto L_0x0036;
    L_0x002d:
        r16 = r27;
        r16 = (kawa.lang.SyntaxForm) r16;
        r27 = r16.getDatum();
        goto L_0x0027;
    L_0x0036:
        r15 = r27;
        r15 = (gnu.lists.Pair) r15;
        r13 = r16;
        r21 = r15.getCar();
    L_0x0040:
        r0 = r21;
        r8 = r0 instanceof kawa.lang.SyntaxForm;
        if (r8 == 0) goto L_0x004f;
    L_0x0046:
        r13 = r21;
        r13 = (kawa.lang.SyntaxForm) r13;
        r21 = r13.getDatum();
        goto L_0x0040;
    L_0x004f:
        r0 = r21;
        r8 = r0 instanceof gnu.lists.Pair;
        if (r8 != 0) goto L_0x0074;
    L_0x0055:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "missing pattern in ";
        r8 = r8.append(r9);
        r8 = r8.append(r10);
        r9 = "'th syntax rule";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r0 = r28;
        r0.syntaxError(r8);
    L_0x0073:
        return;
    L_0x0074:
        r7 = r13;
        r22 = r21;
        r22 = (gnu.lists.Pair) r22;
        r6 = r22.getCar();
        r18 = r28.getFileName();
        r19 = r28.getLineNumber();
        r17 = r28.getColumnNumber();
        r24 = r13;
        r0 = r28;	 Catch:{ all -> 0x0197 }
        r1 = r22;	 Catch:{ all -> 0x0197 }
        r0.setLine(r1);	 Catch:{ all -> 0x0197 }
        r21 = r22.getCdr();	 Catch:{ all -> 0x0197 }
    L_0x0096:
        r0 = r21;	 Catch:{ all -> 0x0197 }
        r8 = r0 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0197 }
        if (r8 == 0) goto L_0x00a7;	 Catch:{ all -> 0x0197 }
    L_0x009c:
        r0 = r21;	 Catch:{ all -> 0x0197 }
        r0 = (kawa.lang.SyntaxForm) r0;	 Catch:{ all -> 0x0197 }
        r24 = r0;	 Catch:{ all -> 0x0197 }
        r21 = r24.getDatum();	 Catch:{ all -> 0x0197 }
        goto L_0x0096;	 Catch:{ all -> 0x0197 }
    L_0x00a7:
        r0 = r21;	 Catch:{ all -> 0x0197 }
        r8 = r0 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0197 }
        if (r8 != 0) goto L_0x00d7;	 Catch:{ all -> 0x0197 }
    L_0x00ad:
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0197 }
        r8.<init>();	 Catch:{ all -> 0x0197 }
        r9 = "missing template in ";	 Catch:{ all -> 0x0197 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x0197 }
        r8 = r8.append(r10);	 Catch:{ all -> 0x0197 }
        r9 = "'th syntax rule";	 Catch:{ all -> 0x0197 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x0197 }
        r8 = r8.toString();	 Catch:{ all -> 0x0197 }
        r0 = r28;	 Catch:{ all -> 0x0197 }
        r0.syntaxError(r8);	 Catch:{ all -> 0x0197 }
        r0 = r28;
        r1 = r18;
        r2 = r19;
        r3 = r17;
        r0.setLine(r1, r2, r3);
        goto L_0x0073;
    L_0x00d7:
        r0 = r21;	 Catch:{ all -> 0x0197 }
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0197 }
        r22 = r0;	 Catch:{ all -> 0x0197 }
        r8 = r22.getCdr();	 Catch:{ all -> 0x0197 }
        r9 = gnu.lists.LList.Empty;	 Catch:{ all -> 0x0197 }
        if (r8 == r9) goto L_0x0110;	 Catch:{ all -> 0x0197 }
    L_0x00e5:
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0197 }
        r8.<init>();	 Catch:{ all -> 0x0197 }
        r9 = "junk after ";	 Catch:{ all -> 0x0197 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x0197 }
        r8 = r8.append(r10);	 Catch:{ all -> 0x0197 }
        r9 = "'th syntax rule";	 Catch:{ all -> 0x0197 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x0197 }
        r8 = r8.toString();	 Catch:{ all -> 0x0197 }
        r0 = r28;	 Catch:{ all -> 0x0197 }
        r0.syntaxError(r8);	 Catch:{ all -> 0x0197 }
        r0 = r28;
        r1 = r18;
        r2 = r19;
        r3 = r17;
        r0.setLine(r1, r2, r3);
        goto L_0x0073;
    L_0x0110:
        r23 = r22.getCar();	 Catch:{ all -> 0x0197 }
        r12 = kawa.lang.PatternScope.push(r28);	 Catch:{ all -> 0x0197 }
        r0 = r28;	 Catch:{ all -> 0x0197 }
        r0.push(r12);	 Catch:{ all -> 0x0197 }
    L_0x011d:
        r8 = r6 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0197 }
        if (r8 == 0) goto L_0x012a;	 Catch:{ all -> 0x0197 }
    L_0x0121:
        r0 = r6;	 Catch:{ all -> 0x0197 }
        r0 = (kawa.lang.SyntaxForm) r0;	 Catch:{ all -> 0x0197 }
        r7 = r0;	 Catch:{ all -> 0x0197 }
        r6 = r7.getDatum();	 Catch:{ all -> 0x0197 }
        goto L_0x011d;	 Catch:{ all -> 0x0197 }
    L_0x012a:
        r5 = new java.lang.StringBuffer;	 Catch:{ all -> 0x0197 }
        r5.<init>();	 Catch:{ all -> 0x0197 }
        r8 = r6 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0197 }
        if (r8 == 0) goto L_0x0183;	 Catch:{ all -> 0x0197 }
    L_0x0133:
        r9 = 0;	 Catch:{ all -> 0x0197 }
        r0 = r6;	 Catch:{ all -> 0x0197 }
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0197 }
        r8 = r0;	 Catch:{ all -> 0x0197 }
        r8 = r8.getCar();	 Catch:{ all -> 0x0197 }
        r26[r9] = r8;	 Catch:{ all -> 0x0197 }
        r0 = r6;	 Catch:{ all -> 0x0197 }
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0197 }
        r11 = r0;	 Catch:{ all -> 0x0197 }
        r8 = 12;	 Catch:{ all -> 0x0197 }
        r5.append(r8);	 Catch:{ all -> 0x0197 }
        r8 = 24;	 Catch:{ all -> 0x0197 }
        r5.append(r8);	 Catch:{ all -> 0x0197 }
        r6 = r11.getCdr();	 Catch:{ all -> 0x0197 }
        r4 = new kawa.lang.SyntaxPattern;	 Catch:{ all -> 0x0197 }
        r8 = r26;	 Catch:{ all -> 0x0197 }
        r9 = r28;	 Catch:{ all -> 0x0197 }
        r4.<init>(r5, r6, r7, r8, r9);	 Catch:{ all -> 0x0197 }
        r0 = r25;	 Catch:{ all -> 0x0197 }
        r8 = r0.rules;	 Catch:{ all -> 0x0197 }
        r9 = new kawa.lang.SyntaxRule;	 Catch:{ all -> 0x0197 }
        r0 = r23;	 Catch:{ all -> 0x0197 }
        r1 = r24;	 Catch:{ all -> 0x0197 }
        r2 = r28;	 Catch:{ all -> 0x0197 }
        r9.<init>(r4, r0, r1, r2);	 Catch:{ all -> 0x0197 }
        r8[r10] = r9;	 Catch:{ all -> 0x0197 }
        kawa.lang.PatternScope.pop(r28);	 Catch:{ all -> 0x0197 }
        r28.pop();	 Catch:{ all -> 0x0197 }
        r0 = r28;
        r1 = r18;
        r2 = r19;
        r3 = r17;
        r0.setLine(r1, r2, r3);
        r10 = r10 + 1;
        r27 = r15.getCdr();
        goto L_0x0025;
    L_0x0183:
        r8 = "pattern does not start with name";	 Catch:{ all -> 0x0197 }
        r0 = r28;	 Catch:{ all -> 0x0197 }
        r0.syntaxError(r8);	 Catch:{ all -> 0x0197 }
        r0 = r28;
        r1 = r18;
        r2 = r19;
        r3 = r17;
        r0.setLine(r1, r2, r3);
        goto L_0x0073;
    L_0x0197:
        r8 = move-exception;
        r0 = r28;
        r1 = r18;
        r2 = r19;
        r3 = r17;
        r0.setLine(r1, r2, r3);
        throw r8;
    L_0x01a4:
        r0 = r25;
        r8 = r0.rules;
        r10 = r8.length;
    L_0x01a9:
        r10 = r10 + -1;
        if (r10 < 0) goto L_0x0073;
    L_0x01ad:
        r0 = r25;
        r8 = r0.rules;
        r8 = r8[r10];
        r8 = r8.patternNesting;
        r20 = r8.length();
        r0 = r25;
        r8 = r0.maxVars;
        r0 = r20;
        if (r0 <= r8) goto L_0x01a9;
    L_0x01c1:
        r0 = r20;
        r1 = r25;
        r1.maxVars = r0;
        goto L_0x01a9;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxRules.<init>(java.lang.Object[], java.lang.Object, kawa.lang.Translator):void");
    }

    public SyntaxRules() {
        this.maxVars = 0;
    }

    public SyntaxRules(Object[] literal_identifiers, SyntaxRule[] rules, int maxVars) {
        this.maxVars = 0;
        this.literal_identifiers = literal_identifiers;
        this.rules = rules;
        this.maxVars = maxVars;
    }

    public Object apply1(Object arg) {
        if (!(arg instanceof SyntaxForm)) {
            return expand(arg, (Translator) Compilation.getCurrent());
        }
        SyntaxForm sf = (SyntaxForm) arg;
        Translator tr = (Translator) Compilation.getCurrent();
        ScopeExp save_scope = tr.currentScope();
        tr.setCurrentScope(sf.getScope());
        try {
            Object expand = expand(sf, tr);
            return expand;
        } finally {
            tr.setCurrentScope(save_scope);
        }
    }

    public Object expand(Object obj, Translator tr) {
        Object[] vars = new Object[this.maxVars];
        Macro macro = (Macro) tr.getCurrentSyntax();
        for (SyntaxRule rule : this.rules) {
            if (rule == null) {
                return new ErrorExp("error defining " + macro);
            }
            if (rule.pattern.match(obj, vars, 0)) {
                return rule.execute(vars, tr, TemplateScope.make(tr));
            }
        }
        return tr.syntaxError("no matching syntax-rule for " + this.literal_identifiers[0]);
    }

    public void print(Consumer out) {
        out.write("#<macro ");
        ReportFormat.print(this.literal_identifiers[0], out);
        out.write(62);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.literal_identifiers);
        out.writeObject(this.rules);
        out.writeInt(this.maxVars);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.literal_identifiers = (Object[]) in.readObject();
        this.rules = (SyntaxRule[]) in.readObject();
        this.maxVars = in.readInt();
    }
}

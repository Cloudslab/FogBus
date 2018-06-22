package kawa.lang;

import android.support.v4.internal.view.SupportMenu;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintWriter;
import java.util.Vector;

public class SyntaxPattern extends Pattern implements Externalizable {
    static final int MATCH_ANY = 3;
    static final int MATCH_ANY_CAR = 7;
    static final int MATCH_EQUALS = 2;
    static final int MATCH_IGNORE = 24;
    static final int MATCH_LENGTH = 6;
    static final int MATCH_LREPEAT = 5;
    static final int MATCH_MISC = 0;
    static final int MATCH_NIL = 8;
    static final int MATCH_PAIR = 4;
    static final int MATCH_VECTOR = 16;
    static final int MATCH_WIDE = 1;
    Object[] literals;
    String program;
    int varCount;

    public int varCount() {
        return this.varCount;
    }

    public boolean match(Object obj, Object[] vars, int start_vars) {
        return match(obj, vars, start_vars, 0, null);
    }

    public SyntaxPattern(String program, Object[] literals, int varCount) {
        this.program = program;
        this.literals = literals;
        this.varCount = varCount;
    }

    public SyntaxPattern(Object pattern, Object[] literal_identifiers, Translator tr) {
        this(new StringBuffer(), pattern, null, literal_identifiers, tr);
    }

    SyntaxPattern(StringBuffer programbuf, Object pattern, SyntaxForm syntax, Object[] literal_identifiers, Translator tr) {
        Vector literalsbuf = new Vector();
        translate(pattern, programbuf, literal_identifiers, 0, literalsbuf, null, '\u0000', tr);
        this.program = programbuf.toString();
        this.literals = new Object[literalsbuf.size()];
        literalsbuf.copyInto(this.literals);
        this.varCount = tr.patternScope.pattern_names.size();
    }

    public void disassemble() {
        disassemble(OutPort.errDefault(), (Translator) Compilation.getCurrent(), 0, this.program.length());
    }

    public void disassemble(PrintWriter ps, Translator tr) {
        disassemble(ps, tr, 0, this.program.length());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void disassemble(java.io.PrintWriter r10, kawa.lang.Translator r11, int r12, int r13) {
        /*
        r9 = this;
        r8 = 8;
        r4 = 0;
        if (r11 == 0) goto L_0x000d;
    L_0x0005:
        r6 = r11.patternScope;
        if (r6 == 0) goto L_0x000d;
    L_0x0009:
        r6 = r11.patternScope;
        r4 = r6.pattern_names;
    L_0x000d:
        r5 = 0;
        r1 = r12;
    L_0x000f:
        if (r1 >= r13) goto L_0x01ee;
    L_0x0011:
        r6 = r9.program;
        r0 = r6.charAt(r1);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " ";
        r6 = r6.append(r7);
        r6 = r6.append(r1);
        r7 = ": ";
        r6 = r6.append(r7);
        r6 = r6.append(r0);
        r6 = r6.toString();
        r10.print(r6);
        r1 = r1 + 1;
        r3 = r0 & 7;
        r6 = r5 << 13;
        r7 = r0 >> 3;
        r5 = r6 | r7;
        switch(r3) {
            case 0: goto L_0x01a9;
            case 1: goto L_0x0066;
            case 2: goto L_0x007d;
            case 3: goto L_0x00af;
            case 4: goto L_0x00e6;
            case 5: goto L_0x0104;
            case 6: goto L_0x017c;
            case 7: goto L_0x00af;
            default: goto L_0x0044;
        };
    L_0x0044:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " - ";
        r6 = r6.append(r7);
        r6 = r6.append(r3);
        r7 = 47;
        r6 = r6.append(r7);
        r6 = r6.append(r5);
        r6 = r6.toString();
        r10.println(r6);
    L_0x0064:
        r5 = 0;
        goto L_0x000f;
    L_0x0066:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " - WIDE ";
        r6 = r6.append(r7);
        r6 = r6.append(r5);
        r6 = r6.toString();
        r10.println(r6);
        goto L_0x000f;
    L_0x007d:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " - EQUALS[";
        r6 = r6.append(r7);
        r6 = r6.append(r5);
        r7 = "]";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r10.print(r6);
        r6 = r9.literals;
        if (r6 == 0) goto L_0x00ab;
    L_0x009d:
        if (r5 < 0) goto L_0x00ab;
    L_0x009f:
        r6 = r9.literals;
        r6 = r6.length;
        if (r5 >= r6) goto L_0x00ab;
    L_0x00a4:
        r6 = r9.literals;
        r6 = r6[r5];
        r10.print(r6);
    L_0x00ab:
        r10.println();
        goto L_0x0064;
    L_0x00af:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r6 = 3;
        if (r3 != r6) goto L_0x00e3;
    L_0x00b7:
        r6 = " - ANY[";
    L_0x00b9:
        r6 = r7.append(r6);
        r6 = r6.append(r5);
        r7 = "]";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r10.print(r6);
        if (r4 == 0) goto L_0x00df;
    L_0x00d0:
        if (r5 < 0) goto L_0x00df;
    L_0x00d2:
        r6 = r4.size();
        if (r5 >= r6) goto L_0x00df;
    L_0x00d8:
        r6 = r4.elementAt(r5);
        r10.print(r6);
    L_0x00df:
        r10.println();
        goto L_0x0064;
    L_0x00e3:
        r6 = " - ANY_CAR[";
        goto L_0x00b9;
    L_0x00e6:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " - PAIR[";
        r6 = r6.append(r7);
        r6 = r6.append(r5);
        r7 = "]";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r10.println(r6);
        goto L_0x0064;
    L_0x0104:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " - LREPEAT[";
        r6 = r6.append(r7);
        r6 = r6.append(r5);
        r7 = "]";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r10.println(r6);
        r6 = r1 + r5;
        r9.disassemble(r10, r11, r1, r6);
        r1 = r1 + r5;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " ";
        r6 = r6.append(r7);
        r6 = r6.append(r1);
        r7 = ": - repeat first var:";
        r6 = r6.append(r7);
        r7 = r9.program;
        r2 = r1 + 1;
        r7 = r7.charAt(r1);
        r7 = r7 >> 3;
        r6 = r6.append(r7);
        r6 = r6.toString();
        r10.println(r6);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " ";
        r6 = r6.append(r7);
        r6 = r6.append(r2);
        r7 = ": - repeast nested vars:";
        r6 = r6.append(r7);
        r7 = r9.program;
        r1 = r2 + 1;
        r7 = r7.charAt(r2);
        r7 = r7 >> 3;
        r6 = r6.append(r7);
        r6 = r6.toString();
        r10.println(r6);
        goto L_0x0064;
    L_0x017c:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = " - LENGTH ";
        r6 = r6.append(r7);
        r7 = r5 >> 1;
        r6 = r6.append(r7);
        r7 = " pairs. ";
        r7 = r6.append(r7);
        r6 = r5 & 1;
        if (r6 != 0) goto L_0x01a6;
    L_0x0197:
        r6 = "pure list";
    L_0x0199:
        r6 = r7.append(r6);
        r6 = r6.toString();
        r10.println(r6);
        goto L_0x0064;
    L_0x01a6:
        r6 = "impure list";
        goto L_0x0199;
    L_0x01a9:
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "[misc ch:";
        r6 = r6.append(r7);
        r6 = r6.append(r0);
        r7 = " n:";
        r6 = r6.append(r7);
        r6 = r6.append(r8);
        r7 = "]";
        r6 = r6.append(r7);
        r6 = r6.toString();
        r10.print(r6);
        if (r0 != r8) goto L_0x01d8;
    L_0x01d1:
        r6 = " - NIL";
        r10.println(r6);
        goto L_0x0064;
    L_0x01d8:
        r6 = 16;
        if (r0 != r6) goto L_0x01e3;
    L_0x01dc:
        r6 = " - VECTOR";
        r10.println(r6);
        goto L_0x0064;
    L_0x01e3:
        r6 = 24;
        if (r0 != r6) goto L_0x0044;
    L_0x01e7:
        r6 = " - IGNORE";
        r10.println(r6);
        goto L_0x0064;
    L_0x01ee:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxPattern.disassemble(java.io.PrintWriter, kawa.lang.Translator, int, int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void translate(java.lang.Object r35, java.lang.StringBuffer r36, java.lang.Object[] r37, int r38, java.util.Vector r39, kawa.lang.SyntaxForm r40, char r41, kawa.lang.Translator r42) {
        /*
        r34 = this;
        r0 = r42;
        r0 = r0.patternScope;
        r23 = r0;
        r0 = r23;
        r0 = r0.pattern_names;
        r22 = r0;
    L_0x000c:
        r0 = r35;
        r4 = r0 instanceof kawa.lang.SyntaxForm;
        if (r4 == 0) goto L_0x001b;
    L_0x0012:
        r40 = r35;
        r40 = (kawa.lang.SyntaxForm) r40;
        r35 = r40.getDatum();
        goto L_0x000c;
    L_0x001b:
        r0 = r35;
        r4 = r0 instanceof gnu.lists.Pair;
        if (r4 == 0) goto L_0x014f;
    L_0x0021:
        r0 = r42;
        r1 = r35;
        r26 = r0.pushPositionOf(r1);
        r29 = r36.length();	 Catch:{ all -> 0x0146 }
        r4 = 4;
        r0 = r36;
        r0.append(r4);	 Catch:{ all -> 0x0146 }
        r0 = r35;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0146 }
        r21 = r0;
        r10 = r40;
        r20 = r21.getCdr();	 Catch:{ all -> 0x0146 }
    L_0x003f:
        r0 = r20;
        r4 = r0 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0146 }
        if (r4 == 0) goto L_0x0050;
    L_0x0045:
        r0 = r20;
        r0 = (kawa.lang.SyntaxForm) r0;	 Catch:{ all -> 0x0146 }
        r40 = r0;
        r20 = r40.getDatum();	 Catch:{ all -> 0x0146 }
        goto L_0x003f;
    L_0x0050:
        r24 = 0;
        r0 = r20;
        r4 = r0 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0146 }
        if (r4 == 0) goto L_0x0084;
    L_0x0058:
        r0 = r20;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0146 }
        r4 = r0;
        r4 = r4.getCar();	 Catch:{ all -> 0x0146 }
        r5 = "...";
        r0 = r42;
        r4 = r0.matches(r4, r5);	 Catch:{ all -> 0x0146 }
        if (r4 == 0) goto L_0x0084;
    L_0x006b:
        r24 = 1;
        r20 = (gnu.lists.Pair) r20;	 Catch:{ all -> 0x0146 }
        r20 = r20.getCdr();	 Catch:{ all -> 0x0146 }
    L_0x0073:
        r0 = r20;
        r4 = r0 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0146 }
        if (r4 == 0) goto L_0x0084;
    L_0x0079:
        r0 = r20;
        r0 = (kawa.lang.SyntaxForm) r0;	 Catch:{ all -> 0x0146 }
        r40 = r0;
        r20 = r40.getDatum();	 Catch:{ all -> 0x0146 }
        goto L_0x0073;
    L_0x0084:
        r30 = r22.size();	 Catch:{ all -> 0x0146 }
        r4 = 80;
        r0 = r41;
        if (r0 != r4) goto L_0x0090;
    L_0x008e:
        r41 = 0;
    L_0x0090:
        r5 = r21.getCar();	 Catch:{ all -> 0x0146 }
        if (r24 == 0) goto L_0x00f9;
    L_0x0096:
        r8 = r38 + 1;
    L_0x0098:
        r4 = 86;
        r0 = r41;
        if (r0 != r4) goto L_0x00fc;
    L_0x009e:
        r11 = 0;
    L_0x009f:
        r4 = r34;
        r6 = r36;
        r7 = r37;
        r9 = r39;
        r12 = r42;
        r4.translate(r5, r6, r7, r8, r9, r10, r11, r12);	 Catch:{ all -> 0x0146 }
        r4 = r22.size();	 Catch:{ all -> 0x0146 }
        r31 = r4 - r30;
        r4 = r36.length();	 Catch:{ all -> 0x0146 }
        r4 = r4 - r29;
        r4 = r4 + -1;
        r5 = r4 << 3;
        if (r24 == 0) goto L_0x00ff;
    L_0x00be:
        r4 = 5;
    L_0x00bf:
        r33 = r5 | r4;
        r4 = 65535; // 0xffff float:9.1834E-41 double:3.23786E-319;
        r0 = r33;
        if (r0 <= r4) goto L_0x00d6;
    L_0x00c8:
        r4 = r33 >> 13;
        r4 = r4 + 1;
        r0 = r29;
        r1 = r36;
        r4 = insertInt(r0, r1, r4);	 Catch:{ all -> 0x0146 }
        r29 = r29 + r4;
    L_0x00d6:
        r0 = r33;
        r4 = (char) r0;	 Catch:{ all -> 0x0146 }
        r0 = r36;
        r1 = r29;
        r0.setCharAt(r1, r4);	 Catch:{ all -> 0x0146 }
        r25 = kawa.lang.Translator.listLength(r20);	 Catch:{ all -> 0x0146 }
        r4 = -2147483648; // 0xffffffff80000000 float:-0.0 double:NaN;
        r0 = r25;
        if (r0 != r4) goto L_0x0101;
    L_0x00ea:
        r4 = "cyclic pattern list";
        r0 = r42;
        r0.syntaxError(r4);	 Catch:{ all -> 0x0146 }
        r0 = r42;
        r1 = r26;
        r0.popPositionOf(r1);
    L_0x00f8:
        return;
    L_0x00f9:
        r8 = r38;
        goto L_0x0098;
    L_0x00fc:
        r11 = 80;
        goto L_0x009f;
    L_0x00ff:
        r4 = 4;
        goto L_0x00bf;
    L_0x0101:
        if (r24 == 0) goto L_0x0133;
    L_0x0103:
        r4 = r30 << 3;
        r0 = r36;
        addInt(r0, r4);	 Catch:{ all -> 0x0146 }
        r4 = r31 << 3;
        r0 = r36;
        addInt(r0, r4);	 Catch:{ all -> 0x0146 }
        r4 = gnu.lists.LList.Empty;	 Catch:{ all -> 0x0146 }
        r0 = r20;
        if (r0 != r4) goto L_0x0126;
    L_0x0117:
        r4 = 8;
        r0 = r36;
        r0.append(r4);	 Catch:{ all -> 0x0146 }
        r0 = r42;
        r1 = r26;
        r0.popPositionOf(r1);
        goto L_0x00f8;
    L_0x0126:
        if (r25 < 0) goto L_0x013e;
    L_0x0128:
        r25 = r25 << 1;
    L_0x012a:
        r4 = r25 << 3;
        r4 = r4 | 6;
        r0 = r36;
        addInt(r0, r4);	 Catch:{ all -> 0x0146 }
    L_0x0133:
        r35 = r20;
        r0 = r42;
        r1 = r26;
        r0.popPositionOf(r1);
        goto L_0x000c;
    L_0x013e:
        r0 = r25;
        r4 = -r0;
        r4 = r4 << 1;
        r25 = r4 + -1;
        goto L_0x012a;
    L_0x0146:
        r4 = move-exception;
        r0 = r42;
        r1 = r26;
        r0.popPositionOf(r1);
        throw r4;
    L_0x014f:
        r0 = r35;
        r4 = r0 instanceof gnu.mapping.Symbol;
        if (r4 == 0) goto L_0x022d;
    L_0x0155:
        r0 = r37;
        r0 = r0.length;
        r16 = r0;
    L_0x015a:
        r16 = r16 + -1;
        if (r16 < 0) goto L_0x01bf;
    L_0x015e:
        r13 = r42.currentScope();
        if (r40 != 0) goto L_0x01a8;
    L_0x0164:
        r27 = r13;
    L_0x0166:
        r17 = r37[r16];
        r0 = r17;
        r4 = r0 instanceof kawa.lang.SyntaxForm;
        if (r4 == 0) goto L_0x01ad;
    L_0x016e:
        r32 = r17;
        r32 = (kawa.lang.SyntaxForm) r32;
        r17 = r32.getDatum();
        r28 = r32.getScope();
    L_0x017a:
        r0 = r35;
        r1 = r27;
        r2 = r17;
        r3 = r28;
        r4 = literalIdentifierEq(r0, r1, r2, r3);
        if (r4 == 0) goto L_0x015a;
    L_0x0188:
        r0 = r39;
        r1 = r35;
        r15 = kawa.lang.SyntaxTemplate.indexOf(r0, r1);
        if (r15 >= 0) goto L_0x019d;
    L_0x0192:
        r15 = r39.size();
        r0 = r39;
        r1 = r35;
        r0.addElement(r1);
    L_0x019d:
        r4 = r15 << 3;
        r4 = r4 | 2;
        r0 = r36;
        addInt(r0, r4);
        goto L_0x00f8;
    L_0x01a8:
        r27 = r40.getScope();
        goto L_0x0166;
    L_0x01ad:
        r0 = r42;
        r4 = r0.currentMacroDefinition;
        if (r4 == 0) goto L_0x01bc;
    L_0x01b3:
        r0 = r42;
        r4 = r0.currentMacroDefinition;
        r28 = r4.getCapturedScope();
        goto L_0x017a;
    L_0x01bc:
        r28 = r13;
        goto L_0x017a;
    L_0x01bf:
        r0 = r22;
        r1 = r35;
        r4 = r0.contains(r1);
        if (r4 == 0) goto L_0x01e3;
    L_0x01c9:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "duplicated pattern variable ";
        r4 = r4.append(r5);
        r0 = r35;
        r4 = r4.append(r0);
        r4 = r4.toString();
        r0 = r42;
        r0.syntaxError(r4);
    L_0x01e3:
        r15 = r22.size();
        r0 = r22;
        r1 = r35;
        r0.addElement(r1);
        r4 = 80;
        r0 = r41;
        if (r0 != r4) goto L_0x0226;
    L_0x01f4:
        r18 = 1;
    L_0x01f6:
        r5 = r38 << 1;
        if (r18 == 0) goto L_0x0229;
    L_0x01fa:
        r4 = 1;
    L_0x01fb:
        r19 = r5 + r4;
        r0 = r23;
        r4 = r0.patternNesting;
        r0 = r19;
        r5 = (char) r0;
        r4.append(r5);
        r0 = r23;
        r1 = r35;
        r14 = r0.addDeclaration(r1);
        r0 = r42;
        r14.setLocation(r0);
        r0 = r42;
        r0.push(r14);
        r5 = r15 << 3;
        if (r18 == 0) goto L_0x022b;
    L_0x021d:
        r4 = 7;
    L_0x021e:
        r4 = r4 | r5;
        r0 = r36;
        addInt(r0, r4);
        goto L_0x00f8;
    L_0x0226:
        r18 = 0;
        goto L_0x01f6;
    L_0x0229:
        r4 = 0;
        goto L_0x01fb;
    L_0x022b:
        r4 = 3;
        goto L_0x021e;
    L_0x022d:
        r4 = gnu.lists.LList.Empty;
        r0 = r35;
        if (r0 != r4) goto L_0x023c;
    L_0x0233:
        r4 = 8;
        r0 = r36;
        r0.append(r4);
        goto L_0x00f8;
    L_0x023c:
        r0 = r35;
        r4 = r0 instanceof gnu.lists.FVector;
        if (r4 == 0) goto L_0x0253;
    L_0x0242:
        r4 = 16;
        r0 = r36;
        r0.append(r4);
        r35 = (gnu.lists.FVector) r35;
        r35 = gnu.lists.LList.makeList(r35);
        r41 = 86;
        goto L_0x000c;
    L_0x0253:
        r0 = r39;
        r1 = r35;
        r15 = kawa.lang.SyntaxTemplate.indexOf(r0, r1);
        if (r15 >= 0) goto L_0x0268;
    L_0x025d:
        r15 = r39.size();
        r0 = r39;
        r1 = r35;
        r0.addElement(r1);
    L_0x0268:
        r4 = r15 << 3;
        r4 = r4 | 2;
        r0 = r36;
        addInt(r0, r4);
        goto L_0x00f8;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.SyntaxPattern.translate(java.lang.Object, java.lang.StringBuffer, java.lang.Object[], int, java.util.Vector, kawa.lang.SyntaxForm, char, kawa.lang.Translator):void");
    }

    private static void addInt(StringBuffer sbuf, int val) {
        if (val > SupportMenu.USER_MASK) {
            addInt(sbuf, (val << 13) + 1);
        }
        sbuf.append((char) val);
    }

    private static int insertInt(int offset, StringBuffer sbuf, int val) {
        if (val > SupportMenu.USER_MASK) {
            offset += insertInt(offset, sbuf, (val << 13) + 1);
        }
        sbuf.insert(offset, (char) val);
        return offset + 1;
    }

    boolean match_car(Pair p, Object[] vars, int start_vars, int pc, SyntaxForm syntax) {
        int pc_start = pc;
        int pc2 = pc + 1;
        char ch = this.program.charAt(pc);
        int value = ch >> 3;
        while ((ch & 7) == 1) {
            int i = value << 13;
            pc = pc2 + 1;
            ch = this.program.charAt(pc2);
            value = i | (ch >> 3);
            pc2 = pc;
        }
        if ((ch & 7) != 7) {
            return match(p.getCar(), vars, start_vars, pc_start, syntax);
        }
        if (!(syntax == null || (p.getCar() instanceof SyntaxForm))) {
            p = Translator.makePair(p, SyntaxForms.fromDatum(p.getCar(), syntax), p.getCdr());
        }
        vars[start_vars + value] = p;
        return true;
    }

    public boolean match(Object obj, Object[] vars, int start_vars, int pc, SyntaxForm syntax) {
        int value = 0;
        while (true) {
            if (obj instanceof SyntaxForm) {
                syntax = (SyntaxForm) obj;
                obj = syntax.getDatum();
            } else {
                int pc2 = pc + 1;
                char ch = this.program.charAt(pc);
                value = (value << 13) | (ch >> 3);
                Pair p;
                int i;
                switch (ch & 7) {
                    case 0:
                        if (ch == '\b') {
                            boolean z;
                            if (obj == LList.Empty) {
                                z = true;
                            } else {
                                z = false;
                            }
                            pc = pc2;
                            return z;
                        } else if (ch == '\u0010') {
                            if (obj instanceof FVector) {
                                pc = pc2;
                                return match(LList.makeList((FVector) obj), vars, start_vars, pc2, syntax);
                            }
                            pc = pc2;
                            return false;
                        } else if (ch == '\u0018') {
                            pc = pc2;
                            return true;
                        } else {
                            throw new Error("unknwon pattern opcode");
                        }
                    case 1:
                        pc = pc2;
                        break;
                    case 2:
                        SyntaxForm sf;
                        Object id1;
                        ScopeExp sc1;
                        Object id2;
                        ScopeExp sc2;
                        SyntaxForm lit = this.literals[value];
                        Translator tr = (Translator) Compilation.getCurrent();
                        if (lit instanceof SyntaxForm) {
                            sf = lit;
                            id1 = sf.getDatum();
                            sc1 = sf.getScope();
                        } else {
                            SyntaxForm id12 = lit;
                            Syntax curSyntax = tr.getCurrentSyntax();
                            sc1 = curSyntax instanceof Macro ? ((Macro) curSyntax).getCapturedScope() : null;
                        }
                        if (obj instanceof SyntaxForm) {
                            sf = (SyntaxForm) obj;
                            id2 = sf.getDatum();
                            sc2 = sf.getScope();
                        } else {
                            id2 = obj;
                            sc2 = syntax == null ? tr.currentScope() : syntax.getScope();
                        }
                        pc = pc2;
                        return literalIdentifierEq(id1, sc1, id2, sc2);
                    case 3:
                        if (syntax != null) {
                            obj = SyntaxForms.fromDatum(obj, syntax);
                        }
                        vars[start_vars + value] = obj;
                        pc = pc2;
                        return true;
                    case 4:
                        if (obj instanceof Pair) {
                            p = (Pair) obj;
                            if (match_car(p, vars, start_vars, pc2, syntax)) {
                                pc = pc2 + value;
                                value = 0;
                                obj = p.getCdr();
                                break;
                            }
                            pc = pc2;
                            return false;
                        }
                        pc = pc2;
                        return false;
                    case 5:
                        int i2;
                        int pairsRequired;
                        int repeat_pc = pc2;
                        pc = pc2 + value;
                        pc2 = pc + 1;
                        ch = this.program.charAt(pc);
                        int subvar0 = ch >> 3;
                        while ((ch & 7) == 1) {
                            i2 = subvar0 << 13;
                            pc = pc2 + 1;
                            ch = this.program.charAt(pc2);
                            subvar0 = i2 | (ch >> 3);
                            pc2 = pc;
                        }
                        subvar0 += start_vars;
                        int subvarN = this.program.charAt(pc2) >> 3;
                        pc2++;
                        while ((ch & 7) == 1) {
                            i2 = subvarN << 13;
                            pc = pc2 + 1;
                            ch = this.program.charAt(pc2);
                            subvarN = i2 | (ch >> 3);
                            pc2 = pc;
                        }
                        pc = pc2 + 1;
                        ch = this.program.charAt(pc2);
                        boolean listRequired = true;
                        if (ch == '\b') {
                            pairsRequired = 0;
                        } else {
                            value = ch >> 3;
                            pc2 = pc;
                            while ((ch & 7) == 1) {
                                i2 = value << 13;
                                pc = pc2 + 1;
                                ch = this.program.charAt(pc2);
                                value = i2 | (ch >> 3);
                                pc2 = pc;
                            }
                            if ((value & 1) != 0) {
                                listRequired = false;
                            }
                            pairsRequired = value >> 1;
                            pc = pc2;
                        }
                        int pairsValue = Translator.listLength(obj);
                        boolean listValue;
                        if (pairsValue >= 0) {
                            listValue = true;
                        } else {
                            listValue = false;
                            pairsValue = -1 - pairsValue;
                        }
                        if (pairsValue < pairsRequired || (listRequired && !listValue)) {
                            return false;
                        }
                        int j;
                        int repeat_count = pairsValue - pairsRequired;
                        Object[][] arrays = new Object[subvarN][];
                        for (j = 0; j < subvarN; j++) {
                            arrays[j] = new Object[repeat_count];
                        }
                        for (i = 0; i < repeat_count; i++) {
                            SyntaxForm obj2;
                            while (obj2 instanceof SyntaxForm) {
                                syntax = obj2;
                                obj2 = syntax.getDatum();
                            }
                            p = (Pair) obj2;
                            if (!match_car(p, vars, start_vars, repeat_pc, syntax)) {
                                return false;
                            }
                            obj = p.getCdr();
                            for (j = 0; j < subvarN; j++) {
                                arrays[j][i] = vars[subvar0 + j];
                            }
                        }
                        for (j = 0; j < subvarN; j++) {
                            vars[subvar0 + j] = arrays[j];
                        }
                        value = 0;
                        if (pairsRequired == 0 && listRequired) {
                            return true;
                        }
                        break;
                    case 6:
                        int npairs = value >> 1;
                        LList o = obj;
                        i = 0;
                        while (true) {
                            if (o instanceof SyntaxForm) {
                                o = ((SyntaxForm) o).getDatum();
                            } else if (i == npairs) {
                                if ((value & 1) == 0) {
                                    if (o != LList.Empty) {
                                    }
                                    value = 0;
                                    pc = pc2;
                                    break;
                                }
                                if (o instanceof Pair) {
                                }
                                value = 0;
                                pc = pc2;
                                pc = pc2;
                                return false;
                            } else if (o instanceof Pair) {
                                o = ((Pair) o).getCdr();
                                i++;
                            } else {
                                pc = pc2;
                                return false;
                            }
                        }
                    case 8:
                        pc = pc2;
                        return obj == LList.Empty;
                    default:
                        disassemble();
                        throw new Error("unrecognized pattern opcode @pc:" + pc2);
                }
            }
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.program);
        out.writeObject(this.literals);
        out.writeInt(this.varCount);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.literals = (Object[]) in.readObject();
        this.program = (String) in.readObject();
        this.varCount = in.readInt();
    }

    public static Object[] allocVars(int varCount, Object[] outer) {
        Object[] vars = new Object[varCount];
        if (outer != null) {
            System.arraycopy(outer, 0, vars, 0, outer.length);
        }
        return vars;
    }

    public static boolean literalIdentifierEq(Object id1, ScopeExp sc1, Object id2, ScopeExp sc2) {
        if (id1 != id2 && (id1 == null || id2 == null || !id1.equals(id2))) {
            return false;
        }
        if (sc1 == sc2) {
            return true;
        }
        Declaration d1 = null;
        Declaration d2 = null;
        while (sc1 != null && !(sc1 instanceof ModuleExp)) {
            d1 = sc1.lookup(id1);
            if (d1 != null) {
                break;
            }
            sc1 = sc1.outer;
        }
        while (sc2 != null && !(sc2 instanceof ModuleExp)) {
            d2 = sc2.lookup(id2);
            if (d2 != null) {
                break;
            }
            sc2 = sc2.outer;
        }
        if (d1 != d2) {
            return false;
        }
        return true;
    }

    public static Object[] getLiteralsList(Object list, SyntaxForm syntax, Translator tr) {
        Object savePos = tr.pushPositionOf(list);
        int count = Translator.listLength(list);
        if (count < 0) {
            tr.error('e', "missing or malformed literals list");
            count = 0;
        }
        Object[] literals = new Object[(count + 1)];
        for (int i = 1; i <= count; i++) {
            SyntaxForm list2;
            Object wrapped;
            while (list2 instanceof SyntaxForm) {
                list2 = list2.getDatum();
            }
            Pair pair = (Pair) list2;
            tr.pushPositionOf(pair);
            Object literal = pair.getCar();
            if (literal instanceof SyntaxForm) {
                wrapped = literal;
                literal = ((SyntaxForm) literal).getDatum();
            } else {
                wrapped = literal;
            }
            if (!(literal instanceof Symbol)) {
                tr.error('e', "non-symbol '" + literal + "' in literals list");
            }
            literals[i] = wrapped;
            list2 = pair.getCdr();
        }
        tr.popPositionOf(savePos);
        return literals;
    }

    public void print(Consumer out) {
        out.write("#<syntax-pattern>");
    }
}

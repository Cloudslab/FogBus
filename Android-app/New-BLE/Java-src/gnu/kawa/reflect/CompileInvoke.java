package gnu.kawa.reflect;

import gnu.bytecode.ClassType;
import gnu.bytecode.Member;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ClassExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.PrimProcedure;
import gnu.mapping.MethodProc;

public class CompileInvoke {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static gnu.expr.Expression validateApplyInvoke(gnu.expr.ApplyExp r70, gnu.expr.InlineCalls r71, gnu.bytecode.Type r72, gnu.mapping.Procedure r73) {
        /*
        r36 = r73;
        r36 = (gnu.kawa.reflect.Invoke) r36;
        r0 = r36;
        r0 = r0.kind;
        r40 = r0;
        r25 = r71.getCompilation();
        r6 = r70.getArgs();
        r0 = r6.length;
        r49 = r0;
        r0 = r25;
        r10 = r0.mustCompile;
        if (r10 == 0) goto L_0x002e;
    L_0x001b:
        if (r49 == 0) goto L_0x002e;
    L_0x001d:
        r10 = 86;
        r0 = r40;
        if (r0 == r10) goto L_0x0029;
    L_0x0023:
        r10 = 42;
        r0 = r40;
        if (r0 != r10) goto L_0x0032;
    L_0x0029:
        r10 = 1;
        r0 = r49;
        if (r0 != r10) goto L_0x0032;
    L_0x002e:
        r70.visitArgs(r71);
    L_0x0031:
        return r70;
    L_0x0032:
        r10 = 0;
        r10 = r6[r10];
        r11 = 0;
        r0 = r71;
        r20 = r0.visit(r10, r11);
        r10 = 0;
        r6[r10] = r20;
        r10 = 86;
        r0 = r40;
        if (r0 == r10) goto L_0x004b;
    L_0x0045:
        r10 = 42;
        r0 = r40;
        if (r0 != r10) goto L_0x0107;
    L_0x004b:
        r67 = r20.getType();
    L_0x004f:
        r0 = r67;
        r10 = r0 instanceof gnu.expr.PairClassType;
        if (r10 == 0) goto L_0x0113;
    L_0x0055:
        r10 = 78;
        r0 = r40;
        if (r0 != r10) goto L_0x0113;
    L_0x005b:
        r67 = (gnu.expr.PairClassType) r67;
        r0 = r67;
        r0 = r0.instanceType;
        r66 = r0;
    L_0x0063:
        r0 = r40;
        r48 = getMethodName(r6, r0);
        r10 = 86;
        r0 = r40;
        if (r0 == r10) goto L_0x0075;
    L_0x006f:
        r10 = 42;
        r0 = r40;
        if (r0 != r10) goto L_0x0123;
    L_0x0075:
        r7 = r49 + -1;
        r8 = 2;
        r9 = 0;
    L_0x0079:
        r10 = 78;
        r0 = r40;
        if (r0 != r10) goto L_0x0228;
    L_0x007f:
        r0 = r66;
        r10 = r0 instanceof gnu.bytecode.ArrayType;
        if (r10 == 0) goto L_0x0228;
    L_0x0085:
        r22 = r66;
        r22 = (gnu.bytecode.ArrayType) r22;
        r30 = r22.getComponentType();
        r61 = 0;
        r43 = 0;
        r10 = r6.length;
        r11 = 3;
        if (r10 < r11) goto L_0x00ca;
    L_0x0095:
        r10 = 1;
        r10 = r6[r10];
        r10 = r10 instanceof gnu.expr.QuoteExp;
        if (r10 == 0) goto L_0x00ca;
    L_0x009c:
        r10 = 1;
        r10 = r6[r10];
        r10 = (gnu.expr.QuoteExp) r10;
        r21 = r10.getValue();
        r0 = r21;
        r10 = r0 instanceof gnu.expr.Keyword;
        if (r10 == 0) goto L_0x00ca;
    L_0x00ab:
        r10 = "length";
        r21 = (gnu.expr.Keyword) r21;
        r48 = r21.getName();
        r0 = r48;
        r10 = r10.equals(r0);
        if (r10 != 0) goto L_0x00c5;
    L_0x00bb:
        r10 = "size";
        r0 = r48;
        r10 = r10.equals(r0);
        if (r10 == 0) goto L_0x00ca;
    L_0x00c5:
        r10 = 2;
        r61 = r6[r10];
        r43 = 1;
    L_0x00ca:
        if (r61 != 0) goto L_0x00d8;
    L_0x00cc:
        r10 = new java.lang.Integer;
        r11 = r6.length;
        r11 = r11 + -1;
        r10.<init>(r11);
        r61 = gnu.expr.QuoteExp.getInstance(r10);
    L_0x00d8:
        r10 = gnu.bytecode.Type.intType;
        r0 = r71;
        r1 = r61;
        r61 = r0.visit(r1, r10);
        r18 = new gnu.expr.ApplyExp;
        r10 = new gnu.kawa.reflect.ArrayNew;
        r0 = r30;
        r10.<init>(r0);
        r11 = 1;
        r11 = new gnu.expr.Expression[r11];
        r12 = 0;
        r11[r12] = r61;
        r0 = r18;
        r0.<init>(r10, r11);
        r0 = r18;
        r1 = r22;
        r0.setType(r1);
        if (r43 == 0) goto L_0x0152;
    L_0x00ff:
        r10 = r6.length;
        r11 = 3;
        if (r10 != r11) goto L_0x0152;
    L_0x0103:
        r70 = r18;
        goto L_0x0031;
    L_0x0107:
        r0 = r36;
        r10 = r0.language;
        r0 = r20;
        r67 = r10.getTypeFor(r0);
        goto L_0x004f;
    L_0x0113:
        r0 = r67;
        r10 = r0 instanceof gnu.bytecode.ObjectType;
        if (r10 == 0) goto L_0x011f;
    L_0x0119:
        r66 = r67;
        r66 = (gnu.bytecode.ObjectType) r66;
        goto L_0x0063;
    L_0x011f:
        r66 = 0;
        goto L_0x0063;
    L_0x0123:
        r10 = 78;
        r0 = r40;
        if (r0 != r10) goto L_0x012f;
    L_0x0129:
        r7 = r49;
        r8 = 0;
        r9 = -1;
        goto L_0x0079;
    L_0x012f:
        r10 = 83;
        r0 = r40;
        if (r0 == r10) goto L_0x013b;
    L_0x0135:
        r10 = 115; // 0x73 float:1.61E-43 double:5.7E-322;
        r0 = r40;
        if (r0 != r10) goto L_0x0141;
    L_0x013b:
        r7 = r49 + -2;
        r8 = 2;
        r9 = -1;
        goto L_0x0079;
    L_0x0141:
        r10 = 80;
        r0 = r40;
        if (r0 != r10) goto L_0x014d;
    L_0x0147:
        r7 = r49 + -2;
        r8 = 3;
        r9 = 1;
        goto L_0x0079;
    L_0x014d:
        r70.visitArgs(r71);
        goto L_0x0031;
    L_0x0152:
        r44 = new gnu.expr.LetExp;
        r10 = 1;
        r10 = new gnu.expr.Expression[r10];
        r11 = 0;
        r10[r11] = r18;
        r0 = r44;
        r0.<init>(r10);
        r10 = 0;
        r10 = (java.lang.String) r10;
        r0 = r44;
        r1 = r22;
        r16 = r0.addDeclaration(r10, r1);
        r0 = r16;
        r1 = r18;
        r0.noteValue(r1);
        r23 = new gnu.expr.BeginExp;
        r23.<init>();
        r35 = 0;
        if (r43 == 0) goto L_0x01ea;
    L_0x017a:
        r33 = 3;
    L_0x017c:
        r10 = r6.length;
        r0 = r33;
        if (r0 >= r10) goto L_0x0212;
    L_0x0181:
        r19 = r6[r33];
        if (r43 == 0) goto L_0x01ac;
    L_0x0185:
        r10 = r33 + 1;
        r11 = r6.length;
        if (r10 >= r11) goto L_0x01ac;
    L_0x018a:
        r0 = r19;
        r10 = r0 instanceof gnu.expr.QuoteExp;
        if (r10 == 0) goto L_0x01ac;
    L_0x0190:
        r10 = r19;
        r10 = (gnu.expr.QuoteExp) r10;
        r38 = r10.getValue();
        r0 = r38;
        r10 = r0 instanceof gnu.expr.Keyword;
        if (r10 == 0) goto L_0x01ac;
    L_0x019e:
        r38 = (gnu.expr.Keyword) r38;
        r41 = r38.getName();
        r35 = java.lang.Integer.parseInt(r41);	 Catch:{ Throwable -> 0x01ed }
        r33 = r33 + 1;
        r19 = r6[r33];	 Catch:{ Throwable -> 0x01ed }
    L_0x01ac:
        r0 = r71;
        r1 = r19;
        r2 = r30;
        r19 = r0.visit(r1, r2);
        r10 = new gnu.expr.ApplyExp;
        r11 = new gnu.kawa.reflect.ArraySet;
        r0 = r30;
        r11.<init>(r0);
        r12 = 3;
        r12 = new gnu.expr.Expression[r12];
        r13 = 0;
        r14 = new gnu.expr.ReferenceExp;
        r0 = r16;
        r14.<init>(r0);
        r12[r13] = r14;
        r13 = 1;
        r14 = new java.lang.Integer;
        r0 = r35;
        r14.<init>(r0);
        r14 = gnu.expr.QuoteExp.getInstance(r14);
        r12[r13] = r14;
        r13 = 2;
        r12[r13] = r19;
        r10.<init>(r11, r12);
        r0 = r23;
        r0.add(r10);
        r35 = r35 + 1;
        r33 = r33 + 1;
        goto L_0x017c;
    L_0x01ea:
        r33 = 1;
        goto L_0x017c;
    L_0x01ed:
        r32 = move-exception;
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "non-integer keyword '";
        r11 = r11.append(r12);
        r0 = r41;
        r11 = r11.append(r0);
        r12 = "' in array constructor";
        r11 = r11.append(r12);
        r11 = r11.toString();
        r0 = r25;
        r0.error(r10, r11);
        goto L_0x0031;
    L_0x0212:
        r10 = new gnu.expr.ReferenceExp;
        r0 = r16;
        r10.<init>(r0);
        r0 = r23;
        r0.add(r10);
        r0 = r23;
        r1 = r44;
        r1.body = r0;
        r70 = r44;
        goto L_0x0031;
    L_0x0228:
        if (r66 == 0) goto L_0x0748;
    L_0x022a:
        if (r48 == 0) goto L_0x0748;
    L_0x022c:
        r0 = r66;
        r10 = r0 instanceof gnu.expr.TypeValue;
        if (r10 == 0) goto L_0x0264;
    L_0x0232:
        r10 = 78;
        r0 = r40;
        if (r0 != r10) goto L_0x0264;
    L_0x0238:
        r10 = r66;
        r10 = (gnu.expr.TypeValue) r10;
        r26 = r10.getConstructor();
        if (r26 == 0) goto L_0x0264;
    L_0x0242:
        r10 = r49 + -1;
        r0 = new gnu.expr.Expression[r10];
        r69 = r0;
        r10 = 1;
        r11 = 0;
        r12 = r49 + -1;
        r0 = r69;
        java.lang.System.arraycopy(r6, r10, r0, r11, r12);
        r10 = new gnu.expr.ApplyExp;
        r0 = r26;
        r1 = r69;
        r10.<init>(r0, r1);
        r0 = r71;
        r1 = r72;
        r70 = r0.visit(r10, r1);
        goto L_0x0031;
    L_0x0264:
        if (r25 != 0) goto L_0x02fe;
    L_0x0266:
        r24 = 0;
    L_0x0268:
        r5 = r66;
        r0 = r48;
        r1 = r24;
        r2 = r36;
        r4 = getMethods(r5, r0, r1, r2);	 Catch:{ Exception -> 0x0314 }
        r51 = gnu.kawa.reflect.ClassMethods.selectApplicable(r4, r7);	 Catch:{ Exception -> 0x0314 }
        r35 = -1;
        r10 = 78;
        r0 = r40;
        if (r0 != r10) goto L_0x04aa;
    L_0x0280:
        r10 = 1;
        r39 = hasKeywordArgument(r10, r6);
        r10 = r6.length;
        r0 = r39;
        if (r0 < r10) goto L_0x02a1;
    L_0x028a:
        if (r51 > 0) goto L_0x04aa;
    L_0x028c:
        r10 = 1;
        r10 = new gnu.bytecode.Type[r10];
        r11 = 0;
        r12 = gnu.expr.Compilation.typeClassType;
        r10[r11] = r12;
        r10 = gnu.kawa.reflect.ClassMethods.selectApplicable(r4, r10);
        r12 = 32;
        r10 = r10 >> r12;
        r12 = 1;
        r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1));
        if (r10 != 0) goto L_0x04aa;
    L_0x02a1:
        r0 = r39;
        r1 = r24;
        r63 = checkKeywords(r5, r6, r0, r1);
        r0 = r63;
        r10 = r0.length;
        r10 = r10 * 2;
        r11 = r6.length;
        r11 = r11 - r39;
        if (r10 == r11) goto L_0x02c7;
    L_0x02b3:
        r10 = "add";
        r11 = 86;
        r12 = 0;
        r0 = r36;
        r13 = r0.language;
        r10 = gnu.kawa.reflect.ClassMethods.getMethods(r5, r10, r11, r12, r13);
        r11 = 2;
        r10 = gnu.kawa.reflect.ClassMethods.selectApplicable(r10, r11);
        if (r10 <= 0) goto L_0x04aa;
    L_0x02c7:
        r31 = 0;
        r33 = 0;
    L_0x02cb:
        r0 = r63;
        r10 = r0.length;
        r0 = r33;
        if (r0 >= r10) goto L_0x033d;
    L_0x02d2:
        r10 = r63[r33];
        r10 = r10 instanceof java.lang.String;
        if (r10 == 0) goto L_0x02fb;
    L_0x02d8:
        if (r31 != 0) goto L_0x0335;
    L_0x02da:
        r31 = new java.lang.StringBuffer;
        r31.<init>();
        r10 = "no field or setter ";
        r0 = r31;
        r0.append(r10);
    L_0x02e6:
        r10 = 96;
        r0 = r31;
        r0.append(r10);
        r10 = r63[r33];
        r0 = r31;
        r0.append(r10);
        r10 = 39;
        r0 = r31;
        r0.append(r10);
    L_0x02fb:
        r33 = r33 + 1;
        goto L_0x02cb;
    L_0x02fe:
        r0 = r25;
        r10 = r0.curClass;
        if (r10 == 0) goto L_0x030c;
    L_0x0304:
        r0 = r25;
        r0 = r0.curClass;
        r24 = r0;
        goto L_0x0268;
    L_0x030c:
        r0 = r25;
        r0 = r0.mainClass;
        r24 = r0;
        goto L_0x0268;
    L_0x0314:
        r32 = move-exception;
        r10 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "unknown class: ";
        r11 = r11.append(r12);
        r12 = r66.getName();
        r11 = r11.append(r12);
        r11 = r11.toString();
        r0 = r25;
        r0.error(r10, r11);
        goto L_0x0031;
    L_0x0335:
        r10 = ", ";
        r0 = r31;
        r0.append(r10);
        goto L_0x02e6;
    L_0x033d:
        if (r31 == 0) goto L_0x035c;
    L_0x033f:
        r10 = " in class ";
        r0 = r31;
        r0.append(r10);
        r10 = r66.getName();
        r0 = r31;
        r0.append(r10);
        r10 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r11 = r31.toString();
        r0 = r25;
        r0.error(r10, r11);
        goto L_0x0031;
    L_0x035c:
        r10 = r6.length;
        r0 = r39;
        if (r0 >= r10) goto L_0x03ed;
    L_0x0361:
        r0 = r39;
        r0 = new gnu.expr.Expression[r0];
        r69 = r0;
        r10 = 0;
        r11 = 0;
        r0 = r69;
        r1 = r39;
        java.lang.System.arraycopy(r6, r10, r0, r11, r1);
        r10 = new gnu.expr.ApplyExp;
        r11 = r70.getFunction();
        r0 = r69;
        r10.<init>(r11, r0);
        r0 = r71;
        r17 = r0.visit(r10, r5);
        r17 = (gnu.expr.ApplyExp) r17;
    L_0x0383:
        r0 = r17;
        r0.setType(r5);
        r29 = r17;
        r10 = r6.length;
        if (r10 <= 0) goto L_0x0498;
    L_0x038d:
        r33 = 0;
    L_0x038f:
        r0 = r63;
        r10 = r0.length;
        r0 = r33;
        if (r0 >= r10) goto L_0x0410;
    L_0x0396:
        r62 = r63[r33];
        r0 = r62;
        r10 = r0 instanceof gnu.bytecode.Method;
        if (r10 == 0) goto L_0x03fe;
    L_0x039e:
        r10 = r62;
        r10 = (gnu.bytecode.Method) r10;
        r10 = r10.getParameterTypes();
        r11 = 0;
        r65 = r10[r11];
    L_0x03a9:
        if (r65 == 0) goto L_0x03b5;
    L_0x03ab:
        r0 = r36;
        r10 = r0.language;
        r0 = r65;
        r65 = r10.getLangTypeFor(r0);
    L_0x03b5:
        r10 = r33 * 2;
        r10 = r10 + r39;
        r10 = r10 + 1;
        r10 = r6[r10];
        r0 = r71;
        r1 = r65;
        r19 = r0.visit(r10, r1);
        r10 = 3;
        r0 = new gnu.expr.Expression[r10];
        r59 = r0;
        r10 = 0;
        r59[r10] = r17;
        r10 = 1;
        r11 = new gnu.expr.QuoteExp;
        r0 = r62;
        r11.<init>(r0);
        r59[r10] = r11;
        r10 = 2;
        r59[r10] = r19;
        r17 = new gnu.expr.ApplyExp;
        r10 = gnu.kawa.reflect.SlotSet.setFieldReturnObject;
        r0 = r17;
        r1 = r59;
        r0.<init>(r10, r1);
        r0 = r17;
        r0.setType(r5);
        r33 = r33 + 1;
        goto L_0x038f;
    L_0x03ed:
        r17 = new gnu.expr.ApplyExp;
        r10 = 0;
        r10 = r4[r10];
        r11 = 1;
        r11 = new gnu.expr.Expression[r11];
        r12 = 0;
        r11[r12] = r20;
        r0 = r17;
        r0.<init>(r10, r11);
        goto L_0x0383;
    L_0x03fe:
        r0 = r62;
        r10 = r0 instanceof gnu.bytecode.Field;
        if (r10 == 0) goto L_0x040d;
    L_0x0404:
        r10 = r62;
        r10 = (gnu.bytecode.Field) r10;
        r65 = r10.getType();
        goto L_0x03a9;
    L_0x040d:
        r65 = 0;
        goto L_0x03a9;
    L_0x0410:
        r10 = r6.length;
        r0 = r39;
        if (r0 != r10) goto L_0x047c;
    L_0x0415:
        r59 = 1;
    L_0x0417:
        r29 = r17;
        r10 = r6.length;
        r0 = r59;
        if (r0 >= r10) goto L_0x0498;
    L_0x041e:
        r44 = new gnu.expr.LetExp;
        r10 = 1;
        r10 = new gnu.expr.Expression[r10];
        r11 = 0;
        r10[r11] = r29;
        r0 = r44;
        r0.<init>(r10);
        r10 = 0;
        r10 = (java.lang.String) r10;
        r0 = r44;
        r16 = r0.addDeclaration(r10, r5);
        r0 = r16;
        r1 = r29;
        r0.noteValue(r1);
        r23 = new gnu.expr.BeginExp;
        r23.<init>();
        r33 = r59;
    L_0x0442:
        r10 = r6.length;
        r0 = r33;
        if (r0 >= r10) goto L_0x0484;
    L_0x0447:
        r10 = 3;
        r0 = new gnu.expr.Expression[r10];
        r34 = r0;
        r10 = 0;
        r11 = new gnu.expr.ReferenceExp;
        r0 = r16;
        r11.<init>(r0);
        r34[r10] = r11;
        r10 = 1;
        r11 = "add";
        r11 = gnu.expr.QuoteExp.getInstance(r11);
        r34[r10] = r11;
        r10 = 2;
        r11 = r6[r33];
        r34[r10] = r11;
        r10 = new gnu.expr.ApplyExp;
        r11 = gnu.kawa.reflect.Invoke.invoke;
        r0 = r34;
        r10.<init>(r11, r0);
        r11 = 0;
        r0 = r71;
        r10 = r0.visit(r10, r11);
        r0 = r23;
        r0.add(r10);
        r33 = r33 + 1;
        goto L_0x0442;
    L_0x047c:
        r0 = r63;
        r10 = r0.length;
        r10 = r10 * 2;
        r59 = r10 + r39;
        goto L_0x0417;
    L_0x0484:
        r10 = new gnu.expr.ReferenceExp;
        r0 = r16;
        r10.<init>(r0);
        r0 = r23;
        r0.add(r10);
        r0 = r23;
        r1 = r44;
        r1.body = r0;
        r29 = r44;
    L_0x0498:
        r0 = r29;
        r1 = r70;
        r10 = r0.setLine(r1);
        r0 = r71;
        r1 = r72;
        r70 = r0.checkType(r10, r1);
        goto L_0x0031;
    L_0x04aa:
        if (r51 < 0) goto L_0x062b;
    L_0x04ac:
        r33 = 1;
    L_0x04ae:
        r0 = r33;
        r1 = r49;
        if (r0 >= r1) goto L_0x0558;
    L_0x04b4:
        r22 = 0;
        r10 = r49 + -1;
        r0 = r33;
        if (r0 != r10) goto L_0x04e5;
    L_0x04bc:
        r42 = 1;
    L_0x04be:
        r10 = 80;
        r0 = r40;
        if (r0 != r10) goto L_0x04c9;
    L_0x04c4:
        r10 = 2;
        r0 = r33;
        if (r0 == r10) goto L_0x04d4;
    L_0x04c9:
        r10 = 78;
        r0 = r40;
        if (r0 == r10) goto L_0x04e8;
    L_0x04cf:
        r10 = 1;
        r0 = r33;
        if (r0 != r10) goto L_0x04e8;
    L_0x04d4:
        r22 = 0;
    L_0x04d6:
        r10 = r6[r33];
        r0 = r71;
        r1 = r22;
        r10 = r0.visit(r10, r1);
        r6[r33] = r10;
        r33 = r33 + 1;
        goto L_0x04ae;
    L_0x04e5:
        r42 = 0;
        goto L_0x04be;
    L_0x04e8:
        r10 = 80;
        r0 = r40;
        if (r0 != r10) goto L_0x04f6;
    L_0x04ee:
        r10 = 1;
        r0 = r33;
        if (r0 != r10) goto L_0x04f6;
    L_0x04f3:
        r22 = r5;
        goto L_0x04d6;
    L_0x04f6:
        if (r51 <= 0) goto L_0x04d6;
    L_0x04f8:
        r10 = 78;
        r0 = r40;
        if (r0 != r10) goto L_0x0531;
    L_0x04fe:
        r10 = 1;
    L_0x04ff:
        r55 = r33 - r10;
        r37 = 0;
    L_0x0503:
        r0 = r37;
        r1 = r51;
        if (r0 >= r1) goto L_0x04d6;
    L_0x0509:
        r57 = r4[r37];
        r10 = 83;
        r0 = r40;
        if (r0 == r10) goto L_0x0533;
    L_0x0511:
        r10 = r57.takesTarget();
        if (r10 == 0) goto L_0x0533;
    L_0x0517:
        r10 = 1;
    L_0x0518:
        r56 = r55 + r10;
        if (r42 == 0) goto L_0x0535;
    L_0x051c:
        r10 = r57.takesVarArgs();
        if (r10 == 0) goto L_0x0535;
    L_0x0522:
        r10 = r57.minArgs();
        r0 = r56;
        if (r0 != r10) goto L_0x0535;
    L_0x052a:
        r22 = 0;
    L_0x052c:
        if (r22 == 0) goto L_0x04d6;
    L_0x052e:
        r37 = r37 + 1;
        goto L_0x0503;
    L_0x0531:
        r10 = r8;
        goto L_0x04ff;
    L_0x0533:
        r10 = 0;
        goto L_0x0518;
    L_0x0535:
        r0 = r57;
        r1 = r56;
        r58 = r0.getParameterType(r1);
        if (r37 != 0) goto L_0x0542;
    L_0x053f:
        r22 = r58;
        goto L_0x052c;
    L_0x0542:
        r0 = r58;
        r10 = r0 instanceof gnu.bytecode.PrimType;
        r0 = r22;
        r11 = r0 instanceof gnu.bytecode.PrimType;
        if (r10 == r11) goto L_0x054f;
    L_0x054c:
        r22 = 0;
        goto L_0x052c;
    L_0x054f:
        r0 = r22;
        r1 = r58;
        r22 = gnu.bytecode.Type.lowestCommonSuperType(r0, r1);
        goto L_0x052c;
    L_0x0558:
        r52 = selectApplicable(r4, r5, r6, r7, r8, r9);
        r10 = 32;
        r10 = r52 >> r10;
        r0 = (int) r10;
        r54 = r0;
        r0 = r52;
        r0 = (int) r0;
        r46 = r0;
    L_0x0568:
        r0 = r4.length;
        r50 = r0;
        r10 = r54 + r46;
        if (r10 != 0) goto L_0x0598;
    L_0x056f:
        r10 = 78;
        r0 = r40;
        if (r0 != r10) goto L_0x0598;
    L_0x0575:
        r10 = "valueOf";
        r11 = gnu.kawa.reflect.Invoke.invokeStatic;
        r0 = r24;
        r4 = getMethods(r5, r10, r0, r11);
        r8 = 1;
        r7 = r49 + -1;
        r15 = -1;
        r10 = r4;
        r11 = r5;
        r12 = r6;
        r13 = r7;
        r14 = r8;
        r52 = selectApplicable(r10, r11, r12, r13, r14, r15);
        r10 = 32;
        r10 = r52 >> r10;
        r0 = (int) r10;
        r54 = r0;
        r0 = r52;
        r0 = (int) r0;
        r46 = r0;
    L_0x0598:
        r10 = r54 + r46;
        if (r10 != 0) goto L_0x0658;
    L_0x059c:
        r10 = 80;
        r0 = r40;
        if (r0 == r10) goto L_0x05a8;
    L_0x05a2:
        r10 = r25.warnInvokeUnknownMethod();
        if (r10 == 0) goto L_0x05fc;
    L_0x05a8:
        r10 = 78;
        r0 = r40;
        if (r0 != r10) goto L_0x05c3;
    L_0x05ae:
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r0 = r48;
        r10 = r10.append(r0);
        r11 = "/valueOf";
        r10 = r10.append(r11);
        r48 = r10.toString();
    L_0x05c3:
        r60 = new java.lang.StringBuilder;
        r60.<init>();
        r10 = r4.length;
        r10 = r10 + r50;
        if (r10 != 0) goto L_0x0631;
    L_0x05cd:
        r10 = "no accessible method '";
        r0 = r60;
        r0.append(r10);
    L_0x05d4:
        r0 = r60;
        r1 = r48;
        r0.append(r1);
        r10 = "' in ";
        r0 = r60;
        r0.append(r10);
        r10 = r66.getName();
        r0 = r60;
        r0.append(r10);
        r10 = 80;
        r0 = r40;
        if (r0 != r10) goto L_0x0655;
    L_0x05f1:
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x05f3:
        r11 = r60.toString();
        r0 = r25;
        r0.error(r10, r11);
    L_0x05fc:
        if (r35 < 0) goto L_0x0748;
    L_0x05fe:
        r0 = new gnu.expr.Expression[r7];
        r45 = r0;
        r47 = r4[r35];
        r68 = r47.takesVarArgs();
        r27 = 0;
        if (r9 < 0) goto L_0x0614;
    L_0x060c:
        r28 = r27 + 1;
        r10 = r6[r9];
        r45[r27] = r10;
        r27 = r28;
    L_0x0614:
        r64 = r8;
    L_0x0616:
        r10 = r6.length;
        r0 = r64;
        if (r0 >= r10) goto L_0x072a;
    L_0x061b:
        r0 = r45;
        r10 = r0.length;
        r0 = r27;
        if (r0 >= r10) goto L_0x072a;
    L_0x0622:
        r10 = r6[r64];
        r45[r27] = r10;
        r64 = r64 + 1;
        r27 = r27 + 1;
        goto L_0x0616;
    L_0x062b:
        r54 = 0;
        r46 = 0;
        goto L_0x0568;
    L_0x0631:
        r10 = -983040; // 0xfffffffffff10000 float:NaN double:NaN;
        r0 = r51;
        if (r0 != r10) goto L_0x063f;
    L_0x0637:
        r10 = "too few arguments for method '";
        r0 = r60;
        r0.append(r10);
        goto L_0x05d4;
    L_0x063f:
        r10 = -917504; // 0xfffffffffff20000 float:NaN double:NaN;
        r0 = r51;
        if (r0 != r10) goto L_0x064d;
    L_0x0645:
        r10 = "too many arguments for method '";
        r0 = r60;
        r0.append(r10);
        goto L_0x05d4;
    L_0x064d:
        r10 = "no possibly applicable method '";
        r0 = r60;
        r0.append(r10);
        goto L_0x05d4;
    L_0x0655:
        r10 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x05f3;
    L_0x0658:
        r10 = 1;
        r0 = r54;
        if (r0 == r10) goto L_0x0664;
    L_0x065d:
        if (r54 != 0) goto L_0x0667;
    L_0x065f:
        r10 = 1;
        r0 = r46;
        if (r0 != r10) goto L_0x0667;
    L_0x0664:
        r35 = 0;
        goto L_0x05fc;
    L_0x0667:
        if (r54 <= 0) goto L_0x06de;
    L_0x0669:
        r0 = r54;
        r35 = gnu.mapping.MethodProc.mostSpecific(r4, r0);
        if (r35 >= 0) goto L_0x068b;
    L_0x0671:
        r10 = 83;
        r0 = r40;
        if (r0 != r10) goto L_0x068b;
    L_0x0677:
        r33 = 0;
    L_0x0679:
        r0 = r33;
        r1 = r54;
        if (r0 >= r1) goto L_0x068b;
    L_0x067f:
        r10 = r4[r33];
        r10 = r10.getStaticFlag();
        if (r10 == 0) goto L_0x06d8;
    L_0x0687:
        if (r35 < 0) goto L_0x06d6;
    L_0x0689:
        r35 = -1;
    L_0x068b:
        if (r35 >= 0) goto L_0x05fc;
    L_0x068d:
        r10 = 80;
        r0 = r40;
        if (r0 == r10) goto L_0x0699;
    L_0x0693:
        r10 = r25.warnInvokeUnknownMethod();
        if (r10 == 0) goto L_0x05fc;
    L_0x0699:
        r60 = new java.lang.StringBuffer;
        r60.<init>();
        r10 = "more than one definitely applicable method `";
        r0 = r60;
        r0.append(r10);
        r0 = r60;
        r1 = r48;
        r0.append(r1);
        r10 = "' in ";
        r0 = r60;
        r0.append(r10);
        r10 = r66.getName();
        r0 = r60;
        r0.append(r10);
        r0 = r54;
        r1 = r60;
        append(r4, r0, r1);
        r10 = 80;
        r0 = r40;
        if (r0 != r10) goto L_0x06db;
    L_0x06c9:
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x06cb:
        r11 = r60.toString();
        r0 = r25;
        r0.error(r10, r11);
        goto L_0x05fc;
    L_0x06d6:
        r35 = r33;
    L_0x06d8:
        r33 = r33 + 1;
        goto L_0x0679;
    L_0x06db:
        r10 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x06cb;
    L_0x06de:
        r10 = 80;
        r0 = r40;
        if (r0 == r10) goto L_0x06ea;
    L_0x06e4:
        r10 = r25.warnInvokeUnknownMethod();
        if (r10 == 0) goto L_0x05fc;
    L_0x06ea:
        r60 = new java.lang.StringBuffer;
        r60.<init>();
        r10 = "more than one possibly applicable method '";
        r0 = r60;
        r0.append(r10);
        r0 = r60;
        r1 = r48;
        r0.append(r1);
        r10 = "' in ";
        r0 = r60;
        r0.append(r10);
        r10 = r66.getName();
        r0 = r60;
        r0.append(r10);
        r0 = r46;
        r1 = r60;
        append(r4, r0, r1);
        r10 = 80;
        r0 = r40;
        if (r0 != r10) goto L_0x0727;
    L_0x071a:
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x071c:
        r11 = r60.toString();
        r0 = r25;
        r0.error(r10, r11);
        goto L_0x05fc;
    L_0x0727:
        r10 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        goto L_0x071c;
    L_0x072a:
        r29 = new gnu.expr.ApplyExp;
        r0 = r29;
        r1 = r47;
        r2 = r45;
        r0.<init>(r1, r2);
        r0 = r29;
        r1 = r70;
        r0.setLine(r1);
        r0 = r71;
        r1 = r29;
        r2 = r72;
        r70 = r0.visitApplyOnly(r1, r2);
        goto L_0x0031;
    L_0x0748:
        r70.visitArgs(r71);
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.reflect.CompileInvoke.validateApplyInvoke(gnu.expr.ApplyExp, gnu.expr.InlineCalls, gnu.bytecode.Type, gnu.mapping.Procedure):gnu.expr.Expression");
    }

    static Object[] checkKeywords(ObjectType type, Expression[] args, int start, ClassType caller) {
        int len = args.length;
        int npairs = 0;
        while (((npairs * 2) + start) + 1 < len && (args[(npairs * 2) + start].valueIfConstant() instanceof Keyword)) {
            npairs++;
        }
        Object[] fields = new Object[npairs];
        for (int i = 0; i < npairs; i++) {
            String name = ((Keyword) args[(i * 2) + start].valueIfConstant()).getName();
            Member slot = SlotSet.lookupMember(type, name, caller);
            if (slot == null) {
                slot = type.getMethod(ClassExp.slotToMethodName("add", name), SlotSet.type1Array);
            }
            if (slot == null) {
                Object slot2 = name;
            }
            fields[i] = slot;
        }
        return fields;
    }

    private static String getMethodName(Expression[] args, char kind) {
        if (kind == 'N') {
            return "<init>";
        }
        int nameIndex = kind == 'P' ? 2 : 1;
        if (args.length >= nameIndex + 1) {
            return ClassMethods.checkName(args[nameIndex], false);
        }
        return null;
    }

    private static void append(PrimProcedure[] methods, int mcount, StringBuffer sbuf) {
        for (int i = 0; i < mcount; i++) {
            sbuf.append("\n  candidate: ");
            sbuf.append(methods[i]);
        }
    }

    protected static PrimProcedure[] getMethods(ObjectType ctype, String mname, ClassType caller, Invoke iproc) {
        char c = 'P';
        int kind = iproc.kind;
        if (kind != 80) {
            c = (kind == 42 || kind == 86) ? 'V' : '\u0000';
        }
        return ClassMethods.getMethods(ctype, mname, c, caller, iproc.language);
    }

    static int hasKeywordArgument(int argsStartIndex, Expression[] args) {
        for (int i = argsStartIndex; i < args.length; i++) {
            if (args[i].valueIfConstant() instanceof Keyword) {
                return i;
            }
        }
        return args.length;
    }

    private static long selectApplicable(PrimProcedure[] methods, ObjectType ctype, Expression[] args, int margsLength, int argsStartIndex, int objIndex) {
        Type[] atypes = new Type[margsLength];
        int i = 0;
        if (objIndex >= 0) {
            int dst = 0 + 1;
            atypes[0] = ctype;
            i = dst;
        }
        int src = argsStartIndex;
        while (src < args.length && i < atypes.length) {
            Expression arg = args[src];
            Type atype = null;
            if (InlineCalls.checkIntValue(arg) != null) {
                atype = Type.intType;
            } else if (InlineCalls.checkLongValue(arg) != null) {
                atype = Type.longType;
            } else if (null == null) {
                atype = arg.getType();
            }
            atypes[i] = atype;
            src++;
            i++;
        }
        return ClassMethods.selectApplicable(methods, atypes);
    }

    public static synchronized PrimProcedure getStaticMethod(ClassType type, String name, Expression[] args) {
        PrimProcedure primProcedure;
        synchronized (CompileInvoke.class) {
            int index;
            MethodProc[] methods = getMethods(type, name, null, Invoke.invokeStatic);
            long num = selectApplicable(methods, type, args, args.length, 0, -1);
            int okCount = (int) (num >> 32);
            int maybeCount = (int) num;
            if (methods == null) {
                index = -1;
            } else if (okCount > 0) {
                index = MethodProc.mostSpecific(methods, okCount);
            } else if (maybeCount == 1) {
                index = 0;
            } else {
                index = -1;
            }
            if (index < 0) {
                primProcedure = null;
            } else {
                primProcedure = methods[index];
            }
        }
        return primProcedure;
    }
}

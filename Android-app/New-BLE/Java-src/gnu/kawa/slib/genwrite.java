package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Format;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;

/* compiled from: genwrite.scm */
public class genwrite extends ModuleBody {
    public static final genwrite $instance = new genwrite();
    static final Char Lit0 = Char.make(10);
    static final IntNum Lit1 = IntNum.make(0);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("and").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("or").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("let").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("do").readResolve());
    static final IntNum Lit15 = IntNum.make(7);
    static final IntNum Lit16 = IntNum.make(8);
    static final IntNum Lit17 = IntNum.make(1);
    static final IntNum Lit18 = IntNum.make(50);
    static final IntNum Lit19 = IntNum.make(2);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("lambda").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("pp-expr").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("pp-expr-list").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("pp-LAMBDA").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("pp-IF").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("pp-COND").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("pp-CASE").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("pp-AND").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("pp-LET").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("pp-BEGIN").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("pp-DO").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("let*").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.unquote_sym).readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.unquotesplicing_sym).readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("generic-write").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("reverse-string-append").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("letrec").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("define").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("if").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("set!").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("cond").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("case").readResolve());
    public static final ModuleMethod generic$Mnwrite;
    public static final ModuleMethod reverse$Mnstring$Mnappend;

    /* compiled from: genwrite.scm */
    public class frame0 extends ModuleBody {
        Procedure pp$MnAND = new ModuleMethod(this, 8, genwrite.Lit26, 12291);
        Procedure pp$MnBEGIN = new ModuleMethod(this, 10, genwrite.Lit28, 12291);
        Procedure pp$MnCASE = new ModuleMethod(this, 7, genwrite.Lit25, 12291);
        Procedure pp$MnCOND = new ModuleMethod(this, 6, genwrite.Lit24, 12291);
        Procedure pp$MnDO = new ModuleMethod(this, 11, genwrite.Lit29, 12291);
        Procedure pp$MnIF = new ModuleMethod(this, 5, genwrite.Lit23, 12291);
        Procedure pp$MnLAMBDA = new ModuleMethod(this, 4, genwrite.Lit22, 12291);
        Procedure pp$MnLET = new ModuleMethod(this, 9, genwrite.Lit27, 12291);
        Procedure pp$Mnexpr = new ModuleMethod(this, 2, genwrite.Lit20, 12291);
        Procedure pp$Mnexpr$Mnlist = new ModuleMethod(this, 3, genwrite.Lit21, 12291);
        frame staticLink;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda6indent(java.lang.Object r10, java.lang.Object r11) {
            /*
            r9 = this;
            r4 = java.lang.Boolean.FALSE;
            if (r11 == r4) goto L_0x006d;
        L_0x0004:
            r4 = kawa.standard.Scheme.numLss;
            r4 = r4.apply2(r10, r11);
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x0065;
        L_0x000e:
            r4 = r9.staticLink;
            r5 = 1;
            r6 = gnu.kawa.slib.genwrite.Lit0;
            r5 = kawa.lib.strings.makeString(r5, r6);
            r3 = r4.lambda4out(r5, r11);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x0064;
        L_0x001f:
            r11 = gnu.kawa.slib.genwrite.Lit1;
            r2 = r10;
            r5 = r2;
        L_0x0023:
            r1 = r9;
            r4 = kawa.standard.Scheme.numGrt;
            r6 = gnu.kawa.slib.genwrite.Lit1;
            r4 = r4.apply2(r5, r6);
            r6 = java.lang.Boolean.FALSE;
            if (r4 == r6) goto L_0x0063;
        L_0x0030:
            r4 = kawa.standard.Scheme.numGrt;
            r6 = gnu.kawa.slib.genwrite.Lit15;
            r4 = r4.apply2(r5, r6);
            r6 = java.lang.Boolean.FALSE;
            if (r4 == r6) goto L_0x004e;
        L_0x003c:
            r4 = gnu.kawa.functions.AddOp.$Mn;
            r6 = gnu.kawa.slib.genwrite.Lit16;
            r2 = r4.apply2(r5, r6);
            r4 = r9.staticLink;
            r5 = "        ";
            r11 = r4.lambda4out(r5, r11);
            r5 = r2;
            goto L_0x0023;
        L_0x004e:
            r6 = r9.staticLink;
            r7 = "        ";
            r8 = 0;
            r0 = r5;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x006f }
            r4 = r0;
            r4 = r4.intValue();	 Catch:{ ClassCastException -> 0x006f }
            r4 = kawa.lib.strings.substring(r7, r8, r4);
            r11 = r6.lambda4out(r4, r11);
        L_0x0063:
            r3 = r11;
        L_0x0064:
            return r3;
        L_0x0065:
            r4 = gnu.kawa.functions.AddOp.$Mn;
            r2 = r4.apply2(r10, r11);
            r5 = r2;
            goto L_0x0023;
        L_0x006d:
            r3 = r11;
            goto L_0x0064;
        L_0x006f:
            r4 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "substring";
            r8 = 3;
            r6.<init>(r4, r7, r8, r5);
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.genwrite.frame0.lambda6indent(java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public Object lambda7pr(Object obj, Object col, Object extra, Object pp$Mnpair) {
            frame1 gnu_kawa_slib_genwrite_frame1 = new frame1();
            gnu_kawa_slib_genwrite_frame1.staticLink = this;
            boolean x = lists.isPair(obj);
            if (!x ? vectors.isVector(obj) : x) {
                return this.staticLink.lambda5wr(obj, col);
            }
            LList lList = LList.Empty;
            gnu_kawa_slib_genwrite_frame1.left = numbers.min(AddOp.$Pl.apply2(AddOp.$Mn.apply2(AddOp.$Mn.apply2(this.staticLink.width, col), extra), genwrite.Lit17), genwrite.Lit18);
            gnu_kawa_slib_genwrite_frame1.result = lList;
            genwrite.genericWrite(obj, this.staticLink.display$Qu, Boolean.FALSE, gnu_kawa_slib_genwrite_frame1.lambda$Fn1);
            if (Scheme.numGrt.apply2(gnu_kawa_slib_genwrite_frame1.left, genwrite.Lit1) != Boolean.FALSE) {
                return this.staticLink.lambda4out(genwrite.reverseStringAppend(gnu_kawa_slib_genwrite_frame1.result), col);
            }
            if (lists.isPair(obj)) {
                return Scheme.applyToArgs.apply4(pp$Mnpair, obj, col, extra);
            }
            try {
                return lambda10ppList(vectors.vector$To$List((FVector) obj), this.staticLink.lambda4out("#", col), extra, this.pp$Mnexpr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "vector->list", 1, obj);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda8ppExpr(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
            /*
            r11 = this;
            r0 = gnu.kawa.slib.genwrite.frame.lambda1isReadMacro(r12);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x001d;
        L_0x0008:
            r0 = gnu.kawa.slib.genwrite.frame.lambda2readMacroBody(r12);
            r1 = r11.staticLink;
            r2 = gnu.kawa.slib.genwrite.frame.lambda3readMacroPrefix(r12);
            r1 = r1.lambda4out(r2, r13);
            r2 = r11.pp$Mnexpr;
            r0 = r11.lambda7pr(r0, r1, r14, r2);
        L_0x001c:
            return r0;
        L_0x001d:
            r0 = kawa.lib.lists.car;
            r8 = r0.apply1(r12);
            r0 = kawa.lib.misc.isSymbol(r8);
            if (r0 == 0) goto L_0x012c;
        L_0x0029:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit2;
            r10 = r0.apply2(r8, r1);
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x0046;
        L_0x0035:
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x0056;
        L_0x0039:
            r9 = r11.pp$MnLAMBDA;
        L_0x003b:
            r0 = java.lang.Boolean.FALSE;
            if (r9 == r0) goto L_0x0105;
        L_0x003f:
            r0 = kawa.standard.Scheme.applyToArgs;
            r0 = r0.apply4(r9, r12, r13, r14);
            goto L_0x001c;
        L_0x0046:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit3;
            r10 = r0.apply2(r8, r1);
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x0069;
        L_0x0052:
            r0 = java.lang.Boolean.FALSE;
            if (r10 != r0) goto L_0x0039;
        L_0x0056:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit6;
            r10 = r0.apply2(r8, r1);
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x0087;
        L_0x0062:
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x0093;
        L_0x0066:
            r9 = r11.pp$MnIF;
            goto L_0x003b;
        L_0x0069:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit4;
            r10 = r0.apply2(r8, r1);
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x007a;
        L_0x0075:
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x0056;
        L_0x0079:
            goto L_0x0039;
        L_0x007a:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit5;
            r0 = r0.apply2(r8, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x0056;
        L_0x0086:
            goto L_0x0039;
        L_0x0087:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit7;
            r0 = r0.apply2(r8, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 != r1) goto L_0x0066;
        L_0x0093:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit8;
            r0 = r0.apply2(r8, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x00a2;
        L_0x009f:
            r9 = r11.pp$MnCOND;
            goto L_0x003b;
        L_0x00a2:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit9;
            r0 = r0.apply2(r8, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x00b1;
        L_0x00ae:
            r9 = r11.pp$MnCASE;
            goto L_0x003b;
        L_0x00b1:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit10;
            r10 = r0.apply2(r8, r1);
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x00c5;
        L_0x00bd:
            r0 = java.lang.Boolean.FALSE;
            if (r10 == r0) goto L_0x00d1;
        L_0x00c1:
            r9 = r11.pp$MnAND;
            goto L_0x003b;
        L_0x00c5:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit11;
            r0 = r0.apply2(r8, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 != r1) goto L_0x00c1;
        L_0x00d1:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit12;
            r0 = r0.apply2(r8, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x00e1;
        L_0x00dd:
            r9 = r11.pp$MnLET;
            goto L_0x003b;
        L_0x00e1:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit13;
            r0 = r0.apply2(r8, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x00f1;
        L_0x00ed:
            r9 = r11.pp$MnBEGIN;
            goto L_0x003b;
        L_0x00f1:
            r0 = kawa.standard.Scheme.isEqv;
            r1 = gnu.kawa.slib.genwrite.Lit14;
            r0 = r0.apply2(r8, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x0101;
        L_0x00fd:
            r9 = r11.pp$MnDO;
            goto L_0x003b;
        L_0x0101:
            r9 = java.lang.Boolean.FALSE;
            goto L_0x003b;
        L_0x0105:
            r8 = (gnu.mapping.Symbol) r8;	 Catch:{ ClassCastException -> 0x0134 }
            r0 = kawa.lib.misc.symbol$To$String(r8);
            r0 = kawa.lib.strings.stringLength(r0);
            r1 = 5;
            if (r0 <= r1) goto L_0x0124;
        L_0x0112:
            r4 = java.lang.Boolean.FALSE;
            r5 = java.lang.Boolean.FALSE;
            r6 = java.lang.Boolean.FALSE;
            r7 = r11.pp$Mnexpr;
            r0 = r11;
            r1 = r12;
            r2 = r13;
            r3 = r14;
            r0 = r0.lambda12ppGeneral(r1, r2, r3, r4, r5, r6, r7);
            goto L_0x001c;
        L_0x0124:
            r0 = r11.pp$Mnexpr;
            r0 = r11.lambda9ppCall(r12, r13, r14, r0);
            goto L_0x001c;
        L_0x012c:
            r0 = r11.pp$Mnexpr;
            r0 = r11.lambda10ppList(r12, r13, r14, r0);
            goto L_0x001c;
        L_0x0134:
            r0 = move-exception;
            r1 = new gnu.mapping.WrongType;
            r2 = "symbol->string";
            r3 = 1;
            r1.<init>(r0, r2, r3, r8);
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.genwrite.frame0.lambda8ppExpr(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 2:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 3:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 4:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 5:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 6:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 7:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 8:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 9:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 10:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.value2 = obj2;
                    callContext.value3 = obj3;
                    callContext.proc = moduleMethod;
                    callContext.pc = 3;
                    return 0;
                default:
                    return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
        }

        public Object lambda9ppCall(Object expr, Object col, Object extra, Object pp$Mnitem) {
            Object col$St = this.staticLink.lambda5wr(lists.car.apply1(expr), this.staticLink.lambda4out("(", col));
            if (col == Boolean.FALSE) {
                return col;
            }
            return lambda11ppDown(lists.cdr.apply1(expr), col$St, AddOp.$Pl.apply2(col$St, genwrite.Lit17), extra, pp$Mnitem);
        }

        public Object lambda10ppList(Object l, Object col, Object extra, Object pp$Mnitem) {
            col = this.staticLink.lambda4out("(", col);
            return lambda11ppDown(l, col, col, extra, pp$Mnitem);
        }

        public Object lambda11ppDown(Object l, Object obj, Object col2, Object obj2, Object pp$Mnitem) {
            Boolean lambda7pr;
            while (true) {
                frame0 closureEnv = this;
                if (lambda7pr != Boolean.FALSE) {
                    if (!lists.isPair(l)) {
                        break;
                    }
                    Object rest = lists.cdr.apply1(l);
                    l = rest;
                    lambda7pr = lambda7pr(lists.car.apply1(l), lambda6indent(col2, lambda7pr), lists.isNull(rest) ? AddOp.$Pl.apply2(obj2, genwrite.Lit17) : genwrite.Lit1, pp$Mnitem);
                } else {
                    return lambda7pr;
                }
            }
            return lists.isNull(l) ? this.staticLink.lambda4out(")", lambda7pr) : this.staticLink.lambda4out(")", lambda7pr(l, lambda6indent(col2, this.staticLink.lambda4out(".", lambda6indent(col2, lambda7pr))), AddOp.$Pl.apply2(obj2, genwrite.Lit17), pp$Mnitem));
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda12ppGeneral(java.lang.Object r16, java.lang.Object r17, java.lang.Object r18, java.lang.Object r19, java.lang.Object r20, java.lang.Object r21, java.lang.Object r22) {
            /*
            r15 = this;
            r1 = kawa.lib.lists.car;
            r0 = r16;
            r11 = r1.apply1(r0);
            r1 = kawa.lib.lists.cdr;
            r0 = r16;
            r13 = r1.apply1(r0);
            r1 = r15.staticLink;
            r2 = r15.staticLink;
            r3 = "(";
            r0 = r17;
            r2 = r2.lambda4out(r3, r0);
            r8 = r1.lambda5wr(r11, r2);
            r1 = java.lang.Boolean.FALSE;
            r0 = r19;
            if (r0 == r1) goto L_0x00cb;
        L_0x0026:
            r1 = kawa.lib.lists.isPair(r13);
            if (r1 == 0) goto L_0x00d1;
        L_0x002c:
            r1 = kawa.lib.lists.car;
            r12 = r1.apply1(r13);
            r1 = kawa.lib.lists.cdr;
            r13 = r1.apply1(r13);
            r1 = r15.staticLink;
            r2 = r15.staticLink;
            r3 = " ";
            r2 = r2.lambda4out(r3, r8);
            r9 = r1.lambda5wr(r12, r2);
            r1 = gnu.kawa.functions.AddOp.$Pl;
            r2 = gnu.kawa.slib.genwrite.Lit19;
            r0 = r17;
            r2 = r1.apply2(r0, r2);
            r1 = gnu.kawa.functions.AddOp.$Pl;
            r3 = gnu.kawa.slib.genwrite.Lit17;
            r1 = r1.apply2(r9, r3);
        L_0x0058:
            r7 = r15;
            r3 = java.lang.Boolean.FALSE;
            r0 = r20;
            if (r0 == r3) goto L_0x00e6;
        L_0x005f:
            r3 = kawa.lib.lists.isPair(r13);
            if (r3 == 0) goto L_0x008b;
        L_0x0065:
            r3 = kawa.lib.lists.car;
            r14 = r3.apply1(r13);
            r3 = kawa.lib.lists.cdr;
            r13 = r3.apply1(r13);
            r3 = kawa.lib.lists.isNull(r13);
            if (r3 == 0) goto L_0x00ee;
        L_0x0077:
            r3 = gnu.kawa.functions.AddOp.$Pl;
            r4 = gnu.kawa.slib.genwrite.Lit17;
            r0 = r18;
            r10 = r3.apply2(r0, r4);
        L_0x0081:
            r3 = r15.lambda6indent(r1, r9);
            r0 = r20;
            r9 = r15.lambda7pr(r14, r3, r10, r0);
        L_0x008b:
            r7 = r15;
            r3 = java.lang.Boolean.FALSE;
            r0 = r21;
            if (r0 == r3) goto L_0x00f1;
        L_0x0092:
            r3 = kawa.lib.lists.isPair(r13);
            if (r3 == 0) goto L_0x00f7;
        L_0x0098:
            r3 = kawa.lib.lists.car;
            r14 = r3.apply1(r13);
            r3 = kawa.lib.lists.cdr;
            r13 = r3.apply1(r13);
            r3 = kawa.lib.lists.isNull(r13);
            if (r3 == 0) goto L_0x00fb;
        L_0x00aa:
            r3 = gnu.kawa.functions.AddOp.$Pl;
            r4 = gnu.kawa.slib.genwrite.Lit17;
            r0 = r18;
            r10 = r3.apply2(r0, r4);
        L_0x00b4:
            r1 = r15.lambda6indent(r1, r9);
            r0 = r21;
            r3 = r15.lambda7pr(r14, r1, r10, r0);
            r4 = r2;
            r2 = r13;
        L_0x00c0:
            r7 = r15;
            r1 = r15;
            r5 = r18;
            r6 = r22;
            r1 = r1.lambda11ppDown(r2, r3, r4, r5, r6);
            return r1;
        L_0x00cb:
            r1 = java.lang.Boolean.FALSE;
            r0 = r19;
            if (r0 != r1) goto L_0x002c;
        L_0x00d1:
            r1 = gnu.kawa.functions.AddOp.$Pl;
            r2 = gnu.kawa.slib.genwrite.Lit19;
            r0 = r17;
            r2 = r1.apply2(r0, r2);
            r1 = gnu.kawa.functions.AddOp.$Pl;
            r3 = gnu.kawa.slib.genwrite.Lit17;
            r1 = r1.apply2(r8, r3);
            r9 = r8;
            goto L_0x0058;
        L_0x00e6:
            r3 = java.lang.Boolean.FALSE;
            r0 = r20;
            if (r0 == r3) goto L_0x008b;
        L_0x00ec:
            goto L_0x0065;
        L_0x00ee:
            r10 = gnu.kawa.slib.genwrite.Lit1;
            goto L_0x0081;
        L_0x00f1:
            r3 = java.lang.Boolean.FALSE;
            r0 = r21;
            if (r0 != r3) goto L_0x0098;
        L_0x00f7:
            r3 = r9;
            r4 = r2;
            r2 = r13;
            goto L_0x00c0;
        L_0x00fb:
            r10 = gnu.kawa.slib.genwrite.Lit1;
            goto L_0x00b4;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.genwrite.frame0.lambda12ppGeneral(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public Object lambda13ppExprList(Object l, Object col, Object extra) {
            return lambda10ppList(l, col, extra, this.pp$Mnexpr);
        }

        public Object lambda14pp$MnLAMBDA(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda15pp$MnIF(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda16pp$MnCOND(Object expr, Object col, Object extra) {
            return lambda9ppCall(expr, col, extra, this.pp$Mnexpr$Mnlist);
        }

        public Object lambda17pp$MnCASE(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr, Boolean.FALSE, this.pp$Mnexpr$Mnlist);
        }

        public Object lambda18pp$MnAND(Object expr, Object col, Object extra) {
            return lambda9ppCall(expr, col, extra, this.pp$Mnexpr);
        }

        public Object lambda19pp$MnLET(Object expr, Object col, Object extra) {
            boolean named$Qu;
            Object rest = lists.cdr.apply1(expr);
            boolean x = lists.isPair(rest);
            if (x) {
                named$Qu = misc.isSymbol(lists.car.apply1(rest));
            } else {
                named$Qu = x;
            }
            return lambda12ppGeneral(expr, col, extra, named$Qu ? Boolean.TRUE : Boolean.FALSE, this.pp$Mnexpr$Mnlist, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object lambda20pp$MnBEGIN(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE, this.pp$Mnexpr);
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            switch (moduleMethod.selector) {
                case 2:
                    return lambda8ppExpr(obj, obj2, obj3);
                case 3:
                    return lambda13ppExprList(obj, obj2, obj3);
                case 4:
                    return lambda14pp$MnLAMBDA(obj, obj2, obj3);
                case 5:
                    return lambda15pp$MnIF(obj, obj2, obj3);
                case 6:
                    return lambda16pp$MnCOND(obj, obj2, obj3);
                case 7:
                    return lambda17pp$MnCASE(obj, obj2, obj3);
                case 8:
                    return lambda18pp$MnAND(obj, obj2, obj3);
                case 9:
                    return lambda19pp$MnLET(obj, obj2, obj3);
                case 10:
                    return lambda20pp$MnBEGIN(obj, obj2, obj3);
                case 11:
                    return lambda21pp$MnDO(obj, obj2, obj3);
                default:
                    return super.apply3(moduleMethod, obj, obj2, obj3);
            }
        }

        public Object lambda21pp$MnDO(Object expr, Object col, Object extra) {
            return lambda12ppGeneral(expr, col, extra, Boolean.FALSE, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr$Mnlist, this.pp$Mnexpr);
        }
    }

    /* compiled from: genwrite.scm */
    public class frame1 extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Object left;
        Object result;
        frame0 staticLink;

        public frame1() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/genwrite.scm:72");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 1) {
                return lambda22(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda22(Object str) {
            this.result = lists.cons(str, this.result);
            try {
                this.left = AddOp.$Mn.apply2(this.left, Integer.valueOf(strings.stringLength((CharSequence) str)));
                return ((Boolean) Scheme.numGrt.apply2(this.left, genwrite.Lit1)).booleanValue();
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, str);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: genwrite.scm */
    public class frame extends ModuleBody {
        Object display$Qu;
        Object output;
        Object width;

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public static java.lang.Object lambda1isReadMacro(java.lang.Object r5) {
            /*
            r3 = kawa.lib.lists.car;
            r0 = r3.apply1(r5);
            r3 = kawa.lib.lists.cdr;
            r1 = r3.apply1(r5);
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.kawa.slib.genwrite.Lit30;
            r2 = r3.apply2(r0, r4);
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x0032;
        L_0x0018:
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x0042;
        L_0x001c:
            r5 = r1;
            r2 = kawa.lib.lists.isPair(r5);
            if (r2 == 0) goto L_0x0066;
        L_0x0023:
            r3 = kawa.lib.lists.cdr;
            r3 = r3.apply1(r5);
            r3 = kawa.lib.lists.isNull(r3);
            if (r3 == 0) goto L_0x0063;
        L_0x002f:
            r3 = java.lang.Boolean.TRUE;
        L_0x0031:
            return r3;
        L_0x0032:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.kawa.slib.genwrite.Lit31;
            r2 = r3.apply2(r0, r4);
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x0045;
        L_0x003e:
            r3 = java.lang.Boolean.FALSE;
            if (r2 != r3) goto L_0x001c;
        L_0x0042:
            r3 = java.lang.Boolean.FALSE;
            goto L_0x0031;
        L_0x0045:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.kawa.slib.genwrite.Lit32;
            r2 = r3.apply2(r0, r4);
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x0056;
        L_0x0051:
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x0042;
        L_0x0055:
            goto L_0x001c;
        L_0x0056:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.kawa.slib.genwrite.Lit33;
            r3 = r3.apply2(r0, r4);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x0042;
        L_0x0062:
            goto L_0x001c;
        L_0x0063:
            r3 = java.lang.Boolean.FALSE;
            goto L_0x0031;
        L_0x0066:
            if (r2 == 0) goto L_0x006b;
        L_0x0068:
            r3 = java.lang.Boolean.TRUE;
            goto L_0x0031;
        L_0x006b:
            r3 = java.lang.Boolean.FALSE;
            goto L_0x0031;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.genwrite.frame.lambda1isReadMacro(java.lang.Object):java.lang.Object");
        }

        public static Object lambda2readMacroBody(Object l) {
            return lists.cadr.apply1(l);
        }

        public static Object lambda3readMacroPrefix(Object l) {
            Object head = lists.car.apply1(l);
            lists.cdr.apply1(l);
            if (Scheme.isEqv.apply2(head, genwrite.Lit30) != Boolean.FALSE) {
                return "'";
            }
            if (Scheme.isEqv.apply2(head, genwrite.Lit31) != Boolean.FALSE) {
                return "`";
            }
            if (Scheme.isEqv.apply2(head, genwrite.Lit32) != Boolean.FALSE) {
                return ",";
            }
            return Scheme.isEqv.apply2(head, genwrite.Lit33) != Boolean.FALSE ? ",@" : Values.empty;
        }

        public Object lambda4out(Object str, Object col) {
            if (col == Boolean.FALSE) {
                return col;
            }
            Boolean x = Scheme.applyToArgs.apply2(this.output, str);
            if (x == Boolean.FALSE) {
                return x;
            }
            try {
                return AddOp.$Pl.apply2(col, Integer.valueOf(strings.stringLength((CharSequence) str)));
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, str);
            }
        }

        public Object lambda5wr(Object obj, Object col) {
            frame closureEnv;
            if (lists.isPair(obj)) {
                Object expr = obj;
                closureEnv = this;
                if (lambda1isReadMacro(expr) != Boolean.FALSE) {
                    return lambda5wr(lambda2readMacroBody(expr), lambda4out(lambda3readMacroPrefix(expr), col));
                }
                obj = expr;
            } else if (!lists.isNull(obj)) {
                if (vectors.isVector(obj)) {
                    try {
                        LList vector$To$List = vectors.vector$To$List((FVector) obj);
                        Boolean col2 = lambda4out("#", col);
                        LList lList = vector$To$List;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "vector->list", 1, obj);
                    }
                }
                Object[] objArr = new Object[2];
                objArr[0] = this.display$Qu != Boolean.FALSE ? "~a" : "~s";
                objArr[1] = obj;
                return lambda4out(Format.formatToString(0, objArr), col);
            }
            closureEnv = this;
            if (!lists.isPair(obj)) {
                return lambda4out("()", col2);
            }
            Object l = lists.cdr.apply1(obj);
            if (col2 != Boolean.FALSE) {
                col2 = lambda5wr(lists.car.apply1(obj), lambda4out("(", col2));
            }
            while (col2 != Boolean.FALSE) {
                if (lists.isPair(l)) {
                    Object l2 = lists.cdr.apply1(l);
                    col2 = lambda5wr(lists.car.apply1(l), lambda4out(" ", col2));
                    l = l2;
                } else if (lists.isNull(l)) {
                    return lambda4out(")", col2);
                } else {
                    return lambda4out(")", lambda5wr(l, lambda4out(" . ", col2)));
                }
            }
            return col2;
        }
    }

    static {
        ModuleBody moduleBody = $instance;
        generic$Mnwrite = new ModuleMethod(moduleBody, 12, Lit34, 16388);
        reverse$Mnstring$Mnappend = new ModuleMethod(moduleBody, 13, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public genwrite() {
        ModuleInfo.register(this);
    }

    public static Object genericWrite(Object obj, Object isDisplay, Object width, Object output) {
        frame gnu_kawa_slib_genwrite_frame = new frame();
        gnu_kawa_slib_genwrite_frame.display$Qu = isDisplay;
        gnu_kawa_slib_genwrite_frame.width = width;
        gnu_kawa_slib_genwrite_frame.output = output;
        if (gnu_kawa_slib_genwrite_frame.width == Boolean.FALSE) {
            return gnu_kawa_slib_genwrite_frame.lambda5wr(obj, Lit1);
        }
        CharSequence makeString = strings.makeString(1, Lit0);
        IntNum intNum = Lit1;
        frame0 gnu_kawa_slib_genwrite_frame0 = new frame0();
        gnu_kawa_slib_genwrite_frame0.staticLink = gnu_kawa_slib_genwrite_frame;
        Procedure procedure = gnu_kawa_slib_genwrite_frame0.pp$Mnexpr;
        Procedure procedure2 = gnu_kawa_slib_genwrite_frame0.pp$Mnexpr$Mnlist;
        Procedure procedure3 = gnu_kawa_slib_genwrite_frame0.pp$MnLAMBDA;
        Procedure procedure4 = gnu_kawa_slib_genwrite_frame0.pp$MnIF;
        Procedure procedure5 = gnu_kawa_slib_genwrite_frame0.pp$MnCOND;
        Procedure procedure6 = gnu_kawa_slib_genwrite_frame0.pp$MnCASE;
        Procedure procedure7 = gnu_kawa_slib_genwrite_frame0.pp$MnAND;
        Procedure procedure8 = gnu_kawa_slib_genwrite_frame0.pp$MnLET;
        Procedure procedure9 = gnu_kawa_slib_genwrite_frame0.pp$MnBEGIN;
        gnu_kawa_slib_genwrite_frame0.pp$MnDO = gnu_kawa_slib_genwrite_frame0.pp$MnDO;
        gnu_kawa_slib_genwrite_frame0.pp$MnBEGIN = procedure9;
        gnu_kawa_slib_genwrite_frame0.pp$MnLET = procedure8;
        gnu_kawa_slib_genwrite_frame0.pp$MnAND = procedure7;
        gnu_kawa_slib_genwrite_frame0.pp$MnCASE = procedure6;
        gnu_kawa_slib_genwrite_frame0.pp$MnCOND = procedure5;
        gnu_kawa_slib_genwrite_frame0.pp$MnIF = procedure4;
        gnu_kawa_slib_genwrite_frame0.pp$MnLAMBDA = procedure3;
        gnu_kawa_slib_genwrite_frame0.pp$Mnexpr$Mnlist = procedure2;
        gnu_kawa_slib_genwrite_frame0.pp$Mnexpr = procedure;
        return gnu_kawa_slib_genwrite_frame.lambda4out(makeString, gnu_kawa_slib_genwrite_frame0.lambda7pr(obj, intNum, Lit1, gnu_kawa_slib_genwrite_frame0.pp$Mnexpr));
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 12 ? genericWrite(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 12) {
            return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.value4 = obj4;
        callContext.proc = moduleMethod;
        callContext.pc = 4;
        return 0;
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object reverseStringAppend(Object l) {
        return lambda23revStringAppend(l, Lit1);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 13 ? reverseStringAppend(obj) : super.apply1(moduleMethod, obj);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        if (moduleMethod.selector != 13) {
            return super.match1(moduleMethod, obj, callContext);
        }
        callContext.value1 = obj;
        callContext.proc = moduleMethod;
        callContext.pc = 1;
        return 0;
    }

    public static Object lambda23revStringAppend(Object l, Object i) {
        if (lists.isPair(l)) {
            Object str = lists.car.apply1(l);
            try {
                int len = strings.stringLength((CharSequence) str);
                Object result = lambda23revStringAppend(lists.cdr.apply1(l), AddOp.$Pl.apply2(i, Integer.valueOf(len)));
                Object obj = Lit1;
                try {
                    Object apply2 = AddOp.$Mn.apply2(AddOp.$Mn.apply2(Integer.valueOf(strings.stringLength((CharSequence) result)), i), Integer.valueOf(len));
                    while (Scheme.numLss.apply2(obj, Integer.valueOf(len)) != Boolean.FALSE) {
                        try {
                            try {
                                try {
                                    try {
                                        strings.stringSet$Ex((CharSeq) result, ((Number) apply2).intValue(), strings.stringRef((CharSequence) str, ((Number) obj).intValue()));
                                        obj = AddOp.$Pl.apply2(obj, Lit17);
                                        apply2 = AddOp.$Pl.apply2(apply2, Lit17);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "string-ref", 2, obj);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "string-ref", 1, str);
                                }
                            } catch (ClassCastException e22) {
                                throw new WrongType(e22, "string-set!", 2, apply2);
                            }
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "string-set!", 1, result);
                        }
                    }
                    return result;
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "string-length", 1, result);
                }
            } catch (ClassCastException e22222) {
                throw new WrongType(e22222, "string-length", 1, str);
            }
        }
        try {
            return strings.makeString(((Number) i).intValue());
        } catch (ClassCastException e222222) {
            throw new WrongType(e222222, "make-string", 1, i);
        }
    }
}

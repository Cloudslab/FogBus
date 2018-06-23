package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.Complex;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.lib.vectors;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: printf.scm */
public class printf extends ModuleBody {
    public static final printf $instance = new printf();
    static final IntNum Lit0 = IntNum.make(-15);
    static final IntNum Lit1 = IntNum.make(0);
    static final PairWithPosition Lit10 = PairWithPosition.make(Lit13, PairWithPosition.make(Lit37, PairWithPosition.make(Lit25, PairWithPosition.make(Lit12, PairWithPosition.make(Lit30, PairWithPosition.make(Lit54, PairWithPosition.make(Lit38, PairWithPosition.make(Lit26, PairWithPosition.make(Lit41, PairWithPosition.make(Lit31, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266284), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266280), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266276), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266272), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266268), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266264), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266260), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266256), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266252), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 266247);
    static final Char Lit11 = Char.make(46);
    static final Char Lit12;
    static final Char Lit13;
    static final IntNum Lit14 = IntNum.make(2);
    static final IntNum Lit15 = IntNum.make(5);
    static final IntNum Lit16 = IntNum.make(9);
    static final IntNum Lit17 = IntNum.make(-1);
    static final Char Lit18 = Char.make(92);
    static final Char Lit19 = Char.make(110);
    static final PairWithPosition Lit2 = PairWithPosition.make(Lit6, PairWithPosition.make(Lit5, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 446503), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 446498);
    static final Char Lit20 = Char.make(78);
    static final Char Lit21 = Char.make(10);
    static final Char Lit22 = Char.make(116);
    static final Char Lit23 = Char.make(84);
    static final Char Lit24 = Char.make(9);
    static final Char Lit25;
    static final Char Lit26 = Char.make(70);
    static final Char Lit27 = Char.make(12);
    static final Char Lit28 = Char.make(37);
    static final Char Lit29 = Char.make(32);
    static final Char Lit3;
    static final Char Lit30 = Char.make(108);
    static final Char Lit31 = Char.make(76);
    static final Char Lit32 = Char.make(104);
    static final PairWithPosition Lit33;
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("printf").readResolve());
    static final Char Lit35 = Char.make(99);
    static final Char Lit36 = Char.make(67);
    static final Char Lit37 = Char.make(115);
    static final Char Lit38 = Char.make(83);
    static final Char Lit39 = Char.make(97);
    static final Char Lit4 = Char.make(64);
    static final Char Lit40 = Char.make(65);
    static final Char Lit41 = Char.make(68);
    static final Char Lit42 = Char.make(73);
    static final Char Lit43 = Char.make(117);
    static final Char Lit44 = Char.make(85);
    static final IntNum Lit45 = IntNum.make(10);
    static final Char Lit46 = Char.make(111);
    static final Char Lit47 = Char.make(79);
    static final IntNum Lit48 = IntNum.make(8);
    static final Char Lit49 = Char.make(120);
    static final Char Lit5 = Char.make(45);
    static final IntNum Lit50 = IntNum.make(16);
    static final Char Lit51 = Char.make(88);
    static final Char Lit52 = Char.make(98);
    static final Char Lit53 = Char.make(66);
    static final Char Lit54 = Char.make(69);
    static final Char Lit55 = Char.make(103);
    static final Char Lit56 = Char.make(71);
    static final Char Lit57 = Char.make(107);
    static final Char Lit58 = Char.make(75);
    static final IntNum Lit59 = IntNum.make(6);
    static final Char Lit6 = Char.make(43);
    static final IntNum Lit60 = IntNum.make(-10);
    static final IntNum Lit61 = IntNum.make(3);
    static final FVector Lit62 = FVector.make("y", "z", "a", "f", "p", "n", "u", "m", "", "k", "M", "G", "T", "P", "E", "Z", "Y");
    static final PairWithPosition Lit63 = PairWithPosition.make("i", LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1634315);
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("format-real").readResolve());
    static final Char Lit65 = Char.make(63);
    static final Char Lit66 = Char.make(42);
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("pad").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("sprintf").readResolve());
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("stdio:parse-float").readResolve());
    static final IntNum Lit7 = IntNum.make(1);
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("stdio:round-string").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("stdio:iprintf").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("fprintf").readResolve());
    static final Char Lit8 = Char.make(35);
    static final Char Lit9 = Char.make(48);
    public static final ModuleMethod fprintf;
    public static final ModuleMethod printf;
    public static final ModuleMethod sprintf;
    public static final boolean stdio$Clhex$Mnupper$Mncase$Qu = false;
    public static final ModuleMethod stdio$Cliprintf;
    public static final ModuleMethod stdio$Clparse$Mnfloat;
    public static final ModuleMethod stdio$Clround$Mnstring;

    /* compiled from: printf.scm */
    public class frame0 extends ModuleBody {
        Object digs;
        Object ex;
        final ModuleMethod lambda$Fn2;
        final ModuleMethod lambda$Fn3;
        Object num;
        Object sgn;
        frame staticLink;

        public frame0() {
            PropertySet moduleMethod = new ModuleMethod(this, 2, null, 16388);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:111");
            this.lambda$Fn2 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 3, null, 12291);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:123");
            this.lambda$Fn3 = moduleMethod;
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            return moduleMethod.selector == 2 ? lambda6(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        java.lang.Object lambda6(java.lang.Object r9, java.lang.Object r10, java.lang.Object r11, java.lang.Object r12) {
            /*
            r8 = this;
            r7 = 2;
            r6 = 1;
            r2 = kawa.standard.Scheme.numEqu;
            r3 = r8.staticLink;
            r3 = r3.f29n;
            r3 = r3 + -1;
            r3 = java.lang.Integer.valueOf(r3);
            r3 = r2.apply2(r9, r3);
            r0 = r3;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x0068 }
            r2 = r0;
            r1 = r2.booleanValue();	 Catch:{ ClassCastException -> 0x0068 }
            if (r1 == 0) goto L_0x0061;
        L_0x001c:
            r4 = gnu.kawa.slib.printf.Lit3;
            r2 = r8.staticLink;
            r2 = r2.str;
            r2 = (java.lang.CharSequence) r2;	 Catch:{ ClassCastException -> 0x0072 }
            r0 = r9;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x007b }
            r3 = r0;
            r3 = r3.intValue();	 Catch:{ ClassCastException -> 0x007b }
            r2 = kawa.lib.strings.stringRef(r2, r3);
            r2 = gnu.text.Char.make(r2);
            r2 = kawa.lib.rnrs.unicode.isCharCi$Eq(r4, r2);
            if (r2 == 0) goto L_0x0063;
        L_0x003a:
            r2 = kawa.standard.Scheme.applyToArgs;
            r3 = 7;
            r3 = new java.lang.Object[r3];
            r4 = 0;
            r5 = r8.staticLink;
            r5 = r5.proc;
            r3[r4] = r5;
            r4 = r8.sgn;
            r3[r6] = r4;
            r4 = r8.digs;
            r3[r7] = r4;
            r4 = 3;
            r5 = r8.ex;
            r3[r4] = r5;
            r4 = 4;
            r3[r4] = r10;
            r4 = 5;
            r3[r4] = r11;
            r4 = 6;
            r3[r4] = r12;
            r2 = r2.applyN(r3);
        L_0x0060:
            return r2;
        L_0x0061:
            if (r1 != 0) goto L_0x003a;
        L_0x0063:
            r2 = gnu.kawa.slib.printf.frame.lambda1parseError();
            goto L_0x0060;
        L_0x0068:
            r2 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "x";
            r6 = -2;
            r4.<init>(r2, r5, r6, r3);
            throw r4;
        L_0x0072:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "string-ref";
            r4.<init>(r3, r5, r6, r2);
            throw r4;
        L_0x007b:
            r2 = move-exception;
            r3 = new gnu.mapping.WrongType;
            r4 = "string-ref";
            r3.<init>(r2, r4, r7, r9);
            throw r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.frame0.lambda6(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
            if (moduleMethod.selector != 2) {
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

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 3 ? lambda7(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda7(Object sgn, Object digs, Object ex) {
            frame1 gnu_kawa_slib_printf_frame1 = new frame1();
            gnu_kawa_slib_printf_frame1.staticLink = this;
            gnu_kawa_slib_printf_frame1.sgn = sgn;
            gnu_kawa_slib_printf_frame1.digs = digs;
            gnu_kawa_slib_printf_frame1.ex = ex;
            Object obj = this.num;
            try {
                return printf.stdio$ClParseFloat(numbers.number$To$String(numbers.imagPart((Complex) obj)), gnu_kawa_slib_printf_frame1.lambda$Fn4);
            } catch (ClassCastException e) {
                throw new WrongType(e, "imag-part", 1, obj);
            }
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 3) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame10 extends ModuleBody {
        Object alternate$Mnform;
        Object args;
        Object blank;
        final ModuleMethod lambda$Fn13;
        final ModuleMethod lambda$Fn14;
        final ModuleMethod lambda$Fn15;
        final ModuleMethod lambda$Fn16;
        Object leading$Mn0s;
        Object left$Mnadjust;
        Object os;
        Procedure pad = new ModuleMethod(this, 15, printf.Lit67, -4095);
        Object pr;
        Object precision;
        Object signed;
        frame9 staticLink;
        Object width;

        public frame10() {
            PropertySet moduleMethod = new ModuleMethod(this, 16, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:472");
            this.lambda$Fn13 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 17, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:476");
            this.lambda$Fn14 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 18, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:484");
            this.lambda$Fn15 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 19, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:494");
            this.lambda$Fn16 = moduleMethod;
        }

        public Object lambda22readFormatNumber() {
            if (Scheme.isEqv.apply2(printf.Lit66, this.staticLink.fc) != Boolean.FALSE) {
                this.staticLink.lambda18mustAdvance();
                Object ans = lists.car.apply1(this.args);
                this.args = lists.cdr.apply1(this.args);
                return ans;
            }
            Object c = this.staticLink.fc;
            Object obj = printf.Lit1;
            while (true) {
                frame10 closureEnv = this;
                Object obj2 = this.staticLink.fc;
                try {
                    if (!unicode.isCharNumeric((Char) obj2)) {
                        return obj;
                    }
                    this.staticLink.lambda18mustAdvance();
                    Object c2 = this.staticLink.fc;
                    Procedure procedure = AddOp.$Pl;
                    Object apply2 = MultiplyOp.$St.apply2(obj, printf.Lit45);
                    if (c instanceof Object[]) {
                        c = (Object[]) c;
                    } else {
                        c = new Object[]{c};
                    }
                    obj = procedure.apply2(apply2, numbers.string$To$Number(strings.$make$string$(c)));
                    c = c2;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char-numeric?", 1, obj2);
                }
            }
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            if (moduleMethod.selector != 15) {
                return super.applyN(moduleMethod, objArr);
            }
            Object obj = objArr[0];
            int length = objArr.length - 1;
            Object[] objArr2 = new Object[length];
            while (true) {
                length--;
                if (length < 0) {
                    return lambda23pad$V(obj, objArr2);
                }
                objArr2[length] = objArr[length + 1];
            }
        }

        public Object lambda23pad$V(Object pre, Object[] argsArray) {
            LList strs = LList.makeList(argsArray, 0);
            try {
                Object len = Integer.valueOf(strings.stringLength((CharSequence) pre));
                Object ss = strs;
                while (Scheme.numGEq.apply2(len, this.width) == Boolean.FALSE) {
                    if (!lists.isNull(ss)) {
                        Procedure procedure = AddOp.$Pl;
                        Object apply1 = lists.car.apply1(ss);
                        try {
                            len = procedure.apply2(len, Integer.valueOf(strings.stringLength((CharSequence) apply1)));
                            ss = lists.cdr.apply1(ss);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-length", 1, apply1);
                        }
                    } else if (this.left$Mnadjust != Boolean.FALSE) {
                        Object[] objArr = new Object[2];
                        objArr[0] = strs;
                        r5 = AddOp.$Mn.apply2(this.width, len);
                        try {
                            objArr[1] = LList.list1(strings.makeString(((Number) r5).intValue(), printf.Lit29));
                            return lists.cons(pre, append.append$V(objArr));
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "make-string", 1, r5);
                        }
                    } else if (this.leading$Mn0s != Boolean.FALSE) {
                        r5 = AddOp.$Mn.apply2(this.width, len);
                        try {
                            return lists.cons(pre, lists.cons(strings.makeString(((Number) r5).intValue(), printf.Lit9), strs));
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "make-string", 1, r5);
                        }
                    } else {
                        r5 = AddOp.$Mn.apply2(this.width, len);
                        try {
                            return lists.cons(strings.makeString(((Number) r5).intValue(), printf.Lit29), lists.cons(pre, strs));
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "make-string", 1, r5);
                        }
                    }
                }
                return lists.cons(pre, strs);
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "string-length", 1, pre);
            }
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.matchN(moduleMethod, objArr, callContext);
            }
            callContext.values = objArr;
            callContext.proc = moduleMethod;
            callContext.pc = 5;
            return 0;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda24integerConvert(java.lang.Object r10, java.lang.Object r11, java.lang.Object r12) {
            /*
            r9 = this;
            r8 = 2;
            r6 = 0;
            r5 = 1;
            r3 = r9.precision;
            r3 = gnu.kawa.lispexpr.LangObjType.coerceRealNum(r3);	 Catch:{ ClassCastException -> 0x013d }
            r3 = kawa.lib.numbers.isNegative(r3);
            if (r3 != 0) goto L_0x01aa;
        L_0x000f:
            r3 = java.lang.Boolean.FALSE;
            r9.leading$Mn0s = r3;
            r3 = r9.precision;
            r3 = (java.lang.Number) r3;	 Catch:{ ClassCastException -> 0x0146 }
            r2 = kawa.lib.numbers.isZero(r3);
            if (r2 == 0) goto L_0x0091;
        L_0x001d:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.kawa.slib.printf.Lit1;
            r3 = r3.apply2(r4, r10);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x002b;
        L_0x0029:
            r10 = "";
        L_0x002b:
            r3 = r10;
        L_0x002c:
            r4 = kawa.lib.misc.isSymbol(r3);
            if (r4 == 0) goto L_0x0094;
        L_0x0032:
            r3 = (gnu.mapping.Symbol) r3;	 Catch:{ ClassCastException -> 0x014f }
            r10 = kawa.lib.misc.symbol$To$String(r3);
        L_0x0038:
            r3 = java.lang.Boolean.FALSE;
            if (r12 == r3) goto L_0x0042;
        L_0x003c:
            r3 = kawa.standard.Scheme.applyToArgs;
            r10 = r3.apply2(r12, r10);
        L_0x0042:
            r3 = "";
            r3 = gnu.kawa.functions.IsEqual.apply(r3, r10);
            if (r3 == 0) goto L_0x00ce;
        L_0x004a:
            r1 = "";
        L_0x004c:
            r7 = new java.lang.Object[r8];
            r4 = kawa.standard.Scheme.numLss;
            r0 = r10;
            r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x018f }
            r3 = r0;
            r3 = kawa.lib.strings.stringLength(r3);
            r3 = java.lang.Integer.valueOf(r3);
            r8 = r9.precision;
            r3 = r4.apply2(r3, r8);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x0139;
        L_0x0066:
            r4 = gnu.kawa.functions.AddOp.$Mn;
            r8 = r9.precision;
            r0 = r10;
            r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x0198 }
            r3 = r0;
            r3 = kawa.lib.strings.stringLength(r3);
            r3 = java.lang.Integer.valueOf(r3);
            r4 = r4.apply2(r8, r3);
            r0 = r4;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01a1 }
            r3 = r0;
            r3 = r3.intValue();	 Catch:{ ClassCastException -> 0x01a1 }
            r4 = gnu.kawa.slib.printf.Lit9;
            r3 = kawa.lib.strings.makeString(r3, r4);
        L_0x0088:
            r7[r6] = r3;
            r7[r5] = r10;
            r3 = r9.lambda23pad$V(r1, r7);
            return r3;
        L_0x0091:
            if (r2 == 0) goto L_0x002b;
        L_0x0093:
            goto L_0x0029;
        L_0x0094:
            r4 = kawa.lib.numbers.isNumber(r3);
            if (r4 == 0) goto L_0x00a9;
        L_0x009a:
            r3 = (java.lang.Number) r3;	 Catch:{ ClassCastException -> 0x0158 }
            r0 = r11;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0161 }
            r4 = r0;
            r4 = r4.intValue();	 Catch:{ ClassCastException -> 0x0161 }
            r10 = kawa.lib.numbers.number$To$String(r3, r4);
            goto L_0x0038;
        L_0x00a9:
            r4 = java.lang.Boolean.FALSE;	 Catch:{ ClassCastException -> 0x016a }
            if (r3 == r4) goto L_0x00b9;
        L_0x00ad:
            r4 = r5;
        L_0x00ae:
            r4 = r4 + 1;
            r2 = r4 & 1;
            if (r2 == 0) goto L_0x00bb;
        L_0x00b4:
            if (r2 == 0) goto L_0x00c1;
        L_0x00b6:
            r10 = "0";
            goto L_0x0038;
        L_0x00b9:
            r4 = r6;
            goto L_0x00ae;
        L_0x00bb:
            r4 = kawa.lib.lists.isNull(r3);
            if (r4 != 0) goto L_0x00b6;
        L_0x00c1:
            r4 = kawa.lib.strings.isString(r3);
            if (r4 == 0) goto L_0x00ca;
        L_0x00c7:
            r10 = r3;
            goto L_0x0038;
        L_0x00ca:
            r10 = "1";
            goto L_0x0038;
        L_0x00ce:
            r4 = kawa.standard.Scheme.isEqv;
            r7 = gnu.kawa.slib.printf.Lit5;
            r0 = r10;
            r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x0174 }
            r3 = r0;
            r3 = kawa.lib.strings.stringRef(r3, r6);
            r3 = gnu.text.Char.make(r3);
            r3 = r4.apply2(r7, r3);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x00f8;
        L_0x00e6:
            r0 = r10;
            r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x017d }
            r3 = r0;
            r10 = (java.lang.CharSequence) r10;	 Catch:{ ClassCastException -> 0x0186 }
            r4 = kawa.lib.strings.stringLength(r10);
            r10 = kawa.lib.strings.substring(r3, r5, r4);
            r1 = "-";
            goto L_0x004c;
        L_0x00f8:
            r3 = r9.signed;
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x0102;
        L_0x00fe:
            r1 = "+";
            goto L_0x004c;
        L_0x0102:
            r3 = r9.blank;
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x010c;
        L_0x0108:
            r1 = " ";
            goto L_0x004c;
        L_0x010c:
            r3 = r9.alternate$Mnform;
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x0135;
        L_0x0112:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.kawa.slib.printf.Lit48;
            r3 = r3.apply2(r11, r4);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x0123;
        L_0x011e:
            r3 = "0";
        L_0x0120:
            r1 = r3;
            goto L_0x004c;
        L_0x0123:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.kawa.slib.printf.Lit50;
            r3 = r3.apply2(r11, r4);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x0132;
        L_0x012f:
            r3 = "0x";
            goto L_0x0120;
        L_0x0132:
            r3 = "";
            goto L_0x0120;
        L_0x0135:
            r1 = "";
            goto L_0x004c;
        L_0x0139:
            r3 = "";
            goto L_0x0088;
        L_0x013d:
            r4 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "negative?";
            r6.<init>(r4, r7, r5, r3);
            throw r6;
        L_0x0146:
            r4 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "zero?";
            r6.<init>(r4, r7, r5, r3);
            throw r6;
        L_0x014f:
            r4 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "symbol->string";
            r6.<init>(r4, r7, r5, r3);
            throw r6;
        L_0x0158:
            r4 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "number->string";
            r6.<init>(r4, r7, r5, r3);
            throw r6;
        L_0x0161:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "number->string";
            r4.<init>(r3, r5, r8, r11);
            throw r4;
        L_0x016a:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "x";
            r7 = -2;
            r5.<init>(r4, r6, r7, r3);
            throw r5;
        L_0x0174:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r6 = "string-ref";
            r4.<init>(r3, r6, r5, r10);
            throw r4;
        L_0x017d:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r6 = "substring";
            r4.<init>(r3, r6, r5, r10);
            throw r4;
        L_0x0186:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r6 = "string-length";
            r4.<init>(r3, r6, r5, r10);
            throw r4;
        L_0x018f:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r6 = "string-length";
            r4.<init>(r3, r6, r5, r10);
            throw r4;
        L_0x0198:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r6 = "string-length";
            r4.<init>(r3, r6, r5, r10);
            throw r4;
        L_0x01a1:
            r3 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "make-string";
            r6.<init>(r3, r7, r5, r4);
            throw r6;
        L_0x01aa:
            r3 = r10;
            goto L_0x002c;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.frame10.lambda24integerConvert(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        Object lambda25(Object s) {
            try {
                this.pr = AddOp.$Pl.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                return Scheme.applyToArgs.apply2(this.staticLink.out, s);
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-length", 1, s);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 16:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 17:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 18:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 19:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        boolean lambda26(Object s) {
            Special sl = Special.undefined;
            try {
                Object sl2 = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                try {
                    Object obj;
                    if (numbers.isNegative(LangObjType.coerceRealNum(sl2))) {
                        Procedure procedure = Scheme.applyToArgs;
                        Object obj2 = this.staticLink.out;
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object obj3 = this.pr;
                            try {
                                procedure.apply2(obj2, strings.substring(charSequence, 0, ((Number) obj3).intValue()));
                                obj = printf.Lit1;
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, obj3);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, s);
                        }
                    }
                    Scheme.applyToArgs.apply2(this.staticLink.out, s);
                    obj = sl2;
                    this.pr = obj;
                    try {
                        return numbers.isPositive(LangObjType.coerceRealNum(sl2));
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "positive?", 1, sl2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "negative?", 1, sl2);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "string-length", 1, s);
            }
        }

        Boolean lambda27(Object s) {
            try {
                this.pr = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                if (this.os == Boolean.FALSE) {
                    Scheme.applyToArgs.apply2(this.staticLink.out, s);
                } else {
                    Object obj = this.pr;
                    try {
                        if (numbers.isNegative(LangObjType.coerceRealNum(obj))) {
                            Scheme.applyToArgs.apply2(this.staticLink.out, this.os);
                            this.os = Boolean.FALSE;
                            Scheme.applyToArgs.apply2(this.staticLink.out, s);
                        } else {
                            this.os = strings.stringAppend(this.os, s);
                        }
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "negative?", 1, obj);
                    }
                }
                return Boolean.TRUE;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-length", 1, s);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 16:
                    return lambda25(obj);
                case 17:
                    return lambda26(obj) ? Boolean.TRUE : Boolean.FALSE;
                case 18:
                    return lambda27(obj);
                case 19:
                    return lambda28(obj) ? Boolean.TRUE : Boolean.FALSE;
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda28(Object s) {
            Special sl = Special.undefined;
            try {
                Object sl2 = AddOp.$Mn.apply2(this.pr, Integer.valueOf(strings.stringLength((CharSequence) s)));
                try {
                    if (numbers.isNegative(LangObjType.coerceRealNum(sl2))) {
                        Object[] objArr = new Object[2];
                        objArr[0] = this.os;
                        try {
                            CharSequence charSequence = (CharSequence) s;
                            Object obj = this.pr;
                            try {
                                objArr[1] = strings.substring(charSequence, 0, ((Number) obj).intValue());
                                this.os = strings.stringAppend(objArr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, obj);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, s);
                        }
                    }
                    this.os = strings.stringAppend(this.os, s);
                    this.pr = sl2;
                    try {
                        return numbers.isPositive(LangObjType.coerceRealNum(sl2));
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "positive?", 1, sl2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "negative?", 1, sl2);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "string-length", 1, s);
            }
        }
    }

    /* compiled from: printf.scm */
    public class frame11 extends ModuleBody {
        Object fc;
        Procedure format$Mnreal = new ModuleMethod(this, 13, printf.Lit64, -4092);
        final ModuleMethod lambda$Fn17;
        frame10 staticLink;

        public frame11() {
            PropertySet moduleMethod = new ModuleMethod(this, 14, null, -4093);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:401");
            this.lambda$Fn17 = moduleMethod;
        }

        public Object lambda29f(Object digs, Object exp, Object strip$Mn0s) {
            try {
                digs = printf.stdio$ClRoundString((CharSequence) digs, AddOp.$Pl.apply2(exp, this.staticLink.precision), strip$Mn0s != Boolean.FALSE ? exp : strip$Mn0s);
                boolean x;
                Object list2;
                if (Scheme.numGEq.apply2(exp, printf.Lit1) != Boolean.FALSE) {
                    try {
                        Object i0;
                        if (numbers.isZero((Number) exp)) {
                            i0 = printf.Lit1;
                        } else {
                            try {
                                if (characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef((CharSequence) digs, 0)))) {
                                    i0 = printf.Lit7;
                                } else {
                                    i0 = printf.Lit1;
                                }
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 1, digs);
                            }
                        }
                        Object i1 = numbers.max(printf.Lit7, AddOp.$Pl.apply2(printf.Lit7, exp));
                        try {
                            try {
                                try {
                                    CharSequence idigs = strings.substring((CharSequence) digs, i0.intValue(), ((Number) i1).intValue());
                                    try {
                                        try {
                                            try {
                                                CharSequence fdigs = strings.substring((CharSequence) digs, ((Number) i1).intValue(), strings.stringLength((CharSequence) digs));
                                                x = strings.isString$Eq(fdigs, "");
                                                list2 = (x ? this.staticLink.alternate$Mnform == Boolean.FALSE : x) ? LList.Empty : LList.list2(".", fdigs);
                                                return lists.cons(idigs, list2);
                                            } catch (ClassCastException e2) {
                                                throw new WrongType(e2, "string-length", 1, digs);
                                            }
                                        } catch (ClassCastException e22) {
                                            throw new WrongType(e22, "substring", 2, i1);
                                        }
                                    } catch (ClassCastException e222) {
                                        throw new WrongType(e222, "substring", 1, digs);
                                    }
                                } catch (ClassCastException e2222) {
                                    throw new WrongType(e2222, "substring", 3, i1);
                                }
                            } catch (ClassCastException e22222) {
                                throw new WrongType(e22222, "substring", 2, i0);
                            }
                        } catch (ClassCastException e222222) {
                            throw new WrongType(e222222, "substring", 1, digs);
                        }
                    } catch (ClassCastException e2222222) {
                        throw new WrongType(e2222222, "zero?", 1, exp);
                    }
                }
                list2 = this.staticLink.precision;
                try {
                    if (numbers.isZero((Number) list2)) {
                        return LList.list1(this.staticLink.alternate$Mnform != Boolean.FALSE ? "0." : "0");
                    }
                    if (strip$Mn0s != Boolean.FALSE) {
                        x = strings.isString$Eq(digs, "");
                        Pair list1 = x ? LList.list1("0") : x ? Boolean.TRUE : Boolean.FALSE;
                        x = list1;
                    } else {
                        x = strip$Mn0s;
                    }
                    if (x != Boolean.FALSE) {
                        return x;
                    }
                    String str = "0.";
                    Object min = numbers.min(this.staticLink.precision, AddOp.$Mn.apply2(printf.Lit17, exp));
                    try {
                        return LList.list3(str, strings.makeString(((Number) min).intValue(), printf.Lit9), digs);
                    } catch (ClassCastException e22222222) {
                        throw new WrongType(e22222222, "make-string", 1, min);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "zero?", 1, list2);
                }
            } catch (ClassCastException e222222222) {
                throw new WrongType(e222222222, "stdio:round-string", 0, digs);
            }
        }

        public Object lambda30formatReal$V(Object signed$Qu, Object sgn, Object digs, Object exp, Object[] argsArray) {
            LList rest = LList.makeList(argsArray, 0);
            if (lists.isNull(rest)) {
                try {
                    Object obj;
                    Object obj2;
                    Object lambda29f;
                    Object apply3;
                    if (characters.isChar$Eq(printf.Lit5, (Char) sgn)) {
                        obj = "-";
                    } else {
                        String str = signed$Qu != Boolean.FALSE ? "+" : this.staticLink.blank != Boolean.FALSE ? " " : "";
                    }
                    Boolean x = Scheme.isEqv.apply2(this.fc, printf.Lit13);
                    if (x == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit54) != Boolean.FALSE : x != Boolean.FALSE) {
                        obj2 = Boolean.FALSE;
                    } else {
                        x = Scheme.isEqv.apply2(this.fc, printf.Lit25);
                        if (x == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit26) != Boolean.FALSE : x != Boolean.FALSE) {
                            lambda29f = lambda29f(digs, exp, Boolean.FALSE);
                            return lists.cons(obj, lambda29f);
                        }
                        frame11 gnu_kawa_slib_printf_frame11;
                        x = Scheme.isEqv.apply2(this.fc, printf.Lit55);
                        if (x == Boolean.FALSE ? Scheme.isEqv.apply2(this.fc, printf.Lit56) != Boolean.FALSE : x != Boolean.FALSE) {
                            Object obj3;
                            if (Scheme.isEqv.apply2(this.fc, printf.Lit57) != Boolean.FALSE) {
                                obj3 = "";
                            } else if (Scheme.isEqv.apply2(this.fc, printf.Lit58) != Boolean.FALSE) {
                                String str2 = " ";
                            } else {
                                lambda29f = Values.empty;
                                return lists.cons(obj, lambda29f);
                            }
                            gnu_kawa_slib_printf_frame11 = this;
                            try {
                                Boolean i;
                                if (numbers.isNegative(LangObjType.coerceRealNum(exp))) {
                                    i = DivideOp.quotient.apply2(AddOp.$Mn.apply2(exp, printf.Lit61), printf.Lit61);
                                } else {
                                    i = DivideOp.quotient.apply2(AddOp.$Mn.apply2(exp, printf.Lit7), printf.Lit61);
                                }
                                apply3 = Scheme.numLss.apply3(printf.Lit17, AddOp.$Pl.apply2(i, printf.Lit48), Integer.valueOf(vectors.vectorLength(printf.Lit62)));
                                try {
                                    boolean x2 = ((Boolean) apply3).booleanValue();
                                    Boolean uind = x2 ? i : x2 ? Boolean.TRUE : Boolean.FALSE;
                                    if (uind != Boolean.FALSE) {
                                        this.staticLink.precision = numbers.max(printf.Lit1, AddOp.$Mn.apply2(this.staticLink.precision, AddOp.$Mn.apply2(exp, MultiplyOp.$St.apply2(printf.Lit61, uind))));
                                        Object[] objArr = new Object[2];
                                        objArr[0] = lambda29f(digs, exp, Boolean.FALSE);
                                        FVector fVector = printf.Lit62;
                                        apply3 = AddOp.$Pl.apply2(uind, printf.Lit48);
                                        try {
                                            objArr[1] = LList.list2(obj3, vectors.vectorRef(fVector, ((Number) apply3).intValue()));
                                            lambda29f = append.append$V(objArr);
                                            return lists.cons(obj, lambda29f);
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "vector-ref", 2, apply3);
                                        }
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "x", -2, apply3);
                                }
                            } catch (ClassCastException e22) {
                                throw new WrongType(e22, "negative?", 1, exp);
                            }
                        }
                        gnu_kawa_slib_printf_frame11 = this;
                        lambda29f = this.staticLink.alternate$Mnform;
                        try {
                            boolean strip$Mn0s = ((lambda29f != Boolean.FALSE ? 1 : 0) + 1) & 1;
                            this.staticLink.alternate$Mnform = Boolean.FALSE;
                            if (Scheme.numLEq.apply3(AddOp.$Mn.apply2(printf.Lit7, this.staticLink.precision), exp, this.staticLink.precision) != Boolean.FALSE) {
                                this.staticLink.precision = AddOp.$Mn.apply2(this.staticLink.precision, exp);
                                if (strip$Mn0s) {
                                    lambda29f = Boolean.TRUE;
                                } else {
                                    lambda29f = Boolean.FALSE;
                                }
                                lambda29f = lambda29f(digs, exp, lambda29f);
                                return lists.cons(obj, lambda29f);
                            }
                            this.staticLink.precision = AddOp.$Mn.apply2(this.staticLink.precision, printf.Lit7);
                            obj2 = strip$Mn0s ? Boolean.TRUE : Boolean.FALSE;
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "strip-0s", -2, lambda29f);
                        }
                    }
                    try {
                        CharSequence charSequence = (CharSequence) digs;
                        lambda29f = AddOp.$Pl.apply2(printf.Lit7, this.staticLink.precision);
                        if (obj2 != Boolean.FALSE) {
                            obj2 = printf.Lit1;
                        }
                        digs = printf.stdio$ClRoundString(charSequence, lambda29f, obj2);
                        try {
                            Object istrt = characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef((CharSequence) digs, 0))) ? printf.Lit7 : printf.Lit1;
                            try {
                                try {
                                    CharSequence fdigs = strings.substring((CharSequence) digs, istrt.intValue() + 1, strings.stringLength((CharSequence) digs));
                                    if (!numbers.isZero(istrt)) {
                                        exp = AddOp.$Mn.apply2(exp, printf.Lit7);
                                    }
                                    try {
                                        try {
                                            Pair list1 = LList.list1(strings.substring((CharSequence) digs, istrt.intValue(), istrt.intValue() + 1));
                                            Object x3 = strings.isString$Eq(fdigs, "");
                                            if (x3 == null ? x3 != null : this.staticLink.alternate$Mnform == Boolean.FALSE) {
                                                apply3 = "";
                                            } else {
                                                String str3 = ".";
                                            }
                                            lambda29f = this.fc;
                                            try {
                                                try {
                                                    try {
                                                        LList.chain1(LList.chain1(LList.chain4(list1, apply3, fdigs, unicode.isCharUpperCase((Char) lambda29f) ? "E" : "e", numbers.isNegative(LangObjType.coerceRealNum(exp)) ? "-" : "+"), Scheme.numLss.apply3(printf.Lit60, exp, printf.Lit45) != Boolean.FALSE ? "0" : ""), numbers.number$To$String(numbers.abs((Number) exp)));
                                                        lambda29f = list1;
                                                        return lists.cons(obj, lambda29f);
                                                    } catch (ClassCastException e222) {
                                                        throw new WrongType(e222, "abs", 1, exp);
                                                    }
                                                } catch (ClassCastException e2222) {
                                                    throw new WrongType(e2222, "negative?", 1, exp);
                                                }
                                            } catch (ClassCastException e32) {
                                                throw new WrongType(e32, "char-upper-case?", 1, lambda29f);
                                            }
                                        } catch (ClassCastException e22222) {
                                            throw new WrongType(e22222, "substring", 2, istrt);
                                        }
                                    } catch (ClassCastException e222222) {
                                        throw new WrongType(e222222, "substring", 1, digs);
                                    }
                                } catch (ClassCastException e2222222) {
                                    throw new WrongType(e2222222, "string-length", 1, digs);
                                }
                            } catch (ClassCastException e22222222) {
                                throw new WrongType(e22222222, "substring", 1, digs);
                            }
                        } catch (ClassCastException e222222222) {
                            throw new WrongType(e222222222, "string-ref", 1, digs);
                        }
                    } catch (ClassCastException e2222222222) {
                        throw new WrongType(e2222222222, "stdio:round-string", 0, digs);
                    }
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "char=?", 2, sgn);
                }
            }
            Object[] objArr2 = new Object[3];
            objArr2[0] = lambda30formatReal$V(signed$Qu, sgn, digs, exp, new Object[0]);
            objArr2[1] = Scheme.apply.apply3(this.format$Mnreal, Boolean.TRUE, rest);
            objArr2[2] = printf.Lit63;
            return append.append$V(objArr2);
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 13:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 14:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                default:
                    return super.matchN(moduleMethod, objArr, callContext);
            }
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            Object obj;
            Object obj2;
            Object obj3;
            switch (moduleMethod.selector) {
                case 13:
                    Object obj4 = objArr[0];
                    obj = objArr[1];
                    obj2 = objArr[2];
                    obj3 = objArr[3];
                    int length = objArr.length - 4;
                    Object[] objArr2 = new Object[length];
                    while (true) {
                        length--;
                        if (length < 0) {
                            return lambda30formatReal$V(obj4, obj, obj2, obj3, objArr2);
                        }
                        objArr2[length] = objArr[length + 4];
                    }
                case 14:
                    obj = objArr[0];
                    obj2 = objArr[1];
                    obj3 = objArr[2];
                    int length2 = objArr.length - 3;
                    Object[] objArr3 = new Object[length2];
                    while (true) {
                        length2--;
                        if (length2 < 0) {
                            return lambda31$V(obj, obj2, obj3, objArr3);
                        }
                        objArr3[length2] = objArr[length2 + 3];
                    }
                default:
                    return super.applyN(moduleMethod, objArr);
            }
        }

        Object lambda31$V(Object sgn, Object digs, Object expon, Object[] argsArray) {
            LList imag = LList.makeList(argsArray, 0);
            return Scheme.apply.apply2(this.staticLink.pad, Scheme.apply.applyN(new Object[]{this.format$Mnreal, this.staticLink.signed, sgn, digs, expon, imag}));
        }
    }

    /* compiled from: printf.scm */
    public class frame12 extends ModuleBody {
        Object cnt;
        final ModuleMethod lambda$Fn18;
        Object port;

        public frame12() {
            PropertySet moduleMethod = new ModuleMethod(this, 20, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:546");
            this.lambda$Fn18 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 20 ? lambda32(obj) : super.apply1(moduleMethod, obj);
        }

        Boolean lambda32(Object x) {
            if (strings.isString(x)) {
                try {
                    this.cnt = AddOp.$Pl.apply2(Integer.valueOf(strings.stringLength((CharSequence) x)), this.cnt);
                    ports.display(x, this.port);
                    return Boolean.TRUE;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-length", 1, x);
                }
            }
            this.cnt = AddOp.$Pl.apply2(printf.Lit7, this.cnt);
            ports.display(x, this.port);
            return Boolean.TRUE;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 20) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame13 extends ModuleBody {
        Object cnt;
        Object end;
        final ModuleMethod lambda$Fn19;
        Object f28s;
        Object str;

        public frame13() {
            PropertySet moduleMethod = new ModuleMethod(this, 21, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:564");
            this.lambda$Fn19 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 21) {
                return lambda33(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        boolean lambda33(java.lang.Object r13) {
            /*
            r12 = this;
            r10 = 3;
            r11 = 2;
            r9 = 0;
            r8 = 1;
            r4 = kawa.lib.strings.isString(r13);
            if (r4 == 0) goto L_0x00dd;
        L_0x000a:
            r4 = r12.str;
            r5 = java.lang.Boolean.FALSE;
            if (r4 != r5) goto L_0x0030;
        L_0x0010:
            r5 = kawa.standard.Scheme.numGEq;
            r4 = gnu.kawa.functions.AddOp.$Mn;
            r6 = r12.end;
            r7 = r12.cnt;
            r6 = r4.apply2(r6, r7);
            r0 = r13;
            r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x0175 }
            r4 = r0;
            r4 = kawa.lib.strings.stringLength(r4);
            r4 = java.lang.Integer.valueOf(r4);
            r4 = r5.apply2(r6, r4);
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x0093;
        L_0x0030:
            r5 = new java.lang.Object[r11];
            r0 = r13;
            r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x017e }
            r4 = r0;
            r4 = kawa.lib.strings.stringLength(r4);
            r4 = java.lang.Integer.valueOf(r4);
            r5[r9] = r4;
            r4 = gnu.kawa.functions.AddOp.$Mn;
            r6 = r12.end;
            r7 = r12.cnt;
            r4 = r4.apply2(r6, r7);
            r5[r8] = r4;
            r2 = kawa.lib.numbers.min(r5);
            r7 = gnu.kawa.slib.printf.Lit1;
        L_0x0052:
            r4 = kawa.standard.Scheme.numGEq;
            r4 = r4.apply2(r7, r2);
            r5 = java.lang.Boolean.FALSE;
            if (r4 != r5) goto L_0x00c3;
        L_0x005c:
            r4 = r12.f28s;
            r4 = (gnu.lists.CharSeq) r4;	 Catch:{ ClassCastException -> 0x0187 }
            r6 = r12.cnt;
            r0 = r6;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0190 }
            r5 = r0;
            r10 = r5.intValue();	 Catch:{ ClassCastException -> 0x0190 }
            r0 = r13;
            r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x0199 }
            r5 = r0;
            r0 = r7;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01a2 }
            r6 = r0;
            r6 = r6.intValue();	 Catch:{ ClassCastException -> 0x01a2 }
            r5 = kawa.lib.strings.stringRef(r5, r6);
            kawa.lib.strings.stringSet$Ex(r4, r10, r5);
            r4 = gnu.kawa.functions.AddOp.$Pl;
            r5 = r12.cnt;
            r6 = gnu.kawa.slib.printf.Lit7;
            r4 = r4.apply2(r5, r6);
            r12.cnt = r4;
            r4 = gnu.kawa.functions.AddOp.$Pl;
            r5 = gnu.kawa.slib.printf.Lit7;
            r1 = r4.apply2(r7, r5);
            r7 = r1;
            goto L_0x0052;
        L_0x0093:
            r7 = new java.lang.Object[r11];
            r4 = r12.f28s;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x01ab }
            r6 = r12.cnt;
            r0 = r6;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01b4 }
            r5 = r0;
            r5 = r5.intValue();	 Catch:{ ClassCastException -> 0x01b4 }
            r4 = kawa.lib.strings.substring(r4, r9, r5);
            r7[r9] = r4;
            r7[r8] = r13;
            r4 = kawa.lib.strings.stringAppend(r7);
            r12.f28s = r4;
            r4 = r12.f28s;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x01bd }
            r4 = kawa.lib.strings.stringLength(r4);
            r4 = java.lang.Integer.valueOf(r4);
            r12.cnt = r4;
            r4 = r12.cnt;
            r12.end = r4;
        L_0x00c3:
            r4 = r12.str;
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x0169;
        L_0x00c9:
            r4 = kawa.standard.Scheme.numGEq;
            r5 = r12.cnt;
            r6 = r12.end;
            r4 = r4.apply2(r5, r6);
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x0166;
        L_0x00d7:
            r4 = r8;
        L_0x00d8:
            r4 = r4 + 1;
            r4 = r4 & 1;
            return r4;
        L_0x00dd:
            r4 = r12.str;
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x015b;
        L_0x00e3:
            r4 = kawa.standard.Scheme.numGEq;
            r5 = r12.cnt;
            r6 = r12.end;
            r3 = r4.apply2(r5, r6);
        L_0x00ed:
            r4 = java.lang.Boolean.FALSE;
            if (r3 != r4) goto L_0x00c3;
        L_0x00f1:
            r4 = r12.str;
            r5 = java.lang.Boolean.FALSE;	 Catch:{ ClassCastException -> 0x01c6 }
            if (r4 == r5) goto L_0x015e;
        L_0x00f7:
            r4 = r8;
        L_0x00f8:
            r4 = r4 + 1;
            r3 = r4 & 1;
            if (r3 == 0) goto L_0x0160;
        L_0x00fe:
            r4 = kawa.standard.Scheme.numGEq;
            r5 = r12.cnt;
            r6 = r12.end;
            r4 = r4.apply2(r5, r6);
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x012e;
        L_0x010c:
            r4 = new java.lang.Object[r11];
            r5 = r12.f28s;
            r4[r9] = r5;
            r5 = 100;
            r5 = kawa.lib.strings.makeString(r5);
            r4[r8] = r5;
            r4 = kawa.lib.strings.stringAppend(r4);
            r12.f28s = r4;
            r4 = r12.f28s;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x01d0 }
            r4 = kawa.lib.strings.stringLength(r4);
            r4 = java.lang.Integer.valueOf(r4);
            r12.end = r4;
        L_0x012e:
            r4 = r12.f28s;
            r4 = (gnu.lists.CharSeq) r4;	 Catch:{ ClassCastException -> 0x01d9 }
            r6 = r12.cnt;
            r0 = r6;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01e2 }
            r5 = r0;
            r6 = r5.intValue();	 Catch:{ ClassCastException -> 0x01e2 }
            r5 = kawa.lib.characters.isChar(r13);
            if (r5 == 0) goto L_0x0163;
        L_0x0142:
            r0 = r13;
            r0 = (gnu.text.Char) r0;	 Catch:{ ClassCastException -> 0x01eb }
            r5 = r0;
            r5 = r5.charValue();	 Catch:{ ClassCastException -> 0x01eb }
        L_0x014a:
            kawa.lib.strings.stringSet$Ex(r4, r6, r5);
            r4 = gnu.kawa.functions.AddOp.$Pl;
            r5 = r12.cnt;
            r6 = gnu.kawa.slib.printf.Lit7;
            r4 = r4.apply2(r5, r6);
            r12.cnt = r4;
            goto L_0x00c3;
        L_0x015b:
            r3 = r12.str;
            goto L_0x00ed;
        L_0x015e:
            r4 = r9;
            goto L_0x00f8;
        L_0x0160:
            if (r3 == 0) goto L_0x012e;
        L_0x0162:
            goto L_0x010c;
        L_0x0163:
            r5 = 63;
            goto L_0x014a;
        L_0x0166:
            r4 = r9;
            goto L_0x00d8;
        L_0x0169:
            r4 = r12.str;
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x0172;
        L_0x016f:
            r4 = r8;
            goto L_0x00d8;
        L_0x0172:
            r4 = r9;
            goto L_0x00d8;
        L_0x0175:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "string-length";
            r5.<init>(r4, r6, r8, r13);
            throw r5;
        L_0x017e:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "string-length";
            r5.<init>(r4, r6, r8, r13);
            throw r5;
        L_0x0187:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-set!";
            r6.<init>(r5, r7, r8, r4);
            throw r6;
        L_0x0190:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r7 = "string-set!";
            r5.<init>(r4, r7, r11, r6);
            throw r5;
        L_0x0199:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "string-ref";
            r5.<init>(r4, r6, r8, r13);
            throw r5;
        L_0x01a2:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "string-ref";
            r5.<init>(r4, r6, r11, r7);
            throw r5;
        L_0x01ab:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "substring";
            r6.<init>(r5, r7, r8, r4);
            throw r6;
        L_0x01b4:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r7 = "substring";
            r5.<init>(r4, r7, r10, r6);
            throw r5;
        L_0x01bd:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-length";
            r6.<init>(r5, r7, r8, r4);
            throw r6;
        L_0x01c6:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "x";
            r8 = -2;
            r6.<init>(r5, r7, r8, r4);
            throw r6;
        L_0x01d0:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-length";
            r6.<init>(r5, r7, r8, r4);
            throw r6;
        L_0x01d9:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "string-set!";
            r6.<init>(r5, r7, r8, r4);
            throw r6;
        L_0x01e2:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r7 = "string-set!";
            r5.<init>(r4, r7, r11, r6);
            throw r5;
        L_0x01eb:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "string-set!";
            r5.<init>(r4, r6, r10, r13);
            throw r5;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.frame13.lambda33(java.lang.Object):boolean");
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 21) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame1 extends ModuleBody {
        Object digs;
        Object ex;
        final ModuleMethod lambda$Fn4;
        Object sgn;
        frame0 staticLink;

        public frame1() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, 12291);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:126");
            this.lambda$Fn4 = moduleMethod;
        }

        public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
            return moduleMethod.selector == 1 ? lambda8(obj, obj2, obj3) : super.apply3(moduleMethod, obj, obj2, obj3);
        }

        Object lambda8(Object im$Mnsgn, Object im$Mndigs, Object im$Mnex) {
            return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.staticLink.proc, this.sgn, this.digs, this.ex, im$Mnsgn, im$Mndigs, im$Mnex});
        }

        public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.value3 = obj3;
            callContext.proc = moduleMethod;
            callContext.pc = 3;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame2 extends ModuleBody {
        Object cont;
        final ModuleMethod lambda$Fn5;
        final ModuleMethod lambda$Fn6;
        frame staticLink;

        public frame2() {
            PropertySet moduleMethod = new ModuleMethod(this, 10, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:81");
            this.lambda$Fn6 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:78");
            this.lambda$Fn5 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 11 ? lambda9(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda9(Object i) {
            return this.staticLink.lambda2sign(i, this.lambda$Fn6);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 11) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 10 ? lambda10(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda10(Object i, Object sgn) {
            frame3 gnu_kawa_slib_printf_frame3 = new frame3();
            gnu_kawa_slib_printf_frame3.staticLink = this;
            gnu_kawa_slib_printf_frame3.sgn = sgn;
            return this.staticLink.lambda3digits(i, gnu_kawa_slib_printf_frame3.lambda$Fn7);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 10) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn7;
        Object sgn;
        frame2 staticLink;

        public frame3() {
            PropertySet moduleMethod = new ModuleMethod(this, 9, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:84");
            this.lambda$Fn7 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 9 ? lambda11(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        java.lang.Object lambda11(java.lang.Object r8, java.lang.Object r9) {
            /*
            r7 = this;
            r2 = new gnu.kawa.slib.printf$frame4;
            r2.<init>();
            r2.staticLink = r7;
            r2.idigs = r9;
            r4 = r2.lambda$Fn8;
            r2 = kawa.standard.Scheme.numLss;
            r3 = r7.staticLink;
            r3 = r3.staticLink;
            r3 = r3.f29n;
            r3 = java.lang.Integer.valueOf(r3);
            r3 = r2.apply2(r8, r3);
            r0 = r3;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x005d }
            r2 = r0;
            r1 = r2.booleanValue();	 Catch:{ ClassCastException -> 0x005d }
            if (r1 == 0) goto L_0x0054;
        L_0x0025:
            r5 = gnu.kawa.slib.printf.Lit11;
            r2 = r7.staticLink;
            r2 = r2.staticLink;
            r2 = r2.str;
            r2 = (java.lang.CharSequence) r2;	 Catch:{ ClassCastException -> 0x0067 }
            r0 = r8;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0071 }
            r3 = r0;
            r3 = r3.intValue();	 Catch:{ ClassCastException -> 0x0071 }
            r2 = kawa.lib.strings.stringRef(r2, r3);
            r2 = gnu.text.Char.make(r2);
            r2 = kawa.lib.characters.isChar$Eq(r5, r2);
            if (r2 == 0) goto L_0x0056;
        L_0x0045:
            r2 = kawa.standard.Scheme.applyToArgs;
            r3 = gnu.kawa.functions.AddOp.$Pl;
            r5 = gnu.kawa.slib.printf.Lit7;
            r3 = r3.apply2(r8, r5);
            r2 = r2.apply2(r4, r3);
        L_0x0053:
            return r2;
        L_0x0054:
            if (r1 != 0) goto L_0x0045;
        L_0x0056:
            r2 = kawa.standard.Scheme.applyToArgs;
            r2 = r2.apply2(r4, r8);
            goto L_0x0053;
        L_0x005d:
            r2 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "x";
            r6 = -2;
            r4.<init>(r2, r5, r6, r3);
            throw r4;
        L_0x0067:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "string-ref";
            r6 = 1;
            r4.<init>(r3, r5, r6, r2);
            throw r4;
        L_0x0071:
            r2 = move-exception;
            r3 = new gnu.mapping.WrongType;
            r4 = "string-ref";
            r5 = 2;
            r3.<init>(r2, r4, r5, r8);
            throw r3;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.frame3.lambda11(java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 9) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame4 extends ModuleBody {
        Object idigs;
        final ModuleMethod lambda$Fn8;
        final ModuleMethod lambda$Fn9;
        frame3 staticLink;

        public frame4() {
            PropertySet moduleMethod = new ModuleMethod(this, 7, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:90");
            this.lambda$Fn9 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 8, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:87");
            this.lambda$Fn8 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 8 ? lambda12(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda12(Object i) {
            return this.staticLink.staticLink.staticLink.lambda3digits(i, this.lambda$Fn9);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 8) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 7 ? lambda13(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda13(Object i, Object fdigs) {
            frame5 gnu_kawa_slib_printf_frame5 = new frame5();
            gnu_kawa_slib_printf_frame5.staticLink = this;
            gnu_kawa_slib_printf_frame5.fdigs = fdigs;
            ModuleMethod moduleMethod = gnu_kawa_slib_printf_frame5.lambda$Fn10;
            frame closureEnv = this.staticLink.staticLink.staticLink;
            frame6 gnu_kawa_slib_printf_frame6 = new frame6();
            gnu_kawa_slib_printf_frame6.staticLink = closureEnv;
            gnu_kawa_slib_printf_frame6.cont = moduleMethod;
            if (Scheme.numGEq.apply2(i, Integer.valueOf(this.staticLink.staticLink.staticLink.f29n)) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply3(gnu_kawa_slib_printf_frame6.cont, i, printf.Lit1);
            }
            Object obj = this.staticLink.staticLink.staticLink.str;
            try {
                try {
                    if (lists.memv(Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue())), printf.Lit10) != Boolean.FALSE) {
                        return this.staticLink.staticLink.staticLink.lambda2sign(AddOp.$Pl.apply2(i, printf.Lit7), gnu_kawa_slib_printf_frame6.lambda$Fn11);
                    }
                    return Scheme.applyToArgs.apply3(gnu_kawa_slib_printf_frame6.cont, i, printf.Lit1);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 7) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame5 extends ModuleBody {
        Object fdigs;
        final ModuleMethod lambda$Fn10;
        frame4 staticLink;

        public frame5() {
            PropertySet moduleMethod = new ModuleMethod(this, 6, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:92");
            this.lambda$Fn10 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 6 ? lambda14(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda14(Object i, Object ex) {
            FString digs = strings.stringAppend("0", this.staticLink.idigs, this.fdigs);
            int ndigs = strings.stringLength(digs);
            Object obj = printf.Lit7;
            Procedure procedure = AddOp.$Pl;
            Object obj2 = this.staticLink.idigs;
            try {
                ex = procedure.apply2(ex, Integer.valueOf(strings.stringLength((CharSequence) obj2)));
                while (Scheme.numGEq.apply2(obj, Integer.valueOf(ndigs)) == Boolean.FALSE) {
                    try {
                        if (characters.isChar$Eq(printf.Lit9, Char.make(strings.stringRef(digs, ((Number) obj).intValue())))) {
                            obj = AddOp.$Pl.apply2(obj, printf.Lit7);
                            ex = AddOp.$Mn.apply2(ex, printf.Lit7);
                        } else {
                            Procedure procedure2 = Scheme.applyToArgs;
                            Object[] objArr = new Object[5];
                            objArr[0] = this.staticLink.staticLink.staticLink.cont;
                            objArr[1] = i;
                            objArr[2] = this.staticLink.staticLink.sgn;
                            Object apply2 = AddOp.$Mn.apply2(obj, printf.Lit7);
                            try {
                                objArr[3] = strings.substring(digs, ((Number) apply2).intValue(), ndigs);
                                objArr[4] = ex;
                                return procedure2.applyN(objArr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 2, apply2);
                            }
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 2, obj);
                    }
                }
                return Scheme.applyToArgs.applyN(new Object[]{this.staticLink.staticLink.staticLink.cont, i, this.staticLink.staticLink.sgn, "0", printf.Lit7});
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "string-length", 1, obj2);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 6) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame6 extends ModuleBody {
        Object cont;
        final ModuleMethod lambda$Fn11;
        frame staticLink;

        public frame6() {
            PropertySet moduleMethod = new ModuleMethod(this, 5, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:67");
            this.lambda$Fn11 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 5 ? lambda15(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda15(Object i, Object sgn) {
            frame7 gnu_kawa_slib_printf_frame7 = new frame7();
            gnu_kawa_slib_printf_frame7.staticLink = this;
            gnu_kawa_slib_printf_frame7.sgn = sgn;
            return this.staticLink.lambda3digits(i, gnu_kawa_slib_printf_frame7.lambda$Fn12);
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 5) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame7 extends ModuleBody {
        final ModuleMethod lambda$Fn12;
        Object sgn;
        frame6 staticLink;

        public frame7() {
            PropertySet moduleMethod = new ModuleMethod(this, 4, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:69");
            this.lambda$Fn12 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            return moduleMethod.selector == 4 ? lambda16(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
        }

        Object lambda16(Object i, Object digs) {
            Procedure procedure = Scheme.applyToArgs;
            Object obj = this.staticLink.cont;
            Char charR = printf.Lit5;
            Object obj2 = this.sgn;
            try {
                if (characters.isChar$Eq(charR, (Char) obj2)) {
                    try {
                        obj2 = AddOp.$Mn.apply1(numbers.string$To$Number((CharSequence) digs));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string->number", 1, digs);
                    }
                }
                try {
                    obj2 = numbers.string$To$Number((CharSequence) digs);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string->number", 1, digs);
                }
                return procedure.apply3(obj, i, obj2);
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "char=?", 2, obj2);
            }
        }

        public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
            if (moduleMethod.selector != 4) {
                return super.match2(moduleMethod, obj, obj2, callContext);
            }
            callContext.value1 = obj;
            callContext.value2 = obj2;
            callContext.proc = moduleMethod;
            callContext.pc = 2;
            return 0;
        }
    }

    /* compiled from: printf.scm */
    public class frame8 extends ModuleBody {
        CharSequence str;

        public Object lambda17dig(Object i) {
            try {
                if (!unicode.isCharNumeric(Char.make(strings.stringRef(this.str, ((Number) i).intValue())))) {
                    return printf.Lit1;
                }
                return numbers.string$To$Number(strings.$make$string$(Char.make(strings.stringRef(this.str, ((Number) i).intValue()))));
            } catch (ClassCastException e) {
                throw new WrongType(e, "string-ref", 2, i);
            }
        }
    }

    /* compiled from: printf.scm */
    public class frame9 extends ModuleBody {
        LList args;
        Object fc;
        int fl;
        Object format$Mnstring;
        Object out;
        Object pos;

        public Object lambda18mustAdvance() {
            this.pos = AddOp.$Pl.apply2(printf.Lit7, this.pos);
            if (Scheme.numGEq.apply2(this.pos, Integer.valueOf(this.fl)) != Boolean.FALSE) {
                return lambda20incomplete();
            }
            Object obj = this.format$Mnstring;
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object obj2 = this.pos;
                try {
                    this.fc = Char.make(strings.stringRef(charSequence, ((Number) obj2).intValue()));
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, obj2);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        }

        public boolean lambda19isEndOfFormat() {
            return ((Boolean) Scheme.numGEq.apply2(this.pos, Integer.valueOf(this.fl))).booleanValue();
        }

        public Object lambda20incomplete() {
            return misc.error$V(printf.Lit34, new Object[]{"conversion specification incomplete", this.format$Mnstring});
        }

        public Object lambda21out$St(Object strs) {
            if (strings.isString(strs)) {
                return Scheme.applyToArgs.apply2(this.out, strs);
            }
            Object x;
            while (true) {
                x = lists.isNull(strs);
                if (x != null) {
                    break;
                }
                Boolean x2 = Scheme.applyToArgs.apply2(this.out, lists.car.apply1(strs));
                if (x2 == Boolean.FALSE) {
                    return x2;
                }
                strs = lists.cdr.apply1(strs);
            }
            return x != null ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    /* compiled from: printf.scm */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        int f29n;
        Object proc;
        Object str;

        public frame() {
            PropertySet moduleMethod = new ModuleMethod(this, 12, null, 16388);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm:106");
            this.lambda$Fn1 = moduleMethod;
        }

        public static Boolean lambda1parseError() {
            return Boolean.FALSE;
        }

        public Object lambda2sign(Object i, Object cont) {
            if (Scheme.numLss.apply2(i, Integer.valueOf(this.f29n)) == Boolean.FALSE) {
                return Values.empty;
            }
            Object obj = this.str;
            try {
                try {
                    char c = strings.stringRef((CharSequence) obj, ((Number) i).intValue());
                    Boolean x = Scheme.isEqv.apply2(Char.make(c), printf.Lit5);
                    if (x == Boolean.FALSE ? Scheme.isEqv.apply2(Char.make(c), printf.Lit6) != Boolean.FALSE : x != Boolean.FALSE) {
                        return Scheme.applyToArgs.apply3(cont, AddOp.$Pl.apply2(i, printf.Lit7), Char.make(c));
                    }
                    return Scheme.applyToArgs.apply3(cont, i, printf.Lit6);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, obj);
            }
        }

        public Object lambda3digits(Object i, Object cont) {
            Object obj;
            int i2 = 2;
            Object j = i;
            while (true) {
                Object apply2 = Scheme.numGEq.apply2(j, Integer.valueOf(this.f29n));
                try {
                    boolean x = ((Boolean) apply2).booleanValue();
                    if (x) {
                        if (x) {
                            break;
                        }
                    } else {
                        obj = this.str;
                        try {
                            try {
                                x = unicode.isCharNumeric(Char.make(strings.stringRef((CharSequence) obj, ((Number) j).intValue())));
                                if (!x) {
                                    Char charR = printf.Lit8;
                                    obj = this.str;
                                    try {
                                        try {
                                            if (!characters.isChar$Eq(charR, Char.make(strings.stringRef((CharSequence) obj, ((Number) j).intValue())))) {
                                                break;
                                            }
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "string-ref", i2, j);
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-ref", 1, obj);
                                    }
                                } else if (!x) {
                                    break;
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-ref", i2, j);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "string-ref", 1, obj);
                        }
                    }
                    j = AddOp.$Pl.apply2(j, printf.Lit7);
                } catch (ClassCastException e32) {
                    throw new WrongType(e32, "x", -2, apply2);
                }
            }
            Procedure procedure = Scheme.applyToArgs;
            if (Scheme.numEqu.apply2(i, j) != Boolean.FALSE) {
                obj = "0";
            } else {
                obj = this.str;
                try {
                    try {
                        try {
                            obj = strings.substring((CharSequence) obj, ((Number) i).intValue(), ((Number) j).intValue());
                        } catch (ClassCastException e322) {
                            throw new WrongType(e322, "substring", 3, j);
                        }
                    } catch (ClassCastException e3222) {
                        throw new WrongType(e3222, "substring", i2, i);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "substring", 1, obj);
                }
            }
            return procedure.apply3(cont, j, obj);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda4real(java.lang.Object r10, java.lang.Object r11) {
            /*
            r9 = this;
            r8 = 2;
            r7 = 1;
            r3 = new gnu.kawa.slib.printf$frame2;
            r3.<init>();
            r3.staticLink = r9;
            r3.cont = r11;
            r11 = r3.lambda$Fn5;
        L_0x000d:
            r3 = kawa.standard.Scheme.numLss;
            r4 = r9.f29n;
            r4 = r4 + -1;
            r4 = java.lang.Integer.valueOf(r4);
            r4 = r3.apply2(r10, r4);
            r0 = r4;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x00c0 }
            r3 = r0;
            r2 = r3.booleanValue();	 Catch:{ ClassCastException -> 0x00c0 }
            if (r2 == 0) goto L_0x0076;
        L_0x0025:
            r5 = gnu.kawa.slib.printf.Lit8;
            r3 = r9.str;
            r3 = (java.lang.CharSequence) r3;	 Catch:{ ClassCastException -> 0x00ca }
            r0 = r10;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x00d3 }
            r4 = r0;
            r4 = r4.intValue();	 Catch:{ ClassCastException -> 0x00d3 }
            r3 = kawa.lib.strings.stringRef(r3, r4);
            r3 = gnu.text.Char.make(r3);
            r3 = kawa.lib.characters.isChar$Eq(r5, r3);
            if (r3 == 0) goto L_0x0078;
        L_0x0041:
            r3 = r9.str;
            r3 = (java.lang.CharSequence) r3;	 Catch:{ ClassCastException -> 0x00dc }
            r4 = gnu.kawa.functions.AddOp.$Pl;
            r5 = gnu.kawa.slib.printf.Lit7;
            r5 = r4.apply2(r10, r5);
            r0 = r5;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x00e5 }
            r4 = r0;
            r4 = r4.intValue();	 Catch:{ ClassCastException -> 0x00e5 }
            r1 = kawa.lib.strings.stringRef(r3, r4);
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.text.Char.make(r1);
            r5 = gnu.kawa.slib.printf.Lit12;
            r2 = r3.apply2(r4, r5);
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x007f;
        L_0x0069:
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x0093;
        L_0x006d:
            r3 = gnu.kawa.functions.AddOp.$Pl;
            r4 = gnu.kawa.slib.printf.Lit14;
            r10 = r3.apply2(r10, r4);
            goto L_0x000d;
        L_0x0076:
            if (r2 != 0) goto L_0x0041;
        L_0x0078:
            r3 = kawa.standard.Scheme.applyToArgs;
            r3 = r3.apply2(r11, r10);
        L_0x007e:
            return r3;
        L_0x007f:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.text.Char.make(r1);
            r5 = gnu.kawa.slib.printf.Lit3;
            r2 = r3.apply2(r4, r5);
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x00aa;
        L_0x008f:
            r3 = java.lang.Boolean.FALSE;
            if (r2 != r3) goto L_0x006d;
        L_0x0093:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.text.Char.make(r1);
            r5 = gnu.kawa.slib.printf.Lit11;
            r3 = r3.apply2(r4, r5);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x00bb;
        L_0x00a3:
            r3 = kawa.standard.Scheme.applyToArgs;
            r3 = r3.apply2(r11, r10);
            goto L_0x007e;
        L_0x00aa:
            r3 = kawa.standard.Scheme.isEqv;
            r4 = gnu.text.Char.make(r1);
            r5 = gnu.kawa.slib.printf.Lit13;
            r3 = r3.apply2(r4, r5);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x0093;
        L_0x00ba:
            goto L_0x006d;
        L_0x00bb:
            r3 = lambda1parseError();
            goto L_0x007e;
        L_0x00c0:
            r3 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "x";
            r7 = -2;
            r5.<init>(r3, r6, r7, r4);
            throw r5;
        L_0x00ca:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "string-ref";
            r5.<init>(r4, r6, r7, r3);
            throw r5;
        L_0x00d3:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "string-ref";
            r4.<init>(r3, r5, r8, r10);
            throw r4;
        L_0x00dc:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "string-ref";
            r5.<init>(r4, r6, r7, r3);
            throw r5;
        L_0x00e5:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r6 = "string-ref";
            r4.<init>(r3, r6, r8, r5);
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.frame.lambda4real(java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
            return moduleMethod.selector == 12 ? lambda5(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }

        Object lambda5(Object i, Object sgn, Object digs, Object ex) {
            frame0 gnu_kawa_slib_printf_frame0 = new frame0();
            gnu_kawa_slib_printf_frame0.staticLink = this;
            gnu_kawa_slib_printf_frame0.sgn = sgn;
            gnu_kawa_slib_printf_frame0.digs = digs;
            gnu_kawa_slib_printf_frame0.ex = ex;
            if (Scheme.numEqu.apply2(i, Integer.valueOf(this.f29n)) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply4(this.proc, gnu_kawa_slib_printf_frame0.sgn, gnu_kawa_slib_printf_frame0.digs, gnu_kawa_slib_printf_frame0.ex);
            }
            Object obj = this.str;
            try {
                try {
                    if (lists.memv(Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue())), printf.Lit2) != Boolean.FALSE) {
                        return lambda4real(i, gnu_kawa_slib_printf_frame0.lambda$Fn2);
                    }
                    Procedure procedure = Scheme.isEqv;
                    obj = this.str;
                    try {
                        try {
                            if (procedure.apply2(Char.make(strings.stringRef((CharSequence) obj, ((Number) i).intValue())), printf.Lit4) == Boolean.FALSE) {
                                return Boolean.FALSE;
                            }
                            obj = this.str;
                            try {
                                gnu_kawa_slib_printf_frame0.num = numbers.string$To$Number((CharSequence) obj);
                                if (gnu_kawa_slib_printf_frame0.num == Boolean.FALSE) {
                                    return lambda1parseError();
                                }
                                obj = gnu_kawa_slib_printf_frame0.num;
                                try {
                                    return printf.stdio$ClParseFloat(numbers.number$To$String(numbers.realPart((Complex) obj)), gnu_kawa_slib_printf_frame0.lambda$Fn3);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "real-part", 1, obj);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string->number", 1, obj);
                            }
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "string-ref", 1, obj);
                    }
                } catch (ClassCastException e32) {
                    throw new WrongType(e32, "string-ref", 2, i);
                }
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "string-ref", 1, obj);
            }
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
    }

    static {
        Char charR = Lit35;
        Char charR2 = Lit37;
        Char charR3 = Lit39;
        Char make = Char.make(100);
        Lit12 = make;
        Char make2 = Char.make(105);
        Lit3 = make2;
        Char charR4 = Lit43;
        Char charR5 = Lit46;
        Char charR6 = Lit49;
        Char charR7 = Lit52;
        Char make3 = Char.make(102);
        Lit25 = make3;
        Char make4 = Char.make(101);
        Lit13 = make4;
        Lit33 = PairWithPosition.make(charR, PairWithPosition.make(charR2, PairWithPosition.make(charR3, PairWithPosition.make(make, PairWithPosition.make(make2, PairWithPosition.make(charR4, PairWithPosition.make(charR5, PairWithPosition.make(charR6, PairWithPosition.make(charR7, PairWithPosition.make(make3, PairWithPosition.make(make4, PairWithPosition.make(Lit55, PairWithPosition.make(Lit57, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1781780), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1781776), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1781772), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1781768), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777704), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777700), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777696), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777692), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777688), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777684), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777680), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777676), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/printf.scm", 1777671);
        ModuleBody moduleBody = $instance;
        stdio$Clparse$Mnfloat = new ModuleMethod(moduleBody, 22, Lit69, 8194);
        stdio$Clround$Mnstring = new ModuleMethod(moduleBody, 23, Lit70, 12291);
        stdio$Cliprintf = new ModuleMethod(moduleBody, 24, Lit71, -4094);
        fprintf = new ModuleMethod(moduleBody, 25, Lit72, -4094);
        printf = new ModuleMethod(moduleBody, 26, Lit34, -4095);
        sprintf = new ModuleMethod(moduleBody, 27, Lit68, -4094);
        $instance.run();
    }

    public printf() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        stdio$Clhex$Mnupper$Mncase$Qu = strings.isString$Eq("-F", numbers.number$To$String(Lit0, 16));
    }

    public static Object stdio$ClParseFloat(Object str, Object proc) {
        frame gnu_kawa_slib_printf_frame = new frame();
        gnu_kawa_slib_printf_frame.str = str;
        gnu_kawa_slib_printf_frame.proc = proc;
        Object obj = gnu_kawa_slib_printf_frame.str;
        try {
            gnu_kawa_slib_printf_frame.f29n = strings.stringLength((CharSequence) obj);
            return gnu_kawa_slib_printf_frame.lambda4real(Lit1, gnu_kawa_slib_printf_frame.lambda$Fn1);
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, obj);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 22 ? stdio$ClParseFloat(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 22) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.pc = 2;
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object stdio$ClRoundString(java.lang.CharSequence r14, java.lang.Object r15, java.lang.Object r16) {
        /*
        r1 = new gnu.kawa.slib.printf$frame8;
        r1.<init>();
        r1.str = r14;
        r9 = r1.str;
        r9 = kawa.lib.strings.stringLength(r9);
        r5 = r9 + -1;
        r9 = kawa.standard.Scheme.numLss;
        r10 = Lit1;
        r9 = r9.apply2(r15, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x005d;
    L_0x001b:
        r7 = "";
    L_0x001d:
        r9 = java.lang.Boolean.FALSE;
        r0 = r16;
        if (r0 == r9) goto L_0x005c;
    L_0x0023:
        r0 = r7;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x0248 }
        r9 = r0;
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = r9 + -1;
        r3 = java.lang.Integer.valueOf(r9);
    L_0x0031:
        r9 = kawa.standard.Scheme.numLEq;
        r0 = r16;
        r10 = r9.apply2(r3, r0);
        r0 = r10;
        r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x0252 }
        r9 = r0;
        r8 = r9.booleanValue();	 Catch:{ ClassCastException -> 0x0252 }
        if (r8 == 0) goto L_0x01b4;
    L_0x0043:
        if (r8 == 0) goto L_0x01d0;
    L_0x0045:
        r7 = (java.lang.CharSequence) r7;	 Catch:{ ClassCastException -> 0x0270 }
        r11 = 0;
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit7;
        r10 = r9.apply2(r3, r10);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x027a }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x027a }
        r7 = kawa.lib.strings.substring(r7, r11, r9);
    L_0x005c:
        return r7;
    L_0x005d:
        r9 = kawa.standard.Scheme.numEqu;
        r10 = java.lang.Integer.valueOf(r5);
        r9 = r9.apply2(r10, r15);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x006e;
    L_0x006b:
        r7 = r1.str;
        goto L_0x001d;
    L_0x006e:
        r9 = kawa.standard.Scheme.numLss;
        r10 = java.lang.Integer.valueOf(r5);
        r9 = r9.apply2(r10, r15);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x00dd;
    L_0x007c:
        r9 = 2;
        r9 = new java.lang.Object[r9];
        r10 = 0;
        r11 = Lit1;
        r9[r10] = r11;
        r10 = 1;
        r11 = gnu.kawa.functions.AddOp.$Mn;
        r12 = java.lang.Boolean.FALSE;
        r0 = r16;
        if (r0 == r12) goto L_0x008f;
    L_0x008d:
        r15 = r16;
    L_0x008f:
        r12 = java.lang.Integer.valueOf(r5);
        r11 = r11.apply2(r15, r12);
        r9[r10] = r11;
        r6 = kawa.lib.numbers.max(r9);
        r0 = r6;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01da }
        r9 = r0;
        r9 = kawa.lib.numbers.isZero(r9);
        if (r9 == 0) goto L_0x00ac;
    L_0x00a7:
        r9 = r1.str;
    L_0x00a9:
        r7 = r9;
        goto L_0x001d;
    L_0x00ac:
        r9 = 2;
        r10 = new java.lang.Object[r9];
        r9 = 0;
        r11 = r1.str;
        r10[r9] = r11;
        r11 = 1;
        r0 = r6;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01e4 }
        r9 = r0;
        r12 = r9.intValue();	 Catch:{ ClassCastException -> 0x01e4 }
        r9 = r1.str;
        r9 = kawa.lib.strings.stringRef(r9, r5);
        r9 = gnu.text.Char.make(r9);
        r9 = kawa.lib.rnrs.unicode.isCharNumeric(r9);
        if (r9 == 0) goto L_0x00da;
    L_0x00cd:
        r9 = Lit9;
    L_0x00cf:
        r9 = kawa.lib.strings.makeString(r12, r9);
        r10[r11] = r9;
        r9 = kawa.lib.strings.stringAppend(r10);
        goto L_0x00a9;
    L_0x00da:
        r9 = Lit8;
        goto L_0x00cf;
    L_0x00dd:
        r11 = r1.str;
        r12 = 0;
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit7;
        r10 = r9.apply2(r15, r10);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01ee }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x01ee }
        r7 = kawa.lib.strings.substring(r11, r12, r9);
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit7;
        r9 = r9.apply2(r10, r15);
        r4 = r1.lambda17dig(r9);
        r9 = kawa.standard.Scheme.numGrt;
        r10 = Lit15;
        r10 = r9.apply2(r4, r10);
        r0 = r10;
        r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x01f8 }
        r9 = r0;
        r8 = r9.booleanValue();	 Catch:{ ClassCastException -> 0x01f8 }
        if (r8 == 0) goto L_0x0149;
    L_0x0112:
        if (r8 == 0) goto L_0x001d;
    L_0x0114:
        r3 = r15;
    L_0x0115:
        r2 = r1.lambda17dig(r3);
        r9 = kawa.standard.Scheme.numLss;
        r10 = Lit16;
        r9 = r9.apply2(r2, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0199;
    L_0x0125:
        r0 = r7;
        r0 = (gnu.lists.CharSeq) r0;	 Catch:{ ClassCastException -> 0x0216 }
        r9 = r0;
        r0 = r3;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0220 }
        r10 = r0;
        r11 = r10.intValue();	 Catch:{ ClassCastException -> 0x0220 }
        r10 = gnu.kawa.functions.AddOp.$Pl;
        r12 = Lit7;
        r10 = r10.apply2(r2, r12);
        r10 = (java.lang.Number) r10;	 Catch:{ ClassCastException -> 0x022a }
        r10 = kawa.lib.numbers.number$To$String(r10);
        r12 = 0;
        r10 = kawa.lib.strings.stringRef(r10, r12);
        kawa.lib.strings.stringSet$Ex(r9, r11, r10);
        goto L_0x001d;
    L_0x0149:
        r9 = kawa.standard.Scheme.numEqu;
        r10 = Lit15;
        r10 = r9.apply2(r4, r10);
        r0 = r10;
        r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x0202 }
        r9 = r0;
        r8 = r9.booleanValue();	 Catch:{ ClassCastException -> 0x0202 }
        if (r8 == 0) goto L_0x0195;
    L_0x015b:
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit14;
        r3 = r9.apply2(r10, r15);
    L_0x0163:
        r9 = kawa.standard.Scheme.numGrt;
        r10 = java.lang.Integer.valueOf(r5);
        r9 = r9.apply2(r3, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0180;
    L_0x0171:
        r9 = r1.lambda17dig(r15);
        r9 = (java.lang.Number) r9;
        r9 = r9.intValue();
        r9 = r9 & 1;
        if (r9 == 0) goto L_0x001d;
    L_0x017f:
        goto L_0x0114;
    L_0x0180:
        r9 = r1.lambda17dig(r3);
        r9 = (java.lang.Number) r9;	 Catch:{ ClassCastException -> 0x020c }
        r9 = kawa.lib.numbers.isZero(r9);
        if (r9 == 0) goto L_0x0114;
    L_0x018c:
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit7;
        r3 = r9.apply2(r3, r10);
        goto L_0x0163;
    L_0x0195:
        if (r8 == 0) goto L_0x001d;
    L_0x0197:
        goto L_0x0114;
    L_0x0199:
        r0 = r7;
        r0 = (gnu.lists.CharSeq) r0;	 Catch:{ ClassCastException -> 0x0234 }
        r9 = r0;
        r0 = r3;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x023e }
        r10 = r0;
        r10 = r10.intValue();	 Catch:{ ClassCastException -> 0x023e }
        r11 = 48;
        kawa.lib.strings.stringSet$Ex(r9, r10, r11);
        r9 = gnu.kawa.functions.AddOp.$Mn;
        r10 = Lit7;
        r3 = r9.apply2(r3, r10);
        goto L_0x0115;
    L_0x01b4:
        r11 = Lit9;
        r0 = r7;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x025c }
        r9 = r0;
        r0 = r3;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0266 }
        r10 = r0;
        r10 = r10.intValue();	 Catch:{ ClassCastException -> 0x0266 }
        r9 = kawa.lib.strings.stringRef(r9, r10);
        r9 = gnu.text.Char.make(r9);
        r9 = kawa.lib.characters.isChar$Eq(r11, r9);
        if (r9 == 0) goto L_0x0045;
    L_0x01d0:
        r9 = gnu.kawa.functions.AddOp.$Mn;
        r10 = Lit7;
        r3 = r9.apply2(r3, r10);
        goto L_0x0031;
    L_0x01da:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "zero?";
        r12 = 1;
        r10.<init>(r9, r11, r12, r6);
        throw r10;
    L_0x01e4:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "make-string";
        r12 = 1;
        r10.<init>(r9, r11, r12, r6);
        throw r10;
    L_0x01ee:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "substring";
        r13 = 3;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x01f8:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "x";
        r13 = -2;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x0202:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "x";
        r13 = -2;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x020c:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "zero?";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0216:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-set!";
        r12 = 1;
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0220:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-set!";
        r12 = 2;
        r10.<init>(r9, r11, r12, r3);
        throw r10;
    L_0x022a:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "number->string";
        r13 = 1;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x0234:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-set!";
        r12 = 1;
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x023e:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-set!";
        r12 = 2;
        r10.<init>(r9, r11, r12, r3);
        throw r10;
    L_0x0248:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-length";
        r12 = 1;
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0252:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "x";
        r13 = -2;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x025c:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-ref";
        r12 = 1;
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0266:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-ref";
        r12 = 2;
        r10.<init>(r9, r11, r12, r3);
        throw r10;
    L_0x0270:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "substring";
        r12 = 1;
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x027a:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "substring";
        r13 = 3;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.stdio$ClRoundString(java.lang.CharSequence, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        if (moduleMethod.selector != 23) {
            return super.apply3(moduleMethod, obj, obj2, obj3);
        }
        try {
            return stdio$ClRoundString((CharSequence) obj, obj2, obj3);
        } catch (ClassCastException e) {
            throw new WrongType(e, "stdio:round-string", 1, obj);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector != 23) {
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
        if (!(obj instanceof CharSequence)) {
            return -786431;
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.proc = moduleMethod;
        callContext.pc = 3;
        return 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object stdio$ClIprintf$V(java.lang.Object r17, java.lang.Object r18, java.lang.Object[] r19) {
        /*
        r2 = new gnu.kawa.slib.printf$frame9;
        r2.<init>();
        r0 = r17;
        r2.out = r0;
        r0 = r18;
        r2.format$Mnstring = r0;
        r9 = 0;
        r0 = r19;
        r9 = gnu.lists.LList.makeList(r0, r9);
        r2.args = r9;
        r9 = "";
        r10 = r2.format$Mnstring;
        r9 = gnu.kawa.functions.IsEqual.apply(r9, r10);
        if (r9 != 0) goto L_0x0856;
    L_0x0020:
        r10 = Lit17;
        r9 = r2.format$Mnstring;
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x085a }
        r11 = kawa.lib.strings.stringLength(r9);
        r9 = r2.format$Mnstring;
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x0864 }
        r12 = 0;
        r9 = kawa.lib.strings.stringRef(r9, r12);
        r9 = gnu.text.Char.make(r9);
        r2.fc = r9;
        r2.fl = r11;
        r2.pos = r10;
        r1 = r2.args;
    L_0x003f:
        r12 = new gnu.kawa.slib.printf$frame10;
        r12.<init>();
        r12.staticLink = r2;
        r12.args = r1;
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit7;
        r11 = r2.pos;
        r9 = r9.apply2(r10, r11);
        r2.pos = r9;
        r9 = kawa.standard.Scheme.numGEq;
        r10 = r2.pos;
        r11 = r2.fl;
        r11 = java.lang.Integer.valueOf(r11);
        r9 = r9.apply2(r10, r11);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0076;
    L_0x0066:
        r9 = java.lang.Boolean.FALSE;
        r2.fc = r9;
    L_0x006a:
        r7 = r2.lambda19isEndOfFormat();
        if (r7 == 0) goto L_0x0092;
    L_0x0070:
        if (r7 == 0) goto L_0x008f;
    L_0x0072:
        r9 = java.lang.Boolean.TRUE;
    L_0x0074:
        r7 = r9;
    L_0x0075:
        return r7;
    L_0x0076:
        r9 = r2.format$Mnstring;
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x086e }
        r11 = r2.pos;
        r0 = r11;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0878 }
        r10 = r0;
        r10 = r10.intValue();	 Catch:{ ClassCastException -> 0x0878 }
        r9 = kawa.lib.strings.stringRef(r9, r10);
        r9 = gnu.text.Char.make(r9);
        r2.fc = r9;
        goto L_0x006a;
    L_0x008f:
        r9 = java.lang.Boolean.FALSE;
        goto L_0x0074;
    L_0x0092:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit18;
        r11 = r2.fc;
        r9 = r9.apply2(r10, r11);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x013e;
    L_0x00a0:
        r2.lambda18mustAdvance();
        r6 = r2.fc;
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit19;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x00c7;
    L_0x00b1:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x00d3;
    L_0x00b5:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = Lit21;
        r8 = r9.apply2(r10, r11);
    L_0x00bf:
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x013b;
    L_0x00c3:
        r1 = r12.args;
        goto L_0x003f;
    L_0x00c7:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit20;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x00b5;
    L_0x00d3:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit22;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x00ee;
    L_0x00df:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x00fa;
    L_0x00e3:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = Lit24;
        r8 = r9.apply2(r10, r11);
        goto L_0x00bf;
    L_0x00ee:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit23;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x00e3;
    L_0x00fa:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit25;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0115;
    L_0x0106:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0121;
    L_0x010a:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = Lit27;
        r8 = r9.apply2(r10, r11);
        goto L_0x00bf;
    L_0x0115:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit26;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x010a;
    L_0x0121:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit21;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0130;
    L_0x012d:
        r8 = java.lang.Boolean.TRUE;
        goto L_0x00bf;
    L_0x0130:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = r2.fc;
        r8 = r9.apply2(r10, r11);
        goto L_0x00bf;
    L_0x013b:
        r7 = r8;
        goto L_0x0075;
    L_0x013e:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit28;
        r11 = r2.fc;
        r9 = r9.apply2(r10, r11);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0844;
    L_0x014c:
        r2.lambda18mustAdvance();
        r9 = java.lang.Boolean.FALSE;
        r10 = java.lang.Boolean.FALSE;
        r11 = java.lang.Boolean.FALSE;
        r13 = java.lang.Boolean.FALSE;
        r14 = java.lang.Boolean.FALSE;
        r15 = Lit1;
        r16 = Lit17;
        r0 = r16;
        r12.precision = r0;
        r12.width = r15;
        r12.leading$Mn0s = r14;
        r12.alternate$Mnform = r13;
        r12.blank = r11;
        r12.signed = r10;
        r12.left$Mnadjust = r9;
        r9 = r12.pad;
        r12.pad = r9;
    L_0x0171:
        r6 = r2.fc;
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit5;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0187;
    L_0x017f:
        r9 = java.lang.Boolean.TRUE;
        r12.left$Mnadjust = r9;
    L_0x0183:
        r2.lambda18mustAdvance();
        goto L_0x0171;
    L_0x0187:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit6;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0198;
    L_0x0193:
        r9 = java.lang.Boolean.TRUE;
        r12.signed = r9;
        goto L_0x0183;
    L_0x0198:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit29;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x01a9;
    L_0x01a4:
        r9 = java.lang.Boolean.TRUE;
        r12.blank = r9;
        goto L_0x0183;
    L_0x01a9:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit8;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x01ba;
    L_0x01b5:
        r9 = java.lang.Boolean.TRUE;
        r12.alternate$Mnform = r9;
        goto L_0x0183;
    L_0x01ba:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit9;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x01cb;
    L_0x01c6:
        r9 = java.lang.Boolean.TRUE;
        r12.leading$Mn0s = r9;
        goto L_0x0183;
    L_0x01cb:
        r9 = r12.left$Mnadjust;
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x01d5;
    L_0x01d1:
        r9 = java.lang.Boolean.FALSE;
        r12.leading$Mn0s = r9;
    L_0x01d5:
        r9 = r12.signed;
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x01df;
    L_0x01db:
        r9 = java.lang.Boolean.FALSE;
        r12.blank = r9;
    L_0x01df:
        r9 = r12.lambda22readFormatNumber();
        r12.width = r9;
        r9 = r12.width;
        r9 = gnu.kawa.lispexpr.LangObjType.coerceRealNum(r9);	 Catch:{ ClassCastException -> 0x0882 }
        r9 = kawa.lib.numbers.isNegative(r9);
        if (r9 == 0) goto L_0x01ff;
    L_0x01f1:
        r9 = java.lang.Boolean.TRUE;
        r12.left$Mnadjust = r9;
        r9 = gnu.kawa.functions.AddOp.$Mn;
        r10 = r12.width;
        r9 = r9.apply1(r10);
        r12.width = r9;
    L_0x01ff:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit11;
        r11 = r2.fc;
        r9 = r9.apply2(r10, r11);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0216;
    L_0x020d:
        r2.lambda18mustAdvance();
        r9 = r12.lambda22readFormatNumber();
        r12.precision = r9;
    L_0x0216:
        r6 = r2.fc;
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit30;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x029e;
    L_0x0224:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x022b;
    L_0x0228:
        r2.lambda18mustAdvance();
    L_0x022b:
        r9 = r12.args;
        r9 = kawa.lib.lists.isNull(r9);
        if (r9 == 0) goto L_0x0264;
    L_0x0233:
        r9 = r2.fc;
        r9 = (gnu.text.Char) r9;	 Catch:{ ClassCastException -> 0x088c }
        r9 = kawa.lib.rnrs.unicode.charDowncase(r9);
        r10 = Lit33;
        r9 = kawa.lib.lists.memv(r9, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0264;
    L_0x0245:
        r9 = Lit34;
        r10 = 3;
        r10 = new java.lang.Object[r10];
        r11 = 0;
        r13 = "wrong number of arguments";
        r10[r11] = r13;
        r11 = 1;
        r13 = r2.args;
        r13 = kawa.lib.lists.length(r13);
        r13 = java.lang.Integer.valueOf(r13);
        r10[r11] = r13;
        r11 = 2;
        r13 = r2.format$Mnstring;
        r10[r11] = r13;
        kawa.lib.misc.error$V(r9, r10);
    L_0x0264:
        r6 = r2.fc;
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit35;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x02be;
    L_0x0272:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x02ca;
    L_0x0276:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r9 = r9.apply1(r10);
        r10 = r9 instanceof java.lang.Object[];
        if (r10 == 0) goto L_0x033f;
    L_0x0286:
        r9 = (java.lang.Object[]) r9;
    L_0x0288:
        r9 = kawa.lib.strings.$make$string$(r9);
        r7 = r11.apply2(r13, r9);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x0294:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x029e:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit31;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x02b0;
    L_0x02aa:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x022b;
    L_0x02ae:
        goto L_0x0228;
    L_0x02b0:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit32;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x022b;
    L_0x02bc:
        goto L_0x0228;
    L_0x02be:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit36;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x0276;
    L_0x02ca:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit37;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0348;
    L_0x02d6:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0354;
    L_0x02da:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r9 = r9.apply1(r10);
        r9 = kawa.lib.misc.isSymbol(r9);
        if (r9 == 0) goto L_0x03e2;
    L_0x02e8:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r9 = r9.apply1(r10);
        r9 = (gnu.mapping.Symbol) r9;	 Catch:{ ClassCastException -> 0x0896 }
        r4 = kawa.lib.misc.symbol$To$String(r9);
    L_0x02f6:
        r9 = r12.precision;
        r9 = gnu.kawa.lispexpr.LangObjType.coerceRealNum(r9);	 Catch:{ ClassCastException -> 0x08a0 }
        r7 = kawa.lib.numbers.isNegative(r9);
        if (r7 == 0) goto L_0x03fc;
    L_0x0302:
        if (r7 != 0) goto L_0x0315;
    L_0x0304:
        r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x08b4 }
        r11 = 0;
        r10 = r12.precision;
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x08be }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x08be }
        r4 = kawa.lib.strings.substring(r4, r11, r9);
    L_0x0315:
        r10 = kawa.standard.Scheme.numLEq;
        r11 = r12.width;
        r0 = r4;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x08c8 }
        r9 = r0;
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = java.lang.Integer.valueOf(r9);
        r9 = r10.apply2(r11, r9);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0416;
    L_0x032d:
        r7 = r2.lambda21out$St(r4);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x0335:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x033f:
        r10 = 1;
        r10 = new java.lang.Object[r10];
        r14 = 0;
        r10[r14] = r9;
        r9 = r10;
        goto L_0x0288;
    L_0x0348:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit38;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x02da;
    L_0x0354:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit39;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0475;
    L_0x0360:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0481;
    L_0x0364:
        r9 = "";
        r10 = r12.precision;
        r12.pr = r10;
        r12.os = r9;
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r11 = r9.apply1(r10);
        r9 = r12.alternate$Mnform;
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x04b3;
    L_0x037a:
        r9 = java.lang.Boolean.FALSE;
    L_0x037c:
        r13 = java.lang.Boolean.FALSE;
        r7 = r12.left$Mnadjust;
        r10 = java.lang.Boolean.FALSE;
        if (r7 == r10) goto L_0x04b7;
    L_0x0384:
        r10 = r12.pr;
        r10 = gnu.kawa.lispexpr.LangObjType.coerceRealNum(r10);	 Catch:{ ClassCastException -> 0x08fa }
        r10 = kawa.lib.numbers.isNegative(r10);
        if (r10 == 0) goto L_0x04bb;
    L_0x0390:
        r10 = Lit1;
        r12.pr = r10;
        r10 = r12.lambda$Fn13;
    L_0x0396:
        gnu.kawa.slib.genwrite.genericWrite(r11, r9, r13, r10);
        r7 = r12.left$Mnadjust;
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x04dd;
    L_0x039f:
        r9 = r12.precision;
        r9 = gnu.kawa.lispexpr.LangObjType.coerceRealNum(r9);	 Catch:{ ClassCastException -> 0x090e }
        r9 = kawa.lib.numbers.isNegative(r9);
        if (r9 == 0) goto L_0x04e1;
    L_0x03ab:
        r9 = kawa.standard.Scheme.numGrt;
        r10 = r12.width;
        r11 = r12.pr;
        r9 = r9.apply2(r10, r11);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x03d8;
    L_0x03b9:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r9 = gnu.kawa.functions.AddOp.$Mn;
        r10 = r12.width;
        r14 = r12.pr;
        r10 = r9.apply2(r10, r14);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0918 }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x0918 }
        r10 = Lit29;
        r9 = kawa.lib.strings.makeString(r9, r10);
        r11.apply2(r13, r9);
    L_0x03d8:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x03e2:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r9 = r9.apply1(r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x03f2;
    L_0x03ee:
        r4 = "(NULL)";
        goto L_0x02f6;
    L_0x03f2:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r4 = r9.apply1(r10);
        goto L_0x02f6;
    L_0x03fc:
        r10 = kawa.standard.Scheme.numGEq;
        r11 = r12.precision;
        r0 = r4;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x08aa }
        r9 = r0;
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = java.lang.Integer.valueOf(r9);
        r9 = r10.apply2(r11, r9);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x0315;
    L_0x0414:
        goto L_0x0304;
    L_0x0416:
        r9 = r12.left$Mnadjust;
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0444;
    L_0x041c:
        r10 = gnu.kawa.functions.AddOp.$Mn;
        r11 = r12.width;
        r0 = r4;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x08d2 }
        r9 = r0;
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = java.lang.Integer.valueOf(r9);
        r10 = r10.apply2(r11, r9);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x08dc }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x08dc }
        r10 = Lit29;
        r9 = kawa.lib.strings.makeString(r9, r10);
        r4 = gnu.lists.LList.list2(r4, r9);
        goto L_0x032d;
    L_0x0444:
        r10 = gnu.kawa.functions.AddOp.$Mn;
        r11 = r12.width;
        r0 = r4;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x08e6 }
        r9 = r0;
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = java.lang.Integer.valueOf(r9);
        r10 = r10.apply2(r11, r9);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x08f0 }
        r9 = r0;
        r10 = r9.intValue();	 Catch:{ ClassCastException -> 0x08f0 }
        r9 = r12.leading$Mn0s;
        r11 = java.lang.Boolean.FALSE;
        if (r9 == r11) goto L_0x0472;
    L_0x0466:
        r9 = Lit9;
    L_0x0468:
        r9 = kawa.lib.strings.makeString(r10, r9);
        r4 = gnu.lists.LList.list2(r9, r4);
        goto L_0x032d;
    L_0x0472:
        r9 = Lit29;
        goto L_0x0468;
    L_0x0475:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit40;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x0364;
    L_0x0481:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit12;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0593;
    L_0x048d:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x05a3;
    L_0x0491:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r9 = r9.apply1(r10);
        r10 = Lit45;
        r11 = java.lang.Boolean.FALSE;
        r9 = r12.lambda24integerConvert(r9, r10, r11);
        r7 = r2.lambda21out$St(r9);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x04a9:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x04b3:
        r9 = java.lang.Boolean.TRUE;
        goto L_0x037c;
    L_0x04b7:
        r10 = java.lang.Boolean.FALSE;
        if (r7 != r10) goto L_0x0390;
    L_0x04bb:
        r10 = r12.left$Mnadjust;
        r14 = java.lang.Boolean.FALSE;
        if (r10 == r14) goto L_0x04c5;
    L_0x04c1:
        r10 = r12.lambda$Fn14;
        goto L_0x0396;
    L_0x04c5:
        r10 = r12.pr;
        r10 = gnu.kawa.lispexpr.LangObjType.coerceRealNum(r10);	 Catch:{ ClassCastException -> 0x0904 }
        r10 = kawa.lib.numbers.isNegative(r10);
        if (r10 == 0) goto L_0x04d9;
    L_0x04d1:
        r10 = r12.width;
        r12.pr = r10;
        r10 = r12.lambda$Fn15;
        goto L_0x0396;
    L_0x04d9:
        r10 = r12.lambda$Fn16;
        goto L_0x0396;
    L_0x04dd:
        r9 = java.lang.Boolean.FALSE;
        if (r7 != r9) goto L_0x03ab;
    L_0x04e1:
        r9 = r12.left$Mnadjust;
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x0528;
    L_0x04e7:
        r9 = kawa.standard.Scheme.numGrt;
        r10 = r12.width;
        r11 = gnu.kawa.functions.AddOp.$Mn;
        r13 = r12.precision;
        r14 = r12.pr;
        r11 = r11.apply2(r13, r14);
        r9 = r9.apply2(r10, r11);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x03d8;
    L_0x04fd:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r9 = gnu.kawa.functions.AddOp.$Mn;
        r10 = r12.width;
        r14 = gnu.kawa.functions.AddOp.$Mn;
        r15 = r12.precision;
        r0 = r12.pr;
        r16 = r0;
        r14 = r14.apply2(r15, r16);
        r10 = r9.apply2(r10, r14);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0922 }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x0922 }
        r10 = Lit29;
        r9 = kawa.lib.strings.makeString(r9, r10);
        r11.apply2(r13, r9);
        goto L_0x03d8;
    L_0x0528:
        r9 = r12.os;
        r10 = java.lang.Boolean.FALSE;	 Catch:{ ClassCastException -> 0x092c }
        if (r9 == r10) goto L_0x0558;
    L_0x052e:
        r9 = 1;
    L_0x052f:
        r9 = r9 + 1;
        r7 = r9 & 1;
        if (r7 != 0) goto L_0x03d8;
    L_0x0535:
        r10 = kawa.standard.Scheme.numLEq;
        r11 = r12.width;
        r9 = r12.os;
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x0936 }
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = java.lang.Integer.valueOf(r9);
        r9 = r10.apply2(r11, r9);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x055a;
    L_0x054d:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = r12.os;
        r9.apply2(r10, r11);
        goto L_0x03d8;
    L_0x0558:
        r9 = 0;
        goto L_0x052f;
    L_0x055a:
        r11 = kawa.standard.Scheme.applyToArgs;
        r13 = r2.out;
        r10 = gnu.kawa.functions.AddOp.$Mn;
        r14 = r12.width;
        r9 = r12.os;
        r9 = (java.lang.CharSequence) r9;	 Catch:{ ClassCastException -> 0x0940 }
        r9 = kawa.lib.strings.stringLength(r9);
        r9 = java.lang.Integer.valueOf(r9);
        r10 = r10.apply2(r14, r9);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x094a }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x094a }
        r10 = Lit29;
        r9 = kawa.lib.strings.makeString(r9, r10);
        r7 = r11.apply2(r13, r9);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x03d8;
    L_0x0588:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = r12.os;
        r9.apply2(r10, r11);
        goto L_0x03d8;
    L_0x0593:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit41;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x05d5;
    L_0x059f:
        r9 = java.lang.Boolean.FALSE;
        if (r7 != r9) goto L_0x0491;
    L_0x05a3:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit46;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0619;
    L_0x05af:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0625;
    L_0x05b3:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r9 = r9.apply1(r10);
        r10 = Lit48;
        r11 = java.lang.Boolean.FALSE;
        r9 = r12.lambda24integerConvert(r9, r10, r11);
        r7 = r2.lambda21out$St(r9);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x05cb:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x05d5:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit3;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x05e7;
    L_0x05e1:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x05a3;
    L_0x05e5:
        goto L_0x0491;
    L_0x05e7:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit42;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x05f9;
    L_0x05f3:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x05a3;
    L_0x05f7:
        goto L_0x0491;
    L_0x05f9:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit43;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x060b;
    L_0x0605:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x05a3;
    L_0x0609:
        goto L_0x0491;
    L_0x060b:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit44;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x05a3;
    L_0x0617:
        goto L_0x0491;
    L_0x0619:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit47;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x05b3;
    L_0x0625:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit49;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x065a;
    L_0x0631:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r10 = r9.apply1(r10);
        r11 = Lit50;
        r9 = stdio$Clhex$Mnupper$Mncase$Qu;
        if (r9 == 0) goto L_0x0657;
    L_0x063f:
        r9 = kawa.lib.rnrs.unicode.string$Mndowncase;
    L_0x0641:
        r9 = r12.lambda24integerConvert(r10, r11, r9);
        r7 = r2.lambda21out$St(r9);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x064d:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x0657:
        r9 = java.lang.Boolean.FALSE;
        goto L_0x0641;
    L_0x065a:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit51;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x068f;
    L_0x0666:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r10 = r9.apply1(r10);
        r11 = Lit50;
        r9 = stdio$Clhex$Mnupper$Mncase$Qu;
        if (r9 == 0) goto L_0x068c;
    L_0x0674:
        r9 = java.lang.Boolean.FALSE;
    L_0x0676:
        r9 = r12.lambda24integerConvert(r10, r11, r9);
        r7 = r2.lambda21out$St(r9);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x0682:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x068c:
        r9 = kawa.lib.rnrs.unicode.string$Mnupcase;
        goto L_0x0676;
    L_0x068f:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit52;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x06c1;
    L_0x069b:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x06cd;
    L_0x069f:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r9 = r9.apply1(r10);
        r10 = Lit14;
        r11 = java.lang.Boolean.FALSE;
        r9 = r12.lambda24integerConvert(r9, r10, r11);
        r7 = r2.lambda21out$St(r9);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x06b7:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x06c1:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit53;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x069f;
    L_0x06cd:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit28;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x06eb;
    L_0x06d9:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = Lit28;
        r7 = r9.apply2(r10, r11);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x06e7:
        r1 = r12.args;
        goto L_0x003f;
    L_0x06eb:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit25;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x074e;
    L_0x06f7:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x075e;
    L_0x06fb:
        r9 = kawa.lib.lists.car;
        r10 = r12.args;
        r3 = r9.apply1(r10);
        r9 = r2.fc;
        r10 = new gnu.kawa.slib.printf$frame11;
        r10.<init>();
        r10.staticLink = r12;
        r10.fc = r9;
        r9 = r12.precision;
        r9 = gnu.kawa.lispexpr.LangObjType.coerceRealNum(r9);	 Catch:{ ClassCastException -> 0x0954 }
        r9 = kawa.lib.numbers.isNegative(r9);
        if (r9 == 0) goto L_0x07d1;
    L_0x071a:
        r9 = Lit59;
        r12.precision = r9;
    L_0x071e:
        r9 = kawa.lib.numbers.isNumber(r3);
        if (r9 == 0) goto L_0x07f0;
    L_0x0724:
        r3 = (java.lang.Number) r3;	 Catch:{ ClassCastException -> 0x0972 }
        r9 = kawa.lib.numbers.exact$To$Inexact(r3);
        r5 = kawa.lib.numbers.number$To$String(r9);
    L_0x072e:
        r9 = r10.format$Mnreal;
        r10.format$Mnreal = r9;
        r9 = r10.lambda$Fn17;
        r7 = stdio$ClParseFloat(r5, r9);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x080b;
    L_0x073c:
        r7 = r2.lambda21out$St(r7);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x0744:
        r9 = kawa.lib.lists.cdr;
        r10 = r12.args;
        r1 = r9.apply1(r10);
        goto L_0x003f;
    L_0x074e:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit26;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x076a;
    L_0x075a:
        r9 = java.lang.Boolean.FALSE;
        if (r7 != r9) goto L_0x06fb;
    L_0x075e:
        r9 = r2.lambda19isEndOfFormat();
        if (r9 == 0) goto L_0x0816;
    L_0x0764:
        r7 = r2.lambda20incomplete();
        goto L_0x0075;
    L_0x076a:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit13;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x077b;
    L_0x0776:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x075e;
    L_0x077a:
        goto L_0x06fb;
    L_0x077b:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit54;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x078d;
    L_0x0787:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x075e;
    L_0x078b:
        goto L_0x06fb;
    L_0x078d:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit55;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x079f;
    L_0x0799:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x075e;
    L_0x079d:
        goto L_0x06fb;
    L_0x079f:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit56;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x07b1;
    L_0x07ab:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x075e;
    L_0x07af:
        goto L_0x06fb;
    L_0x07b1:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit57;
        r7 = r9.apply2(r6, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x07c3;
    L_0x07bd:
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x075e;
    L_0x07c1:
        goto L_0x06fb;
    L_0x07c3:
        r9 = kawa.standard.Scheme.isEqv;
        r10 = Lit58;
        r9 = r9.apply2(r6, r10);
        r10 = java.lang.Boolean.FALSE;
        if (r9 == r10) goto L_0x075e;
    L_0x07cf:
        goto L_0x06fb;
    L_0x07d1:
        r9 = r12.precision;
        r9 = (java.lang.Number) r9;	 Catch:{ ClassCastException -> 0x095e }
        r7 = kawa.lib.numbers.isZero(r9);
        if (r7 == 0) goto L_0x07ed;
    L_0x07db:
        r9 = r10.fc;
        r9 = (gnu.text.Char) r9;	 Catch:{ ClassCastException -> 0x0968 }
        r11 = Lit55;
        r9 = kawa.lib.rnrs.unicode.isCharCi$Eq(r9, r11);
        if (r9 == 0) goto L_0x071e;
    L_0x07e7:
        r9 = Lit7;
        r12.precision = r9;
        goto L_0x071e;
    L_0x07ed:
        if (r7 == 0) goto L_0x071e;
    L_0x07ef:
        goto L_0x07e7;
    L_0x07f0:
        r9 = kawa.lib.strings.isString(r3);
        if (r9 == 0) goto L_0x07f9;
    L_0x07f6:
        r5 = r3;
        goto L_0x072e;
    L_0x07f9:
        r9 = kawa.lib.misc.isSymbol(r3);
        if (r9 == 0) goto L_0x0807;
    L_0x07ff:
        r3 = (gnu.mapping.Symbol) r3;	 Catch:{ ClassCastException -> 0x097c }
        r5 = kawa.lib.misc.symbol$To$String(r3);
        goto L_0x072e;
    L_0x0807:
        r5 = "???";
        goto L_0x072e;
    L_0x080b:
        r9 = "???";
        r10 = 0;
        r10 = new java.lang.Object[r10];
        r7 = r12.lambda23pad$V(r9, r10);
        goto L_0x073c;
    L_0x0816:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = Lit28;
        r7 = r9.apply2(r10, r11);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x0824:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = r2.fc;
        r7 = r9.apply2(r10, r11);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x0832:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = Lit65;
        r7 = r9.apply2(r10, r11);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x0840:
        r1 = r12.args;
        goto L_0x003f;
    L_0x0844:
        r9 = kawa.standard.Scheme.applyToArgs;
        r10 = r2.out;
        r11 = r2.fc;
        r7 = r9.apply2(r10, r11);
        r9 = java.lang.Boolean.FALSE;
        if (r7 == r9) goto L_0x0075;
    L_0x0852:
        r1 = r12.args;
        goto L_0x003f;
    L_0x0856:
        r7 = gnu.mapping.Values.empty;
        goto L_0x0075;
    L_0x085a:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-length";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0864:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-ref";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x086e:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-ref";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0878:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r12 = "string-ref";
        r13 = 2;
        r10.<init>(r9, r12, r13, r11);
        throw r10;
    L_0x0882:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "negative?";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x088c:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "char-downcase";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0896:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "symbol->string";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x08a0:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "negative?";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x08aa:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-length";
        r12 = 1;
        r10.<init>(r9, r11, r12, r4);
        throw r10;
    L_0x08b4:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "substring";
        r12 = 1;
        r10.<init>(r9, r11, r12, r4);
        throw r10;
    L_0x08be:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "substring";
        r13 = 3;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x08c8:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-length";
        r12 = 1;
        r10.<init>(r9, r11, r12, r4);
        throw r10;
    L_0x08d2:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-length";
        r12 = 1;
        r10.<init>(r9, r11, r12, r4);
        throw r10;
    L_0x08dc:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "make-string";
        r13 = 1;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x08e6:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "string-length";
        r12 = 1;
        r10.<init>(r9, r11, r12, r4);
        throw r10;
    L_0x08f0:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "make-string";
        r13 = 1;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x08fa:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "negative?";
        r13 = 1;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x0904:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "negative?";
        r13 = 1;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x090e:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "negative?";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0918:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "make-string";
        r13 = 1;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x0922:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "make-string";
        r13 = 1;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x092c:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "x";
        r13 = -2;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0936:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-length";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0940:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-length";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x094a:
        r9 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "make-string";
        r13 = 1;
        r11.<init>(r9, r12, r13, r10);
        throw r11;
    L_0x0954:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "negative?";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x095e:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "zero?";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0968:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "char-ci=?";
        r13 = 1;
        r11.<init>(r10, r12, r13, r9);
        throw r11;
    L_0x0972:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "exact->inexact";
        r12 = 1;
        r10.<init>(r9, r11, r12, r3);
        throw r10;
    L_0x097c:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "symbol->string";
        r12 = 1;
        r10.<init>(r9, r11, r12, r3);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.printf.stdio$ClIprintf$V(java.lang.Object, java.lang.Object, java.lang.Object[]):java.lang.Object");
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 24:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 25:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 26:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 27:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Object fprintf$V(Object port, Object format, Object[] argsArray) {
        frame12 gnu_kawa_slib_printf_frame12 = new frame12();
        gnu_kawa_slib_printf_frame12.port = port;
        LList args = LList.makeList(argsArray, 0);
        gnu_kawa_slib_printf_frame12.cnt = Lit1;
        Scheme.apply.apply4(stdio$Cliprintf, gnu_kawa_slib_printf_frame12.lambda$Fn18, format, args);
        return gnu_kawa_slib_printf_frame12.cnt;
    }

    public static Object printf$V(Object format, Object[] argsArray) {
        return Scheme.apply.apply4(fprintf, ports.current$Mnoutput$Mnport.apply0(), format, LList.makeList(argsArray, 0));
    }

    public static Object sprintf$V(Object str, Object format, Object[] argsArray) {
        Object obj;
        frame13 gnu_kawa_slib_printf_frame13 = new frame13();
        gnu_kawa_slib_printf_frame13.str = str;
        LList args = LList.makeList(argsArray, 0);
        gnu_kawa_slib_printf_frame13.cnt = Lit1;
        if (strings.isString(gnu_kawa_slib_printf_frame13.str)) {
            obj = gnu_kawa_slib_printf_frame13.str;
        } else if (numbers.isNumber(gnu_kawa_slib_printf_frame13.str)) {
            Object obj2 = gnu_kawa_slib_printf_frame13.str;
            try {
                obj = strings.makeString(((Number) obj2).intValue());
            } catch (ClassCastException e) {
                throw new WrongType(e, "make-string", 1, obj2);
            }
        } else if (gnu_kawa_slib_printf_frame13.str == Boolean.FALSE) {
            obj = strings.makeString(100);
        } else {
            obj = misc.error$V(Lit68, new Object[]{"first argument not understood", gnu_kawa_slib_printf_frame13.str});
        }
        gnu_kawa_slib_printf_frame13.f28s = obj;
        obj = gnu_kawa_slib_printf_frame13.f28s;
        try {
            gnu_kawa_slib_printf_frame13.end = Integer.valueOf(strings.stringLength((CharSequence) obj));
            Scheme.apply.apply4(stdio$Cliprintf, gnu_kawa_slib_printf_frame13.lambda$Fn19, format, args);
            if (strings.isString(gnu_kawa_slib_printf_frame13.str)) {
                return gnu_kawa_slib_printf_frame13.cnt;
            }
            if (Scheme.isEqv.apply2(gnu_kawa_slib_printf_frame13.end, gnu_kawa_slib_printf_frame13.cnt) != Boolean.FALSE) {
                return gnu_kawa_slib_printf_frame13.f28s;
            }
            obj = gnu_kawa_slib_printf_frame13.f28s;
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object obj3 = gnu_kawa_slib_printf_frame13.cnt;
                try {
                    return strings.substring(charSequence, 0, ((Number) obj3).intValue());
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 3, obj3);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj);
            }
        } catch (ClassCastException e32) {
            throw new WrongType(e32, "string-length", 1, obj);
        }
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        Object obj;
        Object obj2;
        int length;
        Object[] objArr2;
        switch (moduleMethod.selector) {
            case 24:
                obj = objArr[0];
                obj2 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return stdio$ClIprintf$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 25:
                obj = objArr[0];
                obj2 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return fprintf$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 26:
                obj = objArr[0];
                length = objArr.length - 1;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return printf$V(obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 27:
                obj = objArr[0];
                obj2 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return sprintf$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }
}

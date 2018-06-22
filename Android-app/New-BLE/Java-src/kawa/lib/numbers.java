package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.GenericProc;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Arithmetic;
import gnu.kawa.functions.DivideOp;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.MultiplyOp;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LispReader;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.BitOps;
import gnu.math.Complex;
import gnu.math.DComplex;
import gnu.math.DFloNum;
import gnu.math.Duration;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.math.Quantity;
import gnu.math.RatNum;
import gnu.math.RealNum;
import gnu.math.Unit;
import java.math.BigDecimal;
import java.math.BigInteger;
import kawa.standard.Scheme;

/* compiled from: numbers.scm */
public class numbers extends ModuleBody {
    public static final numbers $instance = new numbers();
    static final IntNum Lit0 = IntNum.make(0);
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("signum").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("inexact?").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("zero?").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("positive?").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("negative?").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("max").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("min").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("abs").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("div-and-mod").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("div0-and-mod0").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("gcd").readResolve());
    static final IntNum Lit2 = IntNum.make(1);
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("lcm").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("numerator").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("denominator").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("floor").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("ceiling").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("truncate").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("round").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("rationalize").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("exp").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("log").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("number?").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("sin").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("cos").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("tan").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("asin").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("acos").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("make-rectangular").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("make-polar").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("real-part").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("imag-part").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("magnitude").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("quantity?").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("angle").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("inexact").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("exact").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("exact->inexact").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("inexact->exact").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("logop").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("bitwise-bit-set?").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("bitwise-copy-bit").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("bitwise-copy-bit-field").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("bitwise-bit-field").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("complex?").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("bitwise-if").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("logtest").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("logcount").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("bitwise-bit-count").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("bitwise-length").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("bitwise-first-bit-set").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("bitwise-rotate-bit-field").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("bitwise-reverse-bit-field").readResolve());
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("number->string").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("string->number").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("real?").readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("quantity->number").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("quantity->unit").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("make-quantity").readResolve());
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("duration").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("rational?").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("integer?").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("exact?").readResolve());
    public static final ModuleMethod abs;
    public static final ModuleMethod acos;
    public static final ModuleMethod angle;
    public static final ModuleMethod asin;
    public static final GenericProc atan = null;
    public static final ModuleMethod bitwise$Mnbit$Mncount;
    public static final ModuleMethod bitwise$Mnbit$Mnfield;
    public static final ModuleMethod bitwise$Mnbit$Mnset$Qu;
    public static final ModuleMethod bitwise$Mncopy$Mnbit;
    public static final ModuleMethod bitwise$Mncopy$Mnbit$Mnfield;
    public static final ModuleMethod bitwise$Mnfirst$Mnbit$Mnset;
    public static final ModuleMethod bitwise$Mnif;
    public static final ModuleMethod bitwise$Mnlength;
    public static final ModuleMethod bitwise$Mnreverse$Mnbit$Mnfield;
    public static final ModuleMethod bitwise$Mnrotate$Mnbit$Mnfield;
    public static final ModuleMethod ceiling;
    public static final ModuleMethod complex$Qu;
    public static final ModuleMethod cos;
    public static final ModuleMethod denominator;
    public static final ModuleMethod div$Mnand$Mnmod;
    public static final ModuleMethod div0$Mnand$Mnmod0;
    public static final ModuleMethod duration;
    public static final ModuleMethod exact;
    public static final ModuleMethod exact$Mn$Grinexact;
    public static final ModuleMethod exact$Qu;
    public static final ModuleMethod exp;
    public static final ModuleMethod floor;
    public static final ModuleMethod gcd;
    public static final ModuleMethod imag$Mnpart;
    public static final ModuleMethod inexact;
    public static final ModuleMethod inexact$Mn$Grexact;
    public static final ModuleMethod inexact$Qu;
    public static final ModuleMethod integer$Qu;
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn2;
    static final ModuleMethod lambda$Fn3;
    static final ModuleMethod lambda$Fn4;
    public static final ModuleMethod lcm;
    public static final ModuleMethod log;
    public static final ModuleMethod logcount;
    public static final ModuleMethod logop;
    public static final ModuleMethod logtest;
    public static final ModuleMethod magnitude;
    public static final ModuleMethod make$Mnpolar;
    public static final ModuleMethod make$Mnquantity;
    public static final ModuleMethod make$Mnrectangular;
    public static final ModuleMethod max;
    public static final ModuleMethod min;
    public static final ModuleMethod negative$Qu;
    public static final ModuleMethod number$Mn$Grstring;
    public static final ModuleMethod number$Qu;
    public static final ModuleMethod numerator;
    public static final ModuleMethod positive$Qu;
    public static final ModuleMethod quantity$Mn$Grnumber;
    public static final ModuleMethod quantity$Mn$Grunit;
    public static final ModuleMethod quantity$Qu;
    public static final ModuleMethod rational$Qu;
    public static final ModuleMethod rationalize;
    public static final ModuleMethod real$Mnpart;
    public static final ModuleMethod real$Qu;
    public static final ModuleMethod round;
    public static final ModuleMethod sin;
    public static final GenericProc sqrt = null;
    public static final ModuleMethod string$Mn$Grnumber;
    public static final ModuleMethod tan;
    public static final ModuleMethod truncate;
    public static final ModuleMethod zero$Qu;

    static {
        ModuleBody moduleBody = $instance;
        number$Qu = new ModuleMethod(moduleBody, 1, Lit3, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        quantity$Qu = new ModuleMethod(moduleBody, 2, Lit4, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        complex$Qu = new ModuleMethod(moduleBody, 3, Lit5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        real$Qu = new ModuleMethod(moduleBody, 4, Lit6, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        rational$Qu = new ModuleMethod(moduleBody, 5, Lit7, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        integer$Qu = new ModuleMethod(moduleBody, 6, Lit8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        exact$Qu = new ModuleMethod(moduleBody, 7, Lit9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        inexact$Qu = new ModuleMethod(moduleBody, 8, Lit10, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        zero$Qu = new ModuleMethod(moduleBody, 9, Lit11, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        positive$Qu = new ModuleMethod(moduleBody, 10, Lit12, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        negative$Qu = new ModuleMethod(moduleBody, 11, Lit13, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        max = new ModuleMethod(moduleBody, 12, Lit14, -4096);
        min = new ModuleMethod(moduleBody, 13, Lit15, -4096);
        abs = new ModuleMethod(moduleBody, 14, Lit16, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        div$Mnand$Mnmod = new ModuleMethod(moduleBody, 15, Lit17, 8194);
        div0$Mnand$Mnmod0 = new ModuleMethod(moduleBody, 16, Lit18, 8194);
        gcd = new ModuleMethod(moduleBody, 17, Lit19, -4096);
        lcm = new ModuleMethod(moduleBody, 18, Lit20, -4096);
        numerator = new ModuleMethod(moduleBody, 19, Lit21, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        denominator = new ModuleMethod(moduleBody, 20, Lit22, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        floor = new ModuleMethod(moduleBody, 21, Lit23, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ceiling = new ModuleMethod(moduleBody, 22, Lit24, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        truncate = new ModuleMethod(moduleBody, 23, Lit25, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        round = new ModuleMethod(moduleBody, 24, Lit26, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        rationalize = new ModuleMethod(moduleBody, 25, Lit27, 8194);
        exp = new ModuleMethod(moduleBody, 26, Lit28, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        log = new ModuleMethod(moduleBody, 27, Lit29, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        sin = new ModuleMethod(moduleBody, 28, Lit30, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        cos = new ModuleMethod(moduleBody, 29, Lit31, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tan = new ModuleMethod(moduleBody, 30, Lit32, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        asin = new ModuleMethod(moduleBody, 31, Lit33, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        acos = new ModuleMethod(moduleBody, 32, Lit34, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 33, null, 8194);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/numbers.scm:146");
        lambda$Fn1 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 34, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/numbers.scm:148");
        lambda$Fn2 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 35, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/numbers.scm:152");
        lambda$Fn3 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 36, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/numbers.scm:156");
        lambda$Fn4 = moduleMethod;
        make$Mnrectangular = new ModuleMethod(moduleBody, 37, Lit35, 8194);
        make$Mnpolar = new ModuleMethod(moduleBody, 38, Lit36, 8194);
        real$Mnpart = new ModuleMethod(moduleBody, 39, Lit37, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        imag$Mnpart = new ModuleMethod(moduleBody, 40, Lit38, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        magnitude = new ModuleMethod(moduleBody, 41, Lit39, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        angle = new ModuleMethod(moduleBody, 42, Lit40, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        inexact = new ModuleMethod(moduleBody, 43, Lit41, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        exact = new ModuleMethod(moduleBody, 44, Lit42, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        exact$Mn$Grinexact = new ModuleMethod(moduleBody, 45, Lit43, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        inexact$Mn$Grexact = new ModuleMethod(moduleBody, 46, Lit44, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        logop = new ModuleMethod(moduleBody, 47, Lit45, 12291);
        bitwise$Mnbit$Mnset$Qu = new ModuleMethod(moduleBody, 48, Lit46, 8194);
        bitwise$Mncopy$Mnbit = new ModuleMethod(moduleBody, 49, Lit47, 12291);
        bitwise$Mncopy$Mnbit$Mnfield = new ModuleMethod(moduleBody, 50, Lit48, 16388);
        bitwise$Mnbit$Mnfield = new ModuleMethod(moduleBody, 51, Lit49, 12291);
        bitwise$Mnif = new ModuleMethod(moduleBody, 52, Lit50, 12291);
        logtest = new ModuleMethod(moduleBody, 53, Lit51, 8194);
        logcount = new ModuleMethod(moduleBody, 54, Lit52, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        bitwise$Mnbit$Mncount = new ModuleMethod(moduleBody, 55, Lit53, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        bitwise$Mnlength = new ModuleMethod(moduleBody, 56, Lit54, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        bitwise$Mnfirst$Mnbit$Mnset = new ModuleMethod(moduleBody, 57, Lit55, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        bitwise$Mnrotate$Mnbit$Mnfield = new ModuleMethod(moduleBody, 58, Lit56, 16388);
        bitwise$Mnreverse$Mnbit$Mnfield = new ModuleMethod(moduleBody, 59, Lit57, 12291);
        number$Mn$Grstring = new ModuleMethod(moduleBody, 60, Lit58, 8193);
        string$Mn$Grnumber = new ModuleMethod(moduleBody, 62, Lit59, 8193);
        quantity$Mn$Grnumber = new ModuleMethod(moduleBody, 64, Lit60, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        quantity$Mn$Grunit = new ModuleMethod(moduleBody, 65, Lit61, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnquantity = new ModuleMethod(moduleBody, 66, Lit62, 8194);
        duration = new ModuleMethod(moduleBody, 67, Lit63, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public numbers() {
        ModuleInfo.register(this);
    }

    public static CharSequence number$To$String(Number number) {
        return number$To$String(number, 10);
    }

    public static Object string$To$Number(CharSequence charSequence) {
        return string$To$Number(charSequence, 10);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        atan = new GenericProc("atan");
        atan.setProperties(new Object[]{lambda$Fn1, lambda$Fn2});
        sqrt = new GenericProc("sqrt");
        sqrt.setProperties(new Object[]{lambda$Fn3, lambda$Fn4});
    }

    public static boolean isNumber(Object x) {
        return x instanceof Number;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 6:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 7:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 8:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 9:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 11:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 14:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 19:
                if (RatNum.asRatNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 20:
                if (RatNum.asRatNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 21:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 22:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 23:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 24:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 26:
                if (!(obj instanceof Complex)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 27:
                if (!(obj instanceof Complex)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 30:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 31:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 32:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 34:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                if (!(obj instanceof Quantity)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 36:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 39:
                if (!(obj instanceof Complex)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 40:
                if (!(obj instanceof Complex)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 41:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 42:
                if (!(obj instanceof Complex)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 43:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 44:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 45:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 46:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 54:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 55:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 56:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 57:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 60:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 62:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 64:
                if (!(obj instanceof Quantity)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 65:
                if (!(obj instanceof Quantity)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 67:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static boolean isQuantity(Object x) {
        return x instanceof Quantity;
    }

    public static boolean isComplex(Object x) {
        return x instanceof Complex;
    }

    public static boolean isReal(Object x) {
        return RealNum.asRealNumOrNull(x) != null;
    }

    public static boolean isRational(Object x) {
        return RatNum.asRatNumOrNull(x) != null;
    }

    public static boolean isInteger(Object x) {
        boolean x2 = x instanceof IntNum;
        if (x2) {
            return x2;
        }
        x2 = x instanceof DFloNum;
        if (x2) {
            try {
                x2 = Math.IEEEremainder(((DFloNum) x).doubleValue(), 1.0d) == 0.0d;
            } catch (ClassCastException e) {
                throw new WrongType(e, "gnu.math.DFloNum.doubleValue()", 1, x);
            }
        }
        if (x2) {
            return x2;
        }
        x2 = x instanceof Number;
        if (!x2) {
            return x2;
        }
        x2 = x instanceof Long;
        if (x2) {
            return x2;
        }
        x2 = x instanceof Integer;
        if (x2) {
            return x2;
        }
        x2 = x instanceof Short;
        if (x2) {
            return x2;
        }
        return x instanceof BigInteger;
    }

    public static boolean isExact(Object x) {
        boolean x2 = x instanceof Number;
        return x2 ? Arithmetic.isExact((Number) x) : x2;
    }

    public static boolean isInexact(Object x) {
        boolean x2 = x instanceof Number;
        return x2 ? (Arithmetic.isExact((Number) x) + 1) & 1 : x2;
    }

    public static boolean isZero(Number x) {
        if (x instanceof Numeric) {
            return ((Numeric) x).isZero();
        }
        if (x instanceof BigInteger) {
            if (Scheme.numEqu.apply2(Lit0, GetNamedPart.getNamedPart.apply2((BigInteger) x, Lit1)) == Boolean.FALSE) {
                return false;
            }
            return true;
        } else if (x instanceof BigDecimal) {
            if (Scheme.numEqu.apply2(Lit0, GetNamedPart.getNamedPart.apply2((BigDecimal) x, Lit1)) == Boolean.FALSE) {
                return false;
            }
            return true;
        } else if (x.doubleValue() != 0.0d) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isPositive(RealNum x) {
        return x.sign() > 0;
    }

    public static boolean isNegative(RealNum x) {
        return x.isNegative();
    }

    public static Object max(Object... args) {
        int n = args.length;
        Object obj = args[0];
        try {
            RealNum result = LangObjType.coerceRealNum(obj);
            int i = 1;
            while (i < n) {
                obj = args[i];
                try {
                    result = result.max(LangObjType.coerceRealNum(obj));
                    i++;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.math.RealNum.max(real)", 2, obj);
                }
            }
            return result;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "result", -2, obj);
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 12:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 13:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 17:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 18:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Object min(Object... args) {
        int n = args.length;
        Object obj = args[0];
        try {
            RealNum result = LangObjType.coerceRealNum(obj);
            int i = 0;
            while (i < n) {
                obj = args[i];
                try {
                    result = result.min(LangObjType.coerceRealNum(obj));
                    i++;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "gnu.math.RealNum.min(real)", 2, obj);
                }
            }
            return result;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "result", -2, obj);
        }
    }

    public static Number abs(Number x) {
        if (x instanceof Numeric) {
            return ((Numeric) x).abs();
        }
        return Scheme.numGEq.apply2(x, Lit0) == Boolean.FALSE ? (Number) AddOp.$Mn.apply1(x) : x;
    }

    public static Object divAndMod(RealNum x, RealNum y) {
        Object apply2 = DivideOp.div.apply2(x, y);
        try {
            apply2 = AddOp.$Mn.apply2(x, MultiplyOp.$St.apply2(LangObjType.coerceRealNum(apply2), y));
            try {
                RealNum r = LangObjType.coerceRealNum(apply2);
                return misc.values(q, r);
            } catch (ClassCastException e) {
                throw new WrongType(e, "r", -2, apply2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "q", -2, apply2);
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 15:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (RealNum.asRealNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 16:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (RealNum.asRealNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 25:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (RealNum.asRealNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 33:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 37:
                if (RealNum.asRealNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (RealNum.asRealNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 38:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 48:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 53:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 60:
                if (!(obj instanceof Number)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 62:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 66:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object div0AndMod0(RealNum x, RealNum y) {
        Object apply2 = DivideOp.div0.apply2(x, y);
        try {
            apply2 = AddOp.$Mn.apply2(x, MultiplyOp.$St.apply2(LangObjType.coerceRealNum(apply2), y));
            try {
                RealNum r = LangObjType.coerceRealNum(apply2);
                return misc.values(q, r);
            } catch (ClassCastException e) {
                throw new WrongType(e, "r", -2, apply2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "q", -2, apply2);
        }
    }

    public static IntNum gcd(IntNum... args) {
        int n = args.length;
        if (n == 0) {
            return Lit0;
        }
        IntNum result = args[0];
        for (int i = 1; i < n; i++) {
            result = IntNum.gcd(result, args[i]);
        }
        return result;
    }

    public static IntNum lcm(IntNum... args) {
        int n = args.length;
        if (n == 0) {
            return Lit2;
        }
        IntNum result = IntNum.abs(args[0]);
        for (int i = 1; i < n; i++) {
            result = IntNum.lcm(result, args[i]);
        }
        return result;
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        int length;
        IntNum[] intNumArr;
        Object obj;
        switch (moduleMethod.selector) {
            case 12:
                return max(objArr);
            case 13:
                return min(objArr);
            case 17:
                length = objArr.length;
                intNumArr = new IntNum[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return gcd(intNumArr);
                    }
                    obj = objArr[length];
                    try {
                        intNumArr[length] = LangObjType.coerceIntNum(obj);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "gcd", 0, obj);
                    }
                }
            case 18:
                length = objArr.length;
                intNumArr = new IntNum[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return lcm(intNumArr);
                    }
                    obj = objArr[length];
                    try {
                        intNumArr[length] = LangObjType.coerceIntNum(obj);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "lcm", 0, obj);
                    }
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static IntNum numerator(RatNum x) {
        return x.numerator();
    }

    public static IntNum denominator(RatNum x) {
        return x.denominator();
    }

    public static RealNum floor(RealNum x) {
        return x.toInt(Numeric.FLOOR);
    }

    public static RealNum ceiling(RealNum x) {
        return x.toInt(Numeric.CEILING);
    }

    public static RealNum truncate(RealNum x) {
        return x.toInt(Numeric.TRUNCATE);
    }

    public static RealNum round(RealNum x) {
        return x.toInt(Numeric.ROUND);
    }

    public static RealNum rationalize(RealNum x, RealNum y) {
        return RatNum.rationalize(LangObjType.coerceRealNum(x.sub(y)), LangObjType.coerceRealNum(x.add(y)));
    }

    public static Complex exp(Complex x) {
        return x.exp();
    }

    public static Complex log(Complex x) {
        return x.log();
    }

    public static double sin(double x) {
        return Math.sin(x);
    }

    public static double cos(double x) {
        return Math.cos(x);
    }

    public static double tan(double x) {
        return Math.tan(x);
    }

    public static double asin(double x) {
        return Math.asin(x);
    }

    public static double acos(double x) {
        return Math.acos(x);
    }

    static double lambda1(double y, double x) {
        return Math.atan2(y, x);
    }

    static double lambda2(double x) {
        return Math.atan(x);
    }

    static Quantity lambda3(Quantity num) {
        return Quantity.make(num.number().sqrt(), num.unit().sqrt());
    }

    static double lambda4(double x) {
        return Math.sqrt(x);
    }

    public static Complex makeRectangular(RealNum x, RealNum y) {
        return Complex.make(x, y);
    }

    public static DComplex makePolar(double x, double y) {
        return Complex.polar(x, y);
    }

    public static RealNum realPart(Complex x) {
        return x.re();
    }

    public static RealNum imagPart(Complex x) {
        return x.im();
    }

    public static Number magnitude(Number x) {
        return abs(x);
    }

    public static RealNum angle(Complex x) {
        return x.angle();
    }

    public static Number inexact(Number num) {
        return Arithmetic.toInexact(num);
    }

    public static Number exact(Number num) {
        return Arithmetic.toExact(num);
    }

    public static Number exact$To$Inexact(Number num) {
        return Arithmetic.toInexact(num);
    }

    public static Number inexact$To$Exact(Number num) {
        return Arithmetic.toExact(num);
    }

    public static IntNum logop(int op, IntNum i, IntNum j) {
        return BitOps.bitOp(op, i, j);
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 47:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                if (IntNum.asIntNumOrNull(obj3) == null) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 49:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 51:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 52:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                if (IntNum.asIntNumOrNull(obj3) == null) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 59:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
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

    public static boolean isBitwiseBitSet(IntNum i, int bitno) {
        return BitOps.bitValue(i, bitno);
    }

    public static IntNum bitwiseCopyBit(IntNum i, int bitno, int new$Mnvalue) {
        return BitOps.setBitValue(i, bitno, new$Mnvalue);
    }

    public static IntNum bitwiseCopyBitField(IntNum to, int start, int end, IntNum from) {
        int mask1 = IntNum.shift(-1, start);
        return bitwiseIf(BitOps.and(IntNum.make(mask1), BitOps.not(IntNum.make(IntNum.shift(-1, end)))), IntNum.shift(from, start), to);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 50:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                if (IntNum.asIntNumOrNull(obj4) == null) {
                    return -786428;
                }
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 58:
                if (IntNum.asIntNumOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            default:
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
    }

    public static IntNum bitwiseBitField(IntNum i, int start, int end) {
        return BitOps.extract(i, start, end);
    }

    public static IntNum bitwiseIf(IntNum e1, IntNum e2, IntNum e3) {
        return BitOps.ior(BitOps.and(e1, e2), BitOps.and(BitOps.not(e1), e3));
    }

    public static boolean logtest(IntNum i, IntNum j) {
        return BitOps.test(i, j);
    }

    public static int logcount(IntNum i) {
        if (IntNum.compare(i, 0) < 0) {
            i = BitOps.not(i);
        }
        return BitOps.bitCount(i);
    }

    public static int bitwiseBitCount(IntNum i) {
        if (IntNum.compare(i, 0) >= 0) {
            return BitOps.bitCount(i);
        }
        return -1 - BitOps.bitCount(BitOps.not(i));
    }

    public static int bitwiseLength(IntNum i) {
        return i.intLength();
    }

    public static int bitwiseFirstBitSet(IntNum i) {
        return BitOps.lowestBitSet(i);
    }

    public static IntNum bitwiseRotateBitField(IntNum n, int start, int end, int count) {
        int width = end - start;
        if (width <= 0) {
            return n;
        }
        int r = count % width;
        if (r < 0) {
            count = r + width;
        } else {
            count = r;
        }
        IntNum field0 = bitwiseBitField(n, start, end);
        return bitwiseCopyBitField(n, start, end, BitOps.ior(IntNum.shift(field0, count), IntNum.shift(field0, count - width)));
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        switch (moduleMethod.selector) {
            case 50:
                try {
                    try {
                        try {
                            try {
                                return bitwiseCopyBitField(LangObjType.coerceIntNum(obj), ((Number) obj2).intValue(), ((Number) obj3).intValue(), LangObjType.coerceIntNum(obj4));
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "bitwise-copy-bit-field", 4, obj4);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "bitwise-copy-bit-field", 3, obj3);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "bitwise-copy-bit-field", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "bitwise-copy-bit-field", 1, obj);
                }
            case 58:
                try {
                    try {
                        try {
                            try {
                                return bitwiseRotateBitField(LangObjType.coerceIntNum(obj), ((Number) obj2).intValue(), ((Number) obj3).intValue(), ((Number) obj4).intValue());
                            } catch (ClassCastException e2222) {
                                throw new WrongType(e2222, "bitwise-rotate-bit-field", 4, obj4);
                            }
                        } catch (ClassCastException e22222) {
                            throw new WrongType(e22222, "bitwise-rotate-bit-field", 3, obj3);
                        }
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "bitwise-rotate-bit-field", 2, obj2);
                    }
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "bitwise-rotate-bit-field", 1, obj);
                }
            default:
                return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
    }

    public static IntNum bitwiseReverseBitField(IntNum n, int start, int end) {
        return BitOps.reverseBits(n, start, end);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 47:
                try {
                    try {
                        try {
                            return logop(((Number) obj).intValue(), LangObjType.coerceIntNum(obj2), LangObjType.coerceIntNum(obj3));
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "logop", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "logop", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "logop", 1, obj);
                }
            case 49:
                try {
                    try {
                        try {
                            return bitwiseCopyBit(LangObjType.coerceIntNum(obj), ((Number) obj2).intValue(), ((Number) obj3).intValue());
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "bitwise-copy-bit", 3, obj3);
                        }
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "bitwise-copy-bit", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "bitwise-copy-bit", 1, obj);
                }
            case 51:
                try {
                    try {
                        try {
                            return bitwiseBitField(LangObjType.coerceIntNum(obj), ((Number) obj2).intValue(), ((Number) obj3).intValue());
                        } catch (ClassCastException e222222) {
                            throw new WrongType(e222222, "bitwise-bit-field", 3, obj3);
                        }
                    } catch (ClassCastException e2222222) {
                        throw new WrongType(e2222222, "bitwise-bit-field", 2, obj2);
                    }
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "bitwise-bit-field", 1, obj);
                }
            case 52:
                try {
                    try {
                        try {
                            return bitwiseIf(LangObjType.coerceIntNum(obj), LangObjType.coerceIntNum(obj2), LangObjType.coerceIntNum(obj3));
                        } catch (ClassCastException e222222222) {
                            throw new WrongType(e222222222, "bitwise-if", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222) {
                        throw new WrongType(e2222222222, "bitwise-if", 2, obj2);
                    }
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "bitwise-if", 1, obj);
                }
            case 59:
                try {
                    try {
                        try {
                            return bitwiseReverseBitField(LangObjType.coerceIntNum(obj), ((Number) obj2).intValue(), ((Number) obj3).intValue());
                        } catch (ClassCastException e222222222222) {
                            throw new WrongType(e222222222222, "bitwise-reverse-bit-field", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222222) {
                        throw new WrongType(e2222222222222, "bitwise-reverse-bit-field", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "bitwise-reverse-bit-field", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static CharSequence number$To$String(Number arg, int radix) {
        return new FString(Arithmetic.toString(arg, radix));
    }

    public static Object string$To$Number(CharSequence str, int radix) {
        Object result = LispReader.parseNumber(str, radix);
        return result instanceof Numeric ? result : Boolean.FALSE;
    }

    public static Complex quantity$To$Number(Quantity q) {
        q.unit();
        if (q.doubleValue() == 1.0d) {
            return q.number();
        }
        return Complex.make(q.reValue(), q.imValue());
    }

    public static Unit quantity$To$Unit(Quantity q) {
        return q.unit();
    }

    public static Quantity makeQuantity(Object val, Object unit) {
        if (unit instanceof Unit) {
            try {
                Unit u = (Unit) unit;
            } catch (ClassCastException e) {
                throw new WrongType(e, "u", -2, unit);
            }
        }
        u = Unit.lookup(unit == null ? null : unit.toString());
        if (u == null) {
            throw new IllegalArgumentException(Format.formatToString(0, "unknown unit: ~s", unit).toString());
        }
        try {
            return Quantity.make((Complex) val, u);
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "gnu.math.Quantity.make(gnu.math.Complex,gnu.math.Unit)", 1, val);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 15:
                try {
                    try {
                        return divAndMod(LangObjType.coerceRealNum(obj), LangObjType.coerceRealNum(obj2));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "div-and-mod", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "div-and-mod", 1, obj);
                }
            case 16:
                try {
                    try {
                        return div0AndMod0(LangObjType.coerceRealNum(obj), LangObjType.coerceRealNum(obj2));
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "div0-and-mod0", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "div0-and-mod0", 1, obj);
                }
            case 25:
                try {
                    try {
                        return rationalize(LangObjType.coerceRealNum(obj), LangObjType.coerceRealNum(obj2));
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "rationalize", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "rationalize", 1, obj);
                }
            case 33:
                try {
                    try {
                        return Double.valueOf(lambda1(((Number) obj).doubleValue(), ((Number) obj2).doubleValue()));
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "lambda", 2, obj2);
                    }
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "lambda", 1, obj);
                }
            case 37:
                try {
                    try {
                        return makeRectangular(LangObjType.coerceRealNum(obj), LangObjType.coerceRealNum(obj2));
                    } catch (ClassCastException e22222222) {
                        throw new WrongType(e22222222, "make-rectangular", 2, obj2);
                    }
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "make-rectangular", 1, obj);
                }
            case 38:
                try {
                    try {
                        return makePolar(((Number) obj).doubleValue(), ((Number) obj2).doubleValue());
                    } catch (ClassCastException e2222222222) {
                        throw new WrongType(e2222222222, "make-polar", 2, obj2);
                    }
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "make-polar", 1, obj);
                }
            case 48:
                try {
                    try {
                        return isBitwiseBitSet(LangObjType.coerceIntNum(obj), ((Number) obj2).intValue()) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e222222222222) {
                        throw new WrongType(e222222222222, "bitwise-bit-set?", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "bitwise-bit-set?", 1, obj);
                }
            case 53:
                try {
                    try {
                        return logtest(LangObjType.coerceIntNum(obj), LangObjType.coerceIntNum(obj2)) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e22222222222222) {
                        throw new WrongType(e22222222222222, "logtest", 2, obj2);
                    }
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "logtest", 1, obj);
                }
            case 60:
                try {
                    try {
                        return number$To$String((Number) obj, ((Number) obj2).intValue());
                    } catch (ClassCastException e2222222222222222) {
                        throw new WrongType(e2222222222222222, "number->string", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "number->string", 1, obj);
                }
            case 62:
                try {
                    try {
                        return string$To$Number((CharSequence) obj, ((Number) obj2).intValue());
                    } catch (ClassCastException e222222222222222222) {
                        throw new WrongType(e222222222222222222, "string->number", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222222222) {
                    throw new WrongType(e2222222222222222222, "string->number", 1, obj);
                }
            case 66:
                return makeQuantity(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Duration duration(Object duration) {
        return Duration.parseDuration(duration == null ? null : duration.toString());
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return isNumber(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 2:
                return isQuantity(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 3:
                return isComplex(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 4:
                return isReal(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 5:
                return isRational(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 6:
                return isInteger(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 7:
                return isExact(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 8:
                return isInexact(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 9:
                try {
                    return isZero((Number) obj) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "zero?", 1, obj);
                }
            case 10:
                try {
                    return isPositive(LangObjType.coerceRealNum(obj)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "positive?", 1, obj);
                }
            case 11:
                try {
                    return isNegative(LangObjType.coerceRealNum(obj)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "negative?", 1, obj);
                }
            case 14:
                try {
                    return abs((Number) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "abs", 1, obj);
                }
            case 19:
                try {
                    return numerator(LangObjType.coerceRatNum(obj));
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "numerator", 1, obj);
                }
            case 20:
                try {
                    return denominator(LangObjType.coerceRatNum(obj));
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "denominator", 1, obj);
                }
            case 21:
                try {
                    return floor(LangObjType.coerceRealNum(obj));
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "floor", 1, obj);
                }
            case 22:
                try {
                    return ceiling(LangObjType.coerceRealNum(obj));
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "ceiling", 1, obj);
                }
            case 23:
                try {
                    return truncate(LangObjType.coerceRealNum(obj));
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "truncate", 1, obj);
                }
            case 24:
                try {
                    return round(LangObjType.coerceRealNum(obj));
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "round", 1, obj);
                }
            case 26:
                try {
                    return exp((Complex) obj);
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "exp", 1, obj);
                }
            case 27:
                try {
                    return log((Complex) obj);
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "log", 1, obj);
                }
            case 28:
                try {
                    return Double.valueOf(sin(((Number) obj).doubleValue()));
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "sin", 1, obj);
                }
            case 29:
                try {
                    return Double.valueOf(cos(((Number) obj).doubleValue()));
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "cos", 1, obj);
                }
            case 30:
                try {
                    return Double.valueOf(tan(((Number) obj).doubleValue()));
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "tan", 1, obj);
                }
            case 31:
                try {
                    return Double.valueOf(asin(((Number) obj).doubleValue()));
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "asin", 1, obj);
                }
            case 32:
                try {
                    return Double.valueOf(acos(((Number) obj).doubleValue()));
                } catch (ClassCastException e2222222222222222) {
                    throw new WrongType(e2222222222222222, "acos", 1, obj);
                }
            case 34:
                try {
                    return Double.valueOf(lambda2(((Number) obj).doubleValue()));
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "lambda", 1, obj);
                }
            case 35:
                try {
                    return lambda3((Quantity) obj);
                } catch (ClassCastException e222222222222222222) {
                    throw new WrongType(e222222222222222222, "lambda", 1, obj);
                }
            case 36:
                try {
                    return Double.valueOf(lambda4(((Number) obj).doubleValue()));
                } catch (ClassCastException e2222222222222222222) {
                    throw new WrongType(e2222222222222222222, "lambda", 1, obj);
                }
            case 39:
                try {
                    return realPart((Complex) obj);
                } catch (ClassCastException e22222222222222222222) {
                    throw new WrongType(e22222222222222222222, "real-part", 1, obj);
                }
            case 40:
                try {
                    return imagPart((Complex) obj);
                } catch (ClassCastException e222222222222222222222) {
                    throw new WrongType(e222222222222222222222, "imag-part", 1, obj);
                }
            case 41:
                try {
                    return magnitude((Number) obj);
                } catch (ClassCastException e2222222222222222222222) {
                    throw new WrongType(e2222222222222222222222, "magnitude", 1, obj);
                }
            case 42:
                try {
                    return angle((Complex) obj);
                } catch (ClassCastException e22222222222222222222222) {
                    throw new WrongType(e22222222222222222222222, "angle", 1, obj);
                }
            case 43:
                try {
                    return inexact((Number) obj);
                } catch (ClassCastException e222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222, "inexact", 1, obj);
                }
            case 44:
                try {
                    return exact((Number) obj);
                } catch (ClassCastException e2222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222, "exact", 1, obj);
                }
            case 45:
                try {
                    return exact$To$Inexact((Number) obj);
                } catch (ClassCastException e22222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222, "exact->inexact", 1, obj);
                }
            case 46:
                try {
                    return inexact$To$Exact((Number) obj);
                } catch (ClassCastException e222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222, "inexact->exact", 1, obj);
                }
            case 54:
                try {
                    return Integer.valueOf(logcount(LangObjType.coerceIntNum(obj)));
                } catch (ClassCastException e2222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222, "logcount", 1, obj);
                }
            case 55:
                try {
                    return Integer.valueOf(bitwiseBitCount(LangObjType.coerceIntNum(obj)));
                } catch (ClassCastException e22222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222, "bitwise-bit-count", 1, obj);
                }
            case 56:
                try {
                    return Integer.valueOf(bitwiseLength(LangObjType.coerceIntNum(obj)));
                } catch (ClassCastException e222222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222222, "bitwise-length", 1, obj);
                }
            case 57:
                try {
                    return Integer.valueOf(bitwiseFirstBitSet(LangObjType.coerceIntNum(obj)));
                } catch (ClassCastException e2222222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222222, "bitwise-first-bit-set", 1, obj);
                }
            case 60:
                try {
                    return number$To$String((Number) obj);
                } catch (ClassCastException e22222222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222222, "number->string", 1, obj);
                }
            case 62:
                try {
                    return string$To$Number((CharSequence) obj);
                } catch (ClassCastException e222222222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222222222, "string->number", 1, obj);
                }
            case 64:
                try {
                    return quantity$To$Number((Quantity) obj);
                } catch (ClassCastException e2222222222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222222222, "quantity->number", 1, obj);
                }
            case 65:
                try {
                    return quantity$To$Unit((Quantity) obj);
                } catch (ClassCastException e22222222222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222222222, "quantity->unit", 1, obj);
                }
            case 67:
                return duration(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}

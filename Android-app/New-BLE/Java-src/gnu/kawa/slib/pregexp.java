package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.characters;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.ports;
import kawa.lib.rnrs.unicode;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: pregexp.scm */
public class pregexp extends ModuleBody {
    public static Char $Stpregexp$Mncomment$Mnchar$St;
    public static Object $Stpregexp$Mnnul$Mnchar$Mnint$St;
    public static Object $Stpregexp$Mnreturn$Mnchar$St;
    public static Object $Stpregexp$Mnspace$Mnsensitive$Qu$St;
    public static Object $Stpregexp$Mntab$Mnchar$St;
    public static IntNum $Stpregexp$Mnversion$St;
    public static final pregexp $instance = new pregexp();
    static final IntNum Lit0 = IntNum.make(20050502);
    static final Char Lit1 = Char.make(59);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol(":bos").readResolve());
    static final SimpleSymbol Lit100 = ((SimpleSymbol) new SimpleSymbol(":sub").readResolve());
    static final SimpleSymbol Lit101 = ((SimpleSymbol) new SimpleSymbol("pregexp-match-positions-aux").readResolve());
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("non-existent-backref").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol(":lookahead").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol(":neg-lookahead").readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol(":lookbehind").readResolve());
    static final PairWithPosition Lit106 = PairWithPosition.make(Lit68, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit73, PairWithPosition.make(Boolean.FALSE, PairWithPosition.make(Lit14, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302017), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302014), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302012), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2302009), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2301999);
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol(":neg-lookbehind").readResolve());
    static final PairWithPosition Lit108;
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol(":no-backtrack").readResolve());
    static final Char Lit11;
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("greedy-quantifier-operand-could-be-empty").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("fk").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("identity").readResolve());
    static final Char Lit113 = Char.make(38);
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("pregexp-match-positions").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("pattern-must-be-compiled-or-string-regexp").readResolve());
    static final PairWithPosition Lit116;
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("pregexp-reverse!").readResolve());
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("pregexp-error").readResolve());
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-pattern").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol(":eos").readResolve());
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-branch").readResolve());
    static final SimpleSymbol Lit121 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-escaped-number").readResolve());
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-escaped-char").readResolve());
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("pregexp-invert-char-list").readResolve());
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("pregexp-string-match").readResolve());
    static final SimpleSymbol Lit125 = ((SimpleSymbol) new SimpleSymbol("pregexp-char-word?").readResolve());
    static final SimpleSymbol Lit126 = ((SimpleSymbol) new SimpleSymbol("pregexp-at-word-boundary?").readResolve());
    static final SimpleSymbol Lit127 = ((SimpleSymbol) new SimpleSymbol("pregexp-list-ref").readResolve());
    static final SimpleSymbol Lit128 = ((SimpleSymbol) new SimpleSymbol("pregexp-make-backref-list").readResolve());
    static final SimpleSymbol Lit129 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace-aux").readResolve());
    static final Char Lit13;
    static final SimpleSymbol Lit130 = ((SimpleSymbol) new SimpleSymbol("pregexp").readResolve());
    static final SimpleSymbol Lit131 = ((SimpleSymbol) new SimpleSymbol("pregexp-match").readResolve());
    static final SimpleSymbol Lit132 = ((SimpleSymbol) new SimpleSymbol("pregexp-split").readResolve());
    static final SimpleSymbol Lit133 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace").readResolve());
    static final SimpleSymbol Lit134 = ((SimpleSymbol) new SimpleSymbol("pregexp-replace*").readResolve());
    static final SimpleSymbol Lit135 = ((SimpleSymbol) new SimpleSymbol("pregexp-quote").readResolve());
    static final SimpleSymbol Lit14;
    static final Char Lit15;
    static final IntNum Lit16 = IntNum.make(2);
    static final SimpleSymbol Lit17;
    static final Char Lit18;
    static final Char Lit19;
    static final Char Lit2 = Char.make(97);
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol(":backref").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-piece").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("backslash").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol(":empty").readResolve());
    static final Char Lit24 = Char.make(10);
    static final Char Lit25 = Char.make(98);
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol(":wbdry").readResolve());
    static final Char Lit27 = Char.make(66);
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol(":not-wbdry").readResolve());
    static final Char Lit29 = Char.make(100);
    static final Char Lit3 = Char.make(32);
    static final SimpleSymbol Lit30;
    static final Char Lit31 = Char.make(68);
    static final PairWithPosition Lit32;
    static final Char Lit33 = Char.make(110);
    static final Char Lit34 = Char.make(114);
    static final Char Lit35 = Char.make(115);
    static final SimpleSymbol Lit36;
    static final Char Lit37 = Char.make(83);
    static final PairWithPosition Lit38;
    static final Char Lit39 = Char.make(116);
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol(":or").readResolve());
    static final Char Lit40 = Char.make(119);
    static final SimpleSymbol Lit41;
    static final Char Lit42 = Char.make(87);
    static final PairWithPosition Lit43;
    static final Char Lit44 = Char.make(58);
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-posix-char-class").readResolve());
    static final Char Lit46;
    static final Char Lit47;
    static final Char Lit48 = Char.make(61);
    static final PairWithPosition Lit49 = PairWithPosition.make(Lit103, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 851996);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol(":seq").readResolve());
    static final Char Lit50 = Char.make(33);
    static final PairWithPosition Lit51 = PairWithPosition.make(Lit104, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 856092);
    static final Char Lit52 = Char.make(62);
    static final PairWithPosition Lit53 = PairWithPosition.make(Lit109, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 860188);
    static final Char Lit54 = Char.make(60);
    static final PairWithPosition Lit55 = PairWithPosition.make(Lit105, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 872479);
    static final PairWithPosition Lit56 = PairWithPosition.make(Lit107, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 876575);
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-cluster-type").readResolve());
    static final Char Lit58 = Char.make(45);
    static final Char Lit59 = Char.make(105);
    static final Char Lit6;
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol(":case-sensitive").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol(":case-insensitive").readResolve());
    static final Char Lit62 = Char.make(120);
    static final PairWithPosition Lit63 = PairWithPosition.make(Lit100, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 942102);
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-subpattern").readResolve());
    static final Char Lit65;
    static final Char Lit66;
    static final Char Lit67;
    static final SimpleSymbol Lit68;
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("minimal?").readResolve());
    static final Char Lit7;
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("at-least").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("at-most").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("next-i").readResolve());
    static final IntNum Lit73;
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("pregexp-wrap-quantifier-if-any").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("left-brace-must-be-followed-by-number").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-nums").readResolve());
    static final Char Lit77 = Char.make(44);
    static final Char Lit78;
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol(":none-of-chars").readResolve());
    static final IntNum Lit8 = IntNum.make(1);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("pregexp-read-char-list").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("character-class-ended-too-soon").readResolve());
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol(":one-of-chars").readResolve());
    static final SimpleSymbol Lit83 = ((SimpleSymbol) new SimpleSymbol(":char-range").readResolve());
    static final Char Lit84 = Char.make(95);
    static final SimpleSymbol Lit85 = ((SimpleSymbol) new SimpleSymbol(":alnum").readResolve());
    static final SimpleSymbol Lit86 = ((SimpleSymbol) new SimpleSymbol(":alpha").readResolve());
    static final SimpleSymbol Lit87 = ((SimpleSymbol) new SimpleSymbol(":ascii").readResolve());
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol(":blank").readResolve());
    static final SimpleSymbol Lit89 = ((SimpleSymbol) new SimpleSymbol(":cntrl").readResolve());
    static final Char Lit9;
    static final SimpleSymbol Lit90 = ((SimpleSymbol) new SimpleSymbol(":graph").readResolve());
    static final SimpleSymbol Lit91 = ((SimpleSymbol) new SimpleSymbol(":lower").readResolve());
    static final SimpleSymbol Lit92 = ((SimpleSymbol) new SimpleSymbol(":print").readResolve());
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol(":punct").readResolve());
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol(":upper").readResolve());
    static final SimpleSymbol Lit95 = ((SimpleSymbol) new SimpleSymbol(":xdigit").readResolve());
    static final Char Lit96 = Char.make(99);
    static final Char Lit97 = Char.make(101);
    static final Char Lit98 = Char.make(102);
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("pregexp-check-if-in-char-class?").readResolve());
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn10;
    static final ModuleMethod lambda$Fn6;
    static final ModuleMethod lambda$Fn7;
    static final ModuleMethod lambda$Fn8;
    static final ModuleMethod lambda$Fn9;
    public static final ModuleMethod pregexp;
    public static final ModuleMethod pregexp$Mnat$Mnword$Mnboundary$Qu;
    public static final ModuleMethod pregexp$Mnchar$Mnword$Qu;
    public static final ModuleMethod pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu;
    public static final ModuleMethod pregexp$Mnerror;
    public static final ModuleMethod pregexp$Mninvert$Mnchar$Mnlist;
    public static final ModuleMethod pregexp$Mnlist$Mnref;
    public static final ModuleMethod pregexp$Mnmake$Mnbackref$Mnlist;
    public static final ModuleMethod pregexp$Mnmatch;
    public static final ModuleMethod pregexp$Mnmatch$Mnpositions;
    public static final ModuleMethod pregexp$Mnmatch$Mnpositions$Mnaux;
    public static final ModuleMethod pregexp$Mnquote;
    public static final ModuleMethod pregexp$Mnread$Mnbranch;
    public static final ModuleMethod pregexp$Mnread$Mnchar$Mnlist;
    public static final ModuleMethod pregexp$Mnread$Mncluster$Mntype;
    public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnchar;
    public static final ModuleMethod pregexp$Mnread$Mnescaped$Mnnumber;
    public static final ModuleMethod pregexp$Mnread$Mnnums;
    public static final ModuleMethod pregexp$Mnread$Mnpattern;
    public static final ModuleMethod pregexp$Mnread$Mnpiece;
    public static final ModuleMethod pregexp$Mnread$Mnposix$Mnchar$Mnclass;
    public static final ModuleMethod pregexp$Mnread$Mnsubpattern;
    public static final ModuleMethod pregexp$Mnreplace;
    public static final ModuleMethod pregexp$Mnreplace$Mnaux;
    public static final ModuleMethod pregexp$Mnreplace$St;
    public static final ModuleMethod pregexp$Mnreverse$Ex;
    public static final ModuleMethod pregexp$Mnsplit;
    public static final ModuleMethod pregexp$Mnstring$Mnmatch;
    public static final ModuleMethod pregexp$Mnwrap$Mnquantifier$Mnif$Mnany;

    /* compiled from: pregexp.scm */
    public class frame0 extends ModuleBody {
        boolean could$Mnloop$Mninfinitely$Qu;
        Object fk;
        Object f18i;
        final ModuleMethod lambda$Fn11;
        final ModuleMethod lambda$Fn12;
        final ModuleMethod lambda$Fn2;
        final ModuleMethod lambda$Fn3;
        final ModuleMethod lambda$Fn4;
        final ModuleMethod lambda$Fn5;
        boolean maximal$Qu;
        Object old;
        Object f19p;
        Object f20q;
        Object re;
        Object re$1;
        Object sk;
        frame staticLink;

        public frame0() {
            PropertySet moduleMethod = new ModuleMethod(this, 9, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:513");
            this.lambda$Fn2 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 10, null, 0);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:514");
            this.lambda$Fn3 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:541");
            this.lambda$Fn4 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 12, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:545");
            this.lambda$Fn5 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 13, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:587");
            this.lambda$Fn11 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 14, null, 0);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:590");
            this.lambda$Fn12 = moduleMethod;
        }

        public Object lambda5loupOneOfChars(Object chars) {
            frame1 gnu_kawa_slib_pregexp_frame1 = new frame1();
            gnu_kawa_slib_pregexp_frame1.staticLink = this;
            gnu_kawa_slib_pregexp_frame1.chars = chars;
            if (lists.isNull(gnu_kawa_slib_pregexp_frame1.chars)) {
                return Scheme.applyToArgs.apply1(this.fk);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(gnu_kawa_slib_pregexp_frame1.chars), this.f18i, this.sk, gnu_kawa_slib_pregexp_frame1.lambda$Fn13);
        }

        Object lambda9(Object i1) {
            return Scheme.applyToArgs.apply1(this.fk);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 9:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 11:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 12:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                case 13:
                    callContext.value1 = obj;
                    callContext.proc = moduleMethod;
                    callContext.pc = 1;
                    return 0;
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        Object lambda10() {
            return Scheme.applyToArgs.apply2(this.sk, AddOp.$Pl.apply2(this.f18i, pregexp.Lit8));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 10:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 14:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public Object lambda6loupSeq(Object res, Object i) {
            frame2 gnu_kawa_slib_pregexp_frame2 = new frame2();
            gnu_kawa_slib_pregexp_frame2.staticLink = this;
            gnu_kawa_slib_pregexp_frame2.res = res;
            if (lists.isNull(gnu_kawa_slib_pregexp_frame2.res)) {
                return Scheme.applyToArgs.apply2(this.sk, i);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(gnu_kawa_slib_pregexp_frame2.res), i, gnu_kawa_slib_pregexp_frame2.lambda$Fn14, this.fk);
        }

        public Object lambda7loupOr(Object res) {
            frame3 gnu_kawa_slib_pregexp_frame3 = new frame3();
            gnu_kawa_slib_pregexp_frame3.staticLink = this;
            gnu_kawa_slib_pregexp_frame3.res = res;
            if (lists.isNull(gnu_kawa_slib_pregexp_frame3.res)) {
                return Scheme.applyToArgs.apply1(this.fk);
            }
            return this.staticLink.lambda3sub(lists.car.apply1(gnu_kawa_slib_pregexp_frame3.res), this.f18i, gnu_kawa_slib_pregexp_frame3.lambda$Fn15, gnu_kawa_slib_pregexp_frame3.lambda$Fn16);
        }

        Object lambda11(Object i) {
            return Scheme.applyToArgs.apply2(this.sk, i);
        }

        Object lambda12(Object i1) {
            Object assv = lists.assv(this.re$1, this.staticLink.backrefs);
            try {
                lists.setCdr$Ex((Pair) assv, lists.cons(this.f18i, i1));
                return Scheme.applyToArgs.apply2(this.sk, i1);
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, assv);
            }
        }

        static Boolean lambda13() {
            return Boolean.FALSE;
        }

        static Boolean lambda14() {
            return Boolean.FALSE;
        }

        static Boolean lambda15() {
            return Boolean.FALSE;
        }

        static Boolean lambda16() {
            return Boolean.FALSE;
        }

        static Boolean lambda17() {
            return Boolean.FALSE;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 9:
                    return lambda9(obj);
                case 11:
                    return lambda11(obj);
                case 12:
                    return lambda12(obj);
                case 13:
                    return lambda18(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        Object lambda18(Object i1) {
            this.staticLink.case$Mnsensitive$Qu = this.old;
            return Scheme.applyToArgs.apply2(this.sk, i1);
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 10:
                    return lambda10();
                case 14:
                    return lambda19();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        Object lambda19() {
            this.staticLink.case$Mnsensitive$Qu = this.old;
            return Scheme.applyToArgs.apply1(this.fk);
        }

        public Object lambda8loupP(Object k, Object i) {
            frame4 gnu_kawa_slib_pregexp_frame4 = new frame4();
            gnu_kawa_slib_pregexp_frame4.staticLink = this;
            gnu_kawa_slib_pregexp_frame4.f22k = k;
            gnu_kawa_slib_pregexp_frame4.f21i = i;
            if (Scheme.numLss.apply2(gnu_kawa_slib_pregexp_frame4.f22k, this.f19p) != Boolean.FALSE) {
                return this.staticLink.lambda3sub(this.re, gnu_kawa_slib_pregexp_frame4.f21i, gnu_kawa_slib_pregexp_frame4.lambda$Fn17, this.fk);
            }
            gnu_kawa_slib_pregexp_frame4.f23q = this.f20q != Boolean.FALSE ? AddOp.$Mn.apply2(this.f20q, this.f19p) : this.f20q;
            return gnu_kawa_slib_pregexp_frame4.lambda24loupQ(pregexp.Lit73, gnu_kawa_slib_pregexp_frame4.f21i);
        }
    }

    /* compiled from: pregexp.scm */
    public class frame1 extends ModuleBody {
        Object chars;
        final ModuleMethod lambda$Fn13;
        frame0 staticLink;

        public frame1() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, 0);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:508");
            this.lambda$Fn13 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 1 ? lambda20() : super.apply0(moduleMethod);
        }

        Object lambda20() {
            return this.staticLink.lambda5loupOneOfChars(lists.cdr.apply1(this.chars));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame2 extends ModuleBody {
        final ModuleMethod lambda$Fn14;
        Object res;
        frame0 staticLink;

        public frame2() {
            PropertySet moduleMethod = new ModuleMethod(this, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:519");
            this.lambda$Fn14 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 2 ? lambda21(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda21(Object i1) {
            return this.staticLink.lambda6loupSeq(lists.cdr.apply1(this.res), i1);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 2) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn15;
        final ModuleMethod lambda$Fn16;
        Object res;
        frame0 staticLink;

        public frame3() {
            PropertySet moduleMethod = new ModuleMethod(this, 3, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:526");
            this.lambda$Fn15 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 4, null, 0);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:529");
            this.lambda$Fn16 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 3 ? lambda22(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda22(Object i1) {
            Boolean x = Scheme.applyToArgs.apply2(this.staticLink.sk, i1);
            return x != Boolean.FALSE ? x : this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 3) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 4 ? lambda23() : super.apply0(moduleMethod);
        }

        Object lambda23() {
            return this.staticLink.lambda7loupOr(lists.cdr.apply1(this.res));
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 4) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame4 extends ModuleBody {
        Object f21i;
        Object f22k;
        final ModuleMethod lambda$Fn17;
        Object f23q;
        frame0 staticLink;

        public frame4() {
            PropertySet moduleMethod = new ModuleMethod(this, 8, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:602");
            this.lambda$Fn17 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 8 ? lambda25(obj) : super.apply1(moduleMethod, obj);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        java.lang.Object lambda25(java.lang.Object r5) {
            /*
            r4 = this;
            r0 = r4.staticLink;
            r0 = r0.could$Mnloop$Mninfinitely$Qu;
            if (r0 == 0) goto L_0x0033;
        L_0x0006:
            r0 = kawa.standard.Scheme.numEqu;
            r1 = r4.f21i;
            r0 = r0.apply2(r5, r1);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x0022;
        L_0x0012:
            r0 = 2;
            r0 = new java.lang.Object[r0];
            r1 = 0;
            r2 = gnu.kawa.slib.pregexp.Lit101;
            r0[r1] = r2;
            r1 = 1;
            r2 = gnu.kawa.slib.pregexp.Lit110;
            r0[r1] = r2;
            gnu.kawa.slib.pregexp.pregexpError$V(r0);
        L_0x0022:
            r0 = r4.staticLink;
            r1 = gnu.kawa.functions.AddOp.$Pl;
            r2 = r4.f22k;
            r3 = gnu.kawa.slib.pregexp.Lit8;
            r1 = r1.apply2(r2, r3);
            r0 = r0.lambda8loupP(r1, r5);
            return r0;
        L_0x0033:
            r0 = r4.staticLink;
            r0 = r0.could$Mnloop$Mninfinitely$Qu;
            if (r0 == 0) goto L_0x0022;
        L_0x0039:
            goto L_0x0012;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.frame4.lambda25(java.lang.Object):java.lang.Object");
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

        public Object lambda24loupQ(Object k, Object i) {
            frame5 gnu_kawa_slib_pregexp_frame5 = new frame5();
            gnu_kawa_slib_pregexp_frame5.staticLink = this;
            gnu_kawa_slib_pregexp_frame5.f25k = k;
            gnu_kawa_slib_pregexp_frame5.f24i = i;
            gnu_kawa_slib_pregexp_frame5.fk = gnu_kawa_slib_pregexp_frame5.fk;
            if (this.f23q == Boolean.FALSE ? this.f23q != Boolean.FALSE : Scheme.numGEq.apply2(gnu_kawa_slib_pregexp_frame5.f25k, this.f23q) != Boolean.FALSE) {
                return gnu_kawa_slib_pregexp_frame5.lambda26fk();
            }
            if (this.staticLink.maximal$Qu) {
                return this.staticLink.staticLink.lambda3sub(this.staticLink.re, gnu_kawa_slib_pregexp_frame5.f24i, gnu_kawa_slib_pregexp_frame5.lambda$Fn18, gnu_kawa_slib_pregexp_frame5.fk);
            }
            Boolean x = gnu_kawa_slib_pregexp_frame5.lambda26fk();
            return x == Boolean.FALSE ? this.staticLink.staticLink.lambda3sub(this.staticLink.re, gnu_kawa_slib_pregexp_frame5.f24i, gnu_kawa_slib_pregexp_frame5.lambda$Fn19, gnu_kawa_slib_pregexp_frame5.fk) : x;
        }
    }

    /* compiled from: pregexp.scm */
    public class frame5 extends ModuleBody {
        Procedure fk;
        Object f24i;
        Object f25k;
        final ModuleMethod lambda$Fn18;
        final ModuleMethod lambda$Fn19;
        frame4 staticLink;

        public frame5() {
            PropertySet moduleMethod = new ModuleMethod(this, 5, pregexp.Lit111, 0);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:612");
            this.fk = moduleMethod;
            moduleMethod = new ModuleMethod(this, 6, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:617");
            this.lambda$Fn18 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 7, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:628");
            this.lambda$Fn19 = moduleMethod;
        }

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 5 ? lambda26fk() : super.apply0(moduleMethod);
        }

        public Object lambda26fk() {
            return Scheme.applyToArgs.apply2(this.staticLink.staticLink.sk, this.f24i);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 5) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        java.lang.Object lambda27(java.lang.Object r6) {
            /*
            r5 = this;
            r1 = r5.staticLink;
            r1 = r1.staticLink;
            r1 = r1.could$Mnloop$Mninfinitely$Qu;
            if (r1 == 0) goto L_0x0039;
        L_0x0008:
            r1 = kawa.standard.Scheme.numEqu;
            r2 = r5.f24i;
            r1 = r1.apply2(r6, r2);
            r2 = java.lang.Boolean.FALSE;
            if (r1 == r2) goto L_0x0024;
        L_0x0014:
            r1 = 2;
            r1 = new java.lang.Object[r1];
            r2 = 0;
            r3 = gnu.kawa.slib.pregexp.Lit101;
            r1[r2] = r3;
            r2 = 1;
            r3 = gnu.kawa.slib.pregexp.Lit110;
            r1[r2] = r3;
            gnu.kawa.slib.pregexp.pregexpError$V(r1);
        L_0x0024:
            r1 = r5.staticLink;
            r2 = gnu.kawa.functions.AddOp.$Pl;
            r3 = r5.f25k;
            r4 = gnu.kawa.slib.pregexp.Lit8;
            r2 = r2.apply2(r3, r4);
            r0 = r1.lambda24loupQ(r2, r6);
            r1 = java.lang.Boolean.FALSE;
            if (r0 == r1) goto L_0x0042;
        L_0x0038:
            return r0;
        L_0x0039:
            r1 = r5.staticLink;
            r1 = r1.staticLink;
            r1 = r1.could$Mnloop$Mninfinitely$Qu;
            if (r1 == 0) goto L_0x0024;
        L_0x0041:
            goto L_0x0014;
        L_0x0042:
            r0 = r5.lambda26fk();
            goto L_0x0038;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.frame5.lambda27(java.lang.Object):java.lang.Object");
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            switch (moduleMethod.selector) {
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
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 6:
                    return lambda27(obj);
                case 7:
                    return lambda28(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        Object lambda28(Object i1) {
            return this.staticLink.lambda24loupQ(AddOp.$Pl.apply2(this.f25k, pregexp.Lit8), i1);
        }
    }

    /* compiled from: pregexp.scm */
    public class frame extends ModuleBody {
        Object backrefs;
        Object case$Mnsensitive$Qu;
        Procedure identity;
        Object f26n;
        Object f27s;
        Object sn;
        Object start;

        public frame() {
            PropertySet moduleMethod = new ModuleMethod(this, 15, pregexp.Lit112, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:460");
            this.identity = moduleMethod;
        }

        public static Object lambda2identity(Object x) {
            return x;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 15 ? lambda2identity(obj) : super.apply1(moduleMethod, obj);
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        static Boolean lambda4() {
            return Boolean.FALSE;
        }

        public Object lambda3sub(Object re, Object i, Object sk, Object fk) {
            Object obj;
            frame0 gnu_kawa_slib_pregexp_frame0 = new frame0();
            gnu_kawa_slib_pregexp_frame0.staticLink = this;
            gnu_kawa_slib_pregexp_frame0.re$1 = re;
            gnu_kawa_slib_pregexp_frame0.f18i = i;
            gnu_kawa_slib_pregexp_frame0.sk = sk;
            gnu_kawa_slib_pregexp_frame0.fk = fk;
            if (Scheme.isEqv.apply2(gnu_kawa_slib_pregexp_frame0.re$1, pregexp.Lit10) != Boolean.FALSE) {
                if (Scheme.numEqu.apply2(gnu_kawa_slib_pregexp_frame0.f18i, this.start) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i);
                }
                return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
            } else if (Scheme.isEqv.apply2(gnu_kawa_slib_pregexp_frame0.re$1, pregexp.Lit12) != Boolean.FALSE) {
                return Scheme.numGEq.apply2(gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE ? Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i) : Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
            } else {
                if (Scheme.isEqv.apply2(gnu_kawa_slib_pregexp_frame0.re$1, pregexp.Lit23) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i);
                }
                if (Scheme.isEqv.apply2(gnu_kawa_slib_pregexp_frame0.re$1, pregexp.Lit26) != Boolean.FALSE) {
                    if (pregexp.isPregexpAtWordBoundary(this.f27s, gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE) {
                        return Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i);
                    }
                    return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                } else if (Scheme.isEqv.apply2(gnu_kawa_slib_pregexp_frame0.re$1, pregexp.Lit28) == Boolean.FALSE) {
                    boolean x = characters.isChar(gnu_kawa_slib_pregexp_frame0.re$1);
                    Object obj2;
                    CharSequence charSequence;
                    if (x ? Scheme.numLss.apply2(gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE : x) {
                        Procedure procedure = this.case$Mnsensitive$Qu != Boolean.FALSE ? characters.char$Eq$Qu : unicode.char$Mnci$Eq$Qu;
                        obj2 = this.f27s;
                        try {
                            charSequence = (CharSequence) obj2;
                            obj = gnu_kawa_slib_pregexp_frame0.f18i;
                            try {
                                return procedure.apply2(Char.make(strings.stringRef(charSequence, ((Number) obj).intValue())), gnu_kawa_slib_pregexp_frame0.re$1) != Boolean.FALSE ? Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, AddOp.$Pl.apply2(gnu_kawa_slib_pregexp_frame0.f18i, pregexp.Lit8)) : Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, obj);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, obj2);
                        }
                    }
                    x = (lists.isPair(gnu_kawa_slib_pregexp_frame0.re$1) + 1) & 1;
                    if (x ? Scheme.numLss.apply2(gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE : x) {
                        obj2 = this.f27s;
                        try {
                            charSequence = (CharSequence) obj2;
                            obj = gnu_kawa_slib_pregexp_frame0.f18i;
                            try {
                                if (pregexp.isPregexpCheckIfInCharClass(Char.make(strings.stringRef(charSequence, ((Number) obj).intValue())), gnu_kawa_slib_pregexp_frame0.re$1) != Boolean.FALSE) {
                                    return Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, AddOp.$Pl.apply2(gnu_kawa_slib_pregexp_frame0.f18i, pregexp.Lit8));
                                }
                                return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "string-ref", 2, obj);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "string-ref", 1, obj2);
                        }
                    }
                    Boolean x2;
                    Object x3 = lists.isPair(gnu_kawa_slib_pregexp_frame0.re$1);
                    if (x3 != null) {
                        x2 = Scheme.isEqv.apply2(lists.car.apply1(gnu_kawa_slib_pregexp_frame0.re$1), pregexp.Lit83);
                        if (x2 == Boolean.FALSE) {
                        }
                    } else {
                        if (x3 != null) {
                        }
                        if (lists.isPair(gnu_kawa_slib_pregexp_frame0.re$1)) {
                            Object tmp = lists.car.apply1(gnu_kawa_slib_pregexp_frame0.re$1);
                            if (Scheme.isEqv.apply2(tmp, pregexp.Lit83) != Boolean.FALSE) {
                                if (Scheme.numGEq.apply2(gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE) {
                                    return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                                }
                                return pregexp.pregexpError$V(new Object[]{pregexp.Lit101});
                            } else if (Scheme.isEqv.apply2(tmp, pregexp.Lit82) != Boolean.FALSE) {
                                if (Scheme.numGEq.apply2(gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE) {
                                    return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                                }
                                return gnu_kawa_slib_pregexp_frame0.lambda5loupOneOfChars(lists.cdr.apply1(gnu_kawa_slib_pregexp_frame0.re$1));
                            } else if (Scheme.isEqv.apply2(tmp, pregexp.Lit17) != Boolean.FALSE) {
                                if (Scheme.numGEq.apply2(gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE) {
                                    return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                                }
                                return lambda3sub(lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), gnu_kawa_slib_pregexp_frame0.f18i, gnu_kawa_slib_pregexp_frame0.lambda$Fn2, gnu_kawa_slib_pregexp_frame0.lambda$Fn3);
                            } else if (Scheme.isEqv.apply2(tmp, pregexp.Lit5) != Boolean.FALSE) {
                                return gnu_kawa_slib_pregexp_frame0.lambda6loupSeq(lists.cdr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), gnu_kawa_slib_pregexp_frame0.f18i);
                            } else {
                                if (Scheme.isEqv.apply2(tmp, pregexp.Lit4) != Boolean.FALSE) {
                                    return gnu_kawa_slib_pregexp_frame0.lambda7loupOr(lists.cdr.apply1(gnu_kawa_slib_pregexp_frame0.re$1));
                                }
                                if (Scheme.isEqv.apply2(tmp, pregexp.Lit20) != Boolean.FALSE) {
                                    Boolean backref;
                                    Boolean c = pregexp.pregexpListRef(this.backrefs, lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1));
                                    if (c != Boolean.FALSE) {
                                        backref = lists.cdr.apply1(c);
                                    } else {
                                        pregexp.pregexpError$V(new Object[]{pregexp.Lit101, pregexp.Lit102, gnu_kawa_slib_pregexp_frame0.re$1});
                                        backref = Boolean.FALSE;
                                    }
                                    if (backref == Boolean.FALSE) {
                                        return Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i);
                                    }
                                    obj2 = this.f27s;
                                    try {
                                        charSequence = (CharSequence) obj2;
                                        obj = lists.car.apply1(backref);
                                        try {
                                            int intValue = ((Number) obj).intValue();
                                            obj = lists.cdr.apply1(backref);
                                            try {
                                                return pregexp.pregexpStringMatch(strings.substring(charSequence, intValue, ((Number) obj).intValue()), this.f27s, gnu_kawa_slib_pregexp_frame0.f18i, this.f26n, gnu_kawa_slib_pregexp_frame0.lambda$Fn4, gnu_kawa_slib_pregexp_frame0.fk);
                                            } catch (ClassCastException e32) {
                                                throw new WrongType(e32, "substring", 3, obj);
                                            }
                                        } catch (ClassCastException e322) {
                                            throw new WrongType(e322, "substring", 2, obj);
                                        }
                                    } catch (ClassCastException e222) {
                                        throw new WrongType(e222, "substring", 1, obj2);
                                    }
                                } else if (Scheme.isEqv.apply2(tmp, pregexp.Lit100) != Boolean.FALSE) {
                                    return lambda3sub(lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), gnu_kawa_slib_pregexp_frame0.f18i, gnu_kawa_slib_pregexp_frame0.lambda$Fn5, gnu_kawa_slib_pregexp_frame0.fk);
                                } else {
                                    if (Scheme.isEqv.apply2(tmp, pregexp.Lit103) != Boolean.FALSE) {
                                        return lambda3sub(lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), gnu_kawa_slib_pregexp_frame0.f18i, this.identity, pregexp.lambda$Fn6) != Boolean.FALSE ? Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i) : Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                                    } else {
                                        if (Scheme.isEqv.apply2(tmp, pregexp.Lit104) != Boolean.FALSE) {
                                            return lambda3sub(lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), gnu_kawa_slib_pregexp_frame0.f18i, this.identity, pregexp.lambda$Fn7) != Boolean.FALSE ? Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk) : Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i);
                                        } else {
                                            Object n$Mnactual;
                                            Object sn$Mnactual;
                                            Boolean found$Mnit$Qu;
                                            if (Scheme.isEqv.apply2(tmp, pregexp.Lit105) != Boolean.FALSE) {
                                                n$Mnactual = this.f26n;
                                                sn$Mnactual = this.sn;
                                                this.f26n = gnu_kawa_slib_pregexp_frame0.f18i;
                                                this.sn = gnu_kawa_slib_pregexp_frame0.f18i;
                                                found$Mnit$Qu = lambda3sub(LList.list4(pregexp.Lit5, pregexp.Lit106, lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), pregexp.Lit12), pregexp.Lit73, this.identity, pregexp.lambda$Fn8);
                                                this.f26n = n$Mnactual;
                                                this.sn = sn$Mnactual;
                                                return found$Mnit$Qu != Boolean.FALSE ? Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i) : Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                                            } else if (Scheme.isEqv.apply2(tmp, pregexp.Lit107) != Boolean.FALSE) {
                                                n$Mnactual = this.f26n;
                                                sn$Mnactual = this.sn;
                                                this.f26n = gnu_kawa_slib_pregexp_frame0.f18i;
                                                this.sn = gnu_kawa_slib_pregexp_frame0.f18i;
                                                found$Mnit$Qu = lambda3sub(LList.list4(pregexp.Lit5, pregexp.Lit108, lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), pregexp.Lit12), pregexp.Lit73, this.identity, pregexp.lambda$Fn9);
                                                this.f26n = n$Mnactual;
                                                this.sn = sn$Mnactual;
                                                return found$Mnit$Qu != Boolean.FALSE ? Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk) : Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i);
                                            } else if (Scheme.isEqv.apply2(tmp, pregexp.Lit109) != Boolean.FALSE) {
                                                found$Mnit$Qu = lambda3sub(lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), gnu_kawa_slib_pregexp_frame0.f18i, this.identity, pregexp.lambda$Fn10);
                                                if (found$Mnit$Qu != Boolean.FALSE) {
                                                    return Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, found$Mnit$Qu);
                                                }
                                                return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                                            } else {
                                                x2 = Scheme.isEqv.apply2(tmp, pregexp.Lit60);
                                                if (x2 == Boolean.FALSE ? Scheme.isEqv.apply2(tmp, pregexp.Lit61) != Boolean.FALSE : x2 != Boolean.FALSE) {
                                                    gnu_kawa_slib_pregexp_frame0.old = this.case$Mnsensitive$Qu;
                                                    this.case$Mnsensitive$Qu = Scheme.isEqv.apply2(lists.car.apply1(gnu_kawa_slib_pregexp_frame0.re$1), pregexp.Lit60);
                                                    return lambda3sub(lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), gnu_kawa_slib_pregexp_frame0.f18i, gnu_kawa_slib_pregexp_frame0.lambda$Fn11, gnu_kawa_slib_pregexp_frame0.lambda$Fn12);
                                                } else if (Scheme.isEqv.apply2(tmp, pregexp.Lit68) != Boolean.FALSE) {
                                                    obj2 = lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1);
                                                    try {
                                                        boolean z;
                                                        gnu_kawa_slib_pregexp_frame0.maximal$Qu = ((obj2 != Boolean.FALSE ? 1 : 0) + 1) & 1;
                                                        gnu_kawa_slib_pregexp_frame0.f19p = lists.caddr.apply1(gnu_kawa_slib_pregexp_frame0.re$1);
                                                        gnu_kawa_slib_pregexp_frame0.f20q = lists.cadddr.apply1(gnu_kawa_slib_pregexp_frame0.re$1);
                                                        if (gnu_kawa_slib_pregexp_frame0.maximal$Qu) {
                                                            obj2 = gnu_kawa_slib_pregexp_frame0.f20q;
                                                            try {
                                                                int i2;
                                                                if (obj2 != Boolean.FALSE) {
                                                                    i2 = 1;
                                                                } else {
                                                                    i2 = 0;
                                                                }
                                                                z = (i2 + 1) & 1;
                                                            } catch (ClassCastException e2222) {
                                                                throw new WrongType(e2222, "could-loop-infinitely?", -2, obj2);
                                                            }
                                                        }
                                                        z = gnu_kawa_slib_pregexp_frame0.maximal$Qu;
                                                        gnu_kawa_slib_pregexp_frame0.could$Mnloop$Mninfinitely$Qu = z;
                                                        gnu_kawa_slib_pregexp_frame0.re = lists.car.apply1(lists.cddddr.apply1(gnu_kawa_slib_pregexp_frame0.re$1));
                                                        return gnu_kawa_slib_pregexp_frame0.lambda8loupP(pregexp.Lit73, gnu_kawa_slib_pregexp_frame0.f18i);
                                                    } catch (ClassCastException e22222) {
                                                        throw new WrongType(e22222, "maximal?", -2, obj2);
                                                    }
                                                } else {
                                                    return pregexp.pregexpError$V(new Object[]{pregexp.Lit101});
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else if (Scheme.numGEq.apply2(gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE) {
                            return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                        } else {
                            return pregexp.pregexpError$V(new Object[]{pregexp.Lit101});
                        }
                    }
                    obj2 = this.f27s;
                    try {
                        charSequence = (CharSequence) obj2;
                        obj = gnu_kawa_slib_pregexp_frame0.f18i;
                        try {
                            char c2 = strings.stringRef(charSequence, ((Number) obj).intValue());
                            ModuleMethod c$Ls = this.case$Mnsensitive$Qu != Boolean.FALSE ? characters.char$Ls$Eq$Qu : unicode.char$Mnci$Ls$Eq$Qu;
                            x2 = c$Ls.apply2(lists.cadr.apply1(gnu_kawa_slib_pregexp_frame0.re$1), Char.make(c2));
                            if (x2 == Boolean.FALSE ? x2 != Boolean.FALSE : c$Ls.apply2(Char.make(c2), lists.caddr.apply1(gnu_kawa_slib_pregexp_frame0.re$1)) != Boolean.FALSE) {
                                return Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, AddOp.$Pl.apply2(gnu_kawa_slib_pregexp_frame0.f18i, pregexp.Lit8));
                            }
                            return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                        } catch (ClassCastException e3222) {
                            throw new WrongType(e3222, "string-ref", 2, obj);
                        }
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "string-ref", 1, obj2);
                    }
                } else if (pregexp.isPregexpAtWordBoundary(this.f27s, gnu_kawa_slib_pregexp_frame0.f18i, this.f26n) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply1(gnu_kawa_slib_pregexp_frame0.fk);
                } else {
                    return Scheme.applyToArgs.apply2(gnu_kawa_slib_pregexp_frame0.sk, gnu_kawa_slib_pregexp_frame0.f18i);
                }
            }
        }
    }

    static {
        Char make = Char.make(92);
        Lit19 = make;
        Char make2 = Char.make(46);
        Lit13 = make2;
        Char make3 = Char.make(63);
        Lit47 = make3;
        Char make4 = Char.make(42);
        Lit65 = make4;
        Char make5 = Char.make(43);
        Lit66 = make5;
        Char make6 = Char.make(124);
        Lit7 = make6;
        Char make7 = Char.make(94);
        Lit9 = make7;
        Char make8 = Char.make(36);
        Lit11 = make8;
        Char make9 = Char.make(91);
        Lit15 = make9;
        Char make10 = Char.make(93);
        Lit46 = make10;
        Char make11 = Char.make(123);
        Lit67 = make11;
        Char make12 = Char.make(125);
        Lit78 = make12;
        Char make13 = Char.make(40);
        Lit18 = make13;
        Char make14 = Char.make(41);
        Lit6 = make14;
        Lit116 = PairWithPosition.make(make, PairWithPosition.make(make2, PairWithPosition.make(make3, PairWithPosition.make(make4, PairWithPosition.make(make5, PairWithPosition.make(make6, PairWithPosition.make(make7, PairWithPosition.make(make8, PairWithPosition.make(make9, PairWithPosition.make(make10, PairWithPosition.make(make11, PairWithPosition.make(make12, PairWithPosition.make(make13, PairWithPosition.make(make14, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153977), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153973), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153969), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153965), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153961), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3153957), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149885), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149881), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149877), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149873), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149869), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149865), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149861), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 3149856);
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol(":between").readResolve();
        Lit68 = simpleSymbol;
        Boolean bool = Boolean.FALSE;
        IntNum make15 = IntNum.make(0);
        Lit73 = make15;
        Boolean bool2 = Boolean.FALSE;
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol(":any").readResolve();
        Lit14 = simpleSymbol2;
        Lit108 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(bool, PairWithPosition.make(make15, PairWithPosition.make(bool2, PairWithPosition.make(simpleSymbol2, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338881), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338878), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338876), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338873), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 2338863);
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(":neg-char").readResolve();
        Lit17 = simpleSymbol;
        simpleSymbol2 = (SimpleSymbol) new SimpleSymbol(":word").readResolve();
        Lit41 = simpleSymbol2;
        Lit43 = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol2, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 696359), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 696348);
        simpleSymbol2 = Lit17;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(":space").readResolve();
        Lit36 = simpleSymbol;
        Lit38 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 684071), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 684060);
        simpleSymbol2 = Lit17;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(":digit").readResolve();
        Lit30 = simpleSymbol;
        Lit32 = PairWithPosition.make(simpleSymbol2, PairWithPosition.make(simpleSymbol, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 667687), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm", 667676);
        ModuleBody moduleBody = $instance;
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 16, Lit117, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:47");
        pregexp$Mnreverse$Ex = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 17, Lit118, -4096);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:57");
        pregexp$Mnerror = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 18, Lit119, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:65");
        pregexp$Mnread$Mnpattern = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 19, Lit120, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:79");
        pregexp$Mnread$Mnbranch = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 20, Lit21, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:91");
        pregexp$Mnread$Mnpiece = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 21, Lit121, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:138");
        pregexp$Mnread$Mnescaped$Mnnumber = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 22, Lit122, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:155");
        pregexp$Mnread$Mnescaped$Mnchar = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 23, Lit45, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:174");
        pregexp$Mnread$Mnposix$Mnchar$Mnclass = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 24, Lit57, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:200");
        pregexp$Mnread$Mncluster$Mntype = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 25, Lit64, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:233");
        pregexp$Mnread$Mnsubpattern = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 26, Lit74, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:254");
        pregexp$Mnwrap$Mnquantifier$Mnif$Mnany = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 27, Lit76, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:300");
        pregexp$Mnread$Mnnums = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 28, Lit123, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:323");
        pregexp$Mninvert$Mnchar$Mnlist = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 29, Lit80, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:330");
        pregexp$Mnread$Mnchar$Mnlist = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 30, Lit124, 24582);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:368");
        pregexp$Mnstring$Mnmatch = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 31, Lit125, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:379");
        pregexp$Mnchar$Mnword$Qu = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 32, Lit126, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:387");
        pregexp$Mnat$Mnword$Mnboundary$Qu = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 33, Lit99, 8194);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:399");
        pregexp$Mncheck$Mnif$Mnin$Mnchar$Mnclass$Qu = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 34, Lit127, 8194);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:429");
        pregexp$Mnlist$Mnref = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 35, Lit128, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:448");
        pregexp$Mnmake$Mnbackref$Mnlist = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 36, null, 0);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:463");
        lambda$Fn1 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 37, null, 0);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:551");
        lambda$Fn6 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 38, null, 0);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:556");
        lambda$Fn7 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 39, null, 0);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:564");
        lambda$Fn8 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 40, null, 0);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:573");
        lambda$Fn9 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 41, null, 0);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:578");
        lambda$Fn10 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 42, Lit101, 24582);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:459");
        pregexp$Mnmatch$Mnpositions$Mnaux = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 43, Lit129, 16388);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:639");
        pregexp$Mnreplace$Mnaux = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 44, Lit130, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:665");
        pregexp = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 45, Lit114, -4094);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:670");
        pregexp$Mnmatch$Mnpositions = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 46, Lit131, -4094);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:690");
        pregexp$Mnmatch = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 47, Lit132, 8194);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:700");
        pregexp$Mnsplit = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 48, Lit133, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:723");
        pregexp$Mnreplace = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 49, Lit134, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:736");
        pregexp$Mnreplace$St = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 50, Lit135, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/pregexp.scm:764");
        pregexp$Mnquote = moduleMethod;
        $instance.run();
    }

    public pregexp() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        $Stpregexp$Mnversion$St = Lit0;
        $Stpregexp$Mncomment$Mnchar$St = Lit1;
        $Stpregexp$Mnnul$Mnchar$Mnint$St = Integer.valueOf(characters.char$To$Integer(Lit2) - 97);
        $Stpregexp$Mnreturn$Mnchar$St = characters.integer$To$Char(((Number) $Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 13);
        $Stpregexp$Mntab$Mnchar$St = characters.integer$To$Char(((Number) $Stpregexp$Mnnul$Mnchar$Mnint$St).intValue() + 9);
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
    }

    public static Object pregexpReverse$Ex(Object s) {
        Object obj = LList.Empty;
        while (!lists.isNull(s)) {
            Object d = lists.cdr.apply1(s);
            try {
                lists.setCdr$Ex((Pair) s, obj);
                obj = s;
                s = d;
            } catch (ClassCastException e) {
                throw new WrongType(e, "set-cdr!", 1, s);
            }
        }
        return obj;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 16:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 31:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 44:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 50:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object pregexpError$V(Object[] argsArray) {
        LList whatever = LList.makeList(argsArray, 0);
        ports.display("Error:");
        Object obj = whatever;
        while (obj != LList.Empty) {
            try {
                Pair arg0 = (Pair) obj;
                Object x = arg0.getCar();
                ports.display(Lit3);
                ports.write(x);
                obj = arg0.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, obj);
            }
        }
        ports.newline();
        return misc.error$V("pregexp-error", new Object[0]);
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 17:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 30:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 42:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 45:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 46:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Object pregexpReadPattern(Object s, Object i, Object n) {
        if (Scheme.numGEq.apply2(i, n) != Boolean.FALSE) {
            return LList.list2(LList.list2(Lit4, LList.list1(Lit5)), i);
        }
        Pair branches = LList.Empty;
        while (true) {
            Object apply2 = Scheme.numGEq.apply2(i, n);
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                Object vv;
                if (x) {
                    if (x) {
                        break;
                    }
                    try {
                        try {
                            if (characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit7)) {
                                i = AddOp.$Pl.apply2(i, Lit8);
                            }
                            vv = pregexpReadBranch(s, i, n);
                            branches = lists.cons(lists.car.apply1(vv), branches);
                            i = lists.cadr.apply1(vv);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-ref", 2, i);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "string-ref", 1, s);
                    }
                }
                try {
                    try {
                        if (characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit6)) {
                            break;
                        }
                        if (characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit7)) {
                            i = AddOp.$Pl.apply2(i, Lit8);
                        }
                        vv = pregexpReadBranch(s, i, n);
                        branches = lists.cons(lists.car.apply1(vv), branches);
                        i = lists.cadr.apply1(vv);
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "string-ref", 2, i);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "string-ref", 1, s);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "x", -2, apply2);
            }
        }
        return LList.list2(lists.cons(Lit4, pregexpReverse$Ex(branches)), i);
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 18:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 19:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 20:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 21:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 22:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 23:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 25:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 26:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 27:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 29:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 32:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 48:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 49:
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

    public static Object pregexpReadBranch(Object s, Object i, Object n) {
        Pair pieces = LList.Empty;
        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
            try {
                try {
                    char c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                    boolean x = characters.isChar$Eq(Char.make(c), Lit7);
                    Object vv;
                    if (x) {
                        if (!x) {
                            vv = pregexpReadPiece(s, i, n);
                            pieces = lists.cons(lists.car.apply1(vv), pieces);
                            i = lists.cadr.apply1(vv);
                        }
                    } else if (!characters.isChar$Eq(Char.make(c), Lit6)) {
                        vv = pregexpReadPiece(s, i, n);
                        pieces = lists.cons(lists.car.apply1(vv), pieces);
                        i = lists.cadr.apply1(vv);
                    }
                    return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(pieces)), i);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, i);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        }
        return LList.list2(lists.cons(Lit5, pregexpReverse$Ex(pieces)), i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadPiece(java.lang.Object r13, java.lang.Object r14, java.lang.Object r15) {
        /*
        r12 = 2;
        r11 = 1;
        r0 = r13;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x0222 }
        r8 = r0;
        r0 = r14;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x022b }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x022b }
        r1 = kawa.lib.strings.stringRef(r8, r9);
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit9;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x0031;
    L_0x0022:
        r8 = Lit10;
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit8;
        r9 = r9.apply2(r14, r10);
        r8 = gnu.lists.LList.list2(r8, r9);
    L_0x0030:
        return r8;
    L_0x0031:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit11;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x0050;
    L_0x0041:
        r8 = Lit12;
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit8;
        r9 = r9.apply2(r14, r10);
        r8 = gnu.lists.LList.list2(r8, r9);
        goto L_0x0030;
    L_0x0050:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit13;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x0073;
    L_0x0060:
        r8 = Lit14;
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit8;
        r9 = r9.apply2(r14, r10);
        r8 = gnu.lists.LList.list2(r8, r9);
        r8 = pregexpWrapQuantifierIfAny(r8, r13, r15);
        goto L_0x0030;
    L_0x0073:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit15;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x00f0;
    L_0x0083:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r2 = r8.apply2(r14, r9);
        r8 = kawa.standard.Scheme.numLss;
        r9 = r8.apply2(r2, r15);
        r0 = r9;
        r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x0234 }
        r8 = r0;
        r7 = r8.booleanValue();	 Catch:{ ClassCastException -> 0x0234 }
        if (r7 == 0) goto L_0x00e3;
    L_0x009b:
        r0 = r13;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x023e }
        r8 = r0;
        r0 = r2;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0247 }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x0247 }
        r8 = kawa.lib.strings.stringRef(r8, r9);
        r5 = gnu.text.Char.make(r8);
    L_0x00af:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = Lit9;
        r8 = r8.apply2(r5, r9);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x00eb;
    L_0x00bb:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit16;
        r8 = r8.apply2(r14, r9);
        r6 = pregexpReadCharList(r13, r8, r15);
        r8 = Lit17;
        r9 = kawa.lib.lists.car;
        r9 = r9.apply1(r6);
        r8 = gnu.lists.LList.list2(r8, r9);
        r9 = kawa.lib.lists.cadr;
        r9 = r9.apply1(r6);
        r8 = gnu.lists.LList.list2(r8, r9);
    L_0x00dd:
        r8 = pregexpWrapQuantifierIfAny(r8, r13, r15);
        goto L_0x0030;
    L_0x00e3:
        if (r7 == 0) goto L_0x00e8;
    L_0x00e5:
        r5 = java.lang.Boolean.TRUE;
        goto L_0x00af;
    L_0x00e8:
        r5 = java.lang.Boolean.FALSE;
        goto L_0x00af;
    L_0x00eb:
        r8 = pregexpReadCharList(r13, r2, r15);
        goto L_0x00dd;
    L_0x00f0:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit18;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x0112;
    L_0x0100:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r8 = r8.apply2(r14, r9);
        r8 = pregexpReadSubpattern(r13, r8, r15);
        r8 = pregexpWrapQuantifierIfAny(r8, r13, r15);
        goto L_0x0030;
    L_0x0112:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit19;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x016f;
    L_0x0122:
        r4 = pregexpReadEscapedNumber(r13, r14, r15);
        r8 = java.lang.Boolean.FALSE;
        if (r4 == r8) goto L_0x0146;
    L_0x012a:
        r8 = Lit20;
        r9 = kawa.lib.lists.car;
        r9 = r9.apply1(r4);
        r8 = gnu.lists.LList.list2(r8, r9);
        r9 = kawa.lib.lists.cadr;
        r9 = r9.apply1(r4);
        r8 = gnu.lists.LList.list2(r8, r9);
    L_0x0140:
        r8 = pregexpWrapQuantifierIfAny(r8, r13, r15);
        goto L_0x0030;
    L_0x0146:
        r4 = pregexpReadEscapedChar(r13, r14, r15);
        r8 = java.lang.Boolean.FALSE;
        if (r4 == r8) goto L_0x015f;
    L_0x014e:
        r8 = kawa.lib.lists.car;
        r8 = r8.apply1(r4);
        r9 = kawa.lib.lists.cadr;
        r9 = r9.apply1(r4);
        r8 = gnu.lists.LList.list2(r8, r9);
        goto L_0x0140;
    L_0x015f:
        r8 = new java.lang.Object[r12];
        r9 = 0;
        r10 = Lit21;
        r8[r9] = r10;
        r9 = Lit22;
        r8[r11] = r9;
        r8 = pregexpError$V(r8);
        goto L_0x0140;
    L_0x016f:
        r7 = $Stpregexp$Mnspace$Mnsensitive$Qu$St;
        r8 = java.lang.Boolean.FALSE;
        if (r7 == r8) goto L_0x018f;
    L_0x0175:
        r8 = java.lang.Boolean.FALSE;
        if (r7 == r8) goto L_0x01a9;
    L_0x0179:
        r8 = gnu.text.Char.make(r1);
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit8;
        r9 = r9.apply2(r14, r10);
        r8 = gnu.lists.LList.list2(r8, r9);
        r8 = pregexpWrapQuantifierIfAny(r8, r13, r15);
        goto L_0x0030;
    L_0x018f:
        r8 = gnu.text.Char.make(r1);
        r8 = kawa.lib.rnrs.unicode.isCharWhitespace(r8);
        r8 = r8 + 1;
        r7 = r8 & 1;
        if (r7 == 0) goto L_0x01bd;
    L_0x019d:
        r8 = gnu.text.Char.make(r1);
        r9 = Lit1;
        r8 = kawa.lib.characters.isChar$Eq(r8, r9);
        if (r8 == 0) goto L_0x0179;
    L_0x01a9:
        r3 = java.lang.Boolean.FALSE;
    L_0x01ab:
        r8 = kawa.standard.Scheme.numGEq;
        r8 = r8.apply2(r14, r15);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x01c0;
    L_0x01b5:
        r8 = Lit23;
        r8 = gnu.lists.LList.list2(r8, r14);
        goto L_0x0030;
    L_0x01bd:
        if (r7 == 0) goto L_0x01a9;
    L_0x01bf:
        goto L_0x0179;
    L_0x01c0:
        r0 = r13;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x0250 }
        r8 = r0;
        r0 = r14;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0259 }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x0259 }
        r1 = kawa.lib.strings.stringRef(r8, r9);
        r8 = java.lang.Boolean.FALSE;
        if (r3 == r8) goto L_0x01ee;
    L_0x01d4:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r14 = r8.apply2(r14, r9);
        r8 = gnu.text.Char.make(r1);
        r9 = Lit24;
        r8 = kawa.lib.characters.isChar$Eq(r8, r9);
        if (r8 == 0) goto L_0x01eb;
    L_0x01e8:
        r3 = java.lang.Boolean.FALSE;
    L_0x01ea:
        goto L_0x01ab;
    L_0x01eb:
        r3 = java.lang.Boolean.TRUE;
        goto L_0x01ea;
    L_0x01ee:
        r8 = gnu.text.Char.make(r1);
        r8 = kawa.lib.rnrs.unicode.isCharWhitespace(r8);
        if (r8 == 0) goto L_0x0203;
    L_0x01f8:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r14 = r8.apply2(r14, r9);
        r3 = java.lang.Boolean.FALSE;
        goto L_0x01ab;
    L_0x0203:
        r8 = gnu.text.Char.make(r1);
        r9 = Lit1;
        r8 = kawa.lib.characters.isChar$Eq(r8, r9);
        if (r8 == 0) goto L_0x021a;
    L_0x020f:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r14 = r8.apply2(r14, r9);
        r3 = java.lang.Boolean.TRUE;
        goto L_0x01ab;
    L_0x021a:
        r8 = Lit23;
        r8 = gnu.lists.LList.list2(r8, r14);
        goto L_0x0030;
    L_0x0222:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r9.<init>(r8, r10, r11, r13);
        throw r9;
    L_0x022b:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r9.<init>(r8, r10, r12, r14);
        throw r9;
    L_0x0234:
        r8 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "x";
        r12 = -2;
        r10.<init>(r8, r11, r12, r9);
        throw r10;
    L_0x023e:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r9.<init>(r8, r10, r11, r13);
        throw r9;
    L_0x0247:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r9.<init>(r8, r10, r12, r2);
        throw r9;
    L_0x0250:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r9.<init>(r8, r10, r11, r13);
        throw r9;
    L_0x0259:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r9.<init>(r8, r10, r12, r14);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadPiece(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpReadEscapedNumber(Object s, Object i, Object n) {
        Object pregexpReverse$Ex;
        Object apply2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(i, Lit8), n);
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                try {
                    CharSequence charSequence = (CharSequence) s;
                    Object apply22 = AddOp.$Pl.apply2(i, Lit8);
                    try {
                        char c = strings.stringRef(charSequence, ((Number) apply22).intValue());
                        x = unicode.isCharNumeric(Char.make(c));
                        if (!x) {
                            return x ? Boolean.TRUE : Boolean.FALSE;
                        } else {
                            i = AddOp.$Pl.apply2(i, Lit16);
                            Pair r = LList.list1(Char.make(c));
                            while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
                                try {
                                    try {
                                        c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                                        if (unicode.isCharNumeric(Char.make(c))) {
                                            i = AddOp.$Pl.apply2(i, Lit8);
                                            r = lists.cons(Char.make(c), r);
                                        } else {
                                            pregexpReverse$Ex = pregexpReverse$Ex(r);
                                            try {
                                                return LList.list2(numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex)), i);
                                            } catch (ClassCastException e) {
                                                throw new WrongType(e, "list->string", 1, pregexpReverse$Ex);
                                            }
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-ref", 2, i);
                                    }
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "string-ref", 1, s);
                                }
                            }
                            pregexpReverse$Ex = pregexpReverse$Ex(r);
                            try {
                                return LList.list2(numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex)), i);
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "list->string", 1, pregexpReverse$Ex);
                            }
                        }
                    } catch (ClassCastException e222) {
                        throw new WrongType(e222, "string-ref", 2, apply22);
                    }
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "string-ref", 1, s);
                }
            }
        } catch (ClassCastException e22222) {
            throw new WrongType(e22222, "x", -2, apply2);
        }
    }

    public static Object pregexpReadEscapedChar(Object s, Object i, Object n) {
        Object apply2 = Scheme.numLss.apply2(AddOp.$Pl.apply2(i, Lit8), n);
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (!x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                try {
                    CharSequence charSequence = (CharSequence) s;
                    apply2 = AddOp.$Pl.apply2(i, Lit8);
                    try {
                        char c = strings.stringRef(charSequence, ((Number) apply2).intValue());
                        if (Scheme.isEqv.apply2(Char.make(c), Lit25) != Boolean.FALSE) {
                            return LList.list2(Lit26, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit27) != Boolean.FALSE) {
                            return LList.list2(Lit28, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit29) != Boolean.FALSE) {
                            return LList.list2(Lit30, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit31) != Boolean.FALSE) {
                            return LList.list2(Lit32, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit33) != Boolean.FALSE) {
                            return LList.list2(Lit24, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit34) != Boolean.FALSE) {
                            return LList.list2($Stpregexp$Mnreturn$Mnchar$St, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit35) != Boolean.FALSE) {
                            return LList.list2(Lit36, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit37) != Boolean.FALSE) {
                            return LList.list2(Lit38, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit39) != Boolean.FALSE) {
                            return LList.list2($Stpregexp$Mntab$Mnchar$St, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit40) != Boolean.FALSE) {
                            return LList.list2(Lit41, AddOp.$Pl.apply2(i, Lit16));
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit42) != Boolean.FALSE) {
                            return LList.list2(Lit43, AddOp.$Pl.apply2(i, Lit16));
                        }
                        return LList.list2(Char.make(c), AddOp.$Pl.apply2(i, Lit16));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, apply2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, s);
                }
            }
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "x", -2, apply2);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadPosixCharClass(java.lang.Object r12, java.lang.Object r13, java.lang.Object r14) {
        /*
        r11 = 2;
        r9 = 0;
        r10 = 1;
        r2 = java.lang.Boolean.FALSE;
        r6 = Lit44;
        r4 = gnu.lists.LList.list1(r6);
    L_0x000b:
        r6 = kawa.standard.Scheme.numGEq;
        r6 = r6.apply2(r13, r14);
        r7 = java.lang.Boolean.FALSE;
        if (r6 == r7) goto L_0x0020;
    L_0x0015:
        r6 = new java.lang.Object[r10];
        r7 = Lit45;
        r6[r9] = r7;
        r6 = pregexpError$V(r6);
    L_0x001f:
        return r6;
    L_0x0020:
        r0 = r12;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x00e7 }
        r6 = r0;
        r0 = r13;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x00f0 }
        r7 = r0;
        r7 = r7.intValue();	 Catch:{ ClassCastException -> 0x00f0 }
        r1 = kawa.lib.strings.stringRef(r6, r7);
        r6 = gnu.text.Char.make(r1);
        r7 = Lit9;
        r6 = kawa.lib.characters.isChar$Eq(r6, r7);
        if (r6 == 0) goto L_0x0047;
    L_0x003c:
        r2 = java.lang.Boolean.TRUE;
        r6 = gnu.kawa.functions.AddOp.$Pl;
        r7 = Lit8;
        r13 = r6.apply2(r13, r7);
        goto L_0x000b;
    L_0x0047:
        r6 = gnu.text.Char.make(r1);
        r6 = kawa.lib.rnrs.unicode.isCharAlphabetic(r6);
        if (r6 == 0) goto L_0x0062;
    L_0x0051:
        r6 = gnu.kawa.functions.AddOp.$Pl;
        r7 = Lit8;
        r13 = r6.apply2(r13, r7);
        r6 = gnu.text.Char.make(r1);
        r4 = kawa.lib.lists.cons(r6, r4);
        goto L_0x000b;
    L_0x0062:
        r6 = gnu.text.Char.make(r1);
        r7 = Lit44;
        r6 = kawa.lib.characters.isChar$Eq(r6, r7);
        if (r6 == 0) goto L_0x00db;
    L_0x006e:
        r6 = kawa.standard.Scheme.numGEq;
        r7 = gnu.kawa.functions.AddOp.$Pl;
        r8 = Lit8;
        r7 = r7.apply2(r13, r8);
        r7 = r6.apply2(r7, r14);
        r0 = r7;
        r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x00f9 }
        r6 = r0;
        r5 = r6.booleanValue();	 Catch:{ ClassCastException -> 0x00f9 }
        if (r5 == 0) goto L_0x0093;
    L_0x0086:
        if (r5 == 0) goto L_0x00b5;
    L_0x0088:
        r6 = new java.lang.Object[r10];
        r7 = Lit45;
        r6[r9] = r7;
        r6 = pregexpError$V(r6);
        goto L_0x001f;
    L_0x0093:
        r12 = (java.lang.CharSequence) r12;	 Catch:{ ClassCastException -> 0x0103 }
        r6 = gnu.kawa.functions.AddOp.$Pl;
        r7 = Lit8;
        r7 = r6.apply2(r13, r7);
        r0 = r7;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x010c }
        r6 = r0;
        r6 = r6.intValue();	 Catch:{ ClassCastException -> 0x010c }
        r6 = kawa.lib.strings.stringRef(r12, r6);
        r6 = gnu.text.Char.make(r6);
        r7 = Lit46;
        r6 = kawa.lib.characters.isChar$Eq(r6, r7);
        if (r6 == 0) goto L_0x0088;
    L_0x00b5:
        r6 = pregexpReverse$Ex(r4);
        r6 = (gnu.lists.LList) r6;	 Catch:{ ClassCastException -> 0x0115 }
        r6 = kawa.lib.strings.list$To$String(r6);
        r3 = kawa.lib.misc.string$To$Symbol(r6);
        r6 = java.lang.Boolean.FALSE;
        if (r2 == r6) goto L_0x00cd;
    L_0x00c7:
        r6 = Lit17;
        r3 = gnu.lists.LList.list2(r6, r3);
    L_0x00cd:
        r6 = gnu.kawa.functions.AddOp.$Pl;
        r7 = Lit16;
        r6 = r6.apply2(r13, r7);
        r6 = gnu.lists.LList.list2(r3, r6);
        goto L_0x001f;
    L_0x00db:
        r6 = new java.lang.Object[r10];
        r7 = Lit45;
        r6[r9] = r7;
        r6 = pregexpError$V(r6);
        goto L_0x001f;
    L_0x00e7:
        r6 = move-exception;
        r7 = new gnu.mapping.WrongType;
        r8 = "string-ref";
        r7.<init>(r6, r8, r10, r12);
        throw r7;
    L_0x00f0:
        r6 = move-exception;
        r7 = new gnu.mapping.WrongType;
        r8 = "string-ref";
        r7.<init>(r6, r8, r11, r13);
        throw r7;
    L_0x00f9:
        r6 = move-exception;
        r8 = new gnu.mapping.WrongType;
        r9 = "x";
        r10 = -2;
        r8.<init>(r6, r9, r10, r7);
        throw r8;
    L_0x0103:
        r6 = move-exception;
        r7 = new gnu.mapping.WrongType;
        r8 = "string-ref";
        r7.<init>(r6, r8, r10, r12);
        throw r7;
    L_0x010c:
        r6 = move-exception;
        r8 = new gnu.mapping.WrongType;
        r9 = "string-ref";
        r8.<init>(r6, r9, r11, r7);
        throw r8;
    L_0x0115:
        r7 = move-exception;
        r8 = new gnu.mapping.WrongType;
        r9 = "list->string";
        r8.<init>(r7, r9, r10, r6);
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadPosixCharClass(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpReadClusterType(Object s, Object i, Object n) {
        try {
            try {
                if (Scheme.isEqv.apply2(Char.make(strings.stringRef((CharSequence) s, ((Number) i).intValue())), Lit47) == Boolean.FALSE) {
                    return LList.list2(Lit63, i);
                }
                i = AddOp.$Pl.apply2(i, Lit8);
                try {
                    try {
                        char tmp = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit44) != Boolean.FALSE) {
                            return LList.list2(LList.Empty, AddOp.$Pl.apply2(i, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit48) != Boolean.FALSE) {
                            return LList.list2(Lit49, AddOp.$Pl.apply2(i, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit50) != Boolean.FALSE) {
                            return LList.list2(Lit51, AddOp.$Pl.apply2(i, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit52) != Boolean.FALSE) {
                            return LList.list2(Lit53, AddOp.$Pl.apply2(i, Lit8));
                        }
                        if (Scheme.isEqv.apply2(Char.make(tmp), Lit54) != Boolean.FALSE) {
                            try {
                                CharSequence charSequence = (CharSequence) s;
                                Object apply2 = AddOp.$Pl.apply2(i, Lit8);
                                try {
                                    tmp = strings.stringRef(charSequence, ((Number) apply2).intValue());
                                    Object pregexpError$V = Scheme.isEqv.apply2(Char.make(tmp), Lit48) != Boolean.FALSE ? Lit55 : Scheme.isEqv.apply2(Char.make(tmp), Lit50) != Boolean.FALSE ? Lit56 : pregexpError$V(new Object[]{Lit57});
                                    return LList.list2(pregexpError$V, AddOp.$Pl.apply2(i, Lit16));
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, s);
                            }
                        }
                        char c;
                        Object r = LList.Empty;
                        Boolean bool = Boolean.FALSE;
                        while (true) {
                            try {
                                try {
                                    c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                                    if (Scheme.isEqv.apply2(Char.make(c), Lit58) == Boolean.FALSE) {
                                        if (Scheme.isEqv.apply2(Char.make(c), Lit59) == Boolean.FALSE) {
                                            if (Scheme.isEqv.apply2(Char.make(c), Lit62) == Boolean.FALSE) {
                                                break;
                                            }
                                            $Stpregexp$Mnspace$Mnsensitive$Qu$St = bool;
                                            i = AddOp.$Pl.apply2(i, Lit8);
                                            bool = Boolean.FALSE;
                                        } else {
                                            i = AddOp.$Pl.apply2(i, Lit8);
                                            r = lists.cons(bool != Boolean.FALSE ? Lit60 : Lit61, r);
                                            bool = Boolean.FALSE;
                                        }
                                    } else {
                                        i = AddOp.$Pl.apply2(i, Lit8);
                                        bool = Boolean.TRUE;
                                    }
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "string-ref", 2, i);
                                }
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "string-ref", 1, s);
                            }
                        }
                        if (Scheme.isEqv.apply2(Char.make(c), Lit44) != Boolean.FALSE) {
                            return LList.list2(r, AddOp.$Pl.apply2(i, Lit8));
                        }
                        return pregexpError$V(new Object[]{Lit57});
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "string-ref", 2, i);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "string-ref", 1, s);
                }
            } catch (ClassCastException e222222) {
                throw new WrongType(e222222, "string-ref", 2, i);
            }
        } catch (ClassCastException e2222222) {
            throw new WrongType(e2222222, "string-ref", 1, s);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadSubpattern(java.lang.Object r15, java.lang.Object r16, java.lang.Object r17) {
        /*
        r5 = $Stpregexp$Mnspace$Mnsensitive$Qu$St;
        r3 = pregexpReadClusterType(r15, r16, r17);
        r10 = kawa.lib.lists.car;
        r1 = r10.apply1(r3);
        r10 = kawa.lib.lists.cadr;
        r16 = r10.apply1(r3);
        r6 = pregexpReadPattern(r15, r16, r17);
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = r5;
        r10 = kawa.lib.lists.car;
        r8 = r10.apply1(r6);
        r10 = kawa.lib.lists.cadr;
        r7 = r10.apply1(r6);
        r10 = kawa.standard.Scheme.numLss;
        r0 = r17;
        r11 = r10.apply2(r7, r0);
        r0 = r11;
        r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x0085 }
        r10 = r0;
        r9 = r10.booleanValue();	 Catch:{ ClassCastException -> 0x0085 }
        if (r9 == 0) goto L_0x0063;
    L_0x0036:
        r15 = (java.lang.CharSequence) r15;	 Catch:{ ClassCastException -> 0x008f }
        r0 = r7;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0099 }
        r10 = r0;
        r10 = r10.intValue();	 Catch:{ ClassCastException -> 0x0099 }
        r10 = kawa.lib.strings.stringRef(r15, r10);
        r10 = gnu.text.Char.make(r10);
        r11 = Lit6;
        r10 = kawa.lib.characters.isChar$Eq(r10, r11);
        if (r10 == 0) goto L_0x0065;
    L_0x0050:
        r10 = kawa.lib.lists.isNull(r1);
        if (r10 == 0) goto L_0x0072;
    L_0x0056:
        r10 = gnu.kawa.functions.AddOp.$Pl;
        r11 = Lit8;
        r10 = r10.apply2(r7, r11);
        r10 = gnu.lists.LList.list2(r8, r10);
    L_0x0062:
        return r10;
    L_0x0063:
        if (r9 != 0) goto L_0x0050;
    L_0x0065:
        r10 = 1;
        r10 = new java.lang.Object[r10];
        r11 = 0;
        r12 = Lit64;
        r10[r11] = r12;
        r10 = pregexpError$V(r10);
        goto L_0x0062;
    L_0x0072:
        r10 = kawa.lib.lists.cdr;
        r2 = r10.apply1(r1);
        r10 = kawa.lib.lists.car;
        r10 = r10.apply1(r1);
        r4 = gnu.lists.LList.list2(r10, r8);
        r8 = r4;
        r1 = r2;
        goto L_0x0050;
    L_0x0085:
        r10 = move-exception;
        r12 = new gnu.mapping.WrongType;
        r13 = "x";
        r14 = -2;
        r12.<init>(r10, r13, r14, r11);
        throw r12;
    L_0x008f:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-ref";
        r13 = 1;
        r11.<init>(r10, r12, r13, r15);
        throw r11;
    L_0x0099:
        r10 = move-exception;
        r11 = new gnu.mapping.WrongType;
        r12 = "string-ref";
        r13 = 2;
        r11.<init>(r10, r12, r13, r7);
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadSubpattern(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpWrapQuantifierIfAny(Object vv, Object s, Object n) {
        Object re = lists.car.apply1(vv);
        Object i = lists.cadr.apply1(vv);
        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
            try {
                try {
                    char c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                    boolean x = unicode.isCharWhitespace(Char.make(c));
                    Boolean x2;
                    Pair new$Mnre;
                    Pair new$Mnvv;
                    Object apply1;
                    Boolean pq;
                    if (x) {
                        if ($Stpregexp$Mnspace$Mnsensitive$Qu$St != Boolean.FALSE) {
                            x2 = Scheme.isEqv.apply2(Char.make(c), Lit65);
                            if (x2 != Boolean.FALSE) {
                                x2 = Scheme.isEqv.apply2(Char.make(c), Lit66);
                                if (x2 != Boolean.FALSE) {
                                    x2 = Scheme.isEqv.apply2(Char.make(c), Lit47);
                                    if (x2 == Boolean.FALSE) {
                                        if (x2 != Boolean.FALSE) {
                                            return vv;
                                        }
                                    } else if (Scheme.isEqv.apply2(Char.make(c), Lit67) != Boolean.FALSE) {
                                        return vv;
                                    }
                                } else if (x2 != Boolean.FALSE) {
                                    return vv;
                                }
                            } else if (x2 == Boolean.FALSE) {
                                return vv;
                            }
                            new$Mnre = LList.list1(Lit68);
                            LList.chain4(new$Mnre, Lit69, Lit70, Lit71, re);
                            new$Mnvv = LList.list2(new$Mnre, Lit72);
                            if (Scheme.isEqv.apply2(Char.make(c), Lit65) != Boolean.FALSE) {
                                apply1 = lists.cddr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply1, Lit73);
                                    apply1 = lists.cdddr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                                    } catch (ClassCastException e) {
                                        throw new WrongType(e, "set-car!", 1, apply1);
                                    }
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "set-car!", 1, apply1);
                                }
                            } else if (Scheme.isEqv.apply2(Char.make(c), Lit66) != Boolean.FALSE) {
                                apply1 = lists.cddr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply1, Lit8);
                                    apply1 = lists.cdddr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                                    } catch (ClassCastException e22) {
                                        throw new WrongType(e22, "set-car!", 1, apply1);
                                    }
                                } catch (ClassCastException e222) {
                                    throw new WrongType(e222, "set-car!", 1, apply1);
                                }
                            } else if (Scheme.isEqv.apply2(Char.make(c), Lit47) != Boolean.FALSE) {
                                apply1 = lists.cddr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply1, Lit73);
                                    apply1 = lists.cdddr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply1, Lit8);
                                    } catch (ClassCastException e2222) {
                                        throw new WrongType(e2222, "set-car!", 1, apply1);
                                    }
                                } catch (ClassCastException e22222) {
                                    throw new WrongType(e22222, "set-car!", 1, apply1);
                                }
                            } else if (Scheme.isEqv.apply2(Char.make(c), Lit67) != Boolean.FALSE) {
                                pq = pregexpReadNums(s, AddOp.$Pl.apply2(i, Lit8), n);
                                if (pq == Boolean.FALSE) {
                                    pregexpError$V(new Object[]{Lit74, Lit75});
                                }
                                apply1 = lists.cddr.apply1(new$Mnre);
                                try {
                                    lists.setCar$Ex((Pair) apply1, lists.car.apply1(pq));
                                    apply1 = lists.cdddr.apply1(new$Mnre);
                                    try {
                                        lists.setCar$Ex((Pair) apply1, lists.cadr.apply1(pq));
                                        i = lists.caddr.apply1(pq);
                                    } catch (ClassCastException e222222) {
                                        throw new WrongType(e222222, "set-car!", 1, apply1);
                                    }
                                } catch (ClassCastException e2222222) {
                                    throw new WrongType(e2222222, "set-car!", 1, apply1);
                                }
                            }
                            i = AddOp.$Pl.apply2(i, Lit8);
                            while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
                                try {
                                    try {
                                        c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                                        x = unicode.isCharWhitespace(Char.make(c));
                                        if (x) {
                                            if (!x) {
                                                if (characters.isChar$Eq(Char.make(c), Lit47)) {
                                                    apply1 = lists.cdr.apply1(new$Mnre);
                                                    lists.setCar$Ex((Pair) apply1, Boolean.TRUE);
                                                    apply1 = lists.cdr.apply1(new$Mnvv);
                                                    lists.setCar$Ex((Pair) apply1, AddOp.$Pl.apply2(i, Lit8));
                                                } else {
                                                    apply1 = lists.cdr.apply1(new$Mnre);
                                                    lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                                                    apply1 = lists.cdr.apply1(new$Mnvv);
                                                    lists.setCar$Ex((Pair) apply1, i);
                                                }
                                                return new$Mnvv;
                                            }
                                        } else if ($Stpregexp$Mnspace$Mnsensitive$Qu$St == Boolean.FALSE) {
                                            if (characters.isChar$Eq(Char.make(c), Lit47)) {
                                                apply1 = lists.cdr.apply1(new$Mnre);
                                                try {
                                                    lists.setCar$Ex((Pair) apply1, Boolean.TRUE);
                                                    apply1 = lists.cdr.apply1(new$Mnvv);
                                                    try {
                                                        lists.setCar$Ex((Pair) apply1, AddOp.$Pl.apply2(i, Lit8));
                                                    } catch (ClassCastException e22222222) {
                                                        throw new WrongType(e22222222, "set-car!", 1, apply1);
                                                    }
                                                } catch (ClassCastException e222222222) {
                                                    throw new WrongType(e222222222, "set-car!", 1, apply1);
                                                }
                                            }
                                            apply1 = lists.cdr.apply1(new$Mnre);
                                            try {
                                                lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                                                apply1 = lists.cdr.apply1(new$Mnvv);
                                                try {
                                                    lists.setCar$Ex((Pair) apply1, i);
                                                } catch (ClassCastException e2222222222) {
                                                    throw new WrongType(e2222222222, "set-car!", 1, apply1);
                                                }
                                            } catch (ClassCastException e22222222222) {
                                                throw new WrongType(e22222222222, "set-car!", 1, apply1);
                                            }
                                            return new$Mnvv;
                                        }
                                        i = AddOp.$Pl.apply2(i, Lit8);
                                    } catch (ClassCastException e3) {
                                        throw new WrongType(e3, "string-ref", 2, i);
                                    }
                                } catch (ClassCastException e32) {
                                    throw new WrongType(e32, "string-ref", 1, s);
                                }
                            }
                            apply1 = lists.cdr.apply1(new$Mnre);
                            try {
                                lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                                apply1 = lists.cdr.apply1(new$Mnvv);
                                try {
                                    lists.setCar$Ex((Pair) apply1, i);
                                    return new$Mnvv;
                                } catch (ClassCastException e222222222222) {
                                    throw new WrongType(e222222222222, "set-car!", 1, apply1);
                                }
                            } catch (ClassCastException e2222222222222) {
                                throw new WrongType(e2222222222222, "set-car!", 1, apply1);
                            }
                        }
                    } else if (!x) {
                        x2 = Scheme.isEqv.apply2(Char.make(c), Lit65);
                        if (x2 != Boolean.FALSE) {
                            x2 = Scheme.isEqv.apply2(Char.make(c), Lit66);
                            if (x2 != Boolean.FALSE) {
                                x2 = Scheme.isEqv.apply2(Char.make(c), Lit47);
                                if (x2 == Boolean.FALSE) {
                                    if (Scheme.isEqv.apply2(Char.make(c), Lit67) != Boolean.FALSE) {
                                        return vv;
                                    }
                                } else if (x2 != Boolean.FALSE) {
                                    return vv;
                                }
                            } else if (x2 != Boolean.FALSE) {
                                return vv;
                            }
                        } else if (x2 == Boolean.FALSE) {
                            return vv;
                        }
                        new$Mnre = LList.list1(Lit68);
                        LList.chain4(new$Mnre, Lit69, Lit70, Lit71, re);
                        new$Mnvv = LList.list2(new$Mnre, Lit72);
                        if (Scheme.isEqv.apply2(Char.make(c), Lit65) != Boolean.FALSE) {
                            apply1 = lists.cddr.apply1(new$Mnre);
                            lists.setCar$Ex((Pair) apply1, Lit73);
                            apply1 = lists.cdddr.apply1(new$Mnre);
                            lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                        } else if (Scheme.isEqv.apply2(Char.make(c), Lit66) != Boolean.FALSE) {
                            apply1 = lists.cddr.apply1(new$Mnre);
                            lists.setCar$Ex((Pair) apply1, Lit8);
                            apply1 = lists.cdddr.apply1(new$Mnre);
                            lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                        } else if (Scheme.isEqv.apply2(Char.make(c), Lit47) != Boolean.FALSE) {
                            apply1 = lists.cddr.apply1(new$Mnre);
                            lists.setCar$Ex((Pair) apply1, Lit73);
                            apply1 = lists.cdddr.apply1(new$Mnre);
                            lists.setCar$Ex((Pair) apply1, Lit8);
                        } else if (Scheme.isEqv.apply2(Char.make(c), Lit67) != Boolean.FALSE) {
                            pq = pregexpReadNums(s, AddOp.$Pl.apply2(i, Lit8), n);
                            if (pq == Boolean.FALSE) {
                                pregexpError$V(new Object[]{Lit74, Lit75});
                            }
                            apply1 = lists.cddr.apply1(new$Mnre);
                            lists.setCar$Ex((Pair) apply1, lists.car.apply1(pq));
                            apply1 = lists.cdddr.apply1(new$Mnre);
                            lists.setCar$Ex((Pair) apply1, lists.cadr.apply1(pq));
                            i = lists.caddr.apply1(pq);
                        }
                        i = AddOp.$Pl.apply2(i, Lit8);
                        while (Scheme.numGEq.apply2(i, n) == Boolean.FALSE) {
                            c = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                            x = unicode.isCharWhitespace(Char.make(c));
                            if (x) {
                                if (!x) {
                                    if (characters.isChar$Eq(Char.make(c), Lit47)) {
                                        apply1 = lists.cdr.apply1(new$Mnre);
                                        lists.setCar$Ex((Pair) apply1, Boolean.TRUE);
                                        apply1 = lists.cdr.apply1(new$Mnvv);
                                        lists.setCar$Ex((Pair) apply1, AddOp.$Pl.apply2(i, Lit8));
                                    } else {
                                        apply1 = lists.cdr.apply1(new$Mnre);
                                        lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                                        apply1 = lists.cdr.apply1(new$Mnvv);
                                        lists.setCar$Ex((Pair) apply1, i);
                                    }
                                    return new$Mnvv;
                                }
                            } else if ($Stpregexp$Mnspace$Mnsensitive$Qu$St == Boolean.FALSE) {
                                if (characters.isChar$Eq(Char.make(c), Lit47)) {
                                    apply1 = lists.cdr.apply1(new$Mnre);
                                    lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                                    apply1 = lists.cdr.apply1(new$Mnvv);
                                    lists.setCar$Ex((Pair) apply1, i);
                                } else {
                                    apply1 = lists.cdr.apply1(new$Mnre);
                                    lists.setCar$Ex((Pair) apply1, Boolean.TRUE);
                                    apply1 = lists.cdr.apply1(new$Mnvv);
                                    lists.setCar$Ex((Pair) apply1, AddOp.$Pl.apply2(i, Lit8));
                                }
                                return new$Mnvv;
                            }
                            i = AddOp.$Pl.apply2(i, Lit8);
                        }
                        apply1 = lists.cdr.apply1(new$Mnre);
                        lists.setCar$Ex((Pair) apply1, Boolean.FALSE);
                        apply1 = lists.cdr.apply1(new$Mnvv);
                        lists.setCar$Ex((Pair) apply1, i);
                        return new$Mnvv;
                    }
                    i = AddOp.$Pl.apply2(i, Lit8);
                } catch (ClassCastException e322) {
                    throw new WrongType(e322, "string-ref", 2, i);
                }
            } catch (ClassCastException e3222) {
                throw new WrongType(e3222, "string-ref", 1, s);
            }
        }
        return vv;
    }

    public static Object pregexpReadNums(Object s, Object i, Object n) {
        char c;
        Object p = LList.Empty;
        Object q = LList.Empty;
        Object obj = Lit8;
        Object obj2 = i;
        while (true) {
            boolean x;
            if (Scheme.numGEq.apply2(obj2, n) != Boolean.FALSE) {
                pregexpError$V(new Object[]{Lit76});
            }
            try {
                try {
                    c = strings.stringRef((CharSequence) s, ((Number) obj2).intValue());
                    Object k;
                    if (!unicode.isCharNumeric(Char.make(c))) {
                        x = unicode.isCharWhitespace(Char.make(c));
                        if (x ? $Stpregexp$Mnspace$Mnsensitive$Qu$St == Boolean.FALSE : x) {
                            obj2 = AddOp.$Pl.apply2(obj2, Lit8);
                        } else {
                            x = characters.isChar$Eq(Char.make(c), Lit77);
                            if (!x) {
                                if (!x) {
                                    break;
                                }
                            } else if (Scheme.numEqu.apply2(obj, Lit8) == Boolean.FALSE) {
                                break;
                            }
                            k = AddOp.$Pl.apply2(obj2, Lit8);
                            obj = Lit16;
                            obj2 = k;
                        }
                    } else if (Scheme.numEqu.apply2(obj, Lit8) != Boolean.FALSE) {
                        p = lists.cons(Char.make(c), p);
                        k = AddOp.$Pl.apply2(obj2, Lit8);
                        obj = Lit8;
                        obj2 = k;
                    } else {
                        q = lists.cons(Char.make(c), q);
                        k = AddOp.$Pl.apply2(obj2, Lit8);
                        obj = Lit16;
                        obj2 = k;
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "string-ref", 2, obj2);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "string-ref", 1, s);
            }
        }
        if (!characters.isChar$Eq(Char.make(c), Lit78)) {
            return Boolean.FALSE;
        }
        Object pregexpReverse$Ex = pregexpReverse$Ex(p);
        try {
            p = numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex));
            pregexpReverse$Ex = pregexpReverse$Ex(q);
            try {
                q = numbers.string$To$Number(strings.list$To$String((LList) pregexpReverse$Ex));
                try {
                    int i2;
                    if (p != Boolean.FALSE) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    x = (i2 + 1) & 1;
                    if (x ? Scheme.numEqu.apply2(obj, Lit8) != Boolean.FALSE : x) {
                        return LList.list3(Lit73, Boolean.FALSE, obj2);
                    }
                    return Scheme.numEqu.apply2(obj, Lit8) != Boolean.FALSE ? LList.list3(p, p, obj2) : LList.list3(p, q, obj2);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "x", -2, p);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "list->string", 1, pregexpReverse$Ex);
            }
        } catch (ClassCastException e32) {
            throw new WrongType(e32, "list->string", 1, pregexpReverse$Ex);
        }
    }

    public static Object pregexpInvertCharList(Object vv) {
        Object apply1 = lists.car.apply1(vv);
        try {
            lists.setCar$Ex((Pair) apply1, Lit79);
            return vv;
        } catch (ClassCastException e) {
            throw new WrongType(e, "set-car!", 1, apply1);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object pregexpReadCharList(java.lang.Object r13, java.lang.Object r14, java.lang.Object r15) {
        /*
        r6 = gnu.lists.LList.Empty;
    L_0x0002:
        r8 = kawa.standard.Scheme.numGEq;
        r8 = r8.apply2(r14, r15);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x001e;
    L_0x000c:
        r8 = 2;
        r8 = new java.lang.Object[r8];
        r9 = 0;
        r10 = Lit80;
        r8[r9] = r10;
        r9 = 1;
        r10 = Lit81;
        r8[r9] = r10;
        r8 = pregexpError$V(r8);
    L_0x001d:
        return r8;
    L_0x001e:
        r0 = r13;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x01d5 }
        r8 = r0;
        r0 = r14;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01df }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x01df }
        r1 = kawa.lib.strings.stringRef(r8, r9);
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit46;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x006c;
    L_0x003e:
        r8 = kawa.lib.lists.isNull(r6);
        if (r8 == 0) goto L_0x0055;
    L_0x0044:
        r8 = gnu.text.Char.make(r1);
        r6 = kawa.lib.lists.cons(r8, r6);
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r14 = r8.apply2(r14, r9);
        goto L_0x0002;
    L_0x0055:
        r8 = Lit82;
        r9 = pregexpReverse$Ex(r6);
        r8 = kawa.lib.lists.cons(r8, r9);
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit8;
        r9 = r9.apply2(r14, r10);
        r8 = gnu.lists.LList.list2(r8, r9);
        goto L_0x001d;
    L_0x006c:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit19;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x00a9;
    L_0x007c:
        r3 = pregexpReadEscapedChar(r13, r14, r15);
        r8 = java.lang.Boolean.FALSE;
        if (r3 == r8) goto L_0x0096;
    L_0x0084:
        r8 = kawa.lib.lists.car;
        r8 = r8.apply1(r3);
        r6 = kawa.lib.lists.cons(r8, r6);
        r8 = kawa.lib.lists.cadr;
        r14 = r8.apply1(r3);
        goto L_0x0002;
    L_0x0096:
        r8 = 2;
        r8 = new java.lang.Object[r8];
        r9 = 0;
        r10 = Lit80;
        r8[r9] = r10;
        r9 = 1;
        r10 = Lit22;
        r8[r9] = r10;
        r8 = pregexpError$V(r8);
        goto L_0x001d;
    L_0x00a9:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit58;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x015f;
    L_0x00b9:
        r7 = kawa.lib.lists.isNull(r6);
        if (r7 == 0) goto L_0x00d3;
    L_0x00bf:
        if (r7 == 0) goto L_0x0107;
    L_0x00c1:
        r8 = gnu.text.Char.make(r1);
        r6 = kawa.lib.lists.cons(r8, r6);
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r14 = r8.apply2(r14, r9);
        goto L_0x0002;
    L_0x00d3:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r4 = r8.apply2(r14, r9);
        r8 = kawa.standard.Scheme.numLss;
        r9 = r8.apply2(r4, r15);
        r0 = r9;
        r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x01e9 }
        r8 = r0;
        r7 = r8.booleanValue();	 Catch:{ ClassCastException -> 0x01e9 }
        if (r7 == 0) goto L_0x0149;
    L_0x00eb:
        r0 = r13;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x01f3 }
        r8 = r0;
        r0 = r4;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x01fd }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x01fd }
        r8 = kawa.lib.strings.stringRef(r8, r9);
        r8 = gnu.text.Char.make(r8);
        r9 = Lit46;
        r8 = kawa.lib.characters.isChar$Eq(r8, r9);
        if (r8 != 0) goto L_0x00c1;
    L_0x0107:
        r8 = kawa.lib.lists.car;
        r2 = r8.apply1(r6);
        r8 = kawa.lib.characters.isChar(r2);
        if (r8 == 0) goto L_0x014d;
    L_0x0113:
        r11 = Lit83;
        r0 = r13;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x0207 }
        r8 = r0;
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit8;
        r10 = r9.apply2(r14, r10);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0211 }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x0211 }
        r8 = kawa.lib.strings.stringRef(r8, r9);
        r8 = gnu.text.Char.make(r8);
        r8 = gnu.lists.LList.list3(r11, r2, r8);
        r9 = kawa.lib.lists.cdr;
        r9 = r9.apply1(r6);
        r6 = kawa.lib.lists.cons(r8, r9);
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit16;
        r14 = r8.apply2(r14, r9);
        goto L_0x0002;
    L_0x0149:
        if (r7 == 0) goto L_0x0107;
    L_0x014b:
        goto L_0x00c1;
    L_0x014d:
        r8 = gnu.text.Char.make(r1);
        r6 = kawa.lib.lists.cons(r8, r6);
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r14 = r8.apply2(r14, r9);
        goto L_0x0002;
    L_0x015f:
        r8 = kawa.standard.Scheme.isEqv;
        r9 = gnu.text.Char.make(r1);
        r10 = Lit15;
        r8 = r8.apply2(r9, r10);
        r9 = java.lang.Boolean.FALSE;
        if (r8 == r9) goto L_0x01c3;
    L_0x016f:
        r0 = r13;
        r0 = (java.lang.CharSequence) r0;	 Catch:{ ClassCastException -> 0x021b }
        r8 = r0;
        r9 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit8;
        r10 = r9.apply2(r14, r10);
        r0 = r10;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x0225 }
        r9 = r0;
        r9 = r9.intValue();	 Catch:{ ClassCastException -> 0x0225 }
        r8 = kawa.lib.strings.stringRef(r8, r9);
        r8 = gnu.text.Char.make(r8);
        r9 = Lit44;
        r8 = kawa.lib.characters.isChar$Eq(r8, r9);
        if (r8 == 0) goto L_0x01b1;
    L_0x0193:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit16;
        r8 = r8.apply2(r14, r9);
        r5 = pregexpReadPosixCharClass(r13, r8, r15);
        r8 = kawa.lib.lists.car;
        r8 = r8.apply1(r5);
        r6 = kawa.lib.lists.cons(r8, r6);
        r8 = kawa.lib.lists.cadr;
        r14 = r8.apply1(r5);
        goto L_0x0002;
    L_0x01b1:
        r8 = gnu.text.Char.make(r1);
        r6 = kawa.lib.lists.cons(r8, r6);
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r14 = r8.apply2(r14, r9);
        goto L_0x0002;
    L_0x01c3:
        r8 = gnu.text.Char.make(r1);
        r6 = kawa.lib.lists.cons(r8, r6);
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r9 = Lit8;
        r14 = r8.apply2(r14, r9);
        goto L_0x0002;
    L_0x01d5:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r11 = 1;
        r9.<init>(r8, r10, r11, r13);
        throw r9;
    L_0x01df:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r11 = 2;
        r9.<init>(r8, r10, r11, r14);
        throw r9;
    L_0x01e9:
        r8 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "x";
        r12 = -2;
        r10.<init>(r8, r11, r12, r9);
        throw r10;
    L_0x01f3:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r11 = 1;
        r9.<init>(r8, r10, r11, r13);
        throw r9;
    L_0x01fd:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r11 = 2;
        r9.<init>(r8, r10, r11, r4);
        throw r9;
    L_0x0207:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r11 = 1;
        r9.<init>(r8, r10, r11, r13);
        throw r9;
    L_0x0211:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r11 = "string-ref";
        r12 = 2;
        r9.<init>(r8, r11, r12, r10);
        throw r9;
    L_0x021b:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "string-ref";
        r11 = 1;
        r9.<init>(r8, r10, r11, r13);
        throw r9;
    L_0x0225:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r11 = "string-ref";
        r12 = 2;
        r9.<init>(r8, r11, r12, r10);
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.pregexp.pregexpReadCharList(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public static Object pregexpStringMatch(Object s1, Object s, Object i, Object n, Object sk, Object fk) {
        try {
            int n1 = strings.stringLength((CharSequence) s1);
            if (Scheme.numGrt.apply2(Integer.valueOf(n1), n) != Boolean.FALSE) {
                return Scheme.applyToArgs.apply1(fk);
            }
            Object j = Lit73;
            Object obj = i;
            while (Scheme.numGEq.apply2(j, Integer.valueOf(n1)) == Boolean.FALSE) {
                if (Scheme.numGEq.apply2(obj, n) != Boolean.FALSE) {
                    return Scheme.applyToArgs.apply1(fk);
                }
                try {
                    try {
                        try {
                            try {
                                if (!characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) s1, ((Number) j).intValue())), Char.make(strings.stringRef((CharSequence) s, ((Number) obj).intValue())))) {
                                    return Scheme.applyToArgs.apply1(fk);
                                }
                                j = AddOp.$Pl.apply2(j, Lit8);
                                obj = AddOp.$Pl.apply2(obj, Lit8);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "string-ref", 2, obj);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "string-ref", 1, s);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "string-ref", 2, j);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "string-ref", 1, s1);
                }
            }
            return Scheme.applyToArgs.apply2(sk, obj);
        } catch (ClassCastException e2222) {
            throw new WrongType(e2222, "string-length", 1, s1);
        }
    }

    public static boolean isPregexpCharWord(Object c) {
        try {
            boolean x = unicode.isCharAlphabetic((Char) c);
            if (x) {
                return x;
            }
            try {
                x = unicode.isCharNumeric((Char) c);
                if (x) {
                    return x;
                }
                try {
                    return characters.isChar$Eq((Char) c, Lit84);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char=?", 1, c);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "char-numeric?", 1, c);
            }
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "char-alphabetic?", 1, c);
        }
    }

    public static Object isPregexpAtWordBoundary(Object s, Object i, Object n) {
        Object apply2 = Scheme.numEqu.apply2(i, Lit73);
        try {
            boolean x = ((Boolean) apply2).booleanValue();
            if (x) {
                return x ? Boolean.TRUE : Boolean.FALSE;
            } else {
                apply2 = Scheme.numGEq.apply2(i, n);
                try {
                    x = ((Boolean) apply2).booleanValue();
                    if (x) {
                        return x ? Boolean.TRUE : Boolean.FALSE;
                    } else {
                        try {
                            try {
                                char c$Sli = strings.stringRef((CharSequence) s, ((Number) i).intValue());
                                try {
                                    CharSequence charSequence = (CharSequence) s;
                                    apply2 = AddOp.$Mn.apply2(i, Lit8);
                                    try {
                                        char c$Sli$Mn1 = strings.stringRef(charSequence, ((Number) apply2).intValue());
                                        Object c$Sli$Slw$Qu = isPregexpCheckIfInCharClass(Char.make(c$Sli), Lit41);
                                        Boolean c$Sli$Mn1$Slw$Qu = isPregexpCheckIfInCharClass(Char.make(c$Sli$Mn1), Lit41);
                                        if (c$Sli$Slw$Qu != Boolean.FALSE) {
                                            x = c$Sli$Mn1$Slw$Qu != Boolean.FALSE ? Boolean.FALSE : Boolean.TRUE;
                                        } else {
                                            x = c$Sli$Slw$Qu;
                                        }
                                        if (x != Boolean.FALSE) {
                                            return x;
                                        }
                                        try {
                                            Object x2 = ((c$Sli$Slw$Qu != Boolean.FALSE ? 1 : 0) + 1) & 1;
                                            if (x2 != null) {
                                                return c$Sli$Mn1$Slw$Qu;
                                            }
                                            return x2 != null ? Boolean.TRUE : Boolean.FALSE;
                                        } catch (ClassCastException e) {
                                            throw new WrongType(e, "x", -2, c$Sli$Slw$Qu);
                                        }
                                    } catch (ClassCastException e2) {
                                        throw new WrongType(e2, "string-ref", 2, apply2);
                                    }
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "string-ref", 1, s);
                                }
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "string-ref", 2, i);
                            }
                        } catch (ClassCastException e2222) {
                            throw new WrongType(e2222, "string-ref", 1, s);
                        }
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "x", -2, apply2);
                }
            }
        } catch (ClassCastException e222222) {
            throw new WrongType(e222222, "x", -2, apply2);
        }
    }

    public static Object isPregexpCheckIfInCharClass(Object c, Object char$Mnclass) {
        boolean x = false;
        if (Scheme.isEqv.apply2(char$Mnclass, Lit14) != Boolean.FALSE) {
            try {
                if (characters.isChar$Eq((Char) c, Lit24)) {
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            } catch (ClassCastException e) {
                throw new WrongType(e, "char=?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit85) != Boolean.FALSE) {
            try {
                x = unicode.isCharAlphabetic((Char) c);
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    try {
                        return unicode.isCharNumeric((Char) c) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "char-numeric?", 1, c);
                    }
                }
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit86) != Boolean.FALSE) {
            try {
                return unicode.isCharAlphabetic((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit87) != Boolean.FALSE) {
            try {
                return characters.char$To$Integer((Char) c) < 128 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit88) != Boolean.FALSE) {
            try {
                x = characters.isChar$Eq((Char) c, Lit3);
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    try {
                        Char charR = (Char) c;
                        Object obj = $Stpregexp$Mntab$Mnchar$St;
                        try {
                            return characters.isChar$Eq(charR, (Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                        } catch (ClassCastException e3) {
                            throw new WrongType(e3, "char=?", 2, obj);
                        }
                    } catch (ClassCastException e22222) {
                        throw new WrongType(e22222, "char=?", 1, c);
                    }
                }
            } catch (ClassCastException e222222) {
                throw new WrongType(e222222, "char=?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit89) != Boolean.FALSE) {
            try {
                return characters.char$To$Integer((Char) c) < 32 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e2222222) {
                throw new WrongType(e2222222, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit30) != Boolean.FALSE) {
            try {
                return unicode.isCharNumeric((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e22222222) {
                throw new WrongType(e22222222, "char-numeric?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit90) != Boolean.FALSE) {
            try {
                if (characters.char$To$Integer((Char) c) >= 32) {
                    x = true;
                }
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    try {
                        return unicode.isCharWhitespace((Char) c) ? Boolean.FALSE : Boolean.TRUE;
                    } catch (ClassCastException e222222222) {
                        throw new WrongType(e222222222, "char-whitespace?", 1, c);
                    }
                }
            } catch (ClassCastException e2222222222) {
                throw new WrongType(e2222222222, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit91) != Boolean.FALSE) {
            try {
                return unicode.isCharLowerCase((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e22222222222) {
                throw new WrongType(e22222222222, "char-lower-case?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit92) != Boolean.FALSE) {
            try {
                return characters.char$To$Integer((Char) c) >= 32 ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e222222222222) {
                throw new WrongType(e222222222222, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit93) != Boolean.FALSE) {
            try {
                if (characters.char$To$Integer((Char) c) >= 32) {
                    x = true;
                }
                if (!x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    try {
                        x = (unicode.isCharWhitespace((Char) c) + 1) & 1;
                        if (!x) {
                            return x ? Boolean.TRUE : Boolean.FALSE;
                        } else {
                            try {
                                x = (unicode.isCharAlphabetic((Char) c) + 1) & 1;
                                if (!x) {
                                    return x ? Boolean.TRUE : Boolean.FALSE;
                                } else {
                                    try {
                                        return unicode.isCharNumeric((Char) c) ? Boolean.FALSE : Boolean.TRUE;
                                    } catch (ClassCastException e2222222222222) {
                                        throw new WrongType(e2222222222222, "char-numeric?", 1, c);
                                    }
                                }
                            } catch (ClassCastException e22222222222222) {
                                throw new WrongType(e22222222222222, "char-alphabetic?", 1, c);
                            }
                        }
                    } catch (ClassCastException e222222222222222) {
                        throw new WrongType(e222222222222222, "char-whitespace?", 1, c);
                    }
                }
            } catch (ClassCastException e2222222222222222) {
                throw new WrongType(e2222222222222222, "char->integer", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit36) != Boolean.FALSE) {
            try {
                return unicode.isCharWhitespace((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e22222222222222222) {
                throw new WrongType(e22222222222222222, "char-whitespace?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit94) != Boolean.FALSE) {
            try {
                return unicode.isCharUpperCase((Char) c) ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e222222222222222222) {
                throw new WrongType(e222222222222222222, "char-upper-case?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit41) != Boolean.FALSE) {
            try {
                x = unicode.isCharAlphabetic((Char) c);
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    try {
                        x = unicode.isCharNumeric((Char) c);
                        if (x) {
                            return x ? Boolean.TRUE : Boolean.FALSE;
                        } else {
                            try {
                                return characters.isChar$Eq((Char) c, Lit84) ? Boolean.TRUE : Boolean.FALSE;
                            } catch (ClassCastException e2222222222222222222) {
                                throw new WrongType(e2222222222222222222, "char=?", 1, c);
                            }
                        }
                    } catch (ClassCastException e22222222222222222222) {
                        throw new WrongType(e22222222222222222222, "char-numeric?", 1, c);
                    }
                }
            } catch (ClassCastException e222222222222222222222) {
                throw new WrongType(e222222222222222222222, "char-alphabetic?", 1, c);
            }
        } else if (Scheme.isEqv.apply2(char$Mnclass, Lit95) != Boolean.FALSE) {
            try {
                x = unicode.isCharNumeric((Char) c);
                if (x) {
                    return x ? Boolean.TRUE : Boolean.FALSE;
                } else {
                    try {
                        x = unicode.isCharCi$Eq((Char) c, Lit2);
                        if (x) {
                            return x ? Boolean.TRUE : Boolean.FALSE;
                        } else {
                            try {
                                x = unicode.isCharCi$Eq((Char) c, Lit25);
                                if (x) {
                                    return x ? Boolean.TRUE : Boolean.FALSE;
                                } else {
                                    try {
                                        x = unicode.isCharCi$Eq((Char) c, Lit96);
                                        if (x) {
                                            return x ? Boolean.TRUE : Boolean.FALSE;
                                        } else {
                                            try {
                                                x = unicode.isCharCi$Eq((Char) c, Lit29);
                                                if (x) {
                                                    return x ? Boolean.TRUE : Boolean.FALSE;
                                                } else {
                                                    try {
                                                        x = unicode.isCharCi$Eq((Char) c, Lit97);
                                                        if (x) {
                                                            return x ? Boolean.TRUE : Boolean.FALSE;
                                                        } else {
                                                            try {
                                                                return unicode.isCharCi$Eq((Char) c, Lit98) ? Boolean.TRUE : Boolean.FALSE;
                                                            } catch (ClassCastException e2222222222222222222222) {
                                                                throw new WrongType(e2222222222222222222222, "char-ci=?", 1, c);
                                                            }
                                                        }
                                                    } catch (ClassCastException e22222222222222222222222) {
                                                        throw new WrongType(e22222222222222222222222, "char-ci=?", 1, c);
                                                    }
                                                }
                                            } catch (ClassCastException e222222222222222222222222) {
                                                throw new WrongType(e222222222222222222222222, "char-ci=?", 1, c);
                                            }
                                        }
                                    } catch (ClassCastException e2222222222222222222222222) {
                                        throw new WrongType(e2222222222222222222222222, "char-ci=?", 1, c);
                                    }
                                }
                            } catch (ClassCastException e22222222222222222222222222) {
                                throw new WrongType(e22222222222222222222222222, "char-ci=?", 1, c);
                            }
                        }
                    } catch (ClassCastException e222222222222222222222222222) {
                        throw new WrongType(e222222222222222222222222222, "char-ci=?", 1, c);
                    }
                }
            } catch (ClassCastException e2222222222222222222222222222) {
                throw new WrongType(e2222222222222222222222222222, "char-numeric?", 1, c);
            }
        } else {
            return pregexpError$V(new Object[]{Lit99});
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 33:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 34:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object pregexpListRef(Object s, Object i) {
        Object k = Lit73;
        while (!lists.isNull(s)) {
            if (Scheme.numEqu.apply2(k, i) != Boolean.FALSE) {
                return lists.car.apply1(s);
            }
            s = lists.cdr.apply1(s);
            k = AddOp.$Pl.apply2(k, Lit8);
        }
        return Boolean.FALSE;
    }

    public static Object pregexpMakeBackrefList(Object re) {
        return lambda1sub(re);
    }

    public static Object lambda1sub(Object re) {
        if (!lists.isPair(re)) {
            return LList.Empty;
        }
        Object car$Mnre = lists.car.apply1(re);
        Object sub$Mncdr$Mnre = lambda1sub(lists.cdr.apply1(re));
        if (Scheme.isEqv.apply2(car$Mnre, Lit100) != Boolean.FALSE) {
            return lists.cons(lists.cons(re, Boolean.FALSE), sub$Mncdr$Mnre);
        }
        return append.append$V(new Object[]{lambda1sub(car$Mnre), sub$Mncdr$Mnre});
    }

    public static Object pregexpMatchPositionsAux(Object re, Object s, Object sn, Object start, Object n, Object i) {
        frame gnu_kawa_slib_pregexp_frame = new frame();
        gnu_kawa_slib_pregexp_frame.f27s = s;
        gnu_kawa_slib_pregexp_frame.sn = sn;
        gnu_kawa_slib_pregexp_frame.start = start;
        gnu_kawa_slib_pregexp_frame.f26n = n;
        Procedure procedure = gnu_kawa_slib_pregexp_frame.identity;
        Object pregexpMakeBackrefList = pregexpMakeBackrefList(re);
        gnu_kawa_slib_pregexp_frame.case$Mnsensitive$Qu = Boolean.TRUE;
        gnu_kawa_slib_pregexp_frame.backrefs = pregexpMakeBackrefList;
        gnu_kawa_slib_pregexp_frame.identity = procedure;
        gnu_kawa_slib_pregexp_frame.lambda3sub(re, i, gnu_kawa_slib_pregexp_frame.identity, lambda$Fn1);
        Object arg0 = gnu_kawa_slib_pregexp_frame.backrefs;
        Pair result = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                result = Pair.make(lists.cdr.apply1(arg02.getCar()), result);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        LList backrefs = LList.reverseInPlace(result);
        Boolean x = lists.car.apply1(backrefs);
        if (x != Boolean.FALSE) {
            return backrefs;
        }
        return x;
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 36:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 37:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 38:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 39:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 40:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 41:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 36:
                return frame.lambda4();
            case 37:
                return frame0.lambda13();
            case 38:
                return frame0.lambda14();
            case 39:
                return frame0.lambda15();
            case 40:
                return frame0.lambda16();
            case 41:
                return frame0.lambda17();
            default:
                return super.apply0(moduleMethod);
        }
    }

    public static Object pregexpReplaceAux(Object str, Object ins, Object n, Object backrefs) {
        Object apply2;
        Object obj = Lit73;
        Object obj2 = "";
        while (Scheme.numGEq.apply2(obj, n) == Boolean.FALSE) {
            try {
                try {
                    Object[] objArr;
                    if (characters.isChar$Eq(Char.make(strings.stringRef((CharSequence) ins, ((Number) obj).intValue())), Lit19)) {
                        Boolean br;
                        CharSequence charSequence;
                        Boolean br$Mni = pregexpReadEscapedNumber(ins, obj, n);
                        if (br$Mni != Boolean.FALSE) {
                            br = lists.car.apply1(br$Mni);
                        } else {
                            try {
                                charSequence = (CharSequence) ins;
                                apply2 = AddOp.$Pl.apply2(obj, Lit8);
                                try {
                                    br = characters.isChar$Eq(Char.make(strings.stringRef(charSequence, ((Number) apply2).intValue())), Lit113) ? Lit73 : Boolean.FALSE;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "string-ref", 2, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "string-ref", 1, ins);
                            }
                        }
                        if (br$Mni != Boolean.FALSE) {
                            obj = lists.cadr.apply1(br$Mni);
                        } else if (br != Boolean.FALSE) {
                            obj = AddOp.$Pl.apply2(obj, Lit16);
                        } else {
                            obj = AddOp.$Pl.apply2(obj, Lit8);
                        }
                        if (br == Boolean.FALSE) {
                            try {
                                try {
                                    char c2 = strings.stringRef((CharSequence) ins, ((Number) obj).intValue());
                                    obj = AddOp.$Pl.apply2(obj, Lit8);
                                    if (!characters.isChar$Eq(Char.make(c2), Lit11)) {
                                        objArr = new Object[2];
                                        objArr[0] = obj2;
                                        objArr[1] = strings.$make$string$(Char.make(c2));
                                        obj2 = strings.stringAppend(objArr);
                                    }
                                } catch (ClassCastException e22) {
                                    throw new WrongType(e22, "string-ref", 2, obj);
                                }
                            } catch (ClassCastException e222) {
                                throw new WrongType(e222, "string-ref", 1, ins);
                            }
                        }
                        Boolean backref = pregexpListRef(backrefs, br);
                        if (backref != Boolean.FALSE) {
                            Object[] objArr2 = new Object[2];
                            objArr2[0] = obj2;
                            try {
                                charSequence = (CharSequence) str;
                                apply2 = lists.car.apply1(backref);
                                try {
                                    int intValue = ((Number) apply2).intValue();
                                    apply2 = lists.cdr.apply1(backref);
                                    try {
                                        objArr2[1] = strings.substring(charSequence, intValue, ((Number) apply2).intValue());
                                        obj2 = strings.stringAppend(objArr2);
                                    } catch (ClassCastException e2222) {
                                        throw new WrongType(e2222, "substring", 3, apply2);
                                    }
                                } catch (ClassCastException e22222) {
                                    throw new WrongType(e22222, "substring", 2, apply2);
                                }
                            } catch (ClassCastException e222222) {
                                throw new WrongType(e222222, "substring", 1, str);
                            }
                        }
                    }
                    obj = AddOp.$Pl.apply2(obj, Lit8);
                    objArr = new Object[2];
                    objArr[0] = obj2;
                    objArr[1] = strings.$make$string$(Char.make(c));
                    obj2 = strings.stringAppend(objArr);
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "string-ref", 2, obj);
                }
            } catch (ClassCastException e22222222) {
                throw new WrongType(e22222222, "string-ref", 1, ins);
            }
        }
        return obj2;
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 43 ? pregexpReplaceAux(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 43) {
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

    public static Pair pregexp(Object s) {
        $Stpregexp$Mnspace$Mnsensitive$Qu$St = Boolean.TRUE;
        try {
            return LList.list2(Lit100, lists.car.apply1(pregexpReadPattern(s, Lit73, Integer.valueOf(strings.stringLength((CharSequence) s)))));
        } catch (ClassCastException e) {
            throw new WrongType(e, "string-length", 1, s);
        }
    }

    public static Object pregexpMatchPositions$V(Object pat, Object str, Object[] argsArray) {
        LList opt$Mnargs = LList.makeList(argsArray, 0);
        if (strings.isString(pat)) {
            pat = pregexp(pat);
        } else if (!lists.isPair(pat)) {
            pregexpError$V(new Object[]{Lit114, Lit115, pat});
        }
        try {
            Object start;
            Object end;
            boolean x;
            int str$Mnlen = strings.stringLength((CharSequence) str);
            if (lists.isNull(opt$Mnargs)) {
                start = Lit73;
            } else {
                start = lists.car.apply1(opt$Mnargs);
                Object apply1 = lists.cdr.apply1(opt$Mnargs);
                try {
                    opt$Mnargs = (LList) apply1;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "opt-args", -2, apply1);
                }
            }
            if (lists.isNull(opt$Mnargs)) {
                end = Integer.valueOf(str$Mnlen);
            } else {
                end = lists.car.apply1(opt$Mnargs);
            }
            Object i = start;
            while (true) {
                Object apply2 = Scheme.numLEq.apply2(i, end);
                try {
                    x = ((Boolean) apply2).booleanValue();
                    if (!x) {
                        break;
                    }
                    x = pregexpMatchPositionsAux(pat, str, Integer.valueOf(str$Mnlen), start, end, i);
                    if (x != Boolean.FALSE) {
                        return x;
                    }
                    i = AddOp.$Pl.apply2(i, Lit8);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "x", -2, apply2);
                }
            }
            return x ? Boolean.TRUE : Boolean.FALSE;
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "string-length", 1, str);
        }
    }

    public static Object pregexpMatch$V(Object pat, Object str, Object[] argsArray) {
        Boolean ix$Mnprs = Scheme.apply.apply4(pregexp$Mnmatch$Mnpositions, pat, str, LList.makeList(argsArray, 0));
        if (ix$Mnprs == Boolean.FALSE) {
            return ix$Mnprs;
        }
        Pair result = LList.Empty;
        Object arg0 = ix$Mnprs;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                Object ix$Mnpr = arg02.getCar();
                if (ix$Mnpr != Boolean.FALSE) {
                    try {
                        CharSequence charSequence = (CharSequence) str;
                        Object apply1 = lists.car.apply1(ix$Mnpr);
                        try {
                            int intValue = ((Number) apply1).intValue();
                            apply1 = lists.cdr.apply1(ix$Mnpr);
                            try {
                                ix$Mnpr = strings.substring(charSequence, intValue, ((Number) apply1).intValue());
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 3, apply1);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 2, apply1);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "substring", 1, str);
                    }
                }
                result = Pair.make(ix$Mnpr, result);
                arg0 = arg03;
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "arg0", -2, arg0);
            }
        }
        return LList.reverseInPlace(result);
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        Object obj;
        Object obj2;
        int length;
        Object[] objArr2;
        switch (moduleMethod.selector) {
            case 17:
                return pregexpError$V(objArr);
            case 30:
                return pregexpStringMatch(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 42:
                return pregexpMatchPositionsAux(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            case 45:
                obj = objArr[0];
                obj2 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return pregexpMatchPositions$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            case 46:
                obj = objArr[0];
                obj2 = objArr[1];
                length = objArr.length - 2;
                objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return pregexpMatch$V(obj, obj2, objArr2);
                    }
                    objArr2[length] = objArr[length + 2];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static Object pregexpSplit(Object pat, Object str) {
        try {
            int n = strings.stringLength((CharSequence) str);
            Object obj = Lit73;
            Object obj2 = LList.Empty;
            Boolean bool = Boolean.FALSE;
            while (Scheme.numGEq.apply2(obj, Integer.valueOf(n)) == Boolean.FALSE) {
                Boolean temp = pregexpMatchPositions$V(pat, str, new Object[]{obj, Integer.valueOf(n)});
                if (temp != Boolean.FALSE) {
                    Object jk = lists.car.apply1(temp);
                    Object j = lists.car.apply1(jk);
                    Object k = lists.cdr.apply1(jk);
                    if (Scheme.numEqu.apply2(j, k) != Boolean.FALSE) {
                        Object i = AddOp.$Pl.apply2(k, Lit8);
                        try {
                            CharSequence charSequence = (CharSequence) str;
                            try {
                                int intValue = ((Number) obj).intValue();
                                Object apply2 = AddOp.$Pl.apply2(j, Lit8);
                                try {
                                    obj2 = lists.cons(strings.substring(charSequence, intValue, ((Number) apply2).intValue()), obj2);
                                    bool = Boolean.TRUE;
                                    obj = i;
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "substring", 3, apply2);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "substring", 2, obj);
                            }
                        } catch (ClassCastException e22) {
                            throw new WrongType(e22, "substring", 1, str);
                        }
                    }
                    Object apply22 = Scheme.numEqu.apply2(j, obj);
                    try {
                        boolean x = ((Boolean) apply22).booleanValue();
                        if (x ? r8 != Boolean.FALSE : x) {
                            bool = Boolean.FALSE;
                            obj = k;
                        } else {
                            try {
                                try {
                                    try {
                                        obj2 = lists.cons(strings.substring((CharSequence) str, ((Number) obj).intValue(), ((Number) j).intValue()), obj2);
                                        bool = Boolean.FALSE;
                                        obj = k;
                                    } catch (ClassCastException e222) {
                                        throw new WrongType(e222, "substring", 3, j);
                                    }
                                } catch (ClassCastException e2222) {
                                    throw new WrongType(e2222, "substring", 2, obj);
                                }
                            } catch (ClassCastException e22222) {
                                throw new WrongType(e22222, "substring", 1, str);
                            }
                        }
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "x", -2, apply22);
                    }
                }
                Integer i2 = Integer.valueOf(n);
                try {
                    try {
                        obj2 = lists.cons(strings.substring((CharSequence) str, ((Number) obj).intValue(), n), obj2);
                        bool = Boolean.FALSE;
                        obj = i2;
                    } catch (ClassCastException e2222222) {
                        throw new WrongType(e2222222, "substring", 2, obj);
                    }
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "substring", 1, str);
                }
            }
            return pregexpReverse$Ex(obj2);
        } catch (ClassCastException e222222222) {
            throw new WrongType(e222222222, "string-length", 1, str);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 33:
                return isPregexpCheckIfInCharClass(obj, obj2);
            case 34:
                return pregexpListRef(obj, obj2);
            case 47:
                return pregexpSplit(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Object pregexpReplace(Object pat, Object str, Object ins) {
        try {
            Boolean pp = pregexpMatchPositions$V(pat, str, new Object[]{Lit73, Integer.valueOf(strings.stringLength((CharSequence) str))});
            if (pp == Boolean.FALSE) {
                return str;
            }
            try {
                int ins$Mnlen = strings.stringLength((CharSequence) ins);
                Object m$Mni = lists.caar.apply1(pp);
                Object m$Mnn = lists.cdar.apply1(pp);
                Object[] objArr = new Object[3];
                try {
                    try {
                        objArr[0] = strings.substring((CharSequence) str, 0, ((Number) m$Mni).intValue());
                        objArr[1] = pregexpReplaceAux(str, ins, Integer.valueOf(ins$Mnlen), pp);
                        try {
                            try {
                                objArr[2] = strings.substring((CharSequence) str, ((Number) m$Mnn).intValue(), n);
                                return strings.stringAppend(objArr);
                            } catch (ClassCastException e) {
                                throw new WrongType(e, "substring", 2, m$Mnn);
                            }
                        } catch (ClassCastException e2) {
                            throw new WrongType(e2, "substring", 1, str);
                        }
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "substring", 3, m$Mni);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "substring", 1, str);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "string-length", 1, ins);
            }
        } catch (ClassCastException e22222) {
            throw new WrongType(e22222, "string-length", 1, str);
        }
    }

    public static Object pregexpReplace$St(Object pat, Object str, Object ins) {
        if (strings.isString(pat)) {
            pat = pregexp(pat);
        }
        try {
            int n = strings.stringLength((CharSequence) str);
            try {
                int ins$Mnlen = strings.stringLength((CharSequence) ins);
                Object obj = Lit73;
                Object obj2 = "";
                while (Scheme.numGEq.apply2(obj, Integer.valueOf(n)) == Boolean.FALSE) {
                    Boolean pp = pregexpMatchPositions$V(pat, str, new Object[]{obj, Integer.valueOf(n)});
                    if (pp == Boolean.FALSE) {
                        if (Scheme.numEqu.apply2(obj, Lit73) == Boolean.FALSE) {
                            Object[] objArr = new Object[2];
                            objArr[0] = obj2;
                            try {
                                try {
                                    objArr[1] = strings.substring((CharSequence) str, ((Number) obj).intValue(), n);
                                    str = strings.stringAppend(objArr);
                                } catch (ClassCastException e) {
                                    throw new WrongType(e, "substring", 2, obj);
                                }
                            } catch (ClassCastException e2) {
                                throw new WrongType(e2, "substring", 1, str);
                            }
                        }
                        return str;
                    }
                    Object i = lists.cdar.apply1(pp);
                    Object[] objArr2 = new Object[3];
                    objArr2[0] = obj2;
                    try {
                        CharSequence charSequence = (CharSequence) str;
                        try {
                            int intValue = ((Number) obj).intValue();
                            Object apply1 = lists.caar.apply1(pp);
                            try {
                                objArr2[1] = strings.substring(charSequence, intValue, ((Number) apply1).intValue());
                                objArr2[2] = pregexpReplaceAux(str, ins, Integer.valueOf(ins$Mnlen), pp);
                                obj2 = strings.stringAppend(objArr2);
                                obj = i;
                            } catch (ClassCastException e22) {
                                throw new WrongType(e22, "substring", 3, apply1);
                            }
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "substring", 2, obj);
                        }
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "substring", 1, str);
                    }
                }
                return obj2;
            } catch (ClassCastException e22222) {
                throw new WrongType(e22222, "string-length", 1, ins);
            }
        } catch (ClassCastException e222222) {
            throw new WrongType(e222222, "string-length", 1, str);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 18:
                return pregexpReadPattern(obj, obj2, obj3);
            case 19:
                return pregexpReadBranch(obj, obj2, obj3);
            case 20:
                return pregexpReadPiece(obj, obj2, obj3);
            case 21:
                return pregexpReadEscapedNumber(obj, obj2, obj3);
            case 22:
                return pregexpReadEscapedChar(obj, obj2, obj3);
            case 23:
                return pregexpReadPosixCharClass(obj, obj2, obj3);
            case 24:
                return pregexpReadClusterType(obj, obj2, obj3);
            case 25:
                return pregexpReadSubpattern(obj, obj2, obj3);
            case 26:
                return pregexpWrapQuantifierIfAny(obj, obj2, obj3);
            case 27:
                return pregexpReadNums(obj, obj2, obj3);
            case 29:
                return pregexpReadCharList(obj, obj2, obj3);
            case 32:
                return isPregexpAtWordBoundary(obj, obj2, obj3);
            case 48:
                return pregexpReplace(obj, obj2, obj3);
            case 49:
                return pregexpReplace$St(obj, obj2, obj3);
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static Object pregexpQuote(Object s) {
        try {
            Object valueOf = Integer.valueOf(strings.stringLength((CharSequence) s) - 1);
            Object obj = LList.Empty;
            while (Scheme.numLss.apply2(valueOf, Lit73) == Boolean.FALSE) {
                Object i = AddOp.$Mn.apply2(valueOf, Lit8);
                try {
                    try {
                        Pair r;
                        char c = strings.stringRef((CharSequence) s, ((Number) valueOf).intValue());
                        if (lists.memv(Char.make(c), Lit116) != Boolean.FALSE) {
                            r = lists.cons(Lit19, lists.cons(Char.make(c), obj));
                        } else {
                            r = lists.cons(Char.make(c), obj);
                        }
                        Pair pair = r;
                        valueOf = i;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-ref", 2, valueOf);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "string-ref", 1, s);
                }
            }
            try {
                return strings.list$To$String((LList) obj);
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "list->string", 1, obj);
            }
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "string-length", 1, s);
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 16:
                return pregexpReverse$Ex(obj);
            case 28:
                return pregexpInvertCharList(obj);
            case 31:
                return isPregexpCharWord(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 35:
                return pregexpMakeBackrefList(obj);
            case 44:
                return pregexp(obj);
            case 50:
                return pregexpQuote(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}

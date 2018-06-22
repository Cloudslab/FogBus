package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.IsEqual;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Char;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.call_with_values;

/* compiled from: srfi37.scm */
public class srfi37 extends ModuleBody {
    public static final srfi37 $instance = new srfi37();
    static final IntNum Lit0 = IntNum.make(1);
    static final Char Lit1 = Char.make(45);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("option-processor").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("args-fold").readResolve());
    static final Char Lit2 = Char.make(61);
    static final IntNum Lit3 = IntNum.make(3);
    static final IntNum Lit4 = IntNum.make(0);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("option?").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("option").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("option-names").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("option-required-arg?").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("option-optional-arg?").readResolve());
    public static final ModuleMethod args$Mnfold;
    public static final ModuleMethod option;
    public static final ModuleMethod option$Mnnames;
    public static final ModuleMethod option$Mnoptional$Mnarg$Qu;
    public static final ModuleMethod option$Mnprocessor;
    public static final ModuleMethod option$Mnrequired$Mnarg$Qu;
    static final Class option$Mntype = option$Mntype.class;
    public static final ModuleMethod option$Qu;

    /* compiled from: srfi37.scm */
    public class frame0 extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        final ModuleMethod lambda$Fn2;
        Object name;
        frame staticLink;

        public frame0() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi37.scm:75");
            this.lambda$Fn2 = moduleMethod;
            moduleMethod = new ModuleMethod(this, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/srfi37.scm:72");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            switch (moduleMethod.selector) {
                case 1:
                    return lambda7(obj) ? Boolean.TRUE : Boolean.FALSE;
                case 2:
                    return lambda6(obj);
                default:
                    return super.apply1(moduleMethod, obj);
            }
        }

        Object lambda6(Object option) {
            try {
                return frame.lambda1find(srfi37.optionNames((option$Mntype) option), this.lambda$Fn2);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-names", 0, option);
            }
        }

        boolean lambda7(Object test$Mnname) {
            return IsEqual.apply(this.name, test$Mnname);
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
                default:
                    return super.match1(moduleMethod, obj, callContext);
            }
        }
    }

    /* compiled from: srfi37.scm */
    public class frame1 extends ModuleBody {
        Object args;
        Object index;
        final ModuleMethod lambda$Fn3 = new ModuleMethod(this, 3, null, 0);
        final ModuleMethod lambda$Fn4 = new ModuleMethod(this, 4, null, -4096);
        final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 5, null, 0);
        final ModuleMethod lambda$Fn6 = new ModuleMethod(this, 6, null, -4096);
        final ModuleMethod lambda$Fn7 = new ModuleMethod(this, 7, null, 0);
        final ModuleMethod lambda$Fn8 = new ModuleMethod(this, 8, null, -4096);
        char name;
        Object option;
        Object seeds;
        Object shorts;
        frame staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 3:
                    return lambda8();
                case 5:
                    return lambda10();
                case 7:
                    return lambda12();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            switch (moduleMethod.selector) {
                case 4:
                    return lambda9$V(objArr);
                case 6:
                    return lambda11$V(objArr);
                case 8:
                    return lambda13$V(objArr);
                default:
                    return super.applyN(moduleMethod, objArr);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 3:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 5:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 7:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 4:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 6:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 8:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                default:
                    return super.matchN(moduleMethod, objArr, callContext);
            }
        }

        Object lambda9$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(this.args, LList.makeList(argsArray, 0));
        }

        Object lambda8() {
            Procedure procedure = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = Char.make(this.name);
                obj = this.shorts;
                try {
                    CharSequence charSequence = (CharSequence) obj;
                    Object apply2 = AddOp.$Pl.apply2(this.index, srfi37.Lit0);
                    try {
                        int intValue = ((Number) apply2).intValue();
                        Object obj2 = this.shorts;
                        try {
                            objArr[3] = strings.substring(charSequence, intValue, strings.stringLength((CharSequence) obj2));
                            objArr[4] = this.seeds;
                            return procedure.applyN(objArr);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "string-length", 1, obj2);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "substring", 2, apply2);
                    }
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "substring", 1, obj);
                }
            } catch (ClassCastException e32) {
                throw new WrongType(e32, "option-processor", 0, obj);
            }
        }

        Object lambda11$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(lists.cdr.apply1(this.args), LList.makeList(argsArray, 0));
        }

        Object lambda10() {
            Procedure procedure = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = Char.make(this.name);
                objArr[3] = lists.car.apply1(this.args);
                objArr[4] = this.seeds;
                return procedure.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }

        Object lambda13$V(Object[] argsArray) {
            return this.staticLink.lambda3scanShortOptions(AddOp.$Pl.apply2(this.index, srfi37.Lit0), this.shorts, this.args, LList.makeList(argsArray, 0));
        }

        Object lambda12() {
            Procedure procedure = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = Char.make(this.name);
                objArr[3] = Boolean.FALSE;
                objArr[4] = this.seeds;
                return procedure.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }
    }

    /* compiled from: srfi37.scm */
    public class frame2 extends ModuleBody {
        final ModuleMethod lambda$Fn10 = new ModuleMethod(this, 10, null, -4096);
        final ModuleMethod lambda$Fn9 = new ModuleMethod(this, 9, null, 0);
        Object operands;
        Object seeds;
        frame staticLink;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 9 ? lambda14() : super.apply0(moduleMethod);
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            return moduleMethod.selector == 10 ? lambda15$V(objArr) : super.applyN(moduleMethod, objArr);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 9) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            if (moduleMethod.selector != 10) {
                return super.matchN(moduleMethod, objArr, callContext);
            }
            callContext.values = objArr;
            callContext.proc = moduleMethod;
            callContext.pc = 5;
            return 0;
        }

        Object lambda14() {
            return Scheme.apply.apply3(this.staticLink.operand$Mnproc, lists.car.apply1(this.operands), this.seeds);
        }

        Object lambda15$V(Object[] argsArray) {
            return this.staticLink.lambda4scanOperands(lists.cdr.apply1(this.operands), LList.makeList(argsArray, 0));
        }
    }

    /* compiled from: srfi37.scm */
    public class frame3 extends ModuleBody {
        Object arg;
        Object args;
        final ModuleMethod lambda$Fn11 = new ModuleMethod(this, 17, null, 0);
        final ModuleMethod lambda$Fn12 = new ModuleMethod(this, 18, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        final ModuleMethod lambda$Fn19 = new ModuleMethod(this, 19, null, 0);
        final ModuleMethod lambda$Fn20 = new ModuleMethod(this, 20, null, -4096);
        final ModuleMethod lambda$Fn21 = new ModuleMethod(this, 21, null, 0);
        final ModuleMethod lambda$Fn22 = new ModuleMethod(this, 22, null, -4096);
        final ModuleMethod lambda$Fn23 = new ModuleMethod(this, 23, null, 0);
        final ModuleMethod lambda$Fn24 = new ModuleMethod(this, 24, null, -4096);
        CharSequence name;
        Object option;
        Object seeds;
        frame staticLink;
        Object temp;

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 17:
                    return lambda16();
                case 19:
                    return lambda24();
                case 21:
                    return lambda26();
                case 23:
                    return lambda28();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 18 ? lambda17(obj) : super.apply1(moduleMethod, obj);
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            switch (moduleMethod.selector) {
                case 20:
                    return lambda25$V(objArr);
                case 22:
                    return lambda27$V(objArr);
                case 24:
                    return lambda29$V(objArr);
                default:
                    return super.applyN(moduleMethod, objArr);
            }
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 17:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 19:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 21:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 23:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                default:
                    return super.match0(moduleMethod, callContext);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 18) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 20:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 22:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                case 24:
                    callContext.values = objArr;
                    callContext.proc = moduleMethod;
                    callContext.pc = 5;
                    return 0;
                default:
                    return super.matchN(moduleMethod, objArr, callContext);
            }
        }

        Object lambda17(Object x) {
            frame4 gnu_kawa_slib_srfi37_frame4 = new frame4();
            gnu_kawa_slib_srfi37_frame4.staticLink = this;
            gnu_kawa_slib_srfi37_frame4.f96x = x;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi37_frame4.lambda$Fn13, gnu_kawa_slib_srfi37_frame4.lambda$Fn14);
        }

        CharSequence lambda16() {
            Object obj = this.arg;
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object obj2 = this.temp;
                try {
                    return strings.substring(charSequence, 2, ((Number) obj2).intValue());
                } catch (ClassCastException e) {
                    throw new WrongType(e, "substring", 3, obj2);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "substring", 1, obj);
            }
        }

        Object lambda25$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(lists.cdr.apply1(this.args), LList.makeList(argsArray, 0));
        }

        Object lambda24() {
            Procedure procedure = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = this.name;
                objArr[3] = lists.car.apply1(this.args);
                objArr[4] = this.seeds;
                return procedure.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }

        Object lambda27$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(this.args, LList.makeList(argsArray, 0));
        }

        Object lambda26() {
            Procedure procedure = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.option;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.option;
                objArr[2] = this.name;
                objArr[3] = Boolean.FALSE;
                objArr[4] = this.seeds;
                return procedure.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }

        Object lambda28() {
            return Scheme.apply.apply3(this.staticLink.operand$Mnproc, this.arg, this.seeds);
        }

        Object lambda29$V(Object[] argsArray) {
            return this.staticLink.lambda5scanArgs(this.args, LList.makeList(argsArray, 0));
        }
    }

    /* compiled from: srfi37.scm */
    public class frame4 extends ModuleBody {
        final ModuleMethod lambda$Fn13 = new ModuleMethod(this, 15, null, 0);
        final ModuleMethod lambda$Fn14 = new ModuleMethod(this, 16, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        frame3 staticLink;
        Object f96x;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 15 ? lambda18() : super.apply0(moduleMethod);
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 16 ? lambda19(obj) : super.apply1(moduleMethod, obj);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 15) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 16) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        Object lambda19(Object x) {
            frame5 gnu_kawa_slib_srfi37_frame5 = new frame5();
            gnu_kawa_slib_srfi37_frame5.staticLink = this;
            gnu_kawa_slib_srfi37_frame5.f97x = x;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi37_frame5.lambda$Fn15, gnu_kawa_slib_srfi37_frame5.lambda$Fn16);
        }

        CharSequence lambda18() {
            Object obj = this.staticLink.arg;
            try {
                CharSequence charSequence = (CharSequence) obj;
                Object apply2 = AddOp.$Pl.apply2(this.staticLink.temp, srfi37.Lit0);
                try {
                    int intValue = ((Number) apply2).intValue();
                    Object obj2 = this.staticLink.arg;
                    try {
                        return strings.substring(charSequence, intValue, strings.stringLength((CharSequence) obj2));
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "string-length", 1, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "substring", 2, apply2);
                }
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "substring", 1, obj);
            }
        }
    }

    /* compiled from: srfi37.scm */
    public class frame5 extends ModuleBody {
        final ModuleMethod lambda$Fn15 = new ModuleMethod(this, 13, null, 0);
        final ModuleMethod lambda$Fn16 = new ModuleMethod(this, 14, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        frame4 staticLink;
        Object f97x;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 13 ? lambda20() : super.apply0(moduleMethod);
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 14 ? lambda21(obj) : super.apply1(moduleMethod, obj);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 13) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 14) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }

        Object lambda21(Object x) {
            frame6 gnu_kawa_slib_srfi37_frame6 = new frame6();
            gnu_kawa_slib_srfi37_frame6.staticLink = this;
            gnu_kawa_slib_srfi37_frame6.f98x = x;
            return call_with_values.callWithValues(gnu_kawa_slib_srfi37_frame6.lambda$Fn17, gnu_kawa_slib_srfi37_frame6.lambda$Fn18);
        }

        Object lambda20() {
            Boolean x = this.staticLink.staticLink.staticLink.lambda2findOption(this.staticLink.f96x);
            return x != Boolean.FALSE ? x : srfi37.option(LList.list1(this.staticLink.f96x), Boolean.TRUE, Boolean.FALSE, this.staticLink.staticLink.staticLink.unrecognized$Mnoption$Mnproc);
        }
    }

    /* compiled from: srfi37.scm */
    public class frame6 extends ModuleBody {
        final ModuleMethod lambda$Fn17 = new ModuleMethod(this, 11, null, 0);
        final ModuleMethod lambda$Fn18 = new ModuleMethod(this, 12, null, -4096);
        frame5 staticLink;
        Object f98x;

        public Object apply0(ModuleMethod moduleMethod) {
            return moduleMethod.selector == 11 ? lambda22() : super.apply0(moduleMethod);
        }

        public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
            return moduleMethod.selector == 12 ? lambda23$V(objArr) : super.applyN(moduleMethod, objArr);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            if (moduleMethod.selector != 11) {
                return super.match0(moduleMethod, callContext);
            }
            callContext.proc = moduleMethod;
            callContext.pc = 0;
            return 0;
        }

        public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
            if (moduleMethod.selector != 12) {
                return super.matchN(moduleMethod, objArr, callContext);
            }
            callContext.values = objArr;
            callContext.proc = moduleMethod;
            callContext.pc = 5;
            return 0;
        }

        Object lambda23$V(Object[] argsArray) {
            return this.staticLink.staticLink.staticLink.staticLink.lambda5scanArgs(this.staticLink.staticLink.staticLink.args, LList.makeList(argsArray, 0));
        }

        Object lambda22() {
            Procedure procedure = Scheme.apply;
            Object[] objArr = new Object[5];
            Object obj = this.f98x;
            try {
                objArr[0] = srfi37.optionProcessor((option$Mntype) obj);
                objArr[1] = this.f98x;
                objArr[2] = this.staticLink.staticLink.f96x;
                objArr[3] = this.staticLink.f97x;
                objArr[4] = this.staticLink.staticLink.staticLink.seeds;
                return procedure.applyN(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "option-processor", 0, obj);
            }
        }
    }

    /* compiled from: srfi37.scm */
    public class frame extends ModuleBody {
        Object operand$Mnproc;
        Object options;
        Object unrecognized$Mnoption$Mnproc;

        public static Object lambda1find(Object l, Object $Qu) {
            if (lists.isNull(l)) {
                return Boolean.FALSE;
            }
            if (Scheme.applyToArgs.apply2($Qu, lists.car.apply1(l)) != Boolean.FALSE) {
                return lists.car.apply1(l);
            }
            return lambda1find(lists.cdr.apply1(l), $Qu);
        }

        public Object lambda2findOption(Object name) {
            frame0 gnu_kawa_slib_srfi37_frame0 = new frame0();
            gnu_kawa_slib_srfi37_frame0.staticLink = this;
            gnu_kawa_slib_srfi37_frame0.name = name;
            return lambda1find(this.options, gnu_kawa_slib_srfi37_frame0.lambda$Fn1);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda3scanShortOptions(java.lang.Object r10, java.lang.Object r11, java.lang.Object r12, java.lang.Object r13) {
            /*
            r9 = this;
            r8 = 1;
            r7 = 0;
            r5 = new gnu.kawa.slib.srfi37$frame1;
            r5.<init>();
            r5.staticLink = r9;
            r5.index = r10;
            r5.shorts = r11;
            r5.args = r12;
            r5.seeds = r13;
            r3 = kawa.standard.Scheme.numEqu;
            r4 = r5.index;
            r2 = r5.shorts;
            r2 = (java.lang.CharSequence) r2;	 Catch:{ ClassCastException -> 0x00e5 }
            r2 = kawa.lib.strings.stringLength(r2);
            r2 = java.lang.Integer.valueOf(r2);
            r2 = r3.apply2(r4, r2);
            r3 = java.lang.Boolean.FALSE;
            if (r2 == r3) goto L_0x0032;
        L_0x0029:
            r2 = r5.args;
            r3 = r5.seeds;
            r2 = r9.lambda5scanArgs(r2, r3);
        L_0x0031:
            return r2;
        L_0x0032:
            r2 = r5.shorts;
            r2 = (java.lang.CharSequence) r2;	 Catch:{ ClassCastException -> 0x00ee }
            r4 = r5.index;
            r0 = r4;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x00f7 }
            r3 = r0;
            r3 = r3.intValue();	 Catch:{ ClassCastException -> 0x00f7 }
            r2 = kawa.lib.strings.stringRef(r2, r3);
            r5.name = r2;
            r2 = r5.name;
            r2 = gnu.text.Char.make(r2);
            r1 = r9.lambda2findOption(r2);
            r2 = java.lang.Boolean.FALSE;
            if (r1 == r2) goto L_0x0095;
        L_0x0054:
            r5.option = r1;
            r3 = kawa.standard.Scheme.numLss;
            r2 = gnu.kawa.functions.AddOp.$Pl;
            r4 = r5.index;
            r6 = gnu.kawa.slib.srfi37.Lit0;
            r4 = r2.apply2(r4, r6);
            r2 = r5.shorts;
            r2 = (java.lang.CharSequence) r2;	 Catch:{ ClassCastException -> 0x0101 }
            r2 = kawa.lib.strings.stringLength(r2);
            r2 = java.lang.Integer.valueOf(r2);
            r3 = r3.apply2(r4, r2);
            r0 = r3;
            r0 = (java.lang.Boolean) r0;	 Catch:{ ClassCastException -> 0x010a }
            r2 = r0;
            r1 = r2.booleanValue();	 Catch:{ ClassCastException -> 0x010a }
            if (r1 == 0) goto L_0x00d4;
        L_0x007c:
            r2 = r5.option;
            r2 = (gnu.kawa.slib.option$Mntype) r2;	 Catch:{ ClassCastException -> 0x0114 }
            r1 = gnu.kawa.slib.srfi37.isOptionRequiredArg(r2);
            r2 = java.lang.Boolean.FALSE;
            if (r1 == r2) goto L_0x00aa;
        L_0x0088:
            r2 = java.lang.Boolean.FALSE;
            if (r1 == r2) goto L_0x00b6;
        L_0x008c:
            r2 = r5.lambda$Fn3;
            r3 = r5.lambda$Fn4;
            r2 = kawa.standard.call_with_values.callWithValues(r2, r3);
            goto L_0x0031;
        L_0x0095:
            r2 = r5.name;
            r2 = gnu.text.Char.make(r2);
            r2 = gnu.lists.LList.list1(r2);
            r3 = java.lang.Boolean.FALSE;
            r4 = java.lang.Boolean.FALSE;
            r6 = r9.unrecognized$Mnoption$Mnproc;
            r1 = gnu.kawa.slib.srfi37.option(r2, r3, r4, r6);
            goto L_0x0054;
        L_0x00aa:
            r2 = r5.option;
            r2 = (gnu.kawa.slib.option$Mntype) r2;	 Catch:{ ClassCastException -> 0x011d }
            r2 = gnu.kawa.slib.srfi37.isOptionOptionalArg(r2);
            r3 = java.lang.Boolean.FALSE;
            if (r2 != r3) goto L_0x008c;
        L_0x00b6:
            r2 = r5.option;
            r2 = (gnu.kawa.slib.option$Mntype) r2;	 Catch:{ ClassCastException -> 0x0126 }
            r1 = gnu.kawa.slib.srfi37.isOptionRequiredArg(r2);
            r2 = java.lang.Boolean.FALSE;
            if (r1 == r2) goto L_0x00d7;
        L_0x00c2:
            r2 = r5.args;
            r2 = kawa.lib.lists.isPair(r2);
            if (r2 == 0) goto L_0x00db;
        L_0x00ca:
            r2 = r5.lambda$Fn5;
            r3 = r5.lambda$Fn6;
            r2 = kawa.standard.call_with_values.callWithValues(r2, r3);
            goto L_0x0031;
        L_0x00d4:
            if (r1 == 0) goto L_0x00b6;
        L_0x00d6:
            goto L_0x008c;
        L_0x00d7:
            r2 = java.lang.Boolean.FALSE;
            if (r1 != r2) goto L_0x00ca;
        L_0x00db:
            r2 = r5.lambda$Fn7;
            r3 = r5.lambda$Fn8;
            r2 = kawa.standard.call_with_values.callWithValues(r2, r3);
            goto L_0x0031;
        L_0x00e5:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "string-length";
            r4.<init>(r3, r5, r8, r2);
            throw r4;
        L_0x00ee:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "string-ref";
            r4.<init>(r3, r5, r8, r2);
            throw r4;
        L_0x00f7:
            r2 = move-exception;
            r3 = new gnu.mapping.WrongType;
            r5 = "string-ref";
            r6 = 2;
            r3.<init>(r2, r5, r6, r4);
            throw r3;
        L_0x0101:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "string-length";
            r4.<init>(r3, r5, r8, r2);
            throw r4;
        L_0x010a:
            r2 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "x";
            r6 = -2;
            r4.<init>(r2, r5, r6, r3);
            throw r4;
        L_0x0114:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "option-required-arg?";
            r4.<init>(r3, r5, r7, r2);
            throw r4;
        L_0x011d:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "option-optional-arg?";
            r4.<init>(r3, r5, r7, r2);
            throw r4;
        L_0x0126:
            r3 = move-exception;
            r4 = new gnu.mapping.WrongType;
            r5 = "option-required-arg?";
            r4.<init>(r3, r5, r7, r2);
            throw r4;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi37.frame.lambda3scanShortOptions(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
        }

        public Object lambda4scanOperands(Object operands, Object seeds) {
            frame2 gnu_kawa_slib_srfi37_frame2 = new frame2();
            gnu_kawa_slib_srfi37_frame2.staticLink = this;
            gnu_kawa_slib_srfi37_frame2.operands = operands;
            gnu_kawa_slib_srfi37_frame2.seeds = seeds;
            if (lists.isNull(gnu_kawa_slib_srfi37_frame2.operands)) {
                return Scheme.apply.apply2(misc.values, gnu_kawa_slib_srfi37_frame2.seeds);
            }
            return call_with_values.callWithValues(gnu_kawa_slib_srfi37_frame2.lambda$Fn9, gnu_kawa_slib_srfi37_frame2.lambda$Fn10);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Object lambda5scanArgs(java.lang.Object r12, java.lang.Object r13) {
            /*
            r11 = this;
            r10 = 2;
            r7 = 0;
            r6 = 1;
            r8 = new gnu.kawa.slib.srfi37$frame3;
            r8.<init>();
            r8.staticLink = r11;
            r8.seeds = r13;
            r4 = kawa.lib.lists.isNull(r12);
            if (r4 == 0) goto L_0x001d;
        L_0x0012:
            r4 = kawa.standard.Scheme.apply;
            r5 = kawa.lib.misc.values;
            r6 = r8.seeds;
            r4 = r4.apply2(r5, r6);
        L_0x001c:
            return r4;
        L_0x001d:
            r4 = kawa.lib.lists.car;
            r4 = r4.apply1(r12);
            r5 = kawa.lib.lists.cdr;
            r5 = r5.apply1(r12);
            r8.args = r5;
            r8.arg = r4;
            r4 = "--";
            r5 = r8.arg;
            r4 = kawa.lib.strings.isString$Eq(r4, r5);
            if (r4 == 0) goto L_0x0040;
        L_0x0037:
            r4 = r8.args;
            r5 = r8.seeds;
            r4 = r11.lambda4scanOperands(r4, r5);
            goto L_0x001c;
        L_0x0040:
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x01e4 }
            r4 = kawa.lib.strings.stringLength(r4);
            r5 = 4;
            if (r4 <= r5) goto L_0x00ba;
        L_0x004b:
            r3 = r6;
        L_0x004c:
            if (r3 == 0) goto L_0x00f9;
        L_0x004e:
            r5 = gnu.kawa.slib.srfi37.Lit1;
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x01ed }
            r4 = kawa.lib.strings.stringRef(r4, r7);
            r4 = gnu.text.Char.make(r4);
            r3 = kawa.lib.characters.isChar$Eq(r5, r4);
            if (r3 == 0) goto L_0x00f1;
        L_0x0062:
            r5 = gnu.kawa.slib.srfi37.Lit1;
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x01f6 }
            r4 = kawa.lib.strings.stringRef(r4, r6);
            r4 = gnu.text.Char.make(r4);
            r3 = kawa.lib.characters.isChar$Eq(r5, r4);
            if (r3 == 0) goto L_0x00e9;
        L_0x0076:
            r5 = gnu.kawa.slib.srfi37.Lit2;
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x01ff }
            r4 = kawa.lib.strings.stringRef(r4, r10);
            r4 = gnu.text.Char.make(r4);
            r4 = kawa.lib.characters.isChar$Eq(r5, r4);
            r4 = r4 + 1;
            r3 = r4 & 1;
            if (r3 == 0) goto L_0x00e1;
        L_0x008e:
            r1 = gnu.kawa.slib.srfi37.Lit3;
        L_0x0090:
            r5 = kawa.standard.Scheme.numEqu;
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x0208 }
            r4 = kawa.lib.strings.stringLength(r4);
            r4 = java.lang.Integer.valueOf(r4);
            r4 = r5.apply2(r1, r4);
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x00bc;
        L_0x00a6:
            r1 = java.lang.Boolean.FALSE;
        L_0x00a8:
            r8.temp = r1;
            r4 = r8.temp;
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x0101;
        L_0x00b0:
            r4 = r8.lambda$Fn11;
            r5 = r8.lambda$Fn12;
            r4 = kawa.standard.call_with_values.callWithValues(r4, r5);
            goto L_0x001c;
        L_0x00ba:
            r3 = r7;
            goto L_0x004c;
        L_0x00bc:
            r9 = gnu.kawa.slib.srfi37.Lit2;
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x0211 }
            r0 = r1;
            r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x021a }
            r5 = r0;
            r5 = r5.intValue();	 Catch:{ ClassCastException -> 0x021a }
            r4 = kawa.lib.strings.stringRef(r4, r5);
            r4 = gnu.text.Char.make(r4);
            r4 = kawa.lib.characters.isChar$Eq(r9, r4);
            if (r4 != 0) goto L_0x00a8;
        L_0x00d8:
            r4 = gnu.kawa.functions.AddOp.$Pl;
            r5 = gnu.kawa.slib.srfi37.Lit0;
            r1 = r4.apply2(r5, r1);
            goto L_0x0090;
        L_0x00e1:
            if (r3 == 0) goto L_0x00e6;
        L_0x00e3:
            r1 = java.lang.Boolean.TRUE;
            goto L_0x00a8;
        L_0x00e6:
            r1 = java.lang.Boolean.FALSE;
            goto L_0x00a8;
        L_0x00e9:
            if (r3 == 0) goto L_0x00ee;
        L_0x00eb:
            r1 = java.lang.Boolean.TRUE;
            goto L_0x00a8;
        L_0x00ee:
            r1 = java.lang.Boolean.FALSE;
            goto L_0x00a8;
        L_0x00f1:
            if (r3 == 0) goto L_0x00f6;
        L_0x00f3:
            r1 = java.lang.Boolean.TRUE;
            goto L_0x00a8;
        L_0x00f6:
            r1 = java.lang.Boolean.FALSE;
            goto L_0x00a8;
        L_0x00f9:
            if (r3 == 0) goto L_0x00fe;
        L_0x00fb:
            r1 = java.lang.Boolean.TRUE;
            goto L_0x00a8;
        L_0x00fe:
            r1 = java.lang.Boolean.FALSE;
            goto L_0x00a8;
        L_0x0101:
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x0223 }
            r4 = kawa.lib.strings.stringLength(r4);
            r5 = 3;
            if (r4 <= r5) goto L_0x0173;
        L_0x010c:
            r3 = r6;
        L_0x010d:
            if (r3 == 0) goto L_0x01b4;
        L_0x010f:
            r5 = gnu.kawa.slib.srfi37.Lit1;
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x022c }
            r4 = kawa.lib.strings.stringRef(r4, r7);
            r4 = gnu.text.Char.make(r4);
            r3 = kawa.lib.characters.isChar$Eq(r5, r4);
            if (r3 == 0) goto L_0x0175;
        L_0x0123:
            r5 = gnu.kawa.slib.srfi37.Lit1;
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x0235 }
            r4 = kawa.lib.strings.stringRef(r4, r6);
            r4 = gnu.text.Char.make(r4);
            r4 = kawa.lib.characters.isChar$Eq(r5, r4);
            if (r4 == 0) goto L_0x0177;
        L_0x0137:
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x023e }
            r5 = r8.arg;
            r5 = (java.lang.CharSequence) r5;	 Catch:{ ClassCastException -> 0x0247 }
            r5 = kawa.lib.strings.stringLength(r5);
            r4 = kawa.lib.strings.substring(r4, r10, r5);
            r8.name = r4;
            r4 = r8.name;
            r3 = r11.lambda2findOption(r4);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x01b7;
        L_0x0153:
            r8.option = r3;
            r4 = r8.option;
            r4 = (gnu.kawa.slib.option$Mntype) r4;	 Catch:{ ClassCastException -> 0x0250 }
            r3 = gnu.kawa.slib.srfi37.isOptionRequiredArg(r4);
            r4 = java.lang.Boolean.FALSE;
            if (r3 == r4) goto L_0x01c8;
        L_0x0161:
            r4 = r8.args;
            r4 = kawa.lib.lists.isPair(r4);
            if (r4 == 0) goto L_0x01cc;
        L_0x0169:
            r4 = r8.lambda$Fn19;
            r5 = r8.lambda$Fn20;
            r4 = kawa.standard.call_with_values.callWithValues(r4, r5);
            goto L_0x001c;
        L_0x0173:
            r3 = r7;
            goto L_0x010d;
        L_0x0175:
            if (r3 != 0) goto L_0x0137;
        L_0x0177:
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x0259 }
            r4 = kawa.lib.strings.stringLength(r4);
            if (r4 <= r6) goto L_0x01d6;
        L_0x0181:
            r3 = r6;
        L_0x0182:
            if (r3 == 0) goto L_0x01d8;
        L_0x0184:
            r5 = gnu.kawa.slib.srfi37.Lit1;
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x0262 }
            r4 = kawa.lib.strings.stringRef(r4, r7);
            r4 = gnu.text.Char.make(r4);
            r4 = kawa.lib.characters.isChar$Eq(r5, r4);
            if (r4 == 0) goto L_0x01da;
        L_0x0198:
            r4 = r8.arg;
            r4 = (java.lang.CharSequence) r4;	 Catch:{ ClassCastException -> 0x026b }
            r5 = r8.arg;
            r5 = (java.lang.CharSequence) r5;	 Catch:{ ClassCastException -> 0x0274 }
            r5 = kawa.lib.strings.stringLength(r5);
            r2 = kawa.lib.strings.substring(r4, r6, r5);
            r4 = gnu.kawa.slib.srfi37.Lit4;
            r5 = r8.args;
            r6 = r8.seeds;
            r4 = r11.lambda3scanShortOptions(r4, r2, r5, r6);
            goto L_0x001c;
        L_0x01b4:
            if (r3 == 0) goto L_0x0177;
        L_0x01b6:
            goto L_0x0137;
        L_0x01b7:
            r4 = r8.name;
            r4 = gnu.lists.LList.list1(r4);
            r5 = java.lang.Boolean.FALSE;
            r6 = java.lang.Boolean.FALSE;
            r9 = r11.unrecognized$Mnoption$Mnproc;
            r3 = gnu.kawa.slib.srfi37.option(r4, r5, r6, r9);
            goto L_0x0153;
        L_0x01c8:
            r4 = java.lang.Boolean.FALSE;
            if (r3 != r4) goto L_0x0169;
        L_0x01cc:
            r4 = r8.lambda$Fn21;
            r5 = r8.lambda$Fn22;
            r4 = kawa.standard.call_with_values.callWithValues(r4, r5);
            goto L_0x001c;
        L_0x01d6:
            r3 = r7;
            goto L_0x0182;
        L_0x01d8:
            if (r3 != 0) goto L_0x0198;
        L_0x01da:
            r4 = r8.lambda$Fn23;
            r5 = r8.lambda$Fn24;
            r4 = kawa.standard.call_with_values.callWithValues(r4, r5);
            goto L_0x001c;
        L_0x01e4:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-length";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x01ed:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x01f6:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x01ff:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x0208:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-length";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x0211:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x021a:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "string-ref";
            r5.<init>(r4, r6, r10, r1);
            throw r5;
        L_0x0223:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-length";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x022c:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x0235:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x023e:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "substring";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x0247:
            r4 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-length";
            r7.<init>(r4, r8, r6, r5);
            throw r7;
        L_0x0250:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r8 = "option-required-arg?";
            r6.<init>(r5, r8, r7, r4);
            throw r6;
        L_0x0259:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-length";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x0262:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-ref";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x026b:
            r5 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "substring";
            r7.<init>(r5, r8, r6, r4);
            throw r7;
        L_0x0274:
            r4 = move-exception;
            r7 = new gnu.mapping.WrongType;
            r8 = "string-length";
            r7.<init>(r4, r8, r6, r5);
            throw r7;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.srfi37.frame.lambda5scanArgs(java.lang.Object, java.lang.Object):java.lang.Object");
        }
    }

    static {
        ModuleBody moduleBody = $instance;
        option$Qu = new ModuleMethod(moduleBody, 25, Lit5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        option = new ModuleMethod(moduleBody, 26, Lit6, 16388);
        option$Mnnames = new ModuleMethod(moduleBody, 27, Lit7, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        option$Mnrequired$Mnarg$Qu = new ModuleMethod(moduleBody, 28, Lit8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        option$Mnoptional$Mnarg$Qu = new ModuleMethod(moduleBody, 29, Lit9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        option$Mnprocessor = new ModuleMethod(moduleBody, 30, Lit10, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        args$Mnfold = new ModuleMethod(moduleBody, 31, Lit11, -4092);
        $instance.run();
    }

    public srfi37() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static option$Mntype option(Object names, Object required$Mnarg$Qu, Object optional$Mnarg$Qu, Object processor) {
        option$Mntype tmp = new option$Mntype();
        tmp.names = names;
        tmp.required$Mnarg$Qu = required$Mnarg$Qu;
        tmp.optional$Mnarg$Qu = optional$Mnarg$Qu;
        tmp.processor = processor;
        return tmp;
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        return moduleMethod.selector == 26 ? option(obj, obj2, obj3, obj4) : super.apply4(moduleMethod, obj, obj2, obj3, obj4);
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 26) {
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

    public static boolean isOption(Object obj) {
        return obj instanceof option$Mntype;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 25:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 27:
                if (!(obj instanceof option$Mntype)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                if (!(obj instanceof option$Mntype)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                if (!(obj instanceof option$Mntype)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 30:
                if (!(obj instanceof option$Mntype)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object optionNames(option$Mntype obj) {
        return obj.names;
    }

    public static Object isOptionRequiredArg(option$Mntype obj) {
        return obj.required$Mnarg$Qu;
    }

    public static Object isOptionOptionalArg(option$Mntype obj) {
        return obj.optional$Mnarg$Qu;
    }

    public static Object optionProcessor(option$Mntype obj) {
        return obj.processor;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 25:
                return isOption(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 27:
                try {
                    return optionNames((option$Mntype) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "option-names", 1, obj);
                }
            case 28:
                try {
                    return isOptionRequiredArg((option$Mntype) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "option-required-arg?", 1, obj);
                }
            case 29:
                try {
                    return isOptionOptionalArg((option$Mntype) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "option-optional-arg?", 1, obj);
                }
            case 30:
                try {
                    return optionProcessor((option$Mntype) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "option-processor", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static Object argsFold$V(Object args, Object options, Object unrecognizedOptionProc, Object operandProc, Object[] argsArray) {
        frame gnu_kawa_slib_srfi37_frame = new frame();
        gnu_kawa_slib_srfi37_frame.options = options;
        gnu_kawa_slib_srfi37_frame.unrecognized$Mnoption$Mnproc = unrecognizedOptionProc;
        gnu_kawa_slib_srfi37_frame.operand$Mnproc = operandProc;
        return gnu_kawa_slib_srfi37_frame.lambda5scanArgs(args, LList.makeList(argsArray, 0));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        if (moduleMethod.selector != 31) {
            return super.applyN(moduleMethod, objArr);
        }
        Object obj = objArr[0];
        Object obj2 = objArr[1];
        Object obj3 = objArr[2];
        Object obj4 = objArr[3];
        int length = objArr.length - 4;
        Object[] objArr2 = new Object[length];
        while (true) {
            length--;
            if (length < 0) {
                return argsFold$V(obj, obj2, obj3, obj4, objArr2);
            }
            objArr2[length] = objArr[length + 4];
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        if (moduleMethod.selector != 31) {
            return super.matchN(moduleMethod, objArr, callContext);
        }
        callContext.values = objArr;
        callContext.proc = moduleMethod;
        callContext.pc = 5;
        return 0;
    }
}

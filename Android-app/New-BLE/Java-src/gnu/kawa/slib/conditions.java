package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.slib.condition.Mntype;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: conditions.scm */
public class conditions extends ModuleBody {
    public static Object $Amcondition;
    public static Object $Amerror;
    public static Object $Ammessage;
    public static Object $Amserious;
    static final Class $Lscondition$Mntype$Gr = Mntype.class;
    public static final Class $Prvt$$Lscondition$Gr = condition.class;
    public static final ModuleMethod $Prvt$type$Mnfield$Mnalist$Mn$Grcondition;
    public static final conditions $instance = new conditions();
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("&condition").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("&message").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("condition?").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("make-condition").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("condition-has-type?").readResolve());
    static final SimpleSymbol Lit13;
    static final SyntaxRules Lit14;
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("condition-ref").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("make-compound-condition").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("extract-condition").readResolve());
    static final SimpleSymbol Lit18;
    static final SyntaxRules Lit19;
    static final PairWithPosition Lit2 = PairWithPosition.make(Lit5, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/conditions.scm", 925699);
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("type-field-alist->condition").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("thing").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("&serious").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("&error").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("message").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("condition-type?").readResolve());
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    static final SyntaxRules Lit9;
    public static final Macro condition = Macro.make(Lit18, Lit19, $instance);
    public static final ModuleMethod condition$Mnhas$Mntype$Qu;
    public static final ModuleMethod condition$Mnref;
    static final Macro condition$Mntype$Mnfield$Mnalist = Macro.make(Lit13, Lit14, $instance);
    public static final ModuleMethod condition$Mntype$Qu;
    public static final ModuleMethod condition$Qu;
    public static final Macro define$Mncondition$Mntype = Macro.make(Lit8, Lit9, $instance);
    public static final ModuleMethod extract$Mncondition;
    public static final ModuleMethod make$Mncompound$Mncondition;
    public static final ModuleMethod make$Mncondition;
    public static final ModuleMethod make$Mncondition$Mntype;

    /* compiled from: conditions.scm */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Mntype type;

        public frame() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/conditions.scm:166");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 1) {
                return lambda2(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda2(Object entry) {
            Object apply1 = lists.car.apply1(entry);
            try {
                return conditions.isConditionSubtype((Mntype) apply1, this.type);
            } catch (ClassCastException e) {
                throw new WrongType(e, "condition-subtype?", 0, apply1);
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

    public conditions() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        $Amcondition = new Mntype(Lit0, Boolean.FALSE, LList.Empty, LList.Empty);
        Symbol symbol = Lit1;
        Object obj = $Amcondition;
        try {
            $Ammessage = makeConditionType(symbol, (Mntype) obj, Lit2);
            symbol = Lit3;
            obj = $Amcondition;
            try {
                $Amserious = makeConditionType(symbol, (Mntype) obj, LList.Empty);
                symbol = Lit4;
                obj = $Amserious;
                try {
                    $Amerror = makeConditionType(symbol, (Mntype) obj, LList.Empty);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-condition-type", 1, obj);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "make-condition-type", 1, obj);
            }
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "make-condition-type", 1, obj);
        }
    }

    static {
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("condition").readResolve();
        Lit18 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018]\f\u0007-\f\u000f\f\u0017\b\b\u0010\b\u0000\u0018\b", new Object[0], 3), "\u0003\u0005\u0005", "\u0011\u0018\u0004\b\u0011\u0018\f\b\u0005\u0011\u0018\u0014\t\u0003\b\u0011\u0018\f\b\r\u0011\u0018\u0014)\u0011\u0018\u001c\b\u000b\b\u0013", new Object[]{Lit20, (SimpleSymbol) new SimpleSymbol("list").readResolve(), (SimpleSymbol) new SimpleSymbol("cons").readResolve(), Lit21}, 2);
        Lit19 = new SyntaxRules(objArr, syntaxRuleArr, 3);
        Object[] objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("condition-type-field-alist").readResolve();
        Lit13 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr2 = new SyntaxRule[1];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol("*").readResolve(), Pair.make(Pair.make((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol(".type-field-alist").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/conditions.scm", 581639), (SimpleSymbol) new SimpleSymbol("as").readResolve(), (SimpleSymbol) new SimpleSymbol("<condition>").readResolve()}, 0);
        Lit14 = new SyntaxRules(objArr2, syntaxRuleArr2, 1);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("define-condition-type").readResolve();
        Lit8 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        r4 = new Object[13];
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("make-condition-type").readResolve();
        Lit7 = simpleSymbol2;
        r4[2] = simpleSymbol2;
        r4[3] = Lit21;
        r4[4] = PairWithPosition.make(Lit22, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/conditions.scm", 327708);
        r4[5] = (SimpleSymbol) new SimpleSymbol("and").readResolve();
        r4[6] = PairWithPosition.make(Lit10, PairWithPosition.make(Lit22, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/conditions.scm", 331803), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/conditions.scm", 331791);
        r4[7] = Lit12;
        r4[8] = Lit22;
        r4[9] = PairWithPosition.make(Lit18, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/conditions.scm", 339996);
        r4[10] = Lit15;
        r4[11] = Lit17;
        r4[12] = Lit18;
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017-\f\u001f\f'\b\u0018\u0010\b", new Object[0], 5), "\u0001\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004É\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014)\u0011\u0018\u001c\b\u0003\t\u000b\b\u0011\u0018\u001c\b\b\u001d\u001bÁ\u0011\u0018\f!\t\u0013\u0018$\b\u0011\u0018,\u0011\u00184\b\u0011\u0018<\u0011\u0018D\b\u0003\b%\u0011\u0018\f!\t#\u0018L\b\u0011\u0018TA\u0011\u0018\\\u0011\u0018d\b\u0003\b\u0011\u0018\u001c\b\u001b", r4, 1);
        Lit9 = new SyntaxRules(objArr, syntaxRuleArr, 5);
        ModuleBody moduleBody = $instance;
        condition$Mntype$Qu = new ModuleMethod(moduleBody, 2, Lit6, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mncondition$Mntype = new ModuleMethod(moduleBody, 3, Lit7, 12291);
        condition$Qu = new ModuleMethod(moduleBody, 4, Lit10, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mncondition = new ModuleMethod(moduleBody, 5, Lit11, -4095);
        condition$Mnhas$Mntype$Qu = new ModuleMethod(moduleBody, 6, Lit12, 8194);
        condition$Mnref = new ModuleMethod(moduleBody, 7, Lit15, 8194);
        make$Mncompound$Mncondition = new ModuleMethod(moduleBody, 8, Lit16, -4095);
        extract$Mncondition = new ModuleMethod(moduleBody, 9, Lit17, 8194);
        $Prvt$type$Mnfield$Mnalist$Mn$Grcondition = new ModuleMethod(moduleBody, 10, Lit20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public static boolean isConditionType(Object obj) {
        return obj instanceof Mntype;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Mntype makeConditionType(Symbol name, Mntype supertype, Object fields) {
        if (!lists.isNull(srfi1.lsetIntersection$V(Scheme.isEq, supertype.all$Mnfields, new Object[]{fields}))) {
            misc.error$V("duplicate field name", new Object[0]);
        }
        return new Mntype(name, supertype, fields, append.append$V(new Object[]{supertype.all$Mnfields, fields}));
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        if (moduleMethod.selector != 3) {
            return super.apply3(moduleMethod, obj, obj2, obj3);
        }
        try {
            try {
                return makeConditionType((Symbol) obj, (Mntype) obj2, obj3);
            } catch (ClassCastException e) {
                throw new WrongType(e, "make-condition-type", 2, obj2);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "make-condition-type", 1, obj);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        if (moduleMethod.selector != 3) {
            return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
        if (!(obj instanceof Symbol)) {
            return -786431;
        }
        callContext.value1 = obj;
        if (!(obj2 instanceof Mntype)) {
            return -786430;
        }
        callContext.value2 = obj2;
        callContext.value3 = obj3;
        callContext.proc = moduleMethod;
        callContext.pc = 3;
        return 0;
    }

    static boolean isConditionSubtype(Mntype subtype, Mntype supertype) {
        while (subtype != Boolean.FALSE) {
            if (subtype == supertype) {
                return true;
            }
            subtype = subtype.supertype;
        }
        return false;
    }

    static Object conditionTypeFieldSupertype(Mntype condition$Mntype, Object field) {
        while (condition$Mntype != Boolean.FALSE) {
            if (lists.memq(field, condition$Mntype.fields) != Boolean.FALSE) {
                return condition$Mntype;
            }
            condition$Mntype = condition$Mntype.supertype;
        }
        return Boolean.FALSE;
    }

    public static boolean isCondition(Object obj) {
        return obj instanceof condition;
    }

    public static condition makeCondition$V(Mntype type, Object[] argsArray) {
        Object alist = lambda1label(LList.makeList(argsArray, 0));
        Procedure procedure = Scheme.isEq;
        Object[] objArr = new Object[2];
        objArr[0] = type.all$Mnfields;
        Pair result = LList.Empty;
        Object arg0 = alist;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                result = Pair.make(lists.car.apply1(arg02.getCar()), result);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        objArr[1] = LList.reverseInPlace(result);
        if (srfi1.lset$Eq$V(procedure, objArr) == Boolean.FALSE) {
            misc.error$V("condition fields don't match condition type", new Object[0]);
        }
        return new condition(LList.list1(lists.cons(type, alist)));
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 5:
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

    public static Object lambda1label(Object plist) {
        if (lists.isNull(plist)) {
            return LList.Empty;
        }
        return lists.cons(lists.cons(lists.car.apply1(plist), lists.cadr.apply1(plist)), lambda1label(lists.cddr.apply1(plist)));
    }

    public static boolean isConditionHasType(Object condition, Mntype type) {
        Object types = conditionTypes(condition);
        while (true) {
            Object apply1 = lists.car.apply1(types);
            try {
                boolean x = isConditionSubtype((Mntype) apply1, type);
                if (x) {
                    return x;
                }
                types = lists.cdr.apply1(types);
            } catch (ClassCastException e) {
                throw new WrongType(e, "condition-subtype?", 0, apply1);
            }
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 6:
                callContext.value1 = obj;
                if (!(obj2 instanceof Mntype)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 7:
                if (!(obj instanceof condition)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 9:
                if (!(obj instanceof condition)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Mntype)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object conditionRef(condition condition, Object field) {
        return typeFieldAlistRef(condition.type$Mnfield$Mnalist, field);
    }

    static Object typeFieldAlistRef(Object type$Mnfield$Mnalist, Object field) {
        while (!lists.isNull(type$Mnfield$Mnalist)) {
            Boolean temp = lists.assq(field, lists.cdr.apply1(lists.car.apply1(type$Mnfield$Mnalist)));
            if (temp != Boolean.FALSE) {
                return lists.cdr.apply1(temp);
            }
            type$Mnfield$Mnalist = lists.cdr.apply1(type$Mnfield$Mnalist);
        }
        return misc.error$V("type-field-alist-ref: field not found", new Object[]{type$Mnfield$Mnalist, field});
    }

    public static condition makeCompoundCondition$V(Object condition$Mn1, Object[] argsArray) {
        LList conditions = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.apply;
        append kawa_standard_append = append.append;
        Object cons = lists.cons(condition$Mn1, conditions);
        Pair result = LList.Empty;
        while (cons != LList.Empty) {
            try {
                Pair arg0 = (Pair) cons;
                Object arg02 = arg0.getCdr();
                result = Pair.make(Scheme.applyToArgs.apply2(condition$Mntype$Mnfield$Mnalist, arg0.getCar()), result);
                cons = arg02;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, cons);
            }
        }
        return new condition(procedure.apply2(kawa_standard_append, LList.reverseInPlace(result)));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 5:
                Object obj = objArr[0];
                try {
                    Mntype mntype = (Mntype) obj;
                    int length = objArr.length - 1;
                    Object[] objArr2 = new Object[length];
                    while (true) {
                        length--;
                        if (length < 0) {
                            return makeCondition$V(mntype, objArr2);
                        }
                        objArr2[length] = objArr[length + 1];
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-condition", 1, obj);
                }
            case 8:
                Object obj2 = objArr[0];
                int length2 = objArr.length - 1;
                Object[] objArr3 = new Object[length2];
                while (true) {
                    length2--;
                    if (length2 < 0) {
                        return makeCompoundCondition$V(obj2, objArr3);
                    }
                    objArr3[length2] = objArr[length2 + 1];
                }
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static condition extractCondition(condition condition, Mntype type) {
        frame gnu_kawa_slib_conditions_frame = new frame();
        gnu_kawa_slib_conditions_frame.type = type;
        Boolean entry = srfi1.find(gnu_kawa_slib_conditions_frame.lambda$Fn1, condition.type$Mnfield$Mnalist);
        if (entry == Boolean.FALSE) {
            misc.error$V("extract-condition: invalid condition type", new Object[]{condition, gnu_kawa_slib_conditions_frame.type});
        }
        Mntype mntype = gnu_kawa_slib_conditions_frame.type;
        Object arg0 = gnu_kawa_slib_conditions_frame.type.all$Mnfields;
        Pair result = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                result = Pair.make(lists.assq(arg02.getCar(), lists.cdr.apply1(entry)), result);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return new condition(LList.list1(lists.cons(mntype, LList.reverseInPlace(result))));
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 6:
                try {
                    return isConditionHasType(obj, (Mntype) obj2) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "condition-has-type?", 2, obj2);
                }
            case 7:
                try {
                    return conditionRef((condition) obj, obj2);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "condition-ref", 1, obj);
                }
            case 9:
                try {
                    try {
                        return extractCondition((condition) obj, (Mntype) obj2);
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "extract-condition", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "extract-condition", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static condition typeFieldAlist$To$Condition(Object type$Mnfield$Mnalist) {
        Object arg0 = type$Mnfield$Mnalist;
        Object obj = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                Object entry = arg02.getCar();
                Object apply1 = lists.car.apply1(entry);
                arg0 = ((Mntype) lists.car.apply1(entry)).all$Mnfields;
                Object obj2 = LList.Empty;
                while (arg0 != LList.Empty) {
                    try {
                        arg02 = (Pair) arg0;
                        Object arg04 = arg02.getCdr();
                        Object field = arg02.getCar();
                        Object x = lists.assq(field, lists.cdr.apply1(entry));
                        if (x == Boolean.FALSE) {
                            x = lists.cons(field, typeFieldAlistRef(type$Mnfield$Mnalist, field));
                        }
                        obj2 = Pair.make(x, obj2);
                        arg0 = arg04;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "arg0", -2, arg0);
                    }
                }
                Pair make = Pair.make(lists.cons(apply1, LList.reverseInPlace(obj2)), obj);
                arg0 = arg03;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, arg0);
            }
        }
        return new condition(LList.reverseInPlace(obj));
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 2:
                return isConditionType(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 4:
                return isCondition(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 10:
                return typeFieldAlist$To$Condition(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    static Object conditionTypes(Object condition) {
        Object arg0 = ((condition) condition).type$Mnfield$Mnalist;
        Pair result = LList.Empty;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                result = Pair.make(lists.car.apply1(arg02.getCar()), result);
                arg0 = arg03;
            } catch (ClassCastException e) {
                throw new WrongType(e, "arg0", -2, arg0);
            }
        }
        return LList.reverseInPlace(result);
    }

    static Object checkConditionTypeFieldAlist(Object the$Mntype$Mnfield$Mnalist) {
        Object type$Mnfield$Mnalist = the$Mntype$Mnfield$Mnalist;
        while (!lists.isNull(type$Mnfield$Mnalist)) {
            Object entry = lists.car.apply1(type$Mnfield$Mnalist);
            Object apply1 = lists.car.apply1(entry);
            try {
                Pair arg0;
                Mntype type = (Mntype) apply1;
                Pair field$Mnalist = lists.cdr.apply1(entry);
                Pair result = LList.Empty;
                Object arg02 = field$Mnalist;
                while (arg02 != LList.Empty) {
                    try {
                        arg0 = (Pair) arg02;
                        Pair arg03 = arg0.getCdr();
                        result = Pair.make(lists.car.apply1(arg0.getCar()), result);
                        arg0 = arg03;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "arg0", -2, arg02);
                    }
                }
                LList fields = LList.reverseInPlace(result);
                Object all$Mnfields = type.all$Mnfields;
                arg02 = srfi1.lsetDifference$V(Scheme.isEq, all$Mnfields, new Object[]{fields});
                while (arg02 != LList.Empty) {
                    try {
                        boolean x;
                        arg0 = (Pair) arg02;
                        Object supertype = conditionTypeFieldSupertype(type, arg0.getCar());
                        Object alist = the$Mntype$Mnfield$Mnalist;
                        while (true) {
                            Object apply12 = lists.car.apply1(lists.car.apply1(alist));
                            try {
                                try {
                                    x = isConditionSubtype((Mntype) apply12, (Mntype) supertype);
                                    if (x) {
                                        break;
                                    }
                                    alist = lists.cdr.apply1(alist);
                                } catch (ClassCastException e2) {
                                    throw new WrongType(e2, "condition-subtype?", 1, supertype);
                                }
                            } catch (ClassCastException e3) {
                                throw new WrongType(e3, "condition-subtype?", 0, apply12);
                            }
                        }
                        if (!x) {
                            misc.error$V("missing field in condition construction", new Object[]{type, missing$Mnfield});
                        }
                        arg02 = arg0.getCdr();
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "arg0", -2, arg02);
                    }
                }
                type$Mnfield$Mnalist = lists.cdr.apply1(type$Mnfield$Mnalist);
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "type", -2, apply1);
            }
        }
        return Values.empty;
    }

    static boolean isMessageCondition(Object thing) {
        boolean x = isCondition(thing);
        if (!x) {
            return x;
        }
        Object obj = $Ammessage;
        try {
            return isConditionHasType(thing, (Mntype) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "condition-has-type?", 1, obj);
        }
    }

    static Object conditionMessage(Object condition) {
        try {
            condition gnu_kawa_slib_condition = (condition) condition;
            Object obj = $Ammessage;
            try {
                return conditionRef(extractCondition(gnu_kawa_slib_condition, (Mntype) obj), Lit5);
            } catch (ClassCastException e) {
                throw new WrongType(e, "extract-condition", 1, obj);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "extract-condition", 0, condition);
        }
    }

    static boolean isSeriousCondition(Object thing) {
        boolean x = isCondition(thing);
        if (!x) {
            return x;
        }
        Object obj = $Amserious;
        try {
            return isConditionHasType(thing, (Mntype) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "condition-has-type?", 1, obj);
        }
    }

    static boolean isError(Object thing) {
        boolean x = isCondition(thing);
        if (!x) {
            return x;
        }
        Object obj = $Amerror;
        try {
            return isConditionHasType(thing, (Mntype) obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "condition-has-type?", 1, obj);
        }
    }
}

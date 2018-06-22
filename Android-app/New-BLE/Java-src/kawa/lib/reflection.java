package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.lang.GetFieldProc;
import kawa.lang.Macro;
import kawa.lang.Record;
import kawa.lang.RecordConstructor;
import kawa.lang.SetFieldProc;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.standard.syntax_case;

/* compiled from: reflection.scm */
public class reflection extends ModuleBody {
    public static final reflection $instance = new reflection();
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("primitive-constructor").readResolve());
    static final SyntaxPattern Lit1 = new SyntaxPattern("\f\u0007\f\u000f,\r\u0017\u0010\b\b\b", new Object[0], 3);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("record-predicate").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("record-type-descriptor").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("record-type-name").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("record-type-field-names").readResolve());
    static final SimpleSymbol Lit14;
    static final SyntaxRules Lit15;
    static final SimpleSymbol Lit16;
    static final SyntaxRules Lit17;
    static final SimpleSymbol Lit18;
    static final SyntaxRules Lit19;
    static final SyntaxTemplate Lit2 = new SyntaxTemplate("\u0001\u0001\u0003", "\b\u0015\u0013", new Object[0], 1);
    static final SimpleSymbol Lit20;
    static final SyntaxRules Lit21;
    static final SimpleSymbol Lit22;
    static final SyntaxRules Lit23;
    static final SimpleSymbol Lit24;
    static final SyntaxRules Lit25;
    static final SimpleSymbol Lit26;
    static final SyntaxRules Lit27;
    static final SimpleSymbol Lit28;
    static final SyntaxRules Lit29;
    static final SyntaxPattern Lit3 = new SyntaxPattern("\r\u001f\u0018\b\b", new Object[0], 4);
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("subtype?").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("constant-fold").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("make").readResolve());
    static final IntNum Lit33 = IntNum.make(9);
    static final IntNum Lit34 = IntNum.make(1);
    static final SyntaxTemplate Lit4 = new SyntaxTemplate("\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004\u0019\b\u001d\u001b\u0011\u0018\f\t\u000b\b\u0011\u0018\u0014\t\u000b\b\u0015\u0011\u0018\u001c\t\u0013\b\u001b", new Object[]{(SimpleSymbol) new SimpleSymbol("lambda").readResolve(), (SimpleSymbol) new SimpleSymbol("::").readResolve(), Lit32, (SimpleSymbol) new SimpleSymbol("as").readResolve()}, 1);
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("make-record-type").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("record-constructor").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("record-accessor").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("record-modifier").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("record?").readResolve());
    public static final ModuleMethod make$Mnrecord$Mntype;
    public static final Macro primitive$Mnarray$Mnget = Macro.make(Lit18, Lit19, $instance);
    public static final Macro primitive$Mnarray$Mnlength = Macro.make(Lit20, Lit21, $instance);
    public static final Macro primitive$Mnarray$Mnnew = Macro.make(Lit14, Lit15, $instance);
    public static final Macro primitive$Mnarray$Mnset = Macro.make(Lit16, Lit17, $instance);
    public static final Macro primitive$Mnconstructor;
    public static final Macro primitive$Mnget$Mnfield = Macro.make(Lit22, Lit23, $instance);
    public static final Macro primitive$Mnget$Mnstatic = Macro.make(Lit26, Lit27, $instance);
    public static final Macro primitive$Mnset$Mnfield = Macro.make(Lit24, Lit25, $instance);
    public static final Macro primitive$Mnset$Mnstatic = Macro.make(Lit28, Lit29, $instance);
    public static final ModuleMethod record$Mnaccessor;
    public static final ModuleMethod record$Mnconstructor;
    public static final ModuleMethod record$Mnmodifier;
    public static final ModuleMethod record$Mnpredicate;
    public static final ModuleMethod record$Mntype$Mndescriptor;
    public static final ModuleMethod record$Mntype$Mnfield$Mnnames;
    public static final ModuleMethod record$Mntype$Mnname;
    public static final ModuleMethod record$Qu;
    public static final ModuleMethod subtype$Qu;

    /* compiled from: reflection.scm */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Object rtype;

        public frame() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/reflection.scm:30");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 1) {
                return lambda1(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda1(Object object) {
            Object obj = this.rtype;
            try {
                return ((Type) obj).isInstance(object);
            } catch (ClassCastException e) {
                throw new WrongType(e, "gnu.bytecode.Type.isInstance(java.lang.Object)", 1, obj);
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

    static {
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("primitive-set-static").readResolve();
        Lit28 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\t\u0003\t\u000b\t\u0013\u0018\u001c", new Object[]{Lit31, Lit32, (SimpleSymbol) new SimpleSymbol("<gnu.kawa.reflect.StaticSet>").readResolve(), PairWithPosition.make(Lit33, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/reflection.scm", 454679)}, 0);
        Lit29 = new SyntaxRules(objArr, syntaxRuleArr, 3);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("primitive-get-static").readResolve();
        Lit26 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\t\u0003\t\u000b\t\u0013\u0018\u001c", new Object[]{Lit31, Lit32, (SimpleSymbol) new SimpleSymbol("<gnu.kawa.reflect.StaticGet>").readResolve(), PairWithPosition.make(Lit33, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/reflection.scm", 430103)}, 0);
        Lit27 = new SyntaxRules(objArr, syntaxRuleArr, 3);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("primitive-set-field").readResolve();
        Lit24 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\t\u0003\t\u000b\t\u0013\u0018\u001c", new Object[]{Lit31, Lit32, (SimpleSymbol) new SimpleSymbol("<kawa.lang.SetFieldProc>").readResolve(), PairWithPosition.make(Lit34, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/reflection.scm", 401431)}, 0);
        Lit25 = new SyntaxRules(objArr, syntaxRuleArr, 3);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("primitive-get-field").readResolve();
        Lit22 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\t\u0003\t\u000b\t\u0013\u0018\u001c", new Object[]{Lit31, Lit32, (SimpleSymbol) new SimpleSymbol("<kawa.lang.GetFieldProc>").readResolve(), PairWithPosition.make(Lit34, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/reflection.scm", 376855)}, 0);
        Lit23 = new SyntaxRules(objArr, syntaxRuleArr, 3);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("primitive-array-length").readResolve();
        Lit20 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{Lit31, Lit32, (SimpleSymbol) new SimpleSymbol("<gnu.kawa.reflect.ArrayLength>").readResolve()}, 0);
        Lit21 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("primitive-array-get").readResolve();
        Lit18 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{Lit31, Lit32, (SimpleSymbol) new SimpleSymbol("<gnu.kawa.reflect.ArrayGet>").readResolve()}, 0);
        Lit19 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("primitive-array-set").readResolve();
        Lit16 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{Lit31, Lit32, (SimpleSymbol) new SimpleSymbol("<gnu.kawa.reflect.ArraySet>").readResolve()}, 0);
        Lit17 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("primitive-array-new").readResolve();
        Lit14 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\b\u0003", new Object[]{Lit31, Lit32, (SimpleSymbol) new SimpleSymbol("<gnu.kawa.reflect.ArrayNew>").readResolve()}, 0);
        Lit15 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        simpleSymbol = Lit0;
        ModuleBody moduleBody = $instance;
        primitive$Mnconstructor = Macro.make(simpleSymbol, new ModuleMethod(moduleBody, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        make$Mnrecord$Mntype = new ModuleMethod(moduleBody, 3, Lit5, 8194);
        record$Mnconstructor = new ModuleMethod(moduleBody, 4, Lit6, 8193);
        record$Mnaccessor = new ModuleMethod(moduleBody, 6, Lit7, 8194);
        record$Mnmodifier = new ModuleMethod(moduleBody, 7, Lit8, 8194);
        record$Qu = new ModuleMethod(moduleBody, 8, Lit9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        record$Mnpredicate = new ModuleMethod(moduleBody, 9, Lit10, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        record$Mntype$Mndescriptor = new ModuleMethod(moduleBody, 10, Lit11, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        record$Mntype$Mnname = new ModuleMethod(moduleBody, 11, Lit12, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        record$Mntype$Mnfield$Mnnames = new ModuleMethod(moduleBody, 12, Lit13, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        subtype$Qu = new ModuleMethod(moduleBody, 13, Lit30, 8194);
        $instance.run();
    }

    public reflection() {
        ModuleInfo.register(this);
    }

    public static RecordConstructor recordConstructor(ClassType classType) {
        return recordConstructor(classType, null);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static Object lambda2(Object form) {
        Object[] allocVars = SyntaxPattern.allocVars(3, null);
        if (!Lit1.match(form, allocVars, 0)) {
            return syntax_case.error("syntax-case", form);
        }
        Object generateTemporaries = std_syntax.generateTemporaries(Lit2.execute(allocVars, TemplateScope.make()));
        allocVars = SyntaxPattern.allocVars(4, allocVars);
        if (!Lit3.match(generateTemporaries, allocVars, 0)) {
            return syntax_case.error("syntax-case", generateTemporaries);
        }
        return Lit4.execute(allocVars, TemplateScope.make());
    }

    public static ClassType makeRecordType(String name, LList fnames) {
        return Record.makeRecordType(name, fnames);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 3:
                callContext.value1 = obj;
                if (!(obj2 instanceof LList)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 4:
                if (LangObjType.coerceToClassTypeOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 6:
                if (LangObjType.coerceToClassTypeOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 7:
                if (LangObjType.coerceToClassTypeOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 13:
                if (LangObjType.coerceToTypeOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (LangObjType.coerceToTypeOrNull(obj2) == null) {
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

    public static RecordConstructor recordConstructor(ClassType cl, Object flds) {
        return new RecordConstructor(cl, flds);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                if (LangObjType.coerceToClassTypeOrNull(obj) == null) {
                    return -786431;
                }
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
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 11:
                if (LangObjType.coerceToClassTypeOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 12:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static GetFieldProc recordAccessor(ClassType class_, String fname) {
        return new GetFieldProc(class_, fname);
    }

    public static SetFieldProc recordModifier(ClassType class_, String fname) {
        return new SetFieldProc(class_, fname);
    }

    public static boolean isRecord(Object obj) {
        return obj instanceof Record;
    }

    public static Procedure recordPredicate(Object rtype) {
        frame kawa_lib_reflection_frame = new frame();
        kawa_lib_reflection_frame.rtype = rtype;
        return kawa_lib_reflection_frame.lambda$Fn1;
    }

    public static Type recordTypeDescriptor(Object object) {
        return Type.make(object.getClass());
    }

    public static String recordTypeName(ClassType rtd) {
        return Compilation.demangleName(rtd.getName(), true);
    }

    public static LList recordTypeFieldNames(Object rtd) {
        try {
            return Record.typeFieldNames(LangObjType.coerceToClassType(rtd));
        } catch (ClassCastException e) {
            throw new WrongType(e, "kawa.lang.Record.typeFieldNames(class-type)", 1, rtd);
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 2:
                return lambda2(obj);
            case 4:
                try {
                    return recordConstructor(LangObjType.coerceToClassType(obj));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "record-constructor", 1, obj);
                }
            case 8:
                return isRecord(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 9:
                return recordPredicate(obj);
            case 10:
                return recordTypeDescriptor(obj);
            case 11:
                try {
                    return recordTypeName(LangObjType.coerceToClassType(obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "record-type-name", 1, obj);
                }
            case 12:
                return recordTypeFieldNames(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static boolean isSubtype(Type t1, Type t2) {
        return t1.isSubtype(t2);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        String str = null;
        ClassType coerceToClassType;
        switch (moduleMethod.selector) {
            case 3:
                if (obj != null) {
                    str = obj.toString();
                }
                try {
                    return makeRecordType(str, (LList) obj2);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-record-type", 2, obj2);
                }
            case 4:
                try {
                    return recordConstructor(LangObjType.coerceToClassType(obj), obj2);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "record-constructor", 1, obj);
                }
            case 6:
                try {
                    coerceToClassType = LangObjType.coerceToClassType(obj);
                    if (obj2 != null) {
                        str = obj2.toString();
                    }
                    return recordAccessor(coerceToClassType, str);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "record-accessor", 1, obj);
                }
            case 7:
                try {
                    coerceToClassType = LangObjType.coerceToClassType(obj);
                    if (obj2 != null) {
                        str = obj2.toString();
                    }
                    return recordModifier(coerceToClassType, str);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "record-modifier", 1, obj);
                }
            case 13:
                try {
                    try {
                        return isSubtype(LangObjType.coerceToType(obj), LangObjType.coerceToType(obj2)) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "subtype?", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "subtype?", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }
}

package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.LocationProc;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.ThreadLocation;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.standard.Scheme;

/* compiled from: parameters.scm */
public class parameters extends ModuleBody {
    public static final ModuleMethod $Prvt$as$Mnlocation$Pc;
    public static final Macro $Prvt$parameterize$Pc = Macro.make(Lit2, Lit3, $instance);
    public static final parameters $instance = new parameters();
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("make-parameter").readResolve());
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("gnu.mapping.Location").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("save").readResolve());
    static final SimpleSymbol Lit2;
    static final SyntaxRules Lit3;
    static final SimpleSymbol Lit4;
    static final SyntaxRules Lit5;
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("p").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("v").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve());
    public static final ModuleMethod make$Mnparameter;
    public static final Macro parameterize = Macro.make(Lit4, Lit5, $instance);

    public parameters() {
        ModuleInfo.register(this);
    }

    public static LocationProc makeParameter(Object obj) {
        return makeParameter(obj, null);
    }

    static {
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("parameterize").readResolve();
        Lit4 = simpleSymbol;
        objArr[0] = simpleSymbol;
        r8 = new SyntaxRule[2];
        r8[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\u0003", new Object[0], 1), "\u0000", "\u0011\u0018\u0004\u0002", new Object[]{Lit6}, 0);
        Object[] objArr2 = new Object[1];
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("parameterize%").readResolve();
        Lit2 = simpleSymbol2;
        objArr2[0] = simpleSymbol2;
        r8[1] = new SyntaxRule(new SyntaxPattern("\f\u0018<,\f\u0007\f\u000f\b\u0013\u001b", new Object[0], 4), "\u0001\u0001\u0000\u0000", "\u0011\u0018\u00041!\t\u0003\b\u000b\u0012\t\u0010\u001a", objArr2, 0);
        Lit5 = new SyntaxRules(objArr, r8, 4);
        objArr = new Object[]{Lit2};
        r8 = new SyntaxRule[2];
        r8[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\f\u0007\u000b", new Object[0], 2), "\u0001\u0000", "\u0011\u0018\u0004!\u0011\u0018\f\n\b\u0011\u0018\f\u0003", new Object[]{(SimpleSymbol) new SimpleSymbol("try-finally").readResolve(), Lit6}, 0);
        objArr2 = new Object[9];
        simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("as-location%").readResolve();
        Lit1 = simpleSymbol2;
        objArr2[4] = simpleSymbol2;
        objArr2[5] = Lit8;
        objArr2[6] = PairWithPosition.make(PairWithPosition.make(Lit12, PairWithPosition.make(PairWithPosition.make(PairWithPosition.make(Lit9, Pair.make(Lit10, Pair.make(Pair.make(Lit11, Pair.make((SimpleSymbol) new SimpleSymbol("setWithSave").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 122893), PairWithPosition.make(Lit7, PairWithPosition.make(Lit8, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 122928), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 122926), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 122892), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 122892), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 122886), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 122886);
        objArr2[7] = Lit2;
        objArr2[8] = PairWithPosition.make(PairWithPosition.make(Lit9, Pair.make(Lit10, Pair.make(Pair.make(Lit11, Pair.make((SimpleSymbol) new SimpleSymbol("setRestore").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 131083), PairWithPosition.make(Lit7, PairWithPosition.make(Lit12, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 131117), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 131115), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/parameters.scm", 131082);
        r8[1] = new SyntaxRule(new SyntaxPattern("\f\u0018<,\f\u0007\f\u000f\b\u0013\f\u001f#", new Object[0], 5), "\u0001\u0001\u0000\u0001\u0000", "\u0011\u0018\u0004Áy\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\b\u0003)\u0011\u0018,\b\u000b\u00184\b\u0011\u0018<\t\u0012!\u0011\u0018D\u001b\"", objArr2, 0);
        Lit3 = new SyntaxRules(objArr, r8, 5);
        ModuleBody moduleBody = $instance;
        make$Mnparameter = new ModuleMethod(moduleBody, 1, Lit0, 8193);
        $Prvt$as$Mnlocation$Pc = new ModuleMethod(moduleBody, 3, Lit1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static LocationProc makeParameter(Object init, Object converter) {
        if (converter != null) {
            init = Scheme.applyToArgs.apply2(converter, init);
        }
        ThreadLocation loc = new ThreadLocation();
        loc.setGlobal(init);
        try {
            return new LocationProc(loc, (Procedure) converter);
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.mapping.LocationProc.<init>(gnu.mapping.Location,gnu.mapping.Procedure)", 2, converter);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 1 ? makeParameter(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.pc = 2;
        return 0;
    }

    public static Location asLocation$Pc(Object param) {
        Location loc;
        if (param instanceof LocationProc) {
            try {
                Object loc2 = ((LocationProc) param).getLocation();
            } catch (ClassCastException e) {
                throw new WrongType(e, "gnu.mapping.LocationProc.getLocation()", 1, param);
            }
        }
        loc = (Location) param;
        if (loc2 instanceof ThreadLocation) {
            try {
                loc = ((ThreadLocation) loc2).getLocation();
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "gnu.mapping.ThreadLocation.getLocation()", 1, loc2);
            }
        }
        return loc;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return makeParameter(obj);
            case 3:
                return asLocation$Pc(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}

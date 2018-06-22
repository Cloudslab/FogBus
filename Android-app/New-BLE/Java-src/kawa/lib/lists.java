package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.GenericProc;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.reflect.SlotSet;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.standard.Scheme;

/* compiled from: lists.scm */
public class lists extends ModuleBody {
    public static final Location $Prvt$define$Mnprocedure = StaticFieldLocation.make("kawa.lib.std_syntax", "define$Mnprocedure");
    public static final lists $instance = new lists();
    static final Keyword Lit0 = Keyword.make("setter");
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("car").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("list-tail").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("list-ref").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("list?").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("reverse!").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("memq").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("memv").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("member").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("assq").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("assv").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("assoc").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("cdr").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("pair?").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("cons").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("null?").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("set-car!").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("set-cdr!").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("length").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("reverse").readResolve());
    public static final ModuleMethod assoc;
    public static final ModuleMethod assq;
    public static final ModuleMethod assv;
    public static final GenericProc caaaar = null;
    static final ModuleMethod caaaar$Fn28;
    public static final GenericProc caaadr = null;
    static final ModuleMethod caaadr$Fn30;
    public static final GenericProc caaar = null;
    static final ModuleMethod caaar$Fn12;
    public static final GenericProc caadar = null;
    static final ModuleMethod caadar$Fn32;
    public static final GenericProc caaddr = null;
    static final ModuleMethod caaddr$Fn34;
    public static final GenericProc caadr = null;
    static final ModuleMethod caadr$Fn14;
    public static final GenericProc caar = null;
    static final ModuleMethod caar$Fn4;
    public static final GenericProc cadaar = null;
    static final ModuleMethod cadaar$Fn36;
    public static final GenericProc cadadr = null;
    static final ModuleMethod cadadr$Fn38;
    public static final GenericProc cadar = null;
    static final ModuleMethod cadar$Fn16;
    public static final GenericProc caddar = null;
    static final ModuleMethod caddar$Fn40;
    public static final GenericProc cadddr = null;
    static final ModuleMethod cadddr$Fn42;
    public static final GenericProc caddr = null;
    static final ModuleMethod caddr$Fn18;
    public static final GenericProc cadr = null;
    static final ModuleMethod cadr$Fn6;
    public static final GenericProc car = null;
    static final ModuleMethod car$Fn1;
    public static final GenericProc cdaaar = null;
    static final ModuleMethod cdaaar$Fn44;
    public static final GenericProc cdaadr = null;
    static final ModuleMethod cdaadr$Fn46;
    public static final GenericProc cdaar = null;
    static final ModuleMethod cdaar$Fn20;
    public static final GenericProc cdadar = null;
    static final ModuleMethod cdadar$Fn48;
    public static final GenericProc cdaddr = null;
    static final ModuleMethod cdaddr$Fn50;
    public static final GenericProc cdadr = null;
    static final ModuleMethod cdadr$Fn22;
    public static final GenericProc cdar = null;
    static final ModuleMethod cdar$Fn8;
    public static final GenericProc cddaar = null;
    static final ModuleMethod cddaar$Fn52;
    public static final GenericProc cddadr = null;
    static final ModuleMethod cddadr$Fn54;
    public static final GenericProc cddar = null;
    static final ModuleMethod cddar$Fn24;
    public static final GenericProc cdddar = null;
    static final ModuleMethod cdddar$Fn56;
    public static final GenericProc cddddr = null;
    static final ModuleMethod cddddr$Fn58;
    public static final GenericProc cdddr = null;
    static final ModuleMethod cdddr$Fn26;
    public static final GenericProc cddr = null;
    static final ModuleMethod cddr$Fn10;
    public static final GenericProc cdr = null;
    static final ModuleMethod cdr$Fn2;
    public static final ModuleMethod cons;
    static final ModuleMethod lambda$Fn11;
    static final ModuleMethod lambda$Fn13;
    static final ModuleMethod lambda$Fn15;
    static final ModuleMethod lambda$Fn17;
    static final ModuleMethod lambda$Fn19;
    static final ModuleMethod lambda$Fn21;
    static final ModuleMethod lambda$Fn23;
    static final ModuleMethod lambda$Fn25;
    static final ModuleMethod lambda$Fn27;
    static final ModuleMethod lambda$Fn29;
    static final ModuleMethod lambda$Fn3;
    static final ModuleMethod lambda$Fn31;
    static final ModuleMethod lambda$Fn33;
    static final ModuleMethod lambda$Fn35;
    static final ModuleMethod lambda$Fn37;
    static final ModuleMethod lambda$Fn39;
    static final ModuleMethod lambda$Fn41;
    static final ModuleMethod lambda$Fn43;
    static final ModuleMethod lambda$Fn45;
    static final ModuleMethod lambda$Fn47;
    static final ModuleMethod lambda$Fn49;
    static final ModuleMethod lambda$Fn5;
    static final ModuleMethod lambda$Fn51;
    static final ModuleMethod lambda$Fn53;
    static final ModuleMethod lambda$Fn55;
    static final ModuleMethod lambda$Fn57;
    static final ModuleMethod lambda$Fn7;
    static final ModuleMethod lambda$Fn9;
    public static final ModuleMethod length;
    public static final ModuleMethod list$Mnref;
    public static final ModuleMethod list$Mntail;
    public static final ModuleMethod list$Qu;
    public static final ModuleMethod member;
    public static final ModuleMethod memq;
    public static final ModuleMethod memv;
    public static final ModuleMethod null$Qu;
    public static final ModuleMethod pair$Qu;
    public static final ModuleMethod reverse;
    public static final ModuleMethod reverse$Ex;
    public static final ModuleMethod set$Mncar$Ex;
    public static final ModuleMethod set$Mncdr$Ex;

    public lists() {
        ModuleInfo.register(this);
    }

    public static Object assoc(Object obj, Object obj2) {
        return assoc(obj, obj2, Scheme.isEqual);
    }

    public static Object member(Object obj, Object obj2) {
        return member(obj, obj2, Scheme.isEqual);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        car = new GenericProc("car");
        car.setProperties(new Object[]{Lit0, set$Mncar$Ex, car$Fn1});
        cdr = new GenericProc("cdr");
        cdr.setProperties(new Object[]{Lit0, set$Mncdr$Ex, cdr$Fn2});
        caar = new GenericProc("caar");
        caar.setProperties(new Object[]{Lit0, lambda$Fn3, caar$Fn4});
        cadr = new GenericProc("cadr");
        cadr.setProperties(new Object[]{Lit0, lambda$Fn5, cadr$Fn6});
        cdar = new GenericProc("cdar");
        cdar.setProperties(new Object[]{Lit0, lambda$Fn7, cdar$Fn8});
        cddr = new GenericProc("cddr");
        cddr.setProperties(new Object[]{Lit0, lambda$Fn9, cddr$Fn10});
        caaar = new GenericProc("caaar");
        caaar.setProperties(new Object[]{Lit0, lambda$Fn11, caaar$Fn12});
        caadr = new GenericProc("caadr");
        caadr.setProperties(new Object[]{Lit0, lambda$Fn13, caadr$Fn14});
        cadar = new GenericProc("cadar");
        cadar.setProperties(new Object[]{Lit0, lambda$Fn15, cadar$Fn16});
        caddr = new GenericProc("caddr");
        caddr.setProperties(new Object[]{Lit0, lambda$Fn17, caddr$Fn18});
        cdaar = new GenericProc("cdaar");
        cdaar.setProperties(new Object[]{Lit0, lambda$Fn19, cdaar$Fn20});
        cdadr = new GenericProc("cdadr");
        cdadr.setProperties(new Object[]{Lit0, lambda$Fn21, cdadr$Fn22});
        cddar = new GenericProc("cddar");
        cddar.setProperties(new Object[]{Lit0, lambda$Fn23, cddar$Fn24});
        cdddr = new GenericProc("cdddr");
        cdddr.setProperties(new Object[]{Lit0, lambda$Fn25, cdddr$Fn26});
        caaaar = new GenericProc("caaaar");
        caaaar.setProperties(new Object[]{Lit0, lambda$Fn27, caaaar$Fn28});
        caaadr = new GenericProc("caaadr");
        caaadr.setProperties(new Object[]{Lit0, lambda$Fn29, caaadr$Fn30});
        caadar = new GenericProc("caadar");
        caadar.setProperties(new Object[]{Lit0, lambda$Fn31, caadar$Fn32});
        caaddr = new GenericProc("caaddr");
        caaddr.setProperties(new Object[]{Lit0, lambda$Fn33, caaddr$Fn34});
        cadaar = new GenericProc("cadaar");
        cadaar.setProperties(new Object[]{Lit0, lambda$Fn35, cadaar$Fn36});
        cadadr = new GenericProc("cadadr");
        cadadr.setProperties(new Object[]{Lit0, lambda$Fn37, cadadr$Fn38});
        caddar = new GenericProc("caddar");
        caddar.setProperties(new Object[]{Lit0, lambda$Fn39, caddar$Fn40});
        cadddr = new GenericProc("cadddr");
        cadddr.setProperties(new Object[]{Lit0, lambda$Fn41, cadddr$Fn42});
        cdaaar = new GenericProc("cdaaar");
        cdaaar.setProperties(new Object[]{Lit0, lambda$Fn43, cdaaar$Fn44});
        cdaadr = new GenericProc("cdaadr");
        cdaadr.setProperties(new Object[]{Lit0, lambda$Fn45, cdaadr$Fn46});
        cdadar = new GenericProc("cdadar");
        cdadar.setProperties(new Object[]{Lit0, lambda$Fn47, cdadar$Fn48});
        cdaddr = new GenericProc("cdaddr");
        cdaddr.setProperties(new Object[]{Lit0, lambda$Fn49, cdaddr$Fn50});
        cddaar = new GenericProc("cddaar");
        cddaar.setProperties(new Object[]{Lit0, lambda$Fn51, cddaar$Fn52});
        cddadr = new GenericProc("cddadr");
        cddadr.setProperties(new Object[]{Lit0, lambda$Fn53, cddadr$Fn54});
        cdddar = new GenericProc("cdddar");
        cdddar.setProperties(new Object[]{Lit0, lambda$Fn55, cdddar$Fn56});
        cddddr = new GenericProc("cddddr");
        cddddr.setProperties(new Object[]{Lit0, lambda$Fn57, cddddr$Fn58});
    }

    static {
        ModuleBody moduleBody = $instance;
        pair$Qu = new ModuleMethod(moduleBody, 1, Lit3, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        cons = new ModuleMethod(moduleBody, 2, Lit4, 8194);
        null$Qu = new ModuleMethod(moduleBody, 3, Lit5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        set$Mncar$Ex = new ModuleMethod(moduleBody, 4, Lit6, 8194);
        set$Mncdr$Ex = new ModuleMethod(moduleBody, 5, Lit7, 8194);
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 6, "car", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/lists.scm:31");
        car$Fn1 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 7, "cdr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/lists.scm:36");
        cdr$Fn2 = moduleMethod;
        lambda$Fn3 = new ModuleMethod(moduleBody, 8, null, 8194);
        caar$Fn4 = new ModuleMethod(moduleBody, 9, "caar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn5 = new ModuleMethod(moduleBody, 10, null, 8194);
        cadr$Fn6 = new ModuleMethod(moduleBody, 11, "cadr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn7 = new ModuleMethod(moduleBody, 12, null, 8194);
        cdar$Fn8 = new ModuleMethod(moduleBody, 13, "cdar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn9 = new ModuleMethod(moduleBody, 14, null, 8194);
        cddr$Fn10 = new ModuleMethod(moduleBody, 15, "cddr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn11 = new ModuleMethod(moduleBody, 16, null, 8194);
        caaar$Fn12 = new ModuleMethod(moduleBody, 17, "caaar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn13 = new ModuleMethod(moduleBody, 18, null, 8194);
        caadr$Fn14 = new ModuleMethod(moduleBody, 19, "caadr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn15 = new ModuleMethod(moduleBody, 20, null, 8194);
        cadar$Fn16 = new ModuleMethod(moduleBody, 21, "cadar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn17 = new ModuleMethod(moduleBody, 22, null, 8194);
        caddr$Fn18 = new ModuleMethod(moduleBody, 23, "caddr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn19 = new ModuleMethod(moduleBody, 24, null, 8194);
        cdaar$Fn20 = new ModuleMethod(moduleBody, 25, "cdaar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn21 = new ModuleMethod(moduleBody, 26, null, 8194);
        cdadr$Fn22 = new ModuleMethod(moduleBody, 27, "cdadr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn23 = new ModuleMethod(moduleBody, 28, null, 8194);
        cddar$Fn24 = new ModuleMethod(moduleBody, 29, "cddar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn25 = new ModuleMethod(moduleBody, 30, null, 8194);
        cdddr$Fn26 = new ModuleMethod(moduleBody, 31, "cdddr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn27 = new ModuleMethod(moduleBody, 32, null, 8194);
        caaaar$Fn28 = new ModuleMethod(moduleBody, 33, "caaaar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn29 = new ModuleMethod(moduleBody, 34, null, 8194);
        caaadr$Fn30 = new ModuleMethod(moduleBody, 35, "caaadr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn31 = new ModuleMethod(moduleBody, 36, null, 8194);
        caadar$Fn32 = new ModuleMethod(moduleBody, 37, "caadar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn33 = new ModuleMethod(moduleBody, 38, null, 8194);
        caaddr$Fn34 = new ModuleMethod(moduleBody, 39, "caaddr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn35 = new ModuleMethod(moduleBody, 40, null, 8194);
        cadaar$Fn36 = new ModuleMethod(moduleBody, 41, "cadaar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn37 = new ModuleMethod(moduleBody, 42, null, 8194);
        cadadr$Fn38 = new ModuleMethod(moduleBody, 43, "cadadr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn39 = new ModuleMethod(moduleBody, 44, null, 8194);
        caddar$Fn40 = new ModuleMethod(moduleBody, 45, "caddar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn41 = new ModuleMethod(moduleBody, 46, null, 8194);
        cadddr$Fn42 = new ModuleMethod(moduleBody, 47, "cadddr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn43 = new ModuleMethod(moduleBody, 48, null, 8194);
        cdaaar$Fn44 = new ModuleMethod(moduleBody, 49, "cdaaar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn45 = new ModuleMethod(moduleBody, 50, null, 8194);
        cdaadr$Fn46 = new ModuleMethod(moduleBody, 51, "cdaadr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn47 = new ModuleMethod(moduleBody, 52, null, 8194);
        cdadar$Fn48 = new ModuleMethod(moduleBody, 53, "cdadar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn49 = new ModuleMethod(moduleBody, 54, null, 8194);
        cdaddr$Fn50 = new ModuleMethod(moduleBody, 55, "cdaddr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn51 = new ModuleMethod(moduleBody, 56, null, 8194);
        cddaar$Fn52 = new ModuleMethod(moduleBody, 57, "cddaar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn53 = new ModuleMethod(moduleBody, 58, null, 8194);
        cddadr$Fn54 = new ModuleMethod(moduleBody, 59, "cddadr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn55 = new ModuleMethod(moduleBody, 60, null, 8194);
        cdddar$Fn56 = new ModuleMethod(moduleBody, 61, "cdddar", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        lambda$Fn57 = new ModuleMethod(moduleBody, 62, null, 8194);
        cddddr$Fn58 = new ModuleMethod(moduleBody, 63, "cddddr", FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        length = new ModuleMethod(moduleBody, 64, Lit8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        reverse = new ModuleMethod(moduleBody, 65, Lit9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mntail = new ModuleMethod(moduleBody, 66, Lit10, 8194);
        list$Mnref = new ModuleMethod(moduleBody, 67, Lit11, 8194);
        list$Qu = new ModuleMethod(moduleBody, 68, Lit12, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        reverse$Ex = new ModuleMethod(moduleBody, 69, Lit13, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        memq = new ModuleMethod(moduleBody, 70, Lit14, 8194);
        memv = new ModuleMethod(moduleBody, 71, Lit15, 8194);
        member = new ModuleMethod(moduleBody, 72, Lit16, 12290);
        assq = new ModuleMethod(moduleBody, 74, Lit17, 8194);
        assv = new ModuleMethod(moduleBody, 75, Lit18, 8194);
        assoc = new ModuleMethod(moduleBody, 76, Lit19, 12290);
        $instance.run();
    }

    public static boolean isPair(Object x) {
        return x instanceof Pair;
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
            case 6:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 7:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
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
            case 13:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 15:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 17:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 19:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 21:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 23:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 25:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 27:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 31:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 33:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 37:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 39:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 41:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 43:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 45:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 49:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 51:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 53:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 55:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 57:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 59:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 61:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 63:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 64:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 65:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 68:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 69:
                if (!(obj instanceof LList)) {
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

    public static Pair cons(Object car, Object cdr) {
        return new Pair(car, cdr);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 4:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 5:
                if (!(obj instanceof Pair)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 8:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 12:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 14:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 16:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 18:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 20:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 22:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 24:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 26:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 30:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 32:
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
            case 36:
                callContext.value1 = obj;
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
            case 40:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 42:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 44:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 46:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 48:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 50:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 52:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 54:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 56:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 58:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 60:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 62:
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
            case 67:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 70:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 71:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 72:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 74:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 75:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 76:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static boolean isNull(Object x) {
        return x == LList.Empty;
    }

    public static void setCar$Ex(Pair p, Object x) {
        p.setCar(x);
    }

    public static void setCdr$Ex(Pair p, Object x) {
        p.setCdr(x);
    }

    static Object car(Pair x) {
        return x.getCar();
    }

    static Object cdr(Pair x) {
        return x.getCdr();
    }

    static Object caar(Object arg) {
        return ((Pair) ((Pair) arg).getCar()).getCar();
    }

    static void lambda1(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) arg).getCar(), Lit1, value);
    }

    static Object cadr(Object arg) {
        return ((Pair) ((Pair) arg).getCdr()).getCar();
    }

    static void lambda2(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) arg).getCdr(), Lit1, value);
    }

    static Object cdar(Object arg) {
        return ((Pair) ((Pair) arg).getCar()).getCdr();
    }

    static void lambda3(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) arg).getCar(), Lit2, value);
    }

    static Object cddr(Object arg) {
        return ((Pair) ((Pair) arg).getCdr()).getCdr();
    }

    static void lambda4(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) arg).getCdr(), Lit2, value);
    }

    static Object caaar(Object arg) {
        return ((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCar();
    }

    static void lambda5(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) arg).getCar()).getCar(), Lit1, value);
    }

    static Object caadr(Object arg) {
        return ((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCar();
    }

    static void lambda6(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) arg).getCdr()).getCar(), Lit1, value);
    }

    static Object cadar(Object arg) {
        return ((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCar();
    }

    static void lambda7(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) arg).getCar()).getCdr(), Lit1, value);
    }

    static Object caddr(Object arg) {
        return ((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCar();
    }

    static void lambda8(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) arg).getCdr()).getCdr(), Lit1, value);
    }

    static Object cdaar(Object arg) {
        return ((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCdr();
    }

    static void lambda9(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) arg).getCar()).getCar(), Lit2, value);
    }

    static Object cdadr(Object arg) {
        return ((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCdr();
    }

    static void lambda10(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) arg).getCdr()).getCar(), Lit2, value);
    }

    static Object cddar(Object arg) {
        return ((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCdr();
    }

    static void lambda11(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) arg).getCar()).getCdr(), Lit2, value);
    }

    static Object cdddr(Object arg) {
        return ((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCdr();
    }

    static void lambda12(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) arg).getCdr()).getCdr(), Lit2, value);
    }

    static Object caaaar(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCar()).getCar();
    }

    static void lambda13(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCar(), Lit1, value);
    }

    static Object caaadr(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCar()).getCar();
    }

    static void lambda14(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCar(), Lit1, value);
    }

    static Object caadar(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCar()).getCar();
    }

    static void lambda15(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCar(), Lit1, value);
    }

    static Object caaddr(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCar()).getCar();
    }

    static void lambda16(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCar(), Lit1, value);
    }

    static Object cadaar(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCdr()).getCar();
    }

    static void lambda17(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCdr(), Lit1, value);
    }

    static Object cadadr(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCdr()).getCar();
    }

    static void lambda18(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCdr(), Lit1, value);
    }

    static Object caddar(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCdr()).getCar();
    }

    static void lambda19(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCdr(), Lit1, value);
    }

    static Object cadddr(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCdr()).getCar();
    }

    static void lambda20(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCdr(), Lit1, value);
    }

    static Object cdaaar(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCar()).getCdr();
    }

    static void lambda21(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCar(), Lit2, value);
    }

    static Object cdaadr(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCar()).getCdr();
    }

    static void lambda22(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCar(), Lit2, value);
    }

    static Object cdadar(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCar()).getCdr();
    }

    static void lambda23(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCar(), Lit2, value);
    }

    static Object cdaddr(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCar()).getCdr();
    }

    static void lambda24(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCar(), Lit2, value);
    }

    static Object cddaar(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCdr()).getCdr();
    }

    static void lambda25(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCar()).getCar()).getCdr(), Lit2, value);
    }

    static Object cddadr(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCdr()).getCdr();
    }

    static void lambda26(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCdr()).getCar()).getCdr(), Lit2, value);
    }

    static Object cdddar(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCdr()).getCdr();
    }

    static void lambda27(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCar()).getCdr()).getCdr(), Lit2, value);
    }

    static Object cddddr(Object arg) {
        return ((Pair) ((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCdr()).getCdr();
    }

    static void lambda28(Object arg, Object value) {
        SlotSet.set$Mnfield$Ex.apply3(((Pair) ((Pair) ((Pair) arg).getCdr()).getCdr()).getCdr(), Lit2, value);
    }

    public static int length(LList list) {
        return LList.length(list);
    }

    public static LList reverse(LList list) {
        Object obj = LList.Empty;
        Object obj2 = list;
        while (!isNull(obj2)) {
            try {
                Pair pair = (Pair) obj2;
                obj2 = cdr.apply1(pair);
                Pair cons = cons(car.apply1(pair), obj);
            } catch (ClassCastException e) {
                throw new WrongType(e, "pair", -2, obj2);
            }
        }
        return (LList) obj;
    }

    public static Object listTail(Object list, int count) {
        return LList.listTail(list, count);
    }

    public static Object listRef(Object list, int index) {
        return car.apply1(listTail(list, index));
    }

    public static boolean isList(Object obj) {
        return LList.listLength(obj, false) >= 0;
    }

    public static LList reverse$Ex(LList list) {
        return LList.reverseInPlace(list);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return isPair(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 3:
                return isNull(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 6:
                try {
                    return car((Pair) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "car", 1, obj);
                }
            case 7:
                try {
                    return cdr((Pair) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "cdr", 1, obj);
                }
            case 9:
                return caar(obj);
            case 11:
                return cadr(obj);
            case 13:
                return cdar(obj);
            case 15:
                return cddr(obj);
            case 17:
                return caaar(obj);
            case 19:
                return caadr(obj);
            case 21:
                return cadar(obj);
            case 23:
                return caddr(obj);
            case 25:
                return cdaar(obj);
            case 27:
                return cdadr(obj);
            case 29:
                return cddar(obj);
            case 31:
                return cdddr(obj);
            case 33:
                return caaaar(obj);
            case 35:
                return caaadr(obj);
            case 37:
                return caadar(obj);
            case 39:
                return caaddr(obj);
            case 41:
                return cadaar(obj);
            case 43:
                return cadadr(obj);
            case 45:
                return caddar(obj);
            case 47:
                return cadddr(obj);
            case 49:
                return cdaaar(obj);
            case 51:
                return cdaadr(obj);
            case 53:
                return cdadar(obj);
            case 55:
                return cdaddr(obj);
            case 57:
                return cddaar(obj);
            case 59:
                return cddadr(obj);
            case 61:
                return cdddar(obj);
            case 63:
                return cddddr(obj);
            case 64:
                try {
                    return Integer.valueOf(length((LList) obj));
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "length", 1, obj);
                }
            case 65:
                try {
                    return reverse((LList) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "reverse", 1, obj);
                }
            case 68:
                return isList(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 69:
                try {
                    return reverse$Ex((LList) obj);
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "reverse!", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static Object memq(Object x, Object list) {
        boolean x2;
        Object lst = list;
        while (true) {
            x2 = lst instanceof Pair;
            if (!x2) {
                break;
            }
            try {
                Pair p = (Pair) lst;
                if (x == p.getCar()) {
                    return lst;
                }
                lst = p.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "p", -2, lst);
            }
        }
        return x2 ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object memv(Object x, Object list) {
        boolean x2;
        Object lst = list;
        while (true) {
            x2 = lst instanceof Pair;
            if (!x2) {
                break;
            }
            try {
                Pair p = (Pair) lst;
                if (Scheme.isEqv.apply2(x, p.getCar()) != Boolean.FALSE) {
                    return lst;
                }
                lst = p.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "p", -2, lst);
            }
        }
        return x2 ? Boolean.TRUE : Boolean.FALSE;
    }

    public static Object member(Object x, Object list, Procedure test) {
        boolean x2;
        Object lst = list;
        while (true) {
            x2 = lst instanceof Pair;
            if (!x2) {
                break;
            }
            try {
                Pair p = (Pair) lst;
                if (test.apply2(x, p.getCar()) != Boolean.FALSE) {
                    return lst;
                }
                lst = p.getCdr();
            } catch (ClassCastException e) {
                throw new WrongType(e, "p", -2, lst);
            }
        }
        return x2 ? Boolean.TRUE : Boolean.FALSE;
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 72:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                if (!(obj3 instanceof Procedure)) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 76:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                if (!(obj3 instanceof Procedure)) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object assq(Object x, Object list) {
        LList list2;
        while (list2 != LList.Empty) {
            Object apply1 = car.apply1(list2);
            try {
                Pair pair = (Pair) apply1;
                if (pair.getCar() == x) {
                    return pair;
                }
                list2 = cdr.apply1(list2);
            } catch (ClassCastException e) {
                throw new WrongType(e, "pair", -2, apply1);
            }
        }
        return Boolean.FALSE;
    }

    public static Object assv(Object x, Object list) {
        LList list2;
        while (list2 != LList.Empty) {
            Object apply1 = car.apply1(list2);
            try {
                Pair pair = (Pair) apply1;
                if (Scheme.isEqv.apply2(pair.getCar(), x) != Boolean.FALSE) {
                    return pair;
                }
                list2 = cdr.apply1(list2);
            } catch (ClassCastException e) {
                throw new WrongType(e, "pair", -2, apply1);
            }
        }
        return Boolean.FALSE;
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 2:
                return cons(obj, obj2);
            case 4:
                try {
                    setCar$Ex((Pair) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-car!", 1, obj);
                }
            case 5:
                try {
                    setCdr$Ex((Pair) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "set-cdr!", 1, obj);
                }
            case 8:
                lambda1(obj, obj2);
                return Values.empty;
            case 10:
                lambda2(obj, obj2);
                return Values.empty;
            case 12:
                lambda3(obj, obj2);
                return Values.empty;
            case 14:
                lambda4(obj, obj2);
                return Values.empty;
            case 16:
                lambda5(obj, obj2);
                return Values.empty;
            case 18:
                lambda6(obj, obj2);
                return Values.empty;
            case 20:
                lambda7(obj, obj2);
                return Values.empty;
            case 22:
                lambda8(obj, obj2);
                return Values.empty;
            case 24:
                lambda9(obj, obj2);
                return Values.empty;
            case 26:
                lambda10(obj, obj2);
                return Values.empty;
            case 28:
                lambda11(obj, obj2);
                return Values.empty;
            case 30:
                lambda12(obj, obj2);
                return Values.empty;
            case 32:
                lambda13(obj, obj2);
                return Values.empty;
            case 34:
                lambda14(obj, obj2);
                return Values.empty;
            case 36:
                lambda15(obj, obj2);
                return Values.empty;
            case 38:
                lambda16(obj, obj2);
                return Values.empty;
            case 40:
                lambda17(obj, obj2);
                return Values.empty;
            case 42:
                lambda18(obj, obj2);
                return Values.empty;
            case 44:
                lambda19(obj, obj2);
                return Values.empty;
            case 46:
                lambda20(obj, obj2);
                return Values.empty;
            case 48:
                lambda21(obj, obj2);
                return Values.empty;
            case 50:
                lambda22(obj, obj2);
                return Values.empty;
            case 52:
                lambda23(obj, obj2);
                return Values.empty;
            case 54:
                lambda24(obj, obj2);
                return Values.empty;
            case 56:
                lambda25(obj, obj2);
                return Values.empty;
            case 58:
                lambda26(obj, obj2);
                return Values.empty;
            case 60:
                lambda27(obj, obj2);
                return Values.empty;
            case 62:
                lambda28(obj, obj2);
                return Values.empty;
            case 66:
                try {
                    return listTail(obj, ((Number) obj2).intValue());
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "list-tail", 2, obj2);
                }
            case 67:
                try {
                    return listRef(obj, ((Number) obj2).intValue());
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "list-ref", 2, obj2);
                }
            case 70:
                return memq(obj, obj2);
            case 71:
                return memv(obj, obj2);
            case 72:
                return member(obj, obj2);
            case 74:
                return assq(obj, obj2);
            case 75:
                return assv(obj, obj2);
            case 76:
                return assoc(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 72:
                try {
                    return member(obj, obj2, (Procedure) obj3);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "member", 3, obj3);
                }
            case 76:
                try {
                    return assoc(obj, obj2, (Procedure) obj3);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "assoc", 3, obj3);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static Object assoc(Object x, Object list, Procedure test) {
        LList list2;
        while (list2 != LList.Empty) {
            Object apply1 = car.apply1(list2);
            try {
                Pair pair = (Pair) apply1;
                if (test.apply2(pair.getCar(), x) != Boolean.FALSE) {
                    return pair;
                }
                list2 = cdr.apply1(list2);
            } catch (ClassCastException e) {
                throw new WrongType(e, "pair", -2, apply1);
            }
        }
        return Boolean.FALSE;
    }
}

package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.prim_syntax;
import kawa.lib.std_syntax;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.syntax_case;

/* compiled from: enums.scm */
public class enums extends ModuleBody {
    public static final Macro $Prvt$$Pcdefine$Mnenum;
    public static final enums $instance = new enums();
    static final PairWithPosition Lit0 = PairWithPosition.make(Lit42, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 65549);
    static final PairWithPosition Lit1 = PairWithPosition.make(Lit46, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 69645);
    static final PairWithPosition Lit10 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 127013), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 127013);
    static final SimpleSymbol Lit11;
    static final SyntaxPattern Lit12 = new SyntaxPattern("\f\u0007\f\u0002\f\u000f,\r\u0017\u0010\b\b\f\u001f\f'\r/(\b\b", new Object[]{"findkeywords"}, 6);
    static final SyntaxTemplate Lit13 = new SyntaxTemplate("\u0001\u0001\u0003\u0001\u0001\u0003", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit14 = new SyntaxTemplate("\u0001\u0001\u0003\u0001\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\t\u000b9\t\u001b\t#\b\u0015\u0013\b-+", new Object[]{Lit11, "findkeywords"}, 1);
    static final SyntaxPattern Lit15 = new SyntaxPattern("\f\u0007\f\u0002\f\u000f,\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{"findkeywords"}, 4);
    static final SyntaxTemplate Lit16 = new SyntaxTemplate("\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004\t\u000b\u0019\b\u0015\u0013\b\u001d\u001b", new Object[]{Lit21}, 1);
    static final SyntaxPattern Lit17 = new SyntaxPattern("\f\u0007\b", new Object[0], 1);
    static final SyntaxPattern Lit18 = new SyntaxPattern("\f\u0007\f\u000f\b", new Object[0], 2);
    static final SyntaxPattern Lit19 = new SyntaxPattern("\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3);
    static final PairWithPosition Lit2 = PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 69658), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 69658);
    static final SyntaxTemplate Lit20;
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("%define-enum").readResolve());
    static final SyntaxPattern Lit22 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017,\r\u001f\u0018\b\b\r' \b\b", new Object[0], 5);
    static final SyntaxTemplate Lit23 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u000b", new Object[0], 0);
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("[]").readResolve());
    static final SyntaxTemplate Lit25 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\b\u001d\u001b", new Object[0], 1);
    static final SyntaxTemplate Lit26 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0013", new Object[0], 0);
    static final SyntaxTemplate Lit27 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\b%#", new Object[0], 1);
    static final SyntaxTemplate Lit28 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("define-simple-class").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 262154)}, 0);
    static final SyntaxTemplate Lit29 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit44, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 266252)}, 0);
    static final PairWithPosition Lit3 = PairWithPosition.make(Lit48, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 69665);
    static final SyntaxTemplate Lit30 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit48, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 266269)}, 0);
    static final SyntaxTemplate Lit31 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit43, PairWithPosition.make(PairWithPosition.make(Lit52, PairWithPosition.make(Lit53, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 266284), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 266278), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 266278), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 266278)}, 0);
    static final SyntaxTemplate Lit32 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit40, PairWithPosition.make(Lit41, PairWithPosition.make(Lit42, PairWithPosition.make(Lit47, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 282642), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 282640), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 282639), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 282630)}, 0);
    static final SyntaxTemplate Lit33 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit42, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 282649)}, 0);
    static final SyntaxTemplate Lit34 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit46, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 286726)}, 0);
    static final SyntaxTemplate Lit35 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit43, PairWithPosition.make(Lit45, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 286739), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 286739)}, 0);
    static final SyntaxTemplate Lit36 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve(), Pair.make(Lit44, Pair.make(Pair.make((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve(), Pair.make(Lit40, LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 290823)}, 0);
    static final SyntaxTemplate Lit37 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit41, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 290882)}, 0);
    static final SyntaxTemplate Lit38 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit39 = new SyntaxTemplate("\u0001\u0001\u0001\u0003\u0003", "\u0010", new Object[0], 0);
    static final PairWithPosition Lit4 = PairWithPosition.make(Lit43, PairWithPosition.make(PairWithPosition.make(Lit52, PairWithPosition.make(Lit53, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 69680), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 69674), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 69674), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 69674);
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("valueOf").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("s").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("::").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("java.lang.Enum").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("static").readResolve());
    static final Keyword Lit46 = Keyword.make("allocation");
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("String").readResolve());
    static final Keyword Lit48 = Keyword.make("access");
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("*init*").readResolve());
    static final PairWithPosition Lit5 = PairWithPosition.make(Keyword.make("init"), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 73741);
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("str").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("num").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("enum").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("final").readResolve());
    static final PairWithPosition Lit6 = PairWithPosition.make(PairWithPosition.make(Lit49, PairWithPosition.make(PairWithPosition.make(Lit50, PairWithPosition.make(Lit42, PairWithPosition.make(Lit47, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90133), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90130), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90125), PairWithPosition.make(PairWithPosition.make(Lit51, PairWithPosition.make(Lit42, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("int").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90149), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90146), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90141), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90141), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90125), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90117), PairWithPosition.make(Lit48, PairWithPosition.make(PairWithPosition.make(Lit43, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("private").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 94222), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 94222), PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("invoke-special").readResolve(), PairWithPosition.make(Lit44, PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("this").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98340), PairWithPosition.make(PairWithPosition.make(Lit43, PairWithPosition.make(Lit49, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98348), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98348), PairWithPosition.make(Lit50, PairWithPosition.make(Lit51, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98359), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98355), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98347), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98340), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98325), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98309), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 98309), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 94221), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 94213), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 90116);
    static final PairWithPosition Lit7 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("values").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 126981);
    static final PairWithPosition Lit8 = PairWithPosition.make(Lit42, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 126990);
    static final PairWithPosition Lit9 = PairWithPosition.make(Lit46, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/enums.scm", 127000);
    public static final Macro define$Mnenum;

    public enums() {
        ModuleInfo.register(this);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return lambda1(obj);
            case 2:
                return lambda2(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
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

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static {
        Object[] objArr = new Object[2];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("define-enum").readResolve();
        Lit11 = simpleSymbol;
        objArr[0] = simpleSymbol;
        objArr[1] = "findkeywords";
        Lit20 = new SyntaxTemplate("\u0001\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\t\u000b\t\u0010\b\u0015\u0013", objArr, 1);
        simpleSymbol = Lit11;
        ModuleBody moduleBody = $instance;
        define$Mnenum = Macro.make(simpleSymbol, new ModuleMethod(moduleBody, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        $Prvt$$Pcdefine$Mnenum = Macro.make(Lit21, new ModuleMethod(moduleBody, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        $instance.run();
    }

    static SimpleSymbol symbolAppend$V(Object[] argsArray) {
        Object car;
        LList syms = LList.makeList(argsArray, 0);
        Procedure procedure = Scheme.apply;
        ModuleMethod moduleMethod = strings.string$Mnappend;
        Pair result = LList.Empty;
        Object arg0 = syms;
        while (arg0 != LList.Empty) {
            try {
                Pair arg02 = (Pair) arg0;
                Object arg03 = arg02.getCdr();
                car = arg02.getCar();
                try {
                    result = Pair.make(misc.symbol$To$String((Symbol) car), result);
                    arg0 = arg03;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "symbol->string", 1, car);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "arg0", -2, arg0);
            }
        }
        car = procedure.apply2(moduleMethod, LList.reverseInPlace(result));
        try {
            return misc.string$To$Symbol((CharSequence) car);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "string->symbol", 1, car);
        }
    }

    static Object makeFieldDesc(Symbol t$Mnname, Symbol e$Mnname, int e$Mnval) {
        Object[] objArr = new Object[2];
        objArr[0] = e$Mnname;
        Object[] objArr2 = new Object[2];
        objArr2[0] = Lit0;
        Object[] objArr3 = new Object[2];
        objArr3[0] = t$Mnname;
        Object[] objArr4 = new Object[2];
        objArr4[0] = Lit1;
        PairWithPosition pairWithPosition = Lit2;
        Object[] objArr5 = new Object[2];
        objArr5[0] = Lit3;
        PairWithPosition pairWithPosition2 = Lit4;
        Object[] objArr6 = new Object[2];
        objArr6[0] = Lit5;
        Object[] objArr7 = new Object[2];
        objArr7[0] = t$Mnname;
        Object[] objArr8 = new Object[2];
        objArr8[0] = misc.symbol$To$String(e$Mnname);
        objArr8[1] = Quote.consX$V(new Object[]{Integer.valueOf(e$Mnval), LList.Empty});
        objArr7[1] = Quote.consX$V(objArr8);
        objArr6[1] = Pair.make(Quote.consX$V(objArr7), LList.Empty);
        objArr5[1] = Pair.make(pairWithPosition2, Quote.append$V(objArr6));
        objArr4[1] = Pair.make(pairWithPosition, Quote.append$V(objArr5));
        objArr3[1] = Quote.append$V(objArr4);
        objArr2[1] = Quote.consX$V(objArr3);
        objArr[1] = Quote.append$V(objArr2);
        return Quote.consX$V(objArr);
    }

    static PairWithPosition makeInit() {
        return Lit6;
    }

    static Pair makeValues(Symbol t$Mnarr, LList e$Mnnames) {
        PairWithPosition pairWithPosition = Lit7;
        Object[] objArr = new Object[2];
        objArr[0] = Lit8;
        Object[] objArr2 = new Object[2];
        objArr2[0] = t$Mnarr;
        Object[] objArr3 = new Object[2];
        objArr3[0] = Lit9;
        PairWithPosition pairWithPosition2 = Lit10;
        Object[] objArr4 = new Object[2];
        objArr4[0] = t$Mnarr;
        objArr4[1] = Quote.append$V(new Object[]{e$Mnnames, LList.Empty});
        objArr3[1] = Pair.make(pairWithPosition2, Pair.make(Quote.consX$V(objArr4), LList.Empty));
        objArr2[1] = Quote.append$V(objArr3);
        objArr[1] = Quote.consX$V(objArr2);
        return Pair.make(pairWithPosition, Quote.append$V(objArr));
    }

    static Object lambda1(Object form) {
        Object[] allocVars = SyntaxPattern.allocVars(6, null);
        if (Lit12.match(form, allocVars, 0)) {
            if (std_syntax.isIdentifier(Lit13.execute(allocVars, TemplateScope.make()))) {
                return Lit14.execute(allocVars, TemplateScope.make());
            }
        }
        if (Lit15.match(form, allocVars, 0)) {
            return Lit16.execute(allocVars, TemplateScope.make());
        } else if (Lit17.match(form, allocVars, 0)) {
            r0 = "no enum type name given";
            if (r0 instanceof Object[]) {
                allocVars = (Object[]) r0;
            } else {
                allocVars = new Object[]{r0};
            }
            return prim_syntax.syntaxError(form, allocVars);
        } else if (Lit18.match(form, allocVars, 0)) {
            r0 = "no enum constants given";
            if (r0 instanceof Object[]) {
                allocVars = (Object[]) r0;
            } else {
                allocVars = new Object[]{r0};
            }
            return prim_syntax.syntaxError(form, allocVars);
        } else if (!Lit19.match(form, allocVars, 0)) {
            return syntax_case.error("syntax-case", form);
        } else {
            return Lit20.execute(allocVars, TemplateScope.make());
        }
    }

    static LList mapNames(Symbol t$Mnname, LList e$Mnnames, int i) {
        if (lists.isNull(e$Mnnames)) {
            return LList.Empty;
        }
        Object apply1 = lists.car.apply1(e$Mnnames);
        try {
            Object makeFieldDesc = makeFieldDesc(t$Mnname, (Symbol) apply1, i);
            apply1 = lists.cdr.apply1(e$Mnnames);
            try {
                return lists.cons(makeFieldDesc, mapNames(t$Mnname, (LList) apply1, i + 1));
            } catch (ClassCastException e) {
                throw new WrongType(e, "map-names", 1, apply1);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "make-field-desc", 1, apply1);
        }
    }

    static Object lambda2(Object form) {
        Object[] allocVars = SyntaxPattern.allocVars(5, null);
        if (!Lit22.match(form, allocVars, 0)) {
            return syntax_case.error("syntax-case", form);
        }
        Object execute = Lit23.execute(allocVars, TemplateScope.make());
        try {
            Symbol t$Mnname = (Symbol) execute;
            Symbol t$Mnarr = symbolAppend$V(new Object[]{t$Mnname, Lit24});
            Object execute2 = Lit25.execute(allocVars, TemplateScope.make());
            try {
                LList e$Mnnames = (LList) execute2;
                lists.length(e$Mnnames);
                LList field$Mndescs = mapNames(t$Mnname, e$Mnnames, 0);
                LList init = makeInit();
                LList values$Mnmethod = makeValues(t$Mnarr, e$Mnnames);
                Object execute3 = Lit26.execute(allocVars, TemplateScope.make());
                try {
                    LList opts = (LList) execute3;
                    Object execute4 = Lit27.execute(allocVars, TemplateScope.make());
                    try {
                        LList other$Mndefs = (LList) execute4;
                        TemplateScope make = TemplateScope.make();
                        Object[] objArr = new Object[2];
                        objArr[0] = Lit28.execute(allocVars, make);
                        Object[] objArr2 = new Object[2];
                        objArr2[0] = std_syntax.datum$To$SyntaxObject(form, t$Mnname);
                        Object execute5 = Lit29.execute(allocVars, make);
                        Object[] objArr3 = new Object[2];
                        objArr3[0] = Lit30.execute(allocVars, make);
                        Object execute6 = Lit31.execute(allocVars, make);
                        Object[] objArr4 = new Object[2];
                        objArr4[0] = std_syntax.datum$To$SyntaxObject(form, opts);
                        Object[] objArr5 = new Object[2];
                        objArr5[0] = std_syntax.datum$To$SyntaxObject(form, init);
                        Object[] objArr6 = new Object[2];
                        objArr6[0] = std_syntax.datum$To$SyntaxObject(form, values$Mnmethod);
                        Object execute7 = Lit32.execute(allocVars, make);
                        Object[] objArr7 = new Object[2];
                        objArr7[0] = Lit33.execute(allocVars, make);
                        Object[] objArr8 = new Object[2];
                        objArr8[0] = std_syntax.datum$To$SyntaxObject(form, t$Mnname);
                        Object[] objArr9 = new Object[2];
                        objArr9[0] = Lit34.execute(allocVars, make);
                        objArr9[1] = Pair.make(Lit35.execute(allocVars, make), Pair.make(Pair.make(Lit36.execute(allocVars, make), Quote.consX$V(new Object[]{std_syntax.datum$To$SyntaxObject(form, t$Mnname), Lit37.execute(allocVars, make)})), Lit38.execute(allocVars, make)));
                        objArr8[1] = Quote.append$V(objArr9);
                        objArr7[1] = Quote.consX$V(objArr8);
                        Pair make2 = Pair.make(execute7, Quote.append$V(objArr7));
                        objArr7 = new Object[2];
                        objArr7[0] = std_syntax.datum$To$SyntaxObject(form, field$Mndescs);
                        objArr7[1] = Quote.append$V(new Object[]{std_syntax.datum$To$SyntaxObject(form, other$Mndefs), Lit39.execute(allocVars, make)});
                        objArr6[1] = Pair.make(make2, Quote.append$V(objArr7));
                        objArr5[1] = Quote.consX$V(objArr6);
                        objArr4[1] = Quote.consX$V(objArr5);
                        objArr3[1] = Pair.make(execute6, Quote.append$V(objArr4));
                        objArr2[1] = Pair.make(execute5, Quote.append$V(objArr3));
                        objArr[1] = Quote.consX$V(objArr2);
                        return Quote.append$V(objArr);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "other-defs", -2, execute4);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "opts", -2, execute3);
                }
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "e-names", -2, execute2);
            }
        } catch (ClassCastException e222) {
            throw new WrongType(e222, "t-name", -2, execute);
        }
    }
}

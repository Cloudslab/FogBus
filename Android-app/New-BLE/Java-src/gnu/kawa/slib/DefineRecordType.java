package gnu.kawa.slib;

import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;

/* compiled from: DefineRecordType.scm */
public class DefineRecordType extends ModuleBody {
    public static final Macro $Prvt$$Pcdefine$Mnrecord$Mnfield = Macro.make(Lit2, Lit3, $instance);
    public static final DefineRecordType $instance = new DefineRecordType();
    static final SimpleSymbol Lit0;
    static final SyntaxRules Lit1;
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("slot-set!").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("tmp").readResolve());
    static final SimpleSymbol Lit2;
    static final SyntaxRules Lit3;
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("define").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("obj").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("::").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("slot-ref").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("value").readResolve());
    public static final Macro define$Mnrecord$Mntype = Macro.make(Lit0, Lit1, $instance);

    public DefineRecordType() {
        ModuleInfo.register(this);
    }

    static {
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("%define-record-field").readResolve();
        Lit2 = simpleSymbol;
        objArr[0] = simpleSymbol;
        r8 = new SyntaxRule[2];
        r8[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004Y\t\u0013\b\u0011\u0018\f\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u001c\u0011\u0018\f\b\u0011\u0018$\b\u000b", new Object[]{Lit4, Lit5, Lit6, Lit7, Lit8}, 0);
        r8[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004á\u0011\u0018\fY\t\u0013\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0003\b\u0011\u0018$\u0011\u0018\u0014\b\u0011\u0018,\b\u000b\b\u0011\u0018\fi\t\u001bA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0003\u00184\u0011\u0018\u001c\u0011\u0018<\b\u0011\u0018D\u0011\u0018\u0014)\u0011\u0018,\b\u000b\u0018L", new Object[]{Lit10, Lit4, Lit5, Lit6, Lit7, Lit8, PairWithPosition.make(Lit9, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/DefineRecordType.scm", 208936), (SimpleSymbol) new SimpleSymbol("<void>").readResolve(), Lit11, PairWithPosition.make(Lit9, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/DefineRecordType.scm", 213021)}, 0);
        Lit3 = new SyntaxRules(objArr, r8, 4);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("define-record-type").readResolve();
        Lit0 = simpleSymbol;
        objArr[0] = simpleSymbol;
        r8 = new SyntaxRule[1];
        r8[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u000f\r\u0017\u0010\b\b\f\u001f-\f'\f/3 \u0018\b", new Object[0], 7), "\u0001\u0001\u0003\u0001\u0003\u0003\u0002", "\u0011\u0018\u0004Y\u0011\u0018\f\t\u0003\t\u0010\b%\b#¹\u0011\u0018\u0014!\t\u001b\u0018\u001c\u0011\u0018$\u0011\u0018,\b\u0011\u00184\u0011\u0018<\b\u0003ǁ\u0011\u0018\u0014)\t\u000b\b\u0015\u0013\u0011\u0018$\t\u0003\b\u0011\u0018Dy\b\u0011\u0018L\u0011\u0018$\t\u0003\b\u0011\u0018T\b\u0003\u0011\u0018\u0004\b\u0015\u0011\u0018\\\u0011\u0018L)\u0011\u0018d\b\u0013\b\u0013\u0018l\b%\u0011\u0018t\t\u0003\t#\t+2", new Object[]{Lit10, (SimpleSymbol) new SimpleSymbol("define-simple-class").readResolve(), Lit4, PairWithPosition.make(Lit5, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/DefineRecordType.scm", 122907), Lit6, (SimpleSymbol) new SimpleSymbol("<boolean>").readResolve(), (SimpleSymbol) new SimpleSymbol(GetNamedPart.INSTANCEOF_METHOD_NAME).readResolve(), Lit5, (SimpleSymbol) new SimpleSymbol("let").readResolve(), Lit12, (SimpleSymbol) new SimpleSymbol("make").readResolve(), Lit11, Lit8, PairWithPosition.make(Lit12, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/DefineRecordType.scm", 143365), Lit2}, 1);
        Lit1 = new SyntaxRules(objArr, r8, 7);
        $instance.run();
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }
}

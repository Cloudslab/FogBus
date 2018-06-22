package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.text.Path;
import kawa.lang.Eval;
import kawa.lang.Macro;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.lib.numbers;
import kawa.lib.parameters;
import kawa.lib.ports;
import kawa.lib.std_syntax;
import kawa.lib.strings;
import kawa.standard.Scheme;
import kawa.standard.readchar;
import kawa.standard.syntax_case;

/* compiled from: testing.scm */
public class testing extends ModuleBody {
    public static final ModuleMethod $Pctest$Mnbegin;
    static final ModuleMethod $Pctest$Mnnull$Mncallback;
    public static final ModuleMethod $Prvt$$Pctest$Mnapproximimate$Eq;
    public static final ModuleMethod $Prvt$$Pctest$Mnas$Mnspecifier;
    public static final Macro $Prvt$$Pctest$Mncomp1body = Macro.make(Lit92, Lit93, $instance);
    public static final Macro $Prvt$$Pctest$Mncomp2body = Macro.make(Lit89, Lit90, $instance);
    public static final ModuleMethod $Prvt$$Pctest$Mnend;
    public static final Macro $Prvt$$Pctest$Mnerror = Macro.make(Lit115, Lit116, $instance);
    public static final Macro $Prvt$$Pctest$Mnevaluate$Mnwith$Mncatch = Macro.make(Lit84, Lit85, $instance);
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnall;
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnany;
    public static final ModuleMethod $Prvt$$Pctest$Mnmatch$Mnnth;
    public static final ModuleMethod $Prvt$$Pctest$Mnon$Mntest$Mnbegin;
    public static final ModuleMethod $Prvt$$Pctest$Mnon$Mntest$Mnend;
    public static final ModuleMethod $Prvt$$Pctest$Mnreport$Mnresult;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist$Ex;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist;
    public static final ModuleMethod $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist$Ex;
    public static final ModuleMethod $Prvt$$Pctest$Mnshould$Mnexecute;
    public static final Macro $Prvt$test$Mngroup = Macro.make(Lit70, Lit71, $instance);
    public static final testing $instance = new testing();
    static final IntNum Lit0 = IntNum.make(0);
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("result-kind").readResolve());
    static final PairWithPosition Lit10;
    static final SyntaxPattern Lit100 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\b", new Object[0], 4);
    static final SyntaxTemplate Lit101 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b\u001b\b\u0011\u0018<\u0011\u0018$\b\u0013", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2756622), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2756622), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2756619), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2764841), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2764841), Lit92}, 0);
    static final SyntaxPattern Lit102 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit103;
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol("test-eqv").readResolve());
    static final SyntaxTemplate Lit105 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("eqv?").readResolve()}, 0);
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("test-eq").readResolve());
    static final SyntaxTemplate Lit107 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("eq?").readResolve()}, 0);
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol("test-equal").readResolve());
    static final SyntaxTemplate Lit109 = new SyntaxTemplate("", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("equal?").readResolve()}, 0);
    static final PairWithPosition Lit11;
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("test-approximate").readResolve());
    static final SyntaxPattern Lit111 = new SyntaxPattern("\\\f\u0007\f\u000f\f\u0017\f\u001f\f'\b\f/\b", new Object[0], 6);
    static final SyntaxTemplate Lit112 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b+\b\u0011\u0018<\u0011\u0018$)\u0011\u0018D\b#\t\u0013\b\u001b", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2891788), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2891788), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2891785), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2900007), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2900007), Lit89, Lit91}, 0);
    static final SyntaxPattern Lit113 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\b", new Object[0], 5);
    static final SyntaxTemplate Lit114;
    static final SimpleSymbol Lit115;
    static final SyntaxRules Lit116;
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("test-error").readResolve());
    static final SyntaxPattern Lit118 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\b", new Object[0], 5);
    static final SyntaxTemplate Lit119;
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("pass").readResolve());
    static final SyntaxPattern Lit120 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\b", new Object[0], 4);
    static final SyntaxTemplate Lit121 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b\b\u0011\u0018$\u0011\u0018\u001c\t\u000b\b\u0013", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3493902), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3493902), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3493899), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3493898), Lit52, Lit149, Lit115}, 0);
    static final SyntaxPattern Lit122 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit123;
    static final SimpleSymbol Lit124 = ((SimpleSymbol) new SimpleSymbol("test-apply").readResolve());
    static final SimpleSymbol Lit125;
    static final SyntaxRules Lit126;
    static final SimpleSymbol Lit127;
    static final SimpleSymbol Lit128;
    static final SyntaxRules Lit129;
    static final IntNum Lit13;
    static final SimpleSymbol Lit130;
    static final SimpleSymbol Lit131;
    static final SyntaxRules Lit132;
    static final SimpleSymbol Lit133;
    static final SimpleSymbol Lit134;
    static final SyntaxRules Lit135;
    static final SimpleSymbol Lit136;
    static final SimpleSymbol Lit137;
    static final SyntaxRules Lit138;
    static final SimpleSymbol Lit139;
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("fail").readResolve());
    static final SyntaxRules Lit140;
    static final SimpleSymbol Lit141 = ((SimpleSymbol) new SimpleSymbol("test-match-name").readResolve());
    static final SimpleSymbol Lit142 = ((SimpleSymbol) new SimpleSymbol("test-read-eval-string").readResolve());
    static final SimpleSymbol Lit143 = ((SimpleSymbol) new SimpleSymbol("runner").readResolve());
    static final SimpleSymbol Lit144 = ((SimpleSymbol) new SimpleSymbol("let").readResolve());
    static final SimpleSymbol Lit145 = ((SimpleSymbol) new SimpleSymbol("cons").readResolve());
    static final SimpleSymbol Lit146 = ((SimpleSymbol) new SimpleSymbol("test-runner-current").readResolve());
    static final SimpleSymbol Lit147 = ((SimpleSymbol) new SimpleSymbol("lambda").readResolve());
    static final SimpleSymbol Lit148 = ((SimpleSymbol) new SimpleSymbol("saved-runner").readResolve());
    static final SimpleSymbol Lit149 = ((SimpleSymbol) new SimpleSymbol("r").readResolve());
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit150 = ((SimpleSymbol) new SimpleSymbol("let*").readResolve());
    static final SimpleSymbol Lit151 = ((SimpleSymbol) new SimpleSymbol("ex").readResolve());
    static final SimpleSymbol Lit152 = ((SimpleSymbol) new SimpleSymbol("expected-error").readResolve());
    static final SimpleSymbol Lit153 = ((SimpleSymbol) new SimpleSymbol("et").readResolve());
    static final SimpleSymbol Lit154 = ((SimpleSymbol) new SimpleSymbol("try-catch").readResolve());
    static final SimpleSymbol Lit155 = ((SimpleSymbol) new SimpleSymbol("actual-value").readResolve());
    static final SimpleSymbol Lit156 = ((SimpleSymbol) new SimpleSymbol("<java.lang.Throwable>").readResolve());
    static final SimpleSymbol Lit157 = ((SimpleSymbol) new SimpleSymbol("actual-error").readResolve());
    static final SimpleSymbol Lit158 = ((SimpleSymbol) new SimpleSymbol("cond").readResolve());
    static final SimpleSymbol Lit159 = ((SimpleSymbol) new SimpleSymbol(GetNamedPart.INSTANCEOF_METHOD_NAME).readResolve());
    static final SyntaxPattern Lit16 = new SyntaxPattern("L\f\u0007\f\u000f\f\u0017\f\u001f\b\f'\f/\b", new Object[0], 6);
    static final SimpleSymbol Lit160 = ((SimpleSymbol) new SimpleSymbol("name").readResolve());
    static final SimpleSymbol Lit161 = ((SimpleSymbol) new SimpleSymbol("if").readResolve());
    static final SimpleSymbol Lit162 = ((SimpleSymbol) new SimpleSymbol("res").readResolve());
    static final SimpleSymbol Lit163 = ((SimpleSymbol) new SimpleSymbol("exp").readResolve());
    static final SimpleSymbol Lit164 = ((SimpleSymbol) new SimpleSymbol("p").readResolve());
    static final SimpleSymbol Lit165 = ((SimpleSymbol) new SimpleSymbol("dynamic-wind").readResolve());
    static final SyntaxTemplate Lit17 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b#\b\u0011\u0018<\u0011\u0018$\t+\t\u0013\b\u001b", new Object[]{Lit150, PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2809868), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2809868), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2809865), Lit160, Lit52, Lit149, Lit145, PairWithPosition.make(Lit15, PairWithPosition.make(Lit7, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2818087), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2818087), Lit89}, 0);
    static final SyntaxPattern Lit18 = new SyntaxPattern("<\f\u0007\f\u000f\f\u0017\b\f\u001f\f'\b", new Object[0], 5);
    static final SyntaxTemplate Lit19 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u001b\b\u0011\u0018$\u0011\u0018\u001c\t#\t\u000b\b\u0013", new Object[]{Lit150, PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2834444), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2834444), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2834441), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2834440), Lit52, Lit149, Lit89}, 0);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("skip").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("test-runner?").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("test-runner-pass-count").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("test-runner-pass-count!").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("test-runner-fail-count").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("test-runner-fail-count!").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("test-runner-xpass-count").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("test-runner-xpass-count!").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("test-runner-xfail-count").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("test-runner-xfail-count!").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("test-runner-skip-count").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("xfail").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("test-runner-skip-count!").readResolve());
    static final SimpleSymbol Lit31;
    static final SimpleSymbol Lit32;
    static final SimpleSymbol Lit33;
    static final SimpleSymbol Lit34;
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("test-runner-group-stack").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("test-runner-group-stack!").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-test-begin").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-test-begin!").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-test-end").readResolve());
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-test-end!").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-group-begin").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-group-begin!").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-group-end").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-group-end!").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-final").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-final!").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-bad-count").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-bad-count!").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-bad-end-name").readResolve());
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("test-runner-on-bad-end-name!").readResolve());
    static final SimpleSymbol Lit51;
    static final SimpleSymbol Lit52;
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("test-runner-aux-value").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("test-runner-aux-value!").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("test-runner-reset").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("test-runner-group-path").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("%test-null-callback").readResolve());
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("test-runner-null").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("test-runner-simple").readResolve());
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit60;
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("test-runner-create").readResolve());
    static final SimpleSymbol Lit62;
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("%test-begin").readResolve());
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("test-on-group-begin-simple").readResolve());
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("test-on-group-end-simple").readResolve());
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("test-on-bad-count-simple").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("test-on-bad-end-name-simple").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("test-on-final-simple").readResolve());
    static final SimpleSymbol Lit69;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit70;
    static final SyntaxRules Lit71;
    static final SimpleSymbol Lit72;
    static final SyntaxRules Lit73;
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("test-on-test-begin-simple").readResolve());
    static final SimpleSymbol Lit75;
    static final SyntaxRules Lit76;
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("test-on-test-end-simple").readResolve());
    static final SimpleSymbol Lit78;
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("test-result-clear").readResolve());
    static final PairWithPosition Lit8 = PairWithPosition.make(Lit14, PairWithPosition.make(Lit9, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1966107), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1966101);
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("test-result-remove").readResolve());
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("test-result-kind").readResolve());
    static final SimpleSymbol Lit82 = ((SimpleSymbol) new SimpleSymbol("test-passed?").readResolve());
    static final SimpleSymbol Lit83;
    static final SimpleSymbol Lit84;
    static final SyntaxRules Lit85;
    static final SimpleSymbol Lit86;
    static final SimpleSymbol Lit87;
    static final SimpleSymbol Lit88 = ((SimpleSymbol) new SimpleSymbol("test-runner-test-name").readResolve());
    static final SimpleSymbol Lit89;
    static final SimpleSymbol Lit9;
    static final SyntaxRules Lit90;
    static final SimpleSymbol Lit91;
    static final SimpleSymbol Lit92;
    static final SyntaxRules Lit93;
    static final SimpleSymbol Lit94 = ((SimpleSymbol) new SimpleSymbol("test-end").readResolve());
    static final SyntaxPattern Lit95 = new SyntaxPattern(",\f\u0007\f\u000f\b\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit96 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u000b\b\u0013", new Object[]{Lit69}, 0);
    static final SyntaxPattern Lit97 = new SyntaxPattern("\u001c\f\u0007\b\f\u000f\b", new Object[0], 2);
    static final SyntaxTemplate Lit98;
    static final SimpleSymbol Lit99 = ((SimpleSymbol) new SimpleSymbol("test-assert").readResolve());
    static final ModuleMethod lambda$Fn1;
    static final ModuleMethod lambda$Fn2;
    static final ModuleMethod lambda$Fn3;
    public static final ModuleMethod test$Mnapply;
    public static final Macro test$Mnapproximate;
    public static final Macro test$Mnassert;
    public static final Macro test$Mnend;
    public static final Macro test$Mneq;
    public static final Macro test$Mnequal;
    public static final Macro test$Mneqv;
    public static final Macro test$Mnerror;
    public static final Macro test$Mnexpect$Mnfail = Macro.make(Lit139, Lit140, $instance);
    public static final Macro test$Mngroup$Mnwith$Mncleanup = Macro.make(Lit72, Lit73, $instance);
    public static Boolean test$Mnlog$Mnto$Mnfile;
    public static final Macro test$Mnmatch$Mnall = Macro.make(Lit131, Lit132, $instance);
    public static final Macro test$Mnmatch$Mnany = Macro.make(Lit134, Lit135, $instance);
    public static final ModuleMethod test$Mnmatch$Mnname;
    public static final Macro test$Mnmatch$Mnnth = Macro.make(Lit128, Lit129, $instance);
    public static final ModuleMethod test$Mnon$Mnbad$Mncount$Mnsimple;
    public static final ModuleMethod test$Mnon$Mnbad$Mnend$Mnname$Mnsimple;
    public static final ModuleMethod test$Mnon$Mnfinal$Mnsimple;
    public static final ModuleMethod test$Mnon$Mngroup$Mnbegin$Mnsimple;
    public static final ModuleMethod test$Mnon$Mngroup$Mnend$Mnsimple;
    static final ModuleMethod test$Mnon$Mntest$Mnbegin$Mnsimple;
    public static final ModuleMethod test$Mnon$Mntest$Mnend$Mnsimple;
    public static final ModuleMethod test$Mnpassed$Qu;
    public static final ModuleMethod test$Mnread$Mneval$Mnstring;
    public static final ModuleMethod test$Mnresult$Mnalist;
    public static final ModuleMethod test$Mnresult$Mnalist$Ex;
    public static final ModuleMethod test$Mnresult$Mnclear;
    public static final ModuleMethod test$Mnresult$Mnkind;
    public static final Macro test$Mnresult$Mnref = Macro.make(Lit75, Lit76, $instance);
    public static final ModuleMethod test$Mnresult$Mnremove;
    public static final ModuleMethod test$Mnresult$Mnset$Ex;
    static final Class test$Mnrunner = test$Mnrunner.class;
    public static final ModuleMethod test$Mnrunner$Mnaux$Mnvalue;
    public static final ModuleMethod test$Mnrunner$Mnaux$Mnvalue$Ex;
    public static final ModuleMethod test$Mnrunner$Mncreate;
    public static Object test$Mnrunner$Mncurrent;
    public static Object test$Mnrunner$Mnfactory;
    public static final ModuleMethod test$Mnrunner$Mnfail$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnfail$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnget;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnpath;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnstack;
    public static final ModuleMethod test$Mnrunner$Mngroup$Mnstack$Ex;
    public static final ModuleMethod test$Mnrunner$Mnnull;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mnend$Mnname;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnbad$Mnend$Mnname$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnfinal;
    public static final ModuleMethod test$Mnrunner$Mnon$Mnfinal$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnbegin;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnbegin$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnend;
    public static final ModuleMethod test$Mnrunner$Mnon$Mngroup$Mnend$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnbegin;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnbegin$Ex;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnend;
    public static final ModuleMethod test$Mnrunner$Mnon$Mntest$Mnend$Ex;
    public static final ModuleMethod test$Mnrunner$Mnpass$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnpass$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnreset;
    public static final ModuleMethod test$Mnrunner$Mnsimple;
    public static final ModuleMethod test$Mnrunner$Mnskip$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnskip$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mntest$Mnname;
    public static final ModuleMethod test$Mnrunner$Mnxfail$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnxfail$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Mnxpass$Mncount;
    public static final ModuleMethod test$Mnrunner$Mnxpass$Mncount$Ex;
    public static final ModuleMethod test$Mnrunner$Qu;
    public static final Macro test$Mnskip = Macro.make(Lit137, Lit138, $instance);
    public static final Macro test$Mnwith$Mnrunner = Macro.make(Lit125, Lit126, $instance);

    /* compiled from: testing.scm */
    public class frame0 extends ModuleBody {
        Object error;
        final ModuleMethod lambda$Fn4;

        public frame0() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, 8194);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:640");
            this.lambda$Fn4 = moduleMethod;
        }

        public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
            if (moduleMethod.selector == 1) {
                return lambda5(obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply2(moduleMethod, obj, obj2);
            }
        }

        boolean lambda5(Object value, Object expected) {
            Object apply2 = Scheme.numGEq.apply2(value, AddOp.$Mn.apply2(expected, this.error));
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                if (x) {
                    return ((Boolean) Scheme.numLEq.apply2(value, AddOp.$Pl.apply2(expected, this.error))).booleanValue();
                }
                return x;
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, apply2);
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
    }

    /* compiled from: testing.scm */
    public class frame1 extends ModuleBody {
        Object first;
        final ModuleMethod lambda$Fn10;
        final ModuleMethod lambda$Fn5 = new ModuleMethod(this, 2, null, 0);
        final ModuleMethod lambda$Fn6 = new ModuleMethod(this, 3, null, 0);
        final ModuleMethod lambda$Fn7;
        final ModuleMethod lambda$Fn8;
        final ModuleMethod lambda$Fn9;
        Object f99r;
        LList rest;
        Object saved$Mnrunner;
        Object saved$Mnrunner$1;

        public frame1() {
            PropertySet moduleMethod = new ModuleMethod(this, 4, null, 0);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:897");
            this.lambda$Fn7 = moduleMethod;
            this.lambda$Fn8 = new ModuleMethod(this, 5, null, 0);
            this.lambda$Fn9 = new ModuleMethod(this, 6, null, 0);
            moduleMethod = new ModuleMethod(this, 7, null, 0);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:897");
            this.lambda$Fn10 = moduleMethod;
        }

        Object lambda6() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.first);
        }

        Object lambda7() {
            return Scheme.apply.apply2(testing.test$Mnapply, this.rest);
        }

        Object lambda10() {
            return Scheme.apply.apply3(testing.test$Mnapply, this.first, this.rest);
        }

        Object lambda9() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.f99r);
        }

        public Object apply0(ModuleMethod moduleMethod) {
            switch (moduleMethod.selector) {
                case 2:
                    return lambda6();
                case 3:
                    return lambda7();
                case 4:
                    return lambda8();
                case 5:
                    return lambda9();
                case 6:
                    return lambda10();
                case 7:
                    return lambda11();
                default:
                    return super.apply0(moduleMethod);
            }
        }

        Object lambda11() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.saved$Mnrunner);
        }

        Object lambda8() {
            return ((Procedure) testing.test$Mnrunner$Mncurrent).apply1(this.saved$Mnrunner$1);
        }

        public int match0(ModuleMethod moduleMethod, CallContext callContext) {
            switch (moduleMethod.selector) {
                case 2:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 3:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 4:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 5:
                    callContext.proc = moduleMethod;
                    callContext.pc = 0;
                    return 0;
                case 6:
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
    }

    /* compiled from: testing.scm */
    public class frame2 extends ModuleBody {
        Object count;
        Object f100i;
        final ModuleMethod lambda$Fn11;
        Object f101n;

        public frame2() {
            PropertySet moduleMethod = new ModuleMethod(this, 8, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:903");
            this.lambda$Fn11 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 8) {
                return lambda12(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda12(Object runner) {
            this.f100i = AddOp.$Pl.apply2(this.f100i, testing.Lit13);
            Object apply2 = Scheme.numGEq.apply2(this.f100i, this.f101n);
            try {
                boolean x = ((Boolean) apply2).booleanValue();
                return x ? ((Boolean) Scheme.numLss.apply2(this.f100i, AddOp.$Pl.apply2(this.f101n, this.count))).booleanValue() : x;
            } catch (ClassCastException e) {
                throw new WrongType(e, "x", -2, apply2);
            }
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
    }

    /* compiled from: testing.scm */
    public class frame3 extends ModuleBody {
        final ModuleMethod lambda$Fn12;
        LList pred$Mnlist;

        public frame3() {
            PropertySet moduleMethod = new ModuleMethod(this, 9, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:915");
            this.lambda$Fn12 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 9 ? lambda13(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda13(Object runner) {
            Boolean result = Boolean.TRUE;
            for (Object l = this.pred$Mnlist; !lists.isNull(l); l = lists.cdr.apply1(l)) {
                if (Scheme.applyToArgs.apply2(lists.car.apply1(l), runner) == Boolean.FALSE) {
                    result = Boolean.FALSE;
                }
            }
            return result;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 9) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: testing.scm */
    public class frame4 extends ModuleBody {
        final ModuleMethod lambda$Fn13;
        LList pred$Mnlist;

        public frame4() {
            PropertySet moduleMethod = new ModuleMethod(this, 10, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:931");
            this.lambda$Fn13 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 10 ? lambda14(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda14(Object runner) {
            Boolean result = Boolean.FALSE;
            for (Object l = this.pred$Mnlist; !lists.isNull(l); l = lists.cdr.apply1(l)) {
                if (Scheme.applyToArgs.apply2(lists.car.apply1(l), runner) != Boolean.FALSE) {
                    result = Boolean.TRUE;
                }
            }
            return result;
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 10) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: testing.scm */
    public class frame5 extends ModuleBody {
        final ModuleMethod lambda$Fn14;
        Object name;

        public frame5() {
            PropertySet moduleMethod = new ModuleMethod(this, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:971");
            this.lambda$Fn14 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            if (moduleMethod.selector == 11) {
                return lambda15(obj) ? Boolean.TRUE : Boolean.FALSE;
            } else {
                return super.apply1(moduleMethod, obj);
            }
        }

        boolean lambda15(Object runner) {
            return IsEqual.apply(this.name, testing.testRunnerTestName(runner));
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
    }

    /* compiled from: testing.scm */
    public class frame extends ModuleBody {
        Object f102p;

        public Object lambda4loop(Object r) {
            if (r == this.f102p) {
                return lists.cdr.apply1(r);
            }
            return lists.cons(lists.car.apply1(r), lambda4loop(lists.cdr.apply1(r)));
        }
    }

    public testing() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
        test$Mnlog$Mnto$Mnfile = Boolean.TRUE;
        test$Mnrunner$Mncurrent = parameters.makeParameter(Boolean.FALSE);
        test$Mnrunner$Mnfactory = parameters.makeParameter(test$Mnrunner$Mnsimple);
    }

    static {
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-expect-fail").readResolve();
        Lit139 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        r6 = new Object[8];
        SimpleSymbol simpleSymbol2 = Lit143;
        SimpleSymbol simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("test-runner-get").readResolve();
        Lit60 = simpleSymbol3;
        r6[1] = PairWithPosition.make(PairWithPosition.make(simpleSymbol2, PairWithPosition.make(PairWithPosition.make(simpleSymbol3, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3952660), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3952660), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3952652), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3952651);
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-runner-fail-list!").readResolve();
        Lit34 = simpleSymbol3;
        r6[2] = simpleSymbol3;
        r6[3] = Lit143;
        r6[4] = Lit145;
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("test-match-all").readResolve();
        Lit131 = simpleSymbol3;
        r6[5] = simpleSymbol3;
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-as-specifier").readResolve();
        Lit136 = simpleSymbol3;
        r6[6] = simpleSymbol3;
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-runner-fail-list").readResolve();
        Lit33 = simpleSymbol3;
        r6[7] = PairWithPosition.make(PairWithPosition.make(simpleSymbol3, PairWithPosition.make(Lit143, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3964958), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3964934), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3964934);
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1), "\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$Q\u0011\u0018,\b\u0005\u0011\u00184\b\u0003\u0018<", r6, 1);
        Lit140 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-skip").readResolve();
        Lit137 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        r6 = new Object[8];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-runner-skip-list!").readResolve();
        Lit32 = simpleSymbol3;
        r6[2] = simpleSymbol3;
        r6[3] = Lit143;
        r6[4] = Lit145;
        r6[5] = Lit131;
        r6[6] = Lit136;
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-runner-skip-list").readResolve();
        Lit31 = simpleSymbol3;
        r6[7] = PairWithPosition.make(PairWithPosition.make(simpleSymbol3, PairWithPosition.make(Lit143, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3932190), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3932166), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3932166);
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1), "\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$Q\u0011\u0018,\b\u0005\u0011\u00184\b\u0003\u0018<", r6, 1);
        Lit138 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-match-any").readResolve();
        Lit134 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        r6 = new Object[2];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-match-any").readResolve();
        Lit133 = simpleSymbol3;
        r6[0] = simpleSymbol3;
        r6[1] = Lit136;
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\b\u0003", r6, 1);
        Lit135 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        objArr = new Object[]{Lit131};
        syntaxRuleArr = new SyntaxRule[1];
        r6 = new Object[2];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-match-all").readResolve();
        Lit130 = simpleSymbol3;
        r6[0] = simpleSymbol3;
        r6[1] = Lit136;
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\b\u0003", r6, 1);
        Lit132 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-match-nth").readResolve();
        Lit128 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[2];
        r6 = new Object[2];
        r6[0] = Lit128;
        IntNum make = IntNum.make(1);
        Lit13 = make;
        r6[1] = PairWithPosition.make(make, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3727384);
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\t\u0003\u0018\f", r6, 0);
        r6 = new Object[1];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-match-nth").readResolve();
        Lit127 = simpleSymbol3;
        r6[0] = simpleSymbol3;
        syntaxRuleArr[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", r6, 0);
        Lit129 = new SyntaxRules(objArr, syntaxRuleArr, 2);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-with-runner").readResolve();
        Lit125 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\b\u0011\u0018\u0014Y\u0011\u0018\u001c\t\u0010\b\u0011\u0018$\b\u0003A\u0011\u0018\u001c\t\u0010\b\r\u000b\u0018,", new Object[]{Lit144, PairWithPosition.make(PairWithPosition.make(Lit148, PairWithPosition.make(PairWithPosition.make(Lit146, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3657754), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3657754), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3657740), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3657739), Lit165, Lit147, Lit146, PairWithPosition.make(PairWithPosition.make(Lit147, PairWithPosition.make(LList.Empty, PairWithPosition.make(PairWithPosition.make(Lit146, PairWithPosition.make(Lit148, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3674156), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3674135), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3674135), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3674132), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3674124), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3674124)}, 1);
        Lit126 = new SyntaxRules(objArr, syntaxRuleArr, 2);
        r6 = new Object[6];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-result-alist!").readResolve();
        Lit52 = simpleSymbol;
        r6[2] = simpleSymbol;
        r6[3] = Lit149;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("%test-error").readResolve();
        Lit115 = simpleSymbol;
        r6[4] = simpleSymbol;
        r6[5] = Boolean.TRUE;
        Lit123 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0013\b\u0011\u0018$\u0011\u0018\u001c\u0011\u0018,\b\u000b", r6, 0);
        Object[] objArr2 = new Object[8];
        objArr2[0] = Lit150;
        objArr2[1] = PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3469326), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3469326), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3469323);
        objArr2[2] = Lit160;
        objArr2[3] = Lit52;
        objArr2[4] = Lit149;
        objArr2[5] = Lit145;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve();
        Lit15 = simpleSymbol;
        SimpleSymbol simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("test-name").readResolve();
        Lit7 = simpleSymbol4;
        objArr2[6] = PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol4, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3477545), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3477545);
        objArr2[7] = Lit115;
        Lit119 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004I\u0011\u0018\f\b\u0011\u0018\u0014\b\u000b©\u0011\u0018\u001c\u0011\u0018$\b\u0011\u0018,A\u0011\u0018,\u0011\u00184\b\u000b\b#\b\u0011\u0018<\u0011\u0018$\t\u0013\b\u001b", objArr2, 0);
        Object[] objArr3 = new Object[]{Lit115};
        r14 = new SyntaxRule[2];
        Object[] objArr4 = new Object[]{Boolean.TRUE};
        r6 = new Object[14];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-on-test-begin").readResolve();
        Lit86 = simpleSymbol3;
        r6[1] = simpleSymbol3;
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("test-result-set!").readResolve();
        Lit78 = simpleSymbol3;
        r6[2] = simpleSymbol3;
        r6[3] = PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit152, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3223581), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3223581), PairWithPosition.make(Boolean.TRUE, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3223596), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3223580);
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-on-test-end").readResolve();
        Lit87 = simpleSymbol3;
        r6[4] = simpleSymbol3;
        r6[5] = Lit154;
        r6[6] = Lit144;
        r6[7] = PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3239966), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3239966);
        r6[8] = PairWithPosition.make(Boolean.FALSE, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3244041);
        r6[9] = Lit151;
        r6[10] = Lit156;
        r6[11] = PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3252256), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3252256), PairWithPosition.make(Lit151, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3252269), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3252255);
        r6[12] = PairWithPosition.make(Boolean.TRUE, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3256331);
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-report-result").readResolve();
        Lit83 = simpleSymbol3;
        r6[13] = PairWithPosition.make(PairWithPosition.make(simpleSymbol3, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3260424), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3260424);
        r14[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u0002\f\u000f\b", objArr4, 2), "\u0001\u0001", "\u0011\u0018\u0004\b)\u0011\u0018\f\b\u00039\u0011\u0018\u0014\t\u0003\u0018\u001cũ\u0011\u0018$\t\u0003\b\u0011\u0018,\u0011\u00184\t\u0010Q\u0011\u0018\u0014\t\u0003\u0011\u0018<\b\u000b\u0018D\b\u0011\u0018L\u0011\u0018T9\u0011\u0018\u0014\t\u0003\u0018\\\u0018d\u0018l", r6, 0);
        r6 = new Object[15];
        r6[0] = Lit161;
        r6[1] = Lit86;
        r6[2] = Lit144;
        r6[3] = Lit153;
        r6[4] = Lit78;
        r6[5] = PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit152, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3276828), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3276828), PairWithPosition.make(Lit153, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3276843), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3276827);
        r6[6] = Lit87;
        r6[7] = Lit154;
        r6[8] = PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3293213), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3293213);
        r6[9] = PairWithPosition.make(Boolean.FALSE, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3297288);
        r6[10] = Lit151;
        r6[11] = Lit156;
        r6[12] = PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3305503), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3305503), PairWithPosition.make(Lit151, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3305516), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3305502);
        SimpleSymbol simpleSymbol5 = Lit158;
        SimpleSymbol simpleSymbol6 = simpleSymbol5;
        r6[13] = PairWithPosition.make(PairWithPosition.make(simpleSymbol6, PairWithPosition.make(PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("and").readResolve(), PairWithPosition.make(PairWithPosition.make(Lit159, PairWithPosition.make(Lit153, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<gnu.bytecode.ClassType>").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309604), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309601), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309590), PairWithPosition.make(PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol("gnu.bytecode.ClassType").readResolve(), Pair.make(Pair.make((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve(), Pair.make((SimpleSymbol) new SimpleSymbol("isSubclass").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3313673), PairWithPosition.make(Lit153, PairWithPosition.make(Lit156, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3313710), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3313707), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3313672), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3313672), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309590), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309585), PairWithPosition.make(PairWithPosition.make(Lit159, PairWithPosition.make(Lit151, PairWithPosition.make(Lit153, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3317784), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3317781), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3317770), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3317770), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309584), PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("else").readResolve(), PairWithPosition.make(Boolean.TRUE, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3321871), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3321865), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3321865), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309584), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309578), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3309578);
        r6[14] = PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3325959), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 3325959);
        r14[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004)\u0011\u0018\f\b\u0003\b\u0011\u0018\u00141\b\u0011\u0018\u001c\b\u000b9\u0011\u0018$\t\u0003\u0018,ũ\u0011\u00184\t\u0003\b\u0011\u0018<\u0011\u0018\u0014\t\u0010Q\u0011\u0018$\t\u0003\u0011\u0018D\b\u0013\u0018L\b\u0011\u0018T\u0011\u0018\\9\u0011\u0018$\t\u0003\u0018d\u0018l\u0018t", r6, 0);
        Lit116 = new SyntaxRules(objArr3, r14, 3);
        r6 = new Object[6];
        r6[0] = Lit150;
        r6[1] = PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2916364), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2916364), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2916361), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2916360);
        r6[2] = Lit52;
        r6[3] = Lit149;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("%test-comp2body").readResolve();
        Lit89 = simpleSymbol;
        r6[4] = simpleSymbol;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("%test-approximimate=").readResolve();
        Lit91 = simpleSymbol;
        r6[5] = simpleSymbol;
        Lit114 = new SyntaxTemplate("\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b#\b\u0011\u0018$\u0011\u0018\u001c)\u0011\u0018,\b\u001b\t\u000b\b\u0013", r6, 0);
        r6 = new Object[5];
        r6[0] = Lit150;
        r6[1] = PairWithPosition.make(PairWithPosition.make(Lit149, PairWithPosition.make(PairWithPosition.make(Lit60, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2781198), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2781198), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2781195), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2781194);
        r6[2] = Lit52;
        r6[3] = Lit149;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("%test-comp1body").readResolve();
        Lit92 = simpleSymbol;
        r6[4] = simpleSymbol;
        Lit103 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\fA\u0011\u0018\u0014\u0011\u0018\u001c\b\u0013\b\u0011\u0018$\u0011\u0018\u001c\b\u000b", r6, 0);
        r6 = new Object[2];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("%test-end").readResolve();
        Lit69 = simpleSymbol;
        r6[0] = simpleSymbol;
        r6[1] = Boolean.FALSE;
        Lit98 = new SyntaxTemplate("\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\b\u000b", r6, 0);
        objArr = new Object[]{Lit92};
        syntaxRuleArr = new SyntaxRule[1];
        r6 = new Object[10];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-evaluate-with-catch").readResolve();
        Lit84 = simpleSymbol3;
        r6[4] = simpleSymbol3;
        r6[5] = Lit78;
        r6[6] = PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2666526), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2666526), PairWithPosition.make(Lit162, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2666539), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2666525);
        r6[7] = Lit87;
        r6[8] = PairWithPosition.make(Lit162, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2670622);
        r6[9] = PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2674696), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2674696);
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0010ű\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u0004\t\u0010\b\u0011\u0018\u0004Q\b\u0011\u0018\u001c\b\u0011\u0018$\b\u000b9\u0011\u0018,\t\u0003\u00184\b\u0011\u0018<\t\u0003\u0018D\u0018L", r6, 0);
        Lit93 = new SyntaxRules(objArr, syntaxRuleArr, 2);
        objArr = new Object[]{Lit89};
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f\b", new Object[0], 4), "\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0010Ǳ\u0011\u0018\f)\u0011\u0018\u0014\b\u0003\b\u0011\u0018\u00041\b\u0011\u0018\u001c\b\u00139\u0011\u0018$\t\u0003\u0018,\b\u0011\u0018\u0004Q\b\u0011\u00184\b\u0011\u0018<\b\u001b9\u0011\u0018$\t\u0003\u0018D\b\u0011\u0018L\t\u0003\b\t\u000b\u0018T\u0018\\", new Object[]{Lit144, Lit161, Lit86, Lit163, Lit78, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("expected-value").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2592794), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2592794), PairWithPosition.make(Lit163, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2592809), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2592793), Lit162, Lit84, PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit155, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2600988), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2600988), PairWithPosition.make(Lit162, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2601001), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2600987), Lit87, PairWithPosition.make(Lit163, PairWithPosition.make(Lit162, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2605094), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2605090), PairWithPosition.make(PairWithPosition.make(Lit83, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2609158), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2609158)}, 0);
        Lit90 = new SyntaxRules(objArr, syntaxRuleArr, 4);
        objArr = new Object[]{Lit84};
        syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\t\u0003\u0018\f", new Object[]{Lit154, PairWithPosition.make(PairWithPosition.make(Lit151, PairWithPosition.make(Lit156, PairWithPosition.make(PairWithPosition.make(Lit78, PairWithPosition.make(PairWithPosition.make(Lit146, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2347035), PairWithPosition.make(PairWithPosition.make(Lit15, PairWithPosition.make(Lit157, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2347058), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2347058), PairWithPosition.make(Lit151, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2347071), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2347057), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2347035), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2347017), PairWithPosition.make(Boolean.FALSE, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2351113), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2347017), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2342921), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2342917), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2342917)}, 0);
        Lit85 = new SyntaxRules(objArr, syntaxRuleArr, 1);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-result-ref").readResolve();
        Lit75 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[2];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\t\u000b\u0018\f", new Object[]{Lit75, PairWithPosition.make(Boolean.FALSE, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1933348)}, 0);
        r6 = new Object[6];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("test-result-alist").readResolve();
        Lit51 = simpleSymbol3;
        r6[3] = simpleSymbol3;
        r6[4] = Lit161;
        r6[5] = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("cdr").readResolve(), PairWithPosition.make(Lit164, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1945619), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1945614);
        syntaxRuleArr[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\b\u0011\u0018\f\b\u0011\u0018\u0014\t\u000b\b\u0011\u0018\u001c\b\u0003\b\u0011\u0018$\u0011\u0018\f\u0011\u0018,\b\u0013", r6, 0);
        Lit76 = new SyntaxRules(objArr, syntaxRuleArr, 3);
        objArr = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("test-group-with-cleanup").readResolve();
        Lit72 = simpleSymbol;
        objArr[0] = simpleSymbol;
        syntaxRuleArr = new SyntaxRule[3];
        r6 = new Object[4];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("test-group").readResolve();
        Lit70 = simpleSymbol3;
        r6[0] = simpleSymbol3;
        r6[1] = Lit165;
        r6[2] = PairWithPosition.make(Lit147, PairWithPosition.make(LList.Empty, PairWithPosition.make(Boolean.FALSE, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1826831), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1826828), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1826820);
        r6[3] = Lit147;
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\u0011\u0018\u00149\u0011\u0018\u001c\t\u0010\b\u000b\b\u0011\u0018\u001c\t\u0010\b\u0013", r6, 0);
        syntaxRuleArr[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\u0011\u0018\f\b\u000b", new Object[]{Lit72, Boolean.FALSE}, 0);
        syntaxRuleArr[2] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\f\u001f#", new Object[0], 5), "\u0001\u0001\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u00039\u0011\u0018\f\t\u000b\b\u0013\t\u001b\"", new Object[]{Lit72, (SimpleSymbol) new SimpleSymbol("begin").readResolve()}, 0);
        Lit73 = new SyntaxRules(objArr, syntaxRuleArr, 5);
        objArr = new Object[]{Lit70};
        syntaxRuleArr = new SyntaxRule[1];
        r6 = new Object[13];
        simpleSymbol3 = (SimpleSymbol) new SimpleSymbol("%test-should-execute").readResolve();
        Lit62 = simpleSymbol3;
        r6[8] = PairWithPosition.make(simpleSymbol3, PairWithPosition.make(Lit149, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1781794), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 1781772);
        r6[9] = Lit165;
        r6[10] = Lit147;
        r6[11] = (SimpleSymbol) new SimpleSymbol("test-begin").readResolve();
        r6[12] = Lit94;
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\u000b", new Object[0], 2), "\u0001\u0000", "\u0011\u0018\u0004\u0011\u0018\f\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\b\u0011\u0018,\u0011\u00184\b\u0003\b\u0011\u0018<\u0011\u0018D\b\u0011\u0018LY\u0011\u0018T\t\u0010\b\u0011\u0018\\\b\u00031\u0011\u0018T\t\u0010\n\b\u0011\u0018T\t\u0010\b\u0011\u0018d\b\u0003", r6, 0);
        Lit71 = new SyntaxRules(objArr, syntaxRuleArr, 2);
        simpleSymbol4 = Lit12;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("xpass").readResolve();
        Lit9 = simpleSymbol;
        Lit11 = PairWithPosition.make(simpleSymbol4, PairWithPosition.make(simpleSymbol, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2220088), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2220082);
        SimpleSymbol simpleSymbol7 = Lit7;
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("source-file").readResolve();
        Lit4 = simpleSymbol;
        simpleSymbol4 = (SimpleSymbol) new SimpleSymbol("source-line").readResolve();
        Lit5 = simpleSymbol4;
        SimpleSymbol simpleSymbol8 = (SimpleSymbol) new SimpleSymbol("source-form").readResolve();
        Lit6 = simpleSymbol8;
        Lit10 = PairWithPosition.make(simpleSymbol7, PairWithPosition.make(simpleSymbol, PairWithPosition.make(simpleSymbol4, PairWithPosition.make(simpleSymbol8, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2072618), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2072606), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2072594), "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm", 2072583);
        ModuleBody moduleBody = $instance;
        test$Mnrunner$Qu = new ModuleMethod(moduleBody, 12, Lit20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnpass$Mncount = new ModuleMethod(moduleBody, 13, Lit21, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnpass$Mncount$Ex = new ModuleMethod(moduleBody, 14, Lit22, 8194);
        test$Mnrunner$Mnfail$Mncount = new ModuleMethod(moduleBody, 15, Lit23, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnfail$Mncount$Ex = new ModuleMethod(moduleBody, 16, Lit24, 8194);
        test$Mnrunner$Mnxpass$Mncount = new ModuleMethod(moduleBody, 17, Lit25, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnxpass$Mncount$Ex = new ModuleMethod(moduleBody, 18, Lit26, 8194);
        test$Mnrunner$Mnxfail$Mncount = new ModuleMethod(moduleBody, 19, Lit27, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnxfail$Mncount$Ex = new ModuleMethod(moduleBody, 20, Lit28, 8194);
        test$Mnrunner$Mnskip$Mncount = new ModuleMethod(moduleBody, 21, Lit29, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnskip$Mncount$Ex = new ModuleMethod(moduleBody, 22, Lit30, 8194);
        $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist = new ModuleMethod(moduleBody, 23, Lit31, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnrunner$Mnskip$Mnlist$Ex = new ModuleMethod(moduleBody, 24, Lit32, 8194);
        $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist = new ModuleMethod(moduleBody, 25, Lit33, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnrunner$Mnfail$Mnlist$Ex = new ModuleMethod(moduleBody, 26, Lit34, 8194);
        test$Mnrunner$Mngroup$Mnstack = new ModuleMethod(moduleBody, 27, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mngroup$Mnstack$Ex = new ModuleMethod(moduleBody, 28, Lit36, 8194);
        test$Mnrunner$Mnon$Mntest$Mnbegin = new ModuleMethod(moduleBody, 29, Lit37, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mntest$Mnbegin$Ex = new ModuleMethod(moduleBody, 30, Lit38, 8194);
        test$Mnrunner$Mnon$Mntest$Mnend = new ModuleMethod(moduleBody, 31, Lit39, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mntest$Mnend$Ex = new ModuleMethod(moduleBody, 32, Lit40, 8194);
        test$Mnrunner$Mnon$Mngroup$Mnbegin = new ModuleMethod(moduleBody, 33, Lit41, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mngroup$Mnbegin$Ex = new ModuleMethod(moduleBody, 34, Lit42, 8194);
        test$Mnrunner$Mnon$Mngroup$Mnend = new ModuleMethod(moduleBody, 35, Lit43, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mngroup$Mnend$Ex = new ModuleMethod(moduleBody, 36, Lit44, 8194);
        test$Mnrunner$Mnon$Mnfinal = new ModuleMethod(moduleBody, 37, Lit45, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnfinal$Ex = new ModuleMethod(moduleBody, 38, Lit46, 8194);
        test$Mnrunner$Mnon$Mnbad$Mncount = new ModuleMethod(moduleBody, 39, Lit47, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnbad$Mncount$Ex = new ModuleMethod(moduleBody, 40, Lit48, 8194);
        test$Mnrunner$Mnon$Mnbad$Mnend$Mnname = new ModuleMethod(moduleBody, 41, Lit49, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnon$Mnbad$Mnend$Mnname$Ex = new ModuleMethod(moduleBody, 42, Lit50, 8194);
        test$Mnresult$Mnalist = new ModuleMethod(moduleBody, 43, Lit51, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnalist$Ex = new ModuleMethod(moduleBody, 44, Lit52, 8194);
        test$Mnrunner$Mnaux$Mnvalue = new ModuleMethod(moduleBody, 45, Lit53, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mnaux$Mnvalue$Ex = new ModuleMethod(moduleBody, 46, Lit54, 8194);
        test$Mnrunner$Mnreset = new ModuleMethod(moduleBody, 47, Lit55, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnrunner$Mngroup$Mnpath = new ModuleMethod(moduleBody, 48, Lit56, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Pctest$Mnnull$Mncallback = new ModuleMethod(moduleBody, 49, Lit57, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 50, null, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:182");
        lambda$Fn1 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 51, null, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:187");
        lambda$Fn2 = moduleMethod;
        moduleMethod = new ModuleMethod(moduleBody, 52, null, 12291);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:188");
        lambda$Fn3 = moduleMethod;
        test$Mnrunner$Mnnull = new ModuleMethod(moduleBody, 53, Lit58, 0);
        test$Mnrunner$Mnsimple = new ModuleMethod(moduleBody, 54, Lit59, 0);
        test$Mnrunner$Mnget = new ModuleMethod(moduleBody, 55, Lit60, 0);
        test$Mnrunner$Mncreate = new ModuleMethod(moduleBody, 56, Lit61, 0);
        $Prvt$$Pctest$Mnshould$Mnexecute = new ModuleMethod(moduleBody, 57, Lit62, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Pctest$Mnbegin = new ModuleMethod(moduleBody, 58, Lit63, 8194);
        test$Mnon$Mngroup$Mnbegin$Mnsimple = new ModuleMethod(moduleBody, 59, Lit64, 12291);
        test$Mnon$Mngroup$Mnend$Mnsimple = new ModuleMethod(moduleBody, 60, Lit65, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnon$Mnbad$Mncount$Mnsimple = new ModuleMethod(moduleBody, 61, Lit66, 12291);
        test$Mnon$Mnbad$Mnend$Mnname$Mnsimple = new ModuleMethod(moduleBody, 62, Lit67, 12291);
        test$Mnon$Mnfinal$Mnsimple = new ModuleMethod(moduleBody, 63, Lit68, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnend = new ModuleMethod(moduleBody, 64, Lit69, 8194);
        test$Mnon$Mntest$Mnbegin$Mnsimple = new ModuleMethod(moduleBody, 65, Lit74, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnon$Mntest$Mnend$Mnsimple = new ModuleMethod(moduleBody, 66, Lit77, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnset$Ex = new ModuleMethod(moduleBody, 67, Lit78, 12291);
        test$Mnresult$Mnclear = new ModuleMethod(moduleBody, 68, Lit79, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnresult$Mnremove = new ModuleMethod(moduleBody, 69, Lit80, 8194);
        test$Mnresult$Mnkind = new ModuleMethod(moduleBody, 70, Lit81, -4096);
        test$Mnpassed$Qu = new ModuleMethod(moduleBody, 71, Lit82, -4096);
        $Prvt$$Pctest$Mnreport$Mnresult = new ModuleMethod(moduleBody, 72, Lit83, 0);
        $Prvt$$Pctest$Mnon$Mntest$Mnbegin = new ModuleMethod(moduleBody, 73, Lit86, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnon$Mntest$Mnend = new ModuleMethod(moduleBody, 74, Lit87, 8194);
        test$Mnrunner$Mntest$Mnname = new ModuleMethod(moduleBody, 75, Lit88, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Prvt$$Pctest$Mnapproximimate$Eq = new ModuleMethod(moduleBody, 76, Lit91, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        simpleSymbol = Lit94;
        PropertySet moduleMethod2 = new ModuleMethod(moduleBody, 77, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod2.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:660");
        test$Mnend = Macro.make(simpleSymbol, moduleMethod2, $instance);
        simpleSymbol = Lit99;
        moduleMethod2 = new ModuleMethod(moduleBody, 78, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod2.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:669");
        test$Mnassert = Macro.make(simpleSymbol, moduleMethod2, $instance);
        simpleSymbol = Lit104;
        moduleMethod2 = new ModuleMethod(moduleBody, 79, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod2.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:696");
        test$Mneqv = Macro.make(simpleSymbol, moduleMethod2, $instance);
        simpleSymbol = Lit106;
        moduleMethod2 = new ModuleMethod(moduleBody, 80, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod2.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:698");
        test$Mneq = Macro.make(simpleSymbol, moduleMethod2, $instance);
        simpleSymbol = Lit108;
        moduleMethod2 = new ModuleMethod(moduleBody, 81, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod2.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:700");
        test$Mnequal = Macro.make(simpleSymbol, moduleMethod2, $instance);
        simpleSymbol = Lit110;
        moduleMethod2 = new ModuleMethod(moduleBody, 82, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod2.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:702");
        test$Mnapproximate = Macro.make(simpleSymbol, moduleMethod2, $instance);
        simpleSymbol = Lit117;
        moduleMethod2 = new ModuleMethod(moduleBody, 83, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod2.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/testing.scm:843");
        test$Mnerror = Macro.make(simpleSymbol, moduleMethod2, $instance);
        test$Mnapply = new ModuleMethod(moduleBody, 84, Lit124, -4095);
        $Prvt$$Pctest$Mnmatch$Mnnth = new ModuleMethod(moduleBody, 85, Lit127, 8194);
        $Prvt$$Pctest$Mnmatch$Mnall = new ModuleMethod(moduleBody, 86, Lit130, -4096);
        $Prvt$$Pctest$Mnmatch$Mnany = new ModuleMethod(moduleBody, 87, Lit133, -4096);
        $Prvt$$Pctest$Mnas$Mnspecifier = new ModuleMethod(moduleBody, 88, Lit136, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnmatch$Mnname = new ModuleMethod(moduleBody, 89, Lit141, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        test$Mnread$Mneval$Mnstring = new ModuleMethod(moduleBody, 90, Lit142, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    static test$Mnrunner $PcTestRunnerAlloc() {
        return new test$Mnrunner();
    }

    public static boolean isTestRunner(Object obj) {
        return obj instanceof test$Mnrunner;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 12:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 13:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 15:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 17:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 19:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 21:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 23:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 25:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 27:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 31:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 33:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 37:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 39:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 41:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 43:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 45:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 48:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 49:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 57:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 60:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 63:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 65:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 66:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 68:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 73:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 75:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 76:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 77:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 78:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 79:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 80:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 81:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 82:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 83:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 88:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 89:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 90:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static Object testRunnerPassCount(test$Mnrunner obj) {
        return obj.pass$Mncount;
    }

    public static void testRunnerPassCount$Ex(test$Mnrunner obj, Object value) {
        obj.pass$Mncount = value;
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 14:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 16:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 18:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 20:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 22:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 24:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 26:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 28:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 30:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 32:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 34:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 36:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 38:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 40:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 42:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 44:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 46:
                if (!(obj instanceof test$Mnrunner)) {
                    return -786431;
                }
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
            case 64:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 69:
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
            case 85:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object testRunnerFailCount(test$Mnrunner obj) {
        return obj.fail$Mncount;
    }

    public static void testRunnerFailCount$Ex(test$Mnrunner obj, Object value) {
        obj.fail$Mncount = value;
    }

    public static Object testRunnerXpassCount(test$Mnrunner obj) {
        return obj.xpass$Mncount;
    }

    public static void testRunnerXpassCount$Ex(test$Mnrunner obj, Object value) {
        obj.xpass$Mncount = value;
    }

    public static Object testRunnerXfailCount(test$Mnrunner obj) {
        return obj.xfail$Mncount;
    }

    public static void testRunnerXfailCount$Ex(test$Mnrunner obj, Object value) {
        obj.xfail$Mncount = value;
    }

    public static Object testRunnerSkipCount(test$Mnrunner obj) {
        return obj.skip$Mncount;
    }

    public static void testRunnerSkipCount$Ex(test$Mnrunner obj, Object value) {
        obj.skip$Mncount = value;
    }

    public static Object $PcTestRunnerSkipList(test$Mnrunner obj) {
        return obj.skip$Mnlist;
    }

    public static void $PcTestRunnerSkipList$Ex(test$Mnrunner obj, Object value) {
        obj.skip$Mnlist = value;
    }

    public static Object $PcTestRunnerFailList(test$Mnrunner obj) {
        return obj.fail$Mnlist;
    }

    public static void $PcTestRunnerFailList$Ex(test$Mnrunner obj, Object value) {
        obj.fail$Mnlist = value;
    }

    static Object $PcTestRunnerRunList(test$Mnrunner obj) {
        return obj.run$Mnlist;
    }

    static void $PcTestRunnerRunList$Ex(test$Mnrunner obj, Object value) {
        obj.run$Mnlist = value;
    }

    static Object $PcTestRunnerSkipSave(test$Mnrunner obj) {
        return obj.skip$Mnsave;
    }

    static void $PcTestRunnerSkipSave$Ex(test$Mnrunner obj, Object value) {
        obj.skip$Mnsave = value;
    }

    static Object $PcTestRunnerFailSave(test$Mnrunner obj) {
        return obj.fail$Mnsave;
    }

    static void $PcTestRunnerFailSave$Ex(test$Mnrunner obj, Object value) {
        obj.fail$Mnsave = value;
    }

    public static Object testRunnerGroupStack(test$Mnrunner obj) {
        return obj.group$Mnstack;
    }

    public static void testRunnerGroupStack$Ex(test$Mnrunner obj, Object value) {
        obj.group$Mnstack = value;
    }

    public static Object testRunnerOnTestBegin(test$Mnrunner obj) {
        return obj.on$Mntest$Mnbegin;
    }

    public static void testRunnerOnTestBegin$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mntest$Mnbegin = value;
    }

    public static Object testRunnerOnTestEnd(test$Mnrunner obj) {
        return obj.on$Mntest$Mnend;
    }

    public static void testRunnerOnTestEnd$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mntest$Mnend = value;
    }

    public static Object testRunnerOnGroupBegin(test$Mnrunner obj) {
        return obj.on$Mngroup$Mnbegin;
    }

    public static void testRunnerOnGroupBegin$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mngroup$Mnbegin = value;
    }

    public static Object testRunnerOnGroupEnd(test$Mnrunner obj) {
        return obj.on$Mngroup$Mnend;
    }

    public static void testRunnerOnGroupEnd$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mngroup$Mnend = value;
    }

    public static Object testRunnerOnFinal(test$Mnrunner obj) {
        return obj.on$Mnfinal;
    }

    public static void testRunnerOnFinal$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mnfinal = value;
    }

    public static Object testRunnerOnBadCount(test$Mnrunner obj) {
        return obj.on$Mnbad$Mncount;
    }

    public static void testRunnerOnBadCount$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mnbad$Mncount = value;
    }

    public static Object testRunnerOnBadEndName(test$Mnrunner obj) {
        return obj.on$Mnbad$Mnend$Mnname;
    }

    public static void testRunnerOnBadEndName$Ex(test$Mnrunner obj, Object value) {
        obj.on$Mnbad$Mnend$Mnname = value;
    }

    static Object $PcTestRunnerTotalCount(test$Mnrunner obj) {
        return obj.total$Mncount;
    }

    static void $PcTestRunnerTotalCount$Ex(test$Mnrunner obj, Object value) {
        obj.total$Mncount = value;
    }

    static Object $PcTestRunnerCountList(test$Mnrunner obj) {
        return obj.count$Mnlist;
    }

    static void $PcTestRunnerCountList$Ex(test$Mnrunner obj, Object value) {
        obj.count$Mnlist = value;
    }

    public static Object testResultAlist(test$Mnrunner obj) {
        return obj.result$Mnalist;
    }

    public static void testResultAlist$Ex(test$Mnrunner obj, Object value) {
        obj.result$Mnalist = value;
    }

    public static Object testRunnerAuxValue(test$Mnrunner obj) {
        return obj.aux$Mnvalue;
    }

    public static void testRunnerAuxValue$Ex(test$Mnrunner obj, Object value) {
        obj.aux$Mnvalue = value;
    }

    public static void testRunnerReset(Object runner) {
        try {
            testResultAlist$Ex((test$Mnrunner) runner, LList.Empty);
            try {
                testRunnerPassCount$Ex((test$Mnrunner) runner, Lit0);
                try {
                    testRunnerFailCount$Ex((test$Mnrunner) runner, Lit0);
                    try {
                        testRunnerXpassCount$Ex((test$Mnrunner) runner, Lit0);
                        try {
                            testRunnerXfailCount$Ex((test$Mnrunner) runner, Lit0);
                            try {
                                testRunnerSkipCount$Ex((test$Mnrunner) runner, Lit0);
                                try {
                                    $PcTestRunnerTotalCount$Ex((test$Mnrunner) runner, Lit0);
                                    try {
                                        $PcTestRunnerCountList$Ex((test$Mnrunner) runner, LList.Empty);
                                        try {
                                            $PcTestRunnerRunList$Ex((test$Mnrunner) runner, Boolean.TRUE);
                                            try {
                                                $PcTestRunnerSkipList$Ex((test$Mnrunner) runner, LList.Empty);
                                                try {
                                                    $PcTestRunnerFailList$Ex((test$Mnrunner) runner, LList.Empty);
                                                    try {
                                                        $PcTestRunnerSkipSave$Ex((test$Mnrunner) runner, LList.Empty);
                                                        try {
                                                            $PcTestRunnerFailSave$Ex((test$Mnrunner) runner, LList.Empty);
                                                            try {
                                                                testRunnerGroupStack$Ex((test$Mnrunner) runner, LList.Empty);
                                                            } catch (ClassCastException e) {
                                                                throw new WrongType(e, "test-runner-group-stack!", 0, runner);
                                                            }
                                                        } catch (ClassCastException e2) {
                                                            throw new WrongType(e2, "%test-runner-fail-save!", 0, runner);
                                                        }
                                                    } catch (ClassCastException e22) {
                                                        throw new WrongType(e22, "%test-runner-skip-save!", 0, runner);
                                                    }
                                                } catch (ClassCastException e222) {
                                                    throw new WrongType(e222, "%test-runner-fail-list!", 0, runner);
                                                }
                                            } catch (ClassCastException e2222) {
                                                throw new WrongType(e2222, "%test-runner-skip-list!", 0, runner);
                                            }
                                        } catch (ClassCastException e22222) {
                                            throw new WrongType(e22222, "%test-runner-run-list!", 0, runner);
                                        }
                                    } catch (ClassCastException e222222) {
                                        throw new WrongType(e222222, "%test-runner-count-list!", 0, runner);
                                    }
                                } catch (ClassCastException e2222222) {
                                    throw new WrongType(e2222222, "%test-runner-total-count!", 0, runner);
                                }
                            } catch (ClassCastException e22222222) {
                                throw new WrongType(e22222222, "test-runner-skip-count!", 0, runner);
                            }
                        } catch (ClassCastException e222222222) {
                            throw new WrongType(e222222222, "test-runner-xfail-count!", 0, runner);
                        }
                    } catch (ClassCastException e2222222222) {
                        throw new WrongType(e2222222222, "test-runner-xpass-count!", 0, runner);
                    }
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "test-runner-fail-count!", 0, runner);
                }
            } catch (ClassCastException e222222222222) {
                throw new WrongType(e222222222222, "test-runner-pass-count!", 0, runner);
            }
        } catch (ClassCastException e2222222222222) {
            throw new WrongType(e2222222222222, "test-result-alist!", 0, runner);
        }
    }

    public static LList testRunnerGroupPath(Object runner) {
        try {
            Object testRunnerGroupStack = testRunnerGroupStack((test$Mnrunner) runner);
            try {
                return lists.reverse((LList) testRunnerGroupStack);
            } catch (ClassCastException e) {
                throw new WrongType(e, "reverse", 1, testRunnerGroupStack);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-group-stack", 0, runner);
        }
    }

    static Boolean $PcTestNullCallback(Object runner) {
        return Boolean.FALSE;
    }

    public static test$Mnrunner testRunnerNull() {
        test$Mnrunner runner = $PcTestRunnerAlloc();
        testRunnerReset(runner);
        testRunnerOnGroupBegin$Ex(runner, lambda$Fn1);
        testRunnerOnGroupEnd$Ex(runner, $Pctest$Mnnull$Mncallback);
        testRunnerOnFinal$Ex(runner, $Pctest$Mnnull$Mncallback);
        testRunnerOnTestBegin$Ex(runner, $Pctest$Mnnull$Mncallback);
        testRunnerOnTestEnd$Ex(runner, $Pctest$Mnnull$Mncallback);
        testRunnerOnBadCount$Ex(runner, lambda$Fn2);
        testRunnerOnBadEndName$Ex(runner, lambda$Fn3);
        return runner;
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 53:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 54:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 55:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 56:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case 72:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    static Boolean lambda1(Object runner, Object name, Object count) {
        return Boolean.FALSE;
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 50:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 51:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 52:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 59:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 61:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 62:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 67:
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

    static Boolean lambda2(Object runner, Object count, Object expected) {
        return Boolean.FALSE;
    }

    static Boolean lambda3(Object runner, Object begin, Object end) {
        return Boolean.FALSE;
    }

    public static test$Mnrunner testRunnerSimple() {
        test$Mnrunner runner = $PcTestRunnerAlloc();
        testRunnerReset(runner);
        testRunnerOnGroupBegin$Ex(runner, test$Mnon$Mngroup$Mnbegin$Mnsimple);
        testRunnerOnGroupEnd$Ex(runner, test$Mnon$Mngroup$Mnend$Mnsimple);
        testRunnerOnFinal$Ex(runner, test$Mnon$Mnfinal$Mnsimple);
        testRunnerOnTestBegin$Ex(runner, test$Mnon$Mntest$Mnbegin$Mnsimple);
        testRunnerOnTestEnd$Ex(runner, test$Mnon$Mntest$Mnend$Mnsimple);
        testRunnerOnBadCount$Ex(runner, test$Mnon$Mnbad$Mncount$Mnsimple);
        testRunnerOnBadEndName$Ex(runner, test$Mnon$Mnbad$Mnend$Mnname$Mnsimple);
        return runner;
    }

    public static Object testRunnerGet() {
        Boolean r = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        if (r == Boolean.FALSE) {
            misc.error$V("test-runner not initialized - test-begin missing?", new Object[0]);
        }
        return r;
    }

    static Object $PcTestSpecificierMatches(Object spec, Object runner) {
        return Scheme.applyToArgs.apply2(spec, runner);
    }

    public static Object testRunnerCreate() {
        return Scheme.applyToArgs.apply1(((Procedure) test$Mnrunner$Mnfactory).apply0());
    }

    static Object $PcTestAnySpecifierMatches(Object list, Object runner) {
        Boolean result = Boolean.FALSE;
        for (Object l = list; !lists.isNull(l); l = lists.cdr.apply1(l)) {
            if ($PcTestSpecificierMatches(lists.car.apply1(l), runner) != Boolean.FALSE) {
                result = Boolean.TRUE;
            }
        }
        return result;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object $PcTestShouldExecute(java.lang.Object r8) {
        /*
        r4 = 1;
        r5 = 0;
        r0 = r8;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x005e }
        r3 = r0;
        r1 = $PcTestRunnerRunList(r3);
        r3 = java.lang.Boolean.TRUE;
        if (r1 != r3) goto L_0x0023;
    L_0x000e:
        r2 = r4;
    L_0x000f:
        if (r2 == 0) goto L_0x0025;
    L_0x0011:
        r3 = r2 + 1;
        r2 = r3 & 1;
        if (r2 == 0) goto L_0x0031;
    L_0x0017:
        if (r2 == 0) goto L_0x0041;
    L_0x0019:
        r3 = Lit1;
        r4 = Lit2;
        testResultSet$Ex(r8, r3, r4);
        r3 = java.lang.Boolean.FALSE;
    L_0x0022:
        return r3;
    L_0x0023:
        r2 = r5;
        goto L_0x000f;
    L_0x0025:
        r3 = $PcTestAnySpecifierMatches(r1, r8);
        r6 = java.lang.Boolean.FALSE;	 Catch:{ ClassCastException -> 0x0067 }
        if (r3 == r6) goto L_0x002f;
    L_0x002d:
        r2 = r4;
        goto L_0x0011;
    L_0x002f:
        r2 = r5;
        goto L_0x0011;
    L_0x0031:
        r0 = r8;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x0071 }
        r3 = r0;
        r3 = $PcTestRunnerSkipList(r3);
        r3 = $PcTestAnySpecifierMatches(r3, r8);
        r4 = java.lang.Boolean.FALSE;
        if (r3 != r4) goto L_0x0019;
    L_0x0041:
        r0 = r8;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x007a }
        r3 = r0;
        r3 = $PcTestRunnerFailList(r3);
        r3 = $PcTestAnySpecifierMatches(r3, r8);
        r4 = java.lang.Boolean.FALSE;
        if (r3 == r4) goto L_0x005b;
    L_0x0051:
        r3 = Lit1;
        r4 = Lit3;
        testResultSet$Ex(r8, r3, r4);
        r3 = Lit3;
        goto L_0x0022;
    L_0x005b:
        r3 = java.lang.Boolean.TRUE;
        goto L_0x0022;
    L_0x005e:
        r3 = move-exception;
        r4 = new gnu.mapping.WrongType;
        r6 = "%test-runner-run-list";
        r4.<init>(r3, r6, r5, r8);
        throw r4;
    L_0x0067:
        r4 = move-exception;
        r5 = new gnu.mapping.WrongType;
        r6 = "x";
        r7 = -2;
        r5.<init>(r4, r6, r7, r3);
        throw r5;
    L_0x0071:
        r3 = move-exception;
        r4 = new gnu.mapping.WrongType;
        r6 = "%test-runner-skip-list";
        r4.<init>(r3, r6, r5, r8);
        throw r4;
    L_0x007a:
        r3 = move-exception;
        r4 = new gnu.mapping.WrongType;
        r6 = "%test-runner-fail-list";
        r4.<init>(r3, r6, r5, r8);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.testing.$PcTestShouldExecute(java.lang.Object):java.lang.Object");
    }

    public static void $PcTestBegin(Object suite$Mnname, Object count) {
        if (((Procedure) test$Mnrunner$Mncurrent).apply0() == Boolean.FALSE) {
            ((Procedure) test$Mnrunner$Mncurrent).apply1(testRunnerCreate());
        }
        Object runner = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        try {
            Scheme.applyToArgs.apply4(testRunnerOnGroupBegin((test$Mnrunner) runner), runner, suite$Mnname, count);
            try {
                try {
                    try {
                        $PcTestRunnerSkipSave$Ex((test$Mnrunner) runner, lists.cons($PcTestRunnerSkipList((test$Mnrunner) runner), $PcTestRunnerSkipSave((test$Mnrunner) runner)));
                        try {
                            try {
                                try {
                                    $PcTestRunnerFailSave$Ex((test$Mnrunner) runner, lists.cons($PcTestRunnerFailList((test$Mnrunner) runner), $PcTestRunnerFailSave((test$Mnrunner) runner)));
                                    try {
                                        try {
                                            try {
                                                $PcTestRunnerCountList$Ex((test$Mnrunner) runner, lists.cons(lists.cons($PcTestRunnerTotalCount((test$Mnrunner) runner), count), $PcTestRunnerCountList((test$Mnrunner) runner)));
                                                try {
                                                    try {
                                                        testRunnerGroupStack$Ex((test$Mnrunner) runner, lists.cons(suite$Mnname, testRunnerGroupStack((test$Mnrunner) runner)));
                                                    } catch (ClassCastException e) {
                                                        throw new WrongType(e, "test-runner-group-stack", 0, runner);
                                                    }
                                                } catch (ClassCastException e2) {
                                                    throw new WrongType(e2, "test-runner-group-stack!", 0, runner);
                                                }
                                            } catch (ClassCastException e22) {
                                                throw new WrongType(e22, "%test-runner-count-list", 0, runner);
                                            }
                                        } catch (ClassCastException e222) {
                                            throw new WrongType(e222, "%test-runner-total-count", 0, runner);
                                        }
                                    } catch (ClassCastException e2222) {
                                        throw new WrongType(e2222, "%test-runner-count-list!", 0, runner);
                                    }
                                } catch (ClassCastException e22222) {
                                    throw new WrongType(e22222, "%test-runner-fail-save", 0, runner);
                                }
                            } catch (ClassCastException e222222) {
                                throw new WrongType(e222222, "%test-runner-fail-list", 0, runner);
                            }
                        } catch (ClassCastException e2222222) {
                            throw new WrongType(e2222222, "%test-runner-fail-save!", 0, runner);
                        }
                    } catch (ClassCastException e22222222) {
                        throw new WrongType(e22222222, "%test-runner-skip-save", 0, runner);
                    }
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "%test-runner-skip-list", 0, runner);
                }
            } catch (ClassCastException e2222222222) {
                throw new WrongType(e2222222222, "%test-runner-skip-save!", 0, runner);
            }
        } catch (ClassCastException e22222222222) {
            throw new WrongType(e22222222222, "test-runner-on-group-begin", 0, runner);
        }
    }

    public static Boolean testOnGroupBeginSimple(Object runner, Object suite$Mnname, Object count) {
        try {
            if (lists.isNull(testRunnerGroupStack((test$Mnrunner) runner))) {
                Object log$Mnfile$Mnname;
                ports.display("%%%% Starting test ");
                ports.display(suite$Mnname);
                if (strings.isString(Boolean.TRUE)) {
                    log$Mnfile$Mnname = Boolean.TRUE;
                } else {
                    log$Mnfile$Mnname = strings.stringAppend(suite$Mnname, ".log");
                }
                try {
                    OutPort log$Mnfile = ports.openOutputFile(Path.valueOf(log$Mnfile$Mnname));
                    ports.display("%%%% Starting test ", log$Mnfile);
                    ports.display(suite$Mnname, log$Mnfile);
                    ports.newline(log$Mnfile);
                    try {
                        testRunnerAuxValue$Ex((test$Mnrunner) runner, log$Mnfile);
                        ports.display("  (Writing full log to \"");
                        ports.display(log$Mnfile$Mnname);
                        ports.display("\")");
                        ports.newline();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "test-runner-aux-value!", 0, runner);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "open-output-file", 1, log$Mnfile$Mnname);
                }
            }
            try {
                Object log = testRunnerAuxValue((test$Mnrunner) runner);
                if (ports.isOutputPort(log)) {
                    ports.display("Group begin: ", log);
                    ports.display(suite$Mnname, log);
                    ports.newline(log);
                }
                return Boolean.FALSE;
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "test-runner-aux-value", 0, runner);
            }
        } catch (ClassCastException e222) {
            throw new WrongType(e222, "test-runner-group-stack", 0, runner);
        }
    }

    public static Boolean testOnGroupEndSimple(Object runner) {
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                ports.display("Group end: ", log);
                try {
                    ports.display(lists.car.apply1(testRunnerGroupStack((test$Mnrunner) runner)), log);
                    ports.newline(log);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-group-stack", 0, runner);
                }
            }
            return Boolean.FALSE;
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-aux-value", 0, runner);
        }
    }

    static void $PcTestOnBadCountWrite(Object runner, Object count, Object expected$Mncount, Object port) {
        ports.display("*** Total number of tests was ", port);
        ports.display(count, port);
        ports.display(" but should be ", port);
        ports.display(expected$Mncount, port);
        ports.display(". ***", port);
        ports.newline(port);
        ports.display("*** Discrepancy indicates testsuite error or exceptions. ***", port);
        ports.newline(port);
    }

    public static void testOnBadCountSimple(Object runner, Object count, Object expected$Mncount) {
        $PcTestOnBadCountWrite(runner, count, expected$Mncount, ports.current$Mnoutput$Mnport.apply0());
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                $PcTestOnBadCountWrite(runner, count, expected$Mncount, log);
            }
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-runner-aux-value", 0, runner);
        }
    }

    public static Object testOnBadEndNameSimple(Object runner, Object begin$Mnname, Object end$Mnname) {
        return misc.error$V(strings.stringAppend($PcTestFormatLine(runner), "test-end ", begin$Mnname, " does not match test-begin ", end$Mnname), new Object[0]);
    }

    static void $PcTestFinalReport1(Object value, Object label, Object port) {
        if (Scheme.numGrt.apply2(value, Lit0) != Boolean.FALSE) {
            ports.display(label, port);
            ports.display(value, port);
            ports.newline(port);
        }
    }

    static void $PcTestFinalReportSimple(Object runner, Object port) {
        try {
            $PcTestFinalReport1(testRunnerPassCount((test$Mnrunner) runner), "# of expected passes      ", port);
            try {
                $PcTestFinalReport1(testRunnerXfailCount((test$Mnrunner) runner), "# of expected failures    ", port);
                try {
                    $PcTestFinalReport1(testRunnerXpassCount((test$Mnrunner) runner), "# of unexpected successes ", port);
                    try {
                        $PcTestFinalReport1(testRunnerFailCount((test$Mnrunner) runner), "# of unexpected failures  ", port);
                        try {
                            $PcTestFinalReport1(testRunnerSkipCount((test$Mnrunner) runner), "# of skipped tests        ", port);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "test-runner-skip-count", 0, runner);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "test-runner-fail-count", 0, runner);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "test-runner-xpass-count", 0, runner);
                }
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "test-runner-xfail-count", 0, runner);
            }
        } catch (ClassCastException e2222) {
            throw new WrongType(e2222, "test-runner-pass-count", 0, runner);
        }
    }

    public static void testOnFinalSimple(Object runner) {
        $PcTestFinalReportSimple(runner, ports.current$Mnoutput$Mnport.apply0());
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (ports.isOutputPort(log)) {
                $PcTestFinalReportSimple(runner, log);
            }
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-runner-aux-value", 0, runner);
        }
    }

    static Object $PcTestFormatLine(Object runner) {
        try {
            Object line$Mninfo = testResultAlist((test$Mnrunner) runner);
            Boolean source$Mnfile = lists.assq(Lit4, line$Mninfo);
            Boolean source$Mnline = lists.assq(Lit5, line$Mninfo);
            Object file = source$Mnfile != Boolean.FALSE ? lists.cdr.apply1(source$Mnfile) : "";
            if (source$Mnline == Boolean.FALSE) {
                return "";
            }
            Object[] objArr = new Object[4];
            objArr[0] = file;
            objArr[1] = ":";
            Object apply1 = lists.cdr.apply1(source$Mnline);
            try {
                objArr[2] = numbers.number$To$String((Number) apply1);
                objArr[3] = ": ";
                return strings.stringAppend(objArr);
            } catch (ClassCastException e) {
                throw new WrongType(e, "number->string", 1, apply1);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-result-alist", 0, runner);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object $PcTestEnd(java.lang.Object r13, java.lang.Object r14) {
        /*
        r12 = 0;
        r7 = testRunnerGet();
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x0140 }
        r9 = r0;
        r4 = testRunnerGroupStack(r9);
        r5 = $PcTestFormatLine(r7);
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x0149 }
        r9 = r0;
        testResultAlist$Ex(r9, r14);
        r9 = kawa.lib.lists.isNull(r4);
        if (r9 == 0) goto L_0x0031;
    L_0x001e:
        r9 = 2;
        r9 = new java.lang.Object[r9];
        r9[r12] = r5;
        r10 = 1;
        r11 = "test-end not in a group";
        r9[r10] = r11;
        r6 = kawa.lib.strings.stringAppend(r9);
        r9 = new java.lang.Object[r12];
        kawa.lib.misc.error$V(r6, r9);
    L_0x0031:
        r9 = java.lang.Boolean.FALSE;
        if (r13 == r9) goto L_0x0131;
    L_0x0035:
        r9 = kawa.lib.lists.car;
        r9 = r9.apply1(r4);
        r9 = gnu.kawa.functions.IsEqual.apply(r13, r9);
        if (r9 != 0) goto L_0x0054;
    L_0x0041:
        r10 = kawa.standard.Scheme.applyToArgs;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x0152 }
        r9 = r0;
        r9 = testRunnerOnBadEndName(r9);
        r11 = kawa.lib.lists.car;
        r11 = r11.apply1(r4);
        r10.apply4(r9, r7, r13, r11);
    L_0x0054:
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x015b }
        r9 = r0;
        r1 = $PcTestRunnerCountList(r9);
        r9 = kawa.lib.lists.cdar;
        r2 = r9.apply1(r1);
        r9 = kawa.lib.lists.caar;
        r8 = r9.apply1(r1);
        r10 = gnu.kawa.functions.AddOp.$Mn;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x0164 }
        r9 = r0;
        r9 = $PcTestRunnerTotalCount(r9);
        r3 = r10.apply2(r9, r8);
        r9 = java.lang.Boolean.FALSE;
        if (r2 == r9) goto L_0x0137;
    L_0x007a:
        r9 = kawa.standard.Scheme.numEqu;
        r9 = r9.apply2(r2, r3);
        r10 = java.lang.Boolean.FALSE;
        if (r9 != r10) goto L_0x0091;
    L_0x0084:
        r10 = kawa.standard.Scheme.applyToArgs;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x016d }
        r9 = r0;
        r9 = testRunnerOnBadCount(r9);
        r10.apply4(r9, r7, r3, r2);
    L_0x0091:
        r10 = kawa.standard.Scheme.applyToArgs;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x0176 }
        r9 = r0;
        r9 = testRunnerOnGroupEnd(r9);
        r10.apply2(r9, r7);
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x017f }
        r9 = r0;
        r11 = kawa.lib.lists.cdr;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x0188 }
        r10 = r0;
        r10 = testRunnerGroupStack(r10);
        r10 = r11.apply1(r10);
        testRunnerGroupStack$Ex(r9, r10);
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x0191 }
        r9 = r0;
        r11 = kawa.lib.lists.car;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x019a }
        r10 = r0;
        r10 = $PcTestRunnerSkipSave(r10);
        r10 = r11.apply1(r10);
        $PcTestRunnerSkipList$Ex(r9, r10);
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01a3 }
        r9 = r0;
        r11 = kawa.lib.lists.cdr;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01ac }
        r10 = r0;
        r10 = $PcTestRunnerSkipSave(r10);
        r10 = r11.apply1(r10);
        $PcTestRunnerSkipSave$Ex(r9, r10);
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01b5 }
        r9 = r0;
        r11 = kawa.lib.lists.car;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01be }
        r10 = r0;
        r10 = $PcTestRunnerFailSave(r10);
        r10 = r11.apply1(r10);
        $PcTestRunnerFailList$Ex(r9, r10);
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01c7 }
        r9 = r0;
        r11 = kawa.lib.lists.cdr;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01d0 }
        r10 = r0;
        r10 = $PcTestRunnerFailSave(r10);
        r10 = r11.apply1(r10);
        $PcTestRunnerFailSave$Ex(r9, r10);
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01d9 }
        r9 = r0;
        r10 = kawa.lib.lists.cdr;
        r10 = r10.apply1(r1);
        $PcTestRunnerCountList$Ex(r9, r10);
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01e2 }
        r9 = r0;
        r9 = testRunnerGroupStack(r9);
        r9 = kawa.lib.lists.isNull(r9);
        if (r9 == 0) goto L_0x013d;
    L_0x0122:
        r10 = kawa.standard.Scheme.applyToArgs;
        r0 = r7;
        r0 = (gnu.kawa.slib.test$Mnrunner) r0;	 Catch:{ ClassCastException -> 0x01eb }
        r9 = r0;
        r9 = testRunnerOnFinal(r9);
        r9 = r10.apply2(r9, r7);
    L_0x0130:
        return r9;
    L_0x0131:
        r9 = java.lang.Boolean.FALSE;
        if (r13 == r9) goto L_0x0054;
    L_0x0135:
        goto L_0x0041;
    L_0x0137:
        r9 = java.lang.Boolean.FALSE;
        if (r2 == r9) goto L_0x0091;
    L_0x013b:
        goto L_0x0084;
    L_0x013d:
        r9 = gnu.mapping.Values.empty;
        goto L_0x0130;
    L_0x0140:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-runner-group-stack";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0149:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-result-alist!";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0152:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-runner-on-bad-end-name";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x015b:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-count-list";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0164:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-total-count";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x016d:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-runner-on-bad-count";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0176:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-runner-on-group-end";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x017f:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-runner-group-stack!";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0188:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-runner-group-stack";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x0191:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-skip-list!";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x019a:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-skip-save";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01a3:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-skip-save!";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01ac:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-skip-save";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01b5:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-fail-list!";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01be:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-fail-save";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01c7:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-fail-save!";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01d0:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-fail-save";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01d9:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "%test-runner-count-list!";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01e2:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-runner-group-stack";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
    L_0x01eb:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "test-runner-on-final";
        r10.<init>(r9, r11, r12, r7);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.testing.$PcTestEnd(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    static Object testOnTestBeginSimple(Object runner) {
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            if (!ports.isOutputPort(log)) {
                return Values.empty;
            }
            try {
                Object results = testResultAlist((test$Mnrunner) runner);
                Boolean source$Mnfile = lists.assq(Lit4, results);
                Boolean source$Mnline = lists.assq(Lit5, results);
                Object source$Mnform = lists.assq(Lit6, results);
                Boolean test$Mnname = lists.assq(Lit7, results);
                ports.display("Test begin:", log);
                ports.newline(log);
                if (test$Mnname != Boolean.FALSE) {
                    $PcTestWriteResult1(test$Mnname, log);
                }
                if (source$Mnfile != Boolean.FALSE) {
                    $PcTestWriteResult1(source$Mnfile, log);
                }
                if (source$Mnline != Boolean.FALSE) {
                    $PcTestWriteResult1(source$Mnline, log);
                }
                return source$Mnfile != Boolean.FALSE ? $PcTestWriteResult1(source$Mnform, log) : Values.empty;
            } catch (ClassCastException e) {
                throw new WrongType(e, "test-result-alist", 0, runner);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-aux-value", 0, runner);
        }
    }

    public static Object testOnTestEndSimple(Object runner) {
        try {
            Object log = testRunnerAuxValue((test$Mnrunner) runner);
            try {
                Boolean p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
                SimpleSymbol kind = p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE;
                if (lists.memq(kind, Lit8) != Boolean.FALSE) {
                    try {
                        Object results = testResultAlist((test$Mnrunner) runner);
                        Boolean source$Mnfile = lists.assq(Lit4, results);
                        Boolean source$Mnline = lists.assq(Lit5, results);
                        Boolean test$Mnname = lists.assq(Lit7, results);
                        if (!(source$Mnfile == Boolean.FALSE && source$Mnline == Boolean.FALSE)) {
                            if (source$Mnfile != Boolean.FALSE) {
                                ports.display(lists.cdr.apply1(source$Mnfile));
                            }
                            ports.display(":");
                            if (source$Mnline != Boolean.FALSE) {
                                ports.display(lists.cdr.apply1(source$Mnline));
                            }
                            ports.display(": ");
                        }
                        ports.display(kind == Lit9 ? "XPASS" : "FAIL");
                        if (test$Mnname != Boolean.FALSE) {
                            ports.display(" ");
                            ports.display(lists.cdr.apply1(test$Mnname));
                        }
                        ports.newline();
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "test-result-alist", 0, runner);
                    }
                }
                if (!ports.isOutputPort(log)) {
                    return Values.empty;
                }
                ports.display("Test end:", log);
                ports.newline(log);
                try {
                    for (Object list = testResultAlist((test$Mnrunner) runner); lists.isPair(list); list = lists.cdr.apply1(list)) {
                        Object pair = lists.car.apply1(list);
                        if (lists.memq(lists.car.apply1(pair), Lit10) == Boolean.FALSE) {
                            $PcTestWriteResult1(pair, log);
                        }
                    }
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-result-alist", 0, runner);
                }
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "test-result-alist", 0, runner);
            }
        } catch (ClassCastException e222) {
            throw new WrongType(e222, "test-runner-aux-value", 0, runner);
        }
    }

    static Object $PcTestWriteResult1(Object pair, Object port) {
        ports.display("  ", port);
        ports.display(lists.car.apply1(pair), port);
        ports.display(": ", port);
        ports.write(lists.cdr.apply1(pair), port);
        ports.newline(port);
        return Values.empty;
    }

    public static Object testResultSet$Ex(Object runner, Object pname, Object value) {
        try {
            Object alist = testResultAlist((test$Mnrunner) runner);
            Object p = lists.assq(pname, alist);
            if (p != Boolean.FALSE) {
                try {
                    lists.setCdr$Ex((Pair) p, value);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-cdr!", 1, p);
                }
            }
            try {
                testResultAlist$Ex((test$Mnrunner) runner, lists.cons(lists.cons(pname, value), alist));
                return Values.empty;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "test-result-alist!", 0, runner);
            }
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "test-result-alist", 0, runner);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 50:
                return lambda1(obj, obj2, obj3);
            case 51:
                return lambda2(obj, obj2, obj3);
            case 52:
                return lambda3(obj, obj2, obj3);
            case 59:
                return testOnGroupBeginSimple(obj, obj2, obj3);
            case 61:
                testOnBadCountSimple(obj, obj2, obj3);
                return Values.empty;
            case 62:
                return testOnBadEndNameSimple(obj, obj2, obj3);
            case 67:
                return testResultSet$Ex(obj, obj2, obj3);
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static void testResultClear(Object runner) {
        try {
            testResultAlist$Ex((test$Mnrunner) runner, LList.Empty);
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist!", 0, runner);
        }
    }

    public static void testResultRemove(Object runner, Object pname) {
        frame gnu_kawa_slib_testing_frame = new frame();
        try {
            Object alist = testResultAlist((test$Mnrunner) runner);
            gnu_kawa_slib_testing_frame.f102p = lists.assq(pname, alist);
            if (gnu_kawa_slib_testing_frame.f102p != Boolean.FALSE) {
                try {
                    testResultAlist$Ex((test$Mnrunner) runner, gnu_kawa_slib_testing_frame.lambda4loop(alist));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-result-alist!", 0, runner);
                }
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-result-alist", 0, runner);
        }
    }

    public static Object testResultKind$V(Object[] argsArray) {
        Object runner;
        LList rest = LList.makeList(argsArray, 0);
        if (lists.isPair(rest)) {
            runner = lists.car.apply1(rest);
        } else {
            runner = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        }
        try {
            Boolean p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
            if (p != Boolean.FALSE) {
                return lists.cdr.apply1(p);
            }
            return Boolean.FALSE;
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 70:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 71:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 84:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 86:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 87:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static Object isTestPassed$V(Object[] argsArray) {
        LList rest = LList.makeList(argsArray, 0);
        Object runner = lists.isPair(rest) ? lists.car.apply1(rest) : testRunnerGet();
        try {
            Object apply1;
            Boolean p = lists.assq(Lit1, testResultAlist((test$Mnrunner) runner));
            if (p != Boolean.FALSE) {
                apply1 = lists.cdr.apply1(p);
            } else {
                apply1 = Boolean.FALSE;
            }
            return lists.memq(apply1, Lit11);
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    public static Object $PcTestReportResult() {
        Object r = testRunnerGet();
        Object result$Mnkind = testResultKind$V(new Object[]{r});
        if (Scheme.isEqv.apply2(result$Mnkind, Lit12) != Boolean.FALSE) {
            try {
                try {
                    testRunnerPassCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerPassCount((test$Mnrunner) r)));
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count", 0, r);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "test-runner-pass-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit14) != Boolean.FALSE) {
            try {
                try {
                    testRunnerFailCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerFailCount((test$Mnrunner) r)));
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "test-runner-fail-count", 0, r);
                }
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "test-runner-fail-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit9) != Boolean.FALSE) {
            try {
                try {
                    testRunnerXpassCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerXpassCount((test$Mnrunner) r)));
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "test-runner-xpass-count", 0, r);
                }
            } catch (ClassCastException e22222) {
                throw new WrongType(e22222, "test-runner-xpass-count!", 0, r);
            }
        } else if (Scheme.isEqv.apply2(result$Mnkind, Lit3) != Boolean.FALSE) {
            try {
                try {
                    testRunnerXfailCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerXfailCount((test$Mnrunner) r)));
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "test-runner-xfail-count", 0, r);
                }
            } catch (ClassCastException e2222222) {
                throw new WrongType(e2222222, "test-runner-xfail-count!", 0, r);
            }
        } else {
            try {
                try {
                    testRunnerSkipCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, testRunnerSkipCount((test$Mnrunner) r)));
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "test-runner-skip-count", 0, r);
                }
            } catch (ClassCastException e222222222) {
                throw new WrongType(e222222222, "test-runner-skip-count!", 0, r);
            }
        }
        try {
            try {
                $PcTestRunnerTotalCount$Ex((test$Mnrunner) r, AddOp.$Pl.apply2(Lit13, $PcTestRunnerTotalCount((test$Mnrunner) r)));
                try {
                    return Scheme.applyToArgs.apply2(testRunnerOnTestEnd((test$Mnrunner) r), r);
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "test-runner-on-test-end", 0, r);
                }
            } catch (ClassCastException e22222222222) {
                throw new WrongType(e22222222222, "%test-runner-total-count", 0, r);
            }
        } catch (ClassCastException e222222222222) {
            throw new WrongType(e222222222222, "%test-runner-total-count!", 0, r);
        }
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case 53:
                return testRunnerNull();
            case 54:
                return testRunnerSimple();
            case 55:
                return testRunnerGet();
            case 56:
                return testRunnerCreate();
            case 72:
                return $PcTestReportResult();
            default:
                return super.apply0(moduleMethod);
        }
    }

    static Object $PcTestSyntaxFile(Object form) {
        return std_syntax.syntaxSource(form);
    }

    static Pair $PcTestSourceLine2(Object form) {
        Boolean line = std_syntax.syntaxLine(form);
        Boolean file = $PcTestSyntaxFile(form);
        Object line$Mnpair = line != Boolean.FALSE ? LList.list1(lists.cons(Lit5, line)) : LList.Empty;
        Pair cons = lists.cons(Lit6, std_syntax.syntaxObject$To$Datum(form));
        if (file != Boolean.FALSE) {
            line$Mnpair = lists.cons(lists.cons(Lit4, file), line$Mnpair);
        }
        return lists.cons(cons, line$Mnpair);
    }

    public static boolean $PcTestOnTestBegin(Object r) {
        $PcTestShouldExecute(r);
        try {
            Scheme.applyToArgs.apply2(testRunnerOnTestBegin((test$Mnrunner) r), r);
            SimpleSymbol simpleSymbol = Lit2;
            try {
                Boolean p = lists.assq(Lit1, testResultAlist((test$Mnrunner) r));
                return ((simpleSymbol == (p != Boolean.FALSE ? lists.cdr.apply1(p) : Boolean.FALSE) ? 1 : 0) + 1) & 1;
            } catch (ClassCastException e) {
                throw new WrongType(e, "test-result-alist", 0, r);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "test-runner-on-test-begin", 0, r);
        }
    }

    public static Object $PcTestOnTestEnd(Object r, Object result) {
        SimpleSymbol simpleSymbol = Lit1;
        try {
            SimpleSymbol apply1;
            Boolean p = lists.assq(Lit1, testResultAlist((test$Mnrunner) r));
            if (p != Boolean.FALSE) {
                apply1 = lists.cdr.apply1(p);
            } else {
                apply1 = Boolean.FALSE;
            }
            Object obj = apply1 == Lit3 ? result != Boolean.FALSE ? Lit9 : Lit3 : result != Boolean.FALSE ? Lit12 : Lit14;
            return testResultSet$Ex(r, simpleSymbol, obj);
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, r);
        }
    }

    public static Object testRunnerTestName(Object runner) {
        try {
            Boolean p = lists.assq(Lit7, testResultAlist((test$Mnrunner) runner));
            return p != Boolean.FALSE ? lists.cdr.apply1(p) : "";
        } catch (ClassCastException e) {
            throw new WrongType(e, "test-result-alist", 0, runner);
        }
    }

    public static Procedure $PcTestApproximimate$Eq(Object error) {
        frame0 gnu_kawa_slib_testing_frame0 = new frame0();
        gnu_kawa_slib_testing_frame0.error = error;
        return gnu_kawa_slib_testing_frame0.lambda$Fn4;
    }

    static Object lambda16(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(3, null);
        if (Lit95.match(list2, allocVars, 0)) {
            return Lit96.execute(allocVars, TemplateScope.make());
        } else if (!Lit97.match(list2, allocVars, 0)) {
            return syntax_case.error("syntax-case", list2);
        } else {
            return Lit98.execute(allocVars, TemplateScope.make());
        }
    }

    static Object lambda17(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(4, null);
        if (Lit100.match(list2, allocVars, 0)) {
            return Lit101.execute(allocVars, TemplateScope.make());
        } else if (!Lit102.match(list2, allocVars, 0)) {
            return syntax_case.error("syntax-case", list2);
        } else {
            return Lit103.execute(allocVars, TemplateScope.make());
        }
    }

    static Object $PcTestComp2(Object comp, Object x) {
        Pair list3 = LList.list3(x, LList.list2(Lit15, $PcTestSourceLine2(x)), comp);
        Object[] allocVars = SyntaxPattern.allocVars(6, null);
        if (Lit16.match(list3, allocVars, 0)) {
            return Lit17.execute(allocVars, TemplateScope.make());
        } else if (!Lit18.match(list3, allocVars, 0)) {
            return syntax_case.error("syntax-case", list3);
        } else {
            return Lit19.execute(allocVars, TemplateScope.make());
        }
    }

    static Object lambda18(Object x) {
        return $PcTestComp2(Lit105.execute(null, TemplateScope.make()), x);
    }

    static Object lambda19(Object x) {
        return $PcTestComp2(Lit107.execute(null, TemplateScope.make()), x);
    }

    static Object lambda20(Object x) {
        return $PcTestComp2(Lit109.execute(null, TemplateScope.make()), x);
    }

    static Object lambda21(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(6, null);
        if (Lit111.match(list2, allocVars, 0)) {
            return Lit112.execute(allocVars, TemplateScope.make());
        } else if (!Lit113.match(list2, allocVars, 0)) {
            return syntax_case.error("syntax-case", list2);
        } else {
            return Lit114.execute(allocVars, TemplateScope.make());
        }
    }

    static Object lambda22(Object x) {
        Pair list2 = LList.list2(x, LList.list2(Lit15, $PcTestSourceLine2(x)));
        Object[] allocVars = SyntaxPattern.allocVars(5, null);
        if (Lit118.match(list2, allocVars, 0)) {
            return Lit119.execute(allocVars, TemplateScope.make());
        } else if (Lit120.match(list2, allocVars, 0)) {
            return Lit121.execute(allocVars, TemplateScope.make());
        } else if (!Lit122.match(list2, allocVars, 0)) {
            return syntax_case.error("syntax-case", list2);
        } else {
            return Lit123.execute(allocVars, TemplateScope.make());
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 12:
                return isTestRunner(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 13:
                try {
                    return testRunnerPassCount((test$Mnrunner) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count", 1, obj);
                }
            case 15:
                try {
                    return testRunnerFailCount((test$Mnrunner) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-runner-fail-count", 1, obj);
                }
            case 17:
                try {
                    return testRunnerXpassCount((test$Mnrunner) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "test-runner-xpass-count", 1, obj);
                }
            case 19:
                try {
                    return testRunnerXfailCount((test$Mnrunner) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "test-runner-xfail-count", 1, obj);
                }
            case 21:
                try {
                    return testRunnerSkipCount((test$Mnrunner) obj);
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "test-runner-skip-count", 1, obj);
                }
            case 23:
                try {
                    return $PcTestRunnerSkipList((test$Mnrunner) obj);
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "%test-runner-skip-list", 1, obj);
                }
            case 25:
                try {
                    return $PcTestRunnerFailList((test$Mnrunner) obj);
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "%test-runner-fail-list", 1, obj);
                }
            case 27:
                try {
                    return testRunnerGroupStack((test$Mnrunner) obj);
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "test-runner-group-stack", 1, obj);
                }
            case 29:
                try {
                    return testRunnerOnTestBegin((test$Mnrunner) obj);
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "test-runner-on-test-begin", 1, obj);
                }
            case 31:
                try {
                    return testRunnerOnTestEnd((test$Mnrunner) obj);
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "test-runner-on-test-end", 1, obj);
                }
            case 33:
                try {
                    return testRunnerOnGroupBegin((test$Mnrunner) obj);
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "test-runner-on-group-begin", 1, obj);
                }
            case 35:
                try {
                    return testRunnerOnGroupEnd((test$Mnrunner) obj);
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "test-runner-on-group-end", 1, obj);
                }
            case 37:
                try {
                    return testRunnerOnFinal((test$Mnrunner) obj);
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "test-runner-on-final", 1, obj);
                }
            case 39:
                try {
                    return testRunnerOnBadCount((test$Mnrunner) obj);
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "test-runner-on-bad-count", 1, obj);
                }
            case 41:
                try {
                    return testRunnerOnBadEndName((test$Mnrunner) obj);
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "test-runner-on-bad-end-name", 1, obj);
                }
            case 43:
                try {
                    return testResultAlist((test$Mnrunner) obj);
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "test-result-alist", 1, obj);
                }
            case 45:
                try {
                    return testRunnerAuxValue((test$Mnrunner) obj);
                } catch (ClassCastException e2222222222222222) {
                    throw new WrongType(e2222222222222222, "test-runner-aux-value", 1, obj);
                }
            case 47:
                testRunnerReset(obj);
                return Values.empty;
            case 48:
                return testRunnerGroupPath(obj);
            case 49:
                return $PcTestNullCallback(obj);
            case 57:
                return $PcTestShouldExecute(obj);
            case 60:
                return testOnGroupEndSimple(obj);
            case 63:
                testOnFinalSimple(obj);
                return Values.empty;
            case 65:
                return testOnTestBeginSimple(obj);
            case 66:
                return testOnTestEndSimple(obj);
            case 68:
                testResultClear(obj);
                return Values.empty;
            case 73:
                return $PcTestOnTestBegin(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 75:
                return testRunnerTestName(obj);
            case 76:
                return $PcTestApproximimate$Eq(obj);
            case 77:
                return lambda16(obj);
            case 78:
                return lambda17(obj);
            case 79:
                return lambda18(obj);
            case 80:
                return lambda19(obj);
            case 81:
                return lambda20(obj);
            case 82:
                return lambda21(obj);
            case 83:
                return lambda22(obj);
            case 88:
                return $PcTestAsSpecifier(obj);
            case 89:
                return testMatchName(obj);
            case 90:
                return testReadEvalString(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public static Object testApply$V(Object first, Object[] argsArray) {
        frame1 gnu_kawa_slib_testing_frame1 = new frame1();
        gnu_kawa_slib_testing_frame1.first = first;
        gnu_kawa_slib_testing_frame1.rest = LList.makeList(argsArray, 0);
        if (isTestRunner(gnu_kawa_slib_testing_frame1.first)) {
            gnu_kawa_slib_testing_frame1.saved$Mnrunner$1 = ((Procedure) test$Mnrunner$Mncurrent).apply0();
            return misc.dynamicWind(gnu_kawa_slib_testing_frame1.lambda$Fn5, gnu_kawa_slib_testing_frame1.lambda$Fn6, gnu_kawa_slib_testing_frame1.lambda$Fn7);
        }
        Object r = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        if (r != Boolean.FALSE) {
            try {
                Object run$Mnlist = $PcTestRunnerRunList((test$Mnrunner) r);
                if (lists.isNull(gnu_kawa_slib_testing_frame1.rest)) {
                    try {
                        try {
                            $PcTestRunnerRunList$Ex((test$Mnrunner) r, lists.reverse$Ex((LList) run$Mnlist));
                            return Scheme.applyToArgs.apply1(gnu_kawa_slib_testing_frame1.first);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "reverse!", 1, run$Mnlist);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "%test-runner-run-list!", 0, r);
                    }
                }
                try {
                    Object list1;
                    test$Mnrunner gnu_kawa_slib_test_Mnrunner = (test$Mnrunner) r;
                    if (run$Mnlist == Boolean.TRUE) {
                        list1 = LList.list1(gnu_kawa_slib_testing_frame1.first);
                    } else {
                        list1 = lists.cons(gnu_kawa_slib_testing_frame1.first, run$Mnlist);
                    }
                    $PcTestRunnerRunList$Ex(gnu_kawa_slib_test_Mnrunner, list1);
                    Scheme.apply.apply2(test$Mnapply, gnu_kawa_slib_testing_frame1.rest);
                    try {
                        $PcTestRunnerRunList$Ex((test$Mnrunner) r, run$Mnlist);
                        return Values.empty;
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "%test-runner-run-list!", 0, r);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "%test-runner-run-list!", 0, r);
                }
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "%test-runner-run-list", 0, r);
            }
        }
        gnu_kawa_slib_testing_frame1.f99r = testRunnerCreate();
        gnu_kawa_slib_testing_frame1.saved$Mnrunner = ((Procedure) test$Mnrunner$Mncurrent).apply0();
        misc.dynamicWind(gnu_kawa_slib_testing_frame1.lambda$Fn8, gnu_kawa_slib_testing_frame1.lambda$Fn9, gnu_kawa_slib_testing_frame1.lambda$Fn10);
        Procedure procedure = Scheme.applyToArgs;
        Object obj = gnu_kawa_slib_testing_frame1.f99r;
        try {
            return procedure.apply2(testRunnerOnFinal((test$Mnrunner) obj), gnu_kawa_slib_testing_frame1.f99r);
        } catch (ClassCastException e3) {
            throw new WrongType(e3, "test-runner-on-final", 0, obj);
        }
    }

    public static Procedure $PcTestMatchNth(Object n, Object count) {
        frame2 gnu_kawa_slib_testing_frame2 = new frame2();
        gnu_kawa_slib_testing_frame2.f101n = n;
        gnu_kawa_slib_testing_frame2.count = count;
        gnu_kawa_slib_testing_frame2.f100i = Lit0;
        return gnu_kawa_slib_testing_frame2.lambda$Fn11;
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 14:
                try {
                    testRunnerPassCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "test-runner-pass-count!", 1, obj);
                }
            case 16:
                try {
                    testRunnerFailCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "test-runner-fail-count!", 1, obj);
                }
            case 18:
                try {
                    testRunnerXpassCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "test-runner-xpass-count!", 1, obj);
                }
            case 20:
                try {
                    testRunnerXfailCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "test-runner-xfail-count!", 1, obj);
                }
            case 22:
                try {
                    testRunnerSkipCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "test-runner-skip-count!", 1, obj);
                }
            case 24:
                try {
                    $PcTestRunnerSkipList$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "%test-runner-skip-list!", 1, obj);
                }
            case 26:
                try {
                    $PcTestRunnerFailList$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "%test-runner-fail-list!", 1, obj);
                }
            case 28:
                try {
                    testRunnerGroupStack$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "test-runner-group-stack!", 1, obj);
                }
            case 30:
                try {
                    testRunnerOnTestBegin$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "test-runner-on-test-begin!", 1, obj);
                }
            case 32:
                try {
                    testRunnerOnTestEnd$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "test-runner-on-test-end!", 1, obj);
                }
            case 34:
                try {
                    testRunnerOnGroupBegin$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "test-runner-on-group-begin!", 1, obj);
                }
            case 36:
                try {
                    testRunnerOnGroupEnd$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "test-runner-on-group-end!", 1, obj);
                }
            case 38:
                try {
                    testRunnerOnFinal$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "test-runner-on-final!", 1, obj);
                }
            case 40:
                try {
                    testRunnerOnBadCount$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "test-runner-on-bad-count!", 1, obj);
                }
            case 42:
                try {
                    testRunnerOnBadEndName$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "test-runner-on-bad-end-name!", 1, obj);
                }
            case 44:
                try {
                    testResultAlist$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "test-result-alist!", 1, obj);
                }
            case 46:
                try {
                    testRunnerAuxValue$Ex((test$Mnrunner) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2222222222222222) {
                    throw new WrongType(e2222222222222222, "test-runner-aux-value!", 1, obj);
                }
            case 58:
                $PcTestBegin(obj, obj2);
                return Values.empty;
            case 64:
                return $PcTestEnd(obj, obj2);
            case 69:
                testResultRemove(obj, obj2);
                return Values.empty;
            case 74:
                return $PcTestOnTestEnd(obj, obj2);
            case 85:
                return $PcTestMatchNth(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Procedure $PcTestMatchAll$V(Object[] argsArray) {
        frame3 gnu_kawa_slib_testing_frame3 = new frame3();
        gnu_kawa_slib_testing_frame3.pred$Mnlist = LList.makeList(argsArray, 0);
        return gnu_kawa_slib_testing_frame3.lambda$Fn12;
    }

    public static Procedure $PcTestMatchAny$V(Object[] argsArray) {
        frame4 gnu_kawa_slib_testing_frame4 = new frame4();
        gnu_kawa_slib_testing_frame4.pred$Mnlist = LList.makeList(argsArray, 0);
        return gnu_kawa_slib_testing_frame4.lambda$Fn13;
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 70:
                return testResultKind$V(objArr);
            case 71:
                return isTestPassed$V(objArr);
            case 84:
                Object obj = objArr[0];
                int length = objArr.length - 1;
                Object[] objArr2 = new Object[length];
                while (true) {
                    length--;
                    if (length < 0) {
                        return testApply$V(obj, objArr2);
                    }
                    objArr2[length] = objArr[length + 1];
                }
            case 86:
                return $PcTestMatchAll$V(objArr);
            case 87:
                return $PcTestMatchAny$V(objArr);
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static Object $PcTestAsSpecifier(Object specifier) {
        if (misc.isProcedure(specifier)) {
            return specifier;
        }
        if (numbers.isInteger(specifier)) {
            return $PcTestMatchNth(Lit13, specifier);
        }
        if (strings.isString(specifier)) {
            return testMatchName(specifier);
        }
        return misc.error$V("not a valid test specifier", new Object[0]);
    }

    public static Procedure testMatchName(Object name) {
        frame5 gnu_kawa_slib_testing_frame5 = new frame5();
        gnu_kawa_slib_testing_frame5.name = name;
        return gnu_kawa_slib_testing_frame5.lambda$Fn14;
    }

    public static Object testReadEvalString(Object string) {
        try {
            InPort port = ports.openInputString((CharSequence) string);
            Object form = ports.read(port);
            if (ports.isEofObject(readchar.readChar.apply1(port))) {
                return Eval.eval.apply1(form);
            }
            return misc.error$V("(not at eof)", new Object[0]);
        } catch (ClassCastException e) {
            throw new WrongType(e, "open-input-string", 1, string);
        }
    }
}

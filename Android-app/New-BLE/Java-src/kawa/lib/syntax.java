package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SynchronizedExp;
import gnu.expr.TryExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.math.IntNum;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxForms;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.SyntaxTemplate;
import kawa.lang.TemplateScope;
import kawa.lang.Translator;
import kawa.standard.IfFeature;
import kawa.standard.Scheme;
import kawa.standard.syntax_case;

/* compiled from: syntax.scm */
public class syntax extends ModuleBody {
    public static final Macro $Pccond$Mnexpand;
    public static final Macro $Pcimport;
    public static final Location $Prvt$and = StaticFieldLocation.make("kawa.lib.std_syntax", "and");
    public static final Location $Prvt$define$Mnconstant = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnconstant");
    public static final Location $Prvt$define$Mnsyntax = StaticFieldLocation.make("kawa.lib.prim_syntax", "define$Mnsyntax");
    public static final Location $Prvt$if = StaticFieldLocation.make("kawa.lib.prim_syntax", "if");
    public static final Location $Prvt$let = StaticFieldLocation.make("kawa.lib.std_syntax", "let");
    public static final Location $Prvt$or = StaticFieldLocation.make("kawa.lib.std_syntax", "or");
    public static final Location $Prvt$try$Mncatch = StaticFieldLocation.make("kawa.lib.prim_syntax", "try$Mncatch");
    public static final syntax $instance = new syntax();
    static final SyntaxPattern Lit0 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
    static final SyntaxTemplate Lit1 = new SyntaxTemplate("\u0001\u0000", "\u0003", new Object[0], 0);
    static final SyntaxPattern Lit10 = new SyntaxPattern("\u0003", new Object[0], 1);
    static final SimpleSymbol Lit100;
    static final SyntaxRules Lit101;
    static final SimpleSymbol Lit102 = ((SimpleSymbol) new SimpleSymbol("$lookup$").readResolve());
    static final SimpleSymbol Lit103 = ((SimpleSymbol) new SimpleSymbol("gnu.mapping.LocationProc").readResolve());
    static final SimpleSymbol Lit104 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quasiquote_sym).readResolve());
    static final SimpleSymbol Lit105 = ((SimpleSymbol) new SimpleSymbol("arg").readResolve());
    static final SimpleSymbol Lit106 = ((SimpleSymbol) new SimpleSymbol("as").readResolve());
    static final SimpleSymbol Lit107 = ((SimpleSymbol) new SimpleSymbol("wt").readResolve());
    static final SimpleSymbol Lit108 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final SimpleSymbol Lit109 = ((SimpleSymbol) new SimpleSymbol("lambda").readResolve());
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit110 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit111 = ((SimpleSymbol) new SimpleSymbol("else").readResolve());
    static final SimpleSymbol Lit112 = ((SimpleSymbol) new SimpleSymbol("and").readResolve());
    static final SimpleSymbol Lit113 = ((SimpleSymbol) new SimpleSymbol("or").readResolve());
    static final SimpleSymbol Lit114 = ((SimpleSymbol) new SimpleSymbol("not").readResolve());
    static final SimpleSymbol Lit115 = ((SimpleSymbol) new SimpleSymbol("let").readResolve());
    static final SimpleSymbol Lit116 = ((SimpleSymbol) new SimpleSymbol("call-with-values").readResolve());
    static final SimpleSymbol Lit117 = ((SimpleSymbol) new SimpleSymbol("x").readResolve());
    static final SimpleSymbol Lit118 = ((SimpleSymbol) new SimpleSymbol("kawa.standard.ImportFromLibrary").readResolve());
    static final SimpleSymbol Lit119 = ((SimpleSymbol) new SimpleSymbol("instance").readResolve());
    static final SyntaxRules Lit12;
    static final SimpleSymbol Lit120 = ((SimpleSymbol) new SimpleSymbol("prefix").readResolve());
    static final SimpleSymbol Lit121 = ((SimpleSymbol) new SimpleSymbol("if").readResolve());
    static final SimpleSymbol Lit122 = ((SimpleSymbol) new SimpleSymbol("form").readResolve());
    static final SimpleSymbol Lit123 = ((SimpleSymbol) new SimpleSymbol("%define-macro").readResolve());
    static final SimpleSymbol Lit13;
    static final SyntaxRules Lit14;
    static final SimpleSymbol Lit15;
    static final SyntaxRules Lit16;
    static final SimpleSymbol Lit17;
    static final SyntaxRules Lit18;
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("unless").readResolve());
    static final SyntaxTemplate Lit2 = new SyntaxTemplate("\u0001\u0000", "\n", new Object[0], 0);
    static final SyntaxRules Lit20;
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("try-finally").readResolve());
    static final SyntaxPattern Lit22 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit23 = new SyntaxTemplate("\u0001\u0001\u0001", "\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit24 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("synchronized").readResolve());
    static final SyntaxPattern Lit26 = new SyntaxPattern("\f\u0007\f\u000f\u0013", new Object[0], 3);
    static final SyntaxTemplate Lit27 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit28 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0012", new Object[0], 0);
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("identifier-list?").readResolve());
    static final SyntaxPattern Lit3 = new SyntaxPattern("\b", new Object[0], 0);
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("identifier-pair-list?").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("import-handle-only").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("import-handle-except").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("import-handle-prefix").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("import-handle-rename").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("import-mapper").readResolve());
    static final SimpleSymbol Lit36;
    static final SyntaxRules Lit37;
    static final SimpleSymbol Lit38;
    static final SyntaxPattern Lit39 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol) new SimpleSymbol("rename").readResolve()}, 4);
    static final SyntaxPattern Lit4 = new SyntaxPattern("\u0003", new Object[0], 1);
    static final SyntaxTemplate Lit40 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit41 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
    static final SyntaxTemplate Lit42 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit43 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit44 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit45 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0018\u0004", new Object[]{(SimpleSymbol) new SimpleSymbol("rest").readResolve()}, 0);
    static final SyntaxPattern Lit46 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol) new SimpleSymbol("only").readResolve()}, 4);
    static final SyntaxTemplate Lit47 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit48 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
    static final SyntaxTemplate Lit49 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxPattern Lit5 = new SyntaxPattern(",\f\u0007\f\u000f\b\u0013", new Object[0], 3);
    static final SyntaxTemplate Lit50 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit51 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit52 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxPattern Lit53 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{(SimpleSymbol) new SimpleSymbol("except").readResolve()}, 4);
    static final SyntaxTemplate Lit54 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit55 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0011\u0018\u0004\b\u000b", new Object[]{Lit38}, 0);
    static final SyntaxTemplate Lit56 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit57 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit58 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0010", new Object[0], 0);
    static final SyntaxTemplate Lit59 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxTemplate Lit6 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0003", new Object[0], 0);
    static final SyntaxPattern Lit60 = new SyntaxPattern("\f\u0007<\f\u0002\f\u000f\f\u0017\b\f\u001f\b", new Object[]{Lit120}, 4);
    static final SyntaxTemplate Lit61;
    static final SyntaxTemplate Lit62 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0013", new Object[0], 0);
    static final SyntaxTemplate Lit63 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u001b", new Object[0], 0);
    static final SyntaxTemplate Lit64 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0010", new Object[0], 0);
    static final SyntaxPattern Lit65 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\u0013\f\u001f\b", new Object[]{Lit120}, 4);
    static final SyntaxTemplate Lit66 = new SyntaxTemplate("\u0001\u0001\u0000\u0001", "\u0012", new Object[0], 0);
    static final SyntaxPattern Lit67 = new SyntaxPattern("\f\u0007,\f\u0002\f\u000f\b\f\u0017\b", new Object[]{(SimpleSymbol) new SimpleSymbol("library").readResolve()}, 3);
    static final SyntaxTemplate Lit68 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make(Lit118, Pair.make(Pair.make(Lit104, Pair.make(Lit119, LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 458759)}, 0);
    static final SyntaxTemplate Lit69 = new SyntaxTemplate("\u0001\u0001\u0001", "\b\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit7 = new SyntaxTemplate("\u0001\u0001\u0000", "\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit70 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
    static final SyntaxTemplate Lit71 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0010", new Object[0], 0);
    static final SyntaxPattern Lit72 = new SyntaxPattern("\f\u0007\f\u000f\f\u0017\b", new Object[0], 3);
    static final SyntaxTemplate Lit73 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make(Lit118, Pair.make(Pair.make(Lit104, Pair.make(Lit119, LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 466951)}, 0);
    static final SyntaxTemplate Lit74 = new SyntaxTemplate("\u0001\u0001\u0001", "\b\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit75 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0013", new Object[0], 0);
    static final SyntaxTemplate Lit76 = new SyntaxTemplate("\u0001\u0001\u0001", "\u0010", new Object[0], 0);
    static final SimpleSymbol Lit77;
    static final SyntaxRules Lit78;
    static final SimpleSymbol Lit79;
    static final SyntaxTemplate Lit8 = new SyntaxTemplate("\u0001\u0001\u0000", "\u0012", new Object[0], 0);
    static final SyntaxRules Lit80;
    static final SimpleSymbol Lit81 = ((SimpleSymbol) new SimpleSymbol("case-lambda").readResolve());
    static final SyntaxPattern Lit82 = new SyntaxPattern("\f\u0007\u000b", new Object[0], 2);
    static final SyntaxTemplate Lit83 = new SyntaxTemplate("\u0001\u0000", "\u0018\u0004", new Object[]{PairWithPosition.make(Lit102, Pair.make((SimpleSymbol) new SimpleSymbol("gnu.expr.GenericProc").readResolve(), Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol) new SimpleSymbol("makeWithoutSorting").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 651273)}, 0);
    static final SyntaxTemplate Lit84 = new SyntaxTemplate("\u0001\u0000", "\n", new Object[0], 0);
    static final SyntaxPattern Lit85 = new SyntaxPattern("\u001c\f\u0017\u001b#", new Object[0], 5);
    static final SyntaxTemplate Lit86 = new SyntaxTemplate("\u0001\u0000\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0013\u001a", new Object[]{Lit109}, 0);
    static final SyntaxTemplate Lit87 = new SyntaxTemplate("\u0001\u0000\u0001\u0000\u0000", "\"", new Object[0], 0);
    static final SyntaxPattern Lit88 = new SyntaxPattern("\b", new Object[0], 2);
    static final SyntaxPattern Lit89 = new SyntaxPattern("\u0013", new Object[0], 3);
    static final SyntaxPattern Lit9 = new SyntaxPattern("\b", new Object[0], 0);
    static final SyntaxTemplate Lit90 = new SyntaxTemplate("\u0001\u0000\u0000", "\u0012", new Object[0], 0);
    static final SimpleSymbol Lit91;
    static final SyntaxRules Lit92;
    static final SimpleSymbol Lit93 = ((SimpleSymbol) new SimpleSymbol("%cond-expand").readResolve());
    static final SyntaxPattern Lit94 = new SyntaxPattern("\f\u0007\u001c\f\u000f\u0013\u001b", new Object[0], 4);
    static final SyntaxTemplate Lit95 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u000b", new Object[0], 0);
    static final SyntaxTemplate Lit96 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u0011\u0018\u0004\u0012", new Object[]{Lit110}, 0);
    static final SyntaxTemplate Lit97;
    static final SimpleSymbol Lit98;
    static final SyntaxRules Lit99;
    public static final Macro case$Mnlambda;
    public static final Macro cond$Mnexpand = Macro.make(Lit91, Lit92, $instance);
    public static final Macro define$Mnalias$Mnparameter = Macro.make(Lit100, Lit101, $instance);
    public static final Macro define$Mnmacro = Macro.make(Lit13, Lit14, $instance);
    public static final Macro define$Mnsyntax$Mncase = Macro.make(Lit15, Lit16, $instance);
    public static final Macro defmacro = Macro.make(Lit11, Lit12, $instance);
    public static final ModuleMethod identifier$Mnlist$Qu;
    public static final ModuleMethod identifier$Mnpair$Mnlist$Qu;
    public static final Macro f108import = Macro.make(Lit36, Lit37, $instance);
    public static final ModuleMethod import$Mnhandle$Mnexcept;
    public static final ModuleMethod import$Mnhandle$Mnonly;
    public static final ModuleMethod import$Mnhandle$Mnprefix;
    public static final ModuleMethod import$Mnhandle$Mnrename;
    public static final ModuleMethod import$Mnmapper;
    public static final Macro let$Mnvalues = Macro.make(Lit77, Lit78, $instance);
    public static final Macro let$St$Mnvalues = Macro.make(Lit79, Lit80, $instance);
    public static final Macro receive = Macro.make(Lit98, Lit99, $instance);
    public static final Macro f109synchronized;
    public static final Macro try$Mnfinally;
    public static final Macro unless = Macro.make(Lit19, Lit20, $instance);
    public static final Macro when = Macro.make(Lit17, Lit18, $instance);

    /* compiled from: syntax.scm */
    public class frame0 extends ModuleBody {
        TemplateScope $unnamed$0;
        Object[] $unnamed$1;

        public Object lambda6loop(Object clauses) {
            Object[] allocVars = SyntaxPattern.allocVars(5, this.$unnamed$1);
            if (syntax.Lit85.match(clauses, allocVars, 0)) {
                return Pair.make(syntax.Lit86.execute(allocVars, this.$unnamed$0), lambda6loop(syntax.Lit87.execute(allocVars, this.$unnamed$0)));
            }
            if (syntax.Lit88.match(clauses, allocVars, 0)) {
                return LList.Empty;
            }
            if (!syntax.Lit89.match(clauses, allocVars, 0)) {
                return syntax_case.error("syntax-case", clauses);
            }
            Object execute = syntax.Lit90.execute(allocVars, this.$unnamed$0);
            String str = "invalid case-lambda clause";
            if (str instanceof Object[]) {
                allocVars = (Object[]) str;
            } else {
                allocVars = new Object[]{str};
            }
            return LList.list1(prim_syntax.syntaxError(execute, allocVars));
        }
    }

    /* compiled from: syntax.scm */
    public class frame extends ModuleBody {
        final ModuleMethod lambda$Fn1;
        Object list;

        public frame() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm:83");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 1 ? lambda1(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda1(Object name) {
            Object l = this.list;
            while (true) {
                boolean x = name == null;
                Object l2;
                if (!x) {
                    if (lists.isNull(l)) {
                        break;
                    }
                    l2 = lists.cdr.apply1(l);
                    name = Scheme.applyToArgs.apply3(lists.caar.apply1(l), name, lists.cdar.apply1(l));
                    l = l2;
                } else if (x) {
                    break;
                } else {
                    l2 = lists.cdr.apply1(l);
                    name = Scheme.applyToArgs.apply3(lists.caar.apply1(l), name, lists.cdar.apply1(l));
                    l = l2;
                }
            }
            return name;
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
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("define-alias-parameter").readResolve();
        Lit100 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        r7 = new Object[21];
        r7[4] = PairWithPosition.make(Lit102, Pair.make(Lit103, Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol) new SimpleSymbol("makeNamed").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1069060);
        r7[5] = Lit108;
        r7[6] = PairWithPosition.make(Lit102, Pair.make(Lit103, Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol) new SimpleSymbol("pushConverter").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1073161);
        r7[7] = Lit109;
        r7[8] = PairWithPosition.make(Lit105, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1081354);
        r7[9] = (SimpleSymbol) new SimpleSymbol("try-catch").readResolve();
        r7[10] = Lit106;
        r7[11] = PairWithPosition.make(Lit105, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1089550);
        r7[12] = (SimpleSymbol) new SimpleSymbol("ex").readResolve();
        r7[13] = (SimpleSymbol) new SimpleSymbol("<java.lang.ClassCastException>").readResolve();
        r7[14] = Lit115;
        r7[15] = Lit107;
        r7[16] = PairWithPosition.make(Lit102, Pair.make((SimpleSymbol) new SimpleSymbol("gnu.mapping.WrongType").readResolve(), Pair.make(Pair.make(Lit104, Pair.make((SimpleSymbol) new SimpleSymbol("make").readResolve(), LList.Empty)), LList.Empty)), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1097748);
        r7[17] = PairWithPosition.make(PairWithPosition.make(Lit106, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("<int>").readResolve(), PairWithPosition.make(IntNum.make(1), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1101846), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1101840), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1101836), PairWithPosition.make(Lit105, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1101849), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1101836);
        r7[18] = (SimpleSymbol) new SimpleSymbol("set!").readResolve();
        r7[19] = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("field").readResolve(), PairWithPosition.make(Lit107, PairWithPosition.make(PairWithPosition.make(Lit108, PairWithPosition.make((SimpleSymbol) new SimpleSymbol("expectedType").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1105941), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1105941), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1105940), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1105937), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1105930);
        r7[20] = PairWithPosition.make(PairWithPosition.make((SimpleSymbol) new SimpleSymbol("primitive-throw").readResolve(), PairWithPosition.make(Lit107, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1110037), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1110020), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 1110020);
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\f\u0017\b", new Object[0], 3), "\u0001\u0001\u0001", "\u0011\u0018\u0004¹\u0011\u0018\f\t\u0003\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$)\u0011\u0018,\b\u0003\b\u0013\b\u0011\u00184\t\u0003\b\u0011\u0018<\u0011\u0018D\b\u0011\u0018L9\u0011\u0018T\t\u000b\u0018\\\b\u0011\u0018d\u0011\u0018l\b\u0011\u0018ty\b\u0011\u0018|\b\u0011\u0018\u0011\u0018d\t\u0003\u0018A\u0011\u0018\u0011\u0018\b\u000b\u0018¤", r7, 0);
        Lit101 = new SyntaxRules(objArr, syntaxRuleArr, 3);
        Object[] objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("receive").readResolve();
        Lit98 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr2 = new SyntaxRule[1];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u000b\b\u0011\u0018\f\t\u0003\b\u0015\u0013", new Object[]{Lit116, Lit109}, 1);
        Lit99 = new SyntaxRules(objArr2, syntaxRuleArr2, 3);
        r7 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("cond-expand").readResolve();
        Lit91 = simpleSymbol;
        r7[0] = simpleSymbol;
        Lit97 = new SyntaxTemplate("\u0001\u0001\u0000\u0000", "\u0011\u0018\u0004\u001a", r7, 0);
        objArr2 = new Object[]{Lit91, Lit112, Lit113, Lit114, Lit111};
        syntaxRuleArr2 = new SyntaxRule[8];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\b", new Object[0], 0), "", "\u0018\u0004", new Object[]{PairWithPosition.make((SimpleSymbol) new SimpleSymbol("%syntax-error").readResolve(), PairWithPosition.make("Unfulfilled cond-expand", LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 802851), "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 802836)}, 0);
        r7 = new Object[]{Lit110};
        syntaxRuleArr2[1] = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0002\r\u0007\u0000\b\b\b", new Object[]{Lit111}, 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0003", r7, 1);
        r7 = new Object[]{Lit110};
        syntaxRuleArr2[2] = new SyntaxRule(new SyntaxPattern("\f\u0018L\u001c\f\u0002\b\r\u0007\u0000\b\b\r\u000f\b\b\b", new Object[]{Lit112}, 2), "\u0003\u0003", "\u0011\u0018\u0004\b\u0005\u0003", r7, 1);
        r7 = new Object[]{Lit91, Lit112};
        syntaxRuleArr2[3] = new SyntaxRule(new SyntaxPattern("\f\u0018|L\f\u0002\f\u0007\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit112}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004¡\t\u0003\b\u0011\u0018\u0004Q1\u0011\u0018\f\b\r\u000b\b\u0015\u0013\b\u001d\u001b\b\u001d\u001b", r7, 1);
        r7 = new Object[]{Lit91};
        syntaxRuleArr2[4] = new SyntaxRule(new SyntaxPattern("\f\u0018L\u001c\f\u0002\b\r\u0007\u0000\b\b\r\u000f\b\b\b", new Object[]{Lit113}, 2), "\u0003\u0003", "\u0011\u0018\u0004\b\r\u000b", r7, 1);
        r7 = new Object[]{Lit91, Lit110, Lit111, Lit113};
        syntaxRuleArr2[5] = new SyntaxRule(new SyntaxPattern("\f\u0018|L\f\u0002\f\u0007\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit113}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004I\t\u0003\b\u0011\u0018\f\b\u0015\u0013\b\u0011\u0018\u0014\b\u0011\u0018\u0004Q1\u0011\u0018\u001c\b\r\u000b\b\u0015\u0013\b\u001d\u001b", r7, 1);
        r7 = new Object[]{Lit91, Lit111};
        syntaxRuleArr2[6] = new SyntaxRule(new SyntaxPattern("\f\u0018\\,\f\u0002\f\u0007\b\r\u000f\b\b\b\r\u0017\u0010\b\b", new Object[]{Lit114}, 3), "\u0001\u0003\u0003", "\u0011\u0018\u0004I\t\u0003\b\u0011\u0018\u0004\b\u0015\u0013\b\u0011\u0018\f\b\r\u000b", r7, 1);
        syntaxRuleArr2[7] = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\u0019\t\u0003\n\u0012", new Object[]{Lit93}, 0);
        Lit92 = new SyntaxRules(objArr2, syntaxRuleArr2, 4);
        objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("let*-values").readResolve();
        Lit79 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        syntaxRuleArr2 = new SyntaxRule[2];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\b\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\r\u000b", new Object[]{Lit110}, 1);
        r7 = new Object[2];
        SimpleSymbol simpleSymbol2 = (SimpleSymbol) new SimpleSymbol("let-values").readResolve();
        Lit77 = simpleSymbol2;
        r7[0] = simpleSymbol2;
        r7[1] = Lit79;
        syntaxRuleArr2[1] = new SyntaxRule(new SyntaxPattern("\f\u0018<\f\u0007\r\u000f\b\b\b\f\u0017\r\u001f\u0018\b\b", new Object[0], 4), "\u0001\u0003\u0001\u0003", "\u0011\u0018\u0004\u0011\b\u0003\b\u0011\u0018\f\u0019\b\r\u000b\t\u0013\b\u001d\u001b", r7, 1);
        Lit80 = new SyntaxRules(objArr2, syntaxRuleArr2, 4);
        objArr2 = new Object[]{Lit77};
        syntaxRuleArr2 = new SyntaxRule[6];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018,\r\u0007\u0000\b\b\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0003\u0001\u0003", "\u0011\u0018\u0004\u0011\u0018\f\u0019\b\u0005\u0003\t\u0010\b\u0011\u0018\u0014\t\u000b\b\u0015\u0013", new Object[]{Lit77, "bind", Lit110}, 1);
        r7 = new Object[]{Lit115};
        syntaxRuleArr2[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\b\f\u0007\f\u000f\b", new Object[]{"bind"}, 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", r7, 0);
        r7 = new Object[]{Lit77, "mktmp"};
        syntaxRuleArr2[2] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\\,\f\u0007\f\u000f\b\r\u0017\u0010\b\b\f\u001f\f'\b", new Object[]{"bind"}, 5), "\u0001\u0001\u0003\u0001\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\u0003\t\u000b\t\u0010\u0019\b\u0015\u0013\t\u001b\b#", r7, 1);
        r7 = new Object[]{Lit116, Lit109, Lit77, "bind"};
        syntaxRuleArr2[3] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\b\f\u0007\f\u000f\f\u0017\f\u001f\f'\b", new Object[]{"mktmp"}, 5), "\u0001\u0001\u0001\u0001\u0001", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u0003\b\u0011\u0018\f\t\u000b\b\u0011\u0018\u0014\u0011\u0018\u001c\t\u0013\t\u001b\b#", r7, 0);
        r7 = new Object[]{Lit77, "mktmp", PairWithPosition.make(Lit117, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 569387), PairWithPosition.make(Lit117, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 569414)};
        syntaxRuleArr2[4] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\u001c\f\u0007\u000b\f\u0017,\r\u001f\u0018\b\b\f',\r/(\b\b\f7\b", new Object[]{"mktmp"}, 7), "\u0001\u0000\u0001\u0003\u0001\u0003\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\n\t\u0013)\u0011\u001d\u001b\u0018\u0014\t#A\u0011-+\b\t\u0003\u0018\u001c\b3", r7, 1);
        r7 = new Object[]{Lit116, Lit109, Lit117, Lit77, "bind", PairWithPosition.make(Lit117, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 593973)};
        syntaxRuleArr2[5] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0002\f\u0007\f\u000f,\r\u0017\u0010\b\b\f\u001f,\r' \b\b\f/\b", new Object[]{"mktmp"}, 6), "\u0001\u0001\u0003\u0001\u0003\u0001", "\u0011\u0018\u00049\u0011\u0018\f\t\u0010\b\u000b\b\u0011\u0018\f)\u0011\u0015\u0013\u0018\u0014\b\u0011\u0018\u001c\u0011\u0018$\t\u001bA\u0011%#\b\t\u0003\u0018,\b+", r7, 1);
        Lit78 = new SyntaxRules(objArr2, syntaxRuleArr2, 7);
        r7 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("%import").readResolve();
        Lit38 = simpleSymbol;
        r7[0] = simpleSymbol;
        Lit61 = new SyntaxTemplate("\u0001\u0001\u0001\u0001", "\u0011\u0018\u0004\b\u000b", r7, 0);
        objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("import").readResolve();
        Lit36 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        syntaxRuleArr2 = new SyntaxRule[1];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\r\u0007\u0000\b\b", new Object[0], 1), "\u0003", "\u0011\u0018\u0004\b\u0005\u0011\u0018\f\t\u0003\u0018\u0014", new Object[]{Lit110, Lit38, PairWithPosition.make(LList.Empty, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 376866)}, 1);
        Lit37 = new SyntaxRules(objArr2, syntaxRuleArr2, 1);
        objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("when").readResolve();
        Lit17 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        syntaxRuleArr2 = new SyntaxRule[1];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004)\u0011\u0018\f\b\u0003\b\u0011\u0018\u0014\b\r\u000b", new Object[]{Lit121, Lit114, Lit110}, 1);
        Lit20 = new SyntaxRules(objArr2, syntaxRuleArr2, 2);
        objArr2 = new Object[]{Lit17};
        syntaxRuleArr2 = new SyntaxRule[1];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\r\u000f\b\b\b", new Object[0], 2), "\u0001\u0003", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\b\r\u000b", new Object[]{Lit121, Lit110}, 1);
        Lit18 = new SyntaxRules(objArr2, syntaxRuleArr2, 2);
        objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("define-syntax-case").readResolve();
        Lit15 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        syntaxRuleArr2 = new SyntaxRule[1];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\u0013", new Object[0], 3), "\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\u0011\u0018\u0014\b\u0011\u0018\u001c\u0011\u0018$\t\u000b\u0012", new Object[]{(SimpleSymbol) new SimpleSymbol("define-syntax").readResolve(), Lit109, PairWithPosition.make(Lit122, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm", 90129), (SimpleSymbol) new SimpleSymbol("syntax-case").readResolve(), Lit122}, 0);
        Lit16 = new SyntaxRules(objArr2, syntaxRuleArr2, 3);
        objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("define-macro").readResolve();
        Lit13 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        syntaxRuleArr2 = new SyntaxRule[2];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\u001c\f\u0007\u000b\u0013", new Object[0], 3), "\u0001\u0000\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\n\u0012", new Object[]{Lit123, Lit109}, 0);
        syntaxRuleArr2[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\b", new Object[0], 2), "\u0001\u0001", "\u0011\u0018\u0004\t\u0003\b\u000b", new Object[]{Lit123}, 0);
        Lit14 = new SyntaxRules(objArr2, syntaxRuleArr2, 3);
        objArr2 = new Object[1];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("defmacro").readResolve();
        Lit11 = simpleSymbol;
        objArr2[0] = simpleSymbol;
        syntaxRuleArr2 = new SyntaxRule[1];
        syntaxRuleArr2[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\u0013", new Object[0], 3), "\u0001\u0001\u0000", "\u0011\u0018\u0004\t\u0003\b\u0011\u0018\f\t\u000b\u0012", new Object[]{Lit123, Lit109}, 0);
        Lit12 = new SyntaxRules(objArr2, syntaxRuleArr2, 3);
        simpleSymbol = Lit21;
        ModuleBody moduleBody = $instance;
        try$Mnfinally = Macro.make(simpleSymbol, new ModuleMethod(moduleBody, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        f109synchronized = Macro.make(Lit25, new ModuleMethod(moduleBody, 3, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        identifier$Mnlist$Qu = new ModuleMethod(moduleBody, 4, Lit29, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        identifier$Mnpair$Mnlist$Qu = new ModuleMethod(moduleBody, 5, Lit30, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        import$Mnhandle$Mnonly = new ModuleMethod(moduleBody, 6, Lit31, 8194);
        import$Mnhandle$Mnexcept = new ModuleMethod(moduleBody, 7, Lit32, 8194);
        import$Mnhandle$Mnprefix = new ModuleMethod(moduleBody, 8, Lit33, 8194);
        import$Mnhandle$Mnrename = new ModuleMethod(moduleBody, 9, Lit34, 8194);
        import$Mnmapper = new ModuleMethod(moduleBody, 10, Lit35, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $Pcimport = Macro.make(Lit38, new ModuleMethod(moduleBody, 11, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        case$Mnlambda = Macro.make(Lit81, new ModuleMethod(moduleBody, 12, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN), $instance);
        simpleSymbol = Lit93;
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 13, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/kawa/lib/syntax.scm:227");
        $Pccond$Mnexpand = Macro.make(simpleSymbol, moduleMethod, $instance);
        $instance.run();
    }

    public syntax() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static Object lambda2(Object x) {
        Object[] allocVars = SyntaxPattern.allocVars(3, null);
        if (!Lit22.match(x, allocVars, 0)) {
            return syntax_case.error("syntax-case", x);
        }
        return new TryExp(SyntaxForms.rewrite(Lit23.execute(allocVars, TemplateScope.make())), SyntaxForms.rewrite(Lit24.execute(allocVars, TemplateScope.make())));
    }

    static Object lambda3(Object x) {
        Object[] allocVars = SyntaxPattern.allocVars(3, null);
        if (!Lit26.match(x, allocVars, 0)) {
            return syntax_case.error("syntax-case", x);
        }
        return new SynchronizedExp(SyntaxForms.rewrite(Lit27.execute(allocVars, TemplateScope.make())), SyntaxForms.rewriteBody(Lit28.execute(allocVars, TemplateScope.make())));
    }

    public static boolean isIdentifierList(Object obj) {
        boolean x;
        if (Translator.listLength(obj) >= 0) {
            x = true;
        } else {
            x = false;
        }
        if (!x) {
            return x;
        }
        while (true) {
            Object[] allocVars = SyntaxPattern.allocVars(2, null);
            if (!Lit0.match(obj, allocVars, 0)) {
                break;
            }
            x = std_syntax.isIdentifier(Lit1.execute(allocVars, TemplateScope.make()));
            if (!x) {
                return x;
            }
            obj = Lit2.execute(allocVars, TemplateScope.make());
        }
        if (Lit3.match(obj, allocVars, 0)) {
            return true;
        }
        if (Lit4.match(obj, allocVars, 0)) {
            return false;
        }
        return syntax_case.error("syntax-case", obj) != Boolean.FALSE;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
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
            case 10:
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

    public static boolean isIdentifierPairList(Object obj) {
        boolean x;
        if (Translator.listLength(obj) >= 0) {
            x = true;
        } else {
            x = false;
        }
        if (!x) {
            return x;
        }
        while (true) {
            Object[] allocVars = SyntaxPattern.allocVars(3, null);
            if (!Lit5.match(obj, allocVars, 0)) {
                break;
            }
            x = std_syntax.isIdentifier(Lit6.execute(allocVars, TemplateScope.make()));
            if (!x) {
                return x;
            }
            x = std_syntax.isIdentifier(Lit7.execute(allocVars, TemplateScope.make()));
            if (!x) {
                return x;
            }
            obj = Lit8.execute(allocVars, TemplateScope.make());
        }
        if (Lit9.match(obj, allocVars, 0)) {
            return true;
        }
        if (Lit10.match(obj, allocVars, 0)) {
            return false;
        }
        return syntax_case.error("syntax-case", obj) != Boolean.FALSE;
    }

    public static Object importHandleOnly(Object name, Object list) {
        return lists.memq(name, list) != Boolean.FALSE ? name : null;
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 6:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 7:
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
            case 9:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static Object importHandleExcept(Object name, Object list) {
        return lists.memq(name, list) != Boolean.FALSE ? null : name;
    }

    public static Object importHandlePrefix(Object name, Object prefix) {
        return name == null ? null : null;
    }

    public static Object importHandleRename(Object name, Object rename$Mnpairs) {
        if (!lists.isPair(rename$Mnpairs)) {
            return name;
        }
        if (name == lists.caar.apply1(rename$Mnpairs)) {
            return lists.cadar.apply1(rename$Mnpairs);
        }
        return importHandleRename(name, lists.cdr.apply1(rename$Mnpairs));
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 6:
                return importHandleOnly(obj, obj2);
            case 7:
                return importHandleExcept(obj, obj2);
            case 8:
                return importHandlePrefix(obj, obj2);
            case 9:
                return importHandleRename(obj, obj2);
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static Procedure importMapper(Object list) {
        frame kawa_lib_syntax_frame = new frame();
        kawa_lib_syntax_frame.list = list;
        return kawa_lib_syntax_frame.lambda$Fn1;
    }

    static Object lambda4(Object form) {
        Object[] allocVars = SyntaxPattern.allocVars(4, null);
        Object[] objArr;
        TemplateScope make;
        Object execute;
        String str;
        if (Lit39.match(form, allocVars, 0)) {
            if (isIdentifierPairList(Lit40.execute(allocVars, TemplateScope.make()))) {
                objArr = new Object[2];
                objArr[0] = Lit41.execute(allocVars, TemplateScope.make());
                objArr[1] = Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnrename, Lit42.execute(allocVars, make)), Lit43.execute(allocVars, make)), Lit44.execute(allocVars, make)});
                return Quote.append$V(objArr);
            }
            execute = Lit45.execute(allocVars, TemplateScope.make());
            str = "invalid 'rename' clause in import";
            if (str instanceof Object[]) {
                allocVars = (Object[]) str;
            } else {
                allocVars = new Object[]{str};
            }
            return prim_syntax.syntaxError(execute, allocVars);
        } else if (Lit46.match(form, allocVars, 0)) {
            if (isIdentifierList(Lit47.execute(allocVars, TemplateScope.make()))) {
                objArr = new Object[2];
                objArr[0] = Lit48.execute(allocVars, TemplateScope.make());
                objArr[1] = Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnonly, Lit49.execute(allocVars, make)), Lit50.execute(allocVars, make)), Lit51.execute(allocVars, make)});
                return Quote.append$V(objArr);
            }
            execute = Lit52.execute(allocVars, TemplateScope.make());
            str = "invalid 'only' identifier list";
            if (str instanceof Object[]) {
                allocVars = (Object[]) str;
            } else {
                allocVars = new Object[]{str};
            }
            return prim_syntax.syntaxError(execute, allocVars);
        } else if (Lit53.match(form, allocVars, 0)) {
            if (isIdentifierList(Lit54.execute(allocVars, TemplateScope.make()))) {
                objArr = new Object[2];
                objArr[0] = Lit55.execute(allocVars, TemplateScope.make());
                objArr[1] = Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnexcept, Lit56.execute(allocVars, make)), Lit57.execute(allocVars, make)), Lit58.execute(allocVars, make)});
                return Quote.append$V(objArr);
            }
            execute = Lit59.execute(allocVars, TemplateScope.make());
            str = "invalid 'except' identifier list";
            if (str instanceof Object[]) {
                allocVars = (Object[]) str;
            } else {
                allocVars = new Object[]{str};
            }
            return prim_syntax.syntaxError(execute, allocVars);
        } else if (Lit60.match(form, allocVars, 0)) {
            objArr = new Object[2];
            objArr[0] = Lit61.execute(allocVars, TemplateScope.make());
            objArr[1] = Quote.consX$V(new Object[]{lists.cons(lists.cons(import$Mnhandle$Mnprefix, Lit62.execute(allocVars, make)), Lit63.execute(allocVars, make)), Lit64.execute(allocVars, make)});
            return Quote.append$V(objArr);
        } else if (Lit65.match(form, allocVars, 0)) {
            execute = Lit66.execute(allocVars, TemplateScope.make());
            str = "invalid prefix clause in import";
            if (str instanceof Object[]) {
                allocVars = (Object[]) str;
            } else {
                allocVars = new Object[]{str};
            }
            return prim_syntax.syntaxError(execute, allocVars);
        } else if (Lit67.match(form, allocVars, 0)) {
            make = TemplateScope.make();
            execute = Lit68.execute(allocVars, make);
            r3 = new Object[2];
            r3[0] = Lit69.execute(allocVars, make);
            r3[1] = Quote.consX$V(new Object[]{importMapper(std_syntax.syntaxObject$To$Datum(Lit70.execute(allocVars, make))), Lit71.execute(allocVars, make)});
            return Pair.make(execute, Quote.append$V(r3));
        } else if (!Lit72.match(form, allocVars, 0)) {
            return syntax_case.error("syntax-case", form);
        } else {
            make = TemplateScope.make();
            execute = Lit73.execute(allocVars, make);
            r3 = new Object[2];
            r3[0] = Lit74.execute(allocVars, make);
            r3[1] = Quote.consX$V(new Object[]{importMapper(std_syntax.syntaxObject$To$Datum(Lit75.execute(allocVars, make))), Lit76.execute(allocVars, make)});
            return Pair.make(execute, Quote.append$V(r3));
        }
    }

    static Object lambda5(Object form) {
        frame0 kawa_lib_syntax_frame0 = new frame0();
        kawa_lib_syntax_frame0.$unnamed$1 = SyntaxPattern.allocVars(2, null);
        if (!Lit82.match(form, kawa_lib_syntax_frame0.$unnamed$1, 0)) {
            return syntax_case.error("syntax-case", form);
        }
        kawa_lib_syntax_frame0.$unnamed$0 = TemplateScope.make();
        return Pair.make(Lit83.execute(kawa_lib_syntax_frame0.$unnamed$1, kawa_lib_syntax_frame0.$unnamed$0), kawa_lib_syntax_frame0.lambda6loop(Lit84.execute(kawa_lib_syntax_frame0.$unnamed$1, kawa_lib_syntax_frame0.$unnamed$0)));
    }

    static Object lambda7(Object x) {
        Object[] allocVars = SyntaxPattern.allocVars(4, null);
        if (!Lit94.match(x, allocVars, 0)) {
            return syntax_case.error("syntax-case", x);
        }
        if (IfFeature.testFeature(Lit95.execute(allocVars, TemplateScope.make()))) {
            return Lit96.execute(allocVars, TemplateScope.make());
        }
        return Lit97.execute(allocVars, TemplateScope.make());
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 2:
                return lambda2(obj);
            case 3:
                return lambda3(obj);
            case 4:
                return isIdentifierList(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 5:
                return isIdentifierPairList(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 10:
                return importMapper(obj);
            case 11:
                return lambda4(obj);
            case 12:
                return lambda5(obj);
            case 13:
                return lambda7(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}

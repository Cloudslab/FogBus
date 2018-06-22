package gnu.kawa.slib;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.IfExp;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.SetExp;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.Convert;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.Consumer;
import gnu.lists.EofClass;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.text.Char;
import gnu.text.SourceMessages;
import kawa.lang.Macro;
import kawa.lang.Quote;
import kawa.lang.SyntaxPattern;
import kawa.lang.SyntaxRule;
import kawa.lang.SyntaxRules;
import kawa.lang.Translator;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.Scheme;

/* compiled from: syntaxutils.scm */
public class syntaxutils extends ModuleBody {
    public static final Macro $Prvt$$Ex = Macro.make(Lit15, Lit16, $instance);
    public static final Macro $Prvt$typecase$Pc = Macro.make(Lit13, Lit14, $instance);
    public static final syntaxutils $instance = new syntaxutils();
    static final Keyword Lit0 = Keyword.make("env");
    static final PairWithPosition Lit1 = PairWithPosition.make(Lit21, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 278557);
    static final PairWithPosition Lit10 = PairWithPosition.make(Lit19, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 552972);
    static final PairWithPosition Lit11 = PairWithPosition.make(Lit25, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 626704);
    static final PairWithPosition Lit12 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol(":").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 634896);
    static final SimpleSymbol Lit13;
    static final SyntaxRules Lit14;
    static final SimpleSymbol Lit15;
    static final SyntaxRules Lit16;
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("expand").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("eql").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol(LispLanguage.quote_sym).readResolve());
    static final Keyword Lit2 = Keyword.make("lang");
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("or").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("begin").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("cond").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("let").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("else").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("as").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("lambda").readResolve());
    static final PairWithPosition Lit3 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("set").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 368647);
    static final PairWithPosition Lit4 = PairWithPosition.make(Lit26, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 376839);
    static final PairWithPosition Lit5 = PairWithPosition.make(Lit21, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 409627);
    static final PairWithPosition Lit6 = PairWithPosition.make((SimpleSymbol) new SimpleSymbol("if").readResolve(), LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 417799);
    static final IntNum Lit7 = IntNum.make(0);
    static final IntNum Lit8 = IntNum.make(1);
    static final PairWithPosition Lit9 = PairWithPosition.make(Lit23, LList.Empty, "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/syntaxutils.scm", 479236);
    public static final ModuleMethod expand = new ModuleMethod($instance, 1, Lit17, -4095);

    /* compiled from: syntaxutils.scm */
    public class frame0 extends ModuleBody {
        LList pack;
    }

    /* compiled from: syntaxutils.scm */
    public class frame1 extends ModuleBody {
        LList pack;
    }

    /* compiled from: syntaxutils.scm */
    public class frame extends ModuleBody {
        LList pack;
    }

    static {
        Object[] objArr = new Object[1];
        SimpleSymbol simpleSymbol = (SimpleSymbol) new SimpleSymbol("!").readResolve();
        Lit15 = simpleSymbol;
        objArr[0] = simpleSymbol;
        SyntaxRule[] syntaxRuleArr = new SyntaxRule[1];
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\f\u000f\r\u0017\u0010\b\b", new Object[0], 3), "\u0001\u0001\u0003", "\u0011\u0018\u0004\t\u000b)\u0011\u0018\f\b\u0003\b\u0015\u0013", new Object[]{(SimpleSymbol) new SimpleSymbol("invoke").readResolve(), Lit19}, 1);
        Lit16 = new SyntaxRules(objArr, syntaxRuleArr, 3);
        objArr = new Object[3];
        simpleSymbol = (SimpleSymbol) new SimpleSymbol("typecase%").readResolve();
        Lit13 = simpleSymbol;
        objArr[0] = simpleSymbol;
        objArr[1] = Lit18;
        objArr[2] = Lit20;
        syntaxRuleArr = new SyntaxRule[6];
        Object[] objArr2 = new Object[]{Lit21};
        syntaxRuleArr[0] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u0002\r\u000f\b\b\b\r\u0017\u0010\b\b", new Object[]{Boolean.TRUE}, 3), "\u0001\u0003\u0003", "\u0011\u0018\u0004\b\r\u000b", objArr2, 1);
        objArr2 = new Object[]{Lit22, (SimpleSymbol) new SimpleSymbol("eqv?").readResolve(), Lit19, Lit24, Lit13};
        syntaxRuleArr[1] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\\,\f\u0002\f\u000f\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit18}, 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004yY\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014\b\u000b\b\u0015\u0013\b\u0011\u0018\u001c\b\u0011\u0018$\t\u0003\b\u001d\u001b", objArr2, 1);
        objArr2 = new Object[]{Lit13};
        syntaxRuleArr[2] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\\,\f\u0002\f\u000f\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit20}, 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004\t\u0003)\t\u000b\b\u0015\u0013\b\u001d\u001b", objArr2, 1);
        objArr2 = new Object[]{Lit23, (SimpleSymbol) new SimpleSymbol("f").readResolve(), Lit26, Lit21, Lit13, Boolean.TRUE};
        syntaxRuleArr[3] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007l<\f\u0002\r\u000f\b\b\b\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[]{Lit20}, 4), "\u0001\u0003\u0003\u0003", "\u0011\u0018\u0004\b\u0011\u0018\f\b\u0011\u0018\u0014\u0011\b\u0003\b\u0011\u0018\u001c\b\u0015\u0013\b\u0011\u0018$\t\u0003I\r\t\u000b\b\u0011\u0018\f\b\u0003\b\u0011\u0018,\b\u0011\u0018$\t\u0003\b\u001d\u001b", objArr2, 1);
        syntaxRuleArr[4] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007<\f\u000f\r\u0017\u0010\b\b\r\u001f\u0018\b\b", new Object[0], 4), "\u0001\u0001\u0003\u0003", "\u0011\u0018\u0004ñ9\u0011\u0018\f\t\u0003\b\u000b\b\u0011\u0018\u0014Q\b\t\u0003\u0011\u0018\u001c\t\u000b\b\u0003\b\u0011\u0018$\b\u0015\u0013\b\u0011\u0018,\b\u0011\u00184\t\u0003\b\u001d\u001b", new Object[]{Lit22, (SimpleSymbol) new SimpleSymbol(GetNamedPart.INSTANCEOF_METHOD_NAME).readResolve(), Lit23, (SimpleSymbol) new SimpleSymbol("::").readResolve(), Lit21, Lit24, Lit13}, 1);
        syntaxRuleArr[5] = new SyntaxRule(new SyntaxPattern("\f\u0018\f\u0007\b", new Object[0], 1), "\u0001", "\u0011\u0018\u0004\u0011\u0018\f\t\u0003\b\u0011\u0018\u0014\u0011\u0018\u001c\b\u0011\u0018$\u0011\u0018,\b\u0003", new Object[]{(SimpleSymbol) new SimpleSymbol("error").readResolve(), "typecase% failed", Lit15, (SimpleSymbol) new SimpleSymbol("getClass").readResolve(), Lit25, (SimpleSymbol) new SimpleSymbol("<object>").readResolve()}, 0);
        Lit14 = new SyntaxRules(objArr, syntaxRuleArr, 4);
        $instance.run();
    }

    public syntaxutils() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object expand$V(Object sexp, Object[] argsArray) {
        Object env = Keyword.searchForKeyword(argsArray, 0, Lit0);
        if (env == Special.dfault) {
            env = misc.interactionEnvironment();
        }
        Object[] objArr = new Object[2];
        objArr[0] = Lit1;
        objArr[1] = Quote.consX$V(new Object[]{sexp, LList.Empty});
        return unrewrite(rewriteForm$V(Quote.append$V(objArr), new Object[]{Lit0, env}));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        if (moduleMethod.selector != 1) {
            return super.applyN(moduleMethod, objArr);
        }
        Object obj = objArr[0];
        int length = objArr.length - 1;
        Object[] objArr2 = new Object[length];
        while (true) {
            length--;
            if (length < 0) {
                return expand$V(obj, objArr2);
            }
            objArr2[length] = objArr[length + 1];
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        if (moduleMethod.selector != 1) {
            return super.matchN(moduleMethod, objArr, callContext);
        }
        callContext.values = objArr;
        callContext.proc = moduleMethod;
        callContext.pc = 5;
        return 0;
    }

    static Expression rewriteForm$V(Object exp, Object[] argsArray) {
        Object lang = Keyword.searchForKeyword(argsArray, 0, Lit2);
        if (lang == Special.dfault) {
            lang = Language.getDefaultLanguage();
        }
        Object env = Keyword.searchForKeyword(argsArray, 0, Lit0);
        if (env == Special.dfault) {
            env = misc.interactionEnvironment();
        }
        try {
            try {
                try {
                    Translator translator = new Translator((Language) lang, new SourceMessages(), NameLookup.getInstance((Environment) env, (Language) lang));
                    translator.pushNewModule(null);
                    Compilation saved$Mncomp = Compilation.setSaveCurrent(translator);
                    try {
                        Expression rewrite = translator.rewrite(exp);
                        return rewrite;
                    } finally {
                        Compilation.restoreCurrent(saved$Mncomp);
                    }
                } catch (ClassCastException e) {
                    throw new WrongType(e, "kawa.lang.Translator.<init>(gnu.expr.Language,gnu.text.SourceMessages,gnu.expr.NameLookup)", 1, lang);
                }
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "gnu.expr.NameLookup.getInstance(gnu.mapping.Environment,gnu.expr.Language)", 2, lang);
            }
        } catch (ClassCastException e22) {
            throw new WrongType(e22, "gnu.expr.NameLookup.getInstance(gnu.mapping.Environment,gnu.expr.Language)", 1, env);
        }
    }

    static Object unrewrite(Expression exp) {
        frame closureEnv = new frame();
        if (exp instanceof LetExp) {
            try {
                return unrewriteLet((LetExp) exp);
            } catch (ClassCastException e) {
                throw new WrongType(e, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof QuoteExp) {
            try {
                return unrewriteQuote((QuoteExp) exp);
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof SetExp) {
            try {
                SetExp exp2 = (SetExp) exp;
                r4 = new Object[2];
                r4[0] = Lit3;
                r5 = new Object[2];
                r5[0] = exp2.getSymbol();
                r5[1] = Quote.consX$V(new Object[]{unrewrite(exp2.getNewValue()), LList.Empty});
                r4[1] = Quote.consX$V(r5);
                return Quote.append$V(r4);
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof LambdaExp) {
            try {
                LambdaExp exp3 = (LambdaExp) exp;
                r4 = new Object[2];
                r4[0] = Lit4;
                r5 = new Object[2];
                closureEnv.pack = LList.Empty;
                for (Declaration decl = exp3.firstDecl(); decl != null; decl = decl.nextDecl()) {
                    closureEnv.pack = lists.cons(decl.getSymbol(), closureEnv.pack);
                }
                r5[0] = lists.reverse$Ex(closureEnv.pack);
                r5[1] = Quote.consX$V(new Object[]{unrewrite(exp3.body), LList.Empty});
                r4[1] = Quote.consX$V(r5);
                return Quote.append$V(r4);
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof ReferenceExp) {
            try {
                return ((ReferenceExp) exp).getSymbol();
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof ApplyExp) {
            try {
                return unrewriteApply((ApplyExp) exp);
            } catch (ClassCastException e22222) {
                throw new WrongType(e22222, "exp", -2, (Object) exp);
            }
        } else if (exp instanceof BeginExp) {
            try {
                BeginExp exp4 = (BeginExp) exp;
                return Quote.append$V(new Object[]{Lit5, unrewrite$St(exp4.getExpressions())});
            } catch (ClassCastException e222222) {
                throw new WrongType(e222222, "exp", -2, (Object) exp);
            }
        } else if (!(exp instanceof IfExp)) {
            return exp;
        } else {
            try {
                IfExp exp5 = (IfExp) exp;
                r5 = new Object[2];
                r5[0] = Lit6;
                Object[] objArr = new Object[2];
                objArr[0] = unrewrite(exp5.getTest());
                Object[] objArr2 = new Object[2];
                objArr2[0] = unrewrite(exp5.getThenClause());
                Object[] objArr3 = new Object[2];
                Expression eclause = exp5.getElseClause();
                objArr3[0] = eclause == null ? LList.Empty : LList.list1(unrewrite(eclause));
                objArr3[1] = LList.Empty;
                objArr2[1] = Quote.append$V(objArr3);
                objArr[1] = Quote.consX$V(objArr2);
                r5[1] = Quote.consX$V(objArr);
                return Quote.append$V(r5);
            } catch (ClassCastException e2222222) {
                throw new WrongType(e2222222, "exp", -2, (Object) exp);
            }
        }
    }

    static Object unrewrite$St(Expression[] exps) {
        frame0 closureEnv = new frame0();
        closureEnv.pack = LList.Empty;
        Integer len = Integer.valueOf(exps.length);
        for (Object obj = Lit7; Scheme.numEqu.apply2(obj, len) == Boolean.FALSE; obj = AddOp.$Pl.apply2(obj, Lit8)) {
            closureEnv.pack = lists.cons(unrewrite(exps[((Number) obj).intValue()]), closureEnv.pack);
        }
        return lists.reverse$Ex(closureEnv.pack);
    }

    static Object unrewriteLet(LetExp exp) {
        frame1 closureEnv = new frame1();
        Object[] objArr = new Object[2];
        objArr[0] = Lit9;
        Object[] objArr2 = new Object[2];
        closureEnv.pack = LList.Empty;
        Declaration decl = exp.firstDecl();
        Object obj = Lit7;
        while (decl != null) {
            closureEnv.pack = lists.cons(LList.list2(decl.getSymbol(), unrewrite(exp.inits[((Number) obj).intValue()])), closureEnv.pack);
            decl = decl.nextDecl();
            obj = AddOp.$Pl.apply2(obj, Lit8);
        }
        objArr2[0] = lists.reverse$Ex(closureEnv.pack);
        objArr2[1] = Quote.consX$V(new Object[]{unrewrite(exp.body), LList.Empty});
        objArr[1] = Quote.consX$V(objArr2);
        return Quote.append$V(objArr);
    }

    static Object unrewriteQuote(QuoteExp exp) {
        Object val = exp.getValue();
        if (Numeric.asNumericOrNull(val) != null) {
            try {
                return LangObjType.coerceNumeric(val);
            } catch (ClassCastException e) {
                throw new WrongType(e, "val", -2, val);
            }
        } else if (val instanceof Boolean) {
            try {
                return (val != Boolean.FALSE ? 1 : null) != null ? Boolean.TRUE : Boolean.FALSE;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "val", -2, val);
            }
        } else if (val instanceof Char) {
            try {
                return (Char) val;
            } catch (ClassCastException e22) {
                throw new WrongType(e22, "val", -2, val);
            }
        } else if (val instanceof Keyword) {
            try {
                return (Keyword) val;
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "val", -2, val);
            }
        } else if (val instanceof CharSequence) {
            try {
                return (CharSequence) val;
            } catch (ClassCastException e2222) {
                throw new WrongType(e2222, "val", -2, val);
            }
        } else if (val == Special.undefined || val == EofClass.eofValue) {
            return val;
        } else {
            String name;
            if (val instanceof Type) {
                try {
                    name = ((Type) val).getName();
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "val", -2, val);
                }
            } else if (val instanceof Class) {
                try {
                    name = ((Class) val).getName();
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "val", -2, val);
                }
            } else {
                Object[] objArr = new Object[2];
                objArr[0] = Lit10;
                objArr[1] = Quote.consX$V(new Object[]{val, LList.Empty});
                return Quote.append$V(objArr);
            }
            return misc.string$To$Symbol(Format.formatToString(0, "<~a>", name));
        }
    }

    static Object unrewriteApply(ApplyExp exp) {
        int i;
        Object fun = exp.getFunction();
        Object args = unrewrite$St(exp.getArgs());
        if (fun instanceof ReferenceExp) {
            try {
                Declaration fbinding = ((ReferenceExp) fun).getBinding();
            } catch (ClassCastException e) {
                throw new WrongType(e, "fun", -2, fun);
            }
        }
        fbinding = null;
        Declaration apply$Mnto$Mnargs = Declaration.getDeclarationFromStatic("kawa.standard.Scheme", "applyToArgs");
        Object fval = exp.getFunctionValue();
        if (fbinding == null) {
            i = 1;
        } else {
            i = 0;
        }
        boolean x = (i + 1) & 1;
        if (x) {
            if (apply$Mnto$Mnargs == null) {
                i = 1;
            } else {
                i = 0;
            }
            x = (i + 1) & 1;
            if (x) {
                if (SlotGet.getSlotValue(false, fbinding, "field", "field", "getField", "isField", Scheme.instance) == apply$Mnto$Mnargs.field) {
                    return args;
                }
            } else if (x) {
                return args;
            }
        } else if (x) {
            return args;
        }
        if (fval instanceof Convert) {
            x = Quote.append$V(new Object[]{Lit11, args});
        } else if (fval instanceof GetNamedPart) {
            x = Quote.append$V(new Object[]{Lit12, args});
        } else {
            x = Boolean.FALSE;
        }
        if (x != Boolean.FALSE) {
            return x;
        }
        return Quote.consX$V(new Object[]{unrewrite(fun), args});
    }
}

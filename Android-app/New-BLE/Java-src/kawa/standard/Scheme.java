package kawa.standard;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.ReferenceExp;
import gnu.kawa.functions.Apply;
import gnu.kawa.functions.ApplyToArgs;
import gnu.kawa.functions.DisplayFormat;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.functions.IsEq;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.IsEqv;
import gnu.kawa.functions.Map;
import gnu.kawa.functions.Not;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.functions.NumberPredicate;
import gnu.kawa.lispexpr.LangObjType;
import gnu.kawa.lispexpr.LangPrimType;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.lispexpr.LispReader;
import gnu.kawa.lispexpr.ReadTable;
import gnu.kawa.lispexpr.ReaderDispatch;
import gnu.kawa.lispexpr.ReaderDispatchMisc;
import gnu.kawa.lispexpr.ReaderParens;
import gnu.kawa.lispexpr.ReaderQuote;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.AbstractFormat;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleEnvironment;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import kawa.lang.Eval;

public class Scheme extends LispLanguage {
    public static final Apply apply = new Apply("apply", applyToArgs);
    static final Declaration applyFieldDecl = Declaration.getDeclarationFromStatic("kawa.standard.Scheme", "applyToArgs");
    public static final ApplyToArgs applyToArgs = new ApplyToArgs("apply-to-args", instance);
    public static LangPrimType booleanType;
    public static final AbstractFormat displayFormat = new DisplayFormat(false, 'S');
    public static final Map forEach = new Map(false, applyToArgs, applyFieldDecl, isEq);
    public static final Scheme instance = new Scheme(kawaEnvironment);
    public static final InstanceOf instanceOf = new InstanceOf(instance, GetNamedPart.INSTANCEOF_METHOD_NAME);
    public static final IsEq isEq = new IsEq(instance, "eq?");
    public static final IsEqual isEqual = new IsEqual(instance, "equal?");
    public static final IsEqv isEqv = new IsEqv(instance, "eqv?", isEq);
    public static final NumberPredicate isEven = new NumberPredicate(instance, "even?", 2);
    public static final NumberPredicate isOdd = new NumberPredicate(instance, "odd?", 1);
    protected static final SimpleEnvironment kawaEnvironment = Environment.make("kawa-environment", r5Environment);
    public static final Map map = new Map(true, applyToArgs, applyFieldDecl, isEq);
    public static final Not not = new Not(instance, "not");
    public static final Environment nullEnvironment = Environment.make("null-environment");
    public static final NumberCompare numEqu = NumberCompare.make(instance, "=", 8);
    public static final NumberCompare numGEq = NumberCompare.make(instance, ">=", 24);
    public static final NumberCompare numGrt = NumberCompare.make(instance, ">", 16);
    public static final NumberCompare numLEq = NumberCompare.make(instance, "<=", 12);
    public static final NumberCompare numLss = NumberCompare.make(instance, "<", 4);
    public static final Environment r4Environment = Environment.make("r4rs-environment", nullEnvironment);
    public static final Environment r5Environment = Environment.make("r5rs-environment", r4Environment);
    static HashMap<Type, String> typeToStringMap;
    static HashMap<String, Type> types;
    public static final Namespace unitNamespace = Namespace.valueOf("http://kawa.gnu.org/unit", "unit");
    public static final AbstractFormat writeFormat = new DisplayFormat(true, 'S');

    static {
        instance.initScheme();
        int withServlets = HttpRequestContext.importServletDefinitions;
        if (withServlets > 0) {
            try {
                String str;
                Scheme scheme = instance;
                if (withServlets > 1) {
                    str = "gnu.kawa.servlet.servlets";
                } else {
                    str = "gnu.kawa.servlet.HTTP";
                }
                scheme.loadClass(str);
            } catch (Throwable th) {
            }
        }
    }

    public static Scheme getInstance() {
        return instance;
    }

    public static Environment builtin() {
        return kawaEnvironment;
    }

    private void initScheme() {
        this.environ = nullEnvironment;
        this.environ.addLocation(LispLanguage.lookup_sym, null, getNamedPartLocation);
        defSntxStFld("lambda", "kawa.standard.SchemeCompilation", "lambda");
        defSntxStFld(LispLanguage.quote_sym, "kawa.lang.Quote", "plainQuote");
        defSntxStFld("%define", "kawa.standard.define", "defineRaw");
        defSntxStFld("define", "kawa.lib.prim_syntax");
        defSntxStFld("if", "kawa.lib.prim_syntax");
        defSntxStFld("set!", "kawa.standard.set_b", "set");
        defSntxStFld("cond", "kawa.lib.std_syntax");
        defSntxStFld("case", "kawa.lib.std_syntax");
        defSntxStFld("and", "kawa.lib.std_syntax");
        defSntxStFld("or", "kawa.lib.std_syntax");
        defSntxStFld("%let", "kawa.standard.let", "let");
        defSntxStFld("let", "kawa.lib.std_syntax");
        defSntxStFld("let*", "kawa.lib.std_syntax");
        defSntxStFld("letrec", "kawa.lib.prim_syntax");
        defSntxStFld("begin", "kawa.standard.begin", "begin");
        defSntxStFld("do", "kawa.lib.std_syntax");
        defSntxStFld("delay", "kawa.lib.std_syntax");
        defProcStFld("%make-promise", "kawa.lib.std_syntax");
        defSntxStFld(LispLanguage.quasiquote_sym, "kawa.lang.Quote", "quasiQuote");
        defSntxStFld("define-syntax", "kawa.lib.prim_syntax");
        defSntxStFld("let-syntax", "kawa.standard.let_syntax", "let_syntax");
        defSntxStFld("letrec-syntax", "kawa.standard.let_syntax", "letrec_syntax");
        defSntxStFld("syntax-rules", "kawa.standard.syntax_rules", "syntax_rules");
        nullEnvironment.setLocked();
        this.environ = r4Environment;
        defProcStFld("not", "kawa.standard.Scheme");
        defProcStFld("boolean?", "kawa.lib.misc");
        defProcStFld("eq?", "kawa.standard.Scheme", "isEq");
        defProcStFld("eqv?", "kawa.standard.Scheme", "isEqv");
        defProcStFld("equal?", "kawa.standard.Scheme", "isEqual");
        defProcStFld("pair?", "kawa.lib.lists");
        defProcStFld("cons", "kawa.lib.lists");
        defProcStFld("car", "kawa.lib.lists");
        defProcStFld("cdr", "kawa.lib.lists");
        defProcStFld("set-car!", "kawa.lib.lists");
        defProcStFld("set-cdr!", "kawa.lib.lists");
        defProcStFld("caar", "kawa.lib.lists");
        defProcStFld("cadr", "kawa.lib.lists");
        defProcStFld("cdar", "kawa.lib.lists");
        defProcStFld("cddr", "kawa.lib.lists");
        defProcStFld("caaar", "kawa.lib.lists");
        defProcStFld("caadr", "kawa.lib.lists");
        defProcStFld("cadar", "kawa.lib.lists");
        defProcStFld("caddr", "kawa.lib.lists");
        defProcStFld("cdaar", "kawa.lib.lists");
        defProcStFld("cdadr", "kawa.lib.lists");
        defProcStFld("cddar", "kawa.lib.lists");
        defProcStFld("cdddr", "kawa.lib.lists");
        defProcStFld("caaaar", "kawa.lib.lists");
        defProcStFld("caaadr", "kawa.lib.lists");
        defProcStFld("caadar", "kawa.lib.lists");
        defProcStFld("caaddr", "kawa.lib.lists");
        defProcStFld("cadaar", "kawa.lib.lists");
        defProcStFld("cadadr", "kawa.lib.lists");
        defProcStFld("caddar", "kawa.lib.lists");
        defProcStFld("cadddr", "kawa.lib.lists");
        defProcStFld("cdaaar", "kawa.lib.lists");
        defProcStFld("cdaadr", "kawa.lib.lists");
        defProcStFld("cdadar", "kawa.lib.lists");
        defProcStFld("cdaddr", "kawa.lib.lists");
        defProcStFld("cddaar", "kawa.lib.lists");
        defProcStFld("cddadr", "kawa.lib.lists");
        defProcStFld("cdddar", "kawa.lib.lists");
        defProcStFld("cddddr", "kawa.lib.lists");
        defProcStFld("null?", "kawa.lib.lists");
        defProcStFld("list?", "kawa.lib.lists");
        defProcStFld("length", "kawa.lib.lists");
        defProcStFld("append", "kawa.standard.append", "append");
        defProcStFld("reverse", "kawa.lib.lists");
        defProcStFld("reverse!", "kawa.lib.lists");
        defProcStFld("list-tail", "kawa.lib.lists");
        defProcStFld("list-ref", "kawa.lib.lists");
        defProcStFld("memq", "kawa.lib.lists");
        defProcStFld("memv", "kawa.lib.lists");
        defProcStFld("member", "kawa.lib.lists");
        defProcStFld("assq", "kawa.lib.lists");
        defProcStFld("assv", "kawa.lib.lists");
        defProcStFld("assoc", "kawa.lib.lists");
        defProcStFld("symbol?", "kawa.lib.misc");
        defProcStFld("symbol->string", "kawa.lib.misc");
        defProcStFld("string->symbol", "kawa.lib.misc");
        defProcStFld("symbol=?", "kawa.lib.misc");
        defProcStFld("symbol-local-name", "kawa.lib.misc");
        defProcStFld("symbol-namespace", "kawa.lib.misc");
        defProcStFld("symbol-namespace-uri", "kawa.lib.misc");
        defProcStFld("symbol-prefix", "kawa.lib.misc");
        defProcStFld("namespace-uri", "kawa.lib.misc");
        defProcStFld("namespace-prefix", "kawa.lib.misc");
        defProcStFld("number?", "kawa.lib.numbers");
        defProcStFld("quantity?", "kawa.lib.numbers");
        defProcStFld("complex?", "kawa.lib.numbers");
        defProcStFld("real?", "kawa.lib.numbers");
        defProcStFld("rational?", "kawa.lib.numbers");
        defProcStFld("integer?", "kawa.lib.numbers");
        defProcStFld("exact?", "kawa.lib.numbers");
        defProcStFld("inexact?", "kawa.lib.numbers");
        defProcStFld("=", "kawa.standard.Scheme", "numEqu");
        defProcStFld("<", "kawa.standard.Scheme", "numLss");
        defProcStFld(">", "kawa.standard.Scheme", "numGrt");
        defProcStFld("<=", "kawa.standard.Scheme", "numLEq");
        defProcStFld(">=", "kawa.standard.Scheme", "numGEq");
        defProcStFld("zero?", "kawa.lib.numbers");
        defProcStFld("positive?", "kawa.lib.numbers");
        defProcStFld("negative?", "kawa.lib.numbers");
        defProcStFld("odd?", "kawa.standard.Scheme", "isOdd");
        defProcStFld("even?", "kawa.standard.Scheme", "isEven");
        defProcStFld("max", "kawa.lib.numbers");
        defProcStFld("min", "kawa.lib.numbers");
        defProcStFld("+", "gnu.kawa.functions.AddOp", "$Pl");
        defProcStFld("-", "gnu.kawa.functions.AddOp", "$Mn");
        defProcStFld("*", "gnu.kawa.functions.MultiplyOp", "$St");
        defProcStFld("/", "gnu.kawa.functions.DivideOp", "$Sl");
        defProcStFld("abs", "kawa.lib.numbers");
        defProcStFld("quotient", "gnu.kawa.functions.DivideOp", "quotient");
        defProcStFld("remainder", "gnu.kawa.functions.DivideOp", "remainder");
        defProcStFld("modulo", "gnu.kawa.functions.DivideOp", "modulo");
        defProcStFld("div", "gnu.kawa.functions.DivideOp", "div");
        defProcStFld("mod", "gnu.kawa.functions.DivideOp", "mod");
        defProcStFld("div0", "gnu.kawa.functions.DivideOp", "div0");
        defProcStFld("mod0", "gnu.kawa.functions.DivideOp", "mod0");
        defProcStFld("div-and-mod", "kawa.lib.numbers");
        defProcStFld("div0-and-mod0", "kawa.lib.numbers");
        defProcStFld("gcd", "kawa.lib.numbers");
        defProcStFld("lcm", "kawa.lib.numbers");
        defProcStFld("numerator", "kawa.lib.numbers");
        defProcStFld("denominator", "kawa.lib.numbers");
        defProcStFld("floor", "kawa.lib.numbers");
        defProcStFld("ceiling", "kawa.lib.numbers");
        defProcStFld("truncate", "kawa.lib.numbers");
        defProcStFld("round", "kawa.lib.numbers");
        defProcStFld("rationalize", "kawa.lib.numbers");
        defProcStFld("exp", "kawa.lib.numbers");
        defProcStFld("log", "kawa.lib.numbers");
        defProcStFld("sin", "kawa.lib.numbers");
        defProcStFld("cos", "kawa.lib.numbers");
        defProcStFld("tan", "kawa.lib.numbers");
        defProcStFld("asin", "kawa.lib.numbers");
        defProcStFld("acos", "kawa.lib.numbers");
        defProcStFld("atan", "kawa.lib.numbers");
        defProcStFld("sqrt", "kawa.lib.numbers");
        defProcStFld("expt", "kawa.standard.expt");
        defProcStFld("make-rectangular", "kawa.lib.numbers");
        defProcStFld("make-polar", "kawa.lib.numbers");
        defProcStFld("real-part", "kawa.lib.numbers");
        defProcStFld("imag-part", "kawa.lib.numbers");
        defProcStFld("magnitude", "kawa.lib.numbers");
        defProcStFld("angle", "kawa.lib.numbers");
        defProcStFld("inexact", "kawa.lib.numbers");
        defProcStFld("exact", "kawa.lib.numbers");
        defProcStFld("exact->inexact", "kawa.lib.numbers");
        defProcStFld("inexact->exact", "kawa.lib.numbers");
        defProcStFld("number->string", "kawa.lib.numbers");
        defProcStFld("string->number", "kawa.lib.numbers");
        defProcStFld("char?", "kawa.lib.characters");
        defProcStFld("char=?", "kawa.lib.characters");
        defProcStFld("char<?", "kawa.lib.characters");
        defProcStFld("char>?", "kawa.lib.characters");
        defProcStFld("char<=?", "kawa.lib.characters");
        defProcStFld("char>=?", "kawa.lib.characters");
        defProcStFld("char-ci=?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-ci<?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-ci>?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-ci<=?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-ci>=?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-alphabetic?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-numeric?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-whitespace?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-upper-case?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-lower-case?", "kawa.lib.rnrs.unicode");
        defProcStFld("char-title-case?", "kawa.lib.rnrs.unicode");
        defProcStFld("char->integer", "kawa.lib.characters");
        defProcStFld("integer->char", "kawa.lib.characters");
        defProcStFld("char-upcase", "kawa.lib.rnrs.unicode");
        defProcStFld("char-downcase", "kawa.lib.rnrs.unicode");
        defProcStFld("char-titlecase", "kawa.lib.rnrs.unicode");
        defProcStFld("char-foldcase", "kawa.lib.rnrs.unicode");
        defProcStFld("char-general-category", "kawa.lib.rnrs.unicode");
        defProcStFld("string?", "kawa.lib.strings");
        defProcStFld("make-string", "kawa.lib.strings");
        defProcStFld("string-length", "kawa.lib.strings");
        defProcStFld("string-ref", "kawa.lib.strings");
        defProcStFld("string-set!", "kawa.lib.strings");
        defProcStFld("string=?", "kawa.lib.strings");
        defProcStFld("string<?", "kawa.lib.strings");
        defProcStFld("string>?", "kawa.lib.strings");
        defProcStFld("string<=?", "kawa.lib.strings");
        defProcStFld("string>=?", "kawa.lib.strings");
        defProcStFld("string-ci=?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-ci<?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-ci>?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-ci<=?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-ci>=?", "kawa.lib.rnrs.unicode");
        defProcStFld("string-normalize-nfd", "kawa.lib.rnrs.unicode");
        defProcStFld("string-normalize-nfkd", "kawa.lib.rnrs.unicode");
        defProcStFld("string-normalize-nfc", "kawa.lib.rnrs.unicode");
        defProcStFld("string-normalize-nfkc", "kawa.lib.rnrs.unicode");
        defProcStFld("substring", "kawa.lib.strings");
        defProcStFld("string-append", "kawa.lib.strings");
        defProcStFld("string-append/shared", "kawa.lib.strings");
        defProcStFld("string->list", "kawa.lib.strings");
        defProcStFld("list->string", "kawa.lib.strings");
        defProcStFld("string-copy", "kawa.lib.strings");
        defProcStFld("string-fill!", "kawa.lib.strings");
        defProcStFld("vector?", "kawa.lib.vectors");
        defProcStFld("make-vector", "kawa.lib.vectors");
        defProcStFld("vector-length", "kawa.lib.vectors");
        defProcStFld("vector-ref", "kawa.lib.vectors");
        defProcStFld("vector-set!", "kawa.lib.vectors");
        defProcStFld("list->vector", "kawa.lib.vectors");
        defProcStFld("vector->list", "kawa.lib.vectors");
        defProcStFld("vector-fill!", "kawa.lib.vectors");
        defProcStFld("vector-append", "kawa.standard.vector_append", "vectorAppend");
        defProcStFld("values-append", "gnu.kawa.functions.AppendValues", "appendValues");
        defProcStFld("procedure?", "kawa.lib.misc");
        defProcStFld("apply", "kawa.standard.Scheme", "apply");
        defProcStFld("map", "kawa.standard.Scheme", "map");
        defProcStFld("for-each", "kawa.standard.Scheme", "forEach");
        defProcStFld("call-with-current-continuation", "gnu.kawa.functions.CallCC", "callcc");
        defProcStFld("call/cc", "kawa.standard.callcc", "callcc");
        defProcStFld("force", "kawa.lib.misc");
        defProcStFld("call-with-input-file", "kawa.lib.ports");
        defProcStFld("call-with-output-file", "kawa.lib.ports");
        defProcStFld("input-port?", "kawa.lib.ports");
        defProcStFld("output-port?", "kawa.lib.ports");
        defProcStFld("current-input-port", "kawa.lib.ports");
        defProcStFld("current-output-port", "kawa.lib.ports");
        defProcStFld("with-input-from-file", "kawa.lib.ports");
        defProcStFld("with-output-to-file", "kawa.lib.ports");
        defProcStFld("open-input-file", "kawa.lib.ports");
        defProcStFld("open-output-file", "kawa.lib.ports");
        defProcStFld("close-input-port", "kawa.lib.ports");
        defProcStFld("close-output-port", "kawa.lib.ports");
        defProcStFld("read", "kawa.lib.ports");
        defProcStFld("read-line", "kawa.lib.ports");
        defProcStFld("read-char", "kawa.standard.readchar", "readChar");
        defProcStFld("peek-char", "kawa.standard.readchar", "peekChar");
        defProcStFld("eof-object?", "kawa.lib.ports");
        defProcStFld("char-ready?", "kawa.lib.ports");
        defProcStFld("write", "kawa.lib.ports");
        defProcStFld("display", "kawa.lib.ports");
        defProcStFld("print-as-xml", "gnu.xquery.lang.XQuery", "writeFormat");
        defProcStFld("write-char", "kawa.lib.ports");
        defProcStFld("newline", "kawa.lib.ports");
        defProcStFld("load", "kawa.standard.load", "load");
        defProcStFld("load-relative", "kawa.standard.load", "loadRelative");
        defProcStFld("transcript-off", "kawa.lib.ports");
        defProcStFld("transcript-on", "kawa.lib.ports");
        defProcStFld("call-with-input-string", "kawa.lib.ports");
        defProcStFld("open-input-string", "kawa.lib.ports");
        defProcStFld("open-output-string", "kawa.lib.ports");
        defProcStFld("get-output-string", "kawa.lib.ports");
        defProcStFld("call-with-output-string", "kawa.lib.ports");
        defProcStFld("force-output", "kawa.lib.ports");
        defProcStFld("port-line", "kawa.lib.ports");
        defProcStFld("set-port-line!", "kawa.lib.ports");
        defProcStFld("port-column", "kawa.lib.ports");
        defProcStFld("current-error-port", "kawa.lib.ports");
        defProcStFld("input-port-line-number", "kawa.lib.ports");
        defProcStFld("set-input-port-line-number!", "kawa.lib.ports");
        defProcStFld("input-port-column-number", "kawa.lib.ports");
        defProcStFld("input-port-read-state", "kawa.lib.ports");
        defProcStFld("default-prompter", "kawa.lib.ports");
        defProcStFld("input-port-prompter", "kawa.lib.ports");
        defProcStFld("set-input-port-prompter!", "kawa.lib.ports");
        defProcStFld("base-uri", "kawa.lib.misc");
        defProcStFld("%syntax-error", "kawa.standard.syntax_error", "syntax_error");
        defProcStFld("syntax-error", "kawa.lib.prim_syntax");
        r4Environment.setLocked();
        this.environ = r5Environment;
        defProcStFld("values", "kawa.lib.misc");
        defProcStFld("call-with-values", "kawa.standard.call_with_values", "callWithValues");
        defSntxStFld("let-values", "kawa.lib.syntax");
        defSntxStFld("let*-values", "kawa.lib.syntax");
        defSntxStFld("case-lambda", "kawa.lib.syntax");
        defSntxStFld("receive", "kawa.lib.syntax");
        defProcStFld("eval", "kawa.lang.Eval");
        defProcStFld("repl", "kawa.standard.SchemeCompilation", "repl");
        defProcStFld("scheme-report-environment", "kawa.lib.misc");
        defProcStFld("null-environment", "kawa.lib.misc");
        defProcStFld("interaction-environment", "kawa.lib.misc");
        defProcStFld("dynamic-wind", "kawa.lib.misc");
        r5Environment.setLocked();
        this.environ = kawaEnvironment;
        defSntxStFld("define-private", "kawa.lib.prim_syntax");
        defSntxStFld("define-constant", "kawa.lib.prim_syntax");
        defSntxStFld("define-autoload", "kawa.standard.define_autoload", "define_autoload");
        defSntxStFld("define-autoloads-from-file", "kawa.standard.define_autoload", "define_autoloads_from_file");
        defProcStFld("exit", "kawa.lib.rnrs.programs");
        defProcStFld("command-line", "kawa.lib.rnrs.programs");
        defProcStFld("bitwise-arithmetic-shift", "gnu.kawa.functions.BitwiseOp", "ashift");
        defProcStFld("arithmetic-shift", "gnu.kawa.functions.BitwiseOp", "ashift");
        defProcStFld("ash", "gnu.kawa.functions.BitwiseOp", "ashift");
        defProcStFld("bitwise-arithmetic-shift-left", "gnu.kawa.functions.BitwiseOp", "ashiftl");
        defProcStFld("bitwise-arithmetic-shift-right", "gnu.kawa.functions.BitwiseOp", "ashiftr");
        defProcStFld("bitwise-and", "gnu.kawa.functions.BitwiseOp", "and");
        defProcStFld("logand", "gnu.kawa.functions.BitwiseOp", "and");
        defProcStFld("bitwise-ior", "gnu.kawa.functions.BitwiseOp", "ior");
        defProcStFld("logior", "gnu.kawa.functions.BitwiseOp", "ior");
        defProcStFld("bitwise-xor", "gnu.kawa.functions.BitwiseOp", "xor");
        defProcStFld("logxor", "gnu.kawa.functions.BitwiseOp", "xor");
        defProcStFld("bitwise-if", "kawa.lib.numbers");
        defProcStFld("bitwise-not", "gnu.kawa.functions.BitwiseOp", "not");
        defProcStFld("lognot", "gnu.kawa.functions.BitwiseOp", "not");
        defProcStFld("logop", "kawa.lib.numbers");
        defProcStFld("bitwise-bit-set?", "kawa.lib.numbers");
        defProcStFld("logbit?", "kawa.lib.numbers", Compilation.mangleNameIfNeeded("bitwise-bit-set?"));
        defProcStFld("logtest", "kawa.lib.numbers");
        defProcStFld("bitwise-bit-count", "kawa.lib.numbers");
        defProcStFld("logcount", "kawa.lib.numbers");
        defProcStFld("bitwise-copy-bit", "kawa.lib.numbers");
        defProcStFld("bitwise-copy-bit-field", "kawa.lib.numbers");
        defProcStFld("bitwise-bit-field", "kawa.lib.numbers");
        defProcStFld("bit-extract", "kawa.lib.numbers", Compilation.mangleNameIfNeeded("bitwise-bit-field"));
        defProcStFld("bitwise-length", "kawa.lib.numbers");
        defProcStFld("integer-length", "kawa.lib.numbers", "bitwise$Mnlength");
        defProcStFld("bitwise-first-bit-set", "kawa.lib.numbers");
        defProcStFld("bitwise-rotate-bit-field", "kawa.lib.numbers");
        defProcStFld("bitwise-reverse-bit-field", "kawa.lib.numbers");
        defProcStFld("string-upcase!", "kawa.lib.strings");
        defProcStFld("string-downcase!", "kawa.lib.strings");
        defProcStFld("string-capitalize!", "kawa.lib.strings");
        defProcStFld("string-upcase", "kawa.lib.rnrs.unicode");
        defProcStFld("string-downcase", "kawa.lib.rnrs.unicode");
        defProcStFld("string-titlecase", "kawa.lib.rnrs.unicode");
        defProcStFld("string-foldcase", "kawa.lib.rnrs.unicode");
        defProcStFld("string-capitalize", "kawa.lib.strings");
        defSntxStFld("primitive-virtual-method", "kawa.standard.prim_method", "virtual_method");
        defSntxStFld("primitive-static-method", "kawa.standard.prim_method", "static_method");
        defSntxStFld("primitive-interface-method", "kawa.standard.prim_method", "interface_method");
        defSntxStFld("primitive-constructor", "kawa.lib.reflection");
        defSntxStFld("primitive-op1", "kawa.standard.prim_method", "op1");
        defSntxStFld("primitive-get-field", "kawa.lib.reflection");
        defSntxStFld("primitive-set-field", "kawa.lib.reflection");
        defSntxStFld("primitive-get-static", "kawa.lib.reflection");
        defSntxStFld("primitive-set-static", "kawa.lib.reflection");
        defSntxStFld("primitive-array-new", "kawa.lib.reflection");
        defSntxStFld("primitive-array-get", "kawa.lib.reflection");
        defSntxStFld("primitive-array-set", "kawa.lib.reflection");
        defSntxStFld("primitive-array-length", "kawa.lib.reflection");
        defProcStFld("subtype?", "kawa.lib.reflection");
        defProcStFld("primitive-throw", "kawa.standard.prim_throw", "primitiveThrow");
        defSntxStFld("try-finally", "kawa.lib.syntax");
        defSntxStFld("try-catch", "kawa.lib.prim_syntax");
        defProcStFld("throw", "kawa.standard.throw_name", "throwName");
        defProcStFld("catch", "kawa.lib.system");
        defProcStFld("error", "kawa.lib.misc");
        defProcStFld("as", "gnu.kawa.functions.Convert", "as");
        defProcStFld(GetNamedPart.INSTANCEOF_METHOD_NAME, "kawa.standard.Scheme", "instanceOf");
        defSntxStFld("synchronized", "kawa.lib.syntax");
        defSntxStFld("object", "kawa.standard.object", "objectSyntax");
        defSntxStFld("define-class", "kawa.standard.define_class", "define_class");
        defSntxStFld("define-simple-class", "kawa.standard.define_class", "define_simple_class");
        defSntxStFld("this", "kawa.standard.thisRef", "thisSyntax");
        defProcStFld("make", "gnu.kawa.reflect.Invoke", "make");
        defProcStFld("slot-ref", "gnu.kawa.reflect.SlotGet", "slotRef");
        defProcStFld("slot-set!", "gnu.kawa.reflect.SlotSet", "set$Mnfield$Ex");
        defProcStFld("field", "gnu.kawa.reflect.SlotGet");
        defProcStFld("class-methods", "gnu.kawa.reflect.ClassMethods", "classMethods");
        defProcStFld("static-field", "gnu.kawa.reflect.SlotGet", "staticField");
        defProcStFld("invoke", "gnu.kawa.reflect.Invoke", "invoke");
        defProcStFld("invoke-static", "gnu.kawa.reflect.Invoke", "invokeStatic");
        defProcStFld("invoke-special", "gnu.kawa.reflect.Invoke", "invokeSpecial");
        defSntxStFld("define-macro", "kawa.lib.syntax");
        defSntxStFld("%define-macro", "kawa.standard.define_syntax", "define_macro");
        defSntxStFld("define-syntax-case", "kawa.lib.syntax");
        defSntxStFld("syntax-case", "kawa.standard.syntax_case", "syntax_case");
        defSntxStFld("%define-syntax", "kawa.standard.define_syntax", "define_syntax");
        defSntxStFld("syntax", "kawa.standard.syntax", "syntax");
        defSntxStFld("quasisyntax", "kawa.standard.syntax", "quasiSyntax");
        defProcStFld("syntax-object->datum", "kawa.lib.std_syntax");
        defProcStFld("datum->syntax-object", "kawa.lib.std_syntax");
        defProcStFld("syntax->expression", "kawa.lib.prim_syntax");
        defProcStFld("syntax-body->expression", "kawa.lib.prim_syntax");
        defProcStFld("generate-temporaries", "kawa.lib.std_syntax");
        defSntxStFld("with-syntax", "kawa.lib.std_syntax");
        defProcStFld("identifier?", "kawa.lib.std_syntax");
        defProcStFld("free-identifier=?", "kawa.lib.std_syntax");
        defProcStFld("syntax-source", "kawa.lib.std_syntax");
        defProcStFld("syntax-line", "kawa.lib.std_syntax");
        defProcStFld("syntax-column", "kawa.lib.std_syntax");
        defSntxStFld("begin-for-syntax", "kawa.lib.std_syntax");
        defSntxStFld("define-for-syntax", "kawa.lib.std_syntax");
        defSntxStFld("include", "kawa.lib.misc_syntax");
        defSntxStFld("include-relative", "kawa.lib.misc_syntax");
        defProcStFld("file-exists?", "kawa.lib.files");
        defProcStFld("file-directory?", "kawa.lib.files");
        defProcStFld("file-readable?", "kawa.lib.files");
        defProcStFld("file-writable?", "kawa.lib.files");
        defProcStFld("delete-file", "kawa.lib.files");
        defProcStFld("system-tmpdir", "kawa.lib.files");
        defProcStFld("make-temporary-file", "kawa.lib.files");
        defProcStFld("rename-file", "kawa.lib.files");
        defProcStFld("copy-file", "kawa.lib.files");
        defProcStFld("create-directory", "kawa.lib.files");
        defProcStFld("->pathname", "kawa.lib.files");
        define("port-char-encoding", Boolean.TRUE);
        define("symbol-read-case", "P");
        defProcStFld("system", "kawa.lib.system");
        defProcStFld("make-process", "kawa.lib.system");
        defProcStFld("tokenize-string-to-string-array", "kawa.lib.system");
        defProcStFld("tokenize-string-using-shell", "kawa.lib.system");
        defProcStFld("command-parse", "kawa.lib.system");
        defProcStFld("process-command-line-assignments", "kawa.lib.system");
        defProcStFld("record-accessor", "kawa.lib.reflection");
        defProcStFld("record-modifier", "kawa.lib.reflection");
        defProcStFld("record-predicate", "kawa.lib.reflection");
        defProcStFld("record-constructor", "kawa.lib.reflection");
        defProcStFld("make-record-type", "kawa.lib.reflection");
        defProcStFld("record-type-descriptor", "kawa.lib.reflection");
        defProcStFld("record-type-name", "kawa.lib.reflection");
        defProcStFld("record-type-field-names", "kawa.lib.reflection");
        defProcStFld("record?", "kawa.lib.reflection");
        defSntxStFld("define-record-type", "gnu.kawa.slib.DefineRecordType");
        defSntxStFld("when", "kawa.lib.syntax");
        defSntxStFld("unless", "kawa.lib.syntax");
        defSntxStFld("fluid-let", "kawa.standard.fluid_let", "fluid_let");
        defSntxStFld("constant-fold", "kawa.standard.constant_fold", "constant_fold");
        defProcStFld("make-parameter", "kawa.lib.parameters");
        defSntxStFld("parameterize", "kawa.lib.parameters");
        defProcStFld("compile-file", "kawa.lib.system");
        defProcStFld("environment-bound?", "kawa.lib.misc");
        defProcStFld("scheme-implementation-version", "kawa.lib.misc");
        defProcStFld("scheme-window", "kawa.lib.windows");
        defSntxStFld("define-procedure", "kawa.lib.std_syntax");
        defProcStFld("add-procedure-properties", "kawa.lib.misc");
        defProcStFld("make-procedure", "gnu.kawa.functions.MakeProcedure", "makeProcedure");
        defProcStFld("procedure-property", "kawa.lib.misc");
        defProcStFld("set-procedure-property!", "kawa.lib.misc");
        defSntxStFld("provide", "kawa.lib.misc_syntax");
        defSntxStFld("test-begin", "kawa.lib.misc_syntax");
        defProcStFld("quantity->number", "kawa.lib.numbers");
        defProcStFld("quantity->unit", "kawa.lib.numbers");
        defProcStFld("make-quantity", "kawa.lib.numbers");
        defSntxStFld("define-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_namespace");
        defSntxStFld("define-xml-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_xml_namespace");
        defSntxStFld("define-private-namespace", "gnu.kawa.lispexpr.DefineNamespace", "define_private_namespace");
        defSntxStFld("define-unit", "kawa.standard.define_unit", "define_unit");
        defSntxStFld("define-base-unit", "kawa.standard.define_unit", "define_base_unit");
        defProcStFld("duration", "kawa.lib.numbers");
        defProcStFld("gentemp", "kawa.lib.misc");
        defSntxStFld("defmacro", "kawa.lib.syntax");
        defProcStFld("setter", "gnu.kawa.functions.Setter", "setter");
        defSntxStFld("resource-url", "kawa.lib.misc_syntax");
        defSntxStFld("module-uri", "kawa.lib.misc_syntax");
        defSntxStFld("future", "kawa.lib.thread");
        defProcStFld("sleep", "kawa.lib.thread");
        defProcStFld("runnable", "kawa.lib.thread");
        defSntxStFld("trace", "kawa.lib.trace");
        defSntxStFld("untrace", "kawa.lib.trace");
        defSntxStFld("disassemble", "kawa.lib.trace");
        defProcStFld("format", "gnu.kawa.functions.Format");
        defProcStFld("parse-format", "gnu.kawa.functions.ParseFormat", "parseFormat");
        defProcStFld("make-element", "gnu.kawa.xml.MakeElement", "makeElement");
        defProcStFld("make-attribute", "gnu.kawa.xml.MakeAttribute", "makeAttribute");
        defProcStFld("map-values", "gnu.kawa.functions.ValuesMap", "valuesMap");
        defProcStFld("children", "gnu.kawa.xml.Children", "children");
        defProcStFld("attributes", "gnu.kawa.xml.Attributes");
        defProcStFld("unescaped-data", "gnu.kawa.xml.MakeUnescapedData", "unescapedData");
        defProcStFld("keyword?", "kawa.lib.keywords");
        defProcStFld("keyword->string", "kawa.lib.keywords");
        defProcStFld("string->keyword", "kawa.lib.keywords");
        defSntxStFld("location", "kawa.standard.location", "location");
        defSntxStFld("define-alias", "kawa.standard.define_alias", "define_alias");
        defSntxStFld("define-variable", "kawa.standard.define_variable", "define_variable");
        defSntxStFld("define-member-alias", "kawa.standard.define_member_alias", "define_member_alias");
        defSntxStFld("define-enum", "gnu.kawa.slib.enums");
        defSntxStFld("import", "kawa.lib.syntax");
        defSntxStFld("require", "kawa.standard.require", "require");
        defSntxStFld("module-name", "kawa.standard.module_name", "module_name");
        defSntxStFld("module-extends", "kawa.standard.module_extends", "module_extends");
        defSntxStFld("module-implements", "kawa.standard.module_implements", "module_implements");
        defSntxStFld("module-static", "kawa.standard.module_static", "module_static");
        defSntxStFld("module-export", "kawa.standard.export", "module_export");
        defSntxStFld("export", "kawa.standard.export", "export");
        defSntxStFld("module-compile-options", "kawa.standard.module_compile_options", "module_compile_options");
        defSntxStFld("with-compile-options", "kawa.standard.with_compile_options", "with_compile_options");
        defProcStFld("array?", "kawa.lib.arrays");
        defProcStFld("array-rank", "kawa.lib.arrays");
        defProcStFld("make-array", "kawa.lib.arrays");
        defProcStFld("array", "kawa.lib.arrays");
        defProcStFld("array-start", "kawa.lib.arrays");
        defProcStFld("array-end", "kawa.lib.arrays");
        defProcStFld("shape", "kawa.lib.arrays");
        defProcStFld("array-ref", "gnu.kawa.functions.ArrayRef", "arrayRef");
        defProcStFld("array-set!", "gnu.kawa.functions.ArraySet", "arraySet");
        defProcStFld("share-array", "kawa.lib.arrays");
        defProcStFld("s8vector?", "kawa.lib.uniform");
        defProcStFld("make-s8vector", "kawa.lib.uniform");
        defProcStFld("s8vector", "kawa.lib.uniform");
        defProcStFld("s8vector-length", "kawa.lib.uniform");
        defProcStFld("s8vector-ref", "kawa.lib.uniform");
        defProcStFld("s8vector-set!", "kawa.lib.uniform");
        defProcStFld("s8vector->list", "kawa.lib.uniform");
        defProcStFld("list->s8vector", "kawa.lib.uniform");
        defProcStFld("u8vector?", "kawa.lib.uniform");
        defProcStFld("make-u8vector", "kawa.lib.uniform");
        defProcStFld("u8vector", "kawa.lib.uniform");
        defProcStFld("u8vector-length", "kawa.lib.uniform");
        defProcStFld("u8vector-ref", "kawa.lib.uniform");
        defProcStFld("u8vector-set!", "kawa.lib.uniform");
        defProcStFld("u8vector->list", "kawa.lib.uniform");
        defProcStFld("list->u8vector", "kawa.lib.uniform");
        defProcStFld("s16vector?", "kawa.lib.uniform");
        defProcStFld("make-s16vector", "kawa.lib.uniform");
        defProcStFld("s16vector", "kawa.lib.uniform");
        defProcStFld("s16vector-length", "kawa.lib.uniform");
        defProcStFld("s16vector-ref", "kawa.lib.uniform");
        defProcStFld("s16vector-set!", "kawa.lib.uniform");
        defProcStFld("s16vector->list", "kawa.lib.uniform");
        defProcStFld("list->s16vector", "kawa.lib.uniform");
        defProcStFld("u16vector?", "kawa.lib.uniform");
        defProcStFld("make-u16vector", "kawa.lib.uniform");
        defProcStFld("u16vector", "kawa.lib.uniform");
        defProcStFld("u16vector-length", "kawa.lib.uniform");
        defProcStFld("u16vector-ref", "kawa.lib.uniform");
        defProcStFld("u16vector-set!", "kawa.lib.uniform");
        defProcStFld("u16vector->list", "kawa.lib.uniform");
        defProcStFld("list->u16vector", "kawa.lib.uniform");
        defProcStFld("s32vector?", "kawa.lib.uniform");
        defProcStFld("make-s32vector", "kawa.lib.uniform");
        defProcStFld("s32vector", "kawa.lib.uniform");
        defProcStFld("s32vector-length", "kawa.lib.uniform");
        defProcStFld("s32vector-ref", "kawa.lib.uniform");
        defProcStFld("s32vector-set!", "kawa.lib.uniform");
        defProcStFld("s32vector->list", "kawa.lib.uniform");
        defProcStFld("list->s32vector", "kawa.lib.uniform");
        defProcStFld("u32vector?", "kawa.lib.uniform");
        defProcStFld("make-u32vector", "kawa.lib.uniform");
        defProcStFld("u32vector", "kawa.lib.uniform");
        defProcStFld("u32vector-length", "kawa.lib.uniform");
        defProcStFld("u32vector-ref", "kawa.lib.uniform");
        defProcStFld("u32vector-set!", "kawa.lib.uniform");
        defProcStFld("u32vector->list", "kawa.lib.uniform");
        defProcStFld("list->u32vector", "kawa.lib.uniform");
        defProcStFld("s64vector?", "kawa.lib.uniform");
        defProcStFld("make-s64vector", "kawa.lib.uniform");
        defProcStFld("s64vector", "kawa.lib.uniform");
        defProcStFld("s64vector-length", "kawa.lib.uniform");
        defProcStFld("s64vector-ref", "kawa.lib.uniform");
        defProcStFld("s64vector-set!", "kawa.lib.uniform");
        defProcStFld("s64vector->list", "kawa.lib.uniform");
        defProcStFld("list->s64vector", "kawa.lib.uniform");
        defProcStFld("u64vector?", "kawa.lib.uniform");
        defProcStFld("make-u64vector", "kawa.lib.uniform");
        defProcStFld("u64vector", "kawa.lib.uniform");
        defProcStFld("u64vector-length", "kawa.lib.uniform");
        defProcStFld("u64vector-ref", "kawa.lib.uniform");
        defProcStFld("u64vector-set!", "kawa.lib.uniform");
        defProcStFld("u64vector->list", "kawa.lib.uniform");
        defProcStFld("list->u64vector", "kawa.lib.uniform");
        defProcStFld("f32vector?", "kawa.lib.uniform");
        defProcStFld("make-f32vector", "kawa.lib.uniform");
        defProcStFld("f32vector", "kawa.lib.uniform");
        defProcStFld("f32vector-length", "kawa.lib.uniform");
        defProcStFld("f32vector-ref", "kawa.lib.uniform");
        defProcStFld("f32vector-set!", "kawa.lib.uniform");
        defProcStFld("f32vector->list", "kawa.lib.uniform");
        defProcStFld("list->f32vector", "kawa.lib.uniform");
        defProcStFld("f64vector?", "kawa.lib.uniform");
        defProcStFld("make-f64vector", "kawa.lib.uniform");
        defProcStFld("f64vector", "kawa.lib.uniform");
        defProcStFld("f64vector-length", "kawa.lib.uniform");
        defProcStFld("f64vector-ref", "kawa.lib.uniform");
        defProcStFld("f64vector-set!", "kawa.lib.uniform");
        defProcStFld("f64vector->list", "kawa.lib.uniform");
        defProcStFld("list->f64vector", "kawa.lib.uniform");
        defSntxStFld("cut", "gnu.kawa.slib.cut");
        defSntxStFld("cute", "gnu.kawa.slib.cut");
        defSntxStFld("cond-expand", "kawa.lib.syntax");
        defSntxStFld("%cond-expand", "kawa.lib.syntax");
        defAliasStFld("*print-base*", "gnu.kawa.functions.DisplayFormat", "outBase");
        defAliasStFld("*print-radix*", "gnu.kawa.functions.DisplayFormat", "outRadix");
        defAliasStFld("*print-right-margin*", "gnu.text.PrettyWriter", "lineLengthLoc");
        defAliasStFld("*print-miser-width*", "gnu.text.PrettyWriter", "miserWidthLoc");
        defAliasStFld("html", "gnu.kawa.xml.XmlNamespace", "HTML");
        defAliasStFld("unit", "kawa.standard.Scheme", "unitNamespace");
        defAliasStFld("path", "gnu.kawa.lispexpr.LangObjType", "pathType");
        defAliasStFld("filepath", "gnu.kawa.lispexpr.LangObjType", "filepathType");
        defAliasStFld("URI", "gnu.kawa.lispexpr.LangObjType", "URIType");
        defProcStFld("resolve-uri", "kawa.lib.files");
        defAliasStFld("vector", "gnu.kawa.lispexpr.LangObjType", "vectorType");
        defAliasStFld(PropertyTypeConstants.PROPERTY_TYPE_STRING, "gnu.kawa.lispexpr.LangObjType", "stringType");
        defAliasStFld("list", "gnu.kawa.lispexpr.LangObjType", "listType");
        defAliasStFld("regex", "gnu.kawa.lispexpr.LangObjType", "regexType");
        defProcStFld("path?", "kawa.lib.files");
        defProcStFld("filepath?", "kawa.lib.files");
        defProcStFld("URI?", "kawa.lib.files");
        defProcStFld("absolute-path?", "kawa.lib.files");
        defProcStFld("path-scheme", "kawa.lib.files");
        defProcStFld("path-authority", "kawa.lib.files");
        defProcStFld("path-user-info", "kawa.lib.files");
        defProcStFld("path-host", "kawa.lib.files");
        defProcStFld("path-port", "kawa.lib.files");
        defProcStFld("path-file", "kawa.lib.files");
        defProcStFld("path-parent", "kawa.lib.files");
        defProcStFld("path-directory", "kawa.lib.files");
        defProcStFld("path-last", "kawa.lib.files");
        defProcStFld("path-extension", "kawa.lib.files");
        defProcStFld("path-fragment", "kawa.lib.files");
        defProcStFld("path-query", "kawa.lib.files");
        kawaEnvironment.setLocked();
    }

    public Scheme() {
        this.environ = kawaEnvironment;
        this.userEnv = getNewEnvironment();
    }

    protected Scheme(Environment env) {
        this.environ = env;
    }

    public String getName() {
        return "Scheme";
    }

    public static Object eval(String string, Environment env) {
        return eval(new CharArrayInPort(string), env);
    }

    public static Object eval(InPort port, Environment env) {
        SourceMessages messages = new SourceMessages();
        try {
            Object body = ReaderParens.readList((LispReader) Language.getDefaultLanguage().getLexer(port, messages), 0, 1, -1);
            if (!messages.seenErrors()) {
                return Eval.evalBody(body, env, messages);
            }
            throw new SyntaxException(messages);
        } catch (SyntaxException e) {
            throw new RuntimeException("eval: errors while compiling:\n" + e.getMessages().toString(20));
        } catch (IOException e2) {
            throw new RuntimeException("eval: I/O exception: " + e2.toString());
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Error ex2) {
            throw ex2;
        } catch (Throwable ex3) {
            WrappedException wrappedException = new WrappedException(ex3);
        }
    }

    public static Object eval(Object sexpr, Environment env) {
        try {
            return Eval.eval(sexpr, env);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Error ex2) {
            throw ex2;
        } catch (Throwable ex3) {
            WrappedException wrappedException = new WrappedException(ex3);
        }
    }

    public AbstractFormat getFormat(boolean readable) {
        return readable ? writeFormat : displayFormat;
    }

    public int getNamespaceOf(Declaration decl) {
        return 3;
    }

    public static Type getTypeValue(Expression exp) {
        return getInstance().getTypeFor(exp);
    }

    static synchronized HashMap<String, Type> getTypeMap() {
        HashMap<String, Type> hashMap;
        synchronized (Scheme.class) {
            if (types == null) {
                booleanType = new LangPrimType(Type.booleanType, getInstance());
                types = new HashMap();
                types.put("void", LangPrimType.voidType);
                types.put("int", LangPrimType.intType);
                types.put("char", LangPrimType.charType);
                types.put(PropertyTypeConstants.PROPERTY_TYPE_BOOLEAN, booleanType);
                types.put("byte", LangPrimType.byteType);
                types.put("short", LangPrimType.shortType);
                types.put("long", LangPrimType.longType);
                types.put(PropertyTypeConstants.PROPERTY_TYPE_FLOAT, LangPrimType.floatType);
                types.put("double", LangPrimType.doubleType);
                types.put("never-returns", Type.neverReturnsType);
                types.put("Object", Type.objectType);
                types.put("String", Type.toStringType);
                types.put("object", Type.objectType);
                types.put("number", LangObjType.numericType);
                types.put("quantity", ClassType.make("gnu.math.Quantity"));
                types.put("complex", ClassType.make("gnu.math.Complex"));
                types.put("real", LangObjType.realType);
                types.put("rational", LangObjType.rationalType);
                types.put(PropertyTypeConstants.PROPERTY_TYPE_INTEGER, LangObjType.integerType);
                types.put("symbol", ClassType.make("gnu.mapping.Symbol"));
                types.put("namespace", ClassType.make("gnu.mapping.Namespace"));
                types.put("keyword", ClassType.make("gnu.expr.Keyword"));
                types.put("pair", ClassType.make("gnu.lists.Pair"));
                types.put("pair-with-position", ClassType.make("gnu.lists.PairWithPosition"));
                types.put("constant-string", ClassType.make("java.lang.String"));
                types.put("abstract-string", ClassType.make("gnu.lists.CharSeq"));
                types.put("character", ClassType.make("gnu.text.Char"));
                types.put("vector", LangObjType.vectorType);
                types.put(PropertyTypeConstants.PROPERTY_TYPE_STRING, LangObjType.stringType);
                types.put("list", LangObjType.listType);
                types.put("function", ClassType.make("gnu.mapping.Procedure"));
                types.put("procedure", ClassType.make("gnu.mapping.Procedure"));
                types.put("input-port", ClassType.make("gnu.mapping.InPort"));
                types.put("output-port", ClassType.make("gnu.mapping.OutPort"));
                types.put("string-output-port", ClassType.make("gnu.mapping.CharArrayOutPort"));
                types.put("record", ClassType.make("kawa.lang.Record"));
                types.put("type", LangObjType.typeType);
                types.put("class-type", LangObjType.typeClassType);
                types.put("class", LangObjType.typeClass);
                types.put("s8vector", ClassType.make("gnu.lists.S8Vector"));
                types.put("u8vector", ClassType.make("gnu.lists.U8Vector"));
                types.put("s16vector", ClassType.make("gnu.lists.S16Vector"));
                types.put("u16vector", ClassType.make("gnu.lists.U16Vector"));
                types.put("s32vector", ClassType.make("gnu.lists.S32Vector"));
                types.put("u32vector", ClassType.make("gnu.lists.U32Vector"));
                types.put("s64vector", ClassType.make("gnu.lists.S64Vector"));
                types.put("u64vector", ClassType.make("gnu.lists.U64Vector"));
                types.put("f32vector", ClassType.make("gnu.lists.F32Vector"));
                types.put("f64vector", ClassType.make("gnu.lists.F64Vector"));
                types.put("document", ClassType.make("gnu.kawa.xml.KDocument"));
                types.put("readtable", ClassType.make("gnu.kawa.lispexpr.ReadTable"));
            }
            hashMap = types;
        }
        return hashMap;
    }

    public static Type getNamedType(String name) {
        getTypeMap();
        Type type = (Type) types.get(name);
        if (type == null && (name.startsWith("elisp:") || name.startsWith("clisp:"))) {
            int colon = name.indexOf(58);
            Class clas = getNamedType(name.substring(colon + 1)).getReflectClass();
            String lang = name.substring(0, colon);
            Language interp = Language.getInstance(lang);
            if (interp == null) {
                throw new RuntimeException("unknown type '" + name + "' - unknown language '" + lang + '\'');
            }
            type = interp.getTypeFor(clas);
            if (type != null) {
                types.put(name, type);
            }
        }
        return type;
    }

    public Type getTypeFor(Class clas) {
        String name = clas.getName();
        if (clas.isPrimitive()) {
            return getNamedType(name);
        }
        if ("java.lang.String".equals(name)) {
            return Type.toStringType;
        }
        if ("gnu.math.IntNum".equals(name)) {
            return LangObjType.integerType;
        }
        if ("gnu.math.DFloNum".equals(name)) {
            return LangObjType.dflonumType;
        }
        if ("gnu.math.RatNum".equals(name)) {
            return LangObjType.rationalType;
        }
        if ("gnu.math.RealNum".equals(name)) {
            return LangObjType.realType;
        }
        if ("gnu.math.Numeric".equals(name)) {
            return LangObjType.numericType;
        }
        if ("gnu.lists.FVector".equals(name)) {
            return LangObjType.vectorType;
        }
        if ("gnu.lists.LList".equals(name)) {
            return LangObjType.listType;
        }
        if ("gnu.text.Path".equals(name)) {
            return LangObjType.pathType;
        }
        if ("gnu.text.URIPath".equals(name)) {
            return LangObjType.URIType;
        }
        if ("gnu.text.FilePath".equals(name)) {
            return LangObjType.filepathType;
        }
        if ("java.lang.Class".equals(name)) {
            return LangObjType.typeClass;
        }
        if ("gnu.bytecode.Type".equals(name)) {
            return LangObjType.typeType;
        }
        if ("gnu.bytecode.ClassType".equals(name)) {
            return LangObjType.typeClassType;
        }
        return Type.make(clas);
    }

    public String formatType(Type type) {
        if (typeToStringMap == null) {
            typeToStringMap = new HashMap();
            for (Entry<String, Type> e : getTypeMap().entrySet()) {
                String s = (String) e.getKey();
                Type t = (Type) e.getValue();
                typeToStringMap.put(t, s);
                Type it = t.getImplementationType();
                if (it != t) {
                    typeToStringMap.put(it, s);
                }
            }
        }
        String str = (String) typeToStringMap.get(type);
        return str != null ? str : super.formatType(type);
    }

    public static Type string2Type(String name) {
        Type t;
        if (name.endsWith("[]")) {
            t = string2Type(name.substring(0, name.length() - 2));
            if (t != null) {
                t = ArrayType.make(t);
            }
        } else {
            t = getNamedType(name);
        }
        if (t != null) {
            return t;
        }
        t = Language.string2Type(name);
        if (t != null) {
            types.put(name, t);
        }
        return t;
    }

    public Type getTypeFor(String name) {
        return string2Type(name);
    }

    public static Type exp2Type(Expression exp) {
        return getInstance().getTypeFor(exp);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression checkDefaultBinding(gnu.mapping.Symbol r54, kawa.lang.Translator r55) {
        /*
        r53 = this;
        r22 = r54.getNamespace();
        r20 = r54.getLocalPart();
        r0 = r22;
        r0 = r0 instanceof gnu.kawa.xml.XmlNamespace;
        r48 = r0;
        if (r48 == 0) goto L_0x001f;
    L_0x0010:
        r22 = (gnu.kawa.xml.XmlNamespace) r22;
        r0 = r22;
        r1 = r20;
        r48 = r0.get(r1);
        r18 = gnu.expr.QuoteExp.getInstance(r48);
    L_0x001e:
        return r18;
    L_0x001f:
        r48 = r22.getName();
        r49 = unitNamespace;
        r49 = r49.getName();
        r0 = r48;
        r1 = r49;
        if (r0 != r1) goto L_0x003a;
    L_0x002f:
        r46 = gnu.math.Unit.lookup(r20);
        if (r46 == 0) goto L_0x003a;
    L_0x0035:
        r18 = gnu.expr.QuoteExp.getInstance(r46);
        goto L_0x001e;
    L_0x003a:
        r21 = r54.toString();
        r17 = r21.length();
        if (r17 != 0) goto L_0x0047;
    L_0x0044:
        r18 = 0;
        goto L_0x001e;
    L_0x0047:
        r48 = 1;
        r0 = r17;
        r1 = r48;
        if (r0 <= r1) goto L_0x0105;
    L_0x004f:
        r48 = r17 + -1;
        r0 = r21;
        r1 = r48;
        r48 = r0.charAt(r1);
        r49 = 63;
        r0 = r48;
        r1 = r49;
        if (r0 != r1) goto L_0x0105;
    L_0x0061:
        r19 = r20.length();
        r48 = 1;
        r0 = r19;
        r1 = r48;
        if (r0 <= r1) goto L_0x0105;
    L_0x006d:
        r48 = 0;
        r49 = r19 + -1;
        r0 = r20;
        r1 = r48;
        r2 = r49;
        r48 = r0.substring(r1, r2);
        r34 = r48.intern();
        r0 = r22;
        r1 = r34;
        r35 = r0.getSymbol(r1);
        r48 = 0;
        r0 = r55;
        r1 = r35;
        r2 = r48;
        r33 = r0.rewrite(r1, r2);
        r0 = r33;
        r0 = r0 instanceof gnu.expr.ReferenceExp;
        r48 = r0;
        if (r48 == 0) goto L_0x00fa;
    L_0x009b:
        r48 = r33;
        r48 = (gnu.expr.ReferenceExp) r48;
        r8 = r48.getBinding();
        if (r8 == 0) goto L_0x00b0;
    L_0x00a5:
        r48 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r48;
        r48 = r8.getFlag(r0);
        if (r48 == 0) goto L_0x00b2;
    L_0x00b0:
        r33 = 0;
    L_0x00b2:
        if (r33 == 0) goto L_0x0105;
    L_0x00b4:
        r18 = new gnu.expr.LambdaExp;
        r48 = 1;
        r0 = r18;
        r1 = r48;
        r0.<init>(r1);
        r0 = r18;
        r1 = r54;
        r0.setSymbol(r1);
        r48 = 0;
        r48 = (java.lang.Object) r48;
        r0 = r18;
        r1 = r48;
        r29 = r0.addDeclaration(r1);
        r48 = new gnu.expr.ApplyExp;
        r49 = instanceOf;
        r50 = 2;
        r0 = r50;
        r0 = new gnu.expr.Expression[r0];
        r50 = r0;
        r51 = 0;
        r52 = new gnu.expr.ReferenceExp;
        r0 = r52;
        r1 = r29;
        r0.<init>(r1);
        r50[r51] = r52;
        r51 = 1;
        r50[r51] = r33;
        r48.<init>(r49, r50);
        r0 = r48;
        r1 = r18;
        r1.body = r0;
        goto L_0x001e;
    L_0x00fa:
        r0 = r33;
        r0 = r0 instanceof gnu.expr.QuoteExp;
        r48 = r0;
        if (r48 != 0) goto L_0x00b2;
    L_0x0102:
        r33 = 0;
        goto L_0x00b2;
    L_0x0105:
        r48 = 0;
        r0 = r21;
        r1 = r48;
        r5 = r0.charAt(r1);
        r48 = 45;
        r0 = r48;
        if (r5 == r0) goto L_0x0125;
    L_0x0115:
        r48 = 43;
        r0 = r48;
        if (r5 == r0) goto L_0x0125;
    L_0x011b:
        r48 = 10;
        r0 = r48;
        r48 = java.lang.Character.digit(r5, r0);
        if (r48 < 0) goto L_0x020f;
    L_0x0125:
        r32 = 0;
        r14 = 0;
    L_0x0128:
        r0 = r17;
        if (r14 >= r0) goto L_0x01d3;
    L_0x012c:
        r0 = r21;
        r4 = r0.charAt(r14);
        r48 = 10;
        r0 = r48;
        r48 = java.lang.Character.digit(r4, r0);
        if (r48 < 0) goto L_0x0157;
    L_0x013c:
        r48 = 3;
        r0 = r32;
        r1 = r48;
        if (r0 >= r1) goto L_0x0149;
    L_0x0144:
        r32 = 2;
    L_0x0146:
        r14 = r14 + 1;
        goto L_0x0128;
    L_0x0149:
        r48 = 5;
        r0 = r32;
        r1 = r48;
        if (r0 >= r1) goto L_0x0154;
    L_0x0151:
        r32 = 4;
        goto L_0x0146;
    L_0x0154:
        r32 = 5;
        goto L_0x0146;
    L_0x0157:
        r48 = 43;
        r0 = r48;
        if (r4 == r0) goto L_0x0163;
    L_0x015d:
        r48 = 45;
        r0 = r48;
        if (r4 != r0) goto L_0x0168;
    L_0x0163:
        if (r32 != 0) goto L_0x0168;
    L_0x0165:
        r32 = 1;
        goto L_0x0146;
    L_0x0168:
        r48 = 46;
        r0 = r48;
        if (r4 != r0) goto L_0x0179;
    L_0x016e:
        r48 = 3;
        r0 = r32;
        r1 = r48;
        if (r0 >= r1) goto L_0x0179;
    L_0x0176:
        r32 = 3;
        goto L_0x0146;
    L_0x0179:
        r48 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r0 = r48;
        if (r4 == r0) goto L_0x0185;
    L_0x017f:
        r48 = 69;
        r0 = r48;
        if (r4 != r0) goto L_0x01d3;
    L_0x0185:
        r48 = 2;
        r0 = r32;
        r1 = r48;
        if (r0 == r1) goto L_0x0195;
    L_0x018d:
        r48 = 4;
        r0 = r32;
        r1 = r48;
        if (r0 != r1) goto L_0x01d3;
    L_0x0195:
        r48 = r14 + 1;
        r0 = r48;
        r1 = r17;
        if (r0 >= r1) goto L_0x01d3;
    L_0x019d:
        r16 = r14 + 1;
        r0 = r21;
        r1 = r16;
        r25 = r0.charAt(r1);
        r48 = 45;
        r0 = r25;
        r1 = r48;
        if (r0 == r1) goto L_0x01b7;
    L_0x01af:
        r48 = 43;
        r0 = r25;
        r1 = r48;
        if (r0 != r1) goto L_0x01c7;
    L_0x01b7:
        r16 = r16 + 1;
        r0 = r16;
        r1 = r17;
        if (r0 >= r1) goto L_0x01c7;
    L_0x01bf:
        r0 = r21;
        r1 = r16;
        r25 = r0.charAt(r1);
    L_0x01c7:
        r48 = 10;
        r0 = r25;
        r1 = r48;
        r48 = java.lang.Character.digit(r0, r1);
        if (r48 >= 0) goto L_0x0274;
    L_0x01d3:
        r0 = r17;
        if (r14 >= r0) goto L_0x020f;
    L_0x01d7:
        r48 = 1;
        r0 = r32;
        r1 = r48;
        if (r0 <= r1) goto L_0x020f;
    L_0x01df:
        r26 = new gnu.math.DFloNum;
        r48 = 0;
        r0 = r21;
        r1 = r48;
        r48 = r0.substring(r1, r14);
        r0 = r26;
        r1 = r48;
        r0.<init>(r1);
        r10 = 0;
        r47 = new java.util.Vector;
        r47.<init>();
        r15 = r14;
    L_0x01f9:
        r0 = r17;
        if (r15 >= r0) goto L_0x0344;
    L_0x01fd:
        r14 = r15 + 1;
        r0 = r21;
        r4 = r0.charAt(r15);
        r48 = 42;
        r0 = r48;
        if (r4 != r0) goto L_0x02ea;
    L_0x020b:
        r0 = r17;
        if (r14 != r0) goto L_0x027a;
    L_0x020f:
        r48 = 2;
        r0 = r17;
        r1 = r48;
        if (r0 <= r1) goto L_0x03ec;
    L_0x0217:
        r48 = 60;
        r0 = r48;
        if (r5 != r0) goto L_0x03ec;
    L_0x021d:
        r48 = r17 + -1;
        r0 = r21;
        r1 = r48;
        r48 = r0.charAt(r1);
        r49 = 62;
        r0 = r48;
        r1 = r49;
        if (r0 != r1) goto L_0x03ec;
    L_0x022f:
        r48 = 1;
        r49 = r17 + -1;
        r0 = r21;
        r1 = r48;
        r2 = r49;
        r21 = r0.substring(r1, r2);
        r17 = r17 + -2;
        r31 = 1;
    L_0x0241:
        r30 = 0;
    L_0x0243:
        r48 = 2;
        r0 = r17;
        r1 = r48;
        if (r0 <= r1) goto L_0x03f0;
    L_0x024b:
        r48 = r17 + -2;
        r0 = r21;
        r1 = r48;
        r48 = r0.charAt(r1);
        r49 = 91;
        r0 = r48;
        r1 = r49;
        if (r0 != r1) goto L_0x03f0;
    L_0x025d:
        r48 = r17 + -1;
        r0 = r21;
        r1 = r48;
        r48 = r0.charAt(r1);
        r49 = 93;
        r0 = r48;
        r1 = r49;
        if (r0 != r1) goto L_0x03f0;
    L_0x026f:
        r17 = r17 + -2;
        r30 = r30 + 1;
        goto L_0x0243;
    L_0x0274:
        r32 = 5;
        r14 = r16 + 1;
        goto L_0x0146;
    L_0x027a:
        r15 = r14 + 1;
        r0 = r21;
        r4 = r0.charAt(r14);
        r14 = r15;
    L_0x0283:
        r41 = r14 + -1;
    L_0x0285:
        r48 = java.lang.Character.isLetter(r4);
        if (r48 != 0) goto L_0x0301;
    L_0x028b:
        r40 = r14 + -1;
        r0 = r40;
        r1 = r41;
        if (r0 == r1) goto L_0x020f;
    L_0x0293:
        r0 = r21;
        r1 = r41;
        r2 = r40;
        r48 = r0.substring(r1, r2);
        r47.addElement(r48);
        r13 = 0;
        r48 = 94;
        r0 = r48;
        if (r4 != r0) goto L_0x04b9;
    L_0x02a7:
        r13 = 1;
        r0 = r17;
        if (r14 == r0) goto L_0x020f;
    L_0x02ac:
        r15 = r14 + 1;
        r0 = r21;
        r4 = r0.charAt(r14);
    L_0x02b4:
        r23 = r10;
        r48 = 43;
        r0 = r48;
        if (r4 != r0) goto L_0x0315;
    L_0x02bc:
        r13 = 1;
        r0 = r17;
        if (r15 == r0) goto L_0x020f;
    L_0x02c1:
        r14 = r15 + 1;
        r0 = r21;
        r4 = r0.charAt(r15);
    L_0x02c9:
        r24 = 0;
        r12 = 0;
    L_0x02cc:
        r48 = 10;
        r0 = r48;
        r9 = java.lang.Character.digit(r4, r0);
        if (r9 > 0) goto L_0x0330;
    L_0x02d6:
        r14 = r14 + -1;
    L_0x02d8:
        if (r24 != 0) goto L_0x02dd;
    L_0x02da:
        r12 = 1;
        if (r13 != 0) goto L_0x020f;
    L_0x02dd:
        if (r23 == 0) goto L_0x02e0;
    L_0x02df:
        r12 = -r12;
    L_0x02e0:
        r48 = gnu.math.IntNum.make(r12);
        r47.addElement(r48);
        r15 = r14;
        goto L_0x01f9;
    L_0x02ea:
        r48 = 47;
        r0 = r48;
        if (r4 != r0) goto L_0x0283;
    L_0x02f0:
        r0 = r17;
        if (r14 == r0) goto L_0x020f;
    L_0x02f4:
        if (r10 != 0) goto L_0x020f;
    L_0x02f6:
        r10 = 1;
        r15 = r14 + 1;
        r0 = r21;
        r4 = r0.charAt(r14);
        r14 = r15;
        goto L_0x0283;
    L_0x0301:
        r0 = r17;
        if (r14 != r0) goto L_0x030a;
    L_0x0305:
        r40 = r14;
        r4 = 49;
        goto L_0x0293;
    L_0x030a:
        r15 = r14 + 1;
        r0 = r21;
        r4 = r0.charAt(r14);
        r14 = r15;
        goto L_0x0285;
    L_0x0315:
        r48 = 45;
        r0 = r48;
        if (r4 != r0) goto L_0x04b6;
    L_0x031b:
        r13 = 1;
        r0 = r17;
        if (r15 == r0) goto L_0x020f;
    L_0x0320:
        r14 = r15 + 1;
        r0 = r21;
        r4 = r0.charAt(r15);
        if (r23 != 0) goto L_0x032d;
    L_0x032a:
        r23 = 1;
    L_0x032c:
        goto L_0x02c9;
    L_0x032d:
        r23 = 0;
        goto L_0x032c;
    L_0x0330:
        r48 = r12 * 10;
        r12 = r48 + r9;
        r24 = r24 + 1;
        r0 = r17;
        if (r14 == r0) goto L_0x02d8;
    L_0x033a:
        r15 = r14 + 1;
        r0 = r21;
        r4 = r0.charAt(r14);
        r14 = r15;
        goto L_0x02cc;
    L_0x0344:
        r0 = r17;
        if (r15 != r0) goto L_0x020f;
    L_0x0348:
        r48 = r47.size();
        r27 = r48 >> 1;
        r0 = r27;
        r0 = new gnu.expr.Expression[r0];
        r42 = r0;
        r14 = 0;
    L_0x0355:
        r0 = r27;
        if (r14 >= r0) goto L_0x03af;
    L_0x0359:
        r48 = r14 * 2;
        r38 = r47.elementAt(r48);
        r38 = (java.lang.String) r38;
        r48 = unitNamespace;
        r49 = r38.intern();
        r45 = r48.getSymbol(r49);
        r0 = r55;
        r1 = r45;
        r43 = r0.rewrite(r1);
        r48 = r14 * 2;
        r48 = r48 + 1;
        r37 = r47.elementAt(r48);
        r37 = (gnu.math.IntNum) r37;
        r48 = r37.longValue();
        r50 = 1;
        r48 = (r48 > r50 ? 1 : (r48 == r50 ? 0 : -1));
        if (r48 == 0) goto L_0x03aa;
    L_0x0387:
        r44 = new gnu.expr.ApplyExp;
        r48 = kawa.standard.expt.expt;
        r49 = 2;
        r0 = r49;
        r0 = new gnu.expr.Expression[r0];
        r49 = r0;
        r50 = 0;
        r49[r50] = r43;
        r50 = 1;
        r51 = gnu.expr.QuoteExp.getInstance(r37);
        r49[r50] = r51;
        r0 = r44;
        r1 = r48;
        r2 = r49;
        r0.<init>(r1, r2);
        r43 = r44;
    L_0x03aa:
        r42[r14] = r43;
        r14 = r14 + 1;
        goto L_0x0355;
    L_0x03af:
        r48 = 1;
        r0 = r27;
        r1 = r48;
        if (r0 != r1) goto L_0x03de;
    L_0x03b7:
        r48 = 0;
        r39 = r42[r48];
    L_0x03bb:
        r18 = new gnu.expr.ApplyExp;
        r48 = gnu.kawa.functions.MultiplyOp.$St;
        r49 = 2;
        r0 = r49;
        r0 = new gnu.expr.Expression[r0];
        r49 = r0;
        r50 = 0;
        r51 = gnu.expr.QuoteExp.getInstance(r26);
        r49[r50] = r51;
        r50 = 1;
        r49[r50] = r39;
        r0 = r18;
        r1 = r48;
        r2 = r49;
        r0.<init>(r1, r2);
        goto L_0x001e;
    L_0x03de:
        r39 = new gnu.expr.ApplyExp;
        r48 = gnu.kawa.functions.MultiplyOp.$St;
        r0 = r39;
        r1 = r48;
        r2 = r42;
        r0.<init>(r1, r2);
        goto L_0x03bb;
    L_0x03ec:
        r31 = 0;
        goto L_0x0241;
    L_0x03f0:
        r7 = r21;
        if (r30 == 0) goto L_0x0400;
    L_0x03f4:
        r48 = 0;
        r0 = r21;
        r1 = r48;
        r2 = r17;
        r7 = r0.substring(r1, r2);
    L_0x0400:
        r36 = getNamedType(r7);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        if (r30 <= 0) goto L_0x043e;
    L_0x0406:
        if (r31 == 0) goto L_0x040a;
    L_0x0408:
        if (r36 != 0) goto L_0x043e;
    L_0x040a:
        r48 = r7.intern();	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r0 = r22;
        r1 = r48;
        r35 = r0.getSymbol(r1);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r48 = 0;
        r0 = r55;
        r1 = r35;
        r2 = r48;
        r33 = r0.rewrite(r1, r2);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r0 = r33;
        r1 = r55;
        r33 = gnu.expr.InlineCalls.inlineCalls(r0, r1);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r0 = r33;
        r0 = r0 instanceof gnu.expr.ErrorExp;	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r48 = r0;
        if (r48 != 0) goto L_0x043e;
    L_0x0432:
        r48 = r55.getLanguage();	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r0 = r48;
        r1 = r33;
        r36 = r0.getTypeFor(r1);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
    L_0x043e:
        if (r36 == 0) goto L_0x044f;
    L_0x0440:
        r30 = r30 + -1;
        if (r30 < 0) goto L_0x0449;
    L_0x0444:
        r36 = gnu.bytecode.ArrayType.make(r36);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        goto L_0x0440;
    L_0x0449:
        r18 = gnu.expr.QuoteExp.getInstance(r36);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        goto L_0x001e;
    L_0x044f:
        r36 = gnu.bytecode.Type.lookupType(r7);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r0 = r36;
        r0 = r0 instanceof gnu.bytecode.PrimType;	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r48 = r0;
        if (r48 == 0) goto L_0x0470;
    L_0x045b:
        r6 = r36.getReflectClass();	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
    L_0x045f:
        if (r6 == 0) goto L_0x04b2;
    L_0x0461:
        if (r30 <= 0) goto L_0x049e;
    L_0x0463:
        r36 = gnu.bytecode.Type.make(r6);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
    L_0x0467:
        r30 = r30 + -1;
        if (r30 < 0) goto L_0x049a;
    L_0x046b:
        r36 = gnu.bytecode.ArrayType.make(r36);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        goto L_0x0467;
    L_0x0470:
        r48 = 46;
        r0 = r48;
        r48 = r7.indexOf(r0);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        if (r48 >= 0) goto L_0x0495;
    L_0x047a:
        r48 = new java.lang.StringBuilder;	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r48.<init>();	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r0 = r55;
        r0 = r0.classPrefix;	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r49 = r0;
        r48 = r48.append(r49);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r49 = gnu.expr.Compilation.mangleNameIfNeeded(r7);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r48 = r48.append(r49);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        r7 = r48.toString();	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
    L_0x0495:
        r6 = gnu.bytecode.ObjectType.getContextClass(r7);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        goto L_0x045f;
    L_0x049a:
        r6 = r36.getReflectClass();	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
    L_0x049e:
        r18 = gnu.expr.QuoteExp.getInstance(r6);	 Catch:{ ClassNotFoundException -> 0x04a4, Throwable -> 0x04b1 }
        goto L_0x001e;
    L_0x04a4:
        r11 = move-exception;
        r28 = gnu.bytecode.ArrayClassLoader.getContextPackage(r21);
        if (r28 == 0) goto L_0x04b2;
    L_0x04ab:
        r18 = gnu.expr.QuoteExp.getInstance(r28);
        goto L_0x001e;
    L_0x04b1:
        r48 = move-exception;
    L_0x04b2:
        r18 = 0;
        goto L_0x001e;
    L_0x04b6:
        r14 = r15;
        goto L_0x02c9;
    L_0x04b9:
        r15 = r14;
        goto L_0x02b4;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.Scheme.checkDefaultBinding(gnu.mapping.Symbol, kawa.lang.Translator):gnu.expr.Expression");
    }

    public Expression makeApply(Expression func, Expression[] args) {
        Expression[] exps = new Expression[(args.length + 1)];
        exps[0] = func;
        System.arraycopy(args, 0, exps, 1, args.length);
        return new ApplyExp(new ReferenceExp(applyFieldDecl), exps);
    }

    public Symbol asSymbol(String ident) {
        return Namespace.EmptyNamespace.getSymbol(ident);
    }

    public ReadTable createReadTable() {
        ReadTable tab = ReadTable.createInitial();
        tab.postfixLookupOperator = ':';
        ReaderDispatch dispatchTable = (ReaderDispatch) tab.lookup(35);
        dispatchTable.set(39, new ReaderQuote(asSymbol("syntax")));
        dispatchTable.set(96, new ReaderQuote(asSymbol("quasisyntax")));
        dispatchTable.set(44, ReaderDispatchMisc.getInstance());
        tab.putReaderCtorFld("path", "gnu.kawa.lispexpr.LangObjType", "pathType");
        tab.putReaderCtorFld("filepath", "gnu.kawa.lispexpr.LangObjType", "filepathType");
        tab.putReaderCtorFld("URI", "gnu.kawa.lispexpr.LangObjType", "URIType");
        tab.putReaderCtor("symbol", ClassType.make("gnu.mapping.Symbol"));
        tab.putReaderCtor("namespace", ClassType.make("gnu.mapping.Namespace"));
        tab.putReaderCtorFld("duration", "kawa.lib.numbers", "duration");
        tab.setFinalColonIsKeyword(true);
        return tab;
    }

    public static void registerEnvironment() {
        Language.setDefaults(getInstance());
    }
}

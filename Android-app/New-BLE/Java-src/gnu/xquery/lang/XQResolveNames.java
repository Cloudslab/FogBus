package gnu.xquery.lang;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.BeginExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.ModuleExp;
import gnu.expr.PrimProcedure;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ResolveNames;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.functions.GetModuleClass;
import gnu.kawa.reflect.SingletonType;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.xml.MakeAttribute;
import gnu.kawa.xml.MakeElement;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.XDataType;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Location;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import gnu.mapping.WrongArguments;
import gnu.xml.NamespaceBinding;
import gnu.xml.XMLFilter;
import gnu.xquery.util.NamedCollator;
import gnu.xquery.util.QNameUtils;
import kawa.standard.Scheme;

public class XQResolveNames extends ResolveNames {
    public static final int BASE_URI_BUILTIN = -11;
    public static final int CASTABLE_AS_BUILTIN = -34;
    public static final int CAST_AS_BUILTIN = -33;
    public static final int COLLECTION_BUILTIN = -8;
    public static final int COMPARE_BUILTIN = -4;
    public static final int DEEP_EQUAL_BUILTIN = -25;
    public static final int DEFAULT_COLLATION_BUILTIN = -29;
    public static final int DISTINCT_VALUES_BUILTIN = -5;
    public static final int DOC_AVAILABLE_BUILTIN = -10;
    public static final int DOC_BUILTIN = -9;
    public static final int HANDLE_EXTENSION_BUILTIN = -3;
    public static final int IDREF_BUILTIN = -31;
    public static final int ID_BUILTIN = -30;
    public static final int INDEX_OF_BUILTIN = -15;
    public static final int LANG_BUILTIN = -23;
    public static final int LAST_BUILTIN = -1;
    public static final int LOCAL_NAME_BUILTIN = -6;
    public static final int MAX_BUILTIN = -27;
    public static final int MIN_BUILTIN = -26;
    public static final int NAMESPACE_URI_BUILTIN = -7;
    public static final int NAME_BUILTIN = -24;
    public static final int NORMALIZE_SPACE_BUILTIN = -17;
    public static final int NUMBER_BUILTIN = -28;
    public static final int POSITION_BUILTIN = -2;
    public static final int RESOLVE_PREFIX_BUILTIN = -13;
    public static final int RESOLVE_URI_BUILTIN = -12;
    public static final int ROOT_BUILTIN = -32;
    public static final int STATIC_BASE_URI_BUILTIN = -14;
    public static final int STRING_BUILTIN = -16;
    public static final int UNORDERED_BUILTIN = -18;
    public static final int XS_QNAME_BUILTIN = -35;
    public static final int XS_QNAME_IGNORE_DEFAULT_BUILTIN = -36;
    public static final Declaration castAsDecl = makeBuiltin("(cast as)", -33);
    public static final Declaration castableAsDecl = makeBuiltin("(castable as)", -34);
    public static final Declaration handleExtensionDecl = makeBuiltin("(extension)", -3);
    public static final Declaration lastDecl = makeBuiltin("last", -1);
    public static final Declaration resolvePrefixDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "(resolve-prefix)"), -13);
    public static final Declaration staticBaseUriDecl = makeBuiltin("static-base-uri", -14);
    public static final Declaration xsQNameDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "QName"), -35);
    public static final Declaration xsQNameIgnoreDefaultDecl = makeBuiltin(Symbol.make(XQuery.SCHEMA_NAMESPACE, "(QName-ignore-default)"), -36);
    public Namespace[] functionNamespacePath;
    private Declaration moduleDecl;
    public XQParser parser;

    public static Declaration makeBuiltin(String name, int code) {
        return makeBuiltin(Symbol.make(XQuery.XQUERY_FUNCTION_NAMESPACE, name, "fn"), code);
    }

    public static Declaration makeBuiltin(Symbol name, int code) {
        Declaration decl = new Declaration((Object) name);
        decl.setProcedureDecl(true);
        decl.setCode(code);
        return decl;
    }

    public XQResolveNames() {
        this(null);
    }

    void pushBuiltin(String name, int code) {
        this.lookup.push(makeBuiltin(name, code));
    }

    public XQResolveNames(Compilation comp) {
        super(comp);
        this.functionNamespacePath = XQuery.defaultFunctionNamespacePath;
        this.lookup.push(lastDecl);
        this.lookup.push(xsQNameDecl);
        this.lookup.push(staticBaseUriDecl);
        pushBuiltin("position", -2);
        pushBuiltin("compare", -4);
        pushBuiltin("distinct-values", -5);
        pushBuiltin("local-name", -6);
        pushBuiltin("name", -24);
        pushBuiltin("namespace-uri", -7);
        pushBuiltin("root", -32);
        pushBuiltin("base-uri", -11);
        pushBuiltin("lang", -23);
        pushBuiltin("resolve-uri", -12);
        pushBuiltin("collection", -8);
        pushBuiltin("doc", -9);
        pushBuiltin("document", -9);
        pushBuiltin("doc-available", -10);
        pushBuiltin("index-of", -15);
        pushBuiltin(PropertyTypeConstants.PROPERTY_TYPE_STRING, -16);
        pushBuiltin("normalize-space", -17);
        pushBuiltin("unordered", -18);
        pushBuiltin("deep-equal", -25);
        pushBuiltin("min", -26);
        pushBuiltin("max", -27);
        pushBuiltin("number", -28);
        pushBuiltin("default-collation", -29);
        pushBuiltin("id", -30);
        pushBuiltin("idref", -31);
    }

    protected void push(ScopeExp exp) {
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            push(decl);
        }
    }

    void push(Declaration decl) {
        Compilation comp = getCompilation();
        Object symbol = decl.getSymbol();
        boolean function = decl.isProcedureDecl();
        if (symbol instanceof String) {
            if (decl.getLineNumber() <= 0 || comp == null) {
                symbol = this.parser.namespaceResolve((String) symbol, function);
            } else {
                String saveFilename = comp.getFileName();
                int saveLine = comp.getLineNumber();
                int saveColumn = comp.getColumnNumber();
                comp.setLocation(decl);
                symbol = this.parser.namespaceResolve((String) symbol, function);
                comp.setLine(saveFilename, saveLine, saveColumn);
            }
            if (symbol != null) {
                decl.setName(symbol);
            } else {
                return;
            }
        }
        Declaration old = this.lookup.lookup(symbol, XQuery.instance.getNamespaceOf(decl));
        if (old != null) {
            if (decl.context == old.context) {
                ScopeExp.duplicateDeclarationError(old, decl, comp);
            } else if (XQParser.warnHidePreviousDeclaration && !((symbol instanceof Symbol) && ((Symbol) symbol).getNamespace() == null)) {
                comp.error('w', decl, "declaration ", " hides previous declaration");
            }
        }
        this.lookup.push(decl);
    }

    Declaration flookup(Symbol sym) {
        Location loc = XQuery.xqEnvironment.lookup(sym, EnvironmentKey.FUNCTION);
        if (loc == null) {
            return null;
        }
        loc = loc.getBase();
        if (loc instanceof StaticFieldLocation) {
            Declaration decl = ((StaticFieldLocation) loc).getDeclaration();
            if (decl != null) {
                return decl;
            }
        }
        Object val = loc.get(null);
        return val != null ? procToDecl(sym, val) : null;
    }

    protected Expression visitReferenceExp(ReferenceExp exp, Void ignored) {
        return visitReferenceExp(exp, (ApplyExp) null);
    }

    protected Expression visitReferenceExp(ReferenceExp exp, ApplyExp call) {
        if (exp.getBinding() != null) {
            return exp;
        }
        Symbol symbol = exp.getSymbol();
        boolean needFunction = exp.isProcedureName();
        boolean needType = exp.getFlag(16);
        int namespace = call == null ? 1 : XQuery.namespaceForFunctions(call.getArgCount());
        Declaration decl = this.lookup.lookup((Object) symbol, namespace);
        if (decl == null) {
            String name;
            if (symbol instanceof Symbol) {
                Symbol sym = symbol;
                if ("".equals(sym.getNamespaceURI())) {
                    String mname;
                    name = sym.getLocalName();
                    if ("request".equals(name)) {
                        mname = "getCurrentRequest";
                    } else if ("response".equals(name)) {
                        mname = "getCurrentResponse";
                    } else {
                        mname = null;
                    }
                    if (mname != null) {
                        return new ApplyExp(ClassType.make("gnu.kawa.servlet.ServletRequestContext").getDeclaredMethod(mname, 0), Expression.noExpressions);
                    }
                }
            }
            if (symbol instanceof Symbol) {
                decl = flookup(symbol);
            } else {
                Object sym2;
                name = (String) symbol;
                if (name.indexOf(58) < 0) {
                    name = name.intern();
                    if (needFunction) {
                        int i = 0;
                        while (i < this.functionNamespacePath.length) {
                            sym2 = this.functionNamespacePath[i].getSymbol(name);
                            decl = this.lookup.lookup(sym2, namespace);
                            if (decl == null) {
                                decl = flookup(sym2);
                                if (decl != null) {
                                    break;
                                }
                                i++;
                            } else {
                                break;
                            }
                        }
                    }
                }
                if (decl == null) {
                    sym2 = this.parser.namespaceResolve(name, needFunction);
                    if (sym2 != null) {
                        decl = this.lookup.lookup(sym2, namespace);
                        if (decl == null && (needFunction || needType)) {
                            String uri = sym2.getNamespaceURI();
                            Type type = null;
                            if (XQuery.SCHEMA_NAMESPACE.equals(uri)) {
                                type = XQuery.getStandardType(sym2.getName());
                            } else if (needType && uri == "" && !getCompilation().isPedantic()) {
                                type = Scheme.string2Type(sym2.getName());
                            }
                            if (type != null) {
                                return new QuoteExp(type).setLine((Expression) exp);
                            }
                            if (uri != null && uri.length() > 6 && uri.startsWith("class:")) {
                                return CompileNamedPart.makeExp(ClassType.make(uri.substring(6)), sym2.getName());
                            }
                            decl = flookup(sym2);
                        }
                    }
                }
            }
        }
        if (decl != null) {
            exp.setBinding(decl);
            return exp;
        } else if (needFunction) {
            error('e', "unknown function " + symbol);
            return exp;
        } else if (needType) {
            this.messages.error('e', exp, "unknown type " + symbol, "XPST0051");
            return exp;
        } else {
            this.messages.error('e', exp, "unknown variable $" + symbol, "XPST0008");
            return exp;
        }
    }

    protected Expression visitSetExp(SetExp exp, Void ignored) {
        Expression result = super.visitSetExp(exp, ignored);
        Declaration decl = exp.getBinding();
        if (!(decl == null || getCompilation().immediate)) {
            Object name = decl.getSymbol();
            if ((name instanceof Symbol) && XQuery.LOCAL_NAMESPACE.equals(((Symbol) name).getNamespaceURI())) {
                Expression new_value = exp.getNewValue();
                if (!((new_value instanceof ApplyExp) && ((ApplyExp) new_value).getFunction() == XQParser.getExternalFunction)) {
                    decl.setFlag(16777216);
                    decl.setPrivate(true);
                }
            }
        }
        return result;
    }

    private Expression visitStatements(Expression exp) {
        if (exp instanceof BeginExp) {
            BeginExp bbody = (BeginExp) exp;
            Expression[] exps = bbody.getExpressions();
            int nexps = bbody.getExpressionCount();
            for (int i = 0; i < nexps; i++) {
                exps[i] = visitStatements(exps[i]);
            }
            return exp;
        } else if (!(exp instanceof SetExp)) {
            return (Expression) visit(exp, null);
        } else {
            Declaration decl = this.moduleDecl;
            SetExp sexp = (SetExp) exp;
            exp = visitSetExp(sexp, null);
            if (sexp.isDefining() && sexp.getBinding() == decl) {
                if (!decl.isProcedureDecl()) {
                    push(decl);
                }
                decl = decl.nextDecl();
            }
            this.moduleDecl = decl;
            return exp;
        }
    }

    public void resolveModule(ModuleExp exp) {
        Declaration decl;
        this.currentLambda = exp;
        for (decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.isProcedureDecl()) {
                push(decl);
            }
        }
        this.moduleDecl = exp.firstDecl();
        exp.body = visitStatements(exp.body);
        for (decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.getSymbol() != null) {
                this.lookup.removeSubsumed(decl);
            }
        }
    }

    Expression getCollator(Expression[] args, int argno) {
        if (args == null || args.length <= argno) {
            NamedCollator coll = this.parser.defaultCollator;
            return coll == null ? QuoteExp.nullExp : new QuoteExp(coll);
        } else {
            return new ApplyExp(ClassType.make("gnu.xquery.util.NamedCollator").getDeclaredMethod("find", 1), new Expression[]{args[argno]});
        }
    }

    Expression withCollator(Method method, Expression[] args, String name, int minArgs) {
        return withCollator(new QuoteExp(new PrimProcedure(method)), args, name, minArgs);
    }

    Expression withCollator(Expression function, Expression[] args, String name, int minArgs) {
        String err = WrongArguments.checkArgCount(name, minArgs, minArgs + 1, args.length);
        if (err != null) {
            return getCompilation().syntaxError(err);
        }
        Expression[] xargs = new Expression[(minArgs + 1)];
        System.arraycopy(args, 0, xargs, 0, minArgs);
        xargs[minArgs] = getCollator(args, minArgs);
        return new ApplyExp(function, xargs);
    }

    Expression withContext(Method method, Expression[] args, String name, int minArgs) {
        String err = WrongArguments.checkArgCount(name, minArgs, minArgs + 1, args.length);
        if (err != null) {
            return getCompilation().syntaxError(err);
        }
        if (args.length == minArgs) {
            Expression[] xargs = new Expression[(minArgs + 1)];
            System.arraycopy(args, 0, xargs, 0, minArgs);
            Declaration dot = this.lookup.lookup(XQParser.DOT_VARNAME, false);
            if (dot == null) {
                String message = "undefined context for " + name;
                this.messages.error('e', message, "XPDY0002");
                return new ErrorExp(message);
            }
            xargs[minArgs] = new ReferenceExp(dot);
            args = xargs;
        }
        return new ApplyExp(method, args);
    }

    private Expression checkArgCount(Expression[] args, Declaration decl, int min, int max) {
        String err = WrongArguments.checkArgCount("fn:" + decl.getName(), min, max, args.length);
        if (err == null) {
            return null;
        }
        return getCompilation().syntaxError(err);
    }

    protected Expression visitApplyExp(ApplyExp exp, Void ignored) {
        Expression[] args;
        Expression app;
        int i;
        Symbol sym;
        Expression func = exp.getFunction();
        NamespaceBinding namespaceSave = this.parser.constructorNamespaces;
        MakeElement proc = exp.getFunctionValue();
        if (proc instanceof MakeElement) {
            MakeElement mk = proc;
            NamespaceBinding nschain = NamespaceBinding.nconc(mk.getNamespaceNodes(), namespaceSave);
            mk.setNamespaceNodes(nschain);
            this.parser.constructorNamespaces = nschain;
        }
        if (func instanceof ReferenceExp) {
            func = visitReferenceExp((ReferenceExp) func, exp);
        } else {
            func = (Expression) visit(func, ignored);
        }
        exp.setFunction(func);
        visitExps(exp.getArgs(), ignored);
        this.parser.constructorNamespaces = namespaceSave;
        func = exp.getFunction();
        if (func instanceof ReferenceExp) {
            Declaration decl = ((ReferenceExp) func).getBinding();
            if (decl != null) {
                int code = decl.getCode();
                if (code < 0) {
                    Expression err;
                    String msg;
                    Method meth;
                    Expression base;
                    Expression aexp;
                    switch (code) {
                        case XS_QNAME_IGNORE_DEFAULT_BUILTIN /*-36*/:
                        case XS_QNAME_BUILTIN /*-35*/:
                            args = exp.getArgs();
                            err = checkArgCount(args, decl, 1, 1);
                            if (err != null) {
                                return err;
                            }
                            NamespaceBinding constructorNamespaces = this.parser.constructorNamespaces;
                            if (code == -36) {
                                constructorNamespaces = new NamespaceBinding(null, "", constructorNamespaces);
                            }
                            if (args[0] instanceof QuoteExp) {
                                try {
                                    return new QuoteExp(QNameUtils.resolveQName(((QuoteExp) args[0]).getValue(), constructorNamespaces, this.parser.prologNamespaces));
                                } catch (RuntimeException ex) {
                                    return getCompilation().syntaxError(ex.getMessage());
                                }
                            }
                            app = new ApplyExp(ClassType.make("gnu.xquery.util.QNameUtils").getDeclaredMethod("resolveQName", 3), new Expression[]{args[0], new QuoteExp(constructorNamespaces), new QuoteExp(this.parser.prologNamespaces)});
                            app.setFlag(4);
                            return app;
                        case CASTABLE_AS_BUILTIN /*-34*/:
                        case CAST_AS_BUILTIN /*-33*/:
                            args = exp.getArgs();
                            Expression texp = args[code == -33 ? 0 : 1];
                            Expression qexp = texp;
                            if (texp instanceof ApplyExp) {
                                ApplyExp taexp = (ApplyExp) texp;
                                if (taexp.getFunction().valueIfConstant() == XQParser.proc_OccurrenceType_getInstance) {
                                    qexp = taexp.getArg(0);
                                }
                            }
                            Type value = qexp.valueIfConstant();
                            msg = null;
                            if (value == SingletonType.getInstance()) {
                                msg = "type to 'cast as' or 'castable as' must be atomic";
                            } else if (value == XDataType.anyAtomicType) {
                                msg = "type to 'cast as' or 'castable as' cannot be anyAtomicType";
                            } else if (value == XDataType.anySimpleType) {
                                msg = "type to 'cast as' or 'castable as' cannot be anySimpleType";
                            } else if (value == XDataType.untypedType) {
                                msg = "type to 'cast as' or 'castable as' cannot be untyped";
                            } else if (value == XDataType.NotationType) {
                                msg = "type to 'cast as' or 'castable as' cannot be NOTATION";
                            }
                            if (msg != null) {
                                this.messages.error('e', texp, msg, "XPST0080");
                            }
                            boolean toQName = value == Compilation.typeSymbol && !(texp instanceof ApplyExp);
                            if (code == -33) {
                                if (toQName) {
                                    return visitApplyExp(XQParser.castQName(args[1], true), ignored);
                                }
                                func = XQParser.makeFunctionExp("gnu.xquery.util.CastAs", "castAs");
                            } else if (toQName && (args[0] instanceof QuoteExp)) {
                                try {
                                    QNameUtils.resolveQName(((QuoteExp) args[0]).getValue(), this.parser.constructorNamespaces, this.parser.prologNamespaces);
                                    return XQuery.trueExp;
                                } catch (RuntimeException e) {
                                    return XQuery.falseExp;
                                }
                            } else {
                                func = XQParser.makeFunctionExp("gnu.xquery.lang.XQParser", "castableAs");
                            }
                            return new ApplyExp(func, args).setLine((Expression) exp);
                        case ROOT_BUILTIN /*-32*/:
                            return withContext(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("root", 1), exp.getArgs(), "fn:root", 0);
                        case IDREF_BUILTIN /*-31*/:
                            return withContext(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("idref", 2), exp.getArgs(), "fn:idref", 1);
                        case ID_BUILTIN /*-30*/:
                            return withContext(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("id$X", 3), exp.getArgs(), "fn:id", 1);
                        case DEFAULT_COLLATION_BUILTIN /*-29*/:
                            err = checkArgCount(exp.getArgs(), decl, 0, 0);
                            if (err != null) {
                                return err;
                            }
                            NamedCollator coll = this.parser.defaultCollator;
                            return QuoteExp.getInstance(coll != null ? coll.getName() : NamedCollator.UNICODE_CODEPOINT_COLLATION);
                        case NUMBER_BUILTIN /*-28*/:
                            return withContext(ClassType.make("gnu.xquery.util.NumberValue").getDeclaredMethod("numberValue", 1), exp.getArgs(), "fn:number", 0);
                        case MAX_BUILTIN /*-27*/:
                            return withCollator(ClassType.make("gnu.xquery.util.MinMax").getDeclaredMethod("max", 2), exp.getArgs(), "fn:max", 1);
                        case MIN_BUILTIN /*-26*/:
                            return withCollator(ClassType.make("gnu.xquery.util.MinMax").getDeclaredMethod("min", 2), exp.getArgs(), "fn:min", 1);
                        case DEEP_EQUAL_BUILTIN /*-25*/:
                            return withCollator(ClassType.make("gnu.xquery.util.SequenceUtils").getDeclaredMethod("deepEqual", 3), exp.getArgs(), "fn:deep-equal", 2);
                        case NAME_BUILTIN /*-24*/:
                            return withContext(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("name", 1), exp.getArgs(), "fn:name", 0);
                        case LANG_BUILTIN /*-23*/:
                            return withContext(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("lang", 2), exp.getArgs(), "fn:lang", 1);
                        case UNORDERED_BUILTIN /*-18*/:
                            args = exp.getArgs();
                            err = checkArgCount(args, decl, 1, 1);
                            if (err == null) {
                                return args[0];
                            }
                            return err;
                        case NORMALIZE_SPACE_BUILTIN /*-17*/:
                            return withContext(ClassType.make("gnu.xquery.util.StringUtils").getDeclaredMethod("normalizeSpace", 1), exp.getArgs(), "fn:normalize-space", 0);
                        case STRING_BUILTIN /*-16*/:
                            return withContext(ClassType.make("gnu.xml.TextUtils").getDeclaredMethod("asString", 1), exp.getArgs(), "fn:string", 0);
                        case INDEX_OF_BUILTIN /*-15*/:
                            return withCollator(ClassType.make("gnu.xquery.util.SequenceUtils").getDeclaredMethod("indexOf$X", 4), exp.getArgs(), "fn:index-of", 2);
                        case STATIC_BASE_URI_BUILTIN /*-14*/:
                            err = checkArgCount(exp.getArgs(), decl, 0, 0);
                            if (err == null) {
                                return getBaseUriExpr();
                            }
                            return err;
                        case RESOLVE_PREFIX_BUILTIN /*-13*/:
                            args = exp.getArgs();
                            err = checkArgCount(args, decl, 1, 1);
                            if (err != null) {
                                return err;
                            }
                            if (args[0] instanceof QuoteExp) {
                                String val = ((QuoteExp) args[0]).getValue();
                                String prefix = val == null ? null : val.toString();
                                val = QNameUtils.lookupPrefix(prefix, this.parser.constructorNamespaces, this.parser.prologNamespaces);
                                if (val == null) {
                                    return getCompilation().syntaxError("unknown namespace prefix '" + prefix + "'");
                                }
                                return new QuoteExp(val);
                            }
                            app = new ApplyExp(new PrimProcedure(ClassType.make("gnu.xquery.util.QNameUtils").getDeclaredMethod("resolvePrefix", 3)), new Expression[]{args[0], new QuoteExp(this.parser.constructorNamespaces), new QuoteExp(this.parser.prologNamespaces)});
                            app.setFlag(4);
                            return app;
                        case RESOLVE_URI_BUILTIN /*-12*/:
                            args = exp.getArgs();
                            err = checkArgCount(args, decl, 1, 2);
                            if (err != null) {
                                return err;
                            }
                            Expression[] margs = new Expression[2];
                            margs[0] = args[0];
                            if (args.length == 1) {
                                margs[1] = getBaseUriExpr();
                            } else {
                                margs[1] = args[1];
                            }
                            return new ApplyExp(ClassType.make("gnu.xquery.util.QNameUtils").getDeclaredMethod("resolveURI", 2), margs);
                        case BASE_URI_BUILTIN /*-11*/:
                            return withContext(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("baseUri", 1), exp.getArgs(), "fn:base-uri", 0);
                        case DOC_AVAILABLE_BUILTIN /*-10*/:
                        case DOC_BUILTIN /*-9*/:
                            String mname;
                            args = exp.getArgs();
                            ClassType cl = ClassType.make("gnu.xquery.util.NodeUtils");
                            if (code == -9) {
                                mname = "docCached";
                                if (XQParser.warnOldVersion && "document".equals(decl.getName())) {
                                    getCompilation().error('w', "replace 'document' by 'doc'");
                                }
                            } else {
                                mname = "availableCached";
                            }
                            meth = cl.getDeclaredMethod(mname, 2);
                            err = checkArgCount(args, decl, 1, 1);
                            if (err != null) {
                                return err;
                            }
                            base = getBaseUriExpr();
                            aexp = new ApplyExp(meth, new Expression[]{args[0], base});
                            if (code == -9) {
                                aexp.setType(NodeType.documentNodeTest);
                            } else {
                                aexp.setType(XDataType.booleanType);
                            }
                            return aexp;
                        case COLLECTION_BUILTIN /*-8*/:
                            args = exp.getArgs();
                            meth = ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("collection", 2);
                            err = checkArgCount(args, decl, 0, 1);
                            if (err != null) {
                                return err;
                            }
                            base = getBaseUriExpr();
                            Expression uri = args.length > 0 ? args[0] : QuoteExp.voidExp;
                            aexp = new ApplyExp(meth, new Expression[]{uri, base});
                            aexp.setType(NodeType.documentNodeTest);
                            return aexp;
                        case NAMESPACE_URI_BUILTIN /*-7*/:
                            return withContext(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("namespaceURI", 1), exp.getArgs(), "fn:namespace-uri", 0);
                        case LOCAL_NAME_BUILTIN /*-6*/:
                            return withContext(ClassType.make("gnu.xquery.util.NodeUtils").getDeclaredMethod("localName", 1), exp.getArgs(), "fn:local-name", 0);
                        case DISTINCT_VALUES_BUILTIN /*-5*/:
                            return withCollator(ClassType.make("gnu.xquery.util.DistinctValues").getDeclaredMethod("distinctValues$X", 3), exp.getArgs(), "fn:distinct-values", 1);
                        case -4:
                            return withCollator(ClassType.make("gnu.xquery.util.StringUtils").getDeclaredMethod("compare", 3), exp.getArgs(), "fn:compare", 2);
                        case -3:
                            Compilation comp = getCompilation();
                            args = exp.getArgs();
                            i = 0;
                            while (i < args.length - 1) {
                                Symbol psymbol = this.parser.namespaceResolve((String) ((QuoteExp) args[i]).getValue(), false);
                                if (psymbol != null) {
                                    if (psymbol.getNamespaceURI().length() == 0) {
                                        comp.error('e', "pragma name cannot be in the empty namespace");
                                    } else {
                                        Expression replacement = checkPragma(psymbol, args[i + 1]);
                                        if (replacement != null) {
                                            return replacement;
                                        }
                                    }
                                }
                                i += 2;
                            }
                            if (i < args.length) {
                                return args[args.length - 1];
                            }
                            msg = "no recognized pragma or default in extension expression";
                            getMessages().error('e', msg, "XQST0079");
                            return new ErrorExp(msg);
                        case -2:
                        case -1:
                            sym = code == -1 ? XQParser.LAST_VARNAME : XQParser.POSITION_VARNAME;
                            decl = this.lookup.lookup((Object) sym, false);
                            if (decl == null) {
                                error('e', "undefined context for " + sym.getName());
                            } else {
                                decl.setCanRead(true);
                            }
                            return new ReferenceExp(sym, decl);
                    }
                }
            }
        }
        proc = exp.getFunctionValue();
        if (proc instanceof Type) {
            if (exp.getArgs().length != 1) {
                this.messages.error('e', "type constructor requires a single argument");
                return exp;
            }
            return new ApplyExp(XQParser.makeFunctionExp("gnu.xquery.util.CastAs", "castAs"), new Expression[]{exp.getFunction(), exp.getArgs()[0]});
        }
        if (proc instanceof MakeElement) {
            MakeElement make = proc;
            NamespaceBinding nsBindings = make.getNamespaceNodes();
            Symbol tag = make.tag;
            if (tag == null) {
                tag = MakeElement.getTagName(exp);
            }
            nsBindings = maybeAddNamespace(tag, false, nsBindings);
            args = exp.getArgs();
            Symbol[] attrSyms = new Symbol[args.length];
            int nattrSyms = 0;
            for (Expression arg : args) {
                if (arg instanceof ApplyExp) {
                    app = (ApplyExp) arg;
                    if (app.getFunction() == MakeAttribute.makeAttributeExp) {
                        sym = MakeElement.getTagName(app);
                        if (sym != null) {
                            for (int j = 0; j != nattrSyms; j++) {
                                if (sym.equals(attrSyms[j])) {
                                    getCompilation().setLine(app);
                                    Symbol elementSym = MakeElement.getTagName(exp);
                                    this.messages.error('e', XMLFilter.duplicateAttributeMessage(sym, elementSym == null ? null : elementSym.toString()), "XQST0040");
                                }
                            }
                            int nattrSyms2 = nattrSyms + 1;
                            attrSyms[nattrSyms] = sym;
                            nsBindings = maybeAddNamespace(sym, true, nsBindings);
                            nattrSyms = nattrSyms2;
                        }
                    }
                }
            }
            if (nsBindings != null) {
                make.setNamespaceNodes(nsBindings);
            }
        }
        return exp;
    }

    public Expression checkPragma(Symbol name, Expression contents) {
        return null;
    }

    Expression getBaseUriExpr() {
        Compilation comp = getCompilation();
        String staticBaseUri = this.parser.getStaticBaseUri();
        if (staticBaseUri != null) {
            return QuoteExp.getInstance(staticBaseUri);
        }
        return GetModuleClass.getModuleClassURI(comp);
    }

    static NamespaceBinding maybeAddNamespace(Symbol qname, boolean isAttribute, NamespaceBinding bindings) {
        if (qname == null) {
            return bindings;
        }
        String prefix = qname.getPrefix();
        String uri = qname.getNamespaceURI();
        if (prefix == "") {
            prefix = null;
        }
        if (uri == "") {
            uri = null;
        }
        return (isAttribute && prefix == null && uri == null) ? bindings : NamespaceBinding.maybeAdd(prefix, uri, bindings);
    }

    static Declaration procToDecl(Object symbol, Object val) {
        Declaration decl = new Declaration(symbol);
        decl.setProcedureDecl(true);
        decl.noteValue(new QuoteExp(val));
        decl.setFlag(16384);
        return decl;
    }
}

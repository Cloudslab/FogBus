package kawa.lang;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ErrorExp;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.Keyword;
import gnu.expr.LambdaExp;
import gnu.expr.Language;
import gnu.expr.LetExp;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.NameLookup;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.Special;
import gnu.kawa.functions.AppendValues;
import gnu.kawa.functions.CompileNamedPart;
import gnu.kawa.functions.GetNamedPart;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.xml.MakeAttribute;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.PairWithPosition;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.Namespace;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.text.SourceLocator;
import gnu.text.SourceMessages;
import gnu.xml.NamespaceBinding;
import java.util.Stack;
import java.util.Vector;
import kawa.standard.begin;
import kawa.standard.require;

public class Translator extends Compilation {
    private static Expression errorExp = new ErrorExp("unknown syntax error");
    public static final Declaration getNamedPartDecl = Declaration.getDeclarationFromStatic("gnu.kawa.functions.GetNamedPart", "getNamedPart");
    public LambdaExp curMethodLambda;
    public Macro currentMacroDefinition;
    Syntax currentSyntax;
    private Environment env = Environment.getCurrent();
    public int firstForm;
    public Stack formStack = new Stack();
    Declaration macroContext;
    public Declaration matchArray;
    Vector notedAccess;
    public PatternScope patternScope;
    public Object pendingForm;
    PairWithPosition positionPair;
    Stack renamedAliasStack;
    public Declaration templateScopeDecl;
    public NamespaceBinding xmlElementNamespaces = NamespaceBinding.predefinedXML;

    private void rewriteBody(gnu.lists.LList r4) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.ssa.SSATransform.placePhi(SSATransform.java:82)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:50)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = this;
    L_0x0000:
        r2 = gnu.lists.LList.Empty;
        if (r4 == r2) goto L_0x0021;
    L_0x0004:
        r0 = r4;
        r0 = (gnu.lists.Pair) r0;
        r1 = r3.pushPositionOf(r0);
        r2 = r0.getCar();	 Catch:{ all -> 0x001c }
        r3.rewriteInBody(r2);	 Catch:{ all -> 0x001c }
        r3.popPositionOf(r1);
        r4 = r0.getCdr();
        r4 = (gnu.lists.LList) r4;
        goto L_0x0000;
    L_0x001c:
        r2 = move-exception;
        r3.popPositionOf(r1);
        throw r2;
    L_0x0021:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.rewriteBody(gnu.lists.LList):void");
    }

    static {
        LispLanguage.getNamedPartLocation.setDeclaration(getNamedPartDecl);
    }

    public Translator(Language language, SourceMessages messages, NameLookup lexical) {
        super(language, messages, lexical);
    }

    public final Environment getGlobalEnvironment() {
        return this.env;
    }

    public Expression parse(Object input) {
        return rewrite(input);
    }

    public final Expression rewrite_car(Pair pair, SyntaxForm syntax) {
        if (syntax == null || syntax.getScope() == this.current_scope || (pair.getCar() instanceof SyntaxForm)) {
            return rewrite_car(pair, false);
        }
        ScopeExp save_scope = this.current_scope;
        try {
            setCurrentScope(syntax.getScope());
            Expression rewrite_car = rewrite_car(pair, false);
            return rewrite_car;
        } finally {
            setCurrentScope(save_scope);
        }
    }

    public final Expression rewrite_car(Pair pair, boolean function) {
        Object car = pair.getCar();
        if (pair instanceof PairWithPosition) {
            return rewrite_with_position(car, function, (PairWithPosition) pair);
        }
        return rewrite(car, function);
    }

    public Syntax getCurrentSyntax() {
        return this.currentSyntax;
    }

    Expression apply_rewrite(Syntax syntax, Pair form) {
        Expression exp = errorExp;
        Syntax saveSyntax = this.currentSyntax;
        this.currentSyntax = syntax;
        try {
            exp = syntax.rewriteForm(form, this);
            return exp;
        } finally {
            this.currentSyntax = saveSyntax;
        }
    }

    static ReferenceExp getOriginalRef(Declaration decl) {
        if (!(decl == null || !decl.isAlias() || decl.isIndirectBinding())) {
            Expression value = decl.getValue();
            if (value instanceof ReferenceExp) {
                return (ReferenceExp) value;
            }
        }
        return null;
    }

    public final boolean selfEvaluatingSymbol(Object obj) {
        return ((LispLanguage) getLanguage()).selfEvaluatingSymbol(obj);
    }

    public final boolean matches(Object form, String literal) {
        return matches(form, null, literal);
    }

    public boolean matches(Object form, SyntaxForm syntax, String literal) {
        if (syntax != null) {
        }
        if (form instanceof SyntaxForm) {
            form = ((SyntaxForm) form).getDatum();
        }
        if ((form instanceof SimpleSymbol) && !selfEvaluatingSymbol(form)) {
            ReferenceExp rexp = getOriginalRef(this.lexical.lookup(form, -1));
            if (rexp != null) {
                form = rexp.getSymbol();
            }
        }
        return (form instanceof SimpleSymbol) && ((Symbol) form).getLocalPart() == literal;
    }

    public boolean matches(Object form, SyntaxForm syntax, Symbol literal) {
        Symbol form2;
        if (syntax != null) {
        }
        if (form instanceof SyntaxForm) {
            form2 = ((SyntaxForm) form).getDatum();
        }
        if ((form2 instanceof SimpleSymbol) && !selfEvaluatingSymbol(form2)) {
            ReferenceExp rexp = getOriginalRef(this.lexical.lookup((Object) form2, -1));
            if (rexp != null) {
                form2 = rexp.getSymbol();
            }
        }
        return form2 == literal;
    }

    public Object matchQuoted(Pair pair) {
        if (matches(pair.getCar(), LispLanguage.quote_sym) && (pair.getCdr() instanceof Pair)) {
            pair = (Pair) pair.getCdr();
            if (pair.getCdr() == LList.Empty) {
                return pair.getCar();
            }
        }
        return null;
    }

    public Declaration lookup(Object name, int namespace) {
        Declaration decl = this.lexical.lookup(name, namespace);
        return (decl == null || !getLanguage().hasNamespace(decl, namespace)) ? currentModule().lookup(name, getLanguage(), namespace) : decl;
    }

    public Declaration lookupGlobal(Object name) {
        return lookupGlobal(name, -1);
    }

    public Declaration lookupGlobal(Object name, int namespace) {
        ModuleExp module = currentModule();
        Declaration decl = module.lookup(name, getLanguage(), namespace);
        if (decl != null) {
            return decl;
        }
        decl = module.getNoDefine(name);
        decl.setIndirectBinding(true);
        return decl;
    }

    Syntax check_if_Syntax(Declaration decl) {
        Declaration d = Declaration.followAliases(decl);
        Object obj = null;
        Expression dval = d.getValue();
        if (dval != null && d.getFlag(32768)) {
            try {
                if (decl.getValue() instanceof ReferenceExp) {
                    Declaration context = ((ReferenceExp) decl.getValue()).contextDecl();
                    if (context != null) {
                        this.macroContext = context;
                    } else if (this.current_scope instanceof TemplateScope) {
                        this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                    }
                } else if (this.current_scope instanceof TemplateScope) {
                    this.macroContext = ((TemplateScope) this.current_scope).macroContext;
                }
                obj = dval.eval(this.env);
            } catch (Throwable ex) {
                ex.printStackTrace();
                error('e', "unable to evaluate macro for " + decl.getSymbol());
            }
        } else if (decl.getFlag(32768) && !decl.needsContext()) {
            obj = StaticFieldLocation.make(decl).get(null);
        }
        if (obj instanceof Syntax) {
            return (Syntax) obj;
        }
        return null;
    }

    public Expression rewrite_pair(Pair p, boolean function) {
        Object proc;
        Symbol sym;
        Expression func = rewrite_car(p, true);
        if (func instanceof QuoteExp) {
            proc = func.valueIfConstant();
            if (proc instanceof Syntax) {
                return apply_rewrite((Syntax) proc, p);
            }
        }
        if (func instanceof ReferenceExp) {
            ReferenceExp ref = (ReferenceExp) func;
            Declaration decl = ref.getBinding();
            if (decl == null) {
                Symbol symbol;
                sym = ref.getSymbol();
                if (!(sym instanceof Symbol) || selfEvaluatingSymbol(sym)) {
                    symbol = this.env.getSymbol(sym.toString());
                } else {
                    symbol = sym;
                    String name = symbol.getName();
                }
                proc = this.env.get(symbol, getLanguage().hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, null);
                if (proc instanceof Syntax) {
                    return apply_rewrite((Syntax) proc, p);
                }
                if (proc instanceof AutoloadProcedure) {
                    try {
                        proc = ((AutoloadProcedure) proc).getLoaded();
                    } catch (RuntimeException e) {
                    }
                }
            } else {
                Declaration saveContext = this.macroContext;
                Syntax syntax = check_if_Syntax(decl);
                if (syntax != null) {
                    Expression e2 = apply_rewrite(syntax, p);
                    this.macroContext = saveContext;
                    return e2;
                }
            }
            ref.setProcedureName(true);
            if (getLanguage().hasSeparateFunctionNamespace()) {
                func.setFlag(8);
            }
        }
        Pair cdr = p.getCdr();
        int cdr_length = listLength(cdr);
        if (cdr_length == -1) {
            return syntaxError("circular list is not allowed after " + p.getCar());
        }
        if (cdr_length < 0) {
            return syntaxError("dotted list [" + cdr + "] is not allowed after " + p.getCar());
        }
        boolean mapKeywordsToAttributes = false;
        Stack vec = new Stack();
        ScopeExp save_scope = this.current_scope;
        int i = 0;
        while (i < cdr_length) {
            if (cdr instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) cdr;
                cdr = sf.getDatum();
                setCurrentScope(sf.getScope());
            }
            Pair cdr_pair = cdr;
            Expression arg = rewrite_car(cdr_pair, false);
            i++;
            if (mapKeywordsToAttributes) {
                if ((i & 1) == 0) {
                    arg = new ApplyExp(MakeAttribute.makeAttribute, new Expression[]{(Expression) vec.pop(), arg});
                } else {
                    if (arg instanceof QuoteExp) {
                        Object value = ((QuoteExp) arg).getValue();
                        if ((value instanceof Keyword) && i < cdr_length) {
                            arg = new QuoteExp(((Keyword) value).asSymbol());
                        }
                    }
                    mapKeywordsToAttributes = false;
                }
            }
            vec.addElement(arg);
            cdr = cdr_pair.getCdr();
        }
        Expression[] args = new Expression[vec.size()];
        vec.copyInto(args);
        if (save_scope != this.current_scope) {
            setCurrentScope(save_scope);
        }
        if (!(func instanceof ReferenceExp) || ((ReferenceExp) func).getBinding() != getNamedPartDecl) {
            return ((LispLanguage) getLanguage()).makeApply(func, args);
        }
        Expression part1 = args[0];
        Expression part2 = args[1];
        sym = namespaceResolve(part1, part2);
        if (sym != null) {
            return rewrite(sym, function);
        }
        return CompileNamedPart.makeExp(part1, part2);
    }

    public Namespace namespaceResolvePrefix(Expression context) {
        if (context instanceof ReferenceExp) {
            Namespace namespace;
            ReferenceExp rexp = (ReferenceExp) context;
            Declaration decl = rexp.getBinding();
            if (decl == null || decl.getFlag(65536)) {
                Object rsym = rexp.getSymbol();
                namespace = this.env.get(rsym instanceof Symbol ? (Symbol) rsym : this.env.getSymbol(rsym.toString()), null);
            } else if (decl.isNamespaceDecl()) {
                namespace = decl.getConstantValue();
            } else {
                namespace = null;
            }
            if (namespace instanceof Namespace) {
                Namespace ns = namespace;
                String uri = ns.getName();
                if (uri == null || !uri.startsWith("class:")) {
                    return ns;
                }
                return null;
            }
        }
        return null;
    }

    public Symbol namespaceResolve(Namespace ns, Expression member) {
        if (ns == null || !(member instanceof QuoteExp)) {
            return null;
        }
        return ns.getSymbol(((QuoteExp) member).getValue().toString().intern());
    }

    public Symbol namespaceResolve(Expression context, Expression member) {
        return namespaceResolve(namespaceResolvePrefix(context), member);
    }

    public static Object stripSyntax(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        return obj;
    }

    public static Object safeCar(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (obj instanceof Pair) {
            return stripSyntax(((Pair) obj).getCar());
        }
        return null;
    }

    public static Object safeCdr(Object obj) {
        while (obj instanceof SyntaxForm) {
            obj = ((SyntaxForm) obj).getDatum();
        }
        if (obj instanceof Pair) {
            return stripSyntax(((Pair) obj).getCdr());
        }
        return null;
    }

    public static int listLength(Object obj) {
        int n = 0;
        Object slow = obj;
        LList fast = obj;
        while (true) {
            if (fast instanceof SyntaxForm) {
                fast = ((SyntaxForm) fast).getDatum();
            } else {
                while (slow instanceof SyntaxForm) {
                    slow = ((SyntaxForm) slow).getDatum();
                }
                if (fast == LList.Empty) {
                    return n;
                }
                if (!(fast instanceof Pair)) {
                    return -1 - n;
                }
                n++;
                LList next = ((Pair) fast).getCdr();
                while (next instanceof SyntaxForm) {
                    next = ((SyntaxForm) next).getDatum();
                }
                if (next == LList.Empty) {
                    return n;
                }
                if (!(next instanceof Pair)) {
                    return -1 - n;
                }
                slow = ((Pair) slow).getCdr();
                fast = ((Pair) next).getCdr();
                n++;
                if (fast == slow) {
                    return Integer.MIN_VALUE;
                }
            }
        }
    }

    public void rewriteInBody(Object exp) {
        if (exp instanceof SyntaxForm) {
            SyntaxForm sf = (SyntaxForm) exp;
            ScopeExp save_scope = this.current_scope;
            try {
                setCurrentScope(sf.getScope());
                rewriteInBody(sf.getDatum());
            } finally {
                setCurrentScope(save_scope);
            }
        } else if (exp instanceof Values) {
            Object[] vals = ((Values) exp).getValues();
            for (Object rewriteInBody : vals) {
                rewriteInBody(rewriteInBody);
            }
        } else {
            this.formStack.add(rewrite(exp, false));
        }
    }

    public Expression rewrite(Object exp) {
        return rewrite(exp, false);
    }

    public Object namespaceResolve(Object name) {
        if (!(name instanceof SimpleSymbol) && (name instanceof Pair)) {
            Pair p = (Pair) name;
            if (safeCar(p) == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                p = (Pair) p.getCdr();
                if (p.getCdr() instanceof Pair) {
                    Expression part1 = rewrite(p.getCar());
                    Expression part2 = rewrite(((Pair) p.getCdr()).getCar());
                    Symbol sym = namespaceResolve(part1, part2);
                    if (sym != null) {
                        return sym;
                    }
                    String combinedName = CompileNamedPart.combineName(part1, part2);
                    if (combinedName != null) {
                        return Namespace.EmptyNamespace.getSymbol(combinedName);
                    }
                }
            }
        }
        return name;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public gnu.expr.Expression rewrite(java.lang.Object r36, boolean r37) {
        /*
        r35 = this;
        r0 = r36;
        r0 = r0 instanceof kawa.lang.SyntaxForm;
        r31 = r0;
        if (r31 == 0) goto L_0x003c;
    L_0x0008:
        r29 = r36;
        r29 = (kawa.lang.SyntaxForm) r29;
        r0 = r35;
        r0 = r0.current_scope;
        r26 = r0;
        r31 = r29.getScope();	 Catch:{ all -> 0x0033 }
        r0 = r35;
        r1 = r31;
        r0.setCurrentScope(r1);	 Catch:{ all -> 0x0033 }
        r31 = r29.getDatum();	 Catch:{ all -> 0x0033 }
        r0 = r35;
        r1 = r31;
        r2 = r37;
        r25 = r0.rewrite(r1, r2);	 Catch:{ all -> 0x0033 }
        r0 = r35;
        r1 = r26;
        r0.setCurrentScope(r1);
    L_0x0032:
        return r25;
    L_0x0033:
        r31 = move-exception;
        r0 = r35;
        r1 = r26;
        r0.setCurrentScope(r1);
        throw r31;
    L_0x003c:
        r0 = r36;
        r0 = r0 instanceof gnu.lists.PairWithPosition;
        r31 = r0;
        if (r31 == 0) goto L_0x0055;
    L_0x0044:
        r31 = r36;
        r31 = (gnu.lists.PairWithPosition) r31;
        r0 = r35;
        r1 = r36;
        r2 = r37;
        r3 = r31;
        r25 = r0.rewrite_with_position(r1, r2, r3);
        goto L_0x0032;
    L_0x0055:
        r0 = r36;
        r0 = r0 instanceof gnu.lists.Pair;
        r31 = r0;
        if (r31 == 0) goto L_0x0064;
    L_0x005d:
        r36 = (gnu.lists.Pair) r36;
        r25 = r35.rewrite_pair(r36, r37);
        goto L_0x0032;
    L_0x0064:
        r0 = r36;
        r0 = r0 instanceof gnu.mapping.Symbol;
        r31 = r0;
        if (r31 == 0) goto L_0x0381;
    L_0x006c:
        r31 = r35.selfEvaluatingSymbol(r36);
        if (r31 != 0) goto L_0x0381;
    L_0x0072:
        r0 = r35;
        r0 = r0.lexical;
        r31 = r0;
        r0 = r31;
        r1 = r36;
        r2 = r37;
        r10 = r0.lookup(r1, r2);
        r5 = 0;
        r0 = r35;
        r0 = r0.current_scope;
        r27 = r0;
        if (r10 != 0) goto L_0x0134;
    L_0x008b:
        r11 = -1;
    L_0x008c:
        r0 = r36;
        r0 = r0 instanceof gnu.mapping.Symbol;
        r31 = r0;
        if (r31 == 0) goto L_0x013e;
    L_0x0094:
        r31 = r36;
        r31 = (gnu.mapping.Symbol) r31;
        r31 = r31.hasEmptyNamespace();
        if (r31 == 0) goto L_0x013e;
    L_0x009e:
        r12 = r36.toString();
    L_0x00a2:
        if (r27 == 0) goto L_0x00d2;
    L_0x00a4:
        r0 = r27;
        r0 = r0 instanceof gnu.expr.LambdaExp;
        r31 = r0;
        if (r31 == 0) goto L_0x018b;
    L_0x00ac:
        r0 = r27;
        r0 = r0.outer;
        r31 = r0;
        r0 = r31;
        r0 = r0 instanceof gnu.expr.ClassExp;
        r31 = r0;
        if (r31 == 0) goto L_0x018b;
    L_0x00ba:
        r31 = r27;
        r31 = (gnu.expr.LambdaExp) r31;
        r31 = r31.isClassMethod();
        if (r31 == 0) goto L_0x018b;
    L_0x00c4:
        r0 = r27;
        r0 = r0.outer;
        r31 = r0;
        r31 = gnu.expr.ScopeExp.nesting(r31);
        r0 = r31;
        if (r11 < r0) goto L_0x0143;
    L_0x00d2:
        if (r10 == 0) goto L_0x01cb;
    L_0x00d4:
        r21 = r10.getSymbol();
        r36 = 0;
        r24 = getOriginalRef(r10);
        if (r24 == 0) goto L_0x00ec;
    L_0x00e0:
        r10 = r24.getBinding();
        if (r10 != 0) goto L_0x00ec;
    L_0x00e6:
        r36 = r24.getSymbol();
        r21 = r36;
    L_0x00ec:
        r31 = r36;
    L_0x00ee:
        r30 = r31;
        r30 = (gnu.mapping.Symbol) r30;
        r32 = r35.getLanguage();
        r28 = r32.hasSeparateFunctionNamespace();
        if (r10 == 0) goto L_0x0220;
    L_0x00fc:
        r0 = r35;
        r0 = r0.current_scope;
        r31 = r0;
        r0 = r31;
        r0 = r0 instanceof kawa.lang.TemplateScope;
        r31 = r0;
        if (r31 == 0) goto L_0x01d1;
    L_0x010a:
        r31 = r10.needsContext();
        if (r31 == 0) goto L_0x01d1;
    L_0x0110:
        r0 = r35;
        r0 = r0.current_scope;
        r31 = r0;
        r31 = (kawa.lang.TemplateScope) r31;
        r0 = r31;
        r5 = r0.macroContext;
    L_0x011c:
        if (r10 == 0) goto L_0x035b;
    L_0x011e:
        if (r37 != 0) goto L_0x0328;
    L_0x0120:
        r31 = r10.getConstantValue();
        r0 = r31;
        r0 = r0 instanceof kawa.standard.object;
        r31 = r0;
        if (r31 == 0) goto L_0x0328;
    L_0x012c:
        r31 = java.lang.Object.class;
        r25 = gnu.expr.QuoteExp.getInstance(r31);
        goto L_0x0032;
    L_0x0134:
        r0 = r10.context;
        r31 = r0;
        r11 = gnu.expr.ScopeExp.nesting(r31);
        goto L_0x008c;
    L_0x013e:
        r12 = 0;
        r27 = 0;
        goto L_0x00a2;
    L_0x0143:
        r4 = r27;
        r4 = (gnu.expr.LambdaExp) r4;
        r0 = r27;
        r7 = r0.outer;
        r7 = (gnu.expr.ClassExp) r7;
        r9 = r7.getClassType();
        r22 = gnu.kawa.reflect.SlotGet.lookupMember(r9, r12, r9);
        r0 = r7.clinitMethod;
        r31 = r0;
        r0 = r31;
        if (r4 == r0) goto L_0x016f;
    L_0x015d:
        r0 = r7.initMethod;
        r31 = r0;
        r0 = r31;
        if (r4 == r0) goto L_0x0193;
    L_0x0165:
        r0 = r4.nameDecl;
        r31 = r0;
        r31 = r31.isStatic();
        if (r31 == 0) goto L_0x0193;
    L_0x016f:
        r8 = 1;
    L_0x0170:
        if (r22 != 0) goto L_0x0198;
    L_0x0172:
        if (r8 == 0) goto L_0x0195;
    L_0x0174:
        r20 = 83;
    L_0x0176:
        r0 = r35;
        r0 = r0.language;
        r31 = r0;
        r0 = r20;
        r1 = r31;
        r19 = gnu.kawa.reflect.ClassMethods.getMethods(r9, r12, r0, r9, r1);
        r0 = r19;
        r0 = r0.length;
        r31 = r0;
        if (r31 != 0) goto L_0x0198;
    L_0x018b:
        r0 = r27;
        r0 = r0.outer;
        r27 = r0;
        goto L_0x00a2;
    L_0x0193:
        r8 = 0;
        goto L_0x0170;
    L_0x0195:
        r20 = 86;
        goto L_0x0176;
    L_0x0198:
        if (r8 == 0) goto L_0x01bd;
    L_0x019a:
        r23 = new gnu.expr.ReferenceExp;
        r0 = r4.outer;
        r31 = r0;
        r31 = (gnu.expr.ClassExp) r31;
        r0 = r31;
        r0 = r0.nameDecl;
        r31 = r0;
        r0 = r23;
        r1 = r31;
        r0.<init>(r1);
    L_0x01af:
        r31 = gnu.expr.QuoteExp.getInstance(r12);
        r0 = r23;
        r1 = r31;
        r25 = gnu.kawa.functions.CompileNamedPart.makeExp(r0, r1);
        goto L_0x0032;
    L_0x01bd:
        r23 = new gnu.expr.ThisExp;
        r31 = r4.firstDecl();
        r0 = r23;
        r1 = r31;
        r0.<init>(r1);
        goto L_0x01af;
    L_0x01cb:
        r21 = r36;
        r31 = r36;
        goto L_0x00ee;
    L_0x01d1:
        r32 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r0 = r32;
        r31 = r10.getFlag(r0);
        if (r31 == 0) goto L_0x011c;
    L_0x01dc:
        r31 = r10.isStatic();
        if (r31 != 0) goto L_0x011c;
    L_0x01e2:
        r27 = r35.currentScope();
    L_0x01e6:
        if (r27 != 0) goto L_0x0203;
    L_0x01e8:
        r31 = new java.lang.Error;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "internal error: missing ";
        r32 = r32.append(r33);
        r0 = r32;
        r32 = r0.append(r10);
        r32 = r32.toString();
        r31.<init>(r32);
        throw r31;
    L_0x0203:
        r0 = r27;
        r0 = r0.outer;
        r31 = r0;
        r0 = r10.context;
        r32 = r0;
        r0 = r31;
        r1 = r32;
        if (r0 != r1) goto L_0x0219;
    L_0x0213:
        r5 = r27.firstDecl();
        goto L_0x011c;
    L_0x0219:
        r0 = r27;
        r0 = r0.outer;
        r27 = r0;
        goto L_0x01e6;
    L_0x0220:
        r0 = r35;
        r0 = r0.env;
        r33 = r0;
        if (r37 == 0) goto L_0x027d;
    L_0x0228:
        if (r28 == 0) goto L_0x027d;
    L_0x022a:
        r32 = gnu.mapping.EnvironmentKey.FUNCTION;
    L_0x022c:
        r0 = r33;
        r1 = r30;
        r2 = r32;
        r18 = r0.lookup(r1, r2);
        if (r18 == 0) goto L_0x023c;
    L_0x0238:
        r18 = r18.getBase();
    L_0x023c:
        r0 = r18;
        r0 = r0 instanceof gnu.kawa.reflect.FieldLocation;
        r32 = r0;
        if (r32 == 0) goto L_0x030a;
    L_0x0244:
        r17 = r18;
        r17 = (gnu.kawa.reflect.FieldLocation) r17;
        r10 = r17.getDeclaration();	 Catch:{ Throwable -> 0x02ce }
        r32 = 0;
        r0 = r35;
        r1 = r32;
        r32 = r0.inlineOk(r1);	 Catch:{ Throwable -> 0x02ce }
        if (r32 != 0) goto L_0x0280;
    L_0x0258:
        r32 = getNamedPartDecl;	 Catch:{ Throwable -> 0x02ce }
        r0 = r32;
        if (r10 == r0) goto L_0x0280;
    L_0x025e:
        r32 = "objectSyntax";
        r33 = r17.getMemberName();	 Catch:{ Throwable -> 0x02ce }
        r32 = r32.equals(r33);	 Catch:{ Throwable -> 0x02ce }
        if (r32 == 0) goto L_0x027a;
    L_0x026a:
        r32 = "kawa.standard.object";
        r33 = r17.getDeclaringClass();	 Catch:{ Throwable -> 0x02ce }
        r33 = r33.getName();	 Catch:{ Throwable -> 0x02ce }
        r32 = r32.equals(r33);	 Catch:{ Throwable -> 0x02ce }
        if (r32 != 0) goto L_0x0280;
    L_0x027a:
        r10 = 0;
        goto L_0x011c;
    L_0x027d:
        r32 = 0;
        goto L_0x022c;
    L_0x0280:
        r0 = r35;
        r0 = r0.immediate;	 Catch:{ Throwable -> 0x02ce }
        r32 = r0;
        if (r32 == 0) goto L_0x02a8;
    L_0x0288:
        r32 = r10.isStatic();	 Catch:{ Throwable -> 0x02ce }
        if (r32 != 0) goto L_0x011c;
    L_0x028e:
        r6 = new gnu.expr.Declaration;	 Catch:{ Throwable -> 0x02ce }
        r32 = "(module-instance)";
        r0 = r32;
        r6.<init>(r0);	 Catch:{ Throwable -> 0x02ce }
        r32 = new gnu.expr.QuoteExp;	 Catch:{ Throwable -> 0x03c7 }
        r33 = r17.getInstance();	 Catch:{ Throwable -> 0x03c7 }
        r32.<init>(r33);	 Catch:{ Throwable -> 0x03c7 }
        r0 = r32;
        r6.setValue(r0);	 Catch:{ Throwable -> 0x03c7 }
        r5 = r6;
        goto L_0x011c;
    L_0x02a8:
        r32 = r10.isStatic();	 Catch:{ Throwable -> 0x02ce }
        if (r32 == 0) goto L_0x02cb;
    L_0x02ae:
        r15 = r17.getRClass();	 Catch:{ Throwable -> 0x02ce }
        if (r15 == 0) goto L_0x02c8;
    L_0x02b4:
        r16 = r15.getClassLoader();	 Catch:{ Throwable -> 0x02ce }
        r0 = r16;
        r0 = r0 instanceof gnu.bytecode.ZipLoader;	 Catch:{ Throwable -> 0x02ce }
        r32 = r0;
        if (r32 != 0) goto L_0x02c8;
    L_0x02c0:
        r0 = r16;
        r0 = r0 instanceof gnu.bytecode.ArrayClassLoader;	 Catch:{ Throwable -> 0x02ce }
        r31 = r0;
        if (r31 == 0) goto L_0x011c;
    L_0x02c8:
        r10 = 0;
        goto L_0x011c;
    L_0x02cb:
        r10 = 0;
        goto L_0x011c;
    L_0x02ce:
        r14 = move-exception;
    L_0x02cf:
        r32 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r33 = new java.lang.StringBuilder;
        r33.<init>();
        r34 = "exception loading '";
        r33 = r33.append(r34);
        r0 = r33;
        r1 = r31;
        r31 = r0.append(r1);
        r33 = "' - ";
        r0 = r31;
        r1 = r33;
        r31 = r0.append(r1);
        r33 = r14.getMessage();
        r0 = r31;
        r1 = r33;
        r31 = r0.append(r1);
        r31 = r31.toString();
        r0 = r35;
        r1 = r32;
        r2 = r31;
        r0.error(r1, r2);
        r10 = 0;
        goto L_0x011c;
    L_0x030a:
        if (r18 == 0) goto L_0x0312;
    L_0x030c:
        r31 = r18.isBound();
        if (r31 != 0) goto L_0x011c;
    L_0x0312:
        r31 = r35.getLanguage();
        r31 = (gnu.kawa.lispexpr.LispLanguage) r31;
        r0 = r31;
        r1 = r30;
        r2 = r35;
        r13 = r0.checkDefaultBinding(r1, r2);
        if (r13 == 0) goto L_0x011c;
    L_0x0324:
        r25 = r13;
        goto L_0x0032;
    L_0x0328:
        r31 = r10.getContext();
        r0 = r31;
        r0 = r0 instanceof kawa.lang.PatternScope;
        r31 = r0;
        if (r31 == 0) goto L_0x035b;
    L_0x0334:
        r31 = new java.lang.StringBuilder;
        r31.<init>();
        r32 = "reference to pattern variable ";
        r31 = r31.append(r32);
        r32 = r10.getName();
        r31 = r31.append(r32);
        r32 = " outside syntax template";
        r31 = r31.append(r32);
        r31 = r31.toString();
        r0 = r35;
        r1 = r31;
        r25 = r0.syntaxError(r1);
        goto L_0x0032;
    L_0x035b:
        r24 = new gnu.expr.ReferenceExp;
        r0 = r24;
        r1 = r21;
        r0.<init>(r1, r10);
        r0 = r24;
        r0.setContextDecl(r5);
        r0 = r24;
        r1 = r35;
        r0.setLine(r1);
        if (r37 == 0) goto L_0x037d;
    L_0x0372:
        if (r28 == 0) goto L_0x037d;
    L_0x0374:
        r31 = 8;
        r0 = r24;
        r1 = r31;
        r0.setFlag(r1);
    L_0x037d:
        r25 = r24;
        goto L_0x0032;
    L_0x0381:
        r0 = r36;
        r0 = r0 instanceof gnu.expr.LangExp;
        r31 = r0;
        if (r31 == 0) goto L_0x039b;
    L_0x0389:
        r36 = (gnu.expr.LangExp) r36;
        r31 = r36.getLangValue();
        r0 = r35;
        r1 = r31;
        r2 = r37;
        r25 = r0.rewrite(r1, r2);
        goto L_0x0032;
    L_0x039b:
        r0 = r36;
        r0 = r0 instanceof gnu.expr.Expression;
        r31 = r0;
        if (r31 == 0) goto L_0x03a9;
    L_0x03a3:
        r36 = (gnu.expr.Expression) r36;
        r25 = r36;
        goto L_0x0032;
    L_0x03a9:
        r31 = gnu.expr.Special.abstractSpecial;
        r0 = r36;
        r1 = r31;
        if (r0 != r1) goto L_0x03b5;
    L_0x03b1:
        r25 = gnu.expr.QuoteExp.abstractExp;
        goto L_0x0032;
    L_0x03b5:
        r0 = r36;
        r1 = r35;
        r31 = kawa.lang.Quote.quote(r0, r1);
        r0 = r31;
        r1 = r35;
        r25 = gnu.expr.QuoteExp.getInstance(r0, r1);
        goto L_0x0032;
    L_0x03c7:
        r14 = move-exception;
        r5 = r6;
        goto L_0x02cf;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lang.Translator.rewrite(java.lang.Object, boolean):gnu.expr.Expression");
    }

    public static void setLine(Expression exp, Object location) {
        if (location instanceof SourceLocator) {
            exp.setLocation((SourceLocator) location);
        }
    }

    public static void setLine(Declaration decl, Object location) {
        if (location instanceof SourceLocator) {
            decl.setLocation((SourceLocator) location);
        }
    }

    public Object pushPositionOf(Object pair) {
        if (pair instanceof SyntaxForm) {
            PairWithPosition pair2 = ((SyntaxForm) pair).getDatum();
        }
        if (!(pair2 instanceof PairWithPosition)) {
            return null;
        }
        Object saved;
        PairWithPosition ppair = pair2;
        if (this.positionPair != null && this.positionPair.getFileName() == getFileName() && this.positionPair.getLineNumber() == getLineNumber() && this.positionPair.getColumnNumber() == getColumnNumber()) {
            saved = this.positionPair;
        } else {
            saved = new PairWithPosition(this, Special.eof, this.positionPair);
        }
        setLine((Object) pair2);
        this.positionPair = ppair;
        return saved;
    }

    public void popPositionOf(Object saved) {
        if (saved != null) {
            setLine(saved);
            this.positionPair = (PairWithPosition) saved;
            if (this.positionPair.getCar() == Special.eof) {
                this.positionPair = (PairWithPosition) this.positionPair.getCdr();
            }
        }
    }

    public void setLineOf(Expression exp) {
        if (!(exp instanceof QuoteExp)) {
            exp.setLocation(this);
        }
    }

    public Type exp2Type(Pair typeSpecPair) {
        Object saved = pushPositionOf(typeSpecPair);
        try {
            Expression texp = InlineCalls.inlineCalls(rewrite_car(typeSpecPair, false), this);
            Type type;
            if (texp instanceof ErrorExp) {
                type = null;
                return type;
            }
            type = getLanguage().getTypeFor(texp);
            if (type == null) {
                try {
                    Object t = texp.eval(this.env);
                    if (t instanceof Class) {
                        type = Type.make((Class) t);
                    } else if (t instanceof Type) {
                        type = (Type) t;
                    }
                } catch (Throwable th) {
                }
            }
            if (type == null) {
                if (texp instanceof ReferenceExp) {
                    error('e', "unknown type name '" + ((ReferenceExp) texp).getName() + '\'');
                } else {
                    error('e', "invalid type spec (must be \"type\" or 'type or <type>)");
                }
                type = Type.pointer_type;
                popPositionOf(saved);
                return type;
            }
            popPositionOf(saved);
            return type;
        } finally {
            popPositionOf(saved);
        }
    }

    public Expression rewrite_with_position(Object exp, boolean function, PairWithPosition pair) {
        Expression result;
        Object saved = pushPositionOf(pair);
        if (exp == pair) {
            try {
                result = rewrite_pair(pair, function);
            } catch (Throwable th) {
                popPositionOf(saved);
            }
        } else {
            result = rewrite(exp, function);
        }
        setLineOf(result);
        popPositionOf(saved);
        return result;
    }

    public static Object wrapSyntax(Object form, SyntaxForm syntax) {
        return (syntax == null || (form instanceof Expression)) ? form : SyntaxForms.fromDatumIfNeeded(form, syntax);
    }

    public Object popForms(int first) {
        int last = this.formStack.size();
        if (last == first) {
            return Values.empty;
        }
        Object elementAt;
        if (last == first + 1) {
            elementAt = this.formStack.elementAt(first);
        } else {
            Values vals = new Values();
            for (int i = first; i < last; i++) {
                vals.writeObject(this.formStack.elementAt(i));
            }
            Values r = vals;
        }
        this.formStack.setSize(first);
        return elementAt;
    }

    public void scanForm(Object st, ScopeExp defs) {
        SyntaxForm sf;
        if (st instanceof SyntaxForm) {
            sf = (SyntaxForm) st;
            ScopeExp save_scope = currentScope();
            try {
                setCurrentScope(sf.getScope());
                int first = this.formStack.size();
                scanForm(sf.getDatum(), defs);
                this.formStack.add(wrapSyntax(popForms(first), sf));
            } finally {
                setCurrentScope(save_scope);
            }
        } else {
            Pair st2;
            if (st instanceof Values) {
                if (st == Values.empty) {
                    st2 = QuoteExp.voidExp;
                } else {
                    Object[] vals = ((Values) st).getValues();
                    for (Object scanForm : vals) {
                        scanForm(scanForm, defs);
                    }
                    return;
                }
            }
            if (st2 instanceof Pair) {
                Pair st_pair = st2;
                Declaration saveContext = this.macroContext;
                Syntax syntax = null;
                ScopeExp savedScope = this.current_scope;
                Object savedPosition = pushPositionOf(st2);
                if ((st2 instanceof SourceLocator) && defs.getLineNumber() < 0) {
                    defs.setLocation((SourceLocator) st2);
                }
                try {
                    begin obj = st_pair.getCar();
                    if (obj instanceof SyntaxForm) {
                        sf = (SyntaxForm) st_pair.getCar();
                        setCurrentScope(sf.getScope());
                        obj = sf.getDatum();
                    }
                    if (obj instanceof Pair) {
                        Pair p = (Pair) obj;
                        if (p.getCar() == LispLanguage.lookup_sym && (p.getCdr() instanceof Pair)) {
                            p = (Pair) p.getCdr();
                            if (p.getCdr() instanceof Pair) {
                                Expression part1 = rewrite(p.getCar());
                                Expression part2 = rewrite(((Pair) p.getCdr()).getCar());
                                Object value1 = part1.valueIfConstant();
                                Object value2 = part2.valueIfConstant();
                                if ((value1 instanceof Class) && (value2 instanceof Symbol)) {
                                    try {
                                        obj = GetNamedPart.getNamedPart(value1, (Symbol) value2);
                                        if (obj instanceof Syntax) {
                                            syntax = obj;
                                        }
                                    } catch (Throwable th) {
                                        obj = null;
                                    }
                                } else {
                                    obj = namespaceResolve(part1, part2);
                                }
                            }
                        }
                    }
                    if ((obj instanceof Symbol) && !selfEvaluatingSymbol(obj)) {
                        Expression func = rewrite(obj, true);
                        if (func instanceof ReferenceExp) {
                            Declaration decl = ((ReferenceExp) func).getBinding();
                            if (decl != null) {
                                syntax = check_if_Syntax(decl);
                            } else {
                                Object obj2 = resolve(obj, true);
                                if (obj2 instanceof Syntax) {
                                    syntax = (Syntax) obj2;
                                }
                            }
                        }
                    } else if (obj == begin.begin) {
                        syntax = obj;
                    }
                    if (savedScope != this.current_scope) {
                        setCurrentScope(savedScope);
                    }
                    popPositionOf(savedPosition);
                    if (syntax != null) {
                        String save_filename = getFileName();
                        int save_line = getLineNumber();
                        int save_column = getColumnNumber();
                        try {
                            setLine((Object) st_pair);
                            syntax.scanForm(st_pair, defs, this);
                            return;
                        } finally {
                            this.macroContext = saveContext;
                            setLine(save_filename, save_line, save_column);
                        }
                    }
                } catch (Throwable th2) {
                    if (savedScope != this.current_scope) {
                        setCurrentScope(savedScope);
                    }
                    popPositionOf(savedPosition);
                }
            }
            this.formStack.add(st2);
        }
    }

    public LList scanBody(Object body, ScopeExp defs, boolean makeList) {
        LList list = makeList ? LList.Empty : null;
        Pair lastPair = null;
        LList body2;
        while (body2 != LList.Empty) {
            int first;
            if (body2 instanceof SyntaxForm) {
                SyntaxForm sf = (SyntaxForm) body2;
                ScopeExp save_scope = this.current_scope;
                try {
                    setCurrentScope(sf.getScope());
                    first = this.formStack.size();
                    LList scanBody = scanBody(sf.getDatum(), defs, makeList);
                    if (makeList) {
                        scanBody = (LList) SyntaxForms.fromDatumIfNeeded(scanBody, sf);
                        if (lastPair == null) {
                            return scanBody;
                        }
                        lastPair.setCdrBackdoor(scanBody);
                        setCurrentScope(save_scope);
                        return list;
                    }
                    this.formStack.add(wrapSyntax(popForms(first), sf));
                    setCurrentScope(save_scope);
                    return null;
                } finally {
                    setCurrentScope(save_scope);
                }
            } else if (body2 instanceof Pair) {
                Pair pair = (Pair) body2;
                first = this.formStack.size();
                scanForm(pair.getCar(), defs);
                if (getState() == 2) {
                    if (pair.getCar() != this.pendingForm) {
                        pair = makePair(pair, this.pendingForm, pair.getCdr());
                    }
                    this.pendingForm = new Pair(begin.begin, pair);
                    return LList.Empty;
                }
                int fsize = this.formStack.size();
                if (makeList) {
                    for (int i = first; i < fsize; i++) {
                        LList npair = makePair(pair, this.formStack.elementAt(i), LList.Empty);
                        if (lastPair == null) {
                            list = npair;
                        } else {
                            lastPair.setCdrBackdoor(npair);
                        }
                        LList lastPair2 = npair;
                    }
                    this.formStack.setSize(first);
                }
                body2 = pair.getCdr();
            } else {
                this.formStack.add(syntaxError("body is not a proper list"));
                return list;
            }
        }
        return list;
    }

    public static Pair makePair(Pair pair, Object car, Object cdr) {
        if (pair instanceof PairWithPosition) {
            return new PairWithPosition((PairWithPosition) pair, car, cdr);
        }
        return new Pair(car, cdr);
    }

    public Expression rewrite_body(Object exp) {
        Object saved = pushPositionOf(exp);
        Expression defs = new LetExp(null);
        int first = this.formStack.size();
        defs.outer = this.current_scope;
        this.current_scope = defs;
        try {
            LList list = scanBody(exp, defs, true);
            if (list.isEmpty()) {
                this.formStack.add(syntaxError("body with no expressions"));
            }
            int ndecls = defs.countNonDynamicDecls();
            if (ndecls != 0) {
                Expression[] inits = new Expression[ndecls];
                int i = ndecls;
                while (true) {
                    i--;
                    if (i < 0) {
                        break;
                    }
                    inits[i] = QuoteExp.undefined_exp;
                }
                defs.inits = inits;
            }
            rewriteBody(list);
            Expression body = makeBody(first, null);
            setLineOf(body);
            if (ndecls == 0) {
                return body;
            }
            defs.body = body;
            setLineOf(defs);
            pop(defs);
            popPositionOf(saved);
            return defs;
        } finally {
            pop(defs);
            popPositionOf(saved);
        }
    }

    private Expression makeBody(int first, ScopeExp scope) {
        int nforms = this.formStack.size() - first;
        if (nforms == 0) {
            return QuoteExp.voidExp;
        }
        if (nforms == 1) {
            return (Expression) this.formStack.pop();
        }
        Expression[] exps = new Expression[nforms];
        for (int i = 0; i < nforms; i++) {
            exps[i] = (Expression) this.formStack.elementAt(first + i);
        }
        this.formStack.setSize(first);
        if (scope instanceof ModuleExp) {
            return new ApplyExp(AppendValues.appendValues, exps);
        }
        return ((LispLanguage) getLanguage()).makeBody(exps);
    }

    public void noteAccess(Object name, ScopeExp scope) {
        if (this.notedAccess == null) {
            this.notedAccess = new Vector();
        }
        this.notedAccess.addElement(name);
        this.notedAccess.addElement(scope);
    }

    public void processAccesses() {
        if (this.notedAccess != null) {
            int sz = this.notedAccess.size();
            ScopeExp saveScope = this.current_scope;
            for (int i = 0; i < sz; i += 2) {
                Object name = this.notedAccess.elementAt(i);
                ScopeExp scope = (ScopeExp) this.notedAccess.elementAt(i + 1);
                if (this.current_scope != scope) {
                    setCurrentScope(scope);
                }
                Declaration decl = this.lexical.lookup(name, -1);
                if (!(decl == null || decl.getFlag(65536))) {
                    decl.getContext().currentLambda().capture(decl);
                    decl.setCanRead(true);
                    decl.setSimple(false);
                    decl.setFlag(524288);
                }
            }
            if (this.current_scope != saveScope) {
                setCurrentScope(saveScope);
            }
        }
    }

    public void finishModule(ModuleExp mexp) {
        boolean moduleStatic = mexp.isStatic();
        Declaration decl = mexp.firstDecl();
        while (decl != null) {
            if (decl.getFlag(512)) {
                String msg1 = "'";
                String msg2 = decl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) ? "' exported but never defined" : decl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) ? "' declared static but never defined" : "' declared but never defined";
                error('e', decl, msg1, msg2);
            }
            if (mexp.getFlag(16384) || (this.generateMain && !this.immediate)) {
                if (!decl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)) {
                    decl.setPrivate(true);
                } else if (decl.isPrivate()) {
                    if (decl.getFlag(16777216)) {
                        error('e', decl, "'", "' is declared both private and exported");
                    }
                    decl.setPrivate(false);
                }
            }
            if (moduleStatic) {
                decl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
            } else if ((mexp.getFlag(65536) && !decl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH)) || Compilation.moduleStatic < 0 || mexp.getFlag(131072)) {
                decl.setFlag(PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM);
            }
            decl = decl.nextDecl();
        }
    }

    static void vectorReverse(Vector vec, int start, int count) {
        int j = count / 2;
        int last = (start + count) - 1;
        for (int i = 0; i < j; i++) {
            Object tmp = vec.elementAt(start + i);
            vec.setElementAt(vec.elementAt(last - i), start + i);
            vec.setElementAt(tmp, last - i);
        }
    }

    public void resolveModule(ModuleExp mexp) {
        int numPending;
        if (this.pendingImports == null) {
            numPending = 0;
        } else {
            numPending = this.pendingImports.size();
        }
        int i = 0;
        while (i < numPending) {
            int i2 = i + 1;
            ModuleInfo info = (ModuleInfo) this.pendingImports.elementAt(i);
            i = i2 + 1;
            ScopeExp defs = (ScopeExp) this.pendingImports.elementAt(i2);
            i2 = i + 1;
            Expression posExp = (Expression) this.pendingImports.elementAt(i);
            i = i2 + 1;
            Integer savedSize = (Integer) this.pendingImports.elementAt(i2);
            if (mexp == defs) {
                Expression referenceExp = new ReferenceExp(null);
                referenceExp.setLine((Compilation) this);
                setLine(posExp);
                int beforeSize = this.formStack.size();
                require.importDefinitions(null, info, null, this.formStack, defs, this);
                int desiredPosition = savedSize.intValue();
                if (savedSize.intValue() != beforeSize) {
                    int curSize = this.formStack.size();
                    int count = curSize - desiredPosition;
                    vectorReverse(this.formStack, desiredPosition, beforeSize - desiredPosition);
                    vectorReverse(this.formStack, beforeSize, curSize - beforeSize);
                    vectorReverse(this.formStack, desiredPosition, count);
                }
                setLine(referenceExp);
            }
        }
        this.pendingImports = null;
        processAccesses();
        setModule(mexp);
        Compilation save_comp = Compilation.setSaveCurrent(this);
        try {
            rewriteInBody(popForms(this.firstForm));
            mexp.body = makeBody(this.firstForm, mexp);
            if (!this.immediate) {
                this.lexical.pop((ScopeExp) mexp);
            }
            Compilation.restoreCurrent(save_comp);
        } catch (Throwable th) {
            Compilation.restoreCurrent(save_comp);
        }
    }

    public Declaration makeRenamedAlias(Declaration decl, ScopeExp templateScope) {
        return templateScope == null ? decl : makeRenamedAlias(decl.getSymbol(), decl, templateScope);
    }

    public Declaration makeRenamedAlias(Object name, Declaration decl, ScopeExp templateScope) {
        Declaration alias = new Declaration(name);
        alias.setAlias(true);
        alias.setPrivate(true);
        alias.context = templateScope;
        ReferenceExp ref = new ReferenceExp(decl);
        ref.setDontDereference(true);
        alias.noteValue(ref);
        return alias;
    }

    public void pushRenamedAlias(Declaration alias) {
        Declaration decl = getOriginalRef(alias).getBinding();
        ScopeExp templateScope = alias.context;
        decl.setSymbol(null);
        Declaration old = templateScope.lookup(decl.getSymbol());
        if (old != null) {
            templateScope.remove(old);
        }
        templateScope.addDeclaration(alias);
        if (this.renamedAliasStack == null) {
            this.renamedAliasStack = new Stack();
        }
        this.renamedAliasStack.push(old);
        this.renamedAliasStack.push(alias);
        this.renamedAliasStack.push(templateScope);
    }

    public void popRenamedAlias(int count) {
        while (true) {
            count--;
            if (count >= 0) {
                ScopeExp templateScope = (ScopeExp) this.renamedAliasStack.pop();
                Declaration alias = (Declaration) this.renamedAliasStack.pop();
                getOriginalRef(alias).getBinding().setSymbol(alias.getSymbol());
                templateScope.remove(alias);
                Object old = this.renamedAliasStack.pop();
                if (old != null) {
                    templateScope.addDeclaration((Declaration) old);
                }
            } else {
                return;
            }
        }
    }

    public Declaration define(Object name, SyntaxForm nameSyntax, ScopeExp defs) {
        Object declName;
        boolean aliasNeeded = (nameSyntax == null || nameSyntax.getScope() == currentScope()) ? false : true;
        if (aliasNeeded) {
            declName = new String(name.toString());
        } else {
            declName = name;
        }
        Declaration decl = defs.getDefine(declName, 'w', this);
        if (aliasNeeded) {
            nameSyntax.getScope().addDeclaration(makeRenamedAlias(name, decl, nameSyntax.getScope()));
        }
        push(decl);
        return decl;
    }
}

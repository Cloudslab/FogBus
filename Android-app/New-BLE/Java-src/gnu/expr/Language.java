package gnu.expr;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.bytecode.Access;
import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.kawa.reflect.ClassMemberLocation;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.AbstractFormat;
import gnu.lists.CharSeq;
import gnu.lists.Consumer;
import gnu.lists.Convert;
import gnu.lists.FString;
import gnu.lists.PrintConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.CharArrayInPort;
import gnu.mapping.Environment;
import gnu.mapping.EnvironmentKey;
import gnu.mapping.InPort;
import gnu.mapping.Location;
import gnu.mapping.Named;
import gnu.mapping.NamedLocation;
import gnu.mapping.Namespace;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.text.Lexer;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kawa.repl;

public abstract class Language {
    public static final int FUNCTION_NAMESPACE = 2;
    public static final int NAMESPACE_PREFIX_NAMESPACE = 4;
    public static final int PARSE_CURRENT_NAMES = 2;
    public static final int PARSE_EXPLICIT = 64;
    public static final int PARSE_FOR_APPLET = 16;
    public static final int PARSE_FOR_EVAL = 3;
    public static final int PARSE_FOR_SERVLET = 32;
    public static final int PARSE_IMMEDIATE = 1;
    public static final int PARSE_ONE_LINE = 4;
    public static final int PARSE_PROLOG = 8;
    public static final int VALUE_NAMESPACE = 1;
    protected static final InheritableThreadLocal<Language> current = new InheritableThreadLocal();
    static int envCounter;
    protected static int env_counter = 0;
    protected static Language global;
    static String[][] languages;
    public static boolean requirePedantic;
    protected Environment environ;
    protected Environment userEnv;

    public abstract Lexer getLexer(InPort inPort, SourceMessages sourceMessages);

    public abstract boolean parse(Compilation compilation, int i) throws IOException, SyntaxException;

    static {
        Environment.setGlobal(BuiltinEnvironment.getInstance());
        r0 = new String[8][];
        r0[0] = new String[]{"scheme", ".scm", ".sc", "kawa.standard.Scheme"};
        r0[1] = new String[]{"krl", ".krl", "gnu.kawa.brl.BRL"};
        r0[2] = new String[]{"brl", ".brl", "gnu.kawa.brl.BRL"};
        r0[3] = new String[]{"emacs", "elisp", "emacs-lisp", ".el", "gnu.jemacs.lang.ELisp"};
        r0[4] = new String[]{"xquery", ".xquery", ".xq", ".xql", "gnu.xquery.lang.XQuery"};
        r0[5] = new String[]{"q2", ".q2", "gnu.q2.lang.Q2"};
        r0[6] = new String[]{"xslt", "xsl", ".xsl", "gnu.kawa.xslt.XSLT"};
        r0[7] = new String[]{"commonlisp", "common-lisp", "clisp", "lisp", ".lisp", ".lsp", ".cl", "gnu.commonlisp.lang.CommonLisp"};
        languages = r0;
    }

    public static Language getDefaultLanguage() {
        Language lang = (Language) current.get();
        return lang != null ? lang : global;
    }

    public static void setCurrentLanguage(Language language) {
        current.set(language);
    }

    public static Language setSaveCurrent(Language language) {
        Language save = (Language) current.get();
        current.set(language);
        return save;
    }

    public static void restoreCurrent(Language saved) {
        current.set(saved);
    }

    public static String[][] getLanguages() {
        return languages;
    }

    public static void registerLanguage(String[] langMapping) {
        String[][] newLangs = new String[(languages.length + 1)][];
        System.arraycopy(languages, 0, newLangs, 0, languages.length);
        newLangs[newLangs.length - 1] = langMapping;
        languages = newLangs;
    }

    public static Language detect(InputStream in) throws IOException {
        if (!in.markSupported()) {
            return null;
        }
        StringBuffer sbuf = new StringBuffer();
        in.mark(HttpRequestContext.HTTP_OK);
        while (sbuf.length() < HttpRequestContext.HTTP_OK) {
            int c = in.read();
            if (c < 0 || c == 10 || c == 13) {
                break;
            }
            sbuf.append((char) c);
        }
        in.reset();
        return detect(sbuf.toString());
    }

    public static Language detect(InPort port) throws IOException {
        StringBuffer sbuf = new StringBuffer();
        port.mark(300);
        port.readLine(sbuf, 'P');
        port.reset();
        return detect(sbuf.toString());
    }

    public static Language detect(String line) {
        String str = line.trim();
        int k = str.indexOf("kawa:");
        if (k >= 0) {
            int i = k + 5;
            int j = i;
            while (j < str.length() && Character.isJavaIdentifierPart(str.charAt(j))) {
                j++;
            }
            if (j > i) {
                Language lang = getInstance(str.substring(i, j));
                if (lang != null) {
                    return lang;
                }
            }
        }
        if (str.indexOf("-*- scheme -*-") >= 0) {
            return getInstance("scheme");
        }
        if (str.indexOf("-*- xquery -*-") >= 0) {
            return getInstance("xquery");
        }
        if (str.indexOf("-*- emacs-lisp -*-") >= 0) {
            return getInstance("elisp");
        }
        if (str.indexOf("-*- common-lisp -*-") >= 0 || str.indexOf("-*- lisp -*-") >= 0) {
            return getInstance("common-lisp");
        }
        if ((str.charAt(0) == '(' && str.charAt(1) == ':') || (str.length() >= 7 && str.substring(0, 7).equals("xquery "))) {
            return getInstance("xquery");
        }
        if (str.charAt(0) == ';' && str.charAt(1) == ';') {
            return getInstance("scheme");
        }
        return null;
    }

    public static Language getInstanceFromFilenameExtension(String filename) {
        int dot = filename.lastIndexOf(46);
        if (dot > 0) {
            Language lang = getInstance(filename.substring(dot));
            if (lang != null) {
                return lang;
            }
        }
        return null;
    }

    public static Language getInstance(String name) {
        int langCount = languages.length;
        int i = 0;
        while (i < langCount) {
            String[] names = languages[i];
            int nameCount = names.length - 1;
            int j = nameCount;
            do {
                j--;
                if (j >= 0) {
                    if (name != null) {
                    }
                    break;
                }
                i++;
            } while (!names[j].equalsIgnoreCase(name));
            try {
                return getInstance(names[0], Class.forName(names[nameCount]));
            } catch (ClassNotFoundException e) {
            }
        }
        return null;
    }

    protected Language() {
        Convert.setInstance(KawaConvert.getInstance());
    }

    public static Language getInstance(String langName, Class langClass) {
        try {
            Method method;
            Class[] args = new Class[0];
            try {
                method = langClass.getDeclaredMethod("get" + (Character.toTitleCase(langName.charAt(0)) + langName.substring(1).toLowerCase()) + "Instance", args);
            } catch (Exception e) {
                method = langClass.getDeclaredMethod("getInstance", args);
            }
            return (Language) method.invoke(null, Values.noArgs);
        } catch (Throwable ex) {
            Throwable th;
            langName = langClass.getName();
            if (ex instanceof InvocationTargetException) {
                th = ((InvocationTargetException) ex).getTargetException();
            } else {
                th = ex;
            }
            throw new WrappedException("getInstance for '" + langName + "' failed", th);
        }
    }

    public boolean isTrue(Object value) {
        return value != Boolean.FALSE;
    }

    public Object booleanObject(boolean b) {
        return b ? Boolean.TRUE : Boolean.FALSE;
    }

    public Object noValue() {
        return Values.empty;
    }

    public boolean hasSeparateFunctionNamespace() {
        return false;
    }

    public final Environment getEnvironment() {
        return this.userEnv != null ? this.userEnv : Environment.getCurrent();
    }

    public final Environment getNewEnvironment() {
        StringBuilder append = new StringBuilder().append("environment-");
        int i = envCounter + 1;
        envCounter = i;
        return Environment.make(append.append(i).toString(), this.environ);
    }

    public Environment getLangEnvironment() {
        return this.environ;
    }

    public NamedLocation lookupBuiltin(Symbol name, Object property, int hash) {
        return this.environ == null ? null : this.environ.lookup(name, property, hash);
    }

    public void define(String sym, Object p) {
        this.environ.define(getSymbol(sym), null, p);
    }

    protected void defAliasStFld(String name, String cname, String fname) {
        StaticFieldLocation.define(this.environ, getSymbol(name), null, cname, fname);
    }

    protected void defProcStFld(String name, String cname, String fname) {
        StaticFieldLocation.define(this.environ, getSymbol(name), hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, cname, fname).setProcedure();
    }

    protected void defProcStFld(String name, String cname) {
        defProcStFld(name, cname, Compilation.mangleNameIfNeeded(name));
    }

    public final void defineFunction(Named proc) {
        Object name = proc.getSymbol();
        this.environ.define(name instanceof Symbol ? (Symbol) name : getSymbol(name.toString()), hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, proc);
    }

    public void defineFunction(String name, Object proc) {
        this.environ.define(getSymbol(name), hasSeparateFunctionNamespace() ? EnvironmentKey.FUNCTION : null, proc);
    }

    public Object getEnvPropertyFor(Field fld, Object value) {
        if (hasSeparateFunctionNamespace() && Compilation.typeProcedure.getReflectClass().isAssignableFrom(fld.getType())) {
            return EnvironmentKey.FUNCTION;
        }
        return null;
    }

    public Object getEnvPropertyFor(Declaration decl) {
        if (hasSeparateFunctionNamespace() && decl.isProcedureDecl()) {
            return EnvironmentKey.FUNCTION;
        }
        return null;
    }

    public void loadClass(String name) throws ClassNotFoundException {
        try {
            try {
                Object inst = Class.forName(name).newInstance();
                ClassMemberLocation.defineAll(inst, this, Environment.getCurrent());
                if (inst instanceof ModuleBody) {
                    ((ModuleBody) inst).run();
                }
            } catch (Exception ex) {
                throw new WrappedException("cannot load " + name, ex);
            }
        } catch (ClassNotFoundException ex2) {
            throw ex2;
        }
    }

    public Symbol getSymbol(String name) {
        return this.environ.getSymbol(name);
    }

    public Object lookup(String name) {
        return this.environ.get((Object) name);
    }

    public AbstractFormat getFormat(boolean readable) {
        return null;
    }

    public Consumer getOutputConsumer(Writer out) {
        OutPort oport = out instanceof OutPort ? (OutPort) out : new OutPort(out);
        oport.objectFormat = getFormat(false);
        return oport;
    }

    public String getName() {
        String name = getClass().getName();
        int dot = name.lastIndexOf(46);
        if (dot >= 0) {
            return name.substring(dot + 1);
        }
        return name;
    }

    public Compilation getCompilation(Lexer lexer, SourceMessages messages, NameLookup lexical) {
        return new Compilation(this, messages, lexical);
    }

    public final Compilation parse(InPort port, SourceMessages messages, int options) throws IOException, SyntaxException {
        return parse(getLexer(port, messages), options, null);
    }

    public final Compilation parse(InPort port, SourceMessages messages, ModuleInfo info) throws IOException, SyntaxException {
        return parse(getLexer(port, messages), 8, info);
    }

    public final Compilation parse(InPort port, SourceMessages messages, int options, ModuleInfo info) throws IOException, SyntaxException {
        return parse(getLexer(port, messages), options, info);
    }

    public final Compilation parse(Lexer lexer, int options, ModuleInfo info) throws IOException, SyntaxException {
        SourceMessages messages = lexer.getMessages();
        NameLookup lexical = (options & 2) != 0 ? NameLookup.getInstance(getEnvironment(), this) : new NameLookup(this);
        boolean immediate = (options & 1) != 0;
        Compilation tr = getCompilation(lexer, messages, lexical);
        if (requirePedantic) {
            tr.pedantic = true;
        }
        if (!immediate) {
            tr.mustCompile = true;
        }
        tr.immediate = immediate;
        tr.langOptions = options;
        if ((options & 64) != 0) {
            tr.explicit = true;
        }
        if ((options & 8) != 0) {
            tr.setState(1);
        }
        tr.pushNewModule(lexer);
        if (info != null) {
            info.setCompilation(tr);
        }
        if (!parse(tr, options)) {
            return null;
        }
        if (tr.getState() != 1) {
            return tr;
        }
        tr.setState(2);
        return tr;
    }

    public void resolve(Compilation comp) {
    }

    public Type getTypeFor(Class clas) {
        return Type.make(clas);
    }

    public final Type getLangTypeFor(Type type) {
        if (!type.isExisting()) {
            return type;
        }
        Class clas = type.getReflectClass();
        if (clas != null) {
            return getTypeFor(clas);
        }
        return type;
    }

    public String formatType(Type type) {
        return type.getName();
    }

    public static Type string2Type(String name) {
        if (!name.endsWith("[]")) {
            return Type.isValidJavaTypeName(name) ? Type.getType(name) : null;
        } else {
            Type t = string2Type(name.substring(0, name.length() - 2));
            if (t == null) {
                return null;
            }
            return ArrayType.make(t);
        }
    }

    public Type getTypeFor(String name) {
        return string2Type(name);
    }

    public final Type getTypeFor(Object spec, boolean lenient) {
        if (spec instanceof Type) {
            return (Type) spec;
        }
        if (spec instanceof Class) {
            return getTypeFor((Class) spec);
        }
        if (lenient && ((spec instanceof FString) || (spec instanceof String) || (((spec instanceof Symbol) && ((Symbol) spec).hasEmptyNamespace()) || (spec instanceof CharSeq)))) {
            return getTypeFor(spec.toString());
        }
        if (spec instanceof Namespace) {
            String uri = ((Namespace) spec).getName();
            if (uri != null && uri.startsWith("class:")) {
                return getLangTypeFor(string2Type(uri.substring(6)));
            }
        }
        return null;
    }

    public final Type asType(Object spec) {
        Type type = getTypeFor(spec, true);
        return type == null ? (Type) spec : type;
    }

    public final Type getTypeFor(Expression exp) {
        return getTypeFor(exp, true);
    }

    public Type getTypeFor(Expression exp, boolean lenient) {
        if (exp instanceof QuoteExp) {
            Object value = ((QuoteExp) exp).getValue();
            if (value instanceof Type) {
                return (Type) value;
            }
            if (value instanceof Class) {
                return Type.make((Class) value);
            }
            return getTypeFor(value, lenient);
        } else if (exp instanceof ReferenceExp) {
            ReferenceExp rexp = (ReferenceExp) exp;
            Declaration decl = Declaration.followAliases(rexp.getBinding());
            Object name = rexp.getName();
            if (decl != null) {
                exp = decl.getValue();
                if ((exp instanceof QuoteExp) && decl.getFlag(16384) && !decl.isIndirectBinding()) {
                    return getTypeFor(((QuoteExp) exp).getValue(), lenient);
                }
                if ((exp instanceof ClassExp) || (exp instanceof ModuleExp)) {
                    decl.setCanRead(true);
                    return ((LambdaExp) exp).getClassType();
                } else if (decl.isAlias() && (exp instanceof QuoteExp)) {
                    Location val = ((QuoteExp) exp).getValue();
                    if (val instanceof Location) {
                        Location loc = val;
                        if (loc.isBound()) {
                            return getTypeFor(loc.get(), lenient);
                        }
                        if (!(loc instanceof Named)) {
                            return null;
                        }
                        name = ((Named) loc).getName();
                    }
                } else if (!decl.getFlag(65536)) {
                    return getTypeFor(exp, lenient);
                }
            }
            Object val2 = getEnvironment().get(name);
            if (val2 instanceof Type) {
                return (Type) val2;
            }
            if (val2 instanceof ClassNamespace) {
                return ((ClassNamespace) val2).getClassType();
            }
            int len = name.length();
            if (len > 2 && name.charAt(0) == '<' && name.charAt(len - 1) == '>') {
                return getTypeFor(name.substring(1, len - 1));
            }
            return null;
        } else if ((exp instanceof ClassExp) || (exp instanceof ModuleExp)) {
            return ((LambdaExp) exp).getClassType();
        } else {
            return null;
        }
    }

    public static Type unionType(Type t1, Type t2) {
        if (t1 == Type.toStringType) {
            t1 = Type.javalangStringType;
        }
        if (t2 == Type.toStringType) {
            t2 = Type.javalangStringType;
        }
        if (t1 == t2) {
            return t1;
        }
        if (!(t1 instanceof PrimType) || !(t2 instanceof PrimType)) {
            return Type.objectType;
        }
        char sig1 = t1.getSignature().charAt(0);
        char sig2 = t2.getSignature().charAt(0);
        if (sig1 == sig2) {
            return t1;
        }
        if ((sig1 == 'B' || sig1 == 'S' || sig1 == Access.INNERCLASS_CONTEXT) && (sig2 == Access.INNERCLASS_CONTEXT || sig2 == 'J')) {
            return t2;
        }
        if ((sig2 == 'B' || sig2 == 'S' || sig2 == Access.INNERCLASS_CONTEXT) && (sig1 == Access.INNERCLASS_CONTEXT || sig1 == 'J')) {
            return t1;
        }
        if (sig1 == Access.FIELD_CONTEXT && sig2 == 'D') {
            return t2;
        }
        if (sig2 == Access.FIELD_CONTEXT && sig1 == 'D') {
            return t1;
        }
        return Type.objectType;
    }

    public Declaration declFromField(ModuleExp mod, Object fvalue, gnu.bytecode.Field fld) {
        Object obj;
        String fname = fld.getName();
        Type ftype = fld.getType();
        boolean isAlias = ftype.isSubtype(Compilation.typeLocation);
        boolean externalAccess = false;
        boolean isFinal = (fld.getModifiers() & 16) != 0;
        boolean isImportedInstance = fname.endsWith("$instance");
        if (isImportedInstance) {
            obj = fname;
        } else if (isFinal && (fvalue instanceof Named)) {
            obj = ((Named) fvalue).getSymbol();
        } else {
            if (fname.startsWith(Declaration.PRIVATE_PREFIX)) {
                externalAccess = true;
                fname = fname.substring(Declaration.PRIVATE_PREFIX.length());
            }
            obj = Compilation.demangleName(fname, true).intern();
        }
        if (obj instanceof String) {
            String uri = mod.getNamespaceUri();
            String sname = (String) obj;
            if (uri == null) {
                obj = Symbol.valueOf(sname);
            } else {
                obj = Symbol.make(uri, sname);
            }
        }
        Declaration fdecl = mod.addDeclaration(obj, isAlias ? Type.objectType : getTypeFor(ftype.getReflectClass()));
        boolean isStatic = (fld.getModifiers() & 8) != 0;
        if (isAlias) {
            fdecl.setIndirectBinding(true);
            if ((ftype instanceof ClassType) && ((ClassType) ftype).isSubclass("gnu.mapping.ThreadLocation")) {
                fdecl.setFlag(268435456);
            }
        } else if (isFinal && (ftype instanceof ClassType)) {
            if (ftype.isSubtype(Compilation.typeProcedure)) {
                fdecl.setProcedureDecl(true);
            } else if (((ClassType) ftype).isSubclass("gnu.mapping.Namespace")) {
                fdecl.setFlag(2097152);
            }
        }
        if (isStatic) {
            fdecl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
        }
        fdecl.field = fld;
        if (isFinal && !isAlias) {
            fdecl.setFlag(16384);
        }
        if (isImportedInstance) {
            fdecl.setFlag(1073741824);
        }
        fdecl.setSimple(false);
        if (externalAccess) {
            fdecl.setFlag(524320);
        }
        return fdecl;
    }

    public int getNamespaceOf(Declaration decl) {
        return 1;
    }

    public boolean hasNamespace(Declaration decl, int namespace) {
        return (getNamespaceOf(decl) & namespace) != 0;
    }

    public void emitPushBoolean(boolean value, CodeAttr code) {
        code.emitGetStatic(value ? Compilation.trueConstant : Compilation.falseConstant);
    }

    public void emitCoerceToBoolean(CodeAttr code) {
        emitPushBoolean(false, code);
        code.emitIfNEq();
        code.emitPushInt(1);
        code.emitElse();
        code.emitPushInt(0);
        code.emitFi();
    }

    public Object coerceFromObject(Class clas, Object obj) {
        return getTypeFor(clas).coerceFromObject(obj);
    }

    public Object coerceToObject(Class clas, Object obj) {
        return getTypeFor(clas).coerceToObject(obj);
    }

    public static synchronized void setDefaults(Language lang) {
        synchronized (Language.class) {
            setCurrentLanguage(lang);
            global = lang;
            if (Environment.getGlobal() == BuiltinEnvironment.getInstance()) {
                Environment.setGlobal(Environment.getCurrent());
            }
        }
    }

    public Procedure getPrompter() {
        Object property = null;
        if (hasSeparateFunctionNamespace()) {
            property = EnvironmentKey.FUNCTION;
        }
        Procedure prompter = (Procedure) getEnvironment().get(getSymbol("default-prompter"), property, null);
        return prompter != null ? prompter : new SimplePrompter();
    }

    public final Object eval(String string) throws Throwable {
        return eval(new CharArrayInPort(string));
    }

    public final Object eval(Reader in) throws Throwable {
        InPort in2;
        if (in instanceof InPort) {
            in2 = (InPort) in;
        } else {
            in = new InPort(in);
        }
        return eval(in2);
    }

    public final Object eval(InPort port) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        int oldIndex = ctx.startFromContext();
        try {
            eval(port, ctx);
            return ctx.getFromContext(oldIndex);
        } catch (Throwable th) {
            ctx.cleanupFromContext(oldIndex);
        }
    }

    public final void eval(String string, Writer out) throws Throwable {
        eval(new CharArrayInPort(string), out);
    }

    public final void eval(String string, PrintConsumer out) throws Throwable {
        eval(string, getOutputConsumer(out));
    }

    public final void eval(String string, Consumer out) throws Throwable {
        eval(new CharArrayInPort(string), out);
    }

    public final void eval(Reader in, Writer out) throws Throwable {
        eval(in, getOutputConsumer(out));
    }

    public void eval(Reader in, Consumer out) throws Throwable {
        InPort port = in instanceof InPort ? (InPort) in : new InPort(in);
        CallContext ctx = CallContext.getInstance();
        Consumer save = ctx.consumer;
        try {
            ctx.consumer = out;
            eval(port, ctx);
        } finally {
            ctx.consumer = save;
        }
    }

    public void eval(InPort port, CallContext ctx) throws Throwable {
        SourceMessages messages = new SourceMessages();
        Language saveLang = setSaveCurrent(this);
        try {
            ModuleExp.evalModule(getEnvironment(), ctx, parse(port, messages, 3), null, null);
            if (messages.seenErrors()) {
                throw new RuntimeException("invalid syntax in eval form:\n" + messages.toString(20));
            }
        } finally {
            restoreCurrent(saveLang);
        }
    }

    public void runAsApplication(String[] args) {
        setDefaults(this);
        repl.main(args);
    }
}

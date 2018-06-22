package kawa.standard;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.expr.QuoteExp;
import gnu.expr.ReferenceExp;
import gnu.expr.ScopeExp;
import gnu.expr.SetExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.reflect.SlotGet;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.InPort;
import gnu.mapping.Procedure;
import gnu.mapping.Symbol;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class require extends Syntax {
    private static final String SLIB_PREFIX = "gnu.kawa.slib.";
    static Hashtable featureMap = new Hashtable();
    public static final require require = new require();

    static {
        require.setName("require");
        map("generic-write", "gnu.kawa.slib.genwrite");
        map("pretty-print", "gnu.kawa.slib.pp");
        map("pprint-file", "gnu.kawa.slib.ppfile");
        map("printf", "gnu.kawa.slib.printf");
        map("xml", "gnu.kawa.slib.XML");
        map("readtable", "gnu.kawa.slib.readtable");
        map("srfi-10", "gnu.kawa.slib.readtable");
        map("http", "gnu.kawa.servlet.HTTP");
        map("servlets", "gnu.kawa.servlet.servlets");
        map("srfi-1", "gnu.kawa.slib.srfi1");
        map("list-lib", "gnu.kawa.slib.srfi1");
        map("srfi-2", "gnu.kawa.slib.srfi2");
        map("and-let*", "gnu.kawa.slib.srfi2");
        map("srfi-13", "gnu.kawa.slib.srfi13");
        map("string-lib", "gnu.kawa.slib.srfi13");
        map("srfi-34", "gnu.kawa.slib.srfi34");
        map("srfi-35", "gnu.kawa.slib.conditions");
        map("condition", "gnu.kawa.slib.conditions");
        map("conditions", "gnu.kawa.slib.conditions");
        map("srfi-37", "gnu.kawa.slib.srfi37");
        map("args-fold", "gnu.kawa.slib.srfi37");
        map("srfi-64", "gnu.kawa.slib.testing");
        map("testing", "gnu.kawa.slib.testing");
        map("srfi-69", "gnu.kawa.slib.srfi69");
        map("hash-table", "gnu.kawa.slib.srfi69");
        map("basic-hash-tables", "gnu.kawa.slib.srfi69");
        map("srfi-95", "kawa.lib.srfi95");
        map("sorting-and-merging", "kawa.lib.srfi95");
        map("regex", "kawa.lib.kawa.regex");
        map("pregexp", "gnu.kawa.slib.pregexp");
        map("gui", "gnu.kawa.slib.gui");
        map("swing-gui", "gnu.kawa.slib.swing");
        map("android-defs", "gnu.kawa.android.defs");
        map("syntax-utils", "gnu.kawa.slib.syntaxutils");
    }

    static void map(String featureName, String className) {
        featureMap.put(featureName, className);
    }

    public static String mapFeature(String featureName) {
        return (String) featureMap.get(featureName);
    }

    public static Object find(String typeName) {
        return ModuleManager.getInstance().findWithClassName(typeName).getInstance();
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (tr.getState() == 1) {
            tr.setState(2);
            tr.pendingForm = st;
            return true;
        }
        Object name;
        Pair args = (Pair) st.getCdr();
        Pair name2 = args.getCar();
        Type type = null;
        if (name2 instanceof Pair) {
            Pair p = name2;
            if (tr.matches(p.getCar(), LispLanguage.quote_sym)) {
                name2 = p.getCdr();
                if (name2 instanceof Pair) {
                    p = name2;
                    if (p.getCdr() == LList.Empty && (p.getCar() instanceof Symbol)) {
                        name = mapFeature(p.getCar().toString());
                        if (name == null) {
                            tr.error('e', "unknown feature name '" + p.getCar() + "' for 'require'");
                            return false;
                        }
                        type = ClassType.make((String) name);
                        if (type instanceof ClassType) {
                            tr.error('e', "invalid specifier for 'require'");
                            return false;
                        }
                        try {
                            importDefinitions(null, ModuleInfo.find((ClassType) type), null, forms, defs, tr);
                            return true;
                        } catch (Exception e) {
                            tr.error('e', "unknown class " + type.getName());
                            return false;
                        }
                    }
                }
                tr.error('e', "invalid quoted symbol for 'require'");
                return false;
            }
        }
        String sourceName;
        ModuleInfo info;
        if (name2 instanceof CharSequence) {
            sourceName = name2.toString();
            info = lookupModuleFromSourcePath(sourceName, defs);
            if (info != null) {
                return importDefinitions(null, info, null, forms, defs, tr);
            }
            tr.error('e', "malformed URL: " + sourceName);
            return false;
        }
        if ((name2 instanceof Symbol) && !tr.selfEvaluatingSymbol(name2)) {
            type = tr.getLanguage().getTypeFor(tr.rewrite(name2, false));
            if ((type instanceof ClassType) && (args.getCdr() instanceof Pair)) {
                name = ((Pair) args.getCdr()).getCar();
                if (name instanceof CharSequence) {
                    sourceName = name.toString();
                    info = lookupModuleFromSourcePath(sourceName, defs);
                    if (info != null) {
                        return importDefinitions(type.getName(), info, null, forms, defs, tr);
                    }
                    tr.error('e', "malformed URL: " + sourceName);
                    return false;
                }
            }
        }
        if (type instanceof ClassType) {
            importDefinitions(null, ModuleInfo.find((ClassType) type), null, forms, defs, tr);
            return true;
        }
        tr.error('e', "invalid specifier for 'require'");
        return false;
    }

    public static ModuleInfo lookupModuleFromSourcePath(String sourceName, ScopeExp defs) {
        ModuleManager manager = ModuleManager.getInstance();
        String baseName = defs.getFileName();
        if (baseName != null) {
            sourceName = Path.valueOf(baseName).resolve(sourceName).toString();
        }
        return manager.findWithSourcePath(sourceName);
    }

    public static boolean importDefinitions(String className, ModuleInfo info, Procedure renamer, Vector forms, ScopeExp defs, Compilation tr) {
        Language language;
        boolean isRunnable;
        ModuleManager manager = ModuleManager.getInstance();
        if ((info.getState() & 1) == 0 && info.getCompilation() == null && !info.checkCurrent(manager, System.currentTimeMillis())) {
            SourceMessages messages = tr.getMessages();
            language = Language.getDefaultLanguage();
            try {
                InPort fstream = InPort.openFile(info.getSourceAbsPath());
                info.clearClass();
                info.setClassName(className);
                int options = 8;
                if (tr.immediate) {
                    options = 8 | 1;
                }
                Compilation comp = language.parse(fstream, messages, options, info);
                info.setClassName(comp.getModule().classFor(comp).getName());
            } catch (FileNotFoundException ex) {
                tr.error('e', "not found: " + ex.getMessage());
                return false;
            } catch (IOException ex2) {
                tr.error('e', "caught " + ex2);
                return false;
            } catch (SyntaxException ex3) {
                if (ex3.getMessages() == messages) {
                    return false;
                }
                throw new RuntimeException("confussing syntax error: " + ex3);
            }
        }
        if (tr.minfo != null && tr.getState() < 4) {
            tr.minfo.addDependency(info);
            if (!info.loadEager(12) && info.getState() < 6) {
                tr.pushPendingImport(info, defs, forms.size());
                return true;
            }
        }
        Type type = info.getClassType();
        String tname = type.getName();
        boolean sharedModule = tr.sharedModuleDefs();
        if (info.getState() < 6) {
            isRunnable = info.getCompilation().makeRunnable();
        } else {
            isRunnable = type.isSubtype(Compilation.typeRunnable);
        }
        Declaration decl = null;
        Expression dofind = Invoke.makeInvokeStatic(ClassType.make("kawa.standard.require"), "find", new Expression[]{new QuoteExp(tname)});
        Field instanceField = null;
        language = tr.getLanguage();
        dofind.setLine(tr);
        int formsStart = forms.size();
        ModuleExp mod = info.setupModuleExp();
        Vector declPairs = new Vector();
        Declaration fdecl = mod.firstDecl();
        while (fdecl != null) {
            Declaration adecl;
            if (!fdecl.isPrivate()) {
                SetExp setExp;
                Object aname = (Symbol) fdecl.getSymbol();
                if (renamer != null) {
                    Throwable mapped;
                    try {
                        mapped = renamer.apply1(aname);
                    } catch (Throwable ex4) {
                        mapped = ex4;
                    }
                    if (mapped != null) {
                        if (mapped instanceof Symbol) {
                            aname = (Symbol) mapped;
                        } else {
                            tr.error('e', "internal error - import name mapper returned non-symbol: " + mapped.getClass().getName());
                        }
                    }
                }
                boolean isStatic = fdecl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                if (!isStatic && decl == null) {
                    decl = new Declaration((Object) Symbol.valueOf(tname.replace('.', '$') + "$instance"), type);
                    decl.setPrivate(true);
                    decl.setFlag(1073758208);
                    defs.addDeclaration(decl);
                    decl.noteValue(dofind);
                    setExp = new SetExp(decl, dofind);
                    setExp.setLine(tr);
                    setExp.setDefining(true);
                    forms.addElement(setExp);
                    formsStart = forms.size();
                    decl.setFlag(536870912);
                    if (isRunnable) {
                        decl.setSimple(false);
                    }
                    decl.setFlag(8192);
                }
                if (fdecl.field != null) {
                    if (fdecl.field.getName().equals("$instance")) {
                        instanceField = fdecl.field;
                    }
                }
                boolean isImportedInstance = fdecl.field != null && fdecl.field.getName().endsWith("$instance");
                Declaration old = defs.lookup(aname, language, language.getNamespaceOf(fdecl));
                if (isImportedInstance) {
                    if (old == null) {
                        adecl = defs.addDeclaration(aname);
                        adecl.setFlag(1073758208);
                        adecl.setType(fdecl.getType());
                        adecl.setFlag(8192);
                    }
                } else if (old == null || old.getFlag(512) || Declaration.followAliases(old) != Declaration.followAliases(fdecl)) {
                    if (old == null || !old.getFlag(66048)) {
                        adecl = defs.addDeclaration(aname);
                        if (old != null) {
                            ScopeExp.duplicateDeclarationError(old, adecl, tr);
                        }
                    } else {
                        old.setFlag(false, 66048);
                        adecl = old;
                    }
                    adecl.setAlias(true);
                    adecl.setIndirectBinding(true);
                }
                adecl.setLocation(tr);
                Expression referenceExp = new ReferenceExp(fdecl);
                referenceExp.setContextDecl(decl);
                if (!isImportedInstance) {
                    referenceExp.setDontDereference(true);
                    if (!sharedModule) {
                        adecl.setPrivate(true);
                    }
                }
                adecl.setFlag(16384);
                if (fdecl.getFlag(32768)) {
                    adecl.setFlag(32768);
                }
                if (fdecl.isProcedureDecl()) {
                    adecl.setProcedureDecl(true);
                }
                if (isStatic) {
                    adecl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                }
                setExp = new SetExp(adecl, referenceExp);
                adecl.setFlag(536870912);
                setExp.setDefining(true);
                if (isImportedInstance) {
                    forms.insertElementAt(setExp, formsStart);
                    formsStart++;
                } else {
                    forms.addElement(setExp);
                }
                declPairs.add(adecl);
                declPairs.add(fdecl);
                adecl.noteValue(referenceExp);
                adecl.setFlag(131072);
                tr.push(adecl);
            }
            fdecl = fdecl.nextDecl();
        }
        int ndecls = declPairs.size();
        for (int i = 0; i < ndecls; i += 2) {
            adecl = (Declaration) declPairs.elementAt(i);
            fdecl = (Declaration) declPairs.elementAt(i + 1);
            Expression fval = fdecl.getValue();
            if (fdecl.isIndirectBinding() && (fval instanceof ReferenceExp)) {
                ReferenceExp aref = (ReferenceExp) adecl.getValue();
                Declaration xdecl = ((ReferenceExp) fval).getBinding();
                aref.setBinding(xdecl);
                if (xdecl.needsContext()) {
                    Declaration cdecl = defs.lookup(Symbol.valueOf(xdecl.field.getDeclaringClass().getName().replace('.', '$') + "$instance"));
                    cdecl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
                    aref.setContextDecl(cdecl);
                }
            }
        }
        if (isRunnable) {
            Method run = Compilation.typeRunnable.getDeclaredMethod("run", 0);
            if (decl != null) {
                dofind = new ReferenceExp(decl);
            } else if (instanceField != null) {
                dofind = new ApplyExp(SlotGet.staticField, new Expression[]{new QuoteExp(type), new QuoteExp("$instance")});
            }
            Expression dofind2 = new ApplyExp(run, new Expression[]{dofind});
            dofind2.setLine(tr);
            forms.addElement(dofind2);
            dofind = dofind2;
        }
        return true;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}

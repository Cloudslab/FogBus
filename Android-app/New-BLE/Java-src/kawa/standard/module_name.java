package kawa.standard;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.bytecode.ClassType;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LispLanguage;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.Symbol;
import kawa.lang.Syntax;
import kawa.lang.SyntaxForm;
import kawa.lang.Translator;

public class module_name extends Syntax {
    public static final module_name module_name = new module_name();

    static {
        module_name.setName("module-name");
    }

    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        int index;
        String className;
        ModuleExp module;
        String oldName;
        SyntaxForm form_cdr = form.getCdr();
        SyntaxForm nameSyntax = null;
        while (form_cdr instanceof SyntaxForm) {
            nameSyntax = form_cdr;
            form_cdr = nameSyntax.getDatum();
        }
        SyntaxForm arg = form_cdr instanceof Pair ? ((Pair) form_cdr).getCar() : null;
        while (arg instanceof SyntaxForm) {
            nameSyntax = arg;
            arg = nameSyntax.getDatum();
        }
        String name = null;
        String err = null;
        Declaration decl = null;
        if (arg instanceof Pair) {
            Pair p = (Pair) arg;
            if (p.getCar() == LispLanguage.quote_sym) {
                Pair arg2 = p.getCdr();
                if (arg2 instanceof Pair) {
                    p = arg2;
                    if (p.getCdr() == LList.Empty && (p.getCar() instanceof String)) {
                        name = (String) p.getCar();
                        if (err == null) {
                            tr.formStack.add(tr.syntaxError(err));
                        }
                        index = name.lastIndexOf(46);
                        className = name;
                        if (index < 0) {
                            tr.classPrefix = name.substring(0, index + 1);
                        } else {
                            name = tr.classPrefix + name;
                            className = tr.classPrefix + Compilation.mangleName(name);
                        }
                        module = tr.getModule();
                        if (tr.mainClass != null) {
                            tr.mainClass = new ClassType(className);
                        } else {
                            oldName = tr.mainClass.getName();
                            if (oldName != null) {
                                tr.mainClass.setName(className);
                            } else if (!oldName.equals(className)) {
                                tr.syntaxError("duplicate module-name: old name: " + oldName);
                            }
                        }
                        module.setType(tr.mainClass);
                        module.setName(name);
                        if (decl != null) {
                            decl.noteValue(new QuoteExp(tr.mainClass, Compilation.typeClass));
                            decl.setFlag(16793600);
                            if (module.outer == null) {
                                decl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                            }
                            decl.setPrivate(true);
                            decl.setType(Compilation.typeClass);
                        }
                        tr.mustCompileHere();
                        return;
                    }
                }
                err = "invalid quoted symbol for 'module-name'";
                if (err == null) {
                    index = name.lastIndexOf(46);
                    className = name;
                    if (index < 0) {
                        name = tr.classPrefix + name;
                        className = tr.classPrefix + Compilation.mangleName(name);
                    } else {
                        tr.classPrefix = name.substring(0, index + 1);
                    }
                    module = tr.getModule();
                    if (tr.mainClass != null) {
                        oldName = tr.mainClass.getName();
                        if (oldName != null) {
                            tr.mainClass.setName(className);
                        } else if (oldName.equals(className)) {
                            tr.syntaxError("duplicate module-name: old name: " + oldName);
                        }
                    } else {
                        tr.mainClass = new ClassType(className);
                    }
                    module.setType(tr.mainClass);
                    module.setName(name);
                    if (decl != null) {
                        decl.noteValue(new QuoteExp(tr.mainClass, Compilation.typeClass));
                        decl.setFlag(16793600);
                        if (module.outer == null) {
                            decl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                        }
                        decl.setPrivate(true);
                        decl.setType(Compilation.typeClass);
                    }
                    tr.mustCompileHere();
                    return;
                }
                tr.formStack.add(tr.syntaxError(err));
            }
        }
        if ((arg instanceof FString) || (arg instanceof String)) {
            name = arg.toString();
            if (err == null) {
                tr.formStack.add(tr.syntaxError(err));
            }
            index = name.lastIndexOf(46);
            className = name;
            if (index < 0) {
                tr.classPrefix = name.substring(0, index + 1);
            } else {
                name = tr.classPrefix + name;
                className = tr.classPrefix + Compilation.mangleName(name);
            }
            module = tr.getModule();
            if (tr.mainClass != null) {
                tr.mainClass = new ClassType(className);
            } else {
                oldName = tr.mainClass.getName();
                if (oldName != null) {
                    tr.mainClass.setName(className);
                } else if (oldName.equals(className)) {
                    tr.syntaxError("duplicate module-name: old name: " + oldName);
                }
            }
            module.setType(tr.mainClass);
            module.setName(name);
            if (decl != null) {
                decl.noteValue(new QuoteExp(tr.mainClass, Compilation.typeClass));
                decl.setFlag(16793600);
                if (module.outer == null) {
                    decl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                }
                decl.setPrivate(true);
                decl.setType(Compilation.typeClass);
            }
            tr.mustCompileHere();
            return;
        }
        if (arg instanceof Symbol) {
            name = arg.toString();
            int len = name.length();
            if (len > 2 && name.charAt(0) == '<' && name.charAt(len - 1) == '>') {
                name = name.substring(1, len - 1);
            }
            decl = tr.define(arg, nameSyntax, defs);
        } else {
            err = "un-implemented expression in module-name";
        }
        if (err == null) {
            index = name.lastIndexOf(46);
            className = name;
            if (index < 0) {
                name = tr.classPrefix + name;
                className = tr.classPrefix + Compilation.mangleName(name);
            } else {
                tr.classPrefix = name.substring(0, index + 1);
            }
            module = tr.getModule();
            if (tr.mainClass != null) {
                oldName = tr.mainClass.getName();
                if (oldName != null) {
                    tr.mainClass.setName(className);
                } else if (oldName.equals(className)) {
                    tr.syntaxError("duplicate module-name: old name: " + oldName);
                }
            } else {
                tr.mainClass = new ClassType(className);
            }
            module.setType(tr.mainClass);
            module.setName(name);
            if (decl != null) {
                decl.noteValue(new QuoteExp(tr.mainClass, Compilation.typeClass));
                decl.setFlag(16793600);
                if (module.outer == null) {
                    decl.setFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH);
                }
                decl.setPrivate(true);
                decl.setType(Compilation.typeClass);
            }
            tr.mustCompileHere();
            return;
        }
        tr.formStack.add(tr.syntaxError(err));
    }
}

package kawa.standard;

import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Language;
import gnu.expr.QuoteExp;
import gnu.expr.ScopeExp;
import gnu.kawa.lispexpr.LispReader;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.InPort;
import gnu.mapping.Symbol;
import gnu.text.SyntaxException;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import kawa.lang.AutoloadProcedure;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class define_autoload extends Syntax {
    public static final define_autoload define_autoload = new define_autoload("define-autoload", false);
    public static final define_autoload define_autoloads_from_file = new define_autoload("define-autoloads-from-file", true);
    boolean fromFile;

    public define_autoload(String name, boolean fromFile) {
        super(name);
        this.fromFile = fromFile;
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (!(st.getCdr() instanceof Pair)) {
            return super.scanForDefinitions(st, forms, defs, tr);
        }
        Pair p = (Pair) st.getCdr();
        if (this.fromFile) {
            while (p.getCar() instanceof CharSequence) {
                if (scanFile(p.getCar().toString(), defs, tr)) {
                    LList rest = p.getCdr();
                    if (rest != LList.Empty) {
                        if (!(rest instanceof Pair)) {
                            break;
                        }
                        p = (Pair) p.getCdr();
                    } else {
                        return true;
                    }
                }
                return false;
            }
            tr.syntaxError("invalid syntax for define-autoloads-from-file");
            return false;
        }
        Object names = p.getCar();
        if (p.getCdr() instanceof Pair) {
            return process(names, ((Pair) p.getCdr()).getCar(), forms, defs, tr);
        }
        tr.syntaxError("invalid syntax for define-autoload");
        return false;
    }

    public boolean scanFile(String filespec, ScopeExp defs, Translator tr) {
        File file = filespec.endsWith(".el") ? new File(filespec) : new File(filespec);
        if (!file.isAbsolute()) {
            file = new File(new File(tr.getFileName()).getParent(), filespec);
        }
        String filename = file.getPath();
        int dot = filename.lastIndexOf(46);
        if (dot >= 0) {
            String extension = filename.substring(dot);
            Language language = Language.getInstance(extension);
            if (language == null) {
                tr.syntaxError("unknown extension for " + filename);
                return true;
            }
            String prefix = tr.classPrefix;
            String cname = filespec.substring(0, filespec.length() - extension.length());
            while (cname.startsWith("../")) {
                int i = prefix.lastIndexOf(46, prefix.length() - 2);
                if (i < 0) {
                    tr.syntaxError("cannot use relative filename \"" + filespec + "\" with simple prefix \"" + prefix + "\"");
                    return false;
                }
                prefix = prefix.substring(0, i + 1);
                cname = cname.substring(3);
            }
            try {
                findAutoloadComments((LispReader) language.getLexer(InPort.openFile(filename), tr.getMessages()), (prefix + cname).replace('/', '.'), defs, tr);
            } catch (Exception ex) {
                tr.syntaxError("error reading " + filename + ": " + ex);
                return true;
            }
        }
        return true;
    }

    public static void findAutoloadComments(LispReader in, String filename, ScopeExp defs, Translator tr) throws IOException, SyntaxException {
        boolean lineStart = true;
        String magic = ";;;###autoload";
        int magicLength = magic.length();
        while (true) {
            int ch = in.peek();
            if (ch >= 0) {
                if (ch == 10 || ch == 13) {
                    in.read();
                    lineStart = true;
                } else {
                    if (lineStart && ch == 59) {
                        int i = 0;
                        while (i != magicLength) {
                            char ch2 = in.read();
                            if (ch2 < 0) {
                                return;
                            }
                            if (ch2 == 10 || ch2 == 13) {
                                lineStart = true;
                                break;
                            } else if (i >= 0) {
                                int i2 = i + 1;
                                if (ch2 == magic.charAt(i)) {
                                    i = i2;
                                } else {
                                    i = -1;
                                }
                            }
                        }
                        if (i > 0) {
                            Pair form = in.readObject();
                            if (form instanceof Pair) {
                                Pair pair = form;
                                AutoloadProcedure autoloadProcedure = null;
                                String name = null;
                                Object car = pair.getCar();
                                String command = car instanceof String ? car.toString() : car instanceof Symbol ? ((Symbol) car).getName() : null;
                                if (command == "defun") {
                                    name = ((Pair) pair.getCdr()).getCar().toString();
                                    AutoloadProcedure autoloadProcedure2 = new AutoloadProcedure(name, filename, tr.getLanguage());
                                } else {
                                    tr.error('w', "unsupported ;;;###autoload followed by: " + pair.getCar());
                                }
                                if (autoloadProcedure != null) {
                                    Declaration decl = defs.getDefine(name, 'w', tr);
                                    Expression ex = new QuoteExp(autoloadProcedure);
                                    decl.setFlag(16384);
                                    decl.noteValue(ex);
                                    decl.setProcedureDecl(true);
                                    decl.setType(Compilation.typeProcedure);
                                }
                            }
                            lineStart = false;
                        }
                    }
                    lineStart = false;
                    in.skip();
                    if (ch == 35 && in.peek() == 124) {
                        in.skip();
                        in.readNestedComment('#', '|');
                    } else if (!Character.isWhitespace((char) ch) && in.readObject(ch) == Sequence.eofValue) {
                        return;
                    }
                }
            } else {
                return;
            }
        }
    }

    public static boolean process(Object names, Object filename, Vector forms, ScopeExp defs, Translator tr) {
        if (names instanceof Pair) {
            Pair p = (Pair) names;
            if (process(p.getCar(), filename, forms, defs, tr) && process(p.getCdr(), filename, forms, defs, tr)) {
                return true;
            }
            return false;
        } else if (names == LList.Empty) {
            return true;
        } else {
            if (!(names instanceof String) && !(names instanceof Symbol)) {
                return false;
            }
            String name = names.toString();
            Declaration decl = defs.getDefine(name, 'w', tr);
            if (filename instanceof String) {
                String fn = (String) filename;
                int len = fn.length();
                if (len > 2 && fn.charAt(0) == '<' && fn.charAt(len - 1) == '>') {
                    filename = fn.substring(1, len - 1);
                }
            }
            Expression ex = new QuoteExp(new AutoloadProcedure(name, filename.toString(), tr.getLanguage()));
            decl.setFlag(16384);
            decl.noteValue(ex);
            return true;
        }
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}

package kawa;

import gnu.bytecode.ZipLoader;
import gnu.expr.Compilation;
import gnu.expr.CompiledModule;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleManager;
import gnu.lists.AbstractFormat;
import gnu.lists.Consumer;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.text.FilePath;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;

public class Shell {
    private static Class[] boolClasses = new Class[]{Boolean.TYPE};
    public static ThreadLocal currentLoadPath = new ThreadLocal();
    public static Object[] defaultFormatInfo;
    public static Method defaultFormatMethod;
    public static String defaultFormatName;
    static Object[][] formats;
    private static Class[] httpPrinterClasses = new Class[]{OutPort.class};
    private static Class[] noClasses = new Class[0];
    private static Object portArg = "(port)";
    private static Class[] xmlPrinterClasses = new Class[]{OutPort.class, Object.class};

    static {
        r0 = new Object[14][];
        r0[0] = new Object[]{"scheme", "gnu.kawa.functions.DisplayFormat", "getSchemeFormat", boolClasses, Boolean.FALSE};
        r0[1] = new Object[]{"readable-scheme", "gnu.kawa.functions.DisplayFormat", "getSchemeFormat", boolClasses, Boolean.TRUE};
        r0[2] = new Object[]{"elisp", "gnu.kawa.functions.DisplayFormat", "getEmacsLispFormat", boolClasses, Boolean.FALSE};
        r0[3] = new Object[]{"readable-elisp", "gnu.kawa.functions.DisplayFormat", "getEmacsLispFormat", boolClasses, Boolean.TRUE};
        r0[4] = new Object[]{"clisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.FALSE};
        r0[5] = new Object[]{"readable-clisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.TRUE};
        r0[6] = new Object[]{"commonlisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.FALSE};
        r0[7] = new Object[]{"readable-commonlisp", "gnu.kawa.functions.DisplayFormat", "getCommonLispFormat", boolClasses, Boolean.TRUE};
        r0[8] = new Object[]{"xml", "gnu.xml.XMLPrinter", "make", xmlPrinterClasses, portArg, null};
        r0[9] = new Object[]{"html", "gnu.xml.XMLPrinter", "make", xmlPrinterClasses, portArg, "html"};
        r0[10] = new Object[]{"xhtml", "gnu.xml.XMLPrinter", "make", xmlPrinterClasses, portArg, "xhtml"};
        r0[11] = new Object[]{"cgi", "gnu.kawa.xml.HttpPrinter", "make", httpPrinterClasses, portArg};
        r0[12] = new Object[]{"ignore", "gnu.lists.VoidConsumer", "getInstance", noClasses};
        r0[13] = new Object[]{null};
        formats = r0;
    }

    public static void setDefaultFormat(String name) {
        Object[] info;
        name = name.intern();
        defaultFormatName = name;
        int i = 0;
        while (true) {
            info = formats[i];
            String iname = info[0];
            if (iname == null) {
                System.err.println("kawa: unknown output format '" + name + "'");
                System.exit(-1);
            } else if (iname == name) {
                break;
            }
            i++;
        }
        defaultFormatInfo = info;
        try {
            defaultFormatMethod = Class.forName((String) info[1]).getMethod((String) info[2], (Class[]) info[3]);
        } catch (Throwable ex) {
            System.err.println("kawa:  caught " + ex + " while looking for format '" + name + "'");
            System.exit(-1);
        }
        if (!defaultFormatInfo[1].equals("gnu.lists.VoidConsumer")) {
            ModuleBody.setMainPrintValues(true);
        }
    }

    public static Consumer getOutputConsumer(OutPort out) {
        Object[] info = defaultFormatInfo;
        if (out == null) {
            return VoidConsumer.getInstance();
        }
        if (info == null) {
            return Language.getDefaultLanguage().getOutputConsumer(out);
        }
        try {
            Object[] args = new Object[(info.length - 4)];
            System.arraycopy(info, 4, args, 0, args.length);
            int i = args.length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                } else if (args[i] == portArg) {
                    args[i] = out;
                }
            }
            Object format = defaultFormatMethod.invoke(null, args);
            if (!(format instanceof AbstractFormat)) {
                return (Consumer) format;
            }
            out.objectFormat = (AbstractFormat) format;
            return out;
        } catch (Throwable ex) {
            RuntimeException runtimeException = new RuntimeException("cannot get output-format '" + defaultFormatName + "' - caught " + ex);
        }
    }

    public static boolean run(Language language, Environment env) {
        OutPort perr;
        InPort inp = InPort.inDefault();
        SourceMessages messages = new SourceMessages();
        if (inp instanceof TtyInPort) {
            Procedure prompter = language.getPrompter();
            if (prompter != null) {
                ((TtyInPort) inp).setPrompter(prompter);
            }
            perr = OutPort.errDefault();
        } else {
            perr = null;
        }
        Throwable ex = run(language, env, inp, OutPort.outDefault(), perr, messages);
        if (ex == null) {
            return true;
        }
        printError(ex, messages, OutPort.errDefault());
        return false;
    }

    public static Throwable run(Language language, Environment env, InPort inp, OutPort pout, OutPort perr, SourceMessages messages) {
        AbstractFormat saveFormat = null;
        if (pout != null) {
            saveFormat = pout.objectFormat;
        }
        try {
            Throwable run = run(language, env, inp, getOutputConsumer(pout), perr, null, messages);
            return run;
        } finally {
            if (pout != null) {
                pout.objectFormat = saveFormat;
            }
        }
    }

    public static boolean run(Language language, Environment env, InPort inp, Consumer out, OutPort perr, URL url) {
        SourceMessages messages = new SourceMessages();
        Throwable ex = run(language, env, inp, out, perr, url, messages);
        if (ex != null) {
            printError(ex, messages, perr);
        }
        return ex == null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Throwable run(gnu.expr.Language r18, gnu.mapping.Environment r19, gnu.mapping.InPort r20, gnu.lists.Consumer r21, gnu.mapping.OutPort r22, java.net.URL r23, gnu.text.SourceMessages r24) {
        /*
        r12 = gnu.expr.Language.setSaveCurrent(r18);
        r0 = r18;
        r1 = r20;
        r2 = r24;
        r8 = r0.getLexer(r1, r2);
        if (r22 == 0) goto L_0x0054;
    L_0x0010:
        r7 = 1;
    L_0x0011:
        r8.setInteractive(r7);
        r5 = gnu.mapping.CallContext.getInstance();
        r11 = 0;
        if (r21 == 0) goto L_0x0021;
    L_0x001b:
        r11 = r5.consumer;
        r0 = r21;
        r5.consumer = r0;
    L_0x0021:
        r14 = java.lang.Thread.currentThread();	 Catch:{ SecurityException -> 0x00dc }
        r10 = r14.getContextClassLoader();	 Catch:{ SecurityException -> 0x00dc }
        r15 = r10 instanceof gnu.bytecode.ArrayClassLoader;	 Catch:{ SecurityException -> 0x00dc }
        if (r15 != 0) goto L_0x0035;
    L_0x002d:
        r15 = new gnu.bytecode.ArrayClassLoader;	 Catch:{ SecurityException -> 0x00dc }
        r15.<init>(r10);	 Catch:{ SecurityException -> 0x00dc }
        r14.setContextClassLoader(r15);	 Catch:{ SecurityException -> 0x00dc }
    L_0x0035:
        r9 = 7;
        r15 = 0;
        r0 = r18;
        r4 = r0.parse(r8, r9, r15);	 Catch:{ Throwable -> 0x0064 }
        if (r7 == 0) goto L_0x0056;
    L_0x003f:
        r15 = 20;
        r0 = r24;
        r1 = r22;
        r13 = r0.checkErrors(r1, r15);	 Catch:{ Throwable -> 0x0064 }
    L_0x0049:
        if (r4 != 0) goto L_0x0071;
    L_0x004b:
        if (r21 == 0) goto L_0x004f;
    L_0x004d:
        r5.consumer = r11;
    L_0x004f:
        gnu.expr.Language.restoreCurrent(r12);
        r6 = 0;
    L_0x0053:
        return r6;
    L_0x0054:
        r7 = 0;
        goto L_0x0011;
    L_0x0056:
        r15 = r24.seenErrors();	 Catch:{ Throwable -> 0x0064 }
        if (r15 == 0) goto L_0x006f;
    L_0x005c:
        r15 = new gnu.text.SyntaxException;	 Catch:{ Throwable -> 0x0064 }
        r0 = r24;
        r15.<init>(r0);	 Catch:{ Throwable -> 0x0064 }
        throw r15;	 Catch:{ Throwable -> 0x0064 }
    L_0x0064:
        r6 = move-exception;
        if (r7 != 0) goto L_0x00d3;
    L_0x0067:
        if (r21 == 0) goto L_0x006b;
    L_0x0069:
        r5.consumer = r11;
    L_0x006b:
        gnu.expr.Language.restoreCurrent(r12);
        goto L_0x0053;
    L_0x006f:
        r13 = 0;
        goto L_0x0049;
    L_0x0071:
        if (r13 != 0) goto L_0x0035;
    L_0x0073:
        r15 = r4.getModule();	 Catch:{ Throwable -> 0x0064 }
        r16 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0064 }
        r16.<init>();	 Catch:{ Throwable -> 0x0064 }
        r17 = "atInteractiveLevel$";
        r16 = r16.append(r17);	 Catch:{ Throwable -> 0x0064 }
        r17 = gnu.expr.ModuleExp.interactiveCounter;	 Catch:{ Throwable -> 0x0064 }
        r17 = r17 + 1;
        gnu.expr.ModuleExp.interactiveCounter = r17;	 Catch:{ Throwable -> 0x0064 }
        r16 = r16.append(r17);	 Catch:{ Throwable -> 0x0064 }
        r16 = r16.toString();	 Catch:{ Throwable -> 0x0064 }
        r15.setName(r16);	 Catch:{ Throwable -> 0x0064 }
    L_0x0093:
        r3 = r20.read();	 Catch:{ Throwable -> 0x0064 }
        if (r3 < 0) goto L_0x00a1;
    L_0x0099:
        r15 = 13;
        if (r3 == r15) goto L_0x00a1;
    L_0x009d:
        r15 = 10;
        if (r3 != r15) goto L_0x00be;
    L_0x00a1:
        r0 = r19;
        r1 = r23;
        r2 = r22;
        r15 = gnu.expr.ModuleExp.evalModule(r0, r5, r4, r1, r2);	 Catch:{ Throwable -> 0x0064 }
        if (r15 == 0) goto L_0x0035;
    L_0x00ad:
        r0 = r21;
        r15 = r0 instanceof java.io.Writer;	 Catch:{ Throwable -> 0x0064 }
        if (r15 == 0) goto L_0x00bb;
    L_0x00b3:
        r0 = r21;
        r0 = (java.io.Writer) r0;	 Catch:{ Throwable -> 0x0064 }
        r15 = r0;
        r15.flush();	 Catch:{ Throwable -> 0x0064 }
    L_0x00bb:
        if (r3 >= 0) goto L_0x0035;
    L_0x00bd:
        goto L_0x004b;
    L_0x00be:
        r15 = 32;
        if (r3 == r15) goto L_0x0093;
    L_0x00c2:
        r15 = 9;
        if (r3 == r15) goto L_0x0093;
    L_0x00c6:
        r20.unread();	 Catch:{ Throwable -> 0x0064 }
        goto L_0x00a1;
    L_0x00ca:
        r15 = move-exception;
        if (r21 == 0) goto L_0x00cf;
    L_0x00cd:
        r5.consumer = r11;
    L_0x00cf:
        gnu.expr.Language.restoreCurrent(r12);
        throw r15;
    L_0x00d3:
        r0 = r24;
        r1 = r22;
        printError(r6, r0, r1);	 Catch:{ all -> 0x00ca }
        goto L_0x0035;
    L_0x00dc:
        r15 = move-exception;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.Shell.run(gnu.expr.Language, gnu.mapping.Environment, gnu.mapping.InPort, gnu.lists.Consumer, gnu.mapping.OutPort, java.net.URL, gnu.text.SourceMessages):java.lang.Throwable");
    }

    public static void printError(Throwable ex, SourceMessages messages, OutPort perr) {
        if (ex instanceof WrongArguments) {
            WrongArguments e = (WrongArguments) ex;
            messages.printAll((PrintWriter) perr, 20);
            if (e.usage != null) {
                perr.println("usage: " + e.usage);
            }
            e.printStackTrace(perr);
        } else if (ex instanceof ClassCastException) {
            messages.printAll((PrintWriter) perr, 20);
            perr.println("Invalid parameter, was: " + ex.getMessage());
            ex.printStackTrace(perr);
        } else {
            if (ex instanceof SyntaxException) {
                SyntaxException se = (SyntaxException) ex;
                if (se.getMessages() == messages) {
                    se.printAll(perr, 20);
                    se.clear();
                    return;
                }
            }
            messages.printAll((PrintWriter) perr, 20);
            ex.printStackTrace(perr);
        }
    }

    public static final CompiledModule checkCompiledZip(InputStream fs, Path path, Environment env, Language language) throws IOException {
        CompiledModule compiledModule = null;
        try {
            fs.mark(5);
            boolean isZip = fs.read() == 80 && fs.read() == 75 && fs.read() == 3 && fs.read() == 4;
            fs.reset();
            if (isZip) {
                fs.close();
                Environment orig_env = Environment.getCurrent();
                String name = path.toString();
                if (env != orig_env) {
                    try {
                        Environment.setCurrent(env);
                    } catch (IOException ex) {
                        throw new WrappedException("load: " + name + " - " + ex.toString(), ex);
                    } catch (Throwable th) {
                        if (env != orig_env) {
                            Environment.setCurrent(orig_env);
                        }
                    }
                }
                if (path instanceof FilePath) {
                    File zfile = ((FilePath) path).toFile();
                    if (!zfile.exists()) {
                        throw new RuntimeException("load: " + name + " - not found");
                    } else if (zfile.canRead()) {
                        compiledModule = CompiledModule.make(new ZipLoader(name).loadAllClasses(), language);
                        if (env != orig_env) {
                            Environment.setCurrent(orig_env);
                        }
                    } else {
                        throw new RuntimeException("load: " + name + " - not readable");
                    }
                }
                throw new RuntimeException("load: " + name + " - not a file path");
            }
        } catch (IOException e) {
        }
        return compiledModule;
    }

    public static boolean runFileOrClass(String fname, boolean lineByLine, int skipLines) {
        boolean z = false;
        Language language = Language.getDefaultLanguage();
        try {
            Path path;
            InputStream fs;
            if (fname.equals("-")) {
                path = Path.valueOf("/dev/stdin");
                fs = System.in;
            } else {
                path = Path.valueOf(fname);
                fs = path.openInputStream();
            }
            return runFile(fs, path, Environment.getCurrent(), lineByLine, skipLines);
        } catch (Throwable e) {
            try {
                CompiledModule.make(Class.forName(fname), language).evalModule(Environment.getCurrent(), OutPort.outDefault());
                return true;
            } catch (Throwable ex) {
                ex.printStackTrace();
                return z;
            }
        }
    }

    public static final boolean runFile(InputStream fs, Path path, Environment env, boolean lineByLine, int skipLines) throws Throwable {
        if (!(fs instanceof BufferedInputStream)) {
            fs = new BufferedInputStream(fs);
        }
        Language language = Language.getDefaultLanguage();
        Path savePath = (Path) currentLoadPath.get();
        InPort src;
        try {
            currentLoadPath.set(path);
            CompiledModule cmodule = checkCompiledZip(fs, path, env, language);
            if (cmodule == null) {
                src = InPort.openFile(fs, path);
                while (true) {
                    skipLines--;
                    if (skipLines < 0) {
                        break;
                    }
                    src.skipRestOfLine();
                }
                SourceMessages messages = new SourceMessages();
                URL url = path.toURL();
                if (lineByLine) {
                    Throwable ex = run(language, env, src, ModuleBody.getMainPrintValues() ? getOutputConsumer(OutPort.outDefault()) : new VoidConsumer(), null, url, messages);
                    if (ex != null) {
                        throw ex;
                    }
                }
                cmodule = compileSource(src, env, url, language, messages);
                messages.printAll(OutPort.errDefault(), 20);
                if (cmodule == null) {
                    src.close();
                    currentLoadPath.set(savePath);
                    return false;
                }
                src.close();
            }
            if (cmodule != null) {
                cmodule.evalModule(env, OutPort.outDefault());
            }
            currentLoadPath.set(savePath);
            return true;
        } catch (Throwable th) {
            currentLoadPath.set(savePath);
        }
    }

    static CompiledModule compileSource(InPort port, Environment env, URL url, Language language, SourceMessages messages) throws SyntaxException, IOException {
        Compilation comp = language.parse(port, messages, 1, ModuleManager.getInstance().findWithSourcePath(port.getName()));
        CallContext.getInstance().values = Values.noArgs;
        Object inst = ModuleExp.evalModule1(env, comp, url, null);
        if (inst == null || messages.seenErrors()) {
            return null;
        }
        return new CompiledModule(comp.getModule(), inst, language);
    }
}

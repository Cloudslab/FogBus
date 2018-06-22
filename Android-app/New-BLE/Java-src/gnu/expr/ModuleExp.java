package gnu.expr;

import gnu.bytecode.ArrayClassLoader;
import gnu.bytecode.ClassType;
import gnu.bytecode.Field;
import gnu.kawa.reflect.ClassMemberLocation;
import gnu.kawa.reflect.StaticFieldLocation;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.OutPort;
import gnu.mapping.Symbol;
import gnu.mapping.WrappedException;
import gnu.text.Path;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import java.io.Externalizable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ModuleExp extends LambdaExp implements Externalizable {
    public static final int EXPORT_SPECIFIED = 16384;
    public static final int IMMEDIATE = 1048576;
    public static final int LAZY_DECLARATIONS = 524288;
    public static final int NONSTATIC_SPECIFIED = 65536;
    public static final int STATIC_RUN_SPECIFIED = 262144;
    public static final int STATIC_SPECIFIED = 32768;
    public static final int SUPERTYPE_SPECIFIED = 131072;
    public static boolean alwaysCompile = compilerAvailable;
    public static boolean compilerAvailable = true;
    public static String dumpZipPrefix;
    public static int interactiveCounter;
    static int lastZipCounter;
    public static boolean neverCompile = false;
    ModuleInfo info;
    ClassType[] interfaces;
    ClassType superType;

    public static Class evalToClass(Compilation comp, URL url) throws SyntaxException {
        ModuleExp mexp = comp.getModule();
        SourceMessages messages = comp.getMessages();
        try {
            comp.minfo.loadByStages(12);
            if (messages.seenErrors()) {
                return null;
            }
            int iClass;
            ArrayClassLoader loader = comp.loader;
            if (url == null) {
                url = Path.currentPath().toURL();
            }
            loader.setResourceContext(url);
            ZipOutputStream zout = null;
            if (dumpZipPrefix != null) {
                StringBuffer stringBuffer = new StringBuffer(dumpZipPrefix);
                lastZipCounter++;
                if (interactiveCounter > lastZipCounter) {
                    lastZipCounter = interactiveCounter;
                }
                stringBuffer.append(lastZipCounter);
                stringBuffer.append(".zip");
                ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(stringBuffer.toString()));
            }
            for (iClass = 0; iClass < comp.numClasses; iClass++) {
                ClassType clas = comp.classes[iClass];
                String className = clas.getName();
                byte[] classBytes = clas.writeToArray();
                loader.addClass(className, classBytes);
                if (zout != null) {
                    ZipEntry zipEntry = new ZipEntry(className.replace('.', '/') + ".class");
                    zipEntry.setSize((long) classBytes.length);
                    CRC32 crc = new CRC32();
                    crc.update(classBytes);
                    zipEntry.setCrc(crc.getValue());
                    zipEntry.setMethod(0);
                    zout.putNextEntry(zipEntry);
                    zout.write(classBytes);
                }
            }
            if (zout != null) {
                zout.close();
            }
            Class clas2 = null;
            ArrayClassLoader context = loader;
            while (context.getParent() instanceof ArrayClassLoader) {
                context = (ArrayClassLoader) context.getParent();
            }
            for (iClass = 0; iClass < comp.numClasses; iClass++) {
                ClassType ctype = comp.classes[iClass];
                Class cclass = loader.loadClass(ctype.getName());
                ctype.setReflectClass(cclass);
                ctype.setExisting(true);
                if (iClass == 0) {
                    clas2 = cclass;
                } else if (context != loader) {
                    context.addClass(cclass);
                }
            }
            ModuleInfo minfo = comp.minfo;
            minfo.setModuleClass(clas2);
            comp.cleanupAfterCompilation();
            int ndeps = minfo.numDependencies;
            for (int idep = 0; idep < ndeps; idep++) {
                ModuleInfo dep = minfo.dependencies[idep];
                Class dclass = dep.getModuleClassRaw();
                if (dclass == null) {
                    dclass = evalToClass(dep.comp, null);
                }
                comp.loader.addClass(dclass);
            }
            return clas2;
        } catch (IOException ex) {
            throw new WrappedException("I/O error in lambda eval", ex);
        } catch (ClassNotFoundException ex2) {
            throw new WrappedException("class not found in lambda eval", ex2);
        } catch (Throwable ex3) {
            comp.getMessages().error('f', "internal compile error - caught " + ex3, ex3);
            SyntaxException syntaxException = new SyntaxException(messages);
        }
    }

    public static void mustNeverCompile() {
        alwaysCompile = false;
        neverCompile = true;
        compilerAvailable = false;
    }

    public static void mustAlwaysCompile() {
        alwaysCompile = true;
        neverCompile = false;
    }

    public static final boolean evalModule(Environment env, CallContext ctx, Compilation comp, URL url, OutPort msg) throws Throwable {
        ModuleExp mexp = comp.getModule();
        Language language = comp.getLanguage();
        Object inst = evalModule1(env, comp, url, msg);
        if (inst == null) {
            return false;
        }
        evalModule2(env, ctx, language, mexp, inst);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object evalModule1(gnu.mapping.Environment r11, gnu.expr.Compilation r12, java.net.URL r13, gnu.mapping.OutPort r14) throws gnu.text.SyntaxException {
        /*
        r10 = 0;
        r8 = 0;
        r3 = r12.getModule();
        r9 = r12.minfo;
        r3.info = r9;
        r5 = gnu.mapping.Environment.setSaveCurrent(r11);
        r4 = gnu.expr.Compilation.setSaveCurrent(r12);
        r2 = r12.getMessages();
        r6 = 0;
        r7 = 0;
        r9 = alwaysCompile;
        if (r9 == 0) goto L_0x0028;
    L_0x001c:
        r9 = neverCompile;
        if (r9 == 0) goto L_0x0028;
    L_0x0020:
        r8 = new java.lang.RuntimeException;
        r9 = "alwaysCompile and neverCompile are both true!";
        r8.<init>(r9);
        throw r8;
    L_0x0028:
        r9 = neverCompile;
        if (r9 == 0) goto L_0x002e;
    L_0x002c:
        r12.mustCompile = r10;
    L_0x002e:
        r9 = 6;
        r12.process(r9);	 Catch:{ all -> 0x00f3 }
        r9 = r12.minfo;	 Catch:{ all -> 0x00f3 }
        r10 = 8;
        r9.loadByStages(r10);	 Catch:{ all -> 0x00f3 }
        if (r14 == 0) goto L_0x0050;
    L_0x003b:
        r9 = 20;
        r9 = r2.checkErrors(r14, r9);	 Catch:{ all -> 0x00f3 }
        if (r9 == 0) goto L_0x0056;
    L_0x0043:
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
        if (r7 == 0) goto L_0x004e;
    L_0x004b:
        r7.setContextClassLoader(r6);
    L_0x004e:
        r0 = r8;
    L_0x004f:
        return r0;
    L_0x0050:
        r9 = r2.seenErrors();	 Catch:{ all -> 0x00f3 }
        if (r9 != 0) goto L_0x0043;
    L_0x0056:
        r9 = r12.mustCompile;	 Catch:{ all -> 0x00f3 }
        if (r9 != 0) goto L_0x0099;
    L_0x005a:
        r8 = gnu.expr.Compilation.debugPrintFinalExpr;	 Catch:{ all -> 0x00f3 }
        if (r8 == 0) goto L_0x008b;
    L_0x005e:
        if (r14 == 0) goto L_0x008b;
    L_0x0060:
        r8 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f3 }
        r8.<init>();	 Catch:{ all -> 0x00f3 }
        r9 = "[Evaluating final module \"";
        r8 = r8.append(r9);	 Catch:{ all -> 0x00f3 }
        r9 = r3.getName();	 Catch:{ all -> 0x00f3 }
        r8 = r8.append(r9);	 Catch:{ all -> 0x00f3 }
        r9 = "\":";
        r8 = r8.append(r9);	 Catch:{ all -> 0x00f3 }
        r8 = r8.toString();	 Catch:{ all -> 0x00f3 }
        r14.println(r8);	 Catch:{ all -> 0x00f3 }
        r3.print(r14);	 Catch:{ all -> 0x00f3 }
        r8 = 93;
        r14.println(r8);	 Catch:{ all -> 0x00f3 }
        r14.flush();	 Catch:{ all -> 0x00f3 }
    L_0x008b:
        r0 = java.lang.Boolean.TRUE;	 Catch:{ all -> 0x00f3 }
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
        if (r7 == 0) goto L_0x004f;
    L_0x0095:
        r7.setContextClassLoader(r6);
        goto L_0x004f;
    L_0x0099:
        r0 = evalToClass(r12, r13);	 Catch:{ all -> 0x00f3 }
        if (r0 != 0) goto L_0x00ac;
    L_0x009f:
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
        if (r7 == 0) goto L_0x00aa;
    L_0x00a7:
        r7.setContextClassLoader(r6);
    L_0x00aa:
        r0 = r8;
        goto L_0x004f;
    L_0x00ac:
        r7 = java.lang.Thread.currentThread();	 Catch:{ Throwable -> 0x00dd }
        r6 = r7.getContextClassLoader();	 Catch:{ Throwable -> 0x00dd }
        r8 = r0.getClassLoader();	 Catch:{ Throwable -> 0x00dd }
        r7.setContextClassLoader(r8);	 Catch:{ Throwable -> 0x00dd }
    L_0x00bb:
        r8 = 0;
        r3.body = r8;	 Catch:{ all -> 0x00f3 }
        r8 = 0;
        r3.thisVariable = r8;	 Catch:{ all -> 0x00f3 }
        if (r14 == 0) goto L_0x00e0;
    L_0x00c3:
        r8 = 20;
        r8 = r2.checkErrors(r14, r8);	 Catch:{ all -> 0x00f3 }
        if (r8 == 0) goto L_0x00e6;
    L_0x00cb:
        r8 = 0;
        r0 = java.lang.Boolean.valueOf(r8);	 Catch:{ all -> 0x00f3 }
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
        if (r7 == 0) goto L_0x004f;
    L_0x00d8:
        r7.setContextClassLoader(r6);
        goto L_0x004f;
    L_0x00dd:
        r1 = move-exception;
        r7 = 0;
        goto L_0x00bb;
    L_0x00e0:
        r8 = r2.seenErrors();	 Catch:{ all -> 0x00f3 }
        if (r8 != 0) goto L_0x00cb;
    L_0x00e6:
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
        if (r7 == 0) goto L_0x004f;
    L_0x00ee:
        r7.setContextClassLoader(r6);
        goto L_0x004f;
    L_0x00f3:
        r8 = move-exception;
        gnu.mapping.Environment.restoreCurrent(r5);
        gnu.expr.Compilation.restoreCurrent(r4);
        if (r7 == 0) goto L_0x00ff;
    L_0x00fc:
        r7.setContextClassLoader(r6);
    L_0x00ff:
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ModuleExp.evalModule1(gnu.mapping.Environment, gnu.expr.Compilation, java.net.URL, gnu.mapping.OutPort):java.lang.Object");
    }

    public static final void evalModule2(Environment env, CallContext ctx, Language language, ModuleExp mexp, Object inst) throws Throwable {
        Environment orig_env = Environment.setSaveCurrent(env);
        Thread thread = null;
        try {
            if (inst == Boolean.TRUE) {
                mexp.body.apply(ctx);
            } else {
                if (inst instanceof Class) {
                    inst = ModuleContext.getContext().findInstance((Class) inst);
                }
                if (inst instanceof Runnable) {
                    if (inst instanceof ModuleBody) {
                        ModuleBody mb = (ModuleBody) inst;
                        if (!mb.runDone) {
                            mb.runDone = true;
                            mb.run(ctx);
                        }
                    } else {
                        ((Runnable) inst).run();
                    }
                }
                if (mexp == null) {
                    ClassMemberLocation.defineAll(inst, language, env);
                } else {
                    for (Declaration decl = mexp.firstDecl(); decl != null; decl = decl.nextDecl()) {
                        Object dname = decl.getSymbol();
                        if (!(decl.isPrivate() || dname == null)) {
                            Symbol sym;
                            Field fld = decl.field;
                            if (dname instanceof Symbol) {
                                sym = (Symbol) dname;
                            } else {
                                sym = Symbol.make("", dname.toString().intern());
                            }
                            Object property = language.getEnvPropertyFor(decl);
                            Expression dvalue = decl.getValue();
                            if ((decl.field.getModifiers() & 16) != 0) {
                                Object value;
                                if (!(dvalue instanceof QuoteExp) || dvalue == QuoteExp.undefined_exp) {
                                    value = decl.field.getReflectField().get(null);
                                    if (!decl.isIndirectBinding()) {
                                        decl.setValue(QuoteExp.getInstance(value));
                                    } else if (!(decl.isAlias() && (dvalue instanceof ReferenceExp))) {
                                        decl.setValue(null);
                                    }
                                } else {
                                    value = ((QuoteExp) dvalue).getValue();
                                }
                                if (decl.isIndirectBinding()) {
                                    env.addLocation(sym, property, (Location) value);
                                } else {
                                    env.define(sym, property, value);
                                }
                            } else {
                                StaticFieldLocation loc = new StaticFieldLocation(fld.getDeclaringClass(), fld.getName());
                                loc.setDeclaration(decl);
                                env.addLocation(sym, property, loc);
                                decl.setValue(null);
                            }
                        }
                    }
                }
            }
            ctx.runUntilDone();
        } finally {
            Environment.restoreCurrent(orig_env);
            if (thread != null) {
                thread.setContextClassLoader(null);
            }
        }
    }

    public String getNamespaceUri() {
        return this.info.uri;
    }

    public final ClassType getSuperType() {
        return this.superType;
    }

    public final void setSuperType(ClassType s) {
        this.superType = s;
    }

    public final ClassType[] getInterfaces() {
        return this.interfaces;
    }

    public final void setInterfaces(ClassType[] s) {
        this.interfaces = s;
    }

    public final boolean isStatic() {
        return getFlag(32768) || !((Compilation.moduleStatic < 0 && !getFlag(1048576)) || getFlag(131072) || getFlag(65536));
    }

    public boolean staticInitRun() {
        return isStatic() && (getFlag(262144) || Compilation.moduleStatic == 2);
    }

    public void allocChildClasses(Compilation comp) {
        declareClosureEnv();
        if (comp.usingCPStyle()) {
            allocFrame(comp);
        }
    }

    void allocFields(Compilation comp) {
        Declaration decl = firstDecl();
        while (decl != null) {
            if ((!decl.isSimple() || decl.isPublic()) && decl.field == null && decl.getFlag(65536) && decl.getFlag(6)) {
                decl.makeField(comp, null);
            }
            decl = decl.nextDecl();
        }
        decl = firstDecl();
        while (decl != null) {
            if (decl.field == null) {
                Expression value = decl.getValue();
                if ((!decl.isSimple() || decl.isPublic() || decl.isNamespaceDecl() || (decl.getFlag(16384) && decl.getFlag(6))) && !decl.getFlag(65536)) {
                    if (!(value instanceof LambdaExp) || (value instanceof ModuleExp) || (value instanceof ClassExp)) {
                        if (!(decl.shouldEarlyInit() || decl.isAlias())) {
                            value = null;
                        }
                        decl.makeField(comp, value);
                    } else {
                        ((LambdaExp) value).allocFieldFor(comp);
                    }
                }
            }
            decl = decl.nextDecl();
        }
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitModuleExp(this, d);
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(Module/", ")", 2);
        Object sym = getSymbol();
        if (sym != null) {
            out.print(sym);
            out.print('/');
        }
        out.print(this.id);
        out.print('/');
        out.writeSpaceFill();
        out.startLogicalBlock("(", false, ")");
        Declaration decl = firstDecl();
        if (decl != null) {
            out.print("Declarations:");
            while (decl != null) {
                out.writeSpaceFill();
                decl.printInfo(out);
                decl = decl.nextDecl();
            }
        }
        out.endLogicalBlock(")");
        out.writeSpaceLinear();
        if (this.body == null) {
            out.print("<null body>");
        } else {
            this.body.print(out);
        }
        out.endLogicalBlock(")");
    }

    public Declaration firstDecl() {
        synchronized (this) {
            if (getFlag(524288)) {
                this.info.setupModuleExp();
            }
        }
        return this.decls;
    }

    public ClassType classFor(Compilation comp) {
        if (this.type != null && this.type != Compilation.typeProcedure) {
            return this.type;
        }
        String className;
        ClassType clas;
        String fileName = getFileName();
        String mname = getName();
        Path path = null;
        if (mname != null) {
            fileName = mname;
        } else if (fileName == null) {
            fileName = getName();
            if (fileName == null) {
                fileName = "$unnamed_input_file$";
            }
        } else if (this.filename.equals("-") || this.filename.equals("/dev/stdin")) {
            fileName = getName();
            if (fileName == null) {
                fileName = "$stdin$";
            }
        } else {
            path = Path.valueOf(fileName);
            fileName = path.getLast();
            int dotIndex = fileName.lastIndexOf(46);
            if (dotIndex > 0) {
                fileName = fileName.substring(0, dotIndex);
            }
        }
        if (getName() == null) {
            setName(fileName);
        }
        fileName = Compilation.mangleNameIfNeeded(fileName);
        if (!(comp.classPrefix.length() != 0 || path == null || path.isAbsolute())) {
            Path parentPath = path.getParent();
            if (parentPath != null) {
                String parent = parentPath.toString();
                if (parent.length() > 0 && parent.indexOf("..") < 0) {
                    parent = parent.replaceAll(System.getProperty("file.separator"), "/");
                    if (parent.startsWith("./")) {
                        parent = parent.substring(2);
                    }
                    className = parent.equals(".") ? fileName : Compilation.mangleURI(parent) + "." + fileName;
                    clas = new ClassType(className);
                    setType(clas);
                    if (comp.mainLambda == this) {
                        return clas;
                    }
                    if (comp.mainClass == null) {
                        comp.mainClass = clas;
                        return clas;
                    } else if (!className.equals(comp.mainClass.getName())) {
                        return clas;
                    } else {
                        comp.error('e', "inconsistent main class name: " + className + " - old name: " + comp.mainClass.getName());
                        return clas;
                    }
                }
            }
        }
        className = comp.classPrefix + fileName;
        clas = new ClassType(className);
        setType(clas);
        if (comp.mainLambda == this) {
            return clas;
        }
        if (comp.mainClass == null) {
            comp.mainClass = clas;
            return clas;
        } else if (!className.equals(comp.mainClass.getName())) {
            return clas;
        } else {
            comp.error('e', "inconsistent main class name: " + className + " - old name: " + comp.mainClass.getName());
            return clas;
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        String name = null;
        if (this.type == null || this.type == Compilation.typeProcedure || this.type.isExisting()) {
            if (null == null) {
                name = getName();
            }
            if (name == null) {
                name = getFileName();
            }
            out.writeObject(name);
            return;
        }
        out.writeObject(this.type);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        Object name = in.readObject();
        if (name instanceof ClassType) {
            this.type = (ClassType) name;
            setName(this.type.getName());
        } else {
            setName((String) name);
        }
        this.flags |= 524288;
    }
}

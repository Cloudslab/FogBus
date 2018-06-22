package gnu.expr;

import android.support.v4.media.session.PlaybackStateCompat;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import java.util.Set;
import java.util.Vector;

public class LambdaExp extends ScopeExp {
    public static final int ATTEMPT_INLINE = 4096;
    static final int CANNOT_INLINE = 32;
    static final int CAN_CALL = 4;
    static final int CAN_READ = 2;
    static final int CLASS_METHOD = 64;
    static final int DEFAULT_CAPTURES_ARG = 512;
    static final int IMPORTS_LEX_VARS = 8;
    static final int INLINE_ONLY = 8192;
    static final int METHODS_COMPILED = 128;
    static final int NEEDS_STATIC_LINK = 16;
    protected static final int NEXT_AVAIL_FLAG = 16384;
    public static final int NO_FIELD = 256;
    public static final int OVERLOADABLE_FIELD = 2048;
    public static final int SEQUENCE_RESULT = 1024;
    static Method searchForKeywordMethod3;
    static Method searchForKeywordMethod4;
    static final ApplyExp unknownContinuation = new ApplyExp((Expression) null, null);
    Vector applyMethods;
    Variable argsArray;
    public Expression body;
    Declaration capturedVars;
    Variable closureEnv;
    public Field closureEnvField;
    public Expression[] defaultArgs;
    private Declaration firstArgsArrayArg;
    public LambdaExp firstChild;
    Variable heapFrame;
    Initializer initChain;
    public LambdaExp inlineHome;
    public Keyword[] keywords;
    public int max_args;
    public int min_args;
    public Declaration nameDecl;
    public LambdaExp nextSibling;
    Method[] primBodyMethods;
    Method[] primMethods;
    Object[] properties;
    public Expression returnContinuation;
    public Type returnType;
    int selectorValue;
    public Field staticLinkField;
    Set<LambdaExp> tailCallers;
    Procedure thisValue;
    Variable thisVariable;
    Expression[] throwsSpecification;
    ClassType type = Compilation.typeProcedure;

    public void capture(Declaration decl) {
        if (decl.isSimple()) {
            if (!(this.capturedVars != null || decl.isStatic() || (this instanceof ModuleExp) || (this instanceof ClassExp))) {
                this.heapFrame = new Variable("heapFrame");
            }
            decl.setSimple(false);
            if (!decl.isPublic()) {
                decl.nextCapturedVar = this.capturedVars;
                this.capturedVars = decl;
            }
        }
    }

    public void setExceptions(Expression[] exceptions) {
        this.throwsSpecification = exceptions;
    }

    public final boolean getInlineOnly() {
        return (this.flags & 8192) != 0;
    }

    public final void setInlineOnly(boolean inlineOnly) {
        setFlag(inlineOnly, 8192);
    }

    public final boolean getNeedsClosureEnv() {
        return (this.flags & 24) != 0;
    }

    public final boolean getNeedsStaticLink() {
        return (this.flags & 16) != 0;
    }

    public final void setNeedsStaticLink(boolean needsStaticLink) {
        if (needsStaticLink) {
            this.flags |= 16;
        } else {
            this.flags &= -17;
        }
    }

    public final boolean getImportsLexVars() {
        return (this.flags & 8) != 0;
    }

    public final void setImportsLexVars(boolean importsLexVars) {
        if (importsLexVars) {
            this.flags |= 8;
        } else {
            this.flags &= -9;
        }
    }

    public final void setImportsLexVars() {
        int old = this.flags;
        this.flags |= 8;
        if ((old & 8) == 0 && this.nameDecl != null) {
            setCallersNeedStaticLink();
        }
    }

    public final void setNeedsStaticLink() {
        int old = this.flags;
        this.flags |= 16;
        if ((old & 16) == 0 && this.nameDecl != null) {
            setCallersNeedStaticLink();
        }
    }

    void setCallersNeedStaticLink() {
        LambdaExp outer = outerLambda();
        for (ApplyExp app = this.nameDecl.firstCall; app != null; app = app.nextCall) {
            LambdaExp caller = app.context;
            while (caller != outer && !(caller instanceof ModuleExp)) {
                caller.setNeedsStaticLink();
                caller = caller.outerLambda();
            }
        }
    }

    public final boolean getCanRead() {
        return (this.flags & 2) != 0;
    }

    public final void setCanRead(boolean read) {
        if (read) {
            this.flags |= 2;
        } else {
            this.flags &= -3;
        }
    }

    public final boolean getCanCall() {
        return (this.flags & 4) != 0;
    }

    public final void setCanCall(boolean called) {
        if (called) {
            this.flags |= 4;
        } else {
            this.flags &= -5;
        }
    }

    public final boolean isClassMethod() {
        return (this.flags & 64) != 0;
    }

    public final void setClassMethod(boolean isMethod) {
        if (isMethod) {
            this.flags |= 64;
        } else {
            this.flags &= -65;
        }
    }

    public final boolean isModuleBody() {
        return this instanceof ModuleExp;
    }

    public final boolean isClassGenerated() {
        return isModuleBody() || (this instanceof ClassExp);
    }

    public boolean isAbstract() {
        return this.body == QuoteExp.abstractExp;
    }

    public int getCallConvention() {
        if (isModuleBody()) {
            if (Compilation.defaultCallConvention >= 2) {
                return Compilation.defaultCallConvention;
            }
            return 2;
        } else if (isClassMethod()) {
            return 1;
        } else {
            return Compilation.defaultCallConvention != 0 ? Compilation.defaultCallConvention : 1;
        }
    }

    public final boolean isHandlingTailCalls() {
        return isModuleBody() || (Compilation.defaultCallConvention >= 3 && !isClassMethod());
    }

    public final boolean variable_args() {
        return this.max_args < 0;
    }

    protected ClassType getCompiledClassType(Compilation comp) {
        if (this.type != Compilation.typeProcedure) {
            return this.type;
        }
        throw new Error("internal error: getCompiledClassType");
    }

    public Type getType() {
        return this.type;
    }

    public ClassType getClassType() {
        return this.type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    public int incomingArgs() {
        return (this.min_args != this.max_args || this.max_args > 4 || this.max_args <= 0) ? 1 : this.max_args;
    }

    int getSelectorValue(Compilation comp) {
        int s = this.selectorValue;
        if (s != 0) {
            return s;
        }
        s = comp.maxSelectorValue;
        comp.maxSelectorValue = this.primMethods.length + s;
        s++;
        this.selectorValue = s;
        return s;
    }

    public final Method getMethod(int argCount) {
        if (this.primMethods == null) {
            return null;
        }
        if (this.max_args >= 0 && argCount > this.max_args) {
            return null;
        }
        int index = argCount - this.min_args;
        if (index < 0) {
            return null;
        }
        int length = this.primMethods.length;
        Method[] methodArr = this.primMethods;
        if (index >= length) {
            index = length - 1;
        }
        return methodArr[index];
    }

    public final Method getMainMethod() {
        Method[] methods = this.primBodyMethods;
        return methods == null ? null : methods[methods.length - 1];
    }

    public final Type restArgType() {
        if (this.min_args == this.max_args) {
            return null;
        }
        if (this.primMethods == null) {
            throw new Error("internal error - restArgType");
        }
        Method[] methods = this.primMethods;
        if (this.max_args >= 0 && methods.length > this.max_args - this.min_args) {
            return null;
        }
        Method method = methods[methods.length - 1];
        Type[] types = method.getParameterTypes();
        int ilast = types.length - 1;
        if (method.getName().endsWith("$X")) {
            ilast--;
        }
        return types[ilast];
    }

    public LambdaExp outerLambda() {
        return this.outer == null ? null : this.outer.currentLambda();
    }

    public LambdaExp outerLambdaNotInline() {
        ScopeExp exp = this;
        while (true) {
            exp = exp.outer;
            if (exp == null) {
                return null;
            }
            if (exp instanceof LambdaExp) {
                LambdaExp result = (LambdaExp) exp;
                if (!result.getInlineOnly()) {
                    return result;
                }
            }
        }
    }

    boolean inlinedIn(LambdaExp outer) {
        for (LambdaExp exp = this; exp.getInlineOnly(); exp = exp.getCaller()) {
            if (exp == outer) {
                return true;
            }
        }
        return false;
    }

    public LambdaExp getCaller() {
        return this.inlineHome;
    }

    public Variable declareThis(ClassType clas) {
        if (this.thisVariable == null) {
            this.thisVariable = new Variable("this");
            getVarScope().addVariableAfter(null, this.thisVariable);
            this.thisVariable.setParameter(true);
        }
        if (this.thisVariable.getType() == null) {
            this.thisVariable.setType(clas);
        }
        if (this.decls != null && this.decls.isThisParameter()) {
            this.decls.var = this.thisVariable;
        }
        return this.thisVariable;
    }

    public Variable declareClosureEnv() {
        if (this.closureEnv == null && getNeedsClosureEnv()) {
            LambdaExp parent = outerLambda();
            if (parent instanceof ClassExp) {
                parent = parent.outerLambda();
            }
            Variable parentFrame = parent.heapFrame != null ? parent.heapFrame : parent.closureEnv;
            if (isClassMethod() && !"*init*".equals(getName())) {
                this.closureEnv = declareThis(this.type);
            } else if (parent.heapFrame == null && !parent.getNeedsStaticLink() && !(parent instanceof ModuleExp)) {
                this.closureEnv = null;
            } else if (!isClassGenerated() && !getInlineOnly()) {
                Method primMethod = getMainMethod();
                boolean isInit = "*init*".equals(getName());
                if (primMethod.getStaticFlag() || isInit) {
                    Variable prev;
                    this.closureEnv = new Variable("closureEnv", primMethod.getParameterTypes()[0]);
                    if (isInit) {
                        prev = declareThis(primMethod.getDeclaringClass());
                    } else {
                        prev = null;
                    }
                    getVarScope().addVariableAfter(prev, this.closureEnv);
                    this.closureEnv.setParameter(true);
                } else {
                    this.closureEnv = declareThis(primMethod.getDeclaringClass());
                }
            } else if (inlinedIn(parent)) {
                this.closureEnv = parentFrame;
            } else {
                this.closureEnv = new Variable("closureEnv", parentFrame.getType());
                getVarScope().addVariable(this.closureEnv);
            }
        }
        return this.closureEnv;
    }

    public LambdaExp(int args) {
        this.min_args = args;
        this.max_args = args;
    }

    public LambdaExp(Expression body) {
        this.body = body;
    }

    public void loadHeapFrame(Compilation comp) {
        LambdaExp curLambda = comp.curLambda;
        while (curLambda != this && curLambda.getInlineOnly()) {
            curLambda = curLambda.getCaller();
        }
        CodeAttr code = comp.getCode();
        if (curLambda.heapFrame == null || this != curLambda) {
            ClassType curType;
            if (curLambda.closureEnv != null) {
                code.emitLoad(curLambda.closureEnv);
                curType = (ClassType) curLambda.closureEnv.getType();
            } else {
                code.emitPushThis();
                curType = comp.curClass;
            }
            while (curLambda != this) {
                Field link = curLambda.staticLinkField;
                if (link != null && link.getDeclaringClass() == r2) {
                    code.emitGetField(link);
                    curType = (ClassType) link.getType();
                }
                curLambda = curLambda.outerLambda();
            }
            return;
        }
        code.emitLoad(curLambda.heapFrame);
    }

    Declaration getArg(int i) {
        for (Declaration var = firstDecl(); var != null; var = var.nextDecl()) {
            if (i == 0) {
                return var;
            }
            i--;
        }
        throw new Error("internal error - getArg");
    }

    public void compileEnd(Compilation comp) {
        CodeAttr code = comp.getCode();
        if (!getInlineOnly()) {
            if (comp.method.reachableHere() && (Compilation.defaultCallConvention < 3 || isModuleBody() || isClassMethod() || isHandlingTailCalls())) {
                code.emitReturn();
            }
            popScope(code);
            code.popScope();
        }
        LambdaExp child = this.firstChild;
        while (child != null) {
            if (!(child.getCanRead() || child.getInlineOnly())) {
                child.compileAsMethod(comp);
            }
            child = child.nextSibling;
        }
        if (this.heapFrame != null) {
            comp.generateConstructor(this);
        }
    }

    public void generateApplyMethods(Compilation comp) {
        comp.generateMatchMethods(this);
        if (Compilation.defaultCallConvention >= 2) {
            comp.generateApplyMethodsWithContext(this);
        } else {
            comp.generateApplyMethodsWithoutContext(this);
        }
    }

    Field allocFieldFor(Compilation comp) {
        if (this.nameDecl != null && this.nameDecl.field != null) {
            return this.nameDecl.field;
        }
        boolean needsClosure = getNeedsClosureEnv();
        ClassType frameType = needsClosure ? getOwningLambda().getHeapFrameType() : comp.mainClass;
        String name = getName();
        String fname = name == null ? "lambda" : Compilation.mangleNameIfNeeded(name);
        int fflags = 16;
        if (this.nameDecl == null || !(this.nameDecl.context instanceof ModuleExp)) {
            StringBuilder append = new StringBuilder().append(fname).append("$Fn");
            int i = comp.localFieldIndex + 1;
            comp.localFieldIndex = i;
            fname = append.append(i).toString();
            if (!needsClosure) {
                fflags = 16 | 8;
            }
        } else {
            boolean external_access = this.nameDecl.needsExternalAccess();
            if (external_access) {
                fname = Declaration.PRIVATE_PREFIX + fname;
            }
            if (this.nameDecl.getFlag(PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH)) {
                fflags = 16 | 8;
                if (!((ModuleExp) this.nameDecl.context).isStatic()) {
                    fflags &= -17;
                }
            }
            if (!this.nameDecl.isPrivate() || external_access || comp.immediate) {
                fflags |= 1;
            }
            if ((this.flags & 2048) != 0) {
                String fname0 = fname;
                int suffix = this.min_args == this.max_args ? this.min_args : 1;
                while (true) {
                    int suffix2 = suffix + 1;
                    fname = fname0 + '$' + suffix;
                    if (frameType.getDeclaredField(fname) == null) {
                        break;
                    }
                    suffix = suffix2;
                }
            }
        }
        Field field = frameType.addField(fname, Compilation.typeModuleMethod, fflags);
        if (this.nameDecl == null) {
            return field;
        }
        this.nameDecl.field = field;
        return field;
    }

    final void addApplyMethod(Compilation comp, Field field) {
        LambdaExp owner = this;
        if (field == null || !field.getStaticFlag()) {
            do {
                owner = owner.outerLambda();
                if (owner instanceof ModuleExp) {
                    break;
                }
            } while (owner.heapFrame == null);
            if (!owner.getHeapFrameType().getSuperclass().isSubtype(Compilation.typeModuleBody)) {
                owner = comp.getModule();
            }
        } else {
            owner = comp.getModule();
        }
        if (owner.applyMethods == null) {
            owner.applyMethods = new Vector();
        }
        owner.applyMethods.addElement(this);
    }

    public Field compileSetField(Compilation comp) {
        if (this.primMethods == null) {
            allocMethod(outerLambda(), comp);
        }
        Field field = allocFieldFor(comp);
        if (comp.usingCPStyle()) {
            compile(comp, Type.objectType);
        } else {
            compileAsMethod(comp);
            addApplyMethod(comp, field);
        }
        return new ProcInitializer(this, comp, field).field;
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            CodeAttr code = comp.getCode();
            LambdaExp outer = outerLambda();
            Type rtype = Compilation.typeModuleMethod;
            if ((this.flags & 256) != 0 || (comp.immediate && (outer instanceof ModuleExp))) {
                if (this.primMethods == null) {
                    allocMethod(outerLambda(), comp);
                }
                compileAsMethod(comp);
                addApplyMethod(comp, null);
                ProcInitializer.emitLoadModuleMethod(this, comp);
            } else {
                Field field = compileSetField(comp);
                if (field.getStaticFlag()) {
                    code.emitGetStatic(field);
                } else {
                    LambdaExp parent = comp.curLambda;
                    code.emitLoad(parent.heapFrame != null ? parent.heapFrame : parent.closureEnv);
                    code.emitGetField(field);
                }
            }
            target.compileFromStack(comp, rtype);
        }
    }

    public ClassType getHeapFrameType() {
        if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
            return (ClassType) getType();
        }
        return (ClassType) this.heapFrame.getType();
    }

    public LambdaExp getOwningLambda() {
        ScopeExp exp = this.outer;
        while (exp != null) {
            if ((exp instanceof ModuleExp) || (((exp instanceof ClassExp) && getNeedsClosureEnv()) || ((exp instanceof LambdaExp) && ((LambdaExp) exp).heapFrame != null))) {
                return (LambdaExp) exp;
            }
            exp = exp.outer;
        }
        return null;
    }

    void addMethodFor(Compilation comp, ObjectType closureEnvType) {
        ClassType ctype;
        ScopeExp sc = this;
        while (sc != null && !(sc instanceof ClassExp)) {
            sc = sc.outer;
        }
        if (sc != null) {
            ctype = ((ClassExp) sc).instanceType;
        } else {
            ctype = getOwningLambda().getHeapFrameType();
        }
        addMethodFor(ctype, comp, closureEnvType);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void addMethodFor(gnu.bytecode.ClassType r59, gnu.expr.Compilation r60, gnu.bytecode.ObjectType r61) {
        /*
        r58 = this;
        r35 = r58.getName();
        r41 = r58.outerLambda();
        r0 = r58;
        r0 = r0.keywords;
        r55 = r0;
        if (r55 != 0) goto L_0x01ec;
    L_0x0010:
        r24 = 0;
    L_0x0012:
        r0 = r58;
        r0 = r0.defaultArgs;
        r55 = r0;
        if (r55 != 0) goto L_0x01f9;
    L_0x001a:
        r40 = 0;
    L_0x001c:
        r0 = r58;
        r0 = r0.flags;
        r55 = r0;
        r0 = r55;
        r0 = r0 & 512;
        r55 = r0;
        if (r55 == 0) goto L_0x0208;
    L_0x002a:
        r39 = 0;
    L_0x002c:
        r0 = r58;
        r0 = r0.max_args;
        r55 = r0;
        if (r55 < 0) goto L_0x0048;
    L_0x0034:
        r0 = r58;
        r0 = r0.min_args;
        r55 = r0;
        r55 = r55 + r39;
        r0 = r58;
        r0 = r0.max_args;
        r56 = r0;
        r0 = r55;
        r1 = r56;
        if (r0 >= r1) goto L_0x020c;
    L_0x0048:
        r53 = 1;
    L_0x004a:
        r55 = r39 + 1;
        r0 = r55;
        r0 = new gnu.bytecode.Method[r0];
        r30 = r0;
        r0 = r30;
        r1 = r58;
        r1.primBodyMethods = r0;
        r0 = r58;
        r0 = r0.primMethods;
        r55 = r0;
        if (r55 != 0) goto L_0x0066;
    L_0x0060:
        r0 = r30;
        r1 = r58;
        r1.primMethods = r0;
    L_0x0066:
        r19 = 0;
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        if (r55 == 0) goto L_0x0210;
    L_0x0070:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        r56 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r55 = r55.getFlag(r56);
        if (r55 == 0) goto L_0x0210;
    L_0x007e:
        r20 = 0;
    L_0x0080:
        r37 = new java.lang.StringBuffer;
        r55 = 60;
        r0 = r37;
        r1 = r55;
        r0.<init>(r1);
        if (r20 == 0) goto L_0x02be;
    L_0x008d:
        r32 = 8;
    L_0x008f:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        if (r55 == 0) goto L_0x00a5;
    L_0x0097:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        r55 = r55.needsExternalAccess();
        if (r55 == 0) goto L_0x02c2;
    L_0x00a3:
        r32 = r32 | 1;
    L_0x00a5:
        r55 = r41.isModuleBody();
        if (r55 != 0) goto L_0x00b3;
    L_0x00ab:
        r0 = r41;
        r0 = r0 instanceof gnu.expr.ClassExp;
        r55 = r0;
        if (r55 == 0) goto L_0x00b5;
    L_0x00b3:
        if (r35 != 0) goto L_0x00d3;
    L_0x00b5:
        r55 = "lambda";
        r0 = r37;
        r1 = r55;
        r0.append(r1);
        r0 = r60;
        r0 = r0.method_counter;
        r55 = r0;
        r55 = r55 + 1;
        r0 = r55;
        r1 = r60;
        r1.method_counter = r0;
        r0 = r37;
        r1 = r55;
        r0.append(r1);
    L_0x00d3:
        r55 = 67;
        r0 = r19;
        r1 = r55;
        if (r0 != r1) goto L_0x02e7;
    L_0x00db:
        r55 = "<clinit>";
        r0 = r37;
        r1 = r55;
        r0.append(r1);
    L_0x00e4:
        r55 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = r58;
        r1 = r55;
        r55 = r0.getFlag(r1);
        if (r55 == 0) goto L_0x00f9;
    L_0x00f0:
        r55 = "$C";
        r0 = r37;
        r1 = r55;
        r0.append(r1);
    L_0x00f9:
        r55 = r58.getCallConvention();
        r56 = 2;
        r0 = r55;
        r1 = r56;
        if (r0 < r1) goto L_0x02fa;
    L_0x0105:
        if (r19 != 0) goto L_0x02fa;
    L_0x0107:
        r54 = 1;
    L_0x0109:
        if (r19 == 0) goto L_0x0111;
    L_0x010b:
        if (r20 == 0) goto L_0x02fe;
    L_0x010d:
        r55 = r32 & -3;
        r32 = r55 + 1;
    L_0x0111:
        r55 = r59.isInterface();
        if (r55 != 0) goto L_0x011d;
    L_0x0117:
        r55 = r58.isAbstract();
        if (r55 == 0) goto L_0x0123;
    L_0x011d:
        r0 = r32;
        r0 = r0 | 1024;
        r32 = r0;
    L_0x0123:
        r55 = r58.isClassMethod();
        if (r55 == 0) goto L_0x0155;
    L_0x0129:
        r0 = r41;
        r0 = r0 instanceof gnu.expr.ClassExp;
        r55 = r0;
        if (r55 == 0) goto L_0x0155;
    L_0x0131:
        r0 = r58;
        r0 = r0.min_args;
        r55 = r0;
        r0 = r58;
        r0 = r0.max_args;
        r56 = r0;
        r0 = r55;
        r1 = r56;
        if (r0 != r1) goto L_0x0155;
    L_0x0143:
        r18 = 0;
        r17 = 0;
        r42 = r58.firstDecl();
    L_0x014b:
        if (r42 != 0) goto L_0x0304;
    L_0x014d:
        r0 = r58;
        r0 = r0.returnType;
        r55 = r0;
        if (r55 == 0) goto L_0x0320;
    L_0x0155:
        r55 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r0 = r58;
        r1 = r55;
        r55 = r0.getFlag(r1);
        if (r55 != 0) goto L_0x016d;
    L_0x0161:
        r55 = r58.getCallConvention();
        r56 = 2;
        r0 = r55;
        r1 = r56;
        if (r0 < r1) goto L_0x0379;
    L_0x016d:
        r46 = gnu.bytecode.Type.voidType;
    L_0x016f:
        if (r61 == 0) goto L_0x0383;
    L_0x0171:
        r0 = r61;
        r1 = r59;
        if (r0 == r1) goto L_0x0383;
    L_0x0177:
        r14 = 1;
    L_0x0178:
        r8 = 0;
        r55 = r58.getCallConvention();
        r56 = 2;
        r0 = r55;
        r1 = r56;
        if (r0 < r1) goto L_0x0188;
    L_0x0185:
        if (r19 != 0) goto L_0x0188;
    L_0x0187:
        r8 = 1;
    L_0x0188:
        r36 = r37.length();
        r16 = 0;
    L_0x018e:
        r0 = r16;
        r1 = r39;
        if (r0 > r1) goto L_0x0590;
    L_0x0194:
        r0 = r37;
        r1 = r36;
        r0.setLength(r1);
        r0 = r58;
        r0 = r0.min_args;
        r55 = r0;
        r43 = r55 + r16;
        r38 = r43;
        r0 = r16;
        r1 = r39;
        if (r0 != r1) goto L_0x01af;
    L_0x01ab:
        if (r53 == 0) goto L_0x01af;
    L_0x01ad:
        r38 = r38 + 1;
    L_0x01af:
        r55 = r14 + r38;
        r55 = r55 + r8;
        r0 = r55;
        r5 = new gnu.bytecode.Type[r0];
        if (r14 <= 0) goto L_0x01bd;
    L_0x01b9:
        r55 = 0;
        r5[r55] = r61;
    L_0x01bd:
        r52 = r58.firstDecl();
        if (r52 == 0) goto L_0x01cd;
    L_0x01c3:
        r55 = r52.isThisParameter();
        if (r55 == 0) goto L_0x01cd;
    L_0x01c9:
        r52 = r52.nextDecl();
    L_0x01cd:
        r21 = 0;
        r22 = r21;
    L_0x01d1:
        r0 = r22;
        r1 = r43;
        if (r0 >= r1) goto L_0x0386;
    L_0x01d7:
        r21 = r22 + 1;
        r55 = r14 + r22;
        r56 = r52.getType();
        r56 = r56.getImplementationType();
        r5[r55] = r56;
        r52 = r52.nextDecl();
        r22 = r21;
        goto L_0x01d1;
    L_0x01ec:
        r0 = r58;
        r0 = r0.keywords;
        r55 = r0;
        r0 = r55;
        r0 = r0.length;
        r24 = r0;
        goto L_0x0012;
    L_0x01f9:
        r0 = r58;
        r0 = r0.defaultArgs;
        r55 = r0;
        r0 = r55;
        r0 = r0.length;
        r55 = r0;
        r40 = r55 - r24;
        goto L_0x001c;
    L_0x0208:
        r39 = r40;
        goto L_0x002c;
    L_0x020c:
        r53 = 0;
        goto L_0x004a;
    L_0x0210:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        if (r55 == 0) goto L_0x022a;
    L_0x0218:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        r56 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r55 = r55.getFlag(r56);
        if (r55 == 0) goto L_0x022a;
    L_0x0226:
        r20 = 1;
        goto L_0x0080;
    L_0x022a:
        r55 = r58.isClassMethod();
        if (r55 == 0) goto L_0x026b;
    L_0x0230:
        r0 = r41;
        r0 = r0 instanceof gnu.expr.ClassExp;
        r55 = r0;
        if (r55 == 0) goto L_0x0267;
    L_0x0238:
        r6 = r41;
        r6 = (gnu.expr.ClassExp) r6;
        r55 = r6.isMakingClassPair();
        if (r55 == 0) goto L_0x0254;
    L_0x0242:
        if (r61 == 0) goto L_0x0254;
    L_0x0244:
        r20 = 1;
    L_0x0246:
        r0 = r6.initMethod;
        r55 = r0;
        r0 = r58;
        r1 = r55;
        if (r0 != r1) goto L_0x0257;
    L_0x0250:
        r19 = 73;
        goto L_0x0080;
    L_0x0254:
        r20 = 0;
        goto L_0x0246;
    L_0x0257:
        r0 = r6.clinitMethod;
        r55 = r0;
        r0 = r58;
        r1 = r55;
        if (r0 != r1) goto L_0x0080;
    L_0x0261:
        r19 = 67;
        r20 = 1;
        goto L_0x0080;
    L_0x0267:
        r20 = 0;
        goto L_0x0080;
    L_0x026b:
        r0 = r58;
        r0 = r0.thisVariable;
        r55 = r0;
        if (r55 != 0) goto L_0x0279;
    L_0x0273:
        r0 = r61;
        r1 = r59;
        if (r0 != r1) goto L_0x027d;
    L_0x0279:
        r20 = 0;
        goto L_0x0080;
    L_0x027d:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        if (r55 == 0) goto L_0x02ba;
    L_0x0285:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        r0 = r55;
        r0 = r0.context;
        r55 = r0;
        r0 = r55;
        r0 = r0 instanceof gnu.expr.ModuleExp;
        r55 = r0;
        if (r55 == 0) goto L_0x02ba;
    L_0x0299:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        r0 = r55;
        r0 = r0.context;
        r31 = r0;
        r31 = (gnu.expr.ModuleExp) r31;
        r55 = r31.getSuperType();
        if (r55 != 0) goto L_0x02b7;
    L_0x02ad:
        r55 = r31.getInterfaces();
        if (r55 != 0) goto L_0x02b7;
    L_0x02b3:
        r20 = 1;
    L_0x02b5:
        goto L_0x0080;
    L_0x02b7:
        r20 = 0;
        goto L_0x02b5;
    L_0x02ba:
        r20 = 1;
        goto L_0x0080;
    L_0x02be:
        r32 = 0;
        goto L_0x008f;
    L_0x02c2:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        r55 = r55.isPrivate();
        if (r55 == 0) goto L_0x02e5;
    L_0x02ce:
        r11 = 0;
    L_0x02cf:
        r55 = r58.isClassMethod();
        if (r55 == 0) goto L_0x02e1;
    L_0x02d5:
        r0 = r58;
        r0 = r0.nameDecl;
        r55 = r0;
        r0 = r55;
        r11 = r0.getAccessFlags(r11);
    L_0x02e1:
        r32 = r32 | r11;
        goto L_0x00a5;
    L_0x02e5:
        r11 = 1;
        goto L_0x02cf;
    L_0x02e7:
        r55 = r58.getSymbol();
        if (r55 == 0) goto L_0x00e4;
    L_0x02ed:
        r55 = gnu.expr.Compilation.mangleName(r35);
        r0 = r37;
        r1 = r55;
        r0.append(r1);
        goto L_0x00e4;
    L_0x02fa:
        r54 = 0;
        goto L_0x0109;
    L_0x02fe:
        r55 = r32 & 2;
        r32 = r55 + 2;
        goto L_0x0111;
    L_0x0304:
        r55 = r42.isThisParameter();
        if (r55 == 0) goto L_0x0314;
    L_0x030a:
        r17 = r17 + -1;
    L_0x030c:
        r42 = r42.nextDecl();
        r17 = r17 + 1;
        goto L_0x014b;
    L_0x0314:
        r56 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = r42;
        r1 = r56;
        r55 = r0.getFlag(r1);
        if (r55 != 0) goto L_0x030c;
    L_0x0320:
        if (r18 != 0) goto L_0x0339;
    L_0x0322:
        r28 = r37.toString();
        r15 = new gnu.expr.LambdaExp$1;
        r0 = r58;
        r1 = r28;
        r15.<init>(r1);
        r55 = 2;
        r0 = r59;
        r1 = r55;
        r18 = r0.getMethods(r15, r1);
    L_0x0339:
        r50 = 0;
        r0 = r18;
        r0 = r0.length;
        r16 = r0;
    L_0x0340:
        r16 = r16 + -1;
        if (r16 < 0) goto L_0x0362;
    L_0x0344:
        r29 = r18[r16];
        if (r42 != 0) goto L_0x0351;
    L_0x0348:
        r44 = r29.getReturnType();
    L_0x034c:
        if (r50 != 0) goto L_0x0358;
    L_0x034e:
        r50 = r44;
        goto L_0x0340;
    L_0x0351:
        r55 = r29.getParameterTypes();
        r44 = r55[r17];
        goto L_0x034c;
    L_0x0358:
        r0 = r44;
        r1 = r50;
        if (r0 == r1) goto L_0x0340;
    L_0x035e:
        if (r42 != 0) goto L_0x030c;
    L_0x0360:
        goto L_0x0155;
    L_0x0362:
        if (r50 == 0) goto L_0x036d;
    L_0x0364:
        if (r42 == 0) goto L_0x0371;
    L_0x0366:
        r0 = r42;
        r1 = r50;
        r0.setType(r1);
    L_0x036d:
        if (r42 != 0) goto L_0x030c;
    L_0x036f:
        goto L_0x0155;
    L_0x0371:
        r0 = r58;
        r1 = r50;
        r0.setCoercedReturnType(r1);
        goto L_0x036d;
    L_0x0379:
        r55 = r58.getReturnType();
        r46 = r55.getImplementationType();
        goto L_0x016f;
    L_0x0383:
        r14 = 0;
        goto L_0x0178;
    L_0x0386:
        if (r8 == 0) goto L_0x0391;
    L_0x0388:
        r0 = r5.length;
        r55 = r0;
        r55 = r55 + -1;
        r56 = gnu.expr.Compilation.typeCallContext;
        r5[r55] = r56;
    L_0x0391:
        r0 = r43;
        r1 = r38;
        if (r0 >= r1) goto L_0x0402;
    L_0x0397:
        r25 = r52.getType();
        r26 = r25.getName();
        r55 = r59.getClassfileVersion();
        r56 = 3211264; // 0x310000 float:4.49994E-39 double:1.586575E-317;
        r0 = r55;
        r1 = r56;
        if (r0 < r1) goto L_0x0460;
    L_0x03ab:
        r0 = r25;
        r0 = r0 instanceof gnu.bytecode.ArrayType;
        r55 = r0;
        if (r55 == 0) goto L_0x0460;
    L_0x03b3:
        r0 = r32;
        r0 = r0 | 128;
        r32 = r0;
    L_0x03b9:
        if (r24 > 0) goto L_0x03d5;
    L_0x03bb:
        r0 = r39;
        r1 = r40;
        if (r0 < r1) goto L_0x03d5;
    L_0x03c1:
        r55 = "gnu.lists.LList";
        r0 = r55;
        r1 = r26;
        r55 = r0.equals(r1);
        if (r55 != 0) goto L_0x03f1;
    L_0x03cd:
        r0 = r25;
        r0 = r0 instanceof gnu.bytecode.ArrayType;
        r55 = r0;
        if (r55 != 0) goto L_0x03f1;
    L_0x03d5:
        r25 = gnu.expr.Compilation.objArrayType;
        r55 = new gnu.bytecode.Variable;
        r56 = "argsArray";
        r57 = gnu.expr.Compilation.objArrayType;
        r55.<init>(r56, r57);
        r0 = r55;
        r1 = r58;
        r1.argsArray = r0;
        r0 = r58;
        r0 = r0.argsArray;
        r55 = r0;
        r56 = 1;
        r55.setParameter(r56);
    L_0x03f1:
        r0 = r52;
        r1 = r58;
        r1.firstArgsArrayArg = r0;
        r0 = r5.length;
        r56 = r0;
        if (r54 == 0) goto L_0x046b;
    L_0x03fc:
        r55 = 2;
    L_0x03fe:
        r55 = r56 - r55;
        r5[r55] = r25;
    L_0x0402:
        if (r54 == 0) goto L_0x040d;
    L_0x0404:
        r55 = "$X";
        r0 = r37;
        r1 = r55;
        r0.append(r1);
    L_0x040d:
        r0 = r41;
        r0 = r0 instanceof gnu.expr.ClassExp;
        r55 = r0;
        if (r55 != 0) goto L_0x0429;
    L_0x0415:
        r0 = r41;
        r0 = r0 instanceof gnu.expr.ModuleExp;
        r55 = r0;
        if (r55 == 0) goto L_0x046e;
    L_0x041d:
        r55 = r41;
        r55 = (gnu.expr.ModuleExp) r55;
        r56 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r55 = r55.getFlag(r56);
        if (r55 == 0) goto L_0x046e;
    L_0x0429:
        r7 = 1;
    L_0x042a:
        r35 = r37.toString();
        r45 = 0;
        r27 = r37.length();
    L_0x0434:
        r47 = r59;
    L_0x0436:
        if (r47 == 0) goto L_0x0472;
    L_0x0438:
        r0 = r47;
        r1 = r35;
        r55 = r0.getDeclaredMethod(r1, r5);
        if (r55 == 0) goto L_0x0470;
    L_0x0442:
        r0 = r37;
        r1 = r27;
        r0.setLength(r1);
        r55 = 36;
        r0 = r37;
        r1 = r55;
        r0.append(r1);
        r45 = r45 + 1;
        r0 = r37;
        r1 = r45;
        r0.append(r1);
        r35 = r37.toString();
        goto L_0x0434;
    L_0x0460:
        r55 = "$V";
        r0 = r37;
        r1 = r55;
        r0.append(r1);
        goto L_0x03b9;
    L_0x046b:
        r55 = 1;
        goto L_0x03fe;
    L_0x046e:
        r7 = 0;
        goto L_0x042a;
    L_0x0470:
        if (r7 == 0) goto L_0x04f7;
    L_0x0472:
        r0 = r59;
        r1 = r35;
        r2 = r46;
        r3 = r32;
        r29 = r0.addMethod(r1, r5, r2, r3);
        r30[r16] = r29;
        r0 = r58;
        r0 = r0.throwsSpecification;
        r55 = r0;
        if (r55 == 0) goto L_0x058c;
    L_0x0488:
        r0 = r58;
        r0 = r0.throwsSpecification;
        r55 = r0;
        r0 = r55;
        r0 = r0.length;
        r55 = r0;
        if (r55 <= 0) goto L_0x058c;
    L_0x0495:
        r0 = r58;
        r0 = r0.throwsSpecification;
        r55 = r0;
        r0 = r55;
        r0 = r0.length;
        r34 = r0;
        r0 = r34;
        r13 = new gnu.bytecode.ClassType[r0];
        r23 = 0;
    L_0x04a6:
        r0 = r23;
        r1 = r34;
        if (r0 >= r1) goto L_0x0582;
    L_0x04ac:
        r12 = 0;
        r0 = r58;
        r0 = r0.throwsSpecification;
        r55 = r0;
        r48 = r55[r23];
        r33 = 0;
        r0 = r48;
        r0 = r0 instanceof gnu.expr.ReferenceExp;
        r55 = r0;
        if (r55 == 0) goto L_0x0533;
    L_0x04bf:
        r49 = r48;
        r49 = (gnu.expr.ReferenceExp) r49;
        r9 = r49.getBinding();
        if (r9 == 0) goto L_0x051b;
    L_0x04c9:
        r10 = r9.getValue();
        r0 = r10 instanceof gnu.expr.ClassExp;
        r55 = r0;
        if (r55 == 0) goto L_0x04fd;
    L_0x04d3:
        r10 = (gnu.expr.ClassExp) r10;
        r0 = r60;
        r12 = r10.getCompiledClassType(r0);
    L_0x04db:
        if (r12 != 0) goto L_0x04e1;
    L_0x04dd:
        if (r33 != 0) goto L_0x04e1;
    L_0x04df:
        r33 = "invalid throws specification";
    L_0x04e1:
        if (r33 == 0) goto L_0x04f2;
    L_0x04e3:
        r55 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r0 = r60;
        r1 = r55;
        r2 = r33;
        r3 = r48;
        r0.error(r1, r2, r3);
        r12 = gnu.bytecode.Type.javalangThrowableType;
    L_0x04f2:
        r13[r23] = r12;
        r23 = r23 + 1;
        goto L_0x04a6;
    L_0x04f7:
        r47 = r47.getSuperclass();
        goto L_0x0436;
    L_0x04fd:
        r55 = new java.lang.StringBuilder;
        r55.<init>();
        r56 = "throws specification ";
        r55 = r55.append(r56);
        r56 = r9.getName();
        r55 = r55.append(r56);
        r56 = " has non-class lexical binding";
        r55 = r55.append(r56);
        r33 = r55.toString();
        goto L_0x04db;
    L_0x051b:
        r55 = new java.lang.StringBuilder;
        r55.<init>();
        r56 = "unknown class ";
        r55 = r55.append(r56);
        r56 = r49.getName();
        r55 = r55.append(r56);
        r33 = r55.toString();
        goto L_0x04db;
    L_0x0533:
        r0 = r48;
        r0 = r0 instanceof gnu.expr.QuoteExp;
        r55 = r0;
        if (r55 == 0) goto L_0x04db;
    L_0x053b:
        r55 = r48;
        r55 = (gnu.expr.QuoteExp) r55;
        r51 = r55.getValue();
        r0 = r51;
        r0 = r0 instanceof java.lang.Class;
        r55 = r0;
        if (r55 == 0) goto L_0x0551;
    L_0x054b:
        r51 = (java.lang.Class) r51;
        r51 = gnu.bytecode.Type.make(r51);
    L_0x0551:
        r0 = r51;
        r0 = r0 instanceof gnu.bytecode.ClassType;
        r55 = r0;
        if (r55 == 0) goto L_0x055d;
    L_0x0559:
        r12 = r51;
        r12 = (gnu.bytecode.ClassType) r12;
    L_0x055d:
        if (r12 == 0) goto L_0x04db;
    L_0x055f:
        r55 = gnu.bytecode.Type.javalangThrowableType;
        r0 = r55;
        r55 = r12.isSubtype(r0);
        if (r55 != 0) goto L_0x04db;
    L_0x0569:
        r55 = new java.lang.StringBuilder;
        r55.<init>();
        r56 = r12.getName();
        r55 = r55.append(r56);
        r56 = " does not extend Throwable";
        r55 = r55.append(r56);
        r33 = r55.toString();
        goto L_0x04db;
    L_0x0582:
        r4 = new gnu.bytecode.ExceptionsAttr;
        r0 = r29;
        r4.<init>(r0);
        r4.setExceptions(r13);
    L_0x058c:
        r16 = r16 + 1;
        goto L_0x018e;
    L_0x0590:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.LambdaExp.addMethodFor(gnu.bytecode.ClassType, gnu.expr.Compilation, gnu.bytecode.ObjectType):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void allocChildClasses(gnu.expr.Compilation r11) {
        /*
        r10 = this;
        r9 = 1;
        r8 = 0;
        r1 = r10.getMainMethod();
        if (r1 == 0) goto L_0x0015;
    L_0x0008:
        r5 = r1.getStaticFlag();
        if (r5 != 0) goto L_0x0015;
    L_0x000e:
        r5 = r1.getDeclaringClass();
        r10.declareThis(r5);
    L_0x0015:
        r0 = r10.firstDecl();
    L_0x0019:
        r5 = r10.firstArgsArrayArg;
        if (r0 != r5) goto L_0x002a;
    L_0x001d:
        r5 = r10.argsArray;
        if (r5 == 0) goto L_0x002a;
    L_0x0021:
        r5 = r10.getVarScope();
        r6 = r10.argsArray;
        r5.addVariable(r6);
    L_0x002a:
        r5 = r10.getInlineOnly();
        if (r5 != 0) goto L_0x004c;
    L_0x0030:
        r5 = r10.getCallConvention();
        r6 = 2;
        if (r5 < r6) goto L_0x004c;
    L_0x0037:
        r5 = r10.firstArgsArrayArg;
        if (r5 != 0) goto L_0x0058;
    L_0x003b:
        if (r0 != 0) goto L_0x004c;
    L_0x003d:
        r5 = r10.getVarScope();
        r6 = gnu.expr.Compilation.typeCallContext;
        r7 = "$ctx";
        r2 = r5.addVariable(r8, r6, r7);
        r2.setParameter(r9);
    L_0x004c:
        if (r0 != 0) goto L_0x006a;
    L_0x004e:
        r10.declareClosureEnv();
        r10.allocFrame(r11);
        r10.allocChildMethods(r11);
        return;
    L_0x0058:
        r5 = r10.argsArray;
        if (r5 == 0) goto L_0x0061;
    L_0x005c:
        r5 = r10.firstArgsArrayArg;
        if (r0 != r5) goto L_0x004c;
    L_0x0060:
        goto L_0x003d;
    L_0x0061:
        r5 = r10.firstArgsArrayArg;
        r5 = r5.nextDecl();
        if (r0 != r5) goto L_0x004c;
    L_0x0069:
        goto L_0x003d;
    L_0x006a:
        r2 = r0.var;
        if (r2 != 0) goto L_0x007a;
    L_0x006e:
        r5 = r10.getInlineOnly();
        if (r5 == 0) goto L_0x007f;
    L_0x0074:
        r5 = r0.ignorable();
        if (r5 == 0) goto L_0x007f;
    L_0x007a:
        r0 = r0.nextDecl();
        goto L_0x0019;
    L_0x007f:
        r5 = r0.isSimple();
        if (r5 == 0) goto L_0x0090;
    L_0x0085:
        r5 = r0.isIndirectBinding();
        if (r5 != 0) goto L_0x0090;
    L_0x008b:
        r2 = r0.allocateVariable(r8);
        goto L_0x007a;
    L_0x0090:
        r5 = r0.getName();
        r5 = gnu.expr.Compilation.mangleName(r5);
        r3 = r5.intern();
        r5 = r0.getType();
        r4 = r5.getImplementationType();
        r5 = r10.getVarScope();
        r2 = r5.addVariable(r8, r4, r3);
        r0.var = r2;
        r2.setParameter(r9);
        goto L_0x007a;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.LambdaExp.allocChildClasses(gnu.expr.Compilation):void");
    }

    void allocMethod(LambdaExp outer, Compilation comp) {
        ObjectType closureEnvType;
        if (!getNeedsClosureEnv()) {
            closureEnvType = null;
        } else if ((outer instanceof ClassExp) || (outer instanceof ModuleExp)) {
            closureEnvType = outer.getCompiledClassType(comp);
        } else {
            LambdaExp owner = outer;
            while (owner.heapFrame == null) {
                owner = owner.outerLambda();
            }
            ClassType closureEnvType2 = (ClassType) owner.heapFrame.getType();
        }
        addMethodFor(comp, closureEnvType);
    }

    void allocChildMethods(Compilation comp) {
        LambdaExp child = this.firstChild;
        while (child != null) {
            if (!(child.isClassGenerated() || child.getInlineOnly() || child.nameDecl == null)) {
                child.allocMethod(this, comp);
            }
            if (child instanceof ClassExp) {
                ClassExp cl = (ClassExp) child;
                if (cl.getNeedsClosureEnv()) {
                    ClassType parentFrameType;
                    if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
                        parentFrameType = (ClassType) getType();
                    } else {
                        parentFrameType = (ClassType) (this.heapFrame != null ? this.heapFrame : this.closureEnv).getType();
                    }
                    Field outerLink = cl.instanceType.setOuterLink(parentFrameType);
                    cl.staticLinkField = outerLink;
                    cl.closureEnvField = outerLink;
                }
            }
            child = child.nextSibling;
        }
    }

    public void allocFrame(Compilation comp) {
        if (this.heapFrame != null) {
            ClassType frameType;
            if ((this instanceof ModuleExp) || (this instanceof ClassExp)) {
                frameType = getCompiledClassType(comp);
            } else {
                frameType = new ClassType(comp.generateClassName("frame"));
                frameType.setSuper(comp.getModuleType());
                comp.addClass(frameType);
            }
            this.heapFrame.setType(frameType);
        }
    }

    void allocParameters(Compilation comp) {
        CodeAttr code = comp.getCode();
        code.locals.enterScope(getVarScope());
        int line = getLineNumber();
        if (line > 0) {
            code.putLineNumber(getFileName(), line);
        }
        if (this.heapFrame != null) {
            this.heapFrame.allocateLocal(code);
        }
    }

    void enterFunction(Compilation comp) {
        CodeAttr code = comp.getCode();
        getVarScope().noteStartFunction(code);
        if (!(this.closureEnv == null || this.closureEnv.isParameter() || comp.usingCPStyle())) {
            if (!getInlineOnly()) {
                code.emitPushThis();
                Field field = this.closureEnvField;
                if (field == null) {
                    field = outerLambda().closureEnvField;
                }
                code.emitGetField(field);
                code.emitStore(this.closureEnv);
            } else if (!inlinedIn(outerLambda())) {
                outerLambda().loadHeapFrame(comp);
                code.emitStore(this.closureEnv);
            }
        }
        if (!comp.usingCPStyle()) {
            ClassType frameType = this.heapFrame == null ? currentModule().getCompiledClassType(comp) : (ClassType) this.heapFrame.getType();
            for (Declaration decl = this.capturedVars; decl != null; decl = decl.nextCapturedVar) {
                if (decl.field == null) {
                    decl.makeField(frameType, comp, null);
                }
            }
        }
        if (!(this.heapFrame == null || comp.usingCPStyle())) {
            Type frameType2 = (ClassType) this.heapFrame.getType();
            if (!(this.closureEnv == null || (this instanceof ModuleExp))) {
                this.staticLinkField = frameType2.addField("staticLink", this.closureEnv.getType());
            }
            if (!((this instanceof ModuleExp) || (this instanceof ClassExp))) {
                frameType2.setEnclosingMember(comp.method);
                code.emitNew(frameType2);
                code.emitDup(frameType2);
                code.emitInvokeSpecial(Compilation.getConstructor(frameType2, this));
                if (this.staticLinkField != null) {
                    code.emitDup(frameType2);
                    code.emitLoad(this.closureEnv);
                    code.emitPutField(this.staticLinkField);
                }
                code.emitStore(this.heapFrame);
            }
        }
        Variable argsArray = this.argsArray;
        if (this.min_args == this.max_args && this.primMethods == null && getCallConvention() < 2) {
            argsArray = null;
        }
        int i = 0;
        int opt_args = this.defaultArgs == null ? 0 : this.defaultArgs.length - (this.keywords == null ? 0 : this.keywords.length);
        if (!(this instanceof ModuleExp)) {
            int key_i;
            int opt_i;
            int plainArgs = -1;
            int defaultStart = 0;
            Method mainMethod = getMainMethod();
            Variable callContextSave = comp.callContextVar;
            Declaration param = firstDecl();
            int key_i2 = 0;
            int opt_i2 = 0;
            while (param != null) {
                Variable variable;
                if (getCallConvention() < 2) {
                    variable = null;
                } else {
                    variable = getVarScope().lookup("$ctx");
                }
                comp.callContextVar = variable;
                if (param == this.firstArgsArrayArg && argsArray != null) {
                    if (this.primMethods != null) {
                        plainArgs = i;
                        defaultStart = plainArgs - this.min_args;
                    } else {
                        plainArgs = 0;
                        defaultStart = 0;
                    }
                }
                if (plainArgs >= 0 || !param.isSimple() || param.isIndirectBinding()) {
                    Type stackType;
                    Type paramType = param.getType();
                    if (plainArgs >= 0) {
                        stackType = Type.objectType;
                    } else {
                        stackType = paramType;
                    }
                    if (!param.isSimple()) {
                        param.loadOwningObject(null, comp);
                    }
                    if (plainArgs < 0) {
                        code.emitLoad(param.getVariable());
                        key_i = key_i2;
                        opt_i = opt_i2;
                    } else if (i < this.min_args) {
                        code.emitLoad(argsArray);
                        code.emitPushInt(i);
                        code.emitArrayLoad(Type.objectType);
                        key_i = key_i2;
                        opt_i = opt_i2;
                    } else if (i < this.min_args + opt_args) {
                        code.emitPushInt(i - plainArgs);
                        code.emitLoad(argsArray);
                        code.emitArrayLength();
                        code.emitIfIntLt();
                        code.emitLoad(argsArray);
                        code.emitPushInt(i - plainArgs);
                        code.emitArrayLoad();
                        code.emitElse();
                        opt_i = opt_i2 + 1;
                        this.defaultArgs[defaultStart + opt_i2].compile(comp, paramType);
                        code.emitFi();
                        key_i = key_i2;
                    } else if (this.max_args >= 0 || i != this.min_args + opt_args) {
                        code.emitLoad(argsArray);
                        code.emitPushInt((this.min_args + opt_args) - plainArgs);
                        key_i = key_i2 + 1;
                        comp.compileConstant(this.keywords[key_i2]);
                        opt_i = opt_i2 + 1;
                        Expression defaultArg = this.defaultArgs[defaultStart + opt_i2];
                        if (defaultArg instanceof QuoteExp) {
                            if (searchForKeywordMethod4 == null) {
                                searchForKeywordMethod4 = Compilation.scmKeywordType.addMethod("searchForKeyword", new Type[]{Compilation.objArrayType, Type.intType, Type.objectType, Type.objectType}, Type.objectType, 9);
                            }
                            defaultArg.compile(comp, paramType);
                            code.emitInvokeStatic(searchForKeywordMethod4);
                        } else {
                            if (searchForKeywordMethod3 == null) {
                                searchForKeywordMethod3 = Compilation.scmKeywordType.addMethod("searchForKeyword", new Type[]{Compilation.objArrayType, Type.intType, Type.objectType}, Type.objectType, 9);
                            }
                            code.emitInvokeStatic(searchForKeywordMethod3);
                            code.emitDup(1);
                            comp.compileConstant(Special.dfault);
                            code.emitIfEq();
                            code.emitPop(1);
                            defaultArg.compile(comp, paramType);
                            code.emitFi();
                        }
                    } else {
                        code.emitLoad(argsArray);
                        code.emitPushInt(i - plainArgs);
                        code.emitInvokeStatic(Compilation.makeListMethod);
                        stackType = Compilation.scmListType;
                        key_i = key_i2;
                        opt_i = opt_i2;
                    }
                    if (paramType != stackType) {
                        CheckedTarget.emitCheckedCoerce(comp, this, i + 1, paramType);
                    }
                    if (param.isIndirectBinding()) {
                        param.pushIndirectBinding(comp);
                    }
                    if (param.isSimple()) {
                        Variable var = param.getVariable();
                        if (param.isIndirectBinding()) {
                            var.setType(Compilation.typeLocation);
                        }
                        code.emitStore(var);
                    } else {
                        code.emitPutField(param.field);
                    }
                } else {
                    key_i = key_i2;
                    opt_i = opt_i2;
                }
                i++;
                param = param.nextDecl();
                key_i2 = key_i;
                opt_i2 = opt_i;
            }
            comp.callContextVar = callContextSave;
            key_i = key_i2;
            opt_i = opt_i2;
        }
    }

    void compileAsMethod(Compilation comp) {
        if ((this.flags & 128) == 0 && !isAbstract()) {
            this.flags |= 128;
            if (this.primMethods != null) {
                int k;
                Declaration decl;
                int k2;
                Method save_method = comp.method;
                LambdaExp save_lambda = comp.curLambda;
                comp.curLambda = this;
                boolean isStatic = this.primMethods[0].getStaticFlag();
                int numStubs = this.primMethods.length - 1;
                Type restArgType = restArgType();
                long[] saveDeclFlags = null;
                if (numStubs > 0) {
                    saveDeclFlags = new long[(this.min_args + numStubs)];
                    k = 0;
                    decl = firstDecl();
                    while (k < this.min_args + numStubs) {
                        k2 = k + 1;
                        saveDeclFlags[k] = decl.flags;
                        decl = decl.nextDecl();
                        k = k2;
                    }
                }
                boolean ctxArg = getCallConvention() >= 2;
                for (int i = 0; i <= numStubs; i++) {
                    comp.method = this.primMethods[i];
                    if (i < numStubs) {
                        CodeAttr code = comp.method.startCode();
                        int toCall = i + 1;
                        while (toCall < numStubs && (this.defaultArgs[toCall] instanceof QuoteExp)) {
                            toCall++;
                        }
                        boolean varArgs = toCall == numStubs && restArgType != null;
                        Variable callContextSave = comp.callContextVar;
                        Variable var = code.getArg(0);
                        if (!isStatic) {
                            code.emitPushThis();
                            if (getNeedsClosureEnv()) {
                                this.closureEnv = var;
                            }
                            var = code.getArg(1);
                        }
                        decl = firstDecl();
                        int j = 0;
                        while (j < this.min_args + i) {
                            decl.flags |= 64;
                            decl.var = var;
                            code.emitLoad(var);
                            var = var.nextVar();
                            j++;
                            decl = decl.nextDecl();
                        }
                        comp.callContextVar = ctxArg ? var : null;
                        j = i;
                        while (j < toCall) {
                            this.defaultArgs[j].compile(comp, StackTarget.getInstance(decl.getType()));
                            j++;
                            decl = decl.nextDecl();
                        }
                        if (varArgs) {
                            Expression arg;
                            String lastTypeName = restArgType.getName();
                            if ("gnu.lists.LList".equals(lastTypeName)) {
                                arg = new QuoteExp(LList.Empty);
                            } else if ("java.lang.Object[]".equals(lastTypeName)) {
                                arg = new QuoteExp(Values.noArgs);
                            } else {
                                throw new Error("unimplemented #!rest type " + lastTypeName);
                            }
                            arg.compile(comp, restArgType);
                        }
                        if (ctxArg) {
                            code.emitLoad(var);
                        }
                        if (isStatic) {
                            code.emitInvokeStatic(this.primMethods[toCall]);
                        } else {
                            code.emitInvokeVirtual(this.primMethods[toCall]);
                        }
                        code.emitReturn();
                        this.closureEnv = null;
                        comp.callContextVar = callContextSave;
                    } else {
                        if (saveDeclFlags != null) {
                            k = 0;
                            decl = firstDecl();
                            while (k < this.min_args + numStubs) {
                                k2 = k + 1;
                                decl.flags = saveDeclFlags[k];
                                decl.var = null;
                                decl = decl.nextDecl();
                                k = k2;
                            }
                        }
                        comp.method.initCode();
                        allocChildClasses(comp);
                        allocParameters(comp);
                        enterFunction(comp);
                        compileBody(comp);
                        compileEnd(comp);
                        generateApplyMethods(comp);
                    }
                }
                comp.method = save_method;
                comp.curLambda = save_lambda;
            }
        }
    }

    public void compileBody(Compilation comp) {
        Target target;
        Expression expression;
        Variable callContextSave = comp.callContextVar;
        comp.callContextVar = null;
        if (getCallConvention() >= 2) {
            Variable var = getVarScope().lookup("$ctx");
            if (var != null && var.getType() == Compilation.typeCallContext) {
                comp.callContextVar = var;
            }
            target = ConsumerTarget.makeContextTarget(comp);
        } else {
            target = Target.pushValue(getReturnType());
        }
        Expression expression2 = this.body;
        if (this.body.getLineNumber() > 0) {
            expression = this.body;
        }
        expression2.compileWithPosition(comp, target, expression);
        comp.callContextVar = callContextSave;
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        LambdaExp saveLambda;
        Compilation comp = visitor.getCompilation();
        if (comp == null) {
            saveLambda = null;
        } else {
            saveLambda = comp.curLambda;
            comp.curLambda = this;
        }
        try {
            R visitLambdaExp = visitor.visitLambdaExp(this, d);
            return visitLambdaExp;
        } finally {
            if (comp != null) {
                comp.curLambda = saveLambda;
            }
        }
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        visitChildrenOnly(visitor, d);
        visitProperties(visitor, d);
    }

    protected final <R, D> void visitChildrenOnly(ExpVisitor<R, D> visitor, D d) {
        LambdaExp save = visitor.currentLambda;
        visitor.currentLambda = this;
        try {
            this.throwsSpecification = visitor.visitExps(this.throwsSpecification, d);
            visitor.visitDefaultArgs(this, d);
            if (visitor.exitValue == null && this.body != null) {
                this.body = visitor.update(this.body, visitor.visit(this.body, d));
            }
            visitor.currentLambda = save;
        } catch (Throwable th) {
            visitor.currentLambda = save;
        }
    }

    protected final <R, D> void visitProperties(ExpVisitor<R, D> visitor, D d) {
        if (this.properties != null) {
            int len = this.properties.length;
            for (int i = 1; i < len; i += 2) {
                Object val = this.properties[i];
                if (val instanceof Expression) {
                    this.properties[i] = visitor.visitAndUpdate((Expression) val, d);
                }
            }
        }
    }

    protected boolean mustCompile() {
        if (this.keywords != null && this.keywords.length > 0) {
            return true;
        }
        if (this.defaultArgs != null) {
            int i = this.defaultArgs.length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                Expression def = this.defaultArgs[i];
                if (def != null && !(def instanceof QuoteExp)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void apply(CallContext ctx) throws Throwable {
        setIndexes();
        ctx.writeValue(new Closure(this, ctx));
    }

    Object evalDefaultArg(int index, CallContext ctx) {
        try {
            return this.defaultArgs[index].eval(ctx);
        } catch (Throwable ex) {
            WrappedException wrappedException = new WrappedException("error evaluating default argument", ex);
        }
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        Expression[] args = exp.getArgs();
        if ((this.flags & 4096) != 0) {
            Expression inlined = InlineCalls.inlineCall(this, args, true);
            if (inlined != null) {
                return visitor.visit(inlined, required);
            }
        }
        exp.visitArgs(visitor);
        int args_length = exp.args.length;
        String msg = WrongArguments.checkArgCount(getName(), this.min_args, this.max_args, args_length);
        if (msg != null) {
            return visitor.noteError(msg);
        }
        int conv = getCallConvention();
        if (!visitor.getCompilation().inlineOk((Expression) this) || !isClassMethod()) {
            return exp;
        }
        if (conv > 2 && conv != 3) {
            return exp;
        }
        Method method = getMethod(args_length);
        if (method == null) {
            return exp;
        }
        Procedure mproc;
        Expression[] margs;
        boolean isStatic = this.nameDecl.isStatic();
        if (isStatic || !(this.outer instanceof ClassExp) || this.outer.isMakingClassPair()) {
            mproc = new PrimProcedure(method, this);
        } else {
            mproc = new PrimProcedure(method, this);
        }
        if (isStatic) {
            margs = exp.args;
        } else {
            LambdaExp curLambda = visitor.getCurrentLambda();
            while (curLambda != null) {
                if (curLambda.outer == this.outer) {
                    Declaration d = curLambda.firstDecl();
                    if (d == null || !d.isThisParameter()) {
                        return visitor.noteError("calling non-static method " + getName() + " from static method " + curLambda.getName());
                    }
                    int nargs = exp.getArgCount();
                    margs = new Expression[(nargs + 1)];
                    System.arraycopy(exp.getArgs(), 0, margs, 1, nargs);
                    margs[0] = new ThisExp(d);
                } else {
                    curLambda = curLambda.outerLambda();
                }
            }
            return visitor.noteError("internal error: missing " + this);
        }
        return new ApplyExp(mproc, margs).setLine((Expression) exp);
    }

    public void print(OutPort out) {
        int opt_i;
        out.startLogicalBlock("(Lambda/", ")", 2);
        Object sym = getSymbol();
        if (sym != null) {
            out.print(sym);
            out.print('/');
        }
        out.print(this.id);
        out.print('/');
        out.print("fl:");
        out.print(Integer.toHexString(this.flags));
        out.writeSpaceFill();
        printLineColumn(out);
        out.startLogicalBlock("(", false, ")");
        Special prevMode = null;
        int i = 0;
        int opt_args = this.defaultArgs == null ? 0 : this.defaultArgs.length - (this.keywords == null ? 0 : this.keywords.length);
        Declaration decl = firstDecl();
        if (decl == null || !decl.isThisParameter()) {
            opt_i = 0;
        } else {
            i = -1;
            opt_i = 0;
        }
        while (decl != null) {
            Special mode;
            int opt_i2;
            if (i < this.min_args) {
                mode = null;
            } else if (i < this.min_args + opt_args) {
                mode = Special.optional;
            } else if (this.max_args >= 0 || i != this.min_args + opt_args) {
                mode = Special.key;
            } else {
                mode = Special.rest;
            }
            if (decl != firstDecl()) {
                out.writeSpaceFill();
            }
            if (mode != prevMode) {
                out.print((Object) mode);
                out.writeSpaceFill();
            }
            Expression defaultArg = null;
            if (mode == Special.optional || mode == Special.key) {
                opt_i2 = opt_i + 1;
                defaultArg = this.defaultArgs[opt_i];
            } else {
                opt_i2 = opt_i;
            }
            if (defaultArg != null) {
                out.print('(');
            }
            decl.printInfo(out);
            if (!(defaultArg == null || defaultArg == QuoteExp.falseExp)) {
                out.print(' ');
                defaultArg.print(out);
                out.print(')');
            }
            i++;
            prevMode = mode;
            decl = decl.nextDecl();
            opt_i = opt_i2;
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

    protected final String getExpClassName() {
        String cname = getClass().getName();
        int index = cname.lastIndexOf(46);
        if (index >= 0) {
            return cname.substring(index + 1);
        }
        return cname;
    }

    public boolean side_effects() {
        return false;
    }

    public String toString() {
        String str = getExpClassName() + ':' + getSymbol() + '/' + this.id + '/';
        int l = getLineNumber();
        if (l <= 0 && this.body != null) {
            l = this.body.getLineNumber();
        }
        if (l > 0) {
            return str + "l:" + l;
        }
        return str;
    }

    public Object getProperty(Object key, Object defaultValue) {
        if (this.properties == null) {
            return defaultValue;
        }
        int i = this.properties.length;
        do {
            i -= 2;
            if (i < 0) {
                return defaultValue;
            }
        } while (this.properties[i] != key);
        return this.properties[i + 1];
    }

    public synchronized void setProperty(Object key, Object value) {
        this.properties = PropertySet.setProperty(this.properties, key, value);
    }

    public final Type getReturnType() {
        if (this.returnType == null) {
            this.returnType = Type.objectType;
            if (!(this.body == null || isAbstract())) {
                this.returnType = this.body.getType();
            }
        }
        return this.returnType;
    }

    public final void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public final void setCoercedReturnType(Type returnType) {
        this.returnType = returnType;
        if (returnType != null && returnType != Type.objectType && returnType != Type.voidType && this.body != QuoteExp.abstractExp) {
            Expression value = this.body;
            this.body = Compilation.makeCoercion(value, returnType);
            this.body.setLine(value);
        }
    }

    public final void setCoercedReturnValue(Expression type, Language language) {
        if (!isAbstract()) {
            Expression value = this.body;
            this.body = Compilation.makeCoercion(value, type);
            this.body.setLine(value);
        }
        Type rtype = language.getTypeFor(type);
        if (rtype != null) {
            setReturnType(rtype);
        }
    }
}

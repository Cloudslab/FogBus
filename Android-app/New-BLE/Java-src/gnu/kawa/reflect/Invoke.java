package gnu.kawa.reflect;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Keyword;
import gnu.expr.Language;
import gnu.expr.PairClassType;
import gnu.expr.PrimProcedure;
import gnu.expr.TypeValue;
import gnu.kawa.lispexpr.ClassNamespace;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import java.lang.reflect.Array;

public class Invoke extends ProcedureN {
    public static final Invoke invoke = new Invoke("invoke", '*');
    public static final Invoke invokeSpecial = new Invoke("invoke-special", 'P');
    public static final Invoke invokeStatic = new Invoke("invoke-static", 'S');
    public static final Invoke make = new Invoke("make", 'N');
    char kind;
    Language language;

    public Invoke(String name, char kind) {
        this(name, kind, Language.getDefaultLanguage());
    }

    public Invoke(String name, char kind, Language language) {
        super(name);
        this.kind = kind;
        this.language = language;
        setProperty(Procedure.validateApplyKey, "gnu.kawa.reflect.CompileInvoke:validateApplyInvoke");
    }

    public static Object invoke$V(Object[] args) throws Throwable {
        return invoke.applyN(args);
    }

    public static Object invokeStatic$V(Object[] args) throws Throwable {
        return invokeStatic.applyN(args);
    }

    public static Object make$V(Object[] args) throws Throwable {
        return make.applyN(args);
    }

    private static ObjectType typeFrom(Object arg, Invoke thisProc) {
        if (arg instanceof Class) {
            arg = Type.make((Class) arg);
        }
        if (arg instanceof ObjectType) {
            return (ObjectType) arg;
        }
        if ((arg instanceof String) || (arg instanceof FString)) {
            return ClassType.make(arg.toString());
        }
        if (arg instanceof Symbol) {
            return ClassType.make(((Symbol) arg).getName());
        }
        if (arg instanceof ClassNamespace) {
            return ((ClassNamespace) arg).getClassType();
        }
        throw new WrongType((Procedure) thisProc, 0, arg, "class-specifier");
    }

    public void apply(CallContext ctx) throws Throwable {
        Object[] args = ctx.getArgs();
        if (this.kind == 'S' || this.kind == 'V' || this.kind == 's' || this.kind == '*') {
            int nargs = args.length;
            Procedure.checkArgCount(this, nargs);
            Object arg0 = args[0];
            Type typeFrom = (this.kind == 'S' || this.kind == 's') ? typeFrom(arg0, this) : Type.make(arg0.getClass());
            Procedure proc = lookupMethods((ObjectType) typeFrom, args[1]);
            Object[] margs = new Object[(nargs - (this.kind == 'S' ? 2 : 1))];
            int i = 0;
            if (this.kind == 'V' || this.kind == '*') {
                int i2 = 0 + 1;
                margs[0] = args[0];
                i = i2;
            }
            System.arraycopy(args, 2, margs, i, nargs - 2);
            proc.checkN(margs, ctx);
            return;
        }
        ctx.writeValue(applyN(args));
    }

    public Object applyN(Object[] args) throws Throwable {
        if (this.kind == 'P') {
            throw new RuntimeException(getName() + ": invoke-special not allowed at run time");
        }
        ObjectType dtype;
        Object obj;
        int nargs = args.length;
        Procedure.checkArgCount(this, nargs);
        Object arg0 = args[0];
        if (this.kind == 'V' || this.kind == '*') {
            dtype = (ObjectType) Type.make(arg0.getClass());
        } else {
            dtype = typeFrom(arg0, this);
        }
        int i;
        if (this.kind == 'N') {
            obj = null;
            if (dtype instanceof TypeValue) {
                Procedure constructor = ((TypeValue) dtype).getConstructor();
                if (constructor != null) {
                    nargs--;
                    Object xargs = new Object[nargs];
                    System.arraycopy(args, 1, xargs, 0, nargs);
                    return constructor.applyN(xargs);
                }
            }
            if (dtype instanceof PairClassType) {
                dtype = ((PairClassType) dtype).instanceType;
            }
            if (dtype instanceof ArrayType) {
                int length;
                boolean lengthSpecified;
                Object arr;
                int index;
                Object arg;
                String kname;
                Type elementType = ((ArrayType) dtype).getComponentType();
                int len = args.length - 1;
                if (len >= 2 && (args[1] instanceof Keyword)) {
                    String name = ((Keyword) args[1]).getName();
                    if ("length".equals(name) || "size".equals(name)) {
                        length = ((Number) args[2]).intValue();
                        i = 3;
                        lengthSpecified = true;
                        arr = Array.newInstance(elementType.getReflectClass(), length);
                        index = 0;
                        while (i <= len) {
                            arg = args[i];
                            if (lengthSpecified && (arg instanceof Keyword) && i < len) {
                                kname = ((Keyword) arg).getName();
                                try {
                                    index = Integer.parseInt(kname);
                                    i++;
                                    arg = args[i];
                                } catch (Throwable th) {
                                    RuntimeException runtimeException = new RuntimeException("non-integer keyword '" + kname + "' in array constructor");
                                }
                            }
                            Array.set(arr, index, elementType.coerceFromObject(arg));
                            index++;
                            i++;
                        }
                        return arr;
                    }
                }
                length = len;
                i = 1;
                lengthSpecified = false;
                arr = Array.newInstance(elementType.getReflectClass(), length);
                index = 0;
                while (i <= len) {
                    arg = args[i];
                    kname = ((Keyword) arg).getName();
                    index = Integer.parseInt(kname);
                    i++;
                    arg = args[i];
                    Array.set(arr, index, elementType.coerceFromObject(arg));
                    index++;
                    i++;
                }
                return arr;
            }
        }
        obj = args[1];
        Procedure proc = lookupMethods(dtype, obj);
        Object margs;
        if (this.kind != 'N') {
            int i2 = (this.kind == 'S' || this.kind == 's') ? 2 : 1;
            margs = new Object[(nargs - i2)];
            i = 0;
            if (this.kind == 'V' || this.kind == '*') {
                int i3 = 0 + 1;
                margs[0] = args[0];
                i = i3;
            }
            System.arraycopy(args, 2, margs, i, nargs - 2);
            return proc.applyN(margs);
        }
        Object result;
        CallContext vars = CallContext.getInstance();
        int keywordStart = 0;
        while (keywordStart < args.length && !(args[keywordStart] instanceof Keyword)) {
            keywordStart++;
        }
        int err = -1;
        if (keywordStart == args.length) {
            err = proc.matchN(args, vars);
            if (err == 0) {
                return vars.runUntilValue();
            }
            MethodProc vproc = ClassMethods.apply((ClassType) dtype, "valueOf", '\u0000', this.language);
            if (vproc != null) {
                margs = new Object[(nargs - 1)];
                System.arraycopy(args, 1, margs, 0, nargs - 1);
                err = vproc.matchN(margs, vars);
                if (err == 0) {
                    return vars.runUntilValue();
                }
            }
            result = proc.apply1(args[0]);
        } else {
            Object[] cargs = new Object[keywordStart];
            System.arraycopy(args, 0, cargs, 0, keywordStart);
            result = proc.applyN(cargs);
        }
        i = keywordStart;
        while (i + 1 < args.length) {
            Keyword arg2 = args[i];
            if (!(arg2 instanceof Keyword)) {
                break;
            }
            SlotSet.apply(false, result, arg2.getName(), args[i + 1]);
            i += 2;
        }
        if (keywordStart == args.length) {
            i = 1;
        }
        if (i != args.length) {
            MethodProc aproc = ClassMethods.apply((ClassType) dtype, "add", '\u0000', this.language);
            if (aproc == null) {
                throw MethodProc.matchFailAsException(err, proc, args);
            }
            while (i < args.length) {
                i3 = i + 1;
                aproc.apply2(result, args[i]);
                i = i3;
            }
        }
        return result;
    }

    public int numArgs() {
        return (this.kind == 'N' ? 1 : 2) | -4096;
    }

    protected MethodProc lookupMethods(ObjectType dtype, Object name) {
        String mname;
        char c = 'P';
        if (this.kind == 'N') {
            mname = "<init>";
        } else {
            if ((name instanceof String) || (name instanceof FString)) {
                mname = name.toString();
            } else if (name instanceof Symbol) {
                mname = ((Symbol) name).getName();
            } else {
                throw new WrongType((Procedure) this, 1, null);
            }
            mname = Compilation.mangleName(mname);
        }
        if (this.kind != 'P') {
            c = (this.kind == '*' || this.kind == 'V') ? 'V' : '\u0000';
        }
        MethodProc proc = ClassMethods.apply(dtype, mname, c, this.language);
        if (proc != null) {
            return proc;
        }
        throw new RuntimeException(getName() + ": no method named `" + mname + "' in class " + dtype.getName());
    }

    public static synchronized ApplyExp makeInvokeStatic(ClassType type, String name, Expression[] args) {
        ApplyExp applyExp;
        synchronized (Invoke.class) {
            Procedure method = getStaticMethod(type, name, args);
            if (method == null) {
                throw new RuntimeException("missing or ambiguous method `" + name + "' in " + type.getName());
            }
            applyExp = new ApplyExp(method, args);
        }
        return applyExp;
    }

    public static synchronized PrimProcedure getStaticMethod(ClassType type, String name, Expression[] args) {
        PrimProcedure staticMethod;
        synchronized (Invoke.class) {
            staticMethod = CompileInvoke.getStaticMethod(type, name, args);
        }
        return staticMethod;
    }
}

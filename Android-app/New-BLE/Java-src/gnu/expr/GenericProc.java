package gnu.expr;

import gnu.bytecode.Type;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.WrongType;

public class GenericProc extends MethodProc {
    int count;
    int maxArgs;
    protected MethodProc[] methods;
    int minArgs;

    public GenericProc(String name) {
        setName(name);
    }

    public int getMethodCount() {
        return this.count;
    }

    public MethodProc getMethod(int i) {
        return i >= this.count ? null : this.methods[i];
    }

    public int numArgs() {
        return this.minArgs | (this.maxArgs << 12);
    }

    protected synchronized void add(MethodProc[] procs) {
        if (this.methods == null) {
            this.methods = new MethodProc[n];
        }
        for (MethodProc add : procs) {
            add(add);
        }
    }

    public synchronized void addAtEnd(MethodProc method) {
        int oldCount = this.count;
        if (this.methods == null) {
            this.methods = new MethodProc[8];
        } else if (oldCount >= this.methods.length) {
            MethodProc[] copy = new MethodProc[(this.methods.length * 2)];
            System.arraycopy(this.methods, 0, copy, 0, oldCount);
            this.methods = copy;
        }
        this.methods[oldCount] = method;
        int n = method.minArgs();
        if (n < this.minArgs || this.count == 0) {
            this.minArgs = n;
        }
        n = method.maxArgs();
        if (n == -1 || n > this.maxArgs) {
            this.maxArgs = n;
        }
        this.count = oldCount + 1;
    }

    public synchronized void add(MethodProc method) {
        int oldCount = this.count;
        addAtEnd(method);
        for (int i = 0; i < oldCount; i++) {
            if (MethodProc.mostSpecific(method, this.methods[i]) == method) {
                System.arraycopy(this.methods, i, this.methods, i + 1, oldCount - i);
                this.methods[i] = method;
                break;
            }
        }
    }

    public Object applyN(Object[] args) throws Throwable {
        if (this.count == 1) {
            return this.methods[0].applyN(args);
        }
        Procedure.checkArgCount(this, args.length);
        CallContext ctx = CallContext.getInstance();
        for (int i = 0; i < this.count; i++) {
            if (this.methods[i].matchN(args, ctx) == 0) {
                return ctx.runUntilValue();
            }
        }
        throw new WrongType((Procedure) this, -1, null);
    }

    public int isApplicable(Type[] args) {
        int best = -1;
        int i = this.count;
        while (true) {
            i--;
            if (i < 0) {
                return best;
            }
            int result = this.methods[i].isApplicable(args);
            if (result == 1) {
                return 1;
            }
            if (result == 0) {
                best = 0;
            }
        }
    }

    public int match0(CallContext ctx) {
        if (this.count == 1) {
            return this.methods[0].match0(ctx);
        }
        for (int i = 0; i < this.count; i++) {
            if (this.methods[i].match0(ctx) == 0) {
                return 0;
            }
        }
        ctx.proc = null;
        return -1;
    }

    public int match1(Object arg1, CallContext ctx) {
        if (this.count == 1) {
            return this.methods[0].match1(arg1, ctx);
        }
        for (int i = 0; i < this.count; i++) {
            if (this.methods[i].match1(arg1, ctx) == 0) {
                return 0;
            }
        }
        ctx.proc = null;
        return -1;
    }

    public int match2(Object arg1, Object arg2, CallContext ctx) {
        if (this.count == 1) {
            return this.methods[0].match2(arg1, arg2, ctx);
        }
        for (int i = 0; i < this.count; i++) {
            if (this.methods[i].match2(arg1, arg2, ctx) == 0) {
                return 0;
            }
        }
        ctx.proc = null;
        return -1;
    }

    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        if (this.count == 1) {
            return this.methods[0].match3(arg1, arg2, arg3, ctx);
        }
        for (int i = 0; i < this.count; i++) {
            if (this.methods[i].match3(arg1, arg2, arg3, ctx) == 0) {
                return 0;
            }
        }
        ctx.proc = null;
        return -1;
    }

    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        if (this.count == 1) {
            return this.methods[0].match4(arg1, arg2, arg3, arg4, ctx);
        }
        for (int i = 0; i < this.count; i++) {
            if (this.methods[i].match4(arg1, arg2, arg3, arg4, ctx) == 0) {
                return 0;
            }
        }
        ctx.proc = null;
        return -1;
    }

    public int matchN(Object[] args, CallContext ctx) {
        if (this.count == 1) {
            return this.methods[0].matchN(args, ctx);
        }
        int i;
        int alen = args.length;
        Type[] atypes = new Type[alen];
        Language language = Language.getDefaultLanguage();
        for (int j = 0; j < alen; j++) {
            Type atype;
            Object arg = args[j];
            if (arg == null) {
                atype = Type.nullType;
            } else {
                Class aclass = arg.getClass();
                if (language != null) {
                    atype = language.getTypeFor(aclass);
                } else {
                    atype = Type.make(aclass);
                }
            }
            atypes[j] = atype;
        }
        int[] codes = new int[this.count];
        int defCount = 0;
        int maybeCount = 0;
        int bestIndex = -1;
        for (i = 0; i < this.count; i++) {
            int code = this.methods[i].isApplicable(atypes);
            if (defCount == 0 && code >= 0) {
                bestIndex = i;
            }
            if (code > 0) {
                defCount++;
            } else if (code == 0) {
                maybeCount++;
            }
            codes[i] = code;
        }
        if (defCount == 1 || (defCount == 0 && maybeCount == 1)) {
            return this.methods[bestIndex].matchN(args, ctx);
        }
        i = 0;
        while (i < this.count) {
            code = codes[i];
            if (code >= 0 && ((code != 0 || defCount <= 0) && this.methods[i].matchN(args, ctx) == 0)) {
                return 0;
            }
            i++;
        }
        ctx.proc = null;
        return -1;
    }

    public void setProperty(Keyword key, Object value) {
        String name = key.getName();
        if (name == "name") {
            setName(value.toString());
        } else if (name == "method") {
            add((MethodProc) value);
        } else {
            super.setProperty(key.asSymbol(), value);
        }
    }

    public final void setProperties(Object[] args) {
        int alen = args.length;
        int i = 0;
        while (i < alen) {
            Object arg = args[i];
            if (arg instanceof Keyword) {
                i++;
                setProperty((Keyword) arg, args[i]);
            } else {
                add((MethodProc) arg);
            }
            i++;
        }
    }

    public static GenericProc make(Object[] args) {
        GenericProc result = new GenericProc();
        result.setProperties(args);
        return result;
    }

    public static GenericProc makeWithoutSorting(Object... args) {
        GenericProc result = new GenericProc();
        int alen = args.length;
        int i = 0;
        while (i < alen) {
            Object arg = args[i];
            if (arg instanceof Keyword) {
                i++;
                result.setProperty((Keyword) arg, args[i]);
            } else {
                result.addAtEnd((MethodProc) arg);
            }
            i++;
        }
        return result;
    }
}

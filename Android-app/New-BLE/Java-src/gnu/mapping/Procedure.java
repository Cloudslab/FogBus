package gnu.mapping;

import android.support.v4.app.FragmentTransaction;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.bytecode.Type;
import gnu.expr.Expression;

public abstract class Procedure extends PropertySet {
    public static final LazyPropertyKey<?> compilerKey = new LazyPropertyKey("compiler");
    private static final Symbol setterKey = Namespace.EmptyNamespace.getSymbol("setter");
    private static final String sourceLocationKey = "source-location";
    public static final Symbol validateApplyKey = Namespace.EmptyNamespace.getSymbol("validate-apply");

    public abstract Object apply0() throws Throwable;

    public abstract Object apply1(Object obj) throws Throwable;

    public abstract Object apply2(Object obj, Object obj2) throws Throwable;

    public abstract Object apply3(Object obj, Object obj2, Object obj3) throws Throwable;

    public abstract Object apply4(Object obj, Object obj2, Object obj3, Object obj4) throws Throwable;

    public abstract Object applyN(Object[] objArr) throws Throwable;

    public void setSourceLocation(String file, int line) {
        setProperty(sourceLocationKey, file + ":" + line);
    }

    public String getSourceLocation() {
        Object value = getProperty(sourceLocationKey, null);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public Procedure(String n) {
        setName(n);
    }

    public final int minArgs() {
        return minArgs(numArgs());
    }

    public final int maxArgs() {
        return maxArgs(numArgs());
    }

    public int numArgs() {
        return -4096;
    }

    public static int minArgs(int num) {
        return num & 4095;
    }

    public static int maxArgs(int num) {
        return num >> 12;
    }

    public static void checkArgCount(Procedure proc, int argCount) {
        int num = proc.numArgs();
        if (argCount < minArgs(num) || (num >= 0 && argCount > maxArgs(num))) {
            throw new WrongArguments(proc, argCount);
        }
    }

    public void apply(CallContext ctx) throws Throwable {
        apply(this, ctx);
    }

    public static void apply(Procedure proc, CallContext ctx) throws Throwable {
        Object result;
        int count = ctx.count;
        if (ctx.where != 0 || count == 0) {
            switch (count) {
                case 0:
                    result = proc.apply0();
                    break;
                case 1:
                    result = proc.apply1(ctx.getNextArg());
                    break;
                case 2:
                    result = proc.apply2(ctx.getNextArg(), ctx.getNextArg());
                    break;
                case 3:
                    result = proc.apply3(ctx.getNextArg(), ctx.getNextArg(), ctx.getNextArg());
                    break;
                case 4:
                    result = proc.apply4(ctx.getNextArg(), ctx.getNextArg(), ctx.getNextArg(), ctx.getNextArg());
                    break;
                default:
                    result = proc.applyN(ctx.getArgs());
                    break;
            }
        }
        result = proc.applyN(ctx.values);
        ctx.writeValue(result);
    }

    public int match0(CallContext ctx) {
        int num = numArgs();
        int min = minArgs(num);
        if (min > 0) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num < 0) {
            return matchN(ProcedureN.noArgs, ctx);
        }
        ctx.count = 0;
        ctx.where = 0;
        ctx.next = 0;
        ctx.proc = this;
        return 0;
    }

    public int match1(Object arg1, CallContext ctx) {
        int num = numArgs();
        int min = minArgs(num);
        if (min > 1) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            int max = maxArgs(num);
            if (max < 1) {
                return MethodProc.NO_MATCH_TOO_MANY_ARGS | max;
            }
            ctx.value1 = arg1;
            ctx.count = 1;
            ctx.where = 1;
            ctx.next = 0;
            ctx.proc = this;
            return 0;
        }
        return matchN(new Object[]{arg1}, ctx);
    }

    public int match2(Object arg1, Object arg2, CallContext ctx) {
        int num = numArgs();
        int min = minArgs(num);
        if (min > 2) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            int max = maxArgs(num);
            if (max < 2) {
                return MethodProc.NO_MATCH_TOO_MANY_ARGS | max;
            }
            ctx.value1 = arg1;
            ctx.value2 = arg2;
            ctx.count = 2;
            ctx.where = 33;
            ctx.next = 0;
            ctx.proc = this;
            return 0;
        }
        return matchN(new Object[]{arg1, arg2}, ctx);
    }

    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        int num = numArgs();
        int min = minArgs(num);
        if (min > 3) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            int max = maxArgs(num);
            if (max < 3) {
                return MethodProc.NO_MATCH_TOO_MANY_ARGS | max;
            }
            ctx.value1 = arg1;
            ctx.value2 = arg2;
            ctx.value3 = arg3;
            ctx.count = 3;
            ctx.where = ErrorMessages.ERROR_SOUND_RECORDER;
            ctx.next = 0;
            ctx.proc = this;
            return 0;
        }
        return matchN(new Object[]{arg1, arg2, arg3}, ctx);
    }

    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        int num = numArgs();
        int min = minArgs(num);
        if (min > 4) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            int max = maxArgs(num);
            if (max < 4) {
                return MethodProc.NO_MATCH_TOO_MANY_ARGS | max;
            }
            ctx.value1 = arg1;
            ctx.value2 = arg2;
            ctx.value3 = arg3;
            ctx.value4 = arg4;
            ctx.count = 4;
            ctx.where = 17185;
            ctx.next = 0;
            ctx.proc = this;
            return 0;
        }
        return matchN(new Object[]{arg1, arg2, arg3, arg4}, ctx);
    }

    public int matchN(Object[] args, CallContext ctx) {
        int num = numArgs();
        int min = minArgs(num);
        if (args.length < min) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            switch (args.length) {
                case 0:
                    return match0(ctx);
                case 1:
                    return match1(args[0], ctx);
                case 2:
                    return match2(args[0], args[1], ctx);
                case 3:
                    return match3(args[0], args[1], args[2], ctx);
                case 4:
                    return match4(args[0], args[1], args[2], args[3], ctx);
                default:
                    int max = maxArgs(num);
                    if (args.length > max) {
                        return MethodProc.NO_MATCH_TOO_MANY_ARGS | max;
                    }
                    break;
            }
        }
        ctx.values = args;
        ctx.count = args.length;
        ctx.where = 0;
        ctx.next = 0;
        ctx.proc = this;
        return 0;
    }

    public void check0(CallContext ctx) {
        int code = match0(ctx);
        if (code != 0) {
            throw MethodProc.matchFailAsException(code, this, ProcedureN.noArgs);
        }
    }

    public void check1(Object arg1, CallContext ctx) {
        int code = match1(arg1, ctx);
        if (code != 0) {
            throw MethodProc.matchFailAsException(code, this, new Object[]{arg1});
        }
    }

    public void check2(Object arg1, Object arg2, CallContext ctx) {
        int code = match2(arg1, arg2, ctx);
        if (code != 0) {
            throw MethodProc.matchFailAsException(code, this, new Object[]{arg1, arg2});
        }
    }

    public void check3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        int code = match3(arg1, arg2, arg3, ctx);
        if (code != 0) {
            throw MethodProc.matchFailAsException(code, this, new Object[]{arg1, arg2, arg3});
        }
    }

    public void check4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        int code = match4(arg1, arg2, arg3, arg4, ctx);
        if (code != 0) {
            throw MethodProc.matchFailAsException(code, this, new Object[]{arg1, arg2, arg3, arg4});
        }
    }

    public void checkN(Object[] args, CallContext ctx) {
        int code = matchN(args, ctx);
        if (code != 0) {
            throw MethodProc.matchFailAsException(code, this, args);
        }
    }

    public Procedure getSetter() {
        if (this instanceof HasSetter) {
            int num_args = numArgs();
            if (num_args == 0) {
                return new Setter0(this);
            }
            if (num_args == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
                return new Setter1(this);
            }
            return new Setter(this);
        }
        Object setter = getProperty(setterKey, null);
        if (setter instanceof Procedure) {
            return (Procedure) setter;
        }
        throw new RuntimeException("procedure '" + getName() + "' has no setter");
    }

    public void setSetter(Procedure setter) {
        if (this instanceof HasSetter) {
            throw new RuntimeException("procedure '" + getName() + "' has builtin setter - cannot be modified");
        }
        setProperty(setterKey, setter);
    }

    public void set0(Object result) throws Throwable {
        getSetter().apply1(result);
    }

    public void set1(Object arg1, Object value) throws Throwable {
        getSetter().apply2(arg1, value);
    }

    public void setN(Object[] args) throws Throwable {
        getSetter().applyN(args);
    }

    public boolean isSideEffectFree() {
        return false;
    }

    public Type getReturnType(Expression[] args) {
        return Type.objectType;
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("#<procedure ");
        String n = getName();
        if (n == null) {
            n = getSourceLocation();
        }
        if (n == null) {
            n = getClass().getName();
        }
        sbuf.append(n);
        sbuf.append('>');
        return sbuf.toString();
    }
}

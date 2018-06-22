package gnu.expr;

import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.lists.Consumer;
import gnu.lists.VoidConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0;
import gnu.mapping.ProcedureN;
import gnu.mapping.Values;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongArguments;
import gnu.text.WriterManager;
import kawa.Shell;

public abstract class ModuleBody extends Procedure0 {
    private static int exitCounter;
    private static boolean mainPrintValues;
    protected boolean runDone;

    public void apply(CallContext ctx) throws Throwable {
        if (ctx.pc == 0) {
            run(ctx);
        }
    }

    public void run(CallContext ctx) throws Throwable {
    }

    public void run() {
        synchronized (this) {
            if (this.runDone) {
                return;
            }
            this.runDone = true;
            run(VoidConsumer.instance);
        }
    }

    public void run(Consumer out) {
        Throwable th;
        CallContext ctx = CallContext.getInstance();
        Consumer save = ctx.consumer;
        ctx.consumer = out;
        try {
            run(ctx);
            th = null;
        } catch (Throwable ex) {
            th = ex;
        }
        runCleanup(ctx, th, save);
    }

    public static void runCleanup(CallContext ctx, Throwable th, Consumer save) {
        if (th == null) {
            try {
                ctx.runUntilDone();
            } catch (Throwable ex) {
                th = ex;
            }
        }
        ctx.consumer = save;
        if (th == null) {
            return;
        }
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        } else {
            throw new WrappedException(th);
        }
    }

    public Object apply0() throws Throwable {
        CallContext ctx = CallContext.getInstance();
        match0(ctx);
        return ctx.runUntilValue();
    }

    public static boolean getMainPrintValues() {
        return mainPrintValues;
    }

    public static void setMainPrintValues(boolean value) {
        mainPrintValues = value;
    }

    public static synchronized void exitIncrement() {
        synchronized (ModuleBody.class) {
            if (exitCounter == 0) {
                exitCounter++;
            }
            exitCounter++;
        }
    }

    public static synchronized void exitDecrement() {
        synchronized (ModuleBody.class) {
            int counter = exitCounter;
            if (counter > 0) {
                counter--;
                if (counter == 0) {
                    System.exit(0);
                } else {
                    exitCounter = counter;
                }
            }
        }
    }

    public final void runAsMain() {
        WriterManager.instance.registerShutdownHook();
        try {
            CallContext ctx = CallContext.getInstance();
            if (getMainPrintValues()) {
                OutPort out = OutPort.outDefault();
                ctx.consumer = Shell.getOutputConsumer(out);
                run(ctx);
                ctx.runUntilDone();
                out.freshLine();
            } else {
                run();
                ctx.runUntilDone();
            }
            OutPort.runCleanups();
            exitDecrement();
        } catch (Throwable ex) {
            ex.printStackTrace();
            OutPort.runCleanups();
            System.exit(-1);
        }
    }

    public Object apply0(ModuleMethod method) throws Throwable {
        return applyN(method, Values.noArgs);
    }

    public Object apply1(ModuleMethod method, Object arg1) throws Throwable {
        return applyN(method, new Object[]{arg1});
    }

    public Object apply2(ModuleMethod method, Object arg1, Object arg2) throws Throwable {
        return applyN(method, new Object[]{arg1, arg2});
    }

    public Object apply3(ModuleMethod method, Object arg1, Object arg2, Object arg3) throws Throwable {
        return applyN(method, new Object[]{arg1, arg2, arg3});
    }

    public Object apply4(ModuleMethod method, Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        return applyN(method, new Object[]{arg1, arg2, arg3, arg4});
    }

    public Object applyN(ModuleMethod method, Object[] args) throws Throwable {
        int count = args.length;
        int num = method.numArgs();
        if (count >= (num & 4095) && (num < 0 || count <= (num >> 12))) {
            switch (count) {
                case 0:
                    return apply0(method);
                case 1:
                    return apply1(method, args[0]);
                case 2:
                    return apply2(method, args[0], args[1]);
                case 3:
                    return apply3(method, args[0], args[1], args[2]);
                case 4:
                    return apply4(method, args[0], args[1], args[2], args[3]);
            }
        }
        throw new WrongArguments(method, count);
    }

    public int match0(ModuleMethod proc, CallContext ctx) {
        int num = proc.numArgs();
        int min = num & 4095;
        if (min > 0) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num < 0) {
            return matchN(proc, ProcedureN.noArgs, ctx);
        }
        ctx.count = 0;
        ctx.where = 0;
        ctx.next = 0;
        ctx.proc = proc;
        return 0;
    }

    public int match1(ModuleMethod proc, Object arg1, CallContext ctx) {
        int num = proc.numArgs();
        int min = num & 4095;
        if (min > 1) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            int max = num >> 12;
            if (max < 1) {
                return MethodProc.NO_MATCH_TOO_MANY_ARGS | max;
            }
            ctx.value1 = arg1;
            ctx.count = 1;
            ctx.where = 1;
            ctx.next = 0;
            ctx.proc = proc;
            return 0;
        }
        return matchN(proc, new Object[]{arg1}, ctx);
    }

    public int match2(ModuleMethod proc, Object arg1, Object arg2, CallContext ctx) {
        int num = proc.numArgs();
        int min = num & 4095;
        if (min > 2) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            int max = num >> 12;
            if (max < 2) {
                return MethodProc.NO_MATCH_TOO_MANY_ARGS | max;
            }
            ctx.value1 = arg1;
            ctx.value2 = arg2;
            ctx.count = 2;
            ctx.where = 33;
            ctx.next = 0;
            ctx.proc = proc;
            return 0;
        }
        return matchN(proc, new Object[]{arg1, arg2}, ctx);
    }

    public int match3(ModuleMethod proc, Object arg1, Object arg2, Object arg3, CallContext ctx) {
        int num = proc.numArgs();
        int min = num & 4095;
        if (min > 3) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            int max = num >> 12;
            if (max < 3) {
                return MethodProc.NO_MATCH_TOO_MANY_ARGS | max;
            }
            ctx.value1 = arg1;
            ctx.value2 = arg2;
            ctx.value3 = arg3;
            ctx.count = 3;
            ctx.where = ErrorMessages.ERROR_SOUND_RECORDER;
            ctx.next = 0;
            ctx.proc = proc;
            return 0;
        }
        return matchN(proc, new Object[]{arg1, arg2, arg3}, ctx);
    }

    public int match4(ModuleMethod proc, Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        int num = proc.numArgs();
        int min = num & 4095;
        if (min > 4) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            int max = num >> 12;
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
            ctx.proc = proc;
            return 0;
        }
        return matchN(proc, new Object[]{arg1, arg2, arg3, arg4}, ctx);
    }

    public int matchN(ModuleMethod proc, Object[] args, CallContext ctx) {
        int num = proc.numArgs();
        int min = num & 4095;
        if (args.length < min) {
            return MethodProc.NO_MATCH_TOO_FEW_ARGS | min;
        }
        if (num >= 0) {
            switch (args.length) {
                case 0:
                    return match0(proc, ctx);
                case 1:
                    return match1(proc, args[0], ctx);
                case 2:
                    return match2(proc, args[0], args[1], ctx);
                case 3:
                    return match3(proc, args[0], args[1], args[2], ctx);
                case 4:
                    return match4(proc, args[0], args[1], args[2], args[3], ctx);
                default:
                    int max = num >> 12;
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
        ctx.proc = proc;
        return 0;
    }
}

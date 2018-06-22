package gnu.expr;

import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import gnu.mapping.WrongArguments;

public class ModuleMethod extends MethodProc {
    public ModuleBody module;
    protected int numArgs;
    public int selector;

    public ModuleMethod(ModuleBody module, int selector, Object name, int numArgs) {
        init(module, selector, name, numArgs);
    }

    public ModuleMethod(ModuleBody module, int selector, Object name, int numArgs, Object argTypes) {
        init(module, selector, name, numArgs);
        this.argTypes = argTypes;
    }

    public ModuleMethod init(ModuleBody module, int selector, Object name, int numArgs) {
        this.module = module;
        this.selector = selector;
        this.numArgs = numArgs;
        if (name != null) {
            setSymbol(name);
        }
        return this;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void resolveParameterTypes() {
        /*
        r11 = this;
        r4 = 0;
        r7 = r11.getName();
        if (r7 == 0) goto L_0x0047;
    L_0x0007:
        r10 = r11.module;	 Catch:{ Throwable -> 0x0046 }
        r6 = r10.getClass();	 Catch:{ Throwable -> 0x0046 }
        r5 = r6.getDeclaredMethods();	 Catch:{ Throwable -> 0x0046 }
        r3 = gnu.expr.Compilation.mangleNameIfNeeded(r7);	 Catch:{ Throwable -> 0x0046 }
        r1 = r5.length;	 Catch:{ Throwable -> 0x0046 }
    L_0x0016:
        r1 = r1 + -1;
        if (r1 < 0) goto L_0x0029;
    L_0x001a:
        r10 = r5[r1];	 Catch:{ Throwable -> 0x0046 }
        r10 = r10.getName();	 Catch:{ Throwable -> 0x0046 }
        r10 = r10.equals(r3);	 Catch:{ Throwable -> 0x0046 }
        if (r10 == 0) goto L_0x0016;
    L_0x0026:
        if (r4 == 0) goto L_0x004f;
    L_0x0028:
        r4 = 0;
    L_0x0029:
        if (r4 == 0) goto L_0x0047;
    L_0x002b:
        r2 = gnu.expr.Language.getDefaultLanguage();	 Catch:{ Throwable -> 0x0046 }
        if (r2 == 0) goto L_0x0047;
    L_0x0031:
        r9 = r4.getParameterTypes();	 Catch:{ Throwable -> 0x0046 }
        r8 = r9.length;	 Catch:{ Throwable -> 0x0046 }
        r0 = new gnu.bytecode.Type[r8];	 Catch:{ Throwable -> 0x0046 }
        r1 = r8;
    L_0x0039:
        r1 = r1 + -1;
        if (r1 < 0) goto L_0x0052;
    L_0x003d:
        r10 = r9[r1];	 Catch:{ Throwable -> 0x0046 }
        r10 = r2.getTypeFor(r10);	 Catch:{ Throwable -> 0x0046 }
        r0[r1] = r10;	 Catch:{ Throwable -> 0x0046 }
        goto L_0x0039;
    L_0x0046:
        r10 = move-exception;
    L_0x0047:
        r10 = r11.argTypes;
        if (r10 != 0) goto L_0x004e;
    L_0x004b:
        super.resolveParameterTypes();
    L_0x004e:
        return;
    L_0x004f:
        r4 = r5[r1];	 Catch:{ Throwable -> 0x0046 }
        goto L_0x0016;
    L_0x0052:
        r11.argTypes = r0;	 Catch:{ Throwable -> 0x0046 }
        goto L_0x0047;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ModuleMethod.resolveParameterTypes():void");
    }

    public int numArgs() {
        return this.numArgs;
    }

    public int match0(CallContext ctx) {
        ctx.count = 0;
        ctx.where = 0;
        return this.module.match0(this, ctx);
    }

    public int match1(Object arg1, CallContext ctx) {
        ctx.count = 1;
        ctx.where = 1;
        return this.module.match1(this, arg1, ctx);
    }

    public int match2(Object arg1, Object arg2, CallContext ctx) {
        ctx.count = 2;
        ctx.where = 33;
        return this.module.match2(this, arg1, arg2, ctx);
    }

    public int match3(Object arg1, Object arg2, Object arg3, CallContext ctx) {
        ctx.count = 3;
        ctx.where = ErrorMessages.ERROR_SOUND_RECORDER;
        return this.module.match3(this, arg1, arg2, arg3, ctx);
    }

    public int match4(Object arg1, Object arg2, Object arg3, Object arg4, CallContext ctx) {
        ctx.count = 4;
        ctx.where = 17185;
        return this.module.match4(this, arg1, arg2, arg3, arg4, ctx);
    }

    public int matchN(Object[] args, CallContext ctx) {
        ctx.count = args.length;
        ctx.where = 0;
        return this.module.matchN(this, args, ctx);
    }

    public void apply(CallContext ctx) throws Throwable {
        Object result;
        switch (ctx.pc) {
            case 0:
                result = apply0();
                break;
            case 1:
                result = apply1(ctx.value1);
                break;
            case 2:
                result = apply2(ctx.value1, ctx.value2);
                break;
            case 3:
                result = apply3(ctx.value1, ctx.value2, ctx.value3);
                break;
            case 4:
                result = apply4(ctx.value1, ctx.value2, ctx.value3, ctx.value4);
                break;
            case 5:
                result = applyN(ctx.values);
                break;
            default:
                throw new Error("internal error - apply " + this);
        }
        ctx.writeValue(result);
    }

    public Object apply0() throws Throwable {
        return this.module.apply0(this);
    }

    public Object apply1(Object arg1) throws Throwable {
        return this.module.apply1(this, arg1);
    }

    public Object apply2(Object arg1, Object arg2) throws Throwable {
        return this.module.apply2(this, arg1, arg2);
    }

    public Object apply3(Object arg1, Object arg2, Object arg3) throws Throwable {
        return this.module.apply3(this, arg1, arg2, arg3);
    }

    public Object apply4(Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        return this.module.apply4(this, arg1, arg2, arg3, arg4);
    }

    public Object applyN(Object[] args) throws Throwable {
        return this.module.applyN(this, args);
    }

    public static Object apply0Default(ModuleMethod method) throws Throwable {
        return method.module.applyN(method, Values.noArgs);
    }

    public static Object apply1Default(ModuleMethod method, Object arg1) throws Throwable {
        return method.module.applyN(method, new Object[]{arg1});
    }

    public static Object apply2Default(ModuleMethod method, Object arg1, Object arg2) throws Throwable {
        return method.module.applyN(method, new Object[]{arg1, arg2});
    }

    public static Object apply3Default(ModuleMethod method, Object arg1, Object arg2, Object arg3) throws Throwable {
        return method.module.applyN(method, new Object[]{arg1, arg2, arg3});
    }

    public static Object apply4Default(ModuleMethod method, Object arg1, Object arg2, Object arg3, Object arg4) throws Throwable {
        return method.module.applyN(method, new Object[]{arg1, arg2, arg3, arg4});
    }

    public static Object applyNDefault(ModuleMethod method, Object[] args) throws Throwable {
        int count = args.length;
        int num = method.numArgs();
        ModuleBody module = method.module;
        if (count >= (num & 4095) && (num < 0 || count <= (num >> 12))) {
            switch (count) {
                case 0:
                    return module.apply0(method);
                case 1:
                    return module.apply1(method, args[0]);
                case 2:
                    return module.apply2(method, args[0], args[1]);
                case 3:
                    return module.apply3(method, args[0], args[1], args[2]);
                case 4:
                    return module.apply4(method, args[0], args[1], args[2], args[3]);
            }
        }
        throw new WrongArguments(method, count);
    }

    public static void applyError() {
        throw new Error("internal error - bad selector");
    }
}

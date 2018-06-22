package gnu.kawa.slib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.PropertySet;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;
import gnu.text.Char;
import gnu.text.Path;
import kawa.lib.lists;
import kawa.lib.ports;

/* compiled from: ppfile.scm */
public class ppfile extends ModuleBody {
    public static final ppfile $instance = new ppfile();
    static final Char Lit0 = Char.make(59);
    static final Char Lit1 = Char.make(10);
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("pprint-filter-file").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("pprint-file").readResolve());
    static final ModuleMethod lambda$Fn3;
    public static final ModuleMethod pprint$Mnfile;
    public static final ModuleMethod pprint$Mnfilter$Mnfile;

    /* compiled from: ppfile.scm */
    public class frame0 extends ModuleBody {
        final ModuleMethod lambda$Fn2;
        Object port;
        frame staticLink;

        public frame0() {
            PropertySet moduleMethod = new ModuleMethod(this, 1, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/ppfile.scm:34");
            this.lambda$Fn2 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 1 ? lambda2(obj) : super.apply1(moduleMethod, obj);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        java.lang.Object lambda2(java.lang.Object r10) {
            /*
            r9 = this;
            r7 = 2;
            r8 = 1;
            r4 = kawa.standard.readchar.peekChar;
            r5 = r9.port;
            r1 = r4.apply1(r5);
        L_0x000a:
            r3 = kawa.lib.ports.isEofObject(r1);
            if (r3 == 0) goto L_0x0018;
        L_0x0010:
            if (r3 == 0) goto L_0x0015;
        L_0x0012:
            r4 = java.lang.Boolean.TRUE;
        L_0x0014:
            return r4;
        L_0x0015:
            r4 = java.lang.Boolean.FALSE;
            goto L_0x0014;
        L_0x0018:
            r0 = r1;
            r0 = (gnu.text.Char) r0;	 Catch:{ ClassCastException -> 0x00cb }
            r4 = r0;
            r4 = kawa.lib.rnrs.unicode.isCharWhitespace(r4);
            if (r4 == 0) goto L_0x0036;
        L_0x0022:
            r4 = kawa.standard.readchar.readChar;
            r5 = r9.port;
            r4 = r4.apply1(r5);
            kawa.lib.ports.display(r4, r10);
            r4 = kawa.standard.readchar.peekChar;
            r5 = r9.port;
            r1 = r4.apply1(r5);
            goto L_0x000a;
        L_0x0036:
            r5 = gnu.kawa.slib.ppfile.Lit0;
            r0 = r1;
            r0 = (gnu.text.Char) r0;	 Catch:{ ClassCastException -> 0x00d4 }
            r4 = r0;
            r4 = kawa.lib.characters.isChar$Eq(r5, r4);
            if (r4 == 0) goto L_0x0082;
        L_0x0042:
            r3 = kawa.lib.ports.isEofObject(r1);
            if (r3 == 0) goto L_0x0050;
        L_0x0048:
            if (r3 == 0) goto L_0x004d;
        L_0x004a:
            r4 = java.lang.Boolean.TRUE;
            goto L_0x0014;
        L_0x004d:
            r4 = java.lang.Boolean.FALSE;
            goto L_0x0014;
        L_0x0050:
            r4 = gnu.kawa.slib.ppfile.Lit1;
            r1 = (gnu.text.Char) r1;	 Catch:{ ClassCastException -> 0x00dd }
            r4 = kawa.lib.characters.isChar$Eq(r4, r1);
            if (r4 == 0) goto L_0x006e;
        L_0x005a:
            r4 = kawa.standard.readchar.readChar;
            r5 = r9.port;
            r4 = r4.apply1(r5);
            kawa.lib.ports.display(r4, r10);
            r4 = kawa.standard.readchar.peekChar;
            r5 = r9.port;
            r1 = r4.apply1(r5);
            goto L_0x000a;
        L_0x006e:
            r4 = kawa.standard.readchar.readChar;
            r5 = r9.port;
            r4 = r4.apply1(r5);
            kawa.lib.ports.display(r4, r10);
            r4 = kawa.standard.readchar.peekChar;
            r5 = r9.port;
            r1 = r4.apply1(r5);
            goto L_0x0042;
        L_0x0082:
            r4 = r9.port;
            r4 = (gnu.mapping.InPort) r4;	 Catch:{ ClassCastException -> 0x00e6 }
            r2 = kawa.lib.ports.read(r4);
            r3 = kawa.lib.ports.isEofObject(r2);
            if (r3 == 0) goto L_0x0099;
        L_0x0090:
            if (r3 == 0) goto L_0x0095;
        L_0x0092:
            r4 = java.lang.Boolean.TRUE;
            goto L_0x0014;
        L_0x0095:
            r4 = java.lang.Boolean.FALSE;
            goto L_0x0014;
        L_0x0099:
            r4 = kawa.standard.Scheme.applyToArgs;
            r5 = r9.staticLink;
            r5 = r5.filter;
            r4 = r4.apply2(r5, r2);
            gnu.kawa.slib.pp.prettyPrint(r4, r10);
            r4 = kawa.standard.readchar.peekChar;
            r5 = r9.port;
            r1 = r4.apply1(r5);
            r4 = kawa.standard.Scheme.isEqv;
            r5 = gnu.kawa.slib.ppfile.Lit1;
            r4 = r4.apply2(r5, r1);
            r5 = java.lang.Boolean.FALSE;
            if (r4 == r5) goto L_0x000a;
        L_0x00ba:
            r4 = kawa.standard.readchar.readChar;
            r5 = r9.port;
            r4.apply1(r5);
            r4 = kawa.standard.readchar.peekChar;
            r5 = r9.port;
            r1 = r4.apply1(r5);
            goto L_0x000a;
        L_0x00cb:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "char-whitespace?";
            r5.<init>(r4, r6, r8, r1);
            throw r5;
        L_0x00d4:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "char=?";
            r5.<init>(r4, r6, r7, r1);
            throw r5;
        L_0x00dd:
            r4 = move-exception;
            r5 = new gnu.mapping.WrongType;
            r6 = "char=?";
            r5.<init>(r4, r6, r7, r1);
            throw r5;
        L_0x00e6:
            r5 = move-exception;
            r6 = new gnu.mapping.WrongType;
            r7 = "read";
            r6.<init>(r5, r7, r8, r4);
            throw r6;
            */
            throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.slib.ppfile.frame0.lambda2(java.lang.Object):java.lang.Object");
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 1) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    /* compiled from: ppfile.scm */
    public class frame extends ModuleBody {
        Object filter;
        final ModuleMethod lambda$Fn1;
        LList optarg;

        public frame() {
            PropertySet moduleMethod = new ModuleMethod(this, 2, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/ppfile.scm:27");
            this.lambda$Fn1 = moduleMethod;
        }

        public Object apply1(ModuleMethod moduleMethod, Object obj) {
            return moduleMethod.selector == 2 ? lambda1(obj) : super.apply1(moduleMethod, obj);
        }

        Object lambda1(Object port) {
            frame0 gnu_kawa_slib_ppfile_frame0 = new frame0();
            gnu_kawa_slib_ppfile_frame0.staticLink = this;
            gnu_kawa_slib_ppfile_frame0.port = port;
            Procedure fun = gnu_kawa_slib_ppfile_frame0.lambda$Fn2;
            Object outport = lists.isNull(this.optarg) ? ports.current$Mnoutput$Mnport.apply0() : lists.car.apply1(this.optarg);
            if (ports.isOutputPort(outport)) {
                return gnu_kawa_slib_ppfile_frame0.lambda2(outport);
            }
            try {
                return ports.callWithOutputFile(Path.valueOf(outport), fun);
            } catch (ClassCastException e) {
                throw new WrongType(e, "call-with-output-file", 1, outport);
            }
        }

        public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
            if (moduleMethod.selector != 2) {
                return super.match1(moduleMethod, obj, callContext);
            }
            callContext.value1 = obj;
            callContext.proc = moduleMethod;
            callContext.pc = 1;
            return 0;
        }
    }

    static {
        ModuleBody moduleBody = $instance;
        pprint$Mnfilter$Mnfile = new ModuleMethod(moduleBody, 3, Lit2, -4094);
        PropertySet moduleMethod = new ModuleMethod(moduleBody, 4, null, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        moduleMethod.setProperty("source-location", "/Users/ewpatton/Programming/mit/ai2-kawa/gnu/kawa/slib/ppfile.scm:70");
        lambda$Fn3 = moduleMethod;
        pprint$Mnfile = new ModuleMethod(moduleBody, 5, Lit3, 8193);
        $instance.run();
    }

    public ppfile() {
        ModuleInfo.register(this);
    }

    public static Object pprintFile(Object obj) {
        return pprintFile(obj, ports.current$Mnoutput$Mnport.apply0());
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Object pprintFilterFile$V(Object inport, Object filter, Object[] argsArray) {
        frame gnu_kawa_slib_ppfile_frame = new frame();
        gnu_kawa_slib_ppfile_frame.filter = filter;
        gnu_kawa_slib_ppfile_frame.optarg = LList.makeList(argsArray, 0);
        Procedure fun = gnu_kawa_slib_ppfile_frame.lambda$Fn1;
        if (ports.isInputPort(inport)) {
            return gnu_kawa_slib_ppfile_frame.lambda1(inport);
        }
        try {
            return ports.callWithInputFile(Path.valueOf(inport), fun);
        } catch (ClassCastException e) {
            throw new WrongType(e, "call-with-input-file", 1, inport);
        }
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        if (moduleMethod.selector != 3) {
            return super.applyN(moduleMethod, objArr);
        }
        Object obj = objArr[0];
        Object obj2 = objArr[1];
        int length = objArr.length - 2;
        Object[] objArr2 = new Object[length];
        while (true) {
            length--;
            if (length < 0) {
                return pprintFilterFile$V(obj, obj2, objArr2);
            }
            objArr2[length] = objArr[length + 2];
        }
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        if (moduleMethod.selector != 3) {
            return super.matchN(moduleMethod, objArr, callContext);
        }
        callContext.values = objArr;
        callContext.proc = moduleMethod;
        callContext.pc = 5;
        return 0;
    }

    public static Object pprintFile(Object ifile, Object oport) {
        return pprintFilterFile$V(ifile, lambda$Fn3, new Object[]{oport});
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 4:
                return lambda3(obj);
            case 5:
                return pprintFile(obj);
            default:
                return super.apply1(moduleMethod, obj);
        }
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        return moduleMethod.selector == 5 ? pprintFile(obj, obj2) : super.apply2(moduleMethod, obj, obj2);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        if (moduleMethod.selector != 5) {
            return super.match2(moduleMethod, obj, obj2, callContext);
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        callContext.proc = moduleMethod;
        callContext.pc = 2;
        return 0;
    }

    static Object lambda3(Object x) {
        return x;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }
}

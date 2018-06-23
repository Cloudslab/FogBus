package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.Special;
import gnu.kawa.functions.AddOp;
import gnu.kawa.functions.DivideOp;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;
import kawa.standard.Scheme;
import kawa.standard.append;

/* compiled from: srfi95.scm */
public class srfi95 extends ModuleBody {
    public static final ModuleMethod $Pcsort$Mnlist;
    public static final ModuleMethod $Pcsort$Mnvector;
    public static final ModuleMethod $Pcvector$Mnsort$Ex;
    public static final srfi95 $instance = new srfi95();
    static final IntNum Lit0 = IntNum.make(-1);
    static final IntNum Lit1 = IntNum.make(2);
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("%vector-sort!").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("%sort-vector").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("sort").readResolve());
    static final IntNum Lit2 = IntNum.make(1);
    static final IntNum Lit3 = IntNum.make(0);
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("identity").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("sorted?").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("merge").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("merge!").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("%sort-list").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("sort!").readResolve());
    static final ModuleMethod identity;
    public static final ModuleMethod merge;
    public static final ModuleMethod merge$Ex;
    public static final ModuleMethod sort;
    public static final ModuleMethod sort$Ex;
    public static final ModuleMethod sorted$Qu;

    /* compiled from: srfi95.scm */
    public class frame0 extends ModuleBody {
        Object keyer;
        Object less$Qu;
        Object seq;

        public Object lambda2step(Object n) {
            Object p;
            if (Scheme.numGrt.apply2(n, srfi95.Lit1) != Boolean.FALSE) {
                Object j = DivideOp.quotient.apply2(n, srfi95.Lit1);
                return srfi95.sort$ClMerge$Ex(lambda2step(j), lambda2step(AddOp.$Mn.apply2(n, j)), this.less$Qu, this.keyer);
            } else if (Scheme.numEqu.apply2(n, srfi95.Lit1) != Boolean.FALSE) {
                Object apply1;
                Object x = lists.car.apply1(this.seq);
                Object y = lists.cadr.apply1(this.seq);
                p = this.seq;
                this.seq = lists.cddr.apply1(this.seq);
                if (Scheme.applyToArgs.apply3(this.less$Qu, Scheme.applyToArgs.apply2(this.keyer, y), Scheme.applyToArgs.apply2(this.keyer, x)) != Boolean.FALSE) {
                    try {
                        lists.setCar$Ex((Pair) p, y);
                        apply1 = lists.cdr.apply1(p);
                        try {
                            lists.setCar$Ex((Pair) apply1, x);
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "set-car!", 1, apply1);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "set-car!", 1, p);
                    }
                }
                apply1 = lists.cdr.apply1(p);
                try {
                    lists.setCdr$Ex((Pair) apply1, LList.Empty);
                    return p;
                } catch (ClassCastException e3) {
                    throw new WrongType(e3, "set-cdr!", 1, apply1);
                }
            } else if (Scheme.numEqu.apply2(n, srfi95.Lit2) == Boolean.FALSE) {
                return LList.Empty;
            } else {
                p = this.seq;
                this.seq = lists.cdr.apply1(this.seq);
                try {
                    lists.setCdr$Ex((Pair) p, LList.Empty);
                    return p;
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "set-cdr!", 1, p);
                }
            }
        }
    }

    /* compiled from: srfi95.scm */
    public class frame1 extends ModuleBody {
        Object key;
        Object less$Qu;

        public Object lambda3loop(Object r, Object a, Object kcara, Object b, Object kcarb) {
            if (Scheme.applyToArgs.apply3(this.less$Qu, kcarb, kcara) != Boolean.FALSE) {
                try {
                    lists.setCdr$Ex((Pair) r, b);
                    if (lists.isNull(lists.cdr.apply1(b))) {
                        try {
                            lists.setCdr$Ex((Pair) b, a);
                            return Values.empty;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "set-cdr!", 1, b);
                        }
                    }
                    return lambda3loop(b, a, kcara, lists.cdr.apply1(b), Scheme.applyToArgs.apply2(this.key, lists.cadr.apply1(b)));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "set-cdr!", 1, r);
                }
            }
            try {
                lists.setCdr$Ex((Pair) r, a);
                if (lists.isNull(lists.cdr.apply1(a))) {
                    try {
                        lists.setCdr$Ex((Pair) a, b);
                        return Values.empty;
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "set-cdr!", 1, a);
                    }
                }
                return lambda3loop(a, lists.cdr.apply1(a), Scheme.applyToArgs.apply2(this.key, lists.cadr.apply1(a)), b, kcarb);
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "set-cdr!", 1, r);
            }
        }
    }

    /* compiled from: srfi95.scm */
    public class frame extends ModuleBody {
        Object key;
        Object less$Qu;

        public Object lambda1loop(Object x, Object kx, Object a, Object y, Object ky, Object b) {
            if (Scheme.applyToArgs.apply3(this.less$Qu, ky, kx) != Boolean.FALSE) {
                if (lists.isNull(b)) {
                    return lists.cons(y, lists.cons(x, a));
                }
                return lists.cons(y, lambda1loop(x, kx, a, lists.car.apply1(b), Scheme.applyToArgs.apply2(this.key, lists.car.apply1(b)), lists.cdr.apply1(b)));
            } else if (lists.isNull(a)) {
                return lists.cons(x, lists.cons(y, b));
            } else {
                return lists.cons(x, lambda1loop(lists.car.apply1(a), Scheme.applyToArgs.apply2(this.key, lists.car.apply1(a)), lists.cdr.apply1(a), y, ky, b));
            }
        }
    }

    public static void $PcSortVector(Sequence sequence, Object obj) {
        $PcSortVector(sequence, obj, Boolean.FALSE);
    }

    static {
        ModuleBody moduleBody = $instance;
        identity = new ModuleMethod(moduleBody, 1, Lit4, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        sorted$Qu = new ModuleMethod(moduleBody, 2, Lit5, 12290);
        merge = new ModuleMethod(moduleBody, 4, Lit6, 16387);
        merge$Ex = new ModuleMethod(moduleBody, 6, Lit7, 16387);
        $Pcsort$Mnlist = new ModuleMethod(moduleBody, 8, Lit8, 12291);
        sort$Ex = new ModuleMethod(moduleBody, 9, Lit9, 12290);
        $Pcvector$Mnsort$Ex = new ModuleMethod(moduleBody, 11, Lit10, 12291);
        $Pcsort$Mnvector = new ModuleMethod(moduleBody, 12, Lit11, 12290);
        sort = new ModuleMethod(moduleBody, 14, Lit12, 12290);
        $instance.run();
    }

    public srfi95() {
        ModuleInfo.register(this);
    }

    public static Object isSorted(Object obj, Object obj2) {
        return isSorted(obj, obj2, identity);
    }

    public static Object merge(Object obj, Object obj2, Object obj3) {
        return merge(obj, obj2, obj3, identity);
    }

    public static Object merge$Ex(Object obj, Object obj2, Object obj3) {
        return merge$Ex(obj, obj2, obj3, identity);
    }

    public static Object sort(Sequence sequence, Object obj) {
        return sort(sequence, obj, Boolean.FALSE);
    }

    public static Object sort$Ex(Sequence sequence, Object obj) {
        return sort$Ex(sequence, obj, Boolean.FALSE);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static Object identity(Object x) {
        return x;
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        return moduleMethod.selector == 1 ? identity(obj) : super.apply1(moduleMethod, obj);
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object isSorted(java.lang.Object r13, java.lang.Object r14, java.lang.Object r15) {
        /*
        r10 = 0;
        r12 = -2;
        r9 = 1;
        r8 = kawa.lib.lists.isNull(r13);
        if (r8 == 0) goto L_0x000c;
    L_0x0009:
        r8 = java.lang.Boolean.TRUE;
    L_0x000b:
        return r8;
    L_0x000c:
        r8 = r13 instanceof gnu.lists.Sequence;
        if (r8 == 0) goto L_0x0073;
    L_0x0010:
        r0 = r13;
        r0 = (gnu.lists.Sequence) r0;	 Catch:{ ClassCastException -> 0x00d5 }
        r1 = r0;
        r8 = r1.size();
        r2 = r8 + -1;
        if (r2 > r9) goto L_0x0024;
    L_0x001c:
        r7 = r9;
    L_0x001d:
        if (r7 == 0) goto L_0x0029;
    L_0x001f:
        if (r7 == 0) goto L_0x0026;
    L_0x0021:
        r8 = java.lang.Boolean.TRUE;
        goto L_0x000b;
    L_0x0024:
        r7 = r10;
        goto L_0x001d;
    L_0x0026:
        r8 = java.lang.Boolean.FALSE;
        goto L_0x000b;
    L_0x0029:
        r8 = r2 + -1;
        r3 = java.lang.Integer.valueOf(r8);
        r8 = kawa.standard.Scheme.applyToArgs;
        r10 = r1.get(r2);
        r4 = r8.apply2(r15, r10);
    L_0x0039:
        r8 = gnu.kawa.lispexpr.LangObjType.coerceRealNum(r3);	 Catch:{ ClassCastException -> 0x00de }
        r7 = kawa.lib.numbers.isNegative(r8);
        if (r7 == 0) goto L_0x004b;
    L_0x0043:
        if (r7 == 0) goto L_0x0048;
    L_0x0045:
        r8 = java.lang.Boolean.TRUE;
        goto L_0x000b;
    L_0x0048:
        r8 = java.lang.Boolean.FALSE;
        goto L_0x000b;
    L_0x004b:
        r10 = kawa.standard.Scheme.applyToArgs;
        r0 = r3;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x00e7 }
        r8 = r0;
        r8 = r8.intValue();	 Catch:{ ClassCastException -> 0x00e7 }
        r8 = r1.get(r8);
        r6 = r10.apply2(r15, r8);
        r8 = kawa.standard.Scheme.applyToArgs;
        r7 = r8.apply3(r14, r6, r4);
        r8 = java.lang.Boolean.FALSE;
        if (r7 == r8) goto L_0x0071;
    L_0x0067:
        r8 = gnu.kawa.functions.AddOp.$Pl;
        r10 = Lit0;
        r3 = r8.apply2(r10, r3);
        r4 = r6;
        goto L_0x0039;
    L_0x0071:
        r8 = r7;
        goto L_0x000b;
    L_0x0073:
        r8 = kawa.lib.lists.cdr;
        r8 = r8.apply1(r13);
        r8 = kawa.lib.lists.isNull(r8);
        if (r8 == 0) goto L_0x0082;
    L_0x007f:
        r8 = java.lang.Boolean.TRUE;
        goto L_0x000b;
    L_0x0082:
        r8 = kawa.standard.Scheme.applyToArgs;
        r11 = kawa.lib.lists.car;
        r11 = r11.apply1(r13);
        r4 = r8.apply2(r15, r11);
        r8 = kawa.lib.lists.cdr;
        r5 = r8.apply1(r13);
    L_0x0094:
        r7 = kawa.lib.lists.isNull(r5);
        if (r7 == 0) goto L_0x00a4;
    L_0x009a:
        if (r7 == 0) goto L_0x00a0;
    L_0x009c:
        r8 = java.lang.Boolean.TRUE;
        goto L_0x000b;
    L_0x00a0:
        r8 = java.lang.Boolean.FALSE;
        goto L_0x000b;
    L_0x00a4:
        r8 = kawa.standard.Scheme.applyToArgs;
        r11 = kawa.lib.lists.car;
        r11 = r11.apply1(r5);
        r6 = r8.apply2(r15, r11);
        r8 = kawa.standard.Scheme.applyToArgs;
        r8 = r8.apply3(r14, r6, r4);
        r11 = java.lang.Boolean.FALSE;	 Catch:{ ClassCastException -> 0x00f1 }
        if (r8 == r11) goto L_0x00c9;
    L_0x00ba:
        r8 = r9;
    L_0x00bb:
        r8 = r8 + 1;
        r7 = r8 & 1;
        if (r7 == 0) goto L_0x00cb;
    L_0x00c1:
        r8 = kawa.lib.lists.cdr;
        r5 = r8.apply1(r5);
        r4 = r6;
        goto L_0x0094;
    L_0x00c9:
        r8 = r10;
        goto L_0x00bb;
    L_0x00cb:
        if (r7 == 0) goto L_0x00d1;
    L_0x00cd:
        r8 = java.lang.Boolean.TRUE;
        goto L_0x000b;
    L_0x00d1:
        r8 = java.lang.Boolean.FALSE;
        goto L_0x000b;
    L_0x00d5:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "arr";
        r9.<init>(r8, r10, r12, r13);
        throw r9;
    L_0x00de:
        r8 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "negative?";
        r10.<init>(r8, r11, r9, r3);
        throw r10;
    L_0x00e7:
        r8 = move-exception;
        r9 = new gnu.mapping.WrongType;
        r10 = "gnu.lists.Sequence.get(int)";
        r11 = 2;
        r9.<init>(r8, r10, r11, r3);
        throw r9;
    L_0x00f1:
        r9 = move-exception;
        r10 = new gnu.mapping.WrongType;
        r11 = "x";
        r10.<init>(r9, r11, r12, r8);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.lib.srfi95.isSorted(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 9:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 12:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 14:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 4:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 6:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 8:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 9:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 11:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 12:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 14:
                if (!(obj instanceof Sequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static Object merge(Object a, Object b, Object isLess, Object key) {
        frame kawa_lib_srfi95_frame = new frame();
        kawa_lib_srfi95_frame.less$Qu = isLess;
        kawa_lib_srfi95_frame.key = key;
        if (lists.isNull(a)) {
            return b;
        }
        if (lists.isNull(b)) {
            return a;
        }
        return kawa_lib_srfi95_frame.lambda1loop(lists.car.apply1(a), Scheme.applyToArgs.apply2(kawa_lib_srfi95_frame.key, lists.car.apply1(a)), lists.cdr.apply1(a), lists.car.apply1(b), Scheme.applyToArgs.apply2(kawa_lib_srfi95_frame.key, lists.car.apply1(b)), lists.cdr.apply1(b));
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            case 6:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.value4 = obj4;
                callContext.proc = moduleMethod;
                callContext.pc = 4;
                return 0;
            default:
                return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
    }

    static Object sort$ClMerge$Ex(Object a, Object b, Object isLess, Object key) {
        frame1 kawa_lib_srfi95_frame1 = new frame1();
        kawa_lib_srfi95_frame1.less$Qu = isLess;
        kawa_lib_srfi95_frame1.key = key;
        if (lists.isNull(a)) {
            return b;
        }
        if (lists.isNull(b)) {
            return a;
        }
        Object kcara = Scheme.applyToArgs.apply2(kawa_lib_srfi95_frame1.key, lists.car.apply1(a));
        Object kcarb = Scheme.applyToArgs.apply2(kawa_lib_srfi95_frame1.key, lists.car.apply1(b));
        if (Scheme.applyToArgs.apply3(kawa_lib_srfi95_frame1.less$Qu, kcarb, kcara) == Boolean.FALSE) {
            if (lists.isNull(lists.cdr.apply1(a))) {
                try {
                    lists.setCdr$Ex((Pair) a, b);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-cdr!", 1, a);
                }
            }
            kawa_lib_srfi95_frame1.lambda3loop(a, lists.cdr.apply1(a), Scheme.applyToArgs.apply2(kawa_lib_srfi95_frame1.key, lists.cadr.apply1(a)), b, kcarb);
            return a;
        } else if (lists.isNull(lists.cdr.apply1(b))) {
            try {
                lists.setCdr$Ex((Pair) b, a);
                return b;
            } catch (ClassCastException e2) {
                throw new WrongType(e2, "set-cdr!", 1, b);
            }
        } else {
            kawa_lib_srfi95_frame1.lambda3loop(b, a, kcara, lists.cdr.apply1(b), Scheme.applyToArgs.apply2(kawa_lib_srfi95_frame1.key, lists.cadr.apply1(b)));
            return b;
        }
    }

    public static Object merge$Ex(Object a, Object b, Object less$Qu, Object key) {
        return sort$ClMerge$Ex(a, b, less$Qu, key);
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        switch (moduleMethod.selector) {
            case 4:
                return merge(obj, obj2, obj3, obj4);
            case 6:
                return merge$Ex(obj, obj2, obj3, obj4);
            default:
                return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
    }

    public static Object $PcSortList(Object seq, Object isLess, Object key) {
        Object obj;
        frame0 kawa_lib_srfi95_frame0 = new frame0();
        kawa_lib_srfi95_frame0.seq = seq;
        kawa_lib_srfi95_frame0.less$Qu = isLess;
        kawa_lib_srfi95_frame0.keyer = Special.undefined;
        if (key != Boolean.FALSE) {
            obj = lists.car;
        } else {
            obj = identity;
        }
        kawa_lib_srfi95_frame0.keyer = obj;
        if (key != Boolean.FALSE) {
            Object lst = kawa_lib_srfi95_frame0.seq;
            while (!lists.isNull(lst)) {
                try {
                    lists.setCar$Ex((Pair) lst, lists.cons(Scheme.applyToArgs.apply2(key, lists.car.apply1(lst)), lists.car.apply1(lst)));
                    lst = lists.cdr.apply1(lst);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "set-car!", 1, lst);
                }
            }
            obj = kawa_lib_srfi95_frame0.seq;
            try {
                kawa_lib_srfi95_frame0.seq = kawa_lib_srfi95_frame0.lambda2step(Integer.valueOf(lists.length((LList) obj)));
                lst = kawa_lib_srfi95_frame0.seq;
                while (!lists.isNull(lst)) {
                    try {
                        lists.setCar$Ex((Pair) lst, lists.cdar.apply1(lst));
                        lst = lists.cdr.apply1(lst);
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "set-car!", 1, lst);
                    }
                }
                return kawa_lib_srfi95_frame0.seq;
            } catch (ClassCastException e3) {
                throw new WrongType(e3, "length", 1, obj);
            }
        }
        obj = kawa_lib_srfi95_frame0.seq;
        try {
            return kawa_lib_srfi95_frame0.lambda2step(Integer.valueOf(lists.length((LList) obj)));
        } catch (ClassCastException e32) {
            throw new WrongType(e32, "length", 1, obj);
        }
    }

    static Object rank$Mn1Array$To$List(Sequence seq) {
        Pair lst = LList.Empty;
        for (int idx = seq.size() - 1; idx >= 0; idx--) {
            lst = lists.cons(seq.get(idx), lst);
        }
        return lst;
    }

    public static Object sort$Ex(Sequence seq, Object less$Qu, Object key) {
        if (!lists.isList(seq)) {
            return $PcVectorSort$Ex(seq, less$Qu, key);
        }
        Object ret = $PcSortList(seq, less$Qu, key);
        if (ret == seq) {
            return seq;
        }
        Object crt = ret;
        while (lists.cdr.apply1(crt) != seq) {
            crt = lists.cdr.apply1(crt);
        }
        try {
            lists.setCdr$Ex((Pair) crt, ret);
            Object scar = lists.car.apply1(seq);
            Object scdr = lists.cdr.apply1(seq);
            try {
                lists.setCar$Ex((Pair) seq, lists.car.apply1(ret));
                try {
                    lists.setCdr$Ex((Pair) seq, lists.cdr.apply1(ret));
                    try {
                        lists.setCar$Ex((Pair) ret, scar);
                        try {
                            lists.setCdr$Ex((Pair) ret, scdr);
                            return seq;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "set-cdr!", 1, ret);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "set-car!", 1, ret);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "set-cdr!", 1, (Object) seq);
                }
            } catch (ClassCastException e222) {
                throw new WrongType(e222, "set-car!", 1, (Object) seq);
            }
        } catch (ClassCastException e2222) {
            throw new WrongType(e2222, "set-cdr!", 1, crt);
        }
    }

    public static Object $PcVectorSort$Ex(Sequence seq, Object less$Qu, Object key) {
        Object sorted = $PcSortList(rank$Mn1Array$To$List(seq), less$Qu, key);
        Object obj = Lit3;
        while (!lists.isNull(sorted)) {
            seq.set(((Number) obj).intValue(), lists.car.apply1(sorted));
            sorted = lists.cdr.apply1(sorted);
            obj = AddOp.$Pl.apply2(obj, Lit2);
        }
        return seq;
    }

    public static void $PcSortVector(Sequence seq, Object less$Qu, Object key) {
        FVector newra = vectors.makeVector(seq.size());
        Object sorted = $PcSortList(rank$Mn1Array$To$List(seq), less$Qu, key);
        Object obj = Lit3;
        while (!lists.isNull(sorted)) {
            try {
                vectors.vectorSet$Ex(newra, ((Number) obj).intValue(), lists.car.apply1(sorted));
                sorted = lists.cdr.apply1(sorted);
                obj = AddOp.$Pl.apply2(obj, Lit2);
            } catch (ClassCastException e) {
                throw new WrongType(e, "vector-set!", 1, obj);
            }
        }
    }

    public static Object sort(Sequence seq, Object less$Qu, Object key) {
        if (lists.isList(seq)) {
            return $PcSortList(append.append$V(new Object[]{seq, LList.Empty}), less$Qu, key);
        }
        $PcSortVector(seq, less$Qu, key);
        return Values.empty;
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 2:
                return isSorted(obj, obj2);
            case 9:
                try {
                    return sort$Ex((Sequence) obj, obj2);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "sort!", 1, obj);
                }
            case 12:
                try {
                    $PcSortVector((Sequence) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "%sort-vector", 1, obj);
                }
            case 14:
                try {
                    return sort((Sequence) obj, obj2);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "sort", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 2:
                return isSorted(obj, obj2, obj3);
            case 4:
                return merge(obj, obj2, obj3);
            case 6:
                return merge$Ex(obj, obj2, obj3);
            case 8:
                return $PcSortList(obj, obj2, obj3);
            case 9:
                try {
                    return sort$Ex((Sequence) obj, obj2, obj3);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "sort!", 1, obj);
                }
            case 11:
                try {
                    return $PcVectorSort$Ex((Sequence) obj, obj2, obj3);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "%vector-sort!", 1, obj);
                }
            case 12:
                try {
                    $PcSortVector((Sequence) obj, obj2, obj3);
                    return Values.empty;
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "%sort-vector", 1, obj);
                }
            case 14:
                try {
                    return sort((Sequence) obj, obj2, obj3);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "sort", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }
}

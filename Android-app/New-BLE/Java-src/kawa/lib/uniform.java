package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.lispexpr.LangObjType;
import gnu.lists.Consumer;
import gnu.lists.F32Vector;
import gnu.lists.F64Vector;
import gnu.lists.LList;
import gnu.lists.S16Vector;
import gnu.lists.S32Vector;
import gnu.lists.S64Vector;
import gnu.lists.S8Vector;
import gnu.lists.Sequence;
import gnu.lists.U16Vector;
import gnu.lists.U32Vector;
import gnu.lists.U64Vector;
import gnu.lists.U8Vector;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.math.IntNum;

/* compiled from: uniform.scm */
public class uniform extends ModuleBody {
    public static final uniform $instance = new uniform();
    static final IntNum Lit0 = IntNum.make(0);
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("s8vector?").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("make-u8vector").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("u8vector").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("u8vector-length").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("u8vector-ref").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("u8vector-set!").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("u8vector->list").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("list->u8vector").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("s16vector?").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("make-s16vector").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("s16vector").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("make-s8vector").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("s16vector-length").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("s16vector-ref").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("s16vector-set!").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("s16vector->list").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("list->s16vector").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("u16vector?").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("make-u16vector").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("u16vector").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("u16vector-length").readResolve());
    static final SimpleSymbol Lit29 = ((SimpleSymbol) new SimpleSymbol("u16vector-ref").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("s8vector").readResolve());
    static final SimpleSymbol Lit30 = ((SimpleSymbol) new SimpleSymbol("u16vector-set!").readResolve());
    static final SimpleSymbol Lit31 = ((SimpleSymbol) new SimpleSymbol("u16vector->list").readResolve());
    static final SimpleSymbol Lit32 = ((SimpleSymbol) new SimpleSymbol("list->u16vector").readResolve());
    static final SimpleSymbol Lit33 = ((SimpleSymbol) new SimpleSymbol("s32vector?").readResolve());
    static final SimpleSymbol Lit34 = ((SimpleSymbol) new SimpleSymbol("make-s32vector").readResolve());
    static final SimpleSymbol Lit35 = ((SimpleSymbol) new SimpleSymbol("s32vector").readResolve());
    static final SimpleSymbol Lit36 = ((SimpleSymbol) new SimpleSymbol("s32vector-length").readResolve());
    static final SimpleSymbol Lit37 = ((SimpleSymbol) new SimpleSymbol("s32vector-ref").readResolve());
    static final SimpleSymbol Lit38 = ((SimpleSymbol) new SimpleSymbol("s32vector-set!").readResolve());
    static final SimpleSymbol Lit39 = ((SimpleSymbol) new SimpleSymbol("s32vector->list").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("s8vector-length").readResolve());
    static final SimpleSymbol Lit40 = ((SimpleSymbol) new SimpleSymbol("list->s32vector").readResolve());
    static final SimpleSymbol Lit41 = ((SimpleSymbol) new SimpleSymbol("u32vector?").readResolve());
    static final SimpleSymbol Lit42 = ((SimpleSymbol) new SimpleSymbol("make-u32vector").readResolve());
    static final SimpleSymbol Lit43 = ((SimpleSymbol) new SimpleSymbol("u32vector").readResolve());
    static final SimpleSymbol Lit44 = ((SimpleSymbol) new SimpleSymbol("u32vector-length").readResolve());
    static final SimpleSymbol Lit45 = ((SimpleSymbol) new SimpleSymbol("u32vector-ref").readResolve());
    static final SimpleSymbol Lit46 = ((SimpleSymbol) new SimpleSymbol("u32vector-set!").readResolve());
    static final SimpleSymbol Lit47 = ((SimpleSymbol) new SimpleSymbol("u32vector->list").readResolve());
    static final SimpleSymbol Lit48 = ((SimpleSymbol) new SimpleSymbol("list->u32vector").readResolve());
    static final SimpleSymbol Lit49 = ((SimpleSymbol) new SimpleSymbol("s64vector?").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("s8vector-ref").readResolve());
    static final SimpleSymbol Lit50 = ((SimpleSymbol) new SimpleSymbol("make-s64vector").readResolve());
    static final SimpleSymbol Lit51 = ((SimpleSymbol) new SimpleSymbol("s64vector").readResolve());
    static final SimpleSymbol Lit52 = ((SimpleSymbol) new SimpleSymbol("s64vector-length").readResolve());
    static final SimpleSymbol Lit53 = ((SimpleSymbol) new SimpleSymbol("s64vector-ref").readResolve());
    static final SimpleSymbol Lit54 = ((SimpleSymbol) new SimpleSymbol("s64vector-set!").readResolve());
    static final SimpleSymbol Lit55 = ((SimpleSymbol) new SimpleSymbol("s64vector->list").readResolve());
    static final SimpleSymbol Lit56 = ((SimpleSymbol) new SimpleSymbol("list->s64vector").readResolve());
    static final SimpleSymbol Lit57 = ((SimpleSymbol) new SimpleSymbol("u64vector?").readResolve());
    static final SimpleSymbol Lit58 = ((SimpleSymbol) new SimpleSymbol("make-u64vector").readResolve());
    static final SimpleSymbol Lit59 = ((SimpleSymbol) new SimpleSymbol("u64vector").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("s8vector-set!").readResolve());
    static final SimpleSymbol Lit60 = ((SimpleSymbol) new SimpleSymbol("u64vector-length").readResolve());
    static final SimpleSymbol Lit61 = ((SimpleSymbol) new SimpleSymbol("u64vector-ref").readResolve());
    static final SimpleSymbol Lit62 = ((SimpleSymbol) new SimpleSymbol("u64vector-set!").readResolve());
    static final SimpleSymbol Lit63 = ((SimpleSymbol) new SimpleSymbol("u64vector->list").readResolve());
    static final SimpleSymbol Lit64 = ((SimpleSymbol) new SimpleSymbol("list->u64vector").readResolve());
    static final SimpleSymbol Lit65 = ((SimpleSymbol) new SimpleSymbol("f32vector?").readResolve());
    static final SimpleSymbol Lit66 = ((SimpleSymbol) new SimpleSymbol("make-f32vector").readResolve());
    static final SimpleSymbol Lit67 = ((SimpleSymbol) new SimpleSymbol("f32vector").readResolve());
    static final SimpleSymbol Lit68 = ((SimpleSymbol) new SimpleSymbol("f32vector-length").readResolve());
    static final SimpleSymbol Lit69 = ((SimpleSymbol) new SimpleSymbol("f32vector-ref").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("s8vector->list").readResolve());
    static final SimpleSymbol Lit70 = ((SimpleSymbol) new SimpleSymbol("f32vector-set!").readResolve());
    static final SimpleSymbol Lit71 = ((SimpleSymbol) new SimpleSymbol("f32vector->list").readResolve());
    static final SimpleSymbol Lit72 = ((SimpleSymbol) new SimpleSymbol("list->f32vector").readResolve());
    static final SimpleSymbol Lit73 = ((SimpleSymbol) new SimpleSymbol("f64vector?").readResolve());
    static final SimpleSymbol Lit74 = ((SimpleSymbol) new SimpleSymbol("make-f64vector").readResolve());
    static final SimpleSymbol Lit75 = ((SimpleSymbol) new SimpleSymbol("f64vector").readResolve());
    static final SimpleSymbol Lit76 = ((SimpleSymbol) new SimpleSymbol("f64vector-length").readResolve());
    static final SimpleSymbol Lit77 = ((SimpleSymbol) new SimpleSymbol("f64vector-ref").readResolve());
    static final SimpleSymbol Lit78 = ((SimpleSymbol) new SimpleSymbol("f64vector-set!").readResolve());
    static final SimpleSymbol Lit79 = ((SimpleSymbol) new SimpleSymbol("f64vector->list").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("list->s8vector").readResolve());
    static final SimpleSymbol Lit80 = ((SimpleSymbol) new SimpleSymbol("list->f64vector").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("u8vector?").readResolve());
    public static final ModuleMethod f32vector;
    public static final ModuleMethod f32vector$Mn$Grlist;
    public static final ModuleMethod f32vector$Mnlength;
    public static final ModuleMethod f32vector$Mnref;
    public static final ModuleMethod f32vector$Mnset$Ex;
    public static final ModuleMethod f32vector$Qu;
    public static final ModuleMethod f64vector;
    public static final ModuleMethod f64vector$Mn$Grlist;
    public static final ModuleMethod f64vector$Mnlength;
    public static final ModuleMethod f64vector$Mnref;
    public static final ModuleMethod f64vector$Mnset$Ex;
    public static final ModuleMethod f64vector$Qu;
    public static final ModuleMethod list$Mn$Grf32vector;
    public static final ModuleMethod list$Mn$Grf64vector;
    public static final ModuleMethod list$Mn$Grs16vector;
    public static final ModuleMethod list$Mn$Grs32vector;
    public static final ModuleMethod list$Mn$Grs64vector;
    public static final ModuleMethod list$Mn$Grs8vector;
    public static final ModuleMethod list$Mn$Gru16vector;
    public static final ModuleMethod list$Mn$Gru32vector;
    public static final ModuleMethod list$Mn$Gru64vector;
    public static final ModuleMethod list$Mn$Gru8vector;
    public static final ModuleMethod make$Mnf32vector;
    public static final ModuleMethod make$Mnf64vector;
    public static final ModuleMethod make$Mns16vector;
    public static final ModuleMethod make$Mns32vector;
    public static final ModuleMethod make$Mns64vector;
    public static final ModuleMethod make$Mns8vector;
    public static final ModuleMethod make$Mnu16vector;
    public static final ModuleMethod make$Mnu32vector;
    public static final ModuleMethod make$Mnu64vector;
    public static final ModuleMethod make$Mnu8vector;
    public static final ModuleMethod s16vector;
    public static final ModuleMethod s16vector$Mn$Grlist;
    public static final ModuleMethod s16vector$Mnlength;
    public static final ModuleMethod s16vector$Mnref;
    public static final ModuleMethod s16vector$Mnset$Ex;
    public static final ModuleMethod s16vector$Qu;
    public static final ModuleMethod s32vector;
    public static final ModuleMethod s32vector$Mn$Grlist;
    public static final ModuleMethod s32vector$Mnlength;
    public static final ModuleMethod s32vector$Mnref;
    public static final ModuleMethod s32vector$Mnset$Ex;
    public static final ModuleMethod s32vector$Qu;
    public static final ModuleMethod s64vector;
    public static final ModuleMethod s64vector$Mn$Grlist;
    public static final ModuleMethod s64vector$Mnlength;
    public static final ModuleMethod s64vector$Mnref;
    public static final ModuleMethod s64vector$Mnset$Ex;
    public static final ModuleMethod s64vector$Qu;
    public static final ModuleMethod s8vector;
    public static final ModuleMethod s8vector$Mn$Grlist;
    public static final ModuleMethod s8vector$Mnlength;
    public static final ModuleMethod s8vector$Mnref;
    public static final ModuleMethod s8vector$Mnset$Ex;
    public static final ModuleMethod s8vector$Qu;
    public static final ModuleMethod u16vector;
    public static final ModuleMethod u16vector$Mn$Grlist;
    public static final ModuleMethod u16vector$Mnlength;
    public static final ModuleMethod u16vector$Mnref;
    public static final ModuleMethod u16vector$Mnset$Ex;
    public static final ModuleMethod u16vector$Qu;
    public static final ModuleMethod u32vector;
    public static final ModuleMethod u32vector$Mn$Grlist;
    public static final ModuleMethod u32vector$Mnlength;
    public static final ModuleMethod u32vector$Mnref;
    public static final ModuleMethod u32vector$Mnset$Ex;
    public static final ModuleMethod u32vector$Qu;
    public static final ModuleMethod u64vector;
    public static final ModuleMethod u64vector$Mn$Grlist;
    public static final ModuleMethod u64vector$Mnlength;
    public static final ModuleMethod u64vector$Mnref;
    public static final ModuleMethod u64vector$Mnset$Ex;
    public static final ModuleMethod u64vector$Qu;
    public static final ModuleMethod u8vector;
    public static final ModuleMethod u8vector$Mn$Grlist;
    public static final ModuleMethod u8vector$Mnlength;
    public static final ModuleMethod u8vector$Mnref;
    public static final ModuleMethod u8vector$Mnset$Ex;
    public static final ModuleMethod u8vector$Qu;

    static {
        ModuleBody moduleBody = $instance;
        s8vector$Qu = new ModuleMethod(moduleBody, 1, Lit1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mns8vector = new ModuleMethod(moduleBody, 2, Lit2, 8193);
        s8vector = new ModuleMethod(moduleBody, 4, Lit3, -4096);
        s8vector$Mnlength = new ModuleMethod(moduleBody, 5, Lit4, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        s8vector$Mnref = new ModuleMethod(moduleBody, 6, Lit5, 8194);
        s8vector$Mnset$Ex = new ModuleMethod(moduleBody, 7, Lit6, 12291);
        s8vector$Mn$Grlist = new ModuleMethod(moduleBody, 8, Lit7, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Grs8vector = new ModuleMethod(moduleBody, 9, Lit8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        u8vector$Qu = new ModuleMethod(moduleBody, 10, Lit9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnu8vector = new ModuleMethod(moduleBody, 11, Lit10, 8193);
        u8vector = new ModuleMethod(moduleBody, 13, Lit11, -4096);
        u8vector$Mnlength = new ModuleMethod(moduleBody, 14, Lit12, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        u8vector$Mnref = new ModuleMethod(moduleBody, 15, Lit13, 8194);
        u8vector$Mnset$Ex = new ModuleMethod(moduleBody, 16, Lit14, 12291);
        u8vector$Mn$Grlist = new ModuleMethod(moduleBody, 17, Lit15, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Gru8vector = new ModuleMethod(moduleBody, 18, Lit16, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        s16vector$Qu = new ModuleMethod(moduleBody, 19, Lit17, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mns16vector = new ModuleMethod(moduleBody, 20, Lit18, 8193);
        s16vector = new ModuleMethod(moduleBody, 22, Lit19, -4096);
        s16vector$Mnlength = new ModuleMethod(moduleBody, 23, Lit20, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        s16vector$Mnref = new ModuleMethod(moduleBody, 24, Lit21, 8194);
        s16vector$Mnset$Ex = new ModuleMethod(moduleBody, 25, Lit22, 12291);
        s16vector$Mn$Grlist = new ModuleMethod(moduleBody, 26, Lit23, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Grs16vector = new ModuleMethod(moduleBody, 27, Lit24, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        u16vector$Qu = new ModuleMethod(moduleBody, 28, Lit25, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnu16vector = new ModuleMethod(moduleBody, 29, Lit26, 8193);
        u16vector = new ModuleMethod(moduleBody, 31, Lit27, -4096);
        u16vector$Mnlength = new ModuleMethod(moduleBody, 32, Lit28, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        u16vector$Mnref = new ModuleMethod(moduleBody, 33, Lit29, 8194);
        u16vector$Mnset$Ex = new ModuleMethod(moduleBody, 34, Lit30, 12291);
        u16vector$Mn$Grlist = new ModuleMethod(moduleBody, 35, Lit31, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Gru16vector = new ModuleMethod(moduleBody, 36, Lit32, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        s32vector$Qu = new ModuleMethod(moduleBody, 37, Lit33, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mns32vector = new ModuleMethod(moduleBody, 38, Lit34, 8193);
        s32vector = new ModuleMethod(moduleBody, 40, Lit35, -4096);
        s32vector$Mnlength = new ModuleMethod(moduleBody, 41, Lit36, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        s32vector$Mnref = new ModuleMethod(moduleBody, 42, Lit37, 8194);
        s32vector$Mnset$Ex = new ModuleMethod(moduleBody, 43, Lit38, 12291);
        s32vector$Mn$Grlist = new ModuleMethod(moduleBody, 44, Lit39, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Grs32vector = new ModuleMethod(moduleBody, 45, Lit40, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        u32vector$Qu = new ModuleMethod(moduleBody, 46, Lit41, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnu32vector = new ModuleMethod(moduleBody, 47, Lit42, 8193);
        u32vector = new ModuleMethod(moduleBody, 49, Lit43, -4096);
        u32vector$Mnlength = new ModuleMethod(moduleBody, 50, Lit44, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        u32vector$Mnref = new ModuleMethod(moduleBody, 51, Lit45, 8194);
        u32vector$Mnset$Ex = new ModuleMethod(moduleBody, 52, Lit46, 12291);
        u32vector$Mn$Grlist = new ModuleMethod(moduleBody, 53, Lit47, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Gru32vector = new ModuleMethod(moduleBody, 54, Lit48, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        s64vector$Qu = new ModuleMethod(moduleBody, 55, Lit49, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mns64vector = new ModuleMethod(moduleBody, 56, Lit50, 8193);
        s64vector = new ModuleMethod(moduleBody, 58, Lit51, -4096);
        s64vector$Mnlength = new ModuleMethod(moduleBody, 59, Lit52, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        s64vector$Mnref = new ModuleMethod(moduleBody, 60, Lit53, 8194);
        s64vector$Mnset$Ex = new ModuleMethod(moduleBody, 61, Lit54, 12291);
        s64vector$Mn$Grlist = new ModuleMethod(moduleBody, 62, Lit55, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Grs64vector = new ModuleMethod(moduleBody, 63, Lit56, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        u64vector$Qu = new ModuleMethod(moduleBody, 64, Lit57, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnu64vector = new ModuleMethod(moduleBody, 65, Lit58, 8193);
        u64vector = new ModuleMethod(moduleBody, 67, Lit59, -4096);
        u64vector$Mnlength = new ModuleMethod(moduleBody, 68, Lit60, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        u64vector$Mnref = new ModuleMethod(moduleBody, 69, Lit61, 8194);
        u64vector$Mnset$Ex = new ModuleMethod(moduleBody, 70, Lit62, 12291);
        u64vector$Mn$Grlist = new ModuleMethod(moduleBody, 71, Lit63, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Gru64vector = new ModuleMethod(moduleBody, 72, Lit64, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        f32vector$Qu = new ModuleMethod(moduleBody, 73, Lit65, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnf32vector = new ModuleMethod(moduleBody, 74, Lit66, 8193);
        f32vector = new ModuleMethod(moduleBody, 76, Lit67, -4096);
        f32vector$Mnlength = new ModuleMethod(moduleBody, 77, Lit68, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        f32vector$Mnref = new ModuleMethod(moduleBody, 78, Lit69, 8194);
        f32vector$Mnset$Ex = new ModuleMethod(moduleBody, 79, Lit70, 12291);
        f32vector$Mn$Grlist = new ModuleMethod(moduleBody, 80, Lit71, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Grf32vector = new ModuleMethod(moduleBody, 81, Lit72, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        f64vector$Qu = new ModuleMethod(moduleBody, 82, Lit73, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        make$Mnf64vector = new ModuleMethod(moduleBody, 83, Lit74, 8193);
        f64vector = new ModuleMethod(moduleBody, 85, Lit75, -4096);
        f64vector$Mnlength = new ModuleMethod(moduleBody, 86, Lit76, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        f64vector$Mnref = new ModuleMethod(moduleBody, 87, Lit77, 8194);
        f64vector$Mnset$Ex = new ModuleMethod(moduleBody, 88, Lit78, 12291);
        f64vector$Mn$Grlist = new ModuleMethod(moduleBody, 89, Lit79, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        list$Mn$Grf64vector = new ModuleMethod(moduleBody, 90, Lit80, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public uniform() {
        ModuleInfo.register(this);
    }

    public static F32Vector makeF32vector(int i) {
        return makeF32vector(i, 0.0f);
    }

    public static F64Vector makeF64vector(int i) {
        return makeF64vector(i, 0.0d);
    }

    public static S16Vector makeS16vector(int i) {
        return makeS16vector(i, 0);
    }

    public static S32Vector makeS32vector(int i) {
        return makeS32vector(i, 0);
    }

    public static S64Vector makeS64vector(int i) {
        return makeS64vector(i, 0);
    }

    public static S8Vector makeS8vector(int i) {
        return makeS8vector(i, 0);
    }

    public static U16Vector makeU16vector(int i) {
        return makeU16vector(i, 0);
    }

    public static U32Vector makeU32vector(int i) {
        return makeU32vector(i, 0);
    }

    public static U64Vector makeU64vector(int i) {
        return makeU64vector(i, Lit0);
    }

    public static U8Vector makeU8vector(int i) {
        return makeU8vector(i, 0);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static boolean isS8vector(Object x) {
        return x instanceof S8Vector;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                if (!(obj instanceof S8Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 8:
                if (!(obj instanceof S8Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 9:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 11:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 14:
                if (!(obj instanceof U8Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 17:
                if (!(obj instanceof U8Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 18:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 19:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 20:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 23:
                if (!(obj instanceof S16Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 26:
                if (!(obj instanceof S16Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 27:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 32:
                if (!(obj instanceof U16Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 35:
                if (!(obj instanceof U16Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 36:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 37:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 38:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 41:
                if (!(obj instanceof S32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 44:
                if (!(obj instanceof S32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 45:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 46:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 50:
                if (!(obj instanceof U32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 53:
                if (!(obj instanceof U32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 54:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 55:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 56:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 59:
                if (!(obj instanceof S64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 62:
                if (!(obj instanceof S64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 63:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 64:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 65:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 68:
                if (!(obj instanceof U64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 71:
                if (!(obj instanceof U64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 72:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 73:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 74:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 77:
                if (!(obj instanceof F32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 80:
                if (!(obj instanceof F32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 81:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 82:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 83:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 86:
                if (!(obj instanceof F64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 89:
                if (!(obj instanceof F64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 90:
                if (!(obj instanceof LList)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static S8Vector makeS8vector(int n, int init) {
        return new S8Vector(n, (byte) init);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 2:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 6:
                if (!(obj instanceof S8Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 11:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 15:
                if (!(obj instanceof U8Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 20:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 24:
                if (!(obj instanceof S16Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 29:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 33:
                if (!(obj instanceof U16Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 38:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 42:
                if (!(obj instanceof S32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 47:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 51:
                if (!(obj instanceof U32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 56:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 60:
                if (!(obj instanceof S64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 65:
                callContext.value1 = obj;
                if (IntNum.asIntNumOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 69:
                if (!(obj instanceof U64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 74:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 78:
                if (!(obj instanceof F32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 83:
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 87:
                if (!(obj instanceof F64Vector)) {
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

    public static S8Vector s8vector$V(Object[] argsArray) {
        return list$To$S8vector(LList.makeList(argsArray, 0));
    }

    public int matchN(ModuleMethod moduleMethod, Object[] objArr, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 4:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 13:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 22:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 31:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 40:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 49:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 58:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 67:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 76:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            case 85:
                callContext.values = objArr;
                callContext.proc = moduleMethod;
                callContext.pc = 5;
                return 0;
            default:
                return super.matchN(moduleMethod, objArr, callContext);
        }
    }

    public static int s8vectorLength(S8Vector v) {
        return v.size();
    }

    public static int s8vectorRef(S8Vector v, int i) {
        return v.intAt(i);
    }

    public static void s8vectorSet$Ex(S8Vector v, int i, int x) {
        v.setByteAt(i, (byte) x);
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 7:
                if (!(obj instanceof S8Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 16:
                if (!(obj instanceof U8Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 25:
                if (!(obj instanceof S16Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 34:
                if (!(obj instanceof U16Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 43:
                if (!(obj instanceof S32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 52:
                if (!(obj instanceof U32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 61:
                if (!(obj instanceof S64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 70:
                if (!(obj instanceof U64Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                if (IntNum.asIntNumOrNull(obj3) == null) {
                    return -786429;
                }
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 79:
                if (!(obj instanceof F32Vector)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case 88:
                if (!(obj instanceof F64Vector)) {
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

    public static LList s8vector$To$List(S8Vector v) {
        return LList.makeList(v);
    }

    public static S8Vector list$To$S8vector(LList l) {
        return new S8Vector((Sequence) l);
    }

    public static boolean isU8vector(Object x) {
        return x instanceof U8Vector;
    }

    public static U8Vector makeU8vector(int n, int init) {
        return new U8Vector(n, (byte) init);
    }

    public static U8Vector u8vector$V(Object[] argsArray) {
        return list$To$U8vector(LList.makeList(argsArray, 0));
    }

    public static int u8vectorLength(U8Vector v) {
        return v.size();
    }

    public static int u8vectorRef(U8Vector v, int i) {
        return v.intAt(i);
    }

    public static void u8vectorSet$Ex(U8Vector v, int i, int x) {
        v.setByteAt(i, (byte) x);
    }

    public static LList u8vector$To$List(U8Vector v) {
        return LList.makeList(v);
    }

    public static U8Vector list$To$U8vector(LList l) {
        return new U8Vector((Sequence) l);
    }

    public static boolean isS16vector(Object x) {
        return x instanceof S16Vector;
    }

    public static S16Vector makeS16vector(int n, int init) {
        return new S16Vector(n, (short) init);
    }

    public static S16Vector s16vector$V(Object[] argsArray) {
        return list$To$S16vector(LList.makeList(argsArray, 0));
    }

    public static int s16vectorLength(S16Vector v) {
        return v.size();
    }

    public static int s16vectorRef(S16Vector v, int i) {
        return v.intAt(i);
    }

    public static void s16vectorSet$Ex(S16Vector v, int i, int x) {
        v.setShortAt(i, (short) x);
    }

    public static LList s16vector$To$List(S16Vector v) {
        return LList.makeList(v);
    }

    public static S16Vector list$To$S16vector(LList l) {
        return new S16Vector((Sequence) l);
    }

    public static boolean isU16vector(Object x) {
        return x instanceof U16Vector;
    }

    public static U16Vector makeU16vector(int n, int init) {
        return new U16Vector(n, (short) init);
    }

    public static U16Vector u16vector$V(Object[] argsArray) {
        return list$To$U16vector(LList.makeList(argsArray, 0));
    }

    public static int u16vectorLength(U16Vector v) {
        return v.size();
    }

    public static int u16vectorRef(U16Vector v, int i) {
        return v.intAt(i);
    }

    public static void u16vectorSet$Ex(U16Vector v, int i, int x) {
        v.setShortAt(i, (short) x);
    }

    public static LList u16vector$To$List(U16Vector v) {
        return LList.makeList(v);
    }

    public static U16Vector list$To$U16vector(LList l) {
        return new U16Vector((Sequence) l);
    }

    public static boolean isS32vector(Object x) {
        return x instanceof S32Vector;
    }

    public static S32Vector makeS32vector(int n, int init) {
        return new S32Vector(n, init);
    }

    public static S32Vector s32vector$V(Object[] argsArray) {
        return list$To$S32vector(LList.makeList(argsArray, 0));
    }

    public static int s32vectorLength(S32Vector v) {
        return v.size();
    }

    public static int s32vectorRef(S32Vector v, int i) {
        return v.intAt(i);
    }

    public static void s32vectorSet$Ex(S32Vector v, int i, int x) {
        v.setIntAt(i, x);
    }

    public static LList s32vector$To$List(S32Vector v) {
        return LList.makeList(v);
    }

    public static S32Vector list$To$S32vector(LList l) {
        return new S32Vector((Sequence) l);
    }

    public static boolean isU32vector(Object x) {
        return x instanceof U32Vector;
    }

    public static U32Vector makeU32vector(int n, long init) {
        return new U32Vector(n, (int) init);
    }

    public static U32Vector u32vector$V(Object[] argsArray) {
        return list$To$U32vector(LList.makeList(argsArray, 0));
    }

    public static int u32vectorLength(U32Vector v) {
        return v.size();
    }

    public static long u32vectorRef(U32Vector v, int i) {
        return ((Number) v.get(i)).longValue();
    }

    public static void u32vectorSet$Ex(U32Vector v, int i, long x) {
        v.setIntAt(i, (int) x);
    }

    public static LList u32vector$To$List(U32Vector v) {
        return LList.makeList(v);
    }

    public static U32Vector list$To$U32vector(LList l) {
        return new U32Vector((Sequence) l);
    }

    public static boolean isS64vector(Object x) {
        return x instanceof S64Vector;
    }

    public static S64Vector makeS64vector(int n, long init) {
        return new S64Vector(n, init);
    }

    public static S64Vector s64vector$V(Object[] argsArray) {
        return list$To$S64vector(LList.makeList(argsArray, 0));
    }

    public static int s64vectorLength(S64Vector v) {
        return v.size();
    }

    public static long s64vectorRef(S64Vector v, int i) {
        return v.longAt(i);
    }

    public static void s64vectorSet$Ex(S64Vector v, int i, long x) {
        v.setLongAt(i, x);
    }

    public static LList s64vector$To$List(S64Vector v) {
        return LList.makeList(v);
    }

    public static S64Vector list$To$S64vector(LList l) {
        return new S64Vector((Sequence) l);
    }

    public static boolean isU64vector(Object x) {
        return x instanceof U64Vector;
    }

    public static U64Vector makeU64vector(int n, IntNum init) {
        try {
            return new U64Vector(n, init.longValue());
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.lists.U64Vector.<init>(int,long)", 2, (Object) init);
        }
    }

    public static U64Vector u64vector$V(Object[] argsArray) {
        return list$To$U64vector(LList.makeList(argsArray, 0));
    }

    public static int u64vectorLength(U64Vector v) {
        return v.size();
    }

    public static IntNum u64vectorRef(U64Vector v, int i) {
        return LangObjType.coerceIntNum(v.get(i));
    }

    public static void u64vectorSet$Ex(U64Vector v, int i, IntNum x) {
        try {
            v.setLongAt(i, x.longValue());
        } catch (ClassCastException e) {
            throw new WrongType(e, "gnu.lists.U64Vector.setLongAt(int,long)", 3, (Object) x);
        }
    }

    public static LList u64vector$To$List(U64Vector v) {
        return LList.makeList(v);
    }

    public static U64Vector list$To$U64vector(LList l) {
        return new U64Vector((Sequence) l);
    }

    public static boolean isF32vector(Object x) {
        return x instanceof F32Vector;
    }

    public static F32Vector makeF32vector(int n, float init) {
        return new F32Vector(n, init);
    }

    public static F32Vector f32vector$V(Object[] argsArray) {
        return list$To$F32vector(LList.makeList(argsArray, 0));
    }

    public static int f32vectorLength(F32Vector v) {
        return v.size();
    }

    public static float f32vectorRef(F32Vector v, int i) {
        return v.floatAt(i);
    }

    public static void f32vectorSet$Ex(F32Vector v, int i, float x) {
        v.setFloatAt(i, x);
    }

    public static LList f32vector$To$List(F32Vector v) {
        return LList.makeList(v);
    }

    public static F32Vector list$To$F32vector(LList l) {
        return new F32Vector((Sequence) l);
    }

    public static boolean isF64vector(Object x) {
        return x instanceof F64Vector;
    }

    public static F64Vector makeF64vector(int n, double init) {
        return new F64Vector(n, init);
    }

    public static F64Vector f64vector$V(Object[] argsArray) {
        return list$To$F64vector(LList.makeList(argsArray, 0));
    }

    public Object applyN(ModuleMethod moduleMethod, Object[] objArr) {
        switch (moduleMethod.selector) {
            case 4:
                return s8vector$V(objArr);
            case 13:
                return u8vector$V(objArr);
            case 22:
                return s16vector$V(objArr);
            case 31:
                return u16vector$V(objArr);
            case 40:
                return s32vector$V(objArr);
            case 49:
                return u32vector$V(objArr);
            case 58:
                return s64vector$V(objArr);
            case 67:
                return u64vector$V(objArr);
            case 76:
                return f32vector$V(objArr);
            case 85:
                return f64vector$V(objArr);
            default:
                return super.applyN(moduleMethod, objArr);
        }
    }

    public static int f64vectorLength(F64Vector v) {
        return v.size();
    }

    public static double f64vectorRef(F64Vector v, int i) {
        return v.doubleAt(i);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 2:
                try {
                    try {
                        return makeS8vector(((Number) obj).intValue(), ((Number) obj2).intValue());
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "make-s8vector", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "make-s8vector", 1, obj);
                }
            case 6:
                try {
                    try {
                        return Integer.valueOf(s8vectorRef((S8Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "s8vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "s8vector-ref", 1, obj);
                }
            case 11:
                try {
                    try {
                        return makeU8vector(((Number) obj).intValue(), ((Number) obj2).intValue());
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "make-u8vector", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "make-u8vector", 1, obj);
                }
            case 15:
                try {
                    try {
                        return Integer.valueOf(u8vectorRef((U8Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "u8vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "u8vector-ref", 1, obj);
                }
            case 20:
                try {
                    try {
                        return makeS16vector(((Number) obj).intValue(), ((Number) obj2).intValue());
                    } catch (ClassCastException e22222222) {
                        throw new WrongType(e22222222, "make-s16vector", 2, obj2);
                    }
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "make-s16vector", 1, obj);
                }
            case 24:
                try {
                    try {
                        return Integer.valueOf(s16vectorRef((S16Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e2222222222) {
                        throw new WrongType(e2222222222, "s16vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "s16vector-ref", 1, obj);
                }
            case 29:
                try {
                    try {
                        return makeU16vector(((Number) obj).intValue(), ((Number) obj2).intValue());
                    } catch (ClassCastException e222222222222) {
                        throw new WrongType(e222222222222, "make-u16vector", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "make-u16vector", 1, obj);
                }
            case 33:
                try {
                    try {
                        return Integer.valueOf(u16vectorRef((U16Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e22222222222222) {
                        throw new WrongType(e22222222222222, "u16vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "u16vector-ref", 1, obj);
                }
            case 38:
                try {
                    try {
                        return makeS32vector(((Number) obj).intValue(), ((Number) obj2).intValue());
                    } catch (ClassCastException e2222222222222222) {
                        throw new WrongType(e2222222222222222, "make-s32vector", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "make-s32vector", 1, obj);
                }
            case 42:
                try {
                    try {
                        return Integer.valueOf(s32vectorRef((S32Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e222222222222222222) {
                        throw new WrongType(e222222222222222222, "s32vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222222222) {
                    throw new WrongType(e2222222222222222222, "s32vector-ref", 1, obj);
                }
            case 47:
                try {
                    try {
                        return makeU32vector(((Number) obj).intValue(), ((Number) obj2).longValue());
                    } catch (ClassCastException e22222222222222222222) {
                        throw new WrongType(e22222222222222222222, "make-u32vector", 2, obj2);
                    }
                } catch (ClassCastException e222222222222222222222) {
                    throw new WrongType(e222222222222222222222, "make-u32vector", 1, obj);
                }
            case 51:
                try {
                    try {
                        return Long.valueOf(u32vectorRef((U32Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e2222222222222222222222) {
                        throw new WrongType(e2222222222222222222222, "u32vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222222222) {
                    throw new WrongType(e22222222222222222222222, "u32vector-ref", 1, obj);
                }
            case 56:
                try {
                    try {
                        return makeS64vector(((Number) obj).intValue(), ((Number) obj2).longValue());
                    } catch (ClassCastException e222222222222222222222222) {
                        throw new WrongType(e222222222222222222222222, "make-s64vector", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222, "make-s64vector", 1, obj);
                }
            case 60:
                try {
                    try {
                        return Long.valueOf(s64vectorRef((S64Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e22222222222222222222222222) {
                        throw new WrongType(e22222222222222222222222222, "s64vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222, "s64vector-ref", 1, obj);
                }
            case 65:
                try {
                    try {
                        return makeU64vector(((Number) obj).intValue(), LangObjType.coerceIntNum(obj2));
                    } catch (ClassCastException e2222222222222222222222222222) {
                        throw new WrongType(e2222222222222222222222222222, "make-u64vector", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222, "make-u64vector", 1, obj);
                }
            case 69:
                try {
                    try {
                        return u64vectorRef((U64Vector) obj, ((Number) obj2).intValue());
                    } catch (ClassCastException e222222222222222222222222222222) {
                        throw new WrongType(e222222222222222222222222222222, "u64vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222222, "u64vector-ref", 1, obj);
                }
            case 74:
                try {
                    try {
                        return makeF32vector(((Number) obj).intValue(), ((Number) obj2).floatValue());
                    } catch (ClassCastException e22222222222222222222222222222222) {
                        throw new WrongType(e22222222222222222222222222222222, "make-f32vector", 2, obj2);
                    }
                } catch (ClassCastException e222222222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222222222, "make-f32vector", 1, obj);
                }
            case 78:
                try {
                    try {
                        return Float.valueOf(f32vectorRef((F32Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e2222222222222222222222222222222222) {
                        throw new WrongType(e2222222222222222222222222222222222, "f32vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222222222, "f32vector-ref", 1, obj);
                }
            case 83:
                try {
                    try {
                        return makeF64vector(((Number) obj).intValue(), ((Number) obj2).doubleValue());
                    } catch (ClassCastException e222222222222222222222222222222222222) {
                        throw new WrongType(e222222222222222222222222222222222222, "make-f64vector", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222222222222, "make-f64vector", 1, obj);
                }
            case 87:
                try {
                    try {
                        return Double.valueOf(f64vectorRef((F64Vector) obj, ((Number) obj2).intValue()));
                    } catch (ClassCastException e22222222222222222222222222222222222222) {
                        throw new WrongType(e22222222222222222222222222222222222222, "f64vector-ref", 2, obj2);
                    }
                } catch (ClassCastException e222222222222222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222222222222222, "f64vector-ref", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static void f64vectorSet$Ex(F64Vector v, int i, double x) {
        v.setDoubleAt(i, x);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case 7:
                try {
                    try {
                        try {
                            s8vectorSet$Ex((S8Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                            return Values.empty;
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "s8vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "s8vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "s8vector-set!", 1, obj);
                }
            case 16:
                try {
                    try {
                        try {
                            u8vectorSet$Ex((U8Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                            return Values.empty;
                        } catch (ClassCastException e222) {
                            throw new WrongType(e222, "u8vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "u8vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "u8vector-set!", 1, obj);
                }
            case 25:
                try {
                    try {
                        try {
                            s16vectorSet$Ex((S16Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                            return Values.empty;
                        } catch (ClassCastException e222222) {
                            throw new WrongType(e222222, "s16vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222222) {
                        throw new WrongType(e2222222, "s16vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "s16vector-set!", 1, obj);
                }
            case 34:
                try {
                    try {
                        try {
                            u16vectorSet$Ex((U16Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                            return Values.empty;
                        } catch (ClassCastException e222222222) {
                            throw new WrongType(e222222222, "u16vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222) {
                        throw new WrongType(e2222222222, "u16vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "u16vector-set!", 1, obj);
                }
            case 43:
                try {
                    try {
                        try {
                            s32vectorSet$Ex((S32Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).intValue());
                            return Values.empty;
                        } catch (ClassCastException e222222222222) {
                            throw new WrongType(e222222222222, "s32vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222222) {
                        throw new WrongType(e2222222222222, "s32vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "s32vector-set!", 1, obj);
                }
            case 52:
                try {
                    try {
                        try {
                            u32vectorSet$Ex((U32Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).longValue());
                            return Values.empty;
                        } catch (ClassCastException e222222222222222) {
                            throw new WrongType(e222222222222222, "u32vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222222222) {
                        throw new WrongType(e2222222222222222, "u32vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "u32vector-set!", 1, obj);
                }
            case 61:
                try {
                    try {
                        try {
                            s64vectorSet$Ex((S64Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).longValue());
                            return Values.empty;
                        } catch (ClassCastException e222222222222222222) {
                            throw new WrongType(e222222222222222222, "s64vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222222222222) {
                        throw new WrongType(e2222222222222222222, "s64vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222222) {
                    throw new WrongType(e22222222222222222222, "s64vector-set!", 1, obj);
                }
            case 70:
                try {
                    try {
                        try {
                            u64vectorSet$Ex((U64Vector) obj, ((Number) obj2).intValue(), LangObjType.coerceIntNum(obj3));
                            return Values.empty;
                        } catch (ClassCastException e222222222222222222222) {
                            throw new WrongType(e222222222222222222222, "u64vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222222222222222) {
                        throw new WrongType(e2222222222222222222222, "u64vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222222222) {
                    throw new WrongType(e22222222222222222222222, "u64vector-set!", 1, obj);
                }
            case 79:
                try {
                    try {
                        try {
                            f32vectorSet$Ex((F32Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).floatValue());
                            return Values.empty;
                        } catch (ClassCastException e222222222222222222222222) {
                            throw new WrongType(e222222222222222222222222, "f32vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222222222222222222) {
                        throw new WrongType(e2222222222222222222222222, "f32vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222, "f32vector-set!", 1, obj);
                }
            case 88:
                try {
                    try {
                        try {
                            f64vectorSet$Ex((F64Vector) obj, ((Number) obj2).intValue(), ((Number) obj3).doubleValue());
                            return Values.empty;
                        } catch (ClassCastException e222222222222222222222222222) {
                            throw new WrongType(e222222222222222222222222222, "f64vector-set!", 3, obj3);
                        }
                    } catch (ClassCastException e2222222222222222222222222222) {
                        throw new WrongType(e2222222222222222222222222222, "f64vector-set!", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222, "f64vector-set!", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static LList f64vector$To$List(F64Vector v) {
        return LList.makeList(v);
    }

    public static F64Vector list$To$F64vector(LList l) {
        return new F64Vector((Sequence) l);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return isS8vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 2:
                try {
                    return makeS8vector(((Number) obj).intValue());
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-s8vector", 1, obj);
                }
            case 5:
                try {
                    return Integer.valueOf(s8vectorLength((S8Vector) obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "s8vector-length", 1, obj);
                }
            case 8:
                try {
                    return s8vector$To$List((S8Vector) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "s8vector->list", 1, obj);
                }
            case 9:
                try {
                    return list$To$S8vector((LList) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "list->s8vector", 1, obj);
                }
            case 10:
                return isU8vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 11:
                try {
                    return makeU8vector(((Number) obj).intValue());
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "make-u8vector", 1, obj);
                }
            case 14:
                try {
                    return Integer.valueOf(u8vectorLength((U8Vector) obj));
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "u8vector-length", 1, obj);
                }
            case 17:
                try {
                    return u8vector$To$List((U8Vector) obj);
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "u8vector->list", 1, obj);
                }
            case 18:
                try {
                    return list$To$U8vector((LList) obj);
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "list->u8vector", 1, obj);
                }
            case 19:
                return isS16vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 20:
                try {
                    return makeS16vector(((Number) obj).intValue());
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "make-s16vector", 1, obj);
                }
            case 23:
                try {
                    return Integer.valueOf(s16vectorLength((S16Vector) obj));
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "s16vector-length", 1, obj);
                }
            case 26:
                try {
                    return s16vector$To$List((S16Vector) obj);
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "s16vector->list", 1, obj);
                }
            case 27:
                try {
                    return list$To$S16vector((LList) obj);
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "list->s16vector", 1, obj);
                }
            case 28:
                return isU16vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 29:
                try {
                    return makeU16vector(((Number) obj).intValue());
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "make-u16vector", 1, obj);
                }
            case 32:
                try {
                    return Integer.valueOf(u16vectorLength((U16Vector) obj));
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "u16vector-length", 1, obj);
                }
            case 35:
                try {
                    return u16vector$To$List((U16Vector) obj);
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "u16vector->list", 1, obj);
                }
            case 36:
                try {
                    return list$To$U16vector((LList) obj);
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "list->u16vector", 1, obj);
                }
            case 37:
                return isS32vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 38:
                try {
                    return makeS32vector(((Number) obj).intValue());
                } catch (ClassCastException e2222222222222222) {
                    throw new WrongType(e2222222222222222, "make-s32vector", 1, obj);
                }
            case 41:
                try {
                    return Integer.valueOf(s32vectorLength((S32Vector) obj));
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "s32vector-length", 1, obj);
                }
            case 44:
                try {
                    return s32vector$To$List((S32Vector) obj);
                } catch (ClassCastException e222222222222222222) {
                    throw new WrongType(e222222222222222222, "s32vector->list", 1, obj);
                }
            case 45:
                try {
                    return list$To$S32vector((LList) obj);
                } catch (ClassCastException e2222222222222222222) {
                    throw new WrongType(e2222222222222222222, "list->s32vector", 1, obj);
                }
            case 46:
                return isU32vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 47:
                try {
                    return makeU32vector(((Number) obj).intValue());
                } catch (ClassCastException e22222222222222222222) {
                    throw new WrongType(e22222222222222222222, "make-u32vector", 1, obj);
                }
            case 50:
                try {
                    return Integer.valueOf(u32vectorLength((U32Vector) obj));
                } catch (ClassCastException e222222222222222222222) {
                    throw new WrongType(e222222222222222222222, "u32vector-length", 1, obj);
                }
            case 53:
                try {
                    return u32vector$To$List((U32Vector) obj);
                } catch (ClassCastException e2222222222222222222222) {
                    throw new WrongType(e2222222222222222222222, "u32vector->list", 1, obj);
                }
            case 54:
                try {
                    return list$To$U32vector((LList) obj);
                } catch (ClassCastException e22222222222222222222222) {
                    throw new WrongType(e22222222222222222222222, "list->u32vector", 1, obj);
                }
            case 55:
                return isS64vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 56:
                try {
                    return makeS64vector(((Number) obj).intValue());
                } catch (ClassCastException e222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222, "make-s64vector", 1, obj);
                }
            case 59:
                try {
                    return Integer.valueOf(s64vectorLength((S64Vector) obj));
                } catch (ClassCastException e2222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222, "s64vector-length", 1, obj);
                }
            case 62:
                try {
                    return s64vector$To$List((S64Vector) obj);
                } catch (ClassCastException e22222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222, "s64vector->list", 1, obj);
                }
            case 63:
                try {
                    return list$To$S64vector((LList) obj);
                } catch (ClassCastException e222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222, "list->s64vector", 1, obj);
                }
            case 64:
                return isU64vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 65:
                try {
                    return makeU64vector(((Number) obj).intValue());
                } catch (ClassCastException e2222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222, "make-u64vector", 1, obj);
                }
            case 68:
                try {
                    return Integer.valueOf(u64vectorLength((U64Vector) obj));
                } catch (ClassCastException e22222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222, "u64vector-length", 1, obj);
                }
            case 71:
                try {
                    return u64vector$To$List((U64Vector) obj);
                } catch (ClassCastException e222222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222222, "u64vector->list", 1, obj);
                }
            case 72:
                try {
                    return list$To$U64vector((LList) obj);
                } catch (ClassCastException e2222222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222222, "list->u64vector", 1, obj);
                }
            case 73:
                return isF32vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 74:
                try {
                    return makeF32vector(((Number) obj).intValue());
                } catch (ClassCastException e22222222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222222, "make-f32vector", 1, obj);
                }
            case 77:
                try {
                    return Integer.valueOf(f32vectorLength((F32Vector) obj));
                } catch (ClassCastException e222222222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222222222, "f32vector-length", 1, obj);
                }
            case 80:
                try {
                    return f32vector$To$List((F32Vector) obj);
                } catch (ClassCastException e2222222222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222222222, "f32vector->list", 1, obj);
                }
            case 81:
                try {
                    return list$To$F32vector((LList) obj);
                } catch (ClassCastException e22222222222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222222222, "list->f32vector", 1, obj);
                }
            case 82:
                return isF64vector(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 83:
                try {
                    return makeF64vector(((Number) obj).intValue());
                } catch (ClassCastException e222222222222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222222222222, "make-f64vector", 1, obj);
                }
            case 86:
                try {
                    return Integer.valueOf(f64vectorLength((F64Vector) obj));
                } catch (ClassCastException e2222222222222222222222222222222222222) {
                    throw new WrongType(e2222222222222222222222222222222222222, "f64vector-length", 1, obj);
                }
            case 89:
                try {
                    return f64vector$To$List((F64Vector) obj);
                } catch (ClassCastException e22222222222222222222222222222222222222) {
                    throw new WrongType(e22222222222222222222222222222222222222, "f64vector->list", 1, obj);
                }
            case 90:
                try {
                    return list$To$F64vector((LList) obj);
                } catch (ClassCastException e222222222222222222222222222222222222222) {
                    throw new WrongType(e222222222222222222222222222222222222222, "list->f64vector", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}

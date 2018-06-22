package kawa.lib.rnrs;

import android.support.v4.app.FragmentTransaction;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.kawa.functions.UnicodeUtils;
import gnu.lists.Consumer;
import gnu.lists.FString;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.WrongType;
import gnu.text.Char;
import java.util.Locale;
import kawa.lib.misc;

/* compiled from: unicode.scm */
public class unicode extends ModuleBody {
    public static final unicode $instance = new unicode();
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("char-upcase").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("char-downcase").readResolve());
    static final SimpleSymbol Lit10 = ((SimpleSymbol) new SimpleSymbol("char-ci=?").readResolve());
    static final SimpleSymbol Lit11 = ((SimpleSymbol) new SimpleSymbol("char-ci<?").readResolve());
    static final SimpleSymbol Lit12 = ((SimpleSymbol) new SimpleSymbol("char-ci>?").readResolve());
    static final SimpleSymbol Lit13 = ((SimpleSymbol) new SimpleSymbol("char-ci<=?").readResolve());
    static final SimpleSymbol Lit14 = ((SimpleSymbol) new SimpleSymbol("char-ci>=?").readResolve());
    static final SimpleSymbol Lit15 = ((SimpleSymbol) new SimpleSymbol("char-general-category").readResolve());
    static final SimpleSymbol Lit16 = ((SimpleSymbol) new SimpleSymbol("string-upcase").readResolve());
    static final SimpleSymbol Lit17 = ((SimpleSymbol) new SimpleSymbol("string-downcase").readResolve());
    static final SimpleSymbol Lit18 = ((SimpleSymbol) new SimpleSymbol("string-titlecase").readResolve());
    static final SimpleSymbol Lit19 = ((SimpleSymbol) new SimpleSymbol("string-foldcase").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("char-titlecase").readResolve());
    static final SimpleSymbol Lit20 = ((SimpleSymbol) new SimpleSymbol("string-ci=?").readResolve());
    static final SimpleSymbol Lit21 = ((SimpleSymbol) new SimpleSymbol("string-ci<?").readResolve());
    static final SimpleSymbol Lit22 = ((SimpleSymbol) new SimpleSymbol("string-ci>?").readResolve());
    static final SimpleSymbol Lit23 = ((SimpleSymbol) new SimpleSymbol("string-ci<=?").readResolve());
    static final SimpleSymbol Lit24 = ((SimpleSymbol) new SimpleSymbol("string-ci>=?").readResolve());
    static final SimpleSymbol Lit25 = ((SimpleSymbol) new SimpleSymbol("string-normalize-nfd").readResolve());
    static final SimpleSymbol Lit26 = ((SimpleSymbol) new SimpleSymbol("string-normalize-nfkd").readResolve());
    static final SimpleSymbol Lit27 = ((SimpleSymbol) new SimpleSymbol("string-normalize-nfc").readResolve());
    static final SimpleSymbol Lit28 = ((SimpleSymbol) new SimpleSymbol("string-normalize-nfkc").readResolve());
    static final SimpleSymbol Lit3 = ((SimpleSymbol) new SimpleSymbol("char-alphabetic?").readResolve());
    static final SimpleSymbol Lit4 = ((SimpleSymbol) new SimpleSymbol("char-numeric?").readResolve());
    static final SimpleSymbol Lit5 = ((SimpleSymbol) new SimpleSymbol("char-whitespace?").readResolve());
    static final SimpleSymbol Lit6 = ((SimpleSymbol) new SimpleSymbol("char-upper-case?").readResolve());
    static final SimpleSymbol Lit7 = ((SimpleSymbol) new SimpleSymbol("char-lower-case?").readResolve());
    static final SimpleSymbol Lit8 = ((SimpleSymbol) new SimpleSymbol("char-title-case?").readResolve());
    static final SimpleSymbol Lit9 = ((SimpleSymbol) new SimpleSymbol("char-foldcase").readResolve());
    public static final ModuleMethod char$Mnalphabetic$Qu;
    public static final ModuleMethod char$Mnci$Eq$Qu;
    public static final ModuleMethod char$Mnci$Gr$Eq$Qu;
    public static final ModuleMethod char$Mnci$Gr$Qu;
    public static final ModuleMethod char$Mnci$Ls$Eq$Qu;
    public static final ModuleMethod char$Mnci$Ls$Qu;
    public static final ModuleMethod char$Mndowncase;
    public static final ModuleMethod char$Mnfoldcase;
    public static final ModuleMethod char$Mngeneral$Mncategory;
    public static final ModuleMethod char$Mnlower$Mncase$Qu;
    public static final ModuleMethod char$Mnnumeric$Qu;
    public static final ModuleMethod char$Mntitle$Mncase$Qu;
    public static final ModuleMethod char$Mntitlecase;
    public static final ModuleMethod char$Mnupcase;
    public static final ModuleMethod char$Mnupper$Mncase$Qu;
    public static final ModuleMethod char$Mnwhitespace$Qu;
    public static final ModuleMethod string$Mnci$Eq$Qu;
    public static final ModuleMethod string$Mnci$Gr$Eq$Qu;
    public static final ModuleMethod string$Mnci$Gr$Qu;
    public static final ModuleMethod string$Mnci$Ls$Eq$Qu;
    public static final ModuleMethod string$Mnci$Ls$Qu;
    public static final ModuleMethod string$Mndowncase;
    public static final ModuleMethod string$Mnfoldcase;
    public static final ModuleMethod string$Mnnormalize$Mnnfc;
    public static final ModuleMethod string$Mnnormalize$Mnnfd;
    public static final ModuleMethod string$Mnnormalize$Mnnfkc;
    public static final ModuleMethod string$Mnnormalize$Mnnfkd;
    public static final ModuleMethod string$Mntitlecase;
    public static final ModuleMethod string$Mnupcase;

    static {
        ModuleBody moduleBody = $instance;
        char$Mnupcase = new ModuleMethod(moduleBody, 1, Lit0, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mndowncase = new ModuleMethod(moduleBody, 2, Lit1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mntitlecase = new ModuleMethod(moduleBody, 3, Lit2, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mnalphabetic$Qu = new ModuleMethod(moduleBody, 4, Lit3, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mnnumeric$Qu = new ModuleMethod(moduleBody, 5, Lit4, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mnwhitespace$Qu = new ModuleMethod(moduleBody, 6, Lit5, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mnupper$Mncase$Qu = new ModuleMethod(moduleBody, 7, Lit6, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mnlower$Mncase$Qu = new ModuleMethod(moduleBody, 8, Lit7, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mntitle$Mncase$Qu = new ModuleMethod(moduleBody, 9, Lit8, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mnfoldcase = new ModuleMethod(moduleBody, 10, Lit9, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        char$Mnci$Eq$Qu = new ModuleMethod(moduleBody, 11, Lit10, 8194);
        char$Mnci$Ls$Qu = new ModuleMethod(moduleBody, 12, Lit11, 8194);
        char$Mnci$Gr$Qu = new ModuleMethod(moduleBody, 13, Lit12, 8194);
        char$Mnci$Ls$Eq$Qu = new ModuleMethod(moduleBody, 14, Lit13, 8194);
        char$Mnci$Gr$Eq$Qu = new ModuleMethod(moduleBody, 15, Lit14, 8194);
        char$Mngeneral$Mncategory = new ModuleMethod(moduleBody, 16, Lit15, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnupcase = new ModuleMethod(moduleBody, 17, Lit16, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mndowncase = new ModuleMethod(moduleBody, 18, Lit17, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mntitlecase = new ModuleMethod(moduleBody, 19, Lit18, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnfoldcase = new ModuleMethod(moduleBody, 20, Lit19, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnci$Eq$Qu = new ModuleMethod(moduleBody, 21, Lit20, 8194);
        string$Mnci$Ls$Qu = new ModuleMethod(moduleBody, 22, Lit21, 8194);
        string$Mnci$Gr$Qu = new ModuleMethod(moduleBody, 23, Lit22, 8194);
        string$Mnci$Ls$Eq$Qu = new ModuleMethod(moduleBody, 24, Lit23, 8194);
        string$Mnci$Gr$Eq$Qu = new ModuleMethod(moduleBody, 25, Lit24, 8194);
        string$Mnnormalize$Mnnfd = new ModuleMethod(moduleBody, 26, Lit25, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnnormalize$Mnnfkd = new ModuleMethod(moduleBody, 27, Lit26, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnnormalize$Mnnfc = new ModuleMethod(moduleBody, 28, Lit27, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mnnormalize$Mnnfkc = new ModuleMethod(moduleBody, 29, Lit28, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public unicode() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static Char charUpcase(Char ch) {
        return Char.make(Character.toUpperCase(ch.intValue()));
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 4:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 5:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 6:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 7:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 8:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 9:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 10:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 16:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 17:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 18:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 19:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 20:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 26:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 27:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 28:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 29:
                if (!(obj instanceof CharSequence)) {
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

    public static Char charDowncase(Char ch) {
        return Char.make(Character.toLowerCase(ch.intValue()));
    }

    public static Char charTitlecase(Char ch) {
        return Char.make(Character.toTitleCase(ch.intValue()));
    }

    public static boolean isCharAlphabetic(Char ch) {
        return Character.isLetter(ch.intValue());
    }

    public static boolean isCharNumeric(Char ch) {
        return Character.isDigit(ch.intValue());
    }

    public static boolean isCharWhitespace(Char ch) {
        return UnicodeUtils.isWhitespace(ch.intValue());
    }

    public static boolean isCharUpperCase(Char ch) {
        return Character.isUpperCase(ch.intValue());
    }

    public static boolean isCharLowerCase(Char ch) {
        return Character.isLowerCase(ch.intValue());
    }

    public static boolean isCharTitleCase(Char ch) {
        return Character.isTitleCase(ch.intValue());
    }

    public static Char charFoldcase(Char ch) {
        int val = ch.intValue();
        boolean x = val == ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN;
        if (x) {
            if (x) {
                return ch;
            }
        } else if (val == ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED) {
            return ch;
        }
        return Char.make(Character.toLowerCase(Character.toUpperCase(val)));
    }

    public static boolean isCharCi$Eq(Char c1, Char c2) {
        return Character.toUpperCase(c1.intValue()) == Character.toUpperCase(c2.intValue());
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 11:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 12:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 13:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 14:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 15:
                if (!(obj instanceof Char)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Char)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 21:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof CharSequence)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 22:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof CharSequence)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 23:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof CharSequence)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 24:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof CharSequence)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case 25:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof CharSequence)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static boolean isCharCi$Ls(Char c1, Char c2) {
        return Character.toUpperCase(c1.intValue()) < Character.toUpperCase(c2.intValue());
    }

    public static boolean isCharCi$Gr(Char c1, Char c2) {
        return Character.toUpperCase(c1.intValue()) > Character.toUpperCase(c2.intValue());
    }

    public static boolean isCharCi$Ls$Eq(Char c1, Char c2) {
        return Character.toUpperCase(c1.intValue()) <= Character.toUpperCase(c2.intValue());
    }

    public static boolean isCharCi$Gr$Eq(Char c1, Char c2) {
        return Character.toUpperCase(c1.intValue()) >= Character.toUpperCase(c2.intValue());
    }

    public static Symbol charGeneralCategory(Char ch) {
        return UnicodeUtils.generalCategory(ch.intValue());
    }

    public static CharSequence stringUpcase(CharSequence str) {
        return new FString(str.toString().toUpperCase(Locale.ENGLISH));
    }

    public static CharSequence stringDowncase(CharSequence str) {
        return new FString(str.toString().toLowerCase(Locale.ENGLISH));
    }

    public static CharSequence stringTitlecase(CharSequence str) {
        return new FString(UnicodeUtils.capitalize(str == null ? null : str.toString()));
    }

    public static CharSequence stringFoldcase(CharSequence str) {
        return new FString(UnicodeUtils.foldCase(str));
    }

    public static boolean isStringCi$Eq(CharSequence str1, CharSequence str2) {
        return UnicodeUtils.foldCase(str1).equals(UnicodeUtils.foldCase(str2));
    }

    public static boolean isStringCi$Ls(CharSequence str1, CharSequence str2) {
        return UnicodeUtils.foldCase(str1).compareTo(UnicodeUtils.foldCase(str2)) < 0;
    }

    public static boolean isStringCi$Gr(CharSequence str1, CharSequence str2) {
        return UnicodeUtils.foldCase(str1).compareTo(UnicodeUtils.foldCase(str2)) > 0;
    }

    public static boolean isStringCi$Ls$Eq(CharSequence str1, CharSequence str2) {
        return UnicodeUtils.foldCase(str1).compareTo(UnicodeUtils.foldCase(str2)) <= 0;
    }

    public static boolean isStringCi$Gr$Eq(CharSequence str1, CharSequence str2) {
        return UnicodeUtils.foldCase(str1).compareTo(UnicodeUtils.foldCase(str2)) >= 0;
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case 11:
                try {
                    try {
                        return isCharCi$Eq((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "char-ci=?", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "char-ci=?", 1, obj);
                }
            case 12:
                try {
                    try {
                        return isCharCi$Ls((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "char-ci<?", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "char-ci<?", 1, obj);
                }
            case 13:
                try {
                    try {
                        return isCharCi$Gr((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "char-ci>?", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "char-ci>?", 1, obj);
                }
            case 14:
                try {
                    try {
                        return isCharCi$Ls$Eq((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e222222) {
                        throw new WrongType(e222222, "char-ci<=?", 2, obj2);
                    }
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "char-ci<=?", 1, obj);
                }
            case 15:
                try {
                    try {
                        return isCharCi$Gr$Eq((Char) obj, (Char) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e22222222) {
                        throw new WrongType(e22222222, "char-ci>=?", 2, obj2);
                    }
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "char-ci>=?", 1, obj);
                }
            case 21:
                try {
                    try {
                        return isStringCi$Eq((CharSequence) obj, (CharSequence) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2222222222) {
                        throw new WrongType(e2222222222, "string-ci=?", 2, obj2);
                    }
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "string-ci=?", 1, obj);
                }
            case 22:
                try {
                    try {
                        return isStringCi$Ls((CharSequence) obj, (CharSequence) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e222222222222) {
                        throw new WrongType(e222222222222, "string-ci<?", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "string-ci<?", 1, obj);
                }
            case 23:
                try {
                    try {
                        return isStringCi$Gr((CharSequence) obj, (CharSequence) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e22222222222222) {
                        throw new WrongType(e22222222222222, "string-ci>?", 2, obj2);
                    }
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "string-ci>?", 1, obj);
                }
            case 24:
                try {
                    try {
                        return isStringCi$Ls$Eq((CharSequence) obj, (CharSequence) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e2222222222222222) {
                        throw new WrongType(e2222222222222222, "string-ci<=?", 2, obj2);
                    }
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "string-ci<=?", 1, obj);
                }
            case 25:
                try {
                    try {
                        return isStringCi$Gr$Eq((CharSequence) obj, (CharSequence) obj2) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e222222222222222222) {
                        throw new WrongType(e222222222222222222, "string-ci>=?", 2, obj2);
                    }
                } catch (ClassCastException e2222222222222222222) {
                    throw new WrongType(e2222222222222222222, "string-ci>=?", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static CharSequence stringNormalizeNfd(CharSequence str) {
        return (CharSequence) misc.error$V("unicode string normalization not available", new Object[0]);
    }

    public static CharSequence stringNormalizeNfkd(CharSequence str) {
        return (CharSequence) misc.error$V("unicode string normalization not available", new Object[0]);
    }

    public static CharSequence stringNormalizeNfc(CharSequence str) {
        return (CharSequence) misc.error$V("unicode string normalization not available", new Object[0]);
    }

    public static CharSequence stringNormalizeNfkc(CharSequence str) {
        return (CharSequence) misc.error$V("unicode string normalization not available", new Object[0]);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                try {
                    return charUpcase((Char) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "char-upcase", 1, obj);
                }
            case 2:
                try {
                    return charDowncase((Char) obj);
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "char-downcase", 1, obj);
                }
            case 3:
                try {
                    return charTitlecase((Char) obj);
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "char-titlecase", 1, obj);
                }
            case 4:
                try {
                    return isCharAlphabetic((Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "char-alphabetic?", 1, obj);
                }
            case 5:
                try {
                    return isCharNumeric((Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "char-numeric?", 1, obj);
                }
            case 6:
                try {
                    return isCharWhitespace((Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "char-whitespace?", 1, obj);
                }
            case 7:
                try {
                    return isCharUpperCase((Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "char-upper-case?", 1, obj);
                }
            case 8:
                try {
                    return isCharLowerCase((Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "char-lower-case?", 1, obj);
                }
            case 9:
                try {
                    return isCharTitleCase((Char) obj) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "char-title-case?", 1, obj);
                }
            case 10:
                try {
                    return charFoldcase((Char) obj);
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "char-foldcase", 1, obj);
                }
            case 16:
                try {
                    return charGeneralCategory((Char) obj);
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "char-general-category", 1, obj);
                }
            case 17:
                try {
                    return stringUpcase((CharSequence) obj);
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "string-upcase", 1, obj);
                }
            case 18:
                try {
                    return stringDowncase((CharSequence) obj);
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "string-downcase", 1, obj);
                }
            case 19:
                try {
                    return stringTitlecase((CharSequence) obj);
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "string-titlecase", 1, obj);
                }
            case 20:
                try {
                    return stringFoldcase((CharSequence) obj);
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "string-foldcase", 1, obj);
                }
            case 26:
                try {
                    return stringNormalizeNfd((CharSequence) obj);
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "string-normalize-nfd", 1, obj);
                }
            case 27:
                try {
                    return stringNormalizeNfkd((CharSequence) obj);
                } catch (ClassCastException e2222222222222222) {
                    throw new WrongType(e2222222222222222, "string-normalize-nfkd", 1, obj);
                }
            case 28:
                try {
                    return stringNormalizeNfc((CharSequence) obj);
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "string-normalize-nfc", 1, obj);
                }
            case 29:
                try {
                    return stringNormalizeNfkc((CharSequence) obj);
                } catch (ClassCastException e222222222222222222) {
                    throw new WrongType(e222222222222222222, "string-normalize-nfkc", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}

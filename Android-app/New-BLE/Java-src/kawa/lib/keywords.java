package kawa.lib;

import android.support.v4.app.FragmentTransaction;
import gnu.expr.Keyword;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.WrongType;

/* compiled from: keywords.scm */
public class keywords extends ModuleBody {
    public static final keywords $instance = new keywords();
    static final SimpleSymbol Lit0 = ((SimpleSymbol) new SimpleSymbol("keyword?").readResolve());
    static final SimpleSymbol Lit1 = ((SimpleSymbol) new SimpleSymbol("keyword->string").readResolve());
    static final SimpleSymbol Lit2 = ((SimpleSymbol) new SimpleSymbol("string->keyword").readResolve());
    public static final ModuleMethod keyword$Mn$Grstring;
    public static final ModuleMethod keyword$Qu;
    public static final ModuleMethod string$Mn$Grkeyword;

    static {
        ModuleBody moduleBody = $instance;
        keyword$Qu = new ModuleMethod(moduleBody, 1, Lit0, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        keyword$Mn$Grstring = new ModuleMethod(moduleBody, 2, Lit1, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        string$Mn$Grkeyword = new ModuleMethod(moduleBody, 3, Lit2, FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        $instance.run();
    }

    public keywords() {
        ModuleInfo.register(this);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static boolean isKeyword(Object object) {
        return Keyword.isKeyword(object);
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case 1:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 2:
                if (!(obj instanceof Keyword)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case 3:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static CharSequence keyword$To$String(Keyword keyword) {
        return keyword.getName();
    }

    public static Keyword string$To$Keyword(String string) {
        return Keyword.make(string);
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case 1:
                return isKeyword(obj) ? Boolean.TRUE : Boolean.FALSE;
            case 2:
                try {
                    return keyword$To$String((Keyword) obj);
                } catch (ClassCastException e) {
                    throw new WrongType(e, "keyword->string", 1, obj);
                }
            case 3:
                return string$To$Keyword(obj == null ? null : obj.toString());
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}

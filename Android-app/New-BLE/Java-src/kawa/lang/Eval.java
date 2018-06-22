package kawa.lang;

import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import gnu.expr.NameLookup;
import gnu.lists.LList;
import gnu.lists.PairWithPosition;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Procedure;
import gnu.mapping.Procedure1or2;
import gnu.text.SourceMessages;

public class Eval extends Procedure1or2 {
    public static final Eval eval = new Eval();
    static final String evalFunctionName = "atEvalLevel$";

    static {
        eval.setName("eval");
    }

    public static void eval(Object sexpr, Environment env, CallContext ctx) throws Throwable {
        PairWithPosition body;
        if (sexpr instanceof PairWithPosition) {
            body = new PairWithPosition((PairWithPosition) sexpr, sexpr, LList.Empty);
        } else {
            body = new PairWithPosition(sexpr, LList.Empty);
            body.setFile("<eval>");
        }
        evalBody(body, env, new SourceMessages(), ctx);
    }

    public static Object evalBody(Object body, Environment env, SourceMessages messages) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        int oldIndex = ctx.startFromContext();
        try {
            evalBody(body, env, messages, ctx);
            return ctx.getFromContext(oldIndex);
        } catch (Throwable th) {
            ctx.cleanupFromContext(oldIndex);
        }
    }

    public static Object eval(Object sexpr, Environment env) throws Throwable {
        CallContext ctx = CallContext.getInstance();
        int oldIndex = ctx.startFromContext();
        try {
            eval(sexpr, env, ctx);
            return ctx.getFromContext(oldIndex);
        } catch (Throwable th) {
            ctx.cleanupFromContext(oldIndex);
        }
    }

    public static void evalBody(Object body, Environment env, SourceMessages messages, CallContext ctx) throws Throwable {
        Language language = Language.getDefaultLanguage();
        Environment saveGlobalEnv = Environment.getCurrent();
        if (env != saveGlobalEnv) {
            try {
                Environment.setCurrent(env);
            } catch (Throwable th) {
                if (env != saveGlobalEnv) {
                    Environment.setCurrent(saveGlobalEnv);
                }
            }
        }
        Translator tr = new Translator(language, messages, NameLookup.getInstance(env, language));
        tr.immediate = true;
        tr.setState(3);
        tr.setSharedModuleDefs(true);
        ModuleExp mod = tr.pushNewModule((String) null);
        Compilation saveComp = Compilation.setSaveCurrent(tr);
        int first = tr.formStack.size();
        tr.scanBody(body, mod, false);
        tr.firstForm = first;
        tr.finishModule(mod);
        Compilation.restoreCurrent(saveComp);
        if (body instanceof PairWithPosition) {
            mod.setFile(((PairWithPosition) body).getFileName());
        }
        StringBuilder append = new StringBuilder().append(evalFunctionName);
        int i = ModuleExp.interactiveCounter + 1;
        ModuleExp.interactiveCounter = i;
        mod.setName(append.append(i).toString());
        ModuleExp.evalModule(env, ctx, tr, null, null);
        if (messages.seenErrors()) {
            throw new RuntimeException("invalid syntax in eval form:\n" + messages.toString(20));
        } else if (env != saveGlobalEnv) {
            Environment.setCurrent(saveGlobalEnv);
        }
    }

    public Object apply1(Object arg1) throws Throwable {
        return eval(arg1, Environment.user());
    }

    public Object apply2(Object arg1, Object arg2) throws Throwable {
        return eval(arg1, (Environment) arg2);
    }

    public void apply(CallContext ctx) throws Throwable {
        Procedure.checkArgCount(this, ctx.count);
        Object exp = ctx.getNextArg();
        Environment env = (Environment) ctx.getNextArg(null);
        if (env == null) {
            env = Environment.user();
        }
        ctx.lastArg();
        eval(exp, env, ctx);
    }
}

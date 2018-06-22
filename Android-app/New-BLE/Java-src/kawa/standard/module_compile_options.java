package kawa.standard;

import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Vector;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_compile_options extends Syntax {
    public static final module_compile_options module_compile_options = new module_compile_options();

    static {
        module_compile_options.setName("module-compile-options");
    }

    public boolean scanForDefinitions(Pair st, Vector forms, ScopeExp defs, Translator tr) {
        if (with_compile_options.getOptions(st.getCdr(), null, this, tr) != LList.Empty) {
            tr.error('e', getName() + " key must be a keyword");
        }
        return true;
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        return null;
    }
}

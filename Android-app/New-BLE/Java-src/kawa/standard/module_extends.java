package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_extends extends Syntax {
    public static final module_extends module_extends = new module_extends();

    static {
        module_extends.setName("module-extends");
    }

    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        Type base = tr.exp2Type((Pair) form.getCdr());
        ModuleExp module = tr.getModule();
        module.setSuperType((ClassType) base);
        module.setFlag(131072);
    }
}

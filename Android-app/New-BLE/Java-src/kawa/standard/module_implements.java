package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.expr.ModuleExp;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class module_implements extends Syntax {
    public static final module_implements module_implements = new module_implements();

    static {
        module_implements.setName("module-implements");
    }

    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        Pair args = form.getCdr();
        int len = LList.listLength(args, false);
        if (len < 0) {
            tr.syntaxError("improper argument list for " + getName());
            return;
        }
        ClassType[] interfaces = new ClassType[len];
        for (int i = 0; i < len; i++) {
            Pair pair = args;
            interfaces[i] = (ClassType) tr.exp2Type(pair);
            args = pair.getCdr();
        }
        ModuleExp module = tr.getModule();
        module.setInterfaces(interfaces);
        module.setFlag(131072);
    }
}

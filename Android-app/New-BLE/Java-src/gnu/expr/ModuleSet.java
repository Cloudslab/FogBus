package gnu.expr;

public abstract class ModuleSet {
    public static final String MODULES_MAP = "$ModulesMap$";
    ModuleSet next;

    public abstract void register(ModuleManager moduleManager);
}

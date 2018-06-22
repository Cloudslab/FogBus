package gnu.xquery.util;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Target;
import gnu.kawa.reflect.InstanceOf;
import gnu.kawa.xml.XDataType;
import gnu.mapping.Procedure;
import gnu.xquery.lang.XQuery;

public class CastableAs extends InstanceOf {
    public static CastableAs castableAs = new CastableAs();

    CastableAs() {
        super(XQuery.getInstance(), "castable as");
        setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateApplyCastableAs");
    }

    public Object apply2(Object arg1, Object arg2) {
        boolean result;
        Type type = this.language.asType(arg2);
        if (type instanceof XDataType) {
            result = ((XDataType) type).castable(arg1);
        } else {
            result = type.isInstance(arg1);
        }
        return this.language.booleanObject(result);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ApplyExp.compile(exp, comp, target);
    }
}

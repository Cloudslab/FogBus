package kawa.lang;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.Compilation;
import gnu.text.Printable;

public abstract class Pattern implements Printable {
    private static Type[] matchArgs = new Type[]{Type.pointer_type, Compilation.objArrayType, Type.intType};
    public static final Method matchPatternMethod = typePattern.addMethod("match", matchArgs, Type.booleanType, 1);
    public static ClassType typePattern = ClassType.make("kawa.lang.Pattern");

    public abstract boolean match(Object obj, Object[] objArr, int i);

    public abstract int varCount();

    public Object[] match(Object obj) {
        Object[] vars = new Object[varCount()];
        return match(obj, vars, 0) ? vars : null;
    }
}

package kawa.standard;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;

public class prim_throw extends Procedure1 implements Inlineable {
    private static ClassType javaThrowableType;
    public static final prim_throw primitiveThrow = new prim_throw();

    public static void throw_it(Object arg1) throws Throwable {
        throw ((Throwable) arg1);
    }

    public Object apply1(Object arg1) throws Throwable {
        throw_it(arg1);
        return Values.empty;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        exp.getArgs()[0].compile(comp, Target.pushObject);
        if (javaThrowableType == null) {
            javaThrowableType = new ClassType("java.lang.Throwable");
        }
        code.emitCheckcast(javaThrowableType);
        code.emitThrow();
    }

    public Type getReturnType(Expression[] args) {
        return Type.neverReturnsType;
    }
}

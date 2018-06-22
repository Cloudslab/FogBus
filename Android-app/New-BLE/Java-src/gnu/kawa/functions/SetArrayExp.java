package gnu.kawa.functions;

import gnu.bytecode.ArrayType;
import gnu.bytecode.ClassType;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.QuoteExp;
import gnu.kawa.reflect.ArraySet;
import gnu.kawa.reflect.Invoke;

/* compiled from: CompilationHelpers */
class SetArrayExp extends ApplyExp {
    public static final ClassType typeSetArray = ClassType.make("gnu.kawa.functions.SetArray");
    Type elementType;

    public SetArrayExp(Expression array, ArrayType arrayType) {
        super(Invoke.make, new Expression[]{new QuoteExp(typeSetArray), array});
        this.elementType = arrayType.getComponentType();
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        exp.visitArgs(visitor);
        if (exp.getArgs().length != 2) {
            return exp;
        }
        return visitor.visitApplyOnly(new ApplyExp(new ArraySet(this.elementType), new Expression[]{getArgs()[1], exp.getArgs()[0], exp.getArgs()[1]}), required);
    }
}

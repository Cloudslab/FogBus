package gnu.expr;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;

public class ObjectExp extends ClassExp {
    public ObjectExp() {
        super(true);
    }

    public Type getType() {
        return this.type;
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        return visitor.visitObjectExp(this, d);
    }

    public void compile(Compilation comp, Target target) {
        compileMembers(comp);
        CodeAttr code = comp.getCode();
        code.emitNew(this.type);
        code.emitDup(1);
        Method init = Compilation.getConstructor(this.type, this);
        if (this.closureEnvField != null) {
            LambdaExp caller = outerLambda();
            Variable closureEnv = Compilation.defaultCallConvention < 2 ? getOwningLambda().heapFrame : caller.heapFrame != null ? caller.heapFrame : caller.closureEnv;
            if (closureEnv == null) {
                code.emitPushThis();
            } else {
                code.emitLoad(closureEnv);
            }
        }
        code.emitInvokeSpecial(init);
        target.compileFromStack(comp, getCompiledClassType(comp));
    }
}

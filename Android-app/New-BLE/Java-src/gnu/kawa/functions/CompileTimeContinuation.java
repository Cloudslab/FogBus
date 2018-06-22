package gnu.kawa.functions;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.ExitableBlock;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure;
import gnu.mapping.ProcedureN;

/* compiled from: CallCC */
class CompileTimeContinuation extends ProcedureN implements Inlineable {
    Target blockTarget;
    ExitableBlock exitableBlock;

    CompileTimeContinuation() {
    }

    public Object applyN(Object[] args) throws Throwable {
        throw new Error("internal error");
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        CodeAttr code = comp.getCode();
        Expression[] args = exp.getArgs();
        boolean noStack = (this.blockTarget instanceof IgnoreTarget) || (this.blockTarget instanceof ConsumerTarget);
        if (!noStack) {
            Type type = target.getType();
        }
        if (noStack || nargs == 1) {
            for (Expression compileWithPosition : args) {
                compileWithPosition.compileWithPosition(comp, this.blockTarget);
            }
        } else {
            Procedure app = AppendValues.appendValues;
            app.compile(new ApplyExp(app, args), comp, this.blockTarget);
        }
        this.exitableBlock.exit();
    }

    public Type getReturnType(Expression[] args) {
        return Type.neverReturnsType;
    }
}

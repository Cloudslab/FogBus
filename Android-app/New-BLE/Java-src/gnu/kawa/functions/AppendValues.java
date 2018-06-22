package gnu.kawa.functions;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.Special;
import gnu.expr.Target;
import gnu.lists.Consumable;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;

public class AppendValues extends MethodProc implements Inlineable {
    public static final AppendValues appendValues = new AppendValues();

    public AppendValues() {
        setProperty(Procedure.validateApplyKey, "gnu.kawa.functions.CompileMisc:validateApplyAppendValues");
    }

    public void apply(CallContext ctx) {
        Special endMarker = Special.dfault;
        while (true) {
            Special arg = ctx.getNextArg(endMarker);
            if (arg != endMarker) {
                if (arg instanceof Consumable) {
                    ((Consumable) arg).consume(ctx.consumer);
                } else {
                    ctx.writeValue(arg);
                }
            } else {
                return;
            }
        }
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        if ((target instanceof ConsumerTarget) || (target instanceof IgnoreTarget)) {
            for (Expression compileWithPosition : exp.getArgs()) {
                compileWithPosition.compileWithPosition(comp, target);
            }
            return;
        }
        ConsumerTarget.compileUsingConsumer(exp, comp, target);
    }

    public Type getReturnType(Expression[] args) {
        return Compilation.typeObject;
    }
}

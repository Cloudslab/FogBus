package gnu.xquery.util;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;

public class OrderedMap extends MethodProc implements Inlineable {
    public static final OrderedMap orderedMap = new OrderedMap();

    static {
        orderedMap.setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateApplyOrderedMap");
    }

    public static Object[] makeTuple$V(Object[] values) {
        return values;
    }

    public void apply(CallContext ctx) throws Throwable {
        OrderedTuples tuples;
        Object[] args = ctx.getArgs();
        Object values = args[0];
        if (args.length == 2) {
            tuples = (OrderedTuples) args[1];
        } else {
            Object[] comps = new Object[(args.length - 2)];
            System.arraycopy(args, 2, comps, 0, comps.length);
            tuples = OrderedTuples.make$V((Procedure) args[1], comps);
        }
        Values.writeValues(values, tuples);
        tuples.run$X(ctx);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        CompileMisc.compileOrderedMap(exp, comp, target, this);
    }

    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }
}

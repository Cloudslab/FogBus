package gnu.kawa.xml;

import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.kawa.functions.AppendValues;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;

public class UnionNodes extends Procedure2 implements Inlineable {
    public static final UnionNodes unionNodes = new UnionNodes();

    public Object apply2(Object vals1, Object vals2) {
        SortedNodes nodes = new SortedNodes();
        Values.writeValues(vals1, nodes);
        Values.writeValues(vals2, nodes);
        return nodes;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ConsumerTarget.compileUsingConsumer(new ApplyExp(AppendValues.appendValues, exp.getArgs()), comp, target, SortNodes.makeSortedNodesMethod, null);
    }

    public Type getReturnType(Expression[] args) {
        return Compilation.typeObject;
    }
}

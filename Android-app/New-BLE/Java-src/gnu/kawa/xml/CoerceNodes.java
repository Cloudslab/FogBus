package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.Target;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;

public class CoerceNodes extends Procedure1 implements Inlineable {
    public static final CoerceNodes coerceNodes = new CoerceNodes();
    public static final Method makeNodesMethod = typeNodes.getDeclaredMethod("<init>", 0);
    public static final ClassType typeNodes = ClassType.make("gnu.kawa.xml.Nodes");

    public Object apply1(Object values) {
        Nodes nodes = new Nodes();
        Values.writeValues(values, nodes);
        return nodes;
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        if (args.length != 1) {
            ApplyExp.compile(exp, comp, target);
        } else {
            ConsumerTarget.compileUsingConsumer(args[0], comp, target, makeNodesMethod, null);
        }
    }

    public Type getReturnType(Expression[] args) {
        return typeNodes;
    }
}

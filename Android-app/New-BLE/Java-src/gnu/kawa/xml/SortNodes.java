package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Inlineable;
import gnu.expr.StackTarget;
import gnu.expr.Target;
import gnu.mapping.Procedure1;
import gnu.mapping.Values;

public class SortNodes extends Procedure1 implements Inlineable {
    public static final Method canonicalizeMethod = Compilation.typeValues.getDeclaredMethod("canonicalize", 0);
    public static final Method makeSortedNodesMethod = typeSortedNodes.getDeclaredMethod("<init>", 0);
    public static final SortNodes sortNodes = new SortNodes();
    public static final ClassType typeSortedNodes = ClassType.make("gnu.kawa.xml.SortedNodes");

    public Object apply1(Object values) {
        SortedNodes nodes = new SortedNodes();
        Values.writeValues(values, nodes);
        if (nodes.count > 1) {
            return nodes;
        }
        if (nodes.count == 0) {
            return Values.empty;
        }
        return nodes.get(0);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        if (args.length == 1 && comp.mustCompile) {
            Method resultMethod;
            if ((target instanceof ConsumerTarget) || ((target instanceof StackTarget) && target.getType().isSubtype(Compilation.typeValues))) {
                resultMethod = null;
            } else {
                resultMethod = canonicalizeMethod;
            }
            ConsumerTarget.compileUsingConsumer(args[0], comp, target, makeSortedNodesMethod, resultMethod);
            return;
        }
        ApplyExp.compile(exp, comp, target);
    }

    public Type getReturnType(Expression[] args) {
        return Compilation.typeObject;
    }
}

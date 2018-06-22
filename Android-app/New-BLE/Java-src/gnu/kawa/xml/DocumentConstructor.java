package gnu.kawa.xml;

import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Location;

public class DocumentConstructor extends NodeConstructor {
    public static final DocumentConstructor documentConstructor = new DocumentConstructor();
    static final Method endDocumentMethod = Compilation.typeConsumer.getDeclaredMethod("endDocument", 0);
    static final Method startDocumentMethod = Compilation.typeConsumer.getDeclaredMethod("startDocument", 0);

    public void apply(CallContext ctx) {
        Consumer saved = ctx.consumer;
        Consumer out = NodeConstructor.pushNodeContext(ctx);
        String endMarker = Location.UNBOUND;
        out.startDocument();
        while (true) {
            String arg = ctx.getNextArg(endMarker);
            if (arg == endMarker) {
                break;
            } else if (arg instanceof Consumable) {
                ((Consumable) arg).consume(out);
            } else {
                try {
                    out.writeObject(arg);
                } finally {
                    NodeConstructor.popNodeContext(saved, ctx);
                }
            }
        }
        out.endDocument();
    }

    public void compileToNode(ApplyExp exp, Compilation comp, ConsumerTarget target) {
        Variable consumer = target.getConsumerVariable();
        CodeAttr code = comp.getCode();
        code.emitLoad(consumer);
        code.emitInvokeInterface(startDocumentMethod);
        for (Expression compileChild : exp.getArgs()) {
            NodeConstructor.compileChild(compileChild, comp, target);
        }
        code.emitLoad(consumer);
        code.emitInvokeInterface(endDocumentMethod);
    }
}

package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.QuoteExp;
import gnu.expr.Special;
import gnu.expr.Target;
import gnu.lists.Consumable;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;

public class MakeAttribute extends NodeConstructor {
    static final Method endAttributeMethod = Compilation.typeConsumer.getDeclaredMethod("endAttribute", 0);
    public static final MakeAttribute makeAttribute = new MakeAttribute();
    public static final QuoteExp makeAttributeExp = new QuoteExp(makeAttribute);
    static final Method startAttributeMethod = typeMakeAttribute.getDeclaredMethod("startAttribute", 2);
    static final ClassType typeMakeAttribute = ClassType.make("gnu.kawa.xml.MakeAttribute");

    public int numArgs() {
        return -4095;
    }

    public static void startAttribute(Consumer out, Object type) {
        out.startAttribute(type);
    }

    public void apply(CallContext ctx) {
        Consumer saved = ctx.consumer;
        Consumer out = NodeConstructor.pushNodeContext(ctx);
        startAttribute(out, ctx.getNextArg());
        Special endMarker = Special.dfault;
        while (true) {
            Special arg = ctx.getNextArg(endMarker);
            if (arg == endMarker) {
                break;
            } else if (arg instanceof Consumable) {
                ((Consumable) arg).consume(out);
            } else {
                try {
                    ctx.writeValue(arg);
                } finally {
                    NodeConstructor.popNodeContext(saved, ctx);
                }
            }
        }
        out.endAttribute();
    }

    public void compileToNode(ApplyExp exp, Compilation comp, ConsumerTarget target) {
        Variable consumer = target.getConsumerVariable();
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        CodeAttr code = comp.getCode();
        code.emitLoad(consumer);
        code.emitDup();
        args[0].compile(comp, Target.pushObject);
        code.emitInvokeStatic(startAttributeMethod);
        for (int i = 1; i < nargs; i++) {
            NodeConstructor.compileChild(args[i], comp, target);
        }
        code.emitInvokeInterface(endAttributeMethod);
    }

    public Type getReturnType(Expression[] args) {
        return Compilation.typeObject;
    }
}

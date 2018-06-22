package gnu.kawa.xml;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.xml.TextUtils;

public class MakeProcInst extends NodeConstructor {
    public static final MakeProcInst makeProcInst = new MakeProcInst();

    public int numArgs() {
        return 8194;
    }

    public static void procInst$C(Object target, Object content, Consumer out) {
        target = KNode.atomicValue(target);
        if (!(target instanceof String) && !(target instanceof UntypedAtomic)) {
            throw new ClassCastException("invalid type of processing-instruction target [XPTY0004]");
        } else if (out instanceof XConsumer) {
            StringBuffer sbuf = new StringBuffer();
            if (content instanceof Values) {
                Object[] vals = ((Values) content).getValues();
                for (int i = 0; i < vals.length; i++) {
                    if (i > 0) {
                        sbuf.append(' ');
                    }
                    TextUtils.stringValue(vals[i], sbuf);
                }
            } else {
                TextUtils.stringValue(content, sbuf);
            }
            int length = sbuf.length();
            int start = 0;
            while (start < length && Character.isWhitespace(sbuf.charAt(start))) {
                start++;
            }
            char[] chars = new char[(length - start)];
            sbuf.getChars(start, length, chars, 0);
            ((XConsumer) out).writeProcessingInstruction(target.toString(), chars, 0, chars.length);
        }
    }

    public static void procInst$X(Object target, Object content, CallContext ctx) {
        Consumer saved = ctx.consumer;
        try {
            procInst$C(target, content, NodeConstructor.pushNodeContext(ctx));
        } finally {
            NodeConstructor.popNodeContext(saved, ctx);
        }
    }

    public void apply(CallContext ctx) {
        procInst$X(ctx.getNextArg(null), ctx.getNextArg(null), ctx);
    }

    public void compileToNode(ApplyExp exp, Compilation comp, ConsumerTarget target) {
        CodeAttr code = comp.getCode();
        Expression[] args = exp.getArgs();
        args[0].compile(comp, Target.pushObject);
        args[1].compile(comp, Target.pushObject);
        code.emitLoad(target.getConsumerVariable());
        code.emitInvokeStatic(ClassType.make("gnu.kawa.xml.MakeProcInst").getDeclaredMethod("procInst$C", 3));
    }
}

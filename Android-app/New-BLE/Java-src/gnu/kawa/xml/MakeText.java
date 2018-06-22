package gnu.kawa.xml;

import android.support.v4.app.FragmentTransaction;
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
import gnu.expr.Target;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.xml.NodeTree;
import gnu.xml.TextUtils;
import gnu.xml.XMLFilter;

public class MakeText extends NodeConstructor {
    public static final MakeText makeText = new MakeText();

    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    public Object apply1(Object arg) {
        if (arg == null) {
            return arg;
        }
        if ((arg instanceof Values) && ((Values) arg).isEmpty()) {
            return arg;
        }
        NodeTree node = new NodeTree();
        TextUtils.textValue(arg, new XMLFilter(node));
        return KNode.make(node);
    }

    public static void text$X(Object arg, CallContext ctx) {
        if (arg == null) {
            return;
        }
        if (!(arg instanceof Values) || !((Values) arg).isEmpty()) {
            Consumer saved = ctx.consumer;
            try {
                TextUtils.textValue(arg, NodeConstructor.pushNodeContext(ctx));
            } finally {
                NodeConstructor.popNodeContext(saved, ctx);
            }
        }
    }

    public void apply(CallContext ctx) {
        text$X(ctx.getNextArg(null), ctx);
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        ApplyExp.compile(exp, comp, target);
    }

    public void compileToNode(ApplyExp exp, Compilation comp, ConsumerTarget target) {
        CodeAttr code = comp.getCode();
        Expression texp = exp.getArgs()[0];
        Variable cvar = target.getConsumerVariable();
        if (texp instanceof QuoteExp) {
            String tval = ((QuoteExp) texp).getValue();
            if (tval instanceof String) {
                String str = tval;
                String segments = CodeAttr.calculateSplit(str);
                int numSegments = segments.length();
                Method writer = ((ClassType) cvar.getType()).getMethod("write", new Type[]{Type.string_type});
                int segStart = 0;
                for (int seg = 0; seg < numSegments; seg++) {
                    code.emitLoad(cvar);
                    int segEnd = segStart + segments.charAt(seg);
                    code.emitPushString(str.substring(segStart, segEnd));
                    code.emitInvoke(writer);
                    segStart = segEnd;
                }
                return;
            }
        }
        texp.compile(comp, Target.pushObject);
        code.emitLoad(cvar);
        code.emitInvokeStatic(ClassType.make("gnu.xml.TextUtils").getDeclaredMethod("textValue", 2));
    }
}

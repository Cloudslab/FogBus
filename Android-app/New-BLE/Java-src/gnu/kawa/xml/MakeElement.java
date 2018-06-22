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
import gnu.mapping.Symbol;
import gnu.xml.NamespaceBinding;
import gnu.xml.XMLFilter;
import gnu.xml.XName;

public class MakeElement extends NodeConstructor {
    static final Method endElementMethod = typeMakeElement.getDeclaredMethod("endElement", 2);
    public static final MakeElement makeElement = new MakeElement();
    static final Method startElementMethod3 = typeMakeElement.getDeclaredMethod("startElement", 3);
    static final Method startElementMethod4 = typeMakeElement.getDeclaredMethod("startElement", 4);
    static final ClassType typeMakeElement = ClassType.make("gnu.kawa.xml.MakeElement");
    public int copyNamespacesMode = 1;
    private boolean handlingKeywordParameters;
    NamespaceBinding namespaceNodes;
    public Symbol tag;

    public int numArgs() {
        return this.tag == null ? -4095 : -4096;
    }

    public String toString() {
        return "makeElement[" + this.tag + "]";
    }

    public boolean isHandlingKeywordParameters() {
        return this.handlingKeywordParameters;
    }

    public void setHandlingKeywordParameters(boolean value) {
        this.handlingKeywordParameters = value;
    }

    public NamespaceBinding getNamespaceNodes() {
        return this.namespaceNodes;
    }

    public void setNamespaceNodes(NamespaceBinding bindings) {
        this.namespaceNodes = bindings;
    }

    public static Symbol getTagName(ApplyExp exp) {
        Expression[] args = exp.getArgs();
        if (args.length > 0) {
            Expression arg0 = args[0];
            if (arg0 instanceof QuoteExp) {
                Object val = ((QuoteExp) arg0).getValue();
                if (val instanceof Symbol) {
                    return (Symbol) val;
                }
            }
        }
        return null;
    }

    public static void startElement(Consumer out, Object qname, int copyNamespacesMode, NamespaceBinding namespaceNodes) {
        XName type;
        if (qname instanceof Symbol) {
            type = new XName((Symbol) qname, namespaceNodes);
        } else {
            type = new XName(Symbol.make("", qname.toString(), ""), namespaceNodes);
        }
        if (out instanceof XMLFilter) {
            ((XMLFilter) out).copyNamespacesMode = copyNamespacesMode;
        }
        out.startElement(type);
    }

    public static void startElement(Consumer out, Object qname, int copyNamespacesMode) {
        Symbol type;
        if (qname instanceof Symbol) {
            type = (Symbol) qname;
        } else {
            type = Symbol.make("", qname.toString(), "");
        }
        if (out instanceof XMLFilter) {
            ((XMLFilter) out).copyNamespacesMode = copyNamespacesMode;
        }
        out.startElement(type);
    }

    public static void endElement(Consumer out, Object type) {
        out.endElement();
    }

    public void apply(CallContext ctx) {
        Consumer saved = ctx.consumer;
        Consumer out = NodeConstructor.pushNodeContext(ctx);
        try {
            Object type = this.tag != null ? this.tag : ctx.getNextArg();
            if (this.namespaceNodes != null) {
                startElement(out, type, this.copyNamespacesMode, this.namespaceNodes);
            } else {
                startElement(out, type, this.copyNamespacesMode);
            }
            Special endMarker = Special.dfault;
            while (true) {
                Special arg = ctx.getNextArg(endMarker);
                if (arg == endMarker) {
                    break;
                }
                if (arg instanceof Consumable) {
                    ((Consumable) arg).consume(out);
                } else {
                    ctx.writeValue(arg);
                }
                if (isHandlingKeywordParameters()) {
                    out.endAttribute();
                }
            }
            endElement(out, type);
        } finally {
            NodeConstructor.popNodeContext(saved, ctx);
        }
    }

    public void compileToNode(ApplyExp exp, Compilation comp, ConsumerTarget target) {
        int i;
        Variable consumer = target.getConsumerVariable();
        Expression[] args = exp.getArgs();
        int nargs = args.length;
        CodeAttr code = comp.getCode();
        code.emitLoad(consumer);
        code.emitDup();
        if (this.tag == null) {
            args[0].compile(comp, Target.pushObject);
            i = 1;
        } else {
            comp.compileConstant(this.tag, Target.pushObject);
            i = 0;
        }
        code.emitDup(1, 1);
        code.emitPushInt(this.copyNamespacesMode);
        if (this.namespaceNodes != null) {
            comp.compileConstant(this.namespaceNodes, Target.pushObject);
            code.emitInvokeStatic(startElementMethod4);
        } else {
            code.emitInvokeStatic(startElementMethod3);
        }
        while (i < nargs) {
            NodeConstructor.compileChild(args[i], comp, target);
            if (isHandlingKeywordParameters()) {
                code.emitLoad(consumer);
                code.emitInvokeInterface(MakeAttribute.endAttributeMethod);
            }
            i++;
        }
        code.emitInvokeStatic(endElementMethod);
    }

    public Type getReturnType(Expression[] args) {
        return Compilation.typeObject;
    }
}

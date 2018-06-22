package gnu.xquery.util;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.Scope;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.LambdaExp;
import gnu.expr.QuoteExp;
import gnu.expr.Target;
import gnu.kawa.functions.ValuesMap;
import gnu.kawa.reflect.OccurrenceType;
import gnu.kawa.xml.AttributeAxis;
import gnu.kawa.xml.ChildAxis;
import gnu.kawa.xml.NodeSetType;
import gnu.kawa.xml.NodeType;
import gnu.kawa.xml.Nodes;
import gnu.kawa.xml.SelfAxis;
import gnu.kawa.xml.TreeScanner;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class RelativeStep extends MethodProc implements Inlineable {
    public static final RelativeStep relativeStep = new RelativeStep();

    RelativeStep() {
        setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateApplyRelativeStep");
    }

    public int numArgs() {
        return 8194;
    }

    public void apply(CallContext ctx) throws Throwable {
        Nodes values;
        Object arg = ctx.getNextArg();
        Procedure proc = (Procedure) ctx.getNextArg();
        Consumer out = ctx.consumer;
        if (arg instanceof Nodes) {
            values = (Nodes) arg;
        } else {
            values = new Nodes();
            Values.writeValues(arg, values);
        }
        int count = values.size();
        int it = 0;
        IntNum countObj = IntNum.make(count);
        RelativeStepFilter filter = new RelativeStepFilter(out);
        for (int pos = 1; pos <= count; pos++) {
            it = values.nextPos(it);
            proc.check3(values.getPosPrevious(it), IntNum.make(pos), countObj, ctx);
            Values.writeValues(ctx.runUntilValue(), filter);
        }
        filter.finish();
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        Expression exp1 = args[0];
        Expression exp2 = args[1];
        if (target instanceof IgnoreTarget) {
            exp1.compile(comp, target);
            exp2.compile(comp, target);
            return;
        }
        char expectedKind;
        Type rtype = exp.getTypeRaw();
        if (rtype == null) {
            rtype = Type.pointer_type;
        }
        int nodeCompare = NodeType.anyNodeTest.compare(OccurrenceType.itemPrimeType(rtype));
        if (nodeCompare >= 0) {
            expectedKind = 'N';
        } else if (nodeCompare == -3) {
            expectedKind = 'A';
        } else {
            expectedKind = ' ';
        }
        TreeScanner step = extractStep(exp2);
        if (step != null) {
            Type type1 = exp1.getType();
            if (((step instanceof ChildAxis) || (step instanceof AttributeAxis) || (step instanceof SelfAxis)) && ((type1 instanceof NodeSetType) || (expectedKind == 'N' && OccurrenceType.itemCountIsZeroOrOne(exp1.getType())))) {
                expectedKind = 'S';
            }
        }
        if (target instanceof ConsumerTarget) {
            Target mtarget;
            ClassType mclass;
            Variable mconsumer;
            Variable tconsumer;
            CodeAttr code = comp.getCode();
            Scope scope = code.pushScope();
            if (expectedKind == 'A' || expectedKind == 'S') {
                mtarget = target;
                mclass = null;
                mconsumer = null;
                tconsumer = null;
            } else {
                Method initMethod;
                if (expectedKind == 'N') {
                    mclass = ClassType.make("gnu.kawa.xml.SortedNodes");
                    initMethod = mclass.getDeclaredMethod("<init>", 0);
                } else {
                    mclass = ClassType.make("gnu.xquery.util.RelativeStepFilter");
                    initMethod = mclass.getDeclaredMethod("<init>", 1);
                }
                mconsumer = scope.addVariable(code, mclass, null);
                mtarget = new ConsumerTarget(mconsumer);
                code.emitNew(mclass);
                code.emitDup(mclass);
                tconsumer = ((ConsumerTarget) target).getConsumerVariable();
                if (expectedKind != 'N') {
                    code.emitLoad(tconsumer);
                }
                code.emitInvoke(initMethod);
                code.emitStore(mconsumer);
            }
            ValuesMap.compileInlined((LambdaExp) exp2, exp1, 1, null, comp, mtarget);
            if (expectedKind == 'N') {
                code.emitLoad(mconsumer);
                code.emitLoad(tconsumer);
                code.emitInvokeStatic(Compilation.typeValues.getDeclaredMethod("writeValues", 2));
            } else if (expectedKind == ' ') {
                code.emitLoad(mconsumer);
                code.emitInvoke(mclass.getDeclaredMethod("finish", 0));
            }
            code.popScope();
            return;
        }
        ConsumerTarget.compileUsingConsumer(exp, comp, target);
    }

    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }

    public static TreeScanner extractStep(Expression exp) {
        while (exp instanceof ApplyExp) {
            ApplyExp aexp = (ApplyExp) exp;
            Expression func = aexp.getFunction();
            if (func instanceof QuoteExp) {
                Object value = ((QuoteExp) func).getValue();
                if (value instanceof TreeScanner) {
                    return (TreeScanner) value;
                }
                if (value instanceof ValuesFilter) {
                    exp = aexp.getArgs()[0];
                }
            }
            return null;
        }
        return null;
    }
}

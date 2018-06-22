package gnu.xquery.util;

import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConsumerTarget;
import gnu.expr.Expression;
import gnu.expr.IgnoreTarget;
import gnu.expr.Inlineable;
import gnu.expr.LambdaExp;
import gnu.expr.Target;
import gnu.kawa.functions.NumberCompare;
import gnu.kawa.functions.ValuesMap;
import gnu.kawa.xml.SortedNodes;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.DFloNum;
import gnu.math.IntNum;

public class ValuesFilter extends MethodProc implements Inlineable {
    public static final ValuesFilter exprFilter = new ValuesFilter('P');
    public static final ValuesFilter forwardFilter = new ValuesFilter(Access.FIELD_CONTEXT);
    public static final Method matchesMethod = typeValuesFilter.getDeclaredMethod("matches", 2);
    public static final ValuesFilter reverseFilter = new ValuesFilter('R');
    public static final ClassType typeValuesFilter = ClassType.make("gnu.xquery.util.ValuesFilter");
    char kind;
    int last_or_position_needed = 2;

    public ValuesFilter(char kind) {
        this.kind = kind;
        setProperty(Procedure.validateApplyKey, "gnu.xquery.util.CompileMisc:validateApplyValuesFilter");
    }

    public static ValuesFilter get(char kind) {
        if (kind == Access.FIELD_CONTEXT) {
            return forwardFilter;
        }
        if (kind == 'R') {
            return reverseFilter;
        }
        return exprFilter;
    }

    public int numArgs() {
        return 8194;
    }

    public static boolean matches(Object result, long count) {
        if (result instanceof Values) {
            result = ((Values) result).canonicalize();
        }
        if (!(result instanceof Number)) {
            return BooleanValue.booleanValue(result);
        }
        if (result instanceof IntNum) {
            if (IntNum.compare((IntNum) result, count) == 0) {
                return true;
            }
            return false;
        } else if ((result instanceof Double) || (result instanceof Float) || (result instanceof DFloNum)) {
            if (((Number) result).doubleValue() != ((double) count)) {
                return false;
            }
            return true;
        } else if (!(result instanceof Long) && !(result instanceof Integer) && !(result instanceof Short) && !(result instanceof Byte)) {
            return NumberCompare.applyWithPromotion(8, IntNum.make(count), result);
        } else {
            if (count != ((Number) result).longValue()) {
                return false;
            }
            return true;
        }
    }

    public void apply(CallContext ctx) throws Throwable {
        Values values;
        Object arg = ctx.getNextArg();
        Procedure proc = (Procedure) ctx.getNextArg();
        Consumer out = ctx.consumer;
        if (this.kind != 'P') {
            Values nodes = new SortedNodes();
            Values.writeValues(arg, nodes);
            values = nodes;
        } else if (arg instanceof Values) {
            values = (Values) arg;
        } else {
            IntNum one = IntNum.one();
            if (matches(proc.apply3(arg, one, one), 1)) {
                out.writeObject(arg);
                return;
            }
            return;
        }
        int count = values.size();
        int it = 0;
        IntNum countObj = IntNum.make(count);
        int pmax = proc.maxArgs();
        int i = 0;
        while (i < count) {
            it = values.nextPos(it);
            Object dot = values.getPosPrevious(it);
            int pos = this.kind == 'R' ? count - i : i + 1;
            IntNum posObj = IntNum.make(pos);
            if (matches(pmax == 2 ? proc.apply2(dot, posObj) : proc.apply3(dot, posObj, countObj), (long) pos)) {
                out.writeObject(dot);
            }
            i++;
        }
    }

    public void compile(ApplyExp exp, Compilation comp, Target target) {
        Expression[] args = exp.getArgs();
        Expression exp1 = args[0];
        Expression exp2 = args[1];
        if (target instanceof IgnoreTarget) {
            exp1.compile(comp, target);
            exp2.compile(comp, target);
        } else if (!(exp2 instanceof LambdaExp)) {
            ApplyExp.compile(exp, comp, target);
        } else if (target instanceof ConsumerTarget) {
            ValuesMap.compileInlined((LambdaExp) exp2, exp1, 1, matchesMethod, comp, target);
        } else {
            ConsumerTarget.compileUsingConsumer(exp, comp, target);
        }
    }

    public Type getReturnType(Expression[] args) {
        return Type.pointer_type;
    }
}

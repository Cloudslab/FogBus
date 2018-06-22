package gnu.xquery.util;

import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class IntegerRange extends MethodProc {
    public static final IntNum MAX_INT = IntNum.make((int) ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    public static final IntNum MIN_INT = IntNum.make(Integer.MIN_VALUE);
    public static final IntegerRange integerRange = new IntegerRange("to");

    public IntegerRange(String name) {
        setName(name);
    }

    public static void integerRange(IntNum first, IntNum last, Consumer out) {
        if (IntNum.compare(first, MIN_INT) < 0 || IntNum.compare(last, MAX_INT) > 0) {
            while (IntNum.compare(first, last) <= 0) {
                out.writeObject(first);
                first = IntNum.add(first, 1);
            }
            return;
        }
        int fst = first.intValue();
        int lst = last.intValue();
        if (fst <= lst) {
            while (true) {
                out.writeInt(fst);
                if (fst != lst) {
                    fst++;
                } else {
                    return;
                }
            }
        }
    }

    public void apply(CallContext ctx) {
        Object first = ctx.getNextArg();
        Object last = ctx.getNextArg();
        ctx.lastArg();
        first = KNode.atomicValue(first);
        last = KNode.atomicValue(last);
        if (first != Values.empty && first != null && last != Values.empty && last != null) {
            if (first instanceof UntypedAtomic) {
                first = IntNum.valueOf(first.toString().trim(), 10);
            }
            if (last instanceof UntypedAtomic) {
                last = IntNum.valueOf(last.toString().trim(), 10);
            }
            integerRange((IntNum) first, (IntNum) last, ctx.consumer);
        }
    }
}

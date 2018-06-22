package gnu.xquery.util;

import gnu.kawa.functions.AddOp;
import gnu.lists.Sequence;
import gnu.lists.TreeList;
import gnu.mapping.Values;
import gnu.math.IntNum;

public class Reduce {
    public static Object sum(Object arg) throws Throwable {
        return sum(arg, IntNum.zero());
    }

    public static Object sum(Object arg, Object zero) throws Throwable {
        if (!(arg instanceof Values)) {
            return (Number) MinMax.convert(arg);
        }
        TreeList tlist = (TreeList) arg;
        int pos = 0;
        Object next = tlist.getPosNext(0);
        if (next == Sequence.eofValue) {
            return zero;
        }
        Object result = MinMax.convert(next);
        while (true) {
            pos = tlist.nextPos(pos);
            next = tlist.getPosNext(pos);
            if (next == Sequence.eofValue) {
                return result;
            }
            result = AddOp.apply2(1, result, MinMax.convert(next));
        }
    }
}

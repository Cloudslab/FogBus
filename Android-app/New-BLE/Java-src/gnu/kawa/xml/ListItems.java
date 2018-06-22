package gnu.kawa.xml;

import gnu.lists.AbstractSequence;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import java.util.List;

public class ListItems extends MethodProc {
    public static ListItems listItems = new ListItems();

    public void apply(CallContext ctx) {
        Consumer out = ctx.consumer;
        List arg = ctx.getNextArg();
        ctx.lastArg();
        List<Object> list = arg;
        if (arg instanceof AbstractSequence) {
            ((AbstractSequence) arg).consumePosRange(0, -1, out);
            return;
        }
        for (Object val : list) {
            Values.writeValues(val, out);
        }
    }
}

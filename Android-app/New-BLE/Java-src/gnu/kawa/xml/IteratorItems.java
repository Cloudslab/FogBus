package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import java.util.Iterator;

public class IteratorItems extends MethodProc {
    public static IteratorItems iteratorItems = new IteratorItems();

    public void apply(CallContext ctx) {
        Consumer out = ctx.consumer;
        Iterator arg = ctx.getNextArg();
        ctx.lastArg();
        Iterator iter = arg;
        while (iter.hasNext()) {
            Values.writeValues(iter.next(), out);
        }
    }
}

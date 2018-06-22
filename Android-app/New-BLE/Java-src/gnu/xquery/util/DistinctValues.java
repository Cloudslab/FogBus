package gnu.xquery.util;

import gnu.mapping.CallContext;
import gnu.mapping.Values;

public class DistinctValues {
    public static void distinctValues$X(Object values, NamedCollator coll, CallContext ctx) {
        Values.writeValues(values, new DistinctValuesConsumer(coll, ctx.consumer));
    }
}

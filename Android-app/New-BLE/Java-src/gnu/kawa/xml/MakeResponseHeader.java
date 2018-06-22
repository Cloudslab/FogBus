package gnu.kawa.xml;

import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;

public class MakeResponseHeader extends MethodProc {
    public static MakeResponseHeader makeResponseHeader = new MakeResponseHeader();

    public void apply(CallContext ctx) {
        String key = ctx.getNextArg().toString();
        Object val = ctx.getNextArg();
        ctx.lastArg();
        Consumer out = ctx.consumer;
        out.startAttribute(key);
        out.write(val.toString());
        out.endAttribute();
    }
}

package gnu.kawa.xml;

import android.support.v4.app.FragmentTransaction;
import gnu.lists.Consumer;
import gnu.lists.XConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Location;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;
import gnu.xml.TextUtils;

public class CommentConstructor extends MethodProc {
    public static final CommentConstructor commentConstructor = new CommentConstructor();

    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    public void apply(CallContext ctx) {
        Consumer saved = ctx.consumer;
        XConsumer out = NodeConstructor.pushNodeContext(ctx);
        StringBuffer sbuf = new StringBuffer();
        String endMarker = Location.UNBOUND;
        boolean first = true;
        int i = 0;
        while (true) {
            String arg = ctx.getNextArg(endMarker);
            if (arg == endMarker) {
                break;
            }
            if (arg instanceof Values) {
                Values vals = (Values) arg;
                int it = 0;
                while (true) {
                    it = vals.nextPos(it);
                    if (it == 0) {
                        break;
                    }
                    if (!first) {
                        sbuf.append(' ');
                    }
                    first = false;
                    TextUtils.stringValue(vals.getPosPrevious(it), sbuf);
                }
            } else {
                if (!first) {
                    try {
                        sbuf.append(' ');
                    } finally {
                        NodeConstructor.popNodeContext(saved, ctx);
                    }
                }
                first = false;
                TextUtils.stringValue(arg, sbuf);
            }
            i++;
        }
        int len = sbuf.length();
        char[] buf = new char[len];
        sbuf.getChars(0, len, buf, 0);
        out.writeComment(buf, 0, len);
    }
}

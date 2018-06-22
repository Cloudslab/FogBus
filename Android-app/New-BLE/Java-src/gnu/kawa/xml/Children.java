package gnu.kawa.xml;

import android.support.v4.app.FragmentTransaction;
import gnu.lists.Consumer;
import gnu.lists.PositionConsumer;
import gnu.lists.SeqPosition;
import gnu.lists.TreeList;
import gnu.lists.TreePosition;
import gnu.mapping.CallContext;
import gnu.mapping.MethodProc;
import gnu.mapping.Values;

public class Children extends MethodProc {
    public static final Children children = new Children();

    public int numArgs() {
        return FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
    }

    public static void children(TreeList tlist, int index, Consumer consumer) {
        int child = tlist.gotoChildrenStart(index);
        if (child >= 0) {
            int limit = tlist.nextDataIndex(index);
            while (true) {
                int ipos = child << 1;
                int next = tlist.nextNodeIndex(child, limit);
                int next0 = next;
                if (next == child) {
                    next = tlist.nextDataIndex(child);
                }
                if (next >= 0) {
                    if (consumer instanceof PositionConsumer) {
                        ((PositionConsumer) consumer).writePosition(tlist, ipos);
                    } else {
                        tlist.consumeIRange(child, next, consumer);
                    }
                    child = next;
                } else {
                    return;
                }
            }
        }
    }

    public static void children(Object node, Consumer consumer) {
        if (node instanceof TreeList) {
            children((TreeList) node, 0, consumer);
        } else if ((node instanceof SeqPosition) && !(node instanceof TreePosition)) {
            SeqPosition pos = (SeqPosition) node;
            if (pos.sequence instanceof TreeList) {
                children((TreeList) pos.sequence, pos.ipos >> 1, consumer);
            }
        }
    }

    public void apply(CallContext ctx) {
        Consumer consumer = ctx.consumer;
        TreeList node = ctx.getNextArg();
        ctx.lastArg();
        if (node instanceof Values) {
            TreeList tlist = node;
            int index = 0;
            while (true) {
                int kind = tlist.getNextKind(index << 1);
                if (kind != 0) {
                    if (kind == 32) {
                        children(tlist.getPosNext(index << 1), consumer);
                    } else {
                        children(tlist, index, consumer);
                    }
                    index = tlist.nextDataIndex(index);
                } else {
                    return;
                }
            }
        }
        children(node, consumer);
    }
}

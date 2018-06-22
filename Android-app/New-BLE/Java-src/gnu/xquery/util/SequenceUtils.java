package gnu.xquery.util;

import gnu.kawa.xml.KAttr;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.NodeType;
import gnu.lists.Consumer;
import gnu.mapping.CallContext;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.xml.NodeTree;

public class SequenceUtils {
    public static final NodeType textOrElement = new NodeType("element-or-text", 3);

    public static boolean isZeroOrOne(Object arg) {
        return !(arg instanceof Values) || ((Values) arg).isEmpty();
    }

    static Object coerceToZeroOrOne(Object arg, String functionName, int iarg) {
        if (isZeroOrOne(arg)) {
            return arg;
        }
        throw new WrongType(functionName, iarg, arg, "xs:item()?");
    }

    public static Object zeroOrOne(Object arg) {
        return coerceToZeroOrOne(arg, "zero-or-one", 1);
    }

    public static Object oneOrMore(Object arg) {
        if (!(arg instanceof Values) || !((Values) arg).isEmpty()) {
            return arg;
        }
        throw new IllegalArgumentException();
    }

    public static Object exactlyOne(Object arg) {
        if (!(arg instanceof Values)) {
            return arg;
        }
        throw new IllegalArgumentException();
    }

    public static boolean isEmptySequence(Object arg) {
        return (arg instanceof Values) && ((Values) arg).isEmpty();
    }

    public static boolean exists(Object arg) {
        return ((arg instanceof Values) && ((Values) arg).isEmpty()) ? false : true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void insertBefore$X(java.lang.Object r11, long r12, java.lang.Object r14, gnu.mapping.CallContext r15) {
        /*
        r4 = r15.consumer;
        r6 = 0;
        r8 = 0;
        r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1));
        if (r7 > 0) goto L_0x000b;
    L_0x0009:
        r12 = 1;
    L_0x000b:
        r7 = r11 instanceof gnu.mapping.Values;
        if (r7 == 0) goto L_0x0030;
    L_0x000f:
        r5 = r11;
        r5 = (gnu.mapping.Values) r5;
        r2 = 0;
        r0 = 0;
    L_0x0015:
        r3 = r5.nextPos(r2);
        if (r3 != 0) goto L_0x001d;
    L_0x001b:
        if (r6 == 0) goto L_0x0024;
    L_0x001d:
        r8 = 1;
        r0 = r0 + r8;
        r7 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1));
        if (r7 != 0) goto L_0x0028;
    L_0x0024:
        gnu.mapping.Values.writeValues(r14, r4);
        r6 = 1;
    L_0x0028:
        if (r3 != 0) goto L_0x002b;
    L_0x002a:
        return;
    L_0x002b:
        r5.consumePosRange(r2, r3, r4);
        r2 = r3;
        goto L_0x0015;
    L_0x0030:
        r8 = 1;
        r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1));
        if (r7 > 0) goto L_0x0039;
    L_0x0036:
        gnu.mapping.Values.writeValues(r14, r4);
    L_0x0039:
        r4.writeObject(r11);
        r8 = 1;
        r7 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1));
        if (r7 <= 0) goto L_0x002a;
    L_0x0042:
        gnu.mapping.Values.writeValues(r14, r4);
        goto L_0x002a;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.xquery.util.SequenceUtils.insertBefore$X(java.lang.Object, long, java.lang.Object, gnu.mapping.CallContext):void");
    }

    public static void remove$X(Object arg, long position, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            Values values = (Values) arg;
            int ipos = 0;
            long i = 0;
            while (true) {
                int next = values.nextPos(ipos);
                if (next != 0) {
                    i++;
                    if (i != position) {
                        values.consumePosRange(ipos, next, out);
                    }
                    ipos = next;
                } else {
                    return;
                }
            }
        } else if (position != 1) {
            out.writeObject(arg);
        }
    }

    public static void reverse$X(Object arg, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (arg instanceof Values) {
            int n;
            Values vals = (Values) arg;
            int ipos = 0;
            int[] poses = new int[100];
            int n2 = 0;
            while (true) {
                if (n2 >= poses.length) {
                    int[] t = new int[(n2 * 2)];
                    System.arraycopy(poses, 0, t, 0, n2);
                    poses = t;
                }
                n = n2 + 1;
                poses[n2] = ipos;
                ipos = vals.nextPos(ipos);
                if (ipos == 0) {
                    break;
                }
                n2 = n;
            }
            int i = n - 1;
            while (true) {
                i--;
                if (i >= 0) {
                    vals.consumePosRange(poses[i], poses[i + 1], out);
                } else {
                    return;
                }
            }
        }
        out.writeObject(arg);
    }

    public static void indexOf$X(Object seqParam, Object srchParam, NamedCollator collator, CallContext ctx) {
        Consumer out = ctx.consumer;
        if (seqParam instanceof Values) {
            Values vals = (Values) seqParam;
            int ipos = vals.startPos();
            int i = 1;
            while (true) {
                ipos = vals.nextPos(ipos);
                if (ipos != 0) {
                    if (Compare.apply(72, vals.getPosPrevious(ipos), srchParam, collator)) {
                        out.writeInt(i);
                    }
                    i++;
                } else {
                    return;
                }
            }
        } else if (Compare.apply(72, seqParam, srchParam, collator)) {
            out.writeInt(1);
        }
    }

    public static boolean deepEqualChildren(NodeTree seq1, int ipos1, NodeTree seq2, int ipos2, NamedCollator collator) {
        NodeType filter = textOrElement;
        int child1 = seq1.firstChildPos(ipos1, filter);
        int child2 = seq2.firstChildPos(ipos2, filter);
        while (child1 != 0 && child2 != 0) {
            if (!deepEqual(seq1, child1, seq2, child2, collator)) {
                return false;
            }
            child1 = seq1.nextMatching(child1, filter, -1, false);
            child2 = seq2.nextMatching(child2, filter, -1, false);
        }
        if (child1 == child2) {
            return true;
        }
        return false;
    }

    public static boolean deepEqual(NodeTree seq1, int ipos1, NodeTree seq2, int ipos2, NamedCollator collator) {
        int kind1 = seq1.getNextKind(ipos1);
        int kind2 = seq2.getNextKind(ipos2);
        switch (kind1) {
            case 33:
                if (kind1 != kind2) {
                    return false;
                }
                if (seq1.posLocalName(ipos1) != seq2.posLocalName(ipos2)) {
                    return false;
                }
                if (seq1.posNamespaceURI(ipos1) != seq2.posNamespaceURI(ipos2)) {
                    return false;
                }
                int attr1 = seq1.firstAttributePos(ipos1);
                int nattr1 = 0;
                while (attr1 != 0 && seq1.getNextKind(attr1) == 35) {
                    nattr1++;
                    String local = seq1.posLocalName(attr1);
                    int attr2 = seq2.getAttributeI(ipos2, seq1.posNamespaceURI(attr1), local);
                    if (attr2 == 0) {
                        return false;
                    }
                    if (!deepEqualItems(KNode.getNodeValue(seq1, attr1), KNode.getNodeValue(seq2, attr2), collator)) {
                        return false;
                    }
                    attr1 = seq1.nextPos(attr1);
                }
                if (nattr1 != seq2.getAttributeCount(ipos2)) {
                    return false;
                }
                break;
            case 34:
                break;
            case 35:
                if (seq1.posLocalName(ipos1) != seq2.posLocalName(ipos2) || seq1.posNamespaceURI(ipos1) != seq2.posNamespaceURI(ipos2)) {
                    return false;
                }
                return deepEqualItems(KAttr.getObjectValue(seq1, ipos1), KAttr.getObjectValue(seq2, ipos2), collator);
            case 37:
                if (seq1.posTarget(ipos1).equals(seq2.posTarget(ipos2))) {
                    return KNode.getNodeValue(seq1, ipos1).equals(KNode.getNodeValue(seq2, ipos2));
                }
                return false;
            default:
                if (kind1 != kind2) {
                    return false;
                }
                return KNode.getNodeValue(seq1, ipos1).equals(KNode.getNodeValue(seq2, ipos2));
        }
        return deepEqualChildren(seq1, ipos1, seq2, ipos2, collator);
    }

    public static boolean deepEqualItems(Object arg1, Object arg2, NamedCollator collator) {
        if (NumberValue.isNaN(arg1) && NumberValue.isNaN(arg2)) {
            return true;
        }
        return Compare.atomicCompare(8, arg1, arg2, collator);
    }

    public static boolean deepEqual(Object arg1, Object arg2, NamedCollator collator) {
        if (arg1 == arg2) {
            return true;
        }
        if (arg1 == null || arg1 == Values.empty) {
            return arg2 == null || arg2 == Values.empty;
        } else {
            if (arg2 == null || arg2 == Values.empty) {
                return false;
            }
            int ipos1 = 1;
            int ipos2 = 1;
            boolean is1seq = arg1 instanceof Values;
            boolean is2seq = arg2 instanceof Values;
            Values vals1 = is1seq ? (Values) arg1 : null;
            Values vals2 = is2seq ? (Values) arg2 : null;
            boolean first = true;
            while (true) {
                if (is1seq) {
                    if (first) {
                        ipos1 = vals1.startPos();
                    }
                    ipos1 = vals1.nextPos(ipos1);
                }
                if (is2seq) {
                    if (first) {
                        ipos2 = vals2.startPos();
                    }
                    ipos2 = vals2.nextPos(ipos2);
                }
                if (ipos1 != 0 && ipos2 != 0) {
                    KNode item1;
                    KNode item2;
                    if (is1seq) {
                        item1 = vals1.getPosPrevious(ipos1);
                    } else {
                        item1 = arg1;
                    }
                    if (is2seq) {
                        item2 = vals2.getPosPrevious(ipos2);
                    } else {
                        item2 = arg2;
                    }
                    if (!(item1 instanceof KNode) && !(item2 instanceof KNode)) {
                        try {
                            if (!deepEqualItems(arg1, arg2, collator)) {
                                return false;
                            }
                        } catch (Throwable th) {
                            return false;
                        }
                    } else if ((item1 instanceof KNode) && (item2 instanceof KNode)) {
                        KNode node1 = item1;
                        KNode node2 = item2;
                        if (!deepEqual((NodeTree) node1.sequence, node1.ipos, (NodeTree) node2.sequence, node2.ipos, collator)) {
                            return false;
                        }
                    }
                    if (first) {
                        first = false;
                        if (!is1seq) {
                            ipos1 = 0;
                        }
                        if (!is2seq) {
                            ipos2 = 0;
                        }
                    }
                } else if (ipos1 == ipos2) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }
}

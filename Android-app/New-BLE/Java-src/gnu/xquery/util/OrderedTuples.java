package gnu.xquery.util;

import gnu.kawa.functions.NumberCompare;
import gnu.kawa.xml.KNode;
import gnu.kawa.xml.UntypedAtomic;
import gnu.lists.FilterConsumer;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;

public class OrderedTuples extends FilterConsumer {
    Procedure body;
    Object[] comps;
    int first;
    int f15n;
    int[] next;
    Object[] tuples = new Object[10];

    public void writeObject(Object v) {
        if (this.f15n >= this.tuples.length) {
            Object[] tmp = new Object[(this.f15n * 2)];
            System.arraycopy(this.tuples, 0, tmp, 0, this.f15n);
            this.tuples = tmp;
        }
        Object[] objArr = this.tuples;
        int i = this.f15n;
        this.f15n = i + 1;
        objArr[i] = v;
    }

    OrderedTuples() {
        super(null);
    }

    public static OrderedTuples make$V(Procedure body, Object[] comps) {
        OrderedTuples tuples = new OrderedTuples();
        tuples.comps = comps;
        tuples.body = body;
        return tuples;
    }

    public void run$X(CallContext ctx) throws Throwable {
        this.first = listsort(0);
        emit(ctx);
    }

    void emit(CallContext ctx) throws Throwable {
        int p = this.first;
        while (p >= 0) {
            emit(p, ctx);
            p = this.next[p];
        }
    }

    void emit(int index, CallContext ctx) throws Throwable {
        this.body.checkN((Object[]) this.tuples[index], ctx);
        ctx.runUntilDone();
    }

    int cmp(int a, int b) throws Throwable {
        for (int i = 0; i < this.comps.length; i += 3) {
            Procedure comparator = this.comps[i];
            String flags = this.comps[i + 1];
            NamedCollator collator = this.comps[i + 2];
            if (collator == null) {
                collator = NamedCollator.codepointCollation;
            }
            Object val1 = comparator.applyN((Object[]) this.tuples[a]);
            Object val2 = comparator.applyN((Object[]) this.tuples[b]);
            val1 = KNode.atomicValue(val1);
            val2 = KNode.atomicValue(val2);
            if (val1 instanceof UntypedAtomic) {
                val1 = val1.toString();
            }
            if (val2 instanceof UntypedAtomic) {
                val2 = val2.toString();
            }
            boolean empty1 = SequenceUtils.isEmptySequence(val1);
            boolean empty2 = SequenceUtils.isEmptySequence(val2);
            if (!empty1 || !empty2) {
                int c;
                if (empty1 || empty2) {
                    boolean z;
                    if (flags.charAt(1) == 'L') {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (empty1 == z) {
                        c = -1;
                    } else {
                        c = 1;
                    }
                } else {
                    boolean isNaN1 = (val1 instanceof Number) && Double.isNaN(((Number) val1).doubleValue());
                    boolean isNaN2 = (val2 instanceof Number) && Double.isNaN(((Number) val2).doubleValue());
                    if (!isNaN1 || !isNaN2) {
                        if (isNaN1 || isNaN2) {
                            c = isNaN1 == (flags.charAt(1) == 'L') ? -1 : 1;
                        } else if ((val1 instanceof Number) && (val2 instanceof Number)) {
                            c = NumberCompare.compare(val1, val2, false);
                        } else {
                            c = collator.compare(val1.toString(), val2.toString());
                        }
                    }
                }
                if (c != 0) {
                    if (flags.charAt(0) == 'A') {
                        return c;
                    }
                    return -c;
                }
            }
        }
        return 0;
    }

    int listsort(int list) throws Throwable {
        if (this.f15n == 0) {
            return -1;
        }
        this.next = new int[this.f15n];
        int i = 1;
        while (i != this.f15n) {
            this.next[i - 1] = i;
            i++;
        }
        this.next[i - 1] = -1;
        int insize = 1;
        while (true) {
            int p = list;
            list = -1;
            int tail = -1;
            int nmerges = 0;
            while (p >= 0) {
                nmerges++;
                int q = p;
                int psize = 0;
                for (i = 0; i < insize; i++) {
                    psize++;
                    q = this.next[q];
                    if (q < 0) {
                        break;
                    }
                }
                int qsize = insize;
                while (true) {
                    if (psize > 0 || (qsize > 0 && q >= 0)) {
                        int e;
                        if (psize == 0) {
                            e = q;
                            q = this.next[q];
                            qsize--;
                        } else if (qsize == 0 || q < 0) {
                            e = p;
                            p = this.next[p];
                            psize--;
                        } else if (cmp(p, q) <= 0) {
                            e = p;
                            p = this.next[p];
                            psize--;
                        } else {
                            e = q;
                            q = this.next[q];
                            qsize--;
                        }
                        if (tail >= 0) {
                            this.next[tail] = e;
                        } else {
                            list = e;
                        }
                        tail = e;
                    }
                }
                p = q;
            }
            this.next[tail] = -1;
            if (nmerges <= 1) {
                return list;
            }
            insize *= 2;
        }
    }
}

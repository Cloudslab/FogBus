package gnu.lists;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.List;

public class LList extends ExtSequence implements Sequence, Externalizable, Comparable {
    public static final LList Empty = new LList();

    public static int listLength(Object obj, boolean allowOtherSequence) {
        int n = 0;
        Object slow = obj;
        LList fast = obj;
        while (fast != Empty) {
            if (fast instanceof Pair) {
                Pair fast_pair = (Pair) fast;
                if (fast_pair.cdr == Empty) {
                    return n + 1;
                }
                if (fast == slow && n > 0) {
                    return -1;
                }
                if (!(fast_pair.cdr instanceof Pair)) {
                    n++;
                    fast = fast_pair.cdr;
                } else if (!(slow instanceof Pair)) {
                    return -2;
                } else {
                    slow = ((Pair) slow).cdr;
                    fast = ((Pair) fast_pair.cdr).cdr;
                    n += 2;
                }
            } else if (!(fast instanceof Sequence) || !allowOtherSequence) {
                return -2;
            } else {
                int j = fast.size();
                if (j >= 0) {
                    j += n;
                }
                return j;
            }
        }
        return n;
    }

    public boolean equals(Object obj) {
        return this == obj;
    }

    public int compareTo(Object obj) {
        return obj == Empty ? 0 : -1;
    }

    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public SeqPosition getIterator(int index) {
        return new LListPosition(this, index, false);
    }

    public int createPos(int index, boolean isAfter) {
        return PositionManager.manager.register(new LListPosition(this, index, isAfter));
    }

    public int createRelativePos(int pos, int delta, boolean isAfter) {
        boolean old_after = isAfterPos(pos);
        if (delta < 0 || pos == 0) {
            return super.createRelativePos(pos, delta, isAfter);
        }
        if (delta == 0) {
            if (isAfter == old_after) {
                return copyPos(pos);
            }
            if (isAfter && !old_after) {
                return super.createRelativePos(pos, delta, isAfter);
            }
        }
        if (pos < 0) {
            throw new IndexOutOfBoundsException();
        }
        LListPosition old = (LListPosition) PositionManager.getPositionObject(pos);
        if (old.xpos == null) {
            return super.createRelativePos(pos, delta, isAfter);
        }
        LListPosition it = new LListPosition(old);
        Pair it_xpos = it.xpos;
        int it_ipos = it.ipos;
        if (isAfter && !old_after) {
            delta--;
            it_ipos += 3;
        }
        if (!isAfter && old_after) {
            delta++;
            it_ipos -= 3;
        }
        while (it_xpos instanceof Pair) {
            delta--;
            if (delta < 0) {
                it.ipos = it_ipos;
                it.xpos = it_xpos;
                return PositionManager.manager.register(it);
            }
            it_ipos += 2;
            it_xpos = it_xpos.cdr;
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean hasNext(int ipos) {
        return false;
    }

    public int nextPos(int ipos) {
        return 0;
    }

    public Object getPosNext(int ipos) {
        return eofValue;
    }

    public Object getPosPrevious(int ipos) {
        return eofValue;
    }

    protected void setPosNext(int ipos, Object value) {
        if (ipos > 0) {
            PositionManager.getPositionObject(ipos).setNext(value);
        } else if (ipos == -1 || !(this instanceof Pair)) {
            throw new IndexOutOfBoundsException();
        } else {
            ((Pair) this).car = value;
        }
    }

    protected void setPosPrevious(int ipos, Object value) {
        if (ipos > 0) {
            PositionManager.getPositionObject(ipos).setPrevious(value);
        } else if (ipos == 0 || !(this instanceof Pair)) {
            throw new IndexOutOfBoundsException();
        } else {
            ((Pair) this).lastPair().car = value;
        }
    }

    public Object get(int index) {
        throw new IndexOutOfBoundsException();
    }

    public static final int length(Object arg) {
        int count = 0;
        while (arg instanceof Pair) {
            count++;
            arg = ((Pair) arg).cdr;
        }
        return count;
    }

    public static LList makeList(List vals) {
        LList result = Empty;
        Pair last = null;
        for (Object pair : vals) {
            LList pair2 = new Pair(pair, Empty);
            if (last == null) {
                result = pair2;
            } else {
                last.cdr = pair2;
            }
            LList last2 = pair2;
        }
        return result;
    }

    public static LList makeList(Object[] vals, int offset, int length) {
        int i = length;
        LList result = Empty;
        while (true) {
            i--;
            if (i < 0) {
                return result;
            }
            result = new Pair(vals[offset + i], result);
        }
    }

    public static LList makeList(Object[] vals, int offset) {
        int i = vals.length - offset;
        LList result = Empty;
        while (true) {
            i--;
            if (i < 0) {
                return result;
            }
            result = new Pair(vals[offset + i], result);
        }
    }

    public void consume(Consumer out) {
        LList list = this;
        out.startElement("list");
        while (list instanceof Pair) {
            if (list != this) {
                out.write(32);
            }
            Pair pair = (Pair) list;
            out.writeObject(pair.car);
            list = pair.cdr;
        }
        if (list != Empty) {
            out.write(32);
            out.write(". ");
            out.writeObject(checkNonList(list));
        }
        out.endElement();
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

    public void writeExternal(ObjectOutput out) throws IOException {
    }

    public Object readResolve() throws ObjectStreamException {
        return Empty;
    }

    public static Pair list1(Object arg1) {
        return new Pair(arg1, Empty);
    }

    public static Pair list2(Object arg1, Object arg2) {
        return new Pair(arg1, new Pair(arg2, Empty));
    }

    public static Pair list3(Object arg1, Object arg2, Object arg3) {
        return new Pair(arg1, new Pair(arg2, new Pair(arg3, Empty)));
    }

    public static Pair list4(Object arg1, Object arg2, Object arg3, Object arg4) {
        return new Pair(arg1, new Pair(arg2, new Pair(arg3, new Pair(arg4, Empty))));
    }

    public static Pair chain1(Pair old, Object arg1) {
        Pair p1 = new Pair(arg1, Empty);
        old.cdr = p1;
        return p1;
    }

    public static Pair chain4(Pair old, Object arg1, Object arg2, Object arg3, Object arg4) {
        Pair p4 = new Pair(arg4, Empty);
        old.cdr = new Pair(arg1, new Pair(arg2, new Pair(arg3, p4)));
        return p4;
    }

    public static LList reverseInPlace(Object list) {
        LList prev = Empty;
        LList list2;
        while (list2 != Empty) {
            LList pair = (Pair) list2;
            list2 = pair.cdr;
            pair.cdr = prev;
            prev = pair;
        }
        return prev;
    }

    public static Object listTail(Object list, int count) {
        while (true) {
            count--;
            if (count < 0) {
                Pair list2;
                return list2;
            }
            if (list2 instanceof Pair) {
                list2 = list2.cdr;
            } else {
                throw new IndexOutOfBoundsException("List is too short.");
            }
        }
    }

    public static Object consX(Object[] args) {
        Object first = args[0];
        int n = args.length - 1;
        if (n <= 0) {
            return first;
        }
        Pair result = new Pair(first, null);
        Pair prev = result;
        for (int i = 1; i < n; i++) {
            Pair next = new Pair(args[i], null);
            prev.cdr = next;
            prev = next;
        }
        prev.cdr = args[n];
        return result;
    }

    public String toString() {
        LList rest = this;
        int i = 0;
        StringBuffer sbuf = new StringBuffer(100);
        sbuf.append('(');
        while (rest != Empty) {
            if (i > 0) {
                sbuf.append(' ');
            }
            if (i < 10) {
                if (!(rest instanceof Pair)) {
                    sbuf.append(". ");
                    sbuf.append(checkNonList(rest));
                    break;
                }
                Pair pair = (Pair) rest;
                sbuf.append(pair.car);
                rest = pair.cdr;
                i++;
            } else {
                sbuf.append("...");
                break;
            }
        }
        sbuf.append(')');
        return sbuf.toString();
    }

    public static Object checkNonList(Object rest) {
        return rest instanceof LList ? "#<not a pair>" : rest;
    }
}

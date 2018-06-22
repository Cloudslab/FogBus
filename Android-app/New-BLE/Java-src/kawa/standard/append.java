package kawa.standard;

import gnu.lists.LList;
import gnu.lists.Pair;
import gnu.mapping.ProcedureN;
import gnu.mapping.WrongType;

public class append extends ProcedureN {
    public static final append append = new append();

    static {
        append.setName("append");
    }

    public Object applyN(Object[] args) {
        return append$V(args);
    }

    public static Object append$V(Object[] args) {
        int count = args.length;
        if (count == 0) {
            return LList.Empty;
        }
        int i = count - 1;
        Pair pair = args[count - 1];
        while (true) {
            i--;
            if (i < 0) {
                return pair;
            }
            LList list = args[i];
            Pair last = null;
            Pair pair2 = null;
            while (list instanceof Pair) {
                Pair pair3;
                Pair list_pair = (Pair) list;
                Pair new_pair = new Pair(list_pair.getCar(), null);
                if (last == null) {
                    pair3 = new_pair;
                } else {
                    last.setCdr(new_pair);
                    pair3 = pair2;
                }
                last = new_pair;
                list = list_pair.getCdr();
                pair2 = pair3;
            }
            if (list != LList.Empty) {
                throw new WrongType(append, i + 1, args[i], "list");
            }
            if (last != null) {
                last.setCdr(pair);
            } else {
                pair2 = pair;
            }
            pair = pair2;
        }
    }
}

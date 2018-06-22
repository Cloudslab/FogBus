package kawa.lang;

import gnu.lists.Consumer;
import gnu.lists.Pair;

public class VarListPat extends Pattern {
    int min_length;

    public VarListPat(int min) {
        this.min_length = min;
    }

    public boolean match(Object obj, Object[] vars, int start_vars) {
        Pair obj2;
        int i = 0;
        while (i < this.min_length) {
            if (!(obj2 instanceof Pair)) {
                return false;
            }
            Pair p = obj2;
            vars[start_vars + i] = p.getCar();
            obj2 = p.getCdr();
            i++;
        }
        vars[start_vars + i] = obj2;
        return true;
    }

    public int varCount() {
        return this.min_length + 1;
    }

    public void print(Consumer out) {
        out.write("#<varlist-pattern min:");
        out.writeInt(this.min_length);
        out.write(62);
    }
}

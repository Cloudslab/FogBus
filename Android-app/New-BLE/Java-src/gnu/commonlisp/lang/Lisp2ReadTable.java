package gnu.commonlisp.lang;

import gnu.kawa.lispexpr.ReadTable;

/* compiled from: Lisp2 */
class Lisp2ReadTable extends ReadTable {
    Lisp2ReadTable() {
    }

    protected Object makeSymbol(String name) {
        return Lisp2.asSymbol(name.intern());
    }
}

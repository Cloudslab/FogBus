package gnu.text;

import gnu.kawa.util.AbstractWeakHashTable;
import gnu.kawa.util.AbstractWeakHashTable.WEntry;

/* compiled from: Char */
class CharMap extends AbstractWeakHashTable<Char, Char> {
    CharMap() {
    }

    public Char get(int key) {
        Char val;
        cleanup();
        for (WEntry<Char, Char> node = ((WEntry[]) this.table)[hashToIndex(key)]; node != null; node = node.next) {
            val = (Char) node.getValue();
            if (val != null && val.intValue() == key) {
                return val;
            }
        }
        val = new Char(key);
        super.put(val, val);
        return val;
    }

    protected Char getKeyFromValue(Char ch) {
        return ch;
    }

    protected boolean matches(Char oldValue, Char newValue) {
        return oldValue.intValue() == newValue.intValue();
    }
}

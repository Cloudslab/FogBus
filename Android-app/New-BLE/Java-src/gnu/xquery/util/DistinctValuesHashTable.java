package gnu.xquery.util;

import gnu.kawa.util.GeneralHashTable;
import gnu.math.Numeric;
import gnu.math.RealNum;

/* compiled from: DistinctValues */
class DistinctValuesHashTable extends GeneralHashTable {
    NamedCollator collator;

    public DistinctValuesHashTable(NamedCollator collator) {
        this.collator = collator;
    }

    public int hash(Object key) {
        if (key == null) {
            return 0;
        }
        if (!(key instanceof Number) || (!(key instanceof RealNum) && (key instanceof Numeric))) {
            return key.hashCode();
        }
        int hash = Float.floatToIntBits(((Number) key).floatValue());
        if (hash == Integer.MIN_VALUE) {
            return 0;
        }
        return hash;
    }

    public boolean matches(Object value1, Object value2) {
        if (value1 == value2) {
            return true;
        }
        if (NumberValue.isNaN(value1) && NumberValue.isNaN(value2)) {
            return true;
        }
        return Compare.apply(72, value1, value2, this.collator);
    }
}

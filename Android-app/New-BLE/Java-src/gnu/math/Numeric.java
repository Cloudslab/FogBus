package gnu.math;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class Numeric extends Number {
    public static final int CEILING = 2;
    public static final int FLOOR = 1;
    public static final int NONNEG_MOD = 5;
    public static final int ROUND = 4;
    public static final int TRUNCATE = 3;

    public abstract Numeric abs();

    public abstract Numeric add(Object obj, int i);

    public abstract Numeric div(Object obj);

    public abstract boolean isExact();

    public abstract boolean isZero();

    public abstract Numeric mul(Object obj);

    public abstract Numeric neg();

    public abstract String toString(int i);

    public float floatValue() {
        return (float) doubleValue();
    }

    public int intValue() {
        return (int) longValue();
    }

    public long longValue() {
        return (long) doubleValue();
    }

    public final Numeric add(Object obj) {
        return add(obj, 1);
    }

    public final Numeric sub(Object obj) {
        return add(obj, -1);
    }

    public String toString() {
        return toString(10);
    }

    public static Numeric asNumericOrNull(Object value) {
        if (value instanceof Numeric) {
            return (Numeric) value;
        }
        if ((value instanceof BigInteger) || (value instanceof Long) || (value instanceof Short) || (value instanceof Byte) || (value instanceof Integer)) {
            return IntNum.asIntNumOrNull(value);
        }
        if (value instanceof BigDecimal) {
            return RatNum.asRatNumOrNull(value);
        }
        if ((value instanceof Float) || (value instanceof Double)) {
            return new DFloNum(((Number) value).doubleValue());
        }
        return null;
    }

    public Numeric toExact() {
        return this;
    }

    public Numeric toInexact() {
        return this;
    }

    public int compare(Object obj) {
        return -3;
    }

    public int compareReversed(Numeric x) {
        throw new IllegalArgumentException();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof Numeric) && compare(obj) == 0) {
            return true;
        }
        return false;
    }

    public boolean grt(Object x) {
        return compare(x) > 0;
    }

    public boolean geq(Object x) {
        return compare(x) >= 0;
    }

    public Numeric addReversed(Numeric x, int k) {
        throw new IllegalArgumentException();
    }

    public Numeric mulReversed(Numeric x) {
        throw new IllegalArgumentException();
    }

    public Numeric divReversed(Numeric x) {
        throw new IllegalArgumentException();
    }

    public Numeric div_inv() {
        return IntNum.one().div(this);
    }

    public Numeric mul_ident() {
        return IntNum.one();
    }

    public Numeric power(IntNum y) {
        if (y.isNegative()) {
            return power(IntNum.neg(y)).div_inv();
        }
        Numeric pow2 = this;
        Numeric r = null;
        while (true) {
            if (y.isOdd()) {
                r = r == null ? pow2 : r.mul(pow2);
            }
            y = IntNum.shift(y, -1);
            if (y.isZero()) {
                break;
            }
            pow2 = pow2.mul(pow2);
        }
        if (r == null) {
            return mul_ident();
        }
        return r;
    }
}

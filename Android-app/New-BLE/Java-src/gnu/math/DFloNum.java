package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DFloNum extends RealNum implements Externalizable {
    private static final DFloNum one = new DFloNum(1.0d);
    double value;

    public DFloNum(double value) {
        this.value = value;
    }

    public DFloNum(String s) throws NumberFormatException {
        this.value = Double.valueOf(s).doubleValue();
        if (this.value == 0.0d && s.charAt(0) == '-') {
            this.value = -0.0d;
        }
    }

    public static DFloNum make(double value) {
        return new DFloNum(value);
    }

    public static DFloNum asDFloNumOrNull(Object value) {
        if (value instanceof DFloNum) {
            return (DFloNum) value;
        }
        if ((value instanceof RealNum) || (value instanceof Number)) {
            return new DFloNum(((Number) value).doubleValue());
        }
        return null;
    }

    public final double doubleValue() {
        return this.value;
    }

    public long longValue() {
        return (long) this.value;
    }

    public int hashCode() {
        return (int) this.value;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof DFloNum) && Double.doubleToLongBits(((DFloNum) obj).value) == Double.doubleToLongBits(this.value);
    }

    public Numeric add(Object y, int k) {
        if (y instanceof RealNum) {
            return new DFloNum(this.value + (((double) k) * ((RealNum) y).doubleValue()));
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).addReversed(this, k);
        }
        throw new IllegalArgumentException();
    }

    public Numeric addReversed(Numeric x, int k) {
        if (x instanceof RealNum) {
            return new DFloNum(((RealNum) x).doubleValue() + (((double) k) * this.value));
        }
        throw new IllegalArgumentException();
    }

    public Numeric mul(Object y) {
        if (y instanceof RealNum) {
            return new DFloNum(this.value * ((RealNum) y).doubleValue());
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).mulReversed(this);
        }
        throw new IllegalArgumentException();
    }

    public Numeric mulReversed(Numeric x) {
        if (x instanceof RealNum) {
            return new DFloNum(((RealNum) x).doubleValue() * this.value);
        }
        throw new IllegalArgumentException();
    }

    public static final DFloNum one() {
        return one;
    }

    public Numeric div(Object y) {
        if (y instanceof RealNum) {
            return new DFloNum(this.value / ((RealNum) y).doubleValue());
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).divReversed(this);
        }
        throw new IllegalArgumentException();
    }

    public Numeric divReversed(Numeric x) {
        if (x instanceof RealNum) {
            return new DFloNum(((RealNum) x).doubleValue() / this.value);
        }
        throw new IllegalArgumentException();
    }

    public Numeric power(IntNum y) {
        return new DFloNum(Math.pow(doubleValue(), y.doubleValue()));
    }

    public boolean isNegative() {
        return this.value < 0.0d;
    }

    public Numeric neg() {
        return new DFloNum(-this.value);
    }

    public int sign() {
        if (this.value > 0.0d) {
            return 1;
        }
        if (this.value < 0.0d) {
            return -1;
        }
        return this.value == 0.0d ? 0 : -2;
    }

    public static int compare(double x, double y) {
        if (x > y) {
            return 1;
        }
        if (x < y) {
            return -1;
        }
        return x == y ? 0 : -2;
    }

    public static int compare(IntNum x_num, IntNum x_den, double y) {
        int result = 1;
        if (Double.isNaN(y)) {
            return -2;
        }
        if (Double.isInfinite(y)) {
            if (y >= 0.0d) {
                result = -1;
            }
            if (!x_den.isZero()) {
                return result;
            }
            if (x_num.isZero()) {
                return -2;
            }
            result >>= 1;
            if (x_num.isNegative()) {
                return result;
            }
            return result ^ -1;
        }
        long bits = Double.doubleToLongBits(y);
        boolean neg = bits < 0;
        int exp = ((int) (bits >> 52)) & 2047;
        bits &= 4503599627370495L;
        if (exp == 0) {
            bits <<= 1;
        } else {
            bits |= 4503599627370496L;
        }
        if (neg) {
            bits = -bits;
        }
        IntNum y_num = IntNum.make(bits);
        if (exp >= 1075) {
            y_num = IntNum.shift(y_num, exp - 1075);
        } else {
            x_num = IntNum.shift(x_num, 1075 - exp);
        }
        return IntNum.compare(x_num, IntNum.times(y_num, x_den));
    }

    public int compare(Object obj) {
        if (!(obj instanceof RatNum)) {
            return compare(this.value, ((RealNum) obj).doubleValue());
        }
        RatNum y_rat = (RatNum) obj;
        int i = compare(y_rat.numerator(), y_rat.denominator(), this.value);
        if (i < -1) {
            return i;
        }
        return -i;
    }

    public int compareReversed(Numeric x) {
        if (!(x instanceof RatNum)) {
            return compare(((RealNum) x).doubleValue(), this.value);
        }
        RatNum x_rat = (RatNum) x;
        return compare(x_rat.numerator(), x_rat.denominator(), this.value);
    }

    public boolean isExact() {
        return false;
    }

    public boolean isZero() {
        return this.value == 0.0d;
    }

    public static RatNum toExact(double value) {
        int i = 1;
        if (Double.isInfinite(value)) {
            if (value < 0.0d) {
                i = -1;
            }
            return RatNum.infinity(i);
        } else if (Double.isNaN(value)) {
            throw new ArithmeticException("cannot convert NaN to exact rational");
        } else {
            long bits = Double.doubleToLongBits(value);
            boolean neg = bits < 0;
            int exp = ((int) (bits >> 52)) & 2047;
            bits &= 4503599627370495L;
            if (exp == 0) {
                bits <<= 1;
            } else {
                bits |= 4503599627370496L;
            }
            if (neg) {
                bits = -bits;
            }
            IntNum mant = IntNum.make(bits);
            if (exp >= 1075) {
                return IntNum.shift(mant, exp - 1075);
            }
            return RatNum.make(mant, IntNum.shift(IntNum.one(), 1075 - exp));
        }
    }

    public String toString() {
        if (this.value == Double.POSITIVE_INFINITY) {
            return "+inf.0";
        }
        if (this.value == Double.NEGATIVE_INFINITY) {
            return "-inf.0";
        }
        return Double.isNaN(this.value) ? "+nan.0" : Double.toString(this.value);
    }

    public String toString(int radix) {
        if (radix == 10) {
            return toString();
        }
        return "#d" + toString();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(this.value);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.value = in.readDouble();
    }
}

package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class IntFraction extends RatNum implements Externalizable {
    IntNum den;
    IntNum num;

    IntFraction() {
    }

    public IntFraction(IntNum num, IntNum den) {
        this.num = num;
        this.den = den;
    }

    public final IntNum numerator() {
        return this.num;
    }

    public final IntNum denominator() {
        return this.den;
    }

    public final boolean isNegative() {
        return this.num.isNegative();
    }

    public final int sign() {
        return this.num.sign();
    }

    public final int compare(Object obj) {
        if (obj instanceof RatNum) {
            return RatNum.compare(this, (RatNum) obj);
        }
        return ((RealNum) obj).compareReversed(this);
    }

    public int compareReversed(Numeric x) {
        return RatNum.compare((RatNum) x, this);
    }

    public Numeric add(Object y, int k) {
        if (y instanceof RatNum) {
            return RatNum.add(this, (RatNum) y, k);
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).addReversed(this, k);
        }
        throw new IllegalArgumentException();
    }

    public Numeric addReversed(Numeric x, int k) {
        if (x instanceof RatNum) {
            return RatNum.add((RatNum) x, this, k);
        }
        throw new IllegalArgumentException();
    }

    public Numeric mul(Object y) {
        if (y instanceof RatNum) {
            return RatNum.times(this, (RatNum) y);
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).mulReversed(this);
        }
        throw new IllegalArgumentException();
    }

    public Numeric mulReversed(Numeric x) {
        if (x instanceof RatNum) {
            return RatNum.times((RatNum) x, this);
        }
        throw new IllegalArgumentException();
    }

    public Numeric div(Object y) {
        if (y instanceof RatNum) {
            return RatNum.divide(this, (RatNum) y);
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).divReversed(this);
        }
        throw new IllegalArgumentException();
    }

    public Numeric divReversed(Numeric x) {
        if (x instanceof RatNum) {
            return RatNum.divide((RatNum) x, this);
        }
        throw new IllegalArgumentException();
    }

    public static IntFraction neg(IntFraction x) {
        return new IntFraction(IntNum.neg(x.numerator()), x.denominator());
    }

    public Numeric neg() {
        return neg(this);
    }

    public long longValue() {
        return toExactInt(4).longValue();
    }

    public double doubleValue() {
        boolean neg = this.num.isNegative();
        if (!this.den.isZero()) {
            IntNum n = this.num;
            if (neg) {
                n = IntNum.neg(n);
            }
            int num_len = n.intLength();
            int den_len = this.den.intLength();
            int exp = 0;
            if (num_len < den_len + 54) {
                exp = (den_len + 54) - num_len;
                n = IntNum.shift(n, exp);
                exp = -exp;
            }
            IntNum quot = new IntNum();
            IntNum remainder = new IntNum();
            IntNum.divide(n, this.den, quot, remainder, 3);
            return quot.canonicalize().roundToDouble(exp, neg, !remainder.canonicalize().isZero());
        } else if (neg) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return this.num.isZero() ? Double.NaN : Double.POSITIVE_INFINITY;
        }
    }

    public String toString(int radix) {
        return this.num.toString(radix) + '/' + this.den.toString(radix);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.num);
        out.writeObject(this.den);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.num = (IntNum) in.readObject();
        this.den = (IntNum) in.readObject();
    }
}

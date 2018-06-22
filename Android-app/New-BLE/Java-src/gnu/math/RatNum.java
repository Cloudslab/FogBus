package gnu.math;

import java.math.BigDecimal;

public abstract class RatNum extends RealNum {
    public static final IntNum ten_exp_9 = IntNum.make(1000000000);

    public abstract IntNum denominator();

    public abstract IntNum numerator();

    public static RatNum make(IntNum num, IntNum den) {
        IntNum g = IntNum.gcd(num, den);
        if (den.isNegative()) {
            g = IntNum.neg(g);
        }
        if (!g.isOne()) {
            num = IntNum.quotient(num, g);
            den = IntNum.quotient(den, g);
        }
        return den.isOne() ? num : new IntFraction(num, den);
    }

    public static RatNum valueOf(BigDecimal value) {
        int i;
        IntNum scaleVal;
        RatNum v = IntNum.valueOf(value.unscaledValue().toString(), 10);
        int scale = value.scale();
        while (scale >= 9) {
            v = divide(v, ten_exp_9);
            scale -= 9;
        }
        while (scale <= -9) {
            v = times(v, ten_exp_9);
            scale += 9;
        }
        if (scale > 0) {
            i = scale;
        } else {
            i = -scale;
        }
        switch (i) {
            case 1:
                scaleVal = IntNum.make(10);
                break;
            case 2:
                scaleVal = IntNum.make(100);
                break;
            case 3:
                scaleVal = IntNum.make(1000);
                break;
            case 4:
                scaleVal = IntNum.make(10000);
                break;
            case 5:
                scaleVal = IntNum.make(100000);
                break;
            case 6:
                scaleVal = IntNum.make(1000000);
                break;
            case 7:
                scaleVal = IntNum.make(10000000);
                break;
            case 8:
                scaleVal = IntNum.make(100000000);
                break;
            default:
                return v;
        }
        if (scale > 0) {
            return divide(v, scaleVal);
        }
        return times(v, scaleVal);
    }

    public static RatNum asRatNumOrNull(Object value) {
        if (value instanceof RatNum) {
            return (RatNum) value;
        }
        if (value instanceof BigDecimal) {
            return valueOf((BigDecimal) value);
        }
        return IntNum.asIntNumOrNull(value);
    }

    public boolean isExact() {
        return true;
    }

    public boolean isZero() {
        return numerator().isZero();
    }

    public final RatNum rneg() {
        return neg(this);
    }

    public static RatNum infinity(int sign) {
        return new IntFraction(IntNum.make(sign), IntNum.zero());
    }

    public static int compare(RatNum x, RatNum y) {
        return IntNum.compare(IntNum.times(x.numerator(), y.denominator()), IntNum.times(y.numerator(), x.denominator()));
    }

    public static boolean equals(RatNum x, RatNum y) {
        return IntNum.equals(x.numerator(), y.numerator()) && IntNum.equals(x.denominator(), y.denominator());
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof RatNum)) {
            return false;
        }
        return equals(this, (RatNum) obj);
    }

    public static RatNum add(RatNum x, RatNum y, int k) {
        IntNum x_num = x.numerator();
        IntNum x_den = x.denominator();
        IntNum y_num = y.numerator();
        IntNum y_den = y.denominator();
        if (IntNum.equals(x_den, y_den)) {
            return make(IntNum.add(x_num, y_num, k), x_den);
        }
        return make(IntNum.add(IntNum.times(y_den, x_num), IntNum.times(y_num, x_den), k), IntNum.times(x_den, y_den));
    }

    public static RatNum neg(RatNum x) {
        IntNum x_num = x.numerator();
        return make(IntNum.neg(x_num), x.denominator());
    }

    public static RatNum times(RatNum x, RatNum y) {
        return make(IntNum.times(x.numerator(), y.numerator()), IntNum.times(x.denominator(), y.denominator()));
    }

    public static RatNum divide(RatNum x, RatNum y) {
        return make(IntNum.times(x.numerator(), y.denominator()), IntNum.times(x.denominator(), y.numerator()));
    }

    public Numeric power(IntNum y) {
        boolean inv;
        if (y.isNegative()) {
            inv = true;
            y = IntNum.neg(y);
        } else {
            inv = false;
        }
        if (y.words == null) {
            IntNum num = IntNum.power(numerator(), y.ival);
            IntNum den = IntNum.power(denominator(), y.ival);
            return inv ? make(den, num) : make(num, den);
        } else {
            double d = doubleValue();
            boolean neg = d < 0.0d && y.isOdd();
            d = Math.pow(d, y.doubleValue());
            if (inv) {
                d = 1.0d / d;
            }
            if (neg) {
                d = -d;
            }
            return new DFloNum(d);
        }
    }

    public final RatNum toExact() {
        return this;
    }

    public RealNum toInt(int rounding_mode) {
        return IntNum.quotient(numerator(), denominator(), rounding_mode);
    }

    public IntNum toExactInt(int rounding_mode) {
        return IntNum.quotient(numerator(), denominator(), rounding_mode);
    }

    public static RealNum rationalize(RealNum x, RealNum y) {
        if (x.grt(y)) {
            return simplest_rational2(y, x);
        }
        if (!y.grt(x)) {
            return x;
        }
        if (x.sign() > 0) {
            return simplest_rational2(x, y);
        }
        if (y.isNegative()) {
            return (RealNum) simplest_rational2((RealNum) y.neg(), (RealNum) x.neg()).neg();
        }
        return IntNum.zero();
    }

    private static RealNum simplest_rational2(RealNum x, RealNum y) {
        RealNum fx = x.toInt(1);
        RealNum fy = y.toInt(1);
        if (!x.grt(fx)) {
            return fx;
        }
        if (!fx.equals(fy)) {
            return (RealNum) fx.add(IntNum.one(), 1);
        }
        return (RealNum) fx.add(IntNum.one().div(simplest_rational2((RealNum) IntNum.one().div(y.sub(fy)), (RealNum) IntNum.one().div(x.sub(fx)))), 1);
    }
}

package gnu.math;

public abstract class Quantity extends Numeric {
    public abstract Complex number();

    public Unit unit() {
        return Unit.Empty;
    }

    public Dimensions dimensions() {
        return unit().dimensions();
    }

    public RealNum re() {
        return number().re();
    }

    public RealNum im() {
        return number().im();
    }

    public final double reValue() {
        return doubleValue();
    }

    public final double imValue() {
        return doubleImagValue();
    }

    public double doubleValue() {
        return unit().doubleValue() * re().doubleValue();
    }

    public double doubleImagValue() {
        return unit().doubleValue() * im().doubleValue();
    }

    public static Quantity make(Complex x, Unit u) {
        if (u == Unit.Empty) {
            return x;
        }
        if (x instanceof DFloNum) {
            return new DQuantity(x.doubleValue(), u);
        }
        return new CQuantity(x, u);
    }

    public static Quantity make(RealNum re, RealNum im, Unit unit) {
        if (unit == Unit.Empty) {
            return Complex.make(re, im);
        }
        if (!im.isZero() || (re.isExact() && im.isExact())) {
            return new CQuantity(re, im, unit);
        }
        return new DQuantity(re.doubleValue(), unit);
    }

    public static Quantity make(double re, double im, Unit unit) {
        if (unit == Unit.Empty) {
            return Complex.make(re, im);
        }
        if (im == 0.0d) {
            return new DQuantity(re, unit);
        }
        return new CQuantity(new DFloNum(re), new DFloNum(im), unit);
    }

    public Numeric neg() {
        return make((Complex) number().neg(), unit());
    }

    public Numeric abs() {
        return make((Complex) number().abs(), unit());
    }

    public static int compare(Quantity x, Quantity y) {
        if (x.unit() == y.unit()) {
            return Complex.compare(x.number(), y.number());
        }
        if (x.dimensions() == y.dimensions() && x.imValue() == y.imValue()) {
            return DFloNum.compare(x.reValue(), y.reValue());
        }
        return -3;
    }

    public int compare(Object obj) {
        if (obj instanceof Quantity) {
            return compare(this, (Quantity) obj);
        }
        return ((Numeric) obj).compareReversed(this);
    }

    public int compareReversed(Numeric x) {
        if (x instanceof Quantity) {
            return compare((Quantity) x, this);
        }
        throw new IllegalArgumentException();
    }

    public static Quantity add(Quantity x, Quantity y, int k) {
        if (x.unit() == y.unit()) {
            return make(Complex.add(x.number(), y.number(), k), x.unit());
        }
        if (x.dimensions() != y.dimensions()) {
            throw new ArithmeticException("units mis-match");
        }
        double x_factor = x.unit().doubleValue();
        return make((x.reValue() + (((double) k) * y.reValue())) / x_factor, (x.imValue() + (((double) k) * y.imValue())) / x_factor, x.unit());
    }

    public Numeric add(Object y, int k) {
        if (y instanceof Quantity) {
            return add(this, (Quantity) y, k);
        }
        return ((Numeric) y).addReversed(this, k);
    }

    public Numeric addReversed(Numeric x, int k) {
        if (x instanceof Quantity) {
            return add((Quantity) x, this, k);
        }
        throw new IllegalArgumentException();
    }

    public static Quantity times(Quantity x, Quantity y) {
        return make((Complex) x.number().mul(y.number()), Unit.times(x.unit(), y.unit()));
    }

    public Numeric mul(Object y) {
        if (y instanceof Quantity) {
            return times(this, (Quantity) y);
        }
        return ((Numeric) y).mulReversed(this);
    }

    public Numeric mulReversed(Numeric x) {
        if (x instanceof Quantity) {
            return times((Quantity) x, this);
        }
        throw new IllegalArgumentException();
    }

    public static Quantity divide(Quantity x, Quantity y) {
        return make((Complex) x.number().div(y.number()), Unit.divide(x.unit(), y.unit()));
    }

    public Numeric div(Object y) {
        if (y instanceof Quantity) {
            return divide(this, (Quantity) y);
        }
        return ((Numeric) y).divReversed(this);
    }

    public Numeric divReversed(Numeric x) {
        if (x instanceof Quantity) {
            return divide((Quantity) x, this);
        }
        throw new IllegalArgumentException();
    }

    public String toString(int radix) {
        String str = number().toString(radix);
        return unit() == Unit.Empty ? str : str + unit().toString();
    }
}

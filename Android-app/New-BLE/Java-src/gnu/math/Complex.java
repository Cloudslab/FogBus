package gnu.math;

public abstract class Complex extends Quantity {
    private static CComplex imMinusOne;
    private static CComplex imOne;

    public Complex number() {
        return this;
    }

    public boolean isExact() {
        return re().isExact() && im().isExact();
    }

    public Complex toExact() {
        RealNum re = re();
        RealNum im = im();
        RealNum xre = re.toExact();
        RealNum xim = im.toExact();
        if (xre == re && xim == im) {
            return this;
        }
        super(xre, xim);
        return this;
    }

    public Complex toInexact() {
        return isExact() ? this : new DComplex(re().doubleValue(), im().doubleValue());
    }

    public static CComplex imOne() {
        if (imOne == null) {
            imOne = new CComplex(IntNum.zero(), IntNum.one());
        }
        return imOne;
    }

    public static CComplex imMinusOne() {
        if (imMinusOne == null) {
            imMinusOne = new CComplex(IntNum.zero(), IntNum.minusOne());
        }
        return imMinusOne;
    }

    public double doubleValue() {
        return re().doubleValue();
    }

    public double doubleImagValue() {
        return im().doubleValue();
    }

    public final double doubleRealValue() {
        return doubleValue();
    }

    public long longValue() {
        return re().longValue();
    }

    public static Complex make(RealNum re, RealNum im) {
        if (im.isZero()) {
            return re;
        }
        if (re.isExact() && im.isExact()) {
            return new CComplex(re, im);
        }
        return new DComplex(re.doubleValue(), im.doubleValue());
    }

    public static Complex make(double re, double im) {
        if (im == 0.0d) {
            return new DFloNum(re);
        }
        return new DComplex(re, im);
    }

    public static DComplex polar(double r, double t) {
        return new DComplex(Math.cos(t) * r, Math.sin(t) * r);
    }

    public static DComplex polar(RealNum r, RealNum t) {
        return polar(r.doubleValue(), t.doubleValue());
    }

    public static Complex power(Complex x, Complex y) {
        if (y instanceof IntNum) {
            return (Complex) x.power((IntNum) y);
        }
        double x_re = x.doubleRealValue();
        double x_im = x.doubleImagValue();
        double y_re = y.doubleRealValue();
        double y_im = y.doubleImagValue();
        if (x_im == 0.0d && y_im == 0.0d && (x_re >= 0.0d || Double.isInfinite(x_re) || Double.isNaN(x_re))) {
            return new DFloNum(Math.pow(x_re, y_re));
        }
        return DComplex.power(x_re, x_im, y_re, y_im);
    }

    public Numeric abs() {
        return new DFloNum(Math.hypot(doubleRealValue(), doubleImagValue()));
    }

    public RealNum angle() {
        return new DFloNum(Math.atan2(doubleImagValue(), doubleRealValue()));
    }

    public static boolean equals(Complex x, Complex y) {
        return x.re().equals(y.re()) && x.im().equals(x.im());
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Complex)) {
            return false;
        }
        return equals(this, (Complex) obj);
    }

    public static int compare(Complex x, Complex y) {
        int code = x.im().compare(y.im());
        return code != 0 ? code : x.re().compare(y.re());
    }

    public int compare(Object obj) {
        if (obj instanceof Complex) {
            return compare(this, (Complex) obj);
        }
        return ((Numeric) obj).compareReversed(this);
    }

    public boolean isZero() {
        return re().isZero() && im().isZero();
    }

    public String toString(int radix) {
        if (im().isZero()) {
            return re().toString(radix);
        }
        String imString = im().toString(radix) + "i";
        if (imString.charAt(0) != '-') {
            imString = "+" + imString;
        }
        return !re().isZero() ? re().toString(radix) + imString : imString;
    }

    public static Complex neg(Complex x) {
        return make(x.re().rneg(), x.im().rneg());
    }

    public Numeric neg() {
        return neg(this);
    }

    public static Complex add(Complex x, Complex y, int k) {
        return make(RealNum.add(x.re(), y.re(), k), RealNum.add(x.im(), y.im(), k));
    }

    public Numeric add(Object y, int k) {
        if (y instanceof Complex) {
            return add(this, (Complex) y, k);
        }
        return ((Numeric) y).addReversed(this, k);
    }

    public Numeric addReversed(Numeric x, int k) {
        if (x instanceof Complex) {
            return add((Complex) x, this, k);
        }
        throw new IllegalArgumentException();
    }

    public static Complex times(Complex x, Complex y) {
        RealNum x_re = x.re();
        RealNum x_im = x.im();
        RealNum y_re = y.re();
        RealNum y_im = y.im();
        return make(RealNum.add(RealNum.times(x_re, y_re), RealNum.times(x_im, y_im), -1), RealNum.add(RealNum.times(x_re, y_im), RealNum.times(x_im, y_re), 1));
    }

    public Numeric mul(Object y) {
        if (y instanceof Complex) {
            return times(this, (Complex) y);
        }
        return ((Numeric) y).mulReversed(this);
    }

    public Numeric mulReversed(Numeric x) {
        if (x instanceof Complex) {
            return times((Complex) x, this);
        }
        throw new IllegalArgumentException();
    }

    public static Complex divide(Complex x, Complex y) {
        if (!x.isExact() || !y.isExact()) {
            return DComplex.div(x.doubleRealValue(), x.doubleImagValue(), y.doubleRealValue(), y.doubleImagValue());
        }
        RealNum x_re = x.re();
        RealNum x_im = x.im();
        RealNum y_re = y.re();
        RealNum y_im = y.im();
        RealNum q = RealNum.add(RealNum.times(y_re, y_re), RealNum.times(y_im, y_im), 1);
        return make(RealNum.divide(RealNum.add(RealNum.times(x_re, y_re), RealNum.times(x_im, y_im), 1), q), RealNum.divide(RealNum.add(RealNum.times(x_im, y_re), RealNum.times(x_re, y_im), -1), q));
    }

    public Numeric div(Object y) {
        if (y instanceof Complex) {
            return divide(this, (Complex) y);
        }
        return ((Numeric) y).divReversed(this);
    }

    public Numeric divReversed(Numeric x) {
        if (x instanceof Complex) {
            return divide((Complex) x, this);
        }
        throw new IllegalArgumentException();
    }

    public Complex exp() {
        return polar(Math.exp(doubleRealValue()), doubleImagValue());
    }

    public Complex log() {
        return DComplex.log(doubleRealValue(), doubleImagValue());
    }

    public Complex sqrt() {
        return DComplex.sqrt(doubleRealValue(), doubleImagValue());
    }
}

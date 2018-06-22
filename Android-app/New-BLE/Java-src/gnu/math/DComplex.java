package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DComplex extends Complex implements Externalizable {
    double imag;
    double real;

    public DComplex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    public RealNum re() {
        return new DFloNum(this.real);
    }

    public double doubleValue() {
        return this.real;
    }

    public RealNum im() {
        return new DFloNum(this.imag);
    }

    public double doubleImagValue() {
        return this.imag;
    }

    public boolean isExact() {
        return false;
    }

    public Complex toExact() {
        return new CComplex(DFloNum.toExact(this.real), DFloNum.toExact(this.imag));
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Complex)) {
            return false;
        }
        Complex y = (Complex) obj;
        if (y.unit() == Unit.Empty && Double.doubleToLongBits(this.real) == Double.doubleToLongBits(y.reValue()) && Double.doubleToLongBits(this.imag) == Double.doubleToLongBits(y.imValue())) {
            return true;
        }
        return false;
    }

    public String toString() {
        String reString;
        String prefix = "";
        if (this.real == Double.POSITIVE_INFINITY) {
            prefix = "#i";
            reString = "1/0";
        } else if (this.real == Double.NEGATIVE_INFINITY) {
            prefix = "#i";
            reString = "-1/0";
        } else if (Double.isNaN(this.real)) {
            prefix = "#i";
            reString = "0/0";
        } else {
            reString = Double.toString(this.real);
        }
        if (Double.doubleToLongBits(this.imag) == 0) {
            return prefix + reString;
        }
        String imString;
        String str;
        if (this.imag == Double.POSITIVE_INFINITY) {
            prefix = "#i";
            imString = "+1/0i";
        } else if (this.imag == Double.NEGATIVE_INFINITY) {
            prefix = "#i";
            imString = "-1/0i";
        } else if (Double.isNaN(this.imag)) {
            prefix = "#i";
            imString = "+0/0i";
        } else {
            imString = Double.toString(this.imag) + "i";
            if (imString.charAt(0) != '-') {
                imString = "+" + imString;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (Double.doubleToLongBits(this.real) == 0) {
            str = prefix;
        } else {
            str = prefix + reString;
        }
        return stringBuilder.append(str).append(imString).toString();
    }

    public String toString(int radix) {
        if (radix == 10) {
            return toString();
        }
        return "#d" + toString();
    }

    public final Numeric neg() {
        return new DComplex(-this.real, -this.imag);
    }

    public Numeric add(Object y, int k) {
        if (!(y instanceof Complex)) {
            return ((Numeric) y).addReversed(this, k);
        }
        Complex yc = (Complex) y;
        if (yc.dimensions() == Dimensions.Empty) {
            return new DComplex(this.real + (((double) k) * yc.reValue()), this.imag + (((double) k) * yc.imValue()));
        }
        throw new ArithmeticException("units mis-match");
    }

    public Numeric mul(Object y) {
        if (!(y instanceof Complex)) {
            return ((Numeric) y).mulReversed(this);
        }
        Complex yc = (Complex) y;
        if (yc.unit() != Unit.Empty) {
            return Complex.times(this, yc);
        }
        double y_re = yc.reValue();
        double y_im = yc.imValue();
        return new DComplex((this.real * y_re) - (this.imag * y_im), (this.real * y_im) + (this.imag * y_re));
    }

    public Numeric div(Object y) {
        if (!(y instanceof Complex)) {
            return ((Numeric) y).divReversed(this);
        }
        Complex yc = (Complex) y;
        return div(this.real, this.imag, yc.doubleValue(), yc.doubleImagValue());
    }

    public static DComplex power(double x_re, double x_im, double y_re, double y_im) {
        double logr = Math.log(Math.hypot(x_re, x_im));
        double t = Math.atan2(x_im, x_re);
        return Complex.polar(Math.exp((logr * y_re) - (y_im * t)), (y_im * logr) + (y_re * t));
    }

    public static Complex log(double x_re, double x_im) {
        return Complex.make(Math.log(Math.hypot(x_re, x_im)), Math.atan2(x_im, x_re));
    }

    public static DComplex div(double x_re, double x_im, double y_re, double y_im) {
        double d;
        double nr;
        double ni;
        double t;
        if (Math.abs(y_re) <= Math.abs(y_im)) {
            t = y_re / y_im;
            d = y_im * (1.0d + (t * t));
            nr = (x_re * t) + x_im;
            ni = (x_im * t) - x_re;
        } else {
            t = y_im / y_re;
            d = y_re * (1.0d + (t * t));
            nr = x_re + (x_im * t);
            ni = x_im - (x_re * t);
        }
        return new DComplex(nr / d, ni / d);
    }

    public static Complex sqrt(double x_re, double x_im) {
        double ni;
        double nr;
        double r = Math.hypot(x_re, x_im);
        if (r == 0.0d) {
            ni = r;
            nr = r;
        } else if (x_re > 0.0d) {
            nr = Math.sqrt(0.5d * (r + x_re));
            ni = (x_im / nr) / 2.0d;
        } else {
            ni = Math.sqrt(0.5d * (r - x_re));
            if (x_im < 0.0d) {
                ni = -ni;
            }
            nr = (x_im / ni) / 2.0d;
        }
        return new DComplex(nr, ni);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(this.real);
        out.writeDouble(this.imag);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.real = in.readDouble();
        this.imag = in.readDouble();
    }
}

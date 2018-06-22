package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DQuantity extends Quantity implements Externalizable {
    double factor;
    Unit unt;

    public final Unit unit() {
        return this.unt;
    }

    public final Complex number() {
        return new DFloNum(this.factor);
    }

    public final RealNum re() {
        return new DFloNum(this.factor);
    }

    public final double doubleValue() {
        return this.factor * this.unt.factor;
    }

    public DQuantity(double factor, Unit unit) {
        this.factor = factor;
        this.unt = unit;
    }

    public boolean isExact() {
        return false;
    }

    public boolean isZero() {
        return this.factor == 0.0d;
    }

    public static DQuantity add(DQuantity x, DQuantity y, double k) {
        if (x.dimensions() != y.dimensions()) {
            throw new ArithmeticException("units mis-match");
        }
        return new DQuantity(x.factor + ((k * (y.unit().factor / x.unit().factor)) * y.factor), x.unit());
    }

    public static DQuantity times(DQuantity x, DQuantity y) {
        return new DQuantity(x.factor * y.factor, Unit.times(x.unit(), y.unit()));
    }

    public static DQuantity divide(DQuantity x, DQuantity y) {
        return new DQuantity(x.factor / y.factor, Unit.divide(x.unit(), y.unit()));
    }

    public Numeric add(Object y, int k) {
        if (y instanceof DQuantity) {
            return add(this, (DQuantity) y, (double) k);
        }
        if (dimensions() == Dimensions.Empty && (y instanceof RealNum)) {
            return new DQuantity(this.factor + (((double) k) * ((RealNum) y).doubleValue()), unit());
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).addReversed(this, k);
        }
        throw new IllegalArgumentException();
    }

    public Numeric addReversed(Numeric x, int k) {
        if (dimensions() == Dimensions.Empty && (x instanceof RealNum)) {
            return new DFloNum(((RealNum) x).doubleValue() + (((double) k) * this.factor));
        }
        throw new IllegalArgumentException();
    }

    public Numeric mul(Object y) {
        if (y instanceof DQuantity) {
            return times(this, (DQuantity) y);
        }
        if (y instanceof RealNum) {
            return new DQuantity(this.factor * ((RealNum) y).doubleValue(), unit());
        }
        if (y instanceof Numeric) {
            return ((Numeric) y).mulReversed(this);
        }
        throw new IllegalArgumentException();
    }

    public Numeric mulReversed(Numeric x) {
        if (x instanceof RealNum) {
            return new DQuantity(((RealNum) x).doubleValue() * this.factor, unit());
        }
        throw new IllegalArgumentException();
    }

    public Numeric div(Object y) {
        if (y instanceof DQuantity) {
            DQuantity qy = (DQuantity) y;
            if (dimensions() == qy.dimensions()) {
                return new DFloNum((this.factor * unit().doubleValue()) / (qy.factor * qy.unit().factor));
            }
            return divide(this, qy);
        } else if (y instanceof RealNum) {
            return new DQuantity(this.factor / ((RealNum) y).doubleValue(), unit());
        } else {
            if (y instanceof Numeric) {
                return ((Numeric) y).divReversed(this);
            }
            throw new IllegalArgumentException();
        }
    }

    public Numeric divReversed(Numeric x) {
        if (x instanceof RealNum) {
            return new DQuantity(((RealNum) x).doubleValue() / this.factor, Unit.divide(Unit.Empty, unit()));
        }
        throw new IllegalArgumentException();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeDouble(this.factor);
        out.writeObject(this.unt);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.factor = in.readDouble();
        this.unt = (Unit) in.readObject();
    }
}

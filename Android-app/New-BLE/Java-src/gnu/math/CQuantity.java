package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class CQuantity extends Quantity implements Externalizable {
    Complex num;
    Unit unt;

    public CQuantity(Complex num, Unit unit) {
        this.num = num;
        this.unt = unit;
    }

    public CQuantity(RealNum real, RealNum imag, Unit unit) {
        this.num = new CComplex(real, imag);
        this.unt = unit;
    }

    public Complex number() {
        return this.num;
    }

    public Unit unit() {
        return this.unt;
    }

    public boolean isExact() {
        return this.num.isExact();
    }

    public boolean isZero() {
        return this.num.isZero();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.num);
        out.writeObject(this.unt);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.num = (Complex) in.readObject();
        this.unt = (Unit) in.readObject();
    }
}

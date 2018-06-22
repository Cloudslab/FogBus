package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class CComplex extends Complex implements Externalizable {
    RealNum imag;
    RealNum real;

    public CComplex(RealNum real, RealNum imag) {
        this.real = real;
        this.imag = imag;
    }

    public RealNum re() {
        return this.real;
    }

    public RealNum im() {
        return this.imag;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(this.real);
        out.writeObject(this.imag);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.real = (RealNum) in.readObject();
        this.imag = (RealNum) in.readObject();
    }
}

package gnu.math;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class NamedUnit extends Unit implements Externalizable {
    Unit base;
    NamedUnit chain;
    String name;
    double scale;

    public NamedUnit(String name, DQuantity value) {
        this.name = name.intern();
        this.scale = value.factor;
        this.base = value.unt;
        init();
    }

    public NamedUnit(String name, double factor, Unit base) {
        this.name = name;
        this.base = base;
        this.scale = factor;
        init();
    }

    protected void init() {
        this.factor = this.scale * this.base.factor;
        this.dims = this.base.dims;
        this.name = this.name.intern();
        int index = (ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & this.name.hashCode()) % table.length;
        this.chain = table[index];
        table[index] = this;
    }

    public String getName() {
        return this.name;
    }

    public static NamedUnit lookup(String name) {
        name = name.intern();
        for (NamedUnit unit = table[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & name.hashCode()) % table.length]; unit != null; unit = unit.chain) {
            if (unit.name == name) {
                return unit;
            }
        }
        return null;
    }

    public static NamedUnit lookup(String name, double scale, Unit base) {
        name = name.intern();
        NamedUnit unit = table[(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED & name.hashCode()) % table.length];
        while (unit != null) {
            if (unit.name == name && unit.scale == scale && unit.base == base) {
                return unit;
            }
            unit = unit.chain;
        }
        return null;
    }

    public static NamedUnit make(String name, double scale, Unit base) {
        NamedUnit lookup = lookup(name, scale, base);
        return lookup == null ? new NamedUnit(name, scale, base) : lookup;
    }

    public static NamedUnit make(String name, Quantity value) {
        double scale;
        if (value instanceof DQuantity) {
            scale = ((DQuantity) value).factor;
        } else if (value.imValue() != 0.0d) {
            throw new ArithmeticException("defining " + name + " using complex value");
        } else {
            scale = value.re().doubleValue();
        }
        Unit base = value.unit();
        NamedUnit lookup = lookup(name, scale, base);
        if (lookup == null) {
            return new NamedUnit(name, scale, base);
        }
        return lookup;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
        out.writeDouble(this.scale);
        out.writeObject(this.base);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
        this.scale = in.readDouble();
        this.base = (Unit) in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        NamedUnit unit = lookup(this.name, this.scale, this.base);
        if (unit != null) {
            return unit;
        }
        init();
        return this;
    }
}

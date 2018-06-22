package gnu.mapping;

public class PlainLocation extends NamedLocation {
    public PlainLocation(Symbol symbol, Object property) {
        super(symbol, property);
    }

    public PlainLocation(Symbol symbol, Object property, Object value) {
        super(symbol, property);
        this.value = value;
    }

    public final Object get(Object defaultValue) {
        if (this.base != null) {
            return this.base.get(defaultValue);
        }
        return this.value != Location.UNBOUND ? this.value : defaultValue;
    }

    public boolean isBound() {
        if (this.base != null) {
            return this.base.isBound();
        }
        return this.value != Location.UNBOUND;
    }

    public final void set(Object newValue) {
        if (this.base == null) {
            this.value = newValue;
        } else if (this.value == DIRECT_ON_SET) {
            this.base = null;
            this.value = newValue;
        } else if (this.base.isConstant()) {
            getEnvironment().put(getKeySymbol(), getKeyProperty(), newValue);
        } else {
            this.base.set(newValue);
        }
    }
}

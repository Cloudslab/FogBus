package gnu.mapping;

public class SharedLocation extends NamedLocation {
    int timestamp;

    public SharedLocation(Symbol symbol, Object property, int timestamp) {
        super(symbol, property);
        this.timestamp = timestamp;
    }

    public final synchronized Object get(Object defaultValue) {
        if (this.base != null) {
            defaultValue = this.base.get(defaultValue);
        } else if (this.value != Location.UNBOUND) {
            defaultValue = this.value;
        }
        return defaultValue;
    }

    public synchronized boolean isBound() {
        boolean isBound;
        isBound = this.base != null ? this.base.isBound() : this.value != Location.UNBOUND;
        return isBound;
    }

    public final synchronized void set(Object newValue) {
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

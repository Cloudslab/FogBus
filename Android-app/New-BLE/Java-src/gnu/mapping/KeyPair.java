package gnu.mapping;

public class KeyPair implements EnvironmentKey {
    Symbol name;
    Object property;

    public KeyPair(Symbol name, Object property) {
        this.name = name;
        this.property = property;
    }

    public Symbol getKeySymbol() {
        return this.name;
    }

    public Object getKeyProperty() {
        return this.property;
    }

    public final boolean matches(EnvironmentKey key) {
        return Symbol.equals(key.getKeySymbol(), this.name) && key.getKeyProperty() == this.property;
    }

    public final boolean matches(Symbol symbol, Object property) {
        return Symbol.equals(symbol, this.name) && property == this.property;
    }

    public boolean equals(Object x) {
        if (!(x instanceof KeyPair)) {
            return false;
        }
        KeyPair e2 = (KeyPair) x;
        if (this.property != e2.property) {
            return false;
        }
        if (this.name == null) {
            if (e2.name != null) {
                return false;
            }
        } else if (!this.name.equals(e2.name)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.name.hashCode() ^ System.identityHashCode(this.property);
    }

    public String toString() {
        return "KeyPair[sym:" + this.name + " prop:" + this.property + "]";
    }
}

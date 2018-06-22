package gnu.mapping;

import java.util.Map.Entry;

public abstract class NamedLocation extends IndirectableLocation implements Entry, EnvironmentKey {
    final Symbol name;
    NamedLocation next;
    final Object property;

    public boolean entered() {
        return this.next != null;
    }

    public Environment getEnvironment() {
        for (NamedLocation loc = this; loc != null; loc = loc.next) {
            if (loc.name == null) {
                Environment env = loc.value;
                if (env != null) {
                    return env;
                }
            }
        }
        return super.getEnvironment();
    }

    public NamedLocation(NamedLocation loc) {
        this.name = loc.name;
        this.property = loc.property;
    }

    public NamedLocation(Symbol name, Object property) {
        this.name = name;
        this.property = property;
    }

    public final Symbol getKeySymbol() {
        return this.name;
    }

    public final Object getKeyProperty() {
        return this.property;
    }

    public final boolean matches(EnvironmentKey key) {
        return Symbol.equals(key.getKeySymbol(), this.name) && key.getKeyProperty() == this.property;
    }

    public final boolean matches(Symbol symbol, Object property) {
        return Symbol.equals(symbol, this.name) && property == this.property;
    }

    public final Object getKey() {
        if (this.property == null) {
            return this.name;
        }
        return this;
    }

    public boolean equals(Object x) {
        if (!(x instanceof NamedLocation)) {
            return false;
        }
        NamedLocation e2 = (NamedLocation) x;
        if (this.name == null) {
            if (e2.name != null) {
                return false;
            }
        } else if (!this.name.equals(e2.name)) {
            return false;
        }
        if (this.property != e2.property) {
            return false;
        }
        Object val1 = getValue();
        Object val2 = e2.getValue();
        if (val1 == val2) {
            return true;
        }
        if (val1 == null || val2 == null) {
            return false;
        }
        return val1.equals(val2);
    }

    public int hashCode() {
        int h = this.name.hashCode() ^ System.identityHashCode(this.property);
        Object val = getValue();
        if (val != null) {
            return h ^ val.hashCode();
        }
        return h;
    }

    public synchronized Object setWithSave(Object newValue) {
        Object withSave;
        if (this.value == INDIRECT_FLUIDS) {
            withSave = this.base.setWithSave(newValue);
        } else {
            ThreadLocation thloc = ThreadLocation.makeAnonymous(this.name);
            thloc.global.base = this.base;
            thloc.global.value = this.value;
            setAlias(thloc);
            NamedLocation entry = thloc.getLocation();
            entry.value = newValue;
            entry.base = null;
            withSave = thloc.global;
        }
        return withSave;
    }

    public synchronized void setRestore(Object oldValue) {
        if (this.value == INDIRECT_FLUIDS) {
            this.base.setRestore(oldValue);
        } else if (oldValue instanceof Location) {
            this.value = null;
            this.base = (Location) oldValue;
        } else {
            this.value = oldValue;
            this.base = null;
        }
    }
}

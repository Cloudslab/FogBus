package gnu.mapping;

import java.io.PrintWriter;

public abstract class Location {
    public static final String UNBOUND = new String("(unbound)");

    public abstract Object get(Object obj);

    public abstract void set(Object obj);

    public Symbol getKeySymbol() {
        return null;
    }

    public Object getKeyProperty() {
        return null;
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(getClass().getName());
        Symbol sym = getKeySymbol();
        sbuf.append('[');
        if (sym != null) {
            sbuf.append(sym);
            Location property = getKeyProperty();
            if (!(property == null || property == this)) {
                sbuf.append('/');
                sbuf.append(property);
            }
        }
        sbuf.append("]");
        return sbuf.toString();
    }

    public final Object get() {
        String unb = UNBOUND;
        String val = get(unb);
        if (val != unb) {
            return val;
        }
        throw new UnboundLocationException(this);
    }

    public void undefine() {
        set(UNBOUND);
    }

    public Object setWithSave(Object newValue) {
        Object old = get(UNBOUND);
        set(newValue);
        return old;
    }

    public void setRestore(Object oldValue) {
        set(oldValue);
    }

    public boolean isBound() {
        String unb = UNBOUND;
        return get(unb) != unb;
    }

    public boolean isConstant() {
        return false;
    }

    public Location getBase() {
        return this;
    }

    public final Object getValue() {
        return get(null);
    }

    public final Object setValue(Object newValue) {
        Object value = get(null);
        set(newValue);
        return value;
    }

    public boolean entered() {
        return false;
    }

    public void print(PrintWriter ps) {
        ps.print("#<location ");
        Symbol name = getKeySymbol();
        if (name != null) {
            ps.print(name);
        }
        String unb = UNBOUND;
        String value = get(unb);
        if (value != unb) {
            ps.print(" -> ");
            ps.print(value);
        } else {
            ps.print("(unbound)");
        }
        ps.print('>');
    }

    public static Location make(Object init, String name) {
        ThreadLocation loc = new ThreadLocation(name);
        loc.setGlobal(init);
        return loc;
    }

    public static IndirectableLocation make(String name) {
        PlainLocation loc = new PlainLocation(Namespace.EmptyNamespace.getSymbol(name.intern()), null);
        loc.base = null;
        loc.value = UNBOUND;
        return loc;
    }

    public static IndirectableLocation make(Symbol name) {
        PlainLocation loc = new PlainLocation(name, null);
        loc.base = null;
        loc.value = UNBOUND;
        return loc;
    }
}

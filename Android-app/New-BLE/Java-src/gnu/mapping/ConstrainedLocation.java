package gnu.mapping;

public class ConstrainedLocation extends Location {
    protected Location base;
    protected Procedure converter;

    public static ConstrainedLocation make(Location base, Procedure converter) {
        ConstrainedLocation cloc = new ConstrainedLocation();
        cloc.base = base;
        cloc.converter = converter;
        return cloc;
    }

    public Symbol getKeySymbol() {
        return this.base.getKeySymbol();
    }

    public Object getKeyProperty() {
        return this.base.getKeyProperty();
    }

    public boolean isConstant() {
        return this.base.isConstant();
    }

    public final Object get(Object defaultValue) {
        return this.base.get(defaultValue);
    }

    public boolean isBound() {
        return this.base.isBound();
    }

    protected Object coerce(Object newValue) {
        try {
            return this.converter.apply1(newValue);
        } catch (Throwable ex) {
            RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
        }
    }

    public final void set(Object newValue) {
        this.base.set(coerce(newValue));
    }

    public Object setWithSave(Object newValue) {
        return this.base.setWithSave(coerce(newValue));
    }

    public void setRestore(Object oldValue) {
        this.base.setRestore(oldValue);
    }
}

package gnu.mapping;

public abstract class IndirectableLocation extends Location {
    protected static final Object DIRECT_ON_SET = new String("(direct-on-set)");
    protected static final Object INDIRECT_FLUIDS = new String("(indirect-fluids)");
    protected Location base;
    protected Object value;

    public Symbol getKeySymbol() {
        return this.base != null ? this.base.getKeySymbol() : null;
    }

    public Object getKeyProperty() {
        return this.base != null ? this.base.getKeyProperty() : null;
    }

    public boolean isConstant() {
        return this.base != null && this.base.isConstant();
    }

    public Location getBase() {
        return this.base == null ? this : this.base.getBase();
    }

    public Location getBaseForce() {
        if (this.base == null) {
            return new PlainLocation(getKeySymbol(), getKeyProperty(), this.value);
        }
        return this.base;
    }

    public void setBase(Location base) {
        this.base = base;
        this.value = null;
    }

    public void setAlias(Location base) {
        this.base = base;
        this.value = INDIRECT_FLUIDS;
    }

    public void undefine() {
        this.base = null;
        this.value = UNBOUND;
    }

    public Environment getEnvironment() {
        return this.base instanceof NamedLocation ? ((NamedLocation) this.base).getEnvironment() : null;
    }
}

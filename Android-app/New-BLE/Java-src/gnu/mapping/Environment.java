package gnu.mapping;

import java.util.Hashtable;

public abstract class Environment extends PropertySet {
    static final int CAN_DEFINE = 1;
    static final int CAN_IMPLICITLY_DEFINE = 4;
    static final int CAN_REDEFINE = 2;
    static final int DIRECT_INHERITED_ON_SET = 16;
    public static final int INDIRECT_DEFINES = 32;
    static final int THREAD_SAFE = 8;
    protected static final InheritedLocal curEnvironment = new InheritedLocal();
    static final Hashtable envTable = new Hashtable(50);
    static Environment global;
    int flags = 23;

    static class InheritedLocal extends InheritableThreadLocal<Environment> {
        InheritedLocal() {
        }

        protected Environment childValue(Environment parentValue) {
            if (parentValue == null) {
                parentValue = Environment.getCurrent();
            }
            SimpleEnvironment env = parentValue.cloneForThread();
            env.flags |= 8;
            env.flags &= -17;
            return env;
        }
    }

    public abstract NamedLocation addLocation(Symbol symbol, Object obj, Location location);

    public abstract void define(Symbol symbol, Object obj, Object obj2);

    public abstract LocationEnumeration enumerateAllLocations();

    public abstract LocationEnumeration enumerateLocations();

    public abstract NamedLocation getLocation(Symbol symbol, Object obj, int i, boolean z);

    protected abstract boolean hasMoreElements(LocationEnumeration locationEnumeration);

    public abstract NamedLocation lookup(Symbol symbol, Object obj, int i);

    public static void setGlobal(Environment env) {
        global = env;
    }

    public static Environment getGlobal() {
        return global;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlag(boolean setting, int flag) {
        if (setting) {
            this.flags |= flag;
        } else {
            this.flags &= flag ^ -1;
        }
    }

    public boolean getCanDefine() {
        return (this.flags & 1) != 0;
    }

    public void setCanDefine(boolean canDefine) {
        if (canDefine) {
            this.flags |= 1;
        } else {
            this.flags &= -2;
        }
    }

    public boolean getCanRedefine() {
        return (this.flags & 2) != 0;
    }

    public void setCanRedefine(boolean canRedefine) {
        if (canRedefine) {
            this.flags |= 2;
        } else {
            this.flags &= -3;
        }
    }

    public final boolean isLocked() {
        return (this.flags & 3) == 0;
    }

    public void setLocked() {
        this.flags &= -8;
    }

    public final void setIndirectDefines() {
        this.flags |= 32;
        ((InheritingEnvironment) this).baseTimestamp = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    public final Location getLocation(Symbol key, Object property) {
        return getLocation(key, property, true);
    }

    public final Location getLocation(Symbol key) {
        return getLocation(key, null, true);
    }

    public final Location lookup(Symbol key, Object property) {
        return getLocation(key, property, false);
    }

    public final Location lookup(Symbol key) {
        return getLocation(key, null, false);
    }

    public final NamedLocation getLocation(Symbol name, Object property, boolean create) {
        return getLocation(name, property, name.hashCode() ^ System.identityHashCode(property), create);
    }

    public final Location getLocation(Object key, boolean create) {
        Object property = null;
        if (key instanceof EnvironmentKey) {
            EnvironmentKey k = (EnvironmentKey) key;
            key = k.getKeySymbol();
            property = k.getKeyProperty();
        }
        return getLocation(key instanceof Symbol ? (Symbol) key : getSymbol((String) key), property, create);
    }

    public boolean isBound(Symbol key, Object property) {
        Location loc = lookup(key, property);
        if (loc == null) {
            return false;
        }
        return loc.isBound();
    }

    public final boolean isBound(Symbol key) {
        return isBound(key, null);
    }

    public final boolean containsKey(Object key) {
        Object property = null;
        if (key instanceof EnvironmentKey) {
            EnvironmentKey k = (EnvironmentKey) key;
            key = k.getKeySymbol();
            property = k.getKeyProperty();
        }
        return isBound(key instanceof Symbol ? (Symbol) key : getSymbol((String) key), property);
    }

    public final Object getChecked(String name) {
        String value = get(name, Location.UNBOUND);
        if (value != Location.UNBOUND) {
            return value;
        }
        throw new UnboundLocationException(name + " in " + this);
    }

    public Object get(Symbol key, Object property, Object defaultValue) {
        Location loc = lookup(key, property);
        return loc == null ? defaultValue : loc.get(defaultValue);
    }

    public final Object get(EnvironmentKey key, Object defaultValue) {
        return get(key.getKeySymbol(), key.getKeyProperty(), defaultValue);
    }

    public final Object get(String key, Object defaultValue) {
        return get(getSymbol(key), null, defaultValue);
    }

    public Object get(Symbol sym) {
        String unb = Location.UNBOUND;
        String val = get(sym, null, unb);
        if (val != unb) {
            return val;
        }
        throw new UnboundLocationException((Object) sym);
    }

    public final Object getFunction(Symbol key, Object defaultValue) {
        return get(key, EnvironmentKey.FUNCTION, defaultValue);
    }

    public final Object getFunction(Symbol sym) {
        String unb = Location.UNBOUND;
        String val = get(sym, EnvironmentKey.FUNCTION, unb);
        if (val != unb) {
            return val;
        }
        throw new UnboundLocationException((Object) sym);
    }

    public final Object get(Object key) {
        Object property = null;
        if (key instanceof EnvironmentKey) {
            EnvironmentKey k = (EnvironmentKey) key;
            key = k.getKeySymbol();
            property = k.getKeyProperty();
        }
        return get(key instanceof Symbol ? (Symbol) key : getSymbol((String) key), property, null);
    }

    public void put(Symbol key, Object property, Object newValue) {
        Location loc = getLocation(key, property);
        if (loc.isConstant()) {
            define(key, property, newValue);
        } else {
            loc.set(newValue);
        }
    }

    public final void put(Symbol key, Object newValue) {
        put(key, null, newValue);
    }

    public final Object put(Object key, Object newValue) {
        Location loc = getLocation(key, true);
        Object oldValue = loc.get(null);
        loc.set(newValue);
        return oldValue;
    }

    public final void putFunction(Symbol key, Object newValue) {
        put(key, EnvironmentKey.FUNCTION, newValue);
    }

    public final Object put(String key, Object value) {
        return put((Object) key, value);
    }

    public Location unlink(Symbol key, Object property, int hash) {
        throw new RuntimeException("unsupported operation: unlink (aka undefine)");
    }

    public Object remove(Symbol key, Object property, int hash) {
        Location loc = unlink(key, property, hash);
        if (loc == null) {
            return null;
        }
        Object value = loc.get(null);
        loc.undefine();
        return value;
    }

    public final Object remove(EnvironmentKey key) {
        Symbol symbol = key.getKeySymbol();
        Object property = key.getKeyProperty();
        return remove(symbol, property, symbol.hashCode() ^ System.identityHashCode(property));
    }

    public final Object remove(Symbol symbol, Object property) {
        return remove(symbol, property, symbol.hashCode() ^ System.identityHashCode(property));
    }

    public final void remove(Symbol sym) {
        remove(sym, null, sym.hashCode());
    }

    public final void removeFunction(Symbol sym) {
        remove(sym, EnvironmentKey.FUNCTION);
    }

    public final Object remove(Object key) {
        if (key instanceof EnvironmentKey) {
            EnvironmentKey k = (EnvironmentKey) key;
            return remove(k.getKeySymbol(), k.getKeyProperty());
        }
        Symbol symbol = key instanceof Symbol ? (Symbol) key : getSymbol((String) key);
        return remove(symbol, null, symbol.hashCode() ^ System.identityHashCode(null));
    }

    public Namespace defaultNamespace() {
        return Namespace.getDefault();
    }

    public Symbol getSymbol(String name) {
        return defaultNamespace().getSymbol(name);
    }

    public static Environment getInstance(String name) {
        if (name == null) {
            name = "";
        }
        synchronized (envTable) {
            Environment env = (Environment) envTable.get(name);
            if (env != null) {
                return env;
            }
            env = new SimpleEnvironment();
            env.setName(name);
            envTable.put(name, env);
            return env;
        }
    }

    public static Environment current() {
        return getCurrent();
    }

    public static Environment getCurrent() {
        Environment env = (Environment) curEnvironment.get();
        if (env != null) {
            return env;
        }
        env = make(Thread.currentThread().getName(), global);
        env.flags |= 8;
        curEnvironment.set(env);
        return env;
    }

    public static void setCurrent(Environment env) {
        curEnvironment.set(env);
    }

    public static Environment setSaveCurrent(Environment env) {
        Environment save = (Environment) curEnvironment.get();
        curEnvironment.set(env);
        return save;
    }

    public static void restoreCurrent(Environment saved) {
        curEnvironment.set(saved);
    }

    public static Environment user() {
        return getCurrent();
    }

    public final void addLocation(NamedLocation loc) {
        addLocation(loc.getKeySymbol(), loc.getKeyProperty(), loc);
    }

    public final void addLocation(EnvironmentKey key, Location loc) {
        addLocation(key.getKeySymbol(), key.getKeyProperty(), loc);
    }

    public static SimpleEnvironment make() {
        return new SimpleEnvironment();
    }

    public static SimpleEnvironment make(String name) {
        return new SimpleEnvironment(name);
    }

    public static InheritingEnvironment make(String name, Environment parent) {
        return new InheritingEnvironment(name, parent);
    }

    public String toString() {
        return "#<environment " + getName() + '>';
    }

    public String toStringVerbose() {
        return toString();
    }

    SimpleEnvironment cloneForThread() {
        InheritingEnvironment env = new InheritingEnvironment(null, this);
        if (this instanceof InheritingEnvironment) {
            InheritingEnvironment p = (InheritingEnvironment) this;
            int numInherited = p.numInherited;
            env.numInherited = numInherited;
            env.inherited = new Environment[numInherited];
            for (int i = 0; i < numInherited; i++) {
                env.inherited[i] = p.inherited[i];
            }
        }
        LocationEnumeration e = enumerateLocations();
        while (e.hasMoreElements()) {
            Location loc = e.nextLocation();
            Symbol name = loc.getKeySymbol();
            Object property = loc.getKeyProperty();
            if (name != null && (loc instanceof NamedLocation)) {
                NamedLocation nloc = (NamedLocation) loc;
                if (nloc.base == null) {
                    NamedLocation sloc = new SharedLocation(name, property, 0);
                    sloc.value = nloc.value;
                    nloc.base = sloc;
                    nloc.value = null;
                    nloc = sloc;
                }
                env.addUnboundLocation(name, property, name.hashCode() ^ System.identityHashCode(property)).base = nloc;
            }
        }
        return env;
    }
}

package gnu.mapping;

public class ThreadLocation extends NamedLocation implements Named {
    public static final String ANONYMOUS = new String("(dynamic)");
    static int counter;
    static SimpleEnvironment env;
    SharedLocation global;
    private int hash;
    private ThreadLocal<NamedLocation> thLocal;

    public class InheritingLocation extends InheritableThreadLocal<NamedLocation> {
        protected SharedLocation childValue(NamedLocation parentValue) {
            if (ThreadLocation.this.property != ThreadLocation.ANONYMOUS) {
                throw new Error();
            }
            if (parentValue == null) {
                parentValue = (SharedLocation) ThreadLocation.this.getLocation();
            }
            NamedLocation nloc = parentValue;
            if (nloc.base == null) {
                NamedLocation sloc = new SharedLocation(ThreadLocation.this.name, ThreadLocation.this.property, 0);
                sloc.value = nloc.value;
                nloc.base = sloc;
                nloc.value = null;
                nloc = sloc;
            }
            SharedLocation sloc2 = new SharedLocation(ThreadLocation.this.name, ThreadLocation.this.property, 0);
            sloc2.value = null;
            sloc2.base = nloc;
            return sloc2;
        }
    }

    private static synchronized int nextCounter() {
        int i;
        synchronized (ThreadLocation.class) {
            i = counter + 1;
            counter = i;
        }
        return i;
    }

    public ThreadLocation() {
        this("param#" + nextCounter());
    }

    public ThreadLocation(String name) {
        super(Symbol.makeUninterned(name), ANONYMOUS);
        this.thLocal = new InheritingLocation();
        this.global = new SharedLocation(this.name, null, 0);
    }

    private ThreadLocation(Symbol name) {
        super(name, ANONYMOUS);
        this.thLocal = new InheritingLocation();
        this.global = new SharedLocation(Symbol.makeUninterned(name == null ? null : name.toString()), null, 0);
    }

    public ThreadLocation(Symbol name, Object property, SharedLocation global) {
        super(name, property);
        this.hash = name.hashCode() ^ System.identityHashCode(property);
        this.global = global;
    }

    public static ThreadLocation makeAnonymous(String name) {
        return new ThreadLocation(name);
    }

    public static ThreadLocation makeAnonymous(Symbol name) {
        return new ThreadLocation(name);
    }

    public void setGlobal(Object value) {
        synchronized (this) {
            if (this.global == null) {
                this.global = new SharedLocation(this.name, null, 0);
            }
            this.global.set(value);
        }
    }

    public NamedLocation getLocation() {
        if (this.property != ANONYMOUS) {
            return Environment.getCurrent().getLocation(this.name, this.property, this.hash, true);
        }
        NamedLocation entry = (NamedLocation) this.thLocal.get();
        if (entry != null) {
            return entry;
        }
        entry = new SharedLocation(this.name, this.property, 0);
        if (this.global != null) {
            entry.setBase(this.global);
        }
        this.thLocal.set(entry);
        return entry;
    }

    public Object get(Object defaultValue) {
        return getLocation().get(defaultValue);
    }

    public void set(Object value) {
        getLocation().set(value);
    }

    public Object setWithSave(Object newValue) {
        return getLocation().setWithSave(newValue);
    }

    public void setRestore(Object oldValue) {
        getLocation().setRestore(oldValue);
    }

    public String getName() {
        return this.name == null ? null : this.name.toString();
    }

    public Object getSymbol() {
        if (this.name != null && this.property == ANONYMOUS && this.global.getKeySymbol() == this.name) {
            return this.name.toString();
        }
        return this.name;
    }

    public void setName(String name) {
        throw new RuntimeException("setName not allowed");
    }

    public static synchronized ThreadLocation getInstance(Symbol name, Object property) {
        ThreadLocation threadLocation;
        synchronized (ThreadLocation.class) {
            if (env == null) {
                env = new SimpleEnvironment("[thread-locations]");
            }
            IndirectableLocation loc = (IndirectableLocation) env.getLocation(name, property);
            if (loc.base != null) {
                threadLocation = (ThreadLocation) loc.base;
            } else {
                ThreadLocation tloc = new ThreadLocation(name, property, null);
                loc.base = tloc;
                threadLocation = tloc;
            }
        }
        return threadLocation;
    }
}

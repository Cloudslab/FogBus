package gnu.mapping;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Set;

public class SimpleEnvironment extends Environment {
    int currentTimestamp;
    int log2Size;
    private int mask;
    int num_bindings;
    NamedLocation sharedTail;
    NamedLocation[] table;

    public int size() {
        return this.num_bindings;
    }

    public static Location getCurrentLocation(String name) {
        return Environment.getCurrent().getLocation((Object) name, true);
    }

    public static Object lookup_global(Symbol name) throws UnboundLocationException {
        Location binding = Environment.getCurrent().lookup(name);
        if (binding != null) {
            return binding.get();
        }
        throw new UnboundLocationException((Object) name);
    }

    public SimpleEnvironment() {
        this(64);
    }

    public SimpleEnvironment(String name) {
        this();
        setName(name);
    }

    public SimpleEnvironment(int capacity) {
        this.log2Size = 4;
        while (capacity > (1 << this.log2Size)) {
            this.log2Size++;
        }
        capacity = 1 << this.log2Size;
        this.table = new NamedLocation[capacity];
        this.mask = capacity - 1;
        this.sharedTail = new PlainLocation(null, null, this);
    }

    public NamedLocation lookup(Symbol name, Object property, int hash) {
        return lookupDirect(name, property, hash);
    }

    public NamedLocation lookupDirect(Symbol name, Object property, int hash) {
        for (NamedLocation loc = this.table[hash & this.mask]; loc != null; loc = loc.next) {
            if (loc.matches(name, property)) {
                return loc;
            }
        }
        return null;
    }

    public synchronized NamedLocation getLocation(Symbol name, Object property, int hash, boolean create) {
        NamedLocation loc;
        loc = lookup(name, property, hash);
        if (loc == null) {
            if (create) {
                loc = addUnboundLocation(name, property, hash);
            } else {
                loc = null;
            }
        }
        return loc;
    }

    protected NamedLocation addUnboundLocation(Symbol name, Object property, int hash) {
        NamedLocation loc = newEntry(name, property, hash & this.mask);
        loc.base = null;
        loc.value = Location.UNBOUND;
        return loc;
    }

    public void put(Symbol key, Object property, Object newValue) {
        Location loc = getLocation(key, property, (this.flags & 4) != 0);
        if (loc == null) {
            throw new UnboundLocationException((Object) key);
        } else if (loc.isConstant()) {
            throw new IllegalStateException("attempt to modify read-only location: " + key + " in " + this + " loc:" + loc);
        } else {
            loc.set(newValue);
        }
    }

    NamedLocation newLocation(Symbol name, Object property) {
        if ((this.flags & 8) != 0) {
            return new SharedLocation(name, property, this.currentTimestamp);
        }
        return new PlainLocation(name, property);
    }

    NamedLocation newEntry(Symbol name, Object property, int index) {
        NamedLocation loc = newLocation(name, property);
        NamedLocation first = this.table[index];
        if (first == null) {
            first = this.sharedTail;
        }
        loc.next = first;
        this.table[index] = loc;
        this.num_bindings++;
        if (this.num_bindings >= this.table.length) {
            rehash();
        }
        return loc;
    }

    public NamedLocation define(Symbol sym, Object property, int hash, Object newValue) {
        NamedLocation loc;
        int index = hash & this.mask;
        for (loc = this.table[index]; loc != null; loc = loc.next) {
            if (loc.matches(sym, property)) {
                if (loc.isBound() ? getCanDefine() : getCanRedefine()) {
                    redefineError(sym, property, loc);
                }
                loc.base = null;
                loc.value = newValue;
                return loc;
            }
        }
        loc = newEntry(sym, property, index);
        loc.set(newValue);
        return loc;
    }

    public void define(Symbol sym, Object property, Object newValue) {
        define(sym, property, sym.hashCode() ^ System.identityHashCode(property), newValue);
    }

    protected void redefineError(Symbol name, Object property, Location loc) {
        throw new IllegalStateException("prohibited define/redefine of " + name + " in " + this);
    }

    public NamedLocation addLocation(Symbol name, Object property, Location loc) {
        return addLocation(name, property, name.hashCode() ^ System.identityHashCode(property), loc);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    gnu.mapping.NamedLocation addLocation(gnu.mapping.Symbol r7, java.lang.Object r8, int r9, gnu.mapping.Location r10) {
        /*
        r6 = this;
        r4 = 0;
        r3 = r10 instanceof gnu.mapping.ThreadLocation;
        if (r3 == 0) goto L_0x0012;
    L_0x0005:
        r3 = r10;
        r3 = (gnu.mapping.ThreadLocation) r3;
        r3 = r3.property;
        if (r3 != r8) goto L_0x0012;
    L_0x000c:
        r10 = (gnu.mapping.ThreadLocation) r10;
        r10 = r10.getLocation();
    L_0x0012:
        r1 = r6.lookupDirect(r7, r8, r9);
        if (r10 != r1) goto L_0x001a;
    L_0x0018:
        r2 = r1;
    L_0x0019:
        return r2;
    L_0x001a:
        if (r1 == 0) goto L_0x0055;
    L_0x001c:
        r0 = 1;
    L_0x001d:
        if (r0 != 0) goto L_0x0023;
    L_0x001f:
        r1 = r6.addUnboundLocation(r7, r8, r9);
    L_0x0023:
        r3 = r6.flags;
        r3 = r3 & 3;
        r5 = 3;
        if (r3 == r5) goto L_0x003b;
    L_0x002a:
        if (r0 == 0) goto L_0x0030;
    L_0x002c:
        r0 = r1.isBound();
    L_0x0030:
        if (r0 == 0) goto L_0x0057;
    L_0x0032:
        r3 = r6.flags;
        r3 = r3 & 2;
        if (r3 != 0) goto L_0x003b;
    L_0x0038:
        r6.redefineError(r7, r8, r1);
    L_0x003b:
        r3 = r6.flags;
        r3 = r3 & 32;
        if (r3 == 0) goto L_0x0064;
    L_0x0041:
        r6 = (gnu.mapping.InheritingEnvironment) r6;
        r3 = r6.getParent(r4);
        r3 = (gnu.mapping.SimpleEnvironment) r3;
        r3 = r3.addLocation(r7, r8, r9, r10);
        r1.base = r3;
    L_0x004f:
        r3 = gnu.mapping.IndirectableLocation.INDIRECT_FLUIDS;
        r1.value = r3;
        r2 = r1;
        goto L_0x0019;
    L_0x0055:
        r0 = r4;
        goto L_0x001d;
    L_0x0057:
        r3 = r6.flags;
        r3 = r3 & 1;
        if (r3 != 0) goto L_0x003b;
    L_0x005d:
        r3 = r10.isBound();
        if (r3 == 0) goto L_0x003b;
    L_0x0063:
        goto L_0x0038;
    L_0x0064:
        r1.base = r10;
        goto L_0x004f;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.mapping.SimpleEnvironment.addLocation(gnu.mapping.Symbol, java.lang.Object, int, gnu.mapping.Location):gnu.mapping.NamedLocation");
    }

    void rehash() {
        NamedLocation[] oldTable = this.table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity * 2;
        NamedLocation[] newTable = new NamedLocation[newCapacity];
        int newMask = newCapacity - 1;
        int i = oldCapacity;
        while (true) {
            i--;
            if (i >= 0) {
                NamedLocation element = oldTable[i];
                while (element != null && element != this.sharedTail) {
                    NamedLocation next = element.next;
                    int j = (element.name.hashCode() ^ System.identityHashCode(element.property)) & newMask;
                    NamedLocation head = newTable[j];
                    if (head == null) {
                        head = this.sharedTail;
                    }
                    element.next = head;
                    newTable[j] = element;
                    element = next;
                }
            } else {
                this.table = newTable;
                this.log2Size++;
                this.mask = newMask;
                return;
            }
        }
    }

    public Location unlink(Symbol symbol, Object property, int hash) {
        int index = hash & this.mask;
        NamedLocation prev = null;
        NamedLocation loc = this.table[index];
        while (loc != null) {
            NamedLocation next = loc.next;
            if (loc.matches(symbol, property)) {
                if (!getCanRedefine()) {
                    redefineError(symbol, property, loc);
                }
                if (prev == null) {
                    this.table[index] = next;
                } else {
                    prev.next = loc;
                }
                this.num_bindings--;
                return loc;
            }
            prev = loc;
            loc = next;
        }
        return null;
    }

    public LocationEnumeration enumerateLocations() {
        LocationEnumeration it = new LocationEnumeration(this.table, 1 << this.log2Size);
        it.env = this;
        return it;
    }

    public LocationEnumeration enumerateAllLocations() {
        return enumerateLocations();
    }

    protected boolean hasMoreElements(LocationEnumeration it) {
        while (true) {
            if (it.nextLoc == null) {
                it.prevLoc = null;
                int i = it.index - 1;
                it.index = i;
                if (i < 0) {
                    return false;
                }
                it.nextLoc = it.bindings[it.index];
                if (it.nextLoc == null) {
                    continue;
                }
            }
            if (it.nextLoc.name != null) {
                return true;
            }
            it.nextLoc = it.nextLoc.next;
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getSymbol());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setSymbol(in.readObject());
    }

    public Object readResolve() throws ObjectStreamException {
        String name = getName();
        Environment env = (Environment) envTable.get(name);
        if (env != null) {
            return env;
        }
        envTable.put(name, this);
        return this;
    }

    public Set entrySet() {
        return new EnvironmentMappings(this);
    }

    public String toStringVerbose() {
        StringBuffer sbuf = new StringBuffer();
        toStringBase(sbuf);
        return "#<environment " + getName() + " num:" + this.num_bindings + " ts:" + this.currentTimestamp + sbuf + '>';
    }

    protected void toStringBase(StringBuffer sbuf) {
    }
}

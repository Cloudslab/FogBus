package gnu.mapping;

public class InheritingEnvironment extends SimpleEnvironment {
    int baseTimestamp;
    Environment[] inherited;
    Namespace[] namespaceMap;
    int numInherited;
    Object[] propertyMap;

    public InheritingEnvironment(String name, Environment parent) {
        super(name);
        addParent(parent);
        if (parent instanceof SimpleEnvironment) {
            SimpleEnvironment simpleEnvironment = (SimpleEnvironment) parent;
            int timestamp = simpleEnvironment.currentTimestamp + 1;
            simpleEnvironment.currentTimestamp = timestamp;
            this.baseTimestamp = timestamp;
            this.currentTimestamp = timestamp;
        }
    }

    public final int getNumParents() {
        return this.numInherited;
    }

    public final Environment getParent(int index) {
        return this.inherited[index];
    }

    public void addParent(Environment env) {
        if (this.numInherited == 0) {
            this.inherited = new Environment[4];
        } else if (this.numInherited <= this.inherited.length) {
            Environment[] newInherited = new Environment[(this.numInherited * 2)];
            System.arraycopy(this.inherited, 0, newInherited, 0, this.numInherited);
            this.inherited = newInherited;
        }
        this.inherited[this.numInherited] = env;
        this.numInherited++;
    }

    public NamedLocation lookupInherited(Symbol name, Object property, int hash) {
        int i = 0;
        while (i < this.numInherited) {
            Symbol sym = name;
            Object prop = property;
            if (this.namespaceMap != null && this.namespaceMap.length > i * 2) {
                Namespace srcNamespace = this.namespaceMap[i * 2];
                Namespace dstNamespace = this.namespaceMap[(i * 2) + 1];
                if (!(srcNamespace == null && dstNamespace == null)) {
                    if (name.getNamespace() != dstNamespace) {
                        continue;
                        i++;
                    } else {
                        sym = Symbol.make(srcNamespace, name.getName());
                    }
                }
            }
            if (this.propertyMap != null && this.propertyMap.length > i * 2) {
                Object srcProperty = this.propertyMap[i * 2];
                Object dstProperty = this.propertyMap[(i * 2) + 1];
                if (!(srcProperty == null && dstProperty == null)) {
                    if (property == dstProperty) {
                        prop = srcProperty;
                    } else {
                        continue;
                        i++;
                    }
                }
            }
            NamedLocation loc = this.inherited[i].lookup(sym, prop, hash);
            if (loc != null && loc.isBound() && (!(loc instanceof SharedLocation) || ((SharedLocation) loc).timestamp < this.baseTimestamp)) {
                return loc;
            }
            i++;
        }
        return null;
    }

    public NamedLocation lookup(Symbol name, Object property, int hash) {
        NamedLocation loc = super.lookup(name, property, hash);
        return (loc == null || !loc.isBound()) ? lookupInherited(name, property, hash) : loc;
    }

    public synchronized NamedLocation getLocation(Symbol name, Object property, int hash, boolean create) {
        NamedLocation namedLocation;
        NamedLocation namedLocation2 = null;
        synchronized (this) {
            NamedLocation loc = lookupDirect(name, property, hash);
            if (loc == null || !(create || loc.isBound())) {
                if ((this.flags & 32) == 0 || !create) {
                    loc = lookupInherited(name, property, hash);
                } else {
                    loc = this.inherited[0].getLocation(name, property, hash, true);
                }
                if (loc == null) {
                    if (create) {
                        namedLocation2 = addUnboundLocation(name, property, hash);
                    }
                    namedLocation = namedLocation2;
                } else if (create) {
                    namedLocation = addUnboundLocation(name, property, hash);
                    if ((this.flags & 1) == 0 && loc.isBound()) {
                        redefineError(name, property, namedLocation);
                    }
                    namedLocation.base = loc;
                    if (loc.value == IndirectableLocation.INDIRECT_FLUIDS) {
                        namedLocation.value = loc.value;
                    } else if ((this.flags & 16) != 0) {
                        namedLocation.value = IndirectableLocation.DIRECT_ON_SET;
                    } else {
                        namedLocation.value = null;
                    }
                    if (namedLocation instanceof SharedLocation) {
                        ((SharedLocation) namedLocation).timestamp = this.baseTimestamp;
                    }
                } else {
                    namedLocation = loc;
                }
            } else {
                namedLocation = loc;
            }
        }
        return namedLocation;
    }

    public LocationEnumeration enumerateAllLocations() {
        LocationEnumeration it = new LocationEnumeration(this.table, 1 << this.log2Size);
        it.env = this;
        if (this.inherited != null && this.inherited.length > 0) {
            it.inherited = this.inherited[0].enumerateAllLocations();
            it.index = 0;
        }
        return it;
    }

    protected boolean hasMoreElements(LocationEnumeration it) {
        if (it.inherited != null) {
            while (true) {
                NamedLocation loc = it.nextLoc;
                while (true) {
                    it.inherited.nextLoc = loc;
                    if (!it.inherited.hasMoreElements()) {
                        break;
                    }
                    Location loc2 = it.inherited.nextLoc;
                    if (lookup(loc2.name, loc2.property) == loc2) {
                        it.nextLoc = loc2;
                        return true;
                    }
                    loc = loc2.next;
                }
                it.prevLoc = null;
                it.nextLoc = it.inherited.nextLoc;
                int i = it.index + 1;
                it.index = i;
                if (i == this.numInherited) {
                    break;
                }
                it.inherited = this.inherited[it.index].enumerateAllLocations();
            }
            it.inherited = null;
            it.bindings = this.table;
            it.index = 1 << this.log2Size;
        }
        return super.hasMoreElements(it);
    }

    protected void toStringBase(StringBuffer sbuf) {
        sbuf.append(" baseTs:");
        sbuf.append(this.baseTimestamp);
        for (int i = 0; i < this.numInherited; i++) {
            sbuf.append(" base:");
            sbuf.append(this.inherited[i].toStringVerbose());
        }
    }
}

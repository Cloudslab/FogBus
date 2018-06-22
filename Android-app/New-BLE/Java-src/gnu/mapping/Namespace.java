package gnu.mapping;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.util.Hashtable;

public class Namespace implements Externalizable, HasNamedParts {
    public static final Namespace EmptyNamespace = valueOf("");
    protected static final Hashtable nsTable = new Hashtable(50);
    int log2Size;
    private int mask;
    String name;
    int num_bindings;
    protected String prefix;
    protected SymbolRef[] table;

    public final String getName() {
        return this.name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getPrefix() {
        return this.prefix;
    }

    protected Namespace() {
        this(64);
    }

    protected Namespace(int capacity) {
        this.prefix = "";
        this.log2Size = 4;
        while (capacity > (1 << this.log2Size)) {
            this.log2Size++;
        }
        capacity = 1 << this.log2Size;
        this.table = new SymbolRef[capacity];
        this.mask = capacity - 1;
    }

    public static Namespace create(int capacity) {
        return new Namespace(capacity);
    }

    public static Namespace create() {
        return new Namespace(64);
    }

    public static Namespace getDefault() {
        return EmptyNamespace;
    }

    public static Symbol getDefaultSymbol(String name) {
        return EmptyNamespace.getSymbol(name);
    }

    public static Namespace valueOf() {
        return EmptyNamespace;
    }

    public static Namespace valueOf(String name) {
        if (name == null) {
            name = "";
        }
        synchronized (nsTable) {
            Namespace ns = (Namespace) nsTable.get(name);
            if (ns != null) {
                return ns;
            }
            ns = new Namespace();
            ns.setName(name.intern());
            nsTable.put(name, ns);
            return ns;
        }
    }

    public static Namespace valueOf(String uri, String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return valueOf(uri);
        }
        String xname = prefix + " -> " + uri;
        synchronized (nsTable) {
            Object old = nsTable.get(xname);
            if (old instanceof Namespace) {
                Namespace old2 = (Namespace) old;
                return old2;
            }
            Namespace ns = new Namespace();
            ns.setName(uri.intern());
            ns.prefix = prefix.intern();
            nsTable.put(xname, ns);
            return ns;
        }
    }

    public static Namespace valueOf(String uri, SimpleSymbol prefix) {
        return valueOf(uri, prefix == null ? null : prefix.getName());
    }

    public static Namespace makeUnknownNamespace(String prefix) {
        String uri;
        if (prefix == null || prefix == "") {
            uri = "";
        } else {
            uri = "http://kawa.gnu.org/unknown-namespace/" + prefix;
        }
        return valueOf(uri, prefix);
    }

    public Object get(String key) {
        return Environment.getCurrent().get(getSymbol(key));
    }

    public boolean isConstant(String key) {
        return false;
    }

    public Symbol getSymbol(String key) {
        return lookup(key, key.hashCode(), true);
    }

    public Symbol lookup(String key) {
        return lookup(key, key.hashCode(), false);
    }

    protected final Symbol lookupInternal(String key, int hash) {
        int index = hash & this.mask;
        SymbolRef prev = null;
        SymbolRef ref = this.table[index];
        while (ref != null) {
            SymbolRef next = ref.next;
            Symbol sym = ref.getSymbol();
            if (sym == null) {
                if (prev == null) {
                    this.table[index] = next;
                } else {
                    prev.next = next;
                }
                this.num_bindings--;
            } else if (sym.getLocalPart().equals(key)) {
                return sym;
            } else {
                prev = ref;
            }
            ref = next;
        }
        return null;
    }

    public Symbol add(Symbol sym, int hash) {
        int index = hash & this.mask;
        SymbolRef ref = new SymbolRef(sym, this);
        sym.namespace = this;
        ref.next = this.table[index];
        this.table[index] = ref;
        this.num_bindings++;
        if (this.num_bindings >= this.table.length) {
            rehash();
        }
        return sym;
    }

    public Symbol lookup(String key, int hash, boolean create) {
        synchronized (this) {
            Symbol sym = lookupInternal(key, hash);
            if (sym != null) {
                return sym;
            } else if (create) {
                if (this == EmptyNamespace) {
                    sym = new SimpleSymbol(key);
                } else {
                    sym = new Symbol(this, key);
                }
                Symbol add = add(sym, hash);
                return add;
            } else {
                return null;
            }
        }
    }

    public boolean remove(Symbol symbol) {
        boolean z;
        synchronized (this) {
            int index = symbol.getLocalPart().hashCode() & this.mask;
            SymbolRef prev = null;
            SymbolRef ref = this.table[index];
            while (ref != null) {
                SymbolRef next = ref.next;
                Symbol refsym = ref.getSymbol();
                if (refsym == null || refsym == symbol) {
                    if (prev == null) {
                        this.table[index] = next;
                    } else {
                        prev.next = next;
                    }
                    this.num_bindings--;
                    if (refsym != null) {
                        z = true;
                        break;
                    }
                } else {
                    prev = ref;
                }
                ref = next;
            }
            z = false;
        }
        return z;
    }

    protected void rehash() {
        int oldCapacity = this.table.length;
        int newCapacity = oldCapacity * 2;
        int newMask = newCapacity - 1;
        int countInserted = 0;
        SymbolRef[] oldTable = this.table;
        SymbolRef[] newTable = new SymbolRef[newCapacity];
        int i = oldCapacity;
        while (true) {
            i--;
            if (i >= 0) {
                SymbolRef ref = oldTable[i];
                while (ref != null) {
                    SymbolRef next = ref.next;
                    Symbol sym = ref.getSymbol();
                    if (sym != null) {
                        int index = sym.getName().hashCode() & newMask;
                        countInserted++;
                        ref.next = newTable[index];
                        newTable[index] = ref;
                    }
                    ref = next;
                }
            } else {
                this.table = newTable;
                this.log2Size++;
                this.mask = newMask;
                this.num_bindings = countInserted;
                return;
            }
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getName());
        out.writeObject(this.prefix);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = ((String) in.readObject()).intern();
        this.prefix = (String) in.readObject();
    }

    public Object readResolve() throws ObjectStreamException {
        String name = getName();
        if (name != null) {
            String xname = (this.prefix == null || this.prefix.length() == 0) ? name : this.prefix + " -> " + name;
            Namespace ns = (Namespace) nsTable.get(xname);
            if (ns != null) {
                return ns;
            }
            nsTable.put(xname, this);
        }
        return this;
    }

    public String toString() {
        StringBuilder sbuf = new StringBuilder("#,(namespace \"");
        sbuf.append(this.name);
        sbuf.append('\"');
        if (!(this.prefix == null || this.prefix == "")) {
            sbuf.append(' ');
            sbuf.append(this.prefix);
        }
        sbuf.append(')');
        return sbuf.toString();
    }
}

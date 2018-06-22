package gnu.mapping;

import java.lang.ref.WeakReference;

public class Table2D {
    private static Table2D instance = new Table2D();
    int log2Size;
    int mask;
    int num_bindings;
    Entry[] table;

    public static final Table2D getInstance() {
        return instance;
    }

    public Table2D() {
        this(64);
    }

    public Table2D(int capacity) {
        this.log2Size = 4;
        while (capacity > (1 << this.log2Size)) {
            this.log2Size++;
        }
        capacity = 1 << this.log2Size;
        this.table = new Entry[capacity];
        this.mask = capacity - 1;
    }

    public Object get(Object key1, Object key2, Object defaultValue) {
        Entry entry = lookup(key1, key2, System.identityHashCode(key1), System.identityHashCode(key2), false);
        return (entry == null || entry.value == entry) ? defaultValue : entry.value;
    }

    public boolean isBound(Object key1, Object key2) {
        Entry entry = lookup(key1, key2, System.identityHashCode(key1), System.identityHashCode(key2), false);
        if (entry == null || entry.value == entry) {
            return false;
        }
        return true;
    }

    public Object put(Object key1, Object key2, Object newValue) {
        Entry entry = lookup(key1, key2, System.identityHashCode(key1), System.identityHashCode(key2), true);
        Object oldValue = entry.getValue();
        entry.value = newValue;
        return oldValue;
    }

    public Object remove(Object key1, Object key2) {
        return remove(key1, key2, System.identityHashCode(key1) ^ System.identityHashCode(key2));
    }

    public Object remove(Object key1, Object key2, int hash) {
        return remove(key1, key2, hash);
    }

    public Object remove(Object key1, Object key2, int hash1, int hash2) {
        int index = (hash1 ^ hash2) & this.mask;
        Entry prev = null;
        Entry e = this.table[index];
        while (e != null) {
            Object k1 = e.key1;
            Object k2 = e.key2;
            boolean dead = false;
            if (k1 instanceof WeakReference) {
                k1 = ((WeakReference) k1).get();
                dead = k1 == null;
            }
            if (k2 instanceof WeakReference) {
                k2 = ((WeakReference) k2).get();
                dead = k2 == null;
            }
            Entry next = e.chain;
            Object oldValue = e.value;
            boolean matches = k1 == key1 && k2 == key2;
            if (dead || matches) {
                if (prev == null) {
                    this.table[index] = next;
                } else {
                    prev.chain = next;
                }
                this.num_bindings--;
                e.value = e;
            } else if (matches) {
                return oldValue;
            } else {
                prev = e;
            }
            e = next;
        }
        return null;
    }

    void rehash() {
        Entry[] oldTable = this.table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity * 2;
        Entry[] newTable = new Entry[newCapacity];
        int newMask = newCapacity - 1;
        this.num_bindings = 0;
        int i = oldCapacity;
        while (true) {
            i--;
            if (i >= 0) {
                Entry e = oldTable[i];
                while (e != null) {
                    Object k1 = e.key1;
                    Object k2 = e.key2;
                    boolean dead = false;
                    if (k1 instanceof WeakReference) {
                        k1 = ((WeakReference) k1).get();
                        dead = k1 == null;
                    }
                    if (k2 instanceof WeakReference) {
                        k2 = ((WeakReference) k2).get();
                        dead = k2 == null;
                    }
                    Entry next = e.chain;
                    if (dead) {
                        e.value = e;
                    } else {
                        int index = (System.identityHashCode(k1) ^ System.identityHashCode(k2)) & newMask;
                        e.chain = newTable[index];
                        newTable[index] = e;
                        this.num_bindings++;
                    }
                    e = next;
                }
            } else {
                this.table = newTable;
                this.log2Size++;
                this.mask = newMask;
                return;
            }
        }
    }

    protected Entry lookup(Object key1, Object key2, int hash1, int hash2, boolean create) {
        int index = (hash1 ^ hash2) & this.mask;
        Entry prev = null;
        Entry first = this.table[index];
        Entry e = first;
        while (e != null) {
            Object k1 = e.key1;
            Object k2 = e.key2;
            boolean dead = false;
            if (k1 instanceof WeakReference) {
                k1 = ((WeakReference) k1).get();
                dead = k1 == null;
            }
            if (k2 instanceof WeakReference) {
                k2 = ((WeakReference) k2).get();
                if (k2 == null) {
                }
                dead = true;
            }
            Entry next = e.chain;
            if (dead) {
                if (prev == null) {
                    this.table[index] = next;
                } else {
                    prev.chain = next;
                }
                this.num_bindings--;
                e.value = e;
            } else if (k1 == key1 && k2 == key2) {
                return e;
            } else {
                prev = e;
            }
            e = next;
        }
        if (!create) {
            return null;
        }
        e = new Entry();
        key1 = wrapReference(key1);
        key2 = wrapReference(key2);
        e.key1 = key1;
        e.key2 = key2;
        this.num_bindings++;
        e.chain = first;
        this.table[index] = e;
        e.value = e;
        return e;
    }

    protected Object wrapReference(Object key) {
        return (key == null || (key instanceof Symbol)) ? key : new WeakReference(key);
    }
}

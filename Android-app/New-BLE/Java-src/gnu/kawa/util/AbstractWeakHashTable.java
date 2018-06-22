package gnu.kawa.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map.Entry;

public abstract class AbstractWeakHashTable<K, V> extends AbstractHashTable<WEntry<K, V>, K, V> {
    ReferenceQueue<V> rqueue = new ReferenceQueue();

    public static class WEntry<K, V> extends WeakReference<V> implements Entry<K, V> {
        public int hash;
        AbstractWeakHashTable<K, V> htable;
        public WEntry next;

        public WEntry(V value, AbstractWeakHashTable<K, V> htable, int hash) {
            super(value, htable.rqueue);
            this.htable = htable;
            this.hash = hash;
        }

        public K getKey() {
            V v = get();
            return v == null ? null : this.htable.getKeyFromValue(v);
        }

        public V getValue() {
            return get();
        }

        public V setValue(V v) {
            throw new UnsupportedOperationException();
        }
    }

    protected abstract K getKeyFromValue(V v);

    public AbstractWeakHashTable() {
        super(64);
    }

    public AbstractWeakHashTable(int capacity) {
        super(capacity);
    }

    protected int getEntryHashCode(WEntry<K, V> entry) {
        return entry.hash;
    }

    protected WEntry<K, V> getEntryNext(WEntry<K, V> entry) {
        return entry.next;
    }

    protected void setEntryNext(WEntry<K, V> entry, WEntry<K, V> next) {
        entry.next = next;
    }

    protected WEntry<K, V>[] allocEntries(int n) {
        return new WEntry[n];
    }

    protected V getValueIfMatching(WEntry<K, V> node, Object key) {
        V val = node.getValue();
        return (val == null || !matches(getKeyFromValue(val), key)) ? null : val;
    }

    public V get(Object key, V defaultValue) {
        cleanup();
        return super.get(key, defaultValue);
    }

    public int hash(Object key) {
        return System.identityHashCode(key);
    }

    protected boolean valuesEqual(V oldValue, V newValue) {
        return oldValue == newValue;
    }

    protected WEntry<K, V> makeEntry(K k, int hash, V value) {
        return new WEntry(value, this, hash);
    }

    public V put(K key, V value) {
        cleanup();
        int hash = hash(key);
        int index = hashToIndex(hash);
        WEntry<K, V> first = ((WEntry[]) this.table)[index];
        WEntry<K, V> node = first;
        WEntry<K, V> prev = null;
        V v = null;
        while (node != null) {
            V curValue = node.getValue();
            if (curValue == value) {
                return curValue;
            }
            WEntry<K, V> next = node.next;
            if (curValue == null || !valuesEqual(curValue, value)) {
                prev = node;
            } else {
                if (prev == null) {
                    ((WEntry[]) this.table)[index] = next;
                } else {
                    prev.next = next;
                }
                v = curValue;
            }
            node = next;
        }
        int i = this.num_bindings + 1;
        this.num_bindings = i;
        if (i >= ((WEntry[]) this.table).length) {
            rehash();
            index = hashToIndex(hash);
            first = ((WEntry[]) this.table)[index];
        }
        node = makeEntry(null, hash, (Object) value);
        node.next = first;
        ((WEntry[]) this.table)[index] = node;
        return v;
    }

    protected void cleanup() {
        cleanup(this, this.rqueue);
    }

    static <Entry extends Entry<K, V>, K, V> void cleanup(AbstractHashTable<Entry, ?, ?> map, ReferenceQueue<?> rqueue) {
        while (true) {
            Entry oldref = (Entry) rqueue.poll();
            if (oldref != null) {
                int index = map.hashToIndex(map.getEntryHashCode(oldref));
                Entry prev = null;
                Entry node = map.table[index];
                while (node != null) {
                    Entry next = map.getEntryNext(node);
                    if (node == oldref) {
                        if (prev == null) {
                            map.table[index] = next;
                        } else {
                            map.setEntryNext(prev, next);
                        }
                        map.num_bindings--;
                    } else {
                        prev = node;
                        node = next;
                    }
                }
                map.num_bindings--;
            } else {
                return;
            }
        }
    }
}

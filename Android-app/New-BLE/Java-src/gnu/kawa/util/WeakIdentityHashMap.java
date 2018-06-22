package gnu.kawa.util;

import java.lang.ref.ReferenceQueue;

public class WeakIdentityHashMap<K, V> extends AbstractHashTable<WeakHashNode<K, V>, K, V> {
    ReferenceQueue<K> rqueue = new ReferenceQueue();

    public WeakIdentityHashMap() {
        super(64);
    }

    public WeakIdentityHashMap(int capacity) {
        super(capacity);
    }

    protected int getEntryHashCode(WeakHashNode<K, V> entry) {
        return entry.hash;
    }

    protected WeakHashNode<K, V> getEntryNext(WeakHashNode<K, V> entry) {
        return entry.next;
    }

    protected void setEntryNext(WeakHashNode<K, V> entry, WeakHashNode<K, V> next) {
        entry.next = next;
    }

    protected WeakHashNode<K, V>[] allocEntries(int n) {
        return new WeakHashNode[n];
    }

    public int hash(Object key) {
        return System.identityHashCode(key);
    }

    protected boolean matches(K key1, Object key2) {
        return key1 == key2;
    }

    protected WeakHashNode<K, V> makeEntry(K key, int hash, V value) {
        WeakHashNode<K, V> node = new WeakHashNode(key, this.rqueue, hash);
        node.value = value;
        return node;
    }

    public V get(Object key, V defaultValue) {
        cleanup();
        return super.get(key, defaultValue);
    }

    public V put(K key, int hash, V value) {
        cleanup();
        return super.put(key, hash, value);
    }

    public V remove(Object key) {
        cleanup();
        return super.remove(key);
    }

    void cleanup() {
        AbstractWeakHashTable.cleanup(this, this.rqueue);
    }
}

package gnu.kawa.util;

public class GeneralHashTable<K, V> extends AbstractHashTable<HashNode<K, V>, K, V> {
    public GeneralHashTable(int capacity) {
        super(capacity);
    }

    protected int getEntryHashCode(HashNode<K, V> entry) {
        return entry.hash;
    }

    protected HashNode<K, V> getEntryNext(HashNode<K, V> entry) {
        return entry.next;
    }

    protected void setEntryNext(HashNode<K, V> entry, HashNode<K, V> next) {
        entry.next = next;
    }

    protected HashNode<K, V>[] allocEntries(int n) {
        return new HashNode[n];
    }

    protected HashNode<K, V> makeEntry(K key, int hash, V value) {
        HashNode<K, V> node = new HashNode(key, value);
        node.hash = hash;
        return node;
    }

    public HashNode<K, V> getNode(Object key) {
        return (HashNode) super.getNode(key);
    }
}

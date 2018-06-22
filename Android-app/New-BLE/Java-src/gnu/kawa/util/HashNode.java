package gnu.kawa.util;

import java.util.Map.Entry;

public class HashNode<K, V> implements Entry<K, V> {
    int hash;
    K key;
    public HashNode<K, V> next;
    V value;

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.key == null ? 0 : this.key.hashCode();
        if (this.value != null) {
            i = this.value.hashCode();
        }
        return hashCode ^ i;
    }

    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public V get(V v) {
        return getValue();
    }

    public boolean equals(Object o) {
        if (!(o instanceof HashNode)) {
            return false;
        }
        HashNode h2 = (HashNode) o;
        if (this.key == null) {
            if (h2.key != null) {
                return false;
            }
        } else if (!this.key.equals(h2.key)) {
            return false;
        }
        if (this.value == null) {
            if (h2.value != null) {
                return false;
            }
        } else if (!this.value.equals(h2.value)) {
            return false;
        }
        return true;
    }
}

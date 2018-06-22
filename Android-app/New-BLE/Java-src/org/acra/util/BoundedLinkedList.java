package org.acra.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class BoundedLinkedList<E> extends LinkedList<E> {
    private final int maxSize;

    public BoundedLinkedList(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean add(E object) {
        if (size() == this.maxSize) {
            removeFirst();
        }
        return super.add(object);
    }

    public void add(int location, E object) {
        if (size() == this.maxSize) {
            removeFirst();
        }
        super.add(location, object);
    }

    public boolean addAll(Collection<? extends E> collection) {
        int overhead = (size() + collection.size()) - this.maxSize;
        if (overhead > 0) {
            removeRange(0, overhead);
        }
        return super.addAll(collection);
    }

    public boolean addAll(int location, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public void addFirst(E e) {
        throw new UnsupportedOperationException();
    }

    public void addLast(E object) {
        add(object);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            result.append(i$.next().toString());
        }
        return result.toString();
    }
}

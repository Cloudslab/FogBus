package com.google.appinventor.components.runtime.collect;

import java.util.HashMap;
import java.util.TreeMap;

public class Maps {
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap();
    }
}

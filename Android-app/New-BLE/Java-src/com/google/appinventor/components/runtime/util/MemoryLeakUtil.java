package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.runtime.collect.Maps;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryLeakUtil {
    private static final String LOG_TAG = "MemoryLeakUtil";
    private static final Map<String, WeakReference<Object>> TRACKED_OBJECTS = Maps.newTreeMap();
    private static final AtomicInteger prefixGenerator = new AtomicInteger(0);

    private MemoryLeakUtil() {
    }

    public static String trackObject(String tag, Object object) {
        String key = tag == null ? prefixGenerator.incrementAndGet() + "_" : prefixGenerator.incrementAndGet() + "_" + tag;
        TRACKED_OBJECTS.put(key, new WeakReference(object));
        return key;
    }

    public static boolean isTrackedObjectCollected(String key, boolean stopTrackingIfCollected) {
        System.gc();
        WeakReference<Object> ref = (WeakReference) TRACKED_OBJECTS.get(key);
        if (ref != null) {
            Object o = ref.get();
            Log.i(LOG_TAG, "Object with tag " + key.substring(key.indexOf("_") + 1) + " has " + (o != null ? "not " : "") + "been garbage collected.");
            if (stopTrackingIfCollected && o == null) {
                TRACKED_OBJECTS.remove(key);
            }
            if (o == null) {
                return true;
            }
            return false;
        }
        throw new IllegalArgumentException("key not found");
    }

    public static void checkAllTrackedObjects(boolean verbose, boolean stopTrackingCollectedObjects) {
        Log.i(LOG_TAG, "Checking Tracked Objects ----------------------------------------");
        System.gc();
        int countRemaining = 0;
        int countCollected = 0;
        Iterator<Entry<String, WeakReference<Object>>> it = TRACKED_OBJECTS.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, WeakReference<Object>> entry = (Entry) it.next();
            String key = (String) entry.getKey();
            Object o = ((WeakReference) entry.getValue()).get();
            if (o != null) {
                countRemaining++;
            } else {
                countCollected++;
                if (stopTrackingCollectedObjects) {
                    it.remove();
                }
            }
            if (verbose) {
                String str;
                String tag = key.substring(key.indexOf("_") + 1);
                String str2 = LOG_TAG;
                StringBuilder append = new StringBuilder().append("Object with tag ").append(tag).append(" has ");
                if (o != null) {
                    str = "not ";
                } else {
                    str = "";
                }
                Log.i(str2, append.append(str).append("been garbage collected.").toString());
            }
        }
        Log.i(LOG_TAG, "summary: collected " + countCollected);
        Log.i(LOG_TAG, "summary: remaining " + countRemaining);
        Log.i(LOG_TAG, "-----------------------------------------------------------------");
    }
}

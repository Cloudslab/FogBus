package com.google.appinventor.components.runtime.util;

import android.util.Log;
import java.io.Closeable;
import java.io.IOException;

public final class IOUtils {
    public static void closeQuietly(String tag, Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.w(tag, "Failed to close resource", e);
            }
        }
    }
}

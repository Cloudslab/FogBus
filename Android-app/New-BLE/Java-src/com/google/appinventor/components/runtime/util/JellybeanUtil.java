package com.google.appinventor.components.runtime.util;

import android.graphics.Point;
import android.view.Display;

public class JellybeanUtil {
    private JellybeanUtil() {
    }

    public static void getRealSize(Display display, Point outSize) {
        display.getRealSize(outSize);
    }
}

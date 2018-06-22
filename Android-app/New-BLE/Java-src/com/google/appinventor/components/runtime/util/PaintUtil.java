package com.google.appinventor.components.runtime.util;

import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;

public class PaintUtil {
    private PaintUtil() {
    }

    public static void changePaint(Paint paint, int argb) {
        paint.setColor(16777215 & argb);
        paint.setAlpha((argb >> 24) & 255);
        paint.setXfermode(null);
    }

    public static void changePaintTransparent(Paint paint) {
        paint.setAlpha(0);
        paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
    }

    public static int hexStringToInt(String argb) {
        String unprefixed = argb;
        if (argb.startsWith("#x") || argb.startsWith("&H")) {
            unprefixed = argb.substring(2);
        }
        long l = Long.parseLong(unprefixed, 16);
        if (l > 2147483647L) {
            l += 0;
        }
        return (int) l;
    }
}

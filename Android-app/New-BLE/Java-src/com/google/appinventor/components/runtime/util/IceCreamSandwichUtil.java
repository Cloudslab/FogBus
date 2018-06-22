package com.google.appinventor.components.runtime.util;

import android.os.Build.VERSION;
import android.widget.TextView;

public final class IceCreamSandwichUtil {
    private IceCreamSandwichUtil() {
    }

    public static void setAllCaps(TextView view, boolean allCaps) {
        if (VERSION.SDK_INT >= 14) {
            view.setAllCaps(allCaps);
        }
    }
}

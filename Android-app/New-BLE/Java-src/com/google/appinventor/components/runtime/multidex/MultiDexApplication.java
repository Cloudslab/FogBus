package com.google.appinventor.components.runtime.multidex;

import android.app.Application;
import android.content.Context;

public class MultiDexApplication extends Application {
    public static boolean installed = false;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this, true);
    }
}

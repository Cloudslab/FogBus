package org.acra.collector;

import android.content.Context;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.util.Log;
import java.lang.reflect.Field;
import org.acra.ACRA;

final class SettingsCollector {
    SettingsCollector() {
    }

    public static String collectSystemSettings(Context ctx) {
        StringBuilder result = new StringBuilder();
        for (Field key : System.class.getFields()) {
            if (!key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class) {
                try {
                    String value = System.getString(ctx.getContentResolver(), (String) key.get(null));
                    if (value != null) {
                        result.append(key.getName()).append("=").append(value).append("\n");
                    }
                } catch (IllegalArgumentException e) {
                    Log.w(ACRA.LOG_TAG, "Error : ", e);
                } catch (IllegalAccessException e2) {
                    Log.w(ACRA.LOG_TAG, "Error : ", e2);
                }
            }
        }
        return result.toString();
    }

    public static String collectSecureSettings(Context ctx) {
        StringBuilder result = new StringBuilder();
        for (Field key : Secure.class.getFields()) {
            if (!key.isAnnotationPresent(Deprecated.class) && key.getType() == String.class && isAuthorized(key)) {
                try {
                    String value = Secure.getString(ctx.getContentResolver(), (String) key.get(null));
                    if (value != null) {
                        result.append(key.getName()).append("=").append(value).append("\n");
                    }
                } catch (IllegalArgumentException e) {
                    Log.w(ACRA.LOG_TAG, "Error : ", e);
                } catch (IllegalAccessException e2) {
                    Log.w(ACRA.LOG_TAG, "Error : ", e2);
                }
            }
        }
        return result.toString();
    }

    private static boolean isAuthorized(Field key) {
        if (key == null || key.getName().startsWith("WIFI_AP")) {
            return false;
        }
        return true;
    }
}

package org.acra.collector;

import android.content.Context;
import android.os.Build.VERSION;
import java.lang.reflect.Field;

class Compatibility {
    Compatibility() {
    }

    public static int getAPILevel() {
        try {
            return VERSION.class.getField("SDK_INT").getInt(null);
        } catch (SecurityException e) {
            return Integer.parseInt(VERSION.SDK);
        } catch (NoSuchFieldException e2) {
            return Integer.parseInt(VERSION.SDK);
        } catch (IllegalArgumentException e3) {
            return Integer.parseInt(VERSION.SDK);
        } catch (IllegalAccessException e4) {
            return Integer.parseInt(VERSION.SDK);
        }
    }

    public static String getDropBoxServiceName() throws NoSuchFieldException, IllegalAccessException {
        Field serviceName = Context.class.getField("DROPBOX_SERVICE");
        if (serviceName != null) {
            return (String) serviceName.get(null);
        }
        return null;
    }
}

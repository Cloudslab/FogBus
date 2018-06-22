package org.acra.util;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import org.acra.ACRA;

public final class ReportUtils {
    public static long getAvailableInternalMemorySize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
    }

    public static long getTotalInternalMemorySize() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) stat.getBlockCount()) * ((long) stat.getBlockSize());
    }

    public static String getDeviceId(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        } catch (RuntimeException e) {
            Log.w(ACRA.LOG_TAG, "Couldn't retrieve DeviceId for : " + context.getPackageName(), e);
            return null;
        }
    }

    public static String getApplicationFilePath(Context context) {
        File filesDir = context.getFilesDir();
        if (filesDir != null) {
            return filesDir.getAbsolutePath();
        }
        Log.w(ACRA.LOG_TAG, "Couldn't retrieve ApplicationFilePath for : " + context.getPackageName());
        return "Couldn't retrieve ApplicationFilePath";
    }

    public static String getDisplayDetails(Context context) {
        try {
            Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            StringBuilder result = new StringBuilder();
            result.append("width=").append(display.getWidth()).append('\n');
            result.append("height=").append(display.getHeight()).append('\n');
            result.append("pixelFormat=").append(display.getPixelFormat()).append('\n');
            result.append("refreshRate=").append(display.getRefreshRate()).append("fps").append('\n');
            result.append("metrics.density=x").append(metrics.density).append('\n');
            result.append("metrics.scaledDensity=x").append(metrics.scaledDensity).append('\n');
            result.append("metrics.widthPixels=").append(metrics.widthPixels).append('\n');
            result.append("metrics.heightPixels=").append(metrics.heightPixels).append('\n');
            result.append("metrics.xdpi=").append(metrics.xdpi).append('\n');
            result.append("metrics.ydpi=").append(metrics.ydpi);
            return result.toString();
        } catch (RuntimeException e) {
            Log.w(ACRA.LOG_TAG, "Couldn't retrieve DisplayDetails for : " + context.getPackageName(), e);
            return "Couldn't retrieve Display Details";
        }
    }

    public static String sparseArrayToString(SparseArray<?> sparseArray) {
        StringBuilder result = new StringBuilder();
        if (sparseArray == null) {
            return "null";
        }
        result.append('{');
        for (int i = 0; i < sparseArray.size(); i++) {
            result.append(sparseArray.keyAt(i));
            result.append(" => ");
            if (sparseArray.valueAt(i) == null) {
                result.append("null");
            } else {
                result.append(sparseArray.valueAt(i).toString());
            }
            if (i < sparseArray.size() - 1) {
                result.append(", ");
            }
        }
        result.append('}');
        return result.toString();
    }
}

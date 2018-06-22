package org.acra;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;

final class CrashReportFinder {
    private final Context context;

    class C03831 implements FilenameFilter {
        C03831() {
        }

        public boolean accept(File dir, String name) {
            return name.endsWith(ACRAConstants.REPORTFILE_EXTENSION);
        }
    }

    public CrashReportFinder(Context context) {
        this.context = context;
    }

    public String[] getCrashReportFiles() {
        if (this.context == null) {
            Log.e(ACRA.LOG_TAG, "Trying to get ACRA reports but ACRA is not initialized.");
            return new String[0];
        }
        File dir = this.context.getFilesDir();
        if (dir == null) {
            Log.w(ACRA.LOG_TAG, "Application files directory does not exist! The application may not be installed correctly. Please try reinstalling.");
            return new String[0];
        }
        Log.d(ACRA.LOG_TAG, "Looking for error files in " + dir.getAbsolutePath());
        String[] result = dir.list(new C03831());
        return result == null ? new String[0] : result;
    }
}

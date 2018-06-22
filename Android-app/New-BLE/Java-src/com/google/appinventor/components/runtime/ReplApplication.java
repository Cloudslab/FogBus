package com.google.appinventor.components.runtime;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.google.appinventor.common.version.GitBuildId;
import com.google.appinventor.components.runtime.multidex.MultiDex;
import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formKey = "")
public class ReplApplication extends Application {
    public static boolean installed = true;
    private static ReplApplication thisInstance;
    private boolean active = false;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        installed = MultiDex.install(this, false);
    }

    public void onCreate() {
        super.onCreate();
        thisInstance = this;
        String acraUri = GitBuildId.getAcraUri();
        if (acraUri.equals("")) {
            Log.i("ReplApplication", "ACRA Not Active");
            return;
        }
        Log.i("ReplApplication", "ACRA Active, URI = " + acraUri);
        ACRAConfiguration config = ACRA.getNewDefaultConfig(this);
        config.setFormUri(acraUri);
        config.setDisableSSLCertValidation(true);
        ACRA.setConfig(config);
        ACRA.init(this);
        this.active = true;
    }

    public static void reportError(Throwable ex, String reportId) {
        ACRA.getErrorReporter().putCustomData("reportid", reportId);
        reportError(ex);
    }

    public static void reportError(Throwable ex) {
        if (thisInstance != null && thisInstance.active) {
            ACRA.getErrorReporter().handleException(ex);
        }
    }

    public static boolean isAcraActive() {
        if (thisInstance == null || !thisInstance.active) {
            return false;
        }
        return true;
    }
}

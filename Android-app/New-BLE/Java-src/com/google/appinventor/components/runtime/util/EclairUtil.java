package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.webkit.GeolocationPermissions;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import com.google.appinventor.components.runtime.WebViewer;

public class EclairUtil {
    private EclairUtil() {
    }

    public static void overridePendingTransitions(Activity activity, int enterAnim, int exitAnim) {
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    public static void setupWebViewGeoLoc(final WebViewer caller, WebView webview, final Activity activity) {
        webview.getSettings().setGeolocationDatabasePath(activity.getFilesDir().getAbsolutePath());
        webview.getSettings().setDatabaseEnabled(true);
        webview.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
                final Callback theCallback = callback;
                final String theOrigin = origin;
                if (caller.PromptforPermission()) {
                    AlertDialog alertDialog = new Builder(activity).create();
                    alertDialog.setTitle("Permission Request");
                    if (origin.equals("file://")) {
                        origin = "This Application";
                    }
                    alertDialog.setMessage(origin + " would like to access your location.");
                    alertDialog.setButton(-1, "Allow", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            theCallback.invoke(theOrigin, true, true);
                        }
                    });
                    alertDialog.setButton(-2, "Refuse", new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            theCallback.invoke(theOrigin, false, true);
                        }
                    });
                    alertDialog.show();
                    return;
                }
                callback.invoke(origin, true, true);
            }
        });
    }

    public static void clearWebViewGeoLoc() {
        GeolocationPermissions.getInstance().clearAll();
    }

    public static String getInstallerPackageName(String pname, Activity form) {
        return form.getPackageManager().getInstallerPackageName(pname);
    }

    public static void disableSuggestions(EditText textview) {
        textview.setInputType(textview.getInputType() | 524288);
    }
}

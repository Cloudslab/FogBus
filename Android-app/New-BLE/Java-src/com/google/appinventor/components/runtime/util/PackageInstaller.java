package com.google.appinventor.components.runtime.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class PackageInstaller {
    private static final String LOG_TAG = "PackageInstaller(AppInventor)";
    private static final String REPL_ASSET_DIR = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/AppInventor/assets/");

    private PackageInstaller() {
    }

    public static void doPackageInstall(final Form form, final String inurl) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                try {
                    URLConnection conn = new URL(inurl).openConnection();
                    File rootDir = new File(PackageInstaller.REPL_ASSET_DIR);
                    InputStream instream = new BufferedInputStream(conn.getInputStream());
                    FileOutputStream apkOut = new FileOutputStream(new File(rootDir + "/package.apk"));
                    byte[] buffer = new byte[32768];
                    while (true) {
                        int len = instream.read(buffer, 0, 32768);
                        if (len > 0) {
                            apkOut.write(buffer, 0, len);
                        } else {
                            instream.close();
                            apkOut.close();
                            Log.d(PackageInstaller.LOG_TAG, "About to Install package from " + inurl);
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.setDataAndType(Uri.fromFile(new File(rootDir + "/package.apk")), "application/vnd.android.package-archive");
                            form.startActivity(intent);
                            return;
                        }
                    }
                } catch (Exception e) {
                    form.dispatchErrorOccurredEvent(form, "PackageInstaller", ErrorMessages.ERROR_WEB_UNABLE_TO_GET, inurl);
                }
            }
        });
    }
}

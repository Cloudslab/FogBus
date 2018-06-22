package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.AppInvHTTPD;
import com.google.appinventor.components.runtime.util.PackageInstaller;
import java.security.MessageDigest;
import java.util.Formatter;

@SimpleObject
@DesignerComponent(category = ComponentCategory.INTERNAL, description = "Component that returns information about the phone.", iconName = "images/phoneip.png", nonVisible = true, version = 1)
public class PhoneStatus extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG = "PhoneStatus";
    private static Activity activity;
    private static PhoneStatus mainInstance = null;
    private final Form form;

    public PhoneStatus(ComponentContainer container) {
        super(container.$form());
        this.form = container.$form();
        activity = container.$context();
        if (mainInstance == null) {
            mainInstance = this;
        }
    }

    @SimpleFunction(description = "Returns the IP address of the phone in the form of a String")
    public static String GetWifiIpAddress() {
        int s_ipAddress = ((WifiManager) activity.getSystemService("wifi")).getDhcpInfo().ipAddress;
        if (isConnected()) {
            return intToIp(s_ipAddress);
        }
        return "Error: No Wifi Connection";
    }

    @SimpleFunction(description = "Returns TRUE if the phone is on Wifi, FALSE otherwise")
    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService("connectivity");
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getNetworkInfo(1);
        }
        return networkInfo == null ? false : networkInfo.isConnected();
    }

    @SimpleFunction(description = "Establish the secret seed for HOTP generation. Return the SHA1 of the provided seed, this will be used to contact the rendezvous server.")
    public String setHmacSeedReturnCode(String seed) {
        AppInvHTTPD.setHmacKey(seed);
        try {
            MessageDigest Sha1 = MessageDigest.getInstance("SHA1");
            Sha1.update(seed.getBytes());
            byte[] result = Sha1.digest();
            StringBuffer sb = new StringBuffer(result.length * 2);
            Formatter formatter = new Formatter(sb);
            int len$ = result.length;
            for (int i$ = 0; i$ < len$; i$++) {
                formatter.format("%02x", new Object[]{Byte.valueOf(arr$[i$])});
            }
            Log.d(LOG_TAG, "Seed = " + seed);
            Log.d(LOG_TAG, "Code = " + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception getting SHA1 Instance", e);
            return "";
        }
    }

    @SimpleFunction(description = "Returns true if we are running in the emulator or USB Connection")
    public boolean isDirect() {
        Log.d(LOG_TAG, "android.os.Build.VERSION.RELEASE = " + VERSION.RELEASE);
        Log.d(LOG_TAG, "android.os.Build.PRODUCT = " + Build.PRODUCT);
        if (Build.PRODUCT.contains("google_sdk")) {
            return true;
        }
        if (this.form instanceof ReplForm) {
            return ((ReplForm) this.form).isDirect();
        }
        return false;
    }

    @SimpleFunction(description = "Start the internal AppInvHTTPD to listen for incoming forms. FOR REPL USE ONLY!")
    public void startHTTPD(boolean secure) {
        ReplForm.topform.startHTTPD(secure);
    }

    @SimpleFunction(description = "Declare that we have loaded our initial assets and other assets should come from the sdcard")
    public void setAssetsLoaded() {
        if (this.form instanceof ReplForm) {
            ((ReplForm) this.form).setAssetsLoaded();
        }
    }

    @SimpleFunction(description = "Causes an Exception, used to debug exception processing.")
    public static void doFault() throws Exception {
        throw new Exception("doFault called!");
    }

    @SimpleFunction(description = "Obtain the Android Application Version")
    public String getVersionName() {
        try {
            return this.form.getPackageManager().getPackageInfo(this.form.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            Log.e(LOG_TAG, "Exception fetching package name.", e);
            return "";
        }
    }

    @SimpleFunction(description = "Downloads the URL and installs it as an Android Package")
    public void installURL(String url) {
        PackageInstaller.doPackageInstall(this.form, url);
    }

    @SimpleFunction(description = "Really Exit the Application")
    public void shutdown() {
        this.form.finish();
        System.exit(0);
    }

    @SimpleEvent
    public void OnSettings() {
        EventDispatcher.dispatchEvent(this, "OnSettings", new Object[0]);
    }

    static void doSettings() {
        Log.d(LOG_TAG, "doSettings called.");
        if (mainInstance != null) {
            mainInstance.OnSettings();
        } else {
            Log.d(LOG_TAG, "mainStance is null on doSettings");
        }
    }

    public static String intToIp(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }
}

package com.google.appinventor.common.version;

public final class AppInventorFeatures {
    private AppInventorFeatures() {
    }

    public static boolean hasDebuggingView() {
        return true;
    }

    public static boolean hasYailGenerationOption() {
        return true;
    }

    public static boolean sendBugReports() {
        return true;
    }

    public static boolean allowMultiScreenApplications() {
        return true;
    }

    public static boolean showInternalComponentsCategory() {
        return false;
    }

    public static boolean takeScreenShots() {
        return false;
    }

    public static boolean trackClientEvents() {
        return false;
    }

    public static boolean showSplashScreen() {
        return true;
    }

    public static boolean showSurveySplashScreen() {
        return true;
    }

    public static boolean requireOneLogin() {
        return true;
    }
}

package org.acra.collector;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;
import org.acra.util.Installation;
import org.acra.util.PackageManagerWrapper;
import org.acra.util.ReportUtils;

public final class CrashReportDataFactory {
    private final Time appStartDate;
    private final Context context;
    private final List<ReportField> crashReportFields;
    private final Map<String, String> customParameters = new HashMap();
    private final String initialConfiguration;
    private final SharedPreferences prefs;

    public CrashReportDataFactory(Context context, SharedPreferences prefs, Time appStartDate, String initialConfiguration) {
        ReportField[] fieldsList;
        this.context = context;
        this.prefs = prefs;
        this.appStartDate = appStartDate;
        this.initialConfiguration = initialConfiguration;
        ReportsCrashes config = ACRA.getConfig();
        ReportField[] customReportFields = config.customReportContent();
        if (customReportFields.length != 0) {
            Log.d(ACRA.LOG_TAG, "Using custom Report Fields");
            fieldsList = customReportFields;
        } else if (config.mailTo() == null || "".equals(config.mailTo())) {
            Log.d(ACRA.LOG_TAG, "Using default Report Fields");
            fieldsList = ACRA.DEFAULT_REPORT_FIELDS;
        } else {
            Log.d(ACRA.LOG_TAG, "Using default Mail Report Fields");
            fieldsList = ACRA.DEFAULT_MAIL_REPORT_FIELDS;
        }
        this.crashReportFields = Arrays.asList(fieldsList);
    }

    public String putCustomData(String key, String value) {
        return (String) this.customParameters.put(key, value);
    }

    public String removeCustomData(String key) {
        return (String) this.customParameters.remove(key);
    }

    public String getCustomData(String key) {
        return (String) this.customParameters.get(key);
    }

    public CrashReportData createCrashData(Throwable th, boolean isSilentReport, Thread brokenThread) {
        CrashReportData crashReportData = new CrashReportData();
        try {
            crashReportData.put(ReportField.STACK_TRACE, getStackTrace(th));
            crashReportData.put(ReportField.USER_APP_START_DATE, this.appStartDate.format3339(false));
            if (isSilentReport) {
                crashReportData.put(ReportField.IS_SILENT, "true");
            }
            if (this.crashReportFields.contains(ReportField.REPORT_ID)) {
                crashReportData.put(ReportField.REPORT_ID, UUID.randomUUID().toString());
            }
            if (this.crashReportFields.contains(ReportField.INSTALLATION_ID)) {
                crashReportData.put(ReportField.INSTALLATION_ID, Installation.id(this.context));
            }
            if (this.crashReportFields.contains(ReportField.INITIAL_CONFIGURATION)) {
                crashReportData.put(ReportField.INITIAL_CONFIGURATION, this.initialConfiguration);
            }
            if (this.crashReportFields.contains(ReportField.CRASH_CONFIGURATION)) {
                crashReportData.put(ReportField.CRASH_CONFIGURATION, ConfigurationCollector.collectConfiguration(this.context));
            }
            if (!(th instanceof OutOfMemoryError) && this.crashReportFields.contains(ReportField.DUMPSYS_MEMINFO)) {
                crashReportData.put(ReportField.DUMPSYS_MEMINFO, DumpSysCollector.collectMemInfo());
            }
            if (this.crashReportFields.contains(ReportField.PACKAGE_NAME)) {
                crashReportData.put(ReportField.PACKAGE_NAME, this.context.getPackageName());
            }
            if (this.crashReportFields.contains(ReportField.BUILD)) {
                crashReportData.put(ReportField.BUILD, ReflectionCollector.collectConstants(Build.class));
            }
            if (this.crashReportFields.contains(ReportField.PHONE_MODEL)) {
                crashReportData.put(ReportField.PHONE_MODEL, Build.MODEL);
            }
            if (this.crashReportFields.contains(ReportField.ANDROID_VERSION)) {
                crashReportData.put(ReportField.ANDROID_VERSION, VERSION.RELEASE);
            }
            if (this.crashReportFields.contains(ReportField.BRAND)) {
                crashReportData.put(ReportField.BRAND, Build.BRAND);
            }
            if (this.crashReportFields.contains(ReportField.PRODUCT)) {
                crashReportData.put(ReportField.PRODUCT, Build.PRODUCT);
            }
            if (this.crashReportFields.contains(ReportField.TOTAL_MEM_SIZE)) {
                crashReportData.put(ReportField.TOTAL_MEM_SIZE, Long.toString(ReportUtils.getTotalInternalMemorySize()));
            }
            if (this.crashReportFields.contains(ReportField.AVAILABLE_MEM_SIZE)) {
                crashReportData.put(ReportField.AVAILABLE_MEM_SIZE, Long.toString(ReportUtils.getAvailableInternalMemorySize()));
            }
            if (this.crashReportFields.contains(ReportField.FILE_PATH)) {
                crashReportData.put(ReportField.FILE_PATH, ReportUtils.getApplicationFilePath(this.context));
            }
            if (this.crashReportFields.contains(ReportField.DISPLAY)) {
                crashReportData.put(ReportField.DISPLAY, ReportUtils.getDisplayDetails(this.context));
            }
            if (this.crashReportFields.contains(ReportField.USER_CRASH_DATE)) {
                Time curDate = new Time();
                curDate.setToNow();
                crashReportData.put(ReportField.USER_CRASH_DATE, curDate.format3339(false));
            }
            if (this.crashReportFields.contains(ReportField.CUSTOM_DATA)) {
                crashReportData.put(ReportField.CUSTOM_DATA, createCustomInfoString());
            }
            if (this.crashReportFields.contains(ReportField.USER_EMAIL)) {
                crashReportData.put(ReportField.USER_EMAIL, this.prefs.getString(ACRA.PREF_USER_EMAIL_ADDRESS, "N/A"));
            }
            if (this.crashReportFields.contains(ReportField.DEVICE_FEATURES)) {
                crashReportData.put(ReportField.DEVICE_FEATURES, DeviceFeaturesCollector.getFeatures(this.context));
            }
            if (this.crashReportFields.contains(ReportField.ENVIRONMENT)) {
                crashReportData.put(ReportField.ENVIRONMENT, ReflectionCollector.collectStaticGettersResults(Environment.class));
            }
            if (this.crashReportFields.contains(ReportField.SETTINGS_SYSTEM)) {
                crashReportData.put(ReportField.SETTINGS_SYSTEM, SettingsCollector.collectSystemSettings(this.context));
            }
            if (this.crashReportFields.contains(ReportField.SETTINGS_SECURE)) {
                crashReportData.put(ReportField.SETTINGS_SECURE, SettingsCollector.collectSecureSettings(this.context));
            }
            if (this.crashReportFields.contains(ReportField.SHARED_PREFERENCES)) {
                crashReportData.put(ReportField.SHARED_PREFERENCES, SharedPreferencesCollector.collect(this.context));
            }
            PackageManagerWrapper pm = new PackageManagerWrapper(this.context);
            PackageInfo pi = pm.getPackageInfo();
            if (pi != null) {
                if (this.crashReportFields.contains(ReportField.APP_VERSION_CODE)) {
                    crashReportData.put(ReportField.APP_VERSION_CODE, Integer.toString(pi.versionCode));
                }
                if (this.crashReportFields.contains(ReportField.APP_VERSION_NAME)) {
                    crashReportData.put(ReportField.APP_VERSION_NAME, pi.versionName != null ? pi.versionName : "not set");
                }
            } else {
                crashReportData.put(ReportField.APP_VERSION_NAME, "Package info unavailable");
            }
            if (this.crashReportFields.contains(ReportField.DEVICE_ID) && this.prefs.getBoolean(ACRA.PREF_ENABLE_DEVICE_ID, true) && pm.hasPermission("android.permission.READ_PHONE_STATE")) {
                String deviceId = ReportUtils.getDeviceId(this.context);
                if (deviceId != null) {
                    crashReportData.put(ReportField.DEVICE_ID, deviceId);
                }
            }
            if (!(this.prefs.getBoolean(ACRA.PREF_ENABLE_SYSTEM_LOGS, true) && pm.hasPermission("android.permission.READ_LOGS")) && Compatibility.getAPILevel() < 16) {
                Log.i(ACRA.LOG_TAG, "READ_LOGS not allowed. ACRA will not include LogCat and DropBox data.");
            } else {
                Log.i(ACRA.LOG_TAG, "READ_LOGS granted! ACRA can include LogCat and DropBox data.");
                if (this.crashReportFields.contains(ReportField.LOGCAT)) {
                    crashReportData.put(ReportField.LOGCAT, LogCatCollector.collectLogCat(null));
                }
                if (this.crashReportFields.contains(ReportField.EVENTSLOG)) {
                    crashReportData.put(ReportField.EVENTSLOG, LogCatCollector.collectLogCat("events"));
                }
                if (this.crashReportFields.contains(ReportField.RADIOLOG)) {
                    crashReportData.put(ReportField.RADIOLOG, LogCatCollector.collectLogCat("radio"));
                }
                if (this.crashReportFields.contains(ReportField.DROPBOX)) {
                    crashReportData.put(ReportField.DROPBOX, DropBoxCollector.read(this.context, ACRA.getConfig().additionalDropBoxTags()));
                }
            }
            if (this.crashReportFields.contains(ReportField.APPLICATION_LOG)) {
                crashReportData.put(ReportField.APPLICATION_LOG, LogFileCollector.collectLogFile(this.context, ACRA.getConfig().applicationLogFile(), ACRA.getConfig().applicationLogFileLines()));
            }
            if (this.crashReportFields.contains(ReportField.MEDIA_CODEC_LIST)) {
                crashReportData.put(ReportField.MEDIA_CODEC_LIST, MediaCodecListCollector.collecMediaCodecList());
            }
            if (this.crashReportFields.contains(ReportField.THREAD_DETAILS)) {
                crashReportData.put(ReportField.THREAD_DETAILS, ThreadCollector.collect(brokenThread));
            }
        } catch (RuntimeException e) {
            Log.e(ACRA.LOG_TAG, "Error while retrieving crash data", e);
        } catch (FileNotFoundException e2) {
            Log.e(ACRA.LOG_TAG, "Error : application log file " + ACRA.getConfig().applicationLogFile() + " not found.", e2);
        } catch (IOException e3) {
            Log.e(ACRA.LOG_TAG, "Error while reading application log file " + ACRA.getConfig().applicationLogFile() + ".", e3);
        }
        return crashReportData;
    }

    private String createCustomInfoString() {
        StringBuilder customInfo = new StringBuilder();
        for (String currentKey : this.customParameters.keySet()) {
            String currentVal = (String) this.customParameters.get(currentKey);
            customInfo.append(currentKey);
            customInfo.append(" = ");
            customInfo.append(currentVal);
            customInfo.append("\n");
        }
        return customInfo.toString();
    }

    private String getStackTrace(Throwable th) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        for (Throwable cause = th; cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        String stacktraceAsString = result.toString();
        printWriter.close();
        return stacktraceAsString;
    }
}

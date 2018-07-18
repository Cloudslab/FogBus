package org.acra;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.os.Looper;
import android.os.Process;
import android.text.format.Time;
import android.util.Log;
import gnu.expr.Declaration;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.acra.annotation.ReportsCrashes;
import org.acra.collector.ConfigurationCollector;
import org.acra.collector.CrashReportData;
import org.acra.collector.CrashReportDataFactory;
import org.acra.sender.EmailIntentSender;
import org.acra.sender.GoogleFormSender;
import org.acra.sender.HttpPostSender;
import org.acra.sender.ReportSender;
import org.acra.util.PackageManagerWrapper;
import org.acra.util.ToastSender;

public class ErrorReporter implements UncaughtExceptionHandler {
    private static boolean toastWaitEnded = true;
    private Thread brokenThread;
    private final CrashReportDataFactory crashReportDataFactory;
    private boolean enabled = false;
    private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
    private final Context mContext;
    private final UncaughtExceptionHandler mDfltExceptionHandler;
    private final List<ReportSender> mReportSenders = new ArrayList();
    private final SharedPreferences prefs;
    private Throwable unhandledThrowable;

    class C03891 extends Thread {
        C03891() {
        }

        public void run() {
            Looper.prepare();
            ToastSender.sendToast(ErrorReporter.this.mContext, ACRA.getConfig().resToastText(), 1);
            Looper.loop();
        }
    }

    class C03902 extends Thread {
        C03902() {
        }

        public void run() {
            Time beforeWait = new Time();
            Time currentTime = new Time();
            beforeWait.setToNow();
            long beforeWaitInMillis = beforeWait.toMillis(false);
            for (long elapsedTimeInMillis = 0; elapsedTimeInMillis < 3000; elapsedTimeInMillis = currentTime.toMillis(false) - beforeWaitInMillis) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    Log.d(ACRA.LOG_TAG, "Interrupted while waiting for Toast to end.", e1);
                }
                currentTime.setToNow();
            }
            ErrorReporter.toastWaitEnded = true;
        }
    }

    ErrorReporter(Context context, SharedPreferences prefs, boolean enabled) {
        this.mContext = context;
        this.prefs = prefs;
        this.enabled = enabled;
        String initialConfiguration = ConfigurationCollector.collectConfiguration(this.mContext);
        Time appStartDate = new Time();
        appStartDate.setToNow();
        this.crashReportDataFactory = new CrashReportDataFactory(this.mContext, prefs, appStartDate, initialConfiguration);
        this.mDfltExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        checkReportsOnApplicationStart();
    }

    public static ErrorReporter getInstance() {
        return ACRA.getErrorReporter();
    }

    @Deprecated
    public void addCustomData(String key, String value) {
        this.crashReportDataFactory.putCustomData(key, value);
    }

    public String putCustomData(String key, String value) {
        return this.crashReportDataFactory.putCustomData(key, value);
    }

    public String removeCustomData(String key) {
        return this.crashReportDataFactory.removeCustomData(key);
    }

    public String getCustomData(String key) {
        return this.crashReportDataFactory.getCustomData(key);
    }

    public void addReportSender(ReportSender sender) {
        this.mReportSenders.add(sender);
    }

    public void removeReportSender(ReportSender sender) {
        this.mReportSenders.remove(sender);
    }

    public void removeReportSenders(Class<?> senderClass) {
        if (ReportSender.class.isAssignableFrom(senderClass)) {
            for (ReportSender sender : this.mReportSenders) {
                if (senderClass.isInstance(sender)) {
                    this.mReportSenders.remove(sender);
                }
            }
        }
    }

    public void removeAllReportSenders() {
        this.mReportSenders.clear();
    }

    public void setReportSender(ReportSender sender) {
        removeAllReportSenders();
        addReportSender(sender);
    }

    public void uncaughtException(Thread t, Throwable e) {
        try {
            if (this.enabled) {
                this.brokenThread = t;
                this.unhandledThrowable = e;
                Log.e(ACRA.LOG_TAG, "ACRA caught a " + e.getClass().getSimpleName() + " exception for " + this.mContext.getPackageName() + ". Building report.");
                handleException(e, ACRA.getConfig().mode(), false, true);
            } else if (this.mDfltExceptionHandler != null) {
                Log.e(ACRA.LOG_TAG, "ACRA is disabled for " + this.mContext.getPackageName() + " - forwarding uncaught Exception on to default ExceptionHandler");
                this.mDfltExceptionHandler.uncaughtException(t, e);
            } else {
                Log.e(ACRA.LOG_TAG, "ACRA is disabled for " + this.mContext.getPackageName() + " - no default ExceptionHandler");
            }
        } catch (Throwable th) {
            if (this.mDfltExceptionHandler != null) {
                this.mDfltExceptionHandler.uncaughtException(t, e);
            }
        }
    }

    private void endApplication() {
        if (ACRA.getConfig().mode() == ReportingInteractionMode.SILENT || (ACRA.getConfig().mode() == ReportingInteractionMode.TOAST && ACRA.getConfig().forceCloseDialogAfterToast())) {
            this.mDfltExceptionHandler.uncaughtException(this.brokenThread, this.unhandledThrowable);
            return;
        }
        Log.e(ACRA.LOG_TAG, this.mContext.getPackageName() + " fatal error : " + this.unhandledThrowable.getMessage(), this.unhandledThrowable);
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

    public void handleSilentException(Throwable e) {
        if (this.enabled) {
            handleException(e, ReportingInteractionMode.SILENT, true, false);
            Log.d(ACRA.LOG_TAG, "ACRA sent Silent report.");
            return;
        }
        Log.d(ACRA.LOG_TAG, "ACRA is disabled. Silent report not sent.");
    }

    public void setEnabled(boolean enabled) {
        Log.i(ACRA.LOG_TAG, "ACRA is " + (enabled ? "enabled" : "disabled") + " for " + this.mContext.getPackageName());
        this.enabled = enabled;
    }

    SendWorker startSendingReports(boolean onlySendSilentReports, boolean approveReportsFirst) {
        SendWorker worker = new SendWorker(this.mContext, this.mReportSenders, onlySendSilentReports, approveReportsFirst);
        worker.start();
        return worker;
    }

    void deletePendingReports() {
        deletePendingReports(true, true, 0);
    }

    public void checkReportsOnApplicationStart() {
        boolean newVersion;
        long lastVersionNr = (long) this.prefs.getInt(ACRA.PREF_LAST_VERSION_NR, 0);
        PackageInfo packageInfo = new PackageManagerWrapper(this.mContext).getPackageInfo();
        if (packageInfo == null || ((long) packageInfo.versionCode) <= lastVersionNr) {
            newVersion = false;
        } else {
            newVersion = true;
        }
        if (newVersion) {
            if (ACRA.getConfig().deleteOldUnsentReportsOnApplicationStart()) {
                deletePendingReports();
            }
            Editor prefsEditor = this.prefs.edit();
            prefsEditor.putInt(ACRA.PREF_LAST_VERSION_NR, packageInfo.versionCode);
            prefsEditor.commit();
        }
        if ((ACRA.getConfig().mode() == ReportingInteractionMode.NOTIFICATION || ACRA.getConfig().mode() == ReportingInteractionMode.DIALOG) && ACRA.getConfig().deleteUnapprovedReportsOnApplicationStart()) {
            deletePendingNonApprovedReports(true);
        }
        CrashReportFinder reportFinder = new CrashReportFinder(this.mContext);
        String[] filesList = reportFinder.getCrashReportFiles();
        if (filesList != null && filesList.length > 0) {
            ReportingInteractionMode reportingInteractionMode = ACRA.getConfig().mode();
            filesList = reportFinder.getCrashReportFiles();
            boolean onlySilentOrApprovedReports = containsOnlySilentOrApprovedReports(filesList);
            if (reportingInteractionMode == ReportingInteractionMode.SILENT || reportingInteractionMode == ReportingInteractionMode.TOAST || (onlySilentOrApprovedReports && (reportingInteractionMode == ReportingInteractionMode.NOTIFICATION || reportingInteractionMode == ReportingInteractionMode.DIALOG))) {
                if (reportingInteractionMode == ReportingInteractionMode.TOAST && !onlySilentOrApprovedReports) {
                    ToastSender.sendToast(this.mContext, ACRA.getConfig().resToastText(), 1);
                }
                Log.v(ACRA.LOG_TAG, "About to start ReportSenderWorker from #checkReportOnApplicationStart");
                startSendingReports(false, false);
            } else if (ACRA.getConfig().mode() == ReportingInteractionMode.NOTIFICATION) {
                notifySendReport(getLatestNonSilentReport(filesList));
            } else if (ACRA.getConfig().mode() == ReportingInteractionMode.DIALOG) {
                notifyDialog(getLatestNonSilentReport(filesList));
            }
        }
    }

    void deletePendingNonApprovedReports(boolean keepOne) {
        int nbReportsToKeep;
        if (keepOne) {
            nbReportsToKeep = 1;
        } else {
            nbReportsToKeep = 0;
        }
        deletePendingReports(false, true, nbReportsToKeep);
    }

    public void handleException(Throwable e, boolean endApplication) {
        handleException(e, ACRA.getConfig().mode(), false, endApplication);
    }

    public void handleException(Throwable e) {
        handleException(e, ACRA.getConfig().mode(), false, false);
    }

    private void handleException(Throwable e, ReportingInteractionMode reportingInteractionMode, boolean forceSilentReport, boolean endApplication) {
        boolean showDirectDialog = true;
        if (this.enabled) {
            boolean shouldDisplayToast;
            boolean sendOnlySilentReports = false;
            if (reportingInteractionMode == null) {
                reportingInteractionMode = ACRA.getConfig().mode();
            } else if (reportingInteractionMode == ReportingInteractionMode.SILENT && ACRA.getConfig().mode() != ReportingInteractionMode.SILENT) {
                sendOnlySilentReports = true;
            }
            if (e == null) {
                e = new Exception("Report requested by developer");
            }
            if (reportingInteractionMode == ReportingInteractionMode.TOAST || (ACRA.getConfig().resToastText() != 0 && (reportingInteractionMode == ReportingInteractionMode.NOTIFICATION || reportingInteractionMode == ReportingInteractionMode.DIALOG))) {
                shouldDisplayToast = true;
            } else {
                shouldDisplayToast = false;
            }
            if (shouldDisplayToast) {
                new C03891().start();
            }
            CrashReportData crashReportData = this.crashReportDataFactory.createCrashData(e, forceSilentReport, this.brokenThread);
            final String reportFileName = getReportFileName(crashReportData);
            saveCrashReportFile(reportFileName, crashReportData);
            SendWorker sender = null;
            if (reportingInteractionMode == ReportingInteractionMode.SILENT || reportingInteractionMode == ReportingInteractionMode.TOAST || this.prefs.getBoolean(ACRA.PREF_ALWAYS_ACCEPT, false)) {
                Log.d(ACRA.LOG_TAG, "About to start ReportSenderWorker from #handleException");
                sender = startSendingReports(sendOnlySilentReports, true);
            } else if (reportingInteractionMode == ReportingInteractionMode.NOTIFICATION) {
                Log.d(ACRA.LOG_TAG, "About to send status bar notification from #handleException");
                notifySendReport(reportFileName);
            }
            if (shouldDisplayToast) {
                toastWaitEnded = false;
                new C03902().start();
            }
            final SendWorker worker = sender;
            if (reportingInteractionMode != ReportingInteractionMode.DIALOG || this.prefs.getBoolean(ACRA.PREF_ALWAYS_ACCEPT, false)) {
                showDirectDialog = false;
            }
            final boolean z = endApplication;
            new Thread() {
                public void run() {
                    Log.d(ACRA.LOG_TAG, "Waiting for Toast + worker...");
                    while (true) {
                        if (!ErrorReporter.toastWaitEnded || (worker != null && worker.isAlive())) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e1) {
                                Log.e(ACRA.LOG_TAG, "Error : ", e1);
                            }
                        }
                    }
                    if (showDirectDialog) {
                        Log.d(ACRA.LOG_TAG, "About to create DIALOG from #handleException");
                        ErrorReporter.this.notifyDialog(reportFileName);
                    }
                    Log.d(ACRA.LOG_TAG, "Wait for Toast + worker ended. Kill Application ? " + z);
                    if (z) {
                        ErrorReporter.this.endApplication();
                    }
                }
            }.start();
        }
    }

    void notifyDialog(String reportFileName) {
        Log.d(ACRA.LOG_TAG, "Creating Dialog for " + reportFileName);
        Intent dialogIntent = new Intent(this.mContext, CrashReportDialog.class);
        dialogIntent.putExtra("REPORT_FILE_NAME", reportFileName);
        dialogIntent.setFlags(Declaration.IS_DYNAMIC);
        this.mContext.startActivity(dialogIntent);
    }

    private void notifySendReport(String reportFileName) {
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        ReportsCrashes conf = ACRA.getConfig();
        Notification notification = new Notification(conf.resNotifIcon(), this.mContext.getText(conf.resNotifTickerText()), System.currentTimeMillis());
        CharSequence contentTitle = this.mContext.getText(conf.resNotifTitle());
        CharSequence contentText = this.mContext.getText(conf.resNotifText());
        Intent notificationIntent = new Intent(this.mContext, CrashReportDialog.class);
        Log.d(ACRA.LOG_TAG, "Creating Notification for " + reportFileName);
        notificationIntent.putExtra("REPORT_FILE_NAME", reportFileName);
        notification.setLatestEventInfo(this.mContext, contentTitle, contentText, PendingIntent.getActivity(this.mContext, 0, notificationIntent, Declaration.PACKAGE_ACCESS));
        notificationManager.cancelAll();
        notificationManager.notify(666, notification);
    }

    private String getReportFileName(CrashReportData crashData) {
        Time now = new Time();
        now.setToNow();
        return "" + now.toMillis(false) + (crashData.getProperty(ReportField.IS_SILENT) != null ? ACRAConstants.SILENT_SUFFIX : "") + ACRAConstants.REPORTFILE_EXTENSION;
    }

    private void saveCrashReportFile(String fileName, CrashReportData crashData) {
        try {
            Log.d(ACRA.LOG_TAG, "Writing crash report file " + fileName + ".");
            new CrashReportPersister(this.mContext).store(crashData, fileName);
        } catch (Exception e) {
            Log.e(ACRA.LOG_TAG, "An error occurred while writing the report file...", e);
        }
    }

    private String getLatestNonSilentReport(String[] filesList) {
        if (filesList == null || filesList.length <= 0) {
            return null;
        }
        for (int i = filesList.length - 1; i >= 0; i--) {
            if (!this.fileNameParser.isSilent(filesList[i])) {
                return filesList[i];
            }
        }
        return filesList[filesList.length - 1];
    }

    private void deletePendingReports(boolean deleteApprovedReports, boolean deleteNonApprovedReports, int nbOfLatestToKeep) {
        String[] filesList = new CrashReportFinder(this.mContext).getCrashReportFiles();
        Arrays.sort(filesList);
        if (filesList != null) {
            for (int iFile = 0; iFile < filesList.length - nbOfLatestToKeep; iFile++) {
                String fileName = filesList[iFile];
                boolean isReportApproved = this.fileNameParser.isApproved(fileName);
                if ((isReportApproved && deleteApprovedReports) || (!isReportApproved && deleteNonApprovedReports)) {
                    File fileToDelete = new File(this.mContext.getFilesDir(), fileName);
                    if (!fileToDelete.delete()) {
                        Log.e(ACRA.LOG_TAG, "Could not delete report : " + fileToDelete);
                    }
                }
            }
        }
    }

    private boolean containsOnlySilentOrApprovedReports(String[] reportFileNames) {
        for (String reportFileName : reportFileNames) {
            if (!this.fileNameParser.isApproved(reportFileName)) {
                return false;
            }
        }
        return true;
    }

    public void setDefaultReportSenders() {
        ReportsCrashes conf = ACRA.getConfig();
        Application mApplication = ACRA.getApplication();
        removeAllReportSenders();
        if (!"".equals(conf.mailTo())) {
            Log.w(ACRA.LOG_TAG, mApplication.getPackageName() + " reports will be sent by email (if accepted by user).");
            setReportSender(new EmailIntentSender(mApplication));
        } else if (!new PackageManagerWrapper(mApplication).hasPermission("android.permission.INTERNET")) {
            Log.e(ACRA.LOG_TAG, mApplication.getPackageName() + " should be granted permission " + "android.permission.INTERNET" + " if you want your crash reports to be sent. If you don't want to add this permission to your application you can also enable sending reports by email. If this is your will then provide your email address in @ReportsCrashes(mailTo=\"your.account@domain.com\"");
        } else if (conf.formUri() != null && !"".equals(conf.formUri())) {
            setReportSender(new HttpPostSender(null));
        } else if (conf.formKey() != null && !"".equals(conf.formKey().trim())) {
            addReportSender(new GoogleFormSender());
        }
    }
}

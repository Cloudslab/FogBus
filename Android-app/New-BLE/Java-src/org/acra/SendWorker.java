package org.acra;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

final class SendWorker extends Thread {
    private final boolean approvePendingReports;
    private final Context context;
    private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
    private final List<ReportSender> reportSenders;
    private final boolean sendOnlySilentReports;

    public SendWorker(Context context, List<ReportSender> reportSenders, boolean sendOnlySilentReports, boolean approvePendingReports) {
        this.context = context;
        this.reportSenders = reportSenders;
        this.sendOnlySilentReports = sendOnlySilentReports;
        this.approvePendingReports = approvePendingReports;
    }

    public void run() {
        if (this.approvePendingReports) {
            approvePendingReports();
        }
        checkAndSendReports(this.context, this.sendOnlySilentReports);
    }

    private void approvePendingReports() {
        Log.d(ACRA.LOG_TAG, "Mark all pending reports as approved.");
        for (String reportFileName : new CrashReportFinder(this.context).getCrashReportFiles()) {
            if (!this.fileNameParser.isApproved(reportFileName)) {
                File reportFile = new File(this.context.getFilesDir(), reportFileName);
                File newFile = new File(this.context.getFilesDir(), reportFileName.replace(ACRAConstants.REPORTFILE_EXTENSION, "-approved.stacktrace"));
                if (!reportFile.renameTo(newFile)) {
                    Log.e(ACRA.LOG_TAG, "Could not rename approved report from " + reportFile + " to " + newFile);
                }
            }
        }
    }

    private void checkAndSendReports(Context context, boolean sendOnlySilentReports) {
        Log.d(ACRA.LOG_TAG, "#checkAndSendReports - start");
        String[] reportFiles = new CrashReportFinder(context).getCrashReportFiles();
        Arrays.sort(reportFiles);
        int reportsSentCount = 0;
        for (String curFileName : reportFiles) {
            if (!sendOnlySilentReports || this.fileNameParser.isSilent(curFileName)) {
                if (reportsSentCount >= 5) {
                    break;
                }
                Log.i(ACRA.LOG_TAG, "Sending file " + curFileName);
                try {
                    sendCrashReport(new CrashReportPersister(context).load(curFileName));
                    deleteFile(context, curFileName);
                    reportsSentCount++;
                } catch (RuntimeException e) {
                    Log.e(ACRA.LOG_TAG, "Failed to send crash reports for " + curFileName, e);
                    deleteFile(context, curFileName);
                } catch (IOException e2) {
                    Log.e(ACRA.LOG_TAG, "Failed to load crash report for " + curFileName, e2);
                    deleteFile(context, curFileName);
                } catch (ReportSenderException e3) {
                    Log.e(ACRA.LOG_TAG, "Failed to send crash report for " + curFileName, e3);
                }
            }
        }
        Log.d(ACRA.LOG_TAG, "#checkAndSendReports - finish");
    }

    private void sendCrashReport(CrashReportData errorContent) throws ReportSenderException {
        if (!ACRA.isDebuggable() || ACRA.getConfig().sendReportsInDevMode()) {
            boolean sentAtLeastOnce = false;
            for (ReportSender sender : this.reportSenders) {
                try {
                    sender.send(errorContent);
                    sentAtLeastOnce = true;
                } catch (ReportSenderException e) {
                    if (sentAtLeastOnce) {
                        Log.w(ACRA.LOG_TAG, "ReportSender of class " + sender.getClass().getName() + " failed but other senders completed their task. ACRA will not send this report again.");
                    } else {
                        throw e;
                    }
                }
            }
        }
    }

    private void deleteFile(Context context, String fileName) {
        if (!context.deleteFile(fileName)) {
            Log.w(ACRA.LOG_TAG, "Could not delete error report : " + fileName);
        }
    }
}

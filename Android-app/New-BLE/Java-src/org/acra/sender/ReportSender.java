package org.acra.sender;

import org.acra.collector.CrashReportData;

public interface ReportSender {
    void send(CrashReportData crashReportData) throws ReportSenderException;
}

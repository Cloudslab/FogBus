package org.acra.sender;

public class ReportSenderException extends Exception {
    public ReportSenderException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}

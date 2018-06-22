package org.acra.sender;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.util.HttpRequest;

public class HttpPostSender implements ReportSender {
    private final Uri mFormUri;
    private final Map<ReportField, String> mMapping;

    public HttpPostSender(Map<ReportField, String> mapping) {
        this.mFormUri = null;
        this.mMapping = mapping;
    }

    public HttpPostSender(String formUri, Map<ReportField, String> mapping) {
        this.mFormUri = Uri.parse(formUri);
        this.mMapping = mapping;
    }

    public void send(CrashReportData report) throws ReportSenderException {
        String password = null;
        try {
            URL reportUrl;
            Map<String, String> finalReport = remap(report);
            if (this.mFormUri == null) {
                reportUrl = new URL(ACRA.getConfig().formUri());
            } else {
                reportUrl = new URL(this.mFormUri.toString());
            }
            Log.d(ACRA.LOG_TAG, "Connect to " + reportUrl.toString());
            String login = isNull(ACRA.getConfig().formUriBasicAuthLogin()) ? null : ACRA.getConfig().formUriBasicAuthLogin();
            if (!isNull(ACRA.getConfig().formUriBasicAuthPassword())) {
                password = ACRA.getConfig().formUriBasicAuthPassword();
            }
            HttpRequest request = new HttpRequest();
            request.setConnectionTimeOut(ACRA.getConfig().connectionTimeout());
            request.setSocketTimeOut(ACRA.getConfig().socketTimeout());
            request.setMaxNrRetries(ACRA.getConfig().maxNumberOfRequestRetries());
            request.setLogin(login);
            request.setPassword(password);
            request.sendPost(reportUrl, finalReport);
        } catch (IOException e) {
            throw new ReportSenderException("Error while sending report to Http Post Form.", e);
        }
    }

    private static boolean isNull(String aString) {
        return aString == null || ACRAConstants.NULL_VALUE.equals(aString);
    }

    private Map<String, String> remap(Map<ReportField, String> report) {
        ReportField[] fields = ACRA.getConfig().customReportContent();
        if (fields.length == 0) {
            fields = ACRA.DEFAULT_REPORT_FIELDS;
        }
        Map<String, String> finalReport = new HashMap(report.size());
        for (ReportField field : fields) {
            if (this.mMapping == null || this.mMapping.get(field) == null) {
                finalReport.put(field.toString(), report.get(field));
            } else {
                finalReport.put(this.mMapping.get(field), report.get(field));
            }
        }
        return finalReport;
    }
}

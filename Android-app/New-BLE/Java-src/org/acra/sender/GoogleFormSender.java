package org.acra.sender;

import android.net.Uri;
import android.util.Log;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.util.HttpRequest;

public class GoogleFormSender implements ReportSender {
    private final Uri mFormUri;

    public GoogleFormSender() {
        this.mFormUri = null;
    }

    public GoogleFormSender(String formKey) {
        this.mFormUri = Uri.parse(String.format(ACRA.getConfig().googleFormUrlFormat(), new Object[]{formKey}));
    }

    public void send(CrashReportData report) throws ReportSenderException {
        Uri formUri;
        if (this.mFormUri == null) {
            formUri = Uri.parse(String.format(ACRA.getConfig().googleFormUrlFormat(), new Object[]{ACRA.getConfig().formKey()}));
        } else {
            formUri = this.mFormUri;
        }
        Map<String, String> formParams = remap(report);
        formParams.put("pageNumber", "0");
        formParams.put("backupCache", "");
        formParams.put("submit", "Envoyer");
        try {
            URL reportUrl = new URL(formUri.toString());
            Log.d(ACRA.LOG_TAG, "Sending report " + ((String) report.get(ReportField.REPORT_ID)));
            Log.d(ACRA.LOG_TAG, "Connect to " + reportUrl);
            HttpRequest request = new HttpRequest();
            request.setConnectionTimeOut(ACRA.getConfig().connectionTimeout());
            request.setSocketTimeOut(ACRA.getConfig().socketTimeout());
            request.setMaxNrRetries(ACRA.getConfig().maxNumberOfRequestRetries());
            request.sendPost(reportUrl, formParams);
        } catch (IOException e) {
            throw new ReportSenderException("Error while sending report to Google Form.", e);
        }
    }

    private Map<String, String> remap(Map<ReportField, String> report) {
        ReportField[] fields = ACRA.getConfig().customReportContent();
        if (fields.length == 0) {
            fields = ACRA.DEFAULT_REPORT_FIELDS;
        }
        int inputId = 0;
        Map<String, String> result = new HashMap();
        for (ReportField originalKey : fields) {
            switch (originalKey) {
                case APP_VERSION_NAME:
                    result.put("entry." + inputId + ".single", "'" + ((String) report.get(originalKey)));
                    break;
                case ANDROID_VERSION:
                    result.put("entry." + inputId + ".single", "'" + ((String) report.get(originalKey)));
                    break;
                default:
                    result.put("entry." + inputId + ".single", report.get(originalKey));
                    break;
            }
            inputId++;
        }
        return result;
    }
}

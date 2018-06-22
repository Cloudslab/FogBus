package org.acra.sender;

import android.content.Context;
import android.content.Intent;
import com.google.appinventor.components.runtime.util.NanoHTTPD;
import gnu.expr.Declaration;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CrashReportData;

public class EmailIntentSender implements ReportSender {
    private final Context mContext;

    public EmailIntentSender(Context ctx) {
        this.mContext = ctx;
    }

    public void send(CrashReportData errorContent) throws ReportSenderException {
        String subject = this.mContext.getPackageName() + " Crash Report";
        String body = buildBody(errorContent);
        Intent emailIntent = new Intent("android.intent.action.SEND");
        emailIntent.addFlags(Declaration.IS_DYNAMIC);
        emailIntent.setType(NanoHTTPD.MIME_PLAINTEXT);
        emailIntent.putExtra("android.intent.extra.SUBJECT", subject);
        emailIntent.putExtra("android.intent.extra.TEXT", body);
        emailIntent.putExtra("android.intent.extra.EMAIL", new String[]{ACRA.getConfig().mailTo()});
        this.mContext.startActivity(emailIntent);
    }

    private String buildBody(CrashReportData errorContent) {
        ReportField[] fields = ACRA.getConfig().customReportContent();
        if (fields.length == 0) {
            fields = ACRA.DEFAULT_MAIL_REPORT_FIELDS;
        }
        StringBuilder builder = new StringBuilder();
        for (ReportField field : fields) {
            builder.append(field.toString()).append("=");
            builder.append((String) errorContent.get(field));
            builder.append('\n');
        }
        return builder.toString();
    }
}

package org.acra.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;

@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReportsCrashes {
    String[] additionalDropBoxTags() default {};

    String[] additionalSharedPreferences() default {};

    String applicationLogFile() default "";

    int applicationLogFileLines() default 100;

    int connectionTimeout() default 3000;

    ReportField[] customReportContent() default {};

    boolean deleteOldUnsentReportsOnApplicationStart() default true;

    boolean deleteUnapprovedReportsOnApplicationStart() default true;

    boolean disableSSLCertValidation() default false;

    int dropboxCollectionMinutes() default 5;

    String[] excludeMatchingSharedPreferencesKeys() default {};

    boolean forceCloseDialogAfterToast() default false;

    String formKey();

    String formUri() default "";

    String formUriBasicAuthLogin() default "ACRA-NULL-STRING";

    String formUriBasicAuthPassword() default "ACRA-NULL-STRING";

    String googleFormUrlFormat() default "https://docs.google.com/spreadsheet/formResponse?formkey=%s&ifq";

    boolean includeDropBoxSystemTags() default false;

    String[] logcatArguments() default {"-t", "100", "-v", "time"};

    boolean logcatFilterByPid() default false;

    String mailTo() default "";

    int maxNumberOfRequestRetries() default 3;

    ReportingInteractionMode mode() default ReportingInteractionMode.SILENT;

    int resDialogCommentPrompt() default 0;

    int resDialogEmailPrompt() default 0;

    int resDialogIcon() default 17301543;

    int resDialogOkToast() default 0;

    int resDialogText() default 0;

    int resDialogTitle() default 0;

    int resNotifIcon() default 17301624;

    int resNotifText() default 0;

    int resNotifTickerText() default 0;

    int resNotifTitle() default 0;

    int resToastText() default 0;

    boolean sendReportsInDevMode() default true;

    int sharedPreferencesMode() default 0;

    String sharedPreferencesName() default "";

    int socketTimeout() default 5000;
}

package org.acra;

import java.lang.annotation.Annotation;
import org.acra.annotation.ReportsCrashes;

public class ACRAConfiguration implements ReportsCrashes {
    private String[] mAdditionalDropboxTags = null;
    private String[] mAdditionalSharedPreferences = null;
    private String mApplicationLogFile = null;
    private Integer mApplicationLogFileLines = null;
    private Integer mConnectionTimeout = null;
    private ReportField[] mCustomReportContent = null;
    private Boolean mDeleteOldUnsentReportsOnApplicationStart = null;
    private Boolean mDeleteUnapprovedReportsOnApplicationStart = null;
    private Boolean mDisableSSLCertValidation = null;
    private Integer mDropboxCollectionMinutes = null;
    private String[] mExcludeMatchingSharedPreferencesKeys = null;
    private Boolean mForceCloseDialogAfterToast = null;
    private String mFormKey = null;
    private String mFormUri = null;
    private String mFormUriBasicAuthLogin = null;
    private String mFormUriBasicAuthPassword = null;
    private String mGoogleFormUrlFormat = null;
    private Boolean mIncludeDropboxSystemTags = null;
    private String[] mLogcatArguments = null;
    private Boolean mLogcatFilterByPid = null;
    private String mMailTo = null;
    private Integer mMaxNumberOfRequestRetries = null;
    private ReportingInteractionMode mMode = null;
    private ReportsCrashes mReportsCrashes = null;
    private Integer mResDialogCommentPrompt = null;
    private Integer mResDialogEmailPrompt = null;
    private Integer mResDialogIcon = null;
    private Integer mResDialogOkToast = null;
    private Integer mResDialogText = null;
    private Integer mResDialogTitle = null;
    private Integer mResNotifIcon = null;
    private Integer mResNotifText = null;
    private Integer mResNotifTickerText = null;
    private Integer mResNotifTitle = null;
    private Integer mResToastText = null;
    private Boolean mSendReportsInDevMode = null;
    private Integer mSharedPreferenceMode = null;
    private String mSharedPreferenceName = null;
    private Integer mSocketTimeout = null;

    public void setAdditionalDropboxTags(String[] additionalDropboxTags) {
        this.mAdditionalDropboxTags = additionalDropboxTags;
    }

    public void setAdditionalSharedPreferences(String[] additionalSharedPreferences) {
        this.mAdditionalSharedPreferences = additionalSharedPreferences;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.mConnectionTimeout = connectionTimeout;
    }

    public void setCustomReportContent(ReportField[] customReportContent) {
        this.mCustomReportContent = customReportContent;
    }

    public void setDeleteUnapprovedReportsOnApplicationStart(Boolean deleteUnapprovedReportsOnApplicationStart) {
        this.mDeleteUnapprovedReportsOnApplicationStart = deleteUnapprovedReportsOnApplicationStart;
    }

    public void setDeleteOldUnsentReportsOnApplicationStart(Boolean deleteOldUnsetReportsOnApplicationStart) {
        this.mDeleteOldUnsentReportsOnApplicationStart = deleteOldUnsetReportsOnApplicationStart;
    }

    public void setDropboxCollectionMinutes(Integer dropboxCollectionMinutes) {
        this.mDropboxCollectionMinutes = dropboxCollectionMinutes;
    }

    public void setForceCloseDialogAfterToast(Boolean forceCloseDialogAfterToast) {
        this.mForceCloseDialogAfterToast = forceCloseDialogAfterToast;
    }

    public void setFormKey(String formKey) {
        this.mFormKey = formKey;
    }

    public void setFormUri(String formUri) {
        this.mFormUri = formUri;
    }

    public void setFormUriBasicAuthLogin(String formUriBasicAuthLogin) {
        this.mFormUriBasicAuthLogin = formUriBasicAuthLogin;
    }

    public void setFormUriBasicAuthPassword(String formUriBasicAuthPassword) {
        this.mFormUriBasicAuthPassword = formUriBasicAuthPassword;
    }

    public void setIncludeDropboxSystemTags(Boolean includeDropboxSystemTags) {
        this.mIncludeDropboxSystemTags = includeDropboxSystemTags;
    }

    public void setLogcatArguments(String[] logcatArguments) {
        this.mLogcatArguments = logcatArguments;
    }

    public void setMailTo(String mailTo) {
        this.mMailTo = mailTo;
    }

    public void setMaxNumberOfRequestRetries(Integer maxNumberOfRequestRetries) {
        this.mMaxNumberOfRequestRetries = maxNumberOfRequestRetries;
    }

    public void setMode(ReportingInteractionMode mode) throws ACRAConfigurationException {
        this.mMode = mode;
        ACRA.checkCrashResources();
    }

    public void setResDialogCommentPrompt(int resId) {
        this.mResDialogCommentPrompt = Integer.valueOf(resId);
    }

    public void setResDialogEmailPrompt(int resId) {
        this.mResDialogEmailPrompt = Integer.valueOf(resId);
    }

    public void setResDialogIcon(int resId) {
        this.mResDialogIcon = Integer.valueOf(resId);
    }

    public void setResDialogOkToast(int resId) {
        this.mResDialogOkToast = Integer.valueOf(resId);
    }

    public void setResDialogText(int resId) {
        this.mResDialogText = Integer.valueOf(resId);
    }

    public void setResDialogTitle(int resId) {
        this.mResDialogTitle = Integer.valueOf(resId);
    }

    public void setResNotifIcon(int resId) {
        this.mResNotifIcon = Integer.valueOf(resId);
    }

    public void setResNotifText(int resId) {
        this.mResNotifText = Integer.valueOf(resId);
    }

    public void setResNotifTickerText(int resId) {
        this.mResNotifTickerText = Integer.valueOf(resId);
    }

    public void setResNotifTitle(int resId) {
        this.mResNotifTitle = Integer.valueOf(resId);
    }

    public void setResToastText(int resId) {
        this.mResToastText = Integer.valueOf(resId);
    }

    public void setSharedPreferenceMode(Integer sharedPreferenceMode) {
        this.mSharedPreferenceMode = sharedPreferenceMode;
    }

    public void setSharedPreferenceName(String sharedPreferenceName) {
        this.mSharedPreferenceName = sharedPreferenceName;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.mSocketTimeout = socketTimeout;
    }

    public void setLogcatFilterByPid(Boolean filterByPid) {
        this.mLogcatFilterByPid = filterByPid;
    }

    public void setSendReportsInDevMode(Boolean sendReportsInDevMode) {
        this.mSendReportsInDevMode = sendReportsInDevMode;
    }

    public void setExcludeMatchingSharedPreferencesKeys(String[] excludeMatchingSharedPreferencesKeys) {
        this.mExcludeMatchingSharedPreferencesKeys = excludeMatchingSharedPreferencesKeys;
    }

    public void setApplicationLogFile(String applicationLogFile) {
        this.mApplicationLogFile = applicationLogFile;
    }

    public void setApplicationLogFileLines(int applicationLogFileLines) {
        this.mApplicationLogFileLines = Integer.valueOf(applicationLogFileLines);
    }

    public void setDisableSSLCertValidation(boolean disableSSLCertValidation) {
        this.mDisableSSLCertValidation = Boolean.valueOf(disableSSLCertValidation);
    }

    public ACRAConfiguration(ReportsCrashes defaults) {
        this.mReportsCrashes = defaults;
    }

    public String[] additionalDropBoxTags() {
        if (this.mAdditionalDropboxTags != null) {
            return this.mAdditionalDropboxTags;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.additionalDropBoxTags();
        }
        return new String[0];
    }

    public String[] additionalSharedPreferences() {
        if (this.mAdditionalSharedPreferences != null) {
            return this.mAdditionalSharedPreferences;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.additionalSharedPreferences();
        }
        return new String[0];
    }

    public Class<? extends Annotation> annotationType() {
        return this.mReportsCrashes.annotationType();
    }

    public int connectionTimeout() {
        if (this.mConnectionTimeout != null) {
            return this.mConnectionTimeout.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.connectionTimeout();
        }
        return ACRAConstants.DEFAULT_CONNECTION_TIMEOUT;
    }

    public ReportField[] customReportContent() {
        if (this.mCustomReportContent != null) {
            return this.mCustomReportContent;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.customReportContent();
        }
        return new ReportField[0];
    }

    public boolean deleteUnapprovedReportsOnApplicationStart() {
        if (this.mDeleteUnapprovedReportsOnApplicationStart != null) {
            return this.mDeleteUnapprovedReportsOnApplicationStart.booleanValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.deleteUnapprovedReportsOnApplicationStart();
        }
        return true;
    }

    public boolean deleteOldUnsentReportsOnApplicationStart() {
        if (this.mDeleteOldUnsentReportsOnApplicationStart != null) {
            return this.mDeleteOldUnsentReportsOnApplicationStart.booleanValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.deleteOldUnsentReportsOnApplicationStart();
        }
        return true;
    }

    public int dropboxCollectionMinutes() {
        if (this.mDropboxCollectionMinutes != null) {
            return this.mDropboxCollectionMinutes.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.dropboxCollectionMinutes();
        }
        return 5;
    }

    public boolean forceCloseDialogAfterToast() {
        if (this.mForceCloseDialogAfterToast != null) {
            return this.mForceCloseDialogAfterToast.booleanValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.forceCloseDialogAfterToast();
        }
        return false;
    }

    public String formKey() {
        if (this.mFormKey != null) {
            return this.mFormKey;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.formKey();
        }
        return "";
    }

    public String formUri() {
        if (this.mFormUri != null) {
            return this.mFormUri;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.formUri();
        }
        return "";
    }

    public String formUriBasicAuthLogin() {
        if (this.mFormUriBasicAuthLogin != null) {
            return this.mFormUriBasicAuthLogin;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.formUriBasicAuthLogin();
        }
        return ACRAConstants.NULL_VALUE;
    }

    public String formUriBasicAuthPassword() {
        if (this.mFormUriBasicAuthPassword != null) {
            return this.mFormUriBasicAuthPassword;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.formUriBasicAuthPassword();
        }
        return ACRAConstants.NULL_VALUE;
    }

    public boolean includeDropBoxSystemTags() {
        if (this.mIncludeDropboxSystemTags != null) {
            return this.mIncludeDropboxSystemTags.booleanValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.includeDropBoxSystemTags();
        }
        return false;
    }

    public String[] logcatArguments() {
        if (this.mLogcatArguments != null) {
            return this.mLogcatArguments;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.logcatArguments();
        }
        return new String[]{"-t", Integer.toString(100), "-v", "time"};
    }

    public String mailTo() {
        if (this.mMailTo != null) {
            return this.mMailTo;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.mailTo();
        }
        return "";
    }

    public int maxNumberOfRequestRetries() {
        if (this.mMaxNumberOfRequestRetries != null) {
            return this.mMaxNumberOfRequestRetries.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.maxNumberOfRequestRetries();
        }
        return 3;
    }

    public ReportingInteractionMode mode() {
        if (this.mMode != null) {
            return this.mMode;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.mode();
        }
        return ReportingInteractionMode.SILENT;
    }

    public int resDialogCommentPrompt() {
        if (this.mResDialogCommentPrompt != null) {
            return this.mResDialogCommentPrompt.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resDialogCommentPrompt();
        }
        return 0;
    }

    public int resDialogEmailPrompt() {
        if (this.mResDialogEmailPrompt != null) {
            return this.mResDialogEmailPrompt.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resDialogEmailPrompt();
        }
        return 0;
    }

    public int resDialogIcon() {
        if (this.mResDialogIcon != null) {
            return this.mResDialogIcon.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resDialogIcon();
        }
        return ACRAConstants.DEFAULT_DIALOG_ICON;
    }

    public int resDialogOkToast() {
        if (this.mResDialogOkToast != null) {
            return this.mResDialogOkToast.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resDialogOkToast();
        }
        return 0;
    }

    public int resDialogText() {
        if (this.mResDialogText != null) {
            return this.mResDialogText.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resDialogText();
        }
        return 0;
    }

    public int resDialogTitle() {
        if (this.mResDialogTitle != null) {
            return this.mResDialogTitle.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resDialogTitle();
        }
        return 0;
    }

    public int resNotifIcon() {
        if (this.mResNotifIcon != null) {
            return this.mResNotifIcon.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resNotifIcon();
        }
        return ACRAConstants.DEFAULT_NOTIFICATION_ICON;
    }

    public int resNotifText() {
        if (this.mResNotifText != null) {
            return this.mResNotifText.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resNotifText();
        }
        return 0;
    }

    public int resNotifTickerText() {
        if (this.mResNotifTickerText != null) {
            return this.mResNotifTickerText.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resNotifTickerText();
        }
        return 0;
    }

    public int resNotifTitle() {
        if (this.mResNotifTitle != null) {
            return this.mResNotifTitle.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resNotifTitle();
        }
        return 0;
    }

    public int resToastText() {
        if (this.mResToastText != null) {
            return this.mResToastText.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.resToastText();
        }
        return 0;
    }

    public int sharedPreferencesMode() {
        if (this.mSharedPreferenceMode != null) {
            return this.mSharedPreferenceMode.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.sharedPreferencesMode();
        }
        return 0;
    }

    public String sharedPreferencesName() {
        if (this.mSharedPreferenceName != null) {
            return this.mSharedPreferenceName;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.sharedPreferencesName();
        }
        return "";
    }

    public int socketTimeout() {
        if (this.mSocketTimeout != null) {
            return this.mSocketTimeout.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.socketTimeout();
        }
        return ACRAConstants.DEFAULT_SOCKET_TIMEOUT;
    }

    public boolean logcatFilterByPid() {
        if (this.mLogcatFilterByPid != null) {
            return this.mLogcatFilterByPid.booleanValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.logcatFilterByPid();
        }
        return false;
    }

    public boolean sendReportsInDevMode() {
        if (this.mSendReportsInDevMode != null) {
            return this.mSendReportsInDevMode.booleanValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.sendReportsInDevMode();
        }
        return true;
    }

    public String[] excludeMatchingSharedPreferencesKeys() {
        if (this.mExcludeMatchingSharedPreferencesKeys != null) {
            return this.mExcludeMatchingSharedPreferencesKeys;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.excludeMatchingSharedPreferencesKeys();
        }
        return new String[0];
    }

    public String applicationLogFile() {
        if (this.mApplicationLogFile != null) {
            return this.mApplicationLogFile;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.applicationLogFile();
        }
        return "";
    }

    public int applicationLogFileLines() {
        if (this.mApplicationLogFileLines != null) {
            return this.mApplicationLogFileLines.intValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.applicationLogFileLines();
        }
        return 100;
    }

    public String googleFormUrlFormat() {
        if (this.mGoogleFormUrlFormat != null) {
            return this.mGoogleFormUrlFormat;
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.googleFormUrlFormat();
        }
        return ACRAConstants.DEFAULT_GOOGLE_FORM_URL_FORMAT;
    }

    public boolean disableSSLCertValidation() {
        if (this.mDisableSSLCertValidation != null) {
            return this.mDisableSSLCertValidation.booleanValue();
        }
        if (this.mReportsCrashes != null) {
            return this.mReportsCrashes.disableSSLCertValidation();
        }
        return false;
    }
}

package com.google.appinventor.components.runtime.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;
import java.io.IOException;

public class OAuth2Helper {
    public static final String PREF_ACCOUNT_NAME = "accountName";
    public static final String PREF_AUTH_TOKEN = "authToken";
    public static final String TAG = "OAuthHelper";
    private static String errorMessage = "Error during OAuth";

    public String getRefreshedAuthToken(Activity activity, String authTokenType) {
        Log.i(TAG, "getRefreshedAuthToken()");
        if (isUiThread()) {
            throw new IllegalArgumentException("Can't get authtoken from UI thread");
        }
        SharedPreferences settings = activity.getPreferences(0);
        String accountName = settings.getString(PREF_ACCOUNT_NAME, null);
        String authToken = settings.getString(PREF_AUTH_TOKEN, null);
        GoogleCredential credential = new GoogleCredential();
        credential.setAccessToken(authToken);
        try {
            Bundle authTokenBundle = (Bundle) getAccountManagerResult(activity, credential, authTokenType, accountName).getResult();
            authToken = authTokenBundle.get("authtoken").toString();
            persistCredentials(settings, authTokenBundle.getString("authAccount"), authToken);
            return authToken;
        } catch (OperationCanceledException e) {
            e.printStackTrace();
            resetAccountCredential(activity);
            errorMessage = "Error: operation cancelled";
            return authToken;
        } catch (AuthenticatorException e2) {
            e2.printStackTrace();
            errorMessage = "Error: Authenticator error";
            return authToken;
        } catch (IOException e3) {
            e3.printStackTrace();
            errorMessage = "Error: I/O error";
            return authToken;
        }
    }

    private AccountManagerFuture<Bundle> getAccountManagerResult(Activity activity, GoogleCredential credential, String authTokenType, String accountName) {
        GoogleAccountManager accountManager = new GoogleAccountManager(activity);
        accountManager.invalidateAuthToken(credential.getAccessToken());
        AccountManager.get(activity).invalidateAuthToken(authTokenType, null);
        Account account = accountManager.getAccountByName(accountName);
        if (account != null) {
            Log.i(TAG, "Getting token by account");
            return accountManager.getAccountManager().getAuthToken(account, authTokenType, true, null, null);
        }
        Log.i(TAG, "Getting token by features, possibly prompting user to select an account");
        return accountManager.getAccountManager().getAuthTokenByFeatures("com.google", authTokenType, null, activity, null, null, null, null);
    }

    private boolean isUiThread() {
        return Looper.getMainLooper().getThread().equals(Thread.currentThread());
    }

    private void persistCredentials(SharedPreferences settings, String accountName, String authToken) {
        Log.i(TAG, "Persisting credentials, acct =" + accountName);
        Editor editor = settings.edit();
        editor.putString(PREF_ACCOUNT_NAME, accountName);
        editor.putString(PREF_AUTH_TOKEN, authToken);
        editor.commit();
    }

    public static void resetAccountCredential(Activity activity) {
        Log.i(TAG, "Reset credentials");
        Editor editor2 = activity.getPreferences(0).edit();
        editor2.remove(PREF_AUTH_TOKEN);
        editor2.remove(PREF_ACCOUNT_NAME);
        editor2.commit();
    }

    public static String getErrorMessage() {
        Log.i(TAG, "getErrorMessage = " + errorMessage);
        return errorMessage;
    }
}

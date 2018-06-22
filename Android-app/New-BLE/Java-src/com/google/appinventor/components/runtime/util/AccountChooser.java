package com.google.appinventor.components.runtime.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.SynchronousQueue;

public class AccountChooser {
    private static final String ACCOUNT_PREFERENCE = "account";
    private static final String ACCOUNT_TYPE = "com.google";
    private static final String LOG_TAG = "AccountChooser";
    private static final String NO_ACCOUNT = "";
    private AccountManager accountManager;
    private Activity activity;
    private String chooseAccountPrompt;
    private String preferencesKey;
    private String service;

    class SelectAccount extends Thread implements OnClickListener, OnCancelListener {
        private String[] accountNames;
        private SynchronousQueue<String> queue;

        class C03151 implements Runnable {
            C03151() {
            }

            public void run() {
                new Builder(AccountChooser.this.activity).setTitle(Html.fromHtml(AccountChooser.this.chooseAccountPrompt)).setOnCancelListener(SelectAccount.this).setSingleChoiceItems(SelectAccount.this.accountNames, -1, SelectAccount.this).show();
                Log.i(AccountChooser.LOG_TAG, "Dialog showing!");
            }
        }

        SelectAccount(Account[] accounts, SynchronousQueue<String> queue) {
            this.queue = queue;
            this.accountNames = new String[accounts.length];
            for (int i = 0; i < accounts.length; i++) {
                this.accountNames[i] = accounts[i].name;
            }
        }

        public void run() {
            AccountChooser.this.activity.runOnUiThread(new C03151());
        }

        public void onClick(DialogInterface dialog, int button) {
            if (button >= 0) {
                try {
                    String account = this.accountNames[button];
                    Log.i(AccountChooser.LOG_TAG, "Chose: " + account);
                    this.queue.put(account);
                } catch (InterruptedException e) {
                }
            } else {
                this.queue.put("");
            }
            dialog.dismiss();
        }

        public void onCancel(DialogInterface dialog) {
            Log.i(AccountChooser.LOG_TAG, "Chose: canceled");
            onClick(dialog, -1);
        }
    }

    public AccountChooser(Activity activity, String service, String title, String key) {
        this.activity = activity;
        this.service = service;
        this.chooseAccountPrompt = title;
        this.preferencesKey = key;
        this.accountManager = AccountManager.get(activity);
    }

    public Account findAccount() {
        Account[] accounts = this.accountManager.getAccountsByType(ACCOUNT_TYPE);
        if (accounts.length == 1) {
            persistAccountName(accounts[0].name);
            return accounts[0];
        } else if (accounts.length == 0) {
            accountName = createAccount();
            if (accountName != null) {
                persistAccountName(accountName);
                return this.accountManager.getAccountsByType(ACCOUNT_TYPE)[0];
            }
            Log.i(LOG_TAG, "User failed to create a valid account");
            return null;
        } else {
            accountName = getPersistedAccountName();
            if (accountName != null) {
                Account account = chooseAccount(accountName, accounts);
                if (account != null) {
                    return account;
                }
            }
            accountName = selectAccount(accounts);
            if (accountName != null) {
                persistAccountName(accountName);
                return chooseAccount(accountName, accounts);
            }
            Log.i(LOG_TAG, "User failed to choose an account");
            return null;
        }
    }

    private Account chooseAccount(String accountName, Account[] accounts) {
        for (Account account : accounts) {
            if (account.name.equals(accountName)) {
                Log.i(LOG_TAG, "chose account: " + accountName);
                return account;
            }
        }
        return null;
    }

    private String createAccount() {
        Log.i(LOG_TAG, "Adding auth token account ...");
        try {
            String accountName = ((Bundle) this.accountManager.addAccount(ACCOUNT_TYPE, this.service, null, null, this.activity, null, null).getResult()).getString("authAccount");
            Log.i(LOG_TAG, "created: " + accountName);
            return accountName;
        } catch (OperationCanceledException e) {
            e.printStackTrace();
        } catch (AuthenticatorException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return null;
    }

    private String selectAccount(Account[] accounts) {
        SynchronousQueue<String> queue = new SynchronousQueue();
        new SelectAccount(accounts, queue).start();
        Log.i(LOG_TAG, "Select: waiting for user...");
        String account = null;
        try {
            account = (String) queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "Selected: " + account);
        return account == "" ? null : account;
    }

    private SharedPreferences getPreferences() {
        return this.activity.getSharedPreferences(this.preferencesKey, 0);
    }

    private String getPersistedAccountName() {
        return getPreferences().getString(ACCOUNT_PREFERENCE, null);
    }

    private void persistAccountName(String accountName) {
        Log.i(LOG_TAG, "persisting account: " + accountName);
        getPreferences().edit().putString(ACCOUNT_PREFERENCE, accountName).commit();
    }

    public void forgetAccountName() {
        getPreferences().edit().remove(ACCOUNT_PREFERENCE).commit();
    }
}

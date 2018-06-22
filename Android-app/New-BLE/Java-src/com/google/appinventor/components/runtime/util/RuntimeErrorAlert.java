package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public final class RuntimeErrorAlert {
    public static void alert(final Object context, String message, String title, String buttonText) {
        Log.i("RuntimeErrorAlert", "in alert");
        AlertDialog alertDialog = new Builder((Context) context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(buttonText, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }
        });
        if (message == null) {
            Log.e(RuntimeErrorAlert.class.getName(), "No error message available");
        } else {
            Log.e(RuntimeErrorAlert.class.getName(), message);
        }
        alertDialog.show();
    }
}

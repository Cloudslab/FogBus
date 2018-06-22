package org.acra.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.acra.ACRA;

public final class ToastSender {
    public static void sendToast(Context context, int toastResourceId, int toastLength) {
        try {
            Toast.makeText(context, toastResourceId, toastLength).show();
        } catch (RuntimeException e) {
            Log.e(ACRA.LOG_TAG, "Could not send crash Toast", e);
        }
    }
}

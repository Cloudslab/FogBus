package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneCallUtil {
    private PhoneCallUtil() {
    }

    public static void makePhoneCall(Context context, String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() > 0) {
            context.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber)));
        }
    }
}

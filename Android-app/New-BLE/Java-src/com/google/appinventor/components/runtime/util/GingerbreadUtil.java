package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.util.Log;
import com.google.appinventor.components.runtime.NearField;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.nio.charset.Charset;
import java.util.Locale;

public class GingerbreadUtil {
    private GingerbreadUtil() {
    }

    public static CookieHandler newCookieManager() {
        return new CookieManager();
    }

    public static boolean clearCookies(CookieHandler cookieHandler) {
        if (cookieHandler instanceof CookieManager) {
            CookieStore cookieStore = ((CookieManager) cookieHandler).getCookieStore();
            if (cookieStore != null) {
                cookieStore.removeAll();
                return true;
            }
        }
        return false;
    }

    public static NfcAdapter newNfcAdapter(Context context) {
        return NfcAdapter.getDefaultAdapter(context);
    }

    public static void enableNFCWriteMode(Activity activity, NfcAdapter nfcAdapter, String textToWrite) {
        nfcAdapter.enableForegroundNdefPush(activity, new NdefMessage(new NdefRecord[]{createTextRecord(textToWrite, true)}));
    }

    public static void disableNFCAdapter(Activity activity, NfcAdapter nfcAdapter) {
        nfcAdapter.disableForegroundNdefPush(activity);
    }

    public static NdefRecord createTextRecord(String payload, boolean encodeInUtf8) {
        byte[] langBytes = Locale.getDefault().getLanguage().getBytes(Charset.forName("US-ASCII"));
        byte[] textBytes = payload.getBytes(encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16"));
        byte[] data = new byte[((langBytes.length + 1) + textBytes.length)];
        data[0] = (byte) ((char) (langBytes.length + (encodeInUtf8 ? 0 : 128)));
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, langBytes.length + 1, textBytes.length);
        return new NdefRecord((short) 1, NdefRecord.RTD_TEXT, new byte[0], data);
    }

    public static void resolveNFCIntent(Intent intent, NearField nfc) {
        if (!"android.nfc.action.NDEF_DISCOVERED".equals(intent.getAction())) {
            Log.e("nearfield", "Unknown intent " + intent);
        } else if (nfc.ReadMode()) {
            NdefMessage[] msgs;
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                byte[] empty = new byte[0];
                NdefRecord[] ndefRecordArr = new NdefRecord[]{new NdefRecord((short) 5, empty, empty, empty)};
                msgs = new NdefMessage[]{new NdefMessage(ndefRecordArr)};
            }
            nfc.TagRead(new String(msgs[0].getRecords()[0].getPayload()).substring(3));
        } else {
            Tag detectedTag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
            NdefMessage msg = null;
            if (nfc.WriteType() == 1) {
                NdefRecord textRecord = createTextRecord(nfc.TextToWrite(), true);
                msg = new NdefMessage(new NdefRecord[]{textRecord});
            }
            if (writeNFCTag(msg, detectedTag)) {
                nfc.TagWritten();
            }
        }
    }

    public static boolean writeNFCTag(NdefMessage message, Tag tag) {
        int size = message.toByteArray().length;
        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();
                if (!ndef.isWritable() || ndef.getMaxSize() < size) {
                    return false;
                }
                ndef.writeNdefMessage(message);
                return true;
            }
            NdefFormatable format = NdefFormatable.get(tag);
            if (format == null) {
                return false;
            }
            try {
                format.connect();
                format.format(message);
                return true;
            } catch (IOException e) {
                return false;
            }
        } catch (Exception e2) {
            return false;
        }
    }
}

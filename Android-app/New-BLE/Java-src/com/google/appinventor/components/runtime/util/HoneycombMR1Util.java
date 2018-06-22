package com.google.appinventor.components.runtime.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import java.io.InputStream;

public class HoneycombMR1Util {
    private HoneycombMR1Util() {
    }

    public static Uri getContentUri() {
        return Contacts.CONTENT_URI;
    }

    public static Uri getPhoneContentUri() {
        return Phone.CONTENT_URI;
    }

    public static Uri getDataContentUri() {
        return Data.CONTENT_URI;
    }

    public static String[] getContactProjection() {
        return new String[]{"_id", "display_name", "photo_thumb_uri", "photo_uri"};
    }

    public static String[] getNameProjection() {
        return new String[]{"contact_id", "display_name", "photo_thumb_uri", "data1"};
    }

    public static String[] getDataProjection() {
        return new String[]{"mimetype", "data1", "data2", "data1", "data2"};
    }

    public static String[] getEmailAdapterProjection() {
        return new String[]{"_id", "display_name", "data1", "mimetype"};
    }

    public static int getIdIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("_id");
    }

    public static int getContactIdIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("contact_id");
    }

    public static int getNameIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("display_name");
    }

    public static int getThumbnailIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("photo_thumb_uri");
    }

    public static int getPhotoIndex(Cursor contactCursor) {
        return contactCursor.getColumnIndex("photo_uri");
    }

    public static int getPhoneIndex(Cursor dataCursor) {
        return dataCursor.getColumnIndex("data1");
    }

    public static int getEmailIndex(Cursor dataCursor) {
        return dataCursor.getColumnIndex("data1");
    }

    public static int getMimeIndex(Cursor dataCursor) {
        return dataCursor.getColumnIndex("mimetype");
    }

    public static String getPhoneType() {
        return "vnd.android.cursor.item/phone_v2";
    }

    public static String getEmailType() {
        return "vnd.android.cursor.item/email_v2";
    }

    public static String getDisplayName() {
        return "display_name";
    }

    public static String getEmailAddress() {
        return "data1";
    }

    public static String getDataMimeType() {
        return "mimetype";
    }

    public static Cursor getDataCursor(String id, Activity activityContext, String[] dataProjection) {
        return activityContext.getContentResolver().query(Data.CONTENT_URI, dataProjection, "contact_id=? AND (mimetype=? OR mimetype=?)", new String[]{id, "vnd.android.cursor.item/phone_v2", "vnd.android.cursor.item/email_v2"}, null);
    }

    public static InputStream openContactPhotoInputStreamHelper(ContentResolver cr, Uri contactUri) {
        return Contacts.openContactPhotoInputStream(cr, contactUri);
    }

    public static String getTimesContacted() {
        return "times_contacted";
    }
}

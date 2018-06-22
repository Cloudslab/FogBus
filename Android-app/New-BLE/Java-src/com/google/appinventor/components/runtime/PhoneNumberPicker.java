package com.google.appinventor.components.runtime;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts.People;
import android.provider.Contacts.Phones;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.HoneycombMR1Util;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.util.ArrayList;
import java.util.List;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SOCIAL, description = "A button that, when clicked on, displays a list of the contacts' phone numbers to choose among. After the user has made a selection, the following properties will be set to information about the chosen contact: <ul>\n<li> <code>ContactName</code>: the contact's name </li>\n <li> <code>PhoneNumber</code>: the contact's phone number </li>\n <li> <code>EmailAddress</code>: the contact's email address </li> <li> <code>Picture</code>: the name of the file containing the contact's image, which can be used as a <code>Picture</code> property value for the <code>Image</code> or <code>ImageSprite</code> component.</li></ul>\n</p><p>Other properties affect the appearance of the button (<code>TextAlignment</code>, <code>BackgroundColor</code>, etc.) and whether it can be clicked on (<code>Enabled</code>).</p>\n<p>The PhoneNumberPicker component may not work on all Android devices. For example, on Android systems before system 3.0, the returned lists of phone numbers and email addresses will be empty.\n", version = 4)
@UsesPermissions(permissionNames = "android.permission.READ_CONTACTS")
public class PhoneNumberPicker extends ContactPicker {
    private static String[] DATA_PROJECTION = null;
    private static final int EMAIL_INDEX = 3;
    private static final String LOG_TAG = "PhoneNumberPicker";
    private static final int NAME_INDEX = 0;
    private static String[] NAME_PROJECTION = null;
    private static final int NUMBER_INDEX = 1;
    private static final int PERSON_INDEX = 2;
    private static final String[] PROJECTION = new String[]{"name", "number", "person", "primary_email"};

    public PhoneNumberPicker(ComponentContainer container) {
        super(container, Phones.CONTENT_URI);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String PhoneNumber() {
        return ensureNotNull(this.phoneNumber);
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == -1) {
            Log.i(LOG_TAG, "received intent is " + data);
            Uri phoneUri = data.getData();
            String desiredPhoneUri = "";
            if (SdkLevel.getLevel() >= 12) {
                desiredPhoneUri = "//com.android.contacts/data";
            } else {
                desiredPhoneUri = "//contacts/phones";
            }
            if (checkContactUri(phoneUri, desiredPhoneUri)) {
                Cursor contactCursor = null;
                Cursor dataCursor = null;
                try {
                    if (SdkLevel.getLevel() >= 12) {
                        NAME_PROJECTION = HoneycombMR1Util.getNameProjection();
                        contactCursor = this.activityContext.getContentResolver().query(phoneUri, NAME_PROJECTION, null, null, null);
                        String id = postHoneycombGetContactNameAndPicture(contactCursor);
                        DATA_PROJECTION = HoneycombMR1Util.getDataProjection();
                        dataCursor = HoneycombMR1Util.getDataCursor(id, this.activityContext, DATA_PROJECTION);
                        postHoneycombGetContactEmailsAndPhones(dataCursor);
                    } else {
                        contactCursor = this.activityContext.getContentResolver().query(phoneUri, PROJECTION, null, null, null);
                        preHoneycombGetContactInfo(contactCursor);
                    }
                    Log.i(LOG_TAG, "Contact name = " + this.contactName + ", phone number = " + this.phoneNumber + ", emailAddress = " + this.emailAddress + ", contactPhotoUri = " + this.contactPictureUri);
                    if (contactCursor != null) {
                        contactCursor.close();
                    }
                    if (dataCursor != null) {
                        dataCursor.close();
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Exception in resultReturned", e);
                    puntContactSelection(ErrorMessages.ERROR_PHONE_UNSUPPORTED_CONTACT_PICKER);
                    if (contactCursor != null) {
                        contactCursor.close();
                    }
                    if (dataCursor != null) {
                        dataCursor.close();
                    }
                } catch (Throwable th) {
                    if (contactCursor != null) {
                        contactCursor.close();
                    }
                    if (dataCursor != null) {
                        dataCursor.close();
                    }
                }
            }
            AfterPicking();
        }
    }

    public void preHoneycombGetContactInfo(Cursor cursor) {
        if (cursor.moveToFirst()) {
            this.contactName = guardCursorGetString(cursor, 0);
            this.phoneNumber = guardCursorGetString(cursor, 1);
            this.contactPictureUri = ContentUris.withAppendedId(People.CONTENT_URI, (long) cursor.getInt(2)).toString();
            this.emailAddress = getEmailAddress(guardCursorGetString(cursor, 3));
        }
    }

    public String postHoneycombGetContactNameAndPicture(Cursor contactCursor) {
        String id = "";
        if (!contactCursor.moveToFirst()) {
            return id;
        }
        int CONTACT_ID_INDEX = HoneycombMR1Util.getContactIdIndex(contactCursor);
        int NAME_INDEX = HoneycombMR1Util.getNameIndex(contactCursor);
        int PHOTO_INDEX = HoneycombMR1Util.getThumbnailIndex(contactCursor);
        this.phoneNumber = guardCursorGetString(contactCursor, HoneycombMR1Util.getPhoneIndex(contactCursor));
        id = guardCursorGetString(contactCursor, CONTACT_ID_INDEX);
        this.contactName = guardCursorGetString(contactCursor, NAME_INDEX);
        this.contactPictureUri = guardCursorGetString(contactCursor, PHOTO_INDEX);
        return id;
    }

    public void postHoneycombGetContactEmailsAndPhones(Cursor dataCursor) {
        List<String> phoneListToStore = new ArrayList();
        List<String> emailListToStore = new ArrayList();
        if (dataCursor.moveToFirst()) {
            int PHONE_INDEX = HoneycombMR1Util.getPhoneIndex(dataCursor);
            int EMAIL_INDEX = HoneycombMR1Util.getEmailIndex(dataCursor);
            int MIME_INDEX = HoneycombMR1Util.getMimeIndex(dataCursor);
            String phoneType = HoneycombMR1Util.getPhoneType();
            String emailType = HoneycombMR1Util.getEmailType();
            while (!dataCursor.isAfterLast()) {
                String type = guardCursorGetString(dataCursor, MIME_INDEX);
                if (type.contains(phoneType)) {
                    phoneListToStore.add(guardCursorGetString(dataCursor, PHONE_INDEX));
                } else if (type.contains(emailType)) {
                    emailListToStore.add(guardCursorGetString(dataCursor, EMAIL_INDEX));
                } else {
                    Log.i("ContactPicker", "Type mismatch: " + type + " not " + phoneType + " or " + emailType);
                }
                dataCursor.moveToNext();
            }
            this.phoneNumberList = phoneListToStore;
            this.emailAddressList = emailListToStore;
            if (this.emailAddressList.isEmpty()) {
                this.emailAddress = "";
            } else {
                this.emailAddress = (String) this.emailAddressList.get(0);
            }
        }
    }
}

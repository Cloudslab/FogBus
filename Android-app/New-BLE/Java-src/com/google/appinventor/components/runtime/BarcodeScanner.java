package com.google.appinventor.components.runtime;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesActivities;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.annotations.androidmanifest.ActivityElement;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;

@DesignerComponent(category = ComponentCategory.SENSORS, description = "Component for using the Barcode Scanner to read a barcode", iconName = "images/barcodeScanner.png", nonVisible = true, version = 2)
@UsesLibraries(libraries = "Barcode.jar,core.jar")
@SimpleObject
@UsesActivities(activities = {@ActivityElement(configChanges = "orientation|keyboardHidden", name = "com.google.zxing.client.android.AppInvCaptureActivity", screenOrientation = "landscape", stateNotNeeded = "true", theme = "@android:style/Theme.NoTitleBar.Fullscreen", windowSoftInputMode = "stateAlwaysHidden")})
@UsesPermissions(permissionNames = "android.permission.CAMERA")
public class BarcodeScanner extends AndroidNonvisibleComponent implements ActivityResultListener, Component {
    private static final String LOCAL_SCAN = "com.google.zxing.client.android.AppInvCaptureActivity";
    private static final String SCANNER_RESULT_NAME = "SCAN_RESULT";
    private static final String SCAN_INTENT = "com.google.zxing.client.android.SCAN";
    private final ComponentContainer container;
    private int requestCode;
    private String result = "";
    private boolean useExternalScanner = true;

    public BarcodeScanner(ComponentContainer container) {
        super(container.$form());
        this.container = container;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Text result of the previous scan.")
    public String Result() {
        return this.result;
    }

    @SimpleFunction(description = "Begins a barcode scan, using the camera. When the scan is complete, the AfterScan event will be raised.")
    public void DoScan() {
        Intent intent = new Intent(SCAN_INTENT);
        if (!this.useExternalScanner && SdkLevel.getLevel() >= 5) {
            intent.setComponent(new ComponentName(this.container.$form().getPackageName(), LOCAL_SCAN));
        }
        if (this.requestCode == 0) {
            this.requestCode = this.form.registerForActivityResult(this);
        }
        try {
            this.container.$context().startActivityForResult(intent, this.requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            this.container.$form().dispatchErrorOccurredEvent(this, "BarcodeScanner", ErrorMessages.ERROR_NO_SCANNER_FOUND, "");
        }
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == -1) {
            if (data.hasExtra(SCANNER_RESULT_NAME)) {
                this.result = data.getStringExtra(SCANNER_RESULT_NAME);
            } else {
                this.result = "";
            }
            AfterScan(this.result);
        }
    }

    @SimpleEvent
    public void AfterScan(String result) {
        EventDispatcher.dispatchEvent(this, "AfterScan", result);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If true App Inventor will look for and use an external scanning program such as \"Bar Code Scanner.\"")
    public boolean UseExternalScanner() {
        return this.useExternalScanner;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void UseExternalScanner(boolean useExternalScanner) {
        this.useExternalScanner = useExternalScanner;
    }
}

package com.google.appinventor.components.runtime;

import android.content.Intent;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MEDIA, description = "Component for using Voice Recognition to convert from speech to text", iconName = "images/speechRecognizer.png", nonVisible = true, version = 1)
public class SpeechRecognizer extends AndroidNonvisibleComponent implements Component, ActivityResultListener {
    private final ComponentContainer container;
    private int requestCode;
    private String result = "";

    public SpeechRecognizer(ComponentContainer container) {
        super(container.$form());
        this.container = container;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String Result() {
        return this.result;
    }

    @SimpleFunction
    public void GetText() {
        BeforeGettingText();
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        if (this.requestCode == 0) {
            this.requestCode = this.form.registerForActivityResult(this);
        }
        this.container.$context().startActivityForResult(intent, this.requestCode);
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        if (requestCode == this.requestCode && resultCode == -1) {
            if (data.hasExtra("android.speech.extra.RESULTS")) {
                this.result = (String) data.getExtras().getStringArrayList("android.speech.extra.RESULTS").get(0);
            } else {
                this.result = "";
            }
            AfterGettingText(this.result);
        }
    }

    @SimpleEvent
    public void BeforeGettingText() {
        EventDispatcher.dispatchEvent(this, "BeforeGettingText", new Object[0]);
    }

    @SimpleEvent
    public void AfterGettingText(String result) {
        EventDispatcher.dispatchEvent(this, "AfterGettingText", result);
    }
}

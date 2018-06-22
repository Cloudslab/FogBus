package com.google.appinventor.components.runtime.util;

import android.content.Intent;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.ITextToSpeech.TextToSpeechCallback;
import gnu.expr.Declaration;
import java.util.Locale;

public class ExternalTextToSpeech implements ITextToSpeech, ActivityResultListener {
    private static final String TTS_INTENT = "com.google.tts.makeBagel";
    private final TextToSpeechCallback callback;
    private final ComponentContainer container;
    private int requestCode;

    public ExternalTextToSpeech(ComponentContainer container, TextToSpeechCallback callback) {
        this.container = container;
        this.callback = callback;
    }

    public void speak(String message, Locale loc) {
        Intent intent = new Intent(TTS_INTENT);
        intent.setFlags(131072);
        intent.setFlags(8388608);
        intent.setFlags(Declaration.MODULE_REFERENCE);
        intent.putExtra("message", message);
        intent.putExtra("language", loc.getISO3Language());
        intent.putExtra("country", loc.getISO3Country());
        if (this.requestCode == 0) {
            this.requestCode = this.container.$form().registerForActivityResult(this);
        }
        this.container.$context().startActivityForResult(intent, this.requestCode);
    }

    public void onDestroy() {
    }

    public void onStop() {
    }

    public void onResume() {
    }

    public void setPitch(float pitch) {
    }

    public void setSpeechRate(float speechRate) {
    }

    public void resultReturned(int requestCode, int resultCode, Intent data) {
        boolean isSuccess = requestCode == this.requestCode && resultCode == -1;
        if (isSuccess) {
            this.callback.onSuccess();
        } else {
            this.callback.onFailure();
        }
    }

    public int isLanguageAvailable(Locale loc) {
        return -1;
    }

    public boolean isInitialized() {
        return true;
    }
}

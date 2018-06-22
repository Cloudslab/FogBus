package com.google.appinventor.components.runtime.util;

import java.util.Locale;

public interface ITextToSpeech {

    public interface TextToSpeechCallback {
        void onFailure();

        void onSuccess();
    }

    boolean isInitialized();

    int isLanguageAvailable(Locale locale);

    void onDestroy();

    void onResume();

    void onStop();

    void setPitch(float f);

    void setSpeechRate(float f);

    void speak(String str, Locale locale);
}

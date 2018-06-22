package com.google.appinventor.components.runtime.util;

public interface AsyncCallbackPair<T> {
    void onFailure(String str);

    void onSuccess(T t);
}

package com.google.appinventor.components.runtime.util;

public abstract class AsyncCallbackFacade<S, T> implements AsyncCallbackPair<S> {
    protected final AsyncCallbackPair<T> callback;

    public AsyncCallbackFacade(AsyncCallbackPair<T> target) {
        this.callback = target;
    }

    public void onFailure(String message) {
        this.callback.onFailure(message);
    }
}

package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
public class NoSuchFileError extends RuntimeError {
    public NoSuchFileError(String message) {
        super(message);
    }
}

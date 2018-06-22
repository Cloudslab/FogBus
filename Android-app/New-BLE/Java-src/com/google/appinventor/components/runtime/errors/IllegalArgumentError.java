package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
public class IllegalArgumentError extends RuntimeError {
    public IllegalArgumentError(String msg) {
        super(msg);
    }
}

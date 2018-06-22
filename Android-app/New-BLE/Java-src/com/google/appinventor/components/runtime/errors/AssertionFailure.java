package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
public class AssertionFailure extends RuntimeError {
    public AssertionFailure(String msg) {
        super(msg);
    }
}

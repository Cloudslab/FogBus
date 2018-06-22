package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
public class FileIOError extends RuntimeError {
    public FileIOError(String message) {
        super(message);
    }
}

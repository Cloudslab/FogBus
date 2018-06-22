package com.google.appinventor.components.runtime.errors;

import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
public class FileAlreadyExistsError extends RuntimeError {
    public FileAlreadyExistsError(String message) {
        super(message);
    }
}

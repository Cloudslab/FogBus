package com.google.appinventor.components.runtime.errors;

public class YailRuntimeError extends RuntimeError {
    private String errorType;

    public YailRuntimeError(String message, String errorType) {
        super(message);
        this.errorType = errorType;
    }

    public String getErrorType() {
        return this.errorType;
    }
}

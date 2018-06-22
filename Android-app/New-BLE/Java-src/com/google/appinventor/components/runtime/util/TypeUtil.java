package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.errors.DispatchableError;

public final class TypeUtil {
    private TypeUtil() {
    }

    public static <T> T cast(Object o, Class<T> tClass, String expected) {
        if (o == null) {
            return null;
        }
        if (tClass.isInstance(o)) {
            return tClass.cast(o);
        }
        throw new DispatchableError(ErrorMessages.ERROR_INVALID_TYPE, o.getClass().getSimpleName(), expected);
    }

    public static <T> T castNotNull(Object o, Class<T> tClass, String expected) {
        if (o != null) {
            return cast(o, tClass, expected);
        }
        throw new DispatchableError(ErrorMessages.ERROR_INVALID_TYPE, "null", expected);
    }
}

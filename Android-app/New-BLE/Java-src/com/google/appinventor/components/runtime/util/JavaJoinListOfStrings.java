package com.google.appinventor.components.runtime.util;

import java.util.List;

public final class JavaJoinListOfStrings {
    private static final boolean DEBUG = false;
    public static final String LOG_TAG = "JavaJoinListOfStrings";

    public static String joinStrings(List<Object> listOfStrings, String separator) {
        return join(listOfStrings, separator);
    }

    private static String join(List<Object> list, String separator) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Object item : list) {
            if (first) {
                first = false;
            } else {
                sb.append(separator);
            }
            sb.append(item.toString());
        }
        return sb.toString();
    }
}

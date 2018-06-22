package org.acra.log;

import android.util.Log;

public final class AndroidLogDelegate implements ACRALog {
    public int mo1617v(String tag, String msg) {
        return Log.v(tag, msg);
    }

    public int mo1618v(String tag, String msg, Throwable tr) {
        return Log.v(tag, msg, tr);
    }

    public int mo1610d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    public int mo1611d(String tag, String msg, Throwable tr) {
        return Log.d(tag, msg, tr);
    }

    public int mo1615i(String tag, String msg) {
        return Log.i(tag, msg);
    }

    public int mo1616i(String tag, String msg, Throwable tr) {
        return Log.i(tag, msg, tr);
    }

    public int mo1619w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    public int mo1620w(String tag, String msg, Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    public int mo1621w(String tag, Throwable tr) {
        return Log.w(tag, tr);
    }

    public int mo1612e(String tag, String msg) {
        return Log.e(tag, msg);
    }

    public int mo1613e(String tag, String msg, Throwable tr) {
        return Log.e(tag, msg, tr);
    }

    public String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }
}

package org.acra.log;

import android.util.Log;

public final class AndroidLogDelegate implements ACRALog {
    public int mo1619v(String tag, String msg) {
        return Log.v(tag, msg);
    }

    public int mo1620v(String tag, String msg, Throwable tr) {
        return Log.v(tag, msg, tr);
    }

    public int mo1612d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    public int mo1613d(String tag, String msg, Throwable tr) {
        return Log.d(tag, msg, tr);
    }

    public int mo1617i(String tag, String msg) {
        return Log.i(tag, msg);
    }

    public int mo1618i(String tag, String msg, Throwable tr) {
        return Log.i(tag, msg, tr);
    }

    public int mo1621w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    public int mo1622w(String tag, String msg, Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    public int mo1623w(String tag, Throwable tr) {
        return Log.w(tag, tr);
    }

    public int mo1614e(String tag, String msg) {
        return Log.e(tag, msg);
    }

    public int mo1615e(String tag, String msg, Throwable tr) {
        return Log.e(tag, msg, tr);
    }

    public String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }
}

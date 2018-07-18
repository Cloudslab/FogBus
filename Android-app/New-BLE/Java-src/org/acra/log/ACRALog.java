package org.acra.log;

public interface ACRALog {
    int mo1612d(String str, String str2);

    int mo1613d(String str, String str2, Throwable th);

    int mo1614e(String str, String str2);

    int mo1615e(String str, String str2, Throwable th);

    String getStackTraceString(Throwable th);

    int mo1617i(String str, String str2);

    int mo1618i(String str, String str2, Throwable th);

    int mo1619v(String str, String str2);

    int mo1620v(String str, String str2, Throwable th);

    int mo1621w(String str, String str2);

    int mo1622w(String str, String str2, Throwable th);

    int mo1623w(String str, Throwable th);
}

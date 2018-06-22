package org.acra.log;

public interface ACRALog {
    int mo1610d(String str, String str2);

    int mo1611d(String str, String str2, Throwable th);

    int mo1612e(String str, String str2);

    int mo1613e(String str, String str2, Throwable th);

    String getStackTraceString(Throwable th);

    int mo1615i(String str, String str2);

    int mo1616i(String str, String str2, Throwable th);

    int mo1617v(String str, String str2);

    int mo1618v(String str, String str2, Throwable th);

    int mo1619w(String str, String str2);

    int mo1620w(String str, String str2, Throwable th);

    int mo1621w(String str, Throwable th);
}

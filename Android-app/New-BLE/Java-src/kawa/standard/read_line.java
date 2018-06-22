package kawa.standard;

import gnu.bytecode.Access;
import gnu.expr.Special;
import gnu.lists.FString;
import gnu.mapping.Values;
import gnu.text.LineBufferedReader;
import java.io.IOException;

public class read_line {
    public static Object apply(LineBufferedReader in, String handling) throws IOException {
        if (in.read() < 0) {
            return Special.eof;
        }
        StringBuffer sbuf;
        char mode;
        int length;
        char last;
        Object dataStr;
        int start = in.pos - 1;
        int pos = start;
        int limit = in.limit;
        char[] buffer = in.buffer;
        int delim = -1;
        int pos2 = pos;
        while (pos2 < limit) {
            pos = pos2 + 1;
            int ch = buffer[pos2];
            FString delimStr;
            if (ch == 13 || ch == 10) {
                pos--;
                if (handling == "trim" || handling == "peek") {
                    if (handling == "peek") {
                        delim = 0;
                    }
                    if (ch == 10) {
                        delim = 1;
                    } else {
                        if (pos + 1 < limit) {
                            delim = buffer[pos + 1] == '\n' ? 2 : 1;
                        }
                        sbuf = new StringBuffer(100);
                        if (pos > start) {
                            sbuf.append(buffer, start, pos - start);
                        }
                        in.pos = pos;
                        mode = handling == "peek" ? 'P' : (handling != "concat" || handling == "split") ? 'A' : Access.INNERCLASS_CONTEXT;
                        in.readLine(sbuf, mode);
                        length = sbuf.length();
                        if (handling == "split") {
                            if (length == 0) {
                                delim = 0;
                            } else {
                                last = sbuf.charAt(length - 1);
                                if (last != '\r') {
                                    delim = 1;
                                } else if (last != '\n') {
                                    delim = 0;
                                } else if (last > '\u0002' || sbuf.charAt(length - 2) != '\r') {
                                    delim = 1;
                                } else {
                                    delim = 2;
                                }
                                length -= delim;
                            }
                        }
                        dataStr = new FString(sbuf, 0, length);
                        if (handling == "split") {
                            return dataStr;
                        }
                        delimStr = new FString(sbuf, length - delim, delim);
                        return new Values(new Object[]{dataStr, delimStr});
                    }
                    in.pos = pos + delim;
                } else {
                    if (handling == "concat" && ch == 10) {
                        pos++;
                        in.pos = pos;
                    }
                    sbuf = new StringBuffer(100);
                    if (pos > start) {
                        sbuf.append(buffer, start, pos - start);
                    }
                    in.pos = pos;
                    if (handling == "peek") {
                        if (handling != "concat") {
                        }
                    }
                    in.readLine(sbuf, mode);
                    length = sbuf.length();
                    if (handling == "split") {
                        if (length == 0) {
                            last = sbuf.charAt(length - 1);
                            if (last != '\r') {
                                delim = 1;
                            } else if (last != '\n') {
                                if (last > '\u0002') {
                                }
                                delim = 1;
                            } else {
                                delim = 0;
                            }
                            length -= delim;
                        } else {
                            delim = 0;
                        }
                    }
                    dataStr = new FString(sbuf, 0, length);
                    if (handling == "split") {
                        return dataStr;
                    }
                    delimStr = new FString(sbuf, length - delim, delim);
                    return new Values(new Object[]{dataStr, delimStr});
                }
                return new FString(buffer, start, pos - start);
            }
            pos2 = pos;
        }
        pos = pos2;
        sbuf = new StringBuffer(100);
        if (pos > start) {
            sbuf.append(buffer, start, pos - start);
        }
        in.pos = pos;
        if (handling == "peek") {
        }
        in.readLine(sbuf, mode);
        length = sbuf.length();
        if (handling == "split") {
            if (length == 0) {
                delim = 0;
            } else {
                last = sbuf.charAt(length - 1);
                if (last != '\r') {
                    delim = 1;
                } else if (last != '\n') {
                    delim = 0;
                } else {
                    if (last > '\u0002') {
                    }
                    delim = 1;
                }
                length -= delim;
            }
        }
        dataStr = new FString(sbuf, 0, length);
        if (handling == "split") {
            return dataStr;
        }
        delimStr = new FString(sbuf, length - delim, delim);
        return new Values(new Object[]{dataStr, delimStr});
    }
}

package gnu.lists;

import java.io.PrintWriter;

public class Strings {
    public static void makeUpperCase(CharSeq str) {
        int i = str.length();
        while (true) {
            i--;
            if (i >= 0) {
                str.setCharAt(i, Character.toUpperCase(str.charAt(i)));
            } else {
                return;
            }
        }
    }

    public static void makeLowerCase(CharSeq str) {
        int i = str.length();
        while (true) {
            i--;
            if (i >= 0) {
                str.setCharAt(i, Character.toLowerCase(str.charAt(i)));
            } else {
                return;
            }
        }
    }

    public static void makeCapitalize(CharSeq str) {
        char prev = ' ';
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (Character.isLetterOrDigit(prev)) {
                ch = Character.toLowerCase(ch);
            } else {
                ch = Character.toTitleCase(ch);
            }
            str.setCharAt(i, ch);
            prev = ch;
        }
    }

    public static void printQuoted(CharSequence str, PrintWriter ps, int escapes) {
        int len = str.length();
        ps.print('\"');
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if (ch == '\\' || ch == '\"') {
                ps.print('\\');
            } else if (escapes > 0) {
                if (ch == '\n') {
                    ps.print("\\n");
                } else if (ch == '\r') {
                    ps.print("\\r");
                } else if (ch == '\t') {
                    ps.print("\\t");
                } else if (ch == '\u0007') {
                    ps.print("\\a");
                } else if (ch == '\b') {
                    ps.print("\\b");
                } else if (ch == '\u000b') {
                    ps.print("\\v");
                } else if (ch == '\f') {
                    ps.print("\\f");
                }
            }
            ps.print(ch);
        }
        ps.print('\"');
    }
}

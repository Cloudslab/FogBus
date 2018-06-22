package gnu.kawa.functions;

import com.google.appinventor.components.common.ComponentConstants;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import gnu.mapping.Namespace;
import gnu.mapping.Symbol;
import java.text.BreakIterator;

public class UnicodeUtils {
    static final Symbol Cc;
    static final Symbol Cf;
    static final Symbol Cn;
    static final Symbol Co;
    static final Symbol Cs;
    static final Symbol Ll;
    static final Symbol Lm;
    static final Symbol Lo;
    static final Symbol Lt;
    static final Symbol Lu;
    static final Symbol Mc;
    static final Symbol Me;
    static final Symbol Mn;
    static final Symbol Nd;
    static final Symbol Nl;
    static final Symbol No;
    static final Symbol Pc;
    static final Symbol Pd;
    static final Symbol Pe;
    static final Symbol Pf;
    static final Symbol Pi;
    static final Symbol Po;
    static final Symbol Ps;
    static final Symbol Sc;
    static final Symbol Sk;
    static final Symbol Sm;
    static final Symbol So;
    static final Symbol Zl;
    static final Symbol Zp;
    static final Symbol Zs;

    public static boolean isWhitespace(int ch) {
        if (ch == 32 || (ch >= 9 && ch <= 13)) {
            return true;
        }
        if (ch < 133) {
            return false;
        }
        if (ch == 133 || ch == ComponentConstants.TEXTBOX_PREFERRED_WIDTH || ch == 5760 || ch == 6158) {
            return true;
        }
        if (ch < 8192 || ch > 12288) {
            return false;
        }
        if (ch <= 8202 || ch == 8232 || ch == 8233 || ch == 8239 || ch == 8287 || ch == 12288) {
            return true;
        }
        return false;
    }

    public static String capitalize(String str) {
        StringBuilder sbuf = new StringBuilder();
        BreakIterator wb = BreakIterator.getWordInstance();
        wb.setText(str);
        int start = wb.first();
        for (int end = wb.next(); end != -1; end = wb.next()) {
            boolean isWord = false;
            for (int p = start; p < end; p++) {
                if (Character.isLetter(str.codePointAt(p))) {
                    isWord = true;
                    break;
                }
            }
            if (isWord) {
                sbuf.append(Character.toTitleCase(str.charAt(start)));
                sbuf.append(str.substring(start + 1, end).toLowerCase());
            } else {
                sbuf.append(str, start, end);
            }
            start = end;
        }
        return sbuf.toString();
    }

    public static String foldCase(CharSequence str) {
        int len = str.length();
        if (len == 0) {
            return "";
        }
        StringBuilder sbuf = null;
        int start = 0;
        int i = 0;
        while (true) {
            int ch = i == len ? -1 : str.charAt(i);
            boolean sigma = ch == 931 || ch == 963 || ch == 962;
            if (ch < 0 || ch == ErrorMessages.ERROR_TWITTER_UNABLE_TO_GET_ACCESS_TOKEN || ch == ErrorMessages.ERROR_TWITTER_AUTHORIZATION_FAILED || sigma) {
                if (sbuf == null && ch >= 0) {
                    sbuf = new StringBuilder();
                }
                if (i > start) {
                    String converted = str.subSequence(start, i).toString().toUpperCase().toLowerCase();
                    if (sbuf == null) {
                        return converted;
                    }
                    sbuf.append(converted);
                }
                if (ch < 0) {
                    return sbuf.toString();
                }
                if (sigma) {
                    ch = 963;
                }
                sbuf.append((char) ch);
                start = i + 1;
            }
            i++;
        }
    }

    public static Symbol generalCategory(int ch) {
        switch (Character.getType(ch)) {
            case 1:
                return Lu;
            case 2:
                return Ll;
            case 3:
                return Lt;
            case 4:
                return Lm;
            case 5:
                return Lo;
            case 6:
                return Mn;
            case 7:
                return Me;
            case 8:
                return Mc;
            case 9:
                return Nd;
            case 10:
                return Nl;
            case 11:
                return No;
            case 12:
                return Zs;
            case 13:
                return Zl;
            case 14:
                return Zp;
            case 15:
                return Cc;
            case 16:
                return Cf;
            case 18:
                return Co;
            case 19:
                return Cs;
            case 20:
                return Pd;
            case 21:
                return Ps;
            case 22:
                return Pe;
            case 23:
                return Pc;
            case 24:
                return Po;
            case 25:
                return Sm;
            case 26:
                return Sc;
            case 27:
                return Sk;
            case 28:
                return So;
            case 29:
                return Pi;
            case 30:
                return Pf;
            default:
                return Cn;
        }
    }

    static {
        Namespace empty = Namespace.EmptyNamespace;
        Mc = empty.getSymbol("Mc");
        Pc = empty.getSymbol("Pc");
        Cc = empty.getSymbol("Cc");
        Sc = empty.getSymbol("Sc");
        Pd = empty.getSymbol("Pd");
        Nd = empty.getSymbol("Nd");
        Me = empty.getSymbol("Me");
        Pe = empty.getSymbol("Pe");
        Pf = empty.getSymbol("Pf");
        Cf = empty.getSymbol("Cf");
        Pi = empty.getSymbol("Pi");
        Nl = empty.getSymbol("Nl");
        Zl = empty.getSymbol("Zl");
        Ll = empty.getSymbol("Ll");
        Sm = empty.getSymbol("Sm");
        Lm = empty.getSymbol("Lm");
        Sk = empty.getSymbol("Sk");
        Mn = empty.getSymbol("Mn");
        Lo = empty.getSymbol("Lo");
        No = empty.getSymbol("No");
        Po = empty.getSymbol("Po");
        So = empty.getSymbol("So");
        Zp = empty.getSymbol("Zp");
        Co = empty.getSymbol("Co");
        Zs = empty.getSymbol("Zs");
        Ps = empty.getSymbol("Ps");
        Cs = empty.getSymbol("Cs");
        Lt = empty.getSymbol("Lt");
        Cn = empty.getSymbol("Cn");
        Lu = empty.getSymbol("Lu");
    }
}

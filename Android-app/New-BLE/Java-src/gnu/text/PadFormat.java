package gnu.text;

import gnu.kawa.servlet.HttpRequestContext;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;

public class PadFormat extends ReportFormat {
    Format fmt;
    int minWidth;
    char padChar;
    int where;

    public PadFormat(Format fmt, int minWidth, char padChar, int where) {
        this.fmt = fmt;
        this.minWidth = minWidth;
        this.padChar = padChar;
        this.where = where;
    }

    public PadFormat(Format fmt, int minWidth) {
        this(fmt, minWidth, ' ', 100);
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        return format(this.fmt, args, start, dst, this.padChar, this.minWidth, 1, 0, this.where, fpos);
    }

    public static int padNeeded(int actualWidth, int minWidth, int colInc, int minPad) {
        int total = actualWidth + minPad;
        if (colInc <= 1) {
            colInc = minWidth - total;
        }
        while (total < minWidth) {
            total += colInc;
        }
        return total - actualWidth;
    }

    public static int format(Format fmt, Object[] args, int start, Writer dst, char padChar, int minWidth, int colInc, int minPad, int where, FieldPosition fpos) throws IOException {
        StringBuffer tbuf = new StringBuffer(HttpRequestContext.HTTP_OK);
        if (fmt instanceof ReportFormat) {
            start = ((ReportFormat) fmt).format(args, start, tbuf, fpos);
        } else if (fmt instanceof MessageFormat) {
            fmt.format(args, tbuf, fpos);
            start = args.length;
        } else {
            fmt.format(args[start], tbuf, fpos);
            start++;
        }
        int len = tbuf.length();
        int pad = padNeeded(len, minWidth, colInc, minPad);
        int prefix = 0;
        String text = tbuf.toString();
        if (pad > 0) {
            if (where == -1) {
                if (len > 0) {
                    char ch = text.charAt(0);
                    if (ch == '-' || ch == '+') {
                        prefix = 0 + 1;
                        dst.write(ch);
                    }
                    if (len - prefix > 2 && text.charAt(prefix) == '0') {
                        dst.write(48);
                        prefix++;
                        ch = text.charAt(prefix);
                        if (ch == 'x' || ch == 'X') {
                            prefix++;
                            dst.write(ch);
                        }
                    }
                    if (prefix > 0) {
                        text = text.substring(prefix);
                    }
                }
                where = 0;
            }
            int padAfter = (pad * where) / 100;
            int padBefore = pad - padAfter;
            while (true) {
                padBefore--;
                if (padBefore < 0) {
                    break;
                }
                dst.write(padChar);
            }
            dst.write(text);
            while (true) {
                padAfter--;
                if (padAfter < 0) {
                    break;
                }
                dst.write(padChar);
            }
        } else {
            dst.write(text);
        }
        return start;
    }
}

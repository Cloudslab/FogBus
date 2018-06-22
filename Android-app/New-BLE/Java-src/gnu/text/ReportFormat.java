package gnu.text;

import gnu.lists.Consumer;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.ParsePosition;

public abstract class ReportFormat extends Format {
    public static final int PARAM_FROM_COUNT = -1342177280;
    public static final int PARAM_FROM_LIST = -1610612736;
    public static final int PARAM_UNSPECIFIED = -1073741824;

    public abstract int format(Object[] objArr, int i, Writer writer, FieldPosition fieldPosition) throws IOException;

    public static int result(int resultCode, int nextArg) {
        return (resultCode << 24) | nextArg;
    }

    public static int nextArg(int result) {
        return 16777215 & result;
    }

    public static int resultCode(int result) {
        return result >>> 24;
    }

    public int format(Object arg, int start, Writer dst, FieldPosition fpos) throws IOException {
        if (arg instanceof Object[]) {
            return format((Object[]) arg, start, dst, fpos);
        }
        return format(new Object[]{arg}, start, dst, fpos);
    }

    public StringBuffer format(Object obj, StringBuffer sbuf, FieldPosition fpos) {
        format((Object[]) obj, 0, sbuf, fpos);
        return sbuf;
    }

    public int format(Object[] args, int start, StringBuffer sbuf, FieldPosition fpos) {
        Writer wr = new CharArrayWriter();
        try {
            start = format(args, start, wr, fpos);
            if (start >= 0) {
                sbuf.append(wr.toCharArray());
            }
            return start;
        } catch (IOException ex) {
            throw new Error("unexpected exception: " + ex);
        }
    }

    public static int format(Format fmt, Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        if (fmt instanceof ReportFormat) {
            return ((ReportFormat) fmt).format(args, start, dst, fpos);
        }
        StringBuffer sbuf = new StringBuffer();
        if (fmt instanceof MessageFormat) {
            start = format(fmt, args, start, sbuf, fpos);
        } else {
            int start2 = start + 1;
            fmt.format(args[start], sbuf, fpos);
            start = start2;
        }
        int slen = sbuf.length();
        char[] cbuf = new char[slen];
        sbuf.getChars(0, slen, cbuf, 0);
        dst.write(cbuf);
        return start;
    }

    public static int format(Format fmt, Object[] args, int start, StringBuffer sbuf, FieldPosition fpos) {
        if (fmt instanceof ReportFormat) {
            return ((ReportFormat) fmt).format(args, start, sbuf, fpos);
        }
        int nargs;
        Object obj;
        if (fmt instanceof MessageFormat) {
            nargs = args.length - start;
            if (start > 0) {
                Object subarr = new Object[(args.length - start)];
                System.arraycopy(args, start, subarr, 0, subarr.length);
                obj = subarr;
            } else {
                obj = args;
            }
        } else {
            obj = args[start];
            nargs = 1;
        }
        fmt.format(obj, sbuf, fpos);
        return start + nargs;
    }

    public static void print(Writer dst, String str) throws IOException {
        if (dst instanceof PrintWriter) {
            ((PrintWriter) dst).print(str);
        } else {
            dst.write(str.toCharArray());
        }
    }

    public static void print(Object value, Consumer out) {
        if (value instanceof Printable) {
            ((Printable) value).print(out);
        } else {
            out.write(value == null ? "null" : value.toString());
        }
    }

    public Object parseObject(String text, ParsePosition status) {
        throw new Error("ReportFormat.parseObject - not implemented");
    }

    public static int getParam(Object arg, int defaultValue) {
        if (arg instanceof Number) {
            return ((Number) arg).intValue();
        }
        if (arg instanceof Character) {
            return ((Character) arg).charValue();
        }
        if (arg instanceof Char) {
            return ((Char) arg).charValue();
        }
        return defaultValue;
    }

    protected static int getParam(int param, int defaultValue, Object[] args, int start) {
        if (param == PARAM_FROM_COUNT) {
            return args.length - start;
        }
        if (param == -1610612736) {
            if (args != null) {
                return getParam(args[start], defaultValue);
            }
            return defaultValue;
        } else if (param != -1073741824) {
            return param;
        } else {
            return defaultValue;
        }
    }

    protected static char getParam(int param, char defaultValue, Object[] args, int start) {
        return (char) getParam(param, (int) defaultValue, args, start);
    }
}

package gnu.kawa.functions;

import gnu.math.DFloNum;
import gnu.math.IntNum;
import gnu.math.Numeric;
import gnu.text.Char;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat */
class LispEscapeFormat extends ReportFormat {
    public static final int ESCAPE_ALL = 242;
    public static final int ESCAPE_NORMAL = 241;
    public static final LispEscapeFormat alwaysTerminate = new LispEscapeFormat(0, -1073741824);
    boolean escapeAll;
    int param1;
    int param2;
    int param3;

    public LispEscapeFormat(int param1, int param2) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = -1073741824;
    }

    public LispEscapeFormat(int param1, int param2, int param3) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    static Numeric getParam(int param, Object[] args, int start) {
        if (param == ReportFormat.PARAM_FROM_COUNT) {
            return IntNum.make(args.length - start);
        }
        if (param != -1610612736) {
            return IntNum.make(param);
        }
        Object arg = args[start];
        if (arg instanceof Numeric) {
            return (Numeric) arg;
        }
        if (arg instanceof Number) {
            if ((arg instanceof Float) || (arg instanceof Double)) {
                return new DFloNum(((Number) arg).doubleValue());
            }
            return IntNum.make(((Number) arg).longValue());
        } else if (arg instanceof Char) {
            return new IntNum(((Char) arg).intValue());
        } else {
            if (arg instanceof Character) {
                return new IntNum(((Character) arg).charValue());
            }
            return new DFloNum(Double.NaN);
        }
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        boolean do_terminate = true;
        int i = 0;
        int orig_start = start;
        if (this.param1 == -1073741824) {
            if (start != args.length) {
                do_terminate = false;
            }
        } else if (this.param2 == -1073741824 && this.param1 == 0) {
            do_terminate = true;
        } else {
            Numeric arg1 = getParam(this.param1, args, start);
            if (this.param1 == -1610612736) {
                start++;
            }
            if (this.param2 == -1073741824) {
                do_terminate = arg1.isZero();
            } else {
                Numeric arg2 = getParam(this.param2, args, start);
                if (this.param2 == -1610612736) {
                    start++;
                }
                if (this.param3 == -1073741824) {
                    do_terminate = arg1.equals(arg2);
                } else {
                    Numeric arg3 = getParam(this.param3, args, start);
                    if (this.param3 == -1610612736) {
                        start++;
                    }
                    if (!(arg2.geq(arg1) && arg3.geq(arg2))) {
                        do_terminate = false;
                    }
                }
            }
        }
        if (do_terminate) {
            i = this.escapeAll ? ESCAPE_ALL : ESCAPE_NORMAL;
        }
        return ReportFormat.result(i, start);
    }
}

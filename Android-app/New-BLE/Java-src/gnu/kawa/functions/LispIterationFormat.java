package gnu.kawa.functions;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;

/* compiled from: LispFormat */
class LispIterationFormat extends ReportFormat {
    boolean atLeastOnce;
    Format body;
    int maxIterations;
    boolean seenAt;
    boolean seenColon;

    LispIterationFormat() {
    }

    public static int format(Format body, int maxIterations, Object[] args, int start, Writer dst, boolean seenColon, boolean atLeastOnce) throws IOException {
        int i = 0;
        while (true) {
            if (i == maxIterations && maxIterations != -1) {
                return start;
            }
            if (start == args.length && (i > 0 || !atLeastOnce)) {
                return start;
            }
            if (seenColon) {
                Object[] curArr = LispFormat.asArray(args[start]);
                start = curArr == null ? start + 1 : start + 1;
                if (ReportFormat.resultCode(ReportFormat.format(body, curArr, 0, dst, null)) == LispEscapeFormat.ESCAPE_ALL) {
                    return start;
                }
            } else {
                start = ReportFormat.format(body, args, start, dst, null);
                if (start < 0) {
                    return ReportFormat.nextArg(start);
                }
            }
            i++;
        }
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int maxIterations = ReportFormat.getParam(this.maxIterations, -1, args, start);
        if (this.maxIterations == -1610612736) {
            start++;
        }
        Format body = this.body;
        if (body == null) {
            int start2 = start + 1;
            Format arg = args[start];
            if (arg instanceof Format) {
                body = arg;
                start = start2;
            } else {
                try {
                    body = new LispFormat(arg.toString());
                    start = start2;
                } catch (Exception e) {
                    ReportFormat.print(dst, "<invalid argument for \"~{~}\" format>");
                    start = start2;
                    return args.length;
                }
            }
        }
        if (this.seenAt) {
            return format(body, maxIterations, args, start, dst, this.seenColon, this.atLeastOnce);
        }
        Object arg2 = args[start];
        Object[] curArgs = LispFormat.asArray(arg2);
        if (curArgs == null) {
            dst.write("{" + arg2 + "}".toString());
        } else {
            format(body, maxIterations, curArgs, 0, dst, this.seenColon, this.atLeastOnce);
        }
        return start + 1;
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("LispIterationFormat[");
        sbuf.append(this.body);
        sbuf.append("]");
        return sbuf.toString();
    }
}

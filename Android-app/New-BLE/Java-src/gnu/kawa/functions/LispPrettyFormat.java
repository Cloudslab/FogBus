package gnu.kawa.functions;

import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;

/* compiled from: LispFormat */
class LispPrettyFormat extends ReportFormat {
    Format body;
    boolean perLine;
    String prefix;
    boolean seenAt;
    Format[] segments;
    String suffix;

    LispPrettyFormat() {
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        String pre = this.prefix;
        String suf = this.suffix;
        OutPort out = dst instanceof OutPort ? (OutPort) dst : null;
        try {
            if (this.seenAt) {
                if (out != null) {
                    out.startLogicalBlock(pre, this.perLine, this.suffix);
                }
                start = ReportFormat.format(this.body, args, start, dst, fpos);
            } else {
                Object curArg = args[start];
                Object[] curArr = LispFormat.asArray(curArg);
                if (curArr == null) {
                    suf = "";
                    pre = suf;
                }
                if (out != null) {
                    out.startLogicalBlock(pre, this.perLine, this.suffix);
                }
                if (curArr == null) {
                    ObjectFormat.format(curArg, dst, -1, true);
                } else {
                    ReportFormat.format(this.body, curArr, 0, dst, fpos);
                }
                start++;
            }
            if (out != null) {
                out.endLogicalBlock(suf);
            }
            return start;
        } catch (Throwable th) {
            if (out != null) {
                out.endLogicalBlock(suf);
            }
        }
    }

    public String toString() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("LispPrettyFormat[");
        sbuf.append("prefix: \"");
        sbuf.append(this.prefix);
        sbuf.append("\", suffix: \"");
        sbuf.append(this.suffix);
        sbuf.append("\", body: ");
        sbuf.append(this.body);
        sbuf.append("]");
        return sbuf.toString();
    }
}

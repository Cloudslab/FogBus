package gnu.kawa.functions;

import gnu.bytecode.Access;
import gnu.math.ExponentialFormat;
import gnu.math.FixedRealFormat;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;

/* compiled from: LispFormat */
class LispRealFormat extends ReportFormat {
    int arg1;
    int arg2;
    int arg3;
    int arg4;
    int arg5;
    int arg6;
    int arg7;
    int argsUsed;
    boolean internalPad;
    char op;
    boolean showPlus;

    LispRealFormat() {
        int i = (this.arg1 == ReportFormat.PARAM_FROM_COUNT || this.arg2 == ReportFormat.PARAM_FROM_COUNT || this.arg3 == ReportFormat.PARAM_FROM_COUNT || this.arg4 == ReportFormat.PARAM_FROM_COUNT || this.arg5 == ReportFormat.PARAM_FROM_COUNT || this.arg6 == ReportFormat.PARAM_FROM_COUNT || this.arg7 == ReportFormat.PARAM_FROM_COUNT) ? 1 : 0;
        this.argsUsed = i;
        if (this.arg1 == -1610612736) {
            this.argsUsed += 2;
        }
        if (this.arg2 == -1610612736) {
            this.argsUsed += 2;
        }
        if (this.arg3 == -1610612736) {
            this.argsUsed += 2;
        }
        if (this.arg4 == -1610612736) {
            this.argsUsed += 2;
        }
        if (this.arg5 == -1610612736) {
            this.argsUsed += 2;
        }
        if (this.arg6 == -1610612736) {
            this.argsUsed += 2;
        }
        if (this.arg7 == -1610612736) {
            this.argsUsed += 2;
        }
    }

    public Format resolve(Object[] args, int start) {
        FixedRealFormat mfmt;
        int decimals;
        int width;
        char padChar;
        if (this.op == '$') {
            mfmt = new FixedRealFormat();
            decimals = ReportFormat.getParam(this.arg1, 2, args, start);
            if (this.arg1 == -1610612736) {
                start++;
            }
            int digits = ReportFormat.getParam(this.arg2, 1, args, start);
            if (this.arg2 == -1610612736) {
                start++;
            }
            width = ReportFormat.getParam(this.arg3, 0, args, start);
            if (this.arg3 == -1610612736) {
                start++;
            }
            padChar = ReportFormat.getParam(this.arg4, ' ', args, start);
            if (this.arg4 == -1610612736) {
                start++;
            }
            mfmt.setMaximumFractionDigits(decimals);
            mfmt.setMinimumIntegerDigits(digits);
            mfmt.width = width;
            mfmt.padChar = padChar;
            mfmt.internalPad = this.internalPad;
            mfmt.showPlus = this.showPlus;
            return mfmt;
        } else if (this.op == Access.FIELD_CONTEXT) {
            mfmt = new FixedRealFormat();
            width = ReportFormat.getParam(this.arg1, 0, args, start);
            if (this.arg1 == -1610612736) {
                start++;
            }
            decimals = ReportFormat.getParam(this.arg2, -1, args, start);
            if (this.arg2 == -1610612736) {
                start++;
            }
            int scale = ReportFormat.getParam(this.arg3, 0, args, start);
            if (this.arg3 == -1610612736) {
                start++;
            }
            mfmt.overflowChar = ReportFormat.getParam(this.arg4, '\u0000', args, start);
            if (this.arg4 == -1610612736) {
                start++;
            }
            padChar = ReportFormat.getParam(this.arg5, ' ', args, start);
            if (this.arg5 == -1610612736) {
                start++;
            }
            mfmt.setMaximumFractionDigits(decimals);
            mfmt.setMinimumIntegerDigits(0);
            mfmt.width = width;
            mfmt.scale = scale;
            mfmt.padChar = padChar;
            mfmt.internalPad = this.internalPad;
            mfmt.showPlus = this.showPlus;
            return mfmt;
        } else {
            Format efmt = new ExponentialFormat();
            efmt.exponentShowSign = true;
            efmt.width = ReportFormat.getParam(this.arg1, 0, args, start);
            if (this.arg1 == -1610612736) {
                start++;
            }
            efmt.fracDigits = ReportFormat.getParam(this.arg2, -1, args, start);
            if (this.arg2 == -1610612736) {
                start++;
            }
            efmt.expDigits = ReportFormat.getParam(this.arg3, 0, args, start);
            if (this.arg3 == -1610612736) {
                start++;
            }
            efmt.intDigits = ReportFormat.getParam(this.arg4, 1, args, start);
            if (this.arg4 == -1610612736) {
                start++;
            }
            efmt.overflowChar = ReportFormat.getParam(this.arg5, '\u0000', args, start);
            if (this.arg5 == -1610612736) {
                start++;
            }
            efmt.padChar = ReportFormat.getParam(this.arg6, ' ', args, start);
            if (this.arg6 == -1610612736) {
                start++;
            }
            efmt.exponentChar = ReportFormat.getParam(this.arg7, 'E', args, start);
            if (this.arg7 == -1610612736) {
                start++;
            }
            efmt.general = this.op == 'G';
            efmt.showPlus = this.showPlus;
            return efmt;
        }
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        StringBuffer sbuf = new StringBuffer(100);
        Format fmt = resolve(args, start);
        start += this.argsUsed >> 1;
        int start2 = start + 1;
        fmt.format(args[start], sbuf, fpos);
        dst.write(sbuf.toString());
        return start2;
    }
}

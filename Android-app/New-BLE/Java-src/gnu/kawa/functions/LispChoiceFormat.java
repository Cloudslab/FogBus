package gnu.kawa.functions;

import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;

/* compiled from: LispFormat */
class LispChoiceFormat extends ReportFormat {
    Format[] choices;
    boolean lastIsDefault;
    int param;
    boolean skipIfFalse;
    boolean testBoolean;

    LispChoiceFormat() {
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        Format fmt;
        int i = 0;
        if (this.testBoolean) {
            Format[] formatArr = this.choices;
            if (args[start] != Boolean.FALSE) {
                i = 1;
            }
            fmt = formatArr[i];
            start++;
        } else if (!this.skipIfFalse) {
            int index = ReportFormat.getParam(this.param, -1610612736, args, start);
            if (this.param == -1610612736) {
                start++;
            }
            if (index < 0 || index >= this.choices.length) {
                if (!this.lastIsDefault) {
                    return start;
                }
                index = this.choices.length - 1;
            }
            fmt = this.choices[index];
        } else if (args[start] == Boolean.FALSE) {
            return start + 1;
        } else {
            fmt = this.choices[0];
        }
        return ReportFormat.format(fmt, args, start, dst, fpos);
    }
}

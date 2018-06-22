package gnu.kawa.functions;

import gnu.text.PadFormat;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat */
class LispObjectFormat extends ReportFormat {
    ReportFormat base;
    int colInc;
    int minPad;
    int minWidth;
    int padChar;
    int where;

    public LispObjectFormat(ReportFormat base, int minWidth, int colInc, int minPad, int padChar, int where) {
        this.base = base;
        this.minWidth = minWidth;
        this.colInc = colInc;
        this.minPad = minPad;
        this.padChar = padChar;
        this.where = where;
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int minWidth = ReportFormat.getParam(this.minWidth, 0, args, start);
        if (this.minWidth == -1610612736) {
            start++;
        }
        int colInc = ReportFormat.getParam(this.colInc, 1, args, start);
        if (this.colInc == -1610612736) {
            start++;
        }
        int minPad = ReportFormat.getParam(this.minPad, 0, args, start);
        if (this.minPad == -1610612736) {
            start++;
        }
        char padChar = ReportFormat.getParam(this.padChar, ' ', args, start);
        if (this.padChar == -1610612736) {
            start++;
        }
        return PadFormat.format(this.base, args, start, dst, padChar, minWidth, colInc, minPad, this.where, fpos);
    }
}

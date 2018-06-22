package gnu.kawa.functions;

import android.support.v4.media.TransportMediator;
import gnu.text.Char;
import gnu.text.ReportFormat;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;

/* compiled from: LispFormat */
class LispCharacterFormat extends ReportFormat {
    int charVal;
    int count;
    boolean seenAt;
    boolean seenColon;

    LispCharacterFormat() {
    }

    public static LispCharacterFormat getInstance(int charVal, int count, boolean seenAt, boolean seenColon) {
        LispCharacterFormat fmt = new LispCharacterFormat();
        fmt.count = count;
        fmt.charVal = charVal;
        fmt.seenAt = seenAt;
        fmt.seenColon = seenColon;
        return fmt;
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int count = ReportFormat.getParam(this.count, 1, args, start);
        if (this.count == -1610612736) {
            start++;
        }
        int charVal = ReportFormat.getParam(this.charVal, '?', args, start);
        if (this.charVal == -1610612736) {
            start++;
        }
        while (true) {
            count--;
            if (count < 0) {
                return start;
            }
            printChar(charVal, this.seenAt, this.seenColon, dst);
        }
    }

    public static void printChar(int ch, boolean seenAt, boolean seenColon, Writer dst) throws IOException {
        if (seenAt) {
            ReportFormat.print(dst, Char.toScmReadableString(ch));
        } else if (!seenColon) {
            dst.write(ch);
        } else if (ch < 32) {
            dst.write(94);
            dst.write(ch + 64);
        } else if (ch >= TransportMediator.KEYCODE_MEDIA_PAUSE) {
            ReportFormat.print(dst, "#\\x");
            ReportFormat.print(dst, Integer.toString(ch, 16));
        } else {
            dst.write(ch);
        }
    }
}

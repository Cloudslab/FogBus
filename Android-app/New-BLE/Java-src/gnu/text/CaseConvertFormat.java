package gnu.text;

import gnu.bytecode.Access;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.Format;

public class CaseConvertFormat extends ReportFormat {
    Format baseFormat;
    char code;

    public CaseConvertFormat(Format baseFormat, char action) {
        this.baseFormat = baseFormat;
        this.code = action;
    }

    public Format getBaseFormat() {
        return this.baseFormat;
    }

    public void setBaseFormat(Format baseFormat) {
        this.baseFormat = baseFormat;
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        StringBuffer sbuf = new StringBuffer(100);
        int result = ReportFormat.format(this.baseFormat, args, start, sbuf, fpos);
        int len = sbuf.length();
        char prev = ' ';
        int i = 0;
        while (i < len) {
            char ch = sbuf.charAt(i);
            if (this.code == 'U') {
                ch = Character.toUpperCase(ch);
            } else if (!(this.code == 'T' && i == 0) && (this.code != Access.CLASS_CONTEXT || Character.isLetterOrDigit(prev))) {
                ch = Character.toLowerCase(ch);
            } else {
                ch = Character.toTitleCase(ch);
            }
            prev = ch;
            dst.write(ch);
            i++;
        }
        return result;
    }
}

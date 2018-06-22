package gnu.kawa.functions;

import gnu.lists.AbstractFormat;
import gnu.lists.Consumer;
import gnu.mapping.OutPort;
import gnu.text.ReportFormat;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.FieldPosition;
import java.text.ParsePosition;
import kawa.standard.Scheme;

public class ObjectFormat extends ReportFormat {
    private static ObjectFormat plainFormat;
    private static ObjectFormat readableFormat;
    int maxChars;
    boolean readable;

    public static ObjectFormat getInstance(boolean readable) {
        if (readable) {
            if (readableFormat == null) {
                readableFormat = new ObjectFormat(true);
            }
            return readableFormat;
        }
        if (plainFormat == null) {
            plainFormat = new ObjectFormat(false);
        }
        return plainFormat;
    }

    public ObjectFormat(boolean readable) {
        this.readable = readable;
        this.maxChars = -1073741824;
    }

    public ObjectFormat(boolean readable, int maxChars) {
        this.readable = readable;
        this.maxChars = maxChars;
    }

    public int format(Object[] args, int start, Writer dst, FieldPosition fpos) throws IOException {
        int maxChars = ReportFormat.getParam(this.maxChars, -1, args, start);
        if (this.maxChars == -1610612736) {
            start++;
        }
        return format(args, start, dst, maxChars, this.readable);
    }

    private static void print(Object obj, OutPort out, boolean readable) {
        boolean saveReadable = out.printReadable;
        AbstractFormat saveFormat = out.objectFormat;
        try {
            out.printReadable = readable;
            AbstractFormat format = readable ? Scheme.writeFormat : Scheme.displayFormat;
            out.objectFormat = format;
            format.writeObject(obj, (Consumer) out);
        } finally {
            out.printReadable = saveReadable;
            out.objectFormat = saveFormat;
        }
    }

    public static boolean format(Object arg, Writer dst, int maxChars, boolean readable) throws IOException {
        if (maxChars < 0 && (dst instanceof OutPort)) {
            print(arg, (OutPort) dst, readable);
            return true;
        } else if (maxChars >= 0 || !(dst instanceof CharArrayWriter)) {
            Writer wr = new CharArrayWriter();
            oport = new OutPort(wr);
            print(arg, oport, readable);
            oport.close();
            int len = wr.size();
            if (maxChars < 0 || len <= maxChars) {
                wr.writeTo(dst);
                return true;
            }
            dst.write(wr.toCharArray(), 0, maxChars);
            return false;
        } else {
            oport = new OutPort(dst);
            print(arg, oport, readable);
            oport.close();
            return true;
        }
    }

    public static int format(Object[] args, int start, Writer dst, int maxChars, boolean readable) throws IOException {
        Object arg;
        if (start >= args.length) {
            arg = "#<missing format argument>";
            start--;
            readable = false;
            maxChars = -1;
        } else {
            arg = args[start];
        }
        format(arg, dst, maxChars, readable);
        return start + 1;
    }

    public Object parseObject(String text, ParsePosition status) {
        throw new RuntimeException("ObjectFormat.parseObject - not implemented");
    }
}

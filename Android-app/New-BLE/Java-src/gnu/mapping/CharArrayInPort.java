package gnu.mapping;

import gnu.lists.CharSeq;
import gnu.lists.FString;
import gnu.text.NullReader;
import gnu.text.Path;
import java.io.IOException;

public class CharArrayInPort extends InPort {
    static final Path stringPath = Path.valueOf("<string>");

    public CharArrayInPort make(CharSequence seq) {
        if (seq instanceof FString) {
            FString fstr = (FString) seq;
            return new CharArrayInPort(fstr.data, fstr.size);
        }
        int len = seq.length();
        char[] buf = new char[len];
        if (seq instanceof String) {
            ((String) seq).getChars(0, len, buf, 0);
        } else if (!(seq instanceof CharSeq)) {
            int i = len;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                buf[i] = seq.charAt(i);
            }
        } else {
            ((CharSeq) seq).getChars(0, len, buf, 0);
        }
        return new CharArrayInPort(buf, len);
    }

    public CharArrayInPort(char[] buffer, int len) {
        super(NullReader.nullReader, stringPath);
        try {
            setBuffer(buffer);
            this.limit = len;
        } catch (IOException ex) {
            throw new Error(ex.toString());
        }
    }

    public CharArrayInPort(char[] buffer) {
        this(buffer, buffer.length);
    }

    public CharArrayInPort(String string) {
        this(string.toCharArray());
    }

    public int read() throws IOException {
        if (this.pos >= this.limit) {
            return -1;
        }
        return super.read();
    }
}

package gnu.lists;

import java.io.Writer;

public class ConsumerWriter extends Writer {
    protected Consumer out;

    public ConsumerWriter(Consumer out) {
        this.out = out;
    }

    public void write(char[] buffer, int offset, int length) {
        this.out.write(buffer, offset, length);
    }

    public void flush() {
    }

    public void close() {
        flush();
    }

    public void finalize() {
        close();
    }
}

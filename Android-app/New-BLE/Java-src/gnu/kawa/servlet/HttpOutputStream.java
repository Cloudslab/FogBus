package gnu.kawa.servlet;

import java.io.IOException;
import java.io.OutputStream;

/* compiled from: ServletPrinter */
class HttpOutputStream extends OutputStream {
    byte[] buffer;
    HttpRequestContext context;
    int count;
    OutputStream out;

    public HttpOutputStream(HttpRequestContext context, int bufSize) {
        this.context = context;
        this.buffer = new byte[bufSize];
    }

    public void write(int b) throws IOException {
        if (this.count >= this.buffer.length) {
            flush();
        }
        byte[] bArr = this.buffer;
        int i = this.count;
        this.count = i + 1;
        bArr[i] = (byte) b;
    }

    public void write(byte[] data, int offset, int length) throws IOException {
        int avail = this.buffer.length - this.count;
        while (length > avail) {
            System.arraycopy(data, offset, this.buffer, this.count, avail);
            this.count += avail;
            flush();
            offset += avail;
            length -= avail;
            avail = this.buffer.length;
        }
        if (length > 0) {
            System.arraycopy(data, offset, this.buffer, this.count, length);
            this.count += length;
        }
    }

    public boolean reset() {
        this.count = 0;
        if (this.out == null) {
            return true;
        }
        return false;
    }

    public void flush() throws IOException {
        if (this.out == null) {
            maybeSendResponseHeaders(-1);
            this.out = this.context.getResponseStream();
        }
        if (this.count > 0) {
            this.out.write(this.buffer, 0, this.count);
            this.count = 0;
        }
    }

    void maybeSendResponseHeaders(int count) throws IOException {
        int statusCode = this.context.statusCode;
        if (statusCode != -999) {
            this.context.sendResponseHeaders(statusCode, this.context.statusReasonPhrase, (long) count);
            this.context.statusCode = -999;
        }
    }

    public void close() throws IOException {
        if (this.out == null) {
            maybeSendResponseHeaders(this.count);
            this.out = this.context.getResponseStream();
        }
        flush();
        this.out.close();
    }
}

package gnu.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

public class LineInputStreamReader extends LineBufferedReader {
    byte[] barr = new byte[8192];
    ByteBuffer bbuf = ByteBuffer.wrap(this.barr);
    char[] carr;
    CharBuffer cbuf = null;
    Charset cset;
    CharsetDecoder decoder;
    InputStream istrm;

    public void setCharset(Charset cset) {
        this.cset = cset;
        this.decoder = cset.newDecoder();
    }

    public void setCharset(String name) {
        Charset cset = Charset.forName(name);
        if (this.cset == null) {
            setCharset(cset);
        } else if (!cset.equals(this.cset)) {
            throw new RuntimeException("encoding " + name + " does not match previous " + this.cset);
        }
    }

    public LineInputStreamReader(InputStream in) {
        super((Reader) null);
        this.bbuf.position(this.barr.length);
        this.istrm = in;
    }

    public void close() throws IOException {
        if (this.in != null) {
            this.in.close();
        }
        this.istrm.close();
    }

    private int fillBytes(int remaining) throws IOException {
        int i = 0;
        int n = this.istrm.read(this.barr, remaining, this.barr.length - remaining);
        this.bbuf.position(0);
        ByteBuffer byteBuffer = this.bbuf;
        if (n >= 0) {
            i = n;
        }
        byteBuffer.limit(i + remaining);
        return n;
    }

    public void markStart() throws IOException {
    }

    public void resetStart(int pos) throws IOException {
        this.bbuf.position(pos);
    }

    public int getByte() throws IOException {
        if (this.bbuf.hasRemaining() || fillBytes(0) > 0) {
            return this.bbuf.get() & 255;
        }
        return -1;
    }

    public int fill(int len) throws IOException {
        int count;
        if (this.cset == null) {
            setCharset("UTF-8");
        }
        if (this.buffer != this.carr) {
            this.cbuf = CharBuffer.wrap(this.buffer);
            this.carr = this.buffer;
        }
        this.cbuf.limit(this.pos + len);
        this.cbuf.position(this.pos);
        boolean eof = false;
        int rem;
        do {
            CoderResult cres = this.decoder.decode(this.bbuf, this.cbuf, false);
            count = this.cbuf.position() - this.pos;
            if (count > 0 || !cres.isUnderflow()) {
                break;
            }
            rem = this.bbuf.remaining();
            if (rem > 0) {
                this.bbuf.compact();
            }
        } while (fillBytes(rem) >= 0);
        eof = true;
        return (count == 0 && eof) ? -1 : count;
    }

    public boolean ready() throws IOException {
        return this.pos < this.limit || this.bbuf.hasRemaining() || this.istrm.available() > 0;
    }
}

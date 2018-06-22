package kawa;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TelnetInputStream extends FilterInputStream {
    static final int SB_IAC = 400;
    protected byte[] buf = new byte[512];
    Telnet connection;
    int count;
    int pos;
    int state = 0;
    int subCommandLength = 0;

    public TelnetInputStream(InputStream in, Telnet conn) throws IOException {
        super(in);
        this.connection = conn;
    }

    public int read() throws IOException {
        while (true) {
            if (this.pos >= this.count) {
                int avail = this.in.available();
                if (avail <= 0) {
                    avail = 1;
                } else if (avail > this.buf.length - this.subCommandLength) {
                    avail = this.buf.length - this.subCommandLength;
                }
                avail = this.in.read(this.buf, this.subCommandLength, avail);
                this.pos = this.subCommandLength;
                this.count = avail;
                if (avail <= 0) {
                    return -1;
                }
            }
            byte[] bArr = this.buf;
            int i = this.pos;
            this.pos = i + 1;
            int ch = bArr[i] & 255;
            if (this.state == 0) {
                if (ch != 255) {
                    return ch;
                }
                this.state = 255;
            } else if (this.state == 255) {
                if (ch == 255) {
                    this.state = 0;
                    return 255;
                } else if (ch == Telnet.WILL || ch == Telnet.WONT || ch == Telnet.DO || ch == Telnet.DONT || ch == 250) {
                    this.state = ch;
                } else if (ch == 244) {
                    System.err.println("Interrupt Process");
                    this.state = 0;
                } else if (ch == 236) {
                    return -1;
                } else {
                    this.state = 0;
                }
            } else if (this.state == Telnet.WILL || this.state == Telnet.WONT || this.state == Telnet.DO || this.state == Telnet.DONT) {
                this.connection.handle(this.state, ch);
                this.state = 0;
            } else if (this.state == 250) {
                if (ch == 255) {
                    this.state = SB_IAC;
                } else {
                    bArr = this.buf;
                    i = this.subCommandLength;
                    this.subCommandLength = i + 1;
                    bArr[i] = (byte) ch;
                }
            } else if (this.state != SB_IAC) {
                System.err.println("Bad state " + this.state);
            } else if (ch == 255) {
                bArr = this.buf;
                i = this.subCommandLength;
                this.subCommandLength = i + 1;
                bArr[i] = (byte) ch;
                this.state = 250;
            } else if (ch == 240) {
                this.connection.subCommand(this.buf, 0, this.subCommandLength);
                this.state = 0;
                this.subCommandLength = 0;
            } else {
                this.state = 0;
                this.subCommandLength = 0;
            }
        }
    }

    public int read(byte[] b, int offset, int length) throws IOException {
        if (length <= 0) {
            return 0;
        }
        int done = 0;
        if (this.state != 0 || this.pos >= this.count) {
            int ch = read();
            if (ch < 0) {
                return ch;
            }
            int offset2 = offset + 1;
            b[offset] = (byte) ch;
            done = 0 + 1;
            offset = offset2;
        }
        if (this.state == 0) {
            while (this.pos < this.count && done < length) {
                byte ch2 = this.buf[this.pos];
                if (ch2 == (byte) -1) {
                    break;
                }
                offset2 = offset + 1;
                b[offset] = ch2;
                done++;
                this.pos++;
                offset = offset2;
            }
        }
        return done;
    }
}

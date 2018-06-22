package kawa;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Telnet implements Runnable {
    public static final int DO = 253;
    public static final int DONT = 254;
    public static final int ECHO = 1;
    static final int EOF = 236;
    static final int IAC = 255;
    static final int IP = 244;
    static final int LINEMODE = 34;
    static final int NAWS = 31;
    static final int NOP = 241;
    static final int OPTION_NO = 0;
    static final int OPTION_WANTNO = 1;
    static final int OPTION_WANTNO_OPPOSITE = 2;
    static final int OPTION_WANTYES = 3;
    static final int OPTION_WANTYES_OPPOSITE = 4;
    static final int OPTION_YES = 5;
    static final int SB = 250;
    static final int SE = 240;
    public static final int SUPPRESS_GO_AHEAD = 3;
    static final int TM = 6;
    static final int TTYPE = 24;
    public static final int WILL = 251;
    public static final int WONT = 252;
    TelnetInputStream in;
    boolean isServer;
    final byte[] optionsState = new byte[256];
    TelnetOutputStream out;
    final byte preferredLineMode = (byte) 3;
    InputStream sin;
    OutputStream sout;
    public byte[] terminalType;
    public short windowHeight;
    public short windowWidth;

    public TelnetInputStream getInputStream() {
        return this.in;
    }

    public TelnetOutputStream getOutputStream() {
        return this.out;
    }

    boolean change(int command, int option) {
        if (option == 6) {
            return true;
        }
        if (this.isServer && option == 31) {
            return true;
        }
        if (this.isServer && command == WILL && option == 34) {
            try {
                this.out.writeSubCommand(34, new byte[]{(byte) 1, (byte) 3});
                return true;
            } catch (IOException e) {
                return true;
            }
        } else if (this.isServer && command == WILL && option == 24) {
            try {
                this.out.writeSubCommand(option, new byte[]{(byte) 1});
                return true;
            } catch (IOException e2) {
                return true;
            }
        } else {
            if (!this.isServer && option == 1) {
                if (command == DO) {
                    return false;
                }
                if (command == WILL) {
                    return true;
                }
            }
            return false;
        }
    }

    public void subCommand(byte[] buf, int off, int len) {
        switch (buf[off]) {
            case 24:
                byte[] type = new byte[(len - 1)];
                System.arraycopy(buf, 1, type, 0, len - 1);
                this.terminalType = type;
                System.err.println("terminal type: '" + new String(type) + "'");
                return;
            case 31:
                if (len == 5) {
                    this.windowWidth = (short) ((buf[1] << 8) + (buf[2] & 255));
                    this.windowHeight = (short) ((buf[3] << 8) + (buf[4] & 255));
                    return;
                }
                return;
            case 34:
                System.err.println("SBCommand LINEMODE " + buf[1] + " len:" + len);
                if (buf[1] == (byte) 3) {
                    for (int i = 2; i + 2 < len; i += 3) {
                        System.err.println("  " + buf[i] + "," + buf[i + 1] + "," + buf[i + 2]);
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    void handle(int command, int option) throws IOException {
        boolean otherSide;
        boolean wantOn = true;
        int i = DONT;
        int i2 = DO;
        if (command < DO) {
            otherSide = true;
        } else {
            otherSide = false;
        }
        if ((command & 1) == 0) {
            wantOn = false;
        }
        byte state = this.optionsState[option];
        if (otherSide) {
            state = (byte) (state >> 3);
        }
        TelnetOutputStream telnetOutputStream;
        switch ((state >> 3) & 7) {
            case 0:
                if (wantOn) {
                    if (!change(command, option)) {
                        telnetOutputStream = this.out;
                        if (!otherSide) {
                            i = WONT;
                        }
                        telnetOutputStream.writeCommand(i, option);
                        break;
                    }
                    state = (byte) 5;
                    this.out.writeCommand(otherSide ? DO : WILL, option);
                    break;
                }
                return;
            case 1:
                state = (byte) 0;
                break;
            case 2:
                state = (byte) 3;
                telnetOutputStream = this.out;
                if (!otherSide) {
                    i2 = WILL;
                }
                telnetOutputStream.writeCommand(i2, option);
                break;
            case 3:
                if (!wantOn) {
                    state = (byte) 0;
                    break;
                }
                state = (byte) 5;
                change(command, option);
                break;
            case 4:
                if (!wantOn) {
                    state = (byte) 0;
                    break;
                }
                state = (byte) 1;
                telnetOutputStream = this.out;
                if (!otherSide) {
                    i = WONT;
                }
                telnetOutputStream.writeCommand(i, option);
                break;
            case 5:
                if (!wantOn) {
                    state = (byte) 0;
                    change(command, option);
                    this.out.writeCommand(otherSide ? DONT : WONT, option);
                    break;
                }
                return;
        }
        if (otherSide) {
            state = (byte) ((this.optionsState[option] & 199) | (state << 3));
        } else {
            state = (byte) ((this.optionsState[option] & 248) | state);
        }
        this.optionsState[option] = state;
    }

    public void request(int command, int option) throws IOException {
        boolean otherSide;
        boolean wantOn = true;
        if (command >= DO) {
            otherSide = true;
        } else {
            otherSide = false;
        }
        if ((command & 1) == 0) {
            wantOn = false;
        }
        byte state = this.optionsState[option];
        if (otherSide) {
            state = (byte) (state >> 3);
        }
        switch (state & 7) {
            case 0:
                if (wantOn) {
                    state = (byte) 3;
                    this.out.writeCommand(command, option);
                    break;
                }
                break;
            case 1:
                if (wantOn) {
                    state = (byte) 2;
                    break;
                }
                break;
            case 2:
                if (!wantOn) {
                    state = (byte) 1;
                    break;
                }
                break;
            case 3:
                if (!wantOn) {
                    state = (byte) 4;
                    break;
                }
                break;
            case 4:
                break;
            case 5:
                if (!wantOn) {
                    state = (byte) 1;
                    this.out.writeCommand(command, option);
                    break;
                }
                break;
        }
        if (wantOn) {
            state = (byte) 3;
        }
        if (otherSide) {
            state = (byte) ((this.optionsState[option] & 199) | (state << 3));
        } else {
            state = (byte) ((this.optionsState[option] & 248) | state);
        }
        this.optionsState[option] = state;
    }

    static void usage() {
        System.err.println("Usage:  [java] kawa.Telnet HOST [PORT#]");
        System.exit(-1);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
        }
        String host = args[0];
        int port = 23;
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        try {
            Telnet telnet = new Telnet(new Socket(host, port), false);
            TelnetOutputStream tout = telnet.getOutputStream();
            Thread t = new Thread(telnet);
            t.setPriority(Thread.currentThread().getPriority() + 1);
            t.start();
            byte[] buffer = new byte[1024];
            while (true) {
                int ch = System.in.read();
                if (ch < 0) {
                    t.stop();
                    return;
                }
                buffer[0] = (byte) ch;
                int avail = System.in.available();
                if (avail > 0) {
                    if (avail > buffer.length - 1) {
                        avail = buffer.length - 1;
                    }
                    avail = System.in.read(buffer, 1, avail);
                }
                tout.write(buffer, 0, avail + 1);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public Telnet(Socket socket, boolean isServer) throws IOException {
        this.sin = socket.getInputStream();
        this.sout = socket.getOutputStream();
        this.out = new TelnetOutputStream(this.sout);
        this.in = new TelnetInputStream(this.sin, this);
        this.isServer = isServer;
    }

    public void run() {
        try {
            TelnetInputStream tin = getInputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int ch = tin.read();
                if (ch >= 0) {
                    buffer[0] = (byte) ch;
                    int avail = tin.available();
                    if (avail > 0) {
                        if (avail > buffer.length - 1) {
                            avail = buffer.length - 1;
                        }
                        avail = tin.read(buffer, 1, avail);
                    }
                    System.out.write(buffer, 0, avail + 1);
                } else {
                    return;
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(-1);
        }
    }
}

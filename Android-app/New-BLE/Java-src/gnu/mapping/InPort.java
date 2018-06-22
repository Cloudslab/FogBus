package gnu.mapping;

import gnu.lists.Consumer;
import gnu.text.LineBufferedReader;
import gnu.text.Path;
import gnu.text.Printable;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class InPort extends LineBufferedReader implements Printable {
    public static final ThreadLocation inLocation = new ThreadLocation("in-default");
    private static InPort systemInPort = new TtyInPort(System.in, Path.valueOf("/dev/stdin"), OutPort.outInitial);

    public InPort(Reader in) {
        super(in);
    }

    public InPort(Reader in, Path path) {
        this(in);
        setPath(path);
    }

    public InPort(InputStream in) {
        super(in);
    }

    public InPort(InputStream in, Path path) {
        this(in);
        setPath(path);
    }

    public static Reader convertToReader(InputStream in, Object conv) {
        if (conv == null || conv == Boolean.TRUE) {
            return new InputStreamReader(in);
        }
        String enc = conv == Boolean.FALSE ? "8859_1" : conv.toString();
        try {
            return new InputStreamReader(in, enc);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("unknown character encoding: " + enc);
        }
    }

    public InPort(InputStream in, Path path, Object conv) throws UnsupportedEncodingException {
        this(convertToReader(in, conv), path);
        if (conv == Boolean.FALSE) {
            try {
                setBuffer(new char[2048]);
                return;
            } catch (IOException e) {
                return;
            }
        }
        setConvertCR(true);
    }

    static {
        inLocation.setGlobal(systemInPort);
    }

    public static InPort inDefault() {
        return (InPort) inLocation.get();
    }

    public static void setInDefault(InPort in) {
        inLocation.set(in);
    }

    public static InPort openFile(Object fname) throws IOException {
        Path path = Path.valueOf(fname);
        return openFile(new BufferedInputStream(path.openInputStream()), path);
    }

    public static InPort openFile(InputStream strm, Object fname) throws UnsupportedEncodingException {
        return new InPort(strm, Path.valueOf(fname), Environment.user().get((Object) "port-char-encoding"));
    }

    public void print(Consumer out) {
        out.write("#<input-port");
        String name = getName();
        if (name != null) {
            out.write(32);
            out.write(name);
        }
        out.write(62);
    }
}

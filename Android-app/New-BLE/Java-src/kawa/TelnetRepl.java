package kawa;

import gnu.expr.Language;
import gnu.mapping.Environment;
import gnu.mapping.Future;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0;
import gnu.mapping.TtyInPort;
import gnu.mapping.Values;
import gnu.text.FilePath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TelnetRepl extends Procedure0 {
    Language language;
    Socket socket;

    public TelnetRepl(Language language, Socket socket) {
        this.language = language;
        this.socket = socket;
    }

    public Object apply0() {
        try {
            Shell.run(this.language, Environment.getCurrent());
            Object obj = Values.empty;
            return obj;
        } finally {
            try {
                this.socket.close();
            } catch (IOException e) {
            }
        }
    }

    public static void serve(Language language, Socket client) throws IOException {
        Telnet conn = new Telnet(client, true);
        OutputStream sout = conn.getOutputStream();
        InputStream sin = conn.getInputStream();
        OutPort out = new OutPort(sout, FilePath.valueOf("/dev/stdout"));
        new Future(new TelnetRepl(language, client), new TtyInPort(sin, FilePath.valueOf("/dev/stdin"), out), out, out).start();
    }
}

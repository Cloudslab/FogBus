package gnu.xquery.util;

import gnu.mapping.OutPort;
import gnu.mapping.WrappedException;
import gnu.xml.XMLPrinter;
import java.io.FileOutputStream;

public class Debug {
    public static String traceFilename = "XQuery-trace.log";
    public static OutPort tracePort = null;
    public static String tracePrefix = "XQuery-trace: ";
    public static boolean traceShouldAppend = false;
    public static boolean traceShouldFlush = true;

    public static synchronized Object trace(Object value, Object message) {
        synchronized (Debug.class) {
            OutPort out = tracePort;
            if (out == null) {
                try {
                    out = new OutPort(new FileOutputStream(traceFilename, traceShouldAppend));
                } catch (Throwable ex) {
                    WrappedException wrappedException = new WrappedException("Could not open '" + traceFilename + "' for fn:trace output", ex);
                }
                tracePort = out;
            }
            out.print(tracePrefix);
            out.print(message);
            out.print(' ');
            new XMLPrinter(out, false).writeObject(value);
            out.println();
            if (traceShouldFlush) {
                out.flush();
            }
        }
        return value;
    }
}

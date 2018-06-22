package gnu.kawa.xml;

import gnu.mapping.OutPort;
import gnu.mapping.Procedure2;
import gnu.mapping.Values;
import gnu.text.Path;
import gnu.xml.XMLPrinter;
import java.io.OutputStream;

public class WriteTo extends Procedure2 {
    public static final WriteTo writeTo = new WriteTo();
    public static final WriteTo writeToIfChanged = new WriteTo();
    boolean ifChanged;

    static {
        writeToIfChanged.ifChanged = true;
    }

    public static void writeTo(Object value, Path ppath, OutputStream outs) throws Throwable {
        OutPort out = new OutPort(outs, ppath);
        XMLPrinter consumer = new XMLPrinter(out, false);
        if ("html".equals(ppath.getExtension())) {
            consumer.setStyle("html");
        }
        Values.writeValues(value, consumer);
        out.close();
    }

    public static void writeTo(Object value, Object path) throws Throwable {
        Path ppath = Path.valueOf(path);
        writeTo(value, ppath, ppath.openOutputStream());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeToIfChanged(java.lang.Object r10, java.lang.Object r11) throws java.lang.Throwable {
        /*
        r8 = gnu.text.Path.valueOf(r11);
        r3 = new java.io.ByteArrayOutputStream;
        r3.<init>();
        writeTo(r10, r8, r3);
        r2 = r3.toByteArray();
        r7 = new java.io.BufferedInputStream;	 Catch:{ Throwable -> 0x0041 }
        r9 = r8.openInputStream();	 Catch:{ Throwable -> 0x0041 }
        r7.<init>(r9);	 Catch:{ Throwable -> 0x0041 }
        r5 = 0;
        r6 = r5;
    L_0x001b:
        r1 = r7.read();	 Catch:{ Throwable -> 0x0041 }
        r9 = r2.length;	 Catch:{ Throwable -> 0x0041 }
        if (r6 != r9) goto L_0x003b;
    L_0x0022:
        r0 = 1;
    L_0x0023:
        if (r1 >= 0) goto L_0x0043;
    L_0x0025:
        if (r0 != 0) goto L_0x003d;
    L_0x0027:
        r5 = r6;
    L_0x0028:
        r7.close();	 Catch:{ Throwable -> 0x0041 }
    L_0x002b:
        r4 = new java.io.BufferedOutputStream;
        r9 = r8.openOutputStream();
        r4.<init>(r9);
        r4.write(r2);
        r4.close();
    L_0x003a:
        return;
    L_0x003b:
        r0 = 0;
        goto L_0x0023;
    L_0x003d:
        r7.close();	 Catch:{ Throwable -> 0x0041 }
        goto L_0x003a;
    L_0x0041:
        r9 = move-exception;
        goto L_0x002b;
    L_0x0043:
        if (r0 != 0) goto L_0x004d;
    L_0x0045:
        r5 = r6 + 1;
        r9 = r2[r6];	 Catch:{ Throwable -> 0x0041 }
        if (r9 != r1) goto L_0x0028;
    L_0x004b:
        r6 = r5;
        goto L_0x001b;
    L_0x004d:
        r5 = r6;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.kawa.xml.WriteTo.writeToIfChanged(java.lang.Object, java.lang.Object):void");
    }

    public Object apply2(Object value, Object fileName) throws Throwable {
        if (this.ifChanged) {
            writeToIfChanged(value, fileName.toString());
        } else {
            writeTo(value, fileName.toString());
        }
        return Values.empty;
    }
}

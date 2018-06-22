package gnu.bytecode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class dump extends ClassFileInput {
    ClassTypeWriter writer;

    dump(InputStream str, ClassTypeWriter writer) throws IOException, ClassFormatError {
        super(str);
        this.ctype = new ClassType();
        readFormatVersion();
        readConstants();
        readClassInfo();
        readFields();
        readMethods();
        readAttributes(this.ctype);
        writer.print(this.ctype);
        writer.flush();
    }

    public ConstantPool readConstants() throws IOException {
        this.ctype.constants = super.readConstants();
        return this.ctype.constants;
    }

    public Attribute readAttribute(String name, int length, AttrContainer container) throws IOException {
        return super.readAttribute(name, length, container);
    }

    static int readMagic(InputStream in) throws IOException {
        int magic = 0;
        for (int j = 0; j < 4; j++) {
            int b = in.read();
            if (b < 0) {
                break;
            }
            magic = (magic << 8) | (b & 255);
        }
        return magic;
    }

    public static void process(InputStream in, String filename, OutputStream out, int flags) throws IOException {
        process(in, filename, new ClassTypeWriter(null, out, flags));
    }

    public static void process(InputStream in, String filename, Writer out, int flags) throws IOException {
        process(in, filename, new ClassTypeWriter(null, out, flags));
    }

    public static void process(InputStream in, String filename, ClassTypeWriter out) throws IOException {
        InputStream inp = new BufferedInputStream(in);
        inp.mark(5);
        int magic = readMagic(inp);
        dump gnu_bytecode_dump;
        if (magic == -889275714) {
            out.print("Reading .class from ");
            out.print(filename);
            out.println('.');
            gnu_bytecode_dump = new dump(inp, out);
        } else if (magic == 1347093252) {
            inp.reset();
            out.print("Reading classes from archive ");
            out.print(filename);
            out.println('.');
            ZipInputStream zin = new ZipInputStream(inp);
            while (true) {
                ZipEntry zent = zin.getNextEntry();
                if (zent != null) {
                    String name = zent.getName();
                    if (zent.isDirectory()) {
                        out.print("Archive directory: ");
                        out.print(name);
                        out.println('.');
                    } else {
                        out.println();
                        if (readMagic(zin) == -889275714) {
                            out.print("Reading class member: ");
                            out.print(name);
                            out.println('.');
                            gnu_bytecode_dump = new dump(zin, out);
                        } else {
                            out.print("Skipping non-class member: ");
                            out.print(name);
                            out.println('.');
                        }
                    }
                } else {
                    System.exit(-1);
                    return;
                }
            }
        } else {
            System.err.println("File " + filename + " is not a valid .class file");
            System.exit(-1);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void main(java.lang.String[] r27) {
        /*
        r0 = r27;
        r4 = r0.length;
        r19 = new gnu.bytecode.ClassTypeWriter;
        r24 = 0;
        r25 = java.lang.System.out;
        r26 = 0;
        r0 = r19;
        r1 = r24;
        r2 = r25;
        r3 = r26;
        r0.<init>(r1, r2, r3);
        if (r4 != 0) goto L_0x001d;
    L_0x0018:
        r24 = java.lang.System.err;
        usage(r24);
    L_0x001d:
        r14 = 0;
    L_0x001e:
        if (r14 >= r4) goto L_0x025b;
    L_0x0020:
        r12 = r27[r14];
        r24 = "-verbose";
        r0 = r24;
        r24 = r12.equals(r0);
        if (r24 != 0) goto L_0x0036;
    L_0x002c:
        r24 = "--verbose";
        r0 = r24;
        r24 = r12.equals(r0);
        if (r24 == 0) goto L_0x0042;
    L_0x0036:
        r24 = 15;
        r0 = r19;
        r1 = r24;
        r0.setFlags(r1);
    L_0x003f:
        r14 = r14 + 1;
        goto L_0x001e;
    L_0x0042:
        r17 = uriSchemeSpecified(r12);
        if (r17 == 0) goto L_0x01cf;
    L_0x0048:
        r24 = "jar:";
        r0 = r24;
        r16 = r12.startsWith(r0);	 Catch:{ IOException -> 0x00d6 }
        if (r16 == 0) goto L_0x00c0;
    L_0x0052:
        r24 = 4;
        r0 = r24;
        r20 = r12.substring(r0);	 Catch:{ IOException -> 0x00d6 }
        r24 = uriSchemeSpecified(r20);	 Catch:{ IOException -> 0x00d6 }
        if (r24 != 0) goto L_0x00a8;
    L_0x0060:
        r24 = 33;
        r0 = r20;
        r1 = r24;
        r11 = r0.indexOf(r1);	 Catch:{ IOException -> 0x00d6 }
        if (r11 < 0) goto L_0x00a8;
    L_0x006c:
        r24 = 0;
        r0 = r20;
        r1 = r24;
        r13 = r0.substring(r1, r11);	 Catch:{ IOException -> 0x00d6 }
        r24 = new java.io.File;	 Catch:{ IOException -> 0x00d6 }
        r0 = r24;
        r0.<init>(r13);	 Catch:{ IOException -> 0x00d6 }
        r24 = r24.toURI();	 Catch:{ IOException -> 0x00d6 }
        r24 = r24.toURL();	 Catch:{ IOException -> 0x00d6 }
        r13 = r24.toString();	 Catch:{ IOException -> 0x00d6 }
        r24 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00d6 }
        r24.<init>();	 Catch:{ IOException -> 0x00d6 }
        r25 = "jar:";
        r24 = r24.append(r25);	 Catch:{ IOException -> 0x00d6 }
        r0 = r24;
        r24 = r0.append(r13);	 Catch:{ IOException -> 0x00d6 }
        r0 = r20;
        r25 = r0.substring(r11);	 Catch:{ IOException -> 0x00d6 }
        r24 = r24.append(r25);	 Catch:{ IOException -> 0x00d6 }
        r12 = r24.toString();	 Catch:{ IOException -> 0x00d6 }
    L_0x00a8:
        r24 = "!/";
        r0 = r20;
        r1 = r24;
        r24 = r0.indexOf(r1);	 Catch:{ IOException -> 0x00d6 }
        if (r24 >= 0) goto L_0x00c0;
    L_0x00b4:
        r24 = 33;
        r0 = r24;
        r11 = r12.lastIndexOf(r0);	 Catch:{ IOException -> 0x00d6 }
        if (r11 > 0) goto L_0x00ef;
    L_0x00be:
        r16 = 0;
    L_0x00c0:
        r23 = new java.net.URL;	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r0 = r23;
        r0.<init>(r12);	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r24 = r23.openConnection();	 Catch:{ ZipException -> 0x013d, FileNotFoundException -> 0x0167 }
        r15 = r24.getInputStream();	 Catch:{ ZipException -> 0x013d, FileNotFoundException -> 0x0167 }
    L_0x00cf:
        r0 = r19;
        process(r15, r12, r0);	 Catch:{ IOException -> 0x00d6 }
        goto L_0x003f;
    L_0x00d6:
        r7 = move-exception;
        r7.printStackTrace();
        r24 = java.lang.System.err;
        r25 = "caught ";
        r24.println(r25);
        r24 = java.lang.System.err;
        r0 = r24;
        r0.print(r7);
        r24 = -1;
        java.lang.System.exit(r24);
        goto L_0x003f;
    L_0x00ef:
        r24 = 47;
        r0 = r24;
        r24 = r12.indexOf(r0, r11);	 Catch:{ IOException -> 0x00d6 }
        if (r24 >= 0) goto L_0x00c0;
    L_0x00f9:
        r24 = r11 + 1;
        r0 = r24;
        r20 = r12.substring(r0);	 Catch:{ IOException -> 0x00d6 }
        r24 = 46;
        r25 = 47;
        r0 = r20;
        r1 = r24;
        r2 = r25;
        r20 = r0.replace(r1, r2);	 Catch:{ IOException -> 0x00d6 }
        r24 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00d6 }
        r24.<init>();	 Catch:{ IOException -> 0x00d6 }
        r25 = 0;
        r26 = r11 + 1;
        r0 = r25;
        r1 = r26;
        r25 = r12.substring(r0, r1);	 Catch:{ IOException -> 0x00d6 }
        r24 = r24.append(r25);	 Catch:{ IOException -> 0x00d6 }
        r25 = 47;
        r24 = r24.append(r25);	 Catch:{ IOException -> 0x00d6 }
        r0 = r24;
        r1 = r20;
        r24 = r0.append(r1);	 Catch:{ IOException -> 0x00d6 }
        r25 = ".class";
        r24 = r24.append(r25);	 Catch:{ IOException -> 0x00d6 }
        r12 = r24.toString();	 Catch:{ IOException -> 0x00d6 }
        goto L_0x00c0;
    L_0x013d:
        r8 = move-exception;
        if (r16 == 0) goto L_0x0166;
    L_0x0140:
        r13 = r23.getFile();	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r24 = 33;
        r0 = r24;
        r22 = r13.lastIndexOf(r0);	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        if (r22 <= 0) goto L_0x0158;
    L_0x014e:
        r24 = 0;
        r0 = r24;
        r1 = r22;
        r13 = r13.substring(r0, r1);	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
    L_0x0158:
        r24 = new java.net.URL;	 Catch:{ FileNotFoundException -> 0x0185, ZipException -> 0x01a1 }
        r0 = r24;
        r0.<init>(r13);	 Catch:{ FileNotFoundException -> 0x0185, ZipException -> 0x01a1 }
        r24 = r24.openConnection();	 Catch:{ FileNotFoundException -> 0x0185, ZipException -> 0x01a1 }
        r24.getInputStream();	 Catch:{ FileNotFoundException -> 0x0185, ZipException -> 0x01a1 }
    L_0x0166:
        throw r8;	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
    L_0x0167:
        r8 = move-exception;
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r25 = "File for URL ";
        r24.print(r25);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r0 = r24;
        r0.print(r12);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r25 = " not found.";
        r24.println(r25);	 Catch:{ IOException -> 0x00d6 }
        r24 = -1;
        java.lang.System.exit(r24);	 Catch:{ IOException -> 0x00d6 }
        r15 = 0;
        goto L_0x00cf;
    L_0x0185:
        r9 = move-exception;
        r24 = java.lang.System.err;	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r25 = "Jar File for URL ";
        r24.print(r25);	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r24 = java.lang.System.err;	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r0 = r24;
        r0.print(r13);	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r24 = java.lang.System.err;	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r25 = " not found.";
        r24.println(r25);	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        r24 = -1;
        java.lang.System.exit(r24);	 Catch:{ FileNotFoundException -> 0x0167, ZipException -> 0x01a1 }
        goto L_0x0166;
    L_0x01a1:
        r8 = move-exception;
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r25 = "Error opening zip archive ";
        r24.print(r25);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r0 = r24;
        r0.print(r12);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r25 = " not found.";
        r24.println(r25);	 Catch:{ IOException -> 0x00d6 }
        r8.printStackTrace();	 Catch:{ IOException -> 0x00d6 }
        r24 = r8.getCause();	 Catch:{ IOException -> 0x00d6 }
        if (r24 == 0) goto L_0x01c7;
    L_0x01c0:
        r24 = r8.getCause();	 Catch:{ IOException -> 0x00d6 }
        r24.printStackTrace();	 Catch:{ IOException -> 0x00d6 }
    L_0x01c7:
        r24 = -1;
        java.lang.System.exit(r24);	 Catch:{ IOException -> 0x00d6 }
        r15 = 0;
        goto L_0x00cf;
    L_0x01cf:
        r15 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x01d6 }
        r15.<init>(r12);	 Catch:{ FileNotFoundException -> 0x01d6 }
        goto L_0x00cf;
    L_0x01d6:
        r8 = move-exception;
        r5 = gnu.bytecode.ObjectType.getContextClass(r12);	 Catch:{ NoClassDefFoundError -> 0x0212, Throwable -> 0x0218 }
        r18 = r5.getClassLoader();	 Catch:{ NoClassDefFoundError -> 0x0212, Throwable -> 0x0218 }
    L_0x01df:
        r24 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00d6 }
        r24.<init>();	 Catch:{ IOException -> 0x00d6 }
        r25 = 46;
        r26 = 47;
        r0 = r25;
        r1 = r26;
        r25 = r12.replace(r0, r1);	 Catch:{ IOException -> 0x00d6 }
        r24 = r24.append(r25);	 Catch:{ IOException -> 0x00d6 }
        r25 = ".class";
        r24 = r24.append(r25);	 Catch:{ IOException -> 0x00d6 }
        r6 = r24.toString();	 Catch:{ IOException -> 0x00d6 }
        r0 = r18;
        r21 = r0.getResource(r6);	 Catch:{ Throwable -> 0x0236 }
        r24 = r21.openConnection();	 Catch:{ Throwable -> 0x0236 }
        r15 = r24.getInputStream();	 Catch:{ Throwable -> 0x0236 }
        r12 = r21.toString();	 Catch:{ Throwable -> 0x0236 }
        goto L_0x00cf;
    L_0x0212:
        r9 = move-exception;
        r18 = gnu.bytecode.ObjectType.getContextClassLoader();	 Catch:{ IOException -> 0x00d6 }
        goto L_0x01df;
    L_0x0218:
        r9 = move-exception;
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r25 = "File ";
        r24.print(r25);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r0 = r24;
        r0.print(r12);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r25 = " not found.";
        r24.println(r25);	 Catch:{ IOException -> 0x00d6 }
        r24 = -1;
        java.lang.System.exit(r24);	 Catch:{ IOException -> 0x00d6 }
        r18 = 0;
        goto L_0x01df;
    L_0x0236:
        r10 = move-exception;
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r25 = "Can't find .class file for class ";
        r24.print(r25);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r0 = r24;
        r0.print(r12);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r25 = " - ";
        r24.print(r25);	 Catch:{ IOException -> 0x00d6 }
        r24 = java.lang.System.err;	 Catch:{ IOException -> 0x00d6 }
        r0 = r24;
        r0.println(r10);	 Catch:{ IOException -> 0x00d6 }
        r24 = -1;
        java.lang.System.exit(r24);	 Catch:{ IOException -> 0x00d6 }
        r15 = 0;
        goto L_0x00cf;
    L_0x025b:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.bytecode.dump.main(java.lang.String[]):void");
    }

    static int uriSchemeLength(String uri) {
        int len = uri.length();
        int i = 0;
        while (i < len) {
            char ch = uri.charAt(i);
            if (ch == ':') {
                return i;
            }
            if (i != 0) {
                if (!(Character.isLetterOrDigit(ch) || ch == '+' || ch == '-' || ch == '.')) {
                }
                i++;
            } else if (Character.isLetter(ch)) {
                i++;
            }
            return -1;
        }
        return -1;
    }

    static boolean uriSchemeSpecified(String name) {
        boolean z = true;
        int ulen = uriSchemeLength(name);
        if (ulen == 1 && File.separatorChar == '\\') {
            char drive = name.charAt(0);
            if (drive >= 'a' && drive <= 'z') {
                return false;
            }
            if (drive < 'A' || drive > 'Z') {
                return true;
            }
            return false;
        }
        if (ulen <= 0) {
            z = false;
        }
        return z;
    }

    public static void usage(PrintStream err) {
        err.println("Prints and dis-assembles the contents of JVM .class files.");
        err.println("Usage: [--verbose] class-or-jar ...");
        err.println("where a class-or-jar can be one of:");
        err.println("- a fully-qualified class name; or");
        err.println("- the name of a .class file, or a URL reference to one; or");
        err.println("- the name of a .jar or .zip archive file, or a URL reference to one.");
        err.println("If a .jar/.zip archive is named, all its.class file members are printed.");
        err.println();
        err.println("You can name a single .class member of an archive with a jar: URL,");
        err.println("which looks like: jar:jar-spec!/p1/p2/cl.class");
        err.println("The jar-spec can be a URL or the name of the .jar file.");
        err.println("You can also use the shorthand syntax: jar:jar-spec!p1.p2.cl");
        System.exit(-1);
    }
}

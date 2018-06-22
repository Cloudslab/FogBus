package gnu.bytecode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipArchive {
    private static void usage() {
        System.err.println("zipfile [ptxq] archive [file ...]");
        System.exit(-1);
    }

    public static long copy(InputStream in, OutputStream out, byte[] buffer) throws IOException {
        long total = 0;
        while (true) {
            int count = in.read(buffer);
            if (count <= 0) {
                return total;
            }
            out.write(buffer, 0, count);
            total += (long) count;
        }
    }

    public static void copy(InputStream in, String name, byte[] buffer) throws IOException {
        File f = new File(name);
        String dir_name = f.getParent();
        if (dir_name != null) {
            File dir = new File(dir_name);
            if (!dir.exists()) {
                System.err.println("mkdirs:" + dir.mkdirs());
            }
        }
        if (name.charAt(name.length() - 1) != '/') {
            OutputStream out = new BufferedOutputStream(new FileOutputStream(f));
            copy(in, out, buffer);
            out.close();
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            usage();
        }
        String command = args[0];
        String archive_name = args[1];
        try {
            int i;
            if (command.equals("t") || command.equals("p") || command.equals("x")) {
                OutputStream out = System.out;
                byte[] buf = new byte[1024];
                ZipEntry zent;
                String name;
                if (args.length == 2) {
                    InputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(archive_name)));
                    while (true) {
                        zent = zipInputStream.getNextEntry();
                        if (zent != null) {
                            name = zent.getName();
                            if (command.equals("t")) {
                                out.print(name);
                                out.print(" size: ");
                                out.println(zent.getSize());
                            } else if (command.equals("p")) {
                                copy(zipInputStream, out, buf);
                            } else {
                                copy(zipInputStream, name, buf);
                            }
                        } else {
                            return;
                        }
                    }
                }
                ZipFile zar = new ZipFile(archive_name);
                for (i = 2; i < args.length; i++) {
                    name = args[i];
                    zent = zar.getEntry(name);
                    if (zent == null) {
                        System.err.println("zipfile " + archive_name + ":" + args[i] + " - not found");
                        System.exit(-1);
                    } else if (command.equals("t")) {
                        out.print(name);
                        out.print(" size: ");
                        out.println(zent.getSize());
                    } else if (command.equals("p")) {
                        copy(zar.getInputStream(zent), out, buf);
                    } else {
                        copy(zar.getInputStream(zent), name, buf);
                    }
                }
            } else if (command.equals("q")) {
                ZipOutputStream zar2 = new ZipOutputStream(new FileOutputStream(archive_name));
                i = 2;
                while (i < args.length) {
                    File in = new File(args[i]);
                    if (!in.exists()) {
                        throw new IOException(args[i] + " - not found");
                    } else if (in.canRead()) {
                        int size = (int) in.length();
                        FileInputStream fin = new FileInputStream(in);
                        byte[] contents = new byte[size];
                        if (fin.read(contents) != size) {
                            throw new IOException(args[i] + " - read error");
                        }
                        fin.close();
                        ZipEntry ze = new ZipEntry(args[i]);
                        ze.setSize((long) size);
                        ze.setTime(in.lastModified());
                        zar2.putNextEntry(ze);
                        zar2.write(contents, 0, size);
                        i++;
                    } else {
                        throw new IOException(args[i] + " - not readable");
                    }
                }
                zar2.close();
            } else {
                usage();
            }
        } catch (IOException ex) {
            System.err.println("I/O Exception:  " + ex);
        }
    }
}

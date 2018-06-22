package gnu.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipLoader extends ClassLoader {
    private Vector<Object> loadedClasses;
    int size = 0;
    ZipFile zar;
    private String zipname;

    public ZipLoader(String name) throws IOException {
        this.zipname = name;
        this.zar = new ZipFile(name);
        Enumeration e = this.zar.entries();
        while (e.hasMoreElements()) {
            if (!((ZipEntry) e.nextElement()).isDirectory()) {
                this.size++;
            }
        }
        this.loadedClasses = new Vector(this.size);
    }

    public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clas;
        int index = this.loadedClasses.indexOf(name);
        if (index >= 0) {
            clas = (Class) this.loadedClasses.elementAt(index + 1);
        } else if (this.zar == null && this.loadedClasses.size() == this.size * 2) {
            clas = Class.forName(name);
        } else {
            boolean reopened = false;
            String member_name = name.replace('.', '/') + ".class";
            if (this.zar == null) {
                try {
                    this.zar = new ZipFile(this.zipname);
                    reopened = true;
                } catch (IOException ex) {
                    throw new ClassNotFoundException("IOException while loading " + member_name + " from ziparchive \"" + name + "\": " + ex.toString());
                }
            }
            ZipEntry member = this.zar.getEntry(member_name);
            if (member == null) {
                if (reopened) {
                    try {
                        close();
                    } catch (IOException e) {
                        throw new RuntimeException("failed to close \"" + this.zipname + "\"");
                    }
                }
                clas = Class.forName(name);
            } else {
                try {
                    int member_size = (int) member.getSize();
                    byte[] bytes = new byte[member_size];
                    new DataInputStream(this.zar.getInputStream(member)).readFully(bytes);
                    clas = defineClass(name, bytes, 0, member_size);
                    this.loadedClasses.addElement(name);
                    this.loadedClasses.addElement(clas);
                    if (this.size * 2 == this.loadedClasses.size()) {
                        close();
                    }
                } catch (IOException ex2) {
                    throw new ClassNotFoundException("IOException while loading " + member_name + " from ziparchive \"" + name + "\": " + ex2.toString());
                }
            }
        }
        if (resolve) {
            resolveClass(clas);
        }
        return clas;
    }

    public Class loadAllClasses() throws IOException {
        Enumeration e = this.zar.entries();
        Class mainClass = null;
        while (e.hasMoreElements()) {
            ZipEntry member = (ZipEntry) e.nextElement();
            String name = member.getName().replace('/', '.');
            name = name.substring(0, name.length() - "/class".length());
            int member_size = (int) member.getSize();
            byte[] bytes = new byte[member_size];
            new DataInputStream(this.zar.getInputStream(member)).readFully(bytes);
            Class clas = defineClass(name, bytes, 0, member_size);
            if (mainClass == null) {
                mainClass = clas;
            }
            this.loadedClasses.addElement(name);
            this.loadedClasses.addElement(clas);
        }
        close();
        return mainClass;
    }

    public void close() throws IOException {
        if (this.zar != null) {
            this.zar.close();
        }
        this.zar = null;
    }
}

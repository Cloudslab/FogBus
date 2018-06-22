package gnu.bytecode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Hashtable;

public class ArrayClassLoader extends ClassLoader {
    Hashtable cmap = new Hashtable(100);
    URL context;
    Hashtable map = new Hashtable(100);

    public ArrayClassLoader(ClassLoader parent) {
        super(parent);
    }

    public URL getResourceContext() {
        return this.context;
    }

    public void setResourceContext(URL context) {
        this.context = context;
    }

    public ArrayClassLoader(byte[][] classBytes) {
        int i = classBytes.length;
        while (true) {
            i--;
            if (i >= 0) {
                addClass("lambda" + i, classBytes[i]);
            } else {
                return;
            }
        }
    }

    public ArrayClassLoader(String[] classNames, byte[][] classBytes) {
        int i = classBytes.length;
        while (true) {
            i--;
            if (i >= 0) {
                addClass(classNames[i], classBytes[i]);
            } else {
                return;
            }
        }
    }

    public void addClass(Class clas) {
        this.cmap.put(clas.getName(), clas);
    }

    public void addClass(String name, byte[] bytes) {
        this.map.put(name, bytes);
    }

    public void addClass(ClassType ctype) {
        this.map.put(ctype.getName(), ctype);
    }

    public InputStream getResourceAsStream(String name) {
        InputStream resourceAsStream = super.getResourceAsStream(name);
        if (resourceAsStream != null || !name.endsWith(".class")) {
            return resourceAsStream;
        }
        Object r = this.map.get(name.substring(0, name.length() - 6).replace('/', '.'));
        if (r instanceof byte[]) {
            return new ByteArrayInputStream((byte[]) r);
        }
        return resourceAsStream;
    }

    protected URL findResource(String name) {
        if (this.context != null) {
            try {
                URL url = new URL(this.context, name);
                url.openConnection().connect();
                return url;
            } catch (Throwable th) {
            }
        }
        return super.findResource(name);
    }

    public Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class clas = loadClass(name);
        if (resolve) {
            resolveClass(clas);
        }
        return clas;
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        Object r = this.cmap.get(name);
        if (r != null) {
            return (Class) r;
        }
        Class clas;
        r = this.map.get(name);
        if (r instanceof ClassType) {
            ClassType ctype = (ClassType) r;
            if (ctype.isExisting()) {
                r = ctype.reflectClass;
            } else {
                r = ctype.writeToArray();
            }
        }
        if (r instanceof byte[]) {
            synchronized (this) {
                r = this.map.get(name);
                if (r instanceof byte[]) {
                    byte[] bytes = (byte[]) r;
                    clas = defineClass(name, bytes, 0, bytes.length);
                    this.cmap.put(name, clas);
                } else {
                    clas = (Class) r;
                }
            }
        } else if (r == null) {
            clas = getParent().loadClass(name);
        } else {
            clas = (Class) r;
        }
        return clas;
    }

    public static Package getContextPackage(String cname) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader instanceof ArrayClassLoader) {
                return ((ArrayClassLoader) loader).getPackage(cname);
            }
        } catch (SecurityException e) {
        }
        return Package.getPackage(cname);
    }
}

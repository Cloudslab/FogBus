package gnu.text;

import gnu.lists.FString;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

public class FilePath extends Path implements Comparable<FilePath> {
    final File file;
    final String path;

    private FilePath(File file) {
        this.file = file;
        this.path = file.toString();
    }

    private FilePath(File file, String path) {
        this.file = file;
        this.path = path;
    }

    public static FilePath valueOf(String str) {
        return new FilePath(new File(str), str);
    }

    public static FilePath valueOf(File file) {
        return new FilePath(file);
    }

    public static FilePath coerceToFilePathOrNull(Object path) {
        if (path instanceof FilePath) {
            return (FilePath) path;
        }
        if (path instanceof URIPath) {
            return valueOf(new File(((URIPath) path).uri));
        }
        if (path instanceof URI) {
            return valueOf(new File((URI) path));
        }
        if (path instanceof File) {
            return valueOf((File) path);
        }
        String str;
        if (path instanceof FString) {
            str = path.toString();
        } else if (!(path instanceof String)) {
            return null;
        } else {
            str = (String) path;
        }
        return valueOf(str);
    }

    public static FilePath makeFilePath(Object arg) {
        FilePath path = coerceToFilePathOrNull(arg);
        if (path != null) {
            return path;
        }
        throw new WrongType((String) null, -4, arg, "filepath");
    }

    public boolean isAbsolute() {
        return this == Path.userDirPath || this.file.isAbsolute();
    }

    public boolean isDirectory() {
        if (this.file.isDirectory()) {
            return true;
        }
        if (!this.file.exists()) {
            int len = this.path.length();
            if (len > 0) {
                char last = this.path.charAt(len - 1);
                if (last == '/' || last == File.separatorChar) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean delete() {
        return toFile().delete();
    }

    public long getLastModified() {
        return this.file.lastModified();
    }

    public boolean exists() {
        return this.file.exists();
    }

    public long getContentLength() {
        long length = this.file.length();
        return (length != 0 || this.file.exists()) ? length : -1;
    }

    public String getPath() {
        return this.file.getPath();
    }

    public String getLast() {
        return this.file.getName();
    }

    public FilePath getParent() {
        File parent = this.file.getParentFile();
        if (parent == null) {
            return null;
        }
        return valueOf(parent);
    }

    public int compareTo(FilePath path) {
        return this.file.compareTo(path.file);
    }

    public boolean equals(Object obj) {
        return (obj instanceof FilePath) && this.file.equals(((FilePath) obj).file);
    }

    public int hashCode() {
        return this.file.hashCode();
    }

    public String toString() {
        return this.path;
    }

    public File toFile() {
        return this.file;
    }

    public URL toURL() {
        if (this == Path.userDirPath) {
            return resolve("").toURL();
        }
        if (!isAbsolute()) {
            return getAbsolute().toURL();
        }
        try {
            return this.file.toURI().toURL();
        } catch (Throwable ex) {
            RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
        }
    }

    private static URI toUri(File file) {
        try {
            if (file.isAbsolute()) {
                return file.toURI();
            }
            String fname = file.toString();
            char fileSep = File.separatorChar;
            if (fileSep != '/') {
                fname = fname.replace(fileSep, '/');
            }
            return new URI(null, null, fname, null);
        } catch (Throwable ex) {
            RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
        }
    }

    public URI toUri() {
        if (this == Path.userDirPath) {
            return resolve("").toURI();
        }
        return toUri(this.file);
    }

    public InputStream openInputStream() throws IOException {
        return new FileInputStream(this.file);
    }

    public OutputStream openOutputStream() throws IOException {
        return new FileOutputStream(this.file);
    }

    public String getScheme() {
        return isAbsolute() ? "file" : null;
    }

    public Path resolve(String relative) {
        if (Path.uriSchemeSpecified(relative)) {
            return URIPath.valueOf(relative);
        }
        File rfile = new File(relative);
        if (rfile.isAbsolute()) {
            return valueOf(rfile);
        }
        File nfile;
        char sep = File.separatorChar;
        if (sep != '/') {
            relative = relative.replace('/', sep);
        }
        if (this == Path.userDirPath) {
            nfile = new File(System.getProperty("user.dir"), relative);
        } else {
            nfile = new File(isDirectory() ? this.file : this.file.getParentFile(), relative);
        }
        return valueOf(nfile);
    }

    public Path getCanonical() {
        Path valueOf;
        try {
            File canon = this.file.getCanonicalFile();
            if (!canon.equals(this.file)) {
                valueOf = valueOf(canon);
            }
        } catch (Throwable th) {
        }
        return valueOf;
    }
}

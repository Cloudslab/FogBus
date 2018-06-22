package gnu.text;

import android.support.v4.media.TransportMediator;
import gnu.bytecode.Access;
import gnu.kawa.servlet.HttpRequestContext;
import gnu.lists.FString;
import gnu.mapping.WrappedException;
import gnu.mapping.WrongType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class URIPath extends Path implements Comparable<URIPath> {
    final URI uri;

    URIPath(URI uri) {
        this.uri = uri;
    }

    public static URIPath coerceToURIPathOrNull(Object path) {
        if (path instanceof URIPath) {
            return (URIPath) path;
        }
        if (path instanceof URL) {
            return URLPath.valueOf((URL) path);
        }
        if (path instanceof URI) {
            return valueOf((URI) path);
        }
        String str;
        if ((path instanceof File) || (path instanceof Path) || (path instanceof FString)) {
            str = path.toString();
        } else if (!(path instanceof String)) {
            return null;
        } else {
            str = (String) path;
        }
        return valueOf(str);
    }

    public static URIPath makeURI(Object arg) {
        URIPath path = coerceToURIPathOrNull(arg);
        if (path != null) {
            return path;
        }
        throw new WrongType((String) null, -4, arg, "URI");
    }

    public static URIPath valueOf(URI uri) {
        return new URIPath(uri);
    }

    public static URIPath valueOf(String uri) {
        try {
            return new URIStringPath(new URI(encodeForUri(uri, Access.INNERCLASS_CONTEXT)), uri);
        } catch (Throwable ex) {
            RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
        }
    }

    public boolean isAbsolute() {
        return this.uri.isAbsolute();
    }

    public boolean exists() {
        try {
            URLConnection conn = toURL().openConnection();
            if (conn instanceof HttpURLConnection) {
                if (((HttpURLConnection) conn).getResponseCode() == HttpRequestContext.HTTP_OK) {
                    return true;
                }
                return false;
            } else if (conn.getLastModified() == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Throwable th) {
            return false;
        }
    }

    public long getLastModified() {
        return URLPath.getLastModified(toURL());
    }

    public long getContentLength() {
        return (long) URLPath.getContentLength(toURL());
    }

    public URI toUri() {
        return this.uri;
    }

    public String toURIString() {
        return this.uri.toString();
    }

    public Path resolve(String rstr) {
        if (Path.uriSchemeSpecified(rstr)) {
            return valueOf(rstr);
        }
        char fileSep = File.separatorChar;
        if (fileSep != '/') {
            if (rstr.length() >= 2 && ((rstr.charAt(1) == ':' && Character.isLetter(rstr.charAt(0))) || (rstr.charAt(0) == fileSep && rstr.charAt(1) == fileSep))) {
                return FilePath.valueOf(new File(rstr));
            }
            rstr = rstr.replace(fileSep, '/');
        }
        try {
            return valueOf(this.uri.resolve(new URI(null, rstr, null)));
        } catch (Throwable ex) {
            RuntimeException wrapIfNeeded = WrappedException.wrapIfNeeded(ex);
        }
    }

    public int compareTo(URIPath path) {
        return this.uri.compareTo(path.uri);
    }

    public boolean equals(Object obj) {
        return (obj instanceof URIPath) && this.uri.equals(((URIPath) obj).uri);
    }

    public int hashCode() {
        return this.uri.hashCode();
    }

    public String toString() {
        return toURIString();
    }

    public URL toURL() {
        return Path.toURL(this.uri.toString());
    }

    public InputStream openInputStream() throws IOException {
        return URLPath.openInputStream(toURL());
    }

    public OutputStream openOutputStream() throws IOException {
        return URLPath.openOutputStream(toURL());
    }

    public String getScheme() {
        return this.uri.getScheme();
    }

    public String getHost() {
        return this.uri.getHost();
    }

    public String getAuthority() {
        return this.uri.getAuthority();
    }

    public String getUserInfo() {
        return this.uri.getUserInfo();
    }

    public int getPort() {
        return this.uri.getPort();
    }

    public String getPath() {
        return this.uri.getPath();
    }

    public String getQuery() {
        return this.uri.getQuery();
    }

    public String getFragment() {
        return this.uri.getFragment();
    }

    public Path getCanonical() {
        if (!isAbsolute()) {
            return getAbsolute().getCanonical();
        }
        URI norm = this.uri.normalize();
        if (norm == this.uri) {
            return this;
        }
        return valueOf(norm);
    }

    public static String encodeForUri(String str, char mode) {
        StringBuffer sbuf = new StringBuffer();
        int len = str.length();
        int i;
        for (int i2 = 0; i2 < len; i2 = i) {
            i = i2 + 1;
            int ch = str.charAt(i2);
            if (ch >= 55296 && ch < 56320 && i < len) {
                ch = (((ch - 55296) * 1024) + (str.charAt(i) - 56320)) + 65536;
                i++;
            }
            if (mode != 'H' ? (ch < 97 || ch > 122) && ((ch < 65 || ch > 90) && !((ch >= 48 && ch <= 57) || ch == 45 || ch == 95 || ch == 46 || ch == TransportMediator.KEYCODE_MEDIA_PLAY || (mode == 'I' && (ch == 59 || ch == 47 || ch == 63 || ch == 58 || ch == 42 || ch == 39 || ch == 40 || ch == 41 || ch == 64 || ch == 38 || ch == 61 || ch == 43 || ch == 36 || ch == 44 || ch == 91 || ch == 93 || ch == 35 || ch == 33 || ch == 37)))) : ch < 32 || ch > TransportMediator.KEYCODE_MEDIA_PLAY) {
                int pos = sbuf.length();
                int nbytes = 0;
                if (ch >= 128) {
                    if (ch >= 2048) {
                        if (ch < 65536) {
                        }
                    }
                }
                do {
                    int b;
                    if (ch < (1 << (nbytes == 0 ? 7 : 6 - nbytes))) {
                        b = ch;
                        if (nbytes > 0) {
                            b |= (65408 >> nbytes) & 255;
                        }
                        ch = 0;
                    } else {
                        b = (ch & 63) | 128;
                        ch >>= 6;
                    }
                    nbytes++;
                    for (int j = 0; j <= 1; j++) {
                        int hex = b & 15;
                        sbuf.insert(pos, (char) (hex <= 9 ? hex + 48 : (hex - 10) + 65));
                        b >>= 4;
                    }
                    sbuf.insert(pos, '%');
                } while (ch != 0);
            } else {
                sbuf.append((char) ch);
            }
        }
        return sbuf.toString();
    }
}

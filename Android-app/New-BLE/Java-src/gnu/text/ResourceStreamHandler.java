package gnu.text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class ResourceStreamHandler extends URLStreamHandler {
    public static final String CLASS_RESOURCE_URI_PREFIX = "class-resource:/";
    public static final int CLASS_RESOURCE_URI_PREFIX_LENGTH = 16;
    ClassLoader cloader;

    public ResourceStreamHandler(ClassLoader cloader) {
        this.cloader = cloader;
    }

    public static URL makeURL(Class clas) throws MalformedURLException {
        String cname = clas.getName();
        int dot = cname.lastIndexOf(46);
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(CLASS_RESOURCE_URI_PREFIX);
        if (dot >= 0) {
            sbuf.append(cname.substring(0, dot));
            sbuf.append('/');
            cname = cname.substring(dot + 1);
        }
        sbuf.append(cname);
        return new URL(null, sbuf.toString(), new ResourceStreamHandler(clas.getClassLoader()));
    }

    public URLConnection openConnection(URL u) throws IOException {
        String ustr = u.toString();
        String rstr = ustr.substring(16);
        int sl = rstr.indexOf(47);
        if (sl > 0) {
            rstr = rstr.substring(0, sl).replace('.', '/') + rstr.substring(sl);
        }
        URL url = this.cloader.getResource(rstr);
        if (url != null) {
            return url.openConnection();
        }
        throw new FileNotFoundException(ustr);
    }
}

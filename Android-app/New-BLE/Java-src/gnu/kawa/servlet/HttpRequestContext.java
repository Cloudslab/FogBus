package gnu.kawa.servlet;

import gnu.mapping.InPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

public abstract class HttpRequestContext {
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_OK = 200;
    static final int STATUS_SENT = -999;
    public static int importServletDefinitions;
    protected static final ThreadLocal<HttpRequestContext> instance = new ThreadLocal();
    ServletPrinter consumer;
    String localPath = "";
    String scriptPath = "";
    public int statusCode = HTTP_OK;
    public String statusReasonPhrase = null;

    public abstract Object getAttribute(String str);

    public abstract String getContextPath();

    public abstract int getLocalPort();

    public abstract String getPathTranslated();

    public abstract String getQueryString();

    public abstract InetAddress getRemoteHost();

    public abstract String getRemoteIPAddress();

    public abstract int getRemotePort();

    public abstract String getRequestHeader(String str);

    public abstract List<String> getRequestHeaders(String str);

    public abstract Map<String, List<String>> getRequestHeaders();

    public abstract String getRequestMethod();

    public abstract Map<String, List<String>> getRequestParameters();

    public abstract InputStream getRequestStream();

    public abstract URI getRequestURI();

    public abstract URL getResourceURL(String str);

    public abstract OutputStream getResponseStream();

    public abstract void log(String str);

    public abstract void log(String str, Throwable th);

    public abstract boolean reset(boolean z);

    public abstract void sendResponseHeaders(int i, String str, long j) throws IOException;

    public abstract void setAttribute(String str, Object obj);

    public abstract void setResponseHeader(String str, String str2);

    public static HttpRequestContext getInstance() {
        HttpRequestContext hctx = (HttpRequestContext) instance.get();
        if (hctx != null) {
            return hctx;
        }
        throw new UnsupportedOperationException("can only be called by http-server");
    }

    public static HttpRequestContext getInstance(String command) {
        HttpRequestContext hctx = (HttpRequestContext) instance.get();
        if (hctx != null) {
            return hctx;
        }
        throw new UnsupportedOperationException(command + " can only be called within http-server");
    }

    public static void setInstance(HttpRequestContext ctx) {
        instance.set(ctx);
    }

    public InPort getRequestPort() {
        return new InPort(getRequestStream());
    }

    public String getRequestBodyChars() throws IOException {
        Reader reader = new InputStreamReader(getRequestStream());
        int buflen = 1024;
        char[] buf = new char[1024];
        int pos = 0;
        while (true) {
            int avail = buflen - pos;
            if (avail <= 0) {
                char[] tmp = new char[(buflen * 2)];
                System.arraycopy(buf, 0, tmp, 0, buflen);
                buf = tmp;
                buflen += buflen;
            }
            int count = reader.read(buf, pos, avail);
            if (count < 0) {
                reader.close();
                return new String(buf, 0, pos);
            }
            pos += count;
        }
    }

    public ServletPrinter getConsumer() throws IOException {
        if (this.consumer == null) {
            this.consumer = new ServletPrinter(this, 8192);
        }
        return this.consumer;
    }

    public String getRequestParameter(String name) {
        List<String> p = (List) getRequestParameters().get(name);
        return (p == null || p.isEmpty()) ? null : (String) p.get(0);
    }

    public String getScriptPath() {
        return this.scriptPath;
    }

    public String getLocalPath() {
        return this.localPath;
    }

    public void setScriptAndLocalPath(String scriptPath, String localPath) {
        this.scriptPath = scriptPath;
        this.localPath = localPath;
    }

    public String getRequestPath() {
        return getRequestURI().getPath();
    }

    public String getRequestScheme() {
        return "http";
    }

    public InetSocketAddress getLocalSocketAddress() {
        return new InetSocketAddress(getLocalHost(), getLocalPort());
    }

    public String getLocalIPAddress() {
        return getLocalHost().getHostAddress();
    }

    public InetAddress getLocalHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (Throwable ex) {
            RuntimeException runtimeException = new RuntimeException(ex);
        }
    }

    public InetSocketAddress getRemoteSocketAddress() {
        return new InetSocketAddress(getRemoteHost(), getRemotePort());
    }

    public StringBuffer getRequestURLBuffer() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(getRequestScheme());
        sbuf.append("://");
        String host = getRequestHeader("Host");
        if (host != null) {
            sbuf.append(host);
        } else {
            sbuf.append(getLocalIPAddress());
            sbuf.append(':');
            sbuf.append(getLocalPort());
        }
        sbuf.append(getRequestPath());
        return sbuf;
    }

    public void setContentType(String type) {
        setResponseHeader("Content-Type", type);
    }

    protected String normalizeToContext(String path) {
        if (path.length() <= 0 || path.charAt(0) != '/') {
            path = getScriptPath() + path;
        } else {
            path = path.substring(1);
        }
        if (path.indexOf("..") >= 0) {
            path = URI.create(path).normalize().toString();
            if (path.startsWith("../")) {
                return null;
            }
        }
        return path;
    }

    public void sendNotFound(String path) throws IOException {
        byte[] bmsg = ("The requested URL " + path + " was not found on this server.\r\n").getBytes();
        sendResponseHeaders(404, null, (long) bmsg.length);
        try {
            getResponseStream().write(bmsg);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

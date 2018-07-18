package com.google.appinventor.components.runtime.util;

import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NanoHTTPD {
    public static final String HTTP_BADREQUEST = "400 Bad Request";
    public static final String HTTP_FORBIDDEN = "403 Forbidden";
    public static final String HTTP_INTERNALERROR = "500 Internal Server Error";
    public static final String HTTP_NOTFOUND = "404 Not Found";
    public static final String HTTP_NOTIMPLEMENTED = "501 Not Implemented";
    public static final String HTTP_NOTMODIFIED = "304 Not Modified";
    public static final String HTTP_OK = "200 OK";
    public static final String HTTP_PARTIALCONTENT = "206 Partial Content";
    public static final String HTTP_RANGE_NOT_SATISFIABLE = "416 Requested Range Not Satisfiable";
    public static final String HTTP_REDIRECT = "301 Moved Permanently";
    private static final String LICENCE = "Copyright (C) 2001,2005-2011 by Jarno Elonen <elonen@iki.fi>\nand Copyright (C) 2010 by Konstantinos Togias <info@ktogias.gr>\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions\nare met:\n\nRedistributions of source code must retain the above copyright notice,\nthis list of conditions and the following disclaimer. Redistributions in\nbinary form must reproduce the above copyright notice, this list of\nconditions and the following disclaimer in the documentation and/or other\nmaterials provided with the distribution. The name of the author may not\nbe used to endorse or promote products derived from this software without\nspecific prior written permission. \n \nTHIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR\nIMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\nOF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.\nIN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,\nINCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT\nNOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\nDATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\nTHEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\nOF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.";
    private static final String LOG_TAG = "AppInvHTTPD";
    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";
    public static final String MIME_HTML = "text/html";
    public static final String MIME_PLAINTEXT = "text/plain";
    public static final String MIME_XML = "text/xml";
    private static final int REPL_STACK_SIZE = 262144;
    private static SimpleDateFormat gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
    protected static PrintStream myErr = System.err;
    protected static PrintStream myOut = System.out;
    private static int theBufferSize = 16384;
    private static Hashtable theMimeTypes = new Hashtable();
    private ThreadPoolExecutor myExecutor = new ThreadPoolExecutor(2, 10, 5, TimeUnit.SECONDS, new SynchronousQueue(), new myThreadFactory());
    private File myRootDir;
    private final ServerSocket myServerSocket;
    private int myTcpPort;
    private Thread myThread;

    class C03361 implements Runnable {
        C03361() {
        }

        public void run() {
            while (true) {
                try {
                    HTTPSession hTTPSession = new HTTPSession(NanoHTTPD.this.myServerSocket.accept());
                } catch (IOException e) {
                    return;
                }
            }
        }
    }

    private class HTTPSession implements Runnable {
        private Socket mySocket;

        public HTTPSession(Socket s) {
            this.mySocket = s;
            Log.d(NanoHTTPD.LOG_TAG, "NanoHTTPD: getPoolSize() = " + NanoHTTPD.this.myExecutor.getPoolSize());
            NanoHTTPD.this.myExecutor.execute(this);
        }

        public void run() {
            try {
                InputStream is = this.mySocket.getInputStream();
                if (is != null) {
                    byte[] buf = new byte[8192];
                    int rlen = is.read(buf, 0, 8192);
                    if (rlen > 0) {
                        OutputStream f;
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf, 0, rlen)));
                        Properties pre = new Properties();
                        Properties parms = new Properties();
                        Properties header = new Properties();
                        Properties files = new Properties();
                        decodeHeader(bufferedReader, pre, parms, header);
                        String method = pre.getProperty("method");
                        String uri = pre.getProperty("uri");
                        long size = Long.MAX_VALUE;
                        String contentLength = header.getProperty("content-length");
                        if (contentLength != null) {
                            try {
                                size = (long) Integer.parseInt(contentLength);
                            } catch (NumberFormatException e) {
                            }
                        }
                        int splitbyte = 0;
                        boolean sbfound = false;
                        while (splitbyte < rlen) {
                            if (buf[splitbyte] == (byte) 13) {
                                splitbyte++;
                                if (buf[splitbyte] == (byte) 10) {
                                    splitbyte++;
                                    if (buf[splitbyte] == (byte) 13) {
                                        splitbyte++;
                                        if (buf[splitbyte] == (byte) 10) {
                                            sbfound = true;
                                            break;
                                        }
                                    } else {
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            }
                            splitbyte++;
                        }
                        splitbyte++;
                        if (method.equalsIgnoreCase("PUT")) {
                            File tmpfile = File.createTempFile("upload", "bin");
                            tmpfile.deleteOnExit();
                            OutputStream fileOutputStream = new FileOutputStream(tmpfile);
                            files.put("content", tmpfile.getAbsolutePath());
                        } else {
                            f = new ByteArrayOutputStream();
                        }
                        if (splitbyte < rlen) {
                            f.write(buf, splitbyte, rlen - splitbyte);
                        }
                        if (splitbyte < rlen) {
                            size -= (long) ((rlen - splitbyte) + 1);
                        } else if (!sbfound || size == Long.MAX_VALUE) {
                            size = 0;
                        }
                        buf = new byte[512];
                        while (rlen >= 0 && size > 0) {
                            rlen = is.read(buf, 0, 512);
                            size -= (long) rlen;
                            if (rlen > 0) {
                                f.write(buf, 0, rlen);
                            }
                        }
                        if (method.equalsIgnoreCase("POST")) {
                            byte[] fbuf = ((ByteArrayOutputStream) f).toByteArray();
                            BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fbuf)));
                            String contentType = "";
                            StringTokenizer stringTokenizer = new StringTokenizer(header.getProperty("content-type"), "; ");
                            if (stringTokenizer.hasMoreTokens()) {
                                contentType = stringTokenizer.nextToken();
                            }
                            if (contentType.equalsIgnoreCase("multipart/form-data")) {
                                if (!stringTokenizer.hasMoreTokens()) {
                                    sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
                                }
                                stringTokenizer = new StringTokenizer(stringTokenizer.nextToken(), "=");
                                if (stringTokenizer.countTokens() != 2) {
                                    sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary syntax error. Usage: GET /example/file.html");
                                }
                                stringTokenizer.nextToken();
                                decodeMultipartData(stringTokenizer.nextToken(), fbuf, in, parms, files);
                            } else {
                                String postLine = "";
                                char[] pbuf = new char[512];
                                for (int read = in.read(pbuf); read >= 0; read = in.read(pbuf)) {
                                    if (postLine.endsWith("\r\n")) {
                                        break;
                                    }
                                    postLine = postLine + String.valueOf(pbuf, 0, read);
                                }
                                decodeParms(postLine.trim(), parms);
                            }
                            in.close();
                        } else if (method.equalsIgnoreCase("PUT ")) {
                            f.close();
                        }
                        Response r = NanoHTTPD.this.serve(uri, method, header, parms, files, this.mySocket);
                        if (r == null) {
                            sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: Serve() returned a null response.");
                        } else {
                            sendResponse(r.status, r.mimeType, r.header, r.data);
                        }
                        is.close();
                    }
                }
            } catch (IOException ioe) {
                sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            } catch (InterruptedException e2) {
            } catch (Throwable th) {
            }
        }

        private void decodeHeader(BufferedReader in, Properties pre, Properties parms, Properties header) throws InterruptedException {
            try {
                String inLine = in.readLine();
                if (inLine != null) {
                    StringTokenizer st = new StringTokenizer(inLine);
                    if (!st.hasMoreTokens()) {
                        sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
                    }
                    pre.put("method", st.nextToken());
                    if (!st.hasMoreTokens()) {
                        sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
                    }
                    String uri = st.nextToken();
                    int qmi = uri.indexOf(63);
                    if (qmi >= 0) {
                        decodeParms(uri.substring(qmi + 1), parms);
                        uri = decodePercent(uri.substring(0, qmi));
                    } else {
                        uri = decodePercent(uri);
                    }
                    if (st.hasMoreTokens()) {
                        String line = in.readLine();
                        while (line != null && line.trim().length() > 0) {
                            int p = line.indexOf(58);
                            if (p >= 0) {
                                header.put(line.substring(0, p).trim().toLowerCase(), line.substring(p + 1).trim());
                            }
                            line = in.readLine();
                        }
                    }
                    pre.put("uri", uri);
                }
            } catch (IOException ioe) {
                sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            }
        }

        private void decodeMultipartData(String boundary, byte[] fbuf, BufferedReader in, Properties parms, Properties files) throws InterruptedException {
            try {
                int[] bpositions = getBoundaryPositions(fbuf, boundary.getBytes());
                int boundarycount = 1;
                String mpline = in.readLine();
                while (mpline != null) {
                    int p;
                    if (mpline.indexOf(boundary) == -1) {
                        sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but next chunk does not start with boundary. Usage: GET /example/file.html");
                    }
                    boundarycount++;
                    Properties item = new Properties();
                    mpline = in.readLine();
                    while (mpline != null && mpline.trim().length() > 0) {
                        p = mpline.indexOf(58);
                        if (p != -1) {
                            item.put(mpline.substring(0, p).trim().toLowerCase(), mpline.substring(p + 1).trim());
                        }
                        mpline = in.readLine();
                    }
                    if (mpline != null) {
                        String contentDisposition = item.getProperty("content-disposition");
                        if (contentDisposition == null) {
                            sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but no content-disposition info found. Usage: GET /example/file.html");
                        }
                        StringTokenizer st = new StringTokenizer(contentDisposition, "; ");
                        Properties disposition = new Properties();
                        while (st.hasMoreTokens()) {
                            String token = st.nextToken();
                            p = token.indexOf(61);
                            if (p != -1) {
                                disposition.put(token.substring(0, p).trim().toLowerCase(), token.substring(p + 1).trim());
                            }
                        }
                        String pname = disposition.getProperty("name");
                        pname = pname.substring(1, pname.length() - 1);
                        String value = "";
                        if (item.getProperty("content-type") != null) {
                            if (boundarycount > bpositions.length) {
                                sendError(NanoHTTPD.HTTP_INTERNALERROR, "Error processing request");
                            }
                            int offset = stripMultipartHeaders(fbuf, bpositions[boundarycount - 2]);
                            files.put(pname, saveTmpFile(fbuf, offset, (bpositions[boundarycount - 1] - offset) - 4));
                            value = disposition.getProperty("filename");
                            value = value.substring(1, value.length() - 1);
                            do {
                                mpline = in.readLine();
                                if (mpline == null) {
                                    break;
                                }
                            } while (mpline.indexOf(boundary) == -1);
                        } else {
                            while (mpline != null && mpline.indexOf(boundary) == -1) {
                                mpline = in.readLine();
                                if (mpline != null) {
                                    int d = mpline.indexOf(boundary);
                                    if (d == -1) {
                                        value = value + mpline;
                                    } else {
                                        value = value + mpline.substring(0, d - 2);
                                    }
                                }
                            }
                        }
                        parms.put(pname, value);
                    }
                }
            } catch (IOException ioe) {
                sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            }
        }

        public int[] getBoundaryPositions(byte[] b, byte[] boundary) {
            int matchcount = 0;
            int matchbyte = -1;
            Vector matchbytes = new Vector();
            int i = 0;
            while (i < b.length) {
                if (b[i] == boundary[matchcount]) {
                    if (matchcount == 0) {
                        matchbyte = i;
                    }
                    matchcount++;
                    if (matchcount == boundary.length) {
                        matchbytes.addElement(new Integer(matchbyte));
                        matchcount = 0;
                        matchbyte = -1;
                    }
                } else {
                    i -= matchcount;
                    matchcount = 0;
                    matchbyte = -1;
                }
                i++;
            }
            int[] ret = new int[matchbytes.size()];
            for (i = 0; i < ret.length; i++) {
                ret[i] = ((Integer) matchbytes.elementAt(i)).intValue();
            }
            return ret;
        }

        private String saveTmpFile(byte[] b, int offset, int len) {
            String path = "";
            if (len > 0) {
                try {
                    File temp = File.createTempFile("NanoHTTPD", "", new File(System.getProperty("java.io.tmpdir")));
                    OutputStream fstream = new FileOutputStream(temp);
                    fstream.write(b, offset, len);
                    fstream.close();
                    path = temp.getAbsolutePath();
                } catch (Exception e) {
                    NanoHTTPD.myErr.println("Error: " + e.getMessage());
                }
            }
            return path;
        }

        private int stripMultipartHeaders(byte[] b, int offset) {
            int i = offset;
            while (i < b.length) {
                if (b[i] == (byte) 13) {
                    i++;
                    if (b[i] == (byte) 10) {
                        i++;
                        if (b[i] == (byte) 13) {
                            i++;
                            if (b[i] == (byte) 10) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                i++;
            }
            return i + 1;
        }

        private String decodePercent(String str) throws InterruptedException {
            try {
                StringBuffer sb = new StringBuffer();
                int i = 0;
                while (i < str.length()) {
                    char c = str.charAt(i);
                    switch (c) {
                        case '%':
                            sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                            i += 2;
                            break;
                        case '+':
                            sb.append(' ');
                            break;
                        default:
                            sb.append(c);
                            break;
                    }
                    i++;
                }
                return sb.toString();
            } catch (Exception e) {
                sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Bad percent-encoding.");
                return null;
            }
        }

        private void decodeParms(String parms, Properties p) throws InterruptedException {
            if (parms != null) {
                StringTokenizer st = new StringTokenizer(parms, "&");
                while (st.hasMoreTokens()) {
                    String e = st.nextToken();
                    int sep = e.indexOf(61);
                    if (sep >= 0) {
                        p.put(decodePercent(e.substring(0, sep)).trim(), decodePercent(e.substring(sep + 1)));
                    }
                }
            }
        }

        private void sendError(String status, String msg) throws InterruptedException {
            sendResponse(status, NanoHTTPD.MIME_PLAINTEXT, null, new ByteArrayInputStream(msg.getBytes()));
            throw new InterruptedException();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void sendResponse(java.lang.String r14, java.lang.String r15, java.util.Properties r16, java.io.InputStream r17) {
            /*
            r13 = this;
            if (r14 != 0) goto L_0x0011;
        L_0x0002:
            r10 = new java.lang.Error;	 Catch:{ IOException -> 0x000a }
            r11 = "sendResponse(): Status can't be null.";
            r10.<init>(r11);	 Catch:{ IOException -> 0x000a }
            throw r10;	 Catch:{ IOException -> 0x000a }
        L_0x000a:
            r3 = move-exception;
            r10 = r13.mySocket;	 Catch:{ Throwable -> 0x0102 }
            r10.close();	 Catch:{ Throwable -> 0x0102 }
        L_0x0010:
            return;
        L_0x0011:
            r10 = r13.mySocket;	 Catch:{ IOException -> 0x000a }
            r5 = r10.getOutputStream();	 Catch:{ IOException -> 0x000a }
            r7 = new java.io.PrintWriter;	 Catch:{ IOException -> 0x000a }
            r7.<init>(r5);	 Catch:{ IOException -> 0x000a }
            r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x000a }
            r10.<init>();	 Catch:{ IOException -> 0x000a }
            r11 = "HTTP/1.0 ";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r14);	 Catch:{ IOException -> 0x000a }
            r11 = " \r\n";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.toString();	 Catch:{ IOException -> 0x000a }
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
            if (r15 == 0) goto L_0x0056;
        L_0x003a:
            r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x000a }
            r10.<init>();	 Catch:{ IOException -> 0x000a }
            r11 = "Content-Type: ";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r15);	 Catch:{ IOException -> 0x000a }
            r11 = "\r\n";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.toString();	 Catch:{ IOException -> 0x000a }
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
        L_0x0056:
            if (r16 == 0) goto L_0x0062;
        L_0x0058:
            r10 = "Date";
            r0 = r16;
            r10 = r0.getProperty(r10);	 Catch:{ IOException -> 0x000a }
            if (r10 != 0) goto L_0x008b;
        L_0x0062:
            r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x000a }
            r10.<init>();	 Catch:{ IOException -> 0x000a }
            r11 = "Date: ";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r11 = com.google.appinventor.components.runtime.util.NanoHTTPD.gmtFrmt;	 Catch:{ IOException -> 0x000a }
            r12 = new java.util.Date;	 Catch:{ IOException -> 0x000a }
            r12.<init>();	 Catch:{ IOException -> 0x000a }
            r11 = r11.format(r12);	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r11 = "\r\n";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.toString();	 Catch:{ IOException -> 0x000a }
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
        L_0x008b:
            if (r16 == 0) goto L_0x00c4;
        L_0x008d:
            r2 = r16.keys();	 Catch:{ IOException -> 0x000a }
        L_0x0091:
            r10 = r2.hasMoreElements();	 Catch:{ IOException -> 0x000a }
            if (r10 == 0) goto L_0x00c4;
        L_0x0097:
            r4 = r2.nextElement();	 Catch:{ IOException -> 0x000a }
            r4 = (java.lang.String) r4;	 Catch:{ IOException -> 0x000a }
            r0 = r16;
            r9 = r0.getProperty(r4);	 Catch:{ IOException -> 0x000a }
            r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x000a }
            r10.<init>();	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r4);	 Catch:{ IOException -> 0x000a }
            r11 = ": ";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r9);	 Catch:{ IOException -> 0x000a }
            r11 = "\r\n";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.toString();	 Catch:{ IOException -> 0x000a }
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
            goto L_0x0091;
        L_0x00c4:
            r10 = "\r\n";
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
            r7.flush();	 Catch:{ IOException -> 0x000a }
            if (r17 == 0) goto L_0x00ed;
        L_0x00ce:
            r6 = r17.available();	 Catch:{ IOException -> 0x000a }
            r10 = com.google.appinventor.components.runtime.util.NanoHTTPD.theBufferSize;	 Catch:{ IOException -> 0x000a }
            r1 = new byte[r10];	 Catch:{ IOException -> 0x000a }
        L_0x00d8:
            if (r6 <= 0) goto L_0x00ed;
        L_0x00da:
            r11 = 0;
            r10 = com.google.appinventor.components.runtime.util.NanoHTTPD.theBufferSize;	 Catch:{ IOException -> 0x000a }
            if (r6 <= r10) goto L_0x00fa;
        L_0x00e1:
            r10 = com.google.appinventor.components.runtime.util.NanoHTTPD.theBufferSize;	 Catch:{ IOException -> 0x000a }
        L_0x00e5:
            r0 = r17;
            r8 = r0.read(r1, r11, r10);	 Catch:{ IOException -> 0x000a }
            if (r8 > 0) goto L_0x00fc;
        L_0x00ed:
            r5.flush();	 Catch:{ IOException -> 0x000a }
            r5.close();	 Catch:{ IOException -> 0x000a }
            if (r17 == 0) goto L_0x0010;
        L_0x00f5:
            r17.close();	 Catch:{ IOException -> 0x000a }
            goto L_0x0010;
        L_0x00fa:
            r10 = r6;
            goto L_0x00e5;
        L_0x00fc:
            r10 = 0;
            r5.write(r1, r10, r8);	 Catch:{ IOException -> 0x000a }
            r6 = r6 - r8;
            goto L_0x00d8;
        L_0x0102:
            r10 = move-exception;
            goto L_0x0010;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.NanoHTTPD.HTTPSession.sendResponse(java.lang.String, java.lang.String, java.util.Properties, java.io.InputStream):void");
        }
    }

    public class Response {
        public InputStream data;
        public Properties header;
        public String mimeType;
        public String status;

        public Response() {
            this.header = new Properties();
            this.status = NanoHTTPD.HTTP_OK;
        }

        public Response(String status, String mimeType, InputStream data) {
            this.header = new Properties();
            this.status = status;
            this.mimeType = mimeType;
            this.data = data;
        }

        public Response(String status, String mimeType, String txt) {
            this.header = new Properties();
            this.status = status;
            this.mimeType = mimeType;
            try {
                this.data = new ByteArrayInputStream(txt.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }
        }

        public void addHeader(String name, String value) {
            this.header.put(name, value);
        }
    }

    private class myThreadFactory implements ThreadFactory {
        private myThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            Thread retval = new Thread(new ThreadGroup("biggerstack"), r, "HTTPD Session", 262144);
            retval.setDaemon(true);
            return retval;
        }
    }

    public Response serve(String uri, String method, Properties header, Properties parms, Properties files, Socket mySocket) {
        myOut.println(method + " '" + uri + "' ");
        Enumeration e = header.propertyNames();
        while (e.hasMoreElements()) {
            String value = (String) e.nextElement();
            myOut.println("  HDR: '" + value + "' = '" + header.getProperty(value) + "'");
        }
        e = parms.propertyNames();
        while (e.hasMoreElements()) {
            value = (String) e.nextElement();
            myOut.println("  PRM: '" + value + "' = '" + parms.getProperty(value) + "'");
        }
        e = files.propertyNames();
        while (e.hasMoreElements()) {
            value = (String) e.nextElement();
            myOut.println("  UPLOADED: '" + value + "' = '" + files.getProperty(value) + "'");
        }
        return serveFile(uri, header, this.myRootDir, true);
    }

    public NanoHTTPD(int port, File wwwroot) throws IOException {
        this.myTcpPort = port;
        this.myRootDir = wwwroot;
        this.myServerSocket = new ServerSocket(this.myTcpPort);
        this.myThread = new Thread(new C03361());
        this.myThread.setDaemon(true);
        this.myThread.start();
    }

    public void stop() {
        try {
            this.myServerSocket.close();
            this.myThread.join();
        } catch (IOException e) {
        } catch (InterruptedException e2) {
        }
    }

    public static void main(String[] args) {
        myOut.println("NanoHTTPD 1.25 (C) 2001,2005-2011 Jarno Elonen and (C) 2010 Konstantinos Togias\n(Command line options: [-p port] [-d root-dir] [--licence])\n");
        int port = 80;
        File wwwroot = new File(".").getAbsoluteFile();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-p")) {
                port = Integer.parseInt(args[i + 1]);
            } else if (args[i].equalsIgnoreCase("-d")) {
                wwwroot = new File(args[i + 1]).getAbsoluteFile();
            } else if (args[i].toLowerCase().endsWith("licence")) {
                myOut.println("Copyright (C) 2001,2005-2011 by Jarno Elonen <elonen@iki.fi>\nand Copyright (C) 2010 by Konstantinos Togias <info@ktogias.gr>\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions\nare met:\n\nRedistributions of source code must retain the above copyright notice,\nthis list of conditions and the following disclaimer. Redistributions in\nbinary form must reproduce the above copyright notice, this list of\nconditions and the following disclaimer in the documentation and/or other\nmaterials provided with the distribution. The name of the author may not\nbe used to endorse or promote products derived from this software without\nspecific prior written permission. \n \nTHIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR\nIMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\nOF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.\nIN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,\nINCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT\nNOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\nDATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\nTHEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\nOF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n");
                break;
            }
        }
        try {
            NanoHTTPD nanoHTTPD = new NanoHTTPD(port, wwwroot);
        } catch (IOException ioe) {
            myErr.println("Couldn't start server:\n" + ioe);
            System.exit(-1);
        }
        myOut.println("Now serving files in port " + port + " from \"" + wwwroot + "\"");
        myOut.println("Hit Enter to stop.\n");
        try {
            System.in.read();
        } catch (Throwable th) {
        }
    }

    private String encodeUri(String uri) {
        String newUri = "";
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("/")) {
                newUri = newUri + "/";
            } else if (tok.equals(" ")) {
                newUri = newUri + "%20";
            } else {
                newUri = newUri + URLEncoder.encode(tok);
            }
        }
        return newUri;
    }

    public Response serveFile(String uri, Properties header, File homeDir, boolean allowDirectoryListing) {
        Response res;
        String mime;
        int dot;
        String etag;
        long startFrom;
        long endAt;
        String range;
        int minus;
        long fileLen;
        Response response = null;
        if (!homeDir.isDirectory()) {
            Response response2 = new Response(HTTP_INTERNALERROR, MIME_PLAINTEXT, "INTERNAL ERRROR: serveFile(): given homeDir is not a directory.");
        }
        if (response == null) {
            uri = uri.trim().replace(File.separatorChar, '/');
            if (uri.indexOf(63) >= 0) {
                uri = uri.substring(0, uri.indexOf(63));
            }
            if (uri.startsWith("..") || uri.endsWith("..") || uri.indexOf("../") >= 0) {
                response2 = new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Won't serve ../ for security reasons.");
            }
        }
        File f = new File(homeDir, uri);
        if (response == null && !f.exists()) {
            response2 = new Response(HTTP_NOTFOUND, MIME_PLAINTEXT, "Error 404, file not found.");
        }
        if (response == null && f.isDirectory()) {
            if (!uri.endsWith("/")) {
                uri = uri + "/";
                response2 = new Response(HTTP_REDIRECT, MIME_HTML, "<html><body>Redirected: <a href=\"" + uri + "\">" + uri + "</a></body></html>");
                response2.addHeader("Location", uri);
            }
            if (response == null) {
                if (new File(f, "index.html").exists()) {
                    f = new File(homeDir, uri + "/index.html");
                    res = response;
                } else if (new File(f, "index.htm").exists()) {
                    f = new File(homeDir, uri + "/index.htm");
                    res = response;
                } else if (allowDirectoryListing && f.canRead()) {
                    String[] files = f.list();
                    String msg = "<html><body><h1>Directory " + uri + "</h1><br/>";
                    if (uri.length() > 1) {
                        String u = uri.substring(0, uri.length() - 1);
                        int slash = u.lastIndexOf(47);
                        if (slash >= 0 && slash < u.length()) {
                            msg = msg + "<b><a href=\"" + uri.substring(0, slash + 1) + "\">..</a></b><br/>";
                        }
                    }
                    if (files != null) {
                        for (int i = 0; i < files.length; i++) {
                            File curFile = new File(f, files[i]);
                            boolean dir = curFile.isDirectory();
                            if (dir) {
                                msg = msg + "<b>";
                                files[i] = files[i] + "/";
                            }
                            msg = msg + "<a href=\"" + encodeUri(uri + files[i]) + "\">" + files[i] + "</a>";
                            if (curFile.isFile()) {
                                long len = curFile.length();
                                msg = msg + " &nbsp;<font size=2>(";
                                if (len < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                                    msg = msg + len + " bytes";
                                } else if (len < 1048576) {
                                    msg = msg + (len / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) + "." + (((len % PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / 10) % 100) + " KB";
                                } else {
                                    msg = msg + (len / 1048576) + "." + (((len % 1048576) / 10) % 100) + " MB";
                                }
                                msg = msg + ")</font>";
                            }
                            msg = msg + "<br/>";
                            if (dir) {
                                msg = msg + "</b>";
                            }
                        }
                    }
                    msg = msg + "</body></html>";
                    res = new Response(HTTP_OK, MIME_HTML, msg);
                } else {
                    res = new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: No directory listing.");
                }
                if (res != null) {
                    mime = null;
                    dot = f.getCanonicalPath().lastIndexOf(46);
                    if (dot >= 0) {
                        mime = (String) theMimeTypes.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());
                    }
                    if (mime == null) {
                        mime = MIME_DEFAULT_BINARY;
                    }
                    etag = Integer.toHexString((f.getAbsolutePath() + f.lastModified() + "" + f.length()).hashCode());
                    startFrom = 0;
                    endAt = -1;
                    range = header.getProperty("range");
                    if (range != null && range.startsWith("bytes=")) {
                        range = range.substring("bytes=".length());
                        minus = range.indexOf(45);
                        if (minus > 0) {
                            try {
                                startFrom = Long.parseLong(range.substring(0, minus));
                                endAt = Long.parseLong(range.substring(minus + 1));
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                    try {
                        fileLen = f.length();
                        if (range != null || startFrom < 0) {
                            if (etag.equals(header.getProperty("if-none-match"))) {
                                response2 = new Response(HTTP_OK, mime, new FileInputStream(f));
                                response2.addHeader("Content-Length", "" + fileLen);
                                response2.addHeader("ETag", etag);
                            } else {
                                response2 = new Response(HTTP_NOTMODIFIED, mime, "");
                            }
                        } else if (startFrom >= fileLen) {
                            response2 = new Response(HTTP_RANGE_NOT_SATISFIABLE, MIME_PLAINTEXT, "");
                            try {
                                response2.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                                response2.addHeader("ETag", etag);
                            } catch (IOException e2) {
                                response2 = new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Reading file failed.");
                                response.addHeader("Accept-Ranges", "bytes");
                                return response;
                            }
                        } else {
                            if (endAt < 0) {
                                endAt = fileLen - 1;
                            }
                            long newLen = (endAt - startFrom) + 1;
                            if (newLen < 0) {
                                newLen = 0;
                            }
                            final long dataLen = newLen;
                            InputStream c03372 = new FileInputStream(f) {
                                public int available() throws IOException {
                                    return (int) dataLen;
                                }
                            };
                            c03372.skip(startFrom);
                            response2 = new Response(HTTP_PARTIALCONTENT, mime, c03372);
                            response2.addHeader("Content-Length", "" + dataLen);
                            response2.addHeader("Content-Range", "bytes " + startFrom + "-" + endAt + "/" + fileLen);
                            response2.addHeader("ETag", etag);
                        }
                    } catch (IOException e3) {
                        response = res;
                        response2 = new Response(HTTP_FORBIDDEN, MIME_PLAINTEXT, "FORBIDDEN: Reading file failed.");
                        response.addHeader("Accept-Ranges", "bytes");
                        return response;
                    }
                }
                response = res;
                response.addHeader("Accept-Ranges", "bytes");
                return response;
            }
        }
        res = response;
        if (res != null) {
            response = res;
        } else {
            mime = null;
            dot = f.getCanonicalPath().lastIndexOf(46);
            if (dot >= 0) {
                mime = (String) theMimeTypes.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());
            }
            if (mime == null) {
                mime = MIME_DEFAULT_BINARY;
            }
            etag = Integer.toHexString((f.getAbsolutePath() + f.lastModified() + "" + f.length()).hashCode());
            startFrom = 0;
            endAt = -1;
            range = header.getProperty("range");
            range = range.substring("bytes=".length());
            minus = range.indexOf(45);
            if (minus > 0) {
                startFrom = Long.parseLong(range.substring(0, minus));
                endAt = Long.parseLong(range.substring(minus + 1));
            }
            fileLen = f.length();
            if (range != null) {
            }
            if (etag.equals(header.getProperty("if-none-match"))) {
                response2 = new Response(HTTP_OK, mime, new FileInputStream(f));
                response2.addHeader("Content-Length", "" + fileLen);
                response2.addHeader("ETag", etag);
            } else {
                response2 = new Response(HTTP_NOTMODIFIED, mime, "");
            }
        }
        response.addHeader("Accept-Ranges", "bytes");
        return response;
    }

    static {
        StringTokenizer st = new StringTokenizer("css            text/css htm            text/html html           text/html xml            text/xml txt            text/plain asc            text/plain gif            image/gif jpg            image/jpeg jpeg           image/jpeg png            image/png mp3            audio/mpeg m3u            audio/mpeg-url mp4            video/mp4 ogv            video/ogg flv            video/x-flv mov            video/quicktime swf            application/x-shockwave-flash js                     application/javascript pdf            application/pdf doc            application/msword ogg            application/x-ogg zip            application/octet-stream exe            application/octet-stream class          application/octet-stream ");
        while (st.hasMoreTokens()) {
            theMimeTypes.put(st.nextToken(), st.nextToken());
        }
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
}

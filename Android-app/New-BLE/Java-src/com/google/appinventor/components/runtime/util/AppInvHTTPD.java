package com.google.appinventor.components.runtime.util;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.appinventor.components.common.YaVersion;
import com.google.appinventor.components.runtime.ReplForm;
import com.google.appinventor.components.runtime.util.NanoHTTPD.Response;
import gnu.expr.Language;
import gnu.expr.ModuleExp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppInvHTTPD extends NanoHTTPD {
    private static final String LOG_TAG = "AppInvHTTPD";
    private static final String MIME_JSON = "application/json";
    private static final int YAV_SKEW_BACKWARD = 4;
    private static final int YAV_SKEW_FORWARD = 1;
    private static byte[] hmacKey;
    private static int seq;
    private final Handler androidUIHandler = new Handler();
    private ReplForm form;
    private File rootDir;
    private Language scheme;
    private boolean secure;

    class C03161 implements Runnable {
        C03161() {
        }

        public void run() {
            AppInvHTTPD.this.form.clear();
        }
    }

    public AppInvHTTPD(int port, File wwwroot, boolean secure, ReplForm form) throws IOException {
        super(port, wwwroot);
        this.rootDir = wwwroot;
        this.scheme = Language.getInstance("scheme");
        this.form = form;
        this.secure = secure;
        ModuleExp.mustNeverCompile();
    }

    public Response serve(String uri, String method, Properties header, Properties parms, Properties files, Socket mySocket) {
        Log.d(LOG_TAG, method + " '" + uri + "' ");
        if (this.secure) {
            String hostAddress = mySocket.getInetAddress().getHostAddress();
            if (!hostAddress.equals("127.0.0.1")) {
                Log.d(LOG_TAG, "Debug: hostAddress = " + hostAddress + " while in secure mode, closing connection.");
                Response response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid Source Location " + hostAddress + "\"}");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                return response;
            }
        }
        Enumeration e;
        String value;
        if (method.equals("OPTIONS")) {
            e = header.propertyNames();
            while (e.hasMoreElements()) {
                value = (String) e.nextElement();
                Log.d(LOG_TAG, "  HDR: '" + value + "' = '" + header.getProperty(value) + "'");
            }
            response = new Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "OK");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            return response;
        } else if (uri.equals("/_newblocks")) {
            adoptMainThreadClassLoader();
            String inSeq = parms.getProperty("seq", "0");
            int iseq = Integer.parseInt(inSeq);
            String blockid = parms.getProperty("blockid");
            String code = parms.getProperty("code");
            inMac = parms.getProperty("mac", "no key provided");
            compMac = "";
            String input_code = code;
            if (hmacKey != null) {
                try {
                    hmacSha1 = Mac.getInstance("HmacSHA1");
                    hmacSha1.init(new SecretKeySpec(hmacKey, "RAW"));
                    tmpMac = hmacSha1.doFinal((code + inSeq + blockid).getBytes());
                    r0 = new StringBuffer(tmpMac.length * 2);
                    r0 = new Formatter(r0);
                    len$ = tmpMac.length;
                    for (i$ = 0; i$ < len$; i$++) {
                        r0.format("%02x", new Object[]{Byte.valueOf(arr$[i$])});
                    }
                    compMac = r0.toString();
                    Log.d(LOG_TAG, "Incoming Mac = " + inMac);
                    Log.d(LOG_TAG, "Computed Mac = " + compMac);
                    Log.d(LOG_TAG, "Incoming seq = " + inSeq);
                    Log.d(LOG_TAG, "Computed seq = " + seq);
                    Log.d(LOG_TAG, "blockid = " + blockid);
                    if (!inMac.equals(compMac)) {
                        Log.e(LOG_TAG, "Hmac does not match");
                        this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "Invalid HMAC");
                        return new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid MAC\"}");
                    } else if (seq == iseq || seq == iseq + 1) {
                        if (seq == iseq + 1) {
                            Log.e(LOG_TAG, "Seq Fixup Invoked");
                        }
                        seq = iseq + 1;
                        code = "(begin (require <com.google.youngandroid.runtime>) (process-repl-input " + blockid + " (begin " + code + " )))";
                        Log.d(LOG_TAG, "To Eval: " + code);
                        try {
                            if (input_code.equals("#f")) {
                                Log.e(LOG_TAG, "Skipping evaluation of #f");
                            } else {
                                this.scheme.eval(code);
                            }
                            response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, RetValManager.fetch(false));
                        } catch (Throwable ex) {
                            Log.e(LOG_TAG, "newblocks: Scheme Failure", ex);
                            RetValManager.appendReturnValue(blockid, "BAD", ex.toString());
                            response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, RetValManager.fetch(false));
                        }
                        res.addHeader("Access-Control-Allow-Origin", "*");
                        res.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                        res.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                        res.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                        return res;
                    } else {
                        Log.e(LOG_TAG, "Seq does not match");
                        this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "Invalid Seq");
                        return new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid Seq\"}");
                    }
                } catch (Exception e2) {
                    Log.e(LOG_TAG, "Error working with hmac", e2);
                    this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "Exception working on HMAC");
                    return new Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "NOT");
                }
            }
            Log.e(LOG_TAG, "No HMAC Key");
            this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "No HMAC Key");
            return new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: No HMAC Key\"}");
        } else if (uri.equals("/_values")) {
            response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, RetValManager.fetch(true));
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            return response;
        } else if (uri.equals("/_getversion")) {
            try {
                String installer;
                String packageName = this.form.getPackageName();
                PackageInfo pInfo = this.form.getPackageManager().getPackageInfo(packageName, 0);
                if (SdkLevel.getLevel() >= 5) {
                    installer = EclairUtil.getInstallerPackageName(YaVersion.ACCEPTABLE_COMPANION_PACKAGE, this.form);
                } else {
                    installer = "Not Known";
                }
                String versionName = pInfo.versionName;
                if (installer == null) {
                    installer = "Not Known";
                }
                response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"version\" : \"" + versionName + "\", \"fingerprint\" : \"" + Build.FINGERPRINT + "\"," + " \"installer\" : \"" + installer + "\", \"package\" : \"" + packageName + "\", \"fqcn\" : true }");
            } catch (NameNotFoundException n) {
                n.printStackTrace();
                response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"verison\" : \"Unknown\"");
            }
            res.addHeader("Access-Control-Allow-Origin", "*");
            res.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            res.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            res.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            if (!this.secure) {
                return res;
            }
            seq = 1;
            this.androidUIHandler.post(new C03161());
            return res;
        } else if (uri.equals("/_update") || uri.equals("/_install")) {
            String url = parms.getProperty("url", "");
            inMac = parms.getProperty("mac", "");
            if (url.equals("") || hmacKey == null || inMac.equals("")) {
                response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Missing Parameters\"}");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                return response;
            }
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(hmacKey, "RAW");
                hmacSha1 = Mac.getInstance("HmacSHA1");
                hmacSha1.init(secretKeySpec);
                tmpMac = hmacSha1.doFinal(url.getBytes());
                r0 = new StringBuffer(tmpMac.length * 2);
                r0 = new Formatter(r0);
                len$ = tmpMac.length;
                for (i$ = 0; i$ < len$; i$++) {
                    r0.format("%02x", new Object[]{Byte.valueOf(arr$[i$])});
                }
                compMac = r0.toString();
                Log.d(LOG_TAG, "Incoming Mac (update) = " + inMac);
                Log.d(LOG_TAG, "Computed Mac (update) = " + compMac);
                if (inMac.equals(compMac)) {
                    doPackageUpdate(url);
                    response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"OK\", \"message\" : \"Update Should Happen\"}");
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                    response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                    response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                    return response;
                }
                Log.e(LOG_TAG, "Hmac does not match");
                this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "Invalid HMAC (update)");
                response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: Invalid MAC\"}");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                return response;
            } catch (Exception e22) {
                Log.e(LOG_TAG, "Error verifying update", e22);
                this.form.dispatchErrorOccurredEvent(this.form, LOG_TAG, ErrorMessages.ERROR_REPL_SECURITY_ERROR, "Exception working on HMAC for update");
                response = new Response(NanoHTTPD.HTTP_OK, MIME_JSON, "{\"status\" : \"BAD\", \"message\" : \"Security Error: Exception processing MAC\"}");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                return response;
            }
        } else if (uri.equals("/_package")) {
            String packageapk = parms.getProperty("package", null);
            if (packageapk == null) {
                return new Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "NOT OK");
            }
            Log.d(LOG_TAG, this.rootDir + "/" + packageapk);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(new File(this.rootDir + "/" + packageapk)), "application/vnd.android.package-archive");
            this.form.startActivity(intent);
            response = new Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "OK");
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
            response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
            response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
            return response;
        } else if (uri.equals("/_extensions")) {
            return processLoadExtensionsRequest(parms);
        } else {
            File fileFrom;
            String filename;
            File fileTo;
            if (method.equals("PUT")) {
                Boolean error = Boolean.valueOf(false);
                String tmpFileName = files.getProperty("content", null);
                if (tmpFileName != null) {
                    fileFrom = new File(tmpFileName);
                    filename = parms.getProperty("filename", null);
                    if (filename != null && (filename.startsWith("..") || filename.endsWith("..") || filename.indexOf("../") >= 0)) {
                        Log.d(LOG_TAG, " Ignoring invalid filename: " + filename);
                        filename = null;
                    }
                    if (filename != null) {
                        fileTo = new File(this.rootDir + "/" + filename);
                        File parentFileTo = fileTo.getParentFile();
                        if (!parentFileTo.exists()) {
                            parentFileTo.mkdirs();
                        }
                        if (!fileFrom.renameTo(fileTo)) {
                            copyFile(fileFrom, fileTo);
                            fileFrom.delete();
                        }
                    } else {
                        fileFrom.delete();
                        Log.e(LOG_TAG, "Received content without a file name!");
                        error = Boolean.valueOf(true);
                    }
                } else {
                    Log.e(LOG_TAG, "Received PUT without content.");
                    error = Boolean.valueOf(true);
                }
                if (error.booleanValue()) {
                    response = new Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "NOTOK");
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                    response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                    response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                    return response;
                }
                response = new Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "OK");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                return response;
            }
            e = header.propertyNames();
            while (e.hasMoreElements()) {
                value = (String) e.nextElement();
                Log.d(LOG_TAG, "  HDR: '" + value + "' = '" + header.getProperty(value) + "'");
            }
            e = parms.propertyNames();
            while (e.hasMoreElements()) {
                value = (String) e.nextElement();
                Log.d(LOG_TAG, "  PRM: '" + value + "' = '" + parms.getProperty(value) + "'");
            }
            e = files.propertyNames();
            if (e.hasMoreElements()) {
                String fieldname = (String) e.nextElement();
                String tempLocation = files.getProperty(fieldname);
                filename = parms.getProperty(fieldname);
                if (filename.startsWith("..") || filename.endsWith("..") || filename.indexOf("../") >= 0) {
                    Log.d(LOG_TAG, " Ignoring invalid filename: " + filename);
                    filename = null;
                }
                fileFrom = new File(tempLocation);
                if (filename == null) {
                    fileFrom.delete();
                } else {
                    fileTo = new File(this.rootDir + "/" + filename);
                    if (!fileFrom.renameTo(fileTo)) {
                        copyFile(fileFrom, fileTo);
                        fileFrom.delete();
                    }
                }
                Log.d(LOG_TAG, " UPLOADED: '" + filename + "' was at '" + tempLocation + "'");
                response = new Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, "OK");
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.addHeader("Access-Control-Allow-Headers", "origin, content-type");
                response.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
                response.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
                return response;
            }
            return serveFile(uri, header, this.rootDir, true);
        }
    }

    private void copyFile(File infile, File outfile) {
        try {
            FileInputStream in = new FileInputStream(infile);
            FileOutputStream out = new FileOutputStream(outfile);
            byte[] buffer = new byte[32768];
            while (true) {
                int len = in.read(buffer);
                if (len > 0) {
                    out.write(buffer, 0, len);
                } else {
                    in.close();
                    out.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response processLoadExtensionsRequest(Properties parms) {
        try {
            JSONArray array = new JSONArray(parms.getProperty("extensions", "[]"));
            List<String> extensionsToLoad = new ArrayList();
            for (int i = 0; i < array.length(); i++) {
                String extensionName = array.optString(i);
                if (extensionName == null) {
                    return error("Invalid JSON content at index " + i);
                }
                extensionsToLoad.add(extensionName);
            }
            try {
                this.form.loadComponents(extensionsToLoad);
                return message("OK");
            } catch (Throwable e) {
                return error(e);
            }
        } catch (Throwable e2) {
            return error(e2);
        }
    }

    private void adoptMainThreadClassLoader() {
        ClassLoader mainClassLoader = Looper.getMainLooper().getThread().getContextClassLoader();
        Thread myThread = Thread.currentThread();
        if (myThread.getContextClassLoader() != mainClassLoader) {
            myThread.setContextClassLoader(mainClassLoader);
        }
    }

    private Response message(String txt) {
        return addHeaders(new Response(NanoHTTPD.HTTP_OK, NanoHTTPD.MIME_PLAINTEXT, txt));
    }

    private Response json(String json) {
        return addHeaders(new Response(NanoHTTPD.HTTP_OK, MIME_JSON, json));
    }

    private Response error(String msg) {
        JSONObject result = new JSONObject();
        try {
            result.put("status", "BAD");
            result.put("message", msg);
        } catch (JSONException e) {
            Log.wtf(LOG_TAG, "Unable to write basic JSON content", e);
        }
        return addHeaders(new Response(NanoHTTPD.HTTP_OK, MIME_JSON, result.toString()));
    }

    private Response error(Throwable t) {
        return error(t.toString());
    }

    private Response addHeaders(Response res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Headers", "origin, content-type");
        res.addHeader("Access-Control-Allow-Methods", "POST,OPTIONS,GET,HEAD,PUT");
        res.addHeader("Allow", "POST,OPTIONS,GET,HEAD,PUT");
        return res;
    }

    public static void setHmacKey(String inputKey) {
        hmacKey = inputKey.getBytes();
        seq = 1;
    }

    private void doPackageUpdate(String inurl) {
        PackageInstaller.doPackageInstall(this.form, inurl);
    }

    public void resetSeq() {
        seq = 1;
    }
}

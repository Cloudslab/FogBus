package com.google.appinventor.components.runtime.util;

import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebServiceUtil {
    private static final WebServiceUtil INSTANCE = new WebServiceUtil();
    private static final String LOG_TAG = "WebServiceUtil";
    private static HttpClient httpClient = null;
    private static Object httpClientSynchronizer = new Object();

    private WebServiceUtil() {
    }

    public static WebServiceUtil getInstance() {
        synchronized (httpClientSynchronizer) {
            if (httpClient == null) {
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
                BasicHttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 20000);
                HttpConnectionParams.setSoTimeout(params, 20000);
                ConnManagerParams.setMaxTotalConnections(params, 20);
                httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(params, schemeRegistry), params);
            }
        }
        return INSTANCE;
    }

    public void postCommandReturningArray(String serviceURL, String commandName, List<NameValuePair> params, final AsyncCallbackPair<JSONArray> callback) {
        postCommand(serviceURL, commandName, params, new AsyncCallbackPair<String>() {
            public void onSuccess(String httpResponseString) {
                try {
                    callback.onSuccess(new JSONArray(httpResponseString));
                } catch (JSONException e) {
                    callback.onFailure(e.getMessage());
                }
            }

            public void onFailure(String failureMessage) {
                callback.onFailure(failureMessage);
            }
        });
    }

    public void postCommandReturningObject(String serviceURL, String commandName, List<NameValuePair> params, final AsyncCallbackPair<JSONObject> callback) {
        postCommand(serviceURL, commandName, params, new AsyncCallbackPair<String>() {
            public void onSuccess(String httpResponseString) {
                try {
                    callback.onSuccess(new JSONObject(httpResponseString));
                } catch (JSONException e) {
                    callback.onFailure(e.getMessage());
                }
            }

            public void onFailure(String failureMessage) {
                callback.onFailure(failureMessage);
            }
        });
    }

    public void postCommand(String serviceURL, String commandName, List<NameValuePair> params, AsyncCallbackPair<String> callback) {
        Log.d(LOG_TAG, "Posting " + commandName + " to " + serviceURL + " with arguments " + params);
        if (serviceURL == null || serviceURL.equals("")) {
            callback.onFailure("No service url to post command to.");
        }
        HttpPost httpPost = new HttpPost(serviceURL + "/" + commandName);
        if (params == null) {
            params = new ArrayList();
        }
        try {
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpPost.setHeader("Accept", "application/json");
            callback.onSuccess((String) httpClient.execute(httpPost, responseHandler));
        } catch (UnsupportedEncodingException e) {
            Log.w(LOG_TAG, e);
            callback.onFailure("Failed to encode params for web service call.");
        } catch (ClientProtocolException e2) {
            Log.w(LOG_TAG, e2);
            callback.onFailure("Communication with the web service encountered a protocol exception.");
        } catch (IOException e3) {
            Log.w(LOG_TAG, e3);
            callback.onFailure("Communication with the web service timed out.");
        }
    }
}

package com.google.appinventor.components.runtime;

import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.AsyncCallbackPair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

@DesignerComponent(category = ComponentCategory.INTERNAL, description = "Non-visible component that communicates with a Web service and stores media files.", iconName = "images/mediastore.png", nonVisible = true, version = 1)
@UsesLibraries(libraries = "httpcore-4.3.2.jar,httpmime-4.3.4.jar")
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public final class MediaStore extends AndroidNonvisibleComponent implements Component {
    private static final String LOG_TAG_COMPONENT = "MediaStore: ";
    private Handler androidUIHandler = new Handler();
    protected final ComponentContainer componentContainer;
    private String serviceURL = "http://ai-mediaservice.appspot.com";

    class C04371 implements AsyncCallbackPair<String> {
        C04371() {
        }

        public void onSuccess(final String response) {
            MediaStore.this.androidUIHandler.post(new Runnable() {
                public void run() {
                    MediaStore.this.MediaStored(response);
                }
            });
        }

        public void onFailure(final String message) {
            MediaStore.this.androidUIHandler.post(new Runnable() {
                public void run() {
                    MediaStore.this.WebServiceError(message);
                }
            });
        }
    }

    public MediaStore(ComponentContainer container) {
        super(container.$form());
        this.componentContainer = container;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String ServiceURL() {
        return this.serviceURL;
    }

    @DesignerProperty(defaultValue = "http://ai-mediaservice.appspot.com", editorType = "string")
    @SimpleProperty
    public void ServiceURL(String url) {
        this.serviceURL = url;
    }

    @SimpleFunction
    public void PostMedia(String mediafile) throws FileNotFoundException {
        AsyncCallbackPair<String> myCallback = new C04371();
        try {
            String newMediaPath;
            HttpClient client = new DefaultHttpClient();
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            if (mediafile.split("/")[0].equals("file:")) {
                newMediaPath = new File(new URL(mediafile).toURI()).getAbsolutePath();
            } else {
                newMediaPath = mediafile;
            }
            entityBuilder.addPart("file", new FileBody(new File(newMediaPath)));
            HttpEntity entity = entityBuilder.build();
            HttpPost post = new HttpPost(getUploadUrl());
            post.setEntity(entity);
            myCallback.onSuccess(EntityUtils.toString(client.execute(post).getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            myCallback.onFailure(e.getMessage());
        }
    }

    private String getUploadUrl() {
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(this.serviceURL).openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "AppInventor");
            con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            while (true) {
                String inputLine = in.readLine();
                if (inputLine != null) {
                    response.append(inputLine);
                } else {
                    in.close();
                    return response.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @SimpleEvent
    public void MediaStored(String url) {
        EventDispatcher.dispatchEvent(this, "MediaStored", url);
    }

    @SimpleEvent
    public void WebServiceError(String message) {
        EventDispatcher.dispatchEvent(this, "WebServiceError", message);
    }
}

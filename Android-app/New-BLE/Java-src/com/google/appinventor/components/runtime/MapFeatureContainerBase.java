package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.util.Log;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.GeoJSONUtil;
import com.google.appinventor.components.runtime.util.MapFactory.MapCircle;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeature;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor;
import com.google.appinventor.components.runtime.util.MapFactory.MapLineString;
import com.google.appinventor.components.runtime.util.MapFactory.MapMarker;
import com.google.appinventor.components.runtime.util.MapFactory.MapPolygon;
import com.google.appinventor.components.runtime.util.MapFactory.MapRectangle;
import com.google.appinventor.components.runtime.util.YailList;
import gnu.kawa.servlet.HttpRequestContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SimpleObject
public abstract class MapFeatureContainerBase extends AndroidViewComponent implements MapFeatureContainer {
    private static final int ERROR_CODE_IO_EXCEPTION = -2;
    private static final int ERROR_CODE_MALFORMED_GEOJSON = -3;
    private static final int ERROR_CODE_MALFORMED_URL = -1;
    private static final int ERROR_CODE_UNKNOWN_TYPE = -4;
    private static final String ERROR_IO_EXCEPTION = "Unable to download content from URL";
    private static final String ERROR_MALFORMED_GEOJSON = "Malformed GeoJSON response. Expected FeatureCollection as root element.";
    private static final String ERROR_MALFORMED_URL = "The URL is malformed";
    private static final String ERROR_UNKNOWN_TYPE = "Unrecognized/invalid type in JSON object";
    private static final String GEOJSON_FEATURECOLLECTION = "FeatureCollection";
    private static final String GEOJSON_FEATURES = "features";
    private static final String GEOJSON_GEOMETRYCOLLECTION = "GeometryCollection";
    private static final String GEOJSON_TYPE = "type";
    private static final String TAG = MapFeatureContainerBase.class.getSimpleName();
    private final MapFeatureVisitor<Void> featureAdder = new C04291();
    protected List<MapFeature> features = new CopyOnWriteArrayList();

    class C04291 implements MapFeatureVisitor<Void> {
        C04291() {
        }

        public Void visit(MapMarker marker, Object... arguments) {
            MapFeatureContainerBase.this.addFeature(marker);
            return null;
        }

        public Void visit(MapLineString lineString, Object... arguments) {
            MapFeatureContainerBase.this.addFeature(lineString);
            return null;
        }

        public Void visit(MapPolygon polygon, Object... arguments) {
            MapFeatureContainerBase.this.addFeature(polygon);
            return null;
        }

        public Void visit(MapCircle circle, Object... arguments) {
            MapFeatureContainerBase.this.addFeature(circle);
            return null;
        }

        public Void visit(MapRectangle rectangle, Object... arguments) {
            MapFeatureContainerBase.this.addFeature(rectangle);
            return null;
        }
    }

    protected MapFeatureContainerBase(ComponentContainer container) {
        super(container);
    }

    @SimpleProperty
    public void Features(YailList features) {
        for (MapFeature feature : this.features) {
            feature.removeFromMap();
        }
        this.features.clear();
        ListIterator<?> it = features.listIterator(1);
        while (it.hasNext()) {
            Object o = it.next();
            if (o instanceof MapFeature) {
                addFeature((MapFeature) o);
            }
        }
        getMap().getView().invalidate();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The list of features placed on this map. This list also includes any features created by calls to FeatureFromDescription")
    public YailList Features() {
        return YailList.makeList(this.features);
    }

    @SimpleEvent(description = "The user clicked on a map feature.")
    public void FeatureClick(MapFeature feature) {
        EventDispatcher.dispatchEvent(this, "FeatureClick", feature);
        if (getMap() != this) {
            getMap().FeatureClick(feature);
        }
    }

    @SimpleEvent(description = "The user long-pressed on a map feature.")
    public void FeatureLongClick(MapFeature feature) {
        EventDispatcher.dispatchEvent(this, "FeatureLongClick", feature);
        if (getMap() != this) {
            getMap().FeatureLongClick(feature);
        }
    }

    @SimpleEvent(description = "The user started dragging a map feature.")
    public void FeatureStartDrag(MapFeature feature) {
        EventDispatcher.dispatchEvent(this, "FeatureStartDrag", feature);
        if (getMap() != this) {
            getMap().FeatureStartDrag(feature);
        }
    }

    @SimpleEvent(description = "The user dragged a map feature.")
    public void FeatureDrag(MapFeature feature) {
        EventDispatcher.dispatchEvent(this, "FeatureDrag", feature);
        if (getMap() != this) {
            getMap().FeatureDrag(feature);
        }
    }

    @SimpleEvent(description = "The user stopped dragging a map feature.")
    public void FeatureStopDrag(MapFeature feature) {
        EventDispatcher.dispatchEvent(this, "FeatureStopDrag", feature);
        if (getMap() != this) {
            getMap().FeatureStopDrag(feature);
        }
    }

    @SimpleFunction(description = "<p>Load a feature collection in <a href=\"https://en.wikipedia.org/wiki/GeoJSON\">GeoJSON</a> format from the given url. On success, the event GotFeatures will be raised with the given url and a list of the features parsed from the GeoJSON as a list of (key, value) pairs. On failure, the LoadError event will be raised with any applicable HTTP response code and error message.</p>")
    public void LoadFromURL(final String url) {
        AsynchUtil.runAsynchronously(new Runnable() {
            public void run() {
                MapFeatureContainerBase.this.performGet(url);
            }
        });
    }

    @SimpleFunction
    public Object FeatureFromDescription(YailList description) {
        try {
            return GeoJSONUtil.processGeoJSONFeature(TAG, this, description);
        } catch (IllegalArgumentException e) {
            $form().dispatchErrorOccurredEvent(this, "FeatureFromDescription", -3, e.getMessage());
            return e.getMessage();
        }
    }

    @SimpleEvent(description = "A GeoJSON document was successfully read from url. The features specified in the document are provided as a list in features.")
    public void GotFeatures(String url, YailList features) {
        EventDispatcher.dispatchEvent(this, "GotFeatures", url, features);
    }

    @SimpleEvent(description = "An error was encountered while processing a GeoJSON document at the given url. The responseCode parameter will contain an HTTP status code and the errorMessage parameter will contain a detailed error message.")
    public void LoadError(String url, int responseCode, String errorMessage) {
        EventDispatcher.dispatchEvent(this, "LoadError", url, Integer.valueOf(responseCode), errorMessage);
    }

    public Activity $context() {
        return this.container.$context();
    }

    public Form $form() {
        return this.container.$form();
    }

    public void $add(AndroidViewComponent component) {
        throw new UnsupportedOperationException("Map.$add() called");
    }

    public void setChildWidth(AndroidViewComponent component, int width) {
        throw new UnsupportedOperationException("Map.setChildWidth called");
    }

    public void setChildHeight(AndroidViewComponent component, int height) {
        throw new UnsupportedOperationException("Map.setChildHeight called");
    }

    public void removeFeature(MapFeature feature) {
        this.features.remove(feature);
        getMap().removeFeature(feature);
    }

    void addFeature(MapMarker marker) {
        this.features.add(marker);
        getMap().addFeature(marker);
    }

    void addFeature(MapLineString polyline) {
        this.features.add(polyline);
        getMap().addFeature(polyline);
    }

    void addFeature(MapPolygon polygon) {
        this.features.add(polygon);
        getMap().addFeature(polygon);
    }

    void addFeature(MapCircle circle) {
        this.features.add(circle);
        getMap().addFeature(circle);
    }

    void addFeature(MapRectangle rectangle) {
        this.features.add(rectangle);
        getMap().addFeature(rectangle);
    }

    public void addFeature(MapFeature feature) {
        feature.accept(this.featureAdder, new Object[0]);
    }

    private void performGet(String url) {
        try {
            String jsonContent = loadUrl(url);
            if (jsonContent != null) {
                processGeoJSON(url, jsonContent);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception retreiving GeoJSON", e);
            $form().dispatchErrorOccurredEvent(this, "LoadFromURL", -4, e.toString());
        }
    }

    private String loadUrl(final String url) {
        String str = null;
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.connect();
            if (connection instanceof HttpURLConnection) {
                HttpURLConnection conn = (HttpURLConnection) connection;
                final int responseCode = conn.getResponseCode();
                final String responseMessage = conn.getResponseMessage();
                if (responseCode != HttpRequestContext.HTTP_OK) {
                    $form().runOnUiThread(new Runnable() {
                        public void run() {
                            MapFeatureContainerBase.this.LoadError(url, responseCode, responseMessage);
                        }
                    });
                    conn.disconnect();
                    return str;
                }
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder content = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                content.append(line);
                content.append("\n");
            }
            reader.close();
            str = content.toString();
        } catch (MalformedURLException e) {
            $form().runOnUiThread(new Runnable() {
                public void run() {
                    MapFeatureContainerBase.this.LoadError(url, -1, MapFeatureContainerBase.ERROR_MALFORMED_URL);
                }
            });
        } catch (IOException e2) {
            $form().runOnUiThread(new Runnable() {
                public void run() {
                    MapFeatureContainerBase.this.LoadError(url, -2, MapFeatureContainerBase.ERROR_IO_EXCEPTION);
                }
            });
        }
        return str;
    }

    protected void processGeoJSON(final String url, String content) throws JSONException {
        JSONObject parsedData = new JSONObject(content);
        String type = parsedData.optString(GEOJSON_TYPE);
        if (GEOJSON_FEATURECOLLECTION.equals(type) || GEOJSON_GEOMETRYCOLLECTION.equals(type)) {
            JSONArray features = parsedData.getJSONArray(GEOJSON_FEATURES);
            final List<YailList> yailFeatures = new ArrayList();
            for (int i = 0; i < features.length(); i++) {
                yailFeatures.add(jsonObjectToYail(features.getJSONObject(i)));
            }
            $form().runOnUiThread(new Runnable() {
                public void run() {
                    MapFeatureContainerBase.this.GotFeatures(url, YailList.makeList(yailFeatures));
                }
            });
            return;
        }
        $form().runOnUiThread(new Runnable() {
            public void run() {
                MapFeatureContainerBase.this.LoadError(url, -3, MapFeatureContainerBase.ERROR_MALFORMED_GEOJSON);
            }
        });
    }

    private YailList jsonObjectToYail(JSONObject object) throws JSONException {
        List pairs = new ArrayList();
        Iterator<String> j = object.keys();
        while (j.hasNext()) {
            Object value = object.get((String) j.next());
            if ((value instanceof Boolean) || (value instanceof Integer) || (value instanceof Long) || (value instanceof Double) || (value instanceof String)) {
                pairs.add(YailList.makeList(new Object[]{key, value}));
            } else if (value instanceof JSONArray) {
                pairs.add(YailList.makeList(new Object[]{key, jsonArrayToYail((JSONArray) value)}));
            } else if (value instanceof JSONObject) {
                pairs.add(YailList.makeList(new Object[]{key, jsonObjectToYail((JSONObject) value)}));
            } else {
                Log.wtf(TAG, ERROR_UNKNOWN_TYPE);
                throw new IllegalArgumentException(ERROR_UNKNOWN_TYPE);
            }
        }
        return YailList.makeList(pairs);
    }

    private YailList jsonArrayToYail(JSONArray array) throws JSONException {
        List items = new ArrayList();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if ((value instanceof Boolean) || (value instanceof Integer) || (value instanceof Long) || (value instanceof Double) || (value instanceof String)) {
                items.add(value);
            } else if (value instanceof JSONArray) {
                items.add(jsonArrayToYail((JSONArray) value));
            } else if (value instanceof JSONObject) {
                items.add(jsonObjectToYail((JSONObject) value));
            } else {
                Log.wtf(TAG, ERROR_UNKNOWN_TYPE);
                throw new IllegalArgumentException(ERROR_UNKNOWN_TYPE);
            }
        }
        return YailList.makeList(items);
    }
}

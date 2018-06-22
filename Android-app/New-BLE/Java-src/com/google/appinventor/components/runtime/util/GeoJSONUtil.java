package com.google.appinventor.components.runtime.util;

import android.text.TextUtils;
import android.util.Log;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.LineString;
import com.google.appinventor.components.runtime.Marker;
import com.google.appinventor.components.runtime.Polygon;
import com.google.appinventor.components.runtime.util.MapFactory.HasFill;
import com.google.appinventor.components.runtime.util.MapFactory.HasStroke;
import com.google.appinventor.components.runtime.util.MapFactory.MapCircle;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeature;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureType;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor;
import com.google.appinventor.components.runtime.util.MapFactory.MapLineString;
import com.google.appinventor.components.runtime.util.MapFactory.MapMarker;
import com.google.appinventor.components.runtime.util.MapFactory.MapPolygon;
import com.google.appinventor.components.runtime.util.MapFactory.MapRectangle;
import com.google.common.annotations.VisibleForTesting;
import gnu.bytecode.Access;
import gnu.lists.FString;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.osmdroid.util.GeoPoint;

public final class GeoJSONUtil {
    private static final String GEOJSON_COORDINATES = "coordinates";
    private static final String GEOJSON_FEATURE = "Feature";
    private static final String GEOJSON_GEOMETRY = "geometry";
    private static final String GEOJSON_PROPERTIES = "properties";
    private static final String GEOJSON_TYPE = "type";
    private static final int KEY = 1;
    private static final int LATITUDE = 2;
    private static final int LONGITUDE = 1;
    private static final String PROPERTY_ANCHOR_HORIZONTAL = "anchorHorizontal";
    private static final String PROPERTY_ANCHOR_VERTICAL = "anchorVertical";
    private static final String PROPERTY_DESCRIPTION = "description";
    private static final String PROPERTY_DRAGGABLE = "draggable";
    private static final String PROPERTY_FILL = "fill";
    private static final String PROPERTY_HEIGHT = "height";
    private static final String PROPERTY_IMAGE = "image";
    private static final String PROPERTY_INFOBOX = "infobox";
    private static final String PROPERTY_STROKE = "stroke";
    private static final String PROPERTY_STROKE_WIDTH = "stroke-width";
    private static final String PROPERTY_TITLE = "title";
    private static final String PROPERTY_VISIBLE = "visible";
    private static final String PROPERTY_WIDTH = "width";
    private static final Map<String, PropertyApplication> SUPPORTED_PROPERTIES = new HashMap();
    private static final int VALUE = 2;
    private static final Map<String, Integer> colors = new HashMap();

    private interface PropertyApplication {
        void apply(MapFeature mapFeature, Object obj);
    }

    static class C04401 implements PropertyApplication {
        C04401() {
        }

        public void apply(MapFeature feature, Object value) {
            if (feature instanceof MapMarker) {
                ((MapMarker) feature).AnchorHorizontal(GeoJSONUtil.parseIntegerOrString(value));
            }
        }
    }

    static class C04412 implements PropertyApplication {
        C04412() {
        }

        public void apply(MapFeature feature, Object value) {
            if (feature instanceof MapMarker) {
                ((MapMarker) feature).AnchorHorizontal();
            }
        }
    }

    static class C04423 implements PropertyApplication {
        C04423() {
        }

        public void apply(MapFeature feature, Object value) {
            feature.Description(value.toString());
        }
    }

    static class C04434 implements PropertyApplication {
        C04434() {
        }

        public void apply(MapFeature feature, Object value) {
            feature.Draggable(GeoJSONUtil.parseBooleanOrString(value));
        }
    }

    static class C04445 implements PropertyApplication {
        C04445() {
        }

        public void apply(MapFeature feature, Object value) {
            if (feature instanceof HasFill) {
                ((HasFill) feature).FillColor(value instanceof Number ? ((Number) value).intValue() : GeoJSONUtil.parseColor(value.toString()));
            }
        }
    }

    static class C04456 implements PropertyApplication {
        C04456() {
        }

        public void apply(MapFeature feature, Object value) {
            if (feature instanceof MapMarker) {
                ((MapMarker) feature).Height(GeoJSONUtil.parseIntegerOrString(value));
            }
        }
    }

    static class C04467 implements PropertyApplication {
        C04467() {
        }

        public void apply(MapFeature feature, Object value) {
            if (feature instanceof MapMarker) {
                ((MapMarker) feature).ImageAsset(value.toString());
            }
        }
    }

    static class C04478 implements PropertyApplication {
        C04478() {
        }

        public void apply(MapFeature feature, Object value) {
            feature.EnableInfobox(GeoJSONUtil.parseBooleanOrString(value));
        }
    }

    static class C04489 implements PropertyApplication {
        C04489() {
        }

        public void apply(MapFeature feature, Object value) {
            if (feature instanceof HasStroke) {
                ((HasStroke) feature).StrokeColor(value instanceof Number ? ((Number) value).intValue() : GeoJSONUtil.parseColor(value.toString()));
            }
        }
    }

    private static final class FeatureWriter implements MapFeatureVisitor<Void> {
        private final PrintStream out;

        private FeatureWriter(PrintStream out) {
            this.out = out;
        }

        private void writeType(String type) {
            this.out.print("\"type\":\"");
            this.out.print(type);
            this.out.print("\"");
        }

        private void writeProperty(String property, Object value) {
            try {
                String result = JsonUtil.getJsonRepresentation(value);
                this.out.print(",\"");
                this.out.print(property);
                this.out.print("\":");
                this.out.print(result);
            } catch (JSONException e) {
                Log.w("GeoJSONUtil", "Unable to serialize the value of \"" + property + "\" as JSON", e);
            }
        }

        private void writeProperty(String property, String value) {
            if (value != null && !TextUtils.isEmpty(value)) {
                writeProperty(property, (Object) value);
            }
        }

        private void writeColorProperty(String property, int color) {
            this.out.print(",\"");
            this.out.print(property);
            this.out.print("\":\"&H");
            String unpadded = Integer.toHexString(color);
            for (int i = 8; i > unpadded.length(); i--) {
                this.out.print("0");
            }
            this.out.print(unpadded);
            this.out.print("\"");
        }

        private void writePointGeometry(GeoPoint point) {
            this.out.print("\"geometry\":{\"type\":\"Point\",\"coordinates\":[");
            this.out.print(point.getLongitude());
            this.out.print(",");
            this.out.print(point.getLatitude());
            if (hasAltitude(point)) {
                this.out.print(",");
                this.out.print(point.getAltitude());
            }
            this.out.print("]}");
        }

        private void writePropertiesHeader(String runtimeType) {
            this.out.print(",\"properties\":{\"$Type\":\"" + runtimeType + "\"");
        }

        private void writeProperties(MapFeature feature) {
            writeProperty(GeoJSONUtil.PROPERTY_DESCRIPTION, feature.Description());
            writeProperty(GeoJSONUtil.PROPERTY_DRAGGABLE, Boolean.valueOf(feature.Draggable()));
            writeProperty(GeoJSONUtil.PROPERTY_INFOBOX, Boolean.valueOf(feature.EnableInfobox()));
            writeProperty(GeoJSONUtil.PROPERTY_TITLE, feature.Title());
            writeProperty(GeoJSONUtil.PROPERTY_VISIBLE, Boolean.valueOf(feature.Visible()));
        }

        private void writeProperties(HasStroke feature) {
            writeColorProperty(GeoJSONUtil.PROPERTY_STROKE, feature.StrokeColor());
            writeProperty(GeoJSONUtil.PROPERTY_STROKE_WIDTH, Integer.valueOf(feature.StrokeWidth()));
        }

        private void writeProperties(HasFill feature) {
            writeColorProperty(GeoJSONUtil.PROPERTY_FILL, feature.FillColor());
        }

        private void writePoints(List<GeoPoint> points) {
            boolean first = true;
            for (GeoPoint p : points) {
                if (!first) {
                    this.out.print(',');
                }
                this.out.print("[");
                this.out.print(p.getLongitude());
                this.out.print(",");
                this.out.print(p.getLatitude());
                if (hasAltitude(p)) {
                    this.out.print(",");
                    this.out.print(p.getAltitude());
                }
                this.out.print("]");
                first = false;
            }
        }

        private void writeLineGeometry(MapLineString lineString) {
            this.out.print("\"geometry\":{\"type\":\"LineString\",\"coordinates\":[");
            writePoints(lineString.getPoints());
            this.out.print("]}");
        }

        private void writeMultipolygonGeometryNoHoles(MapPolygon polygon) {
            this.out.print("\"geometry\":{\"type\":\"MultiPolygon\",\"coordinates\":[");
            Iterator<List<List<GeoPoint>>> holePointIterator = polygon.getHolePoints().iterator();
            boolean first = true;
            for (List writePoints : polygon.getPoints()) {
                if (!first) {
                    this.out.print(",");
                }
                this.out.print("[");
                writePoints(writePoints);
                if (holePointIterator.hasNext()) {
                    for (List<GeoPoint> holePoints : (List) holePointIterator.next()) {
                        this.out.print(",");
                        writePoints(holePoints);
                    }
                }
                this.out.print("]");
                first = false;
            }
            this.out.print("]}");
        }

        private void writePolygonGeometryNoHoles(MapPolygon polygon) {
            this.out.print("\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[");
            writePoints((List) polygon.getPoints().get(0));
            if (!polygon.getHolePoints().isEmpty()) {
                for (List<GeoPoint> points : (List) polygon.getHolePoints().get(0)) {
                    this.out.print(",");
                    writePoints(points);
                }
            }
            this.out.print("]}");
        }

        private void writePolygonGeometry(MapPolygon polygon) {
            if (polygon.getPoints().size() > 1) {
                writeMultipolygonGeometryNoHoles(polygon);
            } else {
                writePolygonGeometryNoHoles(polygon);
            }
        }

        public Void visit(MapMarker marker, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(',');
            writePointGeometry(marker.getCentroid());
            writePropertiesHeader(marker.getClass().getName());
            writeProperties((MapFeature) marker);
            writeProperties((HasStroke) marker);
            writeProperties((HasFill) marker);
            writeProperty(GeoJSONUtil.PROPERTY_ANCHOR_HORIZONTAL, Integer.valueOf(marker.AnchorHorizontal()));
            writeProperty(GeoJSONUtil.PROPERTY_ANCHOR_VERTICAL, Integer.valueOf(marker.AnchorVertical()));
            writeProperty(GeoJSONUtil.PROPERTY_HEIGHT, Integer.valueOf(marker.Height()));
            writeProperty(GeoJSONUtil.PROPERTY_IMAGE, marker.ImageAsset());
            writeProperty(GeoJSONUtil.PROPERTY_WIDTH, Integer.valueOf(marker.Width()));
            this.out.print("}}");
            return null;
        }

        public Void visit(MapLineString lineString, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(',');
            writeLineGeometry(lineString);
            writePropertiesHeader(lineString.getClass().getName());
            writeProperties((MapFeature) lineString);
            writeProperties((HasStroke) lineString);
            this.out.print("}}");
            return null;
        }

        public Void visit(MapPolygon polygon, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(',');
            writePolygonGeometry(polygon);
            writePropertiesHeader(polygon.getClass().getName());
            writeProperties((MapFeature) polygon);
            writeProperties((HasStroke) polygon);
            writeProperties((HasFill) polygon);
            this.out.print("}}");
            return null;
        }

        public Void visit(MapCircle circle, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(',');
            writePointGeometry(circle.getCentroid());
            writePropertiesHeader(circle.getClass().getName());
            writeProperties((MapFeature) circle);
            writeProperties((HasStroke) circle);
            writeProperties((HasFill) circle);
            this.out.print("}}");
            return null;
        }

        public Void visit(MapRectangle rectangle, Object... arguments) {
            this.out.print("{");
            writeType(GeoJSONUtil.GEOJSON_FEATURE);
            this.out.print(",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[");
            this.out.print("[" + rectangle.WestLongitude() + "," + rectangle.NorthLatitude() + "],");
            this.out.print("[" + rectangle.WestLongitude() + "," + rectangle.SouthLatitude() + "],");
            this.out.print("[" + rectangle.EastLongitude() + "," + rectangle.SouthLatitude() + "],");
            this.out.print("[" + rectangle.EastLongitude() + "," + rectangle.NorthLatitude() + "],");
            this.out.print("[" + rectangle.WestLongitude() + "," + rectangle.NorthLatitude() + "]]}");
            writePropertiesHeader(rectangle.getClass().getName());
            writeProperties((MapFeature) rectangle);
            writeProperties((HasStroke) rectangle);
            writeProperties((HasFill) rectangle);
            writeProperty("NorthLatitude", Double.valueOf(rectangle.NorthLatitude()));
            writeProperty("WestLongitude", Double.valueOf(rectangle.WestLongitude()));
            writeProperty("SouthLatitude", Double.valueOf(rectangle.SouthLatitude()));
            writeProperty("EastLongitude", Double.valueOf(rectangle.EastLongitude()));
            this.out.print("}}");
            return null;
        }

        private static boolean hasAltitude(GeoPoint point) {
            return Double.compare(0.0d, point.getAltitude()) != 0;
        }
    }

    static {
        colors.put("black", Integer.valueOf(-16777216));
        colors.put("blue", Integer.valueOf(Component.COLOR_BLUE));
        colors.put("cyan", Integer.valueOf(Component.COLOR_CYAN));
        colors.put("darkgray", Integer.valueOf(Component.COLOR_DKGRAY));
        colors.put("gray", Integer.valueOf(Component.COLOR_GRAY));
        colors.put("green", Integer.valueOf(Component.COLOR_GREEN));
        colors.put("lightgray", Integer.valueOf(Component.COLOR_LTGRAY));
        colors.put("magenta", Integer.valueOf(Component.COLOR_MAGENTA));
        colors.put("orange", Integer.valueOf(Component.COLOR_ORANGE));
        colors.put("pink", Integer.valueOf(Component.COLOR_PINK));
        colors.put("red", Integer.valueOf(-65536));
        colors.put("white", Integer.valueOf(-1));
        colors.put("yellow", Integer.valueOf(-256));
        SUPPORTED_PROPERTIES.put(PROPERTY_ANCHOR_HORIZONTAL.toLowerCase(), new C04401());
        SUPPORTED_PROPERTIES.put(PROPERTY_ANCHOR_VERTICAL.toLowerCase(), new C04412());
        SUPPORTED_PROPERTIES.put(PROPERTY_DESCRIPTION, new C04423());
        SUPPORTED_PROPERTIES.put(PROPERTY_DRAGGABLE, new C04434());
        SUPPORTED_PROPERTIES.put(PROPERTY_FILL, new C04445());
        SUPPORTED_PROPERTIES.put(PROPERTY_HEIGHT, new C04456());
        SUPPORTED_PROPERTIES.put(PROPERTY_IMAGE, new C04467());
        SUPPORTED_PROPERTIES.put(PROPERTY_INFOBOX, new C04478());
        SUPPORTED_PROPERTIES.put(PROPERTY_STROKE, new C04489());
        SUPPORTED_PROPERTIES.put(PROPERTY_STROKE_WIDTH, new PropertyApplication() {
            public void apply(MapFeature feature, Object value) {
                if (feature instanceof HasStroke) {
                    ((HasStroke) feature).StrokeWidth(GeoJSONUtil.parseIntegerOrString(value));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_TITLE, new PropertyApplication() {
            public void apply(MapFeature feature, Object value) {
                feature.Title(value.toString());
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_WIDTH, new PropertyApplication() {
            public void apply(MapFeature feature, Object value) {
                if (feature instanceof MapMarker) {
                    ((MapMarker) feature).Width(GeoJSONUtil.parseIntegerOrString(value));
                }
            }
        });
        SUPPORTED_PROPERTIES.put(PROPERTY_VISIBLE, new PropertyApplication() {
            public void apply(MapFeature feature, Object value) {
                feature.Visible(GeoJSONUtil.parseBooleanOrString(value));
            }
        });
    }

    private GeoJSONUtil() {
    }

    @VisibleForTesting
    static int parseColor(String value) {
        String lcValue = value.toLowerCase();
        Integer result = (Integer) colors.get(lcValue);
        if (result != null) {
            return result.intValue();
        }
        if (lcValue.startsWith("#")) {
            return parseColorHex(lcValue.substring(1));
        }
        if (lcValue.startsWith("&h")) {
            return parseColorHex(lcValue.substring(2));
        }
        return -65536;
    }

    @VisibleForTesting
    static int parseColorHex(String value) {
        int argb = 0;
        int i;
        if (value.length() == 3) {
            argb = -16777216;
            for (i = 0; i < value.length(); i++) {
                int hex = charToHex(value.charAt(i));
                argb |= ((hex << 4) | hex) << ((2 - i) * 8);
            }
        } else if (value.length() == 6) {
            argb = -16777216;
            for (i = 0; i < 3; i++) {
                argb |= ((charToHex(value.charAt(i * 2)) << 4) | charToHex(value.charAt((i * 2) + 1))) << ((2 - i) * 8);
            }
        } else if (value.length() == 8) {
            for (i = 0; i < 4; i++) {
                argb |= ((charToHex(value.charAt(i * 2)) << 4) | charToHex(value.charAt((i * 2) + 1))) << ((3 - i) * 8);
            }
        } else {
            throw new IllegalArgumentException();
        }
        return argb;
    }

    @VisibleForTesting
    static int charToHex(char c) {
        if ('0' <= c && c <= '9') {
            return c - 48;
        }
        if ('a' <= c && c <= 'f') {
            return (c - 97) + 10;
        }
        if ('A' <= c && c <= Access.FIELD_CONTEXT) {
            return (c - 65) + 10;
        }
        throw new IllegalArgumentException("Invalid hex character. Expected [0-9A-Fa-f].");
    }

    public static Object processGeoJSONFeature(String logTag, MapFeatureContainer container, YailList descriptions) {
        String type = null;
        YailList geometry = null;
        YailList properties = null;
        Iterator i$ = ((LList) descriptions.getCdr()).iterator();
        while (i$.hasNext()) {
            YailList keyvalue = (YailList) i$.next();
            String key = keyvalue.getString(0);
            String value = keyvalue.getObject(1);
            if (GEOJSON_TYPE.equals(key)) {
                type = value;
            } else if (GEOJSON_GEOMETRY.equals(key)) {
                geometry = (YailList) value;
            } else if (GEOJSON_PROPERTIES.equals(key)) {
                properties = (YailList) value;
            } else {
                Log.w(logTag, String.format("Unsupported field \"%s\" in JSON format", new Object[]{key}));
            }
        }
        if (!GEOJSON_FEATURE.equals(type)) {
            throw new IllegalArgumentException(String.format("Unknown type \"%s\"", new Object[]{type}));
        } else if (geometry == null) {
            throw new IllegalArgumentException("No geometry defined for feature.");
        } else {
            MapFeature feature = processGeometry(logTag, container, geometry);
            if (properties != null) {
                processProperties(logTag, feature, properties);
            }
            return feature;
        }
    }

    private static MapFeature processGeometry(String logTag, MapFeatureContainer container, YailList geometry) {
        String type = null;
        YailList coordinates = null;
        Iterator i$ = ((LList) geometry.getCdr()).iterator();
        while (i$.hasNext()) {
            YailList keyvalue = (YailList) i$.next();
            String key = keyvalue.getString(0);
            String value = keyvalue.getObject(1);
            if (GEOJSON_TYPE.equals(key)) {
                type = value;
            } else if (GEOJSON_COORDINATES.equals(key)) {
                coordinates = (YailList) value;
            } else {
                Log.w(logTag, String.format("Unsupported field \"%s\" in JSON format", new Object[]{key}));
            }
        }
        if (coordinates != null) {
            return processCoordinates(container, type, coordinates);
        }
        throw new IllegalArgumentException("No coordinates found in GeoJSON Feature");
    }

    private static MapFeature processCoordinates(MapFeatureContainer container, String type, YailList coordinates) {
        if (MapFeatureType.TYPE_POINT.equals(type)) {
            return markerFromGeoJSON(container, coordinates);
        }
        if (MapFeatureType.TYPE_LINESTRING.equals(type)) {
            return lineStringFromGeoJSON(container, coordinates);
        }
        if (MapFeatureType.TYPE_POLYGON.equals(type)) {
            return polygonFromGeoJSON(container, coordinates);
        }
        if (MapFeatureType.TYPE_MULTIPOLYGON.equals(type)) {
            return multipolygonFromGeoJSON(container, coordinates);
        }
        throw new IllegalArgumentException();
    }

    private static MapMarker markerFromGeoJSON(MapFeatureContainer container, YailList coordinates) {
        if (coordinates.length() != 3) {
            throw new IllegalArgumentException("Invalid coordinate supplied in GeoJSON");
        }
        Marker marker = new Marker(container);
        marker.Latitude(((Number) coordinates.get(2)).doubleValue());
        marker.Longitude(((Number) coordinates.get(1)).doubleValue());
        return marker;
    }

    private static MapLineString lineStringFromGeoJSON(MapFeatureContainer container, YailList coordinates) {
        if (coordinates.size() < 2) {
            throw new IllegalArgumentException("Too few coordinates supplied in GeoJSON");
        }
        LineString lineString = new LineString(container);
        lineString.Points(swapCoordinates(coordinates));
        return lineString;
    }

    private static MapPolygon polygonFromGeoJSON(MapFeatureContainer container, YailList coordinates) {
        Polygon polygon = new Polygon(container);
        Iterator i = coordinates.iterator();
        i.next();
        polygon.Points(swapCoordinates((YailList) i.next()));
        if (i.hasNext()) {
            polygon.HolePoints(YailList.makeList(swapNestedCoordinates((LList) ((Pair) coordinates.getCdr()).getCdr())));
        }
        return polygon;
    }

    private static MapPolygon multipolygonFromGeoJSON(MapFeatureContainer container, YailList coordinates) {
        Polygon polygon = new Polygon(container);
        List points = new ArrayList();
        List holePoints = new ArrayList();
        Iterator i = coordinates.iterator();
        i.next();
        while (i.hasNext()) {
            YailList list = (YailList) i.next();
            points.add(swapCoordinates((YailList) list.get(1)));
            holePoints.add(YailList.makeList(swapNestedCoordinates((LList) ((Pair) list.getCdr()).getCdr())));
        }
        polygon.Points(YailList.makeList(points));
        polygon.HolePoints(YailList.makeList(holePoints));
        return polygon;
    }

    private static void processProperties(String logTag, MapFeature feature, YailList properties) {
        Iterator i$ = properties.iterator();
        while (i$.hasNext()) {
            YailList o = i$.next();
            if (o instanceof YailList) {
                YailList pair = o;
                PropertyApplication application = (PropertyApplication) SUPPORTED_PROPERTIES.get(pair.get(1).toString().toLowerCase());
                if (application != null) {
                    application.apply(feature, pair.get(2));
                } else {
                    Log.i(logTag, String.format("Ignoring GeoJSON property \"%s\"", new Object[]{key}));
                }
            }
        }
    }

    @VisibleForTesting
    static boolean parseBooleanOrString(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }
        if (value instanceof String) {
            return ("false".equalsIgnoreCase((String) value) || ((String) value).length() == 0) ? false : true;
        } else {
            if (value instanceof FString) {
                return parseBooleanOrString(value.toString());
            }
            throw new IllegalArgumentException();
        }
    }

    @VisibleForTesting
    static int parseIntegerOrString(Object value) {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        if (value instanceof String) {
            return Integer.parseInt((String) value);
        }
        if (value instanceof FString) {
            return Integer.parseInt(value.toString());
        }
        throw new IllegalArgumentException();
    }

    public static void writeFeaturesAsGeoJSON(List<MapFeature> featuresToSave, String path) throws IOException {
        Throwable th;
        PrintStream out = null;
        try {
            PrintStream out2 = new PrintStream(new FileOutputStream(path));
            try {
                FeatureWriter writer = new FeatureWriter(out2);
                out2.print("{\"type\": \"FeatureCollection\", \"features\":[");
                Iterator<MapFeature> it = featuresToSave.iterator();
                if (it.hasNext()) {
                    ((MapFeature) it.next()).accept(writer, new Object[0]);
                    while (it.hasNext()) {
                        MapFeature feature = (MapFeature) it.next();
                        out2.print(',');
                        feature.accept(writer, new Object[0]);
                    }
                }
                out2.print("]}");
                IOUtils.closeQuietly("GeoJSONUtil", out2);
            } catch (Throwable th2) {
                th = th2;
                out = out2;
                IOUtils.closeQuietly("GeoJSONUtil", out);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            IOUtils.closeQuietly("GeoJSONUtil", out);
            throw th;
        }
    }

    public static YailList swapCoordinates(YailList coordinates) {
        Iterator i = coordinates.iterator();
        i.next();
        while (i.hasNext()) {
            YailList coordinate = (YailList) i.next();
            Object temp = coordinate.get(1);
            Pair p = (Pair) coordinate.getCdr();
            p.setCar(coordinate.get(2));
            ((Pair) p.getCdr()).setCar(temp);
        }
        return coordinates;
    }

    public static LList swapNestedCoordinates(LList coordinates) {
        for (LList it = coordinates; !it.isEmpty(); it = (LList) ((Pair) it).getCdr()) {
            swapCoordinates((YailList) it.get(0));
        }
        return coordinates;
    }
}

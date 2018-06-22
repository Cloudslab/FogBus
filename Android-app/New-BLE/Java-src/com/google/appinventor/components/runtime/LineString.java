package com.google.appinventor.components.runtime;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.errors.DispatchableError;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory.MapCircle;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureType;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor;
import com.google.appinventor.components.runtime.util.MapFactory.MapLineString;
import com.google.appinventor.components.runtime.util.MapFactory.MapMarker;
import com.google.appinventor.components.runtime.util.MapFactory.MapPolygon;
import com.google.appinventor.components.runtime.util.MapFactory.MapRectangle;
import com.google.appinventor.components.runtime.util.YailList;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.util.GeoPoint;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MAPS, description = "LineString", version = 1)
public class LineString extends MapFeatureBase implements MapLineString {
    private static final String TAG = LineString.class.getSimpleName();
    private static final MapFeatureVisitor<Double> distanceComputation = new C04271();
    private List<GeoPoint> points = new ArrayList();

    static class C04271 implements MapFeatureVisitor<Double> {
        C04271() {
        }

        public Double visit(MapMarker marker, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(marker, (LineString) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(marker, (LineString) arguments[0]));
        }

        public Double visit(MapLineString lineString, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(lineString, (LineString) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(lineString, (LineString) arguments[0]));
        }

        public Double visit(MapPolygon polygon, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((LineString) arguments[0], polygon));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((LineString) arguments[0], polygon));
        }

        public Double visit(MapCircle circle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((LineString) arguments[0], circle));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((LineString) arguments[0], circle));
        }

        public Double visit(MapRectangle rectangle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((LineString) arguments[0], rectangle));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((LineString) arguments[0], rectangle));
        }
    }

    public LineString(MapFeatureContainer container) {
        super(container, distanceComputation);
        StrokeWidth(3);
        container.addFeature(this);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The type of the map feature.")
    public String Type() {
        return MapFeatureType.TYPE_LINESTRING;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A list of latitude and longitude pairs that represent the line segments of the polyline.")
    public YailList Points() {
        return GeometryUtil.pointsListToYailList(this.points);
    }

    @SimpleProperty
    public void Points(@NonNull YailList points) {
        if (points.size() < 2) {
            this.container.$form().dispatchErrorOccurredEvent(this, "Points", ErrorMessages.ERROR_LINESTRING_TOO_FEW_POINTS, Integer.valueOf(points.length() - 1));
            return;
        }
        try {
            this.points = GeometryUtil.pointsFromYailList(points);
            clearGeometry();
            this.map.getController().updateFeaturePosition((MapLineString) this);
        } catch (DispatchableError e) {
            this.container.$form().dispatchErrorOccurredEvent(this, "Points", e.getErrorCode(), e.getArguments());
        }
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty
    public void PointsFromString(String points) {
        String functionName = "PointsFromString";
        try {
            List<GeoPoint> geopoints = new ArrayList();
            JSONArray array = new JSONArray(points);
            if (array.length() < 2) {
                throw new DispatchableError(ErrorMessages.ERROR_LINESTRING_TOO_FEW_POINTS, Integer.valueOf(array.length()));
            }
            int length = array.length();
            int i = 0;
            while (i < length) {
                JSONArray point = array.optJSONArray(i);
                if (point == null) {
                    throw new DispatchableError(ErrorMessages.ERROR_EXPECTED_ARRAY_AT_INDEX, Integer.valueOf(i), array.get(i).toString());
                } else if (point.length() < 2) {
                    throw new DispatchableError(ErrorMessages.ERROR_LINESTRING_TOO_FEW_FIELDS, Integer.valueOf(i), Integer.valueOf(points.length()));
                } else {
                    double latitude = point.optDouble(0, Double.NaN);
                    double longitude = point.optDouble(1, Double.NaN);
                    if (!GeometryUtil.isValidLatitude(latitude)) {
                        throw new DispatchableError(ErrorMessages.ERROR_INVALID_LATITUDE_IN_POINT_AT_INDEX, Integer.valueOf(i), array.get(0).toString());
                    } else if (GeometryUtil.isValidLongitude(longitude)) {
                        geopoints.add(new GeoPoint(latitude, longitude));
                        i++;
                    } else {
                        throw new DispatchableError(ErrorMessages.ERROR_INVALID_LONGITUDE_IN_POINT_AT_INDEX, Integer.valueOf(i), array.get(1).toString());
                    }
                }
            }
            this.points = geopoints;
            clearGeometry();
            this.map.getController().updateFeaturePosition((MapLineString) this);
        } catch (JSONException e) {
            Log.e(TAG, "Malformed string to LineString.PointsFromString", e);
            this.container.$form().dispatchErrorOccurredEvent(this, "PointsFromString", ErrorMessages.ERROR_LINESTRING_PARSE_ERROR, e.getMessage());
        } catch (DispatchableError e2) {
            this.container.$form().dispatchErrorOccurredEvent(this, "PointsFromString", e2.getErrorCode(), e2.getArguments());
        }
    }

    @DesignerProperty(defaultValue = "3")
    @SimpleProperty
    public void StrokeWidth(int width) {
        super.StrokeWidth(width);
    }

    public List<GeoPoint> getPoints() {
        return this.points;
    }

    public <T> T accept(MapFeatureVisitor<T> visitor, Object... arguments) {
        return visitor.visit((MapLineString) this, arguments);
    }

    protected Geometry computeGeometry() {
        return GeometryUtil.createGeometry(this.points);
    }

    public void updatePoints(List<GeoPoint> points) {
        this.points = points;
        clearGeometry();
    }
}

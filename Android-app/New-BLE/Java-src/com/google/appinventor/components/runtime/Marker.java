package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesAssets;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory.MapCircle;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeature;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureType;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor;
import com.google.appinventor.components.runtime.util.MapFactory.MapLineString;
import com.google.appinventor.components.runtime.util.MapFactory.MapMarker;
import com.google.appinventor.components.runtime.util.MapFactory.MapPolygon;
import com.google.appinventor.components.runtime.util.MapFactory.MapRectangle;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

@DesignerComponent(category = ComponentCategory.MAPS, description = "<p>An icon positioned at a point to indicate information on a map. Markers can be used to provide an info window, custom fill and stroke colors, and custom images to convey information to the user.</p>", version = 2)
@UsesLibraries(libraries = "osmdroid.aar, androidsvg.jar")
@SimpleObject
@UsesAssets(fileNames = "marker.svg")
public class Marker extends MapFeatureBaseWithFill implements MapMarker {
    private static final String TAG = Marker.class.getSimpleName();
    private static final MapFeatureVisitor<Double> bearingComputation = new C04362();
    private static final MapFeatureVisitor<Double> distanceComputation = new C04351();
    private int anchorHAlign = 3;
    private int anchorVAlign = 3;
    private int height = -1;
    private String imagePath = "";
    private GeoPoint location = new GeoPoint(0.0d, 0.0d);
    private int width = -1;

    static class C04351 implements MapFeatureVisitor<Double> {
        C04351() {
        }

        public Double visit(MapMarker marker, Object... arguments) {
            return Double.valueOf(GeometryUtil.distanceBetween((Marker) arguments[0], marker));
        }

        public Double visit(MapLineString lineString, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((Marker) arguments[0], lineString));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((Marker) arguments[0], lineString));
        }

        public Double visit(MapPolygon polygon, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((Marker) arguments[0], polygon));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((Marker) arguments[0], polygon));
        }

        public Double visit(MapCircle circle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((Marker) arguments[0], circle));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((Marker) arguments[0], circle));
        }

        public Double visit(MapRectangle rectangle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((Marker) arguments[0], rectangle));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((Marker) arguments[0], rectangle));
        }
    }

    static class C04362 implements MapFeatureVisitor<Double> {
        C04362() {
        }

        public Double visit(MapMarker marker, Object... arguments) {
            return Double.valueOf(GeometryUtil.bearingTo((Marker) arguments[0], marker));
        }

        public Double visit(MapLineString lineString, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.bearingToCentroid((MapMarker) arguments[0], lineString));
            }
            return Double.valueOf(GeometryUtil.bearingToEdge((MapMarker) arguments[0], lineString));
        }

        public Double visit(MapPolygon polygon, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.bearingToCentroid((MapMarker) arguments[0], polygon));
            }
            return Double.valueOf(GeometryUtil.bearingToEdge((MapMarker) arguments[0], polygon));
        }

        public Double visit(MapCircle circle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.bearingToCentroid((MapMarker) arguments[0], circle));
            }
            return Double.valueOf(GeometryUtil.bearingToEdge((MapMarker) arguments[0], circle));
        }

        public Double visit(MapRectangle rectangle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.bearingToCentroid((MapMarker) arguments[0], rectangle));
            }
            return Double.valueOf(GeometryUtil.bearingToEdge((MapMarker) arguments[0], rectangle));
        }
    }

    public Marker(MapFeatureContainer container) {
        super(container, distanceComputation);
        container.addFeature(this);
        ShowShadow(false);
        AnchorHorizontal(3);
        AnchorVertical(3);
        ImageAsset("");
        Width(-1);
        Height(-1);
        Latitude(0.0d);
        Longitude(0.0d);
    }

    @SimpleProperty
    public String Type() {
        return MapFeatureType.TYPE_MARKER;
    }

    @DesignerProperty(defaultValue = "0", editorType = "latitude")
    @SimpleProperty
    public void Latitude(double latitude) {
        Log.d(TAG, "Latitude");
        if (latitude < -90.0d || latitude > 90.0d) {
            this.container.$form().dispatchErrorOccurredEvent(this, "Latitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
            return;
        }
        this.location.setLatitude(latitude);
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapMarker) this);
    }

    @SimpleProperty
    public double Latitude() {
        return this.location.getLatitude();
    }

    @DesignerProperty(defaultValue = "0", editorType = "longitude")
    @SimpleProperty
    public void Longitude(double longitude) {
        Log.d(TAG, "Longitude");
        if (longitude < -180.0d || longitude > 180.0d) {
            this.container.$form().dispatchErrorOccurredEvent(this, "Longitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
            return;
        }
        this.location.setLongitude(longitude);
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapMarker) this);
    }

    @SimpleProperty
    public double Longitude() {
        return this.location.getLongitude();
    }

    @DesignerProperty(editorType = "asset")
    @SimpleProperty
    public void ImageAsset(String path) {
        Log.d(TAG, "ImageAsset");
        this.imagePath = path;
        this.map.getController().updateFeatureImage(this);
    }

    @SimpleProperty(description = "The ImageAsset property is used to provide an alternative image for the Marker.")
    public String ImageAsset() {
        return this.imagePath;
    }

    @SimpleProperty
    public void StrokeColor(int argb) {
        super.StrokeColor(argb);
        this.map.getController().updateFeatureStroke(this);
    }

    @DesignerProperty(defaultValue = "3", editorType = "horizontal_alignment")
    @SimpleProperty
    public void AnchorHorizontal(int horizontal) {
        if (horizontal != this.anchorHAlign) {
            if (horizontal > 3 || horizontal < 1) {
                this.container.$form().dispatchErrorOccurredEvent(this, "AnchorHorizontal", ErrorMessages.ERROR_INVALID_ANCHOR_HORIZONTAL, Integer.valueOf(horizontal));
                return;
            }
            this.anchorHAlign = horizontal;
            this.map.getController().updateFeaturePosition((MapMarker) this);
        }
    }

    @SimpleProperty(description = "The horizontal alignment property controls where the Marker's anchor is located relative to its width.")
    public int AnchorHorizontal() {
        return this.anchorHAlign;
    }

    @DesignerProperty(defaultValue = "3", editorType = "vertical_alignment")
    @SimpleProperty
    public void AnchorVertical(int vertical) {
        if (vertical != this.anchorVAlign) {
            if (vertical > 3 || vertical < 1) {
                this.container.$form().dispatchErrorOccurredEvent(this, "AnchorVertical", ErrorMessages.ERROR_INVALID_ANCHOR_VERTICAL, Integer.valueOf(vertical));
                return;
            }
            this.anchorVAlign = vertical;
            this.map.getController().updateFeaturePosition((MapMarker) this);
        }
    }

    @SimpleProperty(description = "The vertical alignment property controls where the Marker's anchor is located relative to its height.")
    public int AnchorVertical() {
        return this.anchorVAlign;
    }

    @SimpleProperty(userVisible = false)
    public void ShowShadow(boolean show) {
    }

    @SimpleProperty(description = "Gets whether or not the shadow of the Marker is shown.")
    public boolean ShowShadow() {
        return false;
    }

    @SimpleProperty
    public void Width(int width) {
        this.width = width;
        this.map.getController().updateFeatureSize(this);
    }

    @SimpleProperty
    public int Width() {
        if (this.width == -2) {
            return this.map.getView().getWidth();
        }
        if (this.width < Component.LENGTH_PERCENT_TAG) {
            return (int) (((((double) (-this.width)) - 0.00408935546875d) / 100.0d) * ((double) this.map.getView().getWidth()));
        }
        return this.width;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void WidthPercent(int pCent) {
        this.width = -1000 - pCent;
        this.map.getController().updateFeatureSize(this);
    }

    @SimpleProperty
    public void Height(int height) {
        this.height = height;
        this.map.getController().updateFeatureSize(this);
    }

    @SimpleProperty
    public int Height() {
        if (this.height == -2) {
            return this.map.getView().getHeight();
        }
        if (this.height < Component.LENGTH_PERCENT_TAG) {
            return (int) (((((double) (-this.height)) - 0.00408935546875d) / 100.0d) * ((double) this.map.getView().getHeight()));
        }
        return this.height;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public void HeightPercent(int pCent) {
        this.height = -1000 - pCent;
        this.map.getController().updateFeatureSize(this);
    }

    @SimpleFunction(description = "Set the location of the marker.")
    public void SetLocation(double latitude, double longitude) {
        Log.d(TAG, "SetLocation");
        this.location.setCoords(latitude, longitude);
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapMarker) this);
    }

    public double DistanceToPoint(double latitude, double longitude, boolean centroid) {
        return DistanceToPoint(latitude, longitude);
    }

    @SimpleFunction(description = "Compute the distance, in meters, between a map feature and a latitude, longitude point.")
    public double DistanceToPoint(double latitude, double longitude) {
        return GeometryUtil.distanceBetween((MapMarker) this, new GeoPoint(latitude, longitude));
    }

    @SimpleFunction(description = "Returns the bearing from the Marker to the given latitude and longitude, in degrees from due north.")
    public double BearingToPoint(double latitude, double longitude) {
        return this.location.bearingTo(new GeoPoint(latitude, longitude));
    }

    @SimpleFunction(description = "Returns the bearing from the Marker to the given map feature, in degrees from due north. If the centroids parameter is true, the bearing will be to the center of the map feature. Otherwise, the bearing will be computed to the point in the feature nearest the Marker.")
    public double BearingToFeature(MapFeature mapFeature, boolean centroids) {
        if (mapFeature == null) {
            return -1.0d;
        }
        return ((Double) mapFeature.accept(bearingComputation, this, Boolean.valueOf(centroids))).doubleValue();
    }

    public IGeoPoint getLocation() {
        return this.location;
    }

    public void updateLocation(double latitude, double longitude) {
        this.location = new GeoPoint(latitude, longitude);
        clearGeometry();
    }

    public <T> T accept(MapFeatureVisitor<T> visitor, Object... arguments) {
        return visitor.visit((MapMarker) this, arguments);
    }

    protected Geometry computeGeometry() {
        return GeometryUtil.createGeometry(this.location);
    }
}

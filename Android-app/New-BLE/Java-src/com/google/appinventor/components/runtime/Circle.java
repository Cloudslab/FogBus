package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
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
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.util.GeoPoint;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MAPS, description = "Circle", version = 1)
public class Circle extends PolygonBase implements MapCircle {
    private static final MapFeatureVisitor<Double> distanceComputation = new C04291();
    private GeoPoint center = new GeoPoint(0.0d, 0.0d);
    private double latitude;
    private double longitude;
    private double radius;

    static class C04291 implements MapFeatureVisitor<Double> {
        C04291() {
        }

        public Double visit(MapMarker marker, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(marker, (Circle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(marker, (Circle) arguments[0]));
        }

        public Double visit(MapLineString lineString, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(lineString, (Circle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(lineString, (Circle) arguments[0]));
        }

        public Double visit(MapPolygon polygon, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(polygon, (Circle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(polygon, (Circle) arguments[0]));
        }

        public Double visit(MapCircle circle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(circle, (Circle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(circle, (Circle) arguments[0]));
        }

        public Double visit(MapRectangle rectangle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids((Circle) arguments[0], rectangle));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges((Circle) arguments[0], rectangle));
        }
    }

    public Circle(MapFeatureContainer container) {
        super(container, distanceComputation);
        container.addFeature(this);
    }

    @SimpleProperty
    public String Type() {
        return MapFeatureType.TYPE_CIRCLE;
    }

    @DesignerProperty(defaultValue = "0", editorType = "non_negative_float")
    @SimpleProperty
    public void Radius(double radius) {
        this.radius = radius;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapCircle) this);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The radius of the circle in meters.")
    public double Radius() {
        return this.radius;
    }

    @DesignerProperty(defaultValue = "0", editorType = "latitude")
    @SimpleProperty
    public void Latitude(double latitude) {
        if (GeometryUtil.isValidLatitude(latitude)) {
            this.latitude = latitude;
            this.center.setLatitude(latitude);
            clearGeometry();
            this.map.getController().updateFeaturePosition((MapCircle) this);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "Latitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The latitude of the center of the circle.")
    public double Latitude() {
        return this.latitude;
    }

    @DesignerProperty(defaultValue = "0", editorType = "longitude")
    @SimpleProperty
    public void Longitude(double longitude) {
        if (GeometryUtil.isValidLongitude(longitude)) {
            this.longitude = longitude;
            this.center.setLongitude(longitude);
            clearGeometry();
            this.map.getController().updateFeaturePosition((MapCircle) this);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "Longitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The longitude of the center of the circle.")
    public double Longitude() {
        return this.longitude;
    }

    @SimpleFunction(description = "Set the center of the Circle.")
    public void SetLocation(double latitude, double longitude) {
        if (!GeometryUtil.isValidLatitude(latitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetLocation", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        } else if (GeometryUtil.isValidLongitude(longitude)) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.center.setLatitude(latitude);
            this.center.setLongitude(longitude);
            clearGeometry();
            this.map.getController().updateFeaturePosition((MapCircle) this);
        } else {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetLocation", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        }
    }

    public <T> T accept(MapFeatureVisitor<T> visitor, Object... arguments) {
        return visitor.visit((MapCircle) this, arguments);
    }

    protected Geometry computeGeometry() {
        return GeometryUtil.createGeometry(this.center);
    }

    public void updateCenter(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        clearGeometry();
    }
}

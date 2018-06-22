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
import com.google.appinventor.components.runtime.util.YailList;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.GeoPoint;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MAPS, description = "Rectangle", version = 1)
public class Rectangle extends PolygonBase implements MapRectangle {
    private static final MapFeatureVisitor<Double> distanceComputation = new C04341();
    private double east = 0.0d;
    private double north = 0.0d;
    private double south = 0.0d;
    private double west = 0.0d;

    static class C04341 implements MapFeatureVisitor<Double> {
        C04341() {
        }

        public Double visit(MapMarker marker, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(marker, (Rectangle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(marker, (Rectangle) arguments[0]));
        }

        public Double visit(MapLineString lineString, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(lineString, (Rectangle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(lineString, (Rectangle) arguments[0]));
        }

        public Double visit(MapPolygon polygon, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(polygon, (Rectangle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(polygon, (Rectangle) arguments[0]));
        }

        public Double visit(MapCircle circle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(circle, (Rectangle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(circle, (Rectangle) arguments[0]));
        }

        public Double visit(MapRectangle rectangle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(rectangle, (Rectangle) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(rectangle, (Rectangle) arguments[0]));
        }
    }

    public Rectangle(MapFeatureContainer container) {
        super(container, distanceComputation);
        container.addFeature(this);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The type of the feature. For rectangles, this returns the text \"Rectangle\".")
    public String Type() {
        return MapFeatureType.TYPE_RECTANGLE;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty
    public void EastLongitude(double east) {
        this.east = east;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapRectangle) this);
    }

    @SimpleProperty
    public double EastLongitude() {
        return this.east;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty
    public void NorthLatitude(double north) {
        this.north = north;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapRectangle) this);
    }

    @SimpleProperty
    public double NorthLatitude() {
        return this.north;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty
    public void SouthLatitude(double south) {
        this.south = south;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapRectangle) this);
    }

    @SimpleProperty
    public double SouthLatitude() {
        return this.south;
    }

    @DesignerProperty(defaultValue = "0", editorType = "float")
    @SimpleProperty
    public void WestLongitude(double west) {
        this.west = west;
        clearGeometry();
        this.map.getController().updateFeaturePosition((MapRectangle) this);
    }

    @SimpleProperty
    public double WestLongitude() {
        return this.west;
    }

    @SimpleFunction(description = "Returns the center of the Rectangle as a list of the form (Latitude Longitude).")
    public YailList Center() {
        return GeometryUtil.asYailList(getCentroid());
    }

    @SimpleFunction(description = "Returns the bounding box of the Rectangle in the format ((North West) (South East)).")
    public YailList Bounds() {
        YailList nw = YailList.makeList(new Double[]{Double.valueOf(this.north), Double.valueOf(this.west)});
        YailList se = YailList.makeList(new Double[]{Double.valueOf(this.south), Double.valueOf(this.east)});
        return YailList.makeList(new YailList[]{nw, se});
    }

    @SimpleFunction(description = "Moves the Rectangle so that it is centered on the given latitude and longitude while attempting to maintain the width and height of the Rectangle as measured from the center to the edges.")
    public void SetCenter(double latitude, double longitude) {
        if (latitude < -90.0d || latitude > 90.0d) {
            this.container.$form().dispatchErrorOccurredEvent(this, "SetCenter", ErrorMessages.ERROR_INVALID_POINT, Double.valueOf(latitude), Double.valueOf(longitude));
        } else if (longitude < -180.0d || longitude > 180.0d) {
            this.container.$form().dispatchErrorOccurredEvent(this, "SetCenter", ErrorMessages.ERROR_INVALID_POINT, Double.valueOf(latitude), Double.valueOf(longitude));
        } else {
            GeoPoint currentCenter = getCentroid();
            IGeoPoint northPoint = new GeoPoint(this.north, currentCenter.getLongitude());
            IGeoPoint southPoint = new GeoPoint(this.south, currentCenter.getLongitude());
            double latExtent2 = GeometryUtil.distanceBetween(northPoint, southPoint) / 2.0d;
            double longExtent2 = GeometryUtil.distanceBetween(new GeoPoint(currentCenter.getLatitude(), this.east), new GeoPoint(currentCenter.getLatitude(), this.west)) / 2.0d;
            currentCenter.setCoords(latitude, longitude);
            this.north = currentCenter.destinationPoint(latExtent2, 0.0f).getLatitude();
            this.south = currentCenter.destinationPoint(latExtent2, 180.0f).getLatitude();
            this.east = currentCenter.destinationPoint(longExtent2, 90.0f).getLongitude();
            this.west = currentCenter.destinationPoint(longExtent2, 270.0f).getLongitude();
            clearGeometry();
            this.map.getController().updateFeaturePosition((MapRectangle) this);
        }
    }

    public <T> T accept(MapFeatureVisitor<T> visitor, Object... arguments) {
        return visitor.visit((MapRectangle) this, arguments);
    }

    protected Geometry computeGeometry() {
        return GeometryUtil.createGeometry(this.north, this.east, this.south, this.west);
    }

    public void updateBounds(double north, double west, double south, double east) {
        this.north = north;
        this.west = west;
        this.south = south;
        this.east = east;
        clearGeometry();
    }
}

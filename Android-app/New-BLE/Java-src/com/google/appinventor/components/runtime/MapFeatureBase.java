package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.MapFactory.HasStroke;
import com.google.appinventor.components.runtime.util.MapFactory.MapCircle;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeature;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor;
import com.google.appinventor.components.runtime.util.MapFactory.MapLineString;
import com.google.appinventor.components.runtime.util.MapFactory.MapMarker;
import com.google.appinventor.components.runtime.util.MapFactory.MapPolygon;
import com.google.appinventor.components.runtime.util.MapFactory.MapRectangle;
import com.google.appinventor.components.runtime.util.YailList;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.util.GeoPoint;

@SimpleObject
public abstract class MapFeatureBase implements MapFeature, HasStroke {
    private GeoPoint centroid = null;
    protected MapFeatureContainer container = null;
    private String description = "";
    private final MapFeatureVisitor<Double> distanceComputation;
    private MapFeatureVisitor<Double> distanceToPoint = new C04281();
    private boolean draggable = false;
    private Geometry geometry = null;
    private boolean infobox = false;
    protected Map map = null;
    private int strokeColor = -16777216;
    private int strokeWidth = 1;
    private String title = "";
    private boolean visible = true;

    class C04281 implements MapFeatureVisitor<Double> {
        C04281() {
        }

        public Double visit(MapMarker marker, Object... arguments) {
            return Double.valueOf(GeometryUtil.distanceBetween(marker, (GeoPoint) arguments[0]));
        }

        public Double visit(MapLineString lineString, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(lineString, (GeoPoint) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(lineString, (GeoPoint) arguments[0]));
        }

        public Double visit(MapPolygon polygon, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(polygon, (GeoPoint) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(polygon, (GeoPoint) arguments[0]));
        }

        public Double visit(MapCircle circle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(circle, (GeoPoint) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(circle, (GeoPoint) arguments[0]));
        }

        public Double visit(MapRectangle rectangle, Object... arguments) {
            if (((Boolean) arguments[1]).booleanValue()) {
                return Double.valueOf(GeometryUtil.distanceBetweenCentroids(rectangle, (GeoPoint) arguments[0]));
            }
            return Double.valueOf(GeometryUtil.distanceBetweenEdges(rectangle, (GeoPoint) arguments[0]));
        }
    }

    protected abstract Geometry computeGeometry();

    protected MapFeatureBase(MapFeatureContainer container, MapFeatureVisitor<Double> distanceComputation) {
        this.container = container;
        this.map = container.getMap();
        this.distanceComputation = distanceComputation;
        Description("");
        Draggable(false);
        EnableInfobox(false);
        StrokeColor(-16777216);
        StrokeWidth(1);
        Title("");
        Visible(true);
    }

    public void setMap(MapFeatureContainer container) {
        this.map = container.getMap();
    }

    public void removeFromMap() {
        this.map.getController().removeFeature(this);
    }

    @DesignerProperty(defaultValue = "True", editorType = "visibility")
    @SimpleProperty
    public void Visible(boolean visibility) {
        if (this.visible != visibility) {
            this.visible = visibility;
            if (this.visible) {
                this.map.getController().showFeature(this);
            } else {
                this.map.getController().hideFeature(this);
            }
            this.map.getView().invalidate();
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Specifies whether the component should be visible on the screen. Value is true if the component is showing and false if hidden.")
    public boolean Visible() {
        return this.visible;
    }

    @DesignerProperty(defaultValue = "&HFF000000", editorType = "color")
    @SimpleProperty
    public void StrokeColor(int argb) {
        this.strokeColor = argb;
        this.map.getController().updateFeatureStroke(this);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The paint color used to outline the map feature.")
    public int StrokeColor() {
        return this.strokeColor;
    }

    @DesignerProperty(defaultValue = "1", editorType = "integer")
    @SimpleProperty
    public void StrokeWidth(int width) {
        this.strokeWidth = width;
        this.map.getController().updateFeatureStroke(this);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The width of the stroke used to outline the map feature.")
    public int StrokeWidth() {
        return this.strokeWidth;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void Draggable(boolean draggable) {
        this.draggable = draggable;
        this.map.getController().updateFeatureDraggable(this);
    }

    @SimpleProperty(description = "The Draggable property is used to set whether or not the user can drag the Marker by long-pressing and then dragging the marker to a new location.")
    public boolean Draggable() {
        return this.draggable;
    }

    @DesignerProperty
    @SimpleProperty
    public void Title(String title) {
        this.title = title;
        this.map.getController().updateFeatureText(this);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The title displayed in the info window that appears when the user clicks on the map feature.")
    public String Title() {
        return this.title;
    }

    @DesignerProperty
    @SimpleProperty
    public void Description(String description) {
        this.description = description;
        this.map.getController().updateFeatureText(this);
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "The description displayed in the info window that appears when the user clicks on the map feature.")
    public String Description() {
        return this.description;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void EnableInfobox(boolean enable) {
        this.infobox = enable;
        this.map.getController().updateFeatureText(this);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Enable or disable the infobox window display when the user taps the feature.")
    public boolean EnableInfobox() {
        return this.infobox;
    }

    @SimpleFunction(description = "Show the infobox for the feature. This will show the infobox even if {@link #EnableInfobox} is set to false.")
    public void ShowInfobox() {
        this.map.getController().showInfobox(this);
    }

    @SimpleFunction(description = "Hide the infobox if it is shown. If the infobox is not visible this function has no effect.")
    public void HideInfobox() {
        this.map.getController().hideInfobox(this);
    }

    public YailList Centroid() {
        return GeometryUtil.asYailList(getCentroid());
    }

    @SimpleFunction(description = "Compute the distance, in meters, between a map feature and a latitude, longitude point.")
    public double DistanceToPoint(double latitude, double longitude, boolean centroid) {
        return ((Double) accept(this.distanceToPoint, new GeoPoint(latitude, longitude), Boolean.valueOf(centroid))).doubleValue();
    }

    @SimpleFunction(description = "Compute the distance, in meters, between two map features.")
    public double DistanceToFeature(MapFeature mapFeature, boolean centroids) {
        if (mapFeature == null) {
            return -1.0d;
        }
        return ((Double) mapFeature.accept(this.distanceComputation, this, Boolean.valueOf(centroids))).doubleValue();
    }

    @SimpleEvent(description = "The user clicked on the feature.")
    public void Click() {
        EventDispatcher.dispatchEvent(this, "Click", new Object[0]);
        this.container.FeatureClick(this);
    }

    @SimpleEvent(description = "The user long-pressed on the feature. This event will only trigger if Draggable is false.")
    public void LongClick() {
        EventDispatcher.dispatchEvent(this, "LongClick", new Object[0]);
        this.container.FeatureLongClick(this);
    }

    @SimpleEvent(description = "The user started a drag operation.")
    public void StartDrag() {
        EventDispatcher.dispatchEvent(this, "StartDrag", new Object[0]);
        this.container.FeatureStartDrag(this);
    }

    @SimpleEvent(description = "The user dragged the map feature.")
    public void Drag() {
        EventDispatcher.dispatchEvent(this, "Drag", new Object[0]);
        this.container.FeatureDrag(this);
    }

    @SimpleEvent(description = "The user stopped a drag operation.")
    public void StopDrag() {
        EventDispatcher.dispatchEvent(this, "StopDrag", new Object[0]);
        this.container.FeatureStopDrag(this);
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this.map.getDispatchDelegate();
    }

    public final synchronized GeoPoint getCentroid() {
        if (this.centroid == null) {
            this.centroid = GeometryUtil.jtsPointToGeoPoint(getGeometry().getCentroid());
        }
        return this.centroid;
    }

    public final synchronized Geometry getGeometry() {
        if (this.geometry == null) {
            this.geometry = computeGeometry();
        }
        return this.geometry;
    }

    protected final synchronized void clearGeometry() {
        this.centroid = null;
        this.geometry = null;
    }
}

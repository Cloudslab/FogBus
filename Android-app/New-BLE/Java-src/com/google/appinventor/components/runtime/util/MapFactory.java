package com.google.appinventor.components.runtime.util;

import android.os.Build.VERSION;
import android.view.View;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.LocationSensor.LocationSensorListener;
import com.google.appinventor.components.runtime.Map;
import java.util.List;
import org.locationtech.jts.geom.Geometry;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;

public final class MapFactory {

    public interface HasFill {
        int FillColor();

        void FillColor(int i);
    }

    public interface HasStroke {
        int StrokeColor();

        void StrokeColor(int i);

        int StrokeWidth();

        void StrokeWidth(int i);
    }

    public interface MapController {
        void addEventListener(MapEventListener mapEventListener);

        void addFeature(MapCircle mapCircle);

        void addFeature(MapLineString mapLineString);

        void addFeature(MapMarker mapMarker);

        void addFeature(MapPolygon mapPolygon);

        void addFeature(MapRectangle mapRectangle);

        BoundingBox getBoundingBox();

        double getLatitude();

        LocationSensorListener getLocationListener();

        double getLongitude();

        MapType getMapType();

        int getOverlayCount();

        View getView();

        int getZoom();

        void hideFeature(MapFeature mapFeature);

        void hideInfobox(MapFeature mapFeature);

        boolean isCompassEnabled();

        boolean isFeatureVisible(MapFeature mapFeature);

        boolean isInfoboxVisible(MapFeature mapFeature);

        boolean isPanEnabled();

        boolean isRotationEnabled();

        boolean isShowUserEnabled();

        boolean isZoomControlEnabled();

        boolean isZoomEnabled();

        void panTo(double d, double d2, int i, double d3);

        void removeFeature(MapFeature mapFeature);

        void setBoundingBox(BoundingBox boundingBox);

        void setCenter(double d, double d2);

        void setCompassEnabled(boolean z);

        void setMapType(MapType mapType);

        void setPanEnabled(boolean z);

        void setRotationEnabled(boolean z);

        void setShowUserEnabled(boolean z);

        void setZoom(int i);

        void setZoomControlEnabled(boolean z);

        void setZoomEnabled(boolean z);

        void showFeature(MapFeature mapFeature);

        void showInfobox(MapFeature mapFeature);

        void updateFeatureDraggable(MapFeature mapFeature);

        void updateFeatureFill(HasFill hasFill);

        void updateFeatureImage(MapMarker mapMarker);

        void updateFeaturePosition(MapCircle mapCircle);

        void updateFeaturePosition(MapLineString mapLineString);

        void updateFeaturePosition(MapMarker mapMarker);

        void updateFeaturePosition(MapPolygon mapPolygon);

        void updateFeaturePosition(MapRectangle mapRectangle);

        void updateFeatureSize(MapMarker mapMarker);

        void updateFeatureStroke(HasStroke hasStroke);

        void updateFeatureText(MapFeature mapFeature);
    }

    public interface MapEventListener {
        void onBoundsChanged();

        void onDoubleTap(double d, double d2);

        void onFeatureClick(MapFeature mapFeature);

        void onFeatureDrag(MapFeature mapFeature);

        void onFeatureLongPress(MapFeature mapFeature);

        void onFeatureStartDrag(MapFeature mapFeature);

        void onFeatureStopDrag(MapFeature mapFeature);

        void onLongPress(double d, double d2);

        void onReady(MapController mapController);

        void onSingleTap(double d, double d2);

        void onZoom();
    }

    public static final class MapFeatureType {
        public static final String TYPE_CIRCLE = "Circle";
        public static final String TYPE_LINESTRING = "LineString";
        public static final String TYPE_MARKER = "Marker";
        public static final String TYPE_MULTILINESTRING = "MultiLineString";
        public static final String TYPE_MULTIPOINT = "MultiPoint";
        public static final String TYPE_MULTIPOLYGON = "MultiPolygon";
        public static final String TYPE_POINT = "Point";
        public static final String TYPE_POLYGON = "Polygon";
        public static final String TYPE_RECTANGLE = "Rectangle";

        private MapFeatureType() {
        }
    }

    public interface MapFeatureVisitor<T> {
        T visit(MapCircle mapCircle, Object... objArr);

        T visit(MapLineString mapLineString, Object... objArr);

        T visit(MapMarker mapMarker, Object... objArr);

        T visit(MapPolygon mapPolygon, Object... objArr);

        T visit(MapRectangle mapRectangle, Object... objArr);
    }

    public enum MapType {
        UNKNOWN,
        ROADS,
        AERIAL,
        TERRAIN
    }

    public interface MapFeature extends Component {
        void Click();

        String Description();

        void Description(String str);

        void Drag();

        void Draggable(boolean z);

        boolean Draggable();

        void EnableInfobox(boolean z);

        boolean EnableInfobox();

        void HideInfobox();

        void LongClick();

        void ShowInfobox();

        void StartDrag();

        void StopDrag();

        String Title();

        void Title(String str);

        String Type();

        void Visible(boolean z);

        boolean Visible();

        <T> T accept(MapFeatureVisitor<T> mapFeatureVisitor, Object... objArr);

        GeoPoint getCentroid();

        Geometry getGeometry();

        void removeFromMap();

        void setMap(MapFeatureContainer mapFeatureContainer);
    }

    public interface MapFeatureContainer extends ComponentContainer {
        void FeatureClick(MapFeature mapFeature);

        void FeatureDrag(MapFeature mapFeature);

        void FeatureLongClick(MapFeature mapFeature);

        void FeatureStartDrag(MapFeature mapFeature);

        void FeatureStopDrag(MapFeature mapFeature);

        YailList Features();

        void Features(YailList yailList);

        void addFeature(MapFeature mapFeature);

        Map getMap();

        void removeFeature(MapFeature mapFeature);
    }

    public interface MapCircle extends MapFeature, HasFill, HasStroke {
        double Latitude();

        void Latitude(double d);

        double Longitude();

        void Longitude(double d);

        double Radius();

        void Radius(double d);

        void SetLocation(double d, double d2);

        void updateCenter(double d, double d2);
    }

    public interface MapFeatureCollection extends MapFeatureContainer {
        YailList Features();

        void GotFeatures(String str, YailList yailList);

        void LoadError(String str, int i, String str2);

        void LoadFromURL(String str);

        String Source();

        void Source(String str);

        boolean Visible();
    }

    public interface MapLineString extends MapFeature, HasStroke {
        YailList Points();

        void Points(YailList yailList);

        List<GeoPoint> getPoints();

        void updatePoints(List<GeoPoint> list);
    }

    public interface MapMarker extends MapFeature, HasFill, HasStroke {
        int AnchorHorizontal();

        void AnchorHorizontal(int i);

        int AnchorVertical();

        void AnchorVertical(int i);

        int Height();

        void Height(int i);

        String ImageAsset();

        void ImageAsset(String str);

        double Latitude();

        void Latitude(double d);

        double Longitude();

        void Longitude(double d);

        void SetLocation(double d, double d2);

        void ShowShadow(boolean z);

        boolean ShowShadow();

        int Width();

        void Width(int i);

        IGeoPoint getLocation();

        void updateLocation(double d, double d2);
    }

    public interface MapPolygon extends MapFeature, HasFill, HasStroke {
        YailList HolePoints();

        void HolePoints(YailList yailList);

        YailList Points();

        void Points(YailList yailList);

        List<List<List<GeoPoint>>> getHolePoints();

        List<List<GeoPoint>> getPoints();

        void updateHolePoints(List<List<List<GeoPoint>>> list);

        void updatePoints(List<List<GeoPoint>> list);
    }

    public interface MapRectangle extends MapFeature, HasFill, HasStroke {
        YailList Bounds();

        YailList Center();

        double EastLongitude();

        void EastLongitude(double d);

        double NorthLatitude();

        void NorthLatitude(double d);

        void SetCenter(double d, double d2);

        double SouthLatitude();

        void SouthLatitude(double d);

        double WestLongitude();

        void WestLongitude(double d);

        void updateBounds(double d, double d2, double d3, double d4);
    }

    public static MapController newMap(Form form) {
        if (VERSION.SDK_INT < 8) {
            return new DummyMapController();
        }
        return new NativeOpenStreetMapController(form);
    }
}

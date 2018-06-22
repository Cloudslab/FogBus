package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVG.Colour;
import com.caverock.androidsvg.SVG.Length;
import com.caverock.androidsvg.SVG.Style;
import com.caverock.androidsvg.SVG.Svg;
import com.caverock.androidsvg.SVG.SvgConditionalElement;
import com.caverock.androidsvg.SVG.SvgObject;
import com.caverock.androidsvg.SVGParseException;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.LocationSensor;
import com.google.appinventor.components.runtime.LocationSensor.LocationSensorListener;
import com.google.appinventor.components.runtime.util.MapFactory.HasFill;
import com.google.appinventor.components.runtime.util.MapFactory.HasStroke;
import com.google.appinventor.components.runtime.util.MapFactory.MapCircle;
import com.google.appinventor.components.runtime.util.MapFactory.MapController;
import com.google.appinventor.components.runtime.util.MapFactory.MapEventListener;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeature;
import com.google.appinventor.components.runtime.util.MapFactory.MapLineString;
import com.google.appinventor.components.runtime.util.MapFactory.MapMarker;
import com.google.appinventor.components.runtime.util.MapFactory.MapPolygon;
import com.google.appinventor.components.runtime.util.MapFactory.MapRectangle;
import com.google.appinventor.components.runtime.util.MapFactory.MapType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.OnTapListener;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Marker.OnMarkerClickListener;
import org.osmdroid.views.overlay.Marker.OnMarkerDragListener;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.OverlayWithIWVisitor;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polygon.OnClickListener;
import org.osmdroid.views.overlay.Polygon.OnDragListener;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.infowindow.OverlayInfoWindow;
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

class NativeOpenStreetMapController implements MapController, MapListener {
    private static final long SPECIFIED_FILL = 1;
    private static final long SPECIFIED_FILL_OPACITY = 4;
    private static final long SPECIFIED_STROKE = 8;
    private static final long SPECIFIED_STROKE_OPACITY = 16;
    private static final long SPECIFIED_STROKE_WIDTH = 32;
    private static final String TAG = NativeOpenStreetMapController.class.getSimpleName();
    private boolean caches;
    private CompassOverlay compass = null;
    private OverlayInfoWindow defaultInfoWindow = null;
    private SVG defaultMarkerSVG = null;
    private Set<MapEventListener> eventListeners = new HashSet();
    private Map<MapFeature, OverlayWithIW> featureOverlays = new HashMap();
    private final Form form;
    private final AppInventorLocationSensorAdapter locationProvider;
    private boolean ready = false;
    private RotationGestureOverlay rotation = null;
    private MapType tileType;
    private TouchOverlay touch = null;
    private final MyLocationNewOverlay userLocation;
    private MapView view;
    private boolean zoomControlEnabled;
    private boolean zoomEnabled;

    class C03361 implements OnTapListener {
        C03361() {
        }

        public void onSingleTap(MapView view, double latitude, double longitude) {
            for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                listener.onSingleTap(latitude, longitude);
            }
        }

        public void onDoubleTap(MapView view, double latitude, double longitude) {
            for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                listener.onDoubleTap(latitude, longitude);
            }
        }
    }

    private class CustomMapView extends MapView {
        public CustomMapView(Context context) {
            super(context, null, new MapReadyHandler());
        }

        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            scrollTo(getScrollX() + ((oldw - w) / 2), getScrollY() + ((oldh - h) / 2));
            super.onSizeChanged(w, h, oldw, oldh);
        }

        public void onDetach() {
        }
    }

    private class MapReadyHandler extends Handler {

        class C03451 implements Runnable {
            C03451() {
            }

            public void run() {
                for (MapEventListener l : NativeOpenStreetMapController.this.eventListeners) {
                    l.onReady(NativeOpenStreetMapController.this);
                }
            }
        }

        private MapReadyHandler() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (!NativeOpenStreetMapController.this.ready && NativeOpenStreetMapController.this.form.canDispatchEvent(null, "MapReady")) {
                        NativeOpenStreetMapController.this.ready = true;
                        NativeOpenStreetMapController.this.form.runOnUiThread(new C03451());
                    }
                    NativeOpenStreetMapController.this.view.invalidate();
                    return;
                default:
                    return;
            }
        }
    }

    static class MultiPolygon extends Polygon {
        private List<Polygon> children = new ArrayList();
        private OnClickListener clickListener;
        private OnDragListener dragListener;
        private boolean draggable;

        MultiPolygon() {
        }

        public void showInfoWindow() {
            if (this.children.size() > 0) {
                ((Polygon) this.children.get(0)).showInfoWindow();
            }
        }

        public void draw(Canvas canvas, MapView mapView, boolean b) {
            for (Polygon child : this.children) {
                child.draw(canvas, mapView, b);
            }
        }

        public void setMultiPoints(List<List<GeoPoint>> points) {
            Iterator<Polygon> polygonIterator = this.children.iterator();
            Iterator<List<GeoPoint>> pointIterator = points.iterator();
            while (polygonIterator.hasNext() && pointIterator.hasNext()) {
                ((Polygon) polygonIterator.next()).setPoints((List) pointIterator.next());
            }
            while (polygonIterator.hasNext()) {
                polygonIterator.next();
                polygonIterator.remove();
            }
            while (pointIterator.hasNext()) {
                Polygon p = new Polygon();
                p.setPoints((List) pointIterator.next());
                p.setStrokeColor(getStrokeColor());
                p.setFillColor(getFillColor());
                p.setStrokeWidth(getStrokeWidth());
                p.setInfoWindow(getInfoWindow());
                p.setDraggable(this.draggable);
                p.setOnClickListener(this.clickListener);
                p.setOnDragListener(this.dragListener);
                this.children.add(p);
            }
        }

        public void setMultiHoles(List<List<List<GeoPoint>>> holes) {
            if (holes == null || holes.isEmpty()) {
                for (Polygon child : this.children) {
                    child.setHoles(Collections.emptyList());
                }
            } else if (holes.size() != this.children.size()) {
                throw new IllegalArgumentException("Holes and points are not of the same arity.");
            } else {
                Iterator<Polygon> polygonIterator = this.children.iterator();
                Iterator<List<List<GeoPoint>>> holeIterator = holes.iterator();
                while (polygonIterator.hasNext() && holeIterator.hasNext()) {
                    ((Polygon) polygonIterator.next()).setHoles((List) holeIterator.next());
                }
            }
        }

        public void setDraggable(boolean draggable) {
            super.setDraggable(draggable);
            this.draggable = draggable;
            for (Polygon child : this.children) {
                child.setDraggable(draggable);
            }
        }

        public void setOnClickListener(OnClickListener listener) {
            super.setOnClickListener(listener);
            this.clickListener = listener;
            for (Polygon child : this.children) {
                child.setOnClickListener(listener);
            }
        }

        public void setOnDragListener(OnDragListener listener) {
            super.setOnDragListener(listener);
            this.dragListener = listener;
            for (Polygon child : this.children) {
                child.setOnDragListener(listener);
            }
        }

        public void setStrokeWidth(float strokeWidth) {
            super.setStrokeWidth(strokeWidth);
            for (Polygon child : this.children) {
                child.setStrokeWidth(strokeWidth);
            }
        }

        public void setStrokeColor(int strokeColor) {
            super.setStrokeColor(strokeColor);
            for (Polygon child : this.children) {
                child.setStrokeColor(strokeColor);
            }
        }

        public void setFillColor(int fillColor) {
            super.setFillColor(fillColor);
            for (Polygon child : this.children) {
                child.setFillColor(fillColor);
            }
        }

        public void setTitle(String title) {
            super.setTitle(title);
            for (Polygon child : this.children) {
                child.setTitle(title);
            }
        }

        public void setSnippet(String snippet) {
            super.setSnippet(snippet);
            for (Polygon child : this.children) {
                child.setSnippet(snippet);
            }
        }

        public boolean onSingleTapConfirmed(MotionEvent event, MapView mapView) {
            for (Polygon child : this.children) {
                if (child.onSingleTapConfirmed(event, mapView)) {
                    return true;
                }
            }
            return false;
        }

        public boolean contains(MotionEvent event) {
            for (Polygon child : this.children) {
                if (child.contains(event)) {
                    return true;
                }
            }
            return false;
        }

        public boolean onLongPress(MotionEvent event, MapView mapView) {
            boolean touched = contains(event);
            if (touched) {
                if (this.mDraggable) {
                    this.mIsDragged = true;
                    closeInfoWindow();
                    this.mDragStartPoint = event;
                    if (this.mOnDragListener != null) {
                        this.mOnDragListener.onDragStart(this);
                    }
                    moveToEventPosition(event, this.mDragStartPoint, mapView);
                } else if (this.mOnClickListener != null) {
                    this.mOnClickListener.onLongClick(this, mapView, (GeoPoint) mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY()));
                }
            }
            return touched;
        }

        public void moveToEventPosition(MotionEvent event, MotionEvent start, MapView view) {
            for (Polygon child : this.children) {
                child.moveToEventPosition(event, start, view);
            }
        }

        public void finishMove(MotionEvent start, MotionEvent end, MapView view) {
            for (Polygon child : this.children) {
                child.finishMove(start, end, view);
            }
        }

        public boolean onTouchEvent(MotionEvent event, MapView mapView) {
            if (this.mDraggable && this.mIsDragged) {
                if (event.getAction() == 1) {
                    this.mIsDragged = false;
                    finishMove(this.mDragStartPoint, event, mapView);
                    if (this.mOnDragListener == null) {
                        return true;
                    }
                    this.mOnDragListener.onDragEnd(this);
                    return true;
                } else if (event.getAction() == 2) {
                    moveToEventPosition(event, this.mDragStartPoint, mapView);
                    if (this.mOnDragListener == null) {
                        return true;
                    }
                    this.mOnDragListener.onDrag(this);
                    return true;
                }
            }
            return false;
        }
    }

    private class TouchOverlay extends Overlay {
        private boolean scrollEnabled;

        private TouchOverlay() {
            this.scrollEnabled = true;
        }

        public void draw(Canvas arg0, MapView arg1, boolean arg2) {
        }

        public boolean onFling(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY, MapView mapView) {
            return !this.scrollEnabled;
        }

        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY, MapView mapView) {
            return !this.scrollEnabled;
        }

        public boolean onLongPress(MotionEvent pEvent, MapView pMapView) {
            IGeoPoint p = pMapView.getProjection().fromPixels((int) pEvent.getX(), (int) pEvent.getY());
            double lat = p.getLatitude();
            double lng = p.getLongitude();
            for (MapEventListener l : NativeOpenStreetMapController.this.eventListeners) {
                l.onLongPress(lat, lng);
            }
            return false;
        }
    }

    private static class AppInventorLocationSensorAdapter implements IMyLocationProvider, LocationSensorListener {
        private IMyLocationConsumer consumer;
        private boolean enabled;
        private Location lastLocation;
        private LocationSensor source;

        private AppInventorLocationSensorAdapter() {
            this.enabled = false;
        }

        public void setSource(LocationSensor source) {
            if (this.source != source) {
                if (this.source != null) {
                    this.source.Enabled(false);
                }
                this.source = source;
                if (this.source != null) {
                    this.source.Enabled(this.enabled);
                }
            }
        }

        public void onTimeIntervalChanged(int time) {
        }

        public void onDistanceIntervalChanged(int distance) {
        }

        public void onLocationChanged(Location location) {
            this.lastLocation = location;
            if (this.consumer != null) {
                this.consumer.onLocationChanged(location, this);
            }
        }

        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String s) {
        }

        public void onProviderDisabled(String s) {
        }

        public boolean startLocationProvider(IMyLocationConsumer consumer) {
            this.consumer = consumer;
            if (this.source != null) {
                this.source.Enabled(true);
                this.enabled = true;
            }
            return this.enabled;
        }

        public void stopLocationProvider() {
            if (this.source != null) {
                this.source.Enabled(false);
            }
            this.enabled = false;
        }

        public Location getLastKnownLocation() {
            return this.lastLocation;
        }

        public void destroy() {
            this.consumer = null;
        }
    }

    NativeOpenStreetMapController(Form form) {
        File osmdroid = new File(form.getCacheDir(), "osmdroid");
        if (osmdroid.exists() || osmdroid.mkdirs()) {
            Configuration.getInstance().setOsmdroidBasePath(osmdroid);
            File osmdroidTiles = new File(osmdroid, "tiles");
            if (osmdroidTiles.exists() || osmdroidTiles.mkdirs()) {
                Configuration.getInstance().setOsmdroidTileCache(osmdroidTiles);
                this.caches = true;
            }
        }
        this.form = form;
        this.touch = new TouchOverlay();
        this.view = new CustomMapView(form.getApplicationContext());
        this.locationProvider = new AppInventorLocationSensorAdapter();
        this.defaultInfoWindow = new OverlayInfoWindow(this.view);
        this.view.setTilesScaledToDpi(true);
        this.view.setMapListener(this);
        this.view.getOverlayManager().add(this.touch);
        this.view.addOnTapListener(new C03361());
        this.userLocation = new MyLocationNewOverlay(this.locationProvider, this.view);
    }

    public View getView() {
        return this.view;
    }

    public double getLatitude() {
        return this.view.getMapCenter().getLatitude();
    }

    public double getLongitude() {
        return this.view.getMapCenter().getLongitude();
    }

    public void setCenter(double latitude, double longitude) {
        this.view.getController().setCenter(new GeoPoint(latitude, longitude));
    }

    public void setZoom(int zoom) {
        this.view.getController().setZoom((double) zoom);
    }

    public int getZoom() {
        return (int) this.view.getZoomLevel(false);
    }

    public void setZoomEnabled(boolean enable) {
        this.zoomEnabled = enable;
        this.view.setMultiTouchControls(enable);
    }

    public boolean isZoomEnabled() {
        return this.zoomEnabled;
    }

    public void setMapType(MapType type) {
        switch (type) {
            case ROADS:
                this.tileType = type;
                this.view.setTileSource(TileSourceFactory.MAPNIK);
                return;
            case AERIAL:
                this.tileType = type;
                this.view.setTileSource(TileSourceFactory.USGS_SAT);
                return;
            case TERRAIN:
                this.tileType = type;
                this.view.setTileSource(TileSourceFactory.USGS_TOPO);
                return;
            default:
                return;
        }
    }

    public MapType getMapType() {
        return this.tileType;
    }

    public void setCompassEnabled(boolean enabled) {
        if (enabled && this.compass == null) {
            this.compass = new CompassOverlay(this.view.getContext(), this.view);
            this.view.getOverlayManager().add(this.compass);
        }
        if (this.compass == null) {
            return;
        }
        if (enabled) {
            this.compass.enableCompass();
        } else {
            this.compass.disableCompass();
        }
    }

    public boolean isCompassEnabled() {
        return this.compass != null && this.compass.isEnabled();
    }

    public void setZoomControlEnabled(boolean enabled) {
        this.view.setBuiltInZoomControls(enabled);
        this.zoomControlEnabled = enabled;
    }

    public boolean isZoomControlEnabled() {
        return this.zoomControlEnabled;
    }

    public void setShowUserEnabled(boolean enable) {
        this.userLocation.setEnabled(enable);
        if (enable) {
            this.userLocation.enableMyLocation();
            this.view.getOverlayManager().add(this.userLocation);
            return;
        }
        this.userLocation.disableMyLocation();
        this.view.getOverlayManager().remove(this.userLocation);
    }

    public boolean isShowUserEnabled() {
        return this.userLocation != null && this.userLocation.isEnabled();
    }

    public void setRotationEnabled(boolean enabled) {
        if (enabled && this.rotation == null) {
            this.rotation = new RotationGestureOverlay(this.view);
        }
        if (this.rotation != null) {
            this.rotation.setEnabled(enabled);
            if (enabled) {
                this.view.getOverlayManager().add(this.rotation);
            } else {
                this.view.getOverlayManager().remove(this.rotation);
            }
        }
    }

    public boolean isRotationEnabled() {
        return this.rotation != null && this.rotation.isEnabled();
    }

    public void setPanEnabled(boolean enable) {
        this.touch.scrollEnabled = enable;
    }

    public boolean isPanEnabled() {
        return this.touch.scrollEnabled;
    }

    public void panTo(double latitude, double longitude, int zoom, double seconds) {
        this.view.getController().animateTo(new GeoPoint(latitude, longitude));
        if (this.view.getController().zoomTo((double) zoom)) {
            Animation animation = this.view.getAnimation();
            if (animation != null) {
                animation.setDuration((long) (1000.0d * seconds));
            }
        }
    }

    public void addEventListener(MapEventListener listener) {
        this.eventListeners.add(listener);
        if ((this.ready || ViewCompat.isAttachedToWindow(this.view)) && this.form.canDispatchEvent(null, "MapReady")) {
            this.ready = true;
            listener.onReady(this);
        }
    }

    public void addFeature(final MapMarker aiMarker) {
        createNativeMarker(aiMarker, new AsyncCallbackPair<Marker>() {

            class C03371 implements OnMarkerClickListener {
                C03371() {
                }

                public boolean onMarkerClick(Marker marker, MapView mapView) {
                    for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                        listener.onFeatureClick(aiMarker);
                    }
                    if (aiMarker.EnableInfobox()) {
                        marker.showInfoWindow();
                    }
                    return false;
                }

                public boolean onMarkerLongPress(Marker marker, MapView mapView) {
                    for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                        listener.onFeatureLongPress(aiMarker);
                    }
                    return false;
                }
            }

            class C03382 implements OnMarkerDragListener {
                C03382() {
                }

                public void onMarkerDrag(Marker marker) {
                    for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                        listener.onFeatureDrag(aiMarker);
                    }
                }

                public void onMarkerDragEnd(Marker marker) {
                    IGeoPoint point = marker.getPosition();
                    aiMarker.updateLocation(point.getLatitude(), point.getLongitude());
                    for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                        listener.onFeatureStopDrag(aiMarker);
                    }
                }

                public void onMarkerDragStart(Marker marker) {
                    for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                        listener.onFeatureStartDrag(aiMarker);
                    }
                }
            }

            public void onFailure(String message) {
                Log.e(NativeOpenStreetMapController.TAG, "Unable to create marker: " + message);
            }

            public void onSuccess(Marker overlay) {
                overlay.setOnMarkerClickListener(new C03371());
                overlay.setOnMarkerDragListener(new C03382());
                if (aiMarker.Visible()) {
                    NativeOpenStreetMapController.this.showOverlay(overlay);
                } else {
                    NativeOpenStreetMapController.this.hideOverlay(overlay);
                }
            }
        });
    }

    public void addFeature(final MapLineString aiPolyline) {
        Polyline polyline = createNativePolyline(aiPolyline);
        this.featureOverlays.put(aiPolyline, polyline);
        polyline.setOnClickListener(new Polyline.OnClickListener() {
            public boolean onClick(Polyline arg0, MapView arg1, GeoPoint arg2) {
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureClick(aiPolyline);
                }
                if (aiPolyline.EnableInfobox()) {
                    arg0.showInfoWindow(arg2);
                }
                return true;
            }

            public boolean onLongClick(Polyline arg0, MapView arg1, GeoPoint arg2) {
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureLongPress(aiPolyline);
                }
                return true;
            }
        });
        polyline.setOnDragListener(new Polyline.OnDragListener() {
            public void onDragStart(Polyline polyline) {
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureStartDrag(aiPolyline);
                }
            }

            public void onDrag(Polyline polyline) {
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureDrag(aiPolyline);
                }
            }

            public void onDragEnd(Polyline polyline) {
                aiPolyline.updatePoints(polyline.getPoints());
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureStopDrag(aiPolyline);
                }
            }
        });
        if (aiPolyline.Visible()) {
            showOverlay(polyline);
        } else {
            hideOverlay(polyline);
        }
    }

    private void configurePolygon(final MapFeature component, Polygon polygon) {
        this.featureOverlays.put(component, polygon);
        polygon.setOnClickListener(new OnClickListener() {
            public boolean onLongClick(Polygon arg0, MapView arg1, GeoPoint arg2) {
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureLongPress(component);
                }
                return true;
            }

            public boolean onClick(Polygon arg0, MapView arg1, GeoPoint arg2) {
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureClick(component);
                }
                if (component.EnableInfobox()) {
                    arg0.showInfoWindow(arg2);
                }
                return true;
            }
        });
        polygon.setOnDragListener(new OnDragListener() {
            public void onDragStart(Polygon polygon) {
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureStartDrag(component);
                }
            }

            public void onDrag(Polygon polygon) {
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureDrag(component);
                }
            }

            public void onDragEnd(Polygon polygon) {
                if (component instanceof MapCircle) {
                    double latitude = 0.0d;
                    double longitude = 0.0d;
                    int count = polygon.getPoints().size();
                    for (GeoPoint p : polygon.getPoints()) {
                        latitude += p.getLatitude();
                        longitude += p.getLongitude();
                    }
                    if (count > 0) {
                        ((MapCircle) component).updateCenter(latitude / ((double) count), longitude / ((double) count));
                    } else {
                        ((MapCircle) component).updateCenter(0.0d, 0.0d);
                    }
                } else if (component instanceof MapRectangle) {
                    double north = -90.0d;
                    double east = -180.0d;
                    double west = 180.0d;
                    double south = 90.0d;
                    for (GeoPoint p2 : polygon.getPoints()) {
                        double lat = p2.getLatitude();
                        double lng = p2.getLongitude();
                        north = Math.max(north, lat);
                        south = Math.min(south, lat);
                        east = Math.max(east, lng);
                        west = Math.min(west, lng);
                    }
                    ((MapRectangle) component).updateBounds(north, west, south, east);
                } else {
                    ((MapPolygon) component).updatePoints(Collections.singletonList(polygon.getPoints()));
                    List<List<GeoPoint>> holes = new ArrayList();
                    holes.addAll(polygon.getHoles());
                    ((MapPolygon) component).updateHolePoints(Collections.singletonList(holes));
                }
                for (MapEventListener listener : NativeOpenStreetMapController.this.eventListeners) {
                    listener.onFeatureStopDrag(component);
                }
            }
        });
        if (component.Visible()) {
            showOverlay(polygon);
        } else {
            hideOverlay(polygon);
        }
    }

    public void addFeature(MapPolygon aiPolygon) {
        configurePolygon(aiPolygon, createNativePolygon(aiPolygon));
    }

    public void addFeature(MapCircle aiCircle) {
        configurePolygon(aiCircle, createNativeCircle(aiCircle));
    }

    public void addFeature(MapRectangle aiRectangle) {
        configurePolygon(aiRectangle, createNativeRectangle(aiRectangle));
    }

    public void removeFeature(MapFeature aiFeature) {
        this.view.getOverlayManager().remove(this.featureOverlays.get(aiFeature));
        this.featureOverlays.remove(aiFeature);
    }

    public void updateFeaturePosition(MapMarker aiMarker) {
        Marker marker = (Marker) this.featureOverlays.get(aiMarker);
        if (marker != null) {
            marker.setPosition(new GeoPoint(aiMarker.Latitude(), aiMarker.Longitude()));
            this.view.invalidate();
        }
    }

    public void updateFeaturePosition(MapLineString aiPolyline) {
        Polyline overlay = (Polyline) this.featureOverlays.get(aiPolyline);
        if (overlay != null) {
            overlay.setPoints(aiPolyline.getPoints());
            this.view.invalidate();
        }
    }

    public void updateFeaturePosition(MapPolygon aiPolygon) {
        MultiPolygon polygon = (MultiPolygon) this.featureOverlays.get(aiPolygon);
        if (polygon != null) {
            polygon.setMultiPoints(aiPolygon.getPoints());
            polygon.setMultiHoles(aiPolygon.getHolePoints());
            this.view.invalidate();
        }
    }

    public void updateFeaturePosition(MapCircle aiCircle) {
        GeoPoint center = new GeoPoint(aiCircle.Latitude(), aiCircle.Longitude());
        Polygon polygon = (Polygon) this.featureOverlays.get(aiCircle);
        if (polygon != null) {
            polygon.setPoints(Polygon.pointsAsCircle(center, aiCircle.Radius()));
            this.view.invalidate();
        }
    }

    public void updateFeaturePosition(MapRectangle aiRectangle) {
        Polygon polygon = (Polygon) this.featureOverlays.get(aiRectangle);
        if (polygon != null) {
            polygon.setPoints(Polygon.pointsAsRect(new BoundingBox(aiRectangle.NorthLatitude(), aiRectangle.EastLongitude(), aiRectangle.SouthLatitude(), aiRectangle.WestLongitude())));
            this.view.invalidate();
        }
    }

    public void updateFeatureFill(final HasFill aiFeature) {
        OverlayWithIW overlay = (OverlayWithIW) this.featureOverlays.get(aiFeature);
        if (overlay != null) {
            overlay.accept(new OverlayWithIWVisitor() {
                public void visit(final Marker marker) {
                    NativeOpenStreetMapController.this.getMarkerDrawable((MapMarker) aiFeature, new AsyncCallbackPair<Drawable>() {
                        public void onFailure(String message) {
                            Log.e(NativeOpenStreetMapController.TAG, "Unable to update fill color for marker: " + message);
                        }

                        public void onSuccess(Drawable result) {
                            marker.setIcon(result);
                            NativeOpenStreetMapController.this.view.invalidate();
                        }
                    });
                }

                public void visit(Polyline polyline) {
                }

                public void visit(Polygon polygon) {
                    polygon.setFillColor(aiFeature.FillColor());
                    NativeOpenStreetMapController.this.view.invalidate();
                }
            });
        }
    }

    public void updateFeatureStroke(final HasStroke aiFeature) {
        OverlayWithIW overlay = (OverlayWithIW) this.featureOverlays.get(aiFeature);
        if (overlay != null) {
            overlay.accept(new OverlayWithIWVisitor() {
                public void visit(final Marker marker) {
                    NativeOpenStreetMapController.this.getMarkerDrawable((MapMarker) aiFeature, new AsyncCallbackPair<Drawable>() {
                        public void onFailure(String message) {
                            Log.e(NativeOpenStreetMapController.TAG, "Unable to update stroke color for marker: " + message);
                        }

                        public void onSuccess(Drawable result) {
                            marker.setIcon(result);
                            NativeOpenStreetMapController.this.view.invalidate();
                        }
                    });
                }

                public void visit(Polyline polyline) {
                    DisplayMetrics metrics = new DisplayMetrics();
                    NativeOpenStreetMapController.this.form.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    polyline.setColor(aiFeature.StrokeColor());
                    polyline.setWidth(((float) aiFeature.StrokeWidth()) * metrics.density);
                    NativeOpenStreetMapController.this.view.invalidate();
                }

                public void visit(Polygon polygon) {
                    DisplayMetrics metrics = new DisplayMetrics();
                    NativeOpenStreetMapController.this.form.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    polygon.setStrokeColor(aiFeature.StrokeColor());
                    polygon.setStrokeWidth(((float) aiFeature.StrokeWidth()) * metrics.density);
                    NativeOpenStreetMapController.this.view.invalidate();
                }
            });
        }
    }

    public void updateFeatureText(MapFeature aiFeature) {
        OverlayWithIW overlay = (OverlayWithIW) this.featureOverlays.get(aiFeature);
        if (overlay != null) {
            overlay.setTitle(aiFeature.Title());
            overlay.setSnippet(aiFeature.Description());
        }
    }

    public void updateFeatureDraggable(MapFeature aiFeature) {
        OverlayWithIW overlay = (OverlayWithIW) this.featureOverlays.get(aiFeature);
        if (overlay != null) {
            overlay.setDraggable(aiFeature.Draggable());
        }
    }

    public void updateFeatureImage(MapMarker aiMarker) {
        final Marker marker = (Marker) this.featureOverlays.get(aiMarker);
        if (marker != null) {
            getMarkerDrawable(aiMarker, new AsyncCallbackPair<Drawable>() {
                public void onFailure(String message) {
                    Log.e(NativeOpenStreetMapController.TAG, "Unable to update feature image: " + message);
                }

                public void onSuccess(Drawable result) {
                    marker.setIcon(result);
                    NativeOpenStreetMapController.this.view.invalidate();
                }
            });
        }
    }

    public void updateFeatureSize(MapMarker aiMarker) {
        final Marker marker = (Marker) this.featureOverlays.get(aiMarker);
        if (marker != null) {
            getMarkerDrawable(aiMarker, new AsyncCallbackPair<Drawable>() {
                public void onFailure(String message) {
                    Log.wtf(NativeOpenStreetMapController.TAG, "Cannot find default marker");
                }

                public void onSuccess(Drawable result) {
                    marker.setIcon(result);
                    NativeOpenStreetMapController.this.view.invalidate();
                }
            });
        }
    }

    private void getMarkerDrawable(MapMarker aiMarker, AsyncCallbackPair<Drawable> callback) {
        String assetPath = aiMarker.ImageAsset();
        if (assetPath == null || assetPath.length() == 0 || assetPath.endsWith(".svg")) {
            getMarkerDrawableVector(aiMarker, callback);
        } else {
            getMarkerDrawableRaster(aiMarker, callback);
        }
    }

    private void getMarkerDrawableVector(MapMarker aiMarker, AsyncCallbackPair<Drawable> callback) {
        SVG markerSvg = null;
        if (this.defaultMarkerSVG == null) {
            try {
                this.defaultMarkerSVG = SVG.getFromAsset(this.view.getContext().getAssets(), "component/marker.svg");
            } catch (SVGParseException e) {
                Log.e(TAG, "Invalid SVG in Marker asset", e);
            } catch (IOException e2) {
                Log.e(TAG, "Unable to read Marker asset", e2);
            }
            if (this.defaultMarkerSVG == null || this.defaultMarkerSVG.getRootElement() == null) {
                throw new IllegalStateException("Unable to load SVG from assets");
            }
        }
        String markerAsset = aiMarker.ImageAsset();
        if (!(markerAsset == null || markerAsset.length() == 0)) {
            try {
                markerSvg = SVG.getFromAsset(this.view.getContext().getAssets(), markerAsset);
            } catch (SVGParseException e3) {
                Log.e(TAG, "Invalid SVG in Marker asset", e3);
            } catch (IOException e22) {
                Log.e(TAG, "Unable to read Marker asset", e22);
            }
            if (markerSvg == null) {
                InputStream inputStream = null;
                try {
                    inputStream = MediaUtil.openMedia(this.form, markerAsset);
                    markerSvg = SVG.getFromInputStream(inputStream);
                } catch (SVGParseException e32) {
                    Log.e(TAG, "Invalid SVG in Marker asset", e32);
                } catch (IOException e222) {
                    Log.e(TAG, "Unable to read Marker asset", e222);
                } finally {
                    IOUtils.closeQuietly(TAG, inputStream);
                }
            }
        }
        if (markerSvg == null) {
            markerSvg = this.defaultMarkerSVG;
        }
        try {
            callback.onSuccess(rasterizeSVG(aiMarker, markerSvg));
        } catch (Exception e4) {
            callback.onFailure(e4.getMessage());
        }
    }

    private void getMarkerDrawableRaster(final MapMarker aiMarker, final AsyncCallbackPair<Drawable> callback) {
        MediaUtil.getBitmapDrawableAsync(this.form, aiMarker.ImageAsset(), new AsyncCallbackPair<BitmapDrawable>() {
            public void onFailure(String message) {
                callback.onSuccess(NativeOpenStreetMapController.this.getDefaultMarkerDrawable(aiMarker));
            }

            public void onSuccess(BitmapDrawable result) {
                callback.onSuccess(result);
            }
        });
    }

    private Drawable getDefaultMarkerDrawable(MapMarker aiMarker) {
        return rasterizeSVG(aiMarker, this.defaultMarkerSVG);
    }

    private static float getBestGuessWidth(Svg svg) {
        if (svg.width != null) {
            return svg.width.floatValue();
        }
        if (svg.viewBox != null) {
            return svg.viewBox.width;
        }
        return Component.SLIDER_THUMB_VALUE;
    }

    private static float getBestGuessHeight(Svg svg) {
        if (svg.height != null) {
            return svg.height.floatValue();
        }
        if (svg.viewBox != null) {
            return svg.viewBox.height;
        }
        return Component.SLIDER_MAX_VALUE;
    }

    private Drawable rasterizeSVG(MapMarker aiMarker, SVG markerSvg) {
        float width;
        Svg svg = markerSvg.getRootElement();
        float density = this.view.getContext().getResources().getDisplayMetrics().density;
        float height = aiMarker.Height() <= 0 ? getBestGuessHeight(svg) : (float) aiMarker.Height();
        if (aiMarker.Width() <= 0) {
            width = getBestGuessWidth(svg);
        } else {
            width = (float) aiMarker.Width();
        }
        float scaleH = height / getBestGuessHeight(svg);
        float scaleW = width / getBestGuessWidth(svg);
        float scale = (float) Math.sqrt((double) ((scaleH * scaleH) + (scaleW * scaleW)));
        Paint fillPaint = new Paint();
        Paint strokePaint = new Paint();
        PaintUtil.changePaint(fillPaint, aiMarker.FillColor());
        PaintUtil.changePaint(strokePaint, aiMarker.StrokeColor());
        Length length = new Length(((float) aiMarker.StrokeWidth()) / scale);
        for (SvgObject element : svg.getChildren()) {
            if (element instanceof SvgConditionalElement) {
                SvgConditionalElement path = (SvgConditionalElement) element;
                path.baseStyle.fill = new Colour(fillPaint.getColor());
                path.baseStyle.fillOpacity = Float.valueOf(((float) fillPaint.getAlpha()) / 255.0f);
                path.baseStyle.stroke = new Colour(strokePaint.getColor());
                path.baseStyle.strokeOpacity = Float.valueOf(((float) strokePaint.getAlpha()) / 255.0f);
                path.baseStyle.strokeWidth = length;
                if (path.style != null) {
                    Style style;
                    if ((path.style.specifiedFlags & 1) == 0) {
                        path.style.fill = new Colour(fillPaint.getColor());
                        style = path.style;
                        style.specifiedFlags |= 1;
                    }
                    if ((path.style.specifiedFlags & 4) == 0) {
                        path.style.fillOpacity = Float.valueOf(((float) fillPaint.getAlpha()) / 255.0f);
                        style = path.style;
                        style.specifiedFlags |= 4;
                    }
                    if ((path.style.specifiedFlags & 8) == 0) {
                        path.style.stroke = new Colour(strokePaint.getColor());
                        style = path.style;
                        style.specifiedFlags |= 8;
                    }
                    if ((path.style.specifiedFlags & 16) == 0) {
                        path.style.strokeOpacity = Float.valueOf(((float) strokePaint.getAlpha()) / 255.0f);
                        style = path.style;
                        style.specifiedFlags |= 16;
                    }
                    if ((path.style.specifiedFlags & 32) == 0) {
                        path.style.strokeWidth = length;
                        style = path.style;
                        style.specifiedFlags |= 32;
                    }
                }
            }
        }
        Picture picture = markerSvg.renderToPicture();
        Picture scaledPicture = new Picture();
        Canvas canvas = scaledPicture.beginRecording((int) (((2.0f * ((float) aiMarker.StrokeWidth())) + width) * density), (int) (((2.0f * ((float) aiMarker.StrokeWidth())) + height) * density));
        canvas.scale(density * scaleW, density * scaleH);
        canvas.translate(length.floatValue(), length.floatValue());
        picture.draw(canvas);
        scaledPicture.endRecording();
        return new PictureDrawable(scaledPicture);
    }

    private void createNativeMarker(MapMarker aiMarker, AsyncCallbackPair<Marker> callback) {
        final Marker osmMarker = new Marker(this.view);
        this.featureOverlays.put(aiMarker, osmMarker);
        osmMarker.setDraggable(aiMarker.Draggable());
        osmMarker.setTitle(aiMarker.Title());
        osmMarker.setSnippet(aiMarker.Description());
        osmMarker.setPosition(new GeoPoint(aiMarker.Latitude(), aiMarker.Longitude()));
        osmMarker.setAnchor(0.5f, 1.0f);
        getMarkerDrawable(aiMarker, new AsyncCallbackFacade<Drawable, Marker>(callback) {
            public void onFailure(String message) {
                this.callback.onFailure(message);
            }

            public void onSuccess(Drawable result) {
                osmMarker.setIcon(result);
                this.callback.onSuccess(osmMarker);
            }
        });
    }

    private Polyline createNativePolyline(MapLineString aiLineString) {
        Polyline osmLine = new Polyline();
        osmLine.setDraggable(aiLineString.Draggable());
        osmLine.setTitle(aiLineString.Title());
        osmLine.setSnippet(aiLineString.Description());
        osmLine.setPoints(aiLineString.getPoints());
        osmLine.setColor(aiLineString.StrokeColor());
        osmLine.setWidth((float) aiLineString.StrokeWidth());
        osmLine.setInfoWindow(this.defaultInfoWindow);
        return osmLine;
    }

    private void createPolygon(Polygon osmPolygon, MapFeature aiFeature) {
        osmPolygon.setDraggable(aiFeature.Draggable());
        osmPolygon.setTitle(aiFeature.Title());
        osmPolygon.setSnippet(aiFeature.Description());
        osmPolygon.setStrokeColor(((HasStroke) aiFeature).StrokeColor());
        osmPolygon.setStrokeWidth((float) ((HasStroke) aiFeature).StrokeWidth());
        osmPolygon.setFillColor(((HasFill) aiFeature).FillColor());
        osmPolygon.setInfoWindow(this.defaultInfoWindow);
    }

    private MultiPolygon createNativePolygon(MapPolygon aiPolygon) {
        MultiPolygon osmPolygon = new MultiPolygon();
        createPolygon(osmPolygon, aiPolygon);
        osmPolygon.setMultiPoints(aiPolygon.getPoints());
        osmPolygon.setMultiHoles(aiPolygon.getHolePoints());
        return osmPolygon;
    }

    private Polygon createNativeCircle(MapCircle aiCircle) {
        Polygon osmPolygon = new Polygon();
        createPolygon(osmPolygon, aiCircle);
        osmPolygon.setPoints(Polygon.pointsAsCircle(new GeoPoint(aiCircle.Latitude(), aiCircle.Longitude()), aiCircle.Radius()));
        return osmPolygon;
    }

    private Polygon createNativeRectangle(MapRectangle aiRectangle) {
        BoundingBox bbox = new BoundingBox(aiRectangle.NorthLatitude(), aiRectangle.EastLongitude(), aiRectangle.SouthLatitude(), aiRectangle.WestLongitude());
        Polygon osmPolygon = new Polygon();
        createPolygon(osmPolygon, aiRectangle);
        osmPolygon.setPoints(new ArrayList(Polygon.pointsAsRect(bbox)));
        return osmPolygon;
    }

    public void showFeature(MapFeature feature) {
        showOverlay((OverlayWithIW) this.featureOverlays.get(feature));
    }

    protected void showOverlay(OverlayWithIW overlay) {
        this.view.getOverlayManager().add(overlay);
    }

    public void hideFeature(MapFeature feature) {
        hideOverlay((OverlayWithIW) this.featureOverlays.get(feature));
    }

    protected void hideOverlay(OverlayWithIW overlay) {
        this.view.getOverlayManager().remove(overlay);
    }

    public boolean isFeatureVisible(MapFeature feature) {
        OverlayWithIW overlay = (OverlayWithIW) this.featureOverlays.get(feature);
        return overlay != null && this.view.getOverlayManager().contains(overlay);
    }

    public void showInfobox(MapFeature feature) {
        ((OverlayWithIW) this.featureOverlays.get(feature)).showInfoWindow();
    }

    public void hideInfobox(MapFeature feature) {
        ((OverlayWithIW) this.featureOverlays.get(feature)).closeInfoWindow();
    }

    public boolean isInfoboxVisible(MapFeature feature) {
        OverlayWithIW overlay = (OverlayWithIW) this.featureOverlays.get(feature);
        return overlay != null && overlay.isInfoWindowOpen();
    }

    public BoundingBox getBoundingBox() {
        return this.view.getBoundingBox();
    }

    public void setBoundingBox(BoundingBox bbox) {
        this.view.getController().setCenter(bbox.getCenter());
        this.view.getController().zoomToSpan(bbox.getLatitudeSpan(), bbox.getLongitudeSpan());
    }

    public boolean onScroll(ScrollEvent event) {
        for (MapEventListener listener : this.eventListeners) {
            listener.onBoundsChanged();
        }
        return true;
    }

    public boolean onZoom(ZoomEvent event) {
        for (MapEventListener listener : this.eventListeners) {
            listener.onZoom();
        }
        return true;
    }

    public LocationSensorListener getLocationListener() {
        return this.locationProvider;
    }

    public int getOverlayCount() {
        System.err.println(this.view.getOverlays());
        return this.view.getOverlays().size();
    }
}

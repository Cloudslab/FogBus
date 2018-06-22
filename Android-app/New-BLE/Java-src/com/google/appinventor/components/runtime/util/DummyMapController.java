package com.google.appinventor.components.runtime.util;

import android.view.View;
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
import org.osmdroid.util.BoundingBox;

class DummyMapController implements MapController {
    DummyMapController() {
    }

    public View getView() {
        throw new UnsupportedOperationException();
    }

    public double getLatitude() {
        throw new UnsupportedOperationException();
    }

    public double getLongitude() {
        throw new UnsupportedOperationException();
    }

    public void setCenter(double latitude, double longitude) {
        throw new UnsupportedOperationException();
    }

    public void setZoom(int zoom) {
        throw new UnsupportedOperationException();
    }

    public int getZoom() {
        throw new UnsupportedOperationException();
    }

    public void setMapType(MapType type) {
        throw new UnsupportedOperationException();
    }

    public MapType getMapType() {
        throw new UnsupportedOperationException();
    }

    public void setCompassEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    public boolean isCompassEnabled() {
        throw new UnsupportedOperationException();
    }

    public void setZoomEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    public boolean isZoomEnabled() {
        throw new UnsupportedOperationException();
    }

    public void setZoomControlEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    public boolean isZoomControlEnabled() {
        throw new UnsupportedOperationException();
    }

    public void setShowUserEnabled(boolean enable) {
        throw new UnsupportedOperationException();
    }

    public boolean isShowUserEnabled() {
        throw new UnsupportedOperationException();
    }

    public void setRotationEnabled(boolean enable) {
        throw new UnsupportedOperationException();
    }

    public boolean isRotationEnabled() {
        throw new UnsupportedOperationException();
    }

    public void setPanEnabled(boolean enable) {
        throw new UnsupportedOperationException();
    }

    public boolean isPanEnabled() {
        throw new UnsupportedOperationException();
    }

    public void panTo(double latitude, double longitude, int zoom, double seconds) {
        throw new UnsupportedOperationException();
    }

    public void addEventListener(MapEventListener listener) {
        throw new UnsupportedOperationException();
    }

    public void addFeature(MapMarker marker) {
        throw new UnsupportedOperationException();
    }

    public void updateFeaturePosition(MapMarker marker) {
        throw new UnsupportedOperationException();
    }

    public void updateFeatureFill(HasFill marker) {
        throw new UnsupportedOperationException();
    }

    public void updateFeatureImage(MapMarker marker) {
        throw new UnsupportedOperationException();
    }

    public void updateFeatureText(MapFeature marker) {
        throw new UnsupportedOperationException();
    }

    public void updateFeatureDraggable(MapFeature marker) {
        throw new UnsupportedOperationException();
    }

    public BoundingBox getBoundingBox() {
        throw new UnsupportedOperationException();
    }

    public void setBoundingBox(BoundingBox bbox) {
        throw new UnsupportedOperationException();
    }

    public void addFeature(MapLineString polyline) {
        throw new UnsupportedOperationException();
    }

    public void addFeature(MapPolygon polygon) {
        throw new UnsupportedOperationException();
    }

    public void addFeature(MapCircle circle) {
        throw new UnsupportedOperationException();
    }

    public void addFeature(MapRectangle circle) {
        throw new UnsupportedOperationException();
    }

    public void removeFeature(MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    public void showFeature(MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    public void hideFeature(MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    public boolean isFeatureVisible(MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    public void showInfobox(MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    public void hideInfobox(MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    public boolean isInfoboxVisible(MapFeature feature) {
        throw new UnsupportedOperationException();
    }

    public void updateFeaturePosition(MapLineString polyline) {
        throw new UnsupportedOperationException();
    }

    public void updateFeaturePosition(MapPolygon polygon) {
        throw new UnsupportedOperationException();
    }

    public void updateFeaturePosition(MapCircle circle) {
        throw new UnsupportedOperationException();
    }

    public void updateFeaturePosition(MapRectangle rectangle) {
        throw new UnsupportedOperationException();
    }

    public void updateFeatureStroke(HasStroke marker) {
        throw new UnsupportedOperationException();
    }

    public void updateFeatureSize(MapMarker marker) {
        throw new UnsupportedOperationException();
    }

    public LocationSensorListener getLocationListener() {
        throw new UnsupportedOperationException();
    }

    public int getOverlayCount() {
        throw new UnsupportedOperationException();
    }
}

package com.google.appinventor.components.runtime;

import android.view.View;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureCollection;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer;
import com.google.appinventor.components.runtime.util.YailList;
import org.json.JSONException;

@SimpleObject
@DesignerComponent(category = ComponentCategory.MAPS, description = "A FeatureColletion contains one or more map features as a group. Any events fired on a feature in the collection will also trigger the corresponding event on the collection object. FeatureCollections can be loaded from external resources as a means of populating a Map with content.", version = 2)
public class FeatureCollection extends MapFeatureContainerBase implements MapFeatureCollection {
    private Map map;
    private String source = "";

    public FeatureCollection(MapFeatureContainer container) {
        super(container);
        this.map = container.getMap();
    }

    @DesignerProperty
    @SimpleProperty(description = "Loads a collection of features from the given string. If the string is not valid GeoJSON, the ErrorLoadingFeatureCollection error will be run with url = <string>.")
    public void FeaturesFromGeoJSON(String geojson) {
        try {
            processGeoJSON("<string>", geojson);
        } catch (JSONException e) {
            $form().dispatchErrorOccurredEvent(this, "FeaturesFromGeoJSON", ErrorMessages.ERROR_INVALID_GEOJSON, e.getMessage());
        }
    }

    @SimpleEvent(description = "A GeoJSON document was successfully read from url. The features specified in the document are provided as a list in features.")
    public void GotFeatures(String url, YailList features) {
        this.source = url;
        super.GotFeatures(url, features);
    }

    @DesignerProperty(editorType = "geojson_type")
    public void Source(String source) {
        this.source = source;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Gets or sets the source URL used to populate the feature collection. If the feature collection was not loaded from a URL, this will be the empty string.")
    public String Source() {
        return this.source;
    }

    public View getView() {
        return null;
    }

    public Map getMap() {
        return this.map;
    }
}

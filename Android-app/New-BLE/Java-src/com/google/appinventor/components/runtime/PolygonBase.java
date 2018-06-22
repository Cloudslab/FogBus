package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureContainer;
import com.google.appinventor.components.runtime.util.MapFactory.MapFeatureVisitor;

@SimpleObject
public abstract class PolygonBase extends MapFeatureBaseWithFill {
    public PolygonBase(MapFeatureContainer container, MapFeatureVisitor<Double> distanceComputation) {
        super(container, distanceComputation);
    }
}

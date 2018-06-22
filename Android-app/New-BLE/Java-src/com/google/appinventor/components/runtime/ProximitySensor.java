package com.google.appinventor.components.runtime;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SENSORS, description = "<p>Non-visible component that can measures the proximity of an object in cm relative to the view screen of a device. This sensor is typically used to determine whether a handset is being held up to a persons ear; i.e. lets you determine how far away an object is from a device. Many devices return the absolute distance, in cm, but some return only near and far values. In this case, the sensor usually reports its maximum range value in the far state and a lesser value in the near state.</p>", iconName = "images/proximitysensor.png", nonVisible = true, version = 1)
public class ProximitySensor extends AndroidNonvisibleComponent implements OnStopListener, OnResumeListener, SensorComponent, OnPauseListener, SensorEventListener, Deleteable {
    private float distance = 0.0f;
    private boolean enabled;
    private boolean keepRunningWhenOnPause;
    private Sensor proximitySensor;
    private final SensorManager sensorManager;

    public ProximitySensor(ComponentContainer container) {
        super(container.$form());
        this.form.registerForOnResume(this);
        this.form.registerForOnStop(this);
        this.form.registerForOnPause(this);
        this.enabled = true;
        this.sensorManager = (SensorManager) container.$context().getSystemService("sensor");
        this.proximitySensor = this.sensorManager.getDefaultSensor(8);
        startListening();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Reports whether or not the device has a proximity sensor")
    public boolean Available() {
        return this.sensorManager.getSensorList(8).size() > 0;
    }

    public void onResume() {
        if (this.enabled) {
            startListening();
        }
    }

    public void onStop() {
        if (this.enabled) {
            stopListening();
        }
    }

    public void onDelete() {
        if (this.enabled) {
            stopListening();
        }
    }

    public void onPause() {
        if (this.enabled && !this.keepRunningWhenOnPause) {
            stopListening();
        }
    }

    private void startListening() {
        this.sensorManager.registerListener(this, this.proximitySensor, 3);
    }

    private void stopListening() {
        this.sensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled) {
            this.distance = ((float[]) sensorEvent.values.clone())[0];
            ProximityChanged(this.distance);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Reports the Maximum Range of the device's ProximitySensor")
    public float MaximumRange() {
        return this.proximitySensor.getMaximumRange();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.enabled;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(description = "If enabled, then device will listen for changes in proximity")
    public void Enabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            if (enabled) {
                startListening();
            } else {
                stopListening();
            }
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean KeepRunningWhenOnPause() {
        return this.keepRunningWhenOnPause;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(description = "If set to true, it will keep sensing for proximity changes even when the app is not visible")
    public void KeepRunningWhenOnPause(boolean enabled) {
        this.keepRunningWhenOnPause = enabled;
    }

    @SimpleEvent(description = "Triggered when distance (in cm) of the object to the device changes. ")
    public void ProximityChanged(float distance) {
        this.distance = distance;
        EventDispatcher.dispatchEvent(this, "ProximityChanged", Float.valueOf(this.distance));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the distance from the object to the device")
    public float Distance() {
        return this.distance;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}

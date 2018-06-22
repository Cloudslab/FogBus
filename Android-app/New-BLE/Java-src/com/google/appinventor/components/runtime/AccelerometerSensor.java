package com.google.appinventor.components.runtime;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import java.util.LinkedList;
import java.util.Queue;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SENSORS, description = "Non-visible component that can detect shaking and measure acceleration approximately in three dimensions using SI units (m/s<sup>2</sup>).  The components are: <ul>\n<li> <strong>xAccel</strong>: 0 when the phone is at rest on a flat      surface, positive when the phone is tilted to the right (i.e.,      its left side is raised), and negative when the phone is tilted      to the left (i.e., its right size is raised).</li>\n <li> <strong>yAccel</strong>: 0 when the phone is at rest on a flat      surface, positive when its bottom is raised, and negative when      its top is raised. </li>\n <li> <strong>zAccel</strong>: Equal to -9.8 (earth's gravity in meters per      second per second when the device is at rest parallel to the ground      with the display facing up,      0 when perpendicular to the ground, and +9.8 when facing down.       The value can also be affected by accelerating it with or against      gravity. </li></ul>", iconName = "images/accelerometersensor.png", nonVisible = true, version = 4)
public class AccelerometerSensor extends AndroidNonvisibleComponent implements OnStopListener, OnResumeListener, SensorComponent, SensorEventListener, Deleteable {
    private static final boolean DEBUG = true;
    private static final String LOG_TAG = "AccelerometerSensor";
    private static final int SENSOR_CACHE_SIZE = 10;
    private static final double moderateShakeThreshold = 13.0d;
    private static final double strongShakeThreshold = 20.0d;
    private static final double weakShakeThreshold = 5.0d;
    private final Queue<Float> X_CACHE = new LinkedList();
    private final Queue<Float> Y_CACHE = new LinkedList();
    private final Queue<Float> Z_CACHE = new LinkedList();
    private Sensor accelerometerSensor;
    private int accuracy;
    private final Handler androidUIHandler;
    private volatile int deviceDefaultOrientation;
    private boolean enabled;
    private boolean legacyMode = false;
    private int minimumInterval;
    private final Resources resources;
    private int sensitivity;
    private final SensorManager sensorManager;
    private long timeLastShook;
    private final WindowManager windowManager;
    private float xAccel;
    private float yAccel;
    private float zAccel;

    class C01511 implements Runnable {
        C01511() {
        }

        public void run() {
            AccelerometerSensor.this.deviceDefaultOrientation = AccelerometerSensor.this.getDeviceDefaultOrientation();
            Log.d(AccelerometerSensor.LOG_TAG, "deviceDefaultOrientation = " + AccelerometerSensor.this.deviceDefaultOrientation);
            Log.d(AccelerometerSensor.LOG_TAG, "Configuration.ORIENTATION_LANDSCAPE = 2");
            Log.d(AccelerometerSensor.LOG_TAG, "Configuration.ORIENTATION_PORTRAIT = 1");
        }
    }

    public AccelerometerSensor(ComponentContainer container) {
        super(container.$form());
        this.form.registerForOnResume(this);
        this.form.registerForOnStop(this);
        this.enabled = true;
        this.resources = container.$context().getResources();
        this.windowManager = (WindowManager) container.$context().getSystemService("window");
        this.sensorManager = (SensorManager) container.$context().getSystemService("sensor");
        this.accelerometerSensor = this.sensorManager.getDefaultSensor(1);
        this.androidUIHandler = new Handler();
        startListening();
        MinimumInterval(400);
        Sensitivity(2);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The minimum interval, in milliseconds, between phone shakes")
    public int MinimumInterval() {
        return this.minimumInterval;
    }

    @DesignerProperty(defaultValue = "400", editorType = "non_negative_integer")
    @SimpleProperty
    public void MinimumInterval(int interval) {
        this.minimumInterval = interval;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how sensitive the accelerometer is. The choices are: 1 = weak, 2 = moderate,  3 = strong.")
    public int Sensitivity() {
        return this.sensitivity;
    }

    @DesignerProperty(defaultValue = "2", editorType = "accelerometer_sensitivity")
    @SimpleProperty
    public void Sensitivity(int sensitivity) {
        if (sensitivity == 1 || sensitivity == 2 || sensitivity == 3) {
            this.sensitivity = sensitivity;
            return;
        }
        this.form.dispatchErrorOccurredEvent(this, "Sensitivity", ErrorMessages.ERROR_BAD_VALUE_FOR_ACCELEROMETER_SENSITIVITY, Integer.valueOf(sensitivity));
    }

    @SimpleEvent
    public void AccelerationChanged(float xAccel, float yAccel, float zAccel) {
        this.xAccel = xAccel;
        this.yAccel = yAccel;
        this.zAccel = zAccel;
        addToSensorCache(this.X_CACHE, xAccel);
        addToSensorCache(this.Y_CACHE, yAccel);
        addToSensorCache(this.Z_CACHE, zAccel);
        long currentTime = System.currentTimeMillis();
        if ((isShaking(this.X_CACHE, xAccel) || isShaking(this.Y_CACHE, yAccel) || isShaking(this.Z_CACHE, zAccel)) && (this.timeLastShook == 0 || currentTime >= this.timeLastShook + ((long) this.minimumInterval))) {
            this.timeLastShook = currentTime;
            Shaking();
        }
        EventDispatcher.dispatchEvent(this, "AccelerationChanged", Float.valueOf(xAccel), Float.valueOf(yAccel), Float.valueOf(zAccel));
    }

    public int getDeviceDefaultOrientation() {
        Configuration config = this.resources.getConfiguration();
        int rotation = this.windowManager.getDefaultDisplay().getRotation();
        Log.d(LOG_TAG, "rotation = " + rotation);
        Log.d(LOG_TAG, "config.orientation = " + config.orientation);
        if ((rotation == 0 || rotation == 2) && config.orientation == 2) {
            return 2;
        }
        if ((rotation == 1 || rotation == 3) && config.orientation == 1) {
            return 2;
        }
        return 1;
    }

    @SimpleEvent
    public void Shaking() {
        EventDispatcher.dispatchEvent(this, "Shaking", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Available() {
        if (this.sensorManager.getSensorList(1).size() > 0) {
            return true;
        }
        return false;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean Enabled() {
        return this.enabled;
    }

    private void startListening() {
        this.androidUIHandler.postDelayed(new C01511(), 32);
        this.sensorManager.registerListener(this, this.accelerometerSensor, 1);
    }

    private void stopListening() {
        this.sensorManager.unregisterListener(this);
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
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
    public float XAccel() {
        return this.xAccel;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float YAccel() {
        return this.yAccel;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public float ZAccel() {
        return this.zAccel;
    }

    private void addToSensorCache(Queue<Float> cache, float value) {
        if (cache.size() >= 10) {
            cache.remove();
        }
        cache.add(Float.valueOf(value));
    }

    private boolean isShaking(Queue<Float> cache, float currentValue) {
        float average = 0.0f;
        for (Float floatValue : cache) {
            average += floatValue.floatValue();
        }
        average /= (float) cache.size();
        if (Sensitivity() == 1) {
            return ((double) Math.abs(average - currentValue)) > strongShakeThreshold;
        } else if (Sensitivity() == 2) {
            if (((double) Math.abs(average - currentValue)) <= moderateShakeThreshold || ((double) Math.abs(average - currentValue)) >= strongShakeThreshold) {
                return false;
            }
            return true;
        } else if (((double) Math.abs(average - currentValue)) <= weakShakeThreshold || ((double) Math.abs(average - currentValue)) >= moderateShakeThreshold) {
            return false;
        } else {
            return true;
        }
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty(description = "Prior to the release that added this property the AccelerometerSensor component passed through sensor values directly as received from the Android system. However these values do not compensate for tablets that default to Landscape mode, requiring the MIT App Inventor programmer to compensate. However compensating would result in incorrect results in Portrait mode devices such as phones. We now detect Landscape mode tablets and perform the compensation. However if your project is already compensating for the change, you will now get incorrect results. Although our preferred solution is for you to update your project, you can also just set this property to “true” and our compensation code will be deactivated. Note: We recommend that you update your project as we may remove this property in a future release.", userVisible = false)
    public void LegacyMode(boolean legacyMode) {
        this.legacyMode = legacyMode;
    }

    public boolean LegacyMode() {
        return this.legacyMode;
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled) {
            float[] values = sensorEvent.values;
            if (this.deviceDefaultOrientation != 2 || this.legacyMode) {
                this.xAccel = values[0];
                this.yAccel = values[1];
            } else {
                this.xAccel = values[1];
                this.yAccel = -values[0];
            }
            this.zAccel = values[2];
            this.accuracy = sensorEvent.accuracy;
            AccelerationChanged(this.xAccel, this.yAccel, this.zAccel);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
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
}

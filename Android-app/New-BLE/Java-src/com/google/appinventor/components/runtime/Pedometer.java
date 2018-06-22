package com.google.appinventor.components.runtime;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SENSORS, description = "A Component that acts like a Pedometer. It senses motion via the Accerleromter and attempts to determine if a step has been taken. Using a configurable stride length, it can estimate the distance traveled as well. ", iconName = "images/pedometer.png", nonVisible = true, version = 2)
public class Pedometer extends AndroidNonvisibleComponent implements Component, SensorEventListener, Deleteable {
    private static final int INTERVAL_VARIATION = 250;
    private static final int NUM_INTERVALS = 2;
    private static final float PEAK_VALLEY_RANGE = 40.0f;
    private static final String PREFS_NAME = "PedometerPrefs";
    private static final float STRIDE_LENGTH = 0.73f;
    private static final String TAG = "Pedometer";
    private static final int WIN_SIZE = 100;
    private int avgPos = 0;
    private float[] avgWindow = new float[10];
    private final Context context;
    private boolean foundNonStep = true;
    private boolean foundValley = false;
    private int intervalPos = 0;
    private float lastValley = 0.0f;
    private float[] lastValues = new float[100];
    private int numStepsRaw = 0;
    private int numStepsWithFilter = 0;
    private boolean pedometerPaused = true;
    private long prevStopClockTime = 0;
    private final SensorManager sensorManager;
    private boolean startPeaking = false;
    private long startTime = 0;
    private long[] stepInterval = new long[2];
    private long stepTimestamp = 0;
    private int stopDetectionTimeout = 2000;
    private float strideLength = STRIDE_LENGTH;
    private float totalDistance = 0.0f;
    private int winPos = 0;

    public Pedometer(ComponentContainer container) {
        super(container.$form());
        this.context = container.$context();
        this.winPos = 0;
        this.startPeaking = false;
        this.numStepsWithFilter = 0;
        this.numStepsRaw = 0;
        this.foundValley = true;
        this.lastValley = 0.0f;
        this.sensorManager = (SensorManager) this.context.getSystemService("sensor");
        SharedPreferences settings = this.context.getSharedPreferences(PREFS_NAME, 0);
        this.strideLength = settings.getFloat("Pedometer.stridelength", STRIDE_LENGTH);
        this.totalDistance = settings.getFloat("Pedometer.distance", 0.0f);
        this.numStepsRaw = settings.getInt("Pedometer.prevStepCount", 0);
        this.prevStopClockTime = settings.getLong("Pedometer.clockTime", 0);
        this.numStepsWithFilter = this.numStepsRaw;
        this.startTime = System.currentTimeMillis();
        Log.d(TAG, "Pedometer Created");
    }

    @SimpleFunction(description = "Start counting steps")
    public void Start() {
        if (this.pedometerPaused) {
            this.pedometerPaused = false;
            this.sensorManager.registerListener(this, (Sensor) this.sensorManager.getSensorList(1).get(0), 0);
            this.startTime = System.currentTimeMillis();
        }
    }

    @SimpleFunction(description = "Stop counting steps")
    public void Stop() {
        Pause();
    }

    @SimpleFunction(description = "Resets the step counter, distance measure and time running.")
    public void Reset() {
        this.numStepsWithFilter = 0;
        this.numStepsRaw = 0;
        this.totalDistance = 0.0f;
        this.prevStopClockTime = 0;
        this.startTime = System.currentTimeMillis();
    }

    @SimpleFunction(description = "Resumes counting, synonym of Start.")
    public void Resume() {
        Start();
    }

    @SimpleFunction(description = "Pause counting of steps and distance.")
    public void Pause() {
        if (!this.pedometerPaused) {
            this.pedometerPaused = true;
            this.sensorManager.unregisterListener(this);
            Log.d(TAG, "Unregistered listener on pause");
            this.prevStopClockTime += System.currentTimeMillis() - this.startTime;
        }
    }

    @SimpleFunction(description = "Saves the pedometer state to the phone. Permits permits accumulation of steps and distance between invocations of an App that uses the pedometer. Different Apps will have their own saved state.")
    public void Save() {
        Editor editor = this.context.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putFloat("Pedometer.stridelength", this.strideLength);
        editor.putFloat("Pedometer.distance", this.totalDistance);
        editor.putInt("Pedometer.prevStepCount", this.numStepsRaw);
        if (this.pedometerPaused) {
            editor.putLong("Pedometer.clockTime", this.prevStopClockTime);
        } else {
            editor.putLong("Pedometer.clockTime", this.prevStopClockTime + (System.currentTimeMillis() - this.startTime));
        }
        editor.putLong("Pedometer.closeTime", System.currentTimeMillis());
        editor.commit();
        Log.d(TAG, "Pedometer state saved.");
    }

    @SimpleEvent(description = "This event is run when a raw step is detected")
    public void SimpleStep(int simpleSteps, float distance) {
        EventDispatcher.dispatchEvent(this, "SimpleStep", Integer.valueOf(simpleSteps), Float.valueOf(distance));
    }

    @SimpleEvent(description = "This event is run when a walking step is detected. A walking step is a step that appears to be involved in forward motion.")
    public void WalkStep(int walkSteps, float distance) {
        EventDispatcher.dispatchEvent(this, "WalkStep", Integer.valueOf(walkSteps), Float.valueOf(distance));
    }

    @DesignerProperty(defaultValue = "0.73", editorType = "non_negative_float")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Set the average stride length in meters.")
    public void StrideLength(float length) {
        this.strideLength = length;
    }

    @SimpleProperty
    public float StrideLength() {
        return this.strideLength;
    }

    @DesignerProperty(defaultValue = "2000", editorType = "non_negative_integer")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The duration in milliseconds of idleness (no steps detected) after which to go into a \"stopped\" state")
    public void StopDetectionTimeout(int timeout) {
        this.stopDetectionTimeout = timeout;
    }

    @SimpleProperty
    public int StopDetectionTimeout() {
        return this.stopDetectionTimeout;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The approximate distance traveled in meters.")
    public float Distance() {
        return this.totalDistance;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Time elapsed in milliseconds since the pedometer was started.")
    public long ElapsedTime() {
        if (this.pedometerPaused) {
            return this.prevStopClockTime;
        }
        return this.prevStopClockTime + (System.currentTimeMillis() - this.startTime);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The number of simple steps taken since the pedometer has started.")
    public int SimpleSteps() {
        return this.numStepsRaw;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "the number of walk steps taken since the pedometer has started.")
    public int WalkSteps() {
        return this.numStepsWithFilter;
    }

    private boolean areStepsEquallySpaced() {
        float avg = 0.0f;
        int num = 0;
        for (long interval : this.stepInterval) {
            if (interval > 0) {
                num++;
                avg += (float) interval;
            }
        }
        avg /= (float) num;
        for (long interval2 : this.stepInterval) {
            if (Math.abs(((float) interval2) - avg) > 250.0f) {
                return false;
            }
        }
        return true;
    }

    private boolean isPeak() {
        int mid = (this.winPos + 50) % 100;
        int i = 0;
        while (i < 100) {
            if (i != mid && this.lastValues[i] > this.lastValues[mid]) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean isValley() {
        int mid = (this.winPos + 50) % 100;
        int i = 0;
        while (i < 100) {
            if (i != mid && this.lastValues[i] < this.lastValues[mid]) {
                return false;
            }
            i++;
        }
        return true;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "Accelerometer accuracy changed.");
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 1) {
            float[] fArr;
            int i;
            float magnitude = 0.0f;
            for (float v : event.values) {
                magnitude += v * v;
            }
            int mid = (this.winPos + 50) % 100;
            if (this.startPeaking && isPeak() && this.foundValley && this.lastValues[mid] - this.lastValley > PEAK_VALLEY_RANGE) {
                long timestamp = System.currentTimeMillis();
                this.stepInterval[this.intervalPos] = timestamp - this.stepTimestamp;
                this.intervalPos = (this.intervalPos + 1) % 2;
                this.stepTimestamp = timestamp;
                if (areStepsEquallySpaced()) {
                    if (this.foundNonStep) {
                        this.numStepsWithFilter += 2;
                        this.totalDistance += this.strideLength * 2.0f;
                        this.foundNonStep = false;
                    }
                    this.numStepsWithFilter++;
                    WalkStep(this.numStepsWithFilter, this.totalDistance);
                    this.totalDistance += this.strideLength;
                } else {
                    this.foundNonStep = true;
                }
                this.numStepsRaw++;
                SimpleStep(this.numStepsRaw, this.totalDistance);
                this.foundValley = false;
            }
            if (this.startPeaking && isValley()) {
                this.foundValley = true;
                this.lastValley = this.lastValues[mid];
            }
            this.avgWindow[this.avgPos] = magnitude;
            this.avgPos = (this.avgPos + 1) % this.avgWindow.length;
            this.lastValues[this.winPos] = 0.0f;
            for (float m : this.avgWindow) {
                fArr = this.lastValues;
                i = this.winPos;
                fArr[i] = fArr[i] + m;
            }
            fArr = this.lastValues;
            i = this.winPos;
            fArr[i] = fArr[i] / ((float) this.avgWindow.length);
            if (this.startPeaking || this.winPos > 1) {
                int i2 = this.winPos - 1;
                if (i2 < 0) {
                    i2 += 100;
                }
                fArr = this.lastValues;
                i = this.winPos;
                fArr[i] = fArr[i] + (2.0f * this.lastValues[i2]);
                i2--;
                if (i2 < 0) {
                    i2 += 100;
                }
                fArr = this.lastValues;
                i = this.winPos;
                fArr[i] = fArr[i] + this.lastValues[i2];
                fArr = this.lastValues;
                i = this.winPos;
                fArr[i] = fArr[i] / 4.0f;
            } else if (!this.startPeaking && this.winPos == 1) {
                this.lastValues[1] = (this.lastValues[1] + this.lastValues[0]) / 2.0f;
            }
            long elapsedTimestamp = System.currentTimeMillis();
            if (elapsedTimestamp - this.stepTimestamp > ((long) this.stopDetectionTimeout)) {
                this.stepTimestamp = elapsedTimestamp;
            }
            if (this.winPos == 99 && !this.startPeaking) {
                this.startPeaking = true;
            }
            this.winPos = (this.winPos + 1) % 100;
        }
    }

    public void onDelete() {
        this.sensorManager.unregisterListener(this);
    }

    @SimpleEvent
    @Deprecated
    public void StartedMoving() {
    }

    @SimpleEvent
    @Deprecated
    public void StoppedMoving() {
    }

    @Deprecated
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void UseGPS(boolean gps) {
    }

    @SimpleEvent
    @Deprecated
    public void CalibrationFailed() {
    }

    @SimpleEvent
    @Deprecated
    public void GPSAvailable() {
    }

    @SimpleEvent
    @Deprecated
    public void GPSLost() {
    }

    @Deprecated
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void CalibrateStrideLength(boolean cal) {
    }

    @Deprecated
    @SimpleProperty
    public boolean CalibrateStrideLength() {
        return false;
    }

    @Deprecated
    @SimpleProperty
    public boolean Moving() {
        return false;
    }
}

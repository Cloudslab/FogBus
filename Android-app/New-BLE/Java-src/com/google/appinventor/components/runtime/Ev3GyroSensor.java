package com.google.appinventor.components.runtime;

import android.os.Handler;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a gyro sensor on a LEGO MINDSTORMS EV3 robot.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
public class Ev3GyroSensor extends LegoMindstormsEv3Sensor implements Deleteable {
    private static final String DEFAULT_SENSOR_MODE_STRING = "angle";
    private static final int DELAY_MILLISECONDS = 50;
    private static final int SENSOR_MODE_ANGLE = 0;
    private static final String SENSOR_MODE_ANGLE_STRING = "angle";
    private static final int SENSOR_MODE_RATE = 1;
    private static final String SENSOR_MODE_RATE_STRING = "rate";
    private static final int SENSOR_TYPE = 32;
    private Handler eventHandler = new Handler();
    private int mode = 0;
    private String modeString = "angle";
    private double previousValue = -1.0d;
    private boolean sensorValueChangedEventEnabled = false;
    private final Runnable sensorValueChecker = new C01701();

    class C01701 implements Runnable {
        C01701() {
        }

        public void run() {
            String functionName = "";
            if (Ev3GyroSensor.this.bluetooth != null && Ev3GyroSensor.this.bluetooth.IsConnected()) {
                double currentValue = Ev3GyroSensor.this.getSensorValue(functionName);
                if (Ev3GyroSensor.this.previousValue < 0.0d) {
                    Ev3GyroSensor.this.previousValue = currentValue;
                    Ev3GyroSensor.this.eventHandler.postDelayed(this, 50);
                    return;
                }
                if (Ev3GyroSensor.this.mode == 1 && Math.abs(currentValue) >= 1.0d) {
                    Ev3GyroSensor.this.SensorValueChanged(currentValue);
                } else if (Ev3GyroSensor.this.mode == 0 && Math.abs(currentValue - Ev3GyroSensor.this.previousValue) >= 1.0d) {
                    Ev3GyroSensor.this.SensorValueChanged(currentValue);
                }
                Ev3GyroSensor.this.previousValue = currentValue;
            }
            Ev3GyroSensor.this.eventHandler.postDelayed(this, 50);
        }
    }

    public Ev3GyroSensor(ComponentContainer container) {
        super(container, "Ev3GyroSensor");
        this.eventHandler.post(this.sensorValueChecker);
        Mode("angle");
        SensorValueChangedEventEnabled(false);
    }

    @SimpleFunction(description = "Returns the current angle or rotation speed based on current mode, or -1 if the value cannot be read from sensor.")
    public double GetSensorValue() {
        return getSensorValue("");
    }

    @DesignerProperty(defaultValue = "angle", editorType = "lego_ev3_gyro_sensor_mode")
    @SimpleProperty
    public void Mode(String modeName) {
        String functionName = "Mode";
        try {
            setMode(modeName);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The sensor mode can be a text constant of either \"rate\" or \"angle\", which correspond to SetAngleMode or SetRateMode respectively.")
    public String Mode() {
        return this.modeString;
    }

    @SimpleFunction(description = "Measures the orientation of the sensor.")
    public void SetAngleMode() {
        setMode("angle");
    }

    @SimpleFunction(description = "Measures the angular velocity of the sensor.")
    public void SetRateMode() {
        setMode(SENSOR_MODE_RATE_STRING);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the SensorValueChanged event should fire when the sensor value changed.")
    public boolean SensorValueChangedEventEnabled() {
        return this.sensorValueChangedEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void SensorValueChangedEventEnabled(boolean enabled) {
        this.sensorValueChangedEventEnabled = enabled;
    }

    @SimpleEvent(description = "Called then the sensor value changed.")
    public void SensorValueChanged(double sensorValue) {
        EventDispatcher.dispatchEvent(this, "SensorValueChanged", Double.valueOf(sensorValue));
    }

    private double getSensorValue(String functionName) {
        return readInputSI(functionName, 0, this.sensorPortNumber, 32, this.mode);
    }

    private void setMode(String newModeString) {
        if ("angle".equals(newModeString)) {
            this.mode = 0;
        } else if (SENSOR_MODE_RATE_STRING.equals(newModeString)) {
            this.mode = 1;
        } else {
            throw new IllegalArgumentException();
        }
        this.modeString = newModeString;
    }

    public void onDelete() {
        super.onDelete();
    }
}

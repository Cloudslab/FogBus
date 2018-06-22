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

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a touch sensor on a LEGO MINDSTORMS NXT robot.", iconName = "images/legoMindstormsNxt.png", nonVisible = true, version = 1)
public class NxtTouchSensor extends LegoMindstormsNxtSensor implements Deleteable {
    private static final String DEFAULT_SENSOR_PORT = "1";
    private Handler handler = new Handler();
    private boolean pressedEventEnabled;
    private State previousState = State.UNKNOWN;
    private boolean releasedEventEnabled;
    private final Runnable sensorReader = new C02531();

    class C02531 implements Runnable {
        C02531() {
        }

        public void run() {
            if (NxtTouchSensor.this.bluetooth != null && NxtTouchSensor.this.bluetooth.IsConnected()) {
                SensorValue<Boolean> sensorValue = NxtTouchSensor.this.getPressedValue("");
                if (sensorValue.valid) {
                    State currentState = ((Boolean) sensorValue.value).booleanValue() ? State.PRESSED : State.RELEASED;
                    if (currentState != NxtTouchSensor.this.previousState) {
                        if (currentState == State.PRESSED && NxtTouchSensor.this.pressedEventEnabled) {
                            NxtTouchSensor.this.Pressed();
                        }
                        if (currentState == State.RELEASED && NxtTouchSensor.this.releasedEventEnabled) {
                            NxtTouchSensor.this.Released();
                        }
                    }
                    NxtTouchSensor.this.previousState = currentState;
                }
            }
            if (NxtTouchSensor.this.isHandlerNeeded()) {
                NxtTouchSensor.this.handler.post(NxtTouchSensor.this.sensorReader);
            }
        }
    }

    private enum State {
        UNKNOWN,
        PRESSED,
        RELEASED
    }

    public NxtTouchSensor(ComponentContainer container) {
        super(container, "NxtTouchSensor");
        SensorPort(DEFAULT_SENSOR_PORT);
        PressedEventEnabled(false);
        ReleasedEventEnabled(false);
    }

    protected void initializeSensor(String functionName) {
        setInputMode(functionName, this.port, 1, 32);
    }

    @DesignerProperty(defaultValue = "1", editorType = "lego_nxt_sensor_port")
    @SimpleProperty(userVisible = false)
    public void SensorPort(String sensorPortLetter) {
        setSensorPort(sensorPortLetter);
    }

    @SimpleFunction(description = "Returns true if the touch sensor is pressed.")
    public boolean IsPressed() {
        String functionName = "IsPressed";
        if (!checkBluetooth(functionName)) {
            return false;
        }
        SensorValue<Boolean> sensorValue = getPressedValue(functionName);
        if (sensorValue.valid) {
            return ((Boolean) sensorValue.value).booleanValue();
        }
        return false;
    }

    private SensorValue<Boolean> getPressedValue(String functionName) {
        boolean z = false;
        byte[] returnPackage = getInputValues(functionName, this.port);
        if (returnPackage == null || !getBooleanValueFromBytes(returnPackage, 4)) {
            return new SensorValue(false, null);
        }
        if (getSWORDValueFromBytes(returnPackage, 12) != 0) {
            z = true;
        }
        return new SensorValue(true, Boolean.valueOf(z));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the Pressed event should fire when the touch sensor is pressed.")
    public boolean PressedEventEnabled() {
        return this.pressedEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void PressedEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.pressedEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Touch sensor has been pressed.")
    public void Pressed() {
        EventDispatcher.dispatchEvent(this, "Pressed", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the Released event should fire when the touch sensor is released.")
    public boolean ReleasedEventEnabled() {
        return this.releasedEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void ReleasedEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.releasedEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Touch sensor has been released.")
    public void Released() {
        EventDispatcher.dispatchEvent(this, "Released", new Object[0]);
    }

    private boolean isHandlerNeeded() {
        return this.pressedEventEnabled || this.releasedEventEnabled;
    }

    public void onDelete() {
        this.handler.removeCallbacks(this.sensorReader);
        super.onDelete();
    }
}

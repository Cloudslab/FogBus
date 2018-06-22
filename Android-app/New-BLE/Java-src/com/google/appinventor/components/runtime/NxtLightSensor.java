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
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a light sensor on a LEGO MINDSTORMS NXT robot.", iconName = "images/legoMindstormsNxt.png", nonVisible = true, version = 1)
public class NxtLightSensor extends LegoMindstormsNxtSensor implements Deleteable {
    private static final int DEFAULT_BOTTOM_OF_RANGE = 256;
    private static final String DEFAULT_SENSOR_PORT = "3";
    private static final int DEFAULT_TOP_OF_RANGE = 767;
    private boolean aboveRangeEventEnabled;
    private boolean belowRangeEventEnabled;
    private int bottomOfRange;
    private boolean generateLight;
    private Handler handler = new Handler();
    private State previousState = State.UNKNOWN;
    private final Runnable sensorReader = new C02511();
    private int topOfRange;
    private boolean withinRangeEventEnabled;

    class C02511 implements Runnable {
        C02511() {
        }

        public void run() {
            if (NxtLightSensor.this.bluetooth != null && NxtLightSensor.this.bluetooth.IsConnected()) {
                SensorValue<Integer> sensorValue = NxtLightSensor.this.getLightValue("");
                if (sensorValue.valid) {
                    State currentState;
                    if (((Integer) sensorValue.value).intValue() < NxtLightSensor.this.bottomOfRange) {
                        currentState = State.BELOW_RANGE;
                    } else if (((Integer) sensorValue.value).intValue() > NxtLightSensor.this.topOfRange) {
                        currentState = State.ABOVE_RANGE;
                    } else {
                        currentState = State.WITHIN_RANGE;
                    }
                    if (currentState != NxtLightSensor.this.previousState) {
                        if (currentState == State.BELOW_RANGE && NxtLightSensor.this.belowRangeEventEnabled) {
                            NxtLightSensor.this.BelowRange();
                        }
                        if (currentState == State.WITHIN_RANGE && NxtLightSensor.this.withinRangeEventEnabled) {
                            NxtLightSensor.this.WithinRange();
                        }
                        if (currentState == State.ABOVE_RANGE && NxtLightSensor.this.aboveRangeEventEnabled) {
                            NxtLightSensor.this.AboveRange();
                        }
                    }
                    NxtLightSensor.this.previousState = currentState;
                }
            }
            if (NxtLightSensor.this.isHandlerNeeded()) {
                NxtLightSensor.this.handler.post(NxtLightSensor.this.sensorReader);
            }
        }
    }

    private enum State {
        UNKNOWN,
        BELOW_RANGE,
        WITHIN_RANGE,
        ABOVE_RANGE
    }

    public NxtLightSensor(ComponentContainer container) {
        super(container, "NxtLightSensor");
        SensorPort(DEFAULT_SENSOR_PORT);
        BottomOfRange(256);
        TopOfRange(DEFAULT_TOP_OF_RANGE);
        BelowRangeEventEnabled(false);
        WithinRangeEventEnabled(false);
        AboveRangeEventEnabled(false);
        GenerateLight(false);
    }

    protected void initializeSensor(String functionName) {
        setInputMode(functionName, this.port, this.generateLight ? 5 : 6, 128);
    }

    @DesignerProperty(defaultValue = "3", editorType = "lego_nxt_sensor_port")
    @SimpleProperty(userVisible = false)
    public void SensorPort(String sensorPortLetter) {
        setSensorPort(sensorPortLetter);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the light sensor should generate light.")
    public boolean GenerateLight() {
        return this.generateLight;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void GenerateLight(boolean generateLight) {
        this.generateLight = generateLight;
        if (this.bluetooth != null && this.bluetooth.IsConnected()) {
            initializeSensor("GenerateLight");
        }
    }

    @SimpleFunction(description = "Returns the current light level as a value between 0 and 1023, or -1 if the light level can not be read.")
    public int GetLightLevel() {
        String functionName = "GetLightLevel";
        if (!checkBluetooth(functionName)) {
            return -1;
        }
        SensorValue<Integer> sensorValue = getLightValue(functionName);
        if (sensorValue.valid) {
            return ((Integer) sensorValue.value).intValue();
        }
        return -1;
    }

    private SensorValue<Integer> getLightValue(String functionName) {
        byte[] returnPackage = getInputValues(functionName, this.port);
        if (returnPackage == null || !getBooleanValueFromBytes(returnPackage, 4)) {
            return new SensorValue(false, null);
        }
        return new SensorValue(true, Integer.valueOf(getUWORDValueFromBytes(returnPackage, 10)));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The bottom of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int BottomOfRange() {
        return this.bottomOfRange;
    }

    @DesignerProperty(defaultValue = "256", editorType = "non_negative_integer")
    @SimpleProperty
    public void BottomOfRange(int bottomOfRange) {
        this.bottomOfRange = bottomOfRange;
        this.previousState = State.UNKNOWN;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The top of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int TopOfRange() {
        return this.topOfRange;
    }

    @DesignerProperty(defaultValue = "767", editorType = "non_negative_integer")
    @SimpleProperty
    public void TopOfRange(int topOfRange) {
        this.topOfRange = topOfRange;
        this.previousState = State.UNKNOWN;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the BelowRange event should fire when the light level goes below the BottomOfRange.")
    public boolean BelowRangeEventEnabled() {
        return this.belowRangeEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void BelowRangeEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.belowRangeEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Light level has gone below the range.")
    public void BelowRange() {
        EventDispatcher.dispatchEvent(this, "BelowRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the WithinRange event should fire when the light level goes between the BottomOfRange and the TopOfRange.")
    public boolean WithinRangeEventEnabled() {
        return this.withinRangeEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void WithinRangeEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.withinRangeEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Light level has gone within the range.")
    public void WithinRange() {
        EventDispatcher.dispatchEvent(this, "WithinRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the AboveRange event should fire when the light level goes above the TopOfRange.")
    public boolean AboveRangeEventEnabled() {
        return this.aboveRangeEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void AboveRangeEventEnabled(boolean enabled) {
        boolean handlerWasNeeded = isHandlerNeeded();
        this.aboveRangeEventEnabled = enabled;
        boolean handlerIsNeeded = isHandlerNeeded();
        if (handlerWasNeeded && !handlerIsNeeded) {
            this.handler.removeCallbacks(this.sensorReader);
        }
        if (!handlerWasNeeded && handlerIsNeeded) {
            this.previousState = State.UNKNOWN;
            this.handler.post(this.sensorReader);
        }
    }

    @SimpleEvent(description = "Light level has gone above the range.")
    public void AboveRange() {
        EventDispatcher.dispatchEvent(this, "AboveRange", new Object[0]);
    }

    private boolean isHandlerNeeded() {
        return this.belowRangeEventEnabled || this.withinRangeEventEnabled || this.aboveRangeEventEnabled;
    }

    public void onDelete() {
        this.handler.removeCallbacks(this.sensorReader);
        super.onDelete();
    }
}

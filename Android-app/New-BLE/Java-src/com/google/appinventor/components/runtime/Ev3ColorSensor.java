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
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a high-level interface to a color sensor on a LEGO MINDSTORMS EV3 robot.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
public class Ev3ColorSensor extends LegoMindstormsEv3Sensor implements Deleteable {
    private static final int DEFAULT_BOTTOM_OF_RANGE = 30;
    private static final String DEFAULT_SENSOR_MODE_STRING = "reflected";
    private static final int DEFAULT_TOP_OF_RANGE = 60;
    private static final int DELAY_MILLISECONDS = 50;
    private static final int SENSOR_MODE_AMBIENT = 1;
    private static final String SENSOR_MODE_AMBIENT_STRING = "ambient";
    private static final int SENSOR_MODE_COLOR = 2;
    private static final String SENSOR_MODE_COLOR_STRING = "color";
    private static final int SENSOR_MODE_REFLECTED = 0;
    private static final String SENSOR_MODE_REFLECTED_STRING = "reflected";
    private static final int SENSOR_TYPE = 29;
    private boolean aboveRangeEventEnabled;
    private boolean belowRangeEventEnabled;
    private int bottomOfRange;
    private boolean colorChangedEventEnabled;
    private Handler eventHandler = new Handler();
    private int mode = 0;
    private String modeString = "reflected";
    private int previousColor = -1;
    private int previousLightLevel = 0;
    private final Runnable sensorValueChecker = new C01691();
    private int topOfRange;
    private boolean withinRangeEventEnabled;

    class C01691 implements Runnable {
        C01691() {
        }

        public void run() {
            String functionName = "";
            if (Ev3ColorSensor.this.bluetooth != null && Ev3ColorSensor.this.bluetooth.IsConnected()) {
                if (Ev3ColorSensor.this.mode == 2) {
                    int currentColor = Ev3ColorSensor.this.getSensorValue(functionName);
                    if (Ev3ColorSensor.this.previousColor < 0) {
                        Ev3ColorSensor.this.previousColor = currentColor;
                        Ev3ColorSensor.this.eventHandler.postDelayed(this, 50);
                        return;
                    }
                    if (currentColor != Ev3ColorSensor.this.previousColor && Ev3ColorSensor.this.colorChangedEventEnabled) {
                        Ev3ColorSensor.this.ColorChanged(currentColor, Ev3ColorSensor.this.toColorName(functionName, currentColor));
                    }
                    Ev3ColorSensor.this.previousColor = currentColor;
                } else {
                    int currentLightLevel = Ev3ColorSensor.this.getSensorValue(functionName);
                    if (Ev3ColorSensor.this.previousLightLevel < 0) {
                        Ev3ColorSensor.this.previousLightLevel = currentLightLevel;
                        Ev3ColorSensor.this.eventHandler.postDelayed(this, 50);
                        return;
                    }
                    if (currentLightLevel < Ev3ColorSensor.this.bottomOfRange) {
                        if (Ev3ColorSensor.this.belowRangeEventEnabled && Ev3ColorSensor.this.previousLightLevel >= Ev3ColorSensor.this.bottomOfRange) {
                            Ev3ColorSensor.this.BelowRange();
                        }
                    } else if (currentLightLevel > Ev3ColorSensor.this.topOfRange) {
                        if (Ev3ColorSensor.this.aboveRangeEventEnabled && Ev3ColorSensor.this.previousLightLevel <= Ev3ColorSensor.this.topOfRange) {
                            Ev3ColorSensor.this.AboveRange();
                        }
                    } else if (Ev3ColorSensor.this.withinRangeEventEnabled && (Ev3ColorSensor.this.previousLightLevel < Ev3ColorSensor.this.bottomOfRange || Ev3ColorSensor.this.previousLightLevel > Ev3ColorSensor.this.topOfRange)) {
                        Ev3ColorSensor.this.WithinRange();
                    }
                    Ev3ColorSensor.this.previousLightLevel = currentLightLevel;
                }
            }
            Ev3ColorSensor.this.eventHandler.postDelayed(this, 50);
        }
    }

    public Ev3ColorSensor(ComponentContainer container) {
        super(container, "Ev3ColorSensor");
        this.eventHandler.post(this.sensorValueChecker);
        TopOfRange(60);
        BottomOfRange(30);
        BelowRangeEventEnabled(false);
        AboveRangeEventEnabled(false);
        WithinRangeEventEnabled(false);
        ColorChangedEventEnabled(false);
        Mode("reflected");
    }

    @SimpleFunction(description = "It returns the light level in percentage, or -1 when the light level cannot be read.")
    public int GetLightLevel() {
        if (this.mode == 2) {
            return -1;
        }
        return getSensorValue("GetLightLevel");
    }

    @SimpleFunction(description = "It returns the color code from 0 to 7 corresponding to no color, black, blue, green, yellow, red, white and brown.")
    public int GetColorCode() {
        if (this.mode != 2) {
            return 0;
        }
        return getSensorValue("GetColorCode");
    }

    @SimpleFunction(description = "Return the color name in one of \"No Color\", \"Black\", \"Blue\", \"Green\", \"Yellow\", \"Red\", \"White\", \"Brown\".")
    public String GetColorName() {
        if (this.mode != 2) {
            return "No Color";
        }
        String functionName = "GetColorName";
        return toColorName(functionName, getSensorValue(functionName));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The bottom of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int BottomOfRange() {
        return this.bottomOfRange;
    }

    @DesignerProperty(defaultValue = "30", editorType = "non_negative_integer")
    @SimpleProperty
    public void BottomOfRange(int bottomOfRange) {
        this.bottomOfRange = bottomOfRange;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The top of the range used for the BelowRange, WithinRange, and AboveRange events.")
    public int TopOfRange() {
        return this.topOfRange;
    }

    @DesignerProperty(defaultValue = "60", editorType = "non_negative_integer")
    @SimpleProperty
    public void TopOfRange(int topOfRange) {
        this.topOfRange = topOfRange;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the BelowRange event should fire when the light level goes below the BottomOfRange.")
    public boolean BelowRangeEventEnabled() {
        return this.belowRangeEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void BelowRangeEventEnabled(boolean enabled) {
        this.belowRangeEventEnabled = enabled;
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
        this.withinRangeEventEnabled = enabled;
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
        this.aboveRangeEventEnabled = enabled;
    }

    @SimpleEvent(description = "Light level has gone above the range.")
    public void AboveRange() {
        EventDispatcher.dispatchEvent(this, "AboveRange", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the ColorChanged event should fire when the Mode property is set to \"color\" and the detected color changes.")
    public boolean ColorChangedEventEnabled() {
        return this.colorChangedEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void ColorChangedEventEnabled(boolean enabled) {
        this.colorChangedEventEnabled = enabled;
    }

    @SimpleEvent(description = "Called when the detected color has changed. The ColorChanged event will occur if the Mode property is set to \"color\" and the ColorChangedEventEnabled property is set to True.")
    public void ColorChanged(int colorCode, String colorName) {
        EventDispatcher.dispatchEvent(this, "ColorChanged", Integer.valueOf(colorCode), colorName);
    }

    private int getSensorValue(String functionName) {
        int level = readInputPercentage(functionName, 0, this.sensorPortNumber, 29, this.mode);
        if (this.mode != 2) {
            return level;
        }
        switch (level) {
            case 12:
                return 1;
            case 25:
                return 2;
            case 37:
                return 3;
            case 50:
                return 4;
            case 62:
                return 5;
            case 75:
                return 6;
            case 87:
                return 7;
            default:
                return 0;
        }
    }

    private String toColorName(String functionName, int colorCode) {
        if (this.mode != 2) {
            return "No Color";
        }
        switch (colorCode) {
            case 0:
                return "No Color";
            case 1:
                return "Black";
            case 2:
                return "Blue";
            case 3:
                return "Green";
            case 4:
                return "Yellow";
            case 5:
                return "Red";
            case 6:
                return "White";
            case 7:
                return "Brown";
            default:
                return "No Color";
        }
    }

    @DesignerProperty(defaultValue = "reflected", editorType = "lego_ev3_color_sensor_mode")
    @SimpleProperty
    public void Mode(String modeName) {
        String functionName = "Mode";
        try {
            setMode(modeName);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Get the current sensor mode.")
    public String Mode() {
        return this.modeString;
    }

    @SimpleFunction(description = "Enter the color detection mode.")
    public void SetColorMode() {
        String functionName = "SetColorMode";
        try {
            setMode("color");
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Make the sensor read the light level with reflected light.")
    public void SetReflectedMode() {
        String functionName = "SetReflectedMode";
        try {
            setMode("reflected");
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Make the sensor read the light level without reflected light.")
    public void SetAmbientMode() {
        String functionName = "SetAmbientMode";
        try {
            setMode(SENSOR_MODE_AMBIENT_STRING);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    private void setMode(String newModeString) {
        this.previousColor = -1;
        this.previousLightLevel = -1;
        if ("reflected".equals(newModeString)) {
            this.mode = 0;
        } else if (SENSOR_MODE_AMBIENT_STRING.equals(newModeString)) {
            this.mode = 1;
        } else if ("color".equals(newModeString)) {
            this.mode = 2;
        } else {
            throw new IllegalArgumentException();
        }
        this.modeString = newModeString;
    }

    public void onDelete() {
        this.eventHandler.removeCallbacks(this.sensorValueChecker);
        super.onDelete();
    }
}

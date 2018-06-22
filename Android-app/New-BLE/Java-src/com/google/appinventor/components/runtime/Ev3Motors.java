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
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;
import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;
import gnu.kawa.servlet.HttpRequestContext;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides both high- and low-level interfaces to a LEGO MINDSTORMS EV3 robot, with functions that can control the motors.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
public class Ev3Motors extends LegoMindstormsEv3Base {
    private static final String DEFAULT_MOTOR_PORTS = "ABC";
    private static final double DEFAULT_WHEEL_DIAMETER = 4.32d;
    private static final int DELAY_MILLISECONDS = 50;
    private boolean directionReversed = false;
    private Handler eventHandler = new Handler();
    private boolean ifReset = false;
    private int motorPortBitField = 1;
    private int previousValue = 0;
    private boolean regulationEnabled = true;
    private final Runnable sensorValueChecker = new C01711();
    private boolean stopBeforeDisconnect = true;
    private boolean tachoCountChangedEventEnabled = false;
    private double wheelDiameter = DEFAULT_WHEEL_DIAMETER;

    class C01711 implements Runnable {
        C01711() {
        }

        public void run() {
            String functionName = "";
            if (Ev3Motors.this.bluetooth != null && Ev3Motors.this.bluetooth.IsConnected()) {
                int sensorValue = Ev3Motors.this.getOutputCount(functionName, 0, Ev3Motors.this.motorPortBitField);
                if (Ev3Motors.this.ifReset) {
                    Ev3Motors.this.ifReset = false;
                } else if (sensorValue != Ev3Motors.this.previousValue && Ev3Motors.this.tachoCountChangedEventEnabled) {
                    Ev3Motors.this.TachoCountChanged(sensorValue);
                }
                Ev3Motors.this.previousValue = sensorValue;
            }
            Ev3Motors.this.eventHandler.postDelayed(this, 50);
        }
    }

    public Ev3Motors(ComponentContainer container) {
        super(container, "Ev3Motors");
        this.eventHandler.post(this.sensorValueChecker);
        MotorPorts(DEFAULT_MOTOR_PORTS);
        StopBeforeDisconnect(true);
        EnableSpeedRegulation(true);
        ReverseDirection(false);
        WheelDiameter(DEFAULT_WHEEL_DIAMETER);
        TachoCountChangedEventEnabled(false);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The motor ports that the motors are connected to. The ports are specified by a sequence of port letters.", userVisible = false)
    public String MotorPorts() {
        return bitFieldToMotorPortLetters(this.motorPortBitField);
    }

    @DesignerProperty(defaultValue = "ABC", editorType = "string")
    @SimpleProperty
    public void MotorPorts(String motorPortLetters) {
        String functionName = "MotorPorts";
        try {
            this.motorPortBitField = motorPortLettersToBitField(motorPortLetters);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_MOTOR_PORT, motorPortLetters);
        }
    }

    @DesignerProperty(defaultValue = "4.32", editorType = "float")
    @SimpleProperty
    public void WheelDiameter(double diameter) {
        this.wheelDiameter = diameter;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The diameter of the wheels attached on the motors in centimeters.", userVisible = false)
    public double WheelDiameter() {
        return this.wheelDiameter;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void ReverseDirection(boolean reversed) {
        String functionName = "ReverseDirection";
        try {
            setOutputDirection(functionName, 0, this.motorPortBitField, reversed ? -1 : 1);
            this.directionReversed = reversed;
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "It specifies if the direction of the motors is reversed.")
    public boolean ReverseDirection() {
        return this.directionReversed;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void EnableSpeedRegulation(boolean enabled) {
        this.regulationEnabled = enabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The robot adjusts the power to maintain the speed if speed regulation is enabled.")
    public boolean EnableSpeedRegulation() {
        return this.regulationEnabled;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether to stop the motor before disconnecting.")
    public boolean StopBeforeDisconnect() {
        return this.stopBeforeDisconnect;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty
    public void StopBeforeDisconnect(boolean stopBeforeDisconnect) {
        this.stopBeforeDisconnect = stopBeforeDisconnect;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Whether the TachoCountChanged event should fire when the angle is changed.")
    public boolean TachoCountChangedEventEnabled() {
        return this.tachoCountChangedEventEnabled;
    }

    @DesignerProperty(defaultValue = "False", editorType = "boolean")
    @SimpleProperty
    public void TachoCountChangedEventEnabled(boolean enabled) {
        this.tachoCountChangedEventEnabled = enabled;
    }

    @SimpleFunction(description = "Start to rotate the motors.")
    public void RotateIndefinitely(int power) {
        String functionName = "RotateIndefinitely";
        try {
            if (this.regulationEnabled) {
                setOutputPower(functionName, 0, this.motorPortBitField, power);
            } else {
                setOutputSpeed(functionName, 0, this.motorPortBitField, power);
            }
            startOutput(functionName, 0, this.motorPortBitField);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Rotate the motors in a number of tacho counts.")
    public void RotateInTachoCounts(int power, int tachoCounts, boolean useBrake) {
        String functionName = "RotateInTachoCounts";
        try {
            if (this.regulationEnabled) {
                outputStepSpeed(functionName, 0, this.motorPortBitField, power, 0, tachoCounts, 0, useBrake);
            } else {
                outputStepPower(functionName, 0, this.motorPortBitField, power, 0, tachoCounts, 0, useBrake);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Rotate the motors in a period of time.")
    public void RotateInDuration(int power, int milliseconds, boolean useBrake) {
        String functionName = "RotateInDuration";
        try {
            if (this.regulationEnabled) {
                outputTimeSpeed(functionName, 0, this.motorPortBitField, power, 0, milliseconds, 0, useBrake);
            } else {
                outputTimePower(functionName, 0, this.motorPortBitField, power, 0, milliseconds, 0, useBrake);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Rotate the motors in a distance.")
    public void RotateInDistance(int power, double distance, boolean useBrake) {
        String functionName = "RotateInDistance";
        int tachoCounts = (int) (((360.0d * distance) / this.wheelDiameter) / 3.141592653589793d);
        try {
            if (this.regulationEnabled) {
                outputStepSpeed(functionName, 0, this.motorPortBitField, power, 0, tachoCounts, 0, useBrake);
            } else {
                outputStepPower(functionName, 0, this.motorPortBitField, power, 0, tachoCounts, 0, useBrake);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Start to rotate the motors at the same speed.")
    public void RotateSyncIndefinitely(int power, int turnRatio) {
        String functionName = "RotateSyncIndefinitely";
        try {
            if (this.motorPortBitField == 0) {
                return;
            }
            if (isOneShotInteger(this.motorPortBitField)) {
                setOutputSpeed(functionName, 0, this.motorPortBitField, power);
            } else {
                outputStepSync(functionName, 0, this.motorPortBitField, power, turnRatio, 0, true);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Rotate the motors at the same speed for a distance in cm.")
    public void RotateSyncInDistance(int power, int distance, int turnRatio, boolean useBrake) {
        String functionName = "RotateSyncInDuration";
        int tachoCounts = (int) (((((double) distance) * 360.0d) / this.wheelDiameter) / 3.141592653589793d);
        try {
            if (this.motorPortBitField == 0) {
                return;
            }
            if (isOneShotInteger(this.motorPortBitField)) {
                outputStepSpeed(functionName, 0, this.motorPortBitField, power, 0, tachoCounts, 0, useBrake);
            } else {
                outputStepSync(functionName, 0, this.motorPortBitField, power, turnRatio, tachoCounts, useBrake);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Rotate the motors at the same speed in a period of time.")
    public void RotateSyncInDuration(int power, int milliseconds, int turnRatio, boolean useBrake) {
        String functionName = "RotateSyncInDuration";
        try {
            if (this.motorPortBitField == 0) {
                return;
            }
            if (isOneShotInteger(this.motorPortBitField)) {
                outputTimeSpeed(functionName, 0, this.motorPortBitField, power, 0, milliseconds, 0, useBrake);
            } else {
                outputTimeSync(functionName, 0, this.motorPortBitField, power, turnRatio, milliseconds, useBrake);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Rotate the motors at the same speed in a number of tacho counts.")
    public void RotateSyncInTachoCounts(int power, int tachoCounts, int turnRatio, boolean useBrake) {
        String functionName = "RotateSyncInTachoCounts";
        try {
            if (this.motorPortBitField == 0) {
                return;
            }
            if (isOneShotInteger(this.motorPortBitField)) {
                outputStepSpeed(functionName, 0, this.motorPortBitField, power, 0, tachoCounts, 0, useBrake);
            } else {
                outputStepSync(functionName, 0, this.motorPortBitField, power, turnRatio, tachoCounts, useBrake);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Stop the motors of the robot.")
    public void Stop(boolean useBrake) {
        String functionName = "Stop";
        try {
            stopOutput(functionName, 0, this.motorPortBitField, useBrake);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Toggle the direction of motors.")
    public void ToggleDirection() {
        String functionName = "ToggleDirection";
        try {
            boolean z;
            setOutputDirection(functionName, 0, this.motorPortBitField, 0);
            if (this.directionReversed) {
                z = false;
            } else {
                z = true;
            }
            this.directionReversed = z;
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Set the current tacho count to zero.")
    public void ResetTachoCount() {
        String functionName = "ResetTachoCount";
        try {
            clearOutputCount(functionName, 0, this.motorPortBitField);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
    }

    @SimpleFunction(description = "Get the current tacho count.")
    public int GetTachoCount() {
        int i = 0;
        String functionName = "GetTachoCount";
        try {
            i = getOutputCount(functionName, 0, this.motorPortBitField);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
        }
        return i;
    }

    @SimpleEvent(description = "Called when the tacho count has changed.")
    public void TachoCountChanged(int tachoCount) {
        EventDispatcher.dispatchEvent(this, "TachoCountChanged", Integer.valueOf(tachoCount));
    }

    private int roundValue(int value, int minimum, int maximum) {
        if (value < minimum) {
            return minimum;
        }
        return value > maximum ? maximum : value;
    }

    private boolean isOneShotInteger(int value) {
        return value != 0 && ((((value - 1) ^ value) ^ -1) & value) == 0;
    }

    private void resetOutput(String functionName, int layer, int nos) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15) {
            throw new IllegalArgumentException();
        }
        this.ifReset = true;
        int i = 0;
        int i2 = 0;
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_RESET, false, i, i2, "cc", Byte.valueOf((byte) layer), Byte.valueOf((byte) nos)), false);
    }

    private void startOutput(String functionName, int layer, int nos) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15) {
            throw new IllegalArgumentException();
        }
        int i = 0;
        int i2 = 0;
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_START, false, i, i2, "cc", Byte.valueOf((byte) layer), Byte.valueOf((byte) nos)), false);
    }

    private void stopOutput(String functionName, int layer, int nos, boolean useBrake) {
        byte b = (byte) 1;
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15) {
            throw new IllegalArgumentException();
        }
        String str = "ccc";
        Object[] objArr = new Object[3];
        objArr[0] = Byte.valueOf((byte) layer);
        objArr[1] = Byte.valueOf((byte) nos);
        if (!useBrake) {
            b = (byte) 0;
        }
        objArr[2] = Byte.valueOf(b);
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_STOP, false, 0, 0, str, objArr), false);
    }

    private void outputStepPower(String functionName, int layer, int nos, int power, int step1, int step2, int step3, boolean brake) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15 || step1 < 0 || step2 < 0 || step3 < 0) {
            throw new IllegalArgumentException();
        }
        int i;
        power = roundValue(power, -100, 100);
        String str = "ccccccc";
        Object[] objArr = new Object[7];
        objArr[0] = Byte.valueOf((byte) layer);
        objArr[1] = Byte.valueOf((byte) nos);
        objArr[2] = Byte.valueOf((byte) power);
        objArr[3] = Integer.valueOf(step1);
        objArr[4] = Integer.valueOf(step2);
        objArr[5] = Integer.valueOf(step3);
        if (brake) {
            i = 1;
        } else {
            i = 0;
        }
        objArr[6] = Byte.valueOf((byte) i);
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_STEP_POWER, false, 0, 0, str, objArr), false);
    }

    private void outputStepSpeed(String functionName, int layer, int nos, int speed, int step1, int step2, int step3, boolean brake) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15 || step1 < 0 || step2 < 0 || step3 < 0) {
            throw new IllegalArgumentException();
        }
        int i;
        speed = roundValue(speed, -100, 100);
        String str = "ccccccc";
        Object[] objArr = new Object[7];
        objArr[0] = Byte.valueOf((byte) layer);
        objArr[1] = Byte.valueOf((byte) nos);
        objArr[2] = Byte.valueOf((byte) speed);
        objArr[3] = Integer.valueOf(step1);
        objArr[4] = Integer.valueOf(step2);
        objArr[5] = Integer.valueOf(step3);
        if (brake) {
            i = 1;
        } else {
            i = 0;
        }
        objArr[6] = Byte.valueOf((byte) i);
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_STEP_SPEED, false, 0, 0, str, objArr), false);
    }

    private void outputStepSync(String functionName, int layer, int nos, int speed, int turnRatio, int tachoCounts, boolean brake) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15 || turnRatio < -200 || turnRatio > HttpRequestContext.HTTP_OK) {
            throw new IllegalArgumentException();
        }
        int i;
        speed = roundValue(speed, -100, 100);
        String str = "cccccc";
        Object[] objArr = new Object[6];
        objArr[0] = Byte.valueOf((byte) layer);
        objArr[1] = Byte.valueOf((byte) nos);
        objArr[2] = Byte.valueOf((byte) speed);
        objArr[3] = Short.valueOf((short) turnRatio);
        objArr[4] = Integer.valueOf(tachoCounts);
        if (brake) {
            i = 1;
        } else {
            i = 0;
        }
        objArr[5] = Byte.valueOf((byte) i);
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_STEP_SYNC, false, 0, 0, str, objArr), false);
    }

    private void outputTimePower(String functionName, int layer, int nos, int power, int step1, int step2, int step3, boolean brake) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15 || step1 < 0 || step2 < 0 || step3 < 0) {
            throw new IllegalArgumentException();
        }
        int i;
        power = roundValue(power, -100, 100);
        String str = "ccccccc";
        Object[] objArr = new Object[7];
        objArr[0] = Byte.valueOf((byte) layer);
        objArr[1] = Byte.valueOf((byte) nos);
        objArr[2] = Byte.valueOf((byte) power);
        objArr[3] = Integer.valueOf(step1);
        objArr[4] = Integer.valueOf(step2);
        objArr[5] = Integer.valueOf(step3);
        if (brake) {
            i = 1;
        } else {
            i = 0;
        }
        objArr[6] = Byte.valueOf((byte) i);
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_TIME_POWER, false, 0, 0, str, objArr), false);
    }

    private void outputTimeSpeed(String functionName, int layer, int nos, int speed, int step1, int step2, int step3, boolean brake) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15 || step1 < 0 || step2 < 0 || step3 < 0) {
            throw new IllegalArgumentException();
        }
        int i;
        speed = roundValue(speed, -100, 100);
        String str = "ccccccc";
        Object[] objArr = new Object[7];
        objArr[0] = Byte.valueOf((byte) layer);
        objArr[1] = Byte.valueOf((byte) nos);
        objArr[2] = Byte.valueOf((byte) speed);
        objArr[3] = Integer.valueOf(step1);
        objArr[4] = Integer.valueOf(step2);
        objArr[5] = Integer.valueOf(step3);
        if (brake) {
            i = 1;
        } else {
            i = 0;
        }
        objArr[6] = Byte.valueOf((byte) i);
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_TIME_SPEED, false, 0, 0, str, objArr), false);
    }

    private void outputTimeSync(String functionName, int layer, int nos, int speed, int turnRatio, int milliseconds, boolean brake) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15 || milliseconds < 0) {
            throw new IllegalArgumentException();
        }
        int i;
        speed = roundValue(speed, -100, 100);
        String str = "cccccc";
        Object[] objArr = new Object[6];
        objArr[0] = Byte.valueOf((byte) layer);
        objArr[1] = Byte.valueOf((byte) nos);
        objArr[2] = Byte.valueOf((byte) speed);
        objArr[3] = Short.valueOf((short) turnRatio);
        objArr[4] = Integer.valueOf(milliseconds);
        if (brake) {
            i = 1;
        } else {
            i = 0;
        }
        objArr[5] = Byte.valueOf((byte) i);
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_TIME_SYNC, false, 0, 0, str, objArr), false);
    }

    private void setOutputDirection(String functionName, int layer, int nos, int direction) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15 || direction < -1 || direction > 1) {
            throw new IllegalArgumentException();
        }
        int i = 0;
        int i2 = 0;
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_POLARITY, false, i, i2, "ccc", Byte.valueOf((byte) layer), Byte.valueOf((byte) nos), Byte.valueOf((byte) direction)), false);
    }

    private void setOutputSpeed(String functionName, int layer, int nos, int speed) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15) {
            throw new IllegalArgumentException();
        }
        speed = roundValue(speed, -100, 100);
        int i = 0;
        int i2 = 0;
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_SPEED, false, i, i2, "ccc", Byte.valueOf((byte) layer), Byte.valueOf((byte) nos), Byte.valueOf((byte) speed)), false);
    }

    private void setOutputPower(String functionName, int layer, int nos, int power) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15) {
            throw new IllegalArgumentException();
        }
        power = roundValue(power, -100, 100);
        int i = 0;
        int i2 = 0;
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_POWER, false, i, i2, "ccc", Byte.valueOf((byte) layer), Byte.valueOf((byte) nos), Byte.valueOf((byte) power)), false);
    }

    private int getOutputCount(String functionName, int layer, int nos) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15) {
            throw new IllegalArgumentException();
        }
        int portNumber;
        switch ((((nos - 1) ^ nos) + 1) >>> 1) {
            case 1:
                portNumber = 0;
                break;
            case 2:
                portNumber = 1;
                break;
            case 4:
                portNumber = 2;
                break;
            case 8:
                portNumber = 3;
                break;
            default:
                throw new IllegalArgumentException();
        }
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_GET_COUNT, true, 4, 0, "ccg", Byte.valueOf((byte) layer), Byte.valueOf((byte) portNumber), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply.length == 5 && reply[0] == (byte) 2) {
            return ((Integer) Ev3BinaryParser.unpack("xi", reply)[0]).intValue();
        }
        return 0;
    }

    private void clearOutputCount(String functionName, int layer, int nos) {
        if (layer < 0 || layer > 3 || nos < 0 || nos > 15) {
            throw new IllegalArgumentException();
        }
        int i = 0;
        int i2 = 0;
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.OUTPUT_CLR_COUNT, false, i, i2, "cc", Byte.valueOf((byte) layer), Byte.valueOf((byte) nos)), false);
    }

    public void beforeDisconnect(BluetoothConnectionBase bluetoothConnection) {
        String functionName = "beforeDisconnect";
        if (this.stopBeforeDisconnect) {
            try {
                stopOutput(functionName, 0, this.motorPortBitField, true);
            } catch (IllegalArgumentException e) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
            }
        }
    }
}

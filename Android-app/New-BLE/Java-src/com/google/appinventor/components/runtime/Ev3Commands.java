package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;
import com.google.appinventor.components.runtime.util.Ev3Constants.Opcode;

@SimpleObject
@DesignerComponent(category = ComponentCategory.LEGOMINDSTORMS, description = "A component that provides a low-level interface to a LEGO MINDSTORMS EV3 robot, with functions to send system or direct commands to EV3 robots.", iconName = "images/legoMindstormsEv3.png", nonVisible = true, version = 1)
@UsesPermissions(permissionNames = "android.permission.INTERNET,android.permission.WRITE_EXTERNAL_STORAGE,android.permission.READ_EXTERNAL_STORAGE")
public class Ev3Commands extends LegoMindstormsEv3Base {
    public Ev3Commands(ComponentContainer container) {
        super(container, "Ev3Commands");
    }

    @SimpleFunction(description = "Keep the EV3 brick from shutdown for a period of time.")
    public void KeepAlive(int minutes) {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        if (minutes < 0 || minutes > 255) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_ARGUMENT, functionName);
            return;
        }
        int i = 0;
        int i2 = 0;
        sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand(Opcode.KEEP_ALIVE, false, i, i2, "c", Byte.valueOf((byte) minutes)), false);
    }

    @SimpleFunction(description = "Get the battery voltage.")
    public double GetBatteryVoltage() {
        byte[] reply = sendCommand(Thread.currentThread().getStackTrace()[1].getMethodName(), Ev3BinaryParser.encodeDirectCommand((byte) -127, true, 4, 0, "cg", Byte.valueOf((byte) 1), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply.length == 5 && reply[0] == (byte) 2) {
            return (double) ((Float) Ev3BinaryParser.unpack("xf", reply)[0]).floatValue();
        }
        return -1.0d;
    }

    @SimpleFunction(description = "Get the battery current.")
    public double GetBatteryCurrent() {
        byte[] reply = sendCommand(Thread.currentThread().getStackTrace()[1].getMethodName(), Ev3BinaryParser.encodeDirectCommand((byte) -127, true, 4, 0, "cg", Byte.valueOf((byte) 2), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply.length == 5 && reply[0] == (byte) 2) {
            return (double) ((Float) Ev3BinaryParser.unpack("xf", reply)[0]).floatValue();
        }
        return -1.0d;
    }

    @SimpleFunction(description = "Get the OS version on EV3.")
    public String GetOSVersion() {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) -127, true, 100, 0, "ccg", Byte.valueOf((byte) 3), Short.valueOf((short) 100), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply[0] == (byte) 2) {
            return String.valueOf(Ev3BinaryParser.unpack("xS", reply)[0]);
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return null;
    }

    @SimpleFunction(description = "Get the OS build on EV3.")
    public String GetOSBuild() {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) 3, true, 100, 0, "ccg", Byte.valueOf((byte) 12), Short.valueOf((short) 100), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply[0] == (byte) 2) {
            return String.valueOf(Ev3BinaryParser.unpack("xS", reply)[0]);
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return null;
    }

    @SimpleFunction(description = "Get the firmware version on EV3.")
    public String GetFirmwareVersion() {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) -127, true, 100, 0, "ccg", Byte.valueOf((byte) 10), Short.valueOf((short) 100), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply[0] == (byte) 2) {
            return String.valueOf(Ev3BinaryParser.unpack("xS", reply)[0]);
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return null;
    }

    @SimpleFunction(description = "Get the firmware build on EV3.")
    public String GetFirmwareBuild() {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) -127, true, 100, 0, "cg", Byte.valueOf(Opcode.MEMORY_READ), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply[0] == (byte) 2) {
            return String.valueOf(Ev3BinaryParser.unpack("xS", reply)[0]);
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return null;
    }

    @SimpleFunction(description = "Get the hardware version of EV3.")
    public String GetHardwareVersion() {
        String functionName = Thread.currentThread().getStackTrace()[1].getMethodName();
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) -127, true, 100, 0, "ccg", Byte.valueOf((byte) 9), Short.valueOf((short) 100), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply[0] == (byte) 2) {
            return String.valueOf(Ev3BinaryParser.unpack("xS", reply)[0]);
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return null;
    }
}

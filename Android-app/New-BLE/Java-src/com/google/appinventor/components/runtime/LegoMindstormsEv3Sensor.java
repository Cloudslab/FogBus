package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;

@SimpleObject
public class LegoMindstormsEv3Sensor extends LegoMindstormsEv3Base {
    protected static final String DEFAULT_SENSOR_PORT = "1";
    protected int sensorPortNumber;

    protected LegoMindstormsEv3Sensor(ComponentContainer container, String logTag) {
        super(container, logTag);
        SensorPort(DEFAULT_SENSOR_PORT);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The sensor port that the sensor is connected to.", userVisible = false)
    public String SensorPort() {
        return portNumberToSensorPortLetter(this.sensorPortNumber);
    }

    @DesignerProperty(defaultValue = "1", editorType = "lego_ev3_sensor_port")
    @SimpleProperty
    public void SensorPort(String sensorPortLetter) {
        setSensorPort("SensorPort", sensorPortLetter);
    }

    protected final void setSensorPort(String functionName, String sensorPortLetter) {
        try {
            this.sensorPortNumber = sensorPortLetterToPortNumber(sensorPortLetter);
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_ILLEGAL_SENSOR_PORT, sensorPortLetter);
        }
    }

    protected final int readInputPercentage(String functionName, int layer, int no, int type, int mode) {
        if (layer < 0 || layer > 3 || no < 0 || no > 3 || mode < -1 || mode > 7) {
            throw new IllegalArgumentException();
        }
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) -103, true, 1, 0, "ccccccg", Byte.valueOf((byte) 27), Byte.valueOf((byte) layer), Byte.valueOf((byte) no), Byte.valueOf((byte) type), Byte.valueOf((byte) mode), Byte.valueOf((byte) 1), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply.length == 2 && reply[0] == (byte) 2) {
            return reply[1];
        }
        return -1;
    }

    protected final double readInputSI(String functionName, int layer, int no, int type, int mode) {
        if (layer < 0 || layer > 3 || no < 0 || no > 3 || mode < -1 || mode > 7) {
            throw new IllegalArgumentException();
        }
        byte[] reply = sendCommand(functionName, Ev3BinaryParser.encodeDirectCommand((byte) -103, true, 4, 0, "ccccccg", Byte.valueOf((byte) 29), Byte.valueOf((byte) layer), Byte.valueOf((byte) no), Byte.valueOf((byte) type), Byte.valueOf((byte) mode), Byte.valueOf((byte) 1), Byte.valueOf((byte) 0)), true);
        if (reply != null && reply.length == 5 && reply[0] == (byte) 2) {
            return (double) ((Float) Ev3BinaryParser.unpack("xf", reply)[0]).floatValue();
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return -1.0d;
    }
}

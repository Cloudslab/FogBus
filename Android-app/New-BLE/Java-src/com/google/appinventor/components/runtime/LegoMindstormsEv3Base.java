package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.Ev3BinaryParser;
import java.util.Collections;

@SimpleObject
public class LegoMindstormsEv3Base extends AndroidNonvisibleComponent implements BluetoothConnectionListener, Component, Deleteable {
    private static final int TOY_ROBOT = 2052;
    protected BluetoothClient bluetooth;
    protected int commandCount;
    protected final String logTag;

    protected LegoMindstormsEv3Base(ComponentContainer container, String logTag) {
        super(container.$form());
        this.logTag = logTag;
    }

    protected LegoMindstormsEv3Base() {
        super(null);
        this.logTag = null;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The BluetoothClient component that should be used for communication.")
    public BluetoothClient BluetoothClient() {
        return this.bluetooth;
    }

    @DesignerProperty(defaultValue = "", editorType = "BluetoothClient")
    @SimpleProperty
    public void BluetoothClient(BluetoothClient bluetoothClient) {
        if (this.bluetooth != null) {
            this.bluetooth.removeBluetoothConnectionListener(this);
            this.bluetooth.detachComponent(this);
            this.bluetooth = null;
        }
        if (bluetoothClient != null) {
            this.bluetooth = bluetoothClient;
            this.bluetooth.attachComponent(this, Collections.singleton(Integer.valueOf(TOY_ROBOT)));
            this.bluetooth.addBluetoothConnectionListener(this);
            if (this.bluetooth.IsConnected()) {
                afterConnect(this.bluetooth);
            }
        }
    }

    protected final boolean isBluetoothConnected(String functionName) {
        if (this.bluetooth == null) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_BLUETOOTH_NOT_SET, new Object[0]);
            return false;
        } else if (this.bluetooth.IsConnected()) {
            return true;
        } else {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_NOT_CONNECTED_TO_ROBOT, new Object[0]);
            return false;
        }
    }

    protected final byte[] sendCommand(String functionName, byte[] command, boolean doReceiveReply) {
        if (!isBluetoothConnected(functionName)) {
            return null;
        }
        byte[] header = Ev3BinaryParser.pack("hh", Short.valueOf((short) (command.length + 2)), Short.valueOf((short) this.commandCount));
        this.commandCount++;
        this.bluetooth.write(functionName, header);
        this.bluetooth.write(functionName, command);
        if (!doReceiveReply) {
            return null;
        }
        header = this.bluetooth.read(functionName, 4);
        if (header.length == 4) {
            Object[] decodedHeader = Ev3BinaryParser.unpack("hh", header);
            int replySize = ((Short) decodedHeader[0]).shortValue() - 2;
            int replyCount = ((Short) decodedHeader[1]).shortValue();
            byte[] reply = this.bluetooth.read(functionName, replySize);
            if (reply.length == replySize) {
                return reply;
            }
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
            return null;
        }
        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_EV3_INVALID_REPLY, new Object[0]);
        return null;
    }

    protected final int sensorPortLetterToPortNumber(String letter) {
        if (letter.length() != 1) {
            throw new IllegalArgumentException("String \"" + letter + "\" is not a valid sensor port letter");
        }
        int portNumber = letter.charAt(0) - 49;
        if (portNumber >= 0 && portNumber <= 3) {
            return portNumber;
        }
        throw new IllegalArgumentException("String \"" + letter + "\" is not a valid sensor port letter");
    }

    protected final String portNumberToSensorPortLetter(int portNumber) {
        if (portNumber >= 0 && portNumber <= 3) {
            return "" + (portNumber + 49);
        }
        throw new IllegalArgumentException(portNumber + " is not a valid port number");
    }

    protected final int motorPortLettersToBitField(String letters) {
        if (letters.length() > 4) {
            throw new IllegalArgumentException("Malformed motor port letters \"" + letters + "\"");
        }
        int portABit = 0;
        int portBBit = 0;
        int portCBit = 0;
        int portDBit = 0;
        for (int i = 0; i < letters.length(); i++) {
            switch (letters.charAt(i)) {
                case 'A':
                    if (portABit == 0) {
                        portABit = 1;
                        break;
                    }
                    throw new IllegalArgumentException("Malformed motor port letters \"" + letters + "\"");
                case 'B':
                    if (portBBit == 0) {
                        portBBit = 2;
                        break;
                    }
                    throw new IllegalArgumentException("Malformed motor port letters \"" + letters + "\"");
                case 'C':
                    if (portCBit == 0) {
                        portCBit = 4;
                        break;
                    }
                    throw new IllegalArgumentException("Malformed motor port letters \"" + letters + "\"");
                case 'D':
                    if (portDBit == 0) {
                        portDBit = 8;
                        break;
                    }
                    throw new IllegalArgumentException("Malformed motor port letters \"" + letters + "\"");
                default:
                    throw new IllegalArgumentException("Malformed motor port letters \"" + letters + "\"");
            }
        }
        return ((portABit | portBBit) | portCBit) | portDBit;
    }

    protected final String bitFieldToMotorPortLetters(int bitField) {
        if (bitField < 0 || bitField > 15) {
            throw new IllegalArgumentException("Invalid bit field number " + bitField);
        }
        String portLetters = "";
        if ((bitField & 1) != 0) {
            portLetters = portLetters + "A";
        }
        if ((bitField & 2) != 0) {
            portLetters = portLetters + "B";
        }
        if ((bitField & 4) != 0) {
            portLetters = portLetters + "C";
        }
        if ((bitField & 8) != 0) {
            return portLetters + "D";
        }
        return portLetters;
    }

    public void afterConnect(BluetoothConnectionBase bluetoothConnection) {
    }

    public void beforeDisconnect(BluetoothConnectionBase bluetoothConnection) {
    }

    public void onDelete() {
        if (this.bluetooth != null) {
            this.bluetooth.removeBluetoothConnectionListener(this);
            this.bluetooth.detachComponent(this);
            this.bluetooth = null;
        }
    }
}

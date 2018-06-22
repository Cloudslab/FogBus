package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.common.PropertyTypeConstants;
import com.google.appinventor.components.runtime.util.BluetoothReflection;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SimpleObject
@DesignerComponent(category = ComponentCategory.CONNECTIVITY, description = "Bluetooth client component", iconName = "images/bluetooth.png", nonVisible = true, version = 5)
@UsesPermissions(permissionNames = "android.permission.BLUETOOTH, android.permission.BLUETOOTH_ADMIN")
public final class BluetoothClient extends BluetoothConnectionBase {
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private Set<Integer> acceptableDeviceClasses;
    private final List<Component> attachedComponents = new ArrayList();

    public BluetoothClient(ComponentContainer container) {
        super(container, PropertyTypeConstants.PROPERTY_TYPE_BLUETOOTHCLIENT);
    }

    boolean attachComponent(Component component, Set<Integer> acceptableDeviceClasses) {
        if (this.attachedComponents.isEmpty()) {
            Set set;
            if (acceptableDeviceClasses == null) {
                set = null;
            } else {
                set = new HashSet(acceptableDeviceClasses);
            }
            this.acceptableDeviceClasses = set;
        } else if (this.acceptableDeviceClasses == null) {
            if (acceptableDeviceClasses != null) {
                return false;
            }
        } else if (acceptableDeviceClasses == null || !this.acceptableDeviceClasses.containsAll(acceptableDeviceClasses)) {
            return false;
        } else {
            if (!acceptableDeviceClasses.containsAll(this.acceptableDeviceClasses)) {
                return false;
            }
        }
        this.attachedComponents.add(component);
        return true;
    }

    void detachComponent(Component component) {
        this.attachedComponents.remove(component);
        if (this.attachedComponents.isEmpty()) {
            this.acceptableDeviceClasses = null;
        }
    }

    @SimpleFunction(description = "Checks whether the Bluetooth device with the specified address is paired.")
    public boolean IsDevicePaired(String address) {
        String functionName = "IsDevicePaired";
        Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
        if (bluetoothAdapter == null) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_AVAILABLE, new Object[0]);
            return false;
        } else if (BluetoothReflection.isBluetoothEnabled(bluetoothAdapter)) {
            int firstSpace = address.indexOf(" ");
            if (firstSpace != -1) {
                address = address.substring(0, firstSpace);
            }
            if (BluetoothReflection.checkBluetoothAddress(bluetoothAdapter, address)) {
                return BluetoothReflection.isBonded(BluetoothReflection.getRemoteDevice(bluetoothAdapter, address));
            }
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_INVALID_ADDRESS, new Object[0]);
            return false;
        } else {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_ENABLED, new Object[0]);
            return false;
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The addresses and names of paired Bluetooth devices")
    public List<String> AddressesAndNames() {
        List<String> addressesAndNames = new ArrayList();
        Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
        if (bluetoothAdapter != null && BluetoothReflection.isBluetoothEnabled(bluetoothAdapter)) {
            for (Object bluetoothDevice : BluetoothReflection.getBondedDevices(bluetoothAdapter)) {
                if (isDeviceClassAcceptable(bluetoothDevice)) {
                    addressesAndNames.add(BluetoothReflection.getBluetoothDeviceAddress(bluetoothDevice) + " " + BluetoothReflection.getBluetoothDeviceName(bluetoothDevice));
                }
            }
        }
        return addressesAndNames;
    }

    private boolean isDeviceClassAcceptable(Object bluetoothDevice) {
        if (this.acceptableDeviceClasses == null) {
            return true;
        }
        Object bluetoothClass = BluetoothReflection.getBluetoothClass(bluetoothDevice);
        if (bluetoothClass == null) {
            return false;
        }
        return this.acceptableDeviceClasses.contains(Integer.valueOf(BluetoothReflection.getDeviceClass(bluetoothClass)));
    }

    @SimpleFunction(description = "Connect to the Bluetooth device with the specified address and the Serial Port Profile (SPP). Returns true if the connection was successful.")
    public boolean Connect(String address) {
        return connect("Connect", address, SPP_UUID);
    }

    @SimpleFunction(description = "Connect to the Bluetooth device with the specified address and UUID. Returns true if the connection was successful.")
    public boolean ConnectWithUUID(String address, String uuid) {
        return connect("ConnectWithUUID", address, uuid);
    }

    private boolean connect(String functionName, String address, String uuidString) {
        Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
        if (bluetoothAdapter == null) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_AVAILABLE, new Object[0]);
            return false;
        } else if (BluetoothReflection.isBluetoothEnabled(bluetoothAdapter)) {
            int firstSpace = address.indexOf(" ");
            if (firstSpace != -1) {
                address = address.substring(0, firstSpace);
            }
            if (BluetoothReflection.checkBluetoothAddress(bluetoothAdapter, address)) {
                Object bluetoothDevice = BluetoothReflection.getRemoteDevice(bluetoothAdapter, address);
                if (!BluetoothReflection.isBonded(bluetoothDevice)) {
                    this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_PAIRED_DEVICE, new Object[0]);
                    return false;
                } else if (isDeviceClassAcceptable(bluetoothDevice)) {
                    try {
                        UUID uuid = UUID.fromString(uuidString);
                        Disconnect();
                        try {
                            connect(bluetoothDevice, uuid);
                            return true;
                        } catch (IOException e) {
                            Disconnect();
                            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_UNABLE_TO_CONNECT, new Object[0]);
                            return false;
                        }
                    } catch (IllegalArgumentException e2) {
                        this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_INVALID_UUID, uuidString);
                        return false;
                    }
                } else {
                    this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_REQUIRED_CLASS_OF_DEVICE, new Object[0]);
                    return false;
                }
            }
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_INVALID_ADDRESS, new Object[0]);
            return false;
        } else {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_ENABLED, new Object[0]);
            return false;
        }
    }

    private void connect(Object bluetoothDevice, UUID uuid) throws IOException {
        Object bluetoothSocket;
        if (this.secure || SdkLevel.getLevel() < 10) {
            bluetoothSocket = BluetoothReflection.createRfcommSocketToServiceRecord(bluetoothDevice, uuid);
        } else {
            bluetoothSocket = BluetoothReflection.createInsecureRfcommSocketToServiceRecord(bluetoothDevice, uuid);
        }
        BluetoothReflection.connectToBluetoothSocket(bluetoothSocket);
        setConnection(bluetoothSocket);
        Log.i(this.logTag, "Connected to Bluetooth device " + BluetoothReflection.getBluetoothDeviceAddress(bluetoothDevice) + " " + BluetoothReflection.getBluetoothDeviceName(bluetoothDevice) + ".");
    }
}

package edu.mit.appinventor.ble;

import android.app.Activity;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.Deleteable;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import com.google.common.collect.Lists;
import gnu.lists.FString;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@DesignerComponent(category = ComponentCategory.EXTENSION, description = "Bluetooth Low Energy, also referred to as Bluetooth LE or simply BLE, is a new communication protocol similar to classic Bluetooth except that it is designed to consume less power while maintaining comparable functionality. For this reason, Bluetooth LE is the preferred choice of communication with IoT devices that have limited power resources. Starting with Android 4.3, Google introduced built-in support for Bluetooth Low Energy. The BluetoothLE extension requires Android 5.0 or higher to avoid known issues with Google's Bluetooth LE support prior to Android 5.0.", iconName = "images/bluetooth.png", nonVisible = true, version = 2)
@SimpleObject(external = true)
@UsesPermissions(permissionNames = "android.permission.BLUETOOTH, android.permission.BLUETOOTH_ADMIN,android.permission.ACCESS_COARSE_LOCATION")
public class BluetoothLE extends AndroidNonvisibleComponent implements Component, Deleteable {
    public static final int ERROR_CHARACTERISTIC_INDEX_OOB = 9104;
    public static final int ERROR_DEVICE_INDEX_OOB = 9101;
    public static final int ERROR_SERVICE_INDEX_OOB = 9102;
    public static final int ERROR_SERVICE_INVALID_UUID = 9103;
    private static final String LOG_TAG = "BluetoothLE";
    private final Activity activity;
    Set<BluetoothConnectionListener> connectionListeners = new HashSet();
    private BluetoothLEint inner;

    public static abstract class BLEResponseHandler<T> {
        public void onReceive(String serviceUuid, String characteristicUuid, List<T> list) {
        }

        public void onWrite(String serviceUuid, String characteristicUuid, List<T> list) {
        }
    }

    public interface BluetoothConnectionListener {
        void onConnected(BluetoothLE bluetoothLE);

        void onDisconnected(BluetoothLE bluetoothLE);
    }

    public void onDelete() {
        if (this.inner != null) {
            this.inner.Disconnect();
            this.inner = null;
        }
    }

    public BluetoothLE(ComponentContainer container) {
        super(container.$form());
        this.activity = container.$context();
        if (!container.$form().getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            Log.e(LOG_TAG, "Bluetooth LE is unsupported on this hardware. Any subsequent function calls will complain.");
        } else if (SdkLevel.getLevel() < 21) {
            Log.e(LOG_TAG, "The BluetoothLE extension is unsupported at this API Level. Any subsequent function calls will complain.");
        } else {
            Log.d(LOG_TAG, "Appear to have Bluetooth LE support, continuing...");
        }
        this.inner = new BluetoothLEint(this, this.activity, container);
    }

    public Form getForm() {
        return this.form;
    }

    public void addConnectionListener(BluetoothConnectionListener listener) {
        this.connectionListeners.add(listener);
    }

    public void removeConnectionListener(BluetoothConnectionListener listener) {
        this.connectionListeners.remove(listener);
    }

    @DesignerProperty(defaultValue = "false", editorType = "boolean")
    @SimpleProperty
    public void AutoReconnect(boolean autoReconnect) {
        this.inner.setAutoReconnect(autoReconnect);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "If true, the application will attempt to reestablish a lost connection to a device due to link loss (e.g., moving out of range). This will not apply to connections that are disconnected by a call to the <a href='#Disconnect'><code>Disconnect</code></a> method. Such disconnects will need to be reconnected via a call to <a href='#Connect'><code>Connect</code></a> or <a href='#ConnectWithAddress'><code>ConnectWithAddress</code></a>.")
    public boolean AutoReconnect() {
        return this.inner.getAutoReconnect();
    }

    @DesignerProperty(defaultValue = "10", editorType = "non_negative_integer")
    @SimpleProperty
    public void ConnectionTimeout(int timeout) {
        this.inner.setConnectionTimeout(timeout);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The amount of time, in seconds, that the BluetoothLE component will wait for a connection to be established with a device after a call to <a href='#Connect'><code>Connect</code></a> or <a href='#ConnectWithAddress'><code>ConnectWithAddress</code></a>. If a connection is not established in the given amount of time, the attempt will be aborted and the <a href='#ConnectionFailed'><code>ConnectionFailed</code></a> event will be run.")
    public int ConnectionTimeout() {
        return this.inner.getConnectionTimeout();
    }

    @SimpleFunction
    public void StartScanning() {
        if (this.inner != null) {
            this.inner.StartScanning();
        }
    }

    @SimpleFunction
    public void StopScanning() {
        if (this.inner != null) {
            this.inner.StopScanning();
        }
    }

    @SimpleProperty(description = "The scanning state of the Bluetooth low energy component.")
    public boolean Scanning() {
        if (this.inner != null) {
            return this.inner.isScanning();
        }
        return false;
    }

    @SimpleFunction
    public void Connect(int index) {
        if (this.inner != null) {
            try {
                this.inner.Connect(index);
            } catch (IndexOutOfBoundsException e) {
                this.form.dispatchErrorOccurredEvent(this, "Connect", ErrorMessages.ERROR_EXTENSION_ERROR, Integer.valueOf(ERROR_DEVICE_INDEX_OOB), LOG_TAG, e.getMessage());
            }
        }
    }

    @SimpleFunction
    public void ConnectWithAddress(String address) {
        if (this.inner != null) {
            this.inner.ConnectWithAddress(address);
        }
    }

    @SimpleFunction
    public void Disconnect() {
        if (this.inner != null) {
            this.inner.Disconnect();
        }
    }

    @SimpleFunction
    public void DisconnectWithAddress(String address) {
        if (this.inner != null) {
            this.inner.DisconnectWithAddress(address);
        }
    }

    @SimpleFunction
    public void ReadBytes(String serviceUuid, String characteristicUuid, boolean signed) {
        if (this.inner != null) {
            this.inner.ReadByteValues(serviceUuid, characteristicUuid, signed);
        }
    }

    @SimpleFunction
    public void RegisterForBytes(String serviceUuid, String characteristicUuid, boolean signed) {
        if (this.inner != null) {
            this.inner.RegisterForByteValues(serviceUuid, characteristicUuid, signed);
        }
    }

    @SimpleFunction
    public void WriteBytes(String serviceUuid, String characteristicUuid, boolean signed, Object values) {
        if (this.inner != null) {
            this.inner.WriteByteValues(serviceUuid, characteristicUuid, signed, toList(Integer.class, values, 1));
        }
    }

    @SimpleFunction
    public void WriteBytesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, Object values) {
        if (this.inner != null) {
            this.inner.WriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed, toList(Integer.class, values, 1));
        }
    }

    @SimpleFunction
    public void ReadShorts(String serviceUuid, String characteristicUuid, boolean signed) {
        if (this.inner != null) {
            this.inner.ReadShortValues(serviceUuid, characteristicUuid, signed);
        }
    }

    @SimpleFunction
    public void RegisterForShorts(String serviceUuid, String characteristicUuid, boolean signed) {
        if (this.inner != null) {
            this.inner.RegisterForShortValues(serviceUuid, characteristicUuid, signed);
        }
    }

    @SimpleFunction
    public void WriteShorts(String serviceUuid, String characteristicUuid, boolean signed, Object values) {
        if (this.inner != null) {
            this.inner.WriteShortValues(serviceUuid, characteristicUuid, signed, toList(Integer.class, values, 2));
        }
    }

    @SimpleFunction
    public void WriteShortsWithResponse(String serviceUuid, String characteristicUuid, boolean signed, Object values) {
        if (this.inner != null) {
            this.inner.WriteShortValuesWithResponse(serviceUuid, characteristicUuid, signed, toList(Integer.class, values, 2));
        }
    }

    @SimpleFunction
    public void ReadIntegers(String serviceUuid, String characteristicUuid, boolean signed) {
        if (this.inner != null) {
            this.inner.ReadIntegerValues(serviceUuid, characteristicUuid, signed);
        }
    }

    @SimpleFunction
    public void RegisterForIntegers(String serviceUuid, String characteristicUuid, boolean signed) {
        if (this.inner != null) {
            this.inner.RegisterForIntegerValues(serviceUuid, characteristicUuid, signed);
        }
    }

    @SimpleFunction
    public void WriteIntegers(String serviceUuid, String characteristicUuid, boolean signed, Object values) {
        if (this.inner != null) {
            this.inner.WriteIntegerValues(serviceUuid, characteristicUuid, signed, toList(Long.class, values, 4));
        }
    }

    @SimpleFunction
    public void WriteIntegersWithResponse(String serviceUuid, String characteristicUuid, boolean signed, Object values) {
        if (this.inner != null) {
            this.inner.WriteIntegerValuesWithResponse(serviceUuid, characteristicUuid, signed, toList(Long.class, values, 4));
        }
    }

    @SimpleFunction
    public void ReadFloats(String serviceUuid, String characteristicUuid, boolean shortFloat) {
        if (this.inner != null) {
            this.inner.ReadFloatValues(serviceUuid, characteristicUuid, shortFloat);
        }
    }

    @SimpleFunction
    public void RegisterForFloats(String serviceUuid, String characteristicUuid, boolean shortFloat) {
        if (this.inner != null) {
            this.inner.RegisterForFloatValues(serviceUuid, characteristicUuid, shortFloat);
        }
    }

    @SimpleFunction
    public void WriteFloats(String serviceUuid, String characteristicUuid, boolean shortFloat, Object values) {
        if (this.inner != null) {
            this.inner.WriteFloatValues(serviceUuid, characteristicUuid, shortFloat, toList(Float.class, values, shortFloat ? 2 : 4));
        }
    }

    @SimpleFunction
    public void WriteFloatsWithResponse(String serviceUuid, String characteristicUuid, boolean shortFloat, Object values) {
        if (this.inner != null) {
            this.inner.WriteFloatValuesWithResponse(serviceUuid, characteristicUuid, shortFloat, toList(Float.class, values, shortFloat ? 2 : 4));
        }
    }

    @SimpleFunction
    public void ReadStrings(String serviceUuid, String characteristicUuid, boolean utf16) {
        if (this.inner != null) {
            this.inner.ReadStringValues(serviceUuid, characteristicUuid, utf16);
        }
    }

    @SimpleFunction
    public void RegisterForStrings(String serviceUuid, String characteristicUuid, boolean utf16) {
        if (this.inner != null) {
            this.inner.RegisterForStringValues(serviceUuid, characteristicUuid, utf16);
        }
    }

    @SimpleFunction
    public void WriteStrings(String serviceUuid, String characteristicUuid, boolean utf16, Object values) {
        if (this.inner != null) {
            this.inner.WriteStringValues(serviceUuid, characteristicUuid, utf16, toList(String.class, values, utf16 ? 2 : 1));
        }
    }

    @SimpleFunction
    public void WriteStringsWithResponse(String serviceUuid, String characteristicUuid, boolean utf16, Object values) {
        if (this.inner != null) {
            this.inner.WriteStringValuesWithResponse(serviceUuid, characteristicUuid, utf16, toList(String.class, values, utf16 ? 2 : 1));
        }
    }

    @SimpleFunction
    public void UnregisterForValues(String service_uuid, String characteristic_uuid) {
        if (this.inner != null) {
            this.inner.UnregisterForValues(service_uuid, characteristic_uuid);
        }
    }

    @SimpleFunction
    public int FoundDeviceRssi(int index) {
        int i = 0;
        if (this.inner != null) {
            try {
                i = this.inner.FoundDeviceRssi(index);
            } catch (IndexOutOfBoundsException e) {
                this.form.dispatchErrorOccurredEvent(this, "FoundDeviceRssi", ErrorMessages.ERROR_EXTENSION_ERROR, Integer.valueOf(ERROR_DEVICE_INDEX_OOB), LOG_TAG, e.getMessage());
            }
        }
        return i;
    }

    @SimpleFunction
    public String FoundDeviceName(int index) {
        if (this.inner != null) {
            try {
                String result = this.inner.FoundDeviceName(index);
                if (result == null) {
                    return "";
                }
                return result;
            } catch (IllegalArgumentException e) {
                this.form.dispatchErrorOccurredEvent(this, "FoundDeviceName", ErrorMessages.ERROR_EXTENSION_ERROR, Integer.valueOf(ERROR_DEVICE_INDEX_OOB), LOG_TAG, e.getMessage());
            }
        }
        return "";
    }

    @SimpleFunction
    public String FoundDeviceAddress(int index) {
        if (this.inner != null) {
            try {
                String result = this.inner.FoundDeviceAddress(index);
                if (result == null) {
                    return "";
                }
                return result;
            } catch (IllegalArgumentException e) {
                this.form.dispatchErrorOccurredEvent(this, "FoundDeviceAddress", ErrorMessages.ERROR_EXTENSION_ERROR, Integer.valueOf(ERROR_DEVICE_INDEX_OOB), LOG_TAG, e.getMessage());
            }
        }
        return "";
    }

    @SimpleFunction
    public void StartAdvertising(String inData, String serviceUuid) {
        if (this.inner != null) {
            this.inner.StartAdvertising(inData, serviceUuid);
        }
    }

    @SimpleFunction
    public void StopAdvertising() {
        if (this.inner != null) {
            this.inner.StopAdvertising();
        }
    }

    @SimpleFunction
    public void ScanAdvertisements(long scanPeriod) {
        if (this.inner != null) {
            this.inner.ScanAdvertisements(scanPeriod);
        }
    }

    @SimpleFunction
    public void StopScanningAdvertisements() {
        if (this.inner != null) {
            this.inner.StopScanningAdvertisements();
        }
    }

    @SimpleFunction
    public String AdvertisementData(String deviceAddress, String serviceUuid) {
        if (this.inner != null) {
            return this.inner.GetAdvertisementData(deviceAddress, serviceUuid);
        }
        return "";
    }

    @SimpleFunction
    public String AdvertiserAddress(String deviceName) {
        if (this.inner != null) {
            return this.inner.GetAdvertiserAddress(deviceName);
        }
        return "";
    }

    @SimpleFunction
    public List<String> AdvertiserServiceUuids(String deviceAddress) {
        if (this.inner != null) {
            return this.inner.GetAdvertiserServiceUuids(deviceAddress);
        }
        return YailList.makeEmptyList();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the battery level.")
    public String BatteryValue() {
        if (this.inner != null) {
            return this.inner.BatteryValue();
        }
        return "";
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the transmission power.")
    public int TxPower() {
        if (this.inner != null) {
            return this.inner.TxPower();
        }
        return -1;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns true if a BluetoothLE device is connected; Otherwise, returns false.")
    public boolean IsDeviceConnected() {
        if (this.inner != null) {
            return this.inner.IsDeviceConnected();
        }
        return false;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns a sorted list of BluetoothLE devices as a String.")
    public String DeviceList() {
        if (this.inner != null) {
            return this.inner.DeviceList();
        }
        return "";
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "Returns the RSSI (Received Signal Strength Indicator) of connected device.")
    public int ConnectedDeviceRssi() {
        if (this.inner != null) {
            return this.inner.ConnectedDeviceRssi();
        }
        return Integer.MIN_VALUE;
    }

    @SimpleProperty(description = "Returns the value of ScanPeriod.")
    public long AdvertisementScanPeriod() {
        if (this.inner != null) {
            return this.inner.AdvertisementScanPeriod();
        }
        return 0;
    }

    @SimpleProperty(description = "Returns a list of the names of the devices found during Advertisment scanning.")
    public List<String> AdvertiserNames() {
        if (this.inner != null) {
            return this.inner.GetAdvertiserNames();
        }
        return Collections.emptyList();
    }

    @SimpleProperty(description = "Returns a list of the addresses of devices found during Advertisement scanning.")
    public List<String> AdvertiserAddresses() {
        if (this.inner != null) {
            return this.inner.GetAdvertiserAddresses();
        }
        return Collections.emptyList();
    }

    @SimpleProperty(description = "Returns true if the device is currently advertising, false otherwise.")
    public boolean IsDeviceAdvertising() {
        if (this.inner != null) {
            return this.inner.IsDeviceAdvertising();
        }
        return false;
    }

    @SimpleEvent
    public void Connected() {
    }

    @SimpleEvent
    public void ConnectionFailed(final String reason) {
        this.form.runOnUiThread(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(BluetoothLE.this, "ConnectionFailed", reason);
            }
        });
    }

    @SimpleEvent
    public void Disconnected() {
    }

    @SimpleEvent(description = "Trigger event when RSSI (Received Signal Strength Indicator) of found BluetoothLE device changes")
    public void RssiChanged(int rssi) {
    }

    @SimpleEvent
    public void DeviceFound() {
    }

    @SimpleEvent
    public void BytesReceived(String serviceUuid, String characteristicUuid, YailList byteValues) {
        EventDispatcher.dispatchEvent(this, "BytesReceived", serviceUuid, characteristicUuid, byteValues);
    }

    @SimpleEvent
    public void BytesWritten(String serviceUuid, String characteristicUuid, YailList byteValues) {
        EventDispatcher.dispatchEvent(this, "BytesWritten", serviceUuid, characteristicUuid, byteValues);
    }

    @SimpleEvent
    public void ShortsReceived(String serviceUuid, String characteristicUuid, YailList shortValues) {
        EventDispatcher.dispatchEvent(this, "ShortsReceived", serviceUuid, characteristicUuid, shortValues);
    }

    @SimpleEvent
    public void ShortsWritten(String serviceUuid, String characteristicUuid, YailList shortValues) {
        EventDispatcher.dispatchEvent(this, "ShortsWritten", serviceUuid, characteristicUuid, shortValues);
    }

    @SimpleEvent
    public void IntegersReceived(String serviceUuid, String characteristicUuid, YailList intValues) {
        EventDispatcher.dispatchEvent(this, "IntegersReceived", serviceUuid, characteristicUuid, intValues);
    }

    @SimpleEvent
    public void IntegersWritten(String serviceUuid, String characteristicUuid, YailList intValues) {
        EventDispatcher.dispatchEvent(this, "IntegersWritten", serviceUuid, characteristicUuid, intValues);
    }

    @SimpleEvent
    public void FloatsReceived(String serviceUuid, String characteristicUuid, YailList floatValues) {
        EventDispatcher.dispatchEvent(this, "FloatsReceived", serviceUuid, characteristicUuid, floatValues);
    }

    @SimpleEvent
    public void FloatsWritten(String serviceUuid, String characteristicUuid, YailList floatValues) {
        EventDispatcher.dispatchEvent(this, "FloatsWritten", serviceUuid, characteristicUuid, floatValues);
    }

    @SimpleEvent
    public void StringsReceived(String serviceUuid, String characteristicUuid, YailList stringValues) {
        EventDispatcher.dispatchEvent(this, "StringsReceived", serviceUuid, characteristicUuid, stringValues);
    }

    @SimpleEvent
    public void StringsWritten(String serviceUuid, String characteristicUuid, YailList stringValues) {
        EventDispatcher.dispatchEvent(this, "StringsWritten", serviceUuid, characteristicUuid, stringValues);
    }

    @SimpleFunction
    public String SupportedServices() {
        if (this.inner != null) {
            return this.inner.GetSupportedServices();
        }
        return "";
    }

    @SimpleProperty(description = "A list of pairs, one for each advertised service, indicating the service's UUID and its name, if known. The format of the list will be ((uuid1 name1) (uuid2 name2) ...). If no device is connected or Bluetooth low energy is not supported, then an empty list will be returned.")
    public YailList DeviceServices() {
        if (this.inner != null) {
            return this.inner.getSupportedServicesList();
        }
        return YailList.makeEmptyList();
    }

    @SimpleFunction
    public String ServiceByIndex(int index) {
        if (this.inner != null) {
            try {
                String result = this.inner.GetServiceByIndex(index);
                if (result == null) {
                    return "";
                }
                return result;
            } catch (IndexOutOfBoundsException e) {
                this.form.dispatchErrorOccurredEvent(this, "ServiceByIndex", ErrorMessages.ERROR_EXTENSION_ERROR, Integer.valueOf(ERROR_SERVICE_INDEX_OOB), LOG_TAG, e.getMessage());
            }
        }
        return "";
    }

    @SimpleFunction
    public String SupportedCharacteristics() {
        if (this.inner != null) {
            return this.inner.GetSupportedCharacteristics();
        }
        return "";
    }

    @SimpleProperty(description = "A list of triples, one for each characteristic advertised by the connected device, containing the service UUID, characteristic UUID, and the characteristic's name, if known. The format of the list will be ((service1 characteristic1 name1) (service2 characteristic2 name2) ...). If no device is connected or Bluetooth low energy is not supported, then an empty list will be returned.")
    public YailList DeviceCharacteristics() {
        if (this.inner != null) {
            return this.inner.getSupportedCharacteristicsList();
        }
        return YailList.makeEmptyList();
    }

    @SimpleProperty(description = "The advertised name of the connected device. If no device is connected or Bluetooth low energy is not supported, this will return the empty string.")
    public String ConnectedDeviceName() {
        if (this.inner != null) {
            return this.inner.ConnectedDeviceName();
        }
        return "";
    }

    @SimpleFunction
    public YailList GetCharacteristicsForService(String serviceUuid) {
        if (this.inner != null) {
            try {
                return this.inner.GetCharacteristicsForService(serviceUuid);
            } catch (RuntimeException e) {
                this.form.dispatchErrorOccurredEvent(this, "GetCharacteristicsForService", ErrorMessages.ERROR_EXTENSION_ERROR, Integer.valueOf(ERROR_SERVICE_INVALID_UUID), LOG_TAG, e.getMessage());
            }
        }
        return YailList.makeEmptyList();
    }

    @SimpleFunction
    public String CharacteristicByIndex(int index) {
        if (this.inner != null) {
            try {
                String result = this.inner.GetCharacteristicByIndex(index);
                if (result == null) {
                    return "";
                }
                return result;
            } catch (IndexOutOfBoundsException e) {
                this.form.dispatchErrorOccurredEvent(this, "CharacteristicByIndex", ErrorMessages.ERROR_EXTENSION_ERROR, Integer.valueOf(ERROR_CHARACTERISTIC_INDEX_OOB), LOG_TAG, e.getMessage());
            }
        }
        return "";
    }

    public void ExReadByteValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Integer> callback) {
        if (this.inner != null) {
            this.inner.ReadByteValues(serviceUuid, characteristicUuid, signed, callback);
        }
    }

    public void ExRegisterForByteValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Integer> callback) {
        if (this.inner != null) {
            this.inner.RegisterForByteValues(serviceUuid, characteristicUuid, signed, callback);
        }
    }

    public void ExWriteByteValues(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values) {
        if (this.inner != null) {
            this.inner.WriteByteValues(serviceUuid, characteristicUuid, signed, values);
        }
    }

    public void ExWriteByteValues(String serviceUuid, String characteristicUuid, boolean signed, Object value) {
        if (this.inner != null) {
            this.inner.WriteByteValues(serviceUuid, characteristicUuid, signed, toList(Integer.class, value, 1));
        }
    }

    public void ExWriteByteValues(String serviceUuid, String characteristicUuid, boolean signed, int value) {
        ExWriteByteValues(serviceUuid, characteristicUuid, signed, Collections.singletonList(Integer.valueOf(value)));
    }

    public void ExWriteByteValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values, BLEResponseHandler<Integer> callback) {
        if (this.inner != null) {
            this.inner.WriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed, values, callback);
        }
    }

    public void ExWriteByteValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, Object value, BLEResponseHandler<Integer> callback) {
        if (this.inner != null) {
            this.inner.WriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed, toList(Integer.class, value, 1), callback);
        }
    }

    public void ExWriteByteValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, int value, BLEResponseHandler<Integer> callback) {
        ExWriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed, Collections.singletonList(Integer.valueOf(value)), (BLEResponseHandler) callback);
    }

    public void ExReadShortValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Integer> callback) {
        if (this.inner != null) {
            this.inner.ReadShortValues(serviceUuid, characteristicUuid, signed, callback);
        }
    }

    public void ExRegisterForShortValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Integer> callback) {
        if (this.inner != null) {
            this.inner.RegisterForShortValues(serviceUuid, characteristicUuid, signed, callback);
        }
    }

    public void ExWriteShortValues(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values) {
        if (this.inner != null) {
            this.inner.WriteShortValues(serviceUuid, characteristicUuid, signed, values);
        }
    }

    public void ExWriteShortValues(String serviceUuid, String characteristicUuid, boolean signed, int value) {
        ExWriteShortValues(serviceUuid, characteristicUuid, signed, Collections.singletonList(Integer.valueOf(value)));
    }

    public void ExWriteShortValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values, BLEResponseHandler<Integer> callback) {
        if (this.inner != null) {
            this.inner.WriteShortValuesWithResponse(serviceUuid, characteristicUuid, signed, values, callback);
        }
    }

    public void ExWriteShortValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, int value, BLEResponseHandler<Integer> callback) {
        ExWriteShortValuesWithResponse(serviceUuid, characteristicUuid, signed, Collections.singletonList(Integer.valueOf(value)), (BLEResponseHandler) callback);
    }

    public void ExReadIntegerValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Long> callback) {
        if (this.inner != null) {
            this.inner.ReadIntegerValues(serviceUuid, characteristicUuid, signed, callback);
        }
    }

    public void ExRegisterForIntegerValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Long> callback) {
        if (this.inner != null) {
            this.inner.ReadIntegerValues(serviceUuid, characteristicUuid, signed, callback);
        }
    }

    public void ExWriteIntegerValues(String serviceUuid, String characteristicUuid, boolean signed, List<Long> values) {
        if (this.inner != null) {
            this.inner.WriteIntegerValues(serviceUuid, characteristicUuid, signed, values);
        }
    }

    public void ExWriteIntegerValues(String serviceUuid, String characteristicUuid, boolean signed, long value) {
        ExWriteIntegerValues(serviceUuid, characteristicUuid, signed, Collections.singletonList(Long.valueOf(value)));
    }

    public void ExWriteIntegerValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Long> values, BLEResponseHandler<Long> callback) {
        if (this.inner != null) {
            this.inner.WriteIntegerValuesWithResponse(serviceUuid, characteristicUuid, signed, values, callback);
        }
    }

    public void ExWriteIntegerValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, long value, BLEResponseHandler<Long> callback) {
        ExWriteIntegerValuesWithResponse(serviceUuid, characteristicUuid, signed, Collections.singletonList(Long.valueOf(value)), (BLEResponseHandler) callback);
    }

    public void ExReadFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloats, BLEResponseHandler<Float> callback) {
        if (this.inner != null) {
            this.inner.ReadFloatValues(serviceUuid, characteristicUuid, shortFloats, callback);
        }
    }

    public void ExRegisterForFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloats, BLEResponseHandler<Float> callback) {
        if (this.inner != null) {
            this.inner.RegisterForFloatValues(serviceUuid, characteristicUuid, shortFloats, callback);
        }
    }

    public void ExWriteFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloats, List<Float> values) {
        if (this.inner != null) {
            this.inner.WriteFloatValues(serviceUuid, characteristicUuid, shortFloats, values);
        }
    }

    public void ExWriteFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloats, float value) {
        ExWriteFloatValues(serviceUuid, characteristicUuid, shortFloats, Collections.singletonList(Float.valueOf(value)));
    }

    public void ExWriteFloatValuesWithResponse(String serviceUuid, String characteristicUuid, boolean shortFloats, List<Float> values, BLEResponseHandler<Float> callback) {
        if (this.inner != null) {
            this.inner.WriteFloatValuesWithResponse(serviceUuid, characteristicUuid, shortFloats, values, callback);
        }
    }

    public void ExWriteFloatValuesWithResponse(String serviceUuid, String characteristicUuid, boolean shortFloats, float value, BLEResponseHandler<Float> callback) {
        ExWriteFloatValuesWithResponse(serviceUuid, characteristicUuid, shortFloats, Collections.singletonList(Float.valueOf(value)), (BLEResponseHandler) callback);
    }

    public void ExReadStringValues(String serviceUuid, String characteristicUuid, boolean utf16, BLEResponseHandler<String> callback) {
        if (this.inner != null) {
            this.inner.ReadStringValues(serviceUuid, characteristicUuid, utf16, callback);
        }
    }

    public void ExRegisterForStringValues(String serviceUuid, String characteristicUuid, boolean utf16, BLEResponseHandler<String> callback) {
        if (this.inner != null) {
            this.inner.RegisterForStringValues(serviceUuid, characteristicUuid, utf16, callback);
        }
    }

    public void ExWriteStringValues(String serviceUuid, String characteristicUuid, boolean utf16, List<String> values) {
        if (this.inner != null) {
            this.inner.WriteStringValues(serviceUuid, characteristicUuid, utf16, values);
        }
    }

    public void ExWriteStringValues(String serviceUuid, String characteristicUuid, boolean utf16, String value) {
        ExWriteStringValues(serviceUuid, characteristicUuid, utf16, Collections.singletonList(value));
    }

    public void ExWriteStringValuesWithResponse(String serviceUuid, String characteristicUuid, boolean utf16, List<String> values, BLEResponseHandler<String> callback) {
        if (this.inner != null) {
            this.inner.WriteStringValuesWithResponse(serviceUuid, characteristicUuid, utf16, values, callback);
        }
    }

    public void ExWriteStringValuesWithResponse(String serviceUuid, String characteristicUuid, boolean utf16, String value, BLEResponseHandler<String> callback) {
        ExWriteStringValuesWithResponse(serviceUuid, characteristicUuid, utf16, Collections.singletonList(value), (BLEResponseHandler) callback);
    }

    public void ExUnregisterForValues(String serviceUuid, String characteristicUuid, BLEResponseHandler<?> callback) {
        if (this.inner != null) {
            this.inner.UnregisterForValues(serviceUuid, characteristicUuid, callback);
        }
    }

    public boolean isServicePublished(String serviceUuid) {
        if (this.inner != null) {
            return this.inner.isServicePublished(serviceUuid);
        }
        return false;
    }

    public boolean isCharacteristicPublished(String serviceUuid, String characteristicUuid) {
        if (this.inner != null) {
            return this.inner.isCharacteristicPublished(serviceUuid, characteristicUuid);
        }
        return false;
    }

    private static <T> List<T> toList(Class<T> tClass, Object value, int size) {
        if (tClass.isAssignableFrom(value.getClass())) {
            return Collections.singletonList(tClass.cast(value));
        }
        if (value instanceof YailList) {
            Iterator<?> i = ((YailList) value).iterator();
            i.next();
            return listFromIterator(tClass, i);
        } else if (value instanceof FString) {
            return toList(tClass, value.toString(), size);
        } else {
            if (value instanceof List) {
                return listFromIterator(tClass, ((List) value).iterator());
            }
            if (value instanceof Collection) {
                return listFromIterator(tClass, ((Collection) value).iterator());
            }
            if (value instanceof String) {
                try {
                    byte[] content = ((String) value).getBytes(size == 1 ? "UTF-8" : "UTF-16LE");
                    if (tClass.equals(Integer.class)) {
                        return checkedCast(tClass, toIntList(content));
                    }
                    return Collections.emptyList();
                } catch (UnsupportedEncodingException e) {
                    Log.wtf(LOG_TAG, "No support for UTF-8 or UTF-16", e);
                    return Collections.emptyList();
                }
            }
            throw new ClassCastException("Unable to convert " + value + " to list");
        }
    }

    private static <T> List<T> listFromIterator(Class<T> tClass, Iterator<?> i) {
        if (tClass.equals(Integer.class)) {
            return toIntList(Lists.newArrayList(i));
        }
        if (tClass.equals(Long.class)) {
            return toLongList(Lists.newArrayList(i));
        }
        if (tClass.equals(Float.class)) {
            return toFloatList(Lists.newArrayList(i));
        }
        List<T> result = new ArrayList();
        while (i.hasNext()) {
            Object o = i.next();
            if (tClass.isInstance(o) || tClass.isAssignableFrom(o.getClass())) {
                result.add(tClass.cast(o));
            } else {
                throw new ClassCastException("Unable to convert " + o + " of type " + o.getClass().getName() + " to type " + tClass.getName());
            }
        }
        return result;
    }

    private static <T> List<T> checkedCast(Class<T> tClass, List<?> list) {
        for (Object o : list) {
            if (!tClass.isInstance(o) && !tClass.isAssignableFrom(o.getClass())) {
                throw new ClassCastException("Unable to convert " + o + " to type " + tClass.getName());
            }
        }
        return list;
    }

    private static <T extends Number> List<Float> toFloatList(List<T> value) {
        List<Float> result = new ArrayList(value.size());
        for (T o : value) {
            result.add(Float.valueOf(o.floatValue()));
        }
        return result;
    }

    private static <T extends Number> List<Long> toLongList(List<T> value) {
        List<Long> result = new ArrayList(value.size());
        for (T o : value) {
            result.add(Long.valueOf(o.longValue()));
        }
        return result;
    }

    private static <T extends Number> List<Integer> toIntList(List<T> value) {
        List<Integer> result = new ArrayList(value.size());
        for (T o : value) {
            result.add(Integer.valueOf(o.intValue()));
        }
        return result;
    }

    private static List<Integer> toIntList(byte[] values) {
        List<Integer> result = new ArrayList(values.length);
        for (byte b : values) {
            result.add(Integer.valueOf(b));
        }
        return result;
    }
}

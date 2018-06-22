package edu.mit.appinventor.ble;

import java.util.HashMap;
import java.util.UUID;

public class BluetoothLEGattAttributes {
    public static final UUID BATTERY_LEVEL_CHARACTERISTIC = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");
    public static final UUID BATTERY_LEVEL_SERVICE = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb");
    public static final UUID CLIENT_CHARACTERISTIC_CONFIGURATION = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID DEVICE_INFORMATION_SERVICE = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fbb");
    public static final UUID FINDME_CHARACTERISTIC = UUID.fromString("00002a06-0000-1000-8000-00805f9b34fb");
    public static final UUID FINDME_SERVICE = UUID.fromString("00001802-0000-1000-8000-00805f9b34fb");
    public static final UUID HEART_RATE_MEASURE_CHARACTERISTIC = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
    public static final UUID HEART_RATE_SERVICE = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    public static final UUID LINKLOSS_CHARACTERISTIC = UUID.fromString("00002a06-0000-1000-8000-00805f9b34fb");
    public static final UUID LINKLOSS_SERVICE = UUID.fromString("00001803-0000-1000-8000-00805f9b34fb");
    public static final UUID MANUFACTURER_NAME_STRING = UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb");
    public static final UUID THERMOMETER_CHARACTERISTIC = UUID.fromString("00002a1c-0000-1000-8000-00805f9b34fb");
    public static final UUID THERMOMETER_SERVICE = UUID.fromString("00001809-0000-1000-8000-00805f9b34fb");
    public static final UUID TXPOWER_CHARACTERISTIC = UUID.fromString("00002a07-0000-1000-8000-00805f9b34fb");
    public static final UUID TXPOWER_SERVICE = UUID.fromString("00001804-0000-1000-8000-00805f9b34fb");
    private static HashMap<UUID, String> attributes = new HashMap();

    static {
        attributes.put(HEART_RATE_SERVICE, "Heart Rate Service");
        attributes.put(DEVICE_INFORMATION_SERVICE, "Device Information Service");
        attributes.put(BATTERY_LEVEL_SERVICE, "Battery Level Service");
        attributes.put(CLIENT_CHARACTERISTIC_CONFIGURATION, "Client Characteristic Configuration ");
        attributes.put(HEART_RATE_MEASURE_CHARACTERISTIC, "Heart Rate Measurement");
        attributes.put(MANUFACTURER_NAME_STRING, "Manufacturer Name String");
        attributes.put(BATTERY_LEVEL_CHARACTERISTIC, "Battery Level Characteristic");
    }

    public static String lookup(UUID uuid, String defaultName) {
        String name = (String) attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}

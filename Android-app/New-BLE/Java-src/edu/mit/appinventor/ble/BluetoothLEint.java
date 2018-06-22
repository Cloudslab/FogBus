package edu.mit.appinventor.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.ScanSettings.Builder;
import android.content.Intent;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v4.media.TransportMediator;
import android.text.TextUtils;
import android.util.Log;
import com.google.appinventor.components.runtime.ActivityResultListener;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;
import com.google.appinventor.components.runtime.errors.IllegalArgumentError;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;
import com.google.appinventor.components.runtime.util.YailList;
import edu.mit.appinventor.ble.BluetoothLE.BLEResponseHandler;
import edu.mit.appinventor.ble.BluetoothLE.BluetoothConnectionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

final class BluetoothLEint {
    private static final String BLUETOOTH_BASE_UUID_SUFFIX = "-0000-1000-8000-00805F9B34FB";
    private static final int ERROR_ADVERTISEMENTS_NOT_SUPPORTED = 9010;
    private static final int ERROR_API_LEVEL_TOO_LOW = 9003;
    private static final int ERROR_BLUETOOTH_LE_NOT_ENABLED = 9002;
    private static final int ERROR_BLUETOOTH_LE_NOT_SUPPORTED = 9001;
    private static final int ERROR_DEVICE_LIST_EMPTY = 9007;
    private static final int ERROR_INDEX_OUT_OF_BOUNDS = 9006;
    private static final int ERROR_INVALID_UUID_CHARACTERS = 9008;
    private static final int ERROR_INVALID_UUID_FORMAT = 9009;
    private static final int ERROR_NOT_CURRENTLY_CONNECTED = 9005;
    private static final int ERROR_NO_DEVICE_SCAN_IN_PROGRESS = 9004;
    private static final int GATT_LINK_LOSS = 8;
    private static final String LOG_TAG = "BluetoothLEint";
    private static final String UNKNOWN_CHAR_NAME = "Unknown Characteristic";
    private static final String UNKNOWN_SERVICE_NAME = "Unknown Service";
    private static final Map<Integer, String> errorMessages = new HashMap();
    private long SCAN_PERIOD = 5000;
    private final Activity activity;
    private String advertisementScanResult = "";
    private List<String> advertiserAddresses = new ArrayList();
    private boolean autoReconnect = false;
    private int battery = -1;
    private String byteValue = "";
    private CharType charType = CharType.BYTE;
    private String charUUIDList;
    private volatile int connectionTimeout = 10;
    ComponentContainer container;
    private byte[] data;
    private byte[] descriptorValue;
    private String deviceInfoList = "";
    private int device_rssi = 0;
    private int floatOffset = 0;
    private float floatValue = 0.0f;
    private ArrayList<BluetoothGattCharacteristic> gattChars;
    private HashMap<String, BluetoothGatt> gattMap;
    private int intOffset = 0;
    private int intValue = 0;
    private boolean isAdvertising = false;
    private boolean isCharRead = false;
    private boolean isCharWritten = false;
    private volatile boolean isConnected = false;
    private boolean isScanning = false;
    private boolean isServiceRead = false;
    private volatile boolean isUserDisconnect = false;
    private AdvertiseCallback mAdvertiseCallback;
    private ScanCallback mAdvertisementScanCallback;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothLeScanner mBluetoothLeAdvertisementScanner;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private BluetoothLeScanner mBluetoothLeDeviceScanner = null;
    private ScanCallback mDeviceScanCallback;
    private BluetoothGattCallback mGattCallback;
    private BluetoothGattCharacteristic mGattChar;
    private List<BluetoothGattService> mGattService;
    private Handler mHandler = new Handler();
    private HashMap<BluetoothDevice, Integer> mLeDeviceRssi;
    private List<BluetoothDevice> mLeDevices;
    private HashMap<String, String> nameToAddress = new HashMap();
    BluetoothLE outer;
    private final Queue<BLEOperation> pendingOperations = new LinkedList();
    private final Map<UUID, List<BLEOperation>> pendingOperationsByUuid = new HashMap();
    private List<String> scannedAdvertiserNames = new ArrayList();
    private HashMap<String, ScanResult> scannedAdvertisers = new HashMap();
    private String serviceUUIDList;
    private int strOffset = 0;
    private String stringValue = "";
    private int txPower = -1;
    private final Handler uiThread;

    class C03501 extends ScanCallback {
        C03501() {
        }

        public void onScanResult(int callbackType, final ScanResult scanResult) {
            super.onScanResult(callbackType, scanResult);
            if (scanResult != null && scanResult.getDevice() != null) {
                BluetoothLEint.this.uiThread.post(new Runnable() {
                    public void run() {
                        BluetoothLEint.this.isScanning = true;
                        BluetoothLEint.this.addDevice(scanResult.getDevice(), scanResult.getRssi());
                    }
                });
            }
        }

        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        public void onScanFailed(int errorCode) {
            switch (errorCode) {
                case 1:
                    Log.e(BluetoothLEint.LOG_TAG, "Device Scan failed. There is already a scan in progress.");
                    break;
                default:
                    BluetoothLEint.this.isScanning = false;
                    Log.e(BluetoothLEint.LOG_TAG, "Device Scan failed due to an internal error. Error Code: " + errorCode);
                    break;
            }
            super.onScanFailed(errorCode);
        }
    }

    class C03512 extends BluetoothGattCallback {
        C03512() {
        }

        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (gatt == BluetoothLEint.this.mBluetoothGatt) {
                if (newState == 2) {
                    gatt.discoverServices();
                    gatt.readRemoteRssi();
                    Log.i(BluetoothLEint.LOG_TAG, "Connect successful.");
                } else if (newState == 0) {
                    BluetoothLEint.this.isConnected = false;
                    if (!BluetoothLEint.this.autoReconnect || BluetoothLEint.this.isUserDisconnect) {
                        BluetoothLEint.this.mBluetoothGatt.close();
                        BluetoothLEint.this.mBluetoothGatt = null;
                        BluetoothLEint.this.isUserDisconnect = false;
                    } else {
                        BluetoothLEint.this.mBluetoothGatt.connect();
                    }
                    BluetoothLEint.this.Disconnected();
                    Log.i(BluetoothLEint.LOG_TAG, "Disconnect successful.");
                }
                if (!(status == 0 || BluetoothLEint.this.isConnected || (status == 8 && BluetoothLEint.this.autoReconnect))) {
                    BluetoothLEint.this.outer.ConnectionFailed("Connection status was set to OS code " + status);
                }
                Log.i(BluetoothLEint.LOG_TAG, "onConnectionStateChange fired with status: " + status);
                Log.i(BluetoothLEint.LOG_TAG, "onConnectionStateChange fired with newState: " + newState);
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == 0) {
                Log.i(BluetoothLEint.LOG_TAG, "Services Discovered!");
                BluetoothLEint.this.mGattService = gatt.getServices();
                BluetoothLEint.this.isServiceRead = true;
                BluetoothLEint.this.isConnected = true;
                BluetoothLEint.this.pendingOperationsByUuid.clear();
                BluetoothLEint.this.pendingOperations.clear();
                BluetoothLEint.this.Connected();
            } else {
                BluetoothLEint.this.outer.ConnectionFailed("Service discovery failed with OS code " + status);
            }
            Log.i(BluetoothLEint.LOG_TAG, "onServicesDiscovered fired with status: " + status);
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (BluetoothLEint.this.pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
                Log.d(BluetoothLEint.LOG_TAG, "onCharacteristicRead: " + characteristic.getUuid());
                for (BLEOperation operation : new ArrayList((Collection) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid()))) {
                    operation.onCharacteristicRead(gatt, characteristic, status);
                }
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (BluetoothLEint.this.pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
                Log.d(BluetoothLEint.LOG_TAG, "onCharacteristicRead");
                for (BLEOperation operation : new ArrayList((Collection) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid()))) {
                    operation.onCharacteristicChanged(gatt, characteristic);
                }
            }
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (BluetoothLEint.this.pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
                for (BLEOperation operation : new ArrayList((Collection) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid()))) {
                    operation.onCharacteristicWrite(gatt, characteristic, status);
                }
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            BluetoothLEint.this.descriptorValue = descriptor.getValue();
            BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
            if (BluetoothLEint.this.pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
                Log.d(BluetoothLEint.LOG_TAG, "onDescriptorRead");
                for (BLEOperation operation : new ArrayList((Collection) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid()))) {
                    operation.onDescriptorRead(gatt, descriptor, status);
                }
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.i(BluetoothLEint.LOG_TAG, "Write Descriptor Successfully.");
            BluetoothGattCharacteristic characteristic = descriptor.getCharacteristic();
            if (BluetoothLEint.this.pendingOperationsByUuid.containsKey(characteristic.getUuid())) {
                Log.d(BluetoothLEint.LOG_TAG, "onDescriptorWrite");
                for (BLEOperation operation : new ArrayList((Collection) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid()))) {
                    operation.onDescriptorWrite(gatt, descriptor, status);
                }
            }
        }

        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            BluetoothLEint.this.device_rssi = rssi;
            BluetoothLEint.this.RssiChanged(BluetoothLEint.this.device_rssi);
        }
    }

    class C03543 extends ScanCallback {
        C03543() {
        }

        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (result != null && result.getDevice() != null && !TextUtils.isEmpty(result.getDevice().getName())) {
                String advertiserAddress = result.getDevice().getAddress();
                String advertiserName = result.getDevice().getName();
                BluetoothLEint.this.advertiserAddresses.add(advertiserAddress);
                BluetoothLEint.this.scannedAdvertisers.put(advertiserAddress, result);
                BluetoothLEint.this.scannedAdvertiserNames.add(advertiserName);
                BluetoothLEint.this.nameToAddress.put(advertiserName, advertiserAddress);
            }
        }

        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        public void onScanFailed(int errorCode) {
            Log.e(BluetoothLEint.LOG_TAG, "Advertisement onScanFailed: " + errorCode);
            super.onScanFailed(errorCode);
        }
    }

    public abstract class BLEOperation extends BluetoothGattCallback implements Runnable {
        static final int FORMAT_UTF16S = 2;
        static final int FORMAT_UTF8S = 1;
        protected final BluetoothGattCharacteristic characteristic;
        protected boolean needsRemoval = false;
        protected final int size;
        protected final int type;

        public abstract boolean isNotify();

        public abstract boolean isRead();

        public abstract boolean isWrite();

        BLEOperation(BluetoothGattCharacteristic characteristic, int type) {
            this.characteristic = characteristic;
            this.type = type;
            this.size = sizeofT(type);
        }

        public void read(BluetoothGatt gatt) {
        }

        public void unsubscribe(BluetoothGatt gatt) {
        }

        protected final int sizeofT(int type) {
            switch (type) {
                case 17:
                case 33:
                    return 1;
                case 18:
                case 34:
                case 50:
                    return 2;
                case 20:
                case 36:
                case 52:
                    return 4;
                default:
                    return -1;
            }
        }

        protected final void registerPendingOperation() {
            synchronized (BluetoothLEint.this.pendingOperationsByUuid) {
                UUID uuid = this.characteristic.getUuid();
                if (!BluetoothLEint.this.pendingOperationsByUuid.containsKey(uuid)) {
                    BluetoothLEint.this.pendingOperationsByUuid.put(uuid, new ArrayList());
                }
                if (!((List) BluetoothLEint.this.pendingOperationsByUuid.get(uuid)).contains(this)) {
                    ((List) BluetoothLEint.this.pendingOperationsByUuid.get(uuid)).add(this);
                    this.needsRemoval = true;
                }
            }
        }
    }

    private enum CharType {
        BYTE,
        INT,
        STRING,
        FLOAT
    }

    private abstract class BLEAction<T> implements ActivityResultListener {
        private final String functionName;
        private final int requestEnableBT;

        public abstract T action();

        BLEAction(String functionName) {
            this.requestEnableBT = BluetoothLEint.this.container.$form().registerForActivityResult(this);
            this.functionName = functionName;
        }

        public final T run() {
            if (!BluetoothLEint.this.container.$form().getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
                BluetoothLEint.this.signalError(this.functionName, BluetoothLEint.ERROR_BLUETOOTH_LE_NOT_SUPPORTED, new Object[0]);
                return null;
            } else if (SdkLevel.getLevel() >= 21) {
                return action();
            } else {
                BluetoothLEint.this.signalError(this.functionName, BluetoothLEint.ERROR_API_LEVEL_TOO_LOW, new Object[0]);
                return null;
            }
        }

        final BluetoothAdapter obtainBluetoothAdapter() {
            BluetoothAdapter bluetoothAdapter = ((BluetoothManager) BluetoothLEint.this.activity.getSystemService("bluetooth")).getAdapter();
            if (bluetoothAdapter == null) {
                BluetoothLEint.this.signalError(this.functionName, BluetoothLEint.ERROR_BLUETOOTH_LE_NOT_SUPPORTED, new Object[0]);
            } else if (!bluetoothAdapter.isEnabled()) {
                Log.i(BluetoothLEint.LOG_TAG, "Bluetooth is not enabled, attempting to enable now...");
                BluetoothLEint.this.activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), this.requestEnableBT);
            }
            return bluetoothAdapter;
        }

        public void resultReturned(int requestCode, int resultCode, Intent data) {
            if (requestCode != this.requestEnableBT) {
                return;
            }
            if (resultCode == -1) {
                run();
            } else if (resultCode == 0) {
                BluetoothLEint.this.signalError(this.functionName, BluetoothLEint.ERROR_BLUETOOTH_LE_NOT_ENABLED, new Object[0]);
            }
        }
    }

    private abstract class BLEReadOperation<T> extends BLEOperation {
        private int delay = 1;
        private final BLEResponseHandler<T> handler;
        private final Class<T> mClass;
        private boolean notify = false;

        protected abstract void onReceive(List<T> list);

        BLEReadOperation(Class<T> aClass, BluetoothGattCharacteristic characteristic, int type, BLEResponseHandler<T> handler, boolean notify) {
            super(characteristic, type);
            this.mClass = aClass;
            this.handler = handler;
            this.notify = notify;
        }

        public boolean isRead() {
            return true;
        }

        public boolean isNotify() {
            return this.notify;
        }

        public boolean isWrite() {
            return false;
        }

        public void run() {
            if (isNotify()) {
                subscribe(BluetoothLEint.this.mBluetoothGatt);
            } else {
                read(BluetoothLEint.this.mBluetoothGatt);
            }
        }

        public void read(final BluetoothGatt gatt) {
            if (gatt.readCharacteristic(this.characteristic)) {
                registerPendingOperation();
            } else if (this.delay > 2000) {
                Log.i(BluetoothLEint.LOG_TAG, "Took too long to schedule read. Treating this as failure.");
            } else {
                Log.d(BluetoothLEint.LOG_TAG, "Unable to read characteristic " + this.characteristic.getUuid() + ". Deferring operation by " + this.delay + "ms.");
                BluetoothLEint.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        BLEReadOperation.this.read(gatt);
                    }
                }, (long) this.delay);
                this.delay *= 2;
            }
        }

        public void subscribe(final BluetoothGatt gatt) {
            this.notify = true;
            registerPendingOperation();
            BluetoothGattDescriptor desc = this.characteristic.getDescriptor(BluetoothLEGattAttributes.CLIENT_CHARACTERISTIC_CONFIGURATION);
            boolean wroteDescriptor = false;
            if (desc != null) {
                if ((this.characteristic.getProperties() & 16) != 0) {
                    desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                } else {
                    desc.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                }
                gatt.writeDescriptor(desc);
                wroteDescriptor = true;
            }
            if (gatt.setCharacteristicNotification(this.characteristic, true)) {
                if (!wroteDescriptor && BluetoothLEint.this.runPendingOperation(this)) {
                    this.needsRemoval = false;
                }
                Log.d(BluetoothLEint.LOG_TAG, "Subscribed for UUID: " + this.characteristic.getUuid());
            } else if (this.delay > 2000) {
                Log.i(BluetoothLEint.LOG_TAG, "Took too long to subscribe. Treating this as failure.");
            } else {
                Log.d(BluetoothLEint.LOG_TAG, "Unable to set characteristic notification for " + this.characteristic.getUuid() + ". Deferring operation by " + this.delay + "ms.");
                BluetoothLEint.this.mHandler.postDelayed(new Runnable() {
                    public void run() {
                        BLEReadOperation.this.subscribe(gatt);
                    }
                }, (long) this.delay);
                this.delay *= 2;
            }
        }

        public void unsubscribe(final BluetoothGatt gatt) {
            synchronized (BluetoothLEint.this.pendingOperationsByUuid) {
                if (gatt.setCharacteristicNotification(this.characteristic, false)) {
                    ((List) BluetoothLEint.this.pendingOperationsByUuid.get(this.characteristic.getUuid())).remove(this);
                    this.notify = false;
                } else if (this.delay > 2000) {
                    Log.i(BluetoothLEint.LOG_TAG, "Took too long to unsubscribe. Treating this as failure.");
                } else {
                    Log.d(BluetoothLEint.LOG_TAG, "setCharacteristicNotification returned false. Deferring operation by " + this.delay + "ms.");
                    BluetoothLEint.this.mHandler.postDelayed(new Runnable() {
                        public void run() {
                            BLEReadOperation.this.unsubscribe(gatt);
                        }
                    }, (long) this.delay);
                    this.delay *= 2;
                }
            }
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            if (this.characteristic == characteristic) {
                final List<T> data = readCharacteristic();
                onReceive(data);
                Log.d(BluetoothLEint.LOG_TAG, "handler = " + this.handler);
                if (this.handler != null) {
                    Log.d(BluetoothLEint.LOG_TAG, "Posting handler's onReceive to UI thread");
                    BluetoothLEint.this.mHandler.post(new Runnable() {
                        public void run() {
                            BLEReadOperation.this.handler.onReceive(characteristic.getService().getUuid().toString(), characteristic.getUuid().toString(), data);
                        }
                    });
                }
                if (this.needsRemoval && BluetoothLEint.this.runPendingOperation(this)) {
                    this.needsRemoval = false;
                    return;
                }
                return;
            }
            Log.d(BluetoothLEint.LOG_TAG, "Characteristic did not match");
        }

        public void onCharacteristicRead(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
            if (this.characteristic == characteristic) {
                switch (status) {
                    case 0:
                        final List<T> data = readCharacteristic();
                        onReceive(data);
                        Log.d(BluetoothLEint.LOG_TAG, "handler = " + this.handler);
                        if (this.handler != null) {
                            Log.d(BluetoothLEint.LOG_TAG, "Posting handler's onReceive to UI thread");
                            BluetoothLEint.this.mHandler.post(new Runnable() {
                                public void run() {
                                    BLEReadOperation.this.handler.onReceive(characteristic.getService().getUuid().toString(), characteristic.getUuid().toString(), data);
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        try {
                            Log.e(BluetoothLEint.LOG_TAG, "Error code " + status + " from characteristic " + characteristic.getUuid());
                            break;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            ((List) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid())).remove(this);
                            if (this.needsRemoval && BluetoothLEint.this.runPendingOperation(this)) {
                                this.needsRemoval = false;
                            }
                            Log.d(BluetoothLEint.LOG_TAG, "pendingOperations.size() = " + BluetoothLEint.this.pendingOperations.size());
                            Log.d(BluetoothLEint.LOG_TAG, "pendingOperationsByUuid.size() = " + BluetoothLEint.this.pendingOperationsByUuid.size());
                        }
                }
                ((List) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid())).remove(this);
                if (this.needsRemoval && BluetoothLEint.this.runPendingOperation(this)) {
                    this.needsRemoval = false;
                }
                Log.d(BluetoothLEint.LOG_TAG, "pendingOperations.size() = " + BluetoothLEint.this.pendingOperations.size());
                Log.d(BluetoothLEint.LOG_TAG, "pendingOperationsByUuid.size() = " + BluetoothLEint.this.pendingOperationsByUuid.size());
            }
        }

        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.d(BluetoothLEint.LOG_TAG, "onDescriptorRead: " + descriptor.getCharacteristic().getUuid());
            if (this.needsRemoval && BluetoothLEint.this.runPendingOperation(this)) {
                this.needsRemoval = false;
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.d(BluetoothLEint.LOG_TAG, "onDescriptorWrite: " + descriptor.getCharacteristic().getUuid());
            if (this.needsRemoval && BluetoothLEint.this.runPendingOperation(this)) {
                this.needsRemoval = false;
            }
        }

        private List<T> readCharacteristic() {
            Throwable th;
            Log.d(BluetoothLEint.LOG_TAG, "Received bytes = " + Arrays.toString(this.characteristic.getValue()));
            if (this.type == 1 || this.type == 2) {
                Reader reader = null;
                List<T> result = new ArrayList();
                try {
                    Reader reader2 = new InputStreamReader(new ByteArrayInputStream(this.characteristic.getValue()), this.type == 1 ? "UTF-8" : "UTF-16LE");
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            int c = reader2.read();
                            if (c < 0) {
                                break;
                            } else if (c != 0) {
                                sb.append(Character.toChars(c));
                            } else {
                                if (sb.length() > 0) {
                                    result.add(this.mClass.cast(sb.toString()));
                                }
                                sb.setLength(0);
                            }
                        }
                        if (sb.length() > 0) {
                            result.add(this.mClass.cast(sb.toString()));
                        }
                        if (reader2 != null) {
                            try {
                                reader2.close();
                                reader = reader2;
                            } catch (IOException e) {
                                Log.wtf(BluetoothLEint.LOG_TAG, "Unable to close stream after IOException.");
                                reader = reader2;
                            }
                        }
                    } catch (IOException e2) {
                        reader = reader2;
                    } catch (Throwable th2) {
                        th = th2;
                        reader = reader2;
                    }
                } catch (IOException e3) {
                    try {
                        Log.e(BluetoothLEint.LOG_TAG, "Unable to read UTF-8 string from byte array.");
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e4) {
                                Log.wtf(BluetoothLEint.LOG_TAG, "Unable to close stream after IOException.");
                            }
                        }
                        return result;
                    } catch (Throwable th3) {
                        th = th3;
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e5) {
                                Log.wtf(BluetoothLEint.LOG_TAG, "Unable to close stream after IOException.");
                            }
                        }
                        throw th;
                    }
                }
                return result;
            } else if (this.type == 52 || this.type == 50) {
                elements = this.characteristic.getValue().length / this.size;
                values = new ArrayList(elements);
                if (this.size == 4) {
                    ByteBuffer buffer = ByteBuffer.wrap(this.characteristic.getValue()).order(ByteOrder.LITTLE_ENDIAN);
                    for (i = 0; i < elements; i++) {
                        values.add(this.mClass.cast(Float.valueOf(buffer.getFloat(this.size * i))));
                    }
                    return values;
                }
                for (i = 0; i < elements; i++) {
                    Float value = this.characteristic.getFloatValue(this.type, this.size * i);
                    if (value != null) {
                        values.add(this.mClass.cast(value));
                    } else {
                        values.add(this.mClass.cast(Float.valueOf(Float.NaN)));
                    }
                }
                return values;
            } else {
                elements = this.characteristic.getValue().length / this.size;
                Log.d(BluetoothLEint.LOG_TAG, "Reading " + elements + " elements of size " + this.size);
                values = new ArrayList(elements);
                for (i = 0; i < elements; i++) {
                    Integer value2 = this.characteristic.getIntValue(this.type, this.size * i);
                    if (value2 != null) {
                        values.add(this.mClass.cast(value2));
                    } else {
                        values.add(this.mClass.cast(Integer.valueOf(0)));
                    }
                }
                return values;
            }
        }
    }

    private abstract class BLEWriteOperation<T> extends BLEOperation {
        private List<T> data;
        private BLEResponseHandler<T> handler;
        private final Class<T> mClass;
        private final int writeType;

        protected abstract void onWrite(List<T> list);

        BLEWriteOperation(Class<T> aClass, BluetoothGattCharacteristic characteristic, int type, List<T> data, int writeType) {
            super(characteristic, type);
            this.mClass = aClass;
            this.data = data;
            this.writeType = writeType;
        }

        public boolean isRead() {
            return false;
        }

        public boolean isNotify() {
            return false;
        }

        public boolean isWrite() {
            return true;
        }

        public void run() {
            write(BluetoothLEint.this.mBluetoothGatt, this.writeType);
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
            if (this.characteristic == characteristic) {
                switch (status) {
                    case 0:
                        onWrite(this.data);
                        if (this.handler != null) {
                            BluetoothLEint.this.mHandler.post(new Runnable() {
                                public void run() {
                                    BLEWriteOperation.this.handler.onWrite(characteristic.getService().getUuid().toString(), characteristic.getUuid().toString(), BLEWriteOperation.this.data);
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        try {
                            Log.e(BluetoothLEint.LOG_TAG, "Error code " + status + " from characteristic " + characteristic.getUuid());
                            break;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            ((List) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid())).remove(this);
                            if (this.needsRemoval && BluetoothLEint.this.runPendingOperation(this)) {
                                this.needsRemoval = false;
                            }
                            Log.d(BluetoothLEint.LOG_TAG, "pendingOperations.size() = " + BluetoothLEint.this.pendingOperations.size());
                            Log.d(BluetoothLEint.LOG_TAG, "pendingOperationsByUuid.size() = " + BluetoothLEint.this.pendingOperationsByUuid.size());
                        }
                }
                ((List) BluetoothLEint.this.pendingOperationsByUuid.get(characteristic.getUuid())).remove(this);
                if (this.needsRemoval && BluetoothLEint.this.runPendingOperation(this)) {
                    this.needsRemoval = false;
                }
                Log.d(BluetoothLEint.LOG_TAG, "pendingOperations.size() = " + BluetoothLEint.this.pendingOperations.size());
                Log.d(BluetoothLEint.LOG_TAG, "pendingOperationsByUuid.size() = " + BluetoothLEint.this.pendingOperationsByUuid.size());
            }
        }

        private void write(BluetoothGatt gatt, int writeType) {
            registerPendingOperation();
            this.characteristic.setWriteType(writeType);
            int i;
            if (this.mClass == String.class) {
                byte[] str = ((String) this.data.get(0)).getBytes();
                int len = str.length > 22 ? 23 : str.length + 1;
                byte[] buffer = new byte[len];
                for (i = 0; i < len - 1; i++) {
                    buffer[i] = str[i];
                }
                buffer[len - 1] = (byte) 0;
                this.characteristic.setValue(buffer);
            } else if (this.mClass == Float.class) {
                contents = new byte[(this.size * this.data.size())];
                i = 0;
                int value;
                if (this.size == 4) {
                    for (Float f : this.data) {
                        value = Float.floatToIntBits(f.floatValue());
                        r6 = i + 1;
                        contents[i] = (byte) (value & 255);
                        i = r6 + 1;
                        contents[r6] = (byte) ((value >> 8) & 255);
                        r6 = i + 1;
                        contents[i] = (byte) ((value >> 16) & 255);
                        i = r6 + 1;
                        contents[r6] = (byte) ((value >> 24) & 255);
                    }
                } else {
                    for (Float f2 : this.data) {
                        value = Float.floatToIntBits(f2.floatValue());
                        value = (((Integer.MIN_VALUE & value) >> 16) | ((((((2139095040 & value) >> 23) + TransportMediator.KEYCODE_MEDIA_PAUSE) - 15) & 31) << 11)) | ((8384512 & value) >> 13);
                        r6 = i + 1;
                        contents[i] = (byte) (value & 255);
                        i = r6 + 1;
                        contents[r6] = (byte) ((value >> 8) & 255);
                    }
                }
                this.characteristic.setValue(contents);
            } else {
                contents = new byte[(this.size * this.data.size())];
                i = 0;
                for (Number n : this.data) {
                    long value2 = n.longValue();
                    int j = 0;
                    while (j < this.size) {
                        r6 = i + 1;
                        contents[i] = (byte) ((int) (255 & value2));
                        value2 >>= 8;
                        j++;
                        i = r6;
                    }
                }
                this.characteristic.setValue(contents);
            }
            gatt.writeCharacteristic(this.characteristic);
        }

        public void write(BluetoothGatt gatt) {
            write(gatt, 1);
        }

        public void writeWithResponse(BluetoothGatt gatt, BLEResponseHandler<T> handler) {
            this.handler = handler;
            write(gatt, 1);
        }
    }

    class BLEReadByteOperation extends BLEReadOperation<Integer> {
        BLEReadByteOperation(BluetoothLEint this$0, BluetoothGattCharacteristic characteristic, boolean signed, BLEResponseHandler<Integer> handler) {
            this(characteristic, signed, handler, false);
        }

        BLEReadByteOperation(BluetoothGattCharacteristic characteristic, boolean signed, BLEResponseHandler<Integer> handler, boolean notify) {
            super(Integer.class, characteristic, signed ? 33 : 17, handler, notify);
        }

        protected void onReceive(List<Integer> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.BytesReceived(BLEReadByteOperation.this.characteristic.getService().getUuid().toString(), BLEReadByteOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEReadFloatOperation extends BLEReadOperation<Float> {
        BLEReadFloatOperation(BluetoothLEint this$0, BluetoothGattCharacteristic characteristic, boolean shortFloat, BLEResponseHandler<Float> handler) {
            this(characteristic, shortFloat, handler, false);
        }

        BLEReadFloatOperation(BluetoothGattCharacteristic characteristic, boolean shortFloat, BLEResponseHandler<Float> handler, boolean notify) {
            super(Float.class, characteristic, shortFloat ? 50 : 52, handler, notify);
        }

        public void onReceive(List<Float> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.FloatsReceived(BLEReadFloatOperation.this.characteristic.getService().getUuid().toString(), BLEReadFloatOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEReadIntegerOperation extends BLEReadOperation<Long> {
        BLEReadIntegerOperation(BluetoothLEint this$0, BluetoothGattCharacteristic characteristic, boolean signed, BLEResponseHandler<Long> handler) {
            this(characteristic, signed, handler, false);
        }

        BLEReadIntegerOperation(BluetoothGattCharacteristic characteristic, boolean signed, BLEResponseHandler<Long> handler, boolean notify) {
            super(Long.class, characteristic, signed ? 36 : 20, handler, notify);
        }

        protected void onReceive(List<Long> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.IntegersReceived(BLEReadIntegerOperation.this.characteristic.getService().getUuid().toString(), BLEReadIntegerOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEReadShortOperation extends BLEReadOperation<Integer> {
        BLEReadShortOperation(BluetoothLEint this$0, BluetoothGattCharacteristic characteristic, boolean signed, BLEResponseHandler<Integer> handler) {
            this(characteristic, signed, handler, false);
        }

        BLEReadShortOperation(BluetoothGattCharacteristic characteristic, boolean signed, BLEResponseHandler<Integer> handler, boolean notify) {
            super(Integer.class, characteristic, signed ? 34 : 18, handler, notify);
        }

        protected void onReceive(List<Integer> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.ShortsReceived(BLEReadShortOperation.this.characteristic.getService().getUuid().toString(), BLEReadShortOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEReadStringOperation extends BLEReadOperation<String> {
        BLEReadStringOperation(BluetoothLEint this$0, BluetoothGattCharacteristic characteristic, boolean utf16, BLEResponseHandler<String> handler) {
            this(characteristic, utf16, handler, false);
        }

        BLEReadStringOperation(BluetoothGattCharacteristic characteristic, boolean utf16, BLEResponseHandler<String> handler, boolean notify) {
            super(String.class, characteristic, utf16 ? 2 : 1, handler, notify);
        }

        public void onReceive(List<String> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.StringsReceived(BLEReadStringOperation.this.characteristic.getService().getUuid().toString(), BLEReadStringOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEWriteBytesOperation extends BLEWriteOperation<Integer> {
        BLEWriteBytesOperation(BluetoothGattCharacteristic characteristic, boolean signed, List<Integer> values, int writeType) {
            super(Integer.class, characteristic, signed ? 33 : 17, values, writeType);
        }

        public void onWrite(List<Integer> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.BytesWritten(BLEWriteBytesOperation.this.characteristic.getService().getUuid().toString(), BLEWriteBytesOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEWriteFloatsOperation extends BLEWriteOperation<Float> {
        BLEWriteFloatsOperation(BluetoothGattCharacteristic characteristic, boolean shortFloats, List<Float> values, int writeType) {
            super(Float.class, characteristic, shortFloats ? 50 : 52, values, writeType);
        }

        public void onWrite(List<Float> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.FloatsWritten(BLEWriteFloatsOperation.this.characteristic.getService().getUuid().toString(), BLEWriteFloatsOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEWriteIntegersOperation extends BLEWriteOperation<Long> {
        BLEWriteIntegersOperation(BluetoothGattCharacteristic characteristic, boolean signed, List<Long> values, int writeType) {
            super(Long.class, characteristic, signed ? 36 : 20, values, writeType);
        }

        public void onWrite(List<Long> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.IntegersWritten(BLEWriteIntegersOperation.this.characteristic.getService().getUuid().toString(), BLEWriteIntegersOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEWriteShortsOperation extends BLEWriteOperation<Integer> {
        BLEWriteShortsOperation(BluetoothGattCharacteristic characteristic, boolean signed, List<Integer> values, int writeType) {
            super(Integer.class, characteristic, signed ? 34 : 18, values, writeType);
        }

        public void onWrite(List<Integer> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.ShortsWritten(BLEWriteShortsOperation.this.characteristic.getService().getUuid().toString(), BLEWriteShortsOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    class BLEWriteStringsOperation extends BLEWriteOperation<String> {
        BLEWriteStringsOperation(BluetoothGattCharacteristic characteristic, boolean utf16, List<String> values, int writeType) {
            super(String.class, characteristic, utf16 ? 2 : 1, values, writeType);
        }

        public void onWrite(List<String> values) {
            final YailList yailList = YailList.makeList((List) values);
            BluetoothLEint.this.mHandler.post(new Runnable() {
                public void run() {
                    BluetoothLEint.this.outer.StringsWritten(BLEWriteStringsOperation.this.characteristic.getService().getUuid().toString(), BLEWriteStringsOperation.this.characteristic.getUuid().toString(), yailList);
                }
            });
        }
    }

    static {
        errorMessages.put(Integer.valueOf(ERROR_BLUETOOTH_LE_NOT_SUPPORTED), "BluetoothLE is not supported on your phone's hardware!");
        errorMessages.put(Integer.valueOf(ERROR_BLUETOOTH_LE_NOT_ENABLED), "BluetoothLE is not enabled!");
        errorMessages.put(Integer.valueOf(ERROR_API_LEVEL_TOO_LOW), "BluetoothLE requires Android 5.0 or newer!");
        errorMessages.put(Integer.valueOf(ERROR_NO_DEVICE_SCAN_IN_PROGRESS), "StopScan cannot be called before StartScan! There is no scan currently in progress.");
        errorMessages.put(Integer.valueOf(ERROR_NOT_CURRENTLY_CONNECTED), "Disconnect cannot be called before you are connected! There is no Bluetooth LE device currently connected.");
        errorMessages.put(Integer.valueOf(ERROR_INDEX_OUT_OF_BOUNDS), "Block %1s attempted to access %2s with an invalid index. Index out of bounds!");
        errorMessages.put(Integer.valueOf(ERROR_DEVICE_LIST_EMPTY), "You cannot connect to a device when the device list is empty! Try scanning again.");
        errorMessages.put(Integer.valueOf(ERROR_INVALID_UUID_CHARACTERS), "%1s UUID string in block %2s contains invalid characters! Try typing it in again and rebuilding your app.");
        errorMessages.put(Integer.valueOf(ERROR_INVALID_UUID_FORMAT), "%1s UUID string in block %2s does not use the proper format! Try typing it in again and rebuilding your app.");
        errorMessages.put(Integer.valueOf(ERROR_ADVERTISEMENTS_NOT_SUPPORTED), "Bluetooth Advertisements not supported!");
    }

    BluetoothLEint(BluetoothLE outer, Activity activity, ComponentContainer container) {
        this.outer = outer;
        this.activity = activity;
        this.container = container;
        this.mLeDevices = new ArrayList();
        this.mGattService = new ArrayList();
        this.gattChars = new ArrayList();
        this.mLeDeviceRssi = new HashMap();
        this.gattMap = new HashMap();
        this.uiThread = new Handler();
        this.mDeviceScanCallback = new C03501();
        this.mGattCallback = new C03512();
        this.mAdvertisementScanCallback = new C03543();
    }

    boolean isScanning() {
        return this.isScanning;
    }

    void StartScanning() {
        new BLEAction<Void>("StartScanning") {
            public Void action() {
                if (!BluetoothLEint.this.mLeDevices.isEmpty()) {
                    BluetoothLEint.this.mLeDevices.clear();
                    BluetoothLEint.this.mLeDeviceRssi.clear();
                }
                BluetoothAdapter btAdapter = obtainBluetoothAdapter();
                if (btAdapter != null) {
                    BluetoothLEint.this.mBluetoothLeDeviceScanner = btAdapter.getBluetoothLeScanner();
                    ScanSettings settings = new Builder().setScanMode(2).build();
                    List<ScanFilter> filters = new ArrayList();
                    filters.add(new ScanFilter.Builder().build());
                    BluetoothLEint.this.mBluetoothLeDeviceScanner.startScan(filters, settings, BluetoothLEint.this.mDeviceScanCallback);
                    Log.i(BluetoothLEint.LOG_TAG, "StartScanning successful.");
                }
                return null;
            }
        }.run();
    }

    void StopScanning() {
        new BLEAction<Void>("StopScanning") {
            public Void action() {
                if (BluetoothLEint.this.mBluetoothLeDeviceScanner != null) {
                    BluetoothLEint.this.mBluetoothLeDeviceScanner.stopScan(BluetoothLEint.this.mDeviceScanCallback);
                    BluetoothLEint.this.isScanning = false;
                    Log.i(BluetoothLEint.LOG_TAG, "StopScanning successful.");
                } else {
                    BluetoothLEint.this.signalError("StopScanning", BluetoothLEint.ERROR_NO_DEVICE_SCAN_IN_PROGRESS, new Object[0]);
                }
                return null;
            }
        }.run();
    }

    void Connect(final int index) {
        if (index < 1 || index > this.mLeDevices.size()) {
            this.outer.ConnectionFailed("Invalid index provided to Connect");
            throw new IndexOutOfBoundsException("Expected device index between 1 and " + this.mLeDevices.size());
        } else {
            new BLEAction<Void>("Connect") {
                public Void action() {
                    try {
                        if (BluetoothLEint.this.mBluetoothLeAdvertisementScanner != null) {
                            BluetoothLEint.this.mBluetoothLeAdvertisementScanner.stopScan(BluetoothLEint.this.mDeviceScanCallback);
                            BluetoothLEint.this.isScanning = false;
                        }
                        if (BluetoothLEint.this.mLeDevices.isEmpty()) {
                            BluetoothLEint.this.outer.ConnectionFailed("Device list was empty");
                            BluetoothLEint.this.signalError("Connect", BluetoothLEint.ERROR_DEVICE_LIST_EMPTY, new Object[0]);
                        } else {
                            BluetoothLEint.this.forceDisconnect();
                            BluetoothLEint.this.mBluetoothGatt = ((BluetoothDevice) BluetoothLEint.this.mLeDevices.get(index - 1)).connectGatt(BluetoothLEint.this.activity, BluetoothLEint.this.autoReconnect, BluetoothLEint.this.mGattCallback);
                            if (BluetoothLEint.this.mBluetoothGatt != null) {
                                BluetoothLEint.this.gattMap.put(((BluetoothDevice) BluetoothLEint.this.mLeDevices.get(index - 1)).toString(), BluetoothLEint.this.mBluetoothGatt);
                                BluetoothLEint.this.scheduleConnectionTimeoutMessage();
                            } else {
                                BluetoothLEint.this.outer.ConnectionFailed("Connect failed to return a BluetoothGatt object");
                                Log.e(BluetoothLEint.LOG_TAG, "Connect failed.");
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        BluetoothLEint.this.outer.ConnectionFailed(e.getMessage());
                        BluetoothLEint.this.signalError("Connect", BluetoothLEint.ERROR_INDEX_OUT_OF_BOUNDS, "Connect", "DeviceList");
                    }
                    return null;
                }
            }.run();
        }
    }

    void ConnectWithAddress(final String address) {
        new BLEAction<Void>("ConnectWithAddress") {
            public Void action() {
                try {
                    if (BluetoothLEint.this.mBluetoothLeAdvertisementScanner != null) {
                        BluetoothLEint.this.mBluetoothLeAdvertisementScanner.stopScan(BluetoothLEint.this.mDeviceScanCallback);
                        BluetoothLEint.this.isScanning = false;
                    }
                    if (BluetoothLEint.this.mLeDevices.isEmpty()) {
                        BluetoothLEint.this.outer.ConnectionFailed("Device list was empty");
                        BluetoothLEint.this.signalError("ConnectWithAddress", BluetoothLEint.ERROR_DEVICE_LIST_EMPTY, new Object[0]);
                    } else {
                        for (BluetoothDevice bluetoothDevice : BluetoothLEint.this.mLeDevices) {
                            if (bluetoothDevice.getAddress().equals(address)) {
                                BluetoothLEint.this.forceDisconnect();
                                BluetoothLEint.this.mBluetoothGatt = bluetoothDevice.connectGatt(BluetoothLEint.this.activity, BluetoothLEint.this.autoReconnect, BluetoothLEint.this.mGattCallback);
                                if (BluetoothLEint.this.mBluetoothGatt != null) {
                                    BluetoothLEint.this.gattMap.put(bluetoothDevice.getAddress(), BluetoothLEint.this.mBluetoothGatt);
                                    BluetoothLEint.this.scheduleConnectionTimeoutMessage();
                                    Log.i(BluetoothLEint.LOG_TAG, "ConnectWithAddress successful.");
                                    break;
                                }
                                BluetoothLEint.this.outer.ConnectionFailed("Connect failed to return a BluetoothGatt object");
                                Log.e(BluetoothLEint.LOG_TAG, "ConnectWithAddress failed.");
                            }
                        }
                        BluetoothLEint.this.outer.ConnectionFailed("No device found with address " + address);
                    }
                } catch (IndexOutOfBoundsException e) {
                    BluetoothLEint.this.outer.ConnectionFailed(e.getMessage());
                    BluetoothLEint.this.signalError("ConnectWithAddress", BluetoothLEint.ERROR_INDEX_OUT_OF_BOUNDS, "ConnectWithAddress", "DeviceList");
                }
                return null;
            }
        }.run();
    }

    void Disconnect() {
        new BLEAction<Void>("Disconnect") {
            public Void action() {
                if (BluetoothLEint.this.mBluetoothGatt != null) {
                    BluetoothLEint.this.isUserDisconnect = true;
                    BluetoothLEint.this.mBluetoothGatt.disconnect();
                } else {
                    BluetoothLEint.this.signalError("Disconnect", BluetoothLEint.ERROR_NOT_CURRENTLY_CONNECTED, new Object[0]);
                }
                return null;
            }
        }.run();
    }

    void DisconnectWithAddress(final String address) {
        new BLEAction<Void>("DisconnectWithAddress") {
            public Void action() {
                if (BluetoothLEint.this.gattMap.containsKey(address)) {
                    ((BluetoothGatt) BluetoothLEint.this.gattMap.get(address)).disconnect();
                    BluetoothLEint.this.gattMap.remove(address);
                    Log.i(BluetoothLEint.LOG_TAG, "Disconnect successful.");
                } else {
                    Log.e(BluetoothLEint.LOG_TAG, "Disconnect failed. No such address in the list.");
                }
                return null;
            }
        }.run();
    }

    void ReadByteValues(String serviceUuid, String characteristicUuid, boolean signed) {
        ReadByteValues(serviceUuid, characteristicUuid, signed, null);
    }

    void ReadByteValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Integer> callback) {
        String METHOD = "ReadByteValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final BLEResponseHandler<Integer> bLEResponseHandler = callback;
        new BLEAction<Void>("ReadByteValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "ReadByteValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "ReadByteValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEReadByteOperation(BluetoothLEint.this, BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler));
                }
                return null;
            }
        }.run();
    }

    void RegisterForByteValues(String serviceUuid, String characteristicUuid, boolean signed) {
        RegisterForByteValues(serviceUuid, characteristicUuid, signed, null);
    }

    void RegisterForByteValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Integer> callback) {
        String METHOD = "RegisterForByteValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final BLEResponseHandler<Integer> bLEResponseHandler = callback;
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                new BLEAction<Void>("RegisterForByteValues") {
                    public Void action() {
                        if (BluetoothLEint.this.validateUUID(str, "Service", "RegisterForByteValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "RegisterForByteValues")) {
                            BluetoothLEint.this.schedulePendingOperation(new BLEReadByteOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler, true));
                        }
                        return null;
                    }
                }.run();
            }
        }, 1);
    }

    void WriteByteValues(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values) {
        String METHOD = "WriteByteValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final List<Integer> list = values;
        new BLEAction<Void>("WriteByteValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteByteValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteByteValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteBytesOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 1));
                }
                return null;
            }
        }.run();
    }

    void WriteByteValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values) {
        WriteByteValuesWithResponse(serviceUuid, characteristicUuid, signed, values, null);
    }

    void WriteByteValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values, BLEResponseHandler<Integer> bLEResponseHandler) {
        String METHOD = "WriteByteValuesWithResponse";
        Log.d(LOG_TAG, "WriteByteValuesWithResponse: " + values);
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final List<Integer> list = values;
        new BLEAction<Void>("WriteByteValuesWithResponse") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteByteValuesWithResponse") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteByteValuesWithResponse")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteBytesOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 2));
                }
                return null;
            }
        }.run();
    }

    void ReadShortValues(String serviceUuid, String characteristicUuid, boolean signed) {
        ReadShortValues(serviceUuid, characteristicUuid, signed, null);
    }

    void ReadShortValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Integer> callback) {
        String METHOD = "ReadShortValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final BLEResponseHandler<Integer> bLEResponseHandler = callback;
        new BLEAction<Void>("ReadShortValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "ReadShortValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "ReadShortValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEReadShortOperation(BluetoothLEint.this, BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler));
                }
                return null;
            }
        }.run();
    }

    void RegisterForShortValues(String serviceUuid, String characteristicUuid, boolean signed) {
        RegisterForShortValues(serviceUuid, characteristicUuid, signed, null);
    }

    void RegisterForShortValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Integer> callback) {
        String METHOD = "RegisterForShortValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final BLEResponseHandler<Integer> bLEResponseHandler = callback;
        new BLEAction<Void>("RegisterForShortValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "RegisterForShortValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "RegisterForShortValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEReadShortOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler, true));
                }
                return null;
            }
        }.run();
    }

    void WriteShortValues(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values) {
        String METHOD = "WriteShortValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final List<Integer> list = values;
        new BLEAction<Void>("WriteShortValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteShortValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteShortValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteShortsOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 1));
                }
                return null;
            }
        }.run();
    }

    void WriteShortValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values) {
        WriteShortValuesWithResponse(serviceUuid, characteristicUuid, signed, values, null);
    }

    void WriteShortValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Integer> values, BLEResponseHandler<Integer> bLEResponseHandler) {
        String METHOD = "WriteShortValuesWithResponse";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final List<Integer> list = values;
        new BLEAction<Void>("WriteShortValuesWithResponse") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteShortValuesWithResponse") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteShortValuesWithResponse")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteShortsOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 2));
                }
                return null;
            }
        }.run();
    }

    void ReadIntegerValues(String serviceUuid, String characteristicUuid, boolean signed) {
        ReadIntegerValues(serviceUuid, characteristicUuid, signed, null);
    }

    void ReadIntegerValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Long> handler) {
        String METHOD = "ReadIntegerValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final BLEResponseHandler<Long> bLEResponseHandler = handler;
        new BLEAction<Void>("ReadIntegerValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "ReadIntegerValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "ReadIntegerValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEReadIntegerOperation(BluetoothLEint.this, BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler));
                }
                return null;
            }
        }.run();
    }

    void RegisterForIntegerValues(String serviceUuid, String characteristicUuid, boolean signed) {
        RegisterForIntegerValues(serviceUuid, characteristicUuid, signed, null);
    }

    void RegisterForIntegerValues(String serviceUuid, String characteristicUuid, boolean signed, BLEResponseHandler<Long> handler) {
        String METHOD = "RegisterForIntegerValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final BLEResponseHandler<Long> bLEResponseHandler = handler;
        new BLEAction<Void>("RegisterForIntegerValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "RegisterForIntegerValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "RegisterForIntegerValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEReadIntegerOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler, true));
                }
                return null;
            }
        }.run();
    }

    void WriteIntegerValues(String serviceUuid, String characteristicUuid, boolean signed, List<Long> values) {
        String METHOD = "WriteIntegerValuesWithResponse";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final List<Long> list = values;
        new BLEAction<Void>("WriteIntegerValuesWithResponse") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteIntegerValuesWithResponse") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteIntegerValuesWithResponse")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteIntegersOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 1));
                }
                return null;
            }
        }.run();
    }

    void WriteIntegerValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Long> values) {
        WriteIntegerValuesWithResponse(serviceUuid, characteristicUuid, signed, values, null);
    }

    void WriteIntegerValuesWithResponse(String serviceUuid, String characteristicUuid, boolean signed, List<Long> values, BLEResponseHandler<Long> bLEResponseHandler) {
        String METHOD = "WriteIntegerValuesWithResponse";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = signed;
        final List<Long> list = values;
        new BLEAction<Void>("WriteIntegerValuesWithResponse") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteIntegerValuesWithResponse") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteIntegerValuesWithResponse")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteIntegersOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 2));
                }
                return null;
            }
        }.run();
    }

    void ReadFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloat) {
        ReadFloatValues(serviceUuid, characteristicUuid, shortFloat, null);
    }

    void ReadFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloat, BLEResponseHandler<Float> callback) {
        String METHOD = "ReadFloatValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = shortFloat;
        final BLEResponseHandler<Float> bLEResponseHandler = callback;
        new BLEAction<Void>("ReadFloatValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "ReadFloatValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "ReadFloatValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEReadFloatOperation(BluetoothLEint.this, BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler));
                }
                return null;
            }
        }.run();
    }

    void RegisterForFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloat) {
        RegisterForFloatValues(serviceUuid, characteristicUuid, shortFloat, null);
    }

    void RegisterForFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloat, BLEResponseHandler<Float> callback) {
        String METHOD = "RegisterForFloatValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = shortFloat;
        final BLEResponseHandler<Float> bLEResponseHandler = callback;
        new BLEAction<Void>("RegisterForFloatValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "RegisterForFloatValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "RegisterForFloatValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEReadFloatOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler, true));
                }
                return null;
            }
        }.run();
    }

    void WriteFloatValues(String serviceUuid, String characteristicUuid, boolean shortFloat, List<Float> values) {
        String METHOD = "WriteFloatValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = shortFloat;
        final List<Float> list = values;
        new BLEAction<Void>("WriteFloatValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteFloatValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteFloatValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteFloatsOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 1));
                }
                return null;
            }
        }.run();
    }

    void WriteFloatValuesWithResponse(String serviceUuid, String characteristicUuid, boolean shortFloat, List<Float> values) {
        WriteFloatValuesWithResponse(serviceUuid, characteristicUuid, shortFloat, values, null);
    }

    void WriteFloatValuesWithResponse(String serviceUuid, String characteristicUuid, boolean shortFloat, List<Float> values, BLEResponseHandler<Float> bLEResponseHandler) {
        String METHOD = "WriteFloatValuesWithResponse";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = shortFloat;
        final List<Float> list = values;
        new BLEAction<Void>("WriteFloatValuesWithResponse") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteFloatValuesWithResponse") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteFloatValuesWithResponse")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteFloatsOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 2));
                }
                return null;
            }
        }.run();
    }

    void ReadStringValues(String serviceUuid, String characteristicUuid, boolean utf16) {
        ReadStringValues(serviceUuid, characteristicUuid, utf16, null);
    }

    void ReadStringValues(String serviceUuid, String characteristicUuid, boolean utf16, BLEResponseHandler<String> handler) {
        String METHOD = "ReadStringValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = utf16;
        final BLEResponseHandler<String> bLEResponseHandler = handler;
        new BLEAction<Void>("ReadStringValues") {
            public Void action() {
                BluetoothLEint.this.schedulePendingOperation(new BLEReadStringOperation(BluetoothLEint.this, BluetoothLEint.this.findMGattChar(BluetoothLEint.bleStringToUuid(str), BluetoothLEint.bleStringToUuid(str2)), z, bLEResponseHandler));
                return null;
            }
        }.run();
    }

    void RegisterForStringValues(String serviceUuid, String characteristicUuid, boolean utf16) {
        RegisterForStringValues(serviceUuid, characteristicUuid, utf16, null);
    }

    void RegisterForStringValues(String serviceUuid, String characteristicUuid, boolean utf16, BLEResponseHandler<String> handler) {
        String METHOD = "RegisterForStringValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = utf16;
        final BLEResponseHandler<String> bLEResponseHandler = handler;
        new BLEAction<Void>("RegisterForStringValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "RegisterForStringValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "RegisterForStringValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEReadStringOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, bLEResponseHandler, true));
                }
                return null;
            }
        }.run();
    }

    void WriteStringValues(String serviceUuid, String characteristicUuid, boolean utf16, List<String> values) {
        String METHOD = "WriteStringValues";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = utf16;
        final List<String> list = values;
        new BLEAction<Void>("WriteStringValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteStringValues") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteStringValues")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteStringsOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 1));
                }
                return null;
            }
        }.run();
    }

    void WriteStringValuesWithResponse(String serviceUuid, String characteristicUuid, boolean utf16, List<String> values) {
        WriteStringValuesWithResponse(serviceUuid, characteristicUuid, utf16, values, null);
    }

    void WriteStringValuesWithResponse(String serviceUuid, String characteristicUuid, boolean utf16, List<String> values, BLEResponseHandler<String> bLEResponseHandler) {
        String METHOD = "WriteStringValuesWithResponse";
        final String str = serviceUuid;
        final String str2 = characteristicUuid;
        final boolean z = utf16;
        final List<String> list = values;
        new BLEAction<Void>("WriteStringValuesWithResponse") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(str, "Service", "WriteStringValuesWithResponse") && BluetoothLEint.this.validateUUID(str2, "Characteristic", "WriteStringValuesWithResponse")) {
                    BluetoothLEint.this.schedulePendingOperation(new BLEWriteStringsOperation(BluetoothLEint.this.findMGattChar(UUID.fromString(str), UUID.fromString(str2)), z, list, 2));
                }
                return null;
            }
        }.run();
    }

    void UnregisterForValues(String serviceUuid, String characteristicUuid) {
        UnregisterForValues(serviceUuid, characteristicUuid, null);
    }

    void UnregisterForValues(final String serviceUuid, final String characteristicUuid, BLEResponseHandler<?> bLEResponseHandler) {
        String METHOD = "UnsubscribeForValues";
        new BLEAction<Void>("UnsubscribeForValues") {
            public Void action() {
                if (BluetoothLEint.this.validateUUID(serviceUuid, "Service", "UnsubscribeForValues") && BluetoothLEint.this.validateUUID(characteristicUuid, "Characteristic", "UnsubscribeForValues")) {
                    List<BLEOperation> operations = (List) BluetoothLEint.this.pendingOperationsByUuid.get(UUID.fromString(characteristicUuid));
                    if (operations != null) {
                        for (BLEOperation operation : new ArrayList(operations)) {
                            if (operation.isNotify()) {
                                operation.unsubscribe(BluetoothLEint.this.mBluetoothGatt);
                            }
                        }
                    }
                }
                return null;
            }
        }.run();
    }

    int FoundDeviceRssi(final int index) {
        if (index < 1 || index > this.mLeDevices.size()) {
            throw new IllegalArgumentException("Expected device index between 1 and " + this.mLeDevices.size());
        }
        Integer result = (Integer) new BLEAction<Integer>("FoundDeviceRssi") {
            public Integer action() {
                if (index <= BluetoothLEint.this.mLeDevices.size()) {
                    try {
                        if (!BluetoothLEint.this.mLeDevices.isEmpty()) {
                            return (Integer) BluetoothLEint.this.mLeDeviceRssi.get(BluetoothLEint.this.mLeDevices.get(index - 1));
                        }
                        BluetoothLEint.this.signalError("FoundDeviceRssi", BluetoothLEint.ERROR_DEVICE_LIST_EMPTY, new Object[0]);
                    } catch (IndexOutOfBoundsException e) {
                        BluetoothLEint.this.signalError("FoundDeviceRssi", BluetoothLEint.ERROR_INDEX_OUT_OF_BOUNDS, "FoundDeviceRssi", "DeviceList");
                    }
                }
                return null;
            }
        }.run();
        return result != null ? result.intValue() : -1;
    }

    String FoundDeviceName(int index) {
        if (index < 1 || index > this.mLeDevices.size()) {
            throw new IllegalArgumentException("Expected device index between 1 and " + this.mLeDevices.size());
        } else if (index <= this.mLeDevices.size()) {
            Log.i(LOG_TAG, "Device Name is found");
            return ((BluetoothDevice) this.mLeDevices.get(index - 1)).getName();
        } else {
            Log.e(LOG_TAG, "Device Name isn't found");
            return null;
        }
    }

    String FoundDeviceAddress(int index) {
        if (index < 1 || index > this.mLeDevices.size()) {
            throw new IllegalArgumentException("Expected device index between 1 and " + this.mLeDevices.size());
        } else if (index <= this.mLeDevices.size()) {
            Log.i(LOG_TAG, "Device Address is found");
            return ((BluetoothDevice) this.mLeDevices.get(index - 1)).getAddress();
        } else {
            Log.e(LOG_TAG, "Device Address is found");
            return "";
        }
    }

    String ConnectedDeviceName() {
        if (this.isConnected && this.mBluetoothGatt != null) {
            BluetoothDevice device = this.mBluetoothGatt.getDevice();
            if (device != null) {
                return device.getName();
            }
        }
        return "";
    }

    void StartAdvertising(final String inData, final String serviceUuid) {
        new BLEAction<Void>("StartAdvertising") {

            class C03521 extends AdvertiseCallback {
                C03521() {
                }

                public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                    BluetoothLEint.this.isAdvertising = true;
                    super.onStartSuccess(settingsInEffect);
                }

                public void onStartFailure(int errorCode) {
                    Log.e(BluetoothLEint.LOG_TAG, "Advertising onStartFailure: " + errorCode);
                    super.onStartFailure(errorCode);
                }
            }

            public Void action() {
                BluetoothAdapter btAdapter = obtainBluetoothAdapter();
                if (!btAdapter.isMultipleAdvertisementSupported()) {
                    Log.i(BluetoothLEint.LOG_TAG, "Adapter does not support Bluetooth Advertisements.");
                    BluetoothLEint.this.signalError("StartAdvertising", BluetoothLEint.ERROR_ADVERTISEMENTS_NOT_SUPPORTED, new Object[0]);
                } else if (BluetoothLEint.this.validateUUID(serviceUuid, "Service", "StartAdvertising")) {
                    BluetoothLEint.this.mBluetoothLeAdvertiser = btAdapter.getBluetoothLeAdvertiser();
                    AdvertiseCallback advertisingCallback = new C03521();
                    AdvertiseSettings advSettings = new AdvertiseSettings.Builder().setAdvertiseMode(2).setTxPowerLevel(3).setConnectable(false).build();
                    ParcelUuid pUuid = new ParcelUuid(UUID.fromString(serviceUuid));
                    AdvertiseData advData = new AdvertiseData.Builder().setIncludeDeviceName(true).addServiceUuid(pUuid).addServiceData(pUuid, inData.getBytes(Charset.forName("UTF-8"))).build();
                    if (BluetoothLEint.this.mAdvertiseCallback == null) {
                        BluetoothLEint.this.mAdvertiseCallback = advertisingCallback;
                        if (BluetoothLEint.this.mBluetoothLeAdvertiser != null) {
                            BluetoothLEint.this.mBluetoothLeAdvertiser.startAdvertising(advSettings, advData, BluetoothLEint.this.mAdvertiseCallback);
                        }
                    }
                    Log.i(BluetoothLEint.LOG_TAG, "StartScanningAdvertisements Successfully.");
                }
                return null;
            }
        }.run();
    }

    void StopAdvertising() {
        new BLEAction<Void>("StopAdvertising") {
            public Void action() {
                Log.i(BluetoothLEint.LOG_TAG, "Stopping BLE Advertising");
                if (BluetoothLEint.this.mBluetoothLeAdvertiser != null) {
                    BluetoothLEint.this.mBluetoothLeAdvertiser.stopAdvertising(BluetoothLEint.this.mAdvertiseCallback);
                    BluetoothLEint.this.isAdvertising = false;
                    BluetoothLEint.this.mAdvertiseCallback = null;
                }
                return null;
            }
        }.run();
    }

    void ScanAdvertisements(final long scanPeriod) {
        new BLEAction<Void>("ScanAdvertisements") {

            class C03531 implements Runnable {
                C03531() {
                }

                public void run() {
                    BluetoothLEint.this.StopScanningAdvertisements();
                }
            }

            public Void action() {
                BluetoothLEint.this.SCAN_PERIOD = scanPeriod;
                BluetoothLEint.this.advertiserAddresses = new ArrayList();
                BluetoothLEint.this.scannedAdvertisers = new HashMap();
                BluetoothLEint.this.scannedAdvertiserNames = new ArrayList();
                BluetoothLEint.this.nameToAddress = new HashMap();
                BluetoothLEint.this.uiThread.postDelayed(new C03531(), scanPeriod);
                BluetoothAdapter btAdapter = obtainBluetoothAdapter();
                if (btAdapter != null) {
                    BluetoothLEint.this.mBluetoothLeAdvertisementScanner = btAdapter.getBluetoothLeScanner();
                    if (BluetoothLEint.this.mAdvertisementScanCallback == null) {
                        Log.i(BluetoothLEint.LOG_TAG, "mAdvertisementScanCallback is null.");
                    } else if (BluetoothLEint.this.mBluetoothLeAdvertisementScanner != null) {
                        ScanSettings settings = new Builder().setScanMode(2).build();
                        List<ScanFilter> filters = new ArrayList();
                        filters.add(new ScanFilter.Builder().build());
                        if (settings != null) {
                            BluetoothLEint.this.mBluetoothLeAdvertisementScanner.startScan(filters, settings, BluetoothLEint.this.mAdvertisementScanCallback);
                        } else {
                            Log.i(BluetoothLEint.LOG_TAG, "settings or filters are null.");
                        }
                    } else {
                        Log.i(BluetoothLEint.LOG_TAG, "Bluetooth LE scanner is null.");
                    }
                } else {
                    Log.i(BluetoothLEint.LOG_TAG, "No bluetooth adapter found.");
                }
                return null;
            }
        }.run();
    }

    void StopScanningAdvertisements() {
        new BLEAction<Void>("StopScanningAdvertisements") {
            public Void action() {
                Log.i(BluetoothLEint.LOG_TAG, "Stopping BLE Advertisement Scan.");
                BluetoothLEint.this.mBluetoothLeAdvertisementScanner.stopScan(BluetoothLEint.this.mAdvertisementScanCallback);
                return null;
            }
        }.run();
    }

    String GetAdvertisementData(String deviceAddress, String serviceUuid) {
        if (validateUUID(serviceUuid, "Service", "GetAdvertisementData")) {
            return "" + Arrays.toString((byte[]) ((ScanResult) this.scannedAdvertisers.get(deviceAddress)).getScanRecord().getServiceData().get(ParcelUuid.fromString(serviceUuid)));
        }
        return "";
    }

    String GetAdvertiserAddress(String deviceName) {
        return (String) this.nameToAddress.get(deviceName);
    }

    List<String> GetAdvertiserServiceUuids(String deviceAddress) {
        return BLEUtil.stringifyParcelUuids(((ScanResult) this.scannedAdvertisers.get(deviceAddress)).getScanRecord().getServiceUuids());
    }

    String BatteryValue() {
        if (this.isCharRead) {
            return Integer.toString(this.battery);
        }
        return "Cannot Read Battery Level";
    }

    int TxPower() {
        return this.txPower;
    }

    boolean IsDeviceConnected() {
        return this.isConnected;
    }

    String DeviceList() {
        this.deviceInfoList = "";
        this.mLeDevices = sortDeviceList(this.mLeDevices);
        if (!this.mLeDevices.isEmpty()) {
            for (int i = 0; i < this.mLeDevices.size(); i++) {
                if (i != this.mLeDevices.size() - 1) {
                    this.deviceInfoList += ((BluetoothDevice) this.mLeDevices.get(i)).getAddress() + " " + ((BluetoothDevice) this.mLeDevices.get(i)).getName() + " " + Integer.toString(((Integer) this.mLeDeviceRssi.get(this.mLeDevices.get(i))).intValue()) + ",";
                } else {
                    this.deviceInfoList += ((BluetoothDevice) this.mLeDevices.get(i)).getAddress() + " " + ((BluetoothDevice) this.mLeDevices.get(i)).getName() + " " + Integer.toString(((Integer) this.mLeDeviceRssi.get(this.mLeDevices.get(i))).intValue());
                }
            }
        }
        return this.deviceInfoList;
    }

    int ConnectedDeviceRssi() {
        return this.device_rssi;
    }

    long AdvertisementScanPeriod() {
        return this.SCAN_PERIOD;
    }

    List<String> GetAdvertiserNames() {
        return this.scannedAdvertiserNames;
    }

    List<String> GetAdvertiserAddresses() {
        return this.advertiserAddresses;
    }

    boolean IsDeviceAdvertising() {
        return this.isAdvertising;
    }

    private void Connected() {
        this.uiThread.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(BluetoothLEint.this.outer, "Connected", new Object[0]);
                for (BluetoothConnectionListener listener : new HashSet(BluetoothLEint.this.outer.connectionListeners)) {
                    listener.onConnected(BluetoothLEint.this.outer);
                }
            }
        });
    }

    private void Disconnected() {
        this.uiThread.post(new Runnable() {
            public void run() {
                try {
                    for (BluetoothConnectionListener listener : new HashSet(BluetoothLEint.this.outer.connectionListeners)) {
                        listener.onDisconnected(BluetoothLEint.this.outer);
                    }
                    EventDispatcher.dispatchEvent(BluetoothLEint.this.outer, "Disconnected", new Object[0]);
                } catch (Throwable th) {
                    EventDispatcher.dispatchEvent(BluetoothLEint.this.outer, "Disconnected", new Object[0]);
                }
            }
        });
    }

    private void RssiChanged(final int device_rssi) {
        this.uiThread.postDelayed(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(BluetoothLEint.this.outer, "RssiChanged", Integer.valueOf(device_rssi));
            }
        }, 1000);
    }

    private void DeviceFound() {
        this.uiThread.post(new Runnable() {
            public void run() {
                EventDispatcher.dispatchEvent(BluetoothLEint.this.outer, "DeviceFound", new Object[0]);
            }
        });
    }

    String GetSupportedServices() {
        if (this.mGattService == null) {
            return ",";
        }
        this.serviceUUIDList = ", ";
        for (int i = 0; i < this.mGattService.size(); i++) {
            if (i == 0) {
                this.serviceUUIDList = "";
            }
            UUID serviceUUID = ((BluetoothGattService) this.mGattService.get(i)).getUuid();
            this.serviceUUIDList += serviceUUID + " " + BluetoothLEGattAttributes.lookup(serviceUUID, UNKNOWN_SERVICE_NAME) + ",";
        }
        return this.serviceUUIDList;
    }

    YailList getSupportedServicesList() {
        if (this.mGattService == null) {
            return YailList.makeEmptyList();
        }
        YailList result = YailList.makeEmptyList();
        for (BluetoothGattService service : this.mGattService) {
            UUID serviceUuid = service.getUuid();
            result.add(YailList.makeList(new Object[]{serviceUuid.toString(), BluetoothLEGattAttributes.lookup(serviceUuid, UNKNOWN_SERVICE_NAME)}));
        }
        return result;
    }

    String GetServiceByIndex(int index) {
        if (index >= 1 && index <= this.mGattService.size()) {
            return ((BluetoothGattService) this.mGattService.get(index - 1)).getUuid().toString();
        }
        throw new IndexOutOfBoundsException("Expected service index between 1 and " + this.mGattService.size());
    }

    String GetSupportedCharacteristics() {
        if (this.mGattService == null) {
            return ",";
        }
        this.charUUIDList = ", ";
        for (int i = 0; i < this.mGattService.size(); i++) {
            if (i == 0) {
                this.charUUIDList = "";
            }
            for (BluetoothGattCharacteristic characteristic : ((BluetoothGattService) this.mGattService.get(i)).getCharacteristics()) {
                this.gattChars.add(characteristic);
            }
        }
        String unknownCharString = UNKNOWN_CHAR_NAME;
        Iterator it = this.gattChars.iterator();
        while (it.hasNext()) {
            UUID charUUID = ((BluetoothGattCharacteristic) it.next()).getUuid();
            this.charUUIDList += charUUID + " " + BluetoothLEGattAttributes.lookup(charUUID, unknownCharString) + ",";
        }
        return this.charUUIDList;
    }

    YailList getSupportedCharacteristicsList() {
        if (this.mGattService == null) {
            return YailList.makeEmptyList();
        }
        YailList result = YailList.makeEmptyList();
        for (BluetoothGattService service : this.mGattService) {
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                UUID serviceUuid = service.getUuid();
                UUID characteristicUuid = characteristic.getUuid();
                result.add(YailList.makeList(new Object[]{serviceUuid.toString(), characteristicUuid.toString(), BluetoothLEGattAttributes.lookup(characteristicUuid, UNKNOWN_CHAR_NAME)}));
            }
        }
        return result;
    }

    YailList GetCharacteristicsForService(String serviceUuid) {
        if (this.mGattService == null) {
            return YailList.makeEmptyList();
        }
        List result = new ArrayList();
        String unknownCharString = UNKNOWN_CHAR_NAME;
        try {
            BluetoothGattService service = this.mBluetoothGatt.getService(UUID.fromString(serviceUuid));
            if (service == null) {
                throw new IllegalArgumentException("Device does not advertise service " + serviceUuid);
            }
            for (BluetoothGattCharacteristic c : service.getCharacteristics()) {
                result.add(YailList.makeList(new Object[]{c.getUuid().toString().toUpperCase(), BluetoothLEGattAttributes.lookup(c.getUuid(), UNKNOWN_CHAR_NAME)}));
            }
            return YailList.makeList(result);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception while looking up BluetoothLE service", e);
            throw new RuntimeException("Exception while looking up BluetoothLE service: " + e.getMessage(), e);
        }
    }

    String GetCharacteristicByIndex(int index) {
        if (index >= 1 && index <= this.gattChars.size()) {
            return ((BluetoothGattCharacteristic) this.gattChars.get(index - 1)).getUuid().toString();
        }
        throw new IndexOutOfBoundsException("Expected characteristic index between 1 and " + this.gattChars.size());
    }

    boolean isServicePublished(String serviceUuid) {
        return (this.mBluetoothGatt == null || this.mBluetoothGatt.getService(bleStringToUuid(serviceUuid)) == null) ? false : true;
    }

    boolean isCharacteristicPublished(String serviceUuid, String characteristicUuid) {
        UUID service = bleStringToUuid(serviceUuid);
        UUID characteristic = bleStringToUuid(characteristicUuid);
        if (this.mBluetoothGatt == null) {
            return false;
        }
        BluetoothGattService gattService = this.mBluetoothGatt.getService(service);
        if (gattService == null || gattService.getCharacteristic(characteristic) == null) {
            return false;
        }
        return true;
    }

    void setConnectionTimeout(int timeout) {
        this.connectionTimeout = timeout;
    }

    int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    void setAutoReconnect(boolean autoReconnect) {
        this.autoReconnect = autoReconnect;
    }

    boolean getAutoReconnect() {
        return this.autoReconnect;
    }

    private void signalError(final String functionName, final int errorNumber, Object... messageArgs) {
        final String errorMessage = String.format((String) errorMessages.get(Integer.valueOf(errorNumber)), messageArgs);
        Log.e(LOG_TAG, errorMessage);
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                BluetoothLEint.this.container.$form().dispatchErrorOccurredEvent(BluetoothLEint.this.outer, functionName, ErrorMessages.ERROR_EXTENSION_ERROR, Integer.valueOf(errorNumber), "BluetoothLE", errorMessage);
            }
        });
    }

    private boolean validateUUID(String UUID, String type, String callerBlock) {
        if (BLEUtil.hasValidUUIDFormat(UUID)) {
            return true;
        }
        if (BLEUtil.hasInvalidUUIDChars(UUID)) {
            signalError(callerBlock, ERROR_INVALID_UUID_CHARACTERS, type, callerBlock);
            return false;
        }
        signalError(callerBlock, ERROR_INVALID_UUID_FORMAT, type, callerBlock);
        return false;
    }

    private List<BluetoothDevice> sortDeviceList(List<BluetoothDevice> deviceList) {
        Collections.sort(deviceList, new Comparator<BluetoothDevice>() {
            public int compare(BluetoothDevice device1, BluetoothDevice device2) {
                return ((Integer) BluetoothLEint.this.mLeDeviceRssi.get(device2)).intValue() - ((Integer) BluetoothLEint.this.mLeDeviceRssi.get(device1)).intValue();
            }
        });
        return deviceList;
    }

    private void addDevice(BluetoothDevice device, int rssi) {
        if (this.mLeDevices.contains(device)) {
            this.mLeDeviceRssi.put(device, Integer.valueOf(rssi));
        } else {
            this.mLeDevices.add(device);
            this.mLeDeviceRssi.put(device, Integer.valueOf(rssi));
            DeviceFound();
        }
        RssiChanged(rssi);
    }

    private BluetoothGattCharacteristic findMGattChar(UUID ser_uuid, UUID char_uuid) {
        Log.i(LOG_TAG, "isServiceRead: " + this.isServiceRead);
        Log.i(LOG_TAG, "mGattService.isEmpty(): " + this.mGattService.isEmpty());
        if (this.isServiceRead) {
            if (!this.mGattService.isEmpty()) {
                for (BluetoothGattService aMGattService : this.mGattService) {
                    if (aMGattService.getUuid().equals(ser_uuid)) {
                        return aMGattService.getCharacteristic(char_uuid);
                    }
                }
            }
            throw new IllegalStateException("Service " + ser_uuid + ", characteristic " + char_uuid + " are not published by the connected device.");
        }
        throw new IllegalStateException("Not connected to a Bluetooth low energy device.");
    }

    private void readChar(UUID ser_uuid, UUID char_uuid) {
        this.mGattChar = findMGattChar(ser_uuid, char_uuid);
        if (this.mGattChar != null) {
            Log.i(LOG_TAG, "mGattChar initialized to " + this.mGattChar.getUuid().toString());
            BluetoothGattDescriptor desc = this.mGattChar.getDescriptor(BluetoothLEGattAttributes.CLIENT_CHARACTERISTIC_CONFIGURATION);
            if (desc != null) {
                if ((this.mGattChar.getProperties() & 32) != 0) {
                    desc.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                } else {
                    desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                }
                this.mBluetoothGatt.writeDescriptor(desc);
            }
            this.mBluetoothGatt.setCharacteristicNotification(this.mGattChar, true);
            this.isCharRead = this.mBluetoothGatt.readCharacteristic(this.mGattChar);
        } else {
            Log.i(LOG_TAG, "mGattChar is null!");
        }
        if (this.isCharRead) {
            Log.i(LOG_TAG, "Read Character Successfully.");
        } else {
            Log.e(LOG_TAG, "Read Character Fail.");
        }
    }

    private void writeChar(UUID ser_uuid, UUID char_uuid, Object payload) {
        this.mGattChar = findMGattChar(ser_uuid, char_uuid);
        if (this.mGattChar == null || payload == null) {
            Log.i(LOG_TAG, "mGattChar is null!");
        } else {
            Log.i(LOG_TAG, "mGattChar initialized to " + this.mGattChar.getUuid().toString());
            if (payload instanceof int[]) {
                int[] args = (int[]) payload;
                this.mGattChar.setValue(args[0], args[1], args[2]);
            } else if (payload instanceof byte[]) {
                this.mGattChar.setValue((byte[]) payload);
            } else if (payload instanceof String) {
                this.mGattChar.setValue((String) payload);
            } else {
                throw new IllegalArgumentError("Attempted to write to characteristic with unsupported data type.");
            }
            this.isCharWritten = this.mBluetoothGatt.writeCharacteristic(this.mGattChar);
        }
        if (this.isCharWritten) {
            Log.i(LOG_TAG, "Write Gatt Characteristic Successfully");
        } else {
            Log.e(LOG_TAG, "Write Gatt Characteristic Fail");
        }
    }

    private void schedulePendingOperation(BLEOperation operation) {
        synchronized (this.pendingOperations) {
            this.pendingOperations.add(operation);
            if (this.pendingOperations.size() == 1) {
                this.mHandler.post(operation);
            }
        }
    }

    private boolean runPendingOperation(BLEOperation after) {
        boolean removed;
        synchronized (this.pendingOperations) {
            removed = false;
            BLEOperation operation = (BLEOperation) this.pendingOperations.peek();
            if (operation == after) {
                this.pendingOperations.poll();
                removed = true;
                operation = (BLEOperation) this.pendingOperations.peek();
                Log.d(LOG_TAG, "running next operation " + operation);
                if (operation != null) {
                    this.mHandler.post(operation);
                }
            } else {
                Log.d(LOG_TAG, "after operation = " + after);
                Log.d(LOG_TAG, "pending operation = " + operation);
            }
        }
        return removed;
    }

    private void scheduleConnectionTimeoutMessage() {
        this.uiThread.postDelayed(new Runnable() {
            public void run() {
                if (!BluetoothLEint.this.isConnected && BluetoothLEint.this.mBluetoothGatt != null) {
                    BluetoothLEint.this.mBluetoothGatt.disconnect();
                    BluetoothLEint.this.outer.ConnectionFailed("Connection timeout reached");
                }
            }
        }, (long) (this.connectionTimeout * 1000));
    }

    private void forceDisconnect() {
        if (this.mBluetoothGatt != null) {
            if (this.isConnected) {
                this.mBluetoothGatt.disconnect();
                this.mBluetoothGatt.close();
                this.mBluetoothGatt = null;
            } else if (this.autoReconnect) {
                this.mBluetoothGatt.disconnect();
                this.mBluetoothGatt.close();
                this.mBluetoothGatt = null;
            }
            this.isConnected = false;
            this.isUserDisconnect = true;
        }
        this.mBluetoothGatt = null;
    }

    private static UUID bleStringToUuid(String uuid) {
        uuid = uuid.toLowerCase();
        if (uuid.length() == 4) {
            uuid = "0000" + uuid + BLUETOOTH_BASE_UUID_SUFFIX;
        } else if (uuid.length() == 8) {
            uuid = uuid + BLUETOOTH_BASE_UUID_SUFFIX;
        } else if (uuid.length() == 32) {
            uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
        } else if (!BLEUtil.hasValidUUIDFormat(uuid)) {
            throw new IllegalArgumentException("Invalid UUID: " + uuid);
        }
        return UUID.fromString(uuid);
    }
}

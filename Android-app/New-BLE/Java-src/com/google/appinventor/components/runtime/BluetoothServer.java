package com.google.appinventor.components.runtime;

import android.os.Handler;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.BluetoothReflection;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.SdkLevel;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@SimpleObject
@DesignerComponent(category = ComponentCategory.CONNECTIVITY, description = "Bluetooth server component", iconName = "images/bluetooth.png", nonVisible = true, version = 5)
@UsesPermissions(permissionNames = "android.permission.BLUETOOTH, android.permission.BLUETOOTH_ADMIN")
public final class BluetoothServer extends BluetoothConnectionBase {
    private static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
    private final Handler androidUIHandler = new Handler();
    private final AtomicReference<Object> arBluetoothServerSocket = new AtomicReference();

    public BluetoothServer(ComponentContainer container) {
        super(container, "BluetoothServer");
    }

    @SimpleFunction(description = "Accept an incoming connection with the Serial Port Profile (SPP).")
    public void AcceptConnection(String serviceName) {
        accept("AcceptConnection", serviceName, SPP_UUID);
    }

    @SimpleFunction(description = "Accept an incoming connection with a specific UUID.")
    public void AcceptConnectionWithUUID(String serviceName, String uuid) {
        accept("AcceptConnectionWithUUID", serviceName, uuid);
    }

    private void accept(final String functionName, String name, String uuidString) {
        Object bluetoothAdapter = BluetoothReflection.getBluetoothAdapter();
        if (bluetoothAdapter == null) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_AVAILABLE, new Object[0]);
        } else if (BluetoothReflection.isBluetoothEnabled(bluetoothAdapter)) {
            try {
                UUID uuid = UUID.fromString(uuidString);
                try {
                    Object bluetoothServerSocket;
                    if (this.secure || SdkLevel.getLevel() < 10) {
                        bluetoothServerSocket = BluetoothReflection.listenUsingRfcommWithServiceRecord(bluetoothAdapter, name, uuid);
                    } else {
                        bluetoothServerSocket = BluetoothReflection.listenUsingInsecureRfcommWithServiceRecord(bluetoothAdapter, name, uuid);
                    }
                    this.arBluetoothServerSocket.set(bluetoothServerSocket);
                    AsynchUtil.runAsynchronously(new Runnable() {

                        class C01531 implements Runnable {
                            C01531() {
                            }

                            public void run() {
                                BluetoothServer.this.form.dispatchErrorOccurredEvent(BluetoothServer.this, functionName, ErrorMessages.ERROR_BLUETOOTH_UNABLE_TO_ACCEPT, new Object[0]);
                            }
                        }

                        public void run() {
                            Object acceptedBluetoothSocket = null;
                            Object bluetoothServerSocket = BluetoothServer.this.arBluetoothServerSocket.get();
                            if (bluetoothServerSocket != null) {
                                try {
                                    acceptedBluetoothSocket = BluetoothReflection.accept(bluetoothServerSocket);
                                } catch (IOException e) {
                                    BluetoothServer.this.androidUIHandler.post(new C01531());
                                    return;
                                } finally {
                                    BluetoothServer.this.StopAccepting();
                                }
                            }
                            if (acceptedBluetoothSocket != null) {
                                BluetoothServer.this.androidUIHandler.post(new Runnable() {
                                    public void run() {
                                        try {
                                            BluetoothServer.this.setConnection(acceptedBluetoothSocket);
                                            BluetoothServer.this.ConnectionAccepted();
                                        } catch (IOException e) {
                                            BluetoothServer.this.Disconnect();
                                            BluetoothServer.this.form.dispatchErrorOccurredEvent(BluetoothServer.this, functionName, ErrorMessages.ERROR_BLUETOOTH_UNABLE_TO_ACCEPT, new Object[0]);
                                        }
                                    }
                                });
                            }
                        }
                    });
                } catch (IOException e) {
                    this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_UNABLE_TO_LISTEN, new Object[0]);
                }
            } catch (IllegalArgumentException e2) {
                this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_INVALID_UUID, uuidString);
            }
        } else {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_BLUETOOTH_NOT_ENABLED, new Object[0]);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public final boolean IsAccepting() {
        return this.arBluetoothServerSocket.get() != null;
    }

    @SimpleFunction(description = "Stop accepting an incoming connection.")
    public void StopAccepting() {
        Object bluetoothServerSocket = this.arBluetoothServerSocket.getAndSet(null);
        if (bluetoothServerSocket != null) {
            try {
                BluetoothReflection.closeBluetoothServerSocket(bluetoothServerSocket);
            } catch (IOException e) {
                Log.w(this.logTag, "Error while closing bluetooth server socket: " + e.getMessage());
            }
        }
    }

    @SimpleEvent(description = "Indicates that a bluetooth connection has been accepted.")
    public void ConnectionAccepted() {
        Log.i(this.logTag, "Successfullly accepted bluetooth connection.");
        EventDispatcher.dispatchEvent(this, "ConnectionAccepted", new Object[0]);
    }
}

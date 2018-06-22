package com.google.appinventor.components.runtime;

interface BluetoothConnectionListener {
    void afterConnect(BluetoothConnectionBase bluetoothConnectionBase);

    void beforeDisconnect(BluetoothConnectionBase bluetoothConnectionBase);
}

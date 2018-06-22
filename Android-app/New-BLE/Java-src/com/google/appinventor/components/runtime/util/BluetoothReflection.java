package com.google.appinventor.components.runtime.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class BluetoothReflection {
    private static final int BOND_BONDED = 12;

    private BluetoothReflection() {
    }

    public static Object getBluetoothAdapter() {
        try {
            return invokeStaticMethod(getMethod(Class.forName("android.bluetooth.BluetoothAdapter"), "getDefaultAdapter"));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static boolean isBluetoothEnabled(Object bluetoothAdapter) {
        return ((Boolean) invokeMethod(getMethod(bluetoothAdapter.getClass(), "isEnabled"), bluetoothAdapter, new Object[0])).booleanValue();
    }

    public static Set getBondedDevices(Object bluetoothAdapter) {
        return (Set) invokeMethod(getMethod(bluetoothAdapter.getClass(), "getBondedDevices"), bluetoothAdapter, new Object[0]);
    }

    public static boolean checkBluetoothAddress(Object bluetoothAdapter, String address) {
        return ((Boolean) invokeMethod(getMethod(bluetoothAdapter.getClass(), "checkBluetoothAddress", String.class), bluetoothAdapter, address)).booleanValue();
    }

    public static Object getRemoteDevice(Object bluetoothAdapter, String address) throws IllegalArgumentException {
        return invokeMethodThrowsIllegalArgumentException(getMethod(bluetoothAdapter.getClass(), "getRemoteDevice", String.class), bluetoothAdapter, address);
    }

    public static Object listenUsingRfcommWithServiceRecord(Object bluetoothAdapter, String name, UUID uuid) throws IOException {
        return invokeMethodThrowsIOException(getMethod(bluetoothAdapter.getClass(), "listenUsingRfcommWithServiceRecord", String.class, UUID.class), bluetoothAdapter, name, uuid);
    }

    public static Object listenUsingInsecureRfcommWithServiceRecord(Object bluetoothAdapter, String name, UUID uuid) throws IOException {
        return invokeMethodThrowsIOException(getMethod(bluetoothAdapter.getClass(), "listenUsingInsecureRfcommWithServiceRecord", String.class, UUID.class), bluetoothAdapter, name, uuid);
    }

    public static String getBluetoothDeviceName(Object bluetoothDevice) {
        return (String) invokeMethod(getMethod(bluetoothDevice.getClass(), "getName"), bluetoothDevice, new Object[0]);
    }

    public static String getBluetoothDeviceAddress(Object bluetoothDevice) {
        return (String) invokeMethod(getMethod(bluetoothDevice.getClass(), "getAddress"), bluetoothDevice, new Object[0]);
    }

    public static boolean isBonded(Object bluetoothDevice) {
        return ((Integer) invokeMethod(getMethod(bluetoothDevice.getClass(), "getBondState"), bluetoothDevice, new Object[0])).intValue() == 12;
    }

    public static Object getBluetoothClass(Object bluetoothDevice) {
        return invokeMethod(getMethod(bluetoothDevice.getClass(), "getBluetoothClass"), bluetoothDevice, new Object[0]);
    }

    public static Object createRfcommSocketToServiceRecord(Object bluetoothDevice, UUID uuid) throws IOException {
        return invokeMethodThrowsIOException(getMethod(bluetoothDevice.getClass(), "createRfcommSocketToServiceRecord", UUID.class), bluetoothDevice, uuid);
    }

    public static Object createInsecureRfcommSocketToServiceRecord(Object bluetoothDevice, UUID uuid) throws IOException {
        return invokeMethodThrowsIOException(getMethod(bluetoothDevice.getClass(), "createInsecureRfcommSocketToServiceRecord", UUID.class), bluetoothDevice, uuid);
    }

    public static int getDeviceClass(Object bluetoothClass) {
        return ((Integer) invokeMethod(getMethod(bluetoothClass.getClass(), "getDeviceClass"), bluetoothClass, new Object[0])).intValue();
    }

    public static void connectToBluetoothSocket(Object bluetoothSocket) throws IOException {
        invokeMethodThrowsIOException(getMethod(bluetoothSocket.getClass(), "connect"), bluetoothSocket, new Object[0]);
    }

    public static InputStream getInputStream(Object bluetoothSocket) throws IOException {
        return (InputStream) invokeMethodThrowsIOException(getMethod(bluetoothSocket.getClass(), "getInputStream"), bluetoothSocket, new Object[0]);
    }

    public static OutputStream getOutputStream(Object bluetoothSocket) throws IOException {
        return (OutputStream) invokeMethodThrowsIOException(getMethod(bluetoothSocket.getClass(), "getOutputStream"), bluetoothSocket, new Object[0]);
    }

    public static void closeBluetoothSocket(Object bluetoothSocket) throws IOException {
        invokeMethodThrowsIOException(getMethod(bluetoothSocket.getClass(), "close"), bluetoothSocket, new Object[0]);
    }

    public static Object accept(Object bluetoothServerSocket) throws IOException {
        return invokeMethodThrowsIOException(getMethod(bluetoothServerSocket.getClass(), "accept"), bluetoothServerSocket, new Object[0]);
    }

    public static void closeBluetoothServerSocket(Object bluetoothServerSocket) throws IOException {
        invokeMethodThrowsIOException(getMethod(bluetoothServerSocket.getClass(), "close"), bluetoothServerSocket, new Object[0]);
    }

    private static Method getMethod(Class clazz, String name) {
        try {
            return clazz.getMethod(name, new Class[0]);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method getMethod(Class clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object invokeStaticMethod(Method method) {
        try {
            return method.invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            cause.printStackTrace();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new RuntimeException(cause);
        }
    }

    private static Object invokeMethod(Method method, Object thisObject, Object... args) {
        try {
            return method.invoke(thisObject, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            cause.printStackTrace();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            throw new RuntimeException(cause);
        }
    }

    private static Object invokeMethodThrowsIllegalArgumentException(Method method, Object thisObject, Object... args) throws IllegalArgumentException {
        try {
            return method.invoke(thisObject, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            cause.printStackTrace();
            if (cause instanceof IllegalArgumentException) {
                throw ((IllegalArgumentException) cause);
            } else if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else {
                throw new RuntimeException(e2);
            }
        }
    }

    private static Object invokeMethodThrowsIOException(Method method, Object thisObject, Object... args) throws IOException {
        try {
            return method.invoke(thisObject, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            cause.printStackTrace();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            } else if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else {
                throw new RuntimeException(e2);
            }
        }
    }
}

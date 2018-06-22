package gnu.lists;

import android.support.v4.internal.view.SupportMenu;

public class Convert {
    public static Convert instance = new Convert();

    public static Convert getInstance() {
        return instance;
    }

    public static void setInstance(Convert value) {
        instance = value;
    }

    public Object booleanToObject(boolean value) {
        return value ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean objectToBoolean(Object obj) {
        return !(obj instanceof Boolean) || ((Boolean) obj).booleanValue();
    }

    public static Object toObject(boolean value) {
        return instance.booleanToObject(value);
    }

    public static boolean toBoolean(Object obj) {
        return instance.objectToBoolean(obj);
    }

    public Object charToObject(char ch) {
        return new Character(ch);
    }

    public char objectToChar(Object obj) {
        return ((Character) obj).charValue();
    }

    public static Object toObject(char ch) {
        return instance.charToObject(ch);
    }

    public static char toChar(Object obj) {
        return instance.objectToChar(obj);
    }

    public Object byteToObject(byte value) {
        return new Byte(value);
    }

    public byte objectToByte(Object obj) {
        return ((Number) obj).byteValue();
    }

    public static Object toObject(byte value) {
        return instance.byteToObject(value);
    }

    public static byte toByte(Object obj) {
        return instance.objectToByte(obj);
    }

    public Object byteToObjectUnsigned(byte value) {
        return new Integer(value & 255);
    }

    public byte objectToByteUnsigned(Object obj) {
        return ((Number) obj).byteValue();
    }

    public static Object toObjectUnsigned(byte value) {
        return instance.byteToObjectUnsigned(value);
    }

    public static byte toByteUnsigned(Object obj) {
        return instance.objectToByteUnsigned(obj);
    }

    public Object shortToObject(short value) {
        return new Short(value);
    }

    public short objectToShort(Object obj) {
        return ((Number) obj).shortValue();
    }

    public static Object toObject(short value) {
        return instance.shortToObject(value);
    }

    public static short toShort(Object obj) {
        return instance.objectToShort(obj);
    }

    public Object shortToObjectUnsigned(short value) {
        return new Integer(SupportMenu.USER_MASK & value);
    }

    public short objectToShortUnsigned(Object obj) {
        return ((Number) obj).shortValue();
    }

    public static Object toObjectUnsigned(short value) {
        return instance.shortToObjectUnsigned(value);
    }

    public static short toShortUnsigned(Object obj) {
        return instance.objectToShortUnsigned(obj);
    }

    public Object intToObject(int value) {
        return new Integer(value);
    }

    public int objectToInt(Object obj) {
        return ((Number) obj).intValue();
    }

    public static Object toObject(int value) {
        return instance.intToObject(value);
    }

    public static int toInt(Object obj) {
        return instance.objectToInt(obj);
    }

    public Object intToObjectUnsigned(int value) {
        if (value >= 0) {
            return new Integer(value);
        }
        return new Long(((long) value) & 4294967295L);
    }

    public int objectToIntUnsigned(Object obj) {
        return ((Number) obj).intValue();
    }

    public static Object toObjectUnsigned(int value) {
        return instance.intToObjectUnsigned(value);
    }

    public static int toIntUnsigned(Object obj) {
        return instance.objectToIntUnsigned(obj);
    }

    public Object longToObject(long value) {
        return new Long(value);
    }

    public long objectToLong(Object obj) {
        return ((Number) obj).longValue();
    }

    public static Object toObject(long value) {
        return instance.longToObject(value);
    }

    public static long toLong(Object obj) {
        return instance.objectToLong(obj);
    }

    public Object longToObjectUnsigned(long value) {
        return new Long(value);
    }

    public long objectToLongUnsigned(Object obj) {
        return ((Number) obj).longValue();
    }

    public static Object toObjectUnsigned(long value) {
        return instance.longToObjectUnsigned(value);
    }

    public static long toLongUnsigned(Object obj) {
        return instance.objectToLongUnsigned(obj);
    }

    public Object floatToObject(float value) {
        return new Float(value);
    }

    public float objectToFloat(Object obj) {
        return ((Number) obj).floatValue();
    }

    public static Object toObject(float value) {
        return instance.floatToObject(value);
    }

    public static float toFloat(Object obj) {
        return instance.objectToFloat(obj);
    }

    public Object doubleToObject(double value) {
        return new Double(value);
    }

    public double objectToDouble(Object obj) {
        return ((Number) obj).doubleValue();
    }

    public static Object toObject(double value) {
        return instance.doubleToObject(value);
    }

    public static double toDouble(Object obj) {
        return instance.objectToDouble(obj);
    }

    public static double parseDouble(String str) {
        return Double.parseDouble(str);
    }
}

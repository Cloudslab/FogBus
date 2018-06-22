package com.google.appinventor.components.runtime.util;

public class OrientationSensorUtil {
    private OrientationSensorUtil() {
    }

    static float mod(float dividend, float quotient) {
        float result = dividend % quotient;
        return (result == 0.0f || Math.signum(dividend) == Math.signum(quotient)) ? result : result + quotient;
    }

    public static float normalizeAzimuth(float azimuth) {
        return mod(azimuth, 360.0f);
    }

    public static float normalizePitch(float pitch) {
        return mod(pitch + 180.0f, 360.0f) - 180.0f;
    }

    public static float normalizeRoll(float roll) {
        roll = Math.max(Math.min(roll, 180.0f), -180.0f);
        if (roll >= -90.0f && roll <= 90.0f) {
            return roll;
        }
        roll = 180.0f - roll;
        if (roll >= 270.0f) {
            return roll - 360.0f;
        }
        return roll;
    }
}

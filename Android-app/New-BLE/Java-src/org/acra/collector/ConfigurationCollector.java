package org.acra.collector;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import org.acra.ACRA;

public final class ConfigurationCollector {
    private static final String FIELD_MCC = "mcc";
    private static final String FIELD_MNC = "mnc";
    private static final String FIELD_SCREENLAYOUT = "screenLayout";
    private static final String FIELD_UIMODE = "uiMode";
    private static final String PREFIX_HARDKEYBOARDHIDDEN = "HARDKEYBOARDHIDDEN_";
    private static final String PREFIX_KEYBOARD = "KEYBOARD_";
    private static final String PREFIX_KEYBOARDHIDDEN = "KEYBOARDHIDDEN_";
    private static final String PREFIX_NAVIGATION = "NAVIGATION_";
    private static final String PREFIX_NAVIGATIONHIDDEN = "NAVIGATIONHIDDEN_";
    private static final String PREFIX_ORIENTATION = "ORIENTATION_";
    private static final String PREFIX_SCREENLAYOUT = "SCREENLAYOUT_";
    private static final String PREFIX_TOUCHSCREEN = "TOUCHSCREEN_";
    private static final String PREFIX_UI_MODE = "UI_MODE_";
    private static final String SUFFIX_MASK = "_MASK";
    private static SparseArray<String> mHardKeyboardHiddenValues = new SparseArray();
    private static SparseArray<String> mKeyboardHiddenValues = new SparseArray();
    private static SparseArray<String> mKeyboardValues = new SparseArray();
    private static SparseArray<String> mNavigationHiddenValues = new SparseArray();
    private static SparseArray<String> mNavigationValues = new SparseArray();
    private static SparseArray<String> mOrientationValues = new SparseArray();
    private static SparseArray<String> mScreenLayoutValues = new SparseArray();
    private static SparseArray<String> mTouchScreenValues = new SparseArray();
    private static SparseArray<String> mUiModeValues = new SparseArray();
    private static final HashMap<String, SparseArray<String>> mValueArrays = new HashMap();

    static {
        mValueArrays.put(PREFIX_HARDKEYBOARDHIDDEN, mHardKeyboardHiddenValues);
        mValueArrays.put(PREFIX_KEYBOARD, mKeyboardValues);
        mValueArrays.put(PREFIX_KEYBOARDHIDDEN, mKeyboardHiddenValues);
        mValueArrays.put(PREFIX_NAVIGATION, mNavigationValues);
        mValueArrays.put(PREFIX_NAVIGATIONHIDDEN, mNavigationHiddenValues);
        mValueArrays.put(PREFIX_ORIENTATION, mOrientationValues);
        mValueArrays.put(PREFIX_SCREENLAYOUT, mScreenLayoutValues);
        mValueArrays.put(PREFIX_TOUCHSCREEN, mTouchScreenValues);
        mValueArrays.put(PREFIX_UI_MODE, mUiModeValues);
        for (Field f : Configuration.class.getFields()) {
            if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) {
                String fieldName = f.getName();
                try {
                    if (fieldName.startsWith(PREFIX_HARDKEYBOARDHIDDEN)) {
                        mHardKeyboardHiddenValues.put(f.getInt(null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_KEYBOARD)) {
                        mKeyboardValues.put(f.getInt(null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_KEYBOARDHIDDEN)) {
                        mKeyboardHiddenValues.put(f.getInt(null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_NAVIGATION)) {
                        mNavigationValues.put(f.getInt(null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_NAVIGATIONHIDDEN)) {
                        mNavigationHiddenValues.put(f.getInt(null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_ORIENTATION)) {
                        mOrientationValues.put(f.getInt(null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_SCREENLAYOUT)) {
                        mScreenLayoutValues.put(f.getInt(null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_TOUCHSCREEN)) {
                        mTouchScreenValues.put(f.getInt(null), fieldName);
                    } else if (fieldName.startsWith(PREFIX_UI_MODE)) {
                        mUiModeValues.put(f.getInt(null), fieldName);
                    }
                } catch (IllegalArgumentException e) {
                    Log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e);
                } catch (IllegalAccessException e2) {
                    Log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e2);
                }
            }
        }
    }

    public static String toString(Configuration conf) {
        StringBuilder result = new StringBuilder();
        for (Field f : conf.getClass().getFields()) {
            try {
                if (!Modifier.isStatic(f.getModifiers())) {
                    result.append(f.getName()).append('=');
                    if (f.getType().equals(Integer.TYPE)) {
                        result.append(getFieldValueName(conf, f));
                    } else if (f.get(conf) != null) {
                        result.append(f.get(conf).toString());
                    }
                    result.append('\n');
                }
            } catch (IllegalArgumentException e) {
                Log.e(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e);
            } catch (IllegalAccessException e2) {
                Log.e(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e2);
            }
        }
        return result.toString();
    }

    private static String getFieldValueName(Configuration conf, Field f) throws IllegalAccessException {
        String fieldName = f.getName();
        if (fieldName.equals(FIELD_MCC) || fieldName.equals(FIELD_MNC)) {
            return Integer.toString(f.getInt(conf));
        }
        if (fieldName.equals(FIELD_UIMODE)) {
            return activeFlags((SparseArray) mValueArrays.get(PREFIX_UI_MODE), f.getInt(conf));
        }
        if (fieldName.equals(FIELD_SCREENLAYOUT)) {
            return activeFlags((SparseArray) mValueArrays.get(PREFIX_SCREENLAYOUT), f.getInt(conf));
        }
        SparseArray<String> values = (SparseArray) mValueArrays.get(fieldName.toUpperCase() + '_');
        if (values == null) {
            return Integer.toString(f.getInt(conf));
        }
        String value = (String) values.get(f.getInt(conf));
        if (value == null) {
            return Integer.toString(f.getInt(conf));
        }
        return value;
    }

    private static String activeFlags(SparseArray<String> valueNames, int bitfield) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < valueNames.size(); i++) {
            int maskValue = valueNames.keyAt(i);
            if (((String) valueNames.get(maskValue)).endsWith(SUFFIX_MASK)) {
                int value = bitfield & maskValue;
                if (value > 0) {
                    if (result.length() > 0) {
                        result.append('+');
                    }
                    result.append((String) valueNames.get(value));
                }
            }
        }
        return result.toString();
    }

    public static String collectConfiguration(Context context) {
        try {
            return toString(context.getResources().getConfiguration());
        } catch (RuntimeException e) {
            Log.w(ACRA.LOG_TAG, "Couldn't retrieve CrashConfiguration for : " + context.getPackageName(), e);
            return "Couldn't retrieve crash config";
        }
    }
}

package org.acra.collector;

import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class MediaCodecListCollector {
    private static final String[] AAC_TYPES = new String[]{"aac", "AAC"};
    private static final String[] AVC_TYPES = new String[]{"avc", "h264", "AVC", "H264"};
    private static final String COLOR_FORMAT_PREFIX = "COLOR_";
    private static final String[] H263_TYPES = new String[]{"h263", "H263"};
    private static final String[] MPEG4_TYPES = new String[]{"mp4", "mpeg4", "MP4", "MPEG4"};
    private static Class<?> codecCapabilitiesClass;
    private static Field colorFormatsField;
    private static Method getCapabilitiesForTypeMethod;
    private static Method getCodecInfoAtMethod;
    private static Method getNameMethod;
    private static Method getSupportedTypesMethod;
    private static Method isEncoderMethod;
    private static Field levelField;
    private static SparseArray<String> mAACProfileValues = new SparseArray();
    private static SparseArray<String> mAVCLevelValues = new SparseArray();
    private static SparseArray<String> mAVCProfileValues = new SparseArray();
    private static SparseArray<String> mColorFormatValues = new SparseArray();
    private static SparseArray<String> mH263LevelValues = new SparseArray();
    private static SparseArray<String> mH263ProfileValues = new SparseArray();
    private static SparseArray<String> mMPEG4LevelValues = new SparseArray();
    private static SparseArray<String> mMPEG4ProfileValues = new SparseArray();
    private static Class<?> mediaCodecInfoClass;
    private static Class<?> mediaCodecListClass;
    private static Field profileField;
    private static Field profileLevelsField;

    private enum CodecType {
        AVC,
        H263,
        MPEG4,
        AAC
    }

    static {
        mediaCodecListClass = null;
        getCodecInfoAtMethod = null;
        mediaCodecInfoClass = null;
        getNameMethod = null;
        isEncoderMethod = null;
        getSupportedTypesMethod = null;
        getCapabilitiesForTypeMethod = null;
        codecCapabilitiesClass = null;
        colorFormatsField = null;
        profileLevelsField = null;
        profileField = null;
        levelField = null;
        try {
            mediaCodecListClass = Class.forName("android.media.MediaCodecList");
            getCodecInfoAtMethod = mediaCodecListClass.getMethod("getCodecInfoAt", new Class[]{Integer.TYPE});
            mediaCodecInfoClass = Class.forName("android.media.MediaCodecInfo");
            getNameMethod = mediaCodecInfoClass.getMethod("getName", new Class[0]);
            isEncoderMethod = mediaCodecInfoClass.getMethod("isEncoder", new Class[0]);
            getSupportedTypesMethod = mediaCodecInfoClass.getMethod("getSupportedTypes", new Class[0]);
            getCapabilitiesForTypeMethod = mediaCodecInfoClass.getMethod("getCapabilitiesForType", new Class[]{String.class});
            codecCapabilitiesClass = Class.forName("android.media.MediaCodecInfo$CodecCapabilities");
            colorFormatsField = codecCapabilitiesClass.getField("colorFormats");
            profileLevelsField = codecCapabilitiesClass.getField("profileLevels");
            for (Field f : codecCapabilitiesClass.getFields()) {
                if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) && f.getName().startsWith(COLOR_FORMAT_PREFIX)) {
                    mColorFormatValues.put(f.getInt(null), f.getName());
                }
            }
            Class<?> codecProfileLevelClass = Class.forName("android.media.MediaCodecInfo$CodecProfileLevel");
            for (Field f2 : codecProfileLevelClass.getFields()) {
                if (Modifier.isStatic(f2.getModifiers()) && Modifier.isFinal(f2.getModifiers())) {
                    if (f2.getName().startsWith("AVCLevel")) {
                        mAVCLevelValues.put(f2.getInt(null), f2.getName());
                    } else if (f2.getName().startsWith("AVCProfile")) {
                        mAVCProfileValues.put(f2.getInt(null), f2.getName());
                    } else if (f2.getName().startsWith("H263Level")) {
                        mH263LevelValues.put(f2.getInt(null), f2.getName());
                    } else if (f2.getName().startsWith("H263Profile")) {
                        mH263ProfileValues.put(f2.getInt(null), f2.getName());
                    } else if (f2.getName().startsWith("MPEG4Level")) {
                        mMPEG4LevelValues.put(f2.getInt(null), f2.getName());
                    } else if (f2.getName().startsWith("MPEG4Profile")) {
                        mMPEG4ProfileValues.put(f2.getInt(null), f2.getName());
                    } else if (f2.getName().startsWith("AAC")) {
                        mAACProfileValues.put(f2.getInt(null), f2.getName());
                    }
                }
            }
            profileField = codecProfileLevelClass.getField("profile");
            levelField = codecProfileLevelClass.getField("level");
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e2) {
        } catch (IllegalArgumentException e3) {
        } catch (IllegalAccessException e4) {
        } catch (SecurityException e5) {
        } catch (NoSuchFieldException e6) {
        }
    }

    public static String collecMediaCodecList() {
        StringBuilder result = new StringBuilder();
        if (!(mediaCodecListClass == null || mediaCodecInfoClass == null)) {
            try {
                int codecCount = ((Integer) mediaCodecListClass.getMethod("getCodecCount", new Class[0]).invoke(null, new Object[0])).intValue();
                for (int codecIdx = 0; codecIdx < codecCount; codecIdx++) {
                    result.append("\n");
                    Object codecInfo = getCodecInfoAtMethod.invoke(null, new Object[]{Integer.valueOf(codecIdx)});
                    result.append(codecIdx).append(": ").append(getNameMethod.invoke(codecInfo, new Object[0])).append("\n");
                    result.append("isEncoder: ").append(isEncoderMethod.invoke(codecInfo, new Object[0])).append("\n");
                    String[] supportedTypes = (String[]) getSupportedTypesMethod.invoke(codecInfo, new Object[0]);
                    result.append("Supported types: ").append(Arrays.toString(supportedTypes)).append("\n");
                    for (String type : supportedTypes) {
                        result.append(collectCapabilitiesForType(codecInfo, type));
                    }
                    result.append("\n");
                }
            } catch (NoSuchMethodException e) {
            } catch (IllegalAccessException e2) {
            } catch (InvocationTargetException e3) {
            }
        }
        return result.toString();
    }

    private static String collectCapabilitiesForType(Object codecInfo, String type) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        int i;
        StringBuilder result = new StringBuilder();
        Object codecCapabilities = getCapabilitiesForTypeMethod.invoke(codecInfo, new Object[]{type});
        int[] colorFormats = (int[]) colorFormatsField.get(codecCapabilities);
        if (colorFormats.length > 0) {
            result.append(type).append(" color formats:");
            for (i = 0; i < colorFormats.length; i++) {
                result.append((String) mColorFormatValues.get(colorFormats[i]));
                if (i < colorFormats.length - 1) {
                    result.append(',');
                }
            }
            result.append("\n");
        }
        Object[] codecProfileLevels = (Object[]) profileLevelsField.get(codecCapabilities);
        if (codecProfileLevels.length > 0) {
            result.append(type).append(" profile levels:");
            for (i = 0; i < codecProfileLevels.length; i++) {
                CodecType codecType = identifyCodecType(codecInfo);
                int profileValue = profileField.getInt(codecProfileLevels[i]);
                int levelValue = levelField.getInt(codecProfileLevels[i]);
                if (codecType == null) {
                    result.append(profileValue).append('-').append(levelValue);
                }
                switch (codecType) {
                    case AVC:
                        result.append(profileValue).append((String) mAVCProfileValues.get(profileValue)).append('-').append((String) mAVCLevelValues.get(levelValue));
                        break;
                    case H263:
                        result.append((String) mH263ProfileValues.get(profileValue)).append('-').append((String) mH263LevelValues.get(levelValue));
                        break;
                    case MPEG4:
                        result.append((String) mMPEG4ProfileValues.get(profileValue)).append('-').append((String) mMPEG4LevelValues.get(levelValue));
                        break;
                    case AAC:
                        result.append((String) mAACProfileValues.get(profileValue));
                        break;
                }
                if (i < codecProfileLevels.length - 1) {
                    result.append(',');
                }
            }
            result.append("\n");
        }
        return result.append("\n").toString();
    }

    private static CodecType identifyCodecType(Object codecInfo) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String name = (String) getNameMethod.invoke(codecInfo, new Object[0]);
        for (String token : AVC_TYPES) {
            if (name.contains(token)) {
                return CodecType.AVC;
            }
        }
        for (String token2 : H263_TYPES) {
            if (name.contains(token2)) {
                return CodecType.H263;
            }
        }
        for (String token22 : MPEG4_TYPES) {
            if (name.contains(token22)) {
                return CodecType.MPEG4;
            }
        }
        for (String token222 : AAC_TYPES) {
            if (name.contains(token222)) {
                return CodecType.AAC;
            }
        }
        return null;
    }
}

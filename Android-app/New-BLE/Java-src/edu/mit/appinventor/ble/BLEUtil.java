package edu.mit.appinventor.ble;

import android.os.ParcelUuid;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

final class BLEUtil {
    private static final Pattern INVALID_UUID_CHARS = Pattern.compile("[^0-9a-fA-F-]");
    private static final Pattern UUID_FORMAT = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");

    BLEUtil() {
    }

    static boolean hasValidUUIDFormat(String UUID) {
        return UUID_FORMAT.matcher(UUID).find();
    }

    static boolean hasInvalidUUIDChars(String UUID) {
        return INVALID_UUID_CHARS.matcher(UUID).find();
    }

    static List<String> stringifyParcelUuids(List<ParcelUuid> serviceUUIDs) {
        List<String> deviceServices = new ArrayList();
        for (ParcelUuid serviceUuid : serviceUUIDs) {
            deviceServices.add(serviceUuid.toString());
        }
        return deviceServices;
    }
}

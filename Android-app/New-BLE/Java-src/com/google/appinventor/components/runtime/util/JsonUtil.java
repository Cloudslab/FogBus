package com.google.appinventor.components.runtime.util;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import com.google.appinventor.components.runtime.errors.YailRuntimeError;
import gnu.lists.FString;
import gnu.math.IntFraction;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonUtil {
    private static final String BINFILE_DIR = "/AppInventorBinaries";
    private static final String LOG_TAG = "JsonUtil";

    static class C03331 implements Comparator<File> {
        C03331() {
        }

        public int compare(File f1, File f2) {
            return Long.valueOf(f1.lastModified()).compareTo(Long.valueOf(f2.lastModified()));
        }
    }

    private JsonUtil() {
    }

    public static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
        List<String> returnList = new ArrayList();
        for (int i = 0; i < jArray.length(); i++) {
            returnList.add(jArray.getString(i));
        }
        return returnList;
    }

    public static List<Object> getListFromJsonArray(JSONArray jArray) throws JSONException {
        List<Object> returnList = new ArrayList();
        for (int i = 0; i < jArray.length(); i++) {
            returnList.add(convertJsonItem(jArray.get(i)));
        }
        return returnList;
    }

    public static List<Object> getListFromJsonObject(JSONObject jObject) throws JSONException {
        List<Object> returnList = new ArrayList();
        Iterator<String> keys = jObject.keys();
        List<String> keysList = new ArrayList();
        while (keys.hasNext()) {
            keysList.add(keys.next());
        }
        Collections.sort(keysList);
        for (String key : keysList) {
            List<Object> nestedList = new ArrayList();
            nestedList.add(key);
            nestedList.add(convertJsonItem(jObject.get(key)));
            returnList.add(nestedList);
        }
        return returnList;
    }

    public static Object convertJsonItem(Object o) throws JSONException {
        if (o == null) {
            return "null";
        }
        if (o instanceof JSONObject) {
            return getListFromJsonObject((JSONObject) o);
        }
        if (o instanceof JSONArray) {
            return getListFromJsonArray((JSONArray) o);
        }
        if (o.equals(Boolean.FALSE) || ((o instanceof String) && ((String) o).equalsIgnoreCase("false"))) {
            return Boolean.valueOf(false);
        }
        if (o.equals(Boolean.TRUE) || ((o instanceof String) && ((String) o).equalsIgnoreCase("true"))) {
            return Boolean.valueOf(true);
        }
        return !(o instanceof Number) ? o.toString() : o;
    }

    public static String getJsonRepresentation(Object value) throws JSONException {
        if (value == null || value.equals(null)) {
            return "null";
        }
        if (value instanceof FString) {
            return JSONObject.quote(value.toString());
        }
        if (value instanceof YailList) {
            return ((YailList) value).toJSONString();
        }
        if (value instanceof IntFraction) {
            return JSONObject.numberToString(Double.valueOf(((IntFraction) value).doubleValue()));
        }
        if (value instanceof Number) {
            return JSONObject.numberToString((Number) value);
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof List) {
            value = ((List) value).toArray();
        }
        if (!value.getClass().isArray()) {
            return JSONObject.quote(value.toString());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        String separator = "";
        for (Object o : (Object[]) value) {
            sb.append(separator).append(getJsonRepresentation(o));
            separator = ",";
        }
        sb.append("]");
        return sb.toString();
    }

    public static Object getObjectFromJson(String jsonString) throws JSONException {
        if (jsonString == null || jsonString.equals("")) {
            return "";
        }
        Object value = new JSONTokener(jsonString).nextValue();
        if (value == null || value.equals(null)) {
            return null;
        }
        if ((value instanceof String) || (value instanceof Number) || (value instanceof Boolean)) {
            return value;
        }
        if (value instanceof JSONArray) {
            return getListFromJsonArray((JSONArray) value);
        }
        if (value instanceof JSONObject) {
            return getListFromJsonObject((JSONObject) value);
        }
        throw new JSONException("Invalid JSON string.");
    }

    public static String getJsonRepresentationIfValueFileName(Object value) {
        try {
            List<String> valueList;
            if (value instanceof String) {
                valueList = getStringListFromJsonArray(new JSONArray((String) value));
            } else if (value instanceof List) {
                valueList = (List) value;
            } else {
                throw new YailRuntimeError("getJsonRepresentationIfValueFileName called on unknown type", value.getClass().getName());
            }
            if (valueList.size() != 2) {
                return null;
            }
            if (!((String) valueList.get(0)).startsWith(".")) {
                return null;
            }
            String filename = writeFile((String) valueList.get(1), ((String) valueList.get(0)).substring(1));
            System.out.println("Filename Written: " + filename);
            return getJsonRepresentation(filename.replace("file:/", "file:///"));
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException", e);
            return null;
        }
    }

    private static String writeFile(String input, String fileExtension) {
        try {
            if (fileExtension.length() != 3) {
                throw new YailRuntimeError("File Extension must be three characters", "Write Error");
            }
            byte[] content = Base64.decode(input, 0);
            File destDirectory = new File(Environment.getExternalStorageDirectory() + BINFILE_DIR);
            destDirectory.mkdirs();
            File dest = File.createTempFile("BinFile", "." + fileExtension, destDirectory);
            FileOutputStream outStream = new FileOutputStream(dest);
            outStream.write(content);
            outStream.close();
            String retval = dest.toURI().toASCIIString();
            trimDirectory(20, destDirectory);
            return retval;
        } catch (Exception e) {
            throw new YailRuntimeError(e.getMessage(), "Write");
        }
    }

    private static void trimDirectory(int maxSavedFiles, File directory) {
        File[] files = directory.listFiles();
        Arrays.sort(files, new C03331());
        int excess = files.length - maxSavedFiles;
        for (int i = 0; i < excess; i++) {
            files[i].delete();
        }
    }
}

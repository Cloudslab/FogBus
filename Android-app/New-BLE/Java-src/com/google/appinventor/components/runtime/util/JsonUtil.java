package com.google.appinventor.components.runtime.util;

import gnu.lists.FString;
import gnu.math.IntFraction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonUtil {
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
}

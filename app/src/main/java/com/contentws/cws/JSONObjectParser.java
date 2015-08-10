package com.contentws.cws;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kamal on 8/9/15.
 */
public class JSONObjectParser {
    public static String getString(JSONObject jsonObject, String key) {
        String value = null;
        try {
            value = jsonObject.getString(key);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } finally {
            return value;
        }
    }

    public static ArrayList<String> getStringArray(JSONObject jsonObject, String key) {
        ArrayList<String> arrayListStr = new ArrayList<String>();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            for (int i = 0; i < jsonArray.length(); i++) {
                arrayListStr.add(jsonArray.getString(i));
            }
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } finally {
            return arrayListStr;
        }
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String key) {
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray(key);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } finally {
            return jsonArray;
        }
    }

    public static JSONObject getJSONObject(JSONArray jsonArray, int index) {
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(index);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } finally {
            return jsonObject;
        }
    }

    public static int getInt(JSONObject jsonObject, String key) {
        int value = 0;
        try {
            value = jsonObject.getInt(key);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } finally {
            return value;
        }
    }

    public static boolean getBoolean(JSONObject jsonObject, String key) {
        boolean value = false;
        try {
            value = jsonObject.getBoolean(key);
        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        } finally {
            return value;
        }
    }
}

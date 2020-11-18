package com.jushi.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * SharedPreferences 缓存帮助类
 */
public class SPUtils {

    private static SPUtils spUtils;

    private final String SP_NAME = "SPUtils";
    private SharedPreferences sp;

    public static SPUtils getInstance(Context context) {
        if (spUtils == null) {
            spUtils = new SPUtils(context);
        }
        return spUtils;
    }

    private SPUtils(Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        return new JSONObject(getString(key, ""));
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
        editor.clear();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
        editor.clear();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
        editor.clear();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
        editor.clear();
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
        editor.clear();
    }

    public void putJSONObject(String key, JSONObject json) {
        putString(key, json.toString());
    }

    public void putJSONObject(String key, String json) {
        putString(key, json);
    }

}

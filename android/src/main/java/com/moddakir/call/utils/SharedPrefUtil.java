package com.moddakir.call.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefUtil {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    @SuppressLint("StaticFieldLeak")
    private static SharedPrefUtil instance = null;
    private static String preferenceName;
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    /**
     * Constructor prevents any other class from instantiating.
     */
    private SharedPrefUtil() {
        preferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();

    }

    /**
     * * Make sure that there is only one SharedPrefUtil instance.
     *
     * @param context The android Context instance.
     * @return Returns only one instance of SharedPrefUtil.
     */
    public static SharedPrefUtil getSharedPref(Context context) {

        SharedPrefUtil.context = context;
        SharedPrefUtil.preferenceName = context.getPackageName();
        if (instance == null) {
            instance = new SharedPrefUtil();
        }
        return instance;
    }

    /**
     * Write boolean preferences.
     *
     * @param preferenceName  The unique name of preference.
     * @param preferenceValue The value to save in preference.
     */
    public void write(String preferenceName, boolean preferenceValue) {

        editor.putBoolean(preferenceName, preferenceValue);
        editor.apply();
    }

    /**
     * Read boolean preferences
     *
     * @param preferenceName The unique name of preference.
     * @param defaultValue   The value if there is no saved one.
     * @return The value of saved preference.
     */
    public boolean read(String preferenceName, boolean defaultValue) {
        return preferences.getBoolean(preferenceName, defaultValue);


    }

    public float read(String preferenceName, float defaultValue) {
        return preferences.getFloat(preferenceName, defaultValue);
    }

    public void write(String preferenceName, float preferenceValue) {
        editor.putFloat(preferenceName, preferenceValue);
        editor.apply();
    }


    /**
     * Write string preferences.
     *
     * @param preferenceName  The unique name of preference.
     * @param preferenceValue The value to save in preference.
     */
    public void write(String preferenceName, String preferenceValue) {
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    /**
     * Read string preferences.
     *
     * @param preferenceName The unique name of preference.
     * @return The value of saved preference.
     */
    public String read(String preferenceName, String defaultValue) {
        return preferences.getString(preferenceName, defaultValue);
    }

    /**
     * Write integer preferences.
     *
     * @param preferenceName  The unique name of preference.
     * @param preferenceValue The value to save in preference.
     */

    public void write(String preferenceName, int preferenceValue) {

        editor.putInt(preferenceName, preferenceValue);
        editor.apply();
    }

    /**
     * Read string preferences.
     *
     * @param preferenceName The unique name of preference.
     * @return The value of saved preference.
     */

    public int read(String preferenceName, int defaultValue) {

        return preferences.getInt(preferenceName, defaultValue);
    }

    /**
     * Remove one or more preference from shared preferences.
     *
     * @param preferencesNames Name of preference(s) you want to remove
     */
    public void remove(String... preferencesNames) {
        for (String preferenceName : preferencesNames) {
            editor.remove(preferenceName);
            editor.apply();
        }
    }

    public void write(String preferenceName, ArrayList<String> preferenceValue) {
        Gson gson = new Gson();
        String json = gson.toJson(preferenceValue);
        editor.putString(preferenceName, json);
        editor.apply();

    }

    public ArrayList<String> read(String preferenceName) {
        Gson gson = new Gson();
        String json = preferences.getString(preferenceName, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


    public void clear() {
        editor.clear().apply();
    }
}






package com.moddakir.call.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class CommonPreferences {
    public static String langPrefKey = "lang";
    public static String langPref = "langPref";

    protected static String lang;

    public static void setLang(Context context, String lang) {
        SharedPreferences pref = context.getSharedPreferences(langPref, Context.MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(langPrefKey, lang); // Storing string
        editor.apply();
        CommonPreferences.lang = lang;
    }

    public static String getLang(Context context) {
        if (lang == null) {
            SharedPreferences pref = context.getSharedPreferences(langPref, Context.MODE_PRIVATE); // 0 - for private mode
            CommonPreferences.lang = pref.getString(langPrefKey, ""); // getting String

        }
        return lang;
    }


}

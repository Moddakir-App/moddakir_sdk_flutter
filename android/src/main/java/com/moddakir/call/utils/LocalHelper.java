package com.moddakir.call.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

public class LocalHelper {

    public static ContextWrapper wrap(Context context, String language) {
        changeLanguageType(context, new Locale(language));
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(getLanguageType(context));
            LocaleList localeList = new LocaleList(getLanguageType(context));
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(getLanguageType(context));
            context = context.createConfigurationContext(configuration);
        } else {
            configuration.locale = getLanguageType(context);
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return new ContextWrapper(context);
    }

    private static void changeLanguageType(Context context, Locale localelanguage) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= 24) {
            config.setLocale(localelanguage);
        } else {
            config.locale = localelanguage;
            resources.updateConfiguration(config, dm);
        }
    }

    private static Locale getLanguageType(Context context) {
        Configuration config = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= 24) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }
    }
}


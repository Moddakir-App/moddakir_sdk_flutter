package com.moddakir.call.utils;

import android.content.Context;
import android.os.Build;
import java.util.Locale;
import timber.log.Timber;

public class Helper {

    private static Locale getLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !context.getResources().getConfiguration().getLocales().isEmpty()) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    public synchronized static String getDefualtRegion(Context context) {
        try {

            return getLocale(context).getCountry();
        } catch (Exception e) {
            return "";
        }
    }
}

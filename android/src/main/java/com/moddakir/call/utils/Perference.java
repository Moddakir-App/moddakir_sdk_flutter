package com.moddakir.call.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import com.moddakir.call.R;

public class Perference extends CommonPreferences {
     public static String Pref_Name = "student";



    public static String getSinchAppKey(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Pref_Name, Context.MODE_PRIVATE); // 0 - for private mode
        String app_key = pref.getString("SINCH_APP_KEY", ""); // getting String
        return app_key;
    }



    public static AlertDialog ShowProgress(Context context) {
        AlertDialog alertDialog;
        AlertDialog.Builder builder;
        ProgressBar spin_kit;

        View view;
        if (context == null) {
            view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_progress, null, false);
            spin_kit= view.findViewById(R.id.spin_kit);
            spin_kit.setVisibility(View.VISIBLE);
            builder = new AlertDialog.Builder(context, R.style.CustomDialog);

        } else {
            view = LayoutInflater.from(context).inflate(R.layout.loading_dialog_progress, null, false);
            spin_kit= view.findViewById(R.id.spin_kit);
            spin_kit.setVisibility(View.VISIBLE);
            builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        }

        builder.setView(view);
        builder.setCancelable(false);
        alertDialog = builder.create();
        return alertDialog;
    }


}
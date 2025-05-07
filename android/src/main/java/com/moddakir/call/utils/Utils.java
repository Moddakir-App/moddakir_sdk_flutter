package com.moddakir.call.utils;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

import android.content.Context;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.moddakir.call.Constants;
import com.moddakir.call.R;
import java.math.BigDecimal;
import cn.pedant.SweetAlert.SweetAlertDialog;
public class Utils {



    public static String parseMinutesToMMss(float minutes) {

        try {
            String m = String.valueOf((int) minutes);
            float seconds = minutes - ((int) minutes);

            BigDecimal seconds_bigdecimal =
                    new BigDecimal(seconds).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            seconds = Float.parseFloat(String.valueOf(seconds_bigdecimal));
            seconds = seconds * 60;
            String s = String.valueOf((int) seconds);
            if (m.length() == 1) {
                m = "0" + m;
            }
            if (s.length() == 1) {
                s = "0" + s;
            }
            return m + ":" + s;

        } catch (Exception ex) {
            return "";
        }
    }
    public static void loadAvatar(Context context, String url, ImageView imageView) {

        if (imageView != null)
            try {
                if (context != null)
                    Glide.with(context)
                            .load(url)
                            .placeholder(R.drawable.avatar)
                            .into(imageView);
            } catch (Exception e) {
            }
    }


    public static double MaxNumber(double... items) {
        double max = 0;
        for (double item : items)
            if (max < item)
                max = item;

        return max;
    }

    public static void changeSweetAlertDialogStyle(SweetAlertDialog alertDialog, Context context) {
        Button confirm = (Button) alertDialog.findViewById(R.id.confirm_button);
        Button cancel = (Button) alertDialog.findViewById(R.id.cancel_button);
        TextView titleTV = (TextView) alertDialog.findViewById(R.id.title_text
        );
        if (titleTV != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 32);
            titleTV.setLayoutParams(params);
//            titleTV.setTextSize(Constants.DEFAULT_TEXT_SIZE);
        }
        if (confirm != null) {
            confirm.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                confirm.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimary));
            }
            confirm.setBackgroundResource(R.drawable.custom_primary_bac);
            confirm.setTextSize(COMPLEX_UNIT_DIP, Constants.DEFAULT_TEXT_SIZE);
        }

        if ((cancel != null)) {
            cancel.setBackgroundColor(ContextCompat.getColor(context, R.color.red_btn_bg_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cancel.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.red_btn_bg_color));
            }
            cancel.setBackgroundResource(R.drawable.custom_red_12_background);
            cancel.setTextSize(COMPLEX_UNIT_DIP, Constants.DEFAULT_TEXT_SIZE);

        }
    }


}

package com.moddakir.call.base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

public class ProgressDialog {
    private Dialog dialog;
    private static volatile ProgressDialog mInstance;
    private static volatile Boolean isCancelable = false;
    private ProgressBar spin_kit;

    public Boolean getCancelable() {
        return isCancelable;
    }

    public void setCancelable(Boolean cancelable) {
        isCancelable = cancelable;
    }

    public static synchronized ProgressDialog getInstance() {
        if (mInstance == null) {
            mInstance = new ProgressDialog();
        }
        return mInstance;
    }

    public void show(Context context) {
        /*dialog = new Dialog(context, R.style.CustomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.loading_dialog_progress);
        spin_kit = dialog.findViewById(R.id.spin_kit);
        spin_kit.setVisibility(View.VISIBLE);
        dialog.setCancelable(false);
        dialog.show();*/

    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            spin_kit.setVisibility(View.GONE);
            dialog.dismiss();
        }
    }
}
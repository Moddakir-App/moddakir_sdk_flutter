package com.moddakir.call.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;
import com.moddakir.call.R;
import com.moddakir.call.view.MainView;

import io.reactivex.disposables.Disposable;

public class BaseActivity extends LocalizationActivity implements MainView {
    Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale( getResources().getConfiguration());
//        Toolbar toolbar = findViewById(R.id.toolbar);
//
//        if (toolbar != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            }
//            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }

        registerActivityResults();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

    }


    @Override
    public void showAlertDialog() {
        hideAlertDialog();

        ProgressDialog.getInstance().show(this);
//        if (!isTimer) {
//            isTimer = true;
//
//            disposable = Completable.timer(30, TimeUnit.SECONDS, Schedulers.computation())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(() -> {
//                        ProgressDialog.getInstance().dismiss();
//                        isTimer = false;
//                    });
//        }
    }


    @Override
    public void hideAlertDialog() {
        ProgressDialog.getInstance().dismiss();

    }

    @Override
    public void showMessageDialog(String title, String message, boolean isCancelOutside, Runnable func) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCanceledOnTouchOutside(isCancelOutside);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                (dialog, which) -> {
                    if(func != null){
                        func.run();
                    }
                    alertDialog.cancel();
                });
        alertDialog.show();
    }

    @Override
    protected void onStop() {
        hideAlertDialog();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        hideAlertDialog();

        super.onDestroy();
//        if (alertDialog != null) {
//            alertDialog.dismiss();
//            alertDialog = null;
//        }
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                InputMethodManager manager = (InputMethodManager)
                        getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(
                        view.getWindowToken(), 0);
            }
        }
    }

//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        getWindow().getDecorView().setLayoutDirection(CommonPreferences.getLang(this).equals("ar") ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
//    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LocalHelper.wrap(newBase, CommonPreferences.getLang(newBase)));
//    }


    public void registerActivityResults() {
    }
    public  void adjustFontScale( Configuration configuration) {

        configuration.fontScale = (float) 1.0;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}

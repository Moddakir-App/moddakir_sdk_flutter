package com.moddakir.call.SinchEx;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.moddakir.call.base.BaseActivity;
import com.moddakir.call.utils.CommonPreferences;
import com.moddakir.call.utils.ConnectionStateMonitor;
import com.moddakir.call.R;
import com.moddakir.call.utils.LocalHelper;
import com.moddakir.call.utils.SensorController;

public class MainCallScreen extends BaseActivity implements SensorEventListener {
    private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 1;
    PowerManager.WakeLock wakeLock;

    SensorController sensorController;
    private WindowManager windowManager;
    private View vCallingBar;
    private WindowManager.LayoutParams params;
    Intent serviceIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sensorController = new SensorController();
        mProximity = sensorController.getProximityInstance(this);
        wakeLock = sensorController.getWakelockInsatance(this);
        ConnectionStateMonitor connectionStateMonitor = new ConnectionStateMonitor(this);
        connectionStateMonitor.observe(this, this::StatusChanged);
//        PowerManager.WakeLock screenLock = ((PowerManager)getSystemService(POWER_SERVICE)).newWakeLock(
//                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, ":com.moddakir.mytag");
//        screenLock.acquire();
//
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }
    void onForegroundServices(){
        serviceIntent= new Intent(this, MediaCaptureService.class);
        serviceIntent.putExtra("isVideoCall",false);
        ContextCompat.startForegroundService(this,serviceIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "media_channel",
                    "Media Capture Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = grantResults.length > 0;
        for (int grantResult : grantResults) {
            granted &= grantResult == PackageManager.PERMISSION_GRANTED;
        }
        if (granted) {
           // Toast.makeText(this, "", Toast.LENGTH_LONG).show();
//            onForegroundServices();
        } else {
            Toast.makeText(this, getResources().getString(R.string.permission_request), Toast.LENGTH_LONG).show();
        }

    }

    public void addOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                askDrawPermission();
            }
        }
    }

    @TargetApi(23)
    public void askDrawPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 1);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (wakeLock == null) return;
        if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
            wakeLock.acquire();
        } else {
            wakeLock.release();
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void Draw() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        vCallingBar = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.call_status, null);
        vCallingBar.setOnTouchListener((v, event) -> {
            OpenBackground();
            return false;
        });
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        }
        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                // Allows the view to be on top of the StatusBar
                LAYOUT_FLAG,
                // Keeps the button presses from going to the background window
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        // Enables the notification to recieve touch events
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,

                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP;
        windowManager.addView(vCallingBar, params);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

//            ActivityCompat.requestPermissions(
//                    this,
//                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},
//                    100
//            );
        }else {
//            onForegroundServices();
        }
    }

    protected void ClearViewDrawer(boolean isDestroyed) {
        if (vCallingBar != null) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.canDrawOverlays(this)) {
                        windowManager.removeView(vCallingBar);
                    }
                } else {
                    windowManager.removeView(vCallingBar);
                }
            } catch (Exception ex) {
                //ignore
            }
        }
        try {
            if (isDestroyed) stopService(serviceIntent);
        }catch (Exception e){

        }
    }


    @Override
    protected void onDestroy() {
        sensorController.pause();
        sensorController.getSensorManager(this).unregisterListener(this);
        ClearViewDrawer(true);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        if (wakeLock != null && mProximity != null) {
            sensorController.getSensorManager(this).registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        ClearViewDrawer(false);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorController.pause();
        sensorController.getSensorManager(this).unregisterListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                try {
                    Draw();
                } catch (Exception ex) {

                }
            }
        } else {
            try {
                Draw();
            } catch (Exception ex) {
                //ignore
            }
        }
        super.onPause();
    }

    @Override
    public void onBackPressed() {

    }

    public void OpenBackground() {
        //for subclass
    }

    protected void StatusChanged(int state) {
        //for subclass
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().getDecorView().setLayoutDirection(CommonPreferences.getLang(this).equals("ar") ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        LocalHelper.wrap(newBase, CommonPreferences.getLang(this));
    }

}
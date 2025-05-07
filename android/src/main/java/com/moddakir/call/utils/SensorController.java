package com.moddakir.call.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.PowerManager;

public class SensorController {

    private SensorManager mSensorManager;
    private Sensor mProximity;
    private static final int SENSOR_SENSITIVITY = 1;
    public static SensorController sensorController;
    public static PowerManager.WakeLock mWakeLock;

    public static SensorController getInstance() {
        if (sensorController == null) sensorController = new SensorController();
        return sensorController;
    }

    public SensorManager getSensorManager(Context context) {
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        return mSensorManager;
    }

    public Sensor getProximityInstance(Context context) {
        getSensorManager(context);
        if (mProximity == null) {
            mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }
        return mProximity;
    }


    public int buildVersion() {
        return Build.VERSION.SDK_INT;
    }

    @SuppressLint("InvalidWakeLockTag")
    public PowerManager.WakeLock getWakelockInsatance(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (mWakeLock == null) {
            if (buildVersion() >= 21) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (powerManager.isWakeLockLevelSupported(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK)) {
                        mWakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "1");
                        mWakeLock.setReferenceCounted(false);
                    }
                }
            } else {
                mWakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                        | PowerManager.ACQUIRE_CAUSES_WAKEUP, "1");
            }
        }
//        mWakeLock.acquire();
        return mWakeLock;
    }

    public void acquire() {
        mWakeLock.acquire();
    }

    public void release() {
        mWakeLock.release();
    }

    public void pause() {
//        if (mWakeLock.isHeld())
//            mWakeLock.release();
    }


}

package com.moddakir.call.SinchEx;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.moddakir.call.R;

public class MediaCaptureService extends Service {
    String message;
    boolean isVideoCall;
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize camera/microphone capture here
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent!=null){
            isVideoCall=intent.getBooleanExtra("isVideoCall",false);
            if (isVideoCall){
                message="Camera and microphone are being used.";
            }else message="microphone is being used.";
        }
        startForeground(101, createNotification());
        // Start camera and microphone tasks here
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForegroundService();
        // Release camera and microphone resources here
    }
    private void stopForegroundService() {
        // Stop the foreground service and remove the notification
        stopForeground(true);

        // Optionally, cancel the notification using NotificationManager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(101);  // Notification ID (1) should match the ID used in startForeground
        }

        // Stop the service itself
        stopSelf();
    }
    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, MainCallScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        return new NotificationCompat.Builder(this, "media_channel")
                .setContentTitle("")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    "media_channel",
                    "",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}

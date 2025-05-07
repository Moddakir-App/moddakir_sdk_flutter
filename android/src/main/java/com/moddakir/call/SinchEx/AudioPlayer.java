package com.moddakir.call.SinchEx;

import static android.content.Context.VIBRATOR_SERVICE;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;


import com.moddakir.call.R;

import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer {

    static final String LOG_TAG = "AudioPlayer";

    private Context mContext;

    private MediaPlayer mPlayer;
    private Vibrator vibrator;
    private AudioTrack mProgressTone;

    private final static int SAMPLE_RATE = 16000;

    public AudioPlayer(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void playRingtone() {
        setupMPlayer(R.raw.phone_loud1, true);
    }

    private void setupMPlayer(int resourceID, boolean isLooping) {

        AudioManager audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        // Honour silent mode
        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                mPlayer = new MediaPlayer();
//                mPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                mPlayer.setAudioStreamType(AudioManager.MODE_RINGTONE);
                try {
                    mPlayer.setDataSource(mContext,
                            Uri.parse("android.resource://" + mContext.getPackageName() + "/" + resourceID));
                    mPlayer.prepare();
                } catch (IOException e) {
                    mPlayer = null;
                    return;
                }
                mPlayer.setLooping(isLooping);
                mPlayer.start();
                break;
        }
    }

    public void playError() {
        setupMPlayer(R.raw.beep, false);

    }


    public void stopRingtone() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        vibrateDestroy();
    }

    public void playProgressTone() {
        stopProgressTone();
        try {
            mProgressTone = createProgressTone(mContext);
            mProgressTone.play();
        } catch (Exception e) {

        }
    }

    public void playEndCallSound() {
        setupMPlayer(R.raw.end_call, false);
        //vibrate();
    }

    private void vibrate() {
        vibrator = (Vibrator) mContext.getSystemService(VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            Vibrator vibrate = ((Vibrator) mContext.getSystemService(VIBRATOR_SERVICE));
            vibrate.vibrate(150);
        }
    }

    public void vibrateDestroy() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    public void playWarnningTone() {
        stopWarnningTone();
        try {
            mProgressTone = createWarnningTone(mContext);
            mProgressTone.play();
        } catch (Exception e) {

        }
    }

    public void stopWarnningTone() {
        if (mProgressTone != null) {
            mProgressTone.stop();
            mProgressTone.release();
            mProgressTone = null;
        }
    }

    public void stopProgressTone() {
        vibrateDestroy();

        if (mProgressTone != null) {
            mProgressTone.stop();
            mProgressTone.release();
            mProgressTone = null;
        }
    }

    private static AudioTrack createProgressTone(Context context) throws IOException {
        AssetFileDescriptor fd = context.getResources().openRawResourceFd(R.raw.progress_tone);
        int length = (int) fd.getLength();

        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, length, AudioTrack.MODE_STATIC);

        byte[] data = new byte[length];
        readFileToBytes(fd, data);

        audioTrack.write(data, 0, data.length);
        audioTrack.setLoopPoints(0, data.length / 2, 30);

        return audioTrack;
    }

    private static AudioTrack createWarnningTone(Context context) throws IOException {
        AssetFileDescriptor fd = context.getResources().openRawResourceFd(R.raw.disconnect);
        int length = (int) fd.getLength();
        AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_VOICE_CALL, SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, length, AudioTrack.MODE_STATIC);
        byte[] data = new byte[length];
        readFileToBytes(fd, data);

        audioTrack.write(data, 0, data.length);
        audioTrack.setLoopPoints(0, data.length / 2, 30);

        return audioTrack;
    }

    private static void readFileToBytes(AssetFileDescriptor fd, byte[] data) throws IOException {
        FileInputStream inputStream = fd.createInputStream();

        int bytesRead = 0;
        while (bytesRead < data.length) {
            int res = inputStream.read(data, bytesRead, (data.length - bytesRead));
            if (res == -1) {
                break;
            }
            bytesRead += res;
        }
    }
}

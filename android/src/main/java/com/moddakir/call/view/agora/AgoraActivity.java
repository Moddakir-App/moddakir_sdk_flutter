package com.moddakir.call.view.agora;

import static io.agora.rtm.RtmConstants.RtmConnectionState.CONNECTED;
import static io.agora.rtm.RtmConstants.RtmConnectionState.RECONNECTING;
import static io.agora.rtm.RtmConstants.RtmPresenceEventType.REMOTE_JOIN;
import static io.agora.rtm.RtmConstants.RtmPresenceEventType.REMOTE_LEAVE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.moddakir.call.API.ApiManager;
import com.moddakir.call.Adapters.ConnectingScreenAdapter;
import com.moddakir.call.Adapters.DotsIndicatorDecoration;
import com.moddakir.call.Model.BaseResponse;
import com.moddakir.call.Model.CallLog;
import com.moddakir.call.Model.ConnectingBannersResponse;
import com.moddakir.call.Model.LoginResponseModel;
import com.moddakir.call.Model.RandomTeacherResponseModel;
import com.moddakir.call.Model.User;
import com.moddakir.call.utils.MovableFrameLayout;
import com.moddakir.call.SinchEx.AudioPlayer;
import com.moddakir.call.SinchEx.MainCallScreen;
import com.moddakir.call.view.widget.ButtonCalibriBold;
import com.moddakir.call.view.widget.PlayGifView;
import com.moddakir.call.view.widget.TextViewCalibriBold;
import com.moddakir.call.view.widget.TextViewLateefRegOT;
import com.moddakir.call.view.widget.TextViewUniqueLight;
import com.moddakir.call.utils.CallStatus;
import com.moddakir.call.utils.LogFile;
import com.moddakir.call.utils.SharedPrefUtil;
import com.moddakir.call.utils.Utils;
import com.moddakir.call.Model.ConsumedPakageResponseModel;
import com.moddakir.call.Model.CreateCallResponseModel;
import com.moddakir.call.Model.ResponseModel;
import com.moddakir.call.R;
import com.moddakir.call.utils.AccountPreference;
import com.moddakir.call.utils.Perference;
import com.moddakir.call.view.evaluation.TeacherEvaluationActivity;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.MessageEvent;
import io.agora.rtm.PresenceEvent;
import io.agora.rtm.PublishOptions;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmConfig;
import io.agora.rtm.RtmConstants;
import io.agora.rtm.RtmEventListener;
import io.agora.rtm.SubscribeOptions;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Response;
import timber.log.Timber;

public class AgoraActivity extends MainCallScreen
        implements View.OnClickListener {
    private static final String LOG_TAG = "AgoraActivity";
    private static final String[] REQUESTED_PERMISSIONS =
            {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            };
    String channelName = "";
    Boolean IsConnectRTC = false;
    RtmEventListener listener;
    MovableFrameLayout container;
    LinearLayout muteLayout, speakerLayout;
    private RecyclerView bannersRV;
    PlayGifView warning, like;
    CreateCallResponseModel RTCData = null;
    int maxRingDurationMillis, NotAvailable = 0;
    private RtmClient mRtmClient;
    ConstraintLayout clConnecting;
    RelativeLayout rlCamera;
    private static final String JOINING_FROM_RESERVATION = "JoiningFromReservation";
    private static final String CREATE_CALL_RESPONSE = "create_call_response";
    boolean ismute = false, TeacherCancelled = false;
    Boolean TeacherConnected = false, connectRtc=false;
    boolean isspeaker = true, saveRetryCounts = false;
    User teacher;
    String gender, name, phone, email, language;
    boolean isShowed = false, handlerStopped = false;
    boolean isDismissShowed = false;
    boolean isProg = false;
    boolean endCallCountDown = true;
    boolean endCallInterruptionCountDown = true;
    int callDurationSeconds = 0;
    RelativeLayout errorRelative, succesRelative;
    Disposable disposable;
    private final double publishAudioLosePercentage = 0;
    private final double publishVideoLosePercentage = 0;
    private final double subscribAudioLosePercentage = 0;
    private final double subscribVideoLosePercentage = 0;
    private AudioPlayer mAudioPlayer;
    private CircleImageView civ_teacher_image1, civ_teacher_image, border;
    private RelativeLayout rlVideo;
    private RelativeLayout rlSpeaker;
    private AppCompatImageView ivConnecting;
    private AppCompatImageView ivPreparing;
    private FrameLayout warning_frame;
    private TextViewUniqueLight mCallerName, mCallDuration;
    private TextViewLateefRegOT mCallState;
    private TextViewCalibriBold mCallerName1, pleaseWait, connectingToTeacher, wellDoneTx;
    LinearLayout hang_up;
    LinearLayout re1, rel2;
    ButtonCalibriBold btnEndCall;
    private ImageButton btnEndCall1;
    ImageView btnMute, btnSpeaker;
    private View vOnCall, vCalling;
    private Timer mTimer;
    private int video_status = 1;
    int reason = 0;
    private String isVideo = "false";
    private ConsumedPakageResponseModel consumedPakageResponseModel;
    private TextViewLateefRegOT speed_status, tv_internet_status, tv_internet_status_calling;
    private SweetAlertDialog pDialog, countDownDialog, interruptionDialog;
    private CallLog callLog;
    private CreateCallResponseModel createCallResponseModel;
    private boolean isEndCallByUser = false;
    private boolean isPlanDialogShowed = false;
    private int maxCallDuration;
    private CountDownTimer maxDurationCountDown, ringDurationCountDown, startTeacherNotAvailable;
    private UpdateCallDurationTask mDurationTask;
    private AudioManager audioManager;
    private User user;
    private TextViewCalibriBold text_video, text_speaker, text_decline;
    private RelativeLayout rl_decline;
    private STATUS status;
    private STATE state = STATE.INIT;
    private Boolean ConnectionInterrupted = false;
    private boolean isVisible = false;
    private boolean isReachingTheTeacher = false;
    private boolean isHangupByStudent = false;
    private boolean isShowBandMessage = false;
    private String token = "";
    private int uid = 0;
    private boolean isJoined = false, TeacherJoin = false;
    private RtcEngine agoraEngine;
    private SurfaceView localSurfaceView;
    private SurfaceView remoteSurfaceView;
    Handler handler = new Handler();
    private int UDID = 0, primaryColor;
    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {
        @Override
        public void onUserJoined(int uid, int elapsed) {
            TeacherJoin = true;
            mAudioPlayer.stopProgressTone();

            runOnUiThread(() ->
            {
                vOnCall.setVisibility(View.VISIBLE);
                vCalling.setVisibility(View.GONE);
                isVisible = true;
                rl_decline.setVisibility(View.VISIBLE);
                re1.setVisibility(View.VISIBLE);
                rel2.setVisibility(View.VISIBLE);
                mTimer = new Timer();
                mDurationTask = new UpdateCallDurationTask();
                mTimer.schedule(mDurationTask, 0, 1000);

            });
        }

        @Override
        public void onError(int err) {
            super.onError(err);
            SendErrorCode(reason);
            state = STATE.ERORR;
            endCall();
        }

        @Override
        public void onLeaveChannel(RtcStats stats) {
            super.onLeaveChannel(stats);
        }

        @Override
        public void onConnectionInterrupted() {
            showCallInterruptedMessage(R.string.call_interrupted_message_teacher);
            super.onConnectionInterrupted();
        }

        @Override
        public void onConnectionLost() {
            super.onConnectionLost();
            SendMsg("ConnectionLost");

        }

        @Override
        public void onRejoinChannelSuccess(String channel, int uid, int elapsed) {
            super.onRejoinChannelSuccess(channel, uid, elapsed);
            isJoined = true;
            if (TeacherJoin) {
                vOnCall.setVisibility(View.VISIBLE);
                vCalling.setVisibility(View.GONE);
            }
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
            isJoined = true;
            IsConnectRTC = true;
            maxDurationCountDown();
            try {
                runOnUiThread(() -> {
                    clConnecting.setVisibility(View.GONE);
                });
            } catch (Exception e) {
            }
        }

        @Override
        public void onUserOffline(int uid, int reason) {
            if (mAudioPlayer != null) {
                mAudioPlayer.stopProgressTone();
            }
            if (isVideo.equals("true")) {
                runOnUiThread(() -> remoteSurfaceView.setVisibility(View.GONE));
            }
            if (startTeacherNotAvailable != null) startTeacherNotAvailable.cancel();
            if (callDurationSeconds > 30) {
                TeacherEvaluationActivity.start
                        (AgoraActivity.this, teacher.getId(),
                                teacher.getFullName(),
                                callLog.getCid(),
                                teacher.getAvatarurl(),
                                AccountPreference.getUser(AgoraActivity.this),

                                primaryColor);
                status = STATUS.ENDED;
                finish();
            } else {
                backToHomePage();
            }

        }

    };


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras().containsKey("isBalanceWithdraw")) {
                boolean isBalanceWithdraw = intent.getBooleanExtra("isBalanceWithdraw", false);
                if (isBalanceWithdraw) {
                    state = STATE.CANCELLED;
                    endCall();
                }
            }
            if (intent.getExtras().containsKey("CancelledCall")) {
                state = STATE.DENIED;
                endCall();
            }

        }

    };


    private void showCountDownAlertWarnning() {
        countDownDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        countDownDialog.setCancelable(true);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(600, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(600);
            }
        }
        countDownDialog.setConfirmClickListener(sweetAlertDialog -> {
            countDownDialog.dismissWithAnimation();
        });
        countDownDialog.setCancelClickListener(sweetAlertDialog -> {
            countDownDialog.dismissWithAnimation();
        });

        if (!isFinishing()) {
            countDownDialog.show();
            Utils.changeSweetAlertDialogStyle(countDownDialog, this);

        }
    }

    public void changeMute() {
        if (ismute) {
            agoraEngine.enableAudio();
            agoraEngine.adjustRecordingSignalVolume(100);
            btnMute.setBackgroundResource(R.drawable.mic_on_ic);
            btnMute.setColorFilter(ContextCompat.getColor(this, R.color.colorCallFunIconOn), android.graphics.PorterDuff.Mode.MULTIPLY);
            muteLayout.setBackground(getDrawable(R.drawable.call_functions_on_background));

            ismute = false;
        } else {
            agoraEngine.adjustRecordingSignalVolume(0);
            btnMute.setBackgroundResource(R.drawable.mic_off_ic);
            btnMute.setColorFilter(ContextCompat.getColor(this, R.color.colorCallFunIconOff), android.graphics.PorterDuff.Mode.MULTIPLY);
            muteLayout.setBackground(getDrawable(R.drawable.call_functions_off_background));
            ismute = true;
        }
    }

    public void changeSpeaker() {
        if (audioManager != null)
            if (isspeaker) {
                audioManager.setSpeakerphoneOn(false);
                isspeaker = false;
                speakerLayout.setBackground(getDrawable(R.drawable.call_functions_off_background));
                btnSpeaker.setColorFilter(ContextCompat.getColor(this, R.color.colorCallFunIconOff), android.graphics.PorterDuff.Mode.MULTIPLY);
                btnSpeaker.setBackgroundResource(R.drawable.speaker_off_ic);
            } else {
                audioManager.setSpeakerphoneOn(true);
                isspeaker = true;
                btnSpeaker.setBackgroundResource(R.drawable.speaker_on_ic);
                speakerLayout.setBackground(getDrawable(R.drawable.call_functions_on_background));
                btnSpeaker.setColorFilter(ContextCompat.getColor(this, R.color.colorCallFunIconOn), android.graphics.PorterDuff.Mode.MULTIPLY);
            }
    }

    private void createCallAndGetAvadMin(String unfreezeAccount) {
        HashMap<String, String> map = new HashMap<>();
        User user = AccountPreference.getUser(this);
        map.put("callProviderType", "agora");
        if (user != null) {
            map.put("studentId", user.getId());
            map.put("updaterId", user.getId());
            map.put("studentName", user.GetCalleeName());
            map.put("studentAvatarUrl", user.getAvatarurl());
            map.put("studentCountry", user.getCountry());
            map.put("sinchAppKey", Perference.getSinchAppKey(this));
            map.put("unfreezeAccount", unfreezeAccount);
            map.put("callType", "Voice");
        }
        if (teacher != null) {
            map.put("teacherId", teacher.getId());
            map.put("teacherName", teacher.GetCalleeName());
            map.put("teacherAvatarUrl", teacher.getAvatarurl());
        }
        map.put("status", "CREATED");
        (new ApiManager(AgoraActivity.this).getUserCalls(true)).CreateCall(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseModel -> responseModel)
                .subscribe(new DisposableSingleObserver<Response<CreateCallResponseModel>>() {
                    @Override
                    public void onSuccess(Response<CreateCallResponseModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatusCode() == 417) {
                            } else if (response.body().getStatusCode() == 200) {
                                response.body().getRingingDuration();
                                setupSignaling(response.body().getCallApiKey(), response.body().getStudentSignalingToken());
                                token = response.body().getStudentSessionToken();
                                channelName = response.body().getCall().getCallSessionId();
                                RTCData = response.body();
                                startCall(response.body());
                            } else if (response.body().getStatusCode() == 404) {

                                if (response != null && response.body() != null) {
                                    HideButtons();
                                    onServiceDisconnected(true);
                                } else {
                                    playEndCallSound();
                                    finish();
                                }
                            } else if (response.body().getStatusCode() == 406) {
                                playEndCallSound();
                                finish();
                            } else {
                                onServiceDisconnected(true);
                            }
                        } else {
                            showMessage(getResources().getString(R.string.server_error));
                            onServiceDisconnected(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof java.net.ConnectException || e instanceof java.net.UnknownHostException || e instanceof SocketTimeoutException) {
                            showMessage(getResources().getString(R.string.internetConnectionError));
                        }
                        onServiceDisconnected(true);
                    }
                });


    }

    private void startCall(CreateCallResponseModel createCallResponse) {

        if (createCallResponse.getEnableCallLogs()) {
            LogFile.init(getApplicationContext(), createCallResponse.getCall().getCid() + ".txt");
            ArrayList<String> listOfCallIds = SharedPrefUtil.getSharedPref(getApplicationContext()).read(com.moddakir.call.Constants.CALL_IDS);
            if (listOfCallIds == null || listOfCallIds.size() <= 0) {
                listOfCallIds = new ArrayList<>();
            }
            listOfCallIds.add(createCallResponse.getCall().getCid());
            SharedPrefUtil.getSharedPref(getApplicationContext()).write(com.moddakir.call.Constants.CALL_IDS, listOfCallIds);
        }
        createCallResponseModel = createCallResponse;
        consumedPakageResponseModel = createCallResponseModel.getStudentDurations();
        maxCallDuration = createCallResponseModel.getMaxCallDuration();
        callLog = createCallResponseModel.getCall();

        if (consumedPakageResponseModel != null && consumedPakageResponseModel.getRemainingMinitues() > 0) {
            CallUserSinch();
        } else {

            Toast.makeText(AgoraActivity.this, getResources().getString(R.string.dont_have_balance), Toast.LENGTH_LONG).show();
            onServiceDisconnected(true);
        }

    }

    private void connectToRTC() {
        ChannelMediaOptions options = new ChannelMediaOptions();
        options.autoSubscribeAudio = true;
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;
        agoraEngine.startPreview();
        reason = agoraEngine.joinChannelWithUserAccount(token, channelName, user.getId(), options);
    }


    private String formatTimeSpan(int totalSeconds) {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void updateCallDuration(int seconds) {
        runOnUiThread(() -> mCallDuration.setText(formatTimeSpan(seconds)));
        if (consumedPakageResponseModel != null) {
            float difftime = (consumedPakageResponseModel.getRemainingMinitues() * 60)
                    - (seconds);
            double t = ((consumedPakageResponseModel.getTotalMinitues() * 60) * 0.1);
            if (difftime <= 0) {
                isEndCallByUser = true;
                state = STATE.CANCELLED;
                endCall();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora);
        checkAndRequestAudioPermission();
        CallStatus.userCallStatus = CallStatus.CALL_STATUS.IN_CALL;
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioPlayer = new AudioPlayer(this);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(false);
        checkBultooth();
        callDurationSeconds = 0;
        status = STATUS.INIT;
        state = STATE.INIT;
        user = AccountPreference.getUser(this);
        InitViews();
        disposable = Observable.timer(3, TimeUnit.SECONDS).repeat().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).
                subscribe(aLong -> {
                    double max = Utils.MaxNumber(publishAudioLosePercentage, publishVideoLosePercentage, subscribAudioLosePercentage, subscribVideoLosePercentage);
                    if (max < 1.8 && max >= 0) {

                        tv_internet_status.setText(com.moddakir.call.R.string.internet_speed_good);
                        tv_internet_status.setBackgroundResource(R.color.colorPrimary);
                        tv_internet_status.setVisibility(View.GONE);
                    } else if (max > 1.8 && max < 4.0) {

                        tv_internet_status.setText(com.moddakir.call.R.string.internet_speed_weak);
                        tv_internet_status.setBackgroundResource(R.color.colorGray);

                        tv_internet_status.setVisibility(View.VISIBLE);
                    } else {
                        tv_internet_status.setText(com.moddakir.call.R.string.internet_speed_bad);
                        tv_internet_status.setBackgroundResource(R.color.colorDarkGray);
                        tv_internet_status.setVisibility(View.VISIBLE);

                    }
                });


    }

    private void checkBultooth() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(mReceiver, filter);
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {


            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED.equals(action)) {

            } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {

            }
        }
    };

    @Override
    protected void onStart() {
        Bundle ex = getIntent().getExtras();
        if (ex != null) {
            language = ex.getString("language");
            Perference.setLang(this, language);
        }
        super.onStart();
    }

    private void InitViews() {
        if (getIntent().getBooleanExtra("screenMode", false)) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = findViewById(R.id.tv_duration);
        warning = findViewById(R.id.warm_iv);
        like = findViewById(R.id.likebutton);
        mCallerName1 = findViewById(R.id.remoteUser1);
        pleaseWait = findViewById(R.id.please_wait);
        connectingToTeacher = findViewById(R.id.connecting_to_teacher);
        wellDoneTx = findViewById(R.id.well_done_tx);
        btnEndCall = findViewById(R.id.iv_decline);
        hang_up = findViewById(R.id.hang_up);
        btnEndCall1 = findViewById(R.id.iv_decline1);
        civ_teacher_image1 = findViewById(R.id.civ_teacher_image1);
        civ_teacher_image = findViewById(R.id.civ_teacher_image);
        border = findViewById(R.id.border);
        speed_status = findViewById(R.id.speed_status);
        tv_internet_status = findViewById(R.id.tv_internet_status);
        btnMute = findViewById(R.id.iv_mute);
        btnSpeaker = findViewById(R.id.iv_speaker);
        vCalling = findViewById(R.id.calling);
        vOnCall = findViewById(R.id.oncall);
        clConnecting = findViewById(R.id.clConnecting);
        AppCompatImageView ivClose = findViewById(R.id.ivClose);
        errorRelative = findViewById(R.id.error_relative);
        succesRelative = findViewById(R.id.success_relative);
        warning_frame = findViewById(R.id.warning_frame);
        re1 = findViewById(R.id.re1);
        rel2 = findViewById(R.id.rel2);
        muteLayout = findViewById(R.id.mute_layout);
        speakerLayout = findViewById(R.id.speaker_layout);
        rl_decline = findViewById(R.id.rl_decline);
        primaryColor = getIntent().getIntExtra("primaryColor", getColor(R.color.colorPrimary));
        PlayGifView pGif = findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.loading);
        runOnUiThread(() -> {
            ivClose.setOnClickListener(this);
            btnEndCall.setOnClickListener(this);
            btnEndCall1.setOnClickListener(this);
            hang_up.setOnClickListener(this);
            muteLayout.setOnClickListener(this);
            speakerLayout.setOnClickListener(this);
        });
        warning.setImageResource(R.drawable.look_out);
        like.setImageResource(R.drawable.well_done);
        if (primaryColor != 0) {
            civ_teacher_image1.setBorderColor(primaryColor);
            mCallDuration.setTextColor(primaryColor);
            pleaseWait.setTextColor(primaryColor);
            connectingToTeacher.setTextColor(primaryColor);
            wellDoneTx.setTextColor(primaryColor);
            if (primaryColor!=0) {
                int alpha = 128;
                int colorWithOpacity = Color.argb(alpha, Color.red(primaryColor), Color.green(primaryColor), Color.blue(primaryColor));
                mCallDuration.setBackgroundTintList(ColorStateList.valueOf(colorWithOpacity));
            }
            Drawable[] drawables = mCallDuration.getCompoundDrawablesRelative();
            Drawable drawableStart = drawables[0];
            if (drawableStart != null) {
                Drawable wrappedDrawable = DrawableCompat.wrap(drawableStart);
                DrawableCompat.setTint(wrappedDrawable, primaryColor);
                mCallDuration.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable, drawables[1], drawables[2], drawables[3]);
            }
        }

    }

    private void showingConnectingBanners(RecyclerView recyclerView) {
        (new ApiManager(AgoraActivity.this).getUserCalls(true)).getSdkBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseModel -> responseModel)
                .subscribe(new DisposableSingleObserver<Response<ConnectingBannersResponse>>() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void onSuccess(Response<ConnectingBannersResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getStatusCode() == 200) {

                                ConnectingScreenAdapter adapter = new ConnectingScreenAdapter(AgoraActivity.this, response.body().getData(), primaryColor);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setNestedScrollingEnabled(false);
                                recyclerView.setLayoutManager(new LinearLayoutManager(AgoraActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                recyclerView.setHasFixedSize(true);
                                final int radius = 10;
                                final int dotsHeight = 30;
                                if (primaryColor != 0) {
                                    recyclerView.addItemDecoration(new DotsIndicatorDecoration(radius, radius * 4, dotsHeight, primaryColor, primaryColor));
                                } else {
                                    final int color = ContextCompat.getColor(AgoraActivity.this, R.color.colorPrimary);
                                    recyclerView.addItemDecoration(new DotsIndicatorDecoration(radius, radius * 4, dotsHeight, color, color));
                                }
                                new PagerSnapHelper().attachToRecyclerView(recyclerView);

                                int count = adapter.getItemCount();
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (adapter.currentPos() < count - 1) {
                                            recyclerView.scrollToPosition(adapter.currentPos() + 1);
                                        } else {
                                            int postion = 0;
                                            recyclerView.scrollToPosition(postion);
                                        }

                                        handler.postDelayed(this, 7000);
                                    }
                                }, 7000);
                                recyclerView.setOnTouchListener((view, motionEvent) -> true);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof java.net.ConnectException || e instanceof java.net.UnknownHostException || e instanceof SocketTimeoutException) {
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_decline1 || id == R.id.ivClose) {
            isEndCallByUser = true;
            state = STATE.CANCELLED;
            endCall();
            try {
                agoraEngine.leaveChannel();
                new Thread(() -> {
                    RtcEngine.destroy();
                    agoraEngine = null;
                }).start();

            } catch (Exception e) {

            }
        } else if (id == R.id.iv_decline || id == R.id.hang_up) {
            state = STATE.CANCELLED;
            isHangupByStudent = true;
            isEndCallByUser = true;
            endCall();
            try {
                agoraEngine.leaveChannel();
                new Thread(() -> {
                    RtcEngine.destroy();
                    agoraEngine = null;
                }).start();

            } catch (Exception e) {

            }
        } else if (id == R.id.mute_layout) {
            changeMute();
        } else if (id == R.id.speaker_layout) {
            changeSpeaker();
        }
    }

    private void SendErrorCode(int reason) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("sessionId", RTCData.getCall().getCallSessionId());
        map.put("reason", reason);
        (new ApiManager(AgoraActivity.this).getUserCalls(true)).endReason(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseModel -> responseModel)
                .subscribe(new DisposableSingleObserver<BaseResponse>() {
                    @Override
                    public void onSuccess(@NonNull BaseResponse freezeAccountResponseModel) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }

    private void HideButtons() {
        btnEndCall.setVisibility(View.GONE);
        btnEndCall1.setVisibility(View.GONE);
        btnSpeaker.setVisibility(View.GONE);
        btnMute.setVisibility(View.GONE);
        rel2.setVisibility(View.GONE);
        re1.setVisibility(View.GONE);
        rl_decline.setVisibility(View.GONE);
        vOnCall.setVisibility(View.GONE);
        vCalling.setVisibility(View.VISIBLE);
    }

    private void playEndCallSound() {
        if (mAudioPlayer != null) {
            audioManager.setSpeakerphoneOn(true);
            mAudioPlayer.playEndCallSound();
        }
    }

    private void endCall() {
        handlerStopped = true;
        SendMsg("endCall");
        HideButtons();
        if (disposable != null) disposable.dispose();
        CallStatus.userCallStatus = CallStatus.CALL_STATUS.IDLE;
        playEndCallSound();
        if (mDurationTask != null)
            mDurationTask.cancel();
        if (mTimer != null)
            mTimer.cancel();
        if (ringDurationCountDown != null) {
            ringDurationCountDown.cancel();
        }
        if (mAudioPlayer != null)
            mAudioPlayer.stopProgressTone();
        if (maxDurationCountDown != null)
            maxDurationCountDown.cancel();

        if (startTeacherNotAvailable != null) startTeacherNotAvailable.cancel();


        if (status == STATUS.ENDED) {
            finish();
            return;
        }
        status = STATUS.ENDED;
        if (state == STATE.NO_ANSWER) {
            requestUpdateCall("NO_ANSWER");
            showAplogizeMessage(R.string.apologize);
            return;
        }
        if (state == STATE.ERORR || state == STATE.INIT || state == STATE.DENIED && !isReachingTheTeacher) {
            showAplogizeMessage(R.string.apologize);
            return;
        }
        if (state == STATE.CANCELLED) {
            if (callDurationSeconds >= 30) {
                requestUpdateCall("HUNG_UP");
                TeacherEvaluationActivity.start
                        (this, teacher.getId(), teacher.getFullName(), callLog.getCid(), teacher.getAvatarurl(), AccountPreference.getUser(AgoraActivity.this), primaryColor);
                finish();
            } else if (callDurationSeconds == 0) {
                requestUpdateCall("CANCELED");
                onServiceDisconnected(false);
            } else {
                requestUpdateCall("HUNG_UP");
                onServiceDisconnected(false);
            }
            return;
        }
        if (mRtmClient != null) {
            logout();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        CallStatus.userCallStatus = CallStatus.CALL_STATUS.IDLE;
        if (startTeacherNotAvailable != null) startTeacherNotAvailable.cancel();

        if (pDialog != null) {
            pDialog.dismiss();
        }
        if (mDurationTask != null)
            mDurationTask.cancel();

        if (maxDurationCountDown != null) maxDurationCountDown.cancel();
        if (ringDurationCountDown != null) ringDurationCountDown.cancel();
        if (mTimer != null)
            mTimer.cancel();
        if (countDownDialog != null) {
            countDownDialog.dismiss();
        }
        if (interruptionDialog != null) {
            interruptionDialog.dismiss();
        }
        if (disposable != null && !disposable.isDisposed())
            disposable.dispose();

        if (mAudioPlayer != null) {
            mAudioPlayer.stopRingtone();
            mAudioPlayer.stopProgressTone();

        }
        if ((Vibrator) AgoraActivity.this.getSystemService(VIBRATOR_SERVICE) != null) {
            ((Vibrator) AgoraActivity.this.getSystemService(VIBRATOR_SERVICE)).cancel();
        }
        try {
            if (mRtmClient != null) {
                logout();
            }

        } catch (Exception e) {
        }
    }

    @Override
    protected void StatusChanged(int integer) {
        if (integer == 1) {
            speed_status.setText(com.moddakir.call.R.string.internet_speed_low);
            tv_internet_status.setText(com.moddakir.call.R.string.internet_speed_low);
            tv_internet_status.setBackgroundResource(R.color.colorGray);
            speed_status.setBackgroundResource(R.color.colorGray);
        } else if (integer == 2) {
            speed_status.setText(com.moddakir.call.R.string.internet_speed_good);
            tv_internet_status.setText(com.moddakir.call.R.string.internet_speed_good);
            tv_internet_status.setBackgroundResource(R.color.colorPrimary);
            speed_status.setBackgroundResource(R.color.colorPrimary);
        } else if (integer == 0) {
            speed_status.setText(com.moddakir.call.R.string.no_internet_speed);
            tv_internet_status.setText(com.moddakir.call.R.string.no_internet_speed);
            tv_internet_status.setBackgroundResource(R.color.colorDarkGray);
            speed_status.setBackgroundResource(R.color.colorDarkGray);
        }
    }

    @Override
    public void OpenBackground() {
        Intent openMainActivity = new Intent(getApplicationContext(), AgoraActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(openMainActivity, 0);
    }

    protected void onServiceDisconnected(boolean playEndCallSound) {
        if (playEndCallSound)
            playEndCallSound();
        if (createCallResponseModel != null)
            Observable.timer(createCallResponseModel.getDelayTimeAfterEndingCall(), TimeUnit.SECONDS).subscribe(aLong -> {
                backToHomePage();
                finish();

            });
        else {
            Observable.timer(4, TimeUnit.SECONDS).subscribe(aLong -> {
                backToHomePage();
                finish();

            });
        }
    }

    private void maxDurationCountDown() {
        long maxCallDurationMillis = maxCallDuration * 60 * 1000L;
        endCallCountDown = true;
        runOnUiThread(() -> maxDurationCountDown = new CountDownTimer(maxCallDurationMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if ((millisUntilFinished / 1000) <= 30 && (millisUntilFinished / 1000) > 29) {
                    showCountDownAlertWarnning();
                }
            }

            @Override
            public void onFinish() {
                if (!endCallCountDown) {
                    maxDurationCountDown();
                } else {
                    isEndCallByUser = true;
                    state = STATE.CANCELLED;
                    endCall();
                }
            }

        }.start());

    }

    private void RingDurationCountDown() {
        maxRingDurationMillis = createCallResponseModel.getRingingDuration() * 1000;
        ringDurationCountDown = new CountDownTimer(maxRingDurationMillis, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                state = STATE.NO_ANSWER;
                endCall();
            }
        }.start();
    }

    private void CallUserSinch() {
        if (status == STATUS.ENDED && isEndCallByUser) {
            requestUpdateCall("CANCELED");
            return;
        } else if (status == STATUS.ENDED) {
            return;
        }
        if (teacher != null) {
            mCallerName1.setText(teacher.GetCalleeName());
            Utils.loadAvatar(this, teacher.getAvatarurl(), civ_teacher_image);
            Utils.loadAvatar(this, teacher.getAvatarurl(), civ_teacher_image1);
            User user = AccountPreference.getUser(AgoraActivity.this);
            if (user != null) {
                Map<String, String> headers = new HashMap<>();
                if (user.getAvatarurl() != null && !user.getAvatarurl().trim().isEmpty())
                    headers.put("avatarUrl", user.getAvatarurl());
                headers.put("displayName", user.GetCalleeName());
                headers.put("isVideo", isVideo);
                if (callLog != null) {
                    headers.put("callId", callLog.getCid());
                }
            }

        }
        LogFile.d(LOG_TAG, "onConnectedSession");
        startTeacherNotAvailable = new CountDownTimer(createCallResponseModel.getRingingDuration() * 1000L, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                mAudioPlayer.stopProgressTone();
                TeacherConnected = false;
                status = STATUS.INIT;
            }

        }.start();
    }

    private void backToHomePage() {
        CallStatus.userCallStatus = CallStatus.CALL_STATUS.IDLE;
        finish();
    }

    private void showAplogizeMessage(int message) {
        if (!((Activity) AgoraActivity.this).isFinishing())
            return;
        SweetAlertDialog pDialog = new SweetAlertDialog(AgoraActivity.this, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText(getResources().getString(message));
        pDialog.setCancelable(false);
        pDialog.setConfirmText(getResources().getString(R.string.ok));
        pDialog.setConfirmClickListener(sweetAlertDialog -> {
            pDialog.dismissWithAnimation();
            mAudioPlayer.stopProgressTone();
            finish();
        });
        pDialog.show();
        Utils.changeSweetAlertDialogStyle(pDialog, this);
    }

    private void requestUpdateCall(String status) {
        HashMap<String, Object> map = new HashMap<>();
        if (callLog != null) map.put("callId", callLog.getCid());
        map.put("status", status);
        if (isEndCallByUser) {
            map.put("isHangupByTeacher", false);
        }
        if (isHangupByStudent) {
            map.put("isHangupByStudent", true);
        }
        map.put("lang", Perference.getLang(this));
        (new ApiManager(AgoraActivity.this).getUserCalls(true)).UpdateCall(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseModel -> responseModel)
                .subscribe(new DisposableSingleObserver<Response<ResponseModel>>() {
                    @Override
                    public void onSuccess(Response<ResponseModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private enum STATUS {INIT, RING, INCALL, ENDED}


    private enum STATE {INIT, ENDED, ERORR, CANCELLED, NO_ANSWER, DENIED, NOTAVA}

    private class UpdateCallDurationTask extends TimerTask {
        @Override
        public void run() {
            isReachingTheTeacher = true;
            callDurationSeconds += 1;
            AgoraActivity.this.runOnUiThread(() ->
                    updateCallDuration(callDurationSeconds));
        }
    }


    private void showCallInterruptedMessage(int message) {
        SweetAlertDialog pDialog = new SweetAlertDialog(AgoraActivity.this, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText(getResources().getString(message));
        pDialog.setCancelable(false);
        pDialog.setConfirmText(getResources().getString(R.string.ok));
        pDialog.setConfirmClickListener(sweetAlertDialog -> {
            pDialog.dismiss();
        });
        pDialog.show();
        Utils.changeSweetAlertDialogStyle(pDialog, this);

    }

    void showMessage(String message) {
        runOnUiThread(() ->
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show());
    }
    private void setupVideoSDKEngine(String callApiKey) {
        try {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = callApiKey;
            config.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(config);
            agoraEngine.setAINSMode(true, 0);

        } catch (Exception e) {
        }
    }

    private void setupSignaling(String AppID, String token) {

        listener = new RtmEventListener() {

            @Override
            public void onMessageEvent(MessageEvent event) {
                String text = event.getMessage().getData().toString();
                updateRtmLogsApi("RtmEventListener- onMessageEvent: " + text);
                if (text.equals("error")) {
                    mAudioPlayer.playError();
                    runOnUiThread(() -> {
                        warning_frame.setBackgroundColor(getResources().getColor(R.color.black40opacity));
                        errorRelative.setVisibility(View.VISIBLE);
                    });

                    Observable.timer(3, TimeUnit.SECONDS).subscribe(aLong -> {
                        runOnUiThread(() -> {
                            warning_frame.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                            errorRelative.setVisibility(View.GONE);
                            mAudioPlayer.stopProgressTone();
                        });
                    });

                } else if (text.equals("succeed")) {
                    runOnUiThread(() -> {

                        warning_frame.setBackgroundColor(getResources().getColor(R.color.transparent));

                        succesRelative.setVisibility(View.VISIBLE);
                    });

                    Observable.timer(3, TimeUnit.SECONDS).subscribe(aLong -> {
                        runOnUiThread(() -> {
                            warning_frame.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                            succesRelative.setVisibility(View.GONE);
                        });
                    });
                } else if (text.equals("reachingTheTeacher")) {
                    mAudioPlayer.playProgressTone();
                    TeacherConnected = true;
                    status = STATUS.RING;
                    RingDurationCountDown();
                } else if (text.equals("ConnectRTC")) {
                    connectRtc = true;
                    setupVideoSDKEngine(RTCData.getCallApiKey());
                    connectToRTC();
                    if (startTeacherNotAvailable != null) {
                        startTeacherNotAvailable.cancel();
                    }
                } else if (text.equals("CancelledCall")) {
                    TeacherCancelled = true;
                } else if (text.equals("ConnectionLost")) {
                    tv_internet_status.setVisibility(View.VISIBLE);
                    tv_internet_status.setText(R.string.trying_reach_teacher);
                    tv_internet_status.setBackgroundResource(R.color.colorBusy);
                } else if (text.equals("CallCancelled")) {
                    TeacherCancelled = true;
                }
                if (text.equals("Hangup")) {
                    if (!TeacherCancelled) {
                        mAudioPlayer.stopProgressTone();
                        runOnUiThread(() -> {
                            if (connectRtc) {
                                status = STATUS.ENDED;
                                endCall();
                            }
                        });
                    }
                }
            }


            @Override
            public void onConnectionStateChanged(String channelName, RtmConstants.RtmConnectionState state, RtmConstants.RtmConnectionChangeReason reason) {
                updateRtmLogsApi("RtmEventListener- onConnectionStateChanged: " + "state-" + state.toString() + " reason-" + reason.toString());
                if (state == RECONNECTING) {
                    ConnectionInterrupted = true;
                    tv_internet_status_calling.setText(R.string.internet_speed_bad);
                    tv_internet_status_calling.setBackgroundResource(R.color.colorDarkGray);
                    tv_internet_status_calling.setVisibility(View.VISIBLE);

                }
                if (state == CONNECTED) {
                    if (ConnectionInterrupted) {
                        tv_internet_status_calling.setVisibility(View.GONE);
                        SendMsg("ReconnectRtc");
                        ConnectionInterrupted = false;
                    }

                }

            }

            @Override
            public void onPresenceEvent(PresenceEvent event) {
                updateRtmLogsApi("RtmEventListener- onPresenceEvent: " + event.getEventType().toString());
                if (event.getEventType() == REMOTE_JOIN) {
                }
                if (event.getEventType() == REMOTE_LEAVE) {
                    if (!IsConnectRTC) {
                        if (startTeacherNotAvailable != null) startTeacherNotAvailable.cancel();
                        if (callDurationSeconds > 30) {
                            TeacherEvaluationActivity.start
                                    (AgoraActivity.this, teacher.getId(),
                                            teacher.getFullName(),
                                            callLog.getCid(),
                                            teacher.getAvatarurl(),
                                            AccountPreference.getUser(AgoraActivity.this),
                                            primaryColor);
                            status = STATUS.ENDED;
                            if (mRtmClient != null) {
                                logout();
                            }

                        } else {
                            if (callDurationSeconds != 0) {
                                backToHomePage();
                                finish();
                            } else {
                                saveRetryCounts=false;
                                logout();
                            }
                        }

                    } else {
                        tv_internet_status.setVisibility(View.VISIBLE);
                        tv_internet_status.setText(R.string.trying_reach_teacher);
                        tv_internet_status.setBackgroundResource(R.color.colorBusy);
                    }

                } else if (event.getInterval().getJoinUserList().size() == 2 && TeacherJoin) {
                    if (vCalling.getVisibility() == View.VISIBLE) {
                        handleView();
                    }
                }
            }
        };
        try {
            RtmConfig rtmConfig = new RtmConfig.Builder(AppID, user.getId())
                    .eventListener(listener)
                    .build();
            updateRtmLogsApi("setupSignaling- rtmConfig: try");
            try {
                mRtmClient = RtmClient.create(rtmConfig);
                updateRtmLogsApi("setupSignaling- mRtmClient: try");
            } catch (Exception e) {
                updateRtmLogsApi("setupSignaling- mRtmClient ex: " + e.getMessage());
                e.printStackTrace();
            }

            mRtmClient.addEventListener(listener);
            mRtmClient.login(token, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void responseInfo) {
                    updateRtmLogsApi("setupSignaling- mRtmClient.login: Success");
                    JoinSignalingChannel();
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    updateRtmLogsApi("setupSignaling- mRtmClient.login ex: " + errorInfo.getErrorReason());
                    CharSequence text = "User: " + errorInfo.getErrorReason() + " failed to log in to Signaling!";
                }
            });


        } catch (Exception e) {
            updateRtmLogsApi("setupSignaling- rtmConfig ex: " + e.getMessage());
        }

    }

    private void handleView() {
        mAudioPlayer.stopProgressTone();
        civ_teacher_image1.setVisibility(View.VISIBLE);
        runOnUiThread(() ->
        {
            vOnCall.setVisibility(View.VISIBLE);
            vCalling.setVisibility(View.GONE);
            isVisible = true;
            rl_decline.setVisibility(View.VISIBLE);
            re1.setVisibility(View.VISIBLE);
            rel2.setVisibility(View.VISIBLE);
            mTimer = new Timer();
            mDurationTask = new UpdateCallDurationTask();
            mTimer.schedule(mDurationTask, 0, 1000);

        });


    }

    private void updateRtmLogsApi(String message) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        HashMap<String, Object> map = new HashMap<>();
        map.put("logs", message);
        if (createCallResponseModel != null)
            map.put("sessionId", createCallResponseModel.getCall().getCallSessionId());
        compositeDisposable.add((
                        (new ApiManager(AgoraActivity.this).getUserCalls(true)).updateRtmLogs(map)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .map(responseModel -> responseModel)
                                .subscribe(response -> {
                                    if (response.getStatusCode() == 200) {

                                    }
                                }, e -> {
                                    if (e instanceof java.net.ConnectException || e instanceof java.net.UnknownHostException) {
                                        if (this != null) {
                                            Toast.makeText(this, this.getResources().getString(R.string.internetConnectionError), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                )
        );
    }

    public void logout() {
        if (mRtmClient != null) {
            mRtmClient.removeEventListener(listener);

            mRtmClient.unsubscribe(channelName, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void responseInfo) {
                    mRtmClient.logout(new ResultCallback<Void>() {
                        @Override
                        public void onSuccess(Void responseInfo) {
                            updateRtmLogsApi("mRtmClient.logout onSuccess: success");
                            if (connectRtc) finish();
                            else getRandomTeacher();

                        }

                        @Override
                        public void onFailure(ErrorInfo errorInfo) {
                            updateRtmLogsApi("mRtmClient.logout onFailure: reason-" + errorInfo.getErrorReason() + " code-" + errorInfo.getErrorCode());
                        }
                    });
                    updateRtmLogsApi("mRtmClient.unsubscribe onSuccess: success");
                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                    updateRtmLogsApi("mRtmClient.unsubscribe onFailure: reason-" + errorInfo.getErrorReason() + " code-" + errorInfo.getErrorCode());
                    CharSequence text = "User: " + errorInfo.getErrorReason();
                }
            });
            if (agoraEngine != null) {
                agoraEngine.leaveChannel();
                updateRtmLogsApi("agoraEngine.leaveChannel : left");
            }
            try {
                new Thread(() -> {
                    RtcEngine.destroy();
                    updateRtmLogsApi("RtcEngine.destroy : destroyed");
                    RtmClient.release();
                    updateRtmLogsApi("RtmClient.release : released");
                    agoraEngine = null;
                }).start();
            } catch (Exception e) {
                updateRtmLogsApi("RtcEngine.destroy/release failed: " + e.getMessage());
            }

        }

    }

    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[1]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void JoinSignalingChannel() {
        SubscribeOptions options = new SubscribeOptions();
        options.setWithMessage(true);
        options.setWithPresence(true);
        options.setWithMetadata(false);
        options.setWithLock(false);

        mRtmClient.subscribe(channelName, options, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                updateRtmLogsApi("JoinSignalingChannel- mRtmClient.subscribe: Success");
                HashMap<String, Object> map = new HashMap<>();
                if (RTCData != null) map.put("sessionId", RTCData.getCall().getCallSessionId());
                (new ApiManager(AgoraActivity.this).getUserCalls(true)).JoinAgoraSignaling(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(responseModel -> responseModel)
                        .subscribe(new DisposableSingleObserver<Response<BaseResponse>>() {
                            @Override
                            public void onSuccess(Response<BaseResponse> response) {
                                if (response.isSuccessful() && response.body() != null) {

                                    if (response.body().getStatusCode() == 200) {

                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                                if (e instanceof java.net.ConnectException || e instanceof java.net.UnknownHostException || e instanceof SocketTimeoutException) {
                                }
                            }
                        });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                updateRtmLogsApi("JoinSignalingChannel- mRtmClient.subscribe failure: " + errorInfo.getErrorReason() + " failed to log in to Signaling!");
                CharSequence text = "User: " + errorInfo.getErrorReason() + " failed to log in to Signaling!";

            }
        });

    }

    private void SendMsg(String msg) {
        PublishOptions options = new PublishOptions();
        if (mRtmClient != null) {
            mRtmClient.publish(channelName, msg, options, new ResultCallback<Void>() {
                @Override
                public void onSuccess(Void responseInfo) {

                }

                @Override
                public void onFailure(ErrorInfo errorInfo) {
                }
            });
        }

    }

    private void loginSDK(String gender, String name, String phone, String email) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("gender", gender);
        map.put("fullName", name);
        if (!phone.isEmpty()) {
            map.put("phone", phone);
        }
        map.put("email", email);
        (new ApiManager(AgoraActivity.this).getUserCalls(false)).loginToSdk(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseModel -> responseModel)
                .subscribe(new DisposableSingleObserver<Response<LoginResponseModel>>() {
                    @Override
                    public void onSuccess(Response<LoginResponseModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatusCode() == 200) {
                                User currentUser = response.body().getStudent();
                                currentUser.setAccessToken(response.body().getAccessToken());
                                AccountPreference.registerData(currentUser, AgoraActivity.this);
                                user = currentUser;
                                getRandomTeacher();
                                bannersRV = findViewById(R.id.connecting_screen);
                                bannersRV.setLayoutManager(new LinearLayoutManager(AgoraActivity.this, LinearLayoutManager.HORIZONTAL, true));
                                showingConnectingBanners(bannersRV);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });


    }

    private void getRandomTeacher() {
        (new ApiManager(AgoraActivity.this).getUserCalls(true)).getRandomTeacher()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(responseModel -> responseModel)
                .subscribe(new DisposableSingleObserver<Response<RandomTeacherResponseModel>>() {
                    @Override
                    public void onSuccess(Response<RandomTeacherResponseModel> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().getStatusCode() == 200) {
                                User teacherModel = new User();
                                if (response.body().getTeacher() != null) {
                                    teacher = teacherModel;
                                    teacher = response.body().getTeacher();
                                    if (teacher != null) {
                                        mCallerName1.setText(teacher.GetCalleeName());
                                        Utils.loadAvatar(AgoraActivity.this, teacher.getAvatarurl(), civ_teacher_image);
                                        Utils.loadAvatar(AgoraActivity.this, teacher.getAvatarurl(), civ_teacher_image1);
                                        createCallAndGetAvadMin("false");
                                        handler=new Handler();
                                    }
                                } else {
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            if (!handlerStopped) {
                                                if (!saveRetryCounts) {
                                                    int retryDuration = response.body().getRetryDuration();
                                                    NotAvailable = retryDuration / 10;
                                                    saveRetryCounts = true;
                                                }
                                                if (NotAvailable > 0) {
                                                    getRandomTeacher();
                                                    NotAvailable--;
                                                } else {
                                                    showMessage(getResources().getString(R.string.no_teachers));
                                                    finish();
                                                }
                                            }
                                        }
                                    }, 10000);
                                }

                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public static void makeCall(String moddakirId, String moddakirKey, Context context, String gender,
                                String name, String phone, String email, String language,
                                boolean isLight, Integer primaryColor) {
        Boolean validEmail = false;
        if (!isLight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        gender = gender.toLowerCase();
        language = language.toLowerCase();
        com.moddakir.call.Constants.setAppID(moddakirId);
        com.moddakir.call.Constants.setAppKey(moddakirKey);
        if (moddakirId.isEmpty() || moddakirKey.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.please_contact_moddakir_support), Toast.LENGTH_LONG).show();
            return;
        }
        if (email != null) {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                validEmail = true;
            }
        }
        if (!gender.equals("male") && !gender.equals("female")) {
            Toast.makeText(context, context.getString(R.string.valid_gender), Toast.LENGTH_LONG).show();
        } else if (name.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.name_req), Toast.LENGTH_LONG).show();
        } else if (email.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.email_req), Toast.LENGTH_LONG).show();
        } else if (!phone.isEmpty() && phone.length() < 6) {
            Toast.makeText(context, context.getString(R.string.valid_phone), Toast.LENGTH_LONG).show();
        } else if (!validEmail) {
            Toast.makeText(context, context.getString(R.string.valid_email), Toast.LENGTH_LONG).show();
        } else if (!language.equals("ar") && !language.equals("en") && !language.equals("fr") && !language.equals("in") && !language.equals("ur")) {
            Toast.makeText(context, context.getString(R.string.valid_language), Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(context, AgoraActivity.class);
            intent.putExtra("language", language);
            intent.putExtra("email", email);
            intent.putExtra("gender", gender);
            intent.putExtra("name", name);
            intent.putExtra("phone", phone);
            intent.putExtra("screenMode", isLight);
            intent.putExtra("primaryColor", primaryColor);
            context.startActivity(intent);
        }
    }

    private void requestOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 1002);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!Settings.canDrawOverlays(this)) {
                    requestOverlayPermission();
                } else {
                    StartSdk();
                }
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Permission Denied")
                            .setMessage("Audio recording permission is necessary. Please enable it in app settings.")
                            .setPositiveButton("Open Settings", (dialog, which) -> {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> {
                                Toast.makeText(this, getResources().getString(R.string.permission_request), Toast.LENGTH_LONG).show();
                                finish();
                            })
                            .show();
                } else {

                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == 1002) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                StartSdk();
            }
        }
    }

    private void checkAndRequestAudioPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1001);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, 1001);
            }
        } else {
            if (!Settings.canDrawOverlays(this)) {
                requestOverlayPermission();
            } else {
                StartSdk();
            }
        }
    }

    public void addOverlay() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                askDrawPermission();
            } else {
                StartSdk();
            }
        } else {
            StartSdk();
        }
    }

    @TargetApi(23)
    public void askDrawPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 1);
    }

    private void StartSdk() {
        Bundle ex = getIntent().getExtras();
        if (ex != null) {
            gender = ex.getString("gender");
            name = ex.getString("name");
            phone = ex.getString("phone");
            email = ex.getString("email");

            if (EasyPermissions.hasPermissions(AgoraActivity.this, REQUESTED_PERMISSIONS)) {
                loginSDK(gender, name, phone, email);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    loginSDK(gender, name, phone, email);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1002){
            if (Settings.canDrawOverlays(this)) {
                StartSdk();
            } else {
                Toast.makeText(this, "Overlay permission is required to proceed.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}

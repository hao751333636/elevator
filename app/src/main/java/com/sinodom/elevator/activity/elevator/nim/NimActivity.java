package com.sinodom.elevator.activity.elevator.nim;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatEventType;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoQuality;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatCalleeAckEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatCommonEvent;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatNotifyOption;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.avchat.video.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.video.AVChatTextureViewRenderer;
import com.netease.nimlib.sdk.avchat.video.AVChatVideoCapturerFactory;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.AVChatTimeoutObserver;
import com.sinodom.elevator.activity.elevator.nim.utils.NimCache;
import com.sinodom.elevator.activity.elevator.nim.utils.Preferences;
import com.sinodom.elevator.activity.elevator.nim.utils.SimpleAVChatStateObserver;
import com.sinodom.elevator.activity.sys.LoginActivity;
import com.sinodom.elevator.service.HeartbeatService;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.single.SoundPoolManager;
import com.sinodom.elevator.util.ActivityCollector;
import com.sinodom.elevator.util.SharedPreferencesUtil;
import com.sinodom.elevator.util.TextUtil;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//云视频
public class NimActivity extends BaseActivity {

    private static final String TAG = "NimActivity";
    @BindView(R.id.remotePreviewLayout)
    FrameLayout remotePreviewLayout;
    @BindView(R.id.localPreviewLayout)
    FrameLayout localPreviewLayout;
    @BindView(R.id.bReceive)
    ImageView bReceive;
    @BindView(R.id.ivBg)
    ImageView ivBg;
    @BindView(R.id.tvText)
    TextView tvText;
    private AVChatTextureViewRenderer localRender;
    private AVChatTextureViewRenderer remoteRender;
    protected AVChatCameraCapturer mVideoCapturer;
    protected AVChatData mAVChatData;
    protected String mAccount;
    //震动-声音
    private Vibrator vibrator;
    private int streamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nim);
        ButterKnife.bind(this);
        mAVChatData = null;
        mAVChatData = (AVChatData) getIntent().getSerializableExtra("bean");
        mAccount = getIntent().getStringExtra("account");
        AVChatManager.getInstance().observeCalleeAckNotification(callAckObserver, true);
        AVChatManager.getInstance().observeAVChatState(avchatStateObserver, true);
        AVChatManager.getInstance().observeHangUpNotification(callHangupObserver, true);
        AVChatTimeoutObserver.getInstance(context).observeTimeoutNotification(timeoutObserver, true, true);
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(onlineObserver, true);
        localRender = new AVChatTextureViewRenderer(context);
        remoteRender = new AVChatTextureViewRenderer(context);
        //权限申请
        permission();
    }

    //渐进式
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mAudioManager.adjustStreamVolume(
                        AudioManager.STREAM_VOICE_CALL,
                        AudioManager.ADJUST_LOWER,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;

            case KeyEvent.KEYCODE_VOLUME_UP:
                mAudioManager.adjustStreamVolume(
                        AudioManager.STREAM_VOICE_CALL,
                        AudioManager.ADJUST_RAISE,
                        AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                return true;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void init() {
        try {
            if (mAVChatData != null) {
                dismissKeyguard();
                bReceive.setVisibility(View.VISIBLE);
                ivBg.setVisibility(View.VISIBLE);
                tvText.setVisibility(View.VISIBLE);
                //震动
                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {2000, 3000, 2000, 3000, 2000, 3000, 2000, 3000, 2000, 3000, 2000, 3000};   // 停止 开启 停止 开启
                vibrator.vibrate(pattern, -1);           //如果只想震动一次，index设为-1
                //声音
                streamId = SoundPoolManager.init(context).getSoundPool().play(5, 1, 1, 0, 10, 1);
                //权限申请
//                initReceiveVideo();
            } else if (!TextUtil.isEmpty(mAccount)) {
                initCallVideo(mAccount);
                bReceive.setVisibility(View.GONE);
                ivBg.setVisibility(View.VISIBLE);
                tvText.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.bReceive, R.id.bUnReceive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bReceive:
                //销毁声音-震动
                SoundPoolManager.init(context).getSoundPool().stop(streamId);
                vibrator.cancel();
                initReceiveVideo();
                break;
            case R.id.bUnReceive:
                finish();
                break;
        }
    }

    // 拨打电话
    public static void outgoingCall(Context context, String account) {
        Intent intent = new Intent();
        intent.setClass(context, NimActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("account", account);
        context.startActivity(intent);
    }

    // 接听来电
    public static void incomingCall(Context context, AVChatData config) {
        Intent intent = new Intent();
        intent.setClass(context, NimActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("bean", config);
        context.startActivity(intent);
    }

    private void initNim() {
        //开启音视频引擎
        AVChatManager.getInstance().enableRtc();
        //设置通话可选参数
        AVChatParameters parameters = new AVChatParameters();
//        //语音前处理，回音抑制, 用来消除语音通话的回音。
//        parameters.setRequestKey(AVChatParameters.KEY_AUDIO_EFFECT_ACOUSTIC_ECHO_CANCELER);
//        //语音前处理，降噪，用来消除语音通话的背景噪音
//        parameters.setRequestKey(AVChatParameters.KEY_AUDIO_EFFECT_NOISE_SUPPRESSOR);
        //默认前置摄像头采集。
//        parameters.setBoolean(AVChatParameters.KEY_VIDEO_DEFAULT_FRONT_CAMERA, true);
        //视频绘制时自动旋转。
        parameters.setBoolean(AVChatParameters.KEY_VIDEO_ROTATE_IN_RENDING, false);
        //视频清晰度。
        parameters.setInteger(AVChatParameters.KEY_VIDEO_QUALITY, AVChatVideoQuality.QUALITY_DEFAULT);
        AVChatManager.getInstance().setParameters(parameters);
        //视频通话设置
        AVChatManager.getInstance().enableVideo();
        //创建视频采集模块并且设置到系统中
        if (mVideoCapturer == null) {
            mVideoCapturer = AVChatVideoCapturerFactory.createCameraCapturer(true);
            AVChatManager.getInstance().setupVideoCapturer(mVideoCapturer);
        }
        //开启视频预览
        AVChatManager.getInstance().startVideoPreview();
    }

    private void initCallVideo(String account) {
        AVChatNotifyOption notifyOption = new AVChatNotifyOption();
        notifyOption.extendMessage = "extra_data";
        initNim();
        //发起视频
        AVChatManager.getInstance().call2(account, AVChatType.VIDEO, notifyOption, new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData data) {
                initSurfaceView(NimCache.getAccount());
                mAVChatData = data;
            }

            @Override
            public void onFailed(int code) {
                if (code == ResponseCode.RES_FORBIDDEN) {
                    Toast.makeText(context, "没有权限", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "呼叫失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
            }
        });
    }

    public void initReceiveVideo() {
        initNim();
        //接收视频
        AVChatManager.getInstance().accept2(mAVChatData.getChatId(), new AVChatCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                initSurfaceView(NimCache.getAccount());
            }

            @Override
            public void onFailed(int code) {
                if (code == -1) {
                    Toast.makeText(context, "本地音视频启动失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "建立连接失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
            }
        });
    }

    //监听在线状态
    private Observer<StatusCode> onlineObserver = new Observer<StatusCode>() {
        public void onEvent(StatusCode status) {
            if (status.wontAutoLogin()) {
                // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                showToast("其他端登录！");
                logout();
            }
        }
    };

    //呼叫超时
    private Observer<Integer> timeoutObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer integer) {
            showToast("呼叫超时！");
            finish();
        }
    };

    // 呼叫时，被叫方的响应（接听、拒绝、忙）
    private Observer<AVChatCalleeAckEvent> callAckObserver = new Observer<AVChatCalleeAckEvent>() {
        @Override
        public void onEvent(AVChatCalleeAckEvent ackInfo) {
            if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_BUSY) {
                // 对方正在忙
                showToast("对方正忙！");
            } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_REJECT) {
                // 对方拒绝接听
                showToast("对方拒绝接听！");
                finish();
            } else if (ackInfo.getEvent() == AVChatEventType.CALLEE_ACK_AGREE) {
                // 对方同意接听
                showToast("正在建立连接！");
            }
        }
    };

    // 通话过程中，收到对方挂断电话
    private Observer<AVChatCommonEvent> callHangupObserver = new Observer<AVChatCommonEvent>() {
        @Override
        public void onEvent(AVChatCommonEvent avChatHangUpInfo) {
            //如果是视频通话需要先调用结束预览和关闭视频接口
            if (mAVChatData != null && mAVChatData.getChatId() == avChatHangUpInfo.getChatId()) {
                AVChatManager.getInstance().stopVideoPreview();
                AVChatManager.getInstance().disableVideo();
            }
            //关闭音视频通话底层引擎
            AVChatManager.getInstance().disableRtc();
            finish();
        }
    };

    // 图像surface view 初始化
    public void initSurfaceView(String account) {
        // 设置画布，加入到自己的布局中，用于呈现视频图像
        if (NimCache.getAccount().equals(account)) {
            AVChatManager.getInstance().setupLocalVideoRender(localRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
            addPreviewLayout(localRender, account);
        } else {
            AVChatManager.getInstance().setupRemoteVideoRender(account, remoteRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
            addPreviewLayout(remoteRender, account);
        }
    }

    private void addPreviewLayout(AVChatTextureViewRenderer surfaceView, String account) {
        if (surfaceView.getParent() != null) {
            ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
        }
        if (NimCache.getAccount().equals(account)) {
            localPreviewLayout.addView(surfaceView);
//            surfaceView.setZOrderMediaOverlay(true);
        } else {
            remotePreviewLayout.addView(surfaceView);
//            surfaceView.setZOrderMediaOverlay(false);
        }
    }

    /**
     * ****************************** 监听器 **********************************
     */
    // 通话过程状态监听
    private SimpleAVChatStateObserver avchatStateObserver = new SimpleAVChatStateObserver() {

        //自己成功离开频道回调
        @Override
        public void onLeaveChannel() {
            //如果是视频通话需要先调用结束预览和关闭视频接口
            AVChatManager.getInstance().stopVideoPreview();
            AVChatManager.getInstance().disableVideo();
            //关闭音视频通话底层引擎
            AVChatManager.getInstance().disableRtc();
            finish();
        }

        //加入当前音视频频道用户帐号回调
        @Override
        public void onUserJoined(String account) {
            initSurfaceView(account);
        }

        //音视频连接成功建立回调
        @Override
        public void onCallEstablished() {
            bReceive.setVisibility(View.GONE);
            tvText.setVisibility(View.GONE);
            ivBg.setVisibility(View.GONE);
            //移除超时监听
            AVChatTimeoutObserver.getInstance(context).observeTimeoutNotification(timeoutObserver, false, true);
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            AVChatManager.getInstance().observeCalleeAckNotification(callAckObserver, false);
            AVChatManager.getInstance().observeAVChatState(avchatStateObserver, false);
            AVChatManager.getInstance().observeHangUpNotification(callHangupObserver, false);
            NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(onlineObserver, false);
            // 如果是视频通话，关闭视频模块
            AVChatManager.getInstance().disableVideo();
            // 如果是视频通话，需要先关闭本地预览
            AVChatManager.getInstance().stopVideoPreview();
            //挂断
            if (mAVChatData != null) {
                AVChatManager.getInstance().hangUp2(mAVChatData.getChatId(), new AVChatCallback<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                    }

                    @Override
                    public void onFailed(int code) {
                    }

                    @Override
                    public void onException(Throwable exception) {
                    }
                });
            }
            //销毁音视频引擎和释放资源
            AVChatManager.getInstance().disableRtc();
            //销毁声音-震动
            SoundPoolManager.init(context).getSoundPool().stop(streamId);
            vibrator.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void permission() {
        boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
        boolean permission1 = selfPermissionGranted(Manifest.permission.RECORD_AUDIO);
        if (permission == true && permission1 == true) {
            init();
        } else {
            ActivityCompat.requestPermissions(NimActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                getPermission("请授权APP使用摄像头和录音权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getPermission(String text) {
        new AlertDialog.Builder(context)
                .setTitle(text)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivity(localIntent);
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    // 设置窗口flag，亮屏并且解锁/覆盖在锁屏界面上
    private void dismissKeyguard() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
    }

    private void logout() {
        //关闭心跳服务
        Intent heart = new Intent(context, HeartbeatService.class);
        context.stopService(heart);
        //友盟推送：别名注销-start
        String account = ElevatorManager.getInstance().getSession().getUserID() + "";
        if (!TextUtils.isEmpty(account)) {
            PushAgent mPushAgent = PushAgent.getInstance(context);
            mPushAgent.deleteAlias(account, "ELEVATOR", new UTrack.ICallBack() {
                @Override
                public void onMessage(boolean isSuccess, String message) {
                    Logger.d("友盟别名注销isSuccess=" + isSuccess + ",message=" + message);
                }
            });
        }
        SharedPreferencesUtil.setParam(context, "history", "rememberLogin", false);
        SharedPreferencesUtil.setParam(context, "history", "UserName", "");
        SharedPreferencesUtil.setParam(context, "history", "PassWord", "");
        //网易云信退出登录
        NIMClient.getService(AuthService.class).logout();
        Preferences.saveUserAccount("");
        Preferences.saveUserToken("");
        manager.delSession();
        ActivityCollector.removeAllActivity();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }
}
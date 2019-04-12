package com.sinodom.elevator.activity.elevator.nim;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.constant.AVChatResCode;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoQuality;
import com.netease.nimlib.sdk.avchat.constant.AVChatVideoScalingType;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.avchat.model.AVChatParameters;
import com.netease.nimlib.sdk.avchat.model.AVChatSurfaceViewRenderer;
import com.netease.nimlib.sdk.avchat.video.AVChatCameraCapturer;
import com.netease.nimlib.sdk.avchat.video.AVChatTextureViewRenderer;
import com.netease.nimlib.sdk.avchat.video.AVChatVideoCapturerFactory;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.NimCache;
import com.sinodom.elevator.activity.elevator.nim.utils.SimpleAVChatStateObserver;
import com.sinodom.elevator.bean.MemberBean;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.nim.RoomBean;
import com.sinodom.elevator.service.FloatVideoWindowService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoomActivity extends BaseActivity implements View.OnClickListener {

    private TextView ivBack;
    private TextView tvTime;
    private TextView tvTitle;
    private ImageView switchCamera;
    private ImageView closeCamera;
    private ImageView videoMute;
    private boolean cameraState = false;

    private FrameLayout remoteVideoLayout; //主持人
    private FrameLayout largeVideoLayout; //大屏
    private RelativeLayout rlLargeVideoLayout; //大屏
    private FrameLayout localVideoLayout1;
    private FrameLayout localVideoLayout2;
    private FrameLayout localVideoLayout3;
    private LinearLayout llUser;
    private LinearLayout llUser1;
    private LinearLayout llUser2;
    private LinearLayout llUser3;
    private LinearLayout llUserLarge;
    private ViewGroup[] viewLayouts = new ViewGroup[3];
    private List<MemberBean> memberList;
    protected AVChatCameraCapturer mVideoCapturer;
    public RoomBean mRoomBean;
    private ViewGroup[] userViews = new ViewGroup[3];

    //判断当前视频会议是否包含该用户
    public boolean containsMember(String account) {
        if (memberList != null && memberList.size() > 0) {
            boolean type = false;
            for (MemberBean member : memberList) {
                if (account.equals(member.getAccount())) {
                    type = true;
                    break;
                }
            }
            return type;
        } else {
            return false;
        }
    }

    //判断当前画布是否包含该编号
    public boolean containsNum(int num) {
        if (memberList != null && memberList.size() > 0) {
            boolean type = false;
            for (MemberBean member : memberList) {
                if (num == member.getNum()) {
                    type = true;
                    break;
                }
            }
            return type;
        } else {
            return false;
        }
    }

    private List<String> userJoinedList; // 已经onUserJoined的用户
    AVChatTextureViewRenderer masterRender; // 主播画布
    AVChatTextureViewRenderer largeRender; // 大屏画布

    public String roomId;
    private String roomName;
    private boolean isCreate = false; // 是否是主播
    private String creator;

    //nim视频会议
    private AbortableFuture<LoginInfo> loginRequest;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            //从悬浮窗进来后重新设置画布(判断是不是接通了)
            boolean isFloat = intent.getBooleanExtra("float", false);
            if (isFloat) {
                unbindService(mVideoServiceConnection);//不显示悬浮框
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rlLargeVideoLayout.setVisibility(View.GONE);
                        largeVideoLayout.removeAllViews();
                        reJoin();
                    }
                }, 800);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        showToast("有新的视频请求");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chat_room);
        mHomeWatcherReceiver = new HomeWatcherReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mHomeWatcherReceiver, filter);
        CreateRoomActivity.isCall = false;
        Intent intent = getIntent();
        creator = intent.getStringExtra("creator");
        roomId = intent.getStringExtra("roomId");
        roomName = intent.getStringExtra("roomName");
        isCreate = intent.getBooleanExtra("isCreate", false);
        mRoomBean = (RoomBean) intent.getSerializableExtra("bean");
        memberList = new ArrayList<>();
        userJoinedList = new ArrayList<>();
        Log.e("TAG", "creator=" + creator + "，roomId=" + roomId + "，roomName=" + roomName + "，isCreate=" + isCreate);
        initViews();
        registerObservers(true);
        initLiveVideo();
    }

    private void initViews() {
        ivBack = (TextView) findViewById(R.id.ivBack);
        tvTime = (TextView) findViewById(R.id.tvTime);
        ivBack.setOnClickListener(this);
        remoteVideoLayout = (FrameLayout) findViewById(R.id.remoteVideoLayout);
        largeVideoLayout = (FrameLayout) findViewById(R.id.largeVideoLayout);
        rlLargeVideoLayout = (RelativeLayout) findViewById(R.id.rlLargeVideoLayout);
        localVideoLayout1 = (FrameLayout) findViewById(R.id.localVideoLayout1);
        localVideoLayout2 = (FrameLayout) findViewById(R.id.localVideoLayout2);
        localVideoLayout3 = (FrameLayout) findViewById(R.id.localVideoLayout3);
        viewLayouts[0] = localVideoLayout1;
        viewLayouts[1] = localVideoLayout2;
        viewLayouts[2] = localVideoLayout3;

        llUser = (LinearLayout) findViewById(R.id.llUser);
        llUser1 = (LinearLayout) findViewById(R.id.llUser1);
        llUser2 = (LinearLayout) findViewById(R.id.llUser2);
        llUser3 = (LinearLayout) findViewById(R.id.llUser3);
        llUserLarge = (LinearLayout) findViewById(R.id.llUserLarge);
        userViews[0] = llUser1;
        userViews[1] = llUser2;
        userViews[2] = llUser3;
        //控制
        switchCamera = (ImageView) findViewById(R.id.avchat_switch_camera);
        closeCamera = (ImageView) findViewById(R.id.avchat_close_camera);
        videoMute = (ImageView) findViewById(R.id.avchat_video_mute);
        switchCamera.setOnClickListener(this);
        closeCamera.setOnClickListener(this);
        videoMute.setOnClickListener(this);
        remoteVideoLayout.setOnClickListener(this);
        largeVideoLayout.setOnClickListener(this);
        localVideoLayout1.setOnClickListener(this);
        localVideoLayout2.setOnClickListener(this);
        localVideoLayout3.setOnClickListener(this);
    }

    private void initLiveVideo() {
        //开启音视频引擎
        AVChatManager.getInstance().enableRtc();
        //设置通话可选参数
        AVChatParameters parameters = new AVChatParameters();
        //默认前置摄像头采集。
//        parameters.setBoolean(AVChatParameters.KEY_VIDEO_DEFAULT_FRONT_CAMERA, true);
        //视频绘制时自动旋转。
        parameters.setBoolean(AVChatParameters.KEY_VIDEO_ROTATE_IN_RENDING, false);
//        parameters.setRequestKey(AVChatParameters.KEY_AUDIO_EFFECT_NOISE_SUPPRESSOR);
        parameters.setInteger(AVChatParameters.KEY_VIDEO_QUALITY, AVChatVideoQuality.QUALITY_DEFAULT);//设置视频清晰度
        AVChatManager.getInstance().setParameters(parameters);
        //视频通话设置
        AVChatManager.getInstance().enableVideo();
        //AVChatManager.getInstance().setupLocalVideoRender(IVideoRender render, boolean mirror, int scalingType);
        if (mVideoCapturer == null) {
            mVideoCapturer = AVChatVideoCapturerFactory.createCameraCapturer(true);
            AVChatManager.getInstance().setupVideoCapturer(mVideoCapturer);
        }
        //开启视频预览
        AVChatManager.getInstance().startVideoPreview();
        //加入房间
        AVChatManager.getInstance().joinRoom2(roomId, AVChatType.VIDEO, new AVChatCallback<AVChatData>() {
            @Override
            public void onSuccess(AVChatData avChatData) {
                Log.e("TAG", "join channel success, extra:" + avChatData.getExtra());
                // 设置音量信号监听, 通过AVChatStateObserver的onReportSpeaker回调音量大小
                AVChatParameters avChatParameters = new AVChatParameters();
                avChatParameters.setBoolean(AVChatParameters.KEY_AUDIO_REPORT_SPEAKER, true);
                AVChatManager.getInstance().setParameters(avChatParameters);
                //本地视频显示
                AVChatManager.getInstance().enableAudienceRole(false);//是否是观众角色进入-是否发送视频数据，false：发送视频流
                AVChatManager.getInstance().muteLocalAudio(false);//是否静音，true：静音
//                userJoinedList.add(NimCache.getAccount());
//                if (isCreate) {
//                    //显示主播视频
//                    onMasterJoin(NimCache.getAccount());
//                } else {
//                    showLocalView(NimCache.getAccount());
//                }
                startTimer();
            }

            @Override
            public void onFailed(int i) {
                try {
                    Log.e("TAG", "加入房间失败Code:" + i);
                    if (i == 404) {
                        showToast("本次通话已结束");
                    } else {
                        showToast("加入房间失败Code:" + i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
                finish();
            }

            @Override
            public void onException(Throwable throwable) {
                finish();
            }
        });
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode>() {
                    public void onEvent(StatusCode status) {
                        Log.e("TAG", "User status changed to: " + status);
                        if (status.wontAutoLogin()) {
                            // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
                            showToast("其他端登录");
                            finish();
                        }
                    }
                }, true);
    }

    ServiceConnection mVideoServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 获取服务的操作对象
            FloatVideoWindowService.MyBinder binder = (FloatVideoWindowService.MyBinder) service;
            binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private HomeWatcherReceiver mHomeWatcherReceiver = null;

    public class HomeWatcherReceiver extends BroadcastReceiver {

        private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {

            String intentAction = intent.getAction();
            if (TextUtils.equals(intentAction, Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (TextUtils.equals(SYSTEM_DIALOG_REASON_HOME_KEY, reason)) {
                    Intent intent1 = new Intent(ChatRoomActivity.this, FloatVideoWindowService.class);//开启服务显示悬浮框
                    intent1.putExtra("creator", creator);
                    bindService(intent1, mVideoServiceConnection, Context.BIND_AUTO_CREATE);
                }
            }
        }

    }

    public void startFloat() {
        moveTaskToBack(true);
        Intent intent = new Intent(this, FloatVideoWindowService.class);//开启服务显示悬浮框
        intent.putExtra("creator", creator);
        bindService(intent, mVideoServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出聊天室
            case R.id.ivBack:
                finish();
                break;
            //视频是否开启
            case R.id.avchat_close_camera:
                setVideoState();
                closeCamera = (ImageView) findViewById(R.id.avchat_close_camera);
                break;
            //语音
            case R.id.avchat_video_mute:
                setAudioState();
                break;
            //大屏切换
            case R.id.remoteVideoLayout:
                if (rlLargeVideoLayout.getVisibility() == View.GONE) {
                    rlLargeVideoLayout.setVisibility(View.VISIBLE);
                    remoteVideoLayout.removeAllViews();
                    localVideoLayout1.removeAllViews();
                    localVideoLayout2.removeAllViews();
                    localVideoLayout3.removeAllViews();
                    memberList.clear();
                    if (creator.equals(NimCache.getAccount())) {
                        largeLocal(userJoinedList.get(0));
                    } else {
                        for (String str : userJoinedList) {
                            if (str.equals(creator)) {
                                largeLocal(str);
                            }
                        }
                    }
                }
                break;
            //大屏切换1
            case R.id.localVideoLayout1:
                if (rlLargeVideoLayout.getVisibility() == View.GONE) {
                    if (userJoinedList.size() >= 2) {
                        rlLargeVideoLayout.setVisibility(View.VISIBLE);
                        remoteVideoLayout.removeAllViews();
                        localVideoLayout1.removeAllViews();
                        localVideoLayout2.removeAllViews();
                        localVideoLayout3.removeAllViews();
                        largeLocal(memberList.get(0).getAccount());
                        memberList.clear();
                    }
                }
                break;
            //大屏切换2
            case R.id.localVideoLayout2:
                if (rlLargeVideoLayout.getVisibility() == View.GONE) {
                    if (userJoinedList.size() >= 3) {
                        rlLargeVideoLayout.setVisibility(View.VISIBLE);
                        remoteVideoLayout.removeAllViews();
                        localVideoLayout1.removeAllViews();
                        localVideoLayout2.removeAllViews();
                        localVideoLayout3.removeAllViews();
                        largeLocal(memberList.get(1).getAccount());
                        memberList.clear();
                    }
                }
                break;
            //大屏切换3
            case R.id.localVideoLayout3:
                if (rlLargeVideoLayout.getVisibility() == View.GONE) {
                    if (userJoinedList.size() >= 4) {
                        rlLargeVideoLayout.setVisibility(View.VISIBLE);
                        remoteVideoLayout.removeAllViews();
                        localVideoLayout1.removeAllViews();
                        localVideoLayout2.removeAllViews();
                        localVideoLayout3.removeAllViews();
                        largeLocal(memberList.get(2).getAccount());
                        memberList.clear();
                    }
                }
                break;
            //大屏隐藏
            case R.id.largeVideoLayout:
                for (String a : userJoinedList) {
                    onMasterJoin(a);
                    showLocalView(a);
                }
                remoteVideoLayout.setVisibility(View.VISIBLE);
                localVideoLayout1.setVisibility(View.VISIBLE);
                localVideoLayout2.setVisibility(View.VISIBLE);
                localVideoLayout3.setVisibility(View.VISIBLE);
                rlLargeVideoLayout.setVisibility(View.GONE);
                largeVideoLayout.removeAllViews();
                break;
        }
    }

    public void reJoin() {
        remoteVideoLayout.removeAllViews();
        localVideoLayout1.removeAllViews();
        localVideoLayout2.removeAllViews();
        localVideoLayout3.removeAllViews();
        memberList.clear();
        for (String a : userJoinedList) {
            onMasterJoin(a);
            showLocalView(a);
        }
    }

    //显示大屏
    private void largeLocal(String s) {
        if (largeRender == null) {
            largeRender = new AVChatTextureViewRenderer(context);
        }
        if (NimCache.getAccount().equals(s)) {
            AVChatManager.getInstance().setupLocalVideoRender(largeRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        } else {
            AVChatManager.getInstance().setupRemoteVideoRender(s, largeRender, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
        }
        if (largeRender.getParent() != null)
            ((ViewGroup) largeRender.getParent()).removeView(largeRender);
        largeVideoLayout.addView(largeRender);
//        largeRender.setZOrderMediaOverlay(false);
        for (RoomBean.UserListBean bean : mRoomBean.getUserList()) {
            if (s.equals(bean.getWYID())) {
                TextView nameView = (TextView) llUserLarge.getChildAt(0);
                nameView.setText(bean.getName());
                TextView codeView = (TextView) llUserLarge.getChildAt(1);
                codeView.setText(bean.getUcode());
            }
        }
    }

    // 设置自己的摄像头是否开启
    private void setVideoState() {
        if (AVChatManager.getInstance().isLocalVideoMuted()) {
            closeCamera.setImageResource(R.mipmap.avchat_video_close_camera_normal);
            AVChatManager.getInstance().muteLocalVideo(false);
        } else {
            closeCamera.setImageResource(R.mipmap.avchat_video_close_camera_pressed);
            AVChatManager.getInstance().muteLocalVideo(true);
        }
    }

    // 设置自己的语音是否开启
    private void setAudioState() {
        if (AVChatManager.getInstance().isLocalAudioMuted()) {
            videoMute.setImageResource(R.mipmap.avchat_video_mute_normal);
            AVChatManager.getInstance().muteLocalAudio(false);
        } else {
            videoMute.setImageResource(R.mipmap.avchat_video_mute_pressed);
            AVChatManager.getInstance().muteLocalAudio(true);
        }
    }

    /**************************** 主持人 start ****************************/
    // 主持人进入频道
    private void onMasterJoin(String s) {
        if (userJoinedList != null && userJoinedList.contains(s) && s.equals(creator)) {
            if (masterRender == null) {
                masterRender = new AVChatTextureViewRenderer(context);
            }
            boolean isSetup = setupMasterRender(s, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
            if (isSetup && masterRender != null) {
                addIntoMasterPreviewLayout(masterRender);
            }
        }
    }

    // 将主持人添加到主持人画布
    private void addIntoMasterPreviewLayout(AVChatTextureViewRenderer surfaceView) {
        if (surfaceView.getParent() != null)
            ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
        remoteVideoLayout.addView(surfaceView);
//        surfaceView.setZOrderMediaOverlay(true);
    }

    private boolean setupMasterRender(String s, int mode) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }
        boolean isSetup = false;
        try {
            if (s.equals(NimCache.getAccount())) {
                isSetup = AVChatManager.getInstance().setupLocalVideoRender(masterRender, false, mode);
            } else {
                isSetup = AVChatManager.getInstance().setupRemoteVideoRender(s, masterRender, false, mode);
            }
        } catch (Exception e) {
            Log.e("TAG", "set up video render error:" + e.getMessage());
            e.printStackTrace();
        }
        return isSetup;
    }
    /**************************** 主持人 end ****************************/


    /**************************** 本地 start ****************************/
    // 显示本地成员图像
    private void showLocalView(String a) {
        if (userJoinedList != null && userJoinedList.contains(a) && !creator.equals(a) && !containsMember(a) && memberList.size() < 3) {
            for (int i = 0; i < 3; i++) {
                if (!containsNum(i)) {
                    AVChatSurfaceViewRenderer render = new AVChatSurfaceViewRenderer(context);
                    boolean isSetup = false;
                    try {
                        if (NimCache.getAccount().equals(a)) {
                            isSetup = AVChatManager.getInstance().setupLocalVideoRender(render, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
                        } else {
                            isSetup = AVChatManager.getInstance().setupRemoteVideoRender(a, render, false, AVChatVideoScalingType.SCALE_ASPECT_BALANCED);
                        }
                        Log.e("TAG", "setup render, creator account:" + creator + ", render account:" + a + ", isSetup:" + isSetup);
                    } catch (Exception e) {
                        Log.e("TAG", "set up video render error:" + e.getMessage());
                        e.printStackTrace();
                    }
                    if (isSetup && render != null) {
                        MemberBean member = new MemberBean();
                        member.setNum(i);
                        member.setAccount(a);
                        memberList.add(member);
                        addIntoPreviewLayout(render, viewLayouts[i]);
                    }
                    break;
                }
            }
        }
    }

    // 添加到成员显示的画布
    private void addIntoPreviewLayout(SurfaceView surfaceView, ViewGroup viewLayout) {
        if (surfaceView == null) {
            return;
        }
        if (surfaceView.getParent() != null)
            ((ViewGroup) surfaceView.getParent()).removeView(surfaceView);
        viewLayout.addView(surfaceView);
        surfaceView.setZOrderMediaOverlay(true);
    }

    // 将被取消权限的成员从画布移除, 并将角色置为初始状态
    public void onVideoOff(String a) {
        if (memberList != null && memberList.size() > 0) {
            for (MemberBean member : memberList) {
                if (a.equals(member.getAccount())) {
                    viewLayouts[member.getNum()].removeAllViews();
                    memberList.remove(member);
                    break;
                }
            }
        }

//        if (memberList != null && memberList.size() > 0) {
//            Iterator<MemberBean> it = memberList.iterator();
//            while (it.hasNext()) {
//                MemberBean member = it.next();
//                if (a.equals(member.getAccount())) {
//                    viewLayouts[member.getNum()].removeAllViews();
//                    memberList.remove(member);
//                    break;
//                }
//            }
//        }
    }
    /**************************** 本地 end ****************************/


    /**************************** 退出视频会议 start ****************************/
//    @Override
//    public void onBackPressed() {
//        logoutChatRoom();
//    }

    //退出聊天室
//    private void logoutChatRoom() {
//        MyAlertDialog alertDialog = new MyAlertDialog(context, R.style.DialogStyle);
//        alertDialog.setTitle("确定离开房间吗？");
//        alertDialog.setOnConfirmListener("离开", new MyAlertDialog.OnClickListener() {
//            @Override
//            public void onClick(MyAlertDialog dialog, View v) {
//                dialog.dismiss();
//                finish();
//            }
//        });
//        alertDialog.show();
//    }

    // 成员离开聊天室
    private void leaveChatRoom() {
        try {
            //关闭视频预览
            AVChatManager.getInstance().stopVideoPreview();
            //如果是视频通话，关闭视频模块
            AVChatManager.getInstance().disableVideo();
            //离开房间
            AVChatManager.getInstance().leaveRoom2(roomId, new AVChatCallback<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.e("TAG", "离开房间成功");
                }

                @Override
                public void onFailed(int i) {
                    Log.e("TAG", "离开房间失败Code:" + i);
                }

                @Override
                public void onException(Throwable throwable) {

                }
            });
            //关闭音视频引擎
            AVChatManager.getInstance().disableRtc();
            Log.e("TAG", "chat room do clear");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TAG", "ChatRoom关闭异常");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHomeWatcherReceiver != null) {
            try {
                unregisterReceiver(mHomeWatcherReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (roomId != null) {
            leaveChatRoom();
            userLeave();
        }
    }
    /**************************** 退出视频会议 start ****************************/


    /****************************   监听  ************************************/
    private void registerObservers(boolean register) {
        AVChatManager.getInstance().observeAVChatState(avchatStateObserver, register);
    }

    /**************************** AVChatStateObserver start ****************************/

    // 通话过程状态监听
    private SimpleAVChatStateObserver avchatStateObserver = new SimpleAVChatStateObserver() {
        //服务器断开回调
        @Override
        public void onDisconnectServer(int code) {
            showToast("与服务器断开连接");
            finish();
        }

        /**
         * 当前音视频服务器连接回调
         */
        @Override
        public void onJoinedChannel(int i, String s, String s1, int elapsed) {
            Log.e("TAG", "onJoinedChannel, res:" + i);
            if (i != AVChatResCode.JoinChannelCode.OK) {
                showToast("joined channel:" + i);
            }
        }

        /**
         * 自己成功离开频道回调
         */
        @Override
        public void onLeaveChannel() {
            userJoinedList.remove(NimCache.getAccount());
        }

        /**
         * 加入当前音视频频道用户帐号回调
         */
        @Override
        public void onUserJoined(String s) {
            Log.e("TAG", "onUserJoined=" + s);
            if (!userJoinedList.contains(s)) {
                userJoinedList.add(s);
            }
            //显示主播视频
            onMasterJoin(s);
            //显示本地视频
            showLocalView(s);
            showUser();
            if (NimCache.getAccount().equals(creator)) {
                inJoinOk(s);
            }
        }

        /**
         * 当前用户离开频道回调
         */
        @Override
        public void onUserLeave(String s, int i) {
            // 用户离开频道，如果是有权限用户，移除下画布
            if (s.equals(creator)) {
                remoteVideoLayout.removeAllViews();
                showToast("视频会议已关闭");
                finish();
            } else {
                onVideoOff(s);
            }
            showUser();
            reJoin();
            userJoinedList.remove(s);
        }

        /**
         * 音视频连接成功建立回调
         */
        @Override
        public void onCallEstablished() {
            if (!userJoinedList.contains(NimCache.getAccount())) {
                userJoinedList.add(NimCache.getAccount());
            }
            onMasterJoin(NimCache.getAccount());
            //显示本地视频
            showLocalView(NimCache.getAccount());
            showUser();
        }
    };

    private void inJoinOk(String userId) {
        for (RoomBean.UserListBean bean : mRoomBean.getUserList()) {
            if (bean.getWYID().equals(userId)) {
                userId = bean.getUserID();
                break;
            }
        }
        Call<ResponBean> call = server.getService().addAPPAlarmRoomRecordUser(userId, roomId);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    private void userLeave() {
        Call<ResponBean> call = server.getService().aPPAlarmRoomRecordUpdateEndTime(roomId);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    public void showUser() {
        llUser.setVisibility(View.GONE);
        for (int i = 0; i < 3; i++) {
            userViews[i].setVisibility(View.GONE);
        }
        if (mRoomBean != null && mRoomBean.getUserList().size() > 0) {
            for (RoomBean.UserListBean bean : mRoomBean.getUserList()) {
                if (creator.equals(bean.getWYID())) {
                    llUser.setVisibility(View.VISIBLE);
                    TextView nameView = (TextView) llUser.getChildAt(0);
                    nameView.setText(bean.getName());
                    TextView codeView = (TextView) llUser.getChildAt(1);
                    codeView.setText(bean.getUcode());
                }
            }
        }
        for (int i = 0; i < memberList.size(); i++) {
            userViews[i].setVisibility(View.VISIBLE);
            if (mRoomBean != null && mRoomBean.getUserList().size() > 0) {
                for (RoomBean.UserListBean bean : mRoomBean.getUserList()) {
                    if (memberList.get(i).getAccount().equals(bean.getWYID())) {
                        TextView nameView = (TextView) userViews[i].getChildAt(0);
                        nameView.setText(bean.getName());
                        TextView codeView = (TextView) userViews[i].getChildAt(1);
                        codeView.setText(bean.getUcode());
                    }
                }
            }
        }
    }

    /**************************** AVChatStateObserver end ****************************/
    private Timer timer;
    private int seconds;

    private void startTimer() {
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
        tvTime.setText("通话" + "00:00");
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            seconds++;
            int m = seconds / 60;
            int s = seconds % 60;
            final String time = String.format(Locale.CHINA, "%02d:%02d", m, s);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvTime.setText("通话" + time);
                }
            });
        }
    };
}
package com.sinodom.elevator.activity.elevator.nim;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.NimCache;
import com.sinodom.elevator.activity.elevator.nim.utils.Preferences;
import com.sinodom.elevator.bean.nim.RoomBean;
import com.sinodom.elevator.single.SoundPoolManager;
import com.sinodom.elevator.util.SharedPreferencesUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 视频状态选择：是否接受视频请求
 */
public class VideoStateChooseActivity extends BaseActivity {

    private static final int PERMISSION_REQUEST_CODE = 10;
    private String roomId;
    private String roomName;
    private String creator;
    //震动-声音
    private Vibrator vibrator;
    private int streamId;
    private RoomBean mRoomBean;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showToast("有新的视频请求");
        creator = intent.getStringExtra("creator");
        roomId = intent.getStringExtra("roomId");
        roomName = intent.getStringExtra("roomName");
        mRoomBean = (RoomBean) intent.getSerializableExtra("bean");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_state_choose);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        creator = intent.getStringExtra("creator");
        roomId = intent.getStringExtra("roomId");
        roomName = intent.getStringExtra("roomName");
        mRoomBean = (RoomBean) intent.getSerializableExtra("bean");
//        dismissKeyguard();
        wakeUpAndUnlock();
        try {
            //震动
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {2000, 3000, 2000, 3000, 2000, 3000, 2000, 3000, 2000, 3000, 2000, 3000};   // 停止 开启 停止 开启
            vibrator.vibrate(pattern, -1);           //如果只想震动一次，index设为-1
            //声音
            streamId = SoundPoolManager.init(context).getSoundPool().play(5, 1, 1, 0, 10, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 接听来电
    public static void incomingCall(Context context, CustomNotification messages) {
        RoomBean map1 = new Gson().fromJson(messages.getContent(), RoomBean.class);
        Intent intent = new Intent();
        intent.setClass(context, VideoStateChooseActivity.class);
        intent.putExtra("roomId", map1.getRoomId());
        intent.putExtra("roomName", map1.getRoomName());
        intent.putExtra("creator", map1.getCreator());
        intent.putExtra("bean", map1);
        intent.putExtra("isCreate", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // 设置窗口flag，亮屏并且解锁/覆盖在锁屏界面上
    private void dismissKeyguard() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );
    }

    /**
     * 唤醒手机屏幕并解锁
     */
    public void wakeUpAndUnlock() {
        // 获取电源管理器对象
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean screenOn = pm.isScreenOn();
        if (!screenOn) {
            // 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
            PowerManager.WakeLock wl = pm.newWakeLock(
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");
            wl.acquire(); // 点亮屏幕
            wl.release(); // 释放
        }
        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
    }

    @OnClick({R.id.bReceive, R.id.bUnReceive})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bReceive:
                if (!TextUtils.isEmpty(NimCache.getAccount())) {
                    //权限申请
                    permission();
                } else {
                    try {
                        String account = (String) SharedPreferencesUtil.getParam(context, "history", "UserName", "");
                        if (!TextUtils.isEmpty(account)) {
                            nimLogin(account, "123456");
                        } else {
                            showToast("获取视频账号失败！");
                            finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("获取视频账号失败！");
                        finish();
                    }
                }
                break;
            case R.id.bUnReceive:
                finish();
                break;
        }
    }

    private AbortableFuture<LoginInfo> loginRequest;

    private void nimLogin(final String account, final String token) {
        // 登录
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                onLoginDone();
                NimCache.setAccount(account);
                saveLoginInfo(account, token);
                showToast("登录成功！");
                //权限申请
                permission();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    showToast("帐号或密码错误");
                } else {
                    showToast("登录失败: " + code);
                }
                finish();
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
                showToast("登录视频服务器异常");
                finish();
            }
        });
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    private void onLoginDone() {
        loginRequest = null;
    }

    public void permission() {
        boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
        boolean permission1 = selfPermissionGranted(Manifest.permission.RECORD_AUDIO);
        if (permission == true && permission1 == true) {
            Intent intent = new Intent(context, ChatRoomActivity.class);
            intent.putExtra("roomId", roomId);
            intent.putExtra("roomName", roomName);
            intent.putExtra("creator", creator);
            intent.putExtra("isCreate", false);
            intent.putExtra("bean", mRoomBean);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(intent);
            finish();
        } else {
            ActivityCompat.requestPermissions(VideoStateChooseActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(context, ChatRoomActivity.class);
                intent.putExtra("roomId", roomId);
                intent.putExtra("roomName", roomName);
                intent.putExtra("creator", creator);
                intent.putExtra("isCreate", false);
                intent.putExtra("bean", mRoomBean);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intent);
                finish();
            } else {
                getPermission("请授权APP使用摄像头和录音权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getPermission(String text) {
        new android.support.v7.app.AlertDialog.Builder(context)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            //销毁声音-震动
            SoundPoolManager.init(context).getSoundPool().stop(streamId);
            vibrator.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
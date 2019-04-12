package com.sinodom.elevator.activity.elevator;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.fragment.elevator.main.BusinessFragment;
import com.sinodom.elevator.fragment.elevator.main.MonitorFragment;
import com.sinodom.elevator.fragment.elevator.main.MyFragment;
import com.sinodom.elevator.fragment.elevator.main.RescueFragment;
import com.sinodom.elevator.fragment.elevator.rescue.CallFragment;
import com.sinodom.elevator.service.AsyncUploadService;
import com.sinodom.elevator.service.HeartbeatService;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.single.SoundPoolManager;
import com.sinodom.elevator.util.SharedPreferencesUtil;
import com.sinodom.elevator.view.MyAlertDialog;
import com.tencent.bugly.beta.Beta;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.rlRescue)
    RelativeLayout rlRescue;
    @BindView(R.id.rlBusiness)
    RelativeLayout rlBusiness;
    @BindView(R.id.rlMy)
    RelativeLayout rlMy;
    @BindView(R.id.ivRescue)
    ImageView ivRescue;
    @BindView(R.id.tvRescue)
    TextView tvRescue;
    @BindView(R.id.ivBusiness)
    ImageView ivBusiness;
    @BindView(R.id.tvBusiness)
    TextView tvBusiness;
    @BindView(R.id.ivMy)
    ImageView ivMy;
    @BindView(R.id.tvMy)
    TextView tvMy;
    @BindView(R.id.ivMonitor)
    ImageView ivMonitor;
    @BindView(R.id.tvMonitor)
    TextView tvMonitor;
    private RescueFragment mRescueFragment = null;
    private BusinessFragment mBusinessFragment = null;
    private MonitorFragment mMonitorFragment = null;
    private MyFragment mMyFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_main);
        ButterKnife.bind(this);
        init();
        addAlias();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int guid = intent.getIntExtra("guid", 0);
        loadDetail(guid);
    }

    public void loadDetail(int id) {
        setRescue();
        mRescueFragment.setCall();
        CallFragment fragment = (CallFragment) mRescueFragment.getChildFragmentManager().getFragments().get(0);
        fragment.loadDetailData(id);
    }

    private void init() {
        Beta.checkUpgrade(false, false);
        //初始化音频播放
        SoundPoolManager.init(context);
        boolean permission = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean permission2 = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
        boolean permission3 = selfPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission == true && permission1 == true && permission2 && permission3) {
            try {
                //开启心跳服务
                Intent heart = new Intent(MainActivity.this, HeartbeatService.class);
                startService(heart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        try {
            //开启离线服务
            Intent upload = new Intent(MainActivity.this, AsyncUploadService.class);
            startService(upload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mRescueFragment = new RescueFragment();
        mBusinessFragment = new BusinessFragment();
        mMonitorFragment = new MonitorFragment();
        mMyFragment = new MyFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.tabPager, mRescueFragment).show(mRescueFragment).commit();

//        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
        boolean isPush = (boolean) SharedPreferencesUtil.getParam(context, "history", "push", false);
        if (!isPush) {
            SharedPreferencesUtil.setParam(context, "history", "push", true);
            MyAlertDialog myAlertDialog = new MyAlertDialog(context, R.style.DialogStyle1);
            myAlertDialog.setTitle("请打开APP通知、锁屏显示、后台弹出界面、自启动开关");
            myAlertDialog.setInfo("确保您能及时收到电梯救援相关推送信息！");
            myAlertDialog.setBCancelGone();
            myAlertDialog.setOnConfirmListener(new MyAlertDialog.OnClickListener() {
                @Override
                public void onClick(MyAlertDialog dialog, View v) {
                    dialog.dismiss();
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(localIntent);
                }
            });
            myAlertDialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    //开启心跳服务
                    Intent heart = new Intent(MainActivity.this, HeartbeatService.class);
                    startService(heart);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                getPermission("请授权APP获取位置信息权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //友盟别名注册
    private void addAlias() {
        String deviceToken = ElevatorManager.getInstance().getDeviceToken();
        final String account = ElevatorManager.getInstance().getSession().getUserID() + "";
        final PushAgent mPushAgent = PushAgent.getInstance(context);
        //判断友盟deviceToken注册是否成功
        if (TextUtils.isEmpty(deviceToken)) {
            mPushAgent.register(new IUmengRegisterCallback() {

                @Override
                public void onSuccess(String deviceToken) {
                    //注册成功会返回device token
                    Logger.d("友盟DeviceToken：" + deviceToken);
                    ElevatorManager.getInstance().setDeviceToken(deviceToken);
                    if (!TextUtils.isEmpty(account)) {
                        //友盟别名注册
                        mPushAgent.addAlias(account, "ELEVATOR", new UTrack.ICallBack() {
                            @Override
                            public void onMessage(boolean isSuccess, String message) {
                                Logger.d("友盟别名注册isSuccess=" + isSuccess + ",message=" + message);
                            }
                        });
                    } else {
                        //showToast("获取视频账号失败！");
                    }
                }

                @Override
                public void onFailure(String s, String s1) {
                    //showToast("友盟推送服务初始化失败:" + s + "|" + s1);
                    Logger.e("友盟推送服务初始化失败:s===" + s + "   ,s1===" + s1);
                }
            });
        } else {
            if (!TextUtils.isEmpty(account)) {
                //友盟别名注册
                mPushAgent.addAlias(account, "ELEVATOR", new UTrack.ICallBack() {
                    @Override
                    public void onMessage(boolean isSuccess, String message) {
                        Logger.d("友盟别名注册isSuccess=" + isSuccess + ",message=" + message);
                    }
                });
            } else {
                //showToast("获取视频账号失败！");
            }
        }
    }

    public void setRescue() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        resetTabBtn();
        tvRescue.setTextColor(ContextCompat.getColor(context, R.color.actionbar));
        ivRescue.setBackgroundResource(R.mipmap.ic_tab_help_sel);
        hideFragments(ft);
        if (mRescueFragment == null) {
            mRescueFragment = new RescueFragment();
        }
        if (!mRescueFragment.isAdded()) {
            ft.add(R.id.tabPager, mRescueFragment);
        }
        ft.show(mRescueFragment).commitAllowingStateLoss();
    }

    @OnClick({R.id.rlRescue, R.id.rlBusiness, R.id.rlMonitor, R.id.rlMy})
    public void onViewClicked(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.rlRescue:
                resetTabBtn();
                tvRescue.setTextColor(ContextCompat.getColor(context, R.color.actionbar));
                ivRescue.setBackgroundResource(R.mipmap.ic_tab_help_sel);
                hideFragments(ft);
                if (mRescueFragment == null) {
                    mRescueFragment = new RescueFragment();
                }
                if (!mRescueFragment.isAdded()) {
                    ft.add(R.id.tabPager, mRescueFragment);
                }
                ft.show(mRescueFragment).commit();
                break;
            case R.id.rlBusiness:
                resetTabBtn();
                tvBusiness.setTextColor(ContextCompat.getColor(context, R.color.actionbar));
                ivBusiness.setBackgroundResource(R.mipmap.ic_tab_business_sel);
                hideFragments(ft);
                if (mBusinessFragment == null) {
                    mBusinessFragment = new BusinessFragment();
                }
                if (!mBusinessFragment.isAdded()) {
                    ft.add(R.id.tabPager, mBusinessFragment);
                }
                ft.show(mBusinessFragment).commit();
                break;
            case R.id.rlMonitor:
                resetTabBtn();
                tvMonitor.setTextColor(ContextCompat.getColor(context, R.color.actionbar));
                ivMonitor.setBackgroundResource(R.mipmap.ic_tab_monitor_sel);
                hideFragments(ft);
                if (mMonitorFragment == null) {
                    mMonitorFragment = new MonitorFragment();
                }
                if (!mMonitorFragment.isAdded()) {
                    ft.add(R.id.tabPager, mMonitorFragment);
                }
                ft.show(mMonitorFragment).commit();
                break;
            case R.id.rlMy:
                resetTabBtn();
                tvMy.setTextColor(ContextCompat.getColor(context, R.color.actionbar));
                ivMy.setBackgroundResource(R.mipmap.ic_tab_me_sel);
                hideFragments(ft);
                if (mMyFragment == null) {
                    mMyFragment = new MyFragment();
                }
                if (!mMyFragment.isAdded()) {
                    ft.add(R.id.tabPager, mMyFragment);
                }
                ft.show(mMyFragment).commit();
                break;
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mRescueFragment != null) {
            transaction.hide(mRescueFragment);
        }
        if (mBusinessFragment != null) {
            transaction.hide(mBusinessFragment);
        }
        if (mMonitorFragment != null) {
            transaction.hide(mMonitorFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
    }

    protected void resetTabBtn() {
        tvRescue.setTextColor(ContextCompat.getColor(context, R.color.tab_gray));
        tvBusiness.setTextColor(ContextCompat.getColor(context, R.color.tab_gray));
        tvMonitor.setTextColor(ContextCompat.getColor(context, R.color.tab_gray));
        tvMy.setTextColor(ContextCompat.getColor(context, R.color.tab_gray));
        ivRescue.setBackgroundResource(R.mipmap.ic_tab_help);
        ivBusiness.setBackgroundResource(R.mipmap.ic_tab_business);
        ivMonitor.setBackgroundResource(R.mipmap.ic_tab_monitor);
        ivMy.setBackgroundResource(R.mipmap.ic_tab_me);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isBackground) {
            Log.e("TAG", "MainActivity-->onResume" + isBackground);
            //停止前台定位服务：
            if (HeartbeatService.mLocationClient != null && HeartbeatService.mLocationClient.isStarted()) {
                HeartbeatService.mLocationClient.disableLocInForeground(true);// 关闭前台定位，同时移除通知栏
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBackground) {
            Log.e("TAG", "MainActivity-->onStop" + isBackground);
            //开启前台定位服务：
            Notification.Builder builder = new Notification.Builder(MainActivity.this.getApplicationContext());
            //获取一个Notification构造器
            Intent nfIntent = new Intent(MainActivity.this.getApplicationContext(), MainActivity.class);
            builder.setContentIntent(PendingIntent.getActivity(MainActivity.this, 0, nfIntent, 0)) // 设置PendingIntent
                    .setContentTitle("正在进行后台定位") // 设置下拉列表里的标题
                    .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                    .setContentText("后台定位通知") // 设置上下文内容
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
            Notification notification = builder.build();
//            notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
            if (HeartbeatService.mLocationClient != null && HeartbeatService.mLocationClient.isStarted()) {
                HeartbeatService.mLocationClient.enableLocInForeground(1001, notification);// 调起前台定位
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return doubleBackToExit(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    //fragment切换失效问题
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}

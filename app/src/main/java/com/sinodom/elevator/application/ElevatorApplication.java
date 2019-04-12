package com.sinodom.elevator.application;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatData;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.activity.elevator.nim.NimActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.NimCache;
import com.sinodom.elevator.activity.elevator.nim.utils.Preferences;
import com.sinodom.elevator.service.MyPushIntentService;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.single.Server;
import com.sinodom.elevator.util.glide.GlideImageLoader;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

import org.android.agoo.huawei.HuaWeiRegister;

import java.util.Map;

public class ElevatorApplication extends Application {

    public static Context context;
    // 用于存放倒计时时间
    public static Map<String, Long> map;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //百度地图初始化
        SDKInitializer.initialize(this);
        //腾讯Bugly
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel(BuildConfig.BUGLY_CHANNEL);
        Bugly.init(getApplicationContext(), BuildConfig.BUGLY_APPID, BuildConfig.BUGLY_ENABLE_DEBUG, strategy);
        //logger
        Logger.init("Elevator").hideThreadInfo().methodCount(2).logLevel(BuildConfig.ISLOGGER ? LogLevel.FULL : LogLevel.NONE);

        ElevatorManager.getInstance().init(context);
        Server.getInstance().init();
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
        UMConfigure.init(this, BuildConfig.UMENG_APPKEY, BuildConfig.UMENG_CHANNEL, UMConfigure.DEVICE_TYPE_PHONE, BuildConfig.UMENG_MESSAGE_SECRET);
        HuaWeiRegister.register(context);
        //注册推送服务，每次调用register方法都会回调该接口
        if (BuildConfig.APP_TYPE.equals("Elevator")) {
            //友盟推送
            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, BuildConfig.UMENG_MESSAGE_SECRET);
            PushAgent mPushAgent = PushAgent.getInstance(context);
            mPushAgent.register(new IUmengRegisterCallback() {

                @Override
                public void onSuccess(String deviceToken) {
                    //注册成功会返回device token
                    Logger.d("友盟DeviceToken：" + deviceToken);
                    ElevatorManager.getInstance().setDeviceToken(deviceToken);
                }

                @Override
                public void onFailure(String s, String s1) {
                    Logger.e("友盟deviceToken获取失败:s===" + s + "   ,s1===" + s1);
                }
            });
            mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        }
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.e("app", "失败");
            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.e("app", "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.e("app", "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.e("app", "onDownloadProgress:" + i);
            }
        });
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        QbSdk.setDownloadWithoutWifi(true);
        TbsDownloader.needDownload(context, false);
        //解决7.0文件权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //网易云信
        NimCache.setContext(this);
        NIMClient.init(this, getLoginInfo(), new SDKOptions());
        if (NIMUtil.isMainProcess(this)) {
            registerAVChatIncomingCallObserver(true);
        }
        initImagePicker();
    }

    private int maxImgCount = 6;               //允许选择图片最大数

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            NimCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    /**
     * 注册音视频来电观察者
     *
     * @param register 注册或注销
     */
    private static void registerAVChatIncomingCallObserver(boolean register) {
        AVChatManager.getInstance().observeIncomingCall(inComingCallObserver, register);
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(new Observer<CustomNotification>() {
            @Override
            public void onEvent(CustomNotification message) {
                // 在这里处理自定义通知。
                Log.e("TAG", message.toString());
                //Activity存在判断：未使用视频资源才可以跳转
//                if (!ActivityCollector.isActivityExist(VideoStateChooseActivity.class) && !ActivityCollector.isActivityExist(ChatRoomActivity.class)) {
//                    VideoStateChooseActivity.incomingCall(context, message);
//                } else {
//                    new Handler(Looper.getMainLooper()).post(new Runnable() {
//                        public void run() {
//                            Toast.makeText(context, "有新的视频请求", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
            }
        }, true);
    }

    private static Observer<AVChatData> inComingCallObserver = new Observer<AVChatData>() {
        @Override
        public void onEvent(final AVChatData data) {
            String extra = data.getExtra();
            Log.e("Extra", "Extra Message->" + extra);
            NimActivity.incomingCall(context, data);
        }
    };
}
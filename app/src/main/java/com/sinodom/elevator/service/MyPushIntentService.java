package com.sinodom.elevator.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.BridgeWebViewActivity;
import com.sinodom.elevator.activity.elevator.MainActivity;
import com.sinodom.elevator.activity.elevator.business.complaint.ComplaintActivity;
import com.sinodom.elevator.activity.elevator.nim.ChatRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.VideoStateChooseActivity;
import com.sinodom.elevator.activity.elevator.rescue.PhotoActivity;
import com.sinodom.elevator.activity.sys.AbnormalActivity;
import com.sinodom.elevator.activity.sys.LaunchActivity;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.single.SoundPoolManager;
import com.sinodom.elevator.util.ActivityCollector;
import com.sinodom.elevator.util.AppUtil;
import com.sinodom.elevator.util.HtmlRegexpUtil;
import com.sinodom.elevator.util.SharedPreferencesUtil;
import com.sinodom.elevator.util.TextUtil;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.Map;

/**
 * 友盟推送
 * 完全自定义处理
 */
public class MyPushIntentService extends UmengMessageService {

    // 如果需要打开Activity，请调用Intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)；否则无法打开Activity。
    private NotificationManager mNotificationManager;

    @Override
    public void onMessage(final Context context, Intent intent) {
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            Logger.d("message=" + message);      //消息体
            String type = msg.sound;
            String guid = "";
            String roomId = "";
            String roomName = "";
            String creator = "";
            Map<String, String> extra = msg.extra;
            if (extra != null && extra.size() > 0) {
//                type = extra.get("type");
                guid = extra.get("guid");
                roomId = extra.get("roomId");
                roomName = extra.get("roomName");
                creator = extra.get("creator");
            }
            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

            if (!TextUtils.isEmpty(type) && ElevatorManager.getInstance().getSession() != null
                    && !TextUtils.isEmpty((String) SharedPreferencesUtil.getParam(context, "history", "UserName", ""))) {
                final String finalGuid = guid;
                SoundPoolManager.init(context);
                switch (type) {
                    //通知通告
                    case Constants.PushType.NOTICE:
//                        if (ActivityCollector.isForeground(context, NoticeActivity.class.getName())) {
//                            NoticeActivity activity = ActivityCollector.getActivity(NoticeActivity.class);
//                            activity.rLoad();
//                        }
                        setPendingIntent(context, msg, type, 0);
                        break;
                    //投诉建议
                    case Constants.PushType.COMPLAINT:
//                        if (ActivityCollector.isForeground(context, ComplaintActivity.class.getName())) {
//                            ComplaintActivity activity = ActivityCollector.getActivity(ComplaintActivity.class);
//                            activity.rLoad();
//                        }
                        setPendingIntent(context, msg, type, 0);
                        break;
                    //查看图片
                    case Constants.PushType.PHOTO:
                        if (ActivityCollector.isForeground(context, PhotoActivity.class.getName())) {
                            PhotoActivity activity = ActivityCollector.getActivity(PhotoActivity.class);
                            activity.getPhoto(Integer.valueOf(guid));
                        }
                        setPendingIntent(context, msg, type, Integer.valueOf(guid));
                        break;
                    //救援任务
                    case Constants.PushType.RESCUE0:
                        SoundPoolManager.play(1);
                        if (ActivityCollector.isForeground(context, MainActivity.class.getName())) {
                            final MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!TextUtil.isEmpty(finalGuid)) {
                                        activity.loadDetail(Integer.valueOf(finalGuid));
                                    }
                                }
                            });
                        }
                        setPendingIntent(context, msg, type, Integer.valueOf(finalGuid));
                        break;
                    case Constants.PushType.RESCUE1:
                        SoundPoolManager.play(2);
                        if (ActivityCollector.isForeground(context, MainActivity.class.getName())) {
                            final MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!TextUtil.isEmpty(finalGuid)) {
                                        activity.loadDetail(Integer.valueOf(finalGuid));
                                    }
                                }
                            });
                        }
                        setPendingIntent(context, msg, type, Integer.valueOf(finalGuid));
                        break;
                    case Constants.PushType.RESCUE2:
                        SoundPoolManager.play(3);
                        if (ActivityCollector.isForeground(context, MainActivity.class.getName())) {
                            final MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!TextUtil.isEmpty(finalGuid)) {
                                        activity.loadDetail(Integer.valueOf(finalGuid));
                                    }
                                }
                            });
                        }
                        setPendingIntent(context, msg, type, Integer.valueOf(finalGuid));
                        break;
                    case Constants.PushType.RESCUE3:
                        SoundPoolManager.play(4);
                        if (ActivityCollector.isForeground(context, MainActivity.class.getName())) {
                            final MainActivity activity = ActivityCollector.getActivity(MainActivity.class);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!TextUtil.isEmpty(finalGuid)) {
                                        activity.loadDetail(Integer.valueOf(finalGuid));
                                    }
                                }
                            });
                        }
                        setPendingIntent(context, msg, type, Integer.valueOf(finalGuid));
                        break;
                    case Constants.PushType.VIDEO:
                        //Activity存在判断：未使用视频资源才可以跳转
                        if (!ActivityCollector.isActivityExist(ChatRoomActivity.class)) {
                            //builder.setContentIntent(PendingIntent.getActivity(this, 0, openNotification(context, type, roomId, roomName, creator), FLAG_UPDATE_CURRENT));
                            //判断app是否运行
                            if (AppUtil.isAppRunning(context)) {
                                Intent myintent = new Intent(context, VideoStateChooseActivity.class);
                                myintent.putExtra("roomId", roomId);
                                myintent.putExtra("roomName", roomName);
                                myintent.putExtra("creator", creator);
                                myintent.putExtra("isCreate", false);
                                myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(myintent);
                            }
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    Toast.makeText(context, "有新的视频请求", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPendingIntent(Context context, UMessage msg, String type, int guid) {
        // 创建一个NotificationManager的引用
        if (null == mNotificationManager) {
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // params
        int smallIconId = R.mipmap.ic_launcher;
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(smallIconId)
                .setContentTitle(msg.title)
                .setContentText(HtmlRegexpUtil.filterHtml(msg.text))
                .setTicker(msg.title)
                .setContentIntent(PendingIntent.getActivity(this, 0, openNotification(context, type, guid), 0));
        Notification n = builder.getNotification();
        n.defaults = Notification.DEFAULT_ALL;
        n.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(0, n);

    }

    private Intent openNotification(Context context, String type, int guid) {
        Intent intent;
        try {
            Logger.d("type==" + type);
            //判断app是否运行
            if (AppUtil.isAppRunning(context)) {
                //判断主页面是否存在
                if (ActivityCollector.isActivityExist(MainActivity.class)) {
                    switch (type) {
                        //通知通告
                        case Constants.PushType.NOTICE:
                            intent = new Intent(context, BridgeWebViewActivity.class);
                            intent.putExtra("url", BuildConfig.SERVER + "WebApp/Notice/Index?UserId=" + ElevatorManager.getInstance().getSession().getUserID());
                            intent.putExtra("title", "通知通告");
                            break;
                        //投诉建议
                        case Constants.PushType.COMPLAINT:
                            intent = new Intent(context, BridgeWebViewActivity.class);
                            intent.putExtra("url", BuildConfig.SERVER + "WebApp/Complaint/Index?UserId=" + ElevatorManager.getInstance().getSession().getUserID());
                            intent.putExtra("title", "投诉建议");
                            break;
                        //查看图片
                        case Constants.PushType.PHOTO:
                            intent = new Intent(context, PhotoActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("LiftId", 0);
                            intent.putExtra("TaskId", guid);
                            break;
                        //救援任务
                        case Constants.PushType.RESCUE0:
                        case Constants.PushType.RESCUE1:
                        case Constants.PushType.RESCUE2:
                        case Constants.PushType.RESCUE3:
                            intent = new Intent(context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("guid", guid);
                            break;
                        //未知类型
                        default:
                            intent = new Intent(context, AbnormalActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            break;
                    }
                } else {
                    intent = new Intent(context, LaunchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                }
            } else {
                intent = new Intent(context, LaunchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            intent = new Intent(context, AbnormalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        }
        return intent;
    }
}
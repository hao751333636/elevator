package com.sinodom.elevator.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.sinodom.elevator.activity.elevator.nim.ChatRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.CreateRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.VideoStateChooseActivity;
import com.sinodom.elevator.bean.nim.RoomBean;
import com.sinodom.elevator.util.ActivityCollector;

/**
 * 自定义通知消息广播接收器
 */
public class CustomNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = context.getPackageName() + NimIntent.ACTION_RECEIVE_CUSTOM_NOTIFICATION;
        if (action.equals(intent.getAction())) {
            Log.e("TAG", "收到消息了");
            // 从intent中取出自定义通知
            CustomNotification notification = (CustomNotification) intent.getSerializableExtra(NimIntent.EXTRA_BROADCAST_MSG);
            //Activity存在判断：未使用视频资源才可以跳转
            if (!ActivityCollector.isActivityExist(VideoStateChooseActivity.class) && !ActivityCollector.isActivityExist(ChatRoomActivity.class)) {
                if (!CreateRoomActivity.isCall) {
                    VideoStateChooseActivity.incomingCall(context, notification);
                }
                Log.e("TAG", notification.getContent());
            } else {
                RoomBean roomBean = new Gson().fromJson(notification.getContent(), RoomBean.class);
                ChatRoomActivity activity = ActivityCollector.getActivity(ChatRoomActivity.class);
                if (activity.roomId.equals(roomBean.getRoomId())) {
                    activity.mRoomBean = roomBean;
                    activity.showUser();
                    activity.reJoin();
                }
//                Toast.makeText(context, "有新的视频请求", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

package com.sinodom.elevator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.nim.ChatRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.VideoStateChooseActivity;
import com.sinodom.elevator.activity.sys.LaunchActivity;
import com.sinodom.elevator.single.SoundPoolManager;
import com.sinodom.elevator.util.ActivityCollector;
import com.umeng.message.UmengNotifyClickActivity;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.Map;

/**
 * 小米、华为系统级推送
 */
public class MipushTestActivity extends UmengNotifyClickActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mipush_test);
        Intent intent = new Intent(this, LaunchActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
        startActivity(intent);
        finish();
    }

    //此方法必须调用，否则无法统计打开数
    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
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
                guid = extra.get("guid");
                roomId = extra.get("roomId");
                roomName = extra.get("roomName");
                creator = extra.get("creator");
            }
            if (!TextUtils.isEmpty(type)) {
                final String finalGuid = guid;
                SoundPoolManager.init(MipushTestActivity.this);
                switch (type) {
                    //通知通告
                    case Constants.PushType.NOTICE:
                        break;
                    //投诉建议
                    case Constants.PushType.COMPLAINT:
                        break;
                    //救援任务
                    case Constants.PushType.RESCUE0:
                        SoundPoolManager.play(1);
                        break;
                    case Constants.PushType.RESCUE1:
                        SoundPoolManager.play(2);
                        break;
                    case Constants.PushType.RESCUE2:
                        SoundPoolManager.play(3);
                        break;
                    case Constants.PushType.RESCUE3:
                        SoundPoolManager.play(4);
                        break;
                    case Constants.PushType.VIDEO:
                        //Activity存在判断：未使用视频资源才可以跳转
                        if (!ActivityCollector.isActivityExist(ChatRoomActivity.class)) {
                            Intent myintent = new Intent(MipushTestActivity.this, VideoStateChooseActivity.class);
                            myintent.putExtra("roomId", roomId);
                            myintent.putExtra("roomName", roomName);
                            myintent.putExtra("creator", creator);
                            myintent.putExtra("isCreate", false);
                            myintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MipushTestActivity.this.startActivity(myintent);
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    Toast.makeText(MipushTestActivity.this, "有新的视频请求", Toast.LENGTH_SHORT).show();
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
}

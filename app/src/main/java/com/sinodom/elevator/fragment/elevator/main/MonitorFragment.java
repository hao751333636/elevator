package com.sinodom.elevator.fragment.elevator.main;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.BridgeWebViewActivity;
import com.sinodom.elevator.activity.elevator.monitor.DebugActivity;
import com.sinodom.elevator.activity.elevator.nfc.EquipmentActivity;
import com.sinodom.elevator.activity.elevator.nim.ChatRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.CreateRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.NimCache;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.single.Server;
import com.sinodom.elevator.zxing.activity.CaptureActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2017/11/14.
 * 四维监控
 */

public class MonitorFragment extends BaseFragment {

    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monitor, null);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
    }

    @OnClick({R.id.llMonitor, R.id.llDebug, R.id.llVideo, R.id.llNfc, R.id.llLabel})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llMonitor:
                //物联监控
                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/FourMonitoring/Index?userid=" + manager.getSession().getUserID());
                intent.putExtra("title", "四维监控");
                startActivity(intent);
                break;
            case R.id.llDebug:
                intent = new Intent(getActivity(), DebugActivity.class);
                startActivity(intent);
                break;
            case R.id.llVideo:
                intent = new Intent(context, CreateRoomActivity.class);
                startActivity(intent);
                break;
            case R.id.llNfc:
                //设备绑定
                intent = new Intent(getActivity(), EquipmentActivity.class);
                startActivity(intent);
//                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
//                intent.putExtra("url", BuildConfig.SERVER + "WebApp/NFC/Index?userid=" + manager.getSession().getUserID());
//                intent.putExtra("source", CaptureActivity.SCAN_BQBD);//不为空打开扫码
//                intent.putExtra("title", "标签绑定");
//                startActivity(intent);
                break;
            case R.id.llLabel:
                //配件管理
//                //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
//                    if (!permission) {
//                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 200);
//                    } else {
//                        intent = new Intent(getActivity(), CaptureActivity.class);
//                        intent.putExtra("source", CaptureActivity.SCAN_PJGL);
//                        startActivity(intent);
//                    }
//                } else {
//                    if (PermissionUtil.cameraIsCanUse()) {
//                        intent = new Intent(getActivity(), CaptureActivity.class);
//                        intent.putExtra("source", CaptureActivity.SCAN_PJGL);
//                        startActivity(intent);
//                    } else {
//                        getPermission("缺少相机权限");
//                    }
//                }
                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/CheckParts/LiftParts?userid=" + manager.getSession().getUserID());
                intent.putExtra("source", CaptureActivity.SCAN_PJGL);//不为空打开扫码
                intent.putExtra("title", "配件管理");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                intent.putExtra("source", CaptureActivity.SCAN_PJGL);
                startActivity(intent);
            } else {
                getPermission("请授权APP访问摄像头权限！");
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //报警上报
    private void callLoad() {
        final String roomId = UUID.randomUUID().toString();
        final Map<String, Object> map = new HashMap<>();
        map.put("SendUser", NimCache.getAccount());
        map.put("RoomID", roomId);
        map.put("RoomName", roomId);
        Call<ResponBean> call = Server.getInstance().getService().getAdviceList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                }
            }
        });
    }

    /**
     * 创建会议频道
     */
    private void createChannel(final String roomId) {
        AVChatManager.getInstance().createRoom(roomId, getString(R.string.app_name), new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                avChatChannelInfo.getTimetagMs();
//                RoomActivity.incomingRoom(mContext, roomId);
                Intent intent = new Intent(context, ChatRoomActivity.class);
                intent.putExtra("roomId", roomId);
                intent.putExtra("roomName", roomId);
                intent.putExtra("creator", NimCache.getAccount());
                intent.putExtra("isCreate", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailed(int i) {
                Toast.makeText(context, "创建房间失败, code:" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(context, "创建房间异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

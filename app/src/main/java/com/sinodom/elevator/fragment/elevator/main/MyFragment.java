package com.sinodom.elevator.fragment.elevator.main;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.BridgeWebViewActivity;
import com.sinodom.elevator.activity.elevator.my.ChangePasswordActivity;
import com.sinodom.elevator.activity.elevator.my.InformationActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.Preferences;
import com.sinodom.elevator.activity.sys.LoginActivity;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.service.HeartbeatService;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.util.SharedPreferencesUtil;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 安卓 on 2017/11/14.
 * 我的
 */

public class MyFragment extends BaseFragment {

    @BindView(R.id.tvMy)
    TextView tvMy;
    Unbinder unbinder;
    @BindView(R.id.tvLoginName)
    TextView tvLoginName;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserID)
    TextView tvUserID;
    @BindView(R.id.rlPassword)
    RelativeLayout rlPassword; //修改密码
    @BindView(R.id.rlAbout)
    RelativeLayout rlAbout;   //关于电梯云
    @BindView(R.id.rlContact)
    RelativeLayout rlContact;  //联系我们

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        tvLoginName.setText("登录名：" + manager.getSession().getLoginName());
        tvUserName.setText("用户名：" + manager.getSession().getUserName());
        tvUserID.setText("登录ID：" + manager.getSession().getUserID());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rlPassword, R.id.rlAbout, R.id.rlContact, R.id.tvMy, R.id.ivHead, R.id.tvLoginName, R.id.tvUserName, R.id.tvUserID})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rlPassword:
                intent.setClass(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.rlAbout:
                break;
            case R.id.rlContact:
                intent.setClass(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("title", "联系我们");
                intent.putExtra("url", "http://www.sinodom.ln.cn");
                startActivity(intent);
                break;
            case R.id.ivHead:
            case R.id.tvLoginName:
            case R.id.tvUserName:
            case R.id.tvUserID:
                intent.setClass(getActivity(), InformationActivity.class);
                startActivity(intent);
                break;
            case R.id.tvMy:
                new AlertDialog.Builder(getActivity())
                        .setTitle("退出登录")
                        .setMessage("确定退出吗？")
                        .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logout();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
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
//                    mPushAgent.disable(new IUmengCallback() {
//                        @Override
//                        public void onSuccess() {
//                            Logger.d("友盟推送关闭成功");
//                        }
//
//                        @Override
//                        public void onFailure(String s, String s1) {
//                            Logger.d("友盟推送关闭失败s=" + s + ",是s1=" + s1);
//                        }
//                    });
        }
        SharedPreferencesUtil.setParam(context, "history", "rememberLogin", false);
        SharedPreferencesUtil.setParam(context, "history", "UserName", "");
        SharedPreferencesUtil.setParam(context, "history", "PassWord", "");
        //网易云信退出登录
        NIMClient.getService(AuthService.class).logout();
        Preferences.saveUserAccount("");
        Preferences.saveUserToken("");
        manager.delSession();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}

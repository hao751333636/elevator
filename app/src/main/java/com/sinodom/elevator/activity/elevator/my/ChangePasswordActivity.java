package com.sinodom.elevator.activity.elevator.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.sys.LoginActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.util.SharedPreferencesUtil;
import com.sinodom.elevator.util.TextUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2018/1/3.
 * 我的——修改密码
 */

public class ChangePasswordActivity extends BaseActivity {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etOld)
    EditText etOld;
    @BindView(R.id.etNew)
    EditText etNew;
    @BindView(R.id.etYes)
    EditText etYes;
    @BindView(R.id.tvYes)
    TextView tvYes;

    private String oldPassword = "";
    private String yesPassword = "";
    private String newPassword = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.tvYes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvYes:
                oldPassword = etOld.getText().toString();
                yesPassword = etYes.getText().toString();
                newPassword = etNew.getText().toString();
                if (TextUtil.isEmpty(oldPassword)) {
                    showToast("原密码不能为空");
                } else if (TextUtil.isEmpty(newPassword)) {
                    showToast("新密码不能为空");
                } else if (TextUtil.isEmpty(yesPassword)) {
                    showToast("确认密码不能为空");
                } else {
                    if (oldPassword.length() < 6) {
                        showToast("原密码不能小于6位");
                    } else if (newPassword.length() < 6) {
                        showToast("新密码不能小于6位");
                    } else if (yesPassword.length() < 6) {
                        showToast("确认密码不能小于6位");
                    } else {
                        if (oldPassword.equals(newPassword)){
                            showToast("新密码不能与原密码一致");
                        }else {
                            if (newPassword.equals(yesPassword)){
                                ModifyPwd();
                            }else {
                                showToast("新密码与确认密码不一致");
                            }
                        }
                    }
                }
                break;
        }
    }

    private void Finishs(){
        SharedPreferencesUtil.setParam(context, "history", "rememberLogin", false);
        SharedPreferencesUtil.setParam(context, "history", "UserName", "");
        SharedPreferencesUtil.setParam(context, "history", "PassWord", "");
        manager.delSession();
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void ModifyPwd() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LoginName", manager.getSession().getLoginName());
        map.put("Password", oldPassword);
        map.put("NewPassword", newPassword);

        Call<ResponBean> call = server.getService().modifyPwd(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    showToast("" + response.body().getMessage());
                    if (response.body().isSuccess()) {
                        if (response.body().getMessage().equals("修改成功")){
                            Finishs();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("修改密码失败");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }
}

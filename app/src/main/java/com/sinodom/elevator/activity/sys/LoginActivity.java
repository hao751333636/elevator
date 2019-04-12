package com.sinodom.elevator.activity.sys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.MainActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.NimCache;
import com.sinodom.elevator.activity.elevator.nim.utils.Preferences;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.sys.LoginDataBean;
import com.sinodom.elevator.db.Session;
import com.sinodom.elevator.util.SharedPreferencesUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hyd on 2017/11/10.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.ivCipherDisplay)
    ImageView ivCipherDisplay;
    @BindView(R.id.etPwd)
    EditText etPwd;
    @BindView(R.id.bLogin)
    TextView bLogin;
    private int display;
    private Gson gson = new Gson();
    private AbortableFuture<LoginInfo> loginRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        ivCipherDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (display == 0) {
                    display = 1;
                    ivCipherDisplay.setImageResource(R.mipmap.login_cipher_display);
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    display = 0;
                    ivCipherDisplay.setImageResource(R.mipmap.login_cipher_hides);
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

    }

    @OnClick(R.id.bLogin)
    public void onViewClicked() {
        CharSequence etAccountCs = etAccount.getText();
        if (TextUtils.isEmpty(etAccountCs)) {
            showToast("请输入用户名");
            etAccount.requestFocus();
            return;
        }
        CharSequence etPwdCs = etPwd.getText();
        if (TextUtils.isEmpty(etPwdCs)) {
            showToast("请输入密码");
            etPwd.requestFocus();
            return;
        }
        login(etAccountCs.toString(), etPwdCs.toString());
    }


    private void login(final String account, final String pwd) {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserName", account);
        map.put("PassWord", pwd);
        Call<ResponBean> call = server.getService().login(Constants.Url.LOGIN, map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        LoginDataBean dataBean = gson.fromJson(response.body().getData(), LoginDataBean.class);
                        Logger.d(dataBean.getLoginToken());
                        if (dataBean.getID() != 0) {
                            SharedPreferencesUtil.setParam(context, "history", "rememberLogin", true);
                            SharedPreferencesUtil.setParam(context, "history", "UserName", "" + account);
                            SharedPreferencesUtil.setParam(context, "history", "PassWord", "" + pwd);
                            //Bugly提供了用户ID记录
                            MobclickAgent.onProfileSignIn(account);
                            CrashReport.setUserId(account);
                            CrashReport.putUserData(context, account, dataBean.getUserID() + "");
                            Session session = new Session();
                            session.setUserID(dataBean.getUserID());
                            session.setRoleId(dataBean.getUser().getRoleId());
                            session.setUserName(dataBean.getUser().getUserName());
                            session.setDeptId(dataBean.getUser().getDept().getID());
                            session.setRoleCode(dataBean.getUser().getDept().getRoleGroup().getRoleCode());
                            session.setLoginName(dataBean.getUser().getLoginName());
                            manager.setSession(session);
                            //网易云信登录
                            nimLogin(account, "123456");
                            goMain();
                        } else {
                            showToast("您的账号或密码错误！");
                        }
                    } else {
                        showToast("您的账号或密码错误！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("登录失败！");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                    finish();
                }
            }
        });
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }

    private void goMain() {
        Intent intent;
        switch (BuildConfig.APP_TYPE) {
            case Constants.AppType.ELEVATOR:
//                if (manager.getSession().getRoleId() == 22 || manager.getSession().getRoleId() == 23) {
//                    intent = new Intent(context, com.sinodom.elevator.activity.maintenance.MainActivity.class);
//                } else {
                intent = new Intent(context, MainActivity.class);
//                }
                startActivity(intent);
                finish();
                Logger.d("电梯:" + Constants.AppType.ELEVATOR);
                break;
            case Constants.AppType.MAINTENANCE:
                intent = new Intent(context, com.sinodom.elevator.activity.inspect.MainActivity.class);
                startActivity(intent);
                finish();
                Logger.d("维保:" + Constants.AppType.MAINTENANCE);
                break;
            default:
                intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
                Logger.d("电梯Default:" + Constants.AppType.ELEVATOR);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return doubleBackToExit(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}

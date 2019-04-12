package com.sinodom.elevator.activity.sys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.sinodom.elevator.db.StatusAction;
import com.sinodom.elevator.util.SharedPreferencesUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 闪屏页
 */
public class LaunchActivity extends BaseActivity {

    private Gson gson = new Gson();
    private AbortableFuture<LoginInfo> loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.e("---","onCreate");
        setContentView(R.layout.activity_launch);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // 是否自动登录
                if ((boolean) SharedPreferencesUtil.getParam(context, "history", "rememberLogin", false)) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    final String UserName = (String) SharedPreferencesUtil.getParam(LaunchActivity.this, "history", "UserName", "");
                    String PassWord = (String) SharedPreferencesUtil.getParam(LaunchActivity.this, "history", "PassWord", "");
                    map.put("UserName", UserName);
                    map.put("PassWord", PassWord);
                    Log.e("---","自动登录");
                    Call<ResponBean> call = server.getService().login(Constants.Url.LOGIN, map);
                    mRetrofitManager.call(call, new Callback<ResponBean>() {
                        @Override
                        public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                            try {
                                Log.e("---","自动登录onResponse");
                                if (response.body().isSuccess()) {
                                    Logger.json(response.body().getData());
                                    LoginDataBean dataBean = gson.fromJson(response.body().getData(), LoginDataBean.class);
                                    Logger.d(dataBean.getLoginToken());
                                    if (dataBean.getID() != 0) {
                                        //Bugly提供了用户ID记录
                                        MobclickAgent.onProfileSignIn(UserName);
                                        CrashReport.setUserId(UserName);
                                        CrashReport.putUserData(context, UserName, dataBean.getUserID() + "");
                                        Session session = new Session();
                                        session.setUserID(dataBean.getUserID());
                                        session.setRoleId(dataBean.getUser().getRoleId());
                                        session.setUserName(dataBean.getUser().getUserName());
                                        session.setDeptId(dataBean.getUser().getDept().getID());
                                        session.setRoleCode(dataBean.getUser().getDept().getRoleGroup().getRoleCode());
                                        session.setLoginName(dataBean.getUser().getLoginName());
                                        manager.setSession(session);
                                        //加载StatusAction
                                        loadStatusAction(false);
                                        //网易云信登录
                                        nimLogin(UserName, "123456");
//                                        nimLogin("esl_18642690025", "123456");
                                    } else {
                                        showToast("您的账号或密码错误！");
                                        Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    showToast("您的账号或密码错误！");
                                    Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (Exception e) {
                                showToast("登录失败！");
                                Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            hideLoading();
                        }

                        @Override
                        public void onFailure(Call<ResponBean> call, Throwable throwable) {
                            if (!call.isCanceled()) {
                                hideLoading();
                                showToast(parseError(throwable));
//                                finish();
                                goMain();
                            }
                        }
                    });
                } else {
                    switch (BuildConfig.APP_TYPE) {
                        case Constants.AppType.ELEVATOR:
                            //加载StatusAction
                            loadStatusAction(true);
                            break;
                        case Constants.AppType.MAINTENANCE:
                            Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                    }
                }
            }
        }, 1000);
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

    //加载StatusAction
    private void loadStatusAction(final boolean isFirst) {
        Log.e("---","加载StatusAction");
        Call<ResponBean> call = server.getService().getLastStatusActionList();
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    Log.e("---","加载StatusAction onResponse");
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<StatusAction> list = gson.fromJson(response.body().getData(), new TypeToken<List<StatusAction>>() {
                        }.getType());
                        manager.addStatusActionList(list);
                        if (isFirst) {
                            Intent intent = new Intent(LaunchActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.e("---","goMain");
                            goMain();
                        }
                    } else {
                        showToast("加载系统字典失败！");
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("加载系统字典失败！");
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    finish();
                }
            }
        });
    }

    private void goMain() {
        Intent intent;
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }

}

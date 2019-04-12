package com.sinodom.elevator.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.single.Server;
import com.sinodom.elevator.util.ActivityCollector;
import com.sinodom.elevator.util.retrofit2.RetrofitManager;
import com.sinodom.elevator.view.LoadView;
import com.sinodom.elevator.view.MyAlertDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

public class BaseActivity extends AppCompatActivity {

    protected Toast toast = null;  //用于判断是否已有Toast执行
    protected Activity activity;
    protected Context context;
    protected long exitTime = 0;
    protected LayoutInflater inflater;
    protected LoadView loadView;
    protected InputMethodManager imm;
    protected ElevatorManager manager;
    protected Server server;
    protected String userId = "";
    protected RetrofitManager mRetrofitManager;
    public static int activityActive; //全局变量,表示当前在前台的activity数量
    public static boolean isBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityCollector.addActivity(this, getClass());
        activity = this;
        context = this;
        PushAgent.getInstance(context).onAppStart();//友盟统计应用启动数据
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager = ElevatorManager.getInstance();
        server = Server.getInstance();
        inflater = LayoutInflater.from(context);
        mRetrofitManager = new RetrofitManager();
        if (manager.getSession() != null) {
            userId = "" + manager.getSession().getUserID();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (loadView != null && loadView.isShowing()) {
                loadView.dismiss();
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideKeyboard(event);
        return super.onTouchEvent(event);
    }

    protected void hideKeyboard(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            hideKeyboard();
        }
    }

    protected void hideKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected Boolean doubleBackToExit(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast(getString(R.string.label_push_again_to_exit));
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                getHome();
            }
            return true;
        }
        return false;
    }

    public void getHome() {
        //模拟HOME键
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    /*
    SuperToast
     */
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    //原生
    public void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoading();
                if (toast == null) {
                    toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);  //正常执行
                } else {
                    toast.setText(text);  //用于覆盖前面未消失的提示信息
                }
                toast.show();
            }
        });
    }

    /*
    Loading
     */
    public void showLoading() {
        showLoading(R.string.label_loading);
    }

    public void showLoading(int resId) {
        showLoading(getString(resId));
    }

    public void showLoading(String text) {
        hideLoading();
        if (loadView == null) {
            loadView = new LoadView(context, R.style.dialog);
        }
        loadView.setInfo(text);
        if (!loadView.isShowing()) {
            loadView.show();
        }
    }

    public void showLoading(String text, DialogInterface.OnCancelListener listener) {
        showToast(text);
        loadView.setOnCancelListener(listener);
    }

    public void hideLoading() {
        if (loadView != null && loadView.isShowing()) {
            loadView.dismiss();
        }
    }

    public boolean selfPermissionGranted(String permission) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            // For Android < Android M, self permissions are always granted.
            boolean result = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    // targetSdkVersion >= Android M, we can
                    // use Context#checkSelfPermission
                    result = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
                    if (result && permission.equals(Manifest.permission.READ_PHONE_STATE)) {
                        //适配小米机型
                        AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
                        int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_PHONE_STATE, android.os.Process.myUid(), getPackageName());
                        result = (checkOp == AppOpsManager.MODE_ALLOWED);
                    }
                    //用户拒绝后选择不在提示后
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                        result = false;
                    }
                } else {
                    // targetSdkVersion < Android M, we have to use PermissionChecker
                    result = PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED;
                }
            }
            return result;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void getPermission(String text) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(context, R.style.DialogStyle1);
        myAlertDialog.setTitle(text);
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

    public String parseError(Throwable throwable) {
        if (throwable != null && throwable.getMessage() != null) {
            Logger.e(throwable.getMessage());
        }
        if (throwable instanceof HttpException) {
            return "网络已断开";
        } else if (throwable instanceof ConnectException) {
            return "网络不给力";
//        } else if (throwable instanceof ServerException) {
//            return "服务器错误";
        } else if (throwable instanceof JsonParseException) {
            return "解析失败";
        } else if (throwable instanceof JSONException) {
            return "解析失败";
        } else if (throwable instanceof ParseException) {
            return "解析失败";
        } else if (throwable instanceof SocketTimeoutException) {
            return "请求超时";
        } else if (throwable instanceof UnknownHostException) {
            return "网络不给力";
        } else {
            if (throwable.getMessage() == null) {
                return "服务端无返回信息";
            } else {
                return throwable.getMessage();
            }
        }
    }

    @Override
    protected void onStart() {
        //app 从后台唤醒，进入前台
        activityActive++;
        if (activityActive > 1) {
            isBackground = false;
        }
        if (isBackground)
            Log.i("TAG", "程序从后台唤醒");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        activityActive--;
        if (activityActive == 0) {
            //app 进入后台
            isBackground = true;//记录当前已经进入后台
        }
        if (isBackground)
            Log.i("TAG", "程序进入后台");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

}
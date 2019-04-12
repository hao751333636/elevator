package com.sinodom.elevator.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.single.Server;
import com.sinodom.elevator.util.retrofit2.RetrofitManager;
import com.sinodom.elevator.view.LoadView;
import com.sinodom.elevator.view.MyAlertDialog;
import com.umeng.message.PushAgent;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;

public class BaseFragment extends Fragment {

    protected Activity activity;
    protected Context context;
    protected LayoutInflater inflater;
    protected Toast toast = null;  //用于判断是否已有Toast执行
    protected LoadView loadView;
    protected ElevatorManager manager;
    protected Server server;
    protected RetrofitManager mRetrofitManager;
    protected String userId = "";

    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    private View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        activity = getActivity();
    }

    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new MonitorFragment()时也会被回调
    //如果我们需要在 MonitorFragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(context).onAppStart();//友盟统计应用启动数据
        inflater = LayoutInflater.from(context);
        manager = ElevatorManager.getInstance();
        server = Server.getInstance();
        mRetrofitManager = new RetrofitManager();
        if (manager.getSession() != null){
            userId ="" + manager.getSession().getUserID();
        }
        initVariable();
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (rootView == null) {
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);
    }

    //SuperToast
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    //原生
    public void showToast(final String text) {
        activity.runOnUiThread(new Runnable() {
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
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                startActivity(localIntent);
            }
        });
        myAlertDialog.show();
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
                        AppOpsManager appOpsManager = (AppOpsManager) getActivity().getSystemService(Context.APP_OPS_SERVICE);
                        int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_READ_PHONE_STATE, android.os.Process.myUid(), getActivity().getPackageName());
                        result = (checkOp == AppOpsManager.MODE_ALLOWED);
                    }
                    //用户拒绝后选择不在提示后
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
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
        } else {
            if (throwable.getMessage() == null) {
                return "服务端无返回信息";
            } else {
                return throwable.getMessage();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 MonitorFragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 MonitorFragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     *
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {

    }

    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }
}

package com.sinodom.elevator.activity.elevator.monitor;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.fragment.elevator.monitor.DebugFragment;
import com.sinodom.elevator.fragment.elevator.monitor.InputFragment;
import com.sinodom.elevator.socket.MinaCallBack;
import com.sinodom.elevator.socket.MinaConnector;
import com.sinodom.elevator.socket.NetProxy;
import com.sinodom.elevator.util.HexUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设备调试
 */
public class DebugActivity extends BaseActivity implements MinaCallBack {

    @BindView(R.id.tvInput)
    TextView tvInput;
    @BindView(R.id.tvInput1)
    TextView tvInput1;
    @BindView(R.id.tvDebug)
    TextView tvDebug;
    @BindView(R.id.tvDebug1)
    TextView tvDebug1;
    @BindView(R.id.vpInternetAlarm)
    ViewPager vpInternetAlarm;
    @BindView(R.id.tvWifi)
    TextView tvWifi;
    @BindView(R.id.tvTcp)
    TextView tvTcp;
    @BindView(R.id.tvAuth)
    TextView tvAuth;
    private DebugFragment mDebugFragment;
    private InputFragment mInputFragment;
    private List<Fragment> list;
    private WifiManager mWifiManager;
    //wifi广播接收器
    private WifiStateReceiver mWifiStateReceiver;
    private AlertDialog.Builder mAlertDialog;
    private boolean isShow = false;
    private boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this);
        init();
        initWifi();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initWifi() {
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = mWifiManager.getConnectionInfo();
        if (info.getSSID().contains("HD2000") && info.getSupplicantState().equals(SupplicantState.COMPLETED)) {
            showLoading("正在建立连接，请稍等！");
            initSocket();
        } else {
            if (!isShow) {
                showAlert();
            }
        }
    }

    private void showAlert() {
        isShow = true;
        mAlertDialog.setTitle("需要手动连接Wifi");
        mAlertDialog.setMessage("请连接名称为【HD2000】的Wifi");
        mAlertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                isShow = false;
            }
        });
        mAlertDialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }

    private void initSocket() {
        if (!MinaConnector.isCon) {
            boolean result = NetProxy.getConnect(this);
            if (result) {
                showToast("通信已建立！");
                tvTcp.setText("通信已建立");
                hideLoading();
                //请求授权
                String request = "E1 " + HexUtils.getHexUserID();
                NetProxy.send(request + " " + HexUtils.getCode(request));
            } else {
                showToast("通讯建立失败！");
                finish();
            }
        } else {
            hideLoading();
            Logger.i("通讯已建立！");
        }
    }

    private void init() {
        isForeground = true;
        mAlertDialog = new AlertDialog.Builder(context);
        mDebugFragment = new DebugFragment();
        mInputFragment = new InputFragment();
        list = new ArrayList<>();
        list.add(mDebugFragment);
        list.add(mInputFragment);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vpInternetAlarm.setAdapter(adapter);
        vpInternetAlarm.setCurrentItem(0);
        tvInput.setTextColor(this.getResources().getColor(R.color.black2));
        tvDebug.setTextColor(this.getResources().getColor(R.color.actionbar));
        tvInput1.setVisibility(View.GONE);
        tvDebug1.setVisibility(View.VISIBLE);
        //设置viewpager页面滑动监听事件
        vpInternetAlarm.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @OnClick({R.id.ivBack, R.id.rlInput, R.id.rlDebug})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlDebug:
                vpInternetAlarm.setCurrentItem(0);
                tvInput.setTextColor(getResources().getColor(R.color.black2));
                tvDebug.setTextColor(getResources().getColor(R.color.actionbar));
                tvInput1.setVisibility(View.GONE);
                tvDebug1.setVisibility(View.VISIBLE);
                break;
            case R.id.rlInput:
                vpInternetAlarm.setCurrentItem(1);
                tvInput.setTextColor(getResources().getColor(R.color.actionbar));
                tvDebug.setTextColor(getResources().getColor(R.color.black2));
                tvInput1.setVisibility(View.VISIBLE);
                tvDebug1.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void messageReceivedAA(final String msg) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    public void run() {
                        mDebugFragment.setData(msg);
                    }
                });
    }

    @Override
    public void messageReceivedBB(String msg) {

    }

    @Override
    public void messageReceivedE1(final String msg) {
//        String[] string = msg.split(" ");
//        if (!HexUtils.checkCode(msg).equals(string[string.length - 1])) {
//            showToast("数据出错！");
//            return;
//        }
        NetProxy.send("04");
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    public void run() {
                        String[] string = msg.split(" ");
                        if (string[1].equals("00")) {
                            tvAuth.setText("已授权");
                        } else {
                            tvAuth.setText("未授权");
                        }
                    }
                });
    }

    @Override
    public void messageReceivedE2(final String msg) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    public void run() {
                        mInputFragment.setE2Data(msg);
                    }
                });
    }

    @Override
    public void messageReceivedE3(final String msg) {
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    public void run() {
                        mInputFragment.setE3Data(msg);
                    }
                });
    }

    @Override
    public void sessionClosed(boolean isCon) {
        if (isForeground) {
            new Handler(Looper.getMainLooper())
                    .post(new Runnable() {
                        public void run() {
                            showLoading("通信已断开正在重连，请稍等！");
                            tvTcp.setText("通信已断开");
                            initSocket();
                        }
                    });
        }
    }

    //处理Fragment和ViewPager的适配器
    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    vpInternetAlarm.setCurrentItem(0);
                    tvInput.setTextColor(getResources().getColor(R.color.black2));
                    tvDebug.setTextColor(getResources().getColor(R.color.actionbar));
                    tvInput1.setVisibility(View.GONE);
                    tvDebug1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    //向设备请求电梯信息
                    String request = "E2 00";
                    NetProxy.send(request + " " + HexUtils.getCode(request));
                    showLoading("加载中...");
                    vpInternetAlarm.setCurrentItem(1);
                    tvInput.setTextColor(getResources().getColor(R.color.actionbar));
                    tvDebug.setTextColor(getResources().getColor(R.color.black2));
                    tvInput1.setVisibility(View.VISIBLE);
                    tvDebug1.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }

    /**
     * wifi开关变化广播接收器
     * BroadcastReceiver
     */
    public class WifiStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                        tvWifi.setText("Wifi：");
                        tvTcp.setText("通信已断开");
                        NetProxy.stopNetConnect();
                        if (!isShow) {
                            showAlert();
                        }
                    } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        //获取当前wifi名称
                        tvWifi.setText("Wifi：" + wifiInfo.getSSID());
                        if (wifiInfo.getSSID().contains("HD2000") && wifiInfo.getSupplicantState().equals(SupplicantState.COMPLETED)) {
                            showLoading("正在建立连接，请稍等！");
                            initSocket();
                        } else {
                            tvTcp.setText("通信已断开");
                            if (!isShow) {
                                showAlert();
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //wifi广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        mWifiStateReceiver = new WifiStateReceiver();
        registerReceiver(mWifiStateReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mWifiStateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isForeground = false;
        NetProxy.stopNetConnect();

        WifiInfo info = mWifiManager.getConnectionInfo();
        if (info.getSSID().contains("HD2000")) {
            mWifiManager.disconnect();
//            boolean isRemoved = mWifiManager.removeNetwork(info.getNetworkId());
//            if (!isRemoved) {
//                int index = 0;
//                while (!isRemoved && index < 10) {
//                    index++;
//                    isRemoved = mWifiManager.removeNetwork(info.getNetworkId());
//                }
//            }
//
//            if (isRemoved) {
//            mWifiManager.saveConfiguration();
//            }
        }
    }
}

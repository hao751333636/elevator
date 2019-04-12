package com.sinodom.elevator.activity.elevator.monitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WifiActivity extends BaseActivity {

    private WifiManager mWifiManager;
    private List<AccessPoint> mList;
    private int mNetworkId;
    //wifi广播接收器
    private WifiStateReceiver mWifiStateReceiver;
    private boolean isWifi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        ButterKnife.bind(this);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    @OnClick({R.id.ivBack, R.id.lianjie, R.id.duankai, R.id.fasong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.lianjie:
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                break;
            case R.id.duankai:
                break;
            case R.id.fasong:
                break;
        }
    }

    public class AccessPoint {
        private String ssid;
        private String bssid;
        private String password;
        private float signalStrength;  // 0~100
        private String encryptionType;
        private int networkId;
        /**
         * aps are relative AccessPoints who share the same ssid while different bssid
         * we will treat them as one hotspot
         */
        private ArrayList<AccessPoint> relativeAPs;

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public String getBssid() {
            return bssid;
        }

        public void setBssid(String bssid) {
            this.bssid = bssid;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public float getSignalStrength() {
            return signalStrength;
        }

        public void setSignalStrength(float signalStrength) {
            this.signalStrength = signalStrength;
        }

        public String getEncryptionType() {
            return encryptionType;
        }

        public void setEncryptionType(String encryptionType) {
            this.encryptionType = encryptionType;
        }

        public int getNetworkId() {
            return networkId;
        }

        public void setNetworkId(int networkId) {
            this.networkId = networkId;
        }

        public ArrayList<AccessPoint> getRelativeAPs() {
            return relativeAPs;
        }

        public void setRelativeAPs(ArrayList<AccessPoint> relativeAPs) {
            this.relativeAPs = relativeAPs;
        }
    }

    public WifiConfiguration createConfiguration(AccessPoint ap) {
        String SSID = ap.getSsid();
        WifiConfiguration config = new WifiConfiguration();
        config.SSID = "\"" + SSID + "\"";

        String encryptionType = ap.getEncryptionType();
        String password = ap.getPassword();
        if (encryptionType.toLowerCase().contains("wep")) {
            /**
             * special handling according to password length is a must for wep
             */
            int i = password.length();
            if (((i == 10 || (i == 26) || (i == 58))) && (password.matches("[0-9A-Fa-f]*"))) {
                config.wepKeys[0] = password;
            } else {
                config.wepKeys[0] = "\"" + password + "\"";
            }
            config.allowedAuthAlgorithms
                    .set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (encryptionType.toLowerCase().contains("wpa")) {
            config.preSharedKey = "\"" + password + "\"";
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        } else {
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }
        return config;
    }

    public List<AccessPoint> getScanAp() {
        if (null == mWifiManager) {
            return null;
        }
        List<ScanResult> results = mWifiManager.getScanResults();
        if (results == null || results.size() <= 0) {
            mWifiManager.startScan();
            return null;
        }

        List<AccessPoint> aps = new ArrayList<AccessPoint>();
        for (ScanResult result : results) {
            if (TextUtils.isEmpty(result.SSID)) {
                continue;
            }

            AccessPoint accessPoint = new AccessPoint();
            accessPoint.setSsid(result.SSID);
            accessPoint.setBssid(result.BSSID);
            accessPoint.setEncryptionType(result.capabilities);
            try {
                double level = calculateSignalLevel(result.level, 5.0f) / 5.0;
                /**
                 * in some language such as portuguese,format will fail for unknown reason
                 */
                Locale.setDefault(Locale.US);
                DecimalFormat df = new DecimalFormat("#.##");
                level = Double.parseDouble(df.format(level));
                accessPoint.setSignalStrength((float) level * 100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int networkId = isConfigured(accessPoint);
            if (networkId > -1) {
                accessPoint.setNetworkId(networkId);
            }
            aps.add(accessPoint);
        }
        return aps;
    }

    public List<WifiConfiguration> getConfigedAp() {
        if (null == mWifiManager) {
            return null;
        }

        return mWifiManager.getConfiguredNetworks();
    }

    public int isConfigured(AccessPoint ap) {
        List<WifiConfiguration> configurations = getConfigedAp();
        if (configurations == null || configurations.size() <= 0) {
            return -1;
        }

        for (WifiConfiguration configuration : configurations) {
            /**
             * ssid in WifiConfiguration is always like "CCMC",and bssid is always null
             */
            if (configuration.SSID.replace("\"", "").trim().equals(ap.getSsid())) {
                return configuration.networkId;
            }
        }
        return -1;
    }

    public static float calculateSignalLevel(int rssi, float numLevels) {
        int MIN_RSSI = -100;
        int MAX_RSSI = -55;
        if (rssi <= MIN_RSSI) {
            return 0;
        } else if (rssi >= MAX_RSSI) {
            return numLevels;
        } else {
            float inputRange = (MAX_RSSI - MIN_RSSI);
            float outputRange = numLevels;
            return (float) (rssi - MIN_RSSI) * outputRange / inputRange;
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
                case WifiManager.WIFI_STATE_CHANGED_ACTION:
                    int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                            WifiManager.WIFI_STATE_DISABLED);
                    switch (wifistate) {
                        case WifiManager.WIFI_STATE_DISABLED:
                            //wifi已关闭
                            break;
                        case WifiManager.WIFI_STATE_ENABLED:
                            //wifi已打开
                            mWifiManager.startScan();
                            break;
                        case WifiManager.WIFI_STATE_ENABLING:
                            //wifi正在打开
                            break;
                        default:
                            break;
                    }
                    break;
                case WifiManager.SCAN_RESULTS_AVAILABLE_ACTION:
                    boolean isScanned = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, true);
                    if (isScanned) {
                        mList = getScanAp();
                        for (AccessPoint point : mList) {
                            if (point.getSsid().equals("ESP8266")) {
                                point.setPassword("1234567890");
                                WifiConfiguration config = createConfiguration(point);
                                /**
                                 * networkId is bigger than 0 in most time, 0 in few time and smaller than 0 in no time
                                 */
                                mNetworkId = mWifiManager.addNetwork(config);
                                mWifiManager.enableNetwork(mNetworkId, true);
                                isWifi = true;
                                break;
                            }
                        }
                    }
                    break;
                case WifiManager.SUPPLICANT_STATE_CHANGED_ACTION:
                    int error = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 0);
                    if (WifiManager.ERROR_AUTHENTICATING == error) {
                        showToast("密码错误,请手动连接Wifi！");
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                    break;
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (null != info) {
                        NetworkInfo.DetailedState state = info.getDetailedState();
                        if (info.isConnectedOrConnecting() && info.isConnected()) {
                            if (0 == state.compareTo(NetworkInfo.DetailedState.CONNECTED)) {
                                WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                                if (null != wifiInfo) {
                                    if (isWifi) {
                                        showToast("连接成功！");
                                        isWifi = false;
                                    }
                                }
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
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
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
        mWifiManager.disconnect();
        boolean isRemoved = mWifiManager.removeNetwork(mNetworkId);
        if (!isRemoved) {
            int index = 0;
            while (!isRemoved && index < 10) {
                index++;
                isRemoved = mWifiManager.removeNetwork(mNetworkId);
            }
        }

        if (isRemoved) {
            mWifiManager.saveConfiguration();
        }
    }
}

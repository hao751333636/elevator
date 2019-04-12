package com.sinodom.elevator.activity.elevator.nfc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.util.TextUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonitorBindActivity extends BaseActivity {

    @BindView(R.id.etRegister)
    EditText etRegister;
    @BindView(R.id.etSerial)
    EditText etSerial;
    public MyLocationListener mMyLocationListener;
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_bind);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        //百度定位
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        //百度定位-end
    }

    private void bindLift(double lon, double lat) {
        showLoading("加载中...");
        final Map<String, Object> map = new HashMap<>();
        map.put("SerialNumber", etSerial.getText().toString().trim());
        map.put("CertificateNum", etRegister.getText().toString().trim());
        map.put("Longitude", lon + "");
        map.put("Latitude", lat + "");
        map.put("UserId", userId);
        Call<ResponBean> call = server.getService().equipmentsBind(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("绑定失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.tvCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvCommit:
                if (TextUtil.isEmpty(etSerial.getText().toString().trim())) {
                    showToast("请输入设备序列号！");
                    return;
                }
                if (TextUtil.isEmpty(etRegister.getText().toString().trim())) {
                    showToast("请输入注册代码！");
                    return;
                }
                location();
                break;
        }
    }

    public void location() {
        boolean permission = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission == true && permission1 == true) {
            startLocationClient();
        } else {
            ActivityCompat.requestPermissions(MonitorBindActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }
    }

    private void startLocationClient() {
        showLoading("定位中...");
        mLocationClient.start();
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        //设置定位模式
        //Hight_Accuracy:高精度定位模式下，会同时使用GPS、Wifi和基站定位，返回的是当前条件下精度最好的定位结果,
        //Battery_Saving:低功耗定位模式下，仅使用网络定位即Wifi和基站定位，返回的是当前条件下精度最好的网络定位结果,
        //Device_Sensors:仅用设备定位模式下，只使用用户的GPS进行定位。这个模式下，由于GPS芯片锁定需要时间，首次定位速度会需要一定的时间,
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //返回的定位结果是百度经纬度，gcj02(国测局加密经纬度坐标),bd09ll(百度加密经纬度坐标),bd09(百度加密墨卡托坐标)
        option.setCoorType("bd09ll");
        //设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
        option.setScanSpan(0);
        option.setIsNeedAddress(true);//反地理编码
        mLocationClient.setLocOption(option);
    }

    /**
     * 实现定位回调监听
     */
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            hideLoading();
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                Logger.d("latitude=" + location.getLatitude() + "|longitude=" + location.getLongitude() + "|address=" + location.getAddrStr());
                bindLift(location.getLongitude(), location.getLatitude());
            } else {
                if (location.getLocType() == 167) {
                    Toast.makeText(context, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。", Toast.LENGTH_LONG).show();
                }
                Logger.d("百度定位失败");
                hideLoading();
            }
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationClient();
            } else {
                getPermission("请授权APP获取位置信息权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

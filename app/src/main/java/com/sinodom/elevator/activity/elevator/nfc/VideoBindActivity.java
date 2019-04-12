package com.sinodom.elevator.activity.elevator.nfc;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.lift.LiftCertificateNumBean;
import com.sinodom.elevator.bean.nfc.NfcBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 视频主机绑定
 */
public class VideoBindActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvNfcLift)
    TextView tvNfcLift;
    @BindView(R.id.tvNfcRegister)
    TextView tvNfcRegister;
    @BindView(R.id.tvNfcAddress)
    TextView tvNfcAddress;
    @BindView(R.id.ivBind)
    ImageView ivBind;
    private Gson gson = new Gson();
    private String mLiftNum;
    public MyLocationListener mMyLocationListener;
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_bind);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mLiftNum = getIntent().getStringExtra("liftNum");
        //百度定位
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        //百度定位-end
        showLoading("加载中...");
        loadData();
    }

    private void loadData() {
        Call<ResponBean> call = server.getService().getCheckTermIsNFC(mLiftNum);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<NfcBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<NfcBean>>() {
                        }.getType());
                        if (list.size() > 0) {
                            tvNfcRegister.setText(list.get(0).getCertificateNum());
                            tvNfcAddress.setText(list.get(0).getAddressPath() + list.get(0).getInstallationAddress());
                        }
                        tvNfcLift.setText(mLiftNum);
                        ivBind.setBackgroundResource(R.mipmap.ic_nfc_rebind_lift);
                    } else {
                        tvNfcLift.setText(mLiftNum);
                        ivBind.setBackgroundResource(R.mipmap.ic_nfc_bind_lift);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    private void loadLift(String code) {
        Call<ResponBean> call = server.getService().getLiftByCertificateNum(code);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        LiftCertificateNumBean bean = gson.fromJson(response.body().getData(), LiftCertificateNumBean.class);
                        tvLiftNum.setText(bean.getLiftNum());
                        tvAddress.setText(bean.getAddressPath() + bean.getInstallationAddress());
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

    //刷新数据
    public void rLoad() {
        loadData();
    }

    @OnClick({R.id.ivBack, R.id.ivBind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            //绑定
            case R.id.ivBind:
                shareDialog();
                break;
        }
    }

    private Dialog myDialog;
    private EditText etCertificateNum;
    private TextView tvLiftNum;
    private TextView tvAddress;

    private void shareDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_nfc_bind, null);
        myDialog = new Dialog(this, R.style.nfc_bind);
        myDialog.getWindow().setContentView(dialogView);
        TextView tvCommit = (TextView) dialogView.findViewById(R.id.tvCommit);
        TextView tvCommit1 = (TextView) dialogView.findViewById(R.id.tvCommit1);
        tvLiftNum = (TextView) dialogView.findViewById(R.id.tvLiftNum);
        tvAddress = (TextView) dialogView.findViewById(R.id.tvAddress);
        etCertificateNum = (EditText) dialogView.findViewById(R.id.etCertificateNum);
        tvCommit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence tvFaultCs = etCertificateNum.getText();
                if (TextUtils.isEmpty(tvFaultCs.toString()) || tvFaultCs.toString().length() != 20) {
                    showToast("请输入20位注册号码！");
                    return;
                }
                loadLift(etCertificateNum.getText().toString().trim());
            }
        });
        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence tvFaultCs = etCertificateNum.getText();
                if (TextUtils.isEmpty(tvFaultCs.toString()) || tvFaultCs.toString().length() != 20) {
                    showToast("请输入20位注册号码！");
                    return;
                }
                location();
            }
        });
        myDialog.show();
    }

    private void bindLift(String certificateNum, double lon, double lat) {
        showLoading("加载中...");
        final Map<String, Object> map = new HashMap<>();
        map.put("LiftNum", mLiftNum);
        map.put("CertificateNum", certificateNum);
        map.put("Longitude", lon + "");
        map.put("Latitude", lat + "");
        Call<ResponBean> call = server.getService().videoLiftSign(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        mLiftNum = response.body().getData();
                        rLoad();
                        showToast(response.body().getMessage());
                        myDialog.dismiss();
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

    public void location() {
        boolean permission = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission == true && permission1 == true) {
            startLocationClient();
        } else {
            ActivityCompat.requestPermissions(VideoBindActivity.this,
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
                bindLift(etCertificateNum.getText().toString().trim(), location.getLongitude(), location.getLatitude());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫码返回
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            rLoad();
        }
    }
}
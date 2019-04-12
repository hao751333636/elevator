package com.sinodom.elevator.activity.elevator.nfc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 安卓 on 2018/1/2.
 * 无纸化维保
 */
public class MaintenanceChooseActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvEleInfoNum)
    TextView tvEleInfoNum;
    @BindView(R.id.tvEleInfoIstrue)
    TextView tvEleInfoIstrue;
    @BindView(R.id.tvEleInfoPlace)
    TextView tvEleInfoPlace;
    @BindView(R.id.ivHalf)
    ImageView ivHalf;
    @BindView(R.id.ivQuarter)
    ImageView ivQuarter;
    @BindView(R.id.ivHalfYear)
    ImageView ivHalfYear;
    @BindView(R.id.ivYear)
    ImageView ivYear;
    private String ID = ""; //电梯ID
    private String InstallationAddress = "";  //电梯地址
    private String UploadDate = "";  //上次维保时间
    private String floorNumber = "";   //本次维保对比ID
    private String LongitudeAndLatitude = "";   //电梯位置
    private String LiftNum = "";   //电梯位置
    private String result = "";
    private String CheckType = "";
    private String GateNumber = "";
    private String taskID = "";
    public MyLocationListener mMyLocationListener;
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_choose2);
        ButterKnife.bind(this);
        //百度定位
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        //百度定位-end
        ID = getIntent().getStringExtra("ID");
        taskID = getIntent().getStringExtra("taskID");
        InstallationAddress = getIntent().getStringExtra("InstallationAddress");
        UploadDate = getIntent().getStringExtra("UploadDate");
        result = getIntent().getStringExtra("FloorNumber");
        floorNumber = result.substring(0, result.indexOf("."));
        LongitudeAndLatitude = getIntent().getStringExtra("LongitudeAndLatitude");
        LiftNum = getIntent().getStringExtra("LiftNum");
        GateNumber = getIntent().getStringExtra("GateNumber");


        tvEleInfoNum.setText(LiftNum);
        if (TextUtil.isEmpty(UploadDate)) {
            tvEleInfoIstrue.setText("尚未维保");
        } else {
            String s3 = "" + UploadDate;
            String[] temp = null;
            temp = s3.split("T");
            String strTime = temp[1].substring(0, 5);
            tvEleInfoIstrue.setText("" + temp[0] + "  " + strTime);
        }
        tvEleInfoPlace.setText(InstallationAddress);

        location();
    }

    private void jump(String CheckType, String CType) {
        Bundle bundle = new Bundle();
        bundle.putString("ID", "" + ID);
        bundle.putString("taskID", "" + taskID);
        bundle.putString("InstallationAddress", "" + InstallationAddress);
        bundle.putString("UploadDate", "" + UploadDate);
        bundle.putString("FloorNumber", "" + result);
        bundle.putString("LongitudeAndLatitude", "" + LongitudeAndLatitude);
        bundle.putString("LiftNum", "" + LiftNum);
        bundle.putString("CheckType", "" + CheckType);
        bundle.putString("CType", "" + CType);
        startActivityForResult(new Intent(MaintenanceChooseActivity.this, MaintenanceInspectionActivity.class).putExtras(bundle), Constants.Code.GO_SCORE);
    }

    @OnClick({R.id.ivHalf, R.id.ivQuarter, R.id.ivHalfYear, R.id.ivYear, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivHalf:
                jump("0", "0");
                break;
            case R.id.ivQuarter:
                jump("2", "2");
                break;
            case R.id.ivHalfYear:
                jump("3", "3");
                break;
            case R.id.ivYear:
                jump("4", "4");
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }

    public void location() {
        boolean permission = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission == true && permission1 == true) {
            startLocationClient();
        } else {
            ActivityCompat.requestPermissions(MaintenanceChooseActivity.this,
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
//                bindLift(etCertificateNum.getText().toString().trim());
                LongitudeAndLatitude = location.getLongitude() + "," + location.getLatitude();
                String[] str = GateNumber.split("\\|");
                ivHalf.setVisibility(View.GONE);
                ivQuarter.setVisibility(View.GONE);
                ivHalfYear.setVisibility(View.GONE);
                ivYear.setVisibility(View.GONE);
                for (String a : str) {
                    if (a.equals("1")) {
                        ivHalf.setVisibility(View.VISIBLE);
                    }
                    if (a.equals("2")) {
                        ivQuarter.setVisibility(View.VISIBLE);
                    }
                    if (a.equals("3")) {
                        ivHalfYear.setVisibility(View.VISIBLE);
                    }
                    if (a.equals("4")) {
                        ivYear.setVisibility(View.VISIBLE);
                    }
                }
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

    //拍照返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            finish();
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
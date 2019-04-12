package com.sinodom.elevator.activity.inspect;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.InspectTypeAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.inspect.InspectBean;
import com.sinodom.elevator.util.TextUtil;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 检验选择
 */
public class InspectChooseActivity extends BaseActivity implements BaseAdapter.OnItemClickListener {

    @BindView(R.id.tvEleInfoNum)
    TextView tvEleInfoNum;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvRegistrationCode)
    TextView tvRegistrationCode;
    @BindView(R.id.listView)
    ListView mListView;
    InspectTypeAdapter mAdapter;
    private String mLiftNum;
    private InspectBean mBean;
    private AlertDialog.Builder alertDialog;
    //检验项选择-弹出显示项
    private String[] jItems;
    private String[] wItems;
    public MyLocationListener mMyLocationListener;
    public LocationClient mLocationClient;
    private double longitude;
    private double latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_choose);
        ButterKnife.bind(this);
        mLiftNum = getIntent().getStringExtra("inspectId");
        mAdapter = new InspectTypeAdapter(context);
        mAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        alertDialog = new AlertDialog.Builder(context);
        //百度定位
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        //百度定位-end
        boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean permission2 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission1 == true && permission2 == true) {
            startLocationClient();
        } else {
            ActivityCompat.requestPermissions(InspectChooseActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
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
            if (null != location && location.getLocType() != BDLocation.TypeServerError
                    && !TextUtil.isEmpty(location.getAddrStr()) && location.getLatitude() != 4.9E-324 && location.getLongitude() != 4.9E-324
                    && location.getLatitude() != 0 && location.getLongitude() != 0) {
                Logger.d("latitude=" + location.getLatitude() + "|longitude=" + location.getLongitude() + "|address=" + location.getAddrStr());
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                loadData();
            } else {
                if (location.getLocType() == 167) {
                    Toast.makeText(context, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。", Toast.LENGTH_LONG).show();
                } else if (location.getLocType() == 63) {
                    Toast.makeText(context, "网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "定位失败，无法获取有效定位依据", Toast.LENGTH_LONG).show();
                }
                Logger.d("百度定位失败");
            }
            mLocationClient.stop();
        }

        @Override
        public void onLocDiagnosticMessage(int i, int i1, String s) {
            super.onLocDiagnosticMessage(i, i1, s);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationClient();
            } else {
                getPermission("请授权APP获取位置信息权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void loadData() {
        showLoading("加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("LiftNum", "" + URLEncoder.encode(mLiftNum));
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().getInspectType(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(gson.toJson(response.body()));
                        mBean = gson.fromJson(response.body().getData(), InspectBean.class);
                        showToast("加载成功！");
                        tvEleInfoNum.setText(mBean.getLiftNum());
                        tvAddress.setText(mBean.getInstallationAddress());
                        tvRegistrationCode.setText(mBean.getCertificateNum());
                        mAdapter.setData(mBean.getInspectType());
                        mAdapter.notifyDataSetChanged();
                    } else {
                        showToast(response.body().getMessage());
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("暂无此电梯信息");
                }
                hideLoading();
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

    @OnClick({R.id.ivBack, R.id.tvCopy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                setFinish();
                break;
            case R.id.tvCopy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("" + tvRegistrationCode.getText());
                showToast("复制成功");
                break;
        }
    }

    private void setFinish() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定要退出么？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                        finish();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                    }
                });
        // 显示
        normalDialog.show();
    }

    @Override
    public void onItemClick(View v, final int position) {
        if (mBean.getInspectType().get(position).getState() != 1) {
            checkDept(position);
        } else {
            showToast("该项已经检验完成！");
        }
    }


    private void checkDept(final int position) {
        if (mBean.getInspectType().get(position).getDeptState() == 0) {
            if (mBean.getInspectType().get(position).getDept() != null && mBean.getInspectType().get(position).getDept().size() > 0) {
                jItems = new String[mBean.getInspectType().get(position).getDept().size()];
                for (int i = 0; i < mBean.getInspectType().get(position).getDept().size(); i++) {
                    jItems[i] = mBean.getInspectType().get(position).getDept().get(i).getDeptName();
                }
                alertDialog.setSingleChoiceItems(jItems, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                checkWorkForm(position, which);
                                dialog.dismiss();
                            }
                        }
                ).show();
            } else {
                checkWorkForm(position, 0);
            }
        } else {
            checkWorkForm(position, 0);
        }
    }

    private void checkWorkForm(final int position, final int mWhich) {
        if (mBean.getInspectType().get(position).getWorkFormState() == 0) {
            if (mBean.getInspectType().get(position).getWorkForm() != null && mBean.getInspectType().get(position).getWorkForm().size() > 0) {
                wItems = new String[mBean.getInspectType().get(position).getWorkForm().size()];
                for (int i = 0; i < mBean.getInspectType().get(position).getWorkForm().size(); i++) {
                    wItems[i] = mBean.getInspectType().get(position).getWorkForm().get(i).getName();
                }
                alertDialog.setSingleChoiceItems(wItems, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                toItem(position, mWhich, mBean.getInspectType().get(position).getWorkForm().get(which).getId());
                                dialog.dismiss();
                            }
                        }
                ).show();
            } else {
                toItem(position, mWhich, 0);
            }
        } else {
            toItem(position, mWhich, 0);
        }
    }

    private void toItem(int position, int which, int workId) {
        Intent intent = new Intent(context, InspectItemActivity.class);
        intent.putExtra("bean", mBean);
        intent.putExtra("position", position);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("which", which);
        intent.putExtra("workId", workId);
        startActivityForResult(intent, Constants.Code.GO_SCORE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //救援完成
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
            boolean permission2 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (permission1 == true && permission2 == true) {
                startLocationClient();
            } else {
                ActivityCompat.requestPermissions(InspectChooseActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        }
    }
}

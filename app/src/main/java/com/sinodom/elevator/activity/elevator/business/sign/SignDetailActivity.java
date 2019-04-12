package com.sinodom.elevator.activity.elevator.business.sign;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.elist.RecordDetailsAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.business.EquipmentDataBean;
import com.sinodom.elevator.bean.business.RecordDetailsBean;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.util.Util;
import com.sinodom.elevator.view.LinePathView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 维保确认签字
 */
public class SignDetailActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivScanning)
    ImageView ivScanning;
    TextureMapView mMapView;
    @BindView(R.id.lvRecord)
    ListView lvRecord;
    @BindView(R.id.lpView)
    LinePathView lpView;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor bitmap;
    private RecordDetailsBean recordDetailsBean;
    private EquipmentDataBean mBean;
    private String mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail);
        ButterKnife.bind(this);

        View vHead = View.inflate(this, R.layout.view_list_head_map, null);
        mMapView = (TextureMapView) vHead.findViewById(R.id.bmapView);
        lvRecord.addHeaderView(vHead, null, true);
        lvRecord.setHeaderDividersEnabled(false);

        mBean = (EquipmentDataBean) getIntent().getSerializableExtra("bean");
        mID = getIntent().getStringExtra("id");
        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_rescue_rescue_36dp);
        getCheck();
    }

    private void initView() {
        if (recordDetailsBean.getCheckDetails().size() != 0) {
            RecordDetailsAdapter adapter = new RecordDetailsAdapter(this, recordDetailsBean.getCheckDetails(), recordDetailsBean.getUser().getUserName());
            lvRecord.setAdapter(adapter);
        }
        mBaiduMap = mMapView.getMap();
        // 不显示地图上比例尺
        mMapView.showScaleControl(false);
        // 不显示地图缩放控件（按钮控制栏）
        mMapView.showZoomControls(false);
        mMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //允许ScrollView截断点击事件，ScrollView可滑动
                    lvRecord.requestDisallowInterceptTouchEvent(false);
                } else {
                    //不允许ScrollView截断点击事件，点击事件由子View处理
                    lvRecord.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        //进入地图默认显示辽宁省
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(7).build()));
        setCenter(new LatLng(41.26401, 122.33086));
        if (!TextUtil.isEmpty(recordDetailsBean.getLongitudeAndLatitude())) {
            LatLng mLl = new LatLng(Double.valueOf(Util.getBaidu(recordDetailsBean.getLongitudeAndLatitude())[1]),
                    Double.valueOf(Util.getBaidu(recordDetailsBean.getLongitudeAndLatitude())[0]));
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(mLl).zoom(16.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            setMark(recordDetailsBean);
        }
    }

    //设置地图中心
    private void setCenter(LatLng pt) {
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(pt);
        mBaiduMap.animateMapStatus(status, 1000);
    }

    @OnClick({R.id.ivBack, R.id.ivScanning, R.id.tvClear, R.id.tvCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivScanning:
                break;
            case R.id.tvClear:
                lpView.clear();
                break;
            case R.id.tvCommit:
                boolean permission = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
                if (!permission) {
                    ActivityCompat.requestPermissions(SignDetailActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                } else
                    sign();
                break;
        }
    }

    private void sign() {
        try {
            if (lpView.getTouched()) {
                String path = lpView.save();
                uploadImage(path);
            } else {
                showToast("还没有签名！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sign();
            } else {
                getPermission("请授权APP获取存储文件权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //维保记录详细
    private void getCheck() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", userId);
        Call<ResponBean> call = server.getService().getCheck(map, mID);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        recordDetailsBean = gson.fromJson(response.body().getData(), RecordDetailsBean.class);
                        if (recordDetailsBean.getCheckDetails().size() != 0) {
                            initView();
                        } else {
                            showToast("无应对维保信息");
                            finish();
                        }
                    }
                } catch (Exception e) {
                    showToast("请求失败");
                    e.printStackTrace();
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

    public void uploadImage(String path) {
        showLoading("加载中...");
        //构建要上传的文件
        File file = new File(path);
        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        Call<ResponBean> call = server.getService().uploadFile(part);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        String data = response.body().getData();
                        Logger.d("成功 data = " + data);
                        upload(data);
                        showToast("文件上传成功");
                    } else {
                        showToast("文件上传失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("文件上传失败");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    private void upload(final String file) {
        showLoading("加载中...");
//        recordDetailsBean.setUse_UserID(manager.getSession().getUserID());
//        recordDetailsBean.setUse_UserName(manager.getSession().getUserName());
//        recordDetailsBean.setUse_Createdate(file);
//        recordDetailsBean.setUse_PhotoUrl(file);


        Map<String, Object> map = new HashMap<>();
        map.put("ID", recordDetailsBean.getID());
        map.put("Use_PhotoUrl", file);
        map.put("Use_UserID", manager.getSession().getUserID());
        map.put("Use_UserName", manager.getSession().getUserName());
        Call<ResponBean> call = server.getService().saveCheck(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    Gson gson = new Gson();
                    Logger.d("" + gson.toJson(response.body()));
                    if (response.body().isSuccess()) {
//                    ("提交成功！");
                        showToast("" + response.body().getMessage());
                        if (response.body().getMessage().equals("操作成功")) {
                            setResult(Constants.Code.RESCUE_OK);
                            finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("提交失败");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    private void setMark(RecordDetailsBean bean) {
        double lat = 0;
        double lon = 0;
        try {
            lat = Double.valueOf(Util.getBaidu(bean.getLongitudeAndLatitude())[1]);
            lon = Double.valueOf(Util.getBaidu(bean.getLongitudeAndLatitude())[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //定义Maker坐标点
        LatLng point = new LatLng(lat, lon);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                .zIndex(0);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        mMapView = null;
        bitmap.recycle();
        mRetrofitManager.cancelAll();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
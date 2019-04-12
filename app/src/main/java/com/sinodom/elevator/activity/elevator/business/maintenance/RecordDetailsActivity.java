package com.sinodom.elevator.activity.elevator.business.maintenance;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.elist.RecordDetailsAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.business.RecordDetailsBean;
import com.sinodom.elevator.util.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2017/12/18.
 * 业务 维保记录详情
 */

public class RecordDetailsActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivScanning)
    ImageView ivScanning;
    //    @BindView(R.id.lvRecord)
//    NoScrollListView lvRecord;
//    @BindView(R.id.bmapView)
    TextureMapView mMapView;
    @BindView(R.id.lvRecord)
    ListView lvRecord;
    //    @BindView(R.id.scrollView)
//    ScrollView scrollView;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor bitmap;

    private RecordDetailsBean recordDetailsBean;

    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        ButterKnife.bind(this);

        View vHead = View.inflate(this, R.layout.view_list_head_map, null);
        mMapView = (TextureMapView) vHead.findViewById(R.id.bmapView);
        lvRecord.addHeaderView(vHead);

        id = getIntent().getStringExtra("id");
        Logger.d("id=" + id);
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
        LatLng mLl = new LatLng(Double.valueOf(Util.getBaidu(recordDetailsBean.getLongitudeAndLatitude())[1]),
                Double.valueOf(Util.getBaidu(recordDetailsBean.getLongitudeAndLatitude())[0]));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(mLl).zoom(16.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        setMark(recordDetailsBean);
    }

    @OnClick({R.id.ivBack, R.id.ivScanning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivScanning:
                break;
        }
    }


    //维保记录详细
    private void getCheck() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", userId);
        Call<ResponBean> call = server.getService().getCheck(map, id);
        mRetrofitManager.call(call,new Callback<ResponBean>() {
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
                if(!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
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

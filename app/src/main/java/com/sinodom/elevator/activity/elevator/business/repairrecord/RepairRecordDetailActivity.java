package com.sinodom.elevator.activity.elevator.business.repairrecord;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.repairrecord.RepairRecordDetailBean;
import com.sinodom.elevator.util.Util;
import com.sinodom.elevator.util.glide.GlideApp;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 维修记录详情
 */
public class RepairRecordDetailActivity extends BaseActivity implements BaiduMap.OnMapStatusChangeListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.bmapView)
    TextureMapView mMapView;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvCreateTime)
    TextView tvCreateTime;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    @BindView(R.id.ivBeforePhoto)
    ImageView ivBeforePhoto;
    @BindView(R.id.ivAfterPhoto)
    ImageView ivAfterPhoto;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private Gson gson = new Gson();
    private String mId = "";
    private RepairRecordDetailBean mBean;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor bitmap;
    private LatLng mLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_record_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mId = getIntent().getStringExtra("id");
        bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.ic_rescue_alarm_36dp);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapStatusChangeListener(this);
        // 不显示地图上比例尺
        mMapView.showScaleControl(false);
        // 不显示地图缩放控件（按钮控制栏）
        mMapView.showZoomControls(false);

        mMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //允许ScrollView截断点击事件，ScrollView可滑动
                    scrollView.requestDisallowInterceptTouchEvent(false);
                } else {
                    //不允许ScrollView截断点击事件，点击事件由子View处理
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        showLoading("加载中...");
        loadData();
    }

    private void setMark(String str) {
        double lat = 0;
        double lon = 0;
        try {
            lat = Double.valueOf(Util.getBaidu(str)[1]);
            lon = Double.valueOf(Util.getBaidu(str)[0]);
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

    //加载更多
    private void loadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().getRepair(map, mId);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        mBean = gson.fromJson(response.body().getData(), RepairRecordDetailBean.class);
                        scrollView.setVisibility(View.VISIBLE);
                        mLl = new LatLng(Double.valueOf(Util.getBaidu(mBean.getRepairPosition())[1]),
                                Double.valueOf(Util.getBaidu(mBean.getRepairPosition())[0]));
                        setCenter();
                        setMark(mBean.getRepairPosition());
                        tvUserName.setText(mBean.getUser().getUserName());
                        tvCreateTime.setText(mBean.getCreateTime());
                        tvRemark.setText(mBean.getRemark());

                        GlideApp.with(context)
                                .load(server.getFileUrl(mBean.getBeforePhoto()))
                                .thumbnail(0.1f)
                                .error(R.mipmap.ic_failure)
                                .placeholder(R.mipmap.ic_load)
                                .into(ivBeforePhoto);

                        GlideApp.with(context)
                                .load(server.getFileUrl(mBean.getAfterPhoto()))
                                .thumbnail(0.1f)
                                .error(R.mipmap.ic_failure)
                                .placeholder(R.mipmap.ic_load)
                                .into(ivAfterPhoto);
                    }
                } catch (Exception e) {
                    scrollView.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    scrollView.setVisibility(View.GONE);
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        if (DistanceUtil.getDistance(mapStatus.target, mLl) < 100) {
            return;
        }
        mLl = mapStatus.target;
        setCenter();
    }

    //设置地图中心
    private void setCenter() {
        LatLng pt = new LatLng(Double.valueOf(Util.getBaidu(mBean.getRepairPosition())[1]),
                Double.valueOf(Util.getBaidu(mBean.getRepairPosition())[0]));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(pt).zoom(17.0f);
        MapStatusUpdate status = MapStatusUpdateFactory.newMapStatus(builder.build());
        mBaiduMap.animateMapStatus(status, 5000);
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
}

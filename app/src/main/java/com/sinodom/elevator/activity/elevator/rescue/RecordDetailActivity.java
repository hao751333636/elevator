package com.sinodom.elevator.activity.elevator.rescue;

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
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.RescueMaintenanceAdapter;
import com.sinodom.elevator.adapter.elist.RescueUseAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.rescue.RescueDetailBean;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.PhoneUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.util.Util;
import com.sinodom.elevator.view.NoScrollListView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 处置记录详情
 */
public class RecordDetailActivity extends BaseActivity implements BaiduMap.OnMapStatusChangeListener {
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.bmapView)
    TextureMapView mMapView;
    @BindView(R.id.tvStatusName)
    TextView tvStatusName;
    @BindView(R.id.tvRemedyUser)
    TextView tvRemedyUser;
    @BindView(R.id.tvCreateTime)
    TextView tvCreateTime;
    @BindView(R.id.tvUseConfirmTime)
    TextView tvUseConfirmTime;
    @BindView(R.id.tvMaintConfirmTime)
    TextView tvMaintConfirmTime;
    @BindView(R.id.tvMaintArriveTime)
    TextView tvMaintArriveTime;
    @BindView(R.id.tvRescueCompleteTime)
    TextView tvRescueCompleteTime;
    @BindView(R.id.tvLiftNum)
    TextView tvLiftNum;
    @BindView(R.id.tvCertificateNum)
    TextView tvCertificateNum;
    @BindView(R.id.tvMachineNum)
    TextView tvMachineNum;
    @BindView(R.id.tvBrand)
    TextView tvBrand;
    @BindView(R.id.tvModel)
    TextView tvModel;
    @BindView(R.id.tvInstallationAddress)
    TextView tvInstallationAddress;
    @BindView(R.id.tvLiftSiteDict)
    TextView tvLiftSiteDict;
    @BindView(R.id.tvLiftTypeDict)
    TextView tvLiftTypeDict;
    @BindView(R.id.tvUseDepartment)
    TextView tvUseDepartment;
    @BindView(R.id.lvUseDepartment)
    NoScrollListView lvUseDepartment;
    @BindView(R.id.tvMaintenanceDepartment)
    TextView tvMaintenanceDepartment;
    @BindView(R.id.lvMaintenanceDepartment)
    NoScrollListView lvMaintenanceDepartment;
    @BindView(R.id.tvSourceDict)
    TextView tvSourceDict;
    @BindView(R.id.tvRescueNumber)
    TextView tvRescueNumber;
    @BindView(R.id.tvRescuePhone)
    TextView tvRescuePhone;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tvReasonDict)
    TextView tvReasonDict;
    @BindView(R.id.tvRemedyDict)
    TextView tvRemedyDict;
    private RescueUseAdapter mRescueUseAdapter;
    private RescueMaintenanceAdapter mRescueMaintenanceAdapter;
    private RescueDetailBean mBean;
    private int mID;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor bitmap;
    private LatLng mLl;
    private Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mID = getIntent().getIntExtra("id", 0);
        mRescueUseAdapter = new RescueUseAdapter(context);
        mRescueMaintenanceAdapter = new RescueMaintenanceAdapter(context);
        lvUseDepartment.setAdapter(mRescueUseAdapter);
        lvMaintenanceDepartment.setAdapter(mRescueMaintenanceAdapter);
        mRescueUseAdapter.setOnClickListener(new BaseAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                try {
                    if (!TextUtil.isEmpty(mBean.getLift().getUseUsers().get(position).getMobile())) {
                        PhoneUtil.call(context, mBean.getLift().getUseUsers().get(position).getMobile());
                    } else {
                        showToast("电话为空！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电话为空！");
                }
            }
        });
        mRescueMaintenanceAdapter.setOnClickListener(new BaseAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                try {
                    if (!TextUtil.isEmpty(mBean.getLift().getMaintUsers().get(position).getMobile())) {
                        PhoneUtil.call(context, mBean.getLift().getMaintUsers().get(position).getMobile());
                    } else {
                        showToast("电话为空！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电话为空！");
                }

            }
        });
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
        loadDetailData();
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

    //加载详情
    private void loadDetailData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().getTaskDetail(map, mID);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        RescueDetailBean bean = gson.fromJson(response.body().getData(), RescueDetailBean.class);
                        mBean = bean;
                        scrollView.setVisibility(View.VISIBLE);
                        mLl = new LatLng(Double.valueOf(Util.getBaidu(mBean.getLift().getBaiduMapXY())[1]),
                                Double.valueOf(Util.getBaidu(mBean.getLift().getBaiduMapXY())[0]));
                        setCenter();
                        setMark(mBean.getLift().getBaiduMapXY());

                        tvStatusName.setText(mBean.getStatusName());
                        tvRescueNumber.setText(mBean.getRescueNumber() + "");
                        tvRescuePhone.setText(mBean.getRescuePhone());
                        tvContent.setText(mBean.getContent());
                        tvCreateTime.setText(DateUtil.format(mBean.getCreateTime()));
                        tvMaintConfirmTime.setText(DateUtil.format(mBean.getMaintConfirmTime()));
                        tvRescueCompleteTime.setText(DateUtil.format(mBean.getRescueCompleteTime()));
                        tvUseConfirmTime.setText(DateUtil.format(mBean.getUseConfirmTime()));
                        tvMaintArriveTime.setText(DateUtil.format(mBean.getMaintArriveTime()));
                        if (mBean.getLift() != null) {
                            tvBrand.setText(mBean.getLift().getBrand());
                            tvModel.setText(mBean.getLift().getModel());
                            tvLiftNum.setText(mBean.getLift().getLiftNum());
                            tvMachineNum.setText(mBean.getLift().getMachineNum());
                            tvCertificateNum.setText(mBean.getLift().getCertificateNum());
                            tvInstallationAddress.setText(mBean.getLift().getInstallationAddress());
                            tvLiftTypeDict.setText(mBean.getLift().getLiftTypeDict() != null ? mBean.getLift().getLiftTypeDict().getDictName() : "");
                            tvUseDepartment.setText(mBean.getLift().getUseDepartment() != null ? mBean.getLift().getUseDepartment().getDeptName() : "");
                            tvMaintenanceDepartment.setText(mBean.getLift().getMaintenanceDepartment() != null ? mBean.getLift().getMaintenanceDepartment().getDeptName() : "");
                            tvLiftSiteDict.setText(mBean.getLift().getLiftSiteDict() != null ? mBean.getLift().getLiftSiteDict().getDictName() : "");
                            if (mBean.getLift().getUseUsers() != null) {
                                mRescueUseAdapter.setData(mBean.getLift().getUseUsers());
                                mRescueUseAdapter.notifyDataSetChanged();
                            }
                            if (mBean.getLift().getMaintUsers() != null) {
                                mRescueMaintenanceAdapter.setData(mBean.getLift().getMaintUsers());
                                mRescueMaintenanceAdapter.notifyDataSetChanged();
                            }
                        }
                        tvSourceDict.setText(mBean.getSourceDict() != null ? mBean.getSourceDict().getDictName() : "");
                        tvRemedyUser.setText(mBean.getRemedyUser() != null ? mBean.getRemedyUser().getUserName() + "(" + mBean.getRemedyUser().getMobile() + ")" : "");
                        tvReasonDict.setText(mBean.getReasonDict() != null ? mBean.getReasonDict().getDictName() : "");
                        tvRemedyDict.setText(mBean.getRemedyDict() != null ? mBean.getRemedyDict().getDictName() : "");
                    } else {
                        scrollView.setVisibility(View.GONE);
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    scrollView.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    scrollView.setVisibility(View.GONE);
                    showToast(parseError(throwable));
                }
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.tvRemedyUser, R.id.tvRescuePhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvRemedyUser:
                try {
                    if (!TextUtil.isEmpty(mBean.getRemedyUser().getPhone())) {
                        PhoneUtil.call(context, mBean.getRemedyUser().getPhone());
                    } else {
                        showToast("电话为空！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电话为空！");
                }
                break;
            case R.id.tvRescuePhone:
                if (!TextUtil.isEmpty(mBean.getRescuePhone())) {
                    PhoneUtil.call(context, mBean.getRescuePhone());
                } else {
                    showToast("电话为空！");
                }
                break;
        }
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
        LatLng pt = new LatLng(Double.valueOf(Util.getBaidu(mBean.getLift().getBaiduMapXY())[1]),
                Double.valueOf(Util.getBaidu(mBean.getLift().getBaiduMapXY())[0]));
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(pt).zoom(17.0f);
        MapStatusUpdate status = MapStatusUpdateFactory.newMapStatus(builder.build());
        mBaiduMap.animateMapStatus(status, 1000);
    }
}

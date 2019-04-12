package com.sinodom.elevator.fragment.elevator.rescue;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.avchat.AVChatCallback;
import com.netease.nimlib.sdk.avchat.AVChatManager;
import com.netease.nimlib.sdk.avchat.model.AVChatChannelInfo;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.nim.ChatRoomActivity;
import com.sinodom.elevator.activity.elevator.nim.utils.NimCache;
import com.sinodom.elevator.activity.elevator.nim.utils.Preferences;
import com.sinodom.elevator.activity.elevator.rescue.RescueFinishActivity;
import com.sinodom.elevator.activity.elevator.rescue.ScoreActivity;
import com.sinodom.elevator.activity.elevator.rescue.WbAztsActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.RescueMaintenanceAdapter;
import com.sinodom.elevator.adapter.elist.RescueUseAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.nim.RoomBean;
import com.sinodom.elevator.bean.rescue.RescueBean;
import com.sinodom.elevator.bean.rescue.RescueDetailBean;
import com.sinodom.elevator.bean.rescue.RescueUserBean;
import com.sinodom.elevator.db.StatusAction;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.fragment.elevator.main.RescueFragment;
import com.sinodom.elevator.service.HeartbeatService;
import com.sinodom.elevator.util.ActivityCollector;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.DisplayUtil;
import com.sinodom.elevator.util.PhoneUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.util.Util;
import com.sinodom.elevator.view.NoScrollListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2017/11/14.
 * 电梯救援-报警电梯
 */

public class CallFragment extends BaseFragment implements BaiduMap.OnMarkerClickListener {

    @BindView(R.id.bmapView)
    MapView bmapView;
    Unbinder unbinder;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvRescue)
    TextView tvRescue;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.svRescue)
    ScrollView svRescue;
    @BindView(R.id.svPhone)
    ScrollView svPhone;
    @BindView(R.id.llBottom)
    LinearLayout llBottom;
    @BindView(R.id.llDetails)
    LinearLayout llDetails;
    @BindView(R.id.tvCallTime)
    TextView tvCallTime;
    @BindView(R.id.tvTotalLossTime)
    TextView tvTotalLossTime;
    @BindView(R.id.tvMaintConfirmTime)
    TextView tvMaintConfirmTime;
    @BindView(R.id.tvRescueCompleteTime)
    TextView tvRescueCompleteTime;
    @BindView(R.id.tvStatusName)
    TextView tvStatusName;
    @BindView(R.id.tvLiftNum)
    TextView tvLiftNum;
    @BindView(R.id.tvCertificateNum)
    TextView tvCertificateNum;
    @BindView(R.id.tvBrand)
    TextView tvBrand;
    @BindView(R.id.tvInstallationAddress)
    TextView tvInstallationAddress;
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
    @BindView(R.id.tvCreateTime)
    TextView tvCreateTime;
    @BindView(R.id.tvSourceDict)
    TextView tvSourceDict;
    @BindView(R.id.lvUseDepartment1)
    NoScrollListView lvUseDepartment1;
    @BindView(R.id.lvMaintenanceDepartment1)
    NoScrollListView lvMaintenanceDepartment1;
    @BindView(R.id.tvQrbj)
    TextView tvQrbj;
    @BindView(R.id.tvWatch)
    ImageView tvWatch;
    @BindView(R.id.tvWb)
    TextView tvWb;
    @BindView(R.id.tvAzts)
    TextView tvAzts;
    @BindView(R.id.tvJd)
    TextView tvJd;
    @BindView(R.id.tvFwpj)
    TextView tvFwpj;
    @BindView(R.id.tvQrdc)
    TextView tvQrdc;
    @BindView(R.id.tvWcjy)
    TextView tvWcjy;
    @BindView(R.id.tvAddressLine)
    TextView tvAddressLine;
    @BindView(R.id.llAddress)
    LinearLayout llAddress;
    @BindView(R.id.tvRescueLine)
    TextView tvRescueLine;
    @BindView(R.id.llRescue)
    LinearLayout llRescue;
    @BindView(R.id.tvPhoneLine)
    TextView tvPhoneLine;
    @BindView(R.id.llPhone)
    LinearLayout llPhone;
    @BindView(R.id.llButton)
    LinearLayout llButton;
    @BindView(R.id.tvTotalLossTime1)
    TextView tvTotalLossTime1;
    @BindView(R.id.tvLiftSiteDict)
    TextView tvLiftSiteDict;
    @BindView(R.id.tvMachineNum)
    TextView tvMachineNum;
    @BindView(R.id.tvRescueNumber)
    TextView tvRescueNumber;
    @BindView(R.id.tvRescuePhone)
    TextView tvRescuePhone;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvRescueCompleteTime1)
    TextView tvRescueCompleteTime1;
    @BindView(R.id.tvRemedyUser)
    TextView tvRemedyUser;
    @BindView(R.id.tvRescueNumber1)
    TextView tvRescueNumber1;
    @BindView(R.id.tvRescuePhone1)
    TextView tvRescuePhone1;
    @BindView(R.id.tvRemedyUser1)
    TextView tvRemedyUser1;
    private RescueUseAdapter mRescueUseAdapter;
    private RescueMaintenanceAdapter mRescueMaintenanceAdapter;
    private BaiduMap mBaiduMap;
    private BitmapDescriptor mBitmapLift;
    private BitmapDescriptor mBitmapRescue;
    private BitmapDescriptor mBitmapSign;
    private BitmapDescriptor mBitmapKoriyasu;
    private List<Double> latitudeList = new ArrayList<Double>();
    private List<Double> longitudeList = new ArrayList<Double>();
    private double maxLatitude;
    private double minLatitude;
    private double maxLongitude;
    private double minLongitude;
    private LatLng center;
    private float level;
    private double distance;
    private List<RescueBean> mList = new ArrayList<>();
    //救援人员列表
    private List<RescueUserBean> mUserlist = new ArrayList<>();
    private RescueDetailBean mBean;
    private List<Marker> mMarker = new ArrayList<>();
    private List<LatLng> mLatLngList = new ArrayList<>();
    private Gson gson = new Gson();
    private int pageNumber = 1;
    //救援列表页跳转传递的数据（电梯编号）
    private int mId = 0;
    //当前详情显示数据在列表中的位置
    private int mPosition = 0;
    //判断当前显示为列表还是详情
    private boolean isDetail = false;
    //判断是否需要刷新列表页
    public static boolean isRefresh = false;
    //提交参数
    private Map<String, Object> mMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            RescueFragment rescueFragment = (RescueFragment) getParentFragment();
            mId = rescueFragment.getIds();
            if (mId != 0) {
                for (int i = 0; i < mList.size(); i++) {
                    if (mId == mList.get(i).getID()) {
                        mPosition = i;
                        break;
                    }
                }
                try {
                    //加载详细信息
                    showLoading("加载中...");
                    loadDetailData(mId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onHiddenChanged(hidden);
        Logger.e("onHiddenChanged" + hidden);
    }

    private void init() {
        mBaiduMap = bmapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setOnMarkerClickListener(this);
        //构建Marker图标
        mBitmapLift = BitmapDescriptorFactory.fromResource(R.mipmap.ic_rescue_alarm_36dp);
        mBitmapRescue = BitmapDescriptorFactory.fromResource(R.mipmap.ic_rescue_rescue_36dp);
        mBitmapSign = BitmapDescriptorFactory.fromResource(R.mipmap.ic_rescue_sign_36dp);
        mBitmapKoriyasu = BitmapDescriptorFactory.fromResource(R.mipmap.ic_rescue_koriyasu_36dp);
        mRescueUseAdapter = new RescueUseAdapter(context);
        mRescueMaintenanceAdapter = new RescueMaintenanceAdapter(context);
        lvUseDepartment.setAdapter(mRescueUseAdapter);
        lvUseDepartment1.setAdapter(mRescueUseAdapter);
        lvMaintenanceDepartment.setAdapter(mRescueMaintenanceAdapter);
        lvMaintenanceDepartment1.setAdapter(mRescueMaintenanceAdapter);
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
        //进入地图默认显示辽宁省
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(8).build()));
        setCenter(new LatLng(41.26401, 122.33086));
        rLoad();
    }

    //加载列表
    private void loadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().getTaskList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.d(response.raw().networkResponse().request().url());
                        Logger.json(response.body().getData());
                        List<RescueBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<RescueBean>>() {
                        }.getType());
                        isDetail = false;
                        mList.clear();
                        mList.addAll(list);
                        mLatLngList.clear();
                        String noXY = "";
                        for (RescueBean bean : list) {
//                            if (bean.getRescueType() == 0 && !TextUtil.isEmpty(bean.getLift().getBaiduMapXY())) {
                            if (!TextUtil.isEmpty(bean.getLift().getBaiduMapXY())) {
//                                mList.add(bean);
                                double latitude = Double.valueOf(Util.getBaidu(bean.getLift().getBaiduMapXY())[1]);
                                double longitude = Double.valueOf(Util.getBaidu(bean.getLift().getBaiduMapXY())[0]);
                                LatLng pt = new LatLng(latitude, longitude);
                                mLatLngList.add(pt);
                            } else {
                                noXY = noXY + bean.getLift().getLiftNum() + ",";
                            }
                        }
                        if (!TextUtil.isEmpty(noXY)) {
                            showToast("以下电梯无坐标：\n" + noXY.substring(0, noXY.length() - 1));
                        }
                        setLiftMarker(mList);
                        //比较选出集合中最大经纬度
                        getMax();
                        //计算两个Marker之间的距离
                        calculateDistance();
                        //根据距离判断地图级别
                        getLevel();
                        //计算中心点经纬度，将其设为启动时地图中心
                        setCenter();
                    } else {
                        showToast(response.body().getMessage());
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

    //加载详情
    public void loadDetailData(final int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().getTaskDetail(map, id);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                loadRescueData(id);
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        RescueDetailBean bean = gson.fromJson(response.body().getData(), RescueDetailBean.class);
                        mBean = bean;
                        llBottom.setVisibility(View.VISIBLE);
                        llDetails.setVisibility(View.VISIBLE);
                        bmapView.setVisibility(View.VISIBLE);
                        svRescue.setVisibility(View.GONE);
                        svPhone.setVisibility(View.GONE);
                        tvAddress.setTextColor(getResources().getColor(R.color.actionbar));
                        tvRescue.setTextColor(getResources().getColor(R.color.black1));
                        tvPhone.setTextColor(getResources().getColor(R.color.black1));
                        tvAddressLine.setVisibility(View.VISIBLE);
                        tvRescueLine.setVisibility(View.GONE);
                        tvPhoneLine.setVisibility(View.GONE);
                        setStatusAction();
                        tvStatusName.setText(mBean.getStatusName());
                        tvTotalLossTime.setText(mBean.getTotalLossTime() + "分");
                        tvTotalLossTime1.setText(mBean.getTotalLossTime() + "分");
                        tvRescueNumber.setText(mBean.getRescueNumber() + "");
                        tvRescuePhone.setText(mBean.getRescuePhone());
                        tvRescueNumber1.setText(mBean.getRescueNumber() + "");
                        tvRescuePhone1.setText(mBean.getRescuePhone());
                        tvContent.setText(mBean.getContent());
                        tvCallTime.setText(DateUtil.format(mBean.getCreateTime()));
                        tvCreateTime.setText(DateUtil.format(mBean.getCreateTime()));
                        tvMaintConfirmTime.setText(DateUtil.format(mBean.getMaintConfirmTime()));
                        tvRescueCompleteTime.setText(DateUtil.format(mBean.getRescueCompleteTime()));
                        tvRescueCompleteTime1.setText(DateUtil.format(mBean.getRescueCompleteTime()));
                        if (mBean.getLift() != null) {
                            tvBrand.setText(mBean.getLift().getBrand());
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
                        tvRemedyUser.setText(mBean.getRemedyUser() != null ? mBean.getRemedyUser().getUserName() + mBean.getRemedyUser().getMobile() : "");
                        tvRemedyUser1.setText(mBean.getRemedyUser() != null ? mBean.getRemedyUser().getUserName() + mBean.getRemedyUser().getMobile() : "");
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                    loadRescueData(id);
                }
            }
        });
    }

    //提交数据
    private void commit(Map<String, Object> mapBody, final List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().saveTaskStatus(map, mapBody);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        showToast(response.body().getMessage());
                        if (list != null && list.size() > 0) {
                            score(list);
                        } else if (list != null) {
                            rLoad();
                            isRefresh = true;
                        } else {
                            //加载详细信息
                            showLoading("加载中...");
                            loadDetailData(mBean.getID());
//                            loadRescueData(mList.get(mPosition).getID());
                            isRefresh = true;
                        }
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    //评分
    private void score(List<Map<String, Object>> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().saveEvaluationScore(map, list);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        showToast(response.body().getMessage());
                        rLoad();
                        isRefresh = true;
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    //设置显示按钮
    private void setStatusAction() {
        List<StatusAction> list = manager.getStatusActionList(mBean.getStatusId());
        tvQrbj.setVisibility(View.GONE);
        tvWb.setVisibility(View.GONE);
        tvAzts.setVisibility(View.GONE);
        tvJd.setVisibility(View.GONE);
        tvFwpj.setVisibility(View.GONE);
        tvQrdc.setVisibility(View.GONE);
        tvWcjy.setVisibility(View.GONE);
        for (final StatusAction bean : list) {
            switch (bean.getActionName()) {
                //确认报警
                case "确认报警":
                    if (getJurisdiction(bean)) {
                        tvQrbj.setVisibility(View.VISIBLE);
                        tvQrbj.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setCommit(bean);
                            }
                        });
                    } else {
//                        tvQrbj.setVisibility(View.GONE);
                    }
                    break;
                //误报
                case "误报":
                    if (getJurisdiction(bean)) {
                        tvWb.setVisibility(View.VISIBLE);
                        tvWb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setCommit(bean);
                            }
                        });
                    } else {
//                        tvWb.setVisibility(View.GONE);
                    }
                    break;
                //安装调试
                case "安装调试":
                    if (getJurisdiction(bean)) {
                        tvAzts.setVisibility(View.VISIBLE);
                        tvAzts.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setCommit(bean);
                            }
                        });
                    } else {
//                        tvAzts.setVisibility(View.GONE);
                    }
                    break;
                //接单
                case "接单":
                    if (tvJd.getVisibility() == View.VISIBLE || getJurisdiction(bean)) {
                        tvJd.setVisibility(View.VISIBLE);
                        tvJd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HeartbeatService.mIntevalPeriod = 20 * 1000;//20秒
                                setCommit(bean);
                            }
                        });
                    } else {
//                        tvJd.setVisibility(View.GONE);
                    }
                    break;
                //服务评价
                case "服务评价":
                    if (getJurisdiction(bean)) {
                        tvFwpj.setVisibility(View.VISIBLE);
                        tvFwpj.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setCommit(bean);
                            }
                        });
                    } else {
//                        tvFwpj.setVisibility(View.GONE);
                    }
                    break;
                //确认到场
                case "确认到场":
                    if (getJurisdiction(bean)) {
                        tvQrdc.setVisibility(View.VISIBLE);
                        tvQrdc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HeartbeatService.mIntevalPeriod = 5 * 60 * 1000;//5分钟
                                setCommit(bean);
                            }
                        });
                    } else {
//                        tvQrdc.setVisibility(View.GONE);
                    }
                    break;
                //完成救援
                case "完成救援":
                    if (getJurisdiction(bean)) {
                        tvWcjy.setVisibility(View.VISIBLE);
                        tvWcjy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setCommit(bean);
                            }
                        });
                    } else {
//                        tvWcjy.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }

    //根据角色权限判断按钮显示
    private boolean getJurisdiction(StatusAction bean) {
        List<String> list = new ArrayList<>();
        if (manager.getSession().getRoleCode().equals("SoftDeptRole")) {
            list.add(null);
            list.add("0");
            list.add("1");
        } else if (mBean.getRemedyUserId() == manager.getSession().getUserID()) {
            list.add(null);
        } else if (getUseList(mBean.getLift().getUseUsers())) {
            list.add("0");
        } else if (getMaintList(mBean.getLift().getMaintUsers())) {
            list.add("1");
        } else if (getMaintList(mBean.getLift().getMaint2Users())) {
            list.add("2");
        } else if ((mBean.getLift().getMaint3Users() == null || mBean.getLift().getMaint3Users().size() == 0)
                ? getMaintList(mBean.getLift().getMaint2Users()) : getMaintList(mBean.getLift().getMaint3Users())) {
            list.add("3");
        } else if (manager.getSession().getRoleCode().equals("MaintDeptRole")) {
            if (23 == mBean.getStatusId() || 26 == mBean.getStatusId()) {
                list.add("2");
            } else if (24 == mBean.getStatusId()) {
                list.add("3");
            }
        }
        return list.contains(bean.getUserType());
    }

    private boolean getUseList(List<RescueDetailBean.LiftBean.UseUsersBean> list) {
        boolean isTrue = false;
        for (RescueDetailBean.LiftBean.UseUsersBean bean : list) {
            if (bean.getID() == manager.getSession().getUserID()) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }

    private boolean getMaintList(List<RescueDetailBean.LiftBean.MaintUsersBean> list) {
        boolean isTrue = false;
        for (RescueDetailBean.LiftBean.MaintUsersBean bean : list) {
            if (bean.getID() == manager.getSession().getUserID()) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }

    //设置提交数据
    private void setCommit(StatusAction bean) {
        mMap.clear();
        mMap.put("ID", mBean.getID() + "");
        mMap.put("CreateTime", mBean.getCreateTime());
        List<StatusAction> list = manager.getStatusActionListByArgument(bean.getArgument());
        if (list.size() > 0) {
            mMap.put("StatusId", list.get(0).getStatusId());
            mMap.put("StatusName", list.get(0).getStatusName());
        } else {
            mMap.put("StatusId", bean.getArgument());
            mMap.put("StatusName", bean.getActionName());
        }
        switch ((int) mMap.get("StatusId")) {
            case 11:
            case 12:
            case 13:
            case 51:
                mMap.put("ConfirmUserId", manager.getSession().getUserID());
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 31:
            case 41:
                mMap.put("RemedyUserId", manager.getSession().getUserID());
                break;
        }
        if (bean.getArgument() == 41) {
            Intent intent = new Intent(context, RescueFinishActivity.class);
            startActivityForResult(intent, Constants.Code.GO_RESCUE);
            return;
        }
        if (bean.getArgument() == 51) {
            Intent intent = new Intent(context, ScoreActivity.class);
            startActivityForResult(intent, Constants.Code.GO_SCORE);
            return;
        }
        if (bean.getArgument() == 12) {
            Intent intent = new Intent(context, WbAztsActivity.class);
            startActivityForResult(intent, Constants.Code.GO_WBAZTS);
            return;
        }
        showLoading("加载中...");
        commit(mMap, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //救援完成
        if (requestCode == Constants.Code.GO_RESCUE && resultCode == Constants.Code.RESCUE_OK) {
            if (!TextUtil.isEmpty(data.getStringExtra("ReasonId")) &&
                    !TextUtil.isEmpty(data.getStringExtra("RemedyId"))) {
                mMap.put("RescueNumber", data.getStringExtra("RescueNumber"));
                mMap.put("RescuePhone", data.getStringExtra("RescuePhone"));
                mMap.put("Content", data.getStringExtra("Content"));
                mMap.put("ReasonId", data.getStringExtra("ReasonId"));
                mMap.put("ReasonDesc", data.getStringExtra("ReasonDesc"));
                mMap.put("RemedyId", data.getStringExtra("RemedyId"));
                mMap.put("RemedyDesc", data.getStringExtra("RemedyDesc"));
                showLoading("加载中...");
                commit(mMap, null);
            }
        }
        //服务评价
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            if (data.getIntExtra("dcqk", 0) != 0 && data.getIntExtra("jygc", 0) != 0 && data.getIntExtra("gztd", 0) != 0) {
                List<Map<String, Object>> list = new ArrayList<>();
                Map<String, Object> map1 = new HashMap<>();
                map1.put("TaskId", mBean.getID());
                map1.put("ItemId", "42");
                map1.put("UserId", manager.getSession().getUserID());
                map1.put("Rank", data.getIntExtra("dcqk", 0));
                Map<String, Object> map2 = new HashMap<>();
                map2.put("TaskId", mBean.getID());
                map2.put("ItemId", "43");
                map2.put("UserId", manager.getSession().getUserID());
                map2.put("Rank", data.getIntExtra("jygc", 0));
                Map<String, Object> map3 = new HashMap<>();
                map3.put("TaskId", mBean.getID());
                map3.put("ItemId", "44");
                map3.put("UserId", manager.getSession().getUserID());
                map3.put("Rank", data.getIntExtra("gztd", 0));
                list.add(map1);
                list.add(map2);
                list.add(map3);
                showLoading("加载中...");
                commit(mMap, list);
            }
        }
        //误报/安装调试
        if (requestCode == Constants.Code.GO_WBAZTS && resultCode == Constants.Code.WBAZTS_OK) {
            if (!TextUtil.isEmpty(data.getStringExtra("reasonId")) && !TextUtil.isEmpty(data.getStringExtra("reasonDesc"))) {
                mMap.put("ReasonId", data.getStringExtra("reasonId"));
                mMap.put("ReasonDesc", data.getStringExtra("reasonDesc"));
                showLoading("加载中...");
                List<Map<String, Object>> list = new ArrayList<>();
                commit(mMap, list);
            }
        }
    }

    //加载救援人员
    private void loadRescueData(final int id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().getLocationList(map, id);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<RescueUserBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<RescueUserBean>>() {
                        }.getType());
                        mUserlist.clear();
                        mUserlist.addAll(list);
                        setRescueData();
                    } else {
                        mUserlist.clear();
                        setRescueData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                }

            }
        });
    }

    //设置附近救援人
    private void setRescueData() {
        if (mBean.getLift().getBaiduMapXY() == null) {
            mBaiduMap.clear();
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(8).build()));
            setCenter(new LatLng(41.26401, 122.33086));
        } else {
            //附近救援人
            double latitude = Double.valueOf(Util.getBaidu(mBean.getLift().getBaiduMapXY())[1]);
            double longitude = Double.valueOf(Util.getBaidu(mBean.getLift().getBaiduMapXY())[0]);
            LatLng pt = new LatLng(latitude, longitude);
            mLatLngList.clear();
            mLatLngList.add(pt);
            for (RescueUserBean bean : mUserlist) {
                mLatLngList.add(new LatLng(bean.getBaiduMapY(), bean.getBaiduMapX()));
            }
            isDetail = true;
            mBaiduMap.clear();
            setLiftDetailMarker();
            showLiftInfoWindow(pt);
//          setCenter(pt);
            setRescueMarker(mUserlist);
            //比较选出集合中最大经纬度
            getMax();
            //计算两个Marker之间的距离
            calculateDistance();
            //根据距离判断地图级别
            getLevel();
            //计算中心点经纬度，将其设为启动时地图中心
            setCenter();
        }
    }

    //设置电梯Markr
    private void setLiftMarker(List<RescueBean> data) {
        Marker marker = null;
        for (int i = 0; i < data.size(); i++) {
            if (!TextUtil.isEmpty(data.get(i).getLift().getBaiduMapXY())) {
                try {
                    double lat = Double.valueOf(Util.getBaidu(data.get(i).getLift().getBaiduMapXY())[1]);
                    double lon = Double.valueOf(Util.getBaidu(data.get(i).getLift().getBaiduMapXY())[0]);
                    //定义Maker坐标点
                    LatLng point = new LatLng(lat, lon);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(mBitmapLift)
                            .zIndex(i);
                    //在地图上添加Marker，并显示
                    marker = (Marker) mBaiduMap.addOverlay(option);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mMarker.add(marker);
        }
    }

    //设置电梯详情Markr
    private void setLiftDetailMarker() {
        Marker marker = null;
        if (!TextUtil.isEmpty(mBean.getLift().getBaiduMapXY())) {
            try {
                double lat = Double.valueOf(Util.getBaidu(mBean.getLift().getBaiduMapXY())[1]);
                double lon = Double.valueOf(Util.getBaidu(mBean.getLift().getBaiduMapXY())[0]);
                //定义Maker坐标点
                LatLng point = new LatLng(lat, lon);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(mBitmapLift)
                        .zIndex(9999);
                //在地图上添加Marker，并显示
                marker = (Marker) mBaiduMap.addOverlay(option);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mMarker.add(marker);
    }

    //设置救援人员Markr
    private void setRescueMarker(List<RescueUserBean> data) {
        Marker marker = null;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getBaiduMapX() != 0 && data.get(i).getBaiduMapY() != 0) {
                //定义Maker坐标点
                LatLng point = new LatLng(data.get(i).getBaiduMapY(), data.get(i).getBaiduMapX());
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(point)
                        .icon(mBitmapRescue)
                        .zIndex(i);
                //在地图上添加Marker，并显示
                marker = (Marker) mBaiduMap.addOverlay(option);
            }
            mMarker.add(marker);
        }
    }

    //设置电梯InfoWindow
    private void showLiftInfoWindow(LatLng pt) {
        //创建InfoWindow展示的view
        TextView textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.ic_rescue_infotext_alarm);
        textView.setMaxWidth(DisplayUtil.dip2px(context, 300));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        String custom = TextUtil.isEmpty(mBean.getLift().getCustomNum()) ? "" : "(" + mBean.getLift().getCustomNum() + ")";
        String str = "";
        int length = mBean.getLift().getInstallationAddress().length();
        str = length > 16 ? mBean.getLift().getInstallationAddress().substring(0, 16) + "..." : mBean.getLift().getInstallationAddress();
        textView.setText(mBean.getLift().getLiftNum() + custom + "\n" + str);
        textView.setTextColor(getResources().getColor(R.color.black3));
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(textView, pt, DisplayUtil.dip2px(context, -30));
        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    //设置UserInfoWindow
    private void showUserInfoWindow(LatLng pt, final RescueUserBean bean) {
        //创建InfoWindow展示的view
        TextView textView = new TextView(context);
        textView.setBackgroundResource(R.drawable.ic_rescue_infotext_koriyasu);
        textView.setMaxWidth(DisplayUtil.dip2px(context, 300));
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setText(bean.getUser().getUserName() + "(" + bean.getUser().getMobile() + ")\n" + bean.getUser().getDept().getDeptName());
        textView.setTextColor(getResources().getColor(R.color.black3));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!TextUtil.isEmpty(bean.getUser().getMobile())) {
                        PhoneUtil.call(context, bean.getUser().getMobile());
                    } else {
                        showToast("电话为空！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电话为空！");
                }
            }
        });
        //创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(textView, pt, DisplayUtil.dip2px(context, -30));
        //显示InfoWindow
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    //Marker点击事件
    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            int position = marker.getZIndex();
            if (isDetail) {
                if (position == 9999) {
                    //加载详细信息
                    showLoading("加载中...");
                    loadDetailData(mId == 0 ? mList.get(mPosition).getID() : mId);
//                    loadRescueData(mList.get(mPosition).getID());
                } else {
                    LatLng pt = new LatLng(mUserlist.get(position).getBaiduMapY(), mUserlist.get(position).getBaiduMapX());
                    showUserInfoWindow(pt, mUserlist.get(position));
                    setCenter(pt);
                }
            } else {
                //加载详细信息
                mPosition = position;
                showLoading("加载中...");
                loadDetailData(mList.get(position).getID());
//                loadRescueData(mList.get(position).getID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @OnClick({R.id.tvRefresh, R.id.llAddress, R.id.llRescue, R.id.llPhone, R.id.tvRemedyUser, R.id.tvRemedyUser1, R.id.llVideo,R.id.tvWatch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvRemedyUser:
            case R.id.tvRemedyUser1:
                try {
                    if (!TextUtil.isEmpty(mBean.getRemedyUser().getMobile())) {
                        PhoneUtil.call(context, mBean.getRemedyUser().getMobile());
                    } else {
                        showToast("电话为空！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电话为空！");
                }
                break;
            case R.id.tvRefresh:
                goneView();
                mBaiduMap.clear();
                showLoading("加载中...");
                rLoad();
                break;
            case R.id.llAddress:
                bmapView.setVisibility(View.VISIBLE);
                svRescue.setVisibility(View.GONE);
                svPhone.setVisibility(View.GONE);
                tvAddress.setTextColor(getResources().getColor(R.color.actionbar));
                tvRescue.setTextColor(getResources().getColor(R.color.black1));
                tvPhone.setTextColor(getResources().getColor(R.color.black1));
                tvAddressLine.setVisibility(View.VISIBLE);
                tvRescueLine.setVisibility(View.GONE);
                tvPhoneLine.setVisibility(View.GONE);
                break;
            case R.id.llRescue:
                bmapView.setVisibility(View.GONE);
                svRescue.setVisibility(View.VISIBLE);
                svPhone.setVisibility(View.GONE);
                tvAddress.setTextColor(getResources().getColor(R.color.black1));
                tvRescue.setTextColor(getResources().getColor(R.color.actionbar));
                tvPhone.setTextColor(getResources().getColor(R.color.black1));
                tvAddressLine.setVisibility(View.GONE);
                tvRescueLine.setVisibility(View.VISIBLE);
                tvPhoneLine.setVisibility(View.GONE);
                break;
            case R.id.llPhone:
                bmapView.setVisibility(View.GONE);
                svRescue.setVisibility(View.GONE);
                svPhone.setVisibility(View.VISIBLE);
                tvAddress.setTextColor(getResources().getColor(R.color.black1));
                tvRescue.setTextColor(getResources().getColor(R.color.black1));
                tvPhone.setTextColor(getResources().getColor(R.color.actionbar));
                tvAddressLine.setVisibility(View.GONE);
                tvRescueLine.setVisibility(View.GONE);
                tvPhoneLine.setVisibility(View.VISIBLE);
                break;
            case R.id.llVideo:
                if (!TextUtils.isEmpty(NimCache.getAccount())) {
                    if (ActivityCollector.isActivityExist(ChatRoomActivity.class)) {
                        ChatRoomActivity activity = ActivityCollector.getActivity(ChatRoomActivity.class);
                        activity.finish();
                    }
                    //权限申请
                    permission();
                } else {
                    try {
                        String account = manager.getSession().getLoginName();
                        if (!TextUtils.isEmpty(account)) {
                            nimLogin(account, "123456");
                        } else {
                            showToast("获取视频账号失败！");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("获取视频账号失败！");
                    }
                }
                break;
            case R.id.tvWatch:
                permission2();
                break;
        }
    }

    private void createRoom(final String roomId) {
        createRoom(roomId,true);
    }

    private void createRoom(final String roomId,boolean isMonitoring) {
        Call<ResponBean> call = server.getService().appSendVideoMachineMsg(NimCache.getAccount(), mBean.getLiftId()+"", roomId,isMonitoring);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<RoomBean.UserListBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<RoomBean.UserListBean>>() {
                        }.getType());
                        RoomBean bean = new RoomBean();
                        bean.setCreator(NimCache.getAccount());
                        bean.setRoomId(roomId);
                        bean.setRoomName(roomId);
                        bean.setUserList(list);
                        //创建房间 调用我们接口 暂时模拟
                        Intent intent = new Intent(context, ChatRoomActivity.class);
                        intent.putExtra("roomId", roomId);
                        intent.putExtra("roomName", roomId);
                        intent.putExtra("creator", NimCache.getAccount());
                        intent.putExtra("isCreate", true);
                        intent.putExtra("bean", bean);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        showToast("视频发起失败");
//                        CreateRoomActivity activity = (CreateRoomActivity) getActivity();
//                        activity.isCall = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    CreateRoomActivity activity = (CreateRoomActivity) getActivity();
//                    activity.isCall = false;
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
//                    CreateRoomActivity activity = (CreateRoomActivity) getActivity();
//                    activity.isCall = false;
                }
            }
        });
    }


    /**
     * 创建会议频道
     */
    private void createChannel(final String roomId) {
        AVChatManager.getInstance().createRoom(roomId, getString(R.string.app_name), new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                hideLoading();
                avChatChannelInfo.getTimetagMs();
//                CreateRoomActivity activity = (CreateRoomActivity) getActivity();
//                activity.isCall = true;
                createRoom(roomId);
            }

            @Override
            public void onFailed(int i) {
                Toast.makeText(context, "创建房间失败, code:" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(context, "创建房间异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 创建会议频道
     */
    private void createChannel(final String roomId, final boolean isMonitoring) {
        AVChatManager.getInstance().createRoom(roomId, getString(R.string.app_name), new AVChatCallback<AVChatChannelInfo>() {
            @Override
            public void onSuccess(AVChatChannelInfo avChatChannelInfo) {
                hideLoading();
                avChatChannelInfo.getTimetagMs();
//                CreateRoomActivity activity = (CreateRoomActivity) getActivity();
//                activity.isCall = true;
                createRoom(roomId,isMonitoring);
            }

            @Override
            public void onFailed(int i) {
                Toast.makeText(context, "创建房间失败, code:" + i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onException(Throwable throwable) {
                Toast.makeText(context, "创建房间异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private AbortableFuture<LoginInfo> loginRequest;

    private void nimLogin(final String account, final String token) {
        // 登录
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                onLoginDone();
                NimCache.setAccount(account);
                saveLoginInfo(account, token);
                showToast("登录成功！");
                //权限申请
                permission();
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    showToast("帐号或密码错误");
                } else {
                    showToast("登录失败: " + code);
                }
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
                showToast("登录视频服务器异常");
            }
        });
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    private void onLoginDone() {
        loginRequest = null;
    }


    public void permission() {
        boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
        boolean permission1 = selfPermissionGranted(Manifest.permission.RECORD_AUDIO);
        if (permission == true && permission1 == true) {
            showLoading("正在发起视频！");
            createChannel(UUID.randomUUID().toString());
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 100);
        }
    }
    public void permission2() {
        boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
        boolean permission1 = selfPermissionGranted(Manifest.permission.RECORD_AUDIO);
        if (permission == true && permission1 == true) {
            showLoading("正在发起视频！");
            createChannel(UUID.randomUUID().toString(),false);
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLoading("正在发起视频！");
                createChannel(UUID.randomUUID().toString());
            } else {
                getPermission("请授权APP使用摄像头和录音权限！");
            }
            return;
        }else if(requestCode==101){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLoading("正在发起视频！");
                createChannel(UUID.randomUUID().toString(),false);
            } else {
                getPermission("请授权APP使用摄像头和录音权限！");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getPermission(String text) {
        new android.support.v7.app.AlertDialog.Builder(context)
                .setTitle(text)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent localIntent = new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        localIntent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                        startActivity(localIntent);
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        getActivity().finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void goneView() {
        llBottom.setVisibility(View.GONE);
        llDetails.setVisibility(View.GONE);
        tvQrbj.setVisibility(View.GONE);
        tvWb.setVisibility(View.GONE);
        tvAzts.setVisibility(View.GONE);
        tvJd.setVisibility(View.GONE);
        tvFwpj.setVisibility(View.GONE);
        tvQrdc.setVisibility(View.GONE);
        tvWcjy.setVisibility(View.GONE);
    }

    //刷新数据
    public void rLoad() {
        goneView();
        mBaiduMap.clear();
        showLoading("加载中...");
        pageNumber = 1;
        mList.clear();
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBitmapLift.recycle();
        mBitmapRescue.recycle();
        mBitmapSign.recycle();
        mBitmapKoriyasu.recycle();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        //butterknife声明周期结束时销毁bmapView对象了
//        bmapView.onDestroy();
        mRetrofitManager.cancelAll();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //比较选出集合中最大经纬度
    private void getMax() {
        latitudeList.clear();
        longitudeList.clear();
        for (int i = 0; i < mLatLngList.size(); i++) {
            latitudeList.add(mLatLngList.get(i).latitude);
            longitudeList.add(mLatLngList.get(i).longitude);
        }
        maxLatitude = Collections.max(latitudeList);
        minLatitude = Collections.min(latitudeList);
        maxLongitude = Collections.max(longitudeList);
        minLongitude = Collections.min(longitudeList);
    }

    //计算两个Marker之间的距离
    private void calculateDistance() {
        distance = DistanceUtil.getDistance(new LatLng(minLatitude, minLongitude), new LatLng(maxLatitude, maxLongitude));
    }

    //根据距离判断地图级别3-22
    private void getLevel() {
        //对应缩放级别 21 20  19  18  17   16   15   14    13    12    11     10     9      8      7       6       5       4        3
        int zoom[] = {5, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000, 1000000, 2000000};
        boolean isSetLevel = false;
        for (int i = 0; i < zoom.length; i++) {
            int zoomNow = zoom[i];
            if (zoomNow - distance > 0) {
                isSetLevel = true;
                level = 21 - (i + 1) + 4;
                //设置地图显示级别为计算所得level
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(level).build()));
                break;
            }
        }
        if (!isSetLevel) {
            level = 5;
            //设置地图显示级别为计算所得level
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(level).build()));
        }
    }

    //计算中心点经纬度，将其设为启动时地图中心
    private void setCenter() {
        center = new LatLng((maxLatitude + minLatitude) / 2, (maxLongitude + minLongitude) / 2);
        Log.i("info", "center==" + center);
        setCenter(center);
    }

    //设置地图中心
    private void setCenter(LatLng pt) {
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(pt);
        mBaiduMap.animateMapStatus(status, 1000);
    }
}

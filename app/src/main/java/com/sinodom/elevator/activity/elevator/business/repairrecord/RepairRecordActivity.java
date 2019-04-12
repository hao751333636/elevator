package com.sinodom.elevator.activity.elevator.business.repairrecord;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.elist.RepairRecordAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.business.CityBean;
import com.sinodom.elevator.bean.business.UnitBean;
import com.sinodom.elevator.bean.business.ZoneBean;
import com.sinodom.elevator.bean.repairrecord.RepairRecordBean;
import com.sinodom.elevator.util.DisplayUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.ScreenUtil;
import com.sinodom.elevator.zxing.activity.CaptureActivity;

import java.util.ArrayList;
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
 * 维修记录
 */
public class RepairRecordActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTop)
    TextView tvTop;
    @BindView(R.id.ivShowcasing)
    ImageView ivShowcasing;
    @BindView(R.id.tvShowcasing)
    TextView tvShowcasing;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.lvInternetAlarm)
    PullToRefreshListView lvInternetAlarm;
    @BindView(R.id.tvNOData)
    TextView tvNOData;
    @BindView(R.id.bLoad)
    Button bLoad;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;

    private ListView mListView;
    private List<RepairRecordBean> list = new ArrayList<>();
    private RepairRecordAdapter mAdapter;
    private int pageNumber = 1;
    private Gson gson = new Gson();

    private String ZoneId = "0";
    private String CityId = "0";
    private String stateId = "0";   //0全部  1是  2否
    private String useId = "0";
    private String maintenanceId = "0";

    private String CityName = "市（全部）";
    private String ZoneName = "区（全部）";
    private String StateName = "安装状态（全部）";

    private List<CityBean> cityBeans;
    private List<ZoneBean> zoneBeans;
    private List<UnitBean> useBeans;
    private List<UnitBean> maintenanceBeans;

    private PopupWindow popSearch;
    private PopupWindow popCity;
    private PopupWindow popZone;
    private PopupWindow popState;
    private View layoutCity;
    private View layoutZone;
    private View layoutState;
    private ListView lvMenuCity;
    private ListView lvMenuZone;
    private ListView lvMenuState;
    private List<Map<String, String>> strCityBeans;
    private List<Map<String, String>> strZoneBeans;
    private List<Map<String, String>> strStateBeans;

    private TextView tvCity, tvZone, tvState;
    private TextView tvCancel, tvReset, tvYes;
    private LinearLayout llCity, llZone, llState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_record);
        ButterKnife.bind(this);
        ivShowcasing.setVisibility(View.GONE);
        tvShowcasing.setVisibility(View.GONE);
        init();
    }

    private void init() {
//        getOnlineRatesPic();

        strStateBeans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> mapTemp = new HashMap<String, String>();
            if (i == 0) {
                mapTemp.put("stateName", "安装状态（全部）");
            } else if (i == 1) {
                mapTemp.put("stateName", "是");
            } else {
                mapTemp.put("stateName", "否");
            }

            strStateBeans.add(mapTemp);
        }


        lvInternetAlarm.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新标签
        lvInternetAlarm.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        //设置下拉标签
        lvInternetAlarm.getLoadingLayoutProxy().setPullLabel("准备刷新");
        //设置释放标签
        lvInternetAlarm.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
        lvInternetAlarm.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新 业务代码
                    rLoad();
                } else if (refreshView.isFooterShown()) {
                    //上拉加载更多 业务代码
                    getLiftYearInspectionList();
                } else {
                    lvInternetAlarm.onRefreshComplete();
                }
            }
        });
        mListView = lvInternetAlarm.getRefreshableView();
        mAdapter = new RepairRecordAdapter(context, list, true);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!lvInternetAlarm.isRefreshing()) {
                    Intent intent = new Intent();
                    intent.setClass(context, RepairRecordDetailActivity.class);
                    intent.putExtra("id", "" + list.get(position).getID());
                    startActivity(intent);
                }
            }
        });

        showLoading("加载中...");
        rLoad();
    }

    //刷新数据
    public void rLoad() {
        pageNumber = 1;
        list.clear();
        getLiftYearInspectionList();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event){
//        if(popSearch!=null&&popSearch.isShowing()){
//            return false;
//        }
//        return super.dispatchTouchEvent(event);
//    }

    private void ShowDialog() {

        if (popSearch != null && popSearch.isShowing()) {
            popSearch.dismiss();
        } else {

            View view = getLayoutInflater().inflate(
                    R.layout.view_dialog_enquiries, null);

            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟llCity一样
            popSearch = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            ColorDrawable cd = new ColorDrawable(-0000);
            popSearch.setBackgroundDrawable(cd);
            popSearch.setAnimationStyle(R.style.DialogStyle);
            popSearch.update();
            popSearch.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popSearch.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mRetrofitManager.cancelAll();
                }
            });
            popSearch.setTouchable(true); // 设置popupwindow可点击
            popSearch.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popSearch.setFocusable(true); // 获取焦点
            // 设置popupwindow的位置（相对tvCity的位置）
            popSearch.showAsDropDown(rlSearch, 0,
                    DisplayUtil.dip2px(context, 0.5f));


            tvCancel = (TextView) view.findViewById(R.id.tvCancel);
            tvReset = (TextView) view.findViewById(R.id.tvReset);
            tvYes = (TextView) view.findViewById(R.id.tvYes);

            tvState = (TextView) view.findViewById(R.id.tvState);
            tvCity = (TextView) view.findViewById(R.id.tvCity);
            tvZone = (TextView) view.findViewById(R.id.tvZone);

            llCity = (LinearLayout) view.findViewById(R.id.llCity);
            llZone = (LinearLayout) view.findViewById(R.id.llZone);
            llState = (LinearLayout) view.findViewById(R.id.llState);
            llState.setVisibility(View.GONE);
            tvCity.setText(CityName);
            tvZone.setText(ZoneName);
            tvState.setText(StateName);

            llCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (strCityBeans == null || strCityBeans.size() == 0) {
                        getCityList();
                    } else {
                        newCityPop();
                    }

                }
            });

            llZone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvCity.getText().equals("市（全部）")) {
                        showToast("请先选择城市");
                    } else {
                        getAreaList();
                    }
                }
            });

            llState.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newStatePop();
                }
            });

            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popSearch.dismiss();
                }
            });

            tvReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ZoneId = "0";
                    CityId = "0";
                    stateId = "0";
                    useId = "0";
                    maintenanceId = "0";
                    CityName = "市（全部）";
                    ZoneName = "区（全部）";
                    StateName = "安装状态（全部）";
                    tvCity.setText("市（全部）");
                    tvZone.setText("区（全部）");
                    tvState.setText("安装状态（全部）");
                }
            });

            tvYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popSearch.dismiss();
                    if (tvCity.getText().equals("市（全部）")) {
                        CityId = "0";
                    }
                    if (tvZone.getText().equals("区（全部）")) {
                        ZoneId = "0";
                    }
                    if (tvState.getText().equals("安装状态（全部）")) {
                        stateId = "0";
                    }
                    showLoading("加载中...");
                    rLoad();

                }
            });

        }
    }


    private void newCityPop() {
        if (popCity != null && popCity.isShowing()) {
            popCity.dismiss();
        } else {

            layoutCity = getLayoutInflater().inflate(
                    R.layout.view_pop_menulist, null);
            lvMenuCity = (ListView) layoutCity
                    .findViewById(R.id.lvMenu);

            SimpleAdapter listAdapter = new SimpleAdapter(
                    context, strCityBeans, R.layout.item_pop_menu,
                    new String[]{"cityName"},
                    new int[]{R.id.tvTitlePop});

            lvMenuCity.setAdapter(listAdapter);
            // 点击listview中item的处理
            lvMenuCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View arg1, int arg2, long arg3) {
                    // 改变顶部对应TextView值
                    String strItem = strCityBeans.get(arg2).get(
                            "cityName");
                    CityName = strItem;
                    tvZone.setText("区（全部）");
                    ZoneName = "区（全部）";
                    ZoneId = "0";
                    tvCity.setText(strItem);
                    if (arg2 == 0) {
                        CityId = "0";
                    } else {
                        CityId = "" + cityBeans.get(arg2 - 1).getID();
                    }

                    // 隐藏弹出窗口
                    if (popCity != null && popCity.isShowing()) {
                        popCity.dismiss();
                    }
                }
            });
            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟llCity一样
            if (strCityBeans.size() > 10) {
                popCity = new PopupWindow(layoutCity, llCity.getWidth(),
                        ScreenUtil.getInstance(context).getScreenHeight() / 3);
            } else {
                popCity = new PopupWindow(layoutCity, llCity.getWidth(),
                        LinearLayout.LayoutParams.WRAP_CONTENT);
            }


            ColorDrawable cd = new ColorDrawable(-0000);
            popCity.setBackgroundDrawable(cd);
            popCity.setAnimationStyle(R.style.DialogStyle);
            popCity.update();
            popCity.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popCity.setTouchable(true); // 设置popupwindow可点击
            popCity.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popCity.setFocusable(true); // 获取焦点
            // 设置popupwindow的位置（相对tvCity的位置）
//            int topBarHeight = llCity.getBottom();
//            popCity.showAsDropDown(llCity, 0,
//                    (topBarHeight - llCity.getHeight()) / 2);
            popCity.showAsDropDown(rlSearch, DisplayUtil.dip2px(context, 8),
                    DisplayUtil.dip2px(context, 9) + llCity.getHeight());
            popCity.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popCity.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }
    }

    private void newZonePop() {
        if (popZone != null && popZone.isShowing()) {
            popZone.dismiss();
        } else {
            layoutZone = getLayoutInflater().inflate(
                    R.layout.view_pop_menulist, null);
            lvMenuZone = (ListView) layoutZone
                    .findViewById(R.id.lvMenu);

            SimpleAdapter listAdapter = new SimpleAdapter(
                    context, strZoneBeans, R.layout.item_pop_menu,
                    new String[]{"zoneName"},
                    new int[]{R.id.tvTitlePop});

            lvMenuZone.setAdapter(listAdapter);
            // 点击listview中item的处理
            lvMenuZone.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View arg1, int arg2, long arg3) {
                    // 改变顶部对应TextView值
                    String strItem = strZoneBeans.get(arg2).get(
                            "zoneName");
                    ZoneName = strItem;
                    tvZone.setText(strItem);
                    if (arg2 == 0) {
                        ZoneId = "0";
                    } else {
                        ZoneId = "" + zoneBeans.get(arg2 - 1).getID();
                    }

                    // 隐藏弹出窗口
                    if (popZone != null && popZone.isShowing()) {
                        popZone.dismiss();
                    }
                }
            });
            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟llCity一样
            if (strZoneBeans.size() > 10) {
                popZone = new PopupWindow(layoutZone, llZone.getWidth(),
                        ScreenUtil.getInstance(context).getScreenHeight() / 3);
            } else {
                popZone = new PopupWindow(layoutZone, llZone.getWidth(),
                        LinearLayout.LayoutParams.WRAP_CONTENT);
            }

            ColorDrawable cd = new ColorDrawable(-0000);
            popZone.setBackgroundDrawable(cd);
            popZone.setAnimationStyle(R.style.DialogStyle);
            popZone.update();
            popZone.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popZone.setTouchable(true); // 设置popupwindow可点击
            popZone.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popZone.setFocusable(true); // 获取焦点
            // 设置popupwindow的位置（相对tvCity的位置）
//            int topBarHeight = llZone.getBottom();
//            popZone.showAsDropDown(llZone, 0,
//                    (topBarHeight - llZone.getHeight()) / 2);
            popZone.showAsDropDown(rlSearch, DisplayUtil.dip2px(context, 16) + llCity.getWidth(),
                    DisplayUtil.dip2px(context, 9) + llZone.getHeight());
            popZone.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popZone.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }
    }


    private void newStatePop() {
        if (popState != null && popState.isShowing()) {
            popState.dismiss();
        } else {
            layoutState = getLayoutInflater().inflate(
                    R.layout.view_pop_menulist, null);
            lvMenuState = (ListView) layoutState
                    .findViewById(R.id.lvMenu);

            SimpleAdapter listAdapter = new SimpleAdapter(
                    context, strStateBeans, R.layout.item_pop_menu,
                    new String[]{"stateName"},
                    new int[]{R.id.tvTitlePop});

            lvMenuState.setAdapter(listAdapter);
            // 点击listview中item的处理
            lvMenuState.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0,
                                        View arg1, int arg2, long arg3) {
                    // 改变顶部对应TextView值
                    String strItem = strStateBeans.get(arg2).get(
                            "stateName");
                    StateName = strItem;
                    tvState.setText(strItem);
                    if (strItem.equals("是")) {
                        stateId = "1";
                    } else if (strItem.equals("否")) {
                        stateId = "2";
                    } else {
                        stateId = "0";
                    }


                    // 隐藏弹出窗口
                    if (popState != null && popState.isShowing()) {
                        popState.dismiss();
                    }
                }
            });
            // 创建弹出窗口
            // 窗口内容为layoutLeft，里面包含一个ListView
            // 窗口宽度跟llCity一样
            popState = new PopupWindow(layoutState, llState.getWidth(),
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            ColorDrawable cd = new ColorDrawable(-0000);
            popState.setBackgroundDrawable(cd);
            popState.setAnimationStyle(R.style.DialogStyle);
            popState.update();
            popState.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popState.setTouchable(true); // 设置popupwindow可点击
            popState.setOutsideTouchable(true); // 设置popupwindow外部可点击
            popState.setFocusable(true); // 获取焦点
            // 设置popupwindow的位置（相对tvCity的位置）
            int topBarHeight = llState.getBottom();
//            popState.showAsDropDown(llState, 0,
//                    (topBarHeight - llState.getHeight()) / 2);
            popState.showAsDropDown(rlSearch, DisplayUtil.dip2px(context, 8),
                    DisplayUtil.dip2px(context, 17) + llCity.getHeight() + llState.getHeight());
            popState.setTouchInterceptor(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // 如果点击了popupwindow的外部，popupwindow也会消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popState.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }
    }

    private void getLiftYearInspectionList() {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", userId);
        map.put("PageSize", "20");
        map.put("PageIndex", pageNumber);
        map.put("CityId", CityId);  //城市
        map.put("AddressId", ZoneId);  //区
        map.put("UseDeptId", useId);  //使用单位
        map.put("MaintDeptId", maintenanceId);   //维保单位
//        map.put("IsInstallation", stateId);   //是否安装
        Call<ResponBean> call = server.getService().repairs(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.d("data=" + response.body().getData());
                        pageNumber++;
                        List<RepairRecordBean> mDataBeen2 = new ArrayList<>();
                        mDataBeen2.addAll((List<RepairRecordBean>) gson.fromJson(response.body().getData(), new TypeToken<List<RepairRecordBean>>() {
                        }.getType()));
                        list.addAll(mDataBeen2);
                        mAdapter.setData(list);
                        mAdapter.notifyDataSetChanged();
                        mListView.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListView.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                }
                lvInternetAlarm.onRefreshComplete();
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    lvInternetAlarm.onRefreshComplete();
                    mListView.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }

    //获取城市列表
    private void getCityList() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", userId);
        Call<ResponBean> call = server.getService().getCityList(map, userId);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.d("data=" + response.body().getData());
                        cityBeans = gson.fromJson(response.body().getData(),
                                new TypeToken<ArrayList<CityBean>>() {
                                }.getType());
                        strCityBeans = new ArrayList<>();
                        HashMap<String, String> mapTemp2 = new HashMap<String, String>();
                        mapTemp2.put("cityName", "市（全部）");
                        strCityBeans.add(mapTemp2);
                        for (int i = 0; i < cityBeans.size(); i++) {
                            HashMap<String, String> mapTemp = new HashMap<String, String>();
                            mapTemp.put("cityName", "" + cityBeans.get(i).getName());
                            strCityBeans.add(mapTemp);
                        }
                        newCityPop();
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

    //获取区列表
    private void getAreaList() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", userId);
        Call<ResponBean> call = server.getService().getAreaList(map, userId, CityId);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    Logger.d("data=" + response.body().getData());
                    if (response.body().isSuccess()) {
                        zoneBeans = gson.fromJson(response.body().getData(),
                                new TypeToken<ArrayList<ZoneBean>>() {
                                }.getType());
                        strZoneBeans = new ArrayList<>();
                        HashMap<String, String> mapTemp2 = new HashMap<String, String>();
                        mapTemp2.put("zoneName", "区（全部）");
                        strZoneBeans.add(mapTemp2);
                        for (int i = 0; i < zoneBeans.size(); i++) {
                            HashMap<String, String> mapTemp = new HashMap<String, String>();
                            mapTemp.put("zoneName", "" + zoneBeans.get(i).getName());
                            strZoneBeans.add(mapTemp);
                        }
                        newZonePop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("请求失败");
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


    //获取使用单位列表
    private void getUseDeptList() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", userId);
        map.put("DistrictId", ZoneId);
        map.put("CityId", CityId);
        Logger.d("UserId=" + userId);
        Logger.d("DistrictId=" + ZoneId);
        Logger.d("CityId=" + CityId);
        Call<ResponBean> call = server.getService().getUseDeptList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.d("data=" + response.body().getData());
                        useBeans = gson.fromJson(response.body().getData(),
                                new TypeToken<ArrayList<UnitBean>>() {
                                }.getType());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("请求失败");
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

    //获取维保单位列表
    private void getMaintDeptList() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", userId);
        map.put("DistrictId", ZoneId);
        map.put("CityId", CityId);
        Call<ResponBean> call = server.getService().getMaintDeptList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.d("data=" + response.body().getData());
                        maintenanceBeans = gson.fromJson(response.body().getData(),
                                new TypeToken<ArrayList<UnitBean>>() {
                                }.getType());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("请求失败");
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

    @OnClick({R.id.ivBack, R.id.ivSearch, R.id.bLoad, R.id.ivScanning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.bLoad:
                showLoading("加载中...");
                rLoad();
                break;
            case R.id.ivSearch:
                ShowDialog();
                break;
            case R.id.ivScanning:
                //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
                    if (!permission) {
                        ActivityCompat.requestPermissions(RepairRecordActivity.this,
                                new String[]{Manifest.permission.CAMERA}, 100);
                    } else {
                        Intent intent = new Intent(RepairRecordActivity.this, CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_WXJL);
//                    startActivityForResult(intent,0);
                        startActivity(intent);
                    }
                } else {
                    if (PermissionUtil.cameraIsCanUse()) {
                        Intent intent = new Intent(RepairRecordActivity.this, CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_WXJL);
//                    startActivityForResult(intent,0);
                        startActivity(intent);
                    } else {
                        getPermission("缺少相机权限");
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(RepairRecordActivity.this, CaptureActivity.class);
                intent.putExtra("source", CaptureActivity.SCAN_WXJL);
//                startActivityForResult(intent,0);
                startActivity(intent);
            } else {
                getPermission("请授权APP访问摄像头权限！");
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

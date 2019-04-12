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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.elevator.ExamineDataBean;
import com.sinodom.elevator.bean.steplist.StepDataBean;
import com.sinodom.elevator.fragment.inspect.inspect.InspectionFragment;

import java.net.URLEncoder;
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
 * 检验服务
 */
public class InspectActivity extends BaseActivity {

    @BindView(R.id.vpInspection)
    ViewPager vpInspection;
    @BindView(R.id.tvNavigation1)
    TextView tvNavigation1;
    @BindView(R.id.tvNavigation2)
    TextView tvNavigation2;
    @BindView(R.id.tvNavigation3)
    TextView tvNavigation3;
    @BindView(R.id.tvEleInfoNum)
    TextView tvEleInfoNum;
    @BindView(R.id.tvEleInfoPlace)
    TextView tvEleInfoPlace;
    @BindView(R.id.tvEleInfoIstrue)
    TextView tvEleInfoIstrue;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlNavigation1)
    RelativeLayout rlNavigation1;
    @BindView(R.id.rlNavigation2)
    RelativeLayout rlNavigation2;
    @BindView(R.id.rlNavigation3)
    RelativeLayout rlNavigation3;
    @BindView(R.id.tvRegistrationCode)
    TextView tvRegistrationCode;
    @BindView(R.id.tvCopy)
    TextView tvCopy;
    @BindView(R.id.tvYes)
    TextView tvYes;
    @BindView(R.id.tvSign)
    TextView tvSign;
    private List<Fragment> list;
    private String result, inspectId;
    private StepDataBean dataBean;
    private List<ExamineDataBean> mDataBeen;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    public double latitude = 0;
    public double longitude = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
        ButterKnife.bind(this);
        inspectId = getIntent().getStringExtra("inspectId");
        Permission();

    }

    private void Permission() {
        boolean permission = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
        if (!permission || !permission1) {
            ActivityCompat.requestPermissions(InspectActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            Positioning();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Positioning();
            } else {
                getPermission("请授权APP定位权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void Positioning() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数


        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(0);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

//        option.setIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

//        option.setWifiValidTime(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        showLoading("定位中...");
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                latitude = location.getLatitude();    //获取纬度信息
                longitude = location.getLongitude();    //获取经度信息
//            double longitude = location.getLogitude();    //获取经度信息
//            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
                Logger.d("维度x= " + latitude + "-经度y= " + longitude);
                mLocationClient.stop();
                showLoading();
                userDataHttp_info(latitude, longitude);
            } else {
                if (location.getLocType() == 167) {
                    Toast.makeText(context, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。", Toast.LENGTH_LONG).show();
                }
                Logger.d("百度定位失败");
            }
//            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

//            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        }
    }

    //TODO
    @OnClick({R.id.rlNavigation1, R.id.rlNavigation2, R.id.rlNavigation3, R.id.ivBack, R.id.tvCopy, R.id.tvYes, R.id.tvSign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlNavigation1:
                vpInspection.setCurrentItem(0);
                tvNavigation1.setVisibility(View.VISIBLE);
                tvNavigation2.setVisibility(View.GONE);
                tvNavigation3.setVisibility(View.GONE);
                break;
            case R.id.rlNavigation2:
                vpInspection.setCurrentItem(1);
                tvNavigation1.setVisibility(View.GONE);
                tvNavigation2.setVisibility(View.VISIBLE);
                tvNavigation3.setVisibility(View.GONE);
                break;
            case R.id.rlNavigation3:
                vpInspection.setCurrentItem(2);
                tvNavigation1.setVisibility(View.GONE);
                tvNavigation2.setVisibility(View.GONE);
                tvNavigation3.setVisibility(View.VISIBLE);
                break;
            case R.id.ivBack:
                setFinish();
                break;
            case R.id.tvCopy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("" + tvRegistrationCode.getText());
                showToast("复制成功");
                break;
            case R.id.tvYes:
                setYes();
                break;
            case R.id.tvSign:
                Intent intent = new Intent(InspectActivity.this, InspectSignActivity.class);
                intent.putExtra("id", dataBean.getID());
                startActivity(intent);
                break;
        }
    }

    //处理Fragment和ViewPager的适配器
    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return list.get(arg0);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void userDataHttp_info(double MapX, double MapY) {
        showLoading("加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("LiftNum", "" + URLEncoder.encode(inspectId));
        map.put("UserId", "" + userId);
        map.put("MapX", "" + MapX);
        map.put("MapY", "" + MapY);

        Call<ResponBean> call = server.getService().getInspectByLiftNum(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(gson.toJson(response.body()));
                        dataBean = gson.fromJson(response.body().getData(), StepDataBean.class);
//                    dataBean.getInspectDetails()
                        if (dataBean != null) {
                            showToast("成功！");
                            initView();
                        } else {
                            showToast("暂无此电梯信息");
                        }


                    } else {
                        showToast("失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("失败！");
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

    private void initView() {
        userDataHttp_list();

        if (dataBean.getLift().getInstallationAddress() != null) {
            tvEleInfoPlace.setText(dataBean.getLift().getInstallationAddress());
        }

        if (dataBean.getLiftNum() != null) {
            tvEleInfoNum.setText(dataBean.getLiftNum());
        }
        if (dataBean.getCreateTime() != null) {
            String s3 = "" + dataBean.getCreateTime();
            String[] temp = null;
            temp = s3.split("T");
            String strTime = temp[1].substring(0, 5);
            tvEleInfoIstrue.setText("" + temp[0] + "  " + strTime);
        }
        if (dataBean.getLift().getCertificateNum() != null) {
            tvRegistrationCode.setText(dataBean.getLift().getCertificateNum());
        }

    }

    private void userDataHttp_list() {
        showLoading("加载中...");
        Call<ResponBean> call = server.getService().getInspectStepList();
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(response.body().getData());
                        mDataBeen = gson.fromJson(response.body().getData(),
                                new TypeToken<ArrayList<ExamineDataBean>>() {
                                }.getType());

                        Log.d("bean", String.valueOf(mDataBeen));
                        initData();

                    } else {
                        showToast("请求失败");
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

    private void initData() {
        ArrayList<ExamineDataBean> mDataBeenA = new ArrayList<>();
        ArrayList<ExamineDataBean> mDataBeenB = new ArrayList<>();
        ArrayList<ExamineDataBean> mDataBeenC = new ArrayList<>();

        for (int i = 0; i < mDataBeen.size(); i++) {
            if (mDataBeen.get(i).getTypeID() == 1) {
                mDataBeenA.add(mDataBeen.get(i));
            } else if (mDataBeen.get(i).getTypeID() == 2) {
                mDataBeenB.add(mDataBeen.get(i));
            } else {
                mDataBeenC.add(mDataBeen.get(i));
            }
        }

        ArrayList<StepDataBean.InspectDetailsBean> mDataBeenValueA = new ArrayList<>();
        ArrayList<StepDataBean.InspectDetailsBean> mDataBeenValueB = new ArrayList<>();
        ArrayList<StepDataBean.InspectDetailsBean> mDataBeenValueC = new ArrayList<>();

        if (dataBean.getInspectDetails().size() != 0) {
            for (int i = 0; i < dataBean.getInspectDetails().size(); i++) {
                if (dataBean.getInspectDetails().get(i).getTypeID() == 1) {
                    mDataBeenValueA.add(dataBean.getInspectDetails().get(i));
                } else if (dataBean.getInspectDetails().get(i).getTypeID() == 2) {
                    mDataBeenValueB.add(dataBean.getInspectDetails().get(i));
                } else {
                    mDataBeenValueC.add(dataBean.getInspectDetails().get(i));
                }
            }
        }

        InspectionFragment fragment1 = new InspectionFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("list", mDataBeenA);
        bundle1.putSerializable("list2", mDataBeenValueA);
        bundle1.putString("InspectId", inspectId);
        bundle1.putInt("Id", dataBean.getID());
        bundle1.putString("videoIndex", "1");
        fragment1.setArguments(bundle1);

        InspectionFragment fragment2 = new InspectionFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("list", mDataBeenB);
        bundle2.putSerializable("list2", mDataBeenValueB);
        bundle2.putString("InspectId", inspectId);
        bundle2.putInt("Id", dataBean.getID());
        bundle2.putString("videoIndex", "2");
        fragment2.setArguments(bundle2);

        InspectionFragment fragment3 = new InspectionFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putSerializable("list", mDataBeenC);
        bundle3.putSerializable("list2", mDataBeenValueC);
        bundle3.putString("InspectId", inspectId);
        bundle3.putInt("Id", dataBean.getID());
        bundle3.putString("videoIndex", "3");
        fragment3.setArguments(bundle3);

        list = new ArrayList<>();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vpInspection.setOffscreenPageLimit(list.size());
        vpInspection.setAdapter(adapter);
        vpInspection.setCurrentItem(0);
        tvNavigation1.setVisibility(View.VISIBLE);
        //设置viewpager页面滑动监听事件
        vpInspection.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    vpInspection.setCurrentItem(0);
                    tvNavigation1.setVisibility(View.VISIBLE);
                    tvNavigation2.setVisibility(View.GONE);
                    tvNavigation3.setVisibility(View.GONE);
                    break;
                case 1:
                    vpInspection.setCurrentItem(1);
                    tvNavigation1.setVisibility(View.GONE);
                    tvNavigation2.setVisibility(View.VISIBLE);
                    tvNavigation3.setVisibility(View.GONE);
                    break;
                case 2:
                    vpInspection.setCurrentItem(2);
                    tvNavigation1.setVisibility(View.GONE);
                    tvNavigation2.setVisibility(View.GONE);
                    tvNavigation3.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
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

    private void setYes() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("完成后本次检验将不可更改并结束！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                        updateInspectByID();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setFinish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void updateInspectByID() {
        showLoading("加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().updateInspectByID(map, dataBean.getID());
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    Logger.d("isSuccess=" + response.body().isSuccess());
                    Logger.d("getMessage=" + response.body().getMessage());
                    if (response.body().isSuccess()) {

                        if (response.body().getMessage().equals("操作成功")) {
                            Intent intent = new Intent();
                            setResult(10, intent);
                            finish();
                        } else {
                            Logger.d("成功进入");
                            showToast(response.body().getMessage());
                        }

                    } else {
                        showToast(response.body().getMessage());
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

}

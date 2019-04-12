package com.sinodom.elevator.activity.inspect;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.elevator.ExamineDataBean;
import com.sinodom.elevator.bean.steplist.StepDataBean;
import com.sinodom.elevator.fragment.inspect.inspect.RecordDisplayFragment;

import java.io.Serializable;
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
 * Created by 安卓 on 2017/11/28.
 * 查看检验记录
 */

public class RecordDisplayActivity extends BaseActivity {

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
    @BindView(R.id.tvCopy)
    TextView tvCopy;
    @BindView(R.id.tvRegistrationCode)
    TextView tvRegistrationCode;
    @BindView(R.id.tvYes)
    TextView tvYes;
    @BindView(R.id.tvSign)
    TextView tvSign;


    private List<Fragment> list;


    private String result, inspectId;
    private StepDataBean dataBean;
    private List<ExamineDataBean> mDataBeen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
        ButterKnife.bind(this);
        result = getIntent().getStringExtra("result");
        inspectId = result.substring(result.indexOf("=") + 1);
        tvYes.setVisibility(View.GONE);
        tvSign.setVisibility(View.GONE);
        userDataHttp_info();
    }

    private void userDataHttp_info() {
        showLoading("加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("LiftNum", "" + URLEncoder.encode(inspectId));
        map.put("UserId", "" + userId);
        map.put("MapX", "");
        map.put("MapY", "");
        Call<ResponBean> call = server.getService().getInspectByLiftNum(map);
//        Call<ResponBean> call = server.getService().setList("" + inspectId,"" + userId);
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


    //TODO
    @OnClick({R.id.rlNavigation1, R.id.rlNavigation2, R.id.rlNavigation3, R.id.ivBack, R.id.tvCopy})
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
                finish();
                break;
            case R.id.tvCopy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("" + tvRegistrationCode.getText());
                showToast("复制成功");
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
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


        RecordDisplayFragment fragment1 = new RecordDisplayFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("list", (Serializable) mDataBeenA);
        bundle1.putSerializable("list2", (Serializable) mDataBeenValueA);
//        bundle1.putString("InspectId", inspectId);
//        bundle1.putString("Id", "" + dataBean.getID());
        fragment1.setArguments(bundle1);

        RecordDisplayFragment fragment2 = new RecordDisplayFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("list", (Serializable) mDataBeenB);
        bundle2.putSerializable("list2", (Serializable) mDataBeenValueB);
//        bundle2.putString("InspectId", inspectId);
//        bundle2.putString("Id", "" + dataBean.getID());
        fragment2.setArguments(bundle2);

        RecordDisplayFragment fragment3 = new RecordDisplayFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putSerializable("list", (Serializable) mDataBeenC);
        bundle3.putSerializable("list2", (Serializable) mDataBeenValueC);
//        bundle3.putString("InspectId", inspectId);
//        bundle3.putString("Id", "" + dataBean.getID());
        fragment3.setArguments(bundle3);

        list = new ArrayList<Fragment>();
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
}

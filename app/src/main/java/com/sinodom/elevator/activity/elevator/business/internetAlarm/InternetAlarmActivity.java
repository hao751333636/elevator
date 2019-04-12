package com.sinodom.elevator.activity.elevator.business.internetAlarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.fragment.elevator.business.alarm.EquipmentFragment;
import com.sinodom.elevator.fragment.elevator.business.alarm.StateFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 安卓 on 2017/11/29.
 * 业务-报警仪联网
 */

public class InternetAlarmActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlTitle)
    RelativeLayout rlTitle;
    @BindView(R.id.tvEquipment)
    TextView tvEquipment;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.vpInternetAlarm)
    ViewPager vpInternetAlarm;
    @BindView(R.id.tvEquipment1)
    TextView tvEquipment1;
    @BindView(R.id.rlEquipment)
    RelativeLayout rlEquipment;
    @BindView(R.id.tvState1)
    TextView tvState1;
    @BindView(R.id.rlState)
    RelativeLayout rlState;

    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_alarm);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        EquipmentFragment fragment = new EquipmentFragment();
        StateFragment fragment2 = new StateFragment();

        list = new ArrayList<Fragment>();
        list.add(fragment);
        list.add(fragment2);


        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
//        vpInternetAlarm.setOffscreenPageLimit(list.size());
        vpInternetAlarm.setAdapter(adapter);
        vpInternetAlarm.setCurrentItem(0);
        tvEquipment.setTextColor(this.getResources().getColor(R.color.actionbar));
        tvState.setTextColor(this.getResources().getColor(R.color.black2));
        tvEquipment1.setVisibility(View.VISIBLE);
        tvState1.setVisibility(View.GONE);
        //设置viewpager页面滑动监听事件
        vpInternetAlarm.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @OnClick({R.id.ivBack, R.id.rlEquipment, R.id.rlState})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlEquipment:
                vpInternetAlarm.setCurrentItem(0);
                tvEquipment.setTextColor(this.getResources().getColor(R.color.actionbar));
                tvState.setTextColor(this.getResources().getColor(R.color.black2));
                tvEquipment1.setVisibility(View.VISIBLE);
                tvState1.setVisibility(View.GONE);
                break;
            case R.id.rlState:
                vpInternetAlarm.setCurrentItem(1);
                tvEquipment.setTextColor(this.getResources().getColor(R.color.black2));
                tvState.setTextColor(this.getResources().getColor(R.color.actionbar));
                tvEquipment1.setVisibility(View.GONE);
                tvState1.setVisibility(View.VISIBLE);
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

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    vpInternetAlarm.setCurrentItem(0);
                    tvEquipment.setTextColor(InternetAlarmActivity.this.getResources().getColor(R.color.actionbar));
                    tvState.setTextColor(InternetAlarmActivity.this.getResources().getColor(R.color.black2));
                    tvEquipment1.setVisibility(View.VISIBLE);
                    tvState1.setVisibility(View.GONE);
                    break;
                case 1:
                    vpInternetAlarm.setCurrentItem(1);
                    tvEquipment.setTextColor(InternetAlarmActivity.this.getResources().getColor(R.color.black2));
                    tvState.setTextColor(InternetAlarmActivity.this.getResources().getColor(R.color.actionbar));
                    tvEquipment1.setVisibility(View.GONE);
                    tvState1.setVisibility(View.VISIBLE);
                    break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}

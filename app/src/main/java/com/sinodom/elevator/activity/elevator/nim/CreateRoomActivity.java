package com.sinodom.elevator.activity.elevator.nim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.nim.fragment.CallFragment;
import com.sinodom.elevator.activity.elevator.nim.fragment.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateRoomActivity extends BaseActivity {

    public static boolean isCall = false;
    @BindView(R.id.tvCall)
    TextView tvCall;
    @BindView(R.id.tvCall1)
    TextView tvCall1;
    @BindView(R.id.tvHistory)
    TextView tvHistory;
    @BindView(R.id.tvHistory1)
    TextView tvHistory1;
    @BindView(R.id.vpInternetAlarm)
    ViewPager vpInternetAlarm;
    private CallFragment mCallFragment;
    private HistoryFragment mHistoryFragment;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mCallFragment = new CallFragment();
        mHistoryFragment = new HistoryFragment();
        list = new ArrayList<>();
        list.add(mCallFragment);
        list.add(mHistoryFragment);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vpInternetAlarm.setAdapter(adapter);
        vpInternetAlarm.setCurrentItem(0);
        tvCall.setTextColor(this.getResources().getColor(R.color.white));
        tvHistory.setTextColor(this.getResources().getColor(R.color.black6));
        tvCall1.setVisibility(View.VISIBLE);
        tvHistory1.setVisibility(View.GONE);
        //设置viewpager页面滑动监听事件
        vpInternetAlarm.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @OnClick({R.id.ivBack, R.id.rlCall, R.id.rlHistory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlCall:
                vpInternetAlarm.setCurrentItem(0);
                tvCall.setTextColor(getResources().getColor(R.color.white));
                tvHistory.setTextColor(getResources().getColor(R.color.black6));
                tvCall1.setVisibility(View.VISIBLE);
                tvHistory1.setVisibility(View.GONE);
                break;
            case R.id.rlHistory:
                vpInternetAlarm.setCurrentItem(1);
                tvCall.setTextColor(getResources().getColor(R.color.black6));
                tvHistory.setTextColor(getResources().getColor(R.color.white));
                tvCall1.setVisibility(View.GONE);
                tvHistory1.setVisibility(View.VISIBLE);
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
                    tvCall.setTextColor(getResources().getColor(R.color.white));
                    tvHistory.setTextColor(getResources().getColor(R.color.black6));
                    tvCall1.setVisibility(View.VISIBLE);
                    tvHistory1.setVisibility(View.GONE);
                    break;
                case 1:
                    vpInternetAlarm.setCurrentItem(1);
                    tvCall.setTextColor(getResources().getColor(R.color.black6));
                    tvHistory.setTextColor(getResources().getColor(R.color.white));
                    tvCall1.setVisibility(View.GONE);
                    tvHistory1.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    }
}

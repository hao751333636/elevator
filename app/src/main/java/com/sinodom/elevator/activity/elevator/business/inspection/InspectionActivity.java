package com.sinodom.elevator.activity.elevator.business.inspection;

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
import com.sinodom.elevator.fragment.elevator.business.inspection.AwaitFragment;
import com.sinodom.elevator.fragment.elevator.business.inspection.RecordFragment;
import com.sinodom.elevator.fragment.elevator.business.inspection.TimeoutFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 安卓 on 2017/11/29.
 * 业务-检验管理
 */

public class InspectionActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvRecord)
    TextView tvRecord;
    @BindView(R.id.tvRecord1)
    TextView tvRecord1;
    @BindView(R.id.rlRecord)
    RelativeLayout rlRecord;
    @BindView(R.id.tvAwait)
    TextView tvAwait;
    @BindView(R.id.tvAwait1)
    TextView tvAwait1;
    @BindView(R.id.rlAwait)
    RelativeLayout rlAwait;
    @BindView(R.id.tvTimeout)
    TextView tvTimeout;
    @BindView(R.id.tvTimeout1)
    TextView tvTimeout1;
    @BindView(R.id.rlTimeout)
    RelativeLayout rlTimeout;
    @BindView(R.id.vpInspectionManagement)
    ViewPager vpInspectionManagement;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        RecordFragment fragment = new RecordFragment();
        AwaitFragment fragment2 = new AwaitFragment();
        TimeoutFragment fragment3 = new TimeoutFragment();

        list = new ArrayList<Fragment>();
        list.add(fragment);
        list.add(fragment2);
        list.add(fragment3);


        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vpInspectionManagement.setOffscreenPageLimit(list.size());
        vpInspectionManagement.setAdapter(adapter);
        vpInspectionManagement.setCurrentItem(0);
        tvRecord.setTextColor(this.getResources().getColor(R.color.actionbar));
        tvAwait.setTextColor(this.getResources().getColor(R.color.black2));
        tvTimeout.setTextColor(this.getResources().getColor(R.color.black2));
        tvRecord1.setVisibility(View.VISIBLE);
        tvAwait1.setVisibility(View.GONE);
        tvTimeout1.setVisibility(View.GONE);
        //设置viewpager页面滑动监听事件
        vpInspectionManagement.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    @OnClick({R.id.ivBack, R.id.rlRecord, R.id.rlAwait,R.id.rlTimeout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlRecord:
                vpInspectionManagement.setCurrentItem(0);
                tvRecord.setTextColor(this.getResources().getColor(R.color.actionbar));
                tvAwait.setTextColor(this.getResources().getColor(R.color.black2));
                tvTimeout.setTextColor(this.getResources().getColor(R.color.black2));
                tvRecord1.setVisibility(View.VISIBLE);
                tvAwait1.setVisibility(View.GONE);
                tvTimeout1.setVisibility(View.GONE);
                break;
            case R.id.rlAwait:
                vpInspectionManagement.setCurrentItem(1);
                tvRecord.setTextColor(this.getResources().getColor(R.color.black2));
                tvAwait.setTextColor(this.getResources().getColor(R.color.actionbar));
                tvTimeout.setTextColor(this.getResources().getColor(R.color.black2));
                tvRecord1.setVisibility(View.GONE);
                tvAwait1.setVisibility(View.VISIBLE);
                tvTimeout1.setVisibility(View.GONE);
                break;
            case R.id.rlTimeout:
                vpInspectionManagement.setCurrentItem(2);
                tvRecord.setTextColor(this.getResources().getColor(R.color.black2));
                tvAwait.setTextColor(this.getResources().getColor(R.color.black2));
                tvTimeout.setTextColor(this.getResources().getColor(R.color.actionbar));
                tvRecord1.setVisibility(View.GONE);
                tvAwait1.setVisibility(View.GONE);
                tvTimeout1.setVisibility(View.VISIBLE);
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
                    vpInspectionManagement.setCurrentItem(0);
                    tvRecord.setTextColor(InspectionActivity.this.getResources().getColor(R.color.actionbar));
                    tvAwait.setTextColor(InspectionActivity.this.getResources().getColor(R.color.black2));
                    tvTimeout.setTextColor(InspectionActivity.this.getResources().getColor(R.color.black2));
                    tvRecord1.setVisibility(View.VISIBLE);
                    tvAwait1.setVisibility(View.GONE);
                    tvTimeout1.setVisibility(View.GONE);
                    break;
                case 1:
                    vpInspectionManagement.setCurrentItem(1);
                    tvRecord.setTextColor(InspectionActivity.this.getResources().getColor(R.color.black2));
                    tvAwait.setTextColor(InspectionActivity.this.getResources().getColor(R.color.actionbar));
                    tvTimeout.setTextColor(InspectionActivity.this.getResources().getColor(R.color.black2));
                    tvRecord1.setVisibility(View.GONE);
                    tvAwait1.setVisibility(View.VISIBLE);
                    tvTimeout1.setVisibility(View.GONE);
                    break;
                case 2:
                    vpInspectionManagement.setCurrentItem(2);
                    tvRecord.setTextColor(InspectionActivity.this.getResources().getColor(R.color.black2));
                    tvAwait.setTextColor(InspectionActivity.this.getResources().getColor(R.color.black2));
                    tvTimeout.setTextColor(InspectionActivity.this.getResources().getColor(R.color.actionbar));
                    tvRecord1.setVisibility(View.GONE);
                    tvAwait1.setVisibility(View.GONE);
                    tvTimeout1.setVisibility(View.VISIBLE);
                    break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}

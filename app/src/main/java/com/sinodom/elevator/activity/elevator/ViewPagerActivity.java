package com.sinodom.elevator.activity.elevator;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.PhotoViewPagerAdapter;
import com.sinodom.elevator.bean.lift.PhotoBean;
import com.sinodom.elevator.view.HackyViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends BaseActivity {

    private ImageView ivBack;
    private HackyViewPager mViewPager;
    private PhotoViewPagerAdapter adapter;
    private List<PhotoBean> data = new ArrayList<>();
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        data = (List<PhotoBean>) intent.getSerializableExtra("data");
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
        //setContentView(mViewPager);
        adapter = new PhotoViewPagerAdapter(ViewPagerActivity.this);
        mViewPager.setAdapter(adapter);
        adapter.setData(data);
        adapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(position);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
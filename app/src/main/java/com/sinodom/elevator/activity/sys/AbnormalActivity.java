package com.sinodom.elevator.activity.sys;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;

/**
 * Created by HYD on 2017/7/12.
 * 异常Activit
 */
public class AbnormalActivity extends BaseActivity implements View.OnClickListener {

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abnormal);
        initView();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }
}
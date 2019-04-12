package com.sinodom.elevator.activity.elevator.business.maintenance;

import android.os.Bundle;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 签名详情
 */
public class SignDetailActivity extends BaseActivity {

    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail2);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mId = getIntent().getIntExtra("id", 0);
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
}

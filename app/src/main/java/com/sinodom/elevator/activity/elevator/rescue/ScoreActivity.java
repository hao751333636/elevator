package com.sinodom.elevator.activity.elevator.rescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 服务评价
 */
public class ScoreActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rbDcqk)
    RatingBar rbDcqk;
    @BindView(R.id.rbJygc)
    RatingBar rbJygc;
    @BindView(R.id.rbGztd)
    RatingBar rbGztd;
    @BindView(R.id.tvCommit)
    TextView tvCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(Constants.Code.SCORE_OK, new Intent());
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ivBack, R.id.tvCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                setResult(Constants.Code.SCORE_OK, new Intent());
                finish();
                break;
            case R.id.tvCommit:
                Intent intent = new Intent();
                intent.putExtra("dcqk", (int) rbDcqk.getRating());
                intent.putExtra("jygc", (int) rbJygc.getRating());
                intent.putExtra("gztd", (int) rbGztd.getRating());
                setResult(Constants.Code.SCORE_OK, intent);
                finish();
                break;
        }
    }
}

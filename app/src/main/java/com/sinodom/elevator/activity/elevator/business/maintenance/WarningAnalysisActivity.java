package com.sinodom.elevator.activity.elevator.business.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.BridgeWebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 安卓 on 2018/1/3.
 * 预警分析
 */

public class WarningAnalysisActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlComprehensive)
    RelativeLayout rlComprehensive;
    @BindView(R.id.rlYearsLimit)
    RelativeLayout rlYearsLimit;
    @BindView(R.id.rlTimeout)
    RelativeLayout rlTimeout;
    @BindView(R.id.rlMaintenance)
    RelativeLayout rlMaintenance;
    @BindView(R.id.rlAlarm)
    RelativeLayout rlAlarm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_analysis);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.ivBack, R.id.rlComprehensive, R.id.rlYearsLimit, R.id.rlTimeout, R.id.rlMaintenance, R.id.rlAlarm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlComprehensive:
                jump("救援平均到场时间", BuildConfig.SERVER + "WebApp/Statistics/RescueTime?UserId=" + manager.getSession().getUserID());
                break;
            case R.id.rlYearsLimit:
                jump("电梯年限", BuildConfig.SERVER + "WebApp/Statistics/ElevatorAge?UserId=" + manager.getSession().getUserID());
                break;
            case R.id.rlTimeout:
                jump("本月超期未检电梯数量",BuildConfig.SERVER + "WebApp/Statistics/Overdue?UserId=" + manager.getSession().getUserID());
                break;
            case R.id.rlMaintenance:
                jump("无纸化维保电梯数量",BuildConfig.SERVER + "WebApp/Statistics/MaintenanceRatio?UserId=" + manager.getSession().getUserID());
                break;
            case R.id.rlAlarm:
                jump("报警总数",BuildConfig.SERVER + "WebApp/Statistics/AlarmRatio?UserId=" + manager.getSession().getUserID());
                break;
        }
    }

    private void jump(String Title, String Url){
        Intent intent = new Intent();
        intent.setClass(this, BridgeWebViewActivity.class);
        intent.putExtra("title", Title);
        intent.putExtra("url", Url);
        startActivity(intent);
    }
}


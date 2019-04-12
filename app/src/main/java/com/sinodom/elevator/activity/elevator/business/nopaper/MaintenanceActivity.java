package com.sinodom.elevator.activity.elevator.business.nopaper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.BridgeWebViewActivity;
import com.sinodom.elevator.zxing.activity.CaptureActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaintenanceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.rlElevator1, R.id.rlElevator2, R.id.rlElevator3})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.rlElevator1:
                //无纸化维保
                intent = new Intent(context, BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/NFCMaintenance/Index?userid=" + manager.getSession().getUserID());
                intent.putExtra("source", CaptureActivity.SCAN_WZHWB);//不为空打开扫码
                intent.putExtra("title", "无纸化维保");
                startActivity(intent);
                break;
            case R.id.rlElevator2:
                //超期电梯
                intent = new Intent(context, BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/OverdueLift/index?userid=" + manager.getSession().getUserID());
                intent.putExtra("title", "超期电梯");
                startActivity(intent);
                break;
            case R.id.rlElevator3:
                //停梯申请
                intent = new Intent(context, BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/StopLift/Index?UserId=" + manager.getSession().getUserID());
                intent.putExtra("title", "停梯申请");
                startActivity(intent);
                break;
        }
    }
}

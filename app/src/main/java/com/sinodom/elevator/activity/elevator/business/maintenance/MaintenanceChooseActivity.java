package com.sinodom.elevator.activity.elevator.business.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 安卓 on 2018/1/2.
 * 选择维保
 */

public class MaintenanceChooseActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvEleInfoNum)
    TextView tvEleInfoNum;
    @BindView(R.id.tvEleInfoIstrue)
    TextView tvEleInfoIstrue;
    @BindView(R.id.tvEleInfoPlace)
    TextView tvEleInfoPlace;
    @BindView(R.id.ivHalf)
    ImageView ivHalf;
    @BindView(R.id.ivQuarter)
    ImageView ivQuarter;
    @BindView(R.id.ivHalfYear)
    ImageView ivHalfYear;
    @BindView(R.id.ivYear)
    ImageView ivYear;
    private String ID = ""; //电梯ID
    private String InstallationAddress = "";  //电梯地址
    private String UploadDate = "";  //上次维保时间
    private String floorNumber = "";   //本次维保对比ID
    private String LongitudeAndLatitude = "";   //电梯位置
    private String LiftNum = "";   //电梯位置
    private String result = "";
    private String CheckType = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_choose);
        ButterKnife.bind(this);

        ID = getIntent().getStringExtra("ID");
        InstallationAddress = getIntent().getStringExtra("InstallationAddress");
        UploadDate = getIntent().getStringExtra("UploadDate");
        result = getIntent().getStringExtra("FloorNumber");
        floorNumber = result.substring(0, result.indexOf("."));
        LongitudeAndLatitude = getIntent().getStringExtra("LongitudeAndLatitude");
        LiftNum = getIntent().getStringExtra("LiftNum");


        tvEleInfoNum.setText(LiftNum);
        if (UploadDate.equals("")) {
            tvEleInfoIstrue.setText("尚未巡检");
        } else {
            String s3 = "" + UploadDate;
            String[] temp = null;
            temp = s3.split("T");
            String strTime = temp[1].substring(0, 5);
            tvEleInfoIstrue.setText("" + temp[0] + "  " + strTime);
        }
        tvEleInfoPlace.setText(InstallationAddress);


    }

    private void jump(String CheckType,String CType) {
        Bundle bundle = new Bundle();
        bundle.putString("ID", "" + ID);
        bundle.putString("InstallationAddress", "" + InstallationAddress);
        bundle.putString("UploadDate", "" + UploadDate);
        bundle.putString("FloorNumber", "" + result);
        bundle.putString("LongitudeAndLatitude", "" + LongitudeAndLatitude);
        bundle.putString("LiftNum", "" + LiftNum);
        bundle.putString("CheckType", "" + CheckType);
        bundle.putString("CType", "" + CType);
        startActivity(new Intent(MaintenanceChooseActivity.this, MaintenanceInspectionActivity2.class).putExtras(bundle));
    }

    @OnClick({R.id.ivHalf, R.id.ivQuarter, R.id.ivHalfYear, R.id.ivYear, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivHalf:
                jump("0","0");
                break;
            case R.id.ivQuarter:
                jump("2","2");
                break;
            case R.id.ivHalfYear:
                jump("3","3");
                break;
            case R.id.ivYear:
                jump("4","4");
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }
}

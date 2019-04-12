package com.sinodom.elevator.fragment.elevator.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.BridgeWebViewActivity;
import com.sinodom.elevator.activity.elevator.business.inspection.InspectionActivity;
import com.sinodom.elevator.activity.elevator.business.internetAlarm.InternetAlarmActivity;
import com.sinodom.elevator.activity.elevator.business.maintenance.WarningAnalysisActivity;
import com.sinodom.elevator.activity.elevator.business.nopaper.MaintenanceActivity;
import com.sinodom.elevator.activity.elevator.business.sign.SignActivity;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 安卓 on 2017/11/14.
 * 电梯业务
 */

public class BusinessFragment extends BaseFragment {

    @BindView(R.id.llMaintenance)
    LinearLayout llMaintenance;//电梯维保
    @BindView(R.id.llInspection)
    LinearLayout llInspection; //电梯年检
    @BindView(R.id.llInternetAlarm)
    LinearLayout llInternetAlarm; //报警仪联网
    @BindView(R.id.llShielding)
    LinearLayout llShielding;  //检验管理
    @BindView(R.id.llNotice)
    LinearLayout llNotice; //通知通告
    @BindView(R.id.llMaintenanceRecord)
    LinearLayout llMaintenanceRecord; //维修记录
    @BindView(R.id.llMonitor)
    LinearLayout llMonitor;   //物联监控
    @BindView(R.id.llComplaint)
    LinearLayout llComplaint;  //投诉建议
    Unbinder unbinder;
    @BindView(R.id.llProperty) //物业巡查
            LinearLayout llProperty;
    @BindView(R.id.llWarning) //预警分析
            LinearLayout llWarning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business, null);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        initView(view);
        init();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View v) {
    }

    private void init() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llMaintenance, R.id.llInspection, R.id.llInternetAlarm, R.id.llShielding
            , R.id.llNotice, R.id.llMaintenanceRecord, R.id.llMonitor, R.id.llComplaint
            , R.id.llProperty, R.id.llWarning, R.id.llMaintenance1})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.llMaintenance:
                //电梯维保
                if (manager.getSession().getRoleId() == 12 || manager.getSession().getRoleId() == 13) {
                    intent = new Intent(getActivity(), SignActivity.class);
                } else {
//                    intent = new Intent(getActivity(), MaintenanceActivity.class);
                    intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                    intent.putExtra("title", "电梯维保");
                    intent.putExtra("source", CaptureActivity.SCAN_DTWB);//不为空打开扫码
                    intent.putExtra("url", BuildConfig.SERVER + "WebApp/Maintenance/Index?userId=" + manager.getSession().getUserID());
                }
                startActivity(intent);
                break;
            case R.id.llInspection:
                //电梯年检
                intent = new Intent(getActivity(), InspectionActivity.class);
                startActivity(intent);
                break;
            case R.id.llInternetAlarm:
                //报警仪联网
                intent = new Intent(getActivity(), InternetAlarmActivity.class);
                startActivity(intent);
                break;
            case R.id.llShielding:
                //检验服务
                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/Inspect/Index?UserId=" + manager.getSession().getUserID());
                intent.putExtra("source", CaptureActivity.SCAN_JYFU);//不为空打开扫码
                intent.putExtra("title", "检测服务");
                startActivity(intent);
                break;
            case R.id.llNotice:
                //通知通告
                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/Notice/Index?UserId=" + manager.getSession().getUserID());
                intent.putExtra("title", "通知通告");
                startActivity(intent);
                break;
            case R.id.llMaintenanceRecord:
                //维修记录
//                intent = new Intent(getActivity(), RepairRecordActivity.class);
//                startActivity(intent);
                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("title", "维修记录");
                intent.putExtra("source", CaptureActivity.SCAN_WXJL);//不为空打开扫码
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/Repair/index?userId=" + manager.getSession().getUserID());
                startActivity(intent);
                break;
            case R.id.llMonitor:
                //物联监控
                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/Monitoring/Index?userid=" + manager.getSession().getUserID());
                intent.putExtra("title", "物联监控");
                startActivity(intent);
                break;
            case R.id.llComplaint:
                //投诉建议
                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/Complaint/Index?UserId=" + manager.getSession().getUserID());
                intent.putExtra("title", "投诉建议");
                startActivity(intent);
                break;
            case R.id.llProperty:
                //物业巡查
                intent = new Intent(getActivity(), BridgeWebViewActivity.class);
                intent.putExtra("title", "物业巡查");
                intent.putExtra("source", CaptureActivity.SCAN_WUXC);//不为空打开扫码
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/PropertyCheck/Index?userId=" + manager.getSession().getUserID());
                startActivity(intent);
                break;
            case R.id.llMaintenance1:
                //无纸化维保
                intent = new Intent(getActivity(), MaintenanceActivity.class);
                startActivity(intent);
                break;
            case R.id.llWarning:
                //预警分析
                intent = new Intent(getActivity(), WarningAnalysisActivity.class);
                startActivity(intent);
                break;


        }
    }
}

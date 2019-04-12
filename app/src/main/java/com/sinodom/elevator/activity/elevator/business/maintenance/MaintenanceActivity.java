package com.sinodom.elevator.activity.elevator.business.maintenance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.sinodom.elevator.fragment.elevator.business.maintenance.HistoryFragment;
import com.sinodom.elevator.fragment.elevator.business.maintenance.MaintenanceFragment;
import com.sinodom.elevator.fragment.elevator.business.maintenance.MissedFragment;
import com.sinodom.elevator.fragment.elevator.business.maintenance.PendingFragment;
import com.sinodom.elevator.fragment.elevator.business.maintenance.SignFragment;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 安卓 on 2017/11/29.
 * 业务-电梯维保
 */

public class MaintenanceActivity extends BaseActivity {


    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.tvMaintenance)
    TextView tvMaintenance;
    @BindView(R.id.tvMaintenance1)
    TextView tvMaintenance1;
    @BindView(R.id.rlMaintenance)
    RelativeLayout rlMaintenance;
    @BindView(R.id.tvPending)
    TextView tvPending;
    @BindView(R.id.tvPending1)
    TextView tvPending1;
    @BindView(R.id.rlPending)
    RelativeLayout rlPending;
    @BindView(R.id.tvMissed)
    TextView tvMissed;
    @BindView(R.id.tvMissed1)
    TextView tvMissed1;
    @BindView(R.id.rlMissed)
    RelativeLayout rlMissed;
    @BindView(R.id.tvHistory)
    TextView tvHistory;
    @BindView(R.id.tvHistory1)
    TextView tvHistory1;
    @BindView(R.id.rlHistory)
    RelativeLayout rlHistory;
    @BindView(R.id.vpMaintenance)
    ViewPager vpMaintenance;
    @BindView(R.id.ivScanning)
    ImageView ivScanning;
    @BindView(R.id.tvSign)
    TextView tvSign;
    @BindView(R.id.tvSign1)
    TextView tvSign1;
    @BindView(R.id.rlSign)
    RelativeLayout rlSign;

    private List<Fragment> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        MaintenanceFragment maintenanceFragment = new MaintenanceFragment();
        PendingFragment pendingFragment = new PendingFragment();
        MissedFragment missedFragment = new MissedFragment();
        HistoryFragment historyFragment = new HistoryFragment();
        SignFragment signFragment = new SignFragment();

        list = new ArrayList<Fragment>();
        list.add(maintenanceFragment);
        list.add(pendingFragment);
        list.add(missedFragment);
        list.add(historyFragment);
        list.add(signFragment);


        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        vpMaintenance.setOffscreenPageLimit(list.size());
        vpMaintenance.setAdapter(adapter);
        vpMaintenance.setCurrentItem(0);
        tvMaintenance.setTextColor(this.getResources().getColor(R.color.actionbar));
        tvPending.setTextColor(this.getResources().getColor(R.color.black2));
        tvMissed.setTextColor(this.getResources().getColor(R.color.black2));
        tvHistory.setTextColor(this.getResources().getColor(R.color.black2));
        tvSign.setTextColor(this.getResources().getColor(R.color.black2));
        tvMaintenance1.setVisibility(View.VISIBLE);
        tvPending1.setVisibility(View.GONE);
        tvMissed1.setVisibility(View.GONE);
        tvHistory1.setVisibility(View.GONE);
        tvSign1.setVisibility(View.GONE);
        //设置viewpager页面滑动监听事件
        vpMaintenance.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    vpMaintenance.setCurrentItem(0);
                    tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                    tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvMaintenance1.setVisibility(View.VISIBLE);
                    tvPending1.setVisibility(View.GONE);
                    tvMissed1.setVisibility(View.GONE);
                    tvHistory1.setVisibility(View.GONE);
                    tvSign1.setVisibility(View.GONE);
                    break;
                case 1:
                    vpMaintenance.setCurrentItem(1);
                    tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                    tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvMaintenance1.setVisibility(View.GONE);
                    tvPending1.setVisibility(View.VISIBLE);
                    tvMissed1.setVisibility(View.GONE);
                    tvHistory1.setVisibility(View.GONE);
                    tvSign1.setVisibility(View.GONE);
                    break;
                case 2:
                    vpMaintenance.setCurrentItem(2);
                    tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                    tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvMaintenance1.setVisibility(View.GONE);
                    tvPending1.setVisibility(View.GONE);
                    tvMissed1.setVisibility(View.VISIBLE);
                    tvHistory1.setVisibility(View.GONE);
                    tvSign1.setVisibility(View.GONE);
                    break;
                case 3:
                    vpMaintenance.setCurrentItem(3);
                    tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                    tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvMaintenance1.setVisibility(View.GONE);
                    tvPending1.setVisibility(View.GONE);
                    tvMissed1.setVisibility(View.GONE);
                    tvHistory1.setVisibility(View.VISIBLE);
                    tvSign1.setVisibility(View.GONE);
                    break;
                case 4:
                    vpMaintenance.setCurrentItem(4);
                    tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                    tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                    tvMaintenance1.setVisibility(View.GONE);
                    tvPending1.setVisibility(View.GONE);
                    tvMissed1.setVisibility(View.GONE);
                    tvHistory1.setVisibility(View.GONE);
                    tvSign1.setVisibility(View.VISIBLE);
                    break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


    @OnClick({R.id.ivBack, R.id.ivScanning, R.id.rlMaintenance, R.id.rlPending, R.id.rlMissed, R.id.rlHistory, R.id.rlSign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivScanning:
                //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
                    if (!permission) {
                        ActivityCompat.requestPermissions(MaintenanceActivity.this,
                                new String[]{Manifest.permission.CAMERA}, 10);
                    } else {
                        Intent intent = new Intent(MaintenanceActivity.this, CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_DTWB);
//                    startActivityForResult(intent,0);
                        startActivity(intent);
                    }
                } else {
                    if (PermissionUtil.cameraIsCanUse()) {
                        Intent intent = new Intent(MaintenanceActivity.this, CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_DTWB);
//                    startActivityForResult(intent,0);
                        startActivity(intent);
                    } else {
                        getPermission("缺少相机权限");
                    }
                }
                break;

            case R.id.rlMaintenance:
                vpMaintenance.setCurrentItem(0);
                tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvMaintenance1.setVisibility(View.VISIBLE);
                tvPending1.setVisibility(View.GONE);
                tvMissed1.setVisibility(View.GONE);
                tvHistory1.setVisibility(View.GONE);
                tvSign1.setVisibility(View.GONE);
                break;
            case R.id.rlPending:
                vpMaintenance.setCurrentItem(1);
                tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvMaintenance1.setVisibility(View.GONE);
                tvPending1.setVisibility(View.VISIBLE);
                tvMissed1.setVisibility(View.GONE);
                tvHistory1.setVisibility(View.GONE);
                tvSign1.setVisibility(View.GONE);
                break;
            case R.id.rlMissed:
                vpMaintenance.setCurrentItem(2);
                tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvMaintenance1.setVisibility(View.GONE);
                tvPending1.setVisibility(View.GONE);
                tvMissed1.setVisibility(View.VISIBLE);
                tvHistory1.setVisibility(View.GONE);
                tvSign1.setVisibility(View.GONE);
                break;
            case R.id.rlHistory:
                vpMaintenance.setCurrentItem(3);
                tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvMaintenance1.setVisibility(View.GONE);
                tvPending1.setVisibility(View.GONE);
                tvMissed1.setVisibility(View.GONE);
                tvHistory1.setVisibility(View.VISIBLE);
                tvSign1.setVisibility(View.GONE);
                break;
            case R.id.rlSign:
                vpMaintenance.setCurrentItem(4);
                tvMaintenance.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvPending.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvMissed.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvHistory.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.black2));
                tvSign.setTextColor(MaintenanceActivity.this.getResources().getColor(R.color.actionbar));
                tvMaintenance1.setVisibility(View.GONE);
                tvPending1.setVisibility(View.GONE);
                tvMissed1.setVisibility(View.GONE);
                tvHistory1.setVisibility(View.GONE);
                tvSign1.setVisibility(View.VISIBLE);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MaintenanceActivity.this, CaptureActivity.class);
                intent.putExtra("source", CaptureActivity.SCAN_DTWB);
//                startActivityForResult(intent,0);
                startActivity(intent);
            } else {
                getPermission("请授权APP访问摄像头权限！");
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

//    ImageView main_zxing1;
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //第一层switch
//        switch (requestCode) {
//            case PHOTO_FROM_GALLERY:
//                //第二层switch
//                switch (resultCode) {
//                    case RESULT_OK:
//                        if (data != null) {
//                            Uri uri = data.getData();
//                            main_zxing1.setImageURI(uri);
//                        }
//                        break;
//                    case RESULT_CANCELED:
//                        break;
//                }
//                break;
//
//        }
//    }
//    private static final int PHOTO_FROM_GALLERY = 1;
}

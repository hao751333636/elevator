package com.sinodom.elevator.activity.elevator.nfc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.BridgeWebViewActivity;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.zxing.activity.CaptureActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设备绑定列表
 */
public class EquipmentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.rlPassword, R.id.rlContact, R.id.rlVideo, R.id.rlNfcSetting, R.id.rlMonitor, R.id.rlNfcScan})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            //标签绑定
            case R.id.rlPassword:
                intent = new Intent(context, BridgeWebViewActivity.class);
                intent.putExtra("url", BuildConfig.SERVER + "WebApp/NFC/Index?userid=" + manager.getSession().getUserID());
                intent.putExtra("source", CaptureActivity.SCAN_BQBD);//不为空打开扫码
                intent.putExtra("title", "标牌绑定");
                startActivity(intent);
                break;
            //标签初始化
            case R.id.rlNfcSetting:
                intent = new Intent(context, NfcSettingActivity.class);
                startActivity(intent);
                break;
            //读取标签
            case R.id.rlNfcScan:
                intent = new Intent(context, NfcReadActivity.class);
                startActivity(intent);
                break;
            //四维监控绑定
            case R.id.rlMonitor:
                intent = new Intent(context, MonitorBindActivity.class);
                startActivity(intent);
                break;
            //设备绑定
            case R.id.rlContact:
                //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
                    if (!permission) {
                        ActivityCompat.requestPermissions(EquipmentActivity.this,
                                new String[]{Manifest.permission.CAMERA}, 200);
                    } else {
                        intent = new Intent(EquipmentActivity.this, CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_SBBD);
                        startActivity(intent);
                    }
                } else {
                    if (PermissionUtil.cameraIsCanUse()) {
                        intent = new Intent(EquipmentActivity.this, CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_SBBD);
                        startActivity(intent);
                    } else {
                        getPermission("缺少相机权限");
                    }
                }
                break;
            //视频主机绑定
            case R.id.rlVideo:
                //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
                    if (!permission) {
                        ActivityCompat.requestPermissions(EquipmentActivity.this,
                                new String[]{Manifest.permission.CAMERA}, 100);
                    } else {
                        intent = new Intent(EquipmentActivity.this, CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_SPZJ);
                        startActivity(intent);
                    }
                } else {
                    if (PermissionUtil.cameraIsCanUse()) {
                        intent = new Intent(EquipmentActivity.this, CaptureActivity.class);
                        intent.putExtra("source", CaptureActivity.SCAN_SPZJ);
                        startActivity(intent);
                    } else {
                        getPermission("缺少相机权限");
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(EquipmentActivity.this, CaptureActivity.class);
                intent.putExtra("source", CaptureActivity.SCAN_SBBD);
                startActivityForResult(intent, Constants.Code.GO_SCORE);
            } else {
                getPermission("请授权APP访问摄像头权限！");
            }
            return;
        }
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(EquipmentActivity.this, CaptureActivity.class);
                intent.putExtra("source", CaptureActivity.SCAN_SPZJ);
                startActivityForResult(intent, Constants.Code.GO_SCORE);
            } else {
                getPermission("请授权APP访问摄像头权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

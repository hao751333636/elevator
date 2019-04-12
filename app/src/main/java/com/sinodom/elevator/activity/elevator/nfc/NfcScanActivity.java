package com.sinodom.elevator.activity.elevator.nfc;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.util.HexUtils;
import com.sinodom.elevator.util.ImageUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.PictureUtil;
import com.sinodom.elevator.util.TextUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * NFC扫描
 */
public class NfcScanActivity extends BaseNfcActivity {

    @BindView(R.id.tvNfcType)
    TextView tvNfcType;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.ivScanning)
    ImageView ivScanning;
    @BindView(R.id.tvDevice)
    TextView tvDevice;
    private String mTypeName;
    private String mTvContent;
    private String mPhotoPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_scan);
        ButterKnife.bind(this);
        mTypeName = getIntent().getStringExtra("typeName");
        mTvContent = getIntent().getStringExtra("typeContent");
        tvNfcType.setText(mTypeName);
        tvContent.setText(mTvContent);

    }

    @Override
    public void cry(String type) {
        if (type.equals("phone")) {
            String str = readNdef();
            if (!TextUtil.isEmpty(str)) {
                Intent intent = new Intent();
                intent.putExtra("type", "nfc");
                intent.putExtra("code", str);
                intent.putExtra("uid", HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
                setResult(Constants.Code.SCORE_OK, intent);
                finish();
            } else {
                showToast("扫描失败！");
            }
        } else if (type.equals("deke")) {
            if (!TextUtil.isEmpty(content)) {
                Intent intent = new Intent();
                intent.putExtra("type", "nfc");
                intent.putExtra("code", content);
                intent.putExtra("uid", uid);
                setResult(Constants.Code.SCORE_OK, intent);
                finish();
            } else {
                showToast("扫描失败！");
            }
        }
    }

    @Override
    public void onDevice(String str) {
        super.onDevice(str);
        tvDevice.setText(str);
    }

    @OnClick({R.id.ivBack, R.id.ivScanning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivScanning:
                Pictures();
                break;
        }
    }

    //调用相机拍照
    public void Pictures() {
        Logger.d("拍照");
        try {
            //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean permission = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
                boolean permission1 = selfPermissionGranted(Manifest.permission.CAMERA);
                if (!permission || !permission1) {
                    ActivityCompat.requestPermissions(NfcScanActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
                } else {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
                    mPhotoPath = "";
                    mPhotoPath = PictureUtil.getPicturesDir().getAbsolutePath() + "/" + PictureUtil.getPhotoFileName();
                    File mPhotoFile = new File(mPhotoPath);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, 1);//跳转界面传回拍照所得数据
                }
            } else {
                if (PermissionUtil.cameraIsCanUse()) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
                    mPhotoPath = "";
                    mPhotoPath = PictureUtil.getPicturesDir().getAbsolutePath() + "/" + PictureUtil.getPhotoFileName();
                    File mPhotoFile = new File(mPhotoPath);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, 1);//跳转界面传回拍照所得数据
                } else {
                    getPermission("缺少相机权限");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
                    mPhotoPath = "";
                    mPhotoPath = PictureUtil.getPicturesDir().getAbsolutePath() + "/" + PictureUtil.getPhotoFileName();
                    File mPhotoFile = new File(mPhotoPath);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, 1);//跳转界面传回拍照所得数据
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                getPermission("请授权APP访问摄像头和读写文件权限！");
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //拍照返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                //获取bitmap
                Bitmap bitmap = ImageUtil.getBitmap(mPhotoPath);
                //解决三星图片旋转问题
                Bitmap bitmapRotate = ImageUtil.rotateToDegrees(bitmap, ImageUtil.readPictureDegree(mPhotoPath));
                ImageUtil.savePicToSdcard(bitmapRotate, mPhotoPath);
                Intent intent = new Intent();
                intent.putExtra("type", "photo");
                intent.putExtra("path", mPhotoPath);
                setResult(Constants.Code.SCORE_OK, intent);
                finish();
            } else if (requestCode == 1 && resultCode != RESULT_OK) {
                Logger.d("取消拍照返回");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
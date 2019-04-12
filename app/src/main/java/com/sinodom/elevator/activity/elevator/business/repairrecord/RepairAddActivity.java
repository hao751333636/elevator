package com.sinodom.elevator.activity.elevator.business.repairrecord;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.repairrecord.RepairAddBean;
import com.sinodom.elevator.bean.repairrecord.RepairBean;
import com.sinodom.elevator.util.ImageUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.PictureUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.util.glide.GlideApp;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 维修新建
 */
public class RepairAddActivity extends BaseActivity {

    @BindView(R.id.tvLiftNum)
    TextView tvLiftNum;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvState)
    TextView tvState;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvTime2)
    TextView tvTime2;
    @BindView(R.id.llTime)
    LinearLayout llTime;
    @BindView(R.id.etDescribed)
    EditText etDescribed;
    @BindView(R.id.ivFront)
    ImageView ivFront;
    @BindView(R.id.ivAfter)
    ImageView ivAfter;
    private final static int FRONTCODE = 1;
    private final static int AFTWRCODE = 2;
    private String mPhotoPath;
    private String mPhotoAfterPath = "";
    private String mPhotoFrontPath = "";
    private int mType = 0;
    private RepairBean mBean;
    private RepairAddBean mAddBean = new RepairAddBean();
    public MyLocationListener mMyLocationListener;
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_add);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mBean = (RepairBean) getIntent().getSerializableExtra("bean");
        //百度定位
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        //百度定位-end
        tvLiftNum.setText(mBean.getLiftNum() + "");
        tvAddress.setText(mBean.getRepairPosition());
//        tvState.setText(mBean.getIs_Repair() == 0 ? "从未维修" : "正在维修");
        if (TextUtil.isEmpty(mBean.getCreateTime())) {
            llTime.setVisibility(View.VISIBLE);
            tvTime.setText("尚未维修");
        } else {
            llTime.setVisibility(View.VISIBLE);
            tvTime.setText(mBean.getCreateTime());
        }
        etDescribed.setText(mBean.getRemark());
        if (!TextUtil.isEmpty(mBean.getBeforePhoto())) {
            //加载图片
            String str1 = mBean.getBeforePhoto().substring(2);
            Logger.d("url截取= " + str1);
            String myUrl1 = BuildConfig.SERVER + str1;
            GlideApp.with(context).load(myUrl1)
                    .thumbnail(0.1f)
//                .error(R.mipmap.ic_failure)
//                .placeholder(R.mipmap.ic_load)
                    .into(ivFront);
        }
        if (!TextUtil.isEmpty(mBean.getAfterPhoto())) {
            String str = mBean.getAfterPhoto().substring(2);
            Logger.d("url截取= " + str);
            String myUrl = BuildConfig.SERVER + str;
            GlideApp.with(context).load(myUrl)
                    .thumbnail(0.1f)
//                .error(R.mipmap.ic_failure)
//                .placeholder(R.mipmap.ic_load)
                    .into(ivAfter);
        }
    }

    @OnClick({R.id.ivBack, R.id.ivFront, R.id.ivAfter, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivFront:
                mType = 1;
                Pictures(FRONTCODE);
                break;
            case R.id.ivAfter:
                mType = 2;
                Pictures(AFTWRCODE);
                break;
            case R.id.tvSubmit:
                location();
//                saveCheckDetails();
                break;
        }
    }

    public void location() {
        boolean permission = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission == true && permission1 == true) {
            startLocationClient();
        } else {
            ActivityCompat.requestPermissions(RepairAddActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }
    }

    private void startLocationClient() {
        showLoading("定位中...");
        mLocationClient.start();
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        //设置定位模式
        //Hight_Accuracy:高精度定位模式下，会同时使用GPS、Wifi和基站定位，返回的是当前条件下精度最好的定位结果,
        //Battery_Saving:低功耗定位模式下，仅使用网络定位即Wifi和基站定位，返回的是当前条件下精度最好的网络定位结果,
        //Device_Sensors:仅用设备定位模式下，只使用用户的GPS进行定位。这个模式下，由于GPS芯片锁定需要时间，首次定位速度会需要一定的时间,
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //返回的定位结果是百度经纬度，gcj02(国测局加密经纬度坐标),bd09ll(百度加密经纬度坐标),bd09(百度加密墨卡托坐标)
        option.setCoorType("bd09ll");
        //设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
        option.setScanSpan(0);
        option.setIsNeedAddress(true);//反地理编码
        mLocationClient.setLocOption(option);
    }

    /**
     * 实现定位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            hideLoading();
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                Logger.d("latitude=" + location.getLatitude() + "|longitude=" + location.getLongitude() + "|address=" + location.getAddrStr());
                mAddBean.setUserId(manager.getSession().getUserID());
                mAddBean.setLiftId(mBean.getLiftId());
                mAddBean.setRepairPosition(location.getLongitude() + "," + location.getLatitude());
                mAddBean.setRemark(etDescribed.getText().toString().trim());
                mAddBean.setID(mBean.getId());
//                if (!TextUtil.isEmpty(mPhotoAfterPath) &&
//                        TextUtil.isEmpty(mPhotoFrontPath) && TextUtil.isEmpty(mBean.getBeforePhoto())) {
//                    showToast("缺少维修前照片！");
//                    return;
//                }
                if (!TextUtil.isEmpty(mPhotoFrontPath)) {
                    mAddBean.setBeforePhoto(PictureUtil.bitmapToString(mPhotoFrontPath));
                }
                if (!TextUtil.isEmpty(mPhotoAfterPath)) {
                    mAddBean.setAfterPhoto(PictureUtil.bitmapToString(mPhotoAfterPath));
                }
                saveCheckDetails();
            } else {
                if (location.getLocType() == 167) {
                    Toast.makeText(context, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。", Toast.LENGTH_LONG).show();
                }
                Logger.d("百度定位失败");
                hideLoading();
            }
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setFinish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private void setFinish() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定要退出么？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                        finish();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void saveCheckDetails() {
        showLoading("加载中...");
        Call<ResponBean> call = server.getService().saveRepairNew(mAddBean);
        Logger.d(new Gson().toJson(mAddBean).toString());
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    Gson gson = new Gson();
                    Logger.d("" + gson.toJson(response.body()));
                    if (response.body().isSuccess()) {
//                    ("提交成功！");
                        showToast("" + response.body().getMessage());
                        finish();
                    } else {
                        showToast("" + response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("提交失败");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }


    //调用相机拍照
    public void Pictures(int requestCode) {
        Logger.d("拍照");
        try {
            //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean permission = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
                boolean permission1 = selfPermissionGranted(Manifest.permission.CAMERA);
                if (!permission || !permission1) {
                    ActivityCompat.requestPermissions(RepairAddActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
                } else {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
                    mPhotoPath = "";
                    mPhotoPath = PictureUtil.getPicturesDir().getAbsolutePath() + "/" + PictureUtil.getPhotoFileName();
                    File mPhotoFile = new File(mPhotoPath);
//                    if (!mPhotoFile.exists()) {
//                        mPhotoFile.createNewFile();//创建新文件
//                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, requestCode);//跳转界面传回拍照所得数据
                }
            } else {
                if (PermissionUtil.cameraIsCanUse()) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
                    mPhotoPath = "";
                    mPhotoPath = PictureUtil.getPicturesDir().getAbsolutePath() + "/" + PictureUtil.getPhotoFileName();
                    File mPhotoFile = new File(mPhotoPath);
//                    if (!mPhotoFile.exists()) {
//                        mPhotoFile.createNewFile();//创建新文件
//                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, requestCode);//跳转界面传回拍照所得数据
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
//                    if (!mPhotoFile.exists()) {
//                        mPhotoFile.createNewFile();//创建新文件
//                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, mType);//跳转界面传回拍照所得数据
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                getPermission("请授权APP访问摄像头和读写文件权限！");
            }

            return;
        }
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationClient();
            } else {
                getPermission("请授权APP获取位置信息权限！");
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
            if ((requestCode == FRONTCODE || requestCode == AFTWRCODE) && resultCode == RESULT_OK) {
                //获取bitmap
                Bitmap bitmap = ImageUtil.getBitmap(mPhotoPath);
                //解决三星图片旋转问题
                Logger.e(ImageUtil.readPictureDegree(mPhotoPath)+"");
                Bitmap bitmapRotate = ImageUtil.rotateToDegrees(bitmap, ImageUtil.readPictureDegree(mPhotoPath));
                ImageUtil.savePicToSdcard(bitmapRotate, mPhotoPath);
                switch (requestCode) {
                    case FRONTCODE:
                        //加载图片
                        GlideApp.with(context).load(mPhotoPath)
                                .thumbnail(0.1f)
                                .error(R.mipmap.ic_failure)
                                .placeholder(R.mipmap.ic_load)
                                .into(ivFront);
                        mPhotoFrontPath = mPhotoPath;
                        break;
                    case AFTWRCODE:
                        //加载图片
                        GlideApp.with(context).load(mPhotoPath)
                                .thumbnail(0.1f)
                                .error(R.mipmap.ic_failure)
                                .placeholder(R.mipmap.ic_load)
                                .into(ivAfter);
                        mPhotoAfterPath = mPhotoPath;
                        break;
                }
            } else {
                Logger.d("取消拍照返回");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast("拍照失败请重试！");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }
}

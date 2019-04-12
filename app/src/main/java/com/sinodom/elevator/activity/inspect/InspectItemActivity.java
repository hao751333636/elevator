package com.sinodom.elevator.activity.inspect;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.inspect.video.VideoCameraActivity;
import com.sinodom.elevator.activity.inspect.video.VideoPlayerActivity;
import com.sinodom.elevator.adapter.elist.InspectItemAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.inspect.InspectBean;
import com.sinodom.elevator.bean.inspect.InspectItemBean;
import com.sinodom.elevator.db.InspectItem;
import com.sinodom.elevator.single.ApiService;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.ImageUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.SoftKeyBoardListener;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.view.NoScrollListView;
import com.sinodom.elevator.view.SeeImageView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 检验项
 */
public class InspectItemActivity extends BaseActivity {

    @BindView(R.id.tvEleInfoNum)
    TextView tvEleInfoNum;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvRegistrationCode)
    TextView tvRegistrationCode;
    @BindView(R.id.tvDeptName)
    TextView tvDeptName;
    @BindView(R.id.tvWorkForm)
    TextView tvWorkForm;
    @BindView(R.id.tvSign)
    TextView tvSign;
    @BindView(R.id.listView)
    NoScrollListView mListView;
    @BindView(R.id.rlTitle1)
    RelativeLayout rlTitle1;
    @BindView(R.id.llTitle1)
    LinearLayout llTitle1;
    @BindView(R.id.llTitle2)
    LinearLayout llTitle2;
    @BindView(R.id.llTitle3)
    LinearLayout llTitle3;
    @BindView(R.id.llDeptName)
    LinearLayout llDeptName;
    @BindView(R.id.llWorkForm)
    LinearLayout llWorkForm;
    private InspectItemAdapter mAdapter;
    private InspectBean mBean;
    private InspectItemBean mInspectItemBean;
    private int position;
    private int mPosition;
    private int mWhich;
    private int mWorkId;
    private DynamicReceiver dynamicReceiver;
    private double longitude;
    private double latitude;
    private AlertDialog.Builder alertDialog;
    //检验项选择-弹出显示项
    private String[] jItems;
    private String[] wItems;
    public MyLocationListener mMyLocationListener;
    public LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_item);
        ButterKnife.bind(this);
        mBean = (InspectBean) getIntent().getSerializableExtra("bean");
        position = getIntent().getIntExtra("position", 0);
        mWhich = getIntent().getIntExtra("which", 0);
        mWorkId = getIntent().getIntExtra("workId", 0);
        latitude = getIntent().getDoubleExtra("latitude", 0);
        longitude = getIntent().getDoubleExtra("longitude", 0);
        tvEleInfoNum.setText(mBean.getLiftNum());
        tvAddress.setText(mBean.getInstallationAddress());
        tvRegistrationCode.setText(mBean.getCertificateNum());
        alertDialog = new AlertDialog.Builder(context);
        //百度定位
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        //百度定位-end
        //实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction("fragment1");
        dynamicReceiver = new DynamicReceiver();
        //注册广播接收
        registerReceiver(dynamicReceiver, filter);

        SoftKeyBoardListener.setListener(InspectItemActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                rlTitle1.setVisibility(View.GONE);
                llTitle1.setVisibility(View.GONE);
                llTitle2.setVisibility(View.GONE);
                llTitle3.setVisibility(View.GONE);
                llDeptName.setVisibility(View.GONE);
                llWorkForm.setVisibility(View.GONE);
//                Toast.makeText(InspectItemActivity.this, "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void keyBoardHide(int height) {
                rlTitle1.setVisibility(View.VISIBLE);
                llTitle1.setVisibility(View.VISIBLE);
                llTitle2.setVisibility(View.VISIBLE);
                llTitle3.setVisibility(View.VISIBLE);
                llDeptName.setVisibility(View.VISIBLE);
                llWorkForm.setVisibility(View.VISIBLE);
//                Toast.makeText(InspectItemActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter = new InspectItemAdapter(context);
        mListView.setAdapter(mAdapter);
        mAdapter.setElevatorListNewAdapter(new InspectItemAdapter.MyInterface() {

            @Override
            public void clickNetwork(int position) {
                //查看网络图片或视频
                mPosition = position;
                getClickNetwork();
            }

            @Override
            public void clickLocal(int position) {
                //查看本地照片或视频
                mPosition = position;
                getClickLocal();
            }

            @Override
            public void clickOffLine(int position) {
                //查看离线照片或视频
                mPosition = position;
                getClickOffLine();
            }

            @Override
            public void clickCamera(int position) {
                mPosition = position;
                //拍摄
                //判断是照相还是录像
                if (mInspectItemBean.getInspectStep().get(mPosition).isIsPhoto()) {
                    //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //进入照相
                        boolean permission = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
                        boolean permission1 = selfPermissionGranted(Manifest.permission.CAMERA);
                        if (!permission || !permission1) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
                        } else {
                            getPhoto();
                        }
                    } else {
                        if (PermissionUtil.cameraIsCanUse()) {
                            getPhoto();
                        } else {
                            getPermission("缺少相机权限");
                        }
                    }
                } else {
                    //进入录像
                    Logger.d("开始录制视频");
                    // 设置拍摄视频缓存路径
//                    File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
//                    if (DeviceUtils.isZte()) {
//                        if (dcim.exists()) {
//                            VCamera.setVideoCachePath(dcim + "/Elevator/");
//                        } else {
//                            VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "/sdcard-ext/") + "/Elevator/");
//                        }
//                    } else {
//                        VCamera.setVideoCachePath(dcim + "/Elevator/");
//                    }
//                    // 初始化拍摄，遇到问题可选择开启此标记，以方便生成日志
//                    VCamera.setDebugMode(true);
//                    VCamera.initialize(context);

                    //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        boolean permission1 = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
                        boolean permission2 = selfPermissionGranted(Manifest.permission.CAMERA);
                        boolean permission3 = selfPermissionGranted(Manifest.permission.RECORD_AUDIO);
                        if (!permission1 || !permission2 || !permission3) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, 200);
                        } else {
                            getVideo();
                        }
                    } else {
                        if (PermissionUtil.cameraIsCanUse()) {
                            getVideo();
                        } else {
                            getPermission("缺少相机权限");
                        }
                    }
                }
            }

            @Override
            public void clickSubmit(int position) {
                mPosition = position;
                //确认
                boolean boo = false;
                if (mInspectItemBean.getInspectStep().get(mPosition).isIsPhoto()) {
                    if (!TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getLocalPhotoUrl())
                            || !TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getOffLinePhotoUrl())
                            || !TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getPhotoUrl())) {
                        boo = true;
                    }
                } else {
                    if (!TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getLocalVideoPath())
                            || !TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getOffLineVideoPath())
                            || !TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getVideoPath())) {
                        boo = true;
                    }
                }
//                boolean boo1 = true;
//                if (mInspectItemBean.getInspectStep().get(mPosition).getInspectTemplateAttributeEntityList() != null && mInspectItemBean.getInspectStep().get(mPosition).getInspectTemplateAttributeEntityList().size() > 0) {
//                    for (InspectItemBean.InspectStepBean.InspectTemplateAttributeEntityListBean bean : mInspectItemBean.getInspectStep().get(mPosition).getInspectTemplateAttributeEntityList()) {
//                        if (TextUtil.isEmpty(bean.getInputValue())) {
//                            boo1 = false;
//                        }
//                    }
//                }
                if (boo) {
//                    if (!boo1) {
//                        showToast("请填写设备信息！");
//                        return;
//                    }
                    boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
                    boolean permission2 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
                    if (permission1 == true && permission2 == true) {
                        startLocationClient();
                    } else {
                        ActivityCompat.requestPermissions(InspectItemActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 300);
                    }
                } else {
                    showToast("请先拍摄检测内容");
                }
            }
        });
        loadData();
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
            if (null != location && location.getLocType() != BDLocation.TypeServerError
                    && !TextUtil.isEmpty(location.getAddrStr()) && location.getLatitude() != 4.9E-324 && location.getLongitude() != 4.9E-324
                    && location.getLatitude() != 0 && location.getLongitude() != 0) {
                Logger.d("latitude=" + location.getLatitude() + "|longitude=" + location.getLongitude() + "|address=" + location.getAddrStr());
                try {
                    mInspectItemBean.getInspectStep().get(mPosition).setOffLinePhotoUrl(mInspectItemBean.getInspectStep().get(mPosition).getLocalPhotoUrl());
                    mInspectItemBean.getInspectStep().get(mPosition).setOffLineVideoPath(mInspectItemBean.getInspectStep().get(mPosition).getLocalVideoPath());
                    mInspectItemBean.getInspectStep().get(mPosition).setLocalVideoPath("");
                    mInspectItemBean.getInspectStep().get(mPosition).setLocalPhotoUrl("");
                    mAdapter.notifyDataSetChanged();

                    InspectItem inspectItem = new InspectItem();
                    if (TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getLocalPhotoUrl())
                            && TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getOffLinePhotoUrl())
                            && !TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getPhotoUrl())) {
                        inspectItem.setPhotoUrl(mInspectItemBean.getInspectStep().get(mPosition).getPhotoUrl());
                    }else{
                        inspectItem.setPhotoUrl(mInspectItemBean.getInspectStep().get(mPosition).getOffLinePhotoUrl());
                    }
                    if (TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getLocalVideoPath())
                            && TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getOffLineVideoPath())
                            && !TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getVideoPath())) {
                        inspectItem.setVideoPath(mInspectItemBean.getInspectStep().get(mPosition).getVideoPath());
                    }else{
                        inspectItem.setVideoPath(mInspectItemBean.getInspectStep().get(mPosition).getOffLineVideoPath());
                    }
                    inspectItem.setIsPhoto(mInspectItemBean.getInspectStep().get(mPosition).isIsPhoto());
                    inspectItem.setInspectId(mInspectItemBean.getInspect().getID());
                    inspectItem.setStepId(mInspectItemBean.getInspectStep().get(mPosition).getStepId());
                    inspectItem.setStepName(mInspectItemBean.getInspectStep().get(mPosition).getStepName());
                    inspectItem.setRemark(mInspectItemBean.getInspectStep().get(mPosition).getRemark());
                    inspectItem.setTypeID(mInspectItemBean.getInspectStep().get(mPosition).getTypeID());
                    inspectItem.setTypeName(mInspectItemBean.getInspectStep().get(mPosition).getTypeName());
                    inspectItem.setMapX("" + location.getLatitude());
                    inspectItem.setMapY("" + location.getLongitude());
                    inspectItem.setUserId(manager.getSession().getUserID());
                    inspectItem.setUserName(manager.getSession().getUserName());
                    inspectItem.setCreateTime(DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
                    if (mInspectItemBean.getInspectStep().get(mPosition).getInspectTemplateAttributeEntityList() != null) {
                        JSONArray jsonObject = new JSONArray(new Gson().toJson(mInspectItemBean.getInspectStep().get(mPosition).getInspectTemplateAttributeEntityList()));
                        inspectItem.setTemplateAttributeJson(jsonObject.toString());
                    } else {
                        inspectItem.setTemplateAttributeJson("[]");
                    }
                    manager.addInspectItem(inspectItem);
                    showToast("已经离线缓存");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                if (location.getLocType() == 167) {
                    Toast.makeText(context, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。", Toast.LENGTH_LONG).show();
                } else if (location.getLocType() == 63) {
                    Toast.makeText(context, "网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "定位失败，无法获取有效定位依据", Toast.LENGTH_LONG).show();
                }
                Logger.d("百度定位失败");
            }
            mLocationClient.stop();
        }
    }

    public static final int REQUEST_CODE_SELECT = 100;

    //开始拍照
    private void getPhoto() {
        Intent intent = new Intent(context, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //开始录像
    private void getVideo() {
        // 录制
//        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
//                .doH264Compress(new AutoVBRMode()
////                        .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
//                )
//                .setMediaBitrateConfig(new AutoVBRMode(30)
////                        .setVelocity(BaseMediaBitrateConfig.Velocity.ULTRAFAST)
//                )
//                .smallVideoWidth(480)
//                .smallVideoHeight(360)
//                .recordTimeMax(5 * 60 * 1000)
//                .maxFrameRate(20)
//                .captureThumbnailsTime(1)
//                .recordTimeMin(10000)
//                .build();
//        MediaRecorderActivity.goSmallVideoRecorder(InspectItemActivity.this, SendSmallVideoActivity.class.getName(), config);

        Intent intent = new Intent(context, VideoCameraActivity.class);
        startActivityForResult(intent, Constants.Code.GO_RESCUE);
    }

    //拍照成功刷新数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String str = DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss.SSS");
                //获取bitmap
                Bitmap bitmap = ImageUtil.getBitmap(images.get(0).path);
                //解决三星图片旋转问题
                Bitmap bitmapRotate = ImageUtil.rotateToDegrees(bitmap, ImageUtil.readPictureDegree(images.get(0).path));
                //加时间水印
                Bitmap textBitmap = ImageUtil.drawTextToRightBottom(context,
                        bitmapRotate, str, bitmapRotate.getWidth() / 25, Color.RED, bitmapRotate.getWidth() / 25, bitmapRotate.getWidth() / 25);
                ImageUtil.savePicToSdcard(textBitmap, images.get(0).path);
                mInspectItemBean.getInspectStep().get(mPosition).setLocalPhotoUrl(images.get(0).path);
                mAdapter.notifyDataSetChanged();
            } else {
                showToast("拍照失败！");
            }
        }
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            loadData();
        }
        //录像成功刷新数据
        if (requestCode == Constants.Code.GO_RESCUE && resultCode == Constants.Code.RESCUE_OK) {
            String videoUri = data.getStringExtra("url");
            mInspectItemBean.getInspectStep().get(mPosition).setLocalVideoPath(videoUri);
            mAdapter.notifyDataSetChanged();
        }
    }

    //录像成功刷新数据
    class DynamicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String videoUri = intent.getStringExtra("uri");
            mInspectItemBean.getInspectStep().get(mPosition).setLocalVideoPath(videoUri);
            mAdapter.notifyDataSetChanged();
        }
    }

    //查看网络图片或视频
    private void getClickNetwork() {
        if (mInspectItemBean.getInspectStep().get(mPosition).isIsPhoto()) {
            if (!TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getPhotoUrl())) {
                SeeImageView seeImageView = new SeeImageView(context, R.style.dialog, mInspectItemBean.getInspectStep().get(mPosition).getPhotoUrl(), false);
                seeImageView.show();
            }
        } else {
            if (!TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getVideoPath())) {
                String str = mInspectItemBean.getInspectStep().get(mPosition).getVideoPath().substring(2);
                String myUrl = BuildConfig.SERVER + str;
                startActivity(new Intent(context, VideoPlayerActivity.class).putExtra("path", myUrl));
            }
        }
    }

    //查看本地照片或视频
    private void getClickLocal() {
        if (mInspectItemBean.getInspectStep().get(mPosition).isIsPhoto()) {
            if (!TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getLocalPhotoUrl())) {
                SeeImageView seeImageView = new SeeImageView(context, R.style.dialog, mInspectItemBean.getInspectStep().get(mPosition).getLocalPhotoUrl(), true);
                seeImageView.show();
            }
        } else {
            if (!TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getLocalVideoPath())) {
                startActivity(new Intent(context, VideoPlayerActivity.class).putExtra("path", mInspectItemBean.getInspectStep().get(mPosition).getLocalVideoPath()));
            }
        }
    }

    //查看离线照片或视频
    private void getClickOffLine() {
        if (mInspectItemBean.getInspectStep().get(mPosition).isIsPhoto()) {
            if (!TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getOffLinePhotoUrl())) {
                SeeImageView seeImageView = new SeeImageView(context, R.style.dialog, mInspectItemBean.getInspectStep().get(mPosition).getOffLinePhotoUrl(), true);
                seeImageView.show();
            }
        } else {
            if (!TextUtil.isEmpty(mInspectItemBean.getInspectStep().get(mPosition).getOffLineVideoPath())) {
                startActivity(new Intent(context, VideoPlayerActivity.class).putExtra("path", mInspectItemBean.getInspectStep().get(mPosition).getOffLineVideoPath()));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            } else {
                getPermission("请授权APP读取SD卡权限！");
            }
            return;
        }
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getVideo();
            } else {
                getPermission("请授权APP读取SD卡权限！");
            }
            return;
        }
        if (requestCode == 300) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationClient();
            } else {
                getPermission("请授权APP获取位置信息权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void loadData() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LiftNum", URLEncoder.encode(mBean.getLiftNum()));
        map.put("TypeCode", mBean.getInspectType().get(position).getTypeCode());
        if (mBean.getInspectType().get(position).getDept() == null || mBean.getInspectType().get(position).getDept().size() == 0) {
            map.put("InspectDeptId", 0);
        } else {
            map.put("InspectDeptId", mBean.getInspectType().get(position).getDept().get(mWhich).getDeptId());
        }
        map.put("TypeId", "" + mBean.getInspectType().get(position).getID());
        map.put("UserId", userId);
        map.put("MapX", "" + latitude);
        map.put("MapY", "" + longitude);
        map.put("WorkForm", "" + mWorkId);
        Call<ResponBean> call = server.getService().getInspectByLiftNumNew(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(gson.toJson(response.body()));
                        mInspectItemBean = gson.fromJson(response.body().getData(), InspectItemBean.class);
                        tvDeptName.setText(mInspectItemBean.getInspect().getDeptName());
                        tvWorkForm.setText(mInspectItemBean.getInspect().getName());
                        if (!TextUtil.isEmpty(mInspectItemBean.getInspect().getSign())) {
                            tvSign.setText("已签认");
                        } else {
                            tvSign.setText("未签认");
                        }
                        if (mInspectItemBean != null) {
                            showToast("加载成功！");
                            for (InspectItemBean.InspectStepBean bean : mInspectItemBean.getInspectStep()) {
                                InspectItem inspectItem = manager.getInspectItem(mInspectItemBean.getInspect().getID(), bean.getStepId());
                                if (inspectItem != null && bean.getStepId() == inspectItem.getStepId()) {
                                    if (bean.isIsPhoto()) {
                                        bean.setOffLinePhotoUrl(inspectItem.getPhotoUrl());
                                    } else {
                                        bean.setOffLineVideoPath(inspectItem.getVideoPath());
                                    }
                                    bean.setRemark(inspectItem.getRemark());
                                    List<InspectItemBean.InspectStepBean.InspectTemplateAttributeEntityListBean> list =
                                            gson.fromJson(inspectItem.getTemplateAttributeJson(),
                                                    new TypeToken<List<InspectItemBean.InspectStepBean.InspectTemplateAttributeEntityListBean>>() {
                                                    }.getType());
                                    bean.setInspectTemplateAttributeEntityList(list);
                                }
                            }
                            tvTime.setText(mInspectItemBean.getInspect().getCreateTime().replace("T", " ").substring(0, 16));
                            mAdapter.setData(mInspectItemBean.getInspectStep());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            showToast("暂无此电梯信息");
                        }


                    } else {
                        showToast("加载失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("加载失败！");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    @OnClick({R.id.ivBack, R.id.tvYes, R.id.tvSign, R.id.tvCopy, R.id.tvCommit, R.id.llDeptName, R.id.llWorkForm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                setFinish();
                break;
            case R.id.tvYes:
                setYes();
                break;
            case R.id.tvCommit:
                List<InspectItem> inspectItem = manager.getInspectItem(mInspectItemBean.getInspect().getID());
                if (inspectItem != null && inspectItem.size() > 0) {
                    setCommit(inspectItem);
                } else {
                    showToast("离线传输数据为空！");
                }
                break;
            case R.id.tvSign:
                Intent intent = new Intent(InspectItemActivity.this, InspectSignActivity.class);
                intent.putExtra("id", mInspectItemBean.getInspect().getID());
                intent.putExtra("typeName", mInspectItemBean.getInspectStep().get(0).getTypeName());
                intent.putExtra("typeId", mInspectItemBean.getInspectStep().get(0).getTypeID());
                startActivityForResult(intent, Constants.Code.GO_SCORE);
                break;
            case R.id.tvCopy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("" + tvRegistrationCode.getText());
                showToast("复制成功");
                break;
            case R.id.llDeptName:
                jItems = new String[mBean.getInspectType().get(position).getDept().size()];
                for (int i = 0; i < mBean.getInspectType().get(position).getDept().size(); i++) {
                    jItems[i] = mBean.getInspectType().get(position).getDept().get(i).getDeptName();
                }
                alertDialog.setSingleChoiceItems(jItems, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                loadData(which);
                                dialog.dismiss();
                            }
                        }
                ).show();
                break;
            case R.id.llWorkForm:
                wItems = new String[mBean.getInspectType().get(position).getWorkForm().size()];
                for (int i = 0; i < mBean.getInspectType().get(position).getWorkForm().size(); i++) {
                    wItems[i] = mBean.getInspectType().get(position).getWorkForm().get(i).getName();
                }
                alertDialog.setSingleChoiceItems(wItems, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                updateWorkForm(which);
                                dialog.dismiss();
                            }
                        }
                ).show();
                break;
        }
    }

    private void loadData(int which) {
        showLoading("加载中...");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("ID", mInspectItemBean.getInspect().getID());
        map.put("InspectDeptId", mBean.getInspectType().get(position).getDept().get(which).getDeptId());
        Call<ResponBean> call = server.getService().updateInspectDept(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(gson.toJson(response.body()));
                        loadData();
                        showToast("修改成功！");
                    } else {
                        showToast("修改失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("修改失败！");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    private void updateWorkForm(int which) {
        showLoading("加载中...");
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("ID", mInspectItemBean.getInspect().getID());
        map.put("WorkForm", mBean.getInspectType().get(position).getWorkForm().get(which).getId());
        Call<ResponBean> call = server.getService().updateWorkForm(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(gson.toJson(response.body()));
                        loadData();
                        showToast("修改成功！");
                    } else {
                        showToast("修改失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("修改失败！");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
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

    private void setYes() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("请确认维保是否签认完毕\n完成后本次检测将不可更改并结束！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                        updateInspectByID();
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
                        setResult(Constants.Code.SCORE_OK);
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

    private boolean commit[];

    private void setCommit(final List<InspectItem> inspectItem) {
        if (commit != null && commit.length > 0) {
            for (boolean isCommit : commit) {
                if (!isCommit) {
                    showToast("正在提交中...");
                    return;
                }
            }
        }
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("您确定要一键离线传输吗？");
        normalDialog.setMessage("请您确保手机网络畅通");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                        for (int i = 0; i < inspectItem.size(); i++) {
                            commit = new boolean[inspectItem.size()];
                            uploadFile(i, inspectItem.get(i));
                        }
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

    //上传文件
    public void uploadFile(final int i, final InspectItem bean) {
        showLoading("文件上传中...");
        String s = "";
        if (!TextUtil.isEmpty(bean.getPhotoUrl())) {
            s = bean.getPhotoUrl();
        } else {
            s = bean.getVideoPath();
        }
        if (TextUtil.isEmpty(s)) {
//            showToast("文件不存在");
            hideLoading();
            return;
        }
        if(!TextUtil.isEmpty(bean.getVideoPath())&&bean.getVideoPath().startsWith("~/")){
            commit(i, bean.getVideoPath(), bean);
            hideLoading();
            return;
        }
        if(!TextUtil.isEmpty(bean.getPhotoUrl())&&bean.getPhotoUrl().startsWith("~/")){
            commit(i, bean.getPhotoUrl(), bean);
            hideLoading();
            return;
        }
        //构建要上传的文件
        File file = new File(s);
        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER)
                .client(new OkHttpClient.Builder().
                        connectTimeout(60 * 10, TimeUnit.SECONDS).
                        readTimeout(60 * 10, TimeUnit.SECONDS).
                        writeTimeout(60 * 10, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<ResponBean> call = service.uploadFile(part);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        String data = response.body().getData();
                        Logger.d("成功 data = " + data);
                        commit(i, data, bean);
                        showToast("文件上传成功");
                    } else {
                        commit[i] = true;
                        showToast("文件上传失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    commit[i] = true;
                    showToast("文件上传失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    Logger.d("失败");
                    hideLoading();
                    commit[i] = true;
                    showToast(parseError(throwable));
                }
            }
        });
    }

    //提交数据
    private void commit(final int i, final String file, final InspectItem bean) {
        showLoading("提交中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("InspectId", bean.getInspectId());    //id
        map.put("StepId", bean.getStepId());
        map.put("StepName", bean.getStepName());
        map.put("IsPassed", "true");  // 是否合格
        map.put("UserId", bean.getUserId());
        map.put("UserName", bean.getUserName());
        map.put("TypeID", bean.getTypeID());
        map.put("TypeName", bean.getTypeName());
        map.put("Remark", bean.getRemark());   //备注
        //上传照片还是视频
        if (!TextUtil.isEmpty(bean.getPhotoUrl())) {
            map.put("PhotoUrl", file);
            map.put("VideoPath", "");
        } else {
            map.put("PhotoUrl", "");
            map.put("VideoPath", file);
        }
        //定位
        map.put("MapX", "" + bean.getMapX());
        map.put("MapY", "" + bean.getMapY());
        map.put("TemplateAttributeJson", bean.getTemplateAttributeJson());
        map.put("CreateTime", bean.getCreateTime());
        Logger.d(new Gson().toJson(map).toString());
        Call<ResponBean> call = server.getService().saveInspectDetailNew(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                commit[i] = true;
                try {
                    Gson gson = new Gson();
                    Logger.d("" + gson.toJson(response.body()));
                    if (response.body().isSuccess()) {
                        if (response.body().getMessage().equals("操作成功")) {
                            showToast(response.body().getMessage());
                            manager.delInspectItemByKey(bean.getId());
                            for (InspectItemBean.InspectStepBean inspectStepBean : mInspectItemBean.getInspectStep()) {
                                if (inspectStepBean.getStepId() == bean.getStepId()) {
                                    if (!TextUtil.isEmpty(bean.getPhotoUrl())) {
                                        inspectStepBean.setPhotoUrl(file);
                                        inspectStepBean.setOffLinePhotoUrl("");
                                    } else {
                                        inspectStepBean.setVideoPath(file);
                                        inspectStepBean.setOffLineVideoPath("");
                                    }
                                    mAdapter.setData(mInspectItemBean.getInspectStep());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            showToast(response.body().getMessage());
                        }
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("提交失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    commit[i] = true;
                    showToast(parseError(throwable));
                }
            }
        });
    }

    private void updateInspectByID() {
        showLoading("加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().updateInspectByIDNew(map, mInspectItemBean.getInspect().getID());
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        if (response.body().getMessage().equals("操作成功")) {
                            setResult(Constants.Code.SCORE_OK);
                            finish();
                        } else {
                            showToast(response.body().getMessage());
                        }
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("请求失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
        unregisterReceiver(dynamicReceiver);
    }
}

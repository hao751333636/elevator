package com.sinodom.elevator.fragment.inspect.inspect;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.inspect.InspectActivity;
import com.sinodom.elevator.activity.inspect.video.SendSmallVideoActivity;
import com.sinodom.elevator.activity.inspect.video.SendSmallVideoActivity2;
import com.sinodom.elevator.activity.inspect.video.SendSmallVideoActivity3;
import com.sinodom.elevator.activity.inspect.video.VideoPlayerActivity;
import com.sinodom.elevator.adapter.elist.ElevatorListNewAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.elevator.ExamineDataBean;
import com.sinodom.elevator.bean.steplist.StepDataBean;
import com.sinodom.elevator.db.Inspect;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.ImageUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.PhoneUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.view.NoScrollListView;
import com.sinodom.elevator.view.SeeImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2018/1/11.
 * 检验服务
 */

public class InspectionFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.lvInspection)
    NoScrollListView lvInspection;
    private List<ExamineDataBean> mDataBeen = null;
    private List<StepDataBean.InspectDetailsBean> mDataBeenValue = null;
    private String inspectId;
    private int mId;
    private String mVideoIndex = "1";
    private ElevatorListNewAdapter mAdapter;
    private String userId;
    private int mPosition;
    private String mPhotoPath; //手机拍照后图片地址
    private StepDataBean.InspectDetailsBean[] mStatusBean;  //状态bean
    public LocationClient mLocationClient = null; //百度定位
    private MyLocationListener mMyLocationListener = new MyLocationListener();
    private String mRemark; //备注
    private DynamicReceiver dynamicReceiver;
    //电梯详细信息
    private StepDataBean mStepDataBean;
    public static final int REQUEST_CODE_SELECT = 100;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        //百度定位
        mLocationClient = new LocationClient(context);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        initLocation();
        //百度定位-end

        Bundle bundle = getArguments();
        mDataBeen = (List<ExamineDataBean>) bundle.getSerializable("list");
        mDataBeenValue = (List<StepDataBean.InspectDetailsBean>) bundle.getSerializable("list2");
        inspectId = bundle.getString("InspectId");
        mId = bundle.getInt("Id");
        mVideoIndex = bundle.getString("videoIndex");

        //实例化IntentFilter对象
        IntentFilter filter = new IntentFilter();
        switch (mVideoIndex) {
            case "1":
                filter.addAction("fragment1");
                break;
            case "2":
                filter.addAction("fragment2");
                break;
            case "3":
                filter.addAction("fragment3");
                break;
        }
        dynamicReceiver = new DynamicReceiver();
        //注册广播接收
        getActivity().registerReceiver(dynamicReceiver, filter);
        userId = manager.getSession().getUserID() + "";

        mStatusBean = new StepDataBean.InspectDetailsBean[mDataBeen.size()];  //上锁状态
        for (int i = 0; i < mDataBeen.size(); i++) {
            mStatusBean[i] = new StepDataBean.InspectDetailsBean();
            for (int a = 0; a < mDataBeenValue.size(); a++) {
                if (mDataBeen.get(i).getID() == mDataBeenValue.get(a).getStepId()) {
                    mStatusBean[i] = mDataBeenValue.get(a);
                }
            }
        }
        mAdapter = new ElevatorListNewAdapter(context, mDataBeen, mStatusBean);
        lvInspection.setAdapter(mAdapter);
        mAdapter.setElevatorListNewAdapter(new ElevatorListNewAdapter.MyInterface() {
            @Override
            public void setPosition(int positions) {
                //传递当前是第几条
                mPosition = positions;
            }

            @Override
            public void clickNetwork() {
                //查看网络图片或视频
                getClickNetwork();
            }

            @Override
            public void clickLocal() {
                //查看本地照片或视频
                getClickLocal();
            }

            @Override
            public void clickCamera() {
                //拍摄
                //判断是照相还是录像
                if (mDataBeen.get(mPosition).isIsPhoto()) {
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
                    File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
                    if (DeviceUtils.isZte()) {
                        if (dcim.exists()) {
                            JianXiCamera.setVideoCachePath(dcim + "/Elevator/");
                        } else {
                            JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "/sdcard-ext/") + "/Elevator/");
                        }
                    } else {
                        JianXiCamera.setVideoCachePath(dcim + "/Elevator/");
                    }
                    // 初始化拍摄，遇到问题可选择开启此标记，以方便生成日志
                    JianXiCamera.initialize(false, null);

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
            public void clickLock() {
                //上锁解锁
                if (mStatusBean[mPosition] != null &&
                        (!TextUtil.isEmpty(mStatusBean[mPosition].getPhotoUrl()) || !TextUtil.isEmpty(mStatusBean[mPosition].getVideoPath()))) {
                    lock();
                } else {
                    showToast("检验后才能上锁！");
                }
            }

            @Override
            public void clickSubmit(String note) {
                //确认
                boolean boo = false;
                mRemark = note;
                if (mDataBeen.get(mPosition).isIsPhoto()) {
                    if (mStatusBean[mPosition] != null && !TextUtil.isEmpty(mStatusBean[mPosition].getPhotoLocalUrl())) {
                        boo = true;
                    }
                } else {
                    if (mStatusBean[mPosition] != null && !TextUtil.isEmpty(mStatusBean[mPosition].getVideoLocalPath())) {
                        boo = true;
                    }
                }
                if (boo) {
                    boolean permission = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
                    boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
                    if (!permission || !permission1) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 300);
                    } else {
                        isNetworkConnect();
                    }
                } else {
                    showToast("请先拍摄检验内容");
                }
            }
        });
    }

    private void isNetworkConnect() {
        //网络连接判断
//        if (PhoneUtil.ping()) {
//            //提交
//            startLocationClient();
//        } else {
//            localCommit();
//        }
        PhoneUtil.isNetWorkAvailableOfGet("https://www.baidu.com", new Comparable<Boolean>() {
            @Override
            public int compareTo(Boolean available) {
                if (available) {
                    // TODO 设备访问Internet正常
                    //提交
                    startLocationClient();
                } else {
                    // TODO 设备无法访问Internet
                    localCommit();
                }
                return 0;
            }

        });
    }

    //提交到本地
    private void localCommit() {
        Inspect inspect = new Inspect();
        inspect.setInspectId(mId);
        inspect.setTypeID(mDataBeen.get(mPosition).getTypeID());
        inspect.setTypeName(mDataBeen.get(mPosition).getTypeName());
        inspect.setStepId(mDataBeen.get(mPosition).getID());
        inspect.setStepName(mDataBeen.get(mPosition).getStepName());
        inspect.setIsPassed(true);
        inspect.setRemark(mRemark);
        inspect.setIsPhoto(mDataBeen.get(mPosition).isIsPhoto());
        if (mDataBeen.get(mPosition).isIsPhoto()) {
            inspect.setLocal(mStatusBean[mPosition].getPhotoLocalUrl());
        } else {
            inspect.setLocal(mStatusBean[mPosition].getVideoLocalPath());
        }
        inspect.setMapX(((InspectActivity) getActivity()).latitude + "");
        inspect.setMapY(((InspectActivity) getActivity()).longitude + "");
        manager.addInspect(inspect);
        showToast("离线提交成功");
    }

    //上锁解锁
    private void lock() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", userId);
        mStatusBean[mPosition].setInspectId(mId);
        mStatusBean[mPosition].setTypeID(mDataBeen.get(mPosition).getTypeID());
        mStatusBean[mPosition].setStepId(mDataBeen.get(mPosition).getID());
        mStatusBean[mPosition].setUserId(manager.getSession().getUserID());
        Logger.d(new Gson().toJson(mStatusBean[mPosition]).toString());
        Call<ResponBean> call = server.getService().saveInspectDetailLock(map, mStatusBean[mPosition]);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    Gson gson = new Gson();
                    Logger.d(gson.toJson(response.body()));
                    StepDataBean.InspectDetailsBean bean = gson.fromJson(response.body().getData(), StepDataBean.InspectDetailsBean.class);
                    if (response.body().isSuccess()) {
                        if (response.body().getMessage().equals("操作成功")) {
                            showToast(response.body().getMessage());
                            if (bean.getIsLock() == 0) {
                                mStatusBean[mPosition].setIsLock(0);
                            } else if (bean.getIsLock() == 1) {
                                mStatusBean[mPosition].setIsLock(1);
                            }
                            mAdapter.setRefresh(mStatusBean);
                        } else {
                            showToast(response.body().getMessage());
                        }
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("操作失败！");
                }
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

    //查看网络图片或视频
    private void getClickNetwork() {
        if (mDataBeen.get(mPosition).isIsPhoto()) {
            if (mStatusBean[mPosition] != null && !TextUtil.isEmpty(mStatusBean[mPosition].getPhotoUrl())) {
                SeeImageView seeImageView = new SeeImageView(getActivity(), R.style.dialog, mStatusBean[mPosition].getPhotoUrl(), false);
                seeImageView.show();
            }
        } else {
            if (mStatusBean[mPosition] != null && !TextUtil.isEmpty(mStatusBean[mPosition].getVideoPath())) {
                String str = mStatusBean[mPosition].getVideoPath().substring(2);
                String myUrl = BuildConfig.SERVER + str;
                startActivity(new Intent(getActivity(), VideoPlayerActivity.class).putExtra("path", myUrl));
            }
        }
    }

    //查看本地照片或视频
    private void getClickLocal() {
        if (mDataBeen.get(mPosition).isIsPhoto()) {
            if (mStatusBean[mPosition] != null && !TextUtil.isEmpty(mStatusBean[mPosition].getPhotoLocalUrl())) {
                SeeImageView seeImageView = new SeeImageView(getActivity(), R.style.dialog, mStatusBean[mPosition].getPhotoLocalUrl(), true);
                seeImageView.show();
            }
        } else {
            if (mStatusBean[mPosition] != null && !TextUtil.isEmpty(mStatusBean[mPosition].getVideoLocalPath())) {
                startActivity(new Intent(getActivity(), VideoPlayerActivity.class).putExtra("path", mStatusBean[mPosition].getVideoLocalPath()));
            }
        }
    }

    //开始录像
    private void getVideo() {
        // 录制
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
//                .fullScreen(true)
//                .smallVideoWidth(720)
//                .smallVideoHeight(1280)
                .recordTimeMax(5 * 60 * 1000)
                .recordTimeMin(10000)
                .maxFrameRate(20)
                .videoBitrate(600000)
                .captureThumbnailsTime(1)
                .build();
        switch (mVideoIndex) {
            case "1":
                MediaRecorderActivity.goSmallVideoRecorder(getActivity(), SendSmallVideoActivity.class.getName(), config);
                break;
            case "2":
                MediaRecorderActivity.goSmallVideoRecorder(getActivity(), SendSmallVideoActivity2.class.getName(), config);
                break;
            case "3":
                MediaRecorderActivity.goSmallVideoRecorder(getActivity(), SendSmallVideoActivity3.class.getName(), config);
                break;
        }
    }

    //开始拍照
    private void getPhoto() {
//        try {
//            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
//            mPhotoPath = "";
//            mPhotoPath = PictureUtil.getPicturesDir().getAbsolutePath() + "/" + PictureUtil.getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面
//            File mPhotoFile = new File(mPhotoPath);
////            if (!mPhotoFile.exists()) {
////                mPhotoFile.createNewFile();//创建新文件
////            }
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
//                    Uri.fromFile(mPhotoFile));
//            startActivityForResult(intent, 1);//跳转界面传回拍照所得数据
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Intent intent = new Intent(getActivity(), ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
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
                isNetworkConnect();
            } else {
                getPermission("提交失败，请授权APP定位权限！");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //拍照成功刷新数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (requestCode == 1 && resultCode == RESULT_OK) {
//                String str = DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss.SSS");
//                //获取bitmap
//                Bitmap bitmap = ImageUtil.getBitmap(mPhotoPath);
//                //解决三星图片旋转问题
//                Bitmap bitmapRotate = ImageUtil.rotateToDegrees(bitmap, ImageUtil.readPictureDegree(mPhotoPath));
//                //加时间水印
//                Bitmap textBitmap = ImageUtil.drawTextToRightBottom(context,
//                        bitmapRotate, str, bitmapRotate.getWidth() / 25, Color.RED, bitmapRotate.getWidth() / 25, bitmapRotate.getWidth() / 25);
//                ImageUtil.savePicToSdcard(textBitmap, mPhotoPath);
//                mStatusBean[mPosition].setPhotoLocalUrl(mPhotoPath);
//                mAdapter.setRefresh(mStatusBean);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

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
                mStatusBean[mPosition].setPhotoLocalUrl(images.get(0).path);
                mAdapter.setRefresh(mStatusBean);
            } else {
                showToast("拍照失败！");
            }
        }
    }

    //录像成功刷新数据
    class DynamicReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String videoUri = intent.getStringExtra("uri");
            mStatusBean[mPosition].setVideoLocalPath(videoUri);
            mAdapter.setRefresh(mStatusBean);
        }
    }

    private void startLocationClient() {
        showLoading("定位中...");
        mLocationClient.start();
    }

    private void initLocation() {
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

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                double latitude = location.getLatitude();    //获取纬度信息
                double longitude = location.getLongitude();    //获取经度信息
                Logger.d("维度x= " + latitude + "-经度y= " + longitude);
                hideLoading();
                uploadFile(latitude, longitude);
            } else {
                if (location.getLocType() == 167) {
                    showToast("服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。");
                }
                Logger.d("百度定位失败");
                hideLoading();
            }
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
        }
    }

    //上传文件
    public void uploadFile(final double MapX, final double MapY) {
        showLoading("加载中...");
        String s = "";
        if (mDataBeen.get(mPosition).isIsPhoto()) {
            s = mStatusBean[mPosition].getPhotoLocalUrl();
        } else {
            s = mStatusBean[mPosition].getVideoLocalPath();
        }
        //构建要上传的文件
        File file = new File(s);
        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        Call<ResponBean> call = server.getService().uploadFile(part);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        String data = response.body().getData();
                        Logger.d("成功 data = " + data);
                        commit(data, MapX, MapY);
                        showToast("文件上传成功");
                    } else {
                        showToast("上传失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("上传失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    Logger.d("失败");
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    //提交数据
    private void commit(final String file, final double MapX, final double MapY) {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", userId);
        map.put("InspectId", mId);    //id
        map.put("TypeID", mDataBeen.get(mPosition).getTypeID());
        map.put("TypeName", mDataBeen.get(mPosition).getTypeName());
        map.put("StepId", mDataBeen.get(mPosition).getID());
        map.put("StepName", mDataBeen.get(mPosition).getStepName());
        map.put("IsPassed", "true");  // 是否合格
        map.put("Remark", mRemark);   //备注
        //上传照片还是视频
        if (mDataBeen.get(mPosition).isIsPhoto()) {
            map.put("PhotoUrl", file);
            map.put("VideoPath", "");
        } else {
            map.put("PhotoUrl", "");
            map.put("VideoPath", file);
        }
        //定位
        map.put("MapX", "" + MapX);
        map.put("MapY", "" + MapY);
        Logger.d(new Gson().toJson(map).toString());
        Call<ResponBean> call = server.getService().saveInspectDetail(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    Gson gson = new Gson();
                    Logger.d("" + gson.toJson(response.body()));
                    if (response.body().isSuccess()) {
                        if (response.body().getMessage().equals("操作成功")) {
                            showToast(response.body().getMessage());
                            if (mDataBeen.get(mPosition).isIsPhoto()) {
                                mStatusBean[mPosition].setPhotoUrl(file);
                            } else {
                                mStatusBean[mPosition].setVideoPath(file);
                            }
                            mStatusBean[mPosition].setInspectId(mId);
                            mStatusBean[mPosition].setTypeID(mDataBeen.get(mPosition).getTypeID());
                            mStatusBean[mPosition].setStepId(mDataBeen.get(mPosition).getID());
                            mStatusBean[mPosition].setUserId(manager.getSession().getUserID());
                            mAdapter.setRefresh(mStatusBean);
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
                    showToast(parseError(throwable));
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }
}

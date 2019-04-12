/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sinodom.elevator.zxing.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.business.maintenance.MaintenanceChooseActivity;
import com.sinodom.elevator.activity.elevator.business.property.PropertyInspectionAddActivity;
import com.sinodom.elevator.activity.elevator.business.repairrecord.RepairAddActivity;
import com.sinodom.elevator.activity.elevator.nfc.EquipmentAddActivity;
import com.sinodom.elevator.activity.elevator.nfc.NfcAddActivity;
import com.sinodom.elevator.activity.elevator.nfc.VideoBindActivity;
import com.sinodom.elevator.activity.elevator.parts.PartsActivity;
import com.sinodom.elevator.activity.inspect.ImportCodingActivity;
import com.sinodom.elevator.activity.inspect.InspectChooseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.business.ElevatorInformationBean;
import com.sinodom.elevator.bean.property.PropertyBean;
import com.sinodom.elevator.bean.repairrecord.RepairBean;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.zxing.camera.CameraManager;
import com.sinodom.elevator.zxing.decode.DecodeThread;
import com.sinodom.elevator.zxing.utils.BeepManager;
import com.sinodom.elevator.zxing.utils.CaptureActivityHandler;
import com.sinodom.elevator.zxing.utils.InactivityTimer;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This activity opens the camera and does the actual scanning on a background
 * thread. It draws a viewfinder to help the user place the barcode correctly,
 * shows feedback as the image processing is happening, and then overlays the
 * results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;

    private SurfaceView scanPreview = null;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;
    private ImageView ivBack;
    private ImageView ivLight;
    private TextView tvLight;
    private String inspectId;

    private Rect mCropRect = null;

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isHasSurface = false;

    //1检验 2维保
    private String source;
    public static String SCAN_JYFU = "1";//检验服务
    public static String SCAN_DTWB = "2";//电梯维保
    public static String SCAN_WUXC = "3";//物业巡查
    public static String SCAN_WXJL = "4";//维修记录
    public static String SCAN_SBTS = "5";//设备调试
    public static String SCAN_BQBD = "6";//标签绑定
    public static String SCAN_WZHWB = "7";//无纸化维保
    public static String SCAN_SBBD = "8";//设备绑定
    public static String SCAN_PJGL = "9";//配件管理
    public static String SCAN_SPZJ = "10";//视频主机

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置statusBar颜色
            getWindow().setStatusBarColor(getResources().getColor(R.color.black5));
        }
        setContentView(R.layout.activity_capture);

        source = getIntent().getStringExtra("source");

        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = (RelativeLayout) findViewById(R.id.capture_container);
        scanCropView = (RelativeLayout) findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);


        TextView tvImport = (TextView) findViewById(R.id.tvImport);
        tvImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("result", "");

                if (source.equals(SCAN_JYFU)) {

                    startActivity(new Intent(CaptureActivity.this, ImportCodingActivity.class).putExtras(bundle));
                }

                if (source.equals(SCAN_PJGL)) {

                    startActivity(new Intent(CaptureActivity.this, com.sinodom.elevator.activity.elevator.parts.ImportCodingActivity.class).putExtras(bundle));
                }
                finish();
            }
        });
        if (!source.equals("1") && !source.equals("9")) {
            tvImport.setVisibility(View.GONE);
        }

        //新增
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivLight = (ImageView) findViewById(R.id.ivLight);
        tvLight = (TextView) findViewById(R.id.tvLight);
        ivBack.setOnClickListener(this);
        ivLight.setOnClickListener(this);

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.9f);
        animation.setDuration(4500);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        scanLine.startAnimation(animation);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is
        // necessary because we don't
        // want to open the camera driver and measure the screen size if we're
        // going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the
        // wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (isHasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(scanPreview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            scanPreview.getHolder().addCallback(this);
        }

        inactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!isHasSurface) {
            scanPreview.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        mRetrofitManager.cancelAll();
        super.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!isHasSurface) {
            isHasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isHasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     *
     * @param rawResult The contents of the barcode.
     * @param bundle    The extras
     */
    public void handleDecode(Result rawResult, Bundle bundle) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        if (rawResult.getText() != null && !rawResult.getText().equals("")) {
            inspectId = rawResult.getText().substring(rawResult.getText().indexOf("=") + 1);
            if (source.equals(SCAN_JYFU)) {
                Intent intent = new Intent(CaptureActivity.this, InspectChooseActivity.class);
                intent.putExtra("inspectId", "" + URLDecoder.decode(inspectId));
                setResult(Constants.Code.SCORE_OK);
                startActivity(intent);
                finish();
//                userDataHttp_info();
            } else if (source.equals(SCAN_DTWB)) {
                userDataHttp_info2();
            } else if (source.equals(SCAN_WUXC)) {
                userDataHttp_info3();
            } else if (source.equals(SCAN_WXJL)) {
                userDataHttp_info4();
            } else if (source.equals(SCAN_SBTS)) {
                setResult(Constants.Code.SCORE_OK, new Intent().putExtra("liftNum", URLDecoder.decode(inspectId)));
                finish();
            } else if (source.equals(SCAN_BQBD)) {
                userDataHttp_info6();
            } else if (source.equals(SCAN_WZHWB)) {
                userDataHttp_info7();
            } else if (source.equals(SCAN_SBBD)) {
                Intent intent = new Intent(CaptureActivity.this, EquipmentAddActivity.class);
                intent.putExtra("liftNum", URLDecoder.decode(inspectId));
                startActivity(intent);
            } else if (source.equals(SCAN_PJGL)) {
                Intent intent = new Intent(CaptureActivity.this, PartsActivity.class);
                intent.putExtra("liftNum", URLDecoder.decode(inspectId));
                startActivity(intent);
            } else if (source.equals(SCAN_SPZJ)) {
                Intent intent = new Intent(CaptureActivity.this, VideoBindActivity.class);
                intent.putExtra("liftNum", URLDecoder.decode(inspectId));
                startActivity(intent);
            }

        } else {
            showToast("扫码失败");
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a
            // RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager, DecodeThread.ALL_MODE);
            }

            initCrop();
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        // camera error
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage("相机打开出错，请稍后重试");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
    }

    public Rect getCropRect() {
        return mCropRect;
    }

    /**
     * 初始化截取的矩形区域
     */
    private void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
        mCropRect = new Rect(x, y, width + x, height + y);
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    boolean flag = true;

    protected void light() {
        try {
            if (flag == true) {
                flag = false;
                // 开闪光灯
                cameraManager.openLight();
                tvLight.setText("关");
                ivLight.setImageResource(R.mipmap.qb_scan_btn_scan_off_);
            } else {
                flag = true;
                // 关闪光灯
                cameraManager.offLight();
                tvLight.setText("开");
                ivLight.setImageResource(R.mipmap.qb_scan_btn_flash_nor_);
            }
        } catch (Exception ioe) {
            showToast("闪光灯打开出错，请稍后重试");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.ivBack:
                inactivityTimer.shutdown();
                finish();
                break;
            case R.id.ivLight:
                light();
                break;
        }
    }

    //检验服务
    private void userDataHttp_info() {
        showLoading("加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("LiftNum", "" + inspectId);
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().getIsInspectByLiftNum(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        if (response.body().getMessage().equals("操作成功")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("inspectId", "" + URLDecoder.decode(inspectId));
//                            Intent intent = new Intent(CaptureActivity.this, InspectActivity.class);
                            Intent intent = new Intent(CaptureActivity.this, InspectChooseActivity.class);
                            intent.putExtras(bundle);
                            setResult(Constants.Code.SCORE_OK);
                            startActivity(intent);
                            finish();
                        } else if (response.body().getMessage().equals("任务不存在")) {
                            Establish();
                        } else {
                            showToast(response.body().getMessage());
                            finish();
                        }
                    } else {
                        showToast("电梯不存在！");
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电梯不存在！");
                    finish();
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

    //电梯维保
    private void userDataHttp_info2() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LiftNum", "" + inspectId);
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().getLiftCheckByLiftNum(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
//                        if (response.body().getMessage().equals("操作成功")){
                        Gson gson = new Gson();
                        ElevatorInformationBean elevatorInformationBean = gson.fromJson(response.body().getData(), ElevatorInformationBean.class);
                        if (elevatorInformationBean.getFloorNumber() != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", "" + elevatorInformationBean.getID());
                            bundle.putString("InstallationAddress", "" + elevatorInformationBean.getInstallationAddress());
                            if (elevatorInformationBean.getListCheck().size() == 0) {
                                bundle.putString("UploadDate", "");
                            } else {
                                bundle.putString("UploadDate", "" + elevatorInformationBean.getListCheck().get(0).getUploadDate());
                            }

                            bundle.putString("FloorNumber", "" + elevatorInformationBean.getFloorNumber());
                            bundle.putString("LongitudeAndLatitude", "" + elevatorInformationBean.getLongitudeAndLatitude());
                            bundle.putString("LiftNum", "" + URLDecoder.decode(inspectId));
                            startActivity(new Intent(CaptureActivity.this, MaintenanceChooseActivity.class).putExtras(bundle));
                            setResult(Constants.Code.SCORE_OK);
                            finish();
                        } else {
                            showToast(response.body().getMessage());
                            finish();
                        }
                    } else {
                        showToast("电梯不存在！");
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电梯不存在！");
                    finish();
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

    //物业巡查
    private void userDataHttp_info3() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LiftNum", "" + inspectId);
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().getPropertyStep(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        PropertyBean propertyBean = gson.fromJson(response.body().getData(), PropertyBean.class);
                        if (!TextUtil.isEmpty(propertyBean.getLiftNum())) {
                            Intent intent = new Intent(CaptureActivity.this, PropertyInspectionAddActivity.class);
                            intent.putExtra("bean", propertyBean);
                            startActivity(intent);
                            setResult(Constants.Code.SCORE_OK);
                            finish();
                        } else {
                            showToast("电梯不存在！");
                            finish();
                        }
                    } else {
                        showToast(response.body().getMessage());
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电梯不存在！");
                    finish();
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

    //物业维修
    private void userDataHttp_info4() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LiftNum", "" + inspectId);
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().getRepairNewNum(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        RepairBean bean = gson.fromJson(response.body().getData(), RepairBean.class);
                        Intent intent = new Intent(CaptureActivity.this, RepairAddActivity.class);
                        intent.putExtra("bean", bean);
                        setResult(Constants.Code.SCORE_OK);
                        startActivity(intent);
                        finish();
                    } else {
                        showToast(response.body().getMessage());
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电梯不存在！");
                    finish();
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

    //标签绑定
    private void userDataHttp_info6() {
        startActivity(new Intent(CaptureActivity.this, NfcAddActivity.class).putExtra("liftNum", URLDecoder.decode(inspectId)));
        setResult(Constants.Code.SCORE_OK);
        finish();
    }

    //无纸化维保
    private void userDataHttp_info7() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LiftNum", "" + inspectId);
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().getLiftCheckByLiftNum2(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
//                        if (response.body().getMessage().equals("操作成功")){
                        Gson gson = new Gson();
                        ElevatorInformationBean elevatorInformationBean = gson.fromJson(response.body().getData(), ElevatorInformationBean.class);
                        if (elevatorInformationBean.getFloorNumber() != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", "" + elevatorInformationBean.getID());
                            bundle.putString("InstallationAddress", "" + elevatorInformationBean.getInstallationAddress());
                            if (elevatorInformationBean.getListCheck().size() == 0) {
                                bundle.putString("UploadDate", "");
                            } else {
                                bundle.putString("UploadDate", elevatorInformationBean.getListCheck().get(0).getCheckDate());
                                bundle.putString("GateNumber", elevatorInformationBean.getListCheck().get(0).getCType());
                                bundle.putString("taskID", "" + elevatorInformationBean.getListCheck().get(0).getID());
                            }

                            bundle.putString("FloorNumber", "" + elevatorInformationBean.getFloorNumber());
                            bundle.putString("LongitudeAndLatitude", "" + elevatorInformationBean.getLongitudeAndLatitude());
                            bundle.putString("LiftNum", "" + URLDecoder.decode(inspectId));
                            startActivity(new Intent(CaptureActivity.this, com.sinodom.elevator.activity.elevator.nfc.MaintenanceChooseActivity.class).putExtras(bundle));
                            setResult(Constants.Code.SCORE_OK);
                            finish();
                        } else {
                            showToast(response.body().getMessage());
                            finish();
                        }
                    } else {
                        showToast(response.body().getMessage());
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电梯不存在！");
                    finish();
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

    private void Establish() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("暂无此电梯信息，是否创建？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                        Bundle bundle = new Bundle();
                        bundle.putString("inspectId", "" + URLDecoder.decode(inspectId));
//                        Intent intent = new Intent(CaptureActivity.this, InspectActivity.class);
                        Intent intent = new Intent(CaptureActivity.this, InspectChooseActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
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
}
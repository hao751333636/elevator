package com.sinodom.elevator.activity.elevator.business.maintenance;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.elist.ElevatorSurveyAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.business.CheckDetailEntity;
import com.sinodom.elevator.bean.business.RecordDetailsBean;
import com.sinodom.elevator.db.Maintenance;
import com.sinodom.elevator.util.ImageUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.PhoneUtil;
import com.sinodom.elevator.util.PictureUtil;
import com.sinodom.elevator.view.NoScrollListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2017/12/19.
 * 业务-维保检验项提交
 */

public class MaintenanceInspectionActivity2 extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvEleInfoNum)
    TextView tvEleInfoNum;
    @BindView(R.id.tvEleInfoIstrue)
    TextView tvEleInfoIstrue;
    @BindView(R.id.tvEleInfoPlace)
    TextView tvEleInfoPlace;
    @BindView(R.id.lvMaintenance)
    NoScrollListView lvMaintenance;
    @BindView(R.id.tvSubmit)
    TextView tvSubmit;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private String ID = ""; //电梯ID
    private String InstallationAddress = "";  //电梯地址
    private String UploadDate = "";  //上次维保时间
    private String floorNumber = "";   //本次维保对比ID
    private String LongitudeAndLatitude = "";   //电梯位置
    private String LiftNum = "";   //电梯位置
    private String CheckType = "";   //分类id
    private String CType = "";   //类型id
    private List<RecordDetailsBean.CheckDetailsBean.StepBean> mListBean = new ArrayList<>();
    private List<RecordDetailsBean.CheckDetailsBean.StepBean> mListBean2 = new ArrayList<>();
    private List<CheckDetailEntity> submitBean = new ArrayList<>();
    private ElevatorSurveyAdapter adapter;
    private int mPhotoPathPosition;
    private int mElects = 0;
    private String mPhotoPath;
    private String[] mPhotoPathList;
    private int[] elect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_inspection);
        ButterKnife.bind(this);

        ID = getIntent().getStringExtra("ID");
        InstallationAddress = getIntent().getStringExtra("InstallationAddress");
        UploadDate = getIntent().getStringExtra("UploadDate");
        String result = getIntent().getStringExtra("FloorNumber");
        floorNumber = result.substring(0, result.indexOf("."));
        LongitudeAndLatitude = getIntent().getStringExtra("LongitudeAndLatitude");
        LiftNum = getIntent().getStringExtra("LiftNum");
        CheckType = getIntent().getStringExtra("CheckType");
        CType = getIntent().getStringExtra("CType");
        switch (CType) {
            case "0":
                tvTitle.setText("半月保");
                break;
            case "2":
                tvTitle.setText("季度保");
                break;
            case "3":
                tvTitle.setText("半年保");
                break;
            case "4":
                tvTitle.setText("年度保");
                break;
        }
        tvEleInfoNum.setText(LiftNum);
        if (UploadDate.equals("")) {
            tvEleInfoIstrue.setText("尚未维保");
        } else {
            String s3 = "" + UploadDate;
            String[] temp = null;
            temp = s3.split("T");
            String strTime = temp[1].substring(0, 5);
            tvEleInfoIstrue.setText("" + temp[0] + "  " + strTime);
        }
        tvEleInfoPlace.setText(InstallationAddress);

        getStepList();
    }

    private void initView() {
        for (int a = 0; a < mListBean.size(); a++) {
            if (CheckType.equals("" + mListBean.get(a).getCheckType())) {
                mListBean2.add(mListBean.get(a));
                CheckDetailEntity checkDetailEntity = new CheckDetailEntity();
                checkDetailEntity.setLiftId(Integer.valueOf(ID).intValue());
                checkDetailEntity.setDeptId(manager.getSession().getDeptId());
                checkDetailEntity.setUserId(manager.getSession().getUserID());
                checkDetailEntity.setStepId(mListBean.get(a).getID());
                checkDetailEntity.setCType(CType);
                checkDetailEntity.setLongitudeAndLatitude(LongitudeAndLatitude);
                submitBean.add(checkDetailEntity);
            }
        }
        mPhotoPathList = new String[mListBean2.size()];
        adapter = new ElevatorSurveyAdapter(this, mListBean2);
        lvMaintenance.setAdapter(adapter);
        adapter.setElevatorSurveyAdapter(new ElevatorSurveyAdapter.MyInterface() {

            @Override
            public void foo(int position, boolean elects) {
                mPhotoPathPosition = position;
                if (elects) {
                    mElects = 1;
                    if (mListBean2.get(position).isIsTakePhoto()) {
                        //合格拍照
                        Pictures();
                    } else {
                        //合格不拍照
                        submitBean.get(position).setPassed(elects);
                        submitBean.get(position).setPhoto("");
                    }
                } else {
                    mElects = 2;
                    //不合格
                    Pictures();
                }
            }

            @Override
            public void getElect(int[] elect) {
                setElects(elect);
            }
        });


    }

    private void setElects(int[] elect) {
        this.elect = elect;
    }

    private void getStepList() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", "" + userId);
        map.put("CheckType", "" + CheckType);
        Call<ResponBean> call = server.getService().getStepList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(gson.toJson(response.body()));
                        mListBean = gson.fromJson(response.body().getData(),
                                new TypeToken<ArrayList<RecordDetailsBean.CheckDetailsBean.StepBean>>() {
                                }.getType());
                        Logger.d("mListBean = " + mListBean.size());
                        initView();
                    } else {
                        showToast("失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("失败！");
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
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }


    @OnClick({R.id.ivBack, R.id.tvSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                setFinish();
                break;
            case R.id.tvSubmit:
                Submit();
                break;
        }
    }

    private void Submit() {
        boolean boo = true;
        for (int i = 0; i < mListBean2.size(); i++) {
            if (submitBean.get(i).isPassed()) {
                if (mListBean2.get(i).isIsTakePhoto()) {
                    if (mPhotoPathList[i] == null || mPhotoPathList[i].equals("")) {
                        boo = false;
                        showToast("第" + (i + 1) + "项未维保");
                        break;
                    }
                }
            } else {
                if (mPhotoPathList[i] == null || mPhotoPathList[i].equals("")) {
                    boo = false;
                    showToast("第" + (i + 1) + "项未维保");
                    break;
                }
            }
        }
        if (boo) {
            String[] remark = adapter.getEt();
            for (int i = 0; i < mListBean2.size(); i++) {
                if (mPhotoPathList[i] == null || mPhotoPathList[i].equals("")) {
                    submitBean.get(i).setPhoto("");
                } else {
                    submitBean.get(i).setPhoto("" + PictureUtil.bitmapToString(mPhotoPathList[i]));
                }
                submitBean.get(i).setRemark(remark[i]);
            }
            //网络连接判断
//            if (PhoneUtil.ping()) {
//                //提交
//                saveCheckDetails();
//            } else {
//                localCommit();
//            }
            PhoneUtil.isNetWorkAvailableOfGet("https://www.baidu.com", new Comparable<Boolean>() {
                @Override
                public int compareTo(Boolean available) {
                    if (available) {
                        // TODO 设备访问Internet正常
                        //提交
                        saveCheckDetails();
                    } else {
                        // TODO 设备无法访问Internet
                        localCommit();
                    }
                    return 0;
                }

            });
        }
    }

    //提交到本地
    private void localCommit() {
        Maintenance maintenance = new Maintenance();
        maintenance.setJson(new Gson().toJson(submitBean).toString());
        Logger.d(new Gson().toJson(submitBean).toString());
        manager.addMaintenance(maintenance);
        showToast("离线提交成功");
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

    //调用相机拍照
    public void Pictures() {
        Logger.d("拍照");
        try {
            //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean permission = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
                boolean permission1 = selfPermissionGranted(Manifest.permission.CAMERA);
                if (!permission || !permission1) {
                    ActivityCompat.requestPermissions(MaintenanceInspectionActivity2.this,
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
                    startActivityForResult(intent, 1);//跳转界面传回拍照所得数据
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
//                    if (!mPhotoFile.exists()) {
//                        mPhotoFile.createNewFile();//创建新文件
//                    }
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
                mPhotoPathList[mPhotoPathPosition] = mPhotoPath;
                if (mElects == 1) {
                    submitBean.get(mPhotoPathPosition).setPassed(true);
                } else {
                    submitBean.get(mPhotoPathPosition).setPassed(false);
                }
            } else {
                Logger.d("取消拍照返回");
                if (mPhotoPathList[mPhotoPathPosition] != null && !mPhotoPathList[mPhotoPathPosition].equals("")) {
                    mPhotoPathList[mPhotoPathPosition] = "";
                }
                mElects = 0;
                submitBean.get(mPhotoPathPosition).setPassed(false);
                Elects();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Elects() {
        elect[mPhotoPathPosition] = 0;
        adapter.setElect(elect);
    }

    private void saveCheckDetails() {
        showLoading("加载中...");
        Call<ResponBean> call = server.getService().saveCheckDetails(submitBean);
        Logger.d(new Gson().toJson(submitBean).toString());
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
}

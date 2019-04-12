package com.sinodom.elevator.activity.elevator.nfc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.PaperlessAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.business.CheckDetailPaperlessEntity;
import com.sinodom.elevator.bean.nfc.StepBean;
import com.sinodom.elevator.db.PaperlessMaintenance;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.ImageUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.PictureUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.view.NoScrollListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
 * 业务-无纸化维保提交
 */
public class MaintenanceInspectionActivity extends BaseActivity implements BaseAdapter.OnItemClickListener {

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
    Button tvSubmit;
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
    private String taskID = "";   //类型id
    private List<StepBean> mListBean = new ArrayList<>();
    private List<CheckDetailPaperlessEntity> submitBean = new ArrayList<>();
    private PaperlessAdapter adapter;
    private int mPhotoPathPosition;
    private int mPosition;
    private String mPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_inspection2);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ID = getIntent().getStringExtra("ID");
        taskID = getIntent().getStringExtra("taskID");
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
                tvTitle.setText("月度保");
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
        if (TextUtil.isEmpty(UploadDate)) {
            tvEleInfoIstrue.setText("尚未维保");
        } else {
            String s3 = "" + UploadDate;
            String[] temp = null;
            temp = s3.split("T");
            String strTime = temp[1].substring(0, 5);
            tvEleInfoIstrue.setText("" + temp[0] + "  " + strTime);
        }
        tvEleInfoPlace.setText(InstallationAddress);
        adapter = new PaperlessAdapter(this);
        lvMaintenance.setAdapter(adapter);
        //标签
        adapter.setOnItemClickListener(this);
        //合格
        adapter.setOnCheckBoxClickListener(new BaseAdapter.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(View v, int position) {
                mPhotoPathPosition = position;
                submitBean.get(mPhotoPathPosition).setPassed(true);
                if (mListBean.get(position).isIsTakePhoto()) {
                    //合格拍照
                    Pictures();
                }
            }
        });
        //不合格
        adapter.setOnClickListener(new BaseAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int position) {
                mPhotoPathPosition = position;
                submitBean.get(mPhotoPathPosition).setPassed(false);
                Pictures();
            }
        });
        getStepList();
    }

    @Override
    public void onItemClick(View v, int position) {
        mPhotoPathPosition = position;
        mPosition = position;
        Intent intent = new Intent(MaintenanceInspectionActivity.this, NfcScanActivity.class);
//        intent.putExtra("LiftId", LiftNum);
        intent.putExtra("typeName", mListBean.get(position).getTermName());
        intent.putExtra("typeContent", mListBean.get(position).getTermDesc());
        startActivityForResult(intent, Constants.Code.GO_SCORE);
    }

    private void getStepList() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LiftID", "" + ID);
        map.put("CheckType", "" + CheckType);
        Call<ResponBean> call = server.getService().getStepList2(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(gson.toJson(response.body()));
                        mListBean = gson.fromJson(response.body().getData(), new TypeToken<ArrayList<StepBean>>() {
                        }.getType());
                        Logger.d("mListBean = " + mListBean.size());
                        for (StepBean bean : mListBean) {
                            if (!CheckType.equals("" + bean.getCheckType())) {
                                mListBean.remove(bean);
                            }
                        }
                        for (int i = 0; i < mListBean.size(); i++) {
                            CheckDetailPaperlessEntity checkDetailEntity = new CheckDetailPaperlessEntity();
                            checkDetailEntity.setLiftId(Integer.valueOf(ID).intValue());
                            checkDetailEntity.setDeptId(manager.getSession().getDeptId());
                            checkDetailEntity.setUserId(manager.getSession().getUserID());
                            checkDetailEntity.setStepId(mListBean.get(i).getID());
                            checkDetailEntity.setCType(CType);
                            checkDetailEntity.setID(Integer.valueOf(taskID));
                            if (mListBean.get(i).isIsNFC()) {
                                checkDetailEntity.setNFCCode("");
                            } else {
                                checkDetailEntity.setNFCCode(null);
                            }
                            checkDetailEntity.setLongitudeAndLatitude(LongitudeAndLatitude);
                            submitBean.add(checkDetailEntity);
                        }
                        adapter.setData(mListBean);
                        adapter.notifyDataSetChanged();
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
                tvSubmit.setEnabled(false);
                Submit();
                break;
        }
    }

    private void Submit() {
        for (int i = 0; i < submitBean.size(); i++) {
            submitBean.get(i).setRemark(mListBean.get(i).getDesc());
            submitBean.get(i).setCheckDate(DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
        }
        boolean boo = true;
        for (int i = 0; i < mListBean.size(); i++) {
            if (submitBean.get(i).isPassed()) {
                if (mListBean.get(i).isIsTakePhoto()) {
                    if (TextUtil.isEmpty(submitBean.get(i).getPhoto())) {
                        boo = false;
                        showToast("第" + (i + 1) + "项未维保");
                        tvSubmit.setEnabled(true);
                        break;
                    }
                }
            } else {
                if (TextUtil.isEmpty(submitBean.get(i).getPhoto())) {
                    boo = false;
                    showToast("第" + (i + 1) + "项未维保");
                    tvSubmit.setEnabled(true);
                    break;
                }
            }
        }
        if (boo) {
            String str = "";
            for (int i = 0; i < mListBean.size(); i++) {
                if (mListBean.get(i).isIsNFC() && !mListBean.get(i).isNfcScan() && !TextUtil.isEmpty(mListBean.get(i).getNFCNum())) {
                    str = str + (i + 1) + "、";
                }
            }
            if (!TextUtil.isEmpty(str)) {
                str = str.substring(0, str.length() - 1);
                new AlertDialog.Builder(MaintenanceInspectionActivity.this)
                        .setTitle("第" + str + "项缺少NFC扫描，请完成NFC扫描或在NFC扫描页拍照！")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //提交
//                                saveCheckDetails();
                                dialog.dismiss();
                                tvSubmit.setEnabled(true);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                tvSubmit.setEnabled(true);
                            }
                        })
                        .show();
            } else {
                //提交
                saveCheckDetails();
//                showToast("提交");
            }
        }
    }

    //提交到本地
    private void localCommit() {
        PaperlessMaintenance maintenance = new PaperlessMaintenance();
        maintenance.setJson(new Gson().toJson(submitBean).toString());
        Logger.d(new Gson().toJson(submitBean).toString());
        manager.addPaperlessMaintenance(maintenance);
        showToast("离线提交成功");
        setResult(Constants.Code.SCORE_OK);
        finish();
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

    //调用相机拍照
    public void Pictures() {
        Logger.d("拍照");
        try {
            //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean permission = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
                boolean permission1 = selfPermissionGranted(Manifest.permission.CAMERA);
                if (!permission || !permission1) {
                    ActivityCompat.requestPermissions(MaintenanceInspectionActivity.this,
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
        //扫码返回
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            String type = data.getStringExtra("type");
            if (type.equals("nfc")) {
                String code = data.getStringExtra("code");
                String uid = data.getStringExtra("uid");
//                if (TextUtil.isEmpty(mListBean.get(mPosition).getNFCCode()) && TextUtil.isEmpty(mListBean.get(mPosition).getNFCNum())) {
//                    showToast("该项目未绑定标签，请先绑定标签！");
//                } else if (mListBean.get(mPosition).getNFCCode().equals(code) &&
//                        mListBean.get(mPosition).getNFCNum().equals(uid)) {
//                    mListBean.get(mPosition).setNfcScan(true);
//                    submitBean.get(mPosition).setNFCCode(code);
//                    adapter.setData(mListBean);
//                    adapter.notifyDataSetChanged();
//                    showToast("识别成功！");
//                } else {
//                    showToast("该标签为错误标签，请重新扫描！");
//                }
                if (TextUtil.isEmpty(mListBean.get(mPosition).getNFCNum())) {
                    showToast("该项目未绑定标签，请先绑定标签！");
                } else if (mListBean.get(mPosition).getNFCNum().equals(uid)) {
                    mListBean.get(mPosition).setNfcScan(true);
                    submitBean.get(mPosition).setNFCCode(code);
                    adapter.setData(mListBean);
                    adapter.notifyDataSetChanged();
                    showToast("识别成功！");
                } else {
                    showToast("该标签为错误标签，请重新扫描！");
                }
            } else if (type.equals("photo")) {
                String path = data.getStringExtra("path");
                mListBean.get(mPosition).setNfcScan(true);
                submitBean.get(mPhotoPathPosition).setPhoto(PictureUtil.bitmapToString(path));
                adapter.setData(mListBean);
                adapter.notifyDataSetChanged();
            }
        }
        try {
            if (requestCode == 1 && resultCode == RESULT_OK) {
                //获取bitmap
                Bitmap bitmap = ImageUtil.getBitmap(mPhotoPath);
                //解决三星图片旋转问题
                Bitmap bitmapRotate = ImageUtil.rotateToDegrees(bitmap, ImageUtil.readPictureDegree(mPhotoPath));
                ImageUtil.savePicToSdcard(bitmapRotate, mPhotoPath);
                submitBean.get(mPhotoPathPosition).setPhoto(PictureUtil.bitmapToString(mPhotoPath));
            } else if (requestCode == 1 && resultCode != RESULT_OK) {
                Logger.d("取消拍照返回");
                mListBean.get(mPhotoPathPosition).setSelectType(0);
                adapter.setData(mListBean);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveCheckDetails() {
        showLoading("加载中...");
        Call<ResponBean> call = server.getService().saveNFCCheckDetails(submitBean);
        Logger.d(new Gson().toJson(submitBean).toString());
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    tvSubmit.setEnabled(true);
                    Gson gson = new Gson();
                    Logger.d("" + gson.toJson(response.body()));
                    if (response.body().isSuccess()) {
//                    ("提交成功！");
                        showToast("" + response.body().getMessage());
                        setResult(Constants.Code.SCORE_OK);
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
                    tvSubmit.setEnabled(true);
                    hideLoading();
                    showToast(parseError(throwable));
                    localCommit();
                }
            }
        });
    }
}
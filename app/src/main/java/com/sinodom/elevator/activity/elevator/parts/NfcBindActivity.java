package com.sinodom.elevator.activity.elevator.parts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.nfc.BaseNfcActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.parts.PartsBean;
import com.sinodom.elevator.db.Parts;
import com.sinodom.elevator.single.ApiService;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.HexUtils;
import com.sinodom.elevator.util.ImageUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.util.glide.GlideApp;
import com.sinodom.elevator.view.TimeSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
 * NFC绑定
 */
public class NfcBindActivity extends BaseNfcActivity {

    @BindView(R.id.tvNfcType)
    TextView tvNfcType;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvDevice)
    TextView tvDevice;
    @BindView(R.id.ivPhoto)
    ImageView ivPhoto;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.etBrand)
    EditText etBrand;
    @BindView(R.id.etModel)
    EditText etModel;
    @BindView(R.id.bSearch)
    Button bSearch;
    //时间
    private TimeSelector timeSelectorOver;
    private String mPhotoPath;
    private PartsBean.ListTypeBean mBean;
    private String mType;
    private String str;
    private int mLiftId;
    private PartsBean mListBean;
    public String mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_bind);
        ButterKnife.bind(this);
        mBean = (PartsBean.ListTypeBean) getIntent().getSerializableExtra("bean");
        mListBean = (PartsBean) getIntent().getSerializableExtra("liftBean");
        mLiftId = getIntent().getIntExtra("LiftId", 0);
        tvNfcType.setText(mBean.getPartsName());
        tvContent.setText(mBean.getPartsDesc());
        String address = !TextUtil.isEmpty(mListBean.getInstallationAddress()) && mListBean.getInstallationAddress().length() > 15 ?
                mListBean.getInstallationAddress().substring(mListBean.getInstallationAddress().length() - 15, mListBean.getInstallationAddress().length())
                : mListBean.getInstallationAddress();
        mText = mListBean.getLiftNum() + "`" +
                mListBean.getCertificateNum() + "`" +
                mBean.getPartsName() + "`" +
                address + "`" +
                DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
        setText(mText);
        if (!TextUtil.isEmpty(mBean.getPicture())) {
            String myUrl = BuildConfig.SERVER + mBean.getPicture();
            GlideApp.with(context).load(myUrl)
                    .thumbnail(0.1f)
                    .into(ivPhoto);
        }
    }

    @Override
    public void cry(String type) {
        mType = type;
        if (type.equals("phone")) {
            if(writeNdef(mText)){
                str = readNdef();
            }
            tvContent.setText("标签值：" + str + "\n" + "唯一值：" + HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
        } else if (type.equals("deke")) {
            tvContent.setText("标签值：" + content + "\n" + "唯一值：" + uid);
        }
    }

    @Override
    public void onDevice(String str) {
        super.onDevice(str);
        tvDevice.setText(str);
    }

    @Override
    public void onCard(String str) {
        super.onCard(str);
    }

    @OnClick({R.id.ivBack, R.id.ivPhoto, R.id.llTime, R.id.bSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivPhoto:
                //拍照
                pictures();
                break;
            case R.id.llTime:
                timeSelectorOver = new TimeSelector(context, new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        tvTime.setText(time);
                    }
                }, "1900-01-01 00:00", "2049-12-31 23:59");
                timeSelectorOver.show();
                break;
            case R.id.bSearch:
                if (TextUtil.isEmpty(mPhotoPath)) {
                    showToast("请拍照！");
                    return;
                }
                if (TextUtil.isEmpty(mType)) {
                    showToast("请扫描标签！");
                    return;
                }
                if (mType.equals("phone")) {
                    if (!TextUtil.isEmpty(str)) {
                        localCommit(HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
                    } else {
                        showToast("请重新扫描标签！");
                    }
                } else if (mType.equals("deke")) {
                    if (!TextUtil.isEmpty(content)) {
                        localCommit(uid);
                    } else {
                        showToast("请重新扫描标签！");
                    }
                }

//                PhoneUtil.isNetWorkAvailableOfGet("https://www.baidu.com", new Comparable<Boolean>() {
//                    @Override
//                    public int compareTo(Boolean available) {
//                        if (available) {
//                            // TODO 设备访问Internet正常
//                            //提交
//                            if (mType.equals("phone")) {
//                                if (!TextUtil.isEmpty(str)) {
//                                    bSearch.setClickable(false);
//                                    addLiftParts(mPhotoPath, HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
//                                } else {
//                                    showToast("绑定失败！");
//                                }
//                            } else if (mType.equals("deke")) {
//                                if (!TextUtil.isEmpty(content)) {
//                                    bSearch.setClickable(false);
//                                    addLiftParts(mPhotoPath, uid);
//                                } else {
//                                    showToast("绑定失败！");
//                                }
//                            }
//                        } else {
//                            // TODO 设备无法访问Internet
//                            if (mType.equals("phone")) {
//                                if (!TextUtil.isEmpty(str)) {
//                                    localCommit(HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
//                                } else {
//                                    showToast("绑定失败！");
//                                }
//                            } else if (mType.equals("deke")) {
//                                if (!TextUtil.isEmpty(content)) {
//                                    localCommit(uid);
//                                } else {
//                                    showToast("绑定失败！");
//                                }
//                            }
//                        }
//                        return 0;
//                    }
//
//                });
                break;
        }
    }

    //提交到本地
    private void localCommit(String tagId) {
        Parts parts = new Parts();
        parts.setManufacturer(tagId);
        parts.setBrand(etBrand.getText().toString().trim());
        parts.setModel(etModel.getText().toString().trim());
        parts.setInstallationTime(tvTime.getText().toString().trim());
        parts.setPartsTypeId(mBean.getID() + "");
        parts.setProductName(mBean.getPartsName());
        parts.setLiftId(mLiftId + "");
        parts.setPhotoPath(mPhotoPath);
        manager.addParts(parts);
        showToast("离线提交成功");
        finish();
    }

    //上传文件
    public void addLiftParts(String string, String tagId) {
        showLoading("提交中...");
        //构建要上传的文件
        File file = new File(string);
        RequestBody Manufacturer = RequestBody.create(MediaType.parse("multipart/form-data"), tagId);
        RequestBody Brand = RequestBody.create(MediaType.parse("multipart/form-data"), etBrand.getText().toString().trim());
        RequestBody Model = RequestBody.create(MediaType.parse("multipart/form-data"), etModel.getText().toString().trim());
        RequestBody InstallationTime = RequestBody.create(MediaType.parse("multipart/form-data"), tvTime.getText().toString().trim());
        RequestBody PartsTypeId = RequestBody.create(MediaType.parse("multipart/form-data"), mBean.getID() + "");
        RequestBody ProductName = RequestBody.create(MediaType.parse("multipart/form-data"), mBean.getPartsName());
        RequestBody LiftId = RequestBody.create(MediaType.parse("multipart/form-data"), mLiftId + "");
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
        Call<ResponBean> call = service.addLiftParts(Manufacturer, Brand, Model, InstallationTime, PartsTypeId, ProductName, LiftId, part);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                bSearch.setClickable(true);
                try {
                    if (response.body().isSuccess()) {
                        String data = response.body().getData();
                        Logger.d("成功 data = " + data);
                        showToast(response.body().getMessage());
                        setResult(Constants.Code.SCORE_OK);
                        finish();
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("上传失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    bSearch.setClickable(true);
                    Logger.d("失败");
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    //调用相机拍照
    public void pictures() {
        Logger.d("拍照");
        try {
            //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean permission = selfPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
                boolean permission1 = selfPermissionGranted(Manifest.permission.CAMERA);
                if (!permission || !permission1) {
                    ActivityCompat.requestPermissions(NfcBindActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 100);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int REQUEST_CODE_SELECT = 100;

    //开始拍照
    private void getPhoto() {
        Intent intent = new Intent(context, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
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
        if (requestCode == REQUEST_CODE_SELECT && resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mPhotoPath = images.get(0).path;
                //获取bitmap
                Bitmap bitmap = ImageUtil.getBitmap(mPhotoPath);
                //解决三星图片旋转问题
                Logger.e(ImageUtil.readPictureDegree(mPhotoPath) + "");
                Bitmap bitmapRotate = ImageUtil.rotateToDegrees(bitmap, ImageUtil.readPictureDegree(mPhotoPath));
                ImageUtil.savePicToSdcard(bitmapRotate, mPhotoPath);
                //加载图片
                GlideApp.with(context).load(mPhotoPath)
                        .thumbnail(0.1f)
                        .error(R.mipmap.ic_failure)
                        .placeholder(R.mipmap.ic_load)
                        .into(ivPhoto);
            } else {
                showToast("拍照失败！");
            }
        }
    }
}
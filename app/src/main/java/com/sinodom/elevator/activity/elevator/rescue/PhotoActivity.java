package com.sinodom.elevator.activity.elevator.rescue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.lift.PhotoBean;
import com.sinodom.elevator.bean.rescue.RescueDetailBean;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.Util;
import com.sinodom.elevator.util.glide.GlideApp;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.senab.photoview.PhotoView;

public class PhotoActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.pvView)
    PhotoView pvView;
    @BindView(R.id.tvCommit)
    TextView tvCommit;
    private int mLiftId;
    private int mTaskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mLiftId = intent.getIntExtra("LiftId", 0);
        mTaskId = intent.getIntExtra("TaskId", 0);
        tvCommit.setVisibility(View.GONE);
        showLoading();
        getPhoto(mTaskId);
    }

    private void init() {
        mLiftId = getIntent().getIntExtra("LiftId", 0);
        mTaskId = getIntent().getIntExtra("TaskId", 0);
        tvCommit.setVisibility(View.VISIBLE);
        showLoading();
        getPhoto(mTaskId);
    }

    @OnClick({R.id.ivBack, R.id.tvCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvCommit:
                if (mLiftId == 0) {
                    return;
                }
                showLoading();
                takePhoto();
                break;
        }
    }

    //拍照
    private void takePhoto() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("LiftId", mLiftId);
        map.put("Command", "8009");
        map.put("MobileSerialCode", manager.getDeviceToken());
        Call<ResponBean> call = server.getService().requestEquipmentPhotograph(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        showToast(response.body().getMessage());
                        getPhoto(mTaskId);
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("加载失败");
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

    //获取照片
    public void getPhoto(int taskId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("TaskId", taskId);
        Call<ResponBean> call = server.getService().getEquipmentFile(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        showToast(response.body().getMessage());
                        PhotoBean bean = new Gson().fromJson(response.body().getData(), PhotoBean.class);
                        String str = bean.getFileNmae().substring(1);
                        String myUrl = BuildConfig.SERVER + str;
                        Logger.d("myUrl=" + myUrl);
                        GlideApp.with(context)
                                .load(myUrl)
                                .thumbnail(0.1f)
                                .error(R.mipmap.ic_failure)
                                .placeholder(R.mipmap.ic_load)
                                .transform(new RotateTransformation(context, 180f))
                                .into(pvView);
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("加载失败");
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

    class RotateTransformation extends BitmapTransformation {

        private float rotateRotationAngle = 0f;

        public RotateTransformation(Context context, float rotateRotationAngle) {
            super(context);

            this.rotateRotationAngle = rotateRotationAngle;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Matrix matrix = new Matrix();

            matrix.postRotate(rotateRotationAngle);

            return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }
    }
}

package com.sinodom.elevator.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.bean.lift.PhotoBean;
import com.sinodom.elevator.single.Server;
import com.sinodom.elevator.util.glide.GlideApp;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<PhotoBean> data;

    public PhotoViewPagerAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<PhotoBean>();
    }

    public void setData(List<PhotoBean> data) {
        if (data == null) {
            data = new ArrayList<PhotoBean>();
        }
        this.data = data;
    }

    public List<PhotoBean> getData() {
        if (this.data == null) {
            this.data = new ArrayList<PhotoBean>();
        }
        return this.data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        String str = data.get(position).getFileNmae().substring(1);
        String myUrl = BuildConfig.SERVER + str;
        Logger.d("myUrl=" + myUrl);
        GlideApp.with(context)
                .load(myUrl)
                .thumbnail(0.1f)
                .error(R.mipmap.ic_failure)
                .placeholder(R.mipmap.ic_load)
                .transform(new RotateTransformation(context, 180f))
                .into(photoView);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
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
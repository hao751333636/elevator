package com.sinodom.elevator.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.bean.lift.PhotoBean;
import com.sinodom.elevator.util.glide.GlideApp;

import java.security.MessageDigest;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoGridViewAdapter extends BaseAdapter<PhotoBean> {

    public PhotoGridViewAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.grid_photo_item, null);
            holder = new ViewHolder(view);
            holder.ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final PhotoBean image = data.get(i);
        String str = image.getFileNmae().substring(1);
        String myUrl = BuildConfig.SERVER + str;
        Logger.d("myUrl=" + myUrl);
        GlideApp.with(context)
                .load(myUrl)
                .thumbnail(0.1f)
                .error(R.mipmap.ic_failure)
                .placeholder(R.mipmap.ic_load)
                .transform(new RotateTransformation(context, 180f))
                .into(holder.ivPhoto);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    try {
                        onItemClickListener.onItemClick(v, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.ivPhoto)
        ImageView ivPhoto;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
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
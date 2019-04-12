package com.sinodom.elevator.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.util.glide.GlideApp;

/**
 * Created by 安卓 on 2017/11/15.
 */

public class SeeImageView extends Dialog {

    private String mPhotoPath;
    private Context context;
    private boolean source;

    public SeeImageView(@NonNull Context context, int theme, String mPhotoPath, boolean source) {
        super(context, theme);
        this.mPhotoPath = mPhotoPath;
        this.context = context;
        this.source = source;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_see_image);
        ImageView imageView = (ImageView) findViewById(R.id.ivDisplay);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeeImageView.this.cancel();
            }
        });
        Logger.d("接收到mPhotoPath=" + mPhotoPath);

        if (source) {
            //加载图片
            GlideApp.with(context).load(mPhotoPath)
                    .thumbnail(0.1f)
                    .error(R.mipmap.ic_failure)
                    .placeholder(R.mipmap.ic_load)
                    .into(imageView);
        } else {
            String str = mPhotoPath.substring(2);
            Logger.d("url截取= " + str);
            String myUrl = BuildConfig.SERVER + str;
            GlideApp.with(context)
                    .load(myUrl)
                    .thumbnail(0.1f)
                    .error(R.mipmap.ic_failure)
                    .placeholder(R.mipmap.ic_load)
                    .into(imageView);

        }


    }
}

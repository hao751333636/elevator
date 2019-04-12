package com.sinodom.elevator.util.glide;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;
import com.sinodom.elevator.R;

/**
 * Created by GUO on 2017/11/28.
 */
@GlideExtension
public class ElevatorGlideExtension {
    /**
     * 将构造方法设为私有，作为工具类使用
     */
    private ElevatorGlideExtension() {
    }

    /**
     * 1.自己新增的方法的第一个参数必须是RequestOptions options
     * 2.方法必须是静态的
     *
     * @param options
     */
    @GlideOption
    public static void normal(RequestOptions options) {
        options.centerCrop()
                .placeholder(R.mipmap.image_error)
                .error(R.mipmap.image_error);
    }
}

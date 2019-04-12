package com.sinodom.elevator.util.glide;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.module.AppGlideModule;
import com.sinodom.elevator.util.PictureUtil;

import java.io.File;

/**
 * Created by GUO on 2017/11/28.
 */

@GlideModule
public class ElevatorGlideModule extends AppGlideModule {
    public static DiskCache cache;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        // 在 Android 中有两个主要的方法对图片进行解码：ARGB8888 和 RGB565。前者为每个像素使用了 4 个字节，
        // 后者仅为每个像素使用了 2 个字节。ARGB8888 的优势是图像质量更高以及能存储一个 alpha 通道。
        // Picasso 使用 ARGB8888，Glide 默认使用低质量的 RGB565。
        // 对于 Glide 使用者来说：你使用 Glide module 方法去改变解码规则。
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //设置缓存目录
        File cacheDir = PictureUtil.getPicturesDir();

        cache = DiskLruCacheWrapper.get(cacheDir, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);// 250 MB
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                return cache;
            }
        });
    }
}

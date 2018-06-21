package com.xy.wanandroid.util.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.GlideModule;


/**
 * Created by Yangmu on 2017/2/17.
 */

public class GlideCache implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

    }
////    (1). DiskCacheStrategy.RESULT:展示小大的图片缓存
////            (2). DiskCacheStrategy.ALL; 展示在控件中大小图片尺寸和原图都会缓存
////            (3). DiskCacheStrategy.NONE：不设置缓存
////            (4). DiskCacheStrategy.SOURCE：原图缓存
//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
////设置图片的显示格式ARGB_8888(指图片大小为32bit)
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
//        //设置磁盘缓存目录（和创建的缓存目录相同）
////        String downloadDirectoryPath= AppConfig.SAVEDFILE_LOCATION_BUFFER_BIT+"/GlideCache";
//        String downloadDirectoryPath= "/GlideCache";
//
//        //设置缓存的大小为100M
//        int cacheSize = 100*1024*1024;
//
//        builder.setDiskCache(new DiskLruCacheFactory(downloadDirectoryPath, cacheSize));
//
//        //设置内存缓存大小
//        int maxMemory = (int) Runtime.getRuntime().maxMemory();//获取系统分配给应用的总内存大小
//        int memoryCacheSize = maxMemory / 8;//设置图片内存缓存占用八分之一
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
//    }
//
//    @Override
//    public void registerComponents(Context context, Glide glide) {
//
//    }

}

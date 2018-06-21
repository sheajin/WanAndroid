package com.xy.wanandroid.util.glide;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Glide框架加载圆形图片
 * Created by Yangmu on 2017/2/17.
 * 使用方法
 * private RequestManager glideRequest;
 glideRequest = Glide.with(this);
 glideRequest.load("...").transform(new GlideCircleTransform(context)).into(imageView);
 这里不得不强调下Glide的一个强大的功能，当你在With后面的传Activity或者Fragment，
 Glide就可以根据当前Activity或者Fragment的生命周期维护图片的生命周期，
 比如但activity销毁的时候，就会自动取消需要加载的图片
 */

public class GlideCircleTransform extends BitmapTransformation {
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return null;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
//    private static float radius = 0f;
//
//    public GlideCircleTransform(Context context) {
//        this(context, 4);
//    }
//
//    public GlideCircleTransform(Context context, int dp) {
//        super(context);
//        radius = Resources.getSystem().getDisplayMetrics().density * dp;
//    }
//
//    @Override
//    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//        return roundCrop(pool, toTransform);
//        //Glide 4.0.0以上则使用下面的
//        //Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
//        //return roundCrop(pool, bitmap);
//    }
//
//    private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
//        if (source == null) return null;
//
//        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        if (result == null) {
//            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
//        }
//
//        Canvas canvas = new Canvas(result);
//        Paint paint = new Paint();
//        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//        paint.setAntiAlias(true);
//        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
//        canvas.drawRoundRect(rectF, radius, radius, paint);
//        return result;
//    }
//
//    @Override
//    public String getId() {
//        return getClass().getName() + Math.round(radius);
//    }

}
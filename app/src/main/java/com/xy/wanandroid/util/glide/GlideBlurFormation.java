package com.xy.wanandroid.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by jxy on 2018/8/17.
 */

public class GlideBlurFormation extends BitmapTransformation {

    private Context context;

    public GlideBlurFormation(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return BlurBitmapUtil.instance().blurBitmap(context, toTransform, 20, outWidth, outHeight);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}

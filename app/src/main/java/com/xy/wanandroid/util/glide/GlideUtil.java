package com.xy.wanandroid.util.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.xy.wanandroid.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by jxy on 2017/8/9.
 */

public class GlideUtil {
    /**
     * 加载res
     */
    public static void loadImage(Context context, Object resId, ImageView imageView) {
        Glide.with(context)
                .load(resId)
                .transition(withCrossFade(android.R.anim.fade_in, 100))
                .into(imageView);
    }

    /**
     * 加载圆角
     */
    public static void loadRoundImage(Context context, Object resId, ImageView imageView) {
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        Glide.with(context).
                load(resId).
                apply(mRequestOptions)
                .into(imageView);
    }

    /**
     * 禁止加载gif
     */
    public static void loadImageWithoutGif(Context context, Object resId, ImageView imageView) {
//        RequestOptions mRequestOptions = RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL).dontAnimate(); //禁止播放动画
        Glide.with(context)
                .asBitmap()
                .load(resId)
//                .apply(mRequestOptions)
                .into(imageView);
    }

    /**
     * 加载大图预览图
     */
    public static void loadPlaceImage(Context context, Object resId, ImageView imageView) {
        RequestOptions mRequestOptions = RequestOptions
                .diskCacheStrategyOf(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.icon_placeholder_welfare)
                .error(R.drawable.icon_placeholder_welfare);
        Glide.with(context)
                .load(resId)
                .apply(mRequestOptions)
                .into(imageView);
    }

    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(Context context, final String imageUrl, final ImageView imageView) {
        RequestOptions mOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .apply(mOptions)
                .into(imageView);
    }

    /**
     * 高斯模糊
     */
    public static void loadBlurImage(Context context, Object resId, ImageView imageView) {
        RequestOptions mOptions = new RequestOptions().bitmapTransform(new GlideBlurFormation(context));
        Glide.with(context)
                .load(resId)
                .apply(mOptions)
                .into(imageView);
    }


}

package com.xy.wanandroid.util.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

/**
 * Created by jxy on 2018/8/17.
 */

public class BlurBitmapUtil {

    private static BlurBitmapUtil sInstance;

    private BlurBitmapUtil() {
    }

    public static BlurBitmapUtil instance() {
        if (sInstance == null) {
            synchronized (BlurBitmapUtil.class) {
                if (sInstance == null) {
                    sInstance = new BlurBitmapUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * @param context 上下文对象
     * @param source  需要模糊的图片
     * @return 模糊处理后的Bitmap
     */
      Bitmap rsBlur(Context context, Bitmap source, int radius) {
        Bitmap inputBmp = source;
        //(1)
        RenderScript renderScript = RenderScript.create(context);

        // Allocate memory for Renderscript to work with
        //(2)
        final Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        //(3)
        // Load up an instance of the specific script that we want to use.
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        //(4)
        scriptIntrinsicBlur.setInput(input);
        //(5)
        // Set the blur radius
        scriptIntrinsicBlur.setRadius(radius);
        //(6)
        // Start the ScriptIntrinisicBlur
        scriptIntrinsicBlur.forEach(output);
        //(7)
        // Copy the output to the blurred bitmap
        output.copyTo(inputBmp);
        //(8)
        renderScript.destroy();
        return inputBmp;
    }
}
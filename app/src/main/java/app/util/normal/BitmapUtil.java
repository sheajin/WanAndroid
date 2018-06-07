package app.util.normal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @author: xiaolijuan
 * @description: 图片工具类
 * @projectName: BitmapDome
 * @date: 2016-01-19
 * @time: 21:48
 */
public class BitmapUtil {
    // 默认圆角半径
    private static final int DEFAULT_CORNER_RADIUS_DIP = 8;
    // 默认边框宽度
    private static final int DEFAULT_STROKE_WIDTH_DIP = 2;
    // 边框的颜色
    private static final int DEFAULT_STROKE_COLOR = Color.WHITE;
    // 中间数字的颜色
    private static final int DEFAULT_NUM_COLOR = Color.parseColor("#CCFF0000");

    /**
     * 生成有数字的图片(没有边框)
     *
     * @param context   上下文
     * @param icon      图片
     * @param isShowNum 是否要绘制数字
     * @param num       数字字符串：整型数字 超过99，显示为"99+"
     * @return 重新生成带数字的图片
     */

    public static Bitmap generatorNumIcon2(Context context, Bitmap icon,
                                           boolean isShowNum, String num) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        // 基准屏幕密度
        float baseDensity = 2.3f;
        float factor = dm.density / baseDensity;

        // 初始化画布
        int iconSize = (int) context.getResources().getDimension(
                android.R.dimen.app_icon_size);
        Bitmap numIcon = Bitmap.createBitmap(iconSize, iconSize,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(numIcon);

        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
        Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
        Rect dst = new Rect(0, 0, iconSize, iconSize);
        canvas.drawBitmap(icon, src, dst, iconPaint);

        if (isShowNum) {

            if (TextUtils.isEmpty(num)) {
                num = "0";
            }

            if (!TextUtils.isDigitsOnly(num)) {
                // 非数字
                num = "0";
            }

            int numInt = Integer.valueOf(num);

            if (numInt > 99) {// 超过99
                num = "99+";
            }

            // 启用抗锯齿和使用设备的文本字体大小
            // 测量文本占用的宽度
            Paint numPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
            numPaint.setColor(Color.WHITE);
            numPaint.setTextSize(30f * factor);
            numPaint.setTypeface(Typeface.DEFAULT_BOLD);
            int textWidth = (int) numPaint.measureText(num, 0, num.length());

            /**
             * 绘制圆角矩形背景
             */
            int backgroundHeight = (int) (2 * 25 * factor);
            int backgroundWidth = textWidth > backgroundHeight ? (int) (textWidth + 20 * factor)
                    : backgroundHeight;//圆角矩形背景的宽度

            canvas.save();// 保存状态

            ShapeDrawable drawable = getDefaultBackground(context);
            drawable.setIntrinsicHeight(backgroundHeight);
            drawable.setIntrinsicWidth(backgroundWidth);
            drawable.setBounds(0, 0, backgroundWidth, backgroundHeight);
            canvas.translate(iconSize - backgroundWidth, 0);
            drawable.draw(canvas);

            canvas.restore();// 重置为之前保存的状态

            // 绘制数字
            canvas.drawText(num, (float) (iconSize - (backgroundWidth + textWidth) / 2), 22 * factor, numPaint);
        }
        return numIcon;
    }

    /**
     * 生成有数字的图片(有边框的)
     *
     * @param context   上下文
     * @param icon      图片
     * @param isShowNum 是否要绘制数字
     * @param num       数字字符串：整型数字 超过99，显示为"99+"
     * @return 重新生成带数字的图片
     */
    public static Bitmap generatorNumIcon3(Context context, Bitmap icon,
                                           boolean isShowNum, String num) {

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        // 基准屏幕密度
        float baseDensity = 2.2f;
        float factor = dm.density / baseDensity;

        // 初始化画布
        int iconSize = (int) context.getResources().getDimension(
                android.R.dimen.app_icon_size);

        Bitmap numIcon = Bitmap.createBitmap(iconSize, iconSize,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(numIcon);

        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
        Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
        Rect dst = new Rect(0, 0, iconSize, iconSize);
        canvas.drawBitmap(icon, src, dst, iconPaint);

        if (isShowNum) {

            if (TextUtils.isEmpty(num)) {
                num = "0";
            }

            if (!TextUtils.isDigitsOnly(num)) {
                // 非数字
                num = "0";
            }

            int numInt = Integer.valueOf(num);

            if (numInt > 99) {// 超过99
                num = "99+";
            }

            // 启用抗锯齿和使用设备的文本字体大小
            // 测量文本占用的宽度
            Paint numPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                    | Paint.DEV_KERN_TEXT_FLAG);
            numPaint.setColor(Color.WHITE);
            numPaint.setTextSize(20f * factor);
            numPaint.setTypeface(Typeface.DEFAULT_BOLD);
            int textWidth = (int) numPaint.measureText(num, 0, num.length());

            /**
             * 绘制圆角矩形背景：先画边框，再画内部的圆角矩形
             */
            // 圆角矩形背景的宽度
            int backgroundHeight = (int) (2 * 15 * factor);
            int backgroundWidth = textWidth > backgroundHeight ? (int) (textWidth + 10 * factor)
                    : backgroundHeight;
            // 边框的宽度
            int strokeThickness = (int) (2 * factor);

            canvas.save();// 保存状态

            int strokeHeight = backgroundHeight + strokeThickness * 2;
            int strokeWidth = textWidth > strokeHeight ? (int) (textWidth + 10
                    * factor + 2 * strokeThickness) : strokeHeight;
            ShapeDrawable outStroke = getDefaultStrokeDrawable(context);
            outStroke.setIntrinsicHeight(strokeHeight);
            outStroke.setIntrinsicWidth(strokeWidth);
            outStroke.setBounds(0, 0, strokeWidth, strokeHeight);
            canvas.translate(iconSize - strokeWidth - strokeThickness,
                    strokeThickness);
            outStroke.draw(canvas);

            canvas.restore();// 重置为之前保存的状态

            canvas.save();// 保存状态

            ShapeDrawable drawable = getDefaultBackground(context);
            drawable.setIntrinsicHeight((int) (backgroundHeight + 2 * factor));
            drawable.setIntrinsicWidth((int) (backgroundWidth + 2 * factor));
            drawable.setBounds(0, 0, backgroundWidth, backgroundHeight);
            canvas.translate(iconSize - backgroundWidth - 2 * strokeThickness,
                    2 * strokeThickness);
            drawable.draw(canvas);

            canvas.restore();// 重置为之前保存的状态

            // 绘制数字
            canvas.drawText(
                    num,
                    (float) (iconSize
                            - (backgroundWidth + textWidth + 4 * strokeThickness)
                            / 2 - 1), (22) * factor + 2 * strokeThickness,
                    numPaint);
        }
        return numIcon;
    }


    /**
     * 圆角矩形，相当于用<shape>的xml的背景
     *
     * @param context 上下文
     * @return
     */
    private static ShapeDrawable getDefaultBackground(Context context) {

        // 这个是为了应对不同分辨率的手机，屏幕兼容性
        int r = dipToPixels(context, DEFAULT_CORNER_RADIUS_DIP);
        float[] outerR = new float[]{r, r, r, r, r, r, r, r};

        // 圆角矩形
        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setColor(DEFAULT_NUM_COLOR);// 设置颜色
        return drawable;

    }

    /**
     * 圆角矩形，相当于用<shape>的xml的背景
     *
     * @param context 上下文
     * @return
     */
    private static ShapeDrawable getDefaultStrokeDrawable(Context context) {
        // 这个是为了应对不同分辨率的手机，屏幕兼容性
        int r = dipToPixels(context, DEFAULT_CORNER_RADIUS_DIP);
        int distance = dipToPixels(context, DEFAULT_STROKE_WIDTH_DIP);
        float[] outerR = new float[]{r, r, r, r, r, r, r, r};

        // 圆角矩形
        RoundRectShape rr = new RoundRectShape(outerR, null, null);
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setStrokeWidth(distance);
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.getPaint().setColor(DEFAULT_STROKE_COLOR);// 设置颜色
        return drawable;
    }

    /**
     * dp to px
     *
     * @param context 上下文
     * @param dip
     * @return
     */
    public static int dipToPixels(Context context, int dip) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics());
        return (int) px;
    }

    /**
     * 在原图右上角画圆形状态标志，其中icon为原图，color为所需状态标示的颜色，一般取值为"Color.**",r为所画圆的半径
     *
     * @param icon  原图
     * @param res   获取Resources
     * @param color 所需状态标示的颜色,一般取值为"Color.**"
     * @param r     所画圆的半径
     * @return
     */
    public static Bitmap generatorStatusIcon(Bitmap icon, Resources res,
                                             int color, int r) {
        // 初始化画布
        int iconSize = (int) res.getDimension(android.R.dimen.app_icon_size);
        Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(contactIcon);

        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
        Rect src = new Rect(0, 0, icon.getWidth(), icon.getHeight());
        Rect dst = new Rect(0, 0, iconSize, iconSize);
        canvas.drawBitmap(icon, src, dst, iconPaint);

        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(color);

        canvas.drawCircle(iconSize - r, r, r, countPaint);
        return contactIcon;
    }

    /**
     * 传入一张Bitmap型的方形图片使之变成圆形
     *
     * @param bitmap 图片
     * @return
     */
    public static Bitmap makeRoundCorner(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int left = 0, top = 0, right = width, bottom = height;
        float roundPx = height / 2;
        if (width > height) {
            left = (width - height) / 2;
            top = 0;
            right = left + height;
            bottom = height;
        } else if (height > width) {
            left = 0;
            top = (height - width) / 2;
            right = width;
            bottom = top + width;
            roundPx = width / 2;
        }

        Bitmap output = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(left, top, right, bottom);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 传入一张Bitmap型的方形图片使之变成圆角矩形，ratio值在1以上就很明显
     *
     * @param bitmap 图片
     * @param ratio  比例，这里是宽高与半径的比例
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, float ratio) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, bitmap.getWidth() / ratio,
                bitmap.getHeight() / ratio, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 使bitmap图标边角圆弧化，pixels值在10以上才比较明显，变化慢
     *
     * @param bitmap 图片
     * @param pixels 圆角的半径
     * @return
     */
    public static Bitmap toRoundCorner_1(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * 在一张背景图片上合成四个头像
     *
     * @param backGroundIcon 背景图片
     * @param res            获取Resources
     * @param first          图片1
     * @param second         图片2
     * @param third          图片3
     * @param four           图片4
     * @return
     */
    public static Bitmap add4Bitmap(Bitmap backGroundIcon, Resources res,
                                    Bitmap first, Bitmap second, Bitmap third, Bitmap four) {
        int iconSize = (int) res.getDimension(android.R.dimen.app_icon_size);
        Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(contactIcon);

        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果
        Rect src = new Rect(0, 0, backGroundIcon.getWidth(),
                backGroundIcon.getHeight());
        Rect dst = new Rect(0, 0, iconSize, iconSize);
        canvas.drawBitmap(backGroundIcon, src, dst, iconPaint);

        int width = iconSize / 2;
        int height = iconSize / 2;
        canvas.drawBitmap(pictureZoom(first, iconSize / 2, iconSize / 2), 0, 0,
                iconPaint);
        canvas.drawBitmap(pictureZoom(second, iconSize / 2, iconSize / 2),
                width, 0, iconPaint);
        canvas.drawBitmap(pictureZoom(third, iconSize / 2, iconSize / 2), 0,
                height, iconPaint);
        canvas.drawBitmap(pictureZoom(four, iconSize / 2, iconSize / 2), width,
                height, iconPaint);
        return contactIcon;
    }

    /**
     * 图片缩放
     *
     * @param bitMap    原图片
     * @param newWidth  新的宽
     * @param newHeight 新的高
     * @return
     */
    public static Bitmap pictureZoom(Bitmap bitMap, int newWidth, int newHeight) {

        int width = bitMap.getWidth();
        int height = bitMap.getHeight();

        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        // 得到新的图片
        bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);

        return bitMap;
    }

    /**
     * 渲染成灰色图片
     *
     * @param bitmap 图片
     * @return
     */
    public static final Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }
}

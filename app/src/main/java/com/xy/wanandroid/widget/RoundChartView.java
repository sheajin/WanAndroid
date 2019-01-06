package com.xy.wanandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jxy on 2018/10/19
 */
public class RoundChartView extends View {

    private int radius;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF();

    public RoundChartView(Context context) {
        super(context);
    }

    public RoundChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectF.set(100, 100, 100 + radius * 2, 100 + radius * 2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(80);
        paint.setColor(Color.BLUE);
        canvas.drawArc(rectF, 0, 120, false, paint);
        paint.setColor(Color.GREEN);
        canvas.drawArc(rectF, 120, 120, false, paint);
        paint.setColor(Color.RED);
        canvas.drawArc(rectF, 240, 120, false, paint);

    }

    public class RoundInfo {
        public int color;
        public int radius;
        public int value;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
            invalidate();
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
            invalidate();
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
            invalidate();
        }
    }
}

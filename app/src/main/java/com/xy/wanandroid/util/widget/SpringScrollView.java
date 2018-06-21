package com.xy.wanandroid.util.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jxy on 2018/6/17.
 */
public class SpringScrollView extends NestedScrollView {

    public SpringScrollView(Context context) {
        super(context);
    }

    public SpringScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpringScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 记录下拉View时的起始位置
     */
    private float pullY = 0f;
    /**
     * 记录扩展放大View的原始宽度
     */
    private int viewWidth = 0;
    /**
     * 记录扩展放大View的原始高度
     */
    private int viewHeight = 0;
    /**
     * View是否下拉扩展
     */
    private boolean isUnfolding = false;
    /**
     * View 是否被下拉放大
     */
    private boolean isZoom = false;
    /**
     * 放大系数
     */
    private float zoomRatio = 0.4f;
    /**
     * 最大放大比例
     */
    private float maxZoomRatio = 2f;
    /**
     * 回弹时间系数
     */
    private float replyTimeRatio = 0.5f;
    /**
     * 头部视图隐藏高度
     */
    private int hideHeight = 0;
    /**
     * 扩展放大的头部视图
     */
    private View headView;
    /**
     * 隐藏头部视图高度的比例   为4时即隐藏头部视图顶部1/4高度与底部1/4高度
     */
    private int hideRatio = 4;

    public void setHideRatio(int hideRatio) {
        this.hideRatio = hideRatio;
    }

    public void setZoomRatio(float zoomRatio) {
        this.zoomRatio = zoomRatio;
    }

    public void setMaxZoomRatio(float maxZoomRatio) {
        this.maxZoomRatio = maxZoomRatio;
    }

    public void setReplyTimeRatio(float replyTimeRatio) {
        this.replyTimeRatio = replyTimeRatio;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取头部视图的原始宽高
        if (viewWidth <= 0 || viewHeight <= 0) {
            viewWidth = headView.getMeasuredWidth();
            viewHeight = headView.getMeasuredHeight();
        }
        //绘制视图时隐藏头部View的顶部和底部
        if (hideHeight == 0) {
            hideHeight = viewHeight / hideRatio;
            ViewGroup.LayoutParams layoutParams = headView.getLayoutParams();
            ((MarginLayoutParams) layoutParams).setMargins(0, -hideHeight, 0, -hideHeight);
            headView.setLayoutParams(layoutParams);
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        不可过度滚动，否则上移后下拉会出现部分空白的情况
        setOverScrollMode(OVER_SCROLL_NEVER);
//        获得默认第一个view
        if (getChildAt(0) != null && getChildAt(0) instanceof ViewGroup && headView == null) {
            ViewGroup mViewGroup = (ViewGroup) getChildAt(0);
            if (mViewGroup.getChildCount() > 0) {
                headView = mViewGroup.getChildAt(0);
            }
        }
    }


    /**
     * 下拉扩展的高度
     */
    private float unfoldHeight = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (viewWidth <= 0 || viewHeight <= 0) {
            viewWidth = headView.getMeasuredWidth();
            viewHeight = headView.getMeasuredHeight();
        }
        if (headView == null || viewWidth <= 0 || viewHeight <= 0) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!isUnfolding) {
                    if (getScrollY() == 0) {
                        pullY = ev.getY();//滑动到顶部时，记录位置
                    } else {
                        break;
                    }
                }
                int distance = (int) ((ev.getY() - pullY) * zoomRatio);
                if (distance < 0) break;//若往下滑动
                isUnfolding = true;
                if (hideHeight > distance) {
                    unfoldImage(distance);
                    unfoldHeight = distance;
                    return true;
                }
                setZoom(distance - hideHeight);

                return true;
            case MotionEvent.ACTION_UP:
                isUnfolding = false;
                new Handler().postDelayed(() -> {
                    if (!isUnfolding) {
                        replyView();
                    }
                }, 100);

                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 扩展头部视图
     *
     * @param distance 顶部和底部扩展的距离
     */
    private void unfoldImage(float distance) {
        ViewGroup.LayoutParams layoutParams = headView.getLayoutParams();
        //下拉时保持居中,设置顶部和底部的边距让隐藏的部分视图显示出来，达到扩展目的
        ((MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width
                        - viewWidth) / 2 > 0 ? 0 : -(layoutParams.width - viewWidth) / 2,
                (int) (distance - hideHeight), 0, (int) (distance - hideHeight));
        headView.setLayoutParams(layoutParams);
    }

    /**
     * 放大头部View
     *
     * @param distance 放大的距离
     */
    private void setZoom(float distance) {
        float scaleTimes = (float) ((viewWidth + distance) / (viewWidth * 1.0));
//        如超过最大放大倍数，直接返回
        if (scaleTimes > maxZoomRatio) return;

        ViewGroup.LayoutParams layoutParams = headView.getLayoutParams();
        layoutParams.width = (int) (viewWidth + distance);
        layoutParams.height = (int) (viewHeight * ((viewWidth + distance) / viewWidth));
//        设置控件水平居中
        ((MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - viewWidth) / 2, 0, 0, 0);
        headView.setLayoutParams(layoutParams);
        isZoom = true;
    }

    /**
     * 头部视图还原
     */
    private void replyView() {
        /**
         * 如果头部视图被放大，添加动画还原放大的头部视图
         */
        if (isZoom) {
            final float distance = headView.getMeasuredWidth() - viewWidth;
            // 设置动画
            ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * replyTimeRatio));
            anim.addUpdateListener(animation -> setZoom((Float) animation.getAnimatedValue()));
            anim.start();
        }
        /**
         * 将扩展出来的头部视图还原
         */
        ValueAnimator unfold = ObjectAnimator.ofFloat(unfoldHeight, 0.0F).setDuration((long) (unfoldHeight));
        unfold.addUpdateListener(animation -> unfoldImage((Float) animation.getAnimatedValue()));
        unfold.start();
        unfoldHeight = 0;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) onScrollListener.onScroll(l, t, oldl, oldt);
    }

    private SpringScrollView.OnScrollListener onScrollListener;

    public void setOnScrollListener(SpringScrollView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 滑动监听
     */
    public interface OnScrollListener {
        void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);

    }
}

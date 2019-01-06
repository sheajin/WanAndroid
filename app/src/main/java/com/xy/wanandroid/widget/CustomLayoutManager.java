package com.xy.wanandroid.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.xy.wanandroid.base.app.MyApplication;

/**
 * Created by jxy on 2018/10/30
 */
public class CustomLayoutManager extends LinearLayoutManager {

    private int totalHeight;
    private int verticalScrollOffset;

    public CustomLayoutManager(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        // 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        detachAndScrapAttachedViews(recycler);
        calculateChildrenSite1(recycler);
    }

    private void calculateChildrenSite1(RecyclerView.Recycler recycler) {
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            //遍历recycler的所有view
            View view = recycler.getViewForPosition(i);
            addView(view);
//            //通知测量view的margin值
            measureChildWithMargins(view, 0, 0);
            //计算view实际大小，包括ItemDecorator中设置的偏移量
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);

            Rect rect = new Rect();
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
            calculateItemDecorationsForChild(view, rect);
            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View
            //包括ItemDecorator设置的距离。
            layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
            totalHeight += height;
        }
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //列表向下滚动dy为正，列表向上滚动dy为负，这点与Android坐标系保持一致。
        //实际要滑动的距离
        int travel = dy;

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;

        // 调用该方法通知view在y方向上移动指定距离
        offsetChildrenVertical(-travel);

        return travel;
    }

    private int getVerticalSpace() {
        //计算RecyclerView的可用高度，除去上下Padding值
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    @Override
    public boolean canScrollHorizontally() {
        //返回true表示可以横向滑动
        return super.canScrollHorizontally();
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //在这个方法中处理水平滑动
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    private void calculateChildrenSite2(RecyclerView.Recycler recycler) {
        totalHeight = 0;
        for (int i = 0; i < getItemCount(); i++) {
            //遍历recycler的所有view
            View view = recycler.getViewForPosition(i);
            addView(view);
            //我们自己指定ItemView的尺寸。
            WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
            int screenWidth = wm.getDefaultDisplay().getWidth();
            measureChildWithMargins(view, screenWidth / 2, 0);
            //计算view实际大小，包括ItemDecorator中设置的偏移量
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);

            Rect rect = new Rect();
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
            calculateItemDecorationsForChild(view, rect);

            if (i % 2 == 0) { //当i能被2整除时，是左，否则是右。
                //左
                layoutDecoratedWithMargins(view, 0, totalHeight, screenWidth / 2, totalHeight + height);
            } else {
                //右，需要换行
                layoutDecoratedWithMargins(view, screenWidth / 2, totalHeight, screenWidth, totalHeight + height);
                totalHeight = totalHeight + height;
            }
        }
    }
}

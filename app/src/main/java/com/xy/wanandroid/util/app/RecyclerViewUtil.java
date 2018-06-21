package com.xy.wanandroid.util.app;

import android.support.v7.widget.RecyclerView;

public class RecyclerViewUtil {
    public static void nestedRecyclerView(RecyclerView view, RecyclerView.LayoutManager manager) {
        view.setLayoutManager(manager);
        manager.setAutoMeasureEnabled(true);
        view.setHasFixedSize(true);
        view.setNestedScrollingEnabled(false);
    }
}

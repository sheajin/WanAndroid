package com.xy.wanandroid.ui.drawer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;

import java.util.List;

/**
 * Created by jxy on 2018/7/19.
 */

public class LiveListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public LiveListAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_sort, "英雄联盟");
    }
}

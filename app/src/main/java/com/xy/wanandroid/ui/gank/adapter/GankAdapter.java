package com.xy.wanandroid.ui.gank.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.data.gank.EverydayData;

import java.util.List;

/**
 * Created by jxy on 2018/8/8.
 */

public class GankAdapter extends BaseQuickAdapter<EverydayData.ResultsBean.AndroidBean, BaseViewHolder> {

    public GankAdapter(int layoutResId, @Nullable List<EverydayData.ResultsBean.AndroidBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EverydayData.ResultsBean.AndroidBean item) {

    }
}

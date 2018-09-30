package com.xy.wanandroid.ui.gank.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.data.gank.HotMovieBean;

import java.util.List;

/**
 * Created by jxy on 2018/9/27
 */

public class MovieDetailsAdapter extends BaseQuickAdapter<HotMovieBean.SubjectsBean, BaseViewHolder> {

    public MovieDetailsAdapter(int layoutResId, @Nullable List<HotMovieBean.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotMovieBean.SubjectsBean item) {

    }
}

package com.xy.wanandroid.ui.gank.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by jxy on 2018/8/15.
 */

public class DoubanTopAdapter extends BaseQuickAdapter<HotMovieBean.SubjectsBean, BaseViewHolder> {

    public DoubanTopAdapter(int layoutResId, @Nullable List<HotMovieBean.SubjectsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotMovieBean.SubjectsBean item) {
        helper.setText(R.id.tv_name, item.getTitle());
        helper.setText(R.id.tv_score, "评分: " + String.valueOf(item.getRating().getAverage()));
        GlideUtil.loadPlaceImage(MyApplication.getInstance(), item.getImages().getMedium(), helper.getView(R.id.image_preview));
    }
}

package com.xy.wanandroid.ui.gank.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;

import java.util.List;

/**
 * Created by jxy on 2018/10/26
 */
//public class HotMovieDetailAdapter extends BaseQuickAdapter<MovieDetailBean.CastsBean, BaseViewHolder> {
public class HotMovieDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HotMovieDetailAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_name, item );
        helper.setText(R.id.tv_character, MyApplication.getInstance().getString(R.string.actors));
    }

//    public HotMovieDetailAdapter(int layoutResId, @Nullable List<MovieDetailBean.CastsBean> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, MovieDetailBean.CastsBean item) {
//        helper.setText(R.id.tv_name, item.getName());
//        helper.setText(R.id.tv_character, MyApplication.getInstance().getString(R.string.actors));
//        GlideUtil.loadImage(MyApplication.getInstance(), item.getAvatars().getMedium(), helper.getView(R.id.image_head));
//    }
}

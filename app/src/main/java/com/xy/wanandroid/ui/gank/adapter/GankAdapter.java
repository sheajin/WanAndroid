package com.xy.wanandroid.ui.gank.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.gank.RecommendData;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.ui.gank.viewholder.GankViewHolder;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by jxy on 2018/8/8.
 */

public class GankAdapter extends BaseQuickAdapter<RecommendData.ResultsBean, GankViewHolder> {

    private String lastType = "";

    public GankAdapter(int layoutResId, @Nullable List<RecommendData.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(GankViewHolder helper, RecommendData.ResultsBean item) {
        helper.viewType.setVisibility(item.getType().equals(lastType) ? View.INVISIBLE : View.VISIBLE);
        helper.setText(R.id.tv_cate_one, item.getType());
        if (!item.getType().equals(Constant.WELFARE)) {
            helper.setText(R.id.tv_content_one, item.getDesc());
        }
        if (!item.getPublishedAt().equals("")) {
            GlideUtil.loadImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.imageView);
        }
        lastType = item.getType();
    }

}

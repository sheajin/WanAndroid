package com.xy.wanandroid.ui.gank.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.gank.RecommendData;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by jxy on 2018/8/8.
 */

public class GankAdapter extends BaseQuickAdapter<RecommendData.ResultsBean, BaseViewHolder> {

//    public GankAdapter(List<RecommendData.ResultsBean> data) {
//        super(data);
//        addItemType(RecommendData.ResultsBean.ENTITY_ITEM, R.layout.item_gank);
//        addItemType(RecommendData.ResultsBean.ENTITY_TITLE, R.layout.item_gank_title);
//    }

    public GankAdapter(int layoutResId, @Nullable List<RecommendData.ResultsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendData.ResultsBean item) {
        helper.setText(R.id.tv_cate, item.getType());
        helper.setText(R.id.tv_content, item.getDesc());
        GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_view));
//        switch (helper.getItemViewType()) {
//            case RecommendData.ResultsBean.ENTITY_ITEM:
//                helper.setText(R.id.tv_cate, item.getType());
//                helper.setText(R.id.tv_content, item.getDesc());
//                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_view));
//                break;
//            case RecommendData.ResultsBean.ENTITY_TITLE:
//                helper.setText(R.id.tv_cate, item.getType());
//                break;
//        }
    }
}

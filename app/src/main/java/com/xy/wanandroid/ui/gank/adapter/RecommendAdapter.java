package com.xy.wanandroid.ui.gank.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.gank.RecommendEntity;
import com.xy.wanandroid.ui.gank.viewholder.RecommendViewHolder;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by jxy on 2018/8/5.
 */

public class RecommendAdapter extends BaseMultiItemQuickAdapter<RecommendEntity.ResultsBean, RecommendViewHolder> {

    public RecommendAdapter(List<RecommendEntity.ResultsBean> data) {
        super(data);
        addItemType(RecommendEntity.ResultsBean.ENTITY_TEXT, R.layout.item_recommend_simple);
        addItemType(RecommendEntity.ResultsBean.ENTITY_IMG, R.layout.item_recommend_complex);
        addItemType(RecommendEntity.ResultsBean.ENTITY_PICTURE, R.layout.item_recommend_picture);
    }

    @Override
    protected void convert(RecommendViewHolder helper, RecommendEntity.ResultsBean item) {
        switch (helper.getItemViewType()) {
            case RecommendEntity.ResultsBean.ENTITY_IMG:
                helper.setText(R.id.tv_contents, item.getDesc());
                helper.setText(R.id.tv_authors, item.getWho());
                helper.setText(R.id.tv_times, item.getPublishedAt().substring(0, 10));
                if (item.getImages() != null && item.getImages().size() > 0) {
                    GlideUtil.loadImageWithoutGif(MyApplication.getInstance(), item.getImages().get(0), helper.imageContent);
                }
                break;
            case RecommendEntity.ResultsBean.ENTITY_TEXT:
                helper.setText(R.id.tv_content, item.getDesc());
                helper.setText(R.id.tv_author, item.getWho());
                helper.setText(R.id.tv_time, item.getPublishedAt().substring(0, 10));
                break;
            case RecommendEntity.ResultsBean.ENTITY_PICTURE:
                helper.setText(R.id.tv_pic_author, item.getWho());
                helper.setText(R.id.tv_pic_time, item.getPublishedAt().substring(0, 10));
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), item.getUrl(), helper.imagePicture);
                break;
        }
    }
}
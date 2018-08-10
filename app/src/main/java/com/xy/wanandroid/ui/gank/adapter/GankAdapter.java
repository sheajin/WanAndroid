package com.xy.wanandroid.ui.gank.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.drawer.RecommendData;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by jxy on 2018/8/8.
 */

public class GankAdapter extends BaseMultiItemQuickAdapter<RecommendData.ResultsBean, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GankAdapter(List<RecommendData.ResultsBean> data) {
        super(data);
        addItemType(RecommendData.ResultsBean.ENTITY_ONE, R.layout.item_gank_one);
        addItemType(RecommendData.ResultsBean.ENTITY_TWO, R.layout.item_gank_two);
        addItemType(RecommendData.ResultsBean.ENTITY_THREE, R.layout.item_gank_three);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendData.ResultsBean item) {
        switch (helper.getItemViewType()) {
            case RecommendData.ResultsBean.ENTITY_ONE:
                if (!item.getType().equals(Constant.WELFARE))
                    helper.setText(R.id.tv_content_one, item.getDesc());
                helper.setText(R.id.tv_cate_one, item.getType());
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_one));
                break;
            case RecommendData.ResultsBean.ENTITY_TWO:
                helper.setText(R.id.tv_cate_two, item.getType());
                helper.setText(R.id.tv_two_cate_one, item.getDesc());
                helper.setText(R.id.tv_two_cate_two, item.getDesc());
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_content_one));
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_content_two));
                break;
            case RecommendData.ResultsBean.ENTITY_THREE:
                helper.setText(R.id.tv_cate_three, item.getType());
                helper.setText(R.id.tv_three_cate_one, item.getDesc());
                helper.setText(R.id.tv_three_cate_two, item.getDesc());
                helper.setText(R.id.tv_three_cate_three, item.getDesc());
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_three_content_one));
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_three_content_two));
                GlideUtil.loadPlaceImage(MyApplication.getInstance(), CommonUtil.getRandomImage(), helper.getView(R.id.image_three_content_three));
                break;
        }
    }
}

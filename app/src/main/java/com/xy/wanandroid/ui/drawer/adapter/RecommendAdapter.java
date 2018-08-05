package com.xy.wanandroid.ui.drawer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.drawer.RecommendData;
import com.xy.wanandroid.ui.drawer.viewholder.RecommendEntity;
import com.xy.wanandroid.ui.drawer.viewholder.RecommendViewHolder;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by jxy on 2018/8/5.
 */
public class RecommendAdapter extends BaseMultiItemQuickAdapter<RecommendEntity,RecommendViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RecommendAdapter(List<RecommendEntity> data) {
        super(data);
        addItemType(1,R.layout.item_recommend_simple);
        addItemType(2,R.layout.item_recommend_complex);
    }

    @Override
    protected void convert(RecommendViewHolder helper, RecommendEntity item) {

        int type = helper.getItemViewType();
        switch (type){
            case 1:
                helper.setText(R.id.tv_content, item.getDesc());
        helper.setText(R.id.tv_author, item.getWho());
        helper.setText(R.id.tv_time, item.getPublishedAt().substring(0, 10));
        if (item.getImages() != null)
            GlideUtil.loadImage(MyApplication.getInstance(), item.getImages().get(0), helper.imageContent);
                break;
            case 2:

                break;
        }
    }
}

//    private List<RecommendData> list;
//
//    public RecommendAdapter(int layoutResId, @Nullable List<RecommendData> data) {
//        super(layoutResId, data);
//        this.list = data;
//    }
//
//    @Override
//    protected void convert(RecommendViewHolder helper, RecommendData item) {
//        helper.setText(R.id.tv_content, item.getDesc());
//        helper.setText(R.id.tv_author, item.getWho());
//        helper.setText(R.id.tv_time, item.getPublishedAt().substring(0, 10));
//        if (item.getImages() != null)
//            GlideUtil.loadImage(MyApplication.getInstance(), item.getImages().get(0), helper.imageContent);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (list.get(position).getImages() != null) {
//            return R.layout.item_recommend_complex;
//        } else {
//            return R.layout.item_recommend_simple;
//        }
//    }
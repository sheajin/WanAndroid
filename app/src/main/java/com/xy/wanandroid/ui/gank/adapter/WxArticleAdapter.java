package com.xy.wanandroid.ui.gank.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.data.gank.WxArticleList;

import java.util.List;

/**
 * Created by jxy on 2018/10/31
 */
public class WxArticleAdapter extends BaseQuickAdapter<WxArticleList.DatasBean, BaseViewHolder> {

    public WxArticleAdapter(int layoutResId, @Nullable List<WxArticleList.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WxArticleList.DatasBean item) {
        helper.setText(R.id.tv_knowledge_title, item.getTitle());
        helper.setText(R.id.tv_knowledge_content, item.getNiceDate());
    }
}

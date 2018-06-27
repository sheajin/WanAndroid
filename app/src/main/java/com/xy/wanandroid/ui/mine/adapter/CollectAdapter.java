package com.xy.wanandroid.ui.mine.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.data.mine.CollectBean;

import java.util.List;

/**
 * Created by jxy on 2018/6/25.
 */

public class CollectAdapter extends BaseQuickAdapter<CollectBean.DatasBean, BaseViewHolder> {

    public CollectAdapter(int layoutResId, @Nullable List<CollectBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectBean.DatasBean article) {
        if (!TextUtils.isEmpty(article.getTitle())) {
            helper.setText(R.id.tv_content, article.getTitle());
        }
        if (!TextUtils.isEmpty(article.getAuthor())) {
            helper.setText(R.id.tv_author, article.getAuthor());
        }
        if (!TextUtils.isEmpty(article.getNiceDate())) {
            helper.setText(R.id.tv_time, article.getNiceDate());
        }
        if (!TextUtils.isEmpty(article.getChapterName())) {
            helper.setText(R.id.tv_type, article.getChapterName());
        }
        helper.setImageResource(R.id.image_collect, R.drawable.icon_collect);
        helper.addOnClickListener(R.id.image_collect);
    }
}

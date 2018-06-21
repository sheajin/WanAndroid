package com.xy.wanandroid.ui.knowledge.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.data.knowledge.KnowledgeListBean;

import java.util.List;

/**
 * Created by jxy on 2018/6/14.
 */

public class KnowledgeAdapter extends BaseQuickAdapter<KnowledgeListBean, BaseViewHolder> {

    public KnowledgeAdapter(int layoutResId, @Nullable List<KnowledgeListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeListBean item) {
        helper.setText(R.id.tv_knowledge_title, item.getName());
        StringBuilder sb = new StringBuilder();
        for (KnowledgeListBean.ChildrenBean childrenBean : item.getChildren()) {
            sb.append(childrenBean.getName()).append("   ");
        }
        helper.setText(R.id.tv_knowledge_content, sb.toString());
    }
}

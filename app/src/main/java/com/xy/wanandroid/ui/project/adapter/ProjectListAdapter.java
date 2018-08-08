package com.xy.wanandroid.ui.project.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;

import java.util.List;

import com.xy.wanandroid.data.project.ProjectListBean;
import com.xy.wanandroid.util.glide.GlideUtil;

/**
 * Created by jxy on 2018/6/17.
 */
public class ProjectListAdapter extends BaseQuickAdapter<ProjectListBean.DatasBean, BaseViewHolder> {

    public ProjectListAdapter(int layoutResId, @Nullable List<ProjectListBean.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectListBean.DatasBean bean) {
        if (!TextUtils.isEmpty(bean.getTitle())) {
            helper.setText(R.id.tv_title, bean.getTitle());
        }
        if (!TextUtils.isEmpty(bean.getDesc())) {
            helper.setText(R.id.tv_content, bean.getDesc());
        }
        if (!TextUtils.isEmpty(bean.getNiceDate())) {
            helper.setText(R.id.tv_time, bean.getNiceDate());
        }
        if (!TextUtils.isEmpty(bean.getAuthor())) {
            helper.setText(R.id.tv_author, bean.getAuthor());
        }
        GlideUtil.loadImageWithoutGif(mContext, bean.getEnvelopePic(), helper.getView(R.id.image_simple));
    }
}

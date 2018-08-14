package com.xy.wanandroid.ui.gank.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xy.wanandroid.R;
import com.xy.wanandroid.data.gank.DanmuBean;

import java.util.List;

/**
 * Created by jxy on 2018/7/26.
 */

public class LiveChatAdapter extends BaseQuickAdapter<DanmuBean, BaseViewHolder> {

    public LiveChatAdapter(int layoutResId, @Nullable List<DanmuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DanmuBean item) {
        helper.setText(R.id.tv_name, item.getName() + ": ");
        helper.setText(R.id.tv_danmu, item.getContent());
    }
}

package com.xy.wanandroid.ui.drawer.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.data.drawer.LiveList;
import com.xy.wanandroid.ui.drawer.viewholder.LiveViewHolder;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by jxy on 2018/7/19.
 */

public class LiveListAdapter extends BaseQuickAdapter<LiveList, LiveViewHolder> {

    public LiveListAdapter(int layoutResId, @Nullable List<LiveList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(LiveViewHolder helper, LiveList item) {
        helper.setText(R.id.tv_nickname, item.getNickname());
        helper.setText(R.id.tv_person_num, CommonUtil.getOnlineNum(item.getOnline()));
        helper.setText(R.id.tv_room_name, item.getRoom_name());
        GlideUtil.loadImage(MyApplication.getInstance(), item.getRoom_src(), helper.imagePreview);
    }
}

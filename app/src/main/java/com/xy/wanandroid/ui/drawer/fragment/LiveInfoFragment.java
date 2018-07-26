package com.xy.wanandroid.ui.drawer.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.data.drawer.LiveList;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import butterknife.BindView;

public class LiveInfoFragment extends BaseFragment {
    @BindView(R.id.image_head)
    ImageView imageHead;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_tag)
    TextView mTvTag;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_live_info;
    }

    public static LiveInfoFragment getInstance(LiveList live) {
        LiveInfoFragment liveInfoFragment = new LiveInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ROOMNAME, live.getNickname());
        bundle.putString(Constant.ROOMDETAIL, live.getRoom_name());
        bundle.putString(Constant.ROOMSRC, live.getAvatar());
        bundle.putString(Constant.ROOMTAG, live.getGame_name());
        liveInfoFragment.setArguments(bundle);
        return liveInfoFragment;
    }

    @Override
    protected void initUI() {
        Bundle bundle = getArguments();
        mTvTitle.setText(bundle.getString(Constant.ROOMNAME));
        mTvName.setText(bundle.getString(Constant.ROOMDETAIL));
        mTvTag.setText(bundle.getString(Constant.ROOMTAG));
        GlideUtil.loadRoundImage(activity, bundle.get(Constant.ROOMSRC), imageHead);
    }

    @Override
    protected void initData() {

    }
}

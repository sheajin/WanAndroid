package com.xy.wanandroid.ui.drawer.fragment;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ScrollView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.data.drawer.DanmuBean;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.ui.drawer.adapter.LiveChatAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LiveChatFragment extends BaseFragment {

    @BindView(R.id.scrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<DanmuBean> danmuList;
    private LiveChatAdapter mAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_live_chat;
    }

    public static LiveChatFragment getInstance() {
        return new LiveChatFragment();
    }

    @Override
    protected void initUI() {
        mRv.setLayoutManager(new LinearLayoutManager(activity));
        mRv.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        danmuList = new ArrayList<>();
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch (event.getCode()) {
            case EventConstant.SENDDANMU:
                danmuList.add(new DanmuBean(event.getName(), event.getMess()));
                mAdapter = new LiveChatAdapter(R.layout.item_chat_danmu, danmuList);
                mRv.setAdapter(mAdapter);
                mRv.postDelayed(() -> mScrollView.fullScroll(ScrollView.FOCUS_DOWN), Constant.SCROLL_BOTTOM);
                //防止OOM
                if (danmuList.size() == 50) {
                    danmuList.subList(0, 30).clear();
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        danmuList.clear();
        danmuList = null;
    }
}

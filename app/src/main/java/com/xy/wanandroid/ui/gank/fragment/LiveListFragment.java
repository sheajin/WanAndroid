package com.xy.wanandroid.ui.gank.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.gank.LiveListContract;
import com.xy.wanandroid.data.gank.LiveList;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.gank.LiveListPresenter;
import com.xy.wanandroid.ui.gank.activity.LiveActivity;
import com.xy.wanandroid.ui.gank.adapter.LiveListAdapter;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LiveListFragment extends BaseRootFragment<LiveListPresenter> implements LiveListContract.View, LiveListAdapter.OnItemClickListener {
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;

    private String tagId;
    private List<LiveList> liveList;
    private LiveListAdapter mAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_live_list;
    }

    public static LiveListFragment getInstance(String tagId) {
        LiveListFragment fragment = new LiveListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CATEID, tagId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        tagId = getArguments().getString(Constant.CATEID);
        showLoading();
        GridLayoutManager manager = new GridLayoutManager(activity, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                if (itemPosition % 2 == 1) {
                    outRect.left = 10;
                    outRect.right = 20;
                } else {
                    outRect.left = 20;
                    outRect.right = 10;
                }
                if (itemPosition == 0 || itemPosition == 1) {
                    outRect.top = 30;
                }
                outRect.bottom = 30;
            }
        };
        mRv.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initData() {
        setRefresh();
        liveList = new ArrayList<>();
        mPresenter.getLiveList(tagId);
        mAdapter = new LiveListAdapter(R.layout.item_live_list, liveList);
        mAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getLiveListOk(List<LiveList> dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            liveList = dataBean;
            mAdapter.replaceData(dataBean);
        } else {
            liveList.addAll(dataBean);
            mAdapter.addData(dataBean);
        }
        showNormal();
    }

    @Override
    public void getLiveListErr(String info) {
        ToastUtil.show(activity, info);
        showError();
    }

    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh(tagId);
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore(tagId);
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(activity, LiveActivity.class);
        intent.putExtra(Constant.ROOMINFO, mAdapter.getData().get(position));
        startActivity(intent);
    }

}

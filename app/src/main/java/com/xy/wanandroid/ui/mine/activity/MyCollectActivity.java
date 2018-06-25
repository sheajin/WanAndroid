package com.xy.wanandroid.ui.mine.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.CollectContract;
import com.xy.wanandroid.data.mine.CollectBean;
import com.xy.wanandroid.presenter.mine.CollectPresenter;
import com.xy.wanandroid.ui.main.adapter.HomePageAdapter;
import com.xy.wanandroid.ui.mine.adapter.CollectAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCollectActivity extends BaseRootActivity implements CollectContract.View, HomePageAdapter.OnItemClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<CollectBean.DatasBean> collectList;
    private CollectAdapter mAdapter;
    private CollectPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRv.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void initData() {
        setRefresh();
        collectList = new ArrayList<>();
        presenter = new CollectPresenter(this);
        presenter.getCollectList(0);
        mAdapter = new CollectAdapter(R.layout.item_homepage, collectList);
        mAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getCollectListOk(CollectBean dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            collectList = dataBean.getDatas();
            mAdapter.replaceData(dataBean.getDatas());
        } else {
            collectList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
        }
        showNormal();
    }

    @Override
    public void getCollectListErr(String info) {
        showError();
    }

    @Override
    public void reload() {
        showLoading();
        presenter.getCollectList(0);
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

}

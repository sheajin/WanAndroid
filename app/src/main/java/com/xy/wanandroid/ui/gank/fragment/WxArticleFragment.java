package com.xy.wanandroid.ui.gank.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.gank.WxArticleContract;
import com.xy.wanandroid.data.gank.WxArticleList;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.gank.WxArticlePresenter;
import com.xy.wanandroid.ui.gank.adapter.WxArticleAdapter;
import com.xy.wanandroid.ui.main.activity.ArticleDetailsActivity;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class WxArticleFragment extends BaseRootFragment<WxArticlePresenter> implements WxArticleContract.View, WxArticleAdapter.OnItemClickListener {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;

    private String titleId;
    private List<WxArticleList.DatasBean> articleList;
    private WxArticleAdapter mAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_wx_article;
    }

    public static WxArticleFragment getInstance(String id) {
        WxArticleFragment wxArticleFragment = new WxArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.WX_ARTICLE_ID, id);
        wxArticleFragment.setArguments(bundle);
        return wxArticleFragment;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        if (getArguments() != null) {
            titleId = getArguments().getString(Constant.WX_ARTICLE_ID);
            mPresenter.getWxArticleList(titleId, 1);
        }
        mRv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        setRefresh();
        articleList = new ArrayList<>();
        mAdapter = new WxArticleAdapter(R.layout.item_knowledge, articleList);
        mAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getWxArticleListOK(WxArticleList dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            articleList = dataBean.getDatas();
            mAdapter.replaceData(dataBean.getDatas());
        } else {
            articleList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
        }
    }

    @Override
    public void getWxArticleListErr(String info) {
        ToastUtil.show(activity, info);
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh(titleId);
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore(titleId);
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        bundle.putInt(Constant.ARTICLE_ID, mAdapter.getData().get(position).getId());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, mAdapter.getData().get(position).isCollect());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.share_view));
        startActivity(new Intent(activity, ArticleDetailsActivity.class).putExtras(bundle), options.toBundle());
    }
}

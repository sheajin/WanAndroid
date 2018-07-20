package com.xy.wanandroid.ui.main.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.main.SearchResultContract;
import com.xy.wanandroid.data.main.HomePageArticleBean;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.main.SearchResultPresenter;
import com.xy.wanandroid.ui.main.adapter.HomePageAdapter;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchResultActivity extends BaseRootActivity<SearchResultPresenter> implements SearchResultContract.View,
        HomePageAdapter.OnItemClickListener, HomePageAdapter.OnItemChildClickListener {

    @BindView(R.id.article_toolbar)
    Toolbar mArticleToolbar;
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private String key;
    private List<HomePageArticleBean.DatasBean> resultList;
    private HomePageAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mArticleToolbar);
        getBundleData();
        if (key != null) {
            getSupportActionBar().setTitle(key);
        }
        mArticleToolbar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        key = bundle.getString(Constant.SEARCH_RESULT_TITLE);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        setRefresh();
        resultList = new ArrayList<>();
        mPresenter.getSearchResult(0, key);
        mAdapter = new HomePageAdapter(R.layout.item_homepage, resultList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getSearchResultOk(HomePageArticleBean searchResult, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            resultList = searchResult.getDatas();
            mAdapter.replaceData(searchResult.getDatas());
            showNormal();
            if (searchResult.getDatas().size() == 0)
                showEmpty();
        } else {
            if (searchResult.getDatas().size() > 0) {
                resultList.addAll(searchResult.getDatas());
                mAdapter.addData(searchResult.getDatas());
            } else {
                ToastUtil.show(context, getString(R.string.load_more_no_data));
            }
        }
    }

    @Override
    public void getSearchResultErr(String info) {
        showError();
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh(key);
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore(key);
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.share_view));
        startActivity(new Intent(activity, ArticleDetailsActivity.class).putExtras(bundle), options.toBundle());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }
}

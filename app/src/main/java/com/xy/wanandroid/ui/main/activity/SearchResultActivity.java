package com.xy.wanandroid.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.SearchResultContract;
import com.xy.wanandroid.data.main.SearchResult;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.main.SearchResultPresenter;
import com.xy.wanandroid.ui.main.adapter.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchResultActivity extends BaseRootActivity implements SearchResultContract.View {

    @BindView(R.id.article_toolbar)
    Toolbar mArticleToolbar;
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private String key;
    private List<SearchResult.DatasBean> resultList;
    private SearchResultAdapter mAdapter;
    private SearchResultPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
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
        presenter = new SearchResultPresenter(this);
        presenter.getSearchResult(0, key);
        mAdapter = new SearchResultAdapter(R.layout.item_homepage, resultList);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getSearchResultOk(SearchResult searchResult, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            resultList = searchResult.getDatas();
            mAdapter.replaceData(searchResult.getDatas());
        } else {
            resultList.addAll(searchResult.getDatas());
            mAdapter.addData(searchResult.getDatas());
        }
        showNormal();
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
            presenter.autoRefresh(key);
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.loadMore(key);
            refreshLayout.finishLoadMore(1000);
        });
    }
}

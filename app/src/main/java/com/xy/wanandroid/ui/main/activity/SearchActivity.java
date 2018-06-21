package com.xy.wanandroid.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.SearchContract;
import com.xy.wanandroid.data.main.SearchHot;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.main.SearchPresenter;
import com.xy.wanandroid.ui.main.adapter.SearchHistoryAdapter;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.widget.CommonAlertDialog;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseRootActivity implements SearchContract.View, SearchHistoryAdapter.OnItemChildClickListener, SearchHistoryAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_search)
    Toolbar mToolBarSearch;
    @BindView(R.id.flow_search)
    TagFlowLayout mFlowSearch;
    @BindView(R.id.tv_clear)
    TextView mTvClear;
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private SearchPresenter presenter;
    private List<SearchHot> hotList;
    private List<String> historyList;
    private SearchHistoryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolBarSearch);
        mToolBarSearch.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @OnClick(R.id.tv_clear)
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:
                if (historyList.size() > 0) {
                    CommonAlertDialog.newInstance().showDialog(activity, getString(R.string.delete_history_sure),
                            getString(R.string.sure),
                            getString(R.string.cancel),
                            v -> {
                                historyList.clear();
                                mAdapter.notifyDataSetChanged();
                                presenter.saveSearchHistory(context, historyList);
                                CommonAlertDialog.newInstance().cancelDialog(true);
                            },
                            v -> CommonAlertDialog.newInstance().cancelDialog(true));
                }
                break;
        }
    }

    @Override
    protected void initUI() {
        super.initUI();
        presenter = new SearchPresenter(this);
        mRvHistory.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        hotList = new ArrayList<>();
        historyList = new ArrayList<>();
        presenter.getSearchHot();
        mAdapter = new SearchHistoryAdapter(R.layout.item_search_history, historyList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRvHistory.setAdapter(mAdapter);
        presenter.readSearchHistory(context, historyList);
    }

    /**
     * 使用SearchView设置ToolBar搜索栏
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setQueryHint(getString(R.string.input_search_content));
        mSearchView.setIconified(false);
        mSearchView.setOnCloseListener(() -> {
            onBackPressedSupport();
            return true;
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                historyList.add(query);
                mAdapter.notifyDataSetChanged();
                presenter.saveSearchHistory(context, historyList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 获取热搜关键词
     */
    @Override
    public void getSearchHotOk(List<SearchHot> dataBean) {
        hotList.clear();
        hotList.addAll(dataBean);
        mAdapter.notifyDataSetChanged();
        initFlowLayout();
    }

    private void initFlowLayout() {
        TagAdapter<SearchHot> tagAdapter = new TagAdapter<SearchHot>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, SearchHot searchHot) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.item_flow_layout, null);
                String name = searchHot.getName();
                text.setText(name);
                text.setTextColor(CommonUtil.randomColor());
                return text;
            }
        };
        mFlowSearch.setAdapter(tagAdapter);
        mFlowSearch.setOnTagClickListener((view, position1, parent1) -> {
            String name = hotList.get(position1).getName();
            historyList.add(name);
            presenter.saveSearchHistory(context, historyList);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.SEARCH_RESULT_TITLE, name);
            JumpUtil.overlay(context, SearchResultActivity.class, bundle);
            finish();
            return true;
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        historyList.remove(position);
        adapter.notifyDataSetChanged();
        presenter.saveSearchHistory(context, historyList);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SEARCH_RESULT_TITLE, mAdapter.getData().get(position));
        JumpUtil.overlay(context, SearchResultActivity.class, bundle);
        finish();
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        CommonUtil.hideKeyBoard();
    }
}

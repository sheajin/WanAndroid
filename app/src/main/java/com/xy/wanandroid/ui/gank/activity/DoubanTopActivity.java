package com.xy.wanandroid.ui.gank.activity;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.gank.TopContract;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.presenter.gank.TopPresenter;
import com.xy.wanandroid.ui.gank.adapter.DoubanTopAdapter;
import com.xy.wanandroid.util.app.DisplayUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DoubanTopActivity extends BaseRootActivity<TopPresenter> implements TopContract.View {

    @BindView(R.id.toolbar_douban_top)
    Toolbar mToolBar;
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<HotMovieBean.SubjectsBean> topList;
    private DoubanTopAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_douban_top;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.top250));
        mToolBar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        GridLayoutManager manager = new GridLayoutManager(activity, 3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                if ((itemPosition) % 3 == 0) {
                    outRect.left = DisplayUtil.dip2px(context, 10);
                }
                if ((itemPosition) % 3 == 1) {
                    outRect.left = DisplayUtil.dip2px(context, 10);
                    outRect.right = DisplayUtil.dip2px(context, 10);
                }
                if ((itemPosition) % 3 == 2) {
                    outRect.right = DisplayUtil.dip2px(context, 10);
                }
            }
        };
        mRv.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initData() {
        setRefresh();
        topList = new ArrayList<>();
        mPresenter.getTop();
        mAdapter = new DoubanTopAdapter(R.layout.item_douban_top, topList);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getTopOk(HotMovieBean hotMovieBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            topList = hotMovieBean.getSubjects();
            mAdapter.replaceData(hotMovieBean.getSubjects());
        } else {
            topList = hotMovieBean.getSubjects();
            mAdapter.addData(hotMovieBean.getSubjects());
        }
        showNormal();
    }

    @Override
    public void getTopErr(String info) {
        ToastUtil.show(context, info);
        showError();
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }
}

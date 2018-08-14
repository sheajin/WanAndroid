package com.xy.wanandroid.ui.gank.activity;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.gank.HotContract;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.data.gank.RecommendEntity;
import com.xy.wanandroid.presenter.gank.HotPresenter;
import com.xy.wanandroid.ui.gank.adapter.RecommendAdapter;
import com.xy.wanandroid.util.app.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DoubanHotActivity extends BaseRootActivity<HotPresenter> implements HotContract.View {

    @BindView(R.id.toolbar_douban_hot)
    Toolbar mToolBar;
    @BindView(R.id.normal_view)
    RecyclerView mRv;

    private List<HotMovieBean> hotList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_douban_hot;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.douban_hot));
        mToolBar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initUI() {
        super.initUI();
//        showLoading();
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                if (itemPosition == hotList.size())
                    outRect.bottom = DisplayUtil.dip2px(context, 12);
            }
        };
        mRv.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initData() {
        hotList = new ArrayList<>();
        mPresenter.getHotMovie();
    }

    @Override
    public void getHotMovieOk(HotMovieBean hotMovieBean) {

    }

    @Override
    public void getHotMovieErr(String info) {

    }
}

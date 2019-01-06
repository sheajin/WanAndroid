package com.xy.wanandroid.ui.gank.activity;

import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.gank.HotContract;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.presenter.gank.HotPresenter;
import com.xy.wanandroid.ui.gank.adapter.DoubanHotAdapter;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.app.DisplayUtil;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DoubanHotActivity extends BaseRootActivity<HotPresenter> implements HotContract.View, DoubanHotAdapter.OnItemClickListener {

    @BindView(R.id.toolbar_douban_hot)
    Toolbar mToolBar;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.image_head)
    ImageView mIvHead;

    private List<HotMovieBean.SubjectsBean> hotList;
    private DoubanHotAdapter mAdapter;

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
        showLoading();
        GlideUtil.loadImage(context, CommonUtil.getRandomImage(), mIvHead);
        mRv.setLayoutManager(new LinearLayoutManager(activity));
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                if (itemPosition == hotList.size() - 1)
                    outRect.bottom = DisplayUtil.dip2px(context, 12);
                outRect.top = DisplayUtil.dip2px(context, 12);
                outRect.left = DisplayUtil.dip2px(context, 12);
                outRect.right = DisplayUtil.dip2px(context, 12);
            }
        };
        mRv.addItemDecoration(itemDecoration);
        mRv.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        hotList = new ArrayList<>();
        mPresenter.getHotMovie();
        mAdapter = new DoubanHotAdapter(R.layout.item_douban_hot, hotList);
        mAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mAdapter);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
    }

    @OnClick(R.id.view_movie_top)
    void click() {
        JumpUtil.overlay(context, DoubanTopActivity.class);
    }

    @Override
    public void getHotMovieOk(HotMovieBean hotMovieBean) {
        if (mAdapter == null) {
            return;
        }
        hotList.addAll(hotMovieBean.getSubjects());
        mAdapter.replaceData(hotList);
        showNormal();
    }

    @Override
    public void getHotMovieErr(String info) {
        showError();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        Intent intent = new Intent(activity, MovieDetailsActivity.class);
//        intent.putExtra(Constant.MOVIE_ID, hotList.get(position).getId());
//        intent.putExtra(Constant.MOVIE_TITLE, hotList.get(position).getTitle());
//        startActivity(intent);
    }
}

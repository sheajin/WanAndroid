package com.xy.wanandroid.ui.drawer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.SlidingTabLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.base.adapter.SimpleFragmentStateAdapter;
import com.xy.wanandroid.contract.drawer.VideoContract;
import com.xy.wanandroid.data.drawer.SortList;
import com.xy.wanandroid.presenter.drawer.VideoPresenter;
import com.xy.wanandroid.ui.drawer.fragment.LiveListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoContract.View {
    @BindView(R.id.toolbar_video)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mPager;

    private List<Fragment> fragments;
    private SimpleFragmentStateAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.video));
        mToolBar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        fragments = new ArrayList<>();
    }

    @Override
    protected void initData() {
        fragments.add(LiveListFragment.getInstance());
        initPager();
    }

    /**
     * 设置Viewpager
     */
    private void initPager() {
        pagerAdapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), fragments);
        mPager.setAdapter(pagerAdapter);
    }

    @Override
    public void getLiveTitleOk(List<SortList> dataBean) {

    }

    @Override
    public void getLiveTitleErr(String info) {

    }

    /**
     * Api
     * 1.标题分类,取tag_name  http://capi.douyucdn.cn/api/v1/getColumnDetail?client_sys=android&aid=android1&time=1532078715997&shortName=PCgame
     * 2.内容  http://capi.douyucdn.cn/api/v1/live/1?client_sys=android&aid=android1&time=1532078716457&offset=0&limit=20
     */
}

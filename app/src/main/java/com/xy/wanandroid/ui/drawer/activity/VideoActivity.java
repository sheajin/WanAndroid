package com.xy.wanandroid.ui.drawer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.SlidingTabLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.base.adapter.SimpleFragmentStateAdapter;
import com.xy.wanandroid.contract.drawer.VideoContract;
import com.xy.wanandroid.data.drawer.CategoryTitle;
import com.xy.wanandroid.presenter.drawer.VideoPresenter;
import com.xy.wanandroid.ui.drawer.fragment.LiveListFragment;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class VideoActivity extends BaseActivity<VideoPresenter> implements VideoContract.View {
    @BindView(R.id.toolbar_video)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mPager;

    private String[] titleArray;
    private List<String> titles;
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
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    @Override
    protected void initData() {
        initPager();
        mPresenter.getLiveTitle();
    }

    @Override
    public void getLiveTitleOk(List<CategoryTitle> dataBean) {
        if (dataBean.size() > 0) {
            for (int i = 0; i < 12; i++) {
                titles.add(dataBean.get(i).getTag_name());
                fragments.add(LiveListFragment.getInstance(dataBean.get(i).getTag_id()));
            }
            initTabLayout();
        }
    }

    @Override
    public void getLiveTitleErr(String info) {
        ToastUtil.show(activity, info);
    }

    private void initTabLayout() {
        titleArray = titles.toArray(new String[titles.size()]);
        mPager.setAdapter(pagerAdapter);
        mTabLayout.setViewPager(mPager, titleArray);
        pagerAdapter.notifyDataSetChanged();
        LogUtil.e(Arrays.toString(titleArray));
    }

    /**
     * 设置Viewpager
     */
    private void initPager() {
        pagerAdapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(),fragments);
        mPager.setAdapter(pagerAdapter);
    }
}

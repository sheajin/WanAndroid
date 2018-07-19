package com.xy.wanandroid.ui.drawer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.base.adapter.SimpleFragmentStateAdapter;
import com.xy.wanandroid.ui.drawer.fragment.SortFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoActivity extends BaseActivity {
    @BindView(R.id.toolbar_video)
    Toolbar mToolBar;
    @BindView(R.id.sort_pager)
    ViewPager mSortPager;

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
    protected void initUI() {
        fragments = new ArrayList<>();
    }

    @Override
    protected void initData() {
        fragments.add(SortFragment.getInstance());
        fragments.add(SortFragment.getInstance());
        initPager();
    }

    /**
     * 设置Viewpager
     */
    private void initPager() {
        pagerAdapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), fragments);
        mSortPager.setAdapter(pagerAdapter);
    }
}

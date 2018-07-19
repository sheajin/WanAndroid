package com.xy.wanandroid.ui.drawer.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;

import butterknife.BindView;

public class MusicActivity extends BaseActivity {

    @BindView(R.id.toolbar_music)
    Toolbar mToolBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(getString(R.string.music));
        mToolBar.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }
}

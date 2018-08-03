package com.xy.wanandroid.ui.drawer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.base.adapter.SimpleFragmentStateAdapter;
import com.xy.wanandroid.ui.drawer.fragment.LiveVideoFragment;
import com.xy.wanandroid.util.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MusicActivity extends BaseActivity {

    @BindView(R.id.toolbar_music)
    Toolbar mToolBar;
    @BindView(R.id.image_music)
    ImageView mImageMusic;
    @BindView(R.id.image_maven)
    ImageView mImageMaven;
    @BindView(R.id.image_friend)
    ImageView mImageFriend;
    @BindView(R.id.pager)
    ViewPager mPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_music;
    }

    @Override
    protected void initToolbar() {
        StatusBarUtil.setFullScreen(activity);
    }

    @Override
    protected void initUI() {
        selectItem(1);
    }

    @Override
    protected void initData() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(LiveVideoFragment.getInstance());
        fragments.add(LiveVideoFragment.getInstance());
        fragments.add(LiveVideoFragment.getInstance());
        SimpleFragmentStateAdapter pagerAdapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), fragments);
        mPager.setAdapter(pagerAdapter);
        mPager.setOffscreenPageLimit(3);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.image_music, R.id.image_maven, R.id.image_friend})
    void click(View view) {
        switch (view.getId()) {
            case R.id.image_music:
                selectItem(0);
                break;
            case R.id.image_maven:
                selectItem(1);
                break;
            case R.id.image_friend:
                selectItem(2);
                break;
        }
    }

    private void selectItem(int position) {
        switch (position) {
            case 0:
                cleanSelect();
                mImageMusic.setSelected(true);
                break;
            case 1:
                cleanSelect();
                mImageMaven.setSelected(true);
                break;
            case 2:
                cleanSelect();
                mImageFriend.setSelected(true);
                break;
        }
        mPager.setCurrentItem(position);
    }

    private void cleanSelect() {
        mImageMusic.setSelected(false);
        mImageMaven.setSelected(false);
        mImageFriend.setSelected(false);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
}

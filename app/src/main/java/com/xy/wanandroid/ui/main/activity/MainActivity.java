package com.xy.wanandroid.ui.main.activity;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.ui.gank.fragment.GankFragment;
import com.xy.wanandroid.ui.knowledge.fragment.KnowledgeFragment;
import com.xy.wanandroid.ui.main.fragment.HomePageFragment;
import com.xy.wanandroid.ui.mine.fragment.PersonalFragment;
import com.xy.wanandroid.ui.project.fragment.ProjectFragment;
import com.xy.wanandroid.util.app.BottomNavigationViewHelper;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.float_button)
    FloatingActionButton mFloatingButton;
    @BindView(R.id.toolbar_common)
    Toolbar mToolBar;

    private List<Fragment> fragments;
    private BasePresenter presenter;
    private int lastIndex;
    private long mExitTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        presenter = new BasePresenter();
        initFragment();
        selectFragment(0);
    }

    @Override
    protected void initData() {
        initNavigation();
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolBar);
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(HomePageFragment.getInstance());
        fragments.add(KnowledgeFragment.getInstance());
        fragments.add(ProjectFragment.getInstance());
        fragments.add(GankFragment.getInstance());
        fragments.add(PersonalFragment.getInstance());
    }

    @OnClick(R.id.float_button)
    void click(View view) {
        switch (view.getId()) {
            case R.id.float_button:
                scrollToTop();
                break;
        }
    }

    /**
     * 处理点击滑到顶部
     */
    private void scrollToTop() {
        switch (presenter.getCurrentPage()) {
            case 0:
                EventBus.getDefault().post(new MessageEvent(EventConstant.MAINSCROLLTOTOP, ""));
                break;
            case 1:
                EventBus.getDefault().post(new MessageEvent(EventConstant.KNOWLEDGESCROLLTOTOP, ""));
                break;
            case 2:
                EventBus.getDefault().post(new MessageEvent(EventConstant.PROJECTSCROLLTOTOP, ""));
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param position
     */
    private void selectFragment(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragments.get(position);
        Fragment lastFragment = fragments.get(lastIndex);
        lastIndex = position;
        ft.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            ft.add(R.id.frame_layout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
        presenter.setCurrentPage(position);
    }

    /**
     * bottom选择器
     */
    private void initNavigation() {
        BottomNavigationViewHelper.disableShiftMode(mBottomNavigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main:
                    mFloatingButton.setVisibility(View.VISIBLE);
                    selectFragment(0);
                    break;
                case R.id.tab_knowledge:
                    mFloatingButton.setVisibility(View.VISIBLE);
                    selectFragment(1);
                    break;
                case R.id.tab_project:
                    mFloatingButton.setVisibility(View.VISIBLE);
                    selectFragment(2);
                    break;
                case R.id.tab_gank:
                    mFloatingButton.setVisibility(View.GONE);
                    selectFragment(3);
                    break;
                case R.id.tab_mine:
                    mFloatingButton.setVisibility(View.GONE);
                    selectFragment(4);
                    break;
            }
            return true;
        });
    }

    /**
     * 创建menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * ToolBar menu选择
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_hot:
                JumpUtil.overlay(activity, HotActivity.class);
                break;
            case R.id.menu_main_search:
                JumpUtil.overlay(activity, SearchActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.show(activity, getString(R.string.exit_system));
            mExitTime = System.currentTimeMillis();
        } else {
            SharedPreferenceUtil.put(activity, Constant.ISLOGIN, Constant.FALSE);
            finish();
        }
    }

}

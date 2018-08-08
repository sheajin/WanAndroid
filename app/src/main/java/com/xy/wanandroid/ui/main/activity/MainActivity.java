package com.xy.wanandroid.ui.main.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.ui.drawer.activity.MusicActivity;
import com.xy.wanandroid.ui.drawer.activity.VideoActivity;
import com.xy.wanandroid.ui.gank.fragment.GankFragment;
import com.xy.wanandroid.ui.knowledge.fragment.KnowledgeFragment;
import com.xy.wanandroid.ui.login.LoginActivity;
import com.xy.wanandroid.ui.main.fragment.HomePageFragment;
import com.xy.wanandroid.ui.mine.fragment.PersonalFragment;
import com.xy.wanandroid.ui.project.fragment.ProjectFragment;
import com.xy.wanandroid.util.app.BottomNavigationViewHelper;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.app.ToastUtil;
import com.xy.wanandroid.util.glide.GlideUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseRootActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.float_button)
    FloatingActionButton mFloatingButton;
    @BindView(R.id.toolbar_common)
    Toolbar mToolBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private List<Fragment> fragments;
    private BasePresenter presenter;
    private int lastIndex;
    private long mExitTime;
    private ImageView imageHead;
    private TextView mTvName;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        View headerView = mNavigationView.inflateHeaderView(R.layout.nav_header_view);
        imageHead = headerView.findViewById(R.id.image_avatar);
        mTvName = headerView.findViewById(R.id.tv_name);

        presenter = new BasePresenter();
        initFragment();
        selectFragment(0);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(null);
    }

    @Override
    protected void initData() {
        initNavigationHeader();
        initNavigation();
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolBar, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
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

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch (event.getCode()) {
            case EventConstant.LOGINSUCCESS:
            case EventConstant.LOGOUTSUCCESS:
                initNavigationHeader();
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
     * 设置Navigation Header
     */
    private void initNavigationHeader() {
        mTvName.setText((Boolean) SharedPreferenceUtil.get(activity, Constant.ISLOGIN, false) ?
                (String) SharedPreferenceUtil.get(activity, Constant.USERNAME, "") : getString(R.string.click_head_login));
        GlideUtil.loadRoundImage(activity, R.drawable.image_head, imageHead);
        imageHead.setOnClickListener(v -> JumpUtil.overlay(activity, LoginActivity.class));
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

    /**
     * Navigation菜单选择
     *
     * @param item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_item_music:
                mDrawerLayout.closeDrawer(Gravity.START);
                mDrawerLayout.postDelayed(() -> JumpUtil.overlay(activity, MusicActivity.class), 400);
                break;
            case R.id.nav_item_video:
                mDrawerLayout.closeDrawer(Gravity.START);
                mDrawerLayout.postDelayed(() -> JumpUtil.overlay(activity, VideoActivity.class), 400);
                break;
        }
        return true;
    }

}

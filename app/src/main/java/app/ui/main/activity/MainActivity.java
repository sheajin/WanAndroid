package app.ui.main.activity;


import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import app.base.activity.BaseRootActivity;
import app.base.presenter.BasePresenter;
import app.model.constant.EventConstant;
import app.model.constant.MessageEvent;
import app.ui.knowledge.fragment.KnowledgeFragment;
import app.ui.main.fragment.HomePageFragment;
import app.ui.mine.fragment.PersonalFragment;
import app.ui.project.fragment.ProjectFragment;
import app.util.app.BottomNavigationViewHelper;
import app.util.app.ToastUtil;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseRootActivity {

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
                case R.id.tab_mine:
                    mFloatingButton.setVisibility(View.GONE);
                    selectFragment(3);
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
     * menu选择
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_hot:
                ToastUtil.show(context, "hot");
                break;
            case R.id.menu_main_search:
                ToastUtil.show(context, "search");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.show(context, getString(R.string.exit_system));
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}

package app.ui.main.activity;


import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.xy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import app.base.activity.BaseRootActivity;
import app.model.constant.EventConstant;
import app.model.constant.MessageEvent;
import app.ui.knowledge.fragment.KnowledgeFragment;
import app.ui.main.fragment.HomePageFragment;
import app.ui.mine.fragment.PersonalFragment;
import app.util.app.ToastUtil;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseRootActivity {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigation;

    private List<Fragment> fragments;
    private int lastIndex;
    private long mExitTime;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        initFragment();
        selectFragment(0);
    }

    @Override
    protected void initData() {
        initNavigation();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mToolBar.setTitle(getString(R.string.hot));
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(HomePageFragment.getInstance());
        fragments.add(KnowledgeFragment.getInstance(2));
        fragments.add(PersonalFragment.getInstance(3));
    }

    @OnClick(R.id.float_button)
    void click(View view) {
        switch (view.getId()) {
            case R.id.float_button:
                EventBus.getDefault().post(new MessageEvent(EventConstant.HOMEPAGESCROLLTOTOP, ""));
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
    }

    /**
     * bottom选择器
     */
    private void initNavigation() {
        mBottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main:
                    selectFragment(0);
                    break;
                case R.id.tab_knowledge:
                    selectFragment(1);
                    break;
                case R.id.tab_mine:
                    selectFragment(2);
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

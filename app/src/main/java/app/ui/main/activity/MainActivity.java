package app.ui.main.activity;


import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xy.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import app.base.activity.BaseActivity;
import app.model.data.main.HomePageArticleBean;
import app.ui.knowledge.fragment.KnowledgeFragment;
import app.ui.main.fragment.HomePageFragment;
import app.ui.mine.fragment.PersonalFragment;
import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleTv;

    private List<Fragment> fragments;
    private int lastIndex;

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


    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(HomePageFragment.getInstance());
        fragments.add(KnowledgeFragment.getInstance(2));
        fragments.add(PersonalFragment.getInstance(3));
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

}

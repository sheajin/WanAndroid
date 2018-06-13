package app.ui.main.activity;


import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.xy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import app.base.activity.BaseActivity;
import app.model.constant.EventConstant;
import app.model.constant.MessageEvent;
import app.ui.knowledge.fragment.KnowledgeFragment;
import app.ui.main.fragment.HomePageFragment;
import app.ui.mine.fragment.PersonalFragment;
import app.util.app.LogUtil;
import app.util.app.ToastUtil;
import app.util.network.NetUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigation;
    @BindView(R.id.common_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.float_button)
    FloatingActionButton actionButton;

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

//    @Override
//    public void onNetChange(int netMobile) {
//        super.onNetChange(netMobile);
//        if (netMobile == NetUtils.NETWORK_WIFI) {
//            LogUtil.e("NETWORK_WIFI");
//        } else if (netMobile == NetUtils.NETWORK_MOBILE) {
//            LogUtil.e("NETWORK_MOBILE");
//        } else if (netMobile == NetUtils.NETWORK_NONE) {
//            LogUtil.e("NETWORK_NONE");
//        }
//    }

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

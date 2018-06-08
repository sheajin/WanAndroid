package app.ui.main.fragment;

import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;

import app.base.BaseFragment;
import butterknife.BindView;

public class HomePageFragment extends BaseFragment {
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_homepage;
    }

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

}

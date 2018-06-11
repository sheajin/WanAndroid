package app.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import app.base.fragment.BaseRootFragment;
import app.model.constant.Constant;
import app.model.contract.HomePageContract;
import app.model.data.main.HomePageArticleBean;
import app.presenter.main.HomePagePresenter;
import app.ui.main.adapter.HomePageAdapter;
import app.util.app.RecyclerViewUtil;
import butterknife.BindView;

public class HomePageFragment extends BaseRootFragment<HomePagePresenter> implements HomePageContract.View {
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<HomePageArticleBean.DatasBean> articleList;
    private HomePageAdapter adapter;
    private HomePagePresenter presenter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_homepage;
    }

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        RecyclerViewUtil.nestedRecyclerView(mRv, new LinearLayoutManager(context));
    }

    @Override
    protected void initData() {
        setRefresh();
        articleList = new ArrayList<>();
        presenter = new HomePagePresenter(this);
        presenter.getHomepageList(Constant.ZERO);
        adapter = new HomePageAdapter(R.layout.item_homepage, articleList);
        mRv.setAdapter(adapter);
    }

    @Override
    public void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh) {
        if (adapter == null) {
            return;
        }
        if (isRefresh) {
            articleList = dataBean.getDatas();
            adapter.replaceData(dataBean.getDatas());
        } else {
            articleList.addAll(dataBean.getDatas());
            adapter.addData(dataBean.getDatas());
        }
        showNormal();
    }

    @Override
    public void getHomepageListErr(String info) {
        showError();
    }

    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

}

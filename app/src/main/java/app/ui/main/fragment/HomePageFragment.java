package app.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import app.base.BaseFragment;
import app.model.api.ApiService;
import app.model.api.ApiStore;
import app.model.api.BaseResp;
import app.model.api.HttpObserver;
import app.model.contract.HomePageContract;
import app.model.data.main.HomePageArticleBean;
import app.presenter.main.HomePagePresenter;
import app.ui.main.adapter.HomePageAdapter;
import app.util.app.LogUtil;
import app.util.app.RecyclerViewUtil;
import butterknife.BindView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomePageFragment extends BaseFragment implements HomePageContract.View {
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<HomePageArticleBean.DatasBean> articleList;
    private HomePageAdapter adapter;
    private HomePagePresenter presenter;
    private int page = 0;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_homepage;
    }

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    protected void initUI() {
        showLoadingView();
        RecyclerViewUtil.nestedRecyclerView(mRv, new LinearLayoutManager(context));
    }

    @Override
    protected void initData() {
        articleList = new ArrayList<>();
        presenter = new HomePagePresenter(this);
//        presenter.getHomepageList(page);
        adapter = new HomePageAdapter(R.layout.item_homepage, articleList);
        setRefresh();
    }


    @Override
    public void getHomepageListOk(HomePageArticleBean datasBean) {
//        hideLoadingView(loadingView);
        articleList.clear();
        articleList.addAll(datasBean.getDatas());
        mRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getHomepageListErr(String info) {

    }

    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            presenter.getHomepageList(page);
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            presenter.getHomepageList(page);
            refreshLayout.finishLoadMore(1000);
        });
    }
}

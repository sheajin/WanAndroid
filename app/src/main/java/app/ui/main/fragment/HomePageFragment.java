package app.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import app.base.fragment.BaseRootFragment;
import app.model.constant.Constant;
import app.model.constant.EventConstant;
import app.model.constant.MessageEvent;
import app.model.contract.HomePageContract;
import app.model.data.main.BannerBean;
import app.model.data.main.HomePageArticleBean;
import app.presenter.main.HomePagePresenter;
import app.ui.main.activity.ArticleDetailsActivity;
import app.ui.main.adapter.HomePageAdapter;
import app.util.app.JumpUtil;
import app.util.app.LogUtil;
import app.util.glide.GlideImageLoader;
import butterknife.BindView;

public class HomePageFragment extends BaseRootFragment<HomePagePresenter> implements HomePageContract.View, HomePageAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<HomePageArticleBean.DatasBean> articleList;
    private List<String> linkList;
    private List<String> imageList;
    private List<String> titleList;
    private HomePageAdapter mAdapter;
    private HomePagePresenter presenter;
    private Banner banner;
    private LinearLayout bannerView;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_homepage;
    }

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    public void onMessageEvent(MessageEvent event) {
        if (event.getCode() == EventConstant.HOMEPAGESCROLLTOTOP) {
            mRv.smoothScrollToPosition(0);
        }
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRv.setLayoutManager(new LinearLayoutManager(context));
        bannerView = (LinearLayout) getLayoutInflater().inflate(R.layout.view_banner, null);
        banner = bannerView.findViewById(R.id.banner);
        bannerView.removeView(banner);
        bannerView.addView(banner);
    }

    @Override
    protected void initData() {
        setRefresh();
        articleList = new ArrayList<>();
        linkList = new ArrayList<>();
        imageList = new ArrayList<>();
        titleList = new ArrayList<>();
        presenter = new HomePagePresenter(this);
        presenter.getBanner();
        presenter.getHomepageList(Constant.ZERO);
        mAdapter = new HomePageAdapter(R.layout.item_homepage, articleList);
        mAdapter.addHeaderView(bannerView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            articleList = dataBean.getDatas();
            mAdapter.replaceData(dataBean.getDatas());
        } else {
            articleList.addAll(dataBean.getDatas());
            mAdapter.addData(dataBean.getDatas());
        }
        showNormal();
    }

    @Override
    public void getHomepageListErr(String info) {
        showError();
    }

    @Override
    public void getBannerOk(List<BannerBean> bannerBean) {
        titleList.clear();
        imageList.clear();
        for (BannerBean bean : bannerBean) {
            linkList.add(bean.getUrl());
            titleList.add(bean.getTitle());
            imageList.add(bean.getImagePath());
        }
        banner.setImageLoader(new GlideImageLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .setImages(imageList)
                .setBannerAnimation(Transformer.DepthPage)
                .setBannerTitles(titleList)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
        banner.setOnBannerListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_TITLE, titleList.get(position));
            bundle.putString(Constant.ARTICLE_LINK, linkList.get(position));
            if (!TextUtils.isEmpty(linkList.get(position)))
                JumpUtil.overlay(context, ArticleDetailsActivity.class, bundle);
        });
    }

    @Override
    public void getBannerErr(String info) {
        LogUtil.e(info);
    }

    @Override
    public void reload() {
        super.reload();
        presenter.getBanner();
        presenter.autoRefresh();
    }

    /**
     * SmartRefreshLayout刷新加载
     */
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        JumpUtil.overlay(context, ArticleDetailsActivity.class, bundle);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.image_collect:
                if (mAdapter.getData().get(position).isCollect()) {
                    mAdapter.getData().get(position).setCollect(false);
                } else {
                    mAdapter.getData().get(position).setCollect(true);
                }
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_type:

                break;
        }
    }

}

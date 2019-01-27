package com.xy.wanandroid.ui.main.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.main.HomePageContract;
import com.xy.wanandroid.data.login.UserInfo;
import com.xy.wanandroid.data.main.BannerBean;
import com.xy.wanandroid.data.main.HomePageArticleBean;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.presenter.main.HomePagePresenter;
import com.xy.wanandroid.ui.knowledge.activity.KnowledgeClassifyActivity;
import com.xy.wanandroid.ui.login.LoginActivity;
import com.xy.wanandroid.ui.main.activity.ArticleDetailsActivity;
import com.xy.wanandroid.ui.main.adapter.HomePageAdapter;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.app.ToastUtil;
import com.xy.wanandroid.util.glide.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
    private Banner banner;
    private LinearLayout bannerView;
    private int clickPosition;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_homepage;
    }

    public static HomePageFragment getInstance() {
        return new HomePageFragment();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
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
        if (SharedPreferenceUtil.get(activity, Constant.USERNAME, Constant.DEFAULT).equals(Constant.DEFAULT)) {
            mPresenter.getBanner();
            mPresenter.getHomepageList(0);
        } else {
            mPresenter.loginAndLoad();
        }
        mAdapter = new HomePageAdapter(R.layout.item_homepage, articleList);
        mAdapter.addHeaderView(bannerView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        switch (event.getCode()) {
            case EventConstant.MAINSCROLLTOTOP:
                mRv.smoothScrollToPosition(0);
                break;
            case EventConstant.REFRESHHOMEPAGE:
                mPresenter.getHomepageList(0);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null)
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
        if (!activity.isDestroyed()) {
            activity.runOnUiThread(() -> banner.setImageLoader(new GlideImageLoader())
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setImages(imageList)
                    .setBannerAnimation(Transformer.DepthPage)
                    .setBannerTitles(titleList)
                    .isAutoPlay(true)
                    .setDelayTime(5000)
                    .setIndicatorGravity(BannerConfig.RIGHT)
                    .start());

        }
        banner.setOnBannerListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_TITLE, titleList.get(position));
            bundle.putString(Constant.ARTICLE_LINK, linkList.get(position));
            if (!TextUtils.isEmpty(linkList.get(position))) {
                JumpUtil.overlay(context, ArticleDetailsActivity.class, bundle);
            }
        });
    }

    @Override
    public void getBannerErr(String info) {
        LogUtil.e(info);
    }

    @Override
    public void loginSuccess(UserInfo userInfo) {
        ToastUtil.show(activity, getString(R.string.auto_login_ok));
        SharedPreferenceUtil.put(activity, Constant.ISLOGIN, true);
        EventBus.getDefault().post(new MessageEvent(EventConstant.LOGINSUCCESS, ""));
    }

    @Override
    public void loginErr(String info) {
        ToastUtil.show(activity, info);
    }

    @Override
    public void collectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > clickPosition) {
            ToastUtil.show(activity, getString(R.string.collect_success));
            mAdapter.getData().get(clickPosition).setCollect(true);
            mAdapter.setData(clickPosition, mAdapter.getData().get(clickPosition));
        }
    }

    @Override
    public void collectArticleErr(String info) {
        ToastUtil.show(activity, getString(R.string.please_login));
        JumpUtil.overlay(activity, LoginActivity.class);
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > clickPosition) {
            ToastUtil.show(activity, getString(R.string.cancel_collect_success));
            mAdapter.getData().get(clickPosition).setCollect(false);
            mAdapter.setData(clickPosition, mAdapter.getData().get(clickPosition));
        }
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        ToastUtil.show(activity, info);
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getBanner();
        mPresenter.autoRefresh();
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    /**
     * @param adapter
     * @param view     共享元素跳转,元素为点击的view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        bundle.putInt(Constant.ARTICLE_ID, mAdapter.getData().get(position).getId());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, mAdapter.getData().get(position).isCollect());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.share_view));
        startActivity(new Intent(activity, ArticleDetailsActivity.class).putExtras(bundle), options.toBundle());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        clickPosition = position;
        switch (view.getId()) {
            case R.id.image_collect:
                if ((Boolean) SharedPreferenceUtil.get(context, Constant.ISLOGIN, Constant.FALSE)) {
                    if (mAdapter.getData().get(clickPosition).isCollect()) {
                        mPresenter.cancelCollectArticle(mAdapter.getData().get(clickPosition).getId());
                    } else {
                        mPresenter.collectArticle(mAdapter.getData().get(clickPosition).getId());
                    }
                } else {
                    ToastUtil.show(activity, getString(R.string.please_login));
                    JumpUtil.overlay(activity, LoginActivity.class);
                }
                break;
            case R.id.tv_type:
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.share_view));
                Intent intent = new Intent(activity, KnowledgeClassifyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constant.HOMEPAGE_TAG, true);
                bundle.putInt(Constant.HOMEPAGE_CID, mAdapter.getData().get(position).getChapterId());
                bundle.putString(Constant.HOMEPAGE_CNAME, mAdapter.getData().get(position).getChapterName());
                bundle.putString(Constant.HOMEPAGE_SUPERCNAME, mAdapter.getData().get(position).getSuperChapterName());
                intent.putExtras(bundle);
                startActivity(intent, options.toBundle());
                break;
        }
    }

}

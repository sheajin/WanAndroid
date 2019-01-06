package com.xy.wanandroid.ui.gank.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.gank.GankContract;
import com.xy.wanandroid.data.gank.MusicBanner;
import com.xy.wanandroid.data.gank.RecommendData;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.gank.GankPresenter;
import com.xy.wanandroid.ui.gank.activity.DoubanHotActivity;
import com.xy.wanandroid.ui.gank.activity.ExtraActivity;
import com.xy.wanandroid.ui.gank.activity.RecommendActivity;
import com.xy.wanandroid.ui.gank.activity.VideoActivity;
import com.xy.wanandroid.ui.gank.adapter.GankAdapter;
import com.xy.wanandroid.ui.main.activity.ArticleDetailsActivity;
import com.xy.wanandroid.util.app.DisplayUtil;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.app.ToastUtil;
import com.xy.wanandroid.util.glide.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GankFragment extends BaseRootFragment<GankPresenter> implements GankContract.View {

    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    private List<RecommendData.ResultsBean> gankList;
    private GankAdapter mAdapter;
    private List<String> urlList;
    private int retryCount = 2;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_gank;
    }

    public static GankFragment getInstance() {
        return new GankFragment();
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        GridLayoutManager manager = new GridLayoutManager(context, 6);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int count = 0;
                switch (gankList.get(position).getItemType()) {
                    case RecommendData.ResultsBean.ENTITY_ITEM_ONE:
                        count = 6;
                        break;
                    case RecommendData.ResultsBean.ENTITY_ITEM_TWO:
                        count = 3;
                        break;
                    case RecommendData.ResultsBean.ENTITY_ITEM_THREE:
                        count = 2;
                        break;
                    case RecommendData.ResultsBean.ENTITY_TITLE:
                        count = 6;
                        break;
                    default:
                        break;
                }
                return count;
            }
        });
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                switch (gankList.get(itemPosition).getItemType()) {
                    case RecommendData.ResultsBean.ENTITY_ITEM_ONE:
                        outRect.left = DisplayUtil.dip2px(context, 3);
                        outRect.right = DisplayUtil.dip2px(context, 3);
                        break;
                    case RecommendData.ResultsBean.ENTITY_ITEM_TWO:
                        if (itemPosition % 2 == 1) {
                            outRect.left = DisplayUtil.dip2px(context, 3);
                            outRect.right = DisplayUtil.dip2px(context, 6);
                        } else {
                            outRect.left = DisplayUtil.dip2px(context, 6);
                            outRect.right = DisplayUtil.dip2px(context, 3);
                        }
                        break;
                    case RecommendData.ResultsBean.ENTITY_ITEM_THREE:
                        if (itemPosition % 3 == 0) {
                            outRect.left = DisplayUtil.dip2px(context, 6);
                        }
                        if (itemPosition % 3 == 1) {
                            outRect.left = DisplayUtil.dip2px(context, 3);
                            outRect.right = DisplayUtil.dip2px(context, 3);
                        }
                        if (itemPosition % 3 == 2) {
                            outRect.right = DisplayUtil.dip2px(context, 6);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
        mRv.addItemDecoration(itemDecoration);
        mRv.setLayoutManager(manager);
        mRv.setNestedScrollingEnabled(false);
    }

    @Override
    protected void initData() {
        gankList = new ArrayList<>();
        urlList = new ArrayList<>();
        getBanner();
        mAdapter = new GankAdapter(gankList, activity);
        mPresenter.getEveryDayList();
        mRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(activity, ArticleDetailsActivity.class);
            intent.putExtra(Constant.ARTICLE_TITLE, gankList.get(position).getDesc());
            intent.putExtra(Constant.ARTICLE_LINK, gankList.get(position).getUrl());
            startActivity(intent);
        });
    }

    @OnClick({R.id.view_category, R.id.view_douyu, R.id.view_extra, R.id.view_rank})
    void click(View view) {
        switch (view.getId()) {
            case R.id.view_category:
                JumpUtil.overlay(activity, RecommendActivity.class);
                break;
            case R.id.view_douyu:
                JumpUtil.overlay(activity, VideoActivity.class);
                break;
            case R.id.view_extra:
                JumpUtil.overlay(activity, ExtraActivity.class);
                break;
            case R.id.view_rank:
                JumpUtil.overlay(activity, DoubanHotActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 获取banner
     * 由于json很庞大,所以存储到本地。不必每次请求
     */
    private void getBanner() {
        if (SharedPreferenceUtil.get(context, Constant.GANK_BANNER, Constant.DEFAULT).equals(Constant.DEFAULT)) {
            mPresenter.getMusicBanner();
        } else {
            String banner = (String) SharedPreferenceUtil.get(context, Constant.GANK_BANNER, Constant.DEFAULT);
            if (banner != null) {
                urlList = Arrays.asList(banner.split("="));
            }
        }
        showBanner();
    }

    private void showBanner() {
        activity.runOnUiThread(() -> banner.setImageLoader(new GlideImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(urlList)
                .setBannerAnimation(Transformer.DepthPage)
                .isAutoPlay(true)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start());
    }

    @Override
    public void getMusicBannerOK(MusicBanner musicBanner) {
        StringBuilder sb = new StringBuilder();
        for (MusicBanner.ResultBeanX.FocusBean.ResultBeanx resultBean : musicBanner.getResult().getFocus().getResult()) {
            sb.append(resultBean.getRandpic());
            sb.append("=");
            urlList.add(resultBean.getRandpic());
        }
        SharedPreferenceUtil.put(MyApplication.getInstance(), Constant.GANK_BANNER, sb);
        showBanner();
    }

    @Override
    public void getMusicBannerErr(String info) {
        ToastUtil.show(context, info);
        retryCount--;
        if (retryCount > 0) {
            showError();
            mPresenter.getMusicBanner();
        }
    }

    @Override
    public void getEveryDayListOk(RecommendData dataList, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        gankList.clear();
        gankList.addAll(mPresenter.getList(dataList.getResults()));
        mAdapter.notifyDataSetChanged();
        showNormal();
    }

    @Override
    public void getEveryDayListErr(String info) {
        ToastUtil.show(context, info);
        showError();
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getEveryDayList();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

}

package com.xy.wanandroid.ui.gank.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.gank.GankContract;
import com.xy.wanandroid.data.drawer.RecommendData;
import com.xy.wanandroid.data.gank.MusicBanner;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.gank.GankPresenter;
import com.xy.wanandroid.ui.drawer.activity.MusicActivity;
import com.xy.wanandroid.ui.drawer.activity.VideoActivity;
import com.xy.wanandroid.ui.gank.adapter.GankAdapter;
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

public class GankFragment extends BaseRootFragment<GankPresenter> implements GankContract.View {

    @BindView(R.id.normal_view)
    RecyclerView mRv;

    private List<RecommendData.ResultsBean> gankList;
    private GankAdapter mAdapter;
    private List<String> urlList;
    private Banner banner;

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
        mRv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        gankList = new ArrayList<>();
        urlList = new ArrayList<>();
        getBanner();
        mAdapter = new GankAdapter(gankList);
        initHeader();
        mPresenter.getEveryDayList();
        mRv.setAdapter(mAdapter);
    }

    /**
     * 设置Header
     */
    private void initHeader() {
        LinearLayout headerView = (LinearLayout) getLayoutInflater().inflate(R.layout.view_gank_header, null);
        View view = headerView.findViewById(R.id.view_header);
        View cateView = headerView.findViewById(R.id.view_category);
        View douYuView = headerView.findViewById(R.id.view_douyu);
        cateView.setOnClickListener(v -> JumpUtil.overlay(activity, MusicActivity.class));
        douYuView.setOnClickListener(v -> JumpUtil.overlay(activity, VideoActivity.class));
        banner = headerView.findViewById(R.id.banner);
        headerView.removeView(view);
        headerView.addView(view);
        mAdapter.addHeaderView(headerView);
        showBanner();
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
            urlList = Arrays.asList(banner.split("="));
        }

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
    }

    @Override
    public void getEveryDayListOk(RecommendData dataList, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        gankList = dataList.getResults();
        mAdapter.replaceData(mPresenter.getList(gankList));
        showNormal();
    }

    @Override
    public void getEveryDayListErr(String info) {
        ToastUtil.show(context, info);
        showError();
    }

}

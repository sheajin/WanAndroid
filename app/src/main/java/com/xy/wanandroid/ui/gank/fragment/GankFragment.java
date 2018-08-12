package com.xy.wanandroid.ui.gank.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.gank.GankContract;
import com.xy.wanandroid.data.gank.EverydayData;
import com.xy.wanandroid.data.gank.MusicBanner;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.gank.GankPresenter;
import com.xy.wanandroid.ui.gank.adapter.GankAdapter;
import com.xy.wanandroid.util.app.LogUtil;
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

    private EverydayData.ResultsBean resultsBean;
    private List<EverydayData.ResultsBean.AndroidBean> gankList;
    private GankAdapter mAdapter;
    private List<String> imageList;
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
        imageList = new ArrayList<>();
        getBanner();
        mPresenter.getEveryDayList();
        mAdapter = new GankAdapter(R.layout.item_recommend_simple, gankList);
        initHeader();
        mRv.setAdapter(mAdapter);
    }

    /**
     * 设置Header
     */
    private void initHeader() {
        LinearLayout headerView = (LinearLayout) getLayoutInflater().inflate(R.layout.view_gank_header, null);
        View view = headerView.findViewById(R.id.view_header);
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
            imageList = Arrays.asList(banner.split("="));
        }
    }

    private void showBanner() {
        activity.runOnUiThread(() -> banner.setImageLoader(new GlideImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImages(imageList)
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
            imageList.add(resultBean.getRandpic());
        }
        SharedPreferenceUtil.put(MyApplication.getInstance(), Constant.GANK_BANNER, sb);
        showBanner();
    }

    @Override
    public void getMusicBannerErr(String info) {
        ToastUtil.show(context, info);
    }

    @Override
    public void getEveryDayListOk(EverydayData.ResultsBean result, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        resultsBean = result;
//        gankList.addAll();
//        gankList.addAll(resultsBean.getApp());
//        gankList.addAll(resultsBean.getiOS());
//        gankList.addAll(resultsBean.getJs());
//        gankList.addAll(resultsBean.getRest());
//        gankList.addAll(resultsBean.getWelfare());

        LogUtil.e("gankList"+ (result.getAndroid().size()));
        showNormal();
    }

    @Override
    public void getEveryDayListErr(String info) {
        ToastUtil.show(context, info);
    }
}

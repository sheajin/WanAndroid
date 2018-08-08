package com.xy.wanandroid.presenter.gank;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.gank.GankContract;
import com.xy.wanandroid.data.gank.EverydayData;
import com.xy.wanandroid.data.gank.MusicBanner;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/8/8.
 */

public class GankPresenter extends BasePresenter<GankContract.View> implements GankContract.Presenter {

    private boolean isRefresh = true;

    @Inject
    public GankPresenter() {

    }

    @Override
    public void getMusicBanner() {
        ApiStore.createApi(ApiService.class)
                .getMusicBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<MusicBanner>() {
                    @Override
                    public void onNext(MusicBanner banner) {
                        mView.getMusicBannerOK(banner);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getMusicBannerErr("获取Banner失败");
                    }
                });
    }

    @Override
    public void getEveryDayList() {
        ApiStore.createApi(ApiService.class)
                .getEveryDayData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<EverydayData>() {
                    @Override
                    public void onNext(EverydayData data) {
                        mView.getEveryDayListOk(data, isRefresh);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getEveryDayListErr(e.getMessage());
                    }
                });
    }
}

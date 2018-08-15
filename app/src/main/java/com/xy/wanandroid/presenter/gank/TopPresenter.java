package com.xy.wanandroid.presenter.gank;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.gank.TopContract;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/8/15.
 */

public class TopPresenter extends BasePresenter<TopContract.View> implements TopContract.Presenter {

    private int start = 0;
    private boolean isRefresh = true;

    @Inject
    public TopPresenter() {
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        start = 0;
        getTop();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        start += 18;
        getTop();
    }

    @Override
    public void getTop() {
        ApiStore.createApi(ApiService.class)
                .getMovieTop(start, 18)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<HotMovieBean>() {
                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        mView.getTopOk(hotMovieBean, isRefresh);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getTopErr(e.getMessage());
                    }
                });
    }
}

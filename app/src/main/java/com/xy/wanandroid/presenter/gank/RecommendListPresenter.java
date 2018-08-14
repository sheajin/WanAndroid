package com.xy.wanandroid.presenter.gank;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.gank.RecommendContract;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.data.gank.RecommendEntity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/7/21.
 */
public class RecommendListPresenter extends BasePresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private boolean isRefresh = true;

    @Inject
    public RecommendListPresenter() {

    }

    @Override
    public void autoRefresh(String type, int page) {
        isRefresh = true;
        getRecommendList(type, 15, page);
    }

    @Override
    public void loadMore(String type, int page) {
        isRefresh = false;
        getRecommendList(type, 15, page);
    }

    @Override
    public void getRecommendList(String type, int offset, int page) {
        ApiStore.createApi(ApiService.class)
                .getRecommendList(type, offset, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<RecommendEntity>() {
                    @Override
                    public void onNext(RecommendEntity baseResp) {
                        if (mView != null)
                            mView.getRecommendOk(baseResp, isRefresh);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getRecommendErr(e.getMessage());
                    }
                });
    }

}

package com.xy.wanandroid.presenter.drawer;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.drawer.RecommendContract;
import com.xy.wanandroid.data.drawer.RecommendData;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.GankBaseResp;
import com.xy.wanandroid.model.api.HttpObserver;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/7/21.
 */
public class RecommendListPresenter extends BasePresenter<RecommendContract.View> implements RecommendContract.Presenter {

    private int page = 1;
    private int pre_page = 20;
    private boolean isRefresh = true;

    @Inject
    public RecommendListPresenter() {

    }

    @Override
    public void autoRefresh(String id, int page, int pre_page) {
        isRefresh = true;
        page = 1;
        getRecommendList(id, page, pre_page);
    }

    @Override
    public void loadMore(String id, int page, int pre_page) {
        page++;
        isRefresh = false;
        getRecommendList(id, page, pre_page);
    }

    @Override
    public void getRecommendList(String id, int page, int pre_page) {
        ApiStore.createApi(ApiService.class)
                .getRecommendList(id, page, pre_page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<GankBaseResp<List<RecommendData>>>() {
                    @Override
                    public void onNext(GankBaseResp<List<RecommendData>> baseResp) {
                        if (!baseResp.isError()) {
                            mView.getRecommendOk(baseResp.getResults(), isRefresh);
                        } else {
                            mView.getRecommendErr("获取推荐列表失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getRecommendErr(e.getMessage());
                    }
                });
    }

}

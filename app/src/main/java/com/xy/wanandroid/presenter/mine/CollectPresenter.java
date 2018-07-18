package com.xy.wanandroid.presenter.mine;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.CollectContract;
import com.xy.wanandroid.data.main.HomePageArticleBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/25.
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {

    private int currentPage;
    private boolean isRefresh = true;

    @Inject
    public CollectPresenter() {

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getCollectList(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getCollectList(currentPage);
    }

    @Override
    public void getCollectList(int page) {
        ApiStore.createApi(ApiService.class)
                .getCollectList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<HomePageArticleBean>>() {
                    @Override
                    public void onNext(BaseResp<HomePageArticleBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getCollectListOk(baseResp.getData(), isRefresh);
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getCollectListErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getCollectListErr(e.getMessage());
                    }
                });
    }

    @Override
    public void cancelCollect(int id) {
        ApiStore.createApi(ApiService.class)
                .cancelCollectArticleList(id, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<HomePageArticleBean>>() {
                    @Override
                    public void onNext(BaseResp<HomePageArticleBean> collectBaseResp) {
                        if (collectBaseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.cancelCollectOk();
                        } else if (collectBaseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.cancelCollectErr(collectBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.cancelCollectErr(e.getMessage());
                    }
                });
    }


}

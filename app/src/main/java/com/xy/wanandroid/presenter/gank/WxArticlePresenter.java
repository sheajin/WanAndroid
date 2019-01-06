package com.xy.wanandroid.presenter.gank;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.gank.WxArticleContract;
import com.xy.wanandroid.data.gank.WxArticleList;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/10/31
 */
public class WxArticlePresenter extends BasePresenter<WxArticleContract.View> implements WxArticleContract.Presenter {

    private boolean isRefresh = true;
    private int currentPage;

    @Inject
    public WxArticlePresenter() {

    }

    @Override
    public void autoRefresh(String id) {
        currentPage = 1;
        isRefresh = true;
        getWxArticleList(id, currentPage);
    }

    @Override
    public void loadMore(String id) {
        currentPage++;
        isRefresh = false;
        getWxArticleList(id, currentPage);
    }

    @Override
    public void getWxArticleList(String id, int page) {
        ApiStore.createApi(ApiService.class)
                .getWxArticleList(id, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<WxArticleList>>() {
                    @Override
                    public void onNext(BaseResp<WxArticleList> baseResp) {
                        if (mView != null) {
                            if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                mView.getWxArticleListOK(baseResp.getData(), isRefresh);
                            } else {
                                mView.getWxArticleListErr(baseResp.getErrorMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mView != null) {
                            mView.getWxArticleListErr(e.getMessage());
                        }
                    }
                });
    }

}

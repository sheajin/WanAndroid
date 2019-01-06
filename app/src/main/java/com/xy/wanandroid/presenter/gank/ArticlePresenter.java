package com.xy.wanandroid.presenter.gank;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.gank.ArticleContract;
import com.xy.wanandroid.data.gank.ArticleTitleBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/10/31
 */
public class ArticlePresenter extends BasePresenter<ArticleContract.View> implements ArticleContract.Presenter {

    @Inject
    public ArticlePresenter() {

    }

    @Override
    public void getArticleTitle() {
        ApiStore.createApi(ApiService.class)
                .getArticleTitle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<ArticleTitleBean>>>() {
                    @Override
                    public void onNext(BaseResp<List<ArticleTitleBean>> baseResp) {
                        if (mView != null) {
                            if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                                mView.getArticleTitleOk(baseResp.getData());
                            } else {
                                mView.getArticleTitleErr(baseResp.getErrorMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getArticleTitleErr(e.getMessage());
                    }
                });
    }

}

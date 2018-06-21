package com.xy.wanandroid.presenter.main;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.HomePageContract;
import com.xy.wanandroid.data.main.BannerBean;
import com.xy.wanandroid.data.main.HomePageArticleBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/10.
 */
public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {

    private HomePageContract.View view;
    private boolean isRefresh = true;
    private int currentPage;

    public HomePagePresenter(HomePageContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(HomePageContract.View view) {
        super.attachView(view);
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getBanner();
        getHomepageList(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getHomepageList(currentPage);
    }

    @Override
    public void getHomepageList(int page) {
        ApiStore.createApi(ApiService.class)
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<HomePageArticleBean>>() {
                    @Override
                    public void onNext(BaseResp<HomePageArticleBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.ZERO) {
                            view.getHomepageListOk(baseResp.getData(), isRefresh);
                        }
                    }

                    @Override
                    public void onErrorInfo(BaseResp<HomePageArticleBean> articleBean) {
                        if (articleBean.getData() == null) {
                            view.getHomepageListErr(articleBean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getHomepageListErr(e.getMessage());
                    }
                });
    }

    @Override
    public void getBanner() {
        ApiStore.createApi(ApiService.class)
                .getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<BannerBean>>>() {
                    @Override
                    public void onNext(BaseResp<List<BannerBean>> baseResp) {
                        if (baseResp.getErrorCode() == Constant.ZERO) {
                            view.getBannerOk(baseResp.getData());
                        }
                    }

                    @Override
                    public void onErrorInfo(BaseResp<List<BannerBean>> baseResp) {
                        view.getBannerErr(baseResp.getErrorMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.getBannerErr(e.getMessage());
                    }
                });
    }
}

package com.xy.wanandroid.presenter.main;

import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.main.HomePageContract;
import com.xy.wanandroid.data.login.UserInfo;
import com.xy.wanandroid.data.main.BannerBean;
import com.xy.wanandroid.data.main.HomePageArticleBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.network.RxUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/10.
 */
public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {

    private boolean isRefresh = true;
    private int currentPage;

    @Inject
    public HomePagePresenter(){

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
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<BaseResp<HomePageArticleBean>>() {
                    @Override
                    public void onNext(BaseResp<HomePageArticleBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getHomepageListOk(baseResp.getData(), isRefresh);
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getHomepageListErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getHomepageListErr(e.getMessage());
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
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getBannerOk(baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getBannerErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getBannerErr(e.getMessage());
                    }
                });
    }

    /**
     * 先登录再获取主页信息
     */
    @Override
    public void loginAndLoad() {
        String username = (String) SharedPreferenceUtil.get(MyApplication.getInstance(), Constant.USERNAME, Constant.DEFAULT);
        String password = (String) SharedPreferenceUtil.get(MyApplication.getInstance(), Constant.PASSWORD, Constant.DEFAULT);

        Observable<BaseResp<UserInfo>> observableUser = ApiStore.createApi(ApiService.class).login(username, password);
        Observable<BaseResp<List<BannerBean>>> observableBanner = ApiStore.createApi(ApiService.class).getBanner();
        Observable<BaseResp<HomePageArticleBean>> observableArticle = ApiStore.createApi(ApiService.class).getArticleList(currentPage);
        Observable.zip(observableUser, observableBanner, observableArticle, (userInfoBaseResp, bannerData, homepageData) -> {
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put(Constant.LOGINDATA, userInfoBaseResp);
            hashMap.put(Constant.BANNERDATA, bannerData);
            hashMap.put(Constant.HOMEPAGEDATA, homepageData);
            return hashMap;
        }).compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<Map<String, Object>>() {
                    @Override
                    public void onNext(Map<String, Object> map) {
                        /**
                         * 登录信息
                         */
                        BaseResp<UserInfo> userInfo = RxUtils.cast(map.get(Constant.LOGINDATA));
                        if (userInfo.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.loginSuccess(userInfo.getData());
                        } else if (userInfo.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.loginErr(userInfo.getErrorMsg());
                        }
                        /**
                         * banner信息
                         */
                        BaseResp<List<BannerBean>> bannerResp = RxUtils.cast(map.get(Constant.BANNERDATA));
                        if (bannerResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getBannerOk(bannerResp.getData());
                        } else if (bannerResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getBannerErr(bannerResp.getErrorMsg());
                        }
                        /**
                         * 主页列表信息
                         */
                        BaseResp<HomePageArticleBean> articleResp = RxUtils.cast(map.get(Constant.HOMEPAGEDATA));
                        if (articleResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getHomepageListOk(articleResp.getData(), isRefresh);
                        } else if (articleResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getHomepageListErr(articleResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getHomepageListErr(e.getMessage());
                    }
                });
    }

    @Override
    public void collectArticle(int id) {
        ApiStore.createApi(ApiService.class)
                .collectArticle(id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new HttpObserver<BaseResp>() {
                    @Override
                    public void onNext(BaseResp baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.collectArticleOK((String) baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.collectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.collectArticleErr(e.getMessage());
                    }
                });
    }

    @Override
    public void cancelCollectArticle(int id) {
        ApiStore.createApi(ApiService.class)
                .cancelCollectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp>() {
                    @Override
                    public void onNext(BaseResp baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.cancelCollectArticleOK((String) baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.cancelCollectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.cancelCollectArticleErr(e.getMessage());
                    }
                });
    }
}

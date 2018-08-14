package com.xy.wanandroid.presenter.gank;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.gank.HotContract;
import com.xy.wanandroid.data.gank.HotMovieBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.util.app.LogUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/8/14.
 */
public class HotPresenter extends BasePresenter<HotContract.View> implements HotContract.Presenter {

    @Inject
    public HotPresenter() {
    }

    @Override
    public void getHotMovie() {
        ApiStore.createApi(ApiService.class)
                .getHotMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<HotMovieBean>() {
                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        if (mView != null)
                            mView.getHotMovieOk(hotMovieBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getHotMovieErr(e.getMessage());
                        LogUtil.e("getHotMovie"+e.getMessage());
                    }
                });
    }
}

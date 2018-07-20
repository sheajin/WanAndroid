package com.xy.wanandroid.presenter.drawer;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.drawer.VideoContract;
import com.xy.wanandroid.data.drawer.SortList;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/7/20.
 */

public class VideoPresenter extends BasePresenter<VideoContract.View> implements VideoContract.Presenter {

    @Inject
    public VideoPresenter() {

    }

    @Override
    public void getLiveTitle(Map<String, String> params) {
        ApiStore.createApi(ApiService.class)
                .getLiveTitle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<SortList>>() {
                    @Override
                    public void onNext(BaseResp<SortList> listBaseResp) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}

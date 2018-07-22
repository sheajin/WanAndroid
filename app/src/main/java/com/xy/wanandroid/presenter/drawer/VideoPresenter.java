package com.xy.wanandroid.presenter.drawer;

import android.util.ArrayMap;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.drawer.VideoContract;
import com.xy.wanandroid.data.drawer.CategoryTitle;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.api.ParamsUtil;
import com.xy.wanandroid.model.constant.Constant;

import java.util.List;
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
    public void getLiveTitle() {
        Map<String, String> params = new ArrayMap<>();
        params = ParamsUtil.getCommonParams();
        params.put(Constant.CATEKEY, Constant.CATEVALUE);
        ApiStore.createApi(ApiService.class)
                .getLiveTitle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<CategoryTitle>>>() {
                    @Override
                    public void onNext(BaseResp<List<CategoryTitle>> baseResp) {
                        if (baseResp.getError() == Constant.REQUEST_SUCCESS){
                            mView.getLiveTitleOk(baseResp.data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getLiveTitleErr(e.getMessage());
                    }
                });
    }
}

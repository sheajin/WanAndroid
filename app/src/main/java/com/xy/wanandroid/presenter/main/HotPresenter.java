package com.xy.wanandroid.presenter.main;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.HotContract;
import com.xy.wanandroid.data.main.SearchHot;
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
 * Created by jxy on 2018/6/22.
 */

public class HotPresenter extends BasePresenter<HotContract.View> implements HotContract.Presenter {

    @Inject
    public HotPresenter() {

    }

    @Override
    public void getHotWeb() {
        ApiStore.createApi(ApiService.class)
                .getHotWeb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<SearchHot>>>() {
                    @Override
                    public void onNext(BaseResp<List<SearchHot>> listBaseResp) {
                        if (listBaseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getHotWebOk(listBaseResp.getData());
                        } else if (listBaseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getHotWebErr(listBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getHotWebErr(e.getMessage());
                    }
                });
    }

}

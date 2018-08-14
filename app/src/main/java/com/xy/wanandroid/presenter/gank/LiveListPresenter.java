package com.xy.wanandroid.presenter.gank;

import android.util.ArrayMap;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.gank.LiveListContract;
import com.xy.wanandroid.data.gank.LiveList;
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
 * Created by jxy on 2018/7/21.
 */
public class LiveListPresenter extends BasePresenter<LiveListContract.View> implements LiveListContract.Presenter {

    private int offset = 0;
    private int limit = 20;
    private boolean isRefresh = true;

    @Inject
    public LiveListPresenter() {

    }

    @Override
    public void autoRefresh(String tagId) {
        isRefresh = true;
        offset = 0;
        getLiveList(tagId);
    }

    @Override
    public void loadMore(String tagId) {
        isRefresh = false;
        offset += limit;
        getLiveList(tagId);
    }

    @Override
    public void getLiveList(String tagId) {
        Map<String, String> params = new ArrayMap<>();
        params = ParamsUtil.getCommonParams();
        params.put(Constant.OFFSET, String.valueOf(offset));
        params.put(Constant.LIMIT, String.valueOf(limit));
        ApiStore.createApi(ApiService.class)
                .getLiveList(tagId, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<LiveList>>>() {
                    @Override
                    public void onNext(BaseResp<List<LiveList>> baseResp) {
                        if (baseResp.getError() == Constant.REQUEST_SUCCESS) {
                            if (mView != null)
                                mView.getLiveListOk(baseResp.data, isRefresh);
                        } else {
                            mView.getLiveListErr("获取直播列表失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getLiveListErr(e.getMessage());
                    }
                });
    }
}

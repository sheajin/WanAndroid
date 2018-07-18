package com.xy.wanandroid.presenter.main;

import android.content.Context;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.SearchContract;
import com.xy.wanandroid.data.main.SearchHot;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/21.
 */

public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    @Inject
    public SearchPresenter() {

    }

    @Override
    public void getSearchHot() {
        ApiStore.createApi(ApiService.class)
                .getSearchHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<SearchHot>>>() {
                    @Override
                    public void onNext(BaseResp<List<SearchHot>> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getSearchHotOk(baseResp.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * 保存历史搜索记录
     */
    public void saveSearchHistory(Context context, List<String> historyList) {
        //保存之前先清空之前的存储
        SharedPreferenceUtil.remove(context, Constant.SEARCH_HISTORY);
        //存储
        StringBuilder sb = new StringBuilder();
        if (historyList.size() > 0) {
            for (String s : historyList) {
                sb.append(s).append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
            SharedPreferenceUtil.put(context, Constant.SEARCH_HISTORY, sb.toString().trim());
        }
    }

    /**
     * 读取历史记录
     */
    public void readSearchHistory(Context context, List<String> historyList) {
        historyList.clear();
        String histories = (String) SharedPreferenceUtil.get(context, Constant.SEARCH_HISTORY, Constant.DEFAULT);
        if (!histories.equals(Constant.DEFAULT)) {
            String[] history = histories.split(",");
            historyList.addAll(Arrays.asList(history));
        }
    }
}

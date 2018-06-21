package com.xy.wanandroid.presenter.main;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.SearchResultContract;
import com.xy.wanandroid.data.main.SearchResult;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.LogUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/21.
 */

public class SearchResultPresenter extends BasePresenter<SearchResultContract.View> implements SearchResultContract.Presenter {

    private SearchResultContract.View view;
    private boolean isRefresh = true;
    private int currentPage;

    public SearchResultPresenter(SearchResultContract.View view) {
        this.view = view;
    }

    @Override
    public void autoRefresh(String key) {
        isRefresh = true;
        currentPage = 0;
        getSearchResult(currentPage, key);
    }

    @Override
    public void loadMore(String key) {
        isRefresh = false;
        currentPage++;
        getSearchResult(currentPage, key);
    }

    @Override
    public void getSearchResult(int page, String key) {
        ApiStore.createApi(ApiService.class)
                .getSearchResult(page, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<SearchResult>>() {
                    @Override
                    public void onNext(BaseResp<SearchResult> listBaseResp) {
                        if (listBaseResp.getErrorCode() == Constant.ZERO) {
                            view.getSearchResultOk(listBaseResp.getData(), isRefresh);
                        }
                    }

                    @Override
                    public void onErrorInfo(BaseResp<SearchResult> listBaseResp) {
                        if (listBaseResp.getData() == null) {
                            view.getSearchResultErr(listBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getSearchResultErr(e.getMessage());
                    }
                });
    }
}

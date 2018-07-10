package com.xy.wanandroid.presenter.mine;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.CollectContract;
import com.xy.wanandroid.data.mine.CollectBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/25.
 */

public class CollectPresenter extends BasePresenter<CollectContract.View> implements CollectContract.Presenter {

    private CollectContract.View view;
    private int currentPage;
    private boolean isRefresh = true;

    public CollectPresenter(CollectContract.View view) {
        this.view = view;
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getCollectList(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getCollectList(currentPage);
    }

    @Override
    public void getCollectList(int page) {
        ApiStore.createApi(ApiService.class)
                .getCollectList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<CollectBean>>() {
                    @Override
                    public void onNext(BaseResp<CollectBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            view.getCollectListOk(baseResp.getData(), isRefresh);
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            view.getCollectListErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.getCollectListErr(e.getMessage());
                    }
                });
    }

//    @Override
//    public void cancelCollect(int id) {
//        ApiStore.createApi(ApiService.class)
//                .cancelCollectArticle(id)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new HttpObserver<BaseResp>() {
//                    @Override
//                    public void onNext(BaseResp baseResp) {
//                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
//                            view.cancelCollectOk((String) baseResp.getData());
//                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
//                            view.cancelCollectErr(baseResp.getErrorMsg());
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.cancelCollectErr(e.getMessage());
//                    }
//                });
//    }


}

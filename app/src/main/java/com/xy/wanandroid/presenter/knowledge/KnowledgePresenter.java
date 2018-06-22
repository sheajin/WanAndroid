package com.xy.wanandroid.presenter.knowledge;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.KnowledgeContract;
import com.xy.wanandroid.data.knowledge.KnowledgeListBean;
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
public class KnowledgePresenter extends BasePresenter<KnowledgeContract.View> implements KnowledgeContract.Presenter {

    private KnowledgeContract.View view;
    private boolean isRefresh = true;

    public KnowledgePresenter(KnowledgeContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(KnowledgeContract.View view) {
        super.attachView(view);
    }

    @Override
    public void detachView() {

    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        getKnowledgeList();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        getKnowledgeList();
    }

    @Override
    public void getKnowledgeList() {
        ApiStore.createApi(ApiService.class)
                .getKnowledgeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<KnowledgeListBean>>>() {
                    @Override
                    public void onNext(BaseResp<List<KnowledgeListBean>> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            view.getKnowledgeListOk(baseResp.getData(), isRefresh);
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            view.getKnowledgeListErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.getKnowledgeListErr(e.getMessage());
                    }
                });
    }
}

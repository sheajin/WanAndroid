package com.xy.wanandroid.presenter.knowledge;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.KnowledgeClassifyContract;
import com.xy.wanandroid.data.knowledge.KnowledgeClassifyListBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/18.
 */
public class KnowledgeClassifyPresenter extends BasePresenter<KnowledgeClassifyContract.View> implements KnowledgeClassifyContract.Presenter {
    private KnowledgeClassifyContract.View view;
    private int currentPage;
    private int cid;
    private boolean isRefresh = true;

    public KnowledgeClassifyPresenter(KnowledgeClassifyContract.View view) {
        this.view = view;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getKnowledgeClassifyList(currentPage, getCid());
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getKnowledgeClassifyList(currentPage, getCid());
    }

    @Override
    public void getKnowledgeClassifyList(int page, int id) {
        ApiStore.createApi(ApiService.class)
                .getKnowledgeClassifyList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<KnowledgeClassifyListBean>>() {
                    @Override
                    public void onNext(BaseResp<KnowledgeClassifyListBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            view.getKnowledgeClassifyListOk(baseResp.getData(), isRefresh);
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            view.getKnowledgeClassifyListErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.getKnowledgeClassifyListErr(e.getMessage());
                    }
                });
    }

    @Override
    public void collectArticle(int id) {
        ApiStore.createApi(ApiService.class)
                .collectArticle(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp>() {
                    @Override
                    public void onNext(BaseResp baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            view.collectArticleOK((String) baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            view.collectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.collectArticleErr(e.getMessage());
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
                            view.cancelCollectArticleOK((String) baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            view.cancelCollectArticleErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.cancelCollectArticleErr(e.getMessage());
                    }
                });
    }
}

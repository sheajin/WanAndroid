package com.xy.wanandroid.presenter.project;


import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.ProjectListContract;
import com.xy.wanandroid.data.project.ProjectListBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.LogUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/17.
 */
public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {
    private int cid;
    private int currentPage = 1;
    private boolean isRefresh = true;

    @Inject
    public ProjectListPresenter() {

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
        currentPage = 1;
        getProjectList(currentPage, getCid());
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getProjectList(currentPage, getCid());
    }

    @Override
    public void getProjectList(int page, int id) {
        LogUtil.e("page = " + page + ",id = " + id);
        ApiStore.createApi(ApiService.class)
                .getProjectList(page, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<ProjectListBean>>() {
                    @Override
                    public void onNext(BaseResp<ProjectListBean> listBaseResp) {
                        if (listBaseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            mView.getProjectListOk(listBaseResp.getData(), isRefresh);
                        } else if (listBaseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            mView.getProjectListErr(listBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.getProjectListErr(e.getMessage());
                    }
                });
    }

}



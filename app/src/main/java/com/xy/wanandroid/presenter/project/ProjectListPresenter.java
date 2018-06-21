package com.xy.wanandroid.presenter.project;


import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.ProjectListContract;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.util.app.LogUtil;

import com.xy.wanandroid.data.project.ProjectListBean;
import com.xy.wanandroid.model.api.BaseResp;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/17.
 */
public class ProjectListPresenter extends BasePresenter<ProjectListContract.View> implements ProjectListContract.Presenter {
    private ProjectListContract.View view;
    private int cid;
    private int currentPage = 1;
    private boolean isRefresh = true;


    public ProjectListPresenter(ProjectListContract.View view) {
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
                        if (listBaseResp.getErrorCode() == Constant.ZERO) {
                            view.getProjectListOk(listBaseResp.getData(), isRefresh);
                        }
                    }

                    @Override
                    public void onErrorInfo(BaseResp<ProjectListBean> listBaseResp) {
                        if (listBaseResp.getErrorCode() < Constant.ZERO) {
                            view.getProjectListErr(listBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getProjectListErr(e.getMessage());
                    }
                });
    }

}



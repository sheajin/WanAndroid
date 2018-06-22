package com.xy.wanandroid.presenter.project;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.ProjectContract;
import com.xy.wanandroid.data.project.ProjectBean;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/17.
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {

    private ProjectContract.View view;

    public ProjectPresenter(ProjectContract.View view) {
        this.view = view;
    }


    @Override
    public void getProjectTitle() {
        ApiStore.createApi(ApiService.class)
                .getProjectTitle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<List<ProjectBean>>>() {
                    @Override
                    public void onNext(BaseResp<List<ProjectBean>> listBaseResp) {
                        if (listBaseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            view.getProjectTitleOk(listBaseResp.getData());
                        } else if (listBaseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            view.getProjectTitleErr(listBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.getProjectTitleErr(e.getMessage());
                    }
                });
    }
}

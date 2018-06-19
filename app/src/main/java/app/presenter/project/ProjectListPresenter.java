package app.presenter.project;


import java.util.List;

import app.base.presenter.BasePresenter;
import app.contract.ProjectListContract;
import app.data.project.ProjectListBean;
import app.model.api.ApiService;
import app.model.api.ApiStore;
import app.model.api.BaseResp;
import app.model.api.HttpObserver;
import app.model.constant.Constant;
import app.util.app.LogUtil;
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



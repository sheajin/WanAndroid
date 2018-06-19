package app.presenter.project;

import java.util.List;

import app.base.presenter.BasePresenter;
import app.contract.ProjectContract;
import app.data.project.ProjectBean;
import app.model.api.ApiService;
import app.model.api.ApiStore;
import app.model.api.BaseResp;
import app.model.api.HttpObserver;
import app.model.constant.Constant;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/17.
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter{

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
                        if (listBaseResp.getErrorCode() == Constant.ZERO){
                            view.getProjectTitleOk(listBaseResp.getData());
                        }
                    }

                    @Override
                    public void onErrorInfo(BaseResp<List<ProjectBean>> listBaseResp) {
                        if (listBaseResp.getErrorCode() == Constant.ZERO){
                            view.getProjectTitleErr(listBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getProjectTitleErr(e.getMessage());
                    }
                });
    }
}

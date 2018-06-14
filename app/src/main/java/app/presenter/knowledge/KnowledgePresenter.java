package app.presenter.knowledge;

import java.util.List;

import app.base.presenter.BasePresenter;
import app.model.api.ApiService;
import app.model.api.ApiStore;
import app.model.api.BaseResp;
import app.model.api.HttpObserver;
import app.model.constant.Constant;
import app.model.contract.KnowledgeContract;
import app.model.data.knowledge.KnowledgeListBean;
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
                        if (baseResp.getErrorCode() == Constant.ZERO) {
                            view.getKnowledgeListOk(baseResp.getData(), isRefresh);
                        }
                    }

                    @Override
                    public void onErrorInfo(BaseResp<List<KnowledgeListBean>> baseResp) {
                        if (baseResp.getErrorCode() != Constant.ZERO) {
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

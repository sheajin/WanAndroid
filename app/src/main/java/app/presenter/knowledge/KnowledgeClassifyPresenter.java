package app.presenter.knowledge;

import java.util.List;

import app.base.presenter.BasePresenter;
import app.contract.KnowledgeClassifyContract;
import app.contract.ProjectListContract;
import app.data.knowledge.KnowledgeClassifyListBean;
import app.model.api.ApiService;
import app.model.api.ApiStore;
import app.model.api.BaseResp;
import app.model.api.HttpObserver;
import app.model.constant.Constant;
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
                        if (baseResp.getErrorCode() == Constant.ZERO) {
                            view.getKnowledgeClassifyListOk(baseResp.getData(), isRefresh);
                        }
                    }

                    @Override
                    public void onErrorInfo(BaseResp<KnowledgeClassifyListBean> listBaseResp) {
                        if (listBaseResp.getErrorCode() < Constant.ZERO) {
                            view.getKnowledgeClassifyListErr(listBaseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getKnowledgeClassifyListErr(e.getMessage());
                    }
                });
    }
}

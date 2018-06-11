package app.presenter.main;

import app.base.presenter.BasePresenter;
import app.model.api.ApiService;
import app.model.api.ApiStore;
import app.model.api.BaseResp;
import app.model.api.HttpObserver;
import app.model.constant.Constant;
import app.model.contract.HomePageContract;
import app.model.data.main.HomePageArticleBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/10.
 */
public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {

        private HomePageContract.View view;
    private boolean isRefresh = true;
    private int currentPage;

    public HomePagePresenter(HomePageContract.View view) {
        this.view = view;
    }

    @Override
    public void attachView(HomePageContract.View view) {
        super.attachView(view);
    }

    @Override
    public void autoRefresh() {
        isRefresh = true;
        currentPage = 0;
        getHomepageList(currentPage);
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        currentPage++;
        getHomepageList(currentPage);
    }

    @Override
    public void getHomepageList(int page) {
        ApiStore.createApi(ApiService.class)
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<HomePageArticleBean>>() {
                    @Override
                    public void onNext(BaseResp<HomePageArticleBean> baseResp) {
                        if (baseResp.getErrorCode() == Constant.ZERO) {
                            view.getHomepageListOk(baseResp.getData(), isRefresh);
                        }
                    }

                    @Override
                    public void onErrorInfo(BaseResp<HomePageArticleBean> articleBean) {
                        view.getHomepageListErr(articleBean.getErrorMsg());
                    }
                });
    }
}

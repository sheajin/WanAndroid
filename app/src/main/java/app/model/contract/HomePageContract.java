package app.model.contract;

import java.util.List;

import app.base.presenter.AbsPresenter;
import app.base.view.AbstractView;
import app.model.data.main.BannerBean;
import app.model.data.main.HomePageArticleBean;

/**
 * Created by jxy on 2018/2/2.
 */

public class HomePageContract {

    public interface View extends AbstractView {

        void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh);

        void getHomepageListErr(String info);

        void getBannerOk(List<BannerBean> bannerBean);

        void getBannerErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh();

        void loadMore();

        void getHomepageList(int page);

        void getBanner();
    }
}


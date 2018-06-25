package com.xy.wanandroid.contract;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.login.UserInfo;
import com.xy.wanandroid.data.main.BannerBean;
import com.xy.wanandroid.data.main.HomePageArticleBean;

import java.util.List;

/**
 * Created by jxy on 2018/2/2.
 */

public class HomePageContract {

    public interface View extends AbstractView {

        void getHomepageListOk(HomePageArticleBean dataBean, boolean isRefresh);

        void getHomepageListErr(String info);

        void getBannerOk(List<BannerBean> bannerBean);

        void getBannerErr(String info);

        void loginSuccess(UserInfo userInfo);

        void loginErr(String info);

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);
    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh();

        void loadMore();

        void getHomepageList(int page);

        void getBanner();

        void loginAndLoad();

        void collectArticle(int id);

        void cancelCollectArticle(int id);
    }
}


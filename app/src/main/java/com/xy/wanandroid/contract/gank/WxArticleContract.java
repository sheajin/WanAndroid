package com.xy.wanandroid.contract.gank;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.gank.WxArticleList;

/**
 * Created by jxy on 2018/10/31
 */
public class WxArticleContract {

    public interface View extends AbstractView {

        void getWxArticleListOK(WxArticleList data, boolean isRefresh);

        void getWxArticleListErr(String info);

    }

    public interface Presenter extends AbsPresenter<WxArticleContract.View> {

        void getWxArticleList(String id, int page);

        void autoRefresh(String id);

        void loadMore(String id);
    }
}

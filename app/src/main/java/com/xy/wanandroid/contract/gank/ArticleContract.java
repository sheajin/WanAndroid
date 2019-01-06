package com.xy.wanandroid.contract.gank;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.gank.ArticleTitleBean;

import java.util.List;

/**
 * Created by jxy on 2018/10/31
 */

public class ArticleContract {

    public interface View extends AbstractView {

        void getArticleTitleOk(List<ArticleTitleBean> data);

        void getArticleTitleErr(String info);

    }

    public interface Presenter extends AbsPresenter<ArticleContract.View> {

        void getArticleTitle();

    }
}

package com.xy.wanandroid.contract;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;

/**
 * Created by jxy on 2018/6/27.
 */

public class ArticleDetailContact {

    public interface View extends AbstractView {

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);
    }

    public interface Presenter extends AbsPresenter<ArticleDetailContact.View> {

        void collectArticle(int id);

        void cancelCollectArticle(int id);
    }
}

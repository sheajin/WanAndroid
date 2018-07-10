package com.xy.wanandroid.contract;

import com.xy.wanandroid.data.knowledge.KnowledgeClassifyListBean;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;

/**
 * Created by jxy on 2018/6/18.
 */
public class KnowledgeClassifyContract {

    public interface View extends AbstractView {

        void getKnowledgeClassifyListOk(KnowledgeClassifyListBean knowledgeClassifyListBean, boolean isRefresh);

        void getKnowledgeClassifyListErr(String info);

        void collectArticleOK(String info);

        void collectArticleErr(String info);

        void cancelCollectArticleOK(String info);

        void cancelCollectArticleErr(String info);

    }

    public interface Presenter extends AbsPresenter<KnowledgeClassifyContract.View> {

        void autoRefresh();

        void loadMore();

        void getKnowledgeClassifyList(int page,int id);

        void collectArticle(int id);

        void cancelCollectArticle(int id);

    }
}

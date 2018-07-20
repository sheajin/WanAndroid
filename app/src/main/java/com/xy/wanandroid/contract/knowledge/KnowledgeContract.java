package com.xy.wanandroid.contract.knowledge;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.data.knowledge.KnowledgeListBean;

import java.util.List;

import com.xy.wanandroid.base.view.AbstractView;

/**
 * Created by jxy on 2018/6/14.
 */

public class KnowledgeContract {

    public interface View extends AbstractView {

        void getKnowledgeListOk(List<KnowledgeListBean> dataBean, boolean isRefresh);

        void getKnowledgeListErr(String info);

    }

    public interface Presenter extends AbsPresenter<View> {

        void autoRefresh();

        void loadMore();

        void getKnowledgeList();

    }
}

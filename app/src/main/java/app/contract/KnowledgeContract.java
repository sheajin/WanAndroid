package app.contract;

import java.util.List;

import app.base.presenter.AbsPresenter;
import app.base.view.AbstractView;
import app.data.knowledge.KnowledgeListBean;

/**
 * Created by jxy on 2018/6/14.
 */

public class KnowledgeContract {

    public interface View extends AbstractView {

        void getKnowledgeListOk(List<KnowledgeListBean> dataBean, boolean isRefresh);

        void getKnowledgeListErr(String info);

    }

    public interface Presenter extends AbsPresenter<KnowledgeContract.View> {

        void autoRefresh();

        void loadMore();

        void getKnowledgeList();

    }
}

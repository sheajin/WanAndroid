package app.contract;

import java.util.List;

import app.base.presenter.AbsPresenter;
import app.base.view.AbstractView;
import app.data.knowledge.KnowledgeClassifyListBean;

/**
 * Created by jxy on 2018/6/18.
 */
public class KnowledgeClassifyContract {

    public interface View extends AbstractView {

        void getKnowledgeClassifyListOk(KnowledgeClassifyListBean knowledgeClassifyListBean, boolean isRefresh);

        void getKnowledgeClassifyListErr(String info);

    }

    public interface Presenter extends AbsPresenter<KnowledgeClassifyContract.View> {

        void autoRefresh();

        void loadMore();

        void getKnowledgeClassifyList(int page,int id);

    }
}

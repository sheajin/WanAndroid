package app.contract;

import java.util.List;

import app.base.presenter.AbsPresenter;
import app.base.view.AbstractView;
import app.data.project.ProjectListBean;

/**
 * Created by jxy on 2018/6/17.
 */
public class ProjectListContract {

    public interface View extends AbstractView {

        void getProjectListOk(ProjectListBean projectListBean, boolean isRefresh);

        void getProjectListErr(String info);

    }

    public interface Presenter extends AbsPresenter<ProjectListContract.View> {

        void autoRefresh();

        void loadMore();

        void getProjectList(int page,int id);

    }
}

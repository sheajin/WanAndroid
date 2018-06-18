package app.contract;

import java.util.List;

import app.base.presenter.AbsPresenter;
import app.base.view.AbstractView;
import app.data.project.ProjectBean;

/**
 * Created by jxy on 2018/6/17.
 */
public class ProjectContract {

    public interface View extends AbstractView {

        void getProjectTitleOk(List<ProjectBean> dataBean);

        void getProjectTitleErr(String info);

    }

    public interface Presenter extends AbsPresenter<ProjectContract.View> {

        void getProjectTitle();

    }
}

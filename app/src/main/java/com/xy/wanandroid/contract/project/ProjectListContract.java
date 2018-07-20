package com.xy.wanandroid.contract.project;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.project.ProjectListBean;

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

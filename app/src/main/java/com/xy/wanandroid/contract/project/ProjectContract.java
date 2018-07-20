package com.xy.wanandroid.contract.project;

import java.util.List;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.project.ProjectBean;

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

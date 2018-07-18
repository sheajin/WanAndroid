package com.xy.wanandroid.ui.project.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.adapter.SimpleFragmentStateAdapter;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.ProjectContract;
import com.xy.wanandroid.data.project.ProjectBean;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.presenter.project.ProjectPresenter;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectFragment extends BaseRootFragment<ProjectPresenter> implements ProjectContract.View {

    @BindView(R.id.project_tab_layout)
    SlidingTabLayout mProjectTabLayout;
    @BindView(R.id.pager_project)
    ViewPager mProjectPager;

    private SimpleFragmentStateAdapter adapter;
    private List<Fragment> fragments;
    private List<ProjectBean> projectList;
    private List<String> titles;
    private String[] titleArray;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
    }

    @Override
    protected void initData() {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        projectList = new ArrayList<>();
        adapter = new SimpleFragmentStateAdapter(getChildFragmentManager(), fragments);
        mPresenter.getProjectTitle();
    }

    public static ProjectFragment getInstance() {
        return new ProjectFragment();
    }

    @Override
    public void getProjectTitleOk(List<ProjectBean> dataBean) {
        mProjectTabLayout.setVisibility(View.VISIBLE);
        projectList.clear();
        fragments.clear();
        titles.clear();
        projectList.addAll(dataBean);
        if (projectList.size() > 0) {
            for (ProjectBean projectBean : projectList) {
                fragments.add(ProjectListFragment.getInstance(projectBean.getId()));
                titles.add(projectBean.getName());
            }
            initTabAndPager();
        }
        showNormal();
    }

    /**
     * tab设置数据以及viewpager监听
     */
    private void initTabAndPager() {
        titleArray = titles.toArray(new String[titles.size()]);
        mProjectPager.setAdapter(adapter);
        mProjectTabLayout.setViewPager(mProjectPager, titleArray);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getProjectTitleErr(String info) {
        ToastUtil.show(activity, info);
        showError();
    }

    @Override
    public void reload() {
        super.reload();
        if (mPresenter != null) {
            mPresenter.getProjectTitle();
        }
        showLoading();
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getCode() == EventConstant.PROJECTLOADERR){
            showError();
        }
    }
}

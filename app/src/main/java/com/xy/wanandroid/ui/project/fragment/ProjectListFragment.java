package com.xy.wanandroid.ui.project.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.ProjectListContract;
import com.xy.wanandroid.data.project.ProjectListBean;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.presenter.project.ProjectListPresenter;
import com.xy.wanandroid.ui.main.activity.ArticleDetailsActivity;
import com.xy.wanandroid.ui.project.adapter.ProjectListAdapter;
import com.xy.wanandroid.util.app.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ProjectListFragment extends BaseRootFragment implements ProjectListContract.View, ProjectListAdapter.OnItemClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private int cid;
    private ProjectListPresenter presenter;
    private List<ProjectListBean.DatasBean> projectList;
    private ProjectListAdapter mAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_project_list;
    }

    public static ProjectListFragment getInstance(int cid) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.PROJECT_CID, cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        setRefresh();
        presenter = new ProjectListPresenter(this);
        projectList = new ArrayList<>();
        if (getArguments() != null) {
            cid = getArguments().getInt(Constant.PROJECT_CID);
            presenter.getProjectList(1, cid);
        }
        mAdapter = new ProjectListAdapter(R.layout.item_project_list, projectList);
        mAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getProjectListOk(ProjectListBean dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            projectList = dataBean.getDatas();
            mAdapter.replaceData(dataBean.getDatas());
        } else {
            if (dataBean.getDatas().size() > 0) {
                projectList.addAll(dataBean.getDatas());
                mAdapter.addData(dataBean.getDatas());
            } else {
                ToastUtil.show(context, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void getProjectListErr(String info) {
        ToastUtil.show(context, info);
        showError();
        EventBus.getDefault().post(new MessageEvent(EventConstant.PROJECTLOADERR, ""));
    }

    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.setCid(cid);
            presenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.setCid(cid);
            presenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        switch (event.getCode()) {
            case EventConstant.PROJECTSCROLLTOTOP:
                mRv.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        smartRefreshLayout = null;
        projectList = null;
        mAdapter = null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ARTICLE_TITLE, mAdapter.getData().get(position).getTitle());
        bundle.putString(Constant.ARTICLE_LINK, mAdapter.getData().get(position).getLink());
        bundle.putInt(Constant.ARTICLE_ID, mAdapter.getData().get(position).getId());
        bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, mAdapter.getData().get(position).isCollect());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.share_view));
        startActivity(new Intent(activity, ArticleDetailsActivity.class).putExtras(bundle), options.toBundle());
    }
}

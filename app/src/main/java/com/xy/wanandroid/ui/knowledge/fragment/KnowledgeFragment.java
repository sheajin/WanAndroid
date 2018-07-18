package com.xy.wanandroid.ui.knowledge.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.KnowledgeContract;
import com.xy.wanandroid.data.knowledge.KnowledgeListBean;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.presenter.knowledge.KnowledgePresenter;
import com.xy.wanandroid.ui.knowledge.activity.KnowledgeClassifyActivity;
import com.xy.wanandroid.ui.knowledge.adapter.KnowledgeAdapter;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KnowledgeFragment extends BaseRootFragment<KnowledgePresenter> implements KnowledgeContract.View,KnowledgeAdapter.OnItemClickListener {
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<KnowledgeListBean> knowledgeList;
    private KnowledgeAdapter mAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
        mRv.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void initData() {
        setRefresh();
        knowledgeList = new ArrayList<>();
        mPresenter.getKnowledgeList();
        mAdapter = new KnowledgeAdapter(R.layout.item_knowledge, knowledgeList);
        mAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    public static KnowledgeFragment getInstance() {
        return new KnowledgeFragment();
    }

    public void onMessageEvent(MessageEvent event) {
        switch (event.getCode()){
            case EventConstant.KNOWLEDGESCROLLTOTOP:
                mRv.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    public void getKnowledgeListOk(List<KnowledgeListBean> dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            knowledgeList = dataBean;
            mAdapter.replaceData(dataBean);
        } else {
            ToastUtil.show(context, getString(R.string.load_more_no_data));
        }
        showNormal();
    }

    @Override
    public void getKnowledgeListErr(String info) {
        showError();
    }

    @Override
    public void reload() {
        showLoading();
        mPresenter.getKnowledgeList();
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMore();
            refreshLayout.finishLoadMore(1000);
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.share_view));
        Intent intent = new Intent(activity, KnowledgeClassifyActivity.class);
        intent.putExtra(Constant.KNOWLEDGE,mAdapter.getData().get(position));
        startActivity(intent,options.toBundle());
    }
}

package com.xy.wanandroid.ui.knowledge.fragment;

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
import com.xy.wanandroid.contract.KnowledgeClassifyContract;
import com.xy.wanandroid.data.knowledge.KnowledgeClassifyListBean;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.presenter.knowledge.KnowledgeClassifyPresenter;
import com.xy.wanandroid.ui.knowledge.adapter.KnowledgeClassifyAdapter;
import com.xy.wanandroid.ui.login.LoginActivity;
import com.xy.wanandroid.ui.main.activity.ArticleDetailsActivity;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class KnowledgeListFragment extends BaseRootFragment<KnowledgeClassifyPresenter> implements KnowledgeClassifyContract.View
        , KnowledgeClassifyAdapter.OnItemClickListener, KnowledgeClassifyAdapter.OnItemChildClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private int cid, clickPosition;
    private KnowledgeClassifyPresenter presenter;
    private List<KnowledgeClassifyListBean.DatasBean> knowledgeList;
    private KnowledgeClassifyAdapter mAdapter;

    public static KnowledgeListFragment getInstance(int cid) {
        KnowledgeListFragment fragment = new KnowledgeListFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.KNOWLEDGE_CID, cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge_list;
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
        presenter = new KnowledgeClassifyPresenter(this);
        knowledgeList = new ArrayList<>();
        if (getArguments() != null) {
            cid = getArguments().getInt(Constant.KNOWLEDGE_CID);
            presenter.getKnowledgeClassifyList(0, cid);
        }
        mAdapter = new KnowledgeClassifyAdapter(R.layout.item_homepage, knowledgeList);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public void getKnowledgeClassifyListOk(KnowledgeClassifyListBean dataBean, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh) {
            knowledgeList = dataBean.getDatas();
            mAdapter.replaceData(dataBean.getDatas());
        } else {
            if (dataBean.getDatas().size() > 0) {
                knowledgeList.addAll(dataBean.getDatas());
                mAdapter.addData(dataBean.getDatas());
            } else {
                ToastUtil.show(context, getString(R.string.load_more_no_data));
            }
        }
        showNormal();
    }

    @Override
    public void getKnowledgeClassifyListErr(String info) {
        ToastUtil.show(context, info);
        showError();
        EventBus.getDefault().post(new MessageEvent(EventConstant.KNOWLEDGELOADERR, ""));
    }


    @Override
    public void collectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > clickPosition) {
            ToastUtil.show(activity, getString(R.string.collect_success));
            mAdapter.getData().get(clickPosition).setCollect(true);
            mAdapter.setData(clickPosition, mAdapter.getData().get(clickPosition));
        }
    }

    @Override
    public void collectArticleErr(String info) {
        ToastUtil.show(activity, getString(R.string.please_login));
        JumpUtil.overlay(activity, LoginActivity.class);
    }

    @Override
    public void cancelCollectArticleOK(String info) {
        if (mAdapter != null && mAdapter.getData().size() > clickPosition) {
            ToastUtil.show(activity, getString(R.string.cancel_collect_success));
            mAdapter.getData().get(clickPosition).setCollect(false);
            mAdapter.setData(clickPosition, mAdapter.getData().get(clickPosition));
        }
    }

    @Override
    public void cancelCollectArticleErr(String info) {
        ToastUtil.show(activity, info);
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
            case EventConstant.KNOWLEDGECLASSIFYSCROLLTOTOP:
                mRv.smoothScrollToPosition(0);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        smartRefreshLayout = null;
        knowledgeList = null;
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        clickPosition = position;
        if ((Boolean) SharedPreferenceUtil.get(context, Constant.ISLOGIN, Constant.FALSE)) {
            if (mAdapter.getData().get(clickPosition).isCollect()) {
                presenter.cancelCollectArticle(mAdapter.getData().get(clickPosition).getId());
            } else {
                presenter.collectArticle(mAdapter.getData().get(clickPosition).getId());
            }
        } else {
            ToastUtil.show(activity, getString(R.string.please_login));
            JumpUtil.overlay(activity, LoginActivity.class);
        }
    }
}

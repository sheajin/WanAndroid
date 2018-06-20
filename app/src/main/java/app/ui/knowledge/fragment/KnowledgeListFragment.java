package app.ui.knowledge.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import app.base.fragment.BaseRootFragment;
import app.contract.KnowledgeClassifyContract;
import app.data.knowledge.KnowledgeClassifyListBean;
import app.model.constant.Constant;
import app.model.constant.EventConstant;
import app.model.constant.MessageEvent;
import app.presenter.knowledge.KnowledgeClassifyPresenter;
import app.ui.knowledge.adapter.KnowledgeClassifyAdapter;
import app.ui.main.activity.ArticleDetailsActivity;
import app.util.app.ToastUtil;
import butterknife.BindView;

public class KnowledgeListFragment extends BaseRootFragment<KnowledgeClassifyPresenter> implements KnowledgeClassifyContract.View, KnowledgeClassifyAdapter.OnItemClickListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private int cid;
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
        if (event.getCode() == EventConstant.KNOWLEDGECLASSIFYSCROLLTOTOP) {
            mRv.smoothScrollToPosition(0);
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
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, view, getString(R.string.share_view));
        startActivity(new Intent(activity, ArticleDetailsActivity.class).putExtras(bundle), options.toBundle());
    }

}

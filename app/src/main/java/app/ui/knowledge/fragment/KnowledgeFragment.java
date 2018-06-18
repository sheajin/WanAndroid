package app.ui.knowledge.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import app.base.fragment.BaseRootFragment;
import app.model.constant.Constant;
import app.model.constant.EventConstant;
import app.model.constant.MessageEvent;
import app.contract.KnowledgeContract;
import app.data.knowledge.KnowledgeListBean;
import app.presenter.knowledge.KnowledgePresenter;
import app.ui.knowledge.activity.KnowledgeClassifyActivity;
import app.ui.knowledge.adapter.KnowledgeAdapter;
import app.util.app.ToastUtil;
import butterknife.BindView;

public class KnowledgeFragment extends BaseRootFragment<KnowledgePresenter> implements KnowledgeContract.View,KnowledgeAdapter.OnItemClickListener {
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<KnowledgeListBean> knowledgeList;
    private KnowledgeAdapter mAdapter;
    private KnowledgePresenter presenter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge;
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
        presenter = new KnowledgePresenter(this);
        presenter.getKnowledgeList();
        mAdapter = new KnowledgeAdapter(R.layout.item_knowledge, knowledgeList);
        mAdapter.setOnItemClickListener(this);
        mRv.setAdapter(mAdapter);
    }

    public static KnowledgeFragment getInstance() {
        return new KnowledgeFragment();
    }

    public void onMessageEvent(MessageEvent event) {
        if (event.getCode() == EventConstant.SCROLLTOTOP) {
            mRv.smoothScrollToPosition(0);
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
        presenter.getKnowledgeList();
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            presenter.autoRefresh();
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            presenter.loadMore();
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

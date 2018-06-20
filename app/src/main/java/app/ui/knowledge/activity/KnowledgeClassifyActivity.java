package app.ui.knowledge.activity;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.xy.wanandroid.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import app.base.activity.BaseActivity;
import app.base.adapter.SimpleFragmentStateAdapter;
import app.data.knowledge.KnowledgeListBean;
import app.model.constant.Constant;
import app.model.constant.EventConstant;
import app.model.constant.MessageEvent;
import app.ui.knowledge.fragment.KnowledgeListFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class KnowledgeClassifyActivity extends BaseActivity {
    @BindView(R.id.knowledge_toolbar)
    Toolbar mKnowledgeToolbar;
    @BindView(R.id.knowledge_tab_layout)
    SlidingTabLayout mKnowledgeTabLayout;
    @BindView(R.id.knowledge_viewpager)
    ViewPager mKnowledgeViewpager;
    @BindView(R.id.float_button)
    FloatingActionButton mFloatButton;

    private List<String> titles;
    private List<Fragment> fragments;
    private SimpleFragmentStateAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_knowledge_classify;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mKnowledgeToolbar);
        mKnowledgeToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getCode() == EventConstant.KNOWLEDGELOADERR) {
            showError();
        }
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        adapter = new SimpleFragmentStateAdapter(getSupportFragmentManager(), fragments);
        if (getIntent().getBooleanExtra(Constant.HOMEPAGE_TAG, false)) {
            getHomepageBundleData();
        } else {
            getKnowledgeBundleData();
        }
    }

    /**
     * 获取从知识列表传来的数据
     */
    private void getKnowledgeBundleData() {
        KnowledgeListBean knowledgeListBean = (KnowledgeListBean) getIntent().getSerializableExtra(Constant.KNOWLEDGE);
        if (knowledgeListBean != null) {
            fragments.clear();
            getSupportActionBar().setTitle(knowledgeListBean.getName());
            for (KnowledgeListBean.ChildrenBean childrenBean : knowledgeListBean.getChildren()) {
                titles.add(childrenBean.getName());
                fragments.add(KnowledgeListFragment.getInstance(childrenBean.getId()));
            }
        }
        initTabAndPager(titles.toArray(new String[titles.size()]));
    }

    /**
     * 获取从主列表传来的数据
     */
    private void getHomepageBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int cId = bundle.getInt(Constant.HOMEPAGE_CID);
            String cName = bundle.getString(Constant.HOMEPAGE_CNAME);
            String superCName = bundle.getString(Constant.HOMEPAGE_SUPERCNAME);
            fragments.clear();
            getSupportActionBar().setTitle(superCName);
            titles.add(cName);
            fragments.add(KnowledgeListFragment.getInstance(cId));
        }
        initTabAndPager(titles.toArray(new String[titles.size()]));
    }

    @OnClick(R.id.float_button)
    void click(View view) {
        switch (view.getId()) {
            case R.id.float_button:
                EventBus.getDefault().post(new MessageEvent(EventConstant.KNOWLEDGECLASSIFYSCROLLTOTOP, ""));
                break;
        }
    }

    private void initTabAndPager(String[] titles) {
        mKnowledgeViewpager.setAdapter(adapter);
        mKnowledgeTabLayout.setViewPager(mKnowledgeViewpager, titles);
        adapter.notifyDataSetChanged();
    }

}

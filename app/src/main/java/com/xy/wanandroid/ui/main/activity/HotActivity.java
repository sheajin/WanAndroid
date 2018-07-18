package com.xy.wanandroid.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseRootActivity;
import com.xy.wanandroid.contract.HotContract;
import com.xy.wanandroid.data.main.SearchHot;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.main.HotPresenter;
import com.xy.wanandroid.util.app.CommonUtil;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.ToastUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HotActivity extends BaseRootActivity<HotPresenter> implements HotContract.View {

    @BindView(R.id.flow_hot)
    TagFlowLayout mFlowHot;
    @BindView(R.id.toolbar_hot)
    Toolbar mToolBarHot;

    private List<SearchHot> hotList;
    private TagAdapter<SearchHot> adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(mToolBarHot);
        getSupportActionBar().setTitle("常用网站");
        mToolBarHot.setNavigationOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        super.initUI();
        showLoading();
    }

    @Override
    protected void initData() {
        hotList = new ArrayList<>();
        mPresenter.getHotWeb();
    }

    @Override
    public void getHotWebOk(List<SearchHot> dataBean) {
        showNormal();
        hotList.clear();
        hotList.addAll(dataBean);
        adapter = new TagAdapter<SearchHot>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, SearchHot searchHot) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.item_flow_layout, null);
                text.setText(searchHot.getName());
                text.setTextColor(CommonUtil.randomColor());
                return text;
            }
        };
        mFlowHot.setAdapter(adapter);
        mFlowHot.setOnTagClickListener((view, position, parent) -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ARTICLE_TITLE, hotList.get(position).getName());
            bundle.putString(Constant.ARTICLE_LINK, hotList.get(position).getLink());
//            bundle.putInt(Constant.ARTICLE_ID, hotList.get(position).getId());
//            bundle.putBoolean(Constant.ARTICLE_IS_COLLECT, hotList.get(position).isCollect());
            JumpUtil.overlay(activity, ArticleDetailsActivity.class, bundle);
            return false;
        });
    }

    @Override
    public void getHotWebErr(String info) {
        showError();
        ToastUtil.show(context, info);
    }
}

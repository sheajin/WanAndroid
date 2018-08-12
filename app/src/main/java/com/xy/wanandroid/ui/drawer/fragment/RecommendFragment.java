package com.xy.wanandroid.ui.drawer.fragment;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseRootFragment;
import com.xy.wanandroid.contract.drawer.RecommendContract;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.presenter.drawer.RecommendListPresenter;
import com.xy.wanandroid.ui.drawer.adapter.RecommendAdapter;
import com.xy.wanandroid.data.drawer.RecommendEntity;
import com.xy.wanandroid.util.app.DisplayUtil;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecommendFragment extends BaseRootFragment<RecommendListPresenter> implements RecommendContract.View {
    @BindView(R.id.normal_view)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv)
    RecyclerView mRv;

    private BottomSheet.Builder bottomSheet = null;
    private List<RecommendEntity.ResultsBean> entityList;
    private RecommendAdapter mAdapter;
    private TextView mTvCate;
    private int page = 1;
    private String type = Constant.ALL;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initInjector() {
        super.initInjector();
        mFragmentComponent.inject(this);
    }

    public static RecommendFragment getInstance() {
        return new RecommendFragment();
    }

    @Override
    protected void initUI() {
        super.initUI();
        showNeteaseLoading();
        mRv.setLayoutManager(new LinearLayoutManager(activity));
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                if (itemPosition == entityList.size())
                    outRect.bottom = DisplayUtil.dip2px(context, 12);
            }
        };
        mRv.addItemDecoration(itemDecoration);
    }

    @Override
    protected void initData() {
        setRefresh();
        entityList = new ArrayList<>();
        mPresenter.getRecommendList(Constant.ALL, 15, 1);
        mAdapter = new RecommendAdapter(entityList);
        initHeader();
        mRv.setAdapter(mAdapter);
    }

    /**
     * 初始化header
     */
    private void initHeader() {
        RelativeLayout view = (RelativeLayout) View.inflate(activity, R.layout.header_recommend, null);
        RelativeLayout headerView = view.findViewById(R.id.header_recommend);
        view.removeView(headerView);
        view.addView(headerView);
        mAdapter.addHeaderView(view);
        mTvCate = view.findViewById(R.id.tv_cate);
        View viewCate = view.findViewById(R.id.view_cate);
        mTvCate.setText(R.string.all);
        initBottomSheet();
        viewCate.setOnClickListener(v -> bottomSheet.show());
    }

    /**
     * 设置bottomSheet
     */
    private void initBottomSheet() {
        bottomSheet = new BottomSheet.Builder(activity)
                .title(getString(R.string.choose_cate))
                .sheet(R.menu.menu_category)
                .listener((dialog, which) -> {
                    showNeteaseLoading();
                    switch (which) {
                        case R.id.cate_all:
                            type = Constant.ALLS;
                            break;
                        case R.id.cate_android:
                            type = Constant.ANDROID;
                            break;
                        case R.id.cate_js:
                            type = Constant.JS;
                            break;
                        case R.id.cate_rest:
                            type = Constant.REST;
                            break;
                        case R.id.cate_extra:
                            type = Constant.EXTRA;
                            break;
                    }
                    mTvCate.setText(type);
                    if (type.equals(Constant.ALLS))
                        type = Constant.ALL;
                    mPresenter.getRecommendList(type, 15, 1);
                });
    }

    @Override
    public void getRecommendOk(RecommendEntity recommendEntity, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
        if (isRefresh)
            entityList.clear();
        addToList(recommendEntity);
        showNormal();
    }

    @Override
    public void getRecommendErr(String info) {
        ToastUtil.show(activity, info);
        LogUtil.e(info);
        showError();
    }

    /**
     * 处理列表数据,分类添加
     *
     * @param recommendEntity
     */
    private void addToList(RecommendEntity recommendEntity) {
        for (RecommendEntity.ResultsBean recommend : recommendEntity.getResults()) {
            if (recommend.getType().equals(Constant.WELFARE)) {
                RecommendEntity.ResultsBean entityWelfare = new RecommendEntity.ResultsBean(RecommendEntity.ResultsBean.ENTITY_PICTURE, recommend.getPublishedAt(), recommend.getWho(), recommend.getType(), recommend.getUrl());
                entityList.add(entityWelfare);
            } else {
                if (recommend.getImages().size() == 0) {
                    RecommendEntity.ResultsBean entitySimple = new RecommendEntity.ResultsBean(RecommendEntity.ResultsBean.ENTITY_TEXT, recommend.getDesc(), recommend.getPublishedAt(), recommend.getWho());
                    entityList.add(entitySimple);
                } else {
                    RecommendEntity.ResultsBean entityComplex = new RecommendEntity.ResultsBean(RecommendEntity.ResultsBean.ENTITY_IMG, recommend.getDesc(), recommend.getPublishedAt(), recommend.getWho(), recommend.getImages());
                    entityList.add(entityComplex);
                }
            }
        }
        mAdapter.replaceData(entityList);
    }

    /**
     * SmartRefreshLayout刷新加载
     */
    private void setRefresh() {
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.autoRefresh(type, page);
            refreshLayout.finishRefresh(1000);
        });
        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            mPresenter.loadMore(type, page);
            refreshLayout.finishLoadMore(1000);
        });
    }
}

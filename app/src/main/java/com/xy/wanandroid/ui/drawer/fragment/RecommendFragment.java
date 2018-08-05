package com.xy.wanandroid.ui.drawer.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cocosw.bottomsheet.BottomSheet;
import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.contract.drawer.RecommendContract;
import com.xy.wanandroid.data.drawer.RecommendData;
import com.xy.wanandroid.presenter.drawer.RecommendListPresenter;
import com.xy.wanandroid.ui.drawer.adapter.RecommendAdapter;
import com.xy.wanandroid.ui.drawer.viewholder.RecommendEntity;
import com.xy.wanandroid.util.app.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RecommendFragment extends BaseFragment<RecommendListPresenter> implements RecommendContract.View{

    @BindView(R.id.rv)
    RecyclerView mRv;

    private BottomSheet.Builder builder = null;
    private LinearLayoutManager manager;
    private List<RecommendData> list;
    private List<RecommendEntity> entityList;
    private RecommendAdapter mAdapter;

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
        manager = new LinearLayoutManager(activity);
        RecommendEntity entitySimple = new RecommendEntity();
        entitySimple.type = 1;
        RecommendEntity entityComplex = new RecommendEntity();
        entityComplex.type = 2;
        entityList = new ArrayList<>();
        entityList.add(entitySimple);
        entityList.add(entityComplex);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        mPresenter.getRecommendList("Android", 1, 10);
        mRv.setLayoutManager(manager);
        mAdapter = new RecommendAdapter(entityList);
        mRv.setAdapter(mAdapter);
    }


    @Override
    public void getRecommendOk(List<RecommendData> recommendData, boolean isRefresh) {
        if (mAdapter == null) {
            return;
        }
//        if (isRefresh) {
//            list = recommendData;
//            mAdapter.replaceData();
//        } else {
//            list.addAll(recommendData);
//            mAdapter.addData(recommendData);
//        }
        showNormal();
    }

    @Override
    public void getRecommendErr(String info) {
        ToastUtil.show(activity,info);
    }
}

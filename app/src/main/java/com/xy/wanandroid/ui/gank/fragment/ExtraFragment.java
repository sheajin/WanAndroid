package com.xy.wanandroid.ui.gank.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.data.gank.EverydayData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ExtraFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<EverydayData.ResultsBean> todayList;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_extra;
    }

    @Override
    protected void initUI() {
        mRv.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    protected void initData() {
        todayList = new ArrayList<>();

    }
}

package com.xy.wanandroid.ui.drawer.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.ui.drawer.adapter.SortAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SortFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<String> sortList;
    private SortAdapter sortAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_sort_fragment;
    }

    public static SortFragment getInstance() {
        SortFragment sortFragment = new SortFragment();

        return sortFragment;
    }

    @Override
    protected void initUI() {
        GridLayoutManager manager = new GridLayoutManager(activity, 4);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        sortList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 7; i++) {
            sortList.add("");
        }
        sortAdapter = new SortAdapter(R.layout.item_sort, sortList);
        mRv.setAdapter(sortAdapter);
    }
}

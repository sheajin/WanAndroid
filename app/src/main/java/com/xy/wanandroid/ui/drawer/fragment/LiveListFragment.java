package com.xy.wanandroid.ui.drawer.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.ui.drawer.adapter.LiveListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LiveListFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView mRv;

    private List<String> liveList;
    private LiveListAdapter sortAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_live_list;
    }

    public static LiveListFragment getInstance() {
        LiveListFragment liveFragment = new LiveListFragment();

        return liveFragment;
    }

    @Override
    protected void initUI() {
        GridLayoutManager manager = new GridLayoutManager(activity, 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);
        liveList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        for (int i = 0; i < 7; i++) {
            liveList.add("");
        }
        sortAdapter = new LiveListAdapter(R.layout.item_sort, liveList);
        mRv.setAdapter(sortAdapter);
    }
}

package com.xy.wanandroid.base.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jxy on 2018/6/17.
 */
public class SimpleFragmentStateAdapter extends FragmentPagerAdapter {
    private List<String> titleList;
    private List<Fragment> list;

    public SimpleFragmentStateAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    public SimpleFragmentStateAdapter(FragmentManager fm, List<Fragment> list, List<String> titleList) {
        super(fm);
        this.list = list;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}

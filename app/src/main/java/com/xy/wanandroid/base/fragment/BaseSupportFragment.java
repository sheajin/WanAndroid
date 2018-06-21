package com.xy.wanandroid.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by jxy on 2018/6/20.
 */

public abstract class BaseSupportFragment extends SupportFragment {

    /**
     * ViewPager装载Fragment时,Fragment懒加载实现
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initUI();
        initData();
    }

    /**
     * 界面初始化
     */
    protected abstract void initUI();

    /**
     * 数据初始化
     */
    protected abstract void initData();
}

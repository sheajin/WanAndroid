package com.xy.wanandroid.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.xy.wanandroid.base.app.MyApplication;
import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.util.app.LogUtil;
import com.xy.wanandroid.util.network.NetUtils;
import com.xy.wanandroid.util.network.NetworkBroadcastReceiver;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.xy.wanandroid.base.view.AbstractView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by jxy on 2018/1/8.
 */

public abstract class BaseActivity<T extends AbsPresenter> extends SupportActivity implements AbstractView,
        NetworkBroadcastReceiver.NetEvent,HasSupportFragmentInjector {
    protected MyApplication context;
    protected BaseActivity activity;
    public static NetworkBroadcastReceiver.NetEvent eventActivity;
    private int netMobile;
    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentDispatchingAndroidInjector;
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = MyApplication.getInstance();
        activity = this;
        eventActivity = this;
        initBind();
        onViewCreated();
        initToolbar();
        initUI();
        initData();
    }

    protected void onViewCreated() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    /**
     * init ToolBar
     */
    protected void initToolbar() {
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    public void initBind() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMessageEvent(MessageEvent event) {
    }

    /**
     * 界面初始化
     */
    protected abstract void initUI();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentDispatchingAndroidInjector;
    }

    /**
     * 网络变化之后的类型
     */
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }

    /**
     * 判断有无网络
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtils.NETWORK_NONE) {
            LogUtil.e("NETWORK_NONE");
            return false;
        } else{
            reload();
            LogUtil.e("NETWORK_NORMAL");
            return true;
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void reload() {

    }

}

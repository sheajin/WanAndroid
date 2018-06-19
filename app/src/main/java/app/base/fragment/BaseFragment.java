package app.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import app.base.app.MyApplication;
import app.base.presenter.AbsPresenter;
import app.base.view.AbstractView;
import app.model.constant.MessageEvent;
import app.util.network.NetUtils;
import app.util.network.NetworkBroadcastReceiver;
import butterknife.ButterKnife;


/**
 * Created by jxy on 2018/1/13.
 */

public abstract class BaseFragment<T extends AbsPresenter> extends Fragment implements AbstractView, NetworkBroadcastReceiver.NetEvent {
    public View rootView;
    protected Activity activity;
    protected MyApplication context;
    public static NetworkBroadcastReceiver.NetEvent eventFragment;
    private int netMobile;
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view;
        activity = getActivity();
        context = MyApplication.getInstance();
        isViewCreated = true;
        initBind(view);
        initUI();
        initData();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        // 创建时要判断是否已经显示给用户，加载数据
//        onVisibleToUser();
//    }
//
//    private void onVisibleToUser() {
//        // 如果已经初始化完成，并且显示给用户
//        if (isUIVisible && getUserVisibleHint()) {
//            initData();
//        }
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        onVisibleToUser();
//    }
//
//    public void lazyLoad() {
//        if (isViewCreated && isUIVisible) {
//            initData();
//        }
//    }

    public void initBind(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
    }

    public abstract int getLayoutResID();

    /**
     * 界面初始化
     */
    protected abstract void initUI();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        if (netMobile == NetUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_NONE) {
            return false;
        }
        return false;
    }

    @Override
    public void showError() {

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {

    }

}

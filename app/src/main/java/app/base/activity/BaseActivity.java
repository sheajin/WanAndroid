package app.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import app.base.app.MyApplication;
import app.base.view.AbstractView;
import app.model.constant.MessageEvent;
import app.util.app.LogUtil;
import app.util.network.NetUtils;
import app.util.network.NetworkBroadcastReceiver;
import butterknife.ButterKnife;

/**
 * Created by jxy on 2018/1/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements AbstractView, NetworkBroadcastReceiver.NetEvent {
    protected MyApplication context;
    protected BaseActivity activity;
    public static NetworkBroadcastReceiver.NetEvent eventActivity;
    private int netMobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        context = MyApplication.getInstance();
        activity = this;
        initBind();
        initToolbar();
        initUI();
        initData();
    }

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
            LogUtil.e("NETWORK_WIFI");
            return true;
        } else if (netMobile == NetUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_NONE) {
            LogUtil.e("NETWORK_NONE");
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

}

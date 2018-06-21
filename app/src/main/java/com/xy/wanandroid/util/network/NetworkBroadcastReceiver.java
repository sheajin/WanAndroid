package com.xy.wanandroid.util.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.model.constant.Constant;

/**
 * Created by jxy on 2018/6/12.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    public NetEvent eventActivity = BaseActivity.eventActivity;
    public NetEvent eventFragment = BaseFragment.eventFragment;

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(Constant.NETBROADCAST)) {
            NetUtils.init(context);
            int netWorkState = NetUtils.getNetWorkState();
            // 接口回调传过去状态的类型
            eventActivity.onNetChange(netWorkState);
//            eventFragment.onNetChange(netWorkState);
        }
    }

    // 自定义接口
    public interface NetEvent {
        void onNetChange(int netMobile);
    }
}

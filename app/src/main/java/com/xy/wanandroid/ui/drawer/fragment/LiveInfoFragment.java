package com.xy.wanandroid.ui.drawer.fragment;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.util.app.LogUtil;

public class LiveInfoFragment extends BaseFragment {

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_live_info;
    }

    public static LiveInfoFragment getInstance() {
        return new LiveInfoFragment();
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

//    @Override
//    public void onMessageEvent(MessageEvent event) {
//        super.onMessageEvent(event);
//        switch (event.getCode()) {
//            case EventConstant.SENDDANMU:
//                LogUtil.e("onMessageEvent name = " + event.getName() + "mess = " + event.getMess());
//                break;
//        }
//    }
}

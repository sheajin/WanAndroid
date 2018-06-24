package com.xy.wanandroid.base.app;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;

/**
 * Created by jxy on 2018/6/7.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        UMConfigure.init(this,"5b2f3557a40fa33f2d00002d","umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }
}

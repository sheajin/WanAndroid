package com.xy.wanandroid.base.app;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.xy.wanandroid.model.constant.Constant;

/**
 * Created by jxy on 2018/6/7.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        //Bugly
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_ID, false);
    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }
}
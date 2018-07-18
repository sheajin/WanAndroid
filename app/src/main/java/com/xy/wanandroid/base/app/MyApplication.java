package com.xy.wanandroid.base.app;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.xy.wanandroid.di.component.ApplicationComponent;
import com.xy.wanandroid.di.component.DaggerApplicationComponent;
import com.xy.wanandroid.di.module.ApplicationModule;
import com.xy.wanandroid.model.constant.Constant;

/**
 * Created by jxy on 2018/6/7.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
        myApplication = this;
        //Bugly
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_ID, false);
    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    /**
     * 初始化ApplicationComponent
     */
    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
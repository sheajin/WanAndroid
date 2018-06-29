package com.xy.wanandroid.base.app;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by jxy on 2018/6/7.
 */

public class MyApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static synchronized MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }
}
package com.xy.wanandroid.di.component;

import android.content.Context;

import com.xy.wanandroid.di.module.ApplicationModule;

import dagger.Component;


/**
 * Created by lw on 2017/1/19.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getApplication();
}
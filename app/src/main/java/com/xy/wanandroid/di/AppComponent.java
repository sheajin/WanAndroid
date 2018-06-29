package com.xy.wanandroid.di;

import com.xy.wanandroid.base.app.MyApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by jxy on 2018/6/28.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AndroidSupportInjectionModule.class})
public interface AppComponent {

    void inject(MyApplication wanAndroidApp);
}

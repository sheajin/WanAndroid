package com.xy.wanandroid.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jxy on 2018/7/17.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }
}

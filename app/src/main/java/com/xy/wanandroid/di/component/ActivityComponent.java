package com.xy.wanandroid.di.component;

import android.app.Activity;

import com.xy.wanandroid.di.module.ActivityModule;
import com.xy.wanandroid.ui.drawer.activity.LiveActivity;
import com.xy.wanandroid.ui.drawer.activity.VideoActivity;
import com.xy.wanandroid.ui.login.LoginActivity;
import com.xy.wanandroid.ui.login.RegisterActivity;
import com.xy.wanandroid.ui.main.activity.ArticleDetailsActivity;
import com.xy.wanandroid.ui.main.activity.HotActivity;
import com.xy.wanandroid.ui.main.activity.SearchActivity;
import com.xy.wanandroid.ui.main.activity.SearchResultActivity;
import com.xy.wanandroid.ui.mine.activity.MyCollectActivity;

import dagger.Component;

/**
 * Created by jxy on 2018/7/17.
 */


@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(LoginActivity activity);

    void inject(RegisterActivity activity);

    void inject(ArticleDetailsActivity activity);

    void inject(HotActivity activity);

    void inject(SearchActivity activity);

    void inject(SearchResultActivity activity);

    void inject(MyCollectActivity activity);

    void inject(VideoActivity activity);

    void inject(LiveActivity activity);

}

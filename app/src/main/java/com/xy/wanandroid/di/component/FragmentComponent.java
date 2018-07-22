package com.xy.wanandroid.di.component;

import android.app.Activity;

import com.xy.wanandroid.di.module.FragmentModule;
import com.xy.wanandroid.ui.drawer.fragment.LiveListFragment;
import com.xy.wanandroid.ui.knowledge.fragment.KnowledgeFragment;
import com.xy.wanandroid.ui.knowledge.fragment.KnowledgeListFragment;
import com.xy.wanandroid.ui.main.fragment.HomePageFragment;
import com.xy.wanandroid.ui.project.fragment.ProjectFragment;
import com.xy.wanandroid.ui.project.fragment.ProjectListFragment;

import dagger.Component;

/**
 * Created by jxy on 2018/7/17.
 */

@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomePageFragment fragment);

    void inject(KnowledgeFragment fragment);

    void inject(KnowledgeListFragment fragment);

    void inject(ProjectFragment fragment);

    void inject(ProjectListFragment fragment);

    void inject(LiveListFragment fragment);

}

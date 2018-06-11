package app.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import app.base.app.MyApplication;
import app.base.presenter.AbsPresenter;
import app.base.view.AbstractView;
import butterknife.ButterKnife;


/**
 * Created by jxy on 2018/1/13.
 */

public abstract class BaseFragment<T extends AbsPresenter> extends Fragment implements AbstractView {
    public View rootView;
    protected Activity activity;
    protected MyApplication context;
    @Inject
    protected T mPresenter;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        return inflater.inflate(getLayoutResID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rootView = view;
        activity = getActivity();
        context = MyApplication.getInstance();
        initUI();
        initData();
//        EventBus.getDefault().register(this);
    }

    public abstract int getLayoutResID();

    /**
     * 界面初始化
     */
    protected abstract void initUI();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void reload() {

    }

}

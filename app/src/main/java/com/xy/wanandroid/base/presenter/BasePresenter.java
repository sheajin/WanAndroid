package com.xy.wanandroid.base.presenter;

import com.xy.wanandroid.base.view.AbstractView;

/**
 * Created by jxy on 2018/6/11.
 */

public class BasePresenter<T extends AbstractView> implements AbsPresenter<T> {

    protected T mView;
    private int currentPage;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

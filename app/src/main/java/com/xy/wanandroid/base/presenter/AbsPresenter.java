package com.xy.wanandroid.base.presenter;

import com.xy.wanandroid.base.view.AbstractView;

/**
 * Created by jxy on 2018/6/11.
 */

public interface AbsPresenter<T extends AbstractView> {

    /**
     * 注入View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 回收View
     */
    void detachView();
}

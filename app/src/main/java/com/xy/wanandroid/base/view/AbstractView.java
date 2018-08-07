package com.xy.wanandroid.base.view;


import android.view.View;

/**
 *  View 基类
 *
 * @author quchao
 * @date 2017/11/27
 */

public interface AbstractView {

    /**
     * showNormal
     */
    void showNormal();

    /**
     * Show error
     */
    void showError();

    /**
     * Show loading
     */
    void showLoading();

    void showNeteaseLoading();

    /**
     * Show empty
     */
    void showEmpty();

    /**
     * Reload
     */
    void reload();

    void setVisible(View... views);

    void setInVisible(View... views);

    void setGone(View... views);
}

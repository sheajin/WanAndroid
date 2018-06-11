package app.base.presenter;

import app.base.view.AbstractView;

/**
 * Created by jxy on 2018/6/11.
 */

public class BasePresenter<T extends AbstractView> implements AbsPresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}

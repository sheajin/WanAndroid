package com.xy.wanandroid.base.activity;

import android.view.View;
import android.view.ViewGroup;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.presenter.AbsPresenter;

/**
 * Created by jxy on 2018/6/11.
 */

public abstract class BaseRootActivity<T extends AbsPresenter> extends BaseActivity<T> {

    private static final int NORMAL_STATE = 0;
    private static final int LOADING_STATE = 1;
    public static final int ERROR_STATE = 2;
    public static final int EMPTY_STATE = 3;

    private View mErrorView;
    private View mLoadingView;
    private View mEmptyView;
    private ViewGroup mNormalView;
    private int currentState = NORMAL_STATE;

    public BaseRootActivity() {
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
    }

    @Override
    protected void initUI() {
        if (activity == null) {
            return;
        }
        mNormalView = findViewById(R.id.normal_view);
        if (mNormalView == null) {
            throw new IllegalStateException("The subclass of RootActivity must contain a View named 'mNormalView'.");
        }
        if (!(mNormalView.getParent() instanceof ViewGroup)) {
            throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup.");
        }
        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(activity, R.layout.view_loading, parent);
        View.inflate(activity, R.layout.view_error, parent);
        View.inflate(activity, R.layout.view_empty, parent);
        mLoadingView = parent.findViewById(R.id.loading_group);
        mErrorView = parent.findViewById(R.id.error_group);
        mEmptyView = parent.findViewById(R.id.empty_group);
        mErrorView.setOnClickListener(v -> reload());
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        if (currentState == LOADING_STATE) {
            return;
        }
        hideCurrentView();
        currentState = LOADING_STATE;
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        if (currentState == ERROR_STATE) {
            return;
        }
        hideCurrentView();
        currentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        if (currentState == EMPTY_STATE) {
            return;
        }
        hideCurrentView();
        currentState = EMPTY_STATE;
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormal() {
        if (currentState == NORMAL_STATE) {
            return;
        }
        hideCurrentView();
        currentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    private void hideCurrentView() {
        switch (currentState) {
            case NORMAL_STATE:
                if (mNormalView == null) {
                    return;
                }
                mNormalView.setVisibility(View.GONE);
                break;
            case LOADING_STATE:
                mLoadingView.setVisibility(View.GONE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.GONE);
                break;
            case EMPTY_STATE:
                mEmptyView.setVisibility(View.GONE);
            default:
                break;
        }
    }
}

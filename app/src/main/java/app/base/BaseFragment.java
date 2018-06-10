package app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.xy.wanandroid.R;

import butterknife.ButterKnife;

/**
 * Created by jxy on 2018/1/13.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public View rootView;
    public FrameLayout loadingView;
    protected Activity activity;
    protected MyApplication context;

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        rootView = view;
        context = MyApplication.getInstance();
        activity = getActivity();
        loadingView = getActivity().findViewById(R.id.view_state);
        initUI();
        initData();
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

    protected void init() {
    }

    protected void setText(int id, String str) {
        ((TextView) getView(id)).setText(str);
    }

    protected void setText(int id, int res) {
        ((TextView) getView(id)).setText(res);
    }

    protected <T extends View> T getView(View view, int id) {
        return view.findViewById(id);
    }

    protected <T extends View> T getView(int id) {
        return this.rootView.findViewById(id);
    }

    @Override
    public void onClick(View v) {

    }

    public void showLoadingView() {
        loadingView.getChildAt(0).setVisibility(View.VISIBLE);
        loadingView.getChildAt(1).setVisibility(View.GONE);
    }

    public void hideLoadingView() {
        loadingView.getChildAt(0).setVisibility(View.GONE);
        loadingView.getChildAt(1).setVisibility(View.GONE);
    }

    public void showErrorView() {
        loadingView.getChildAt(0).setVisibility(View.GONE);
        loadingView.getChildAt(1).setVisibility(View.VISIBLE);

    }

    public void hideErrorView() {
        loadingView.getChildAt(0).setVisibility(View.GONE);
        loadingView.getChildAt(1).setVisibility(View.GONE);
    }

    public void random() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

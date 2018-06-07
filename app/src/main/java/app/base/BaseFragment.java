package app.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by jxy on 2018/1/13.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public View rootView;
    protected Activity activity;

    public BaseFragment() {}

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
        activity = getActivity();
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

    protected void setClick(int... id) {
        for (int i = 0; i < id.length; i++) {
            getView(id[i]).setOnClickListener(this);
        }
    }

    protected void setClick(View... view) {
        for (int i = 0; i < view.length; i++) {
            view[i].setOnClickListener(this);
        }
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

    protected void callOnClick(int... id) {
        for (int i = 0; i < id.length; i++) {
            getView(id[i]).callOnClick();
        }
    }

    protected void callOnClick(View... view) {
        for (int i = 0; i < view.length; i++) {
            view[i].callOnClick();
        }
    }

    public void random() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

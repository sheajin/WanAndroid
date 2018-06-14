package app.ui.project;

import com.xy.wanandroid.R;

import app.base.fragment.BaseRootFragment;

public class ProjectFragment extends BaseRootFragment {

    @Override
    protected void initUI() {
        super.initUI();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initData() {

    }

    public static ProjectFragment getInstance() {
        return new ProjectFragment();
    }
}

package app.ui.mine.fragment;

import com.xy.wanandroid.R;

import app.base.fragment.BaseRootFragment;

public class PersonalFragment extends BaseRootFragment {

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initUI() {
    }

    @Override
    protected void initData() {

    }

    public static PersonalFragment getInstance() {
        return new PersonalFragment();
    }
}

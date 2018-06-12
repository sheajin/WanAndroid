package app.ui.knowledge.fragment;

import com.xy.wanandroid.R;

import app.base.fragment.BaseFragment;

public class KnowledgeFragment extends BaseFragment {

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

    public static KnowledgeFragment getInstance(int position) {
        return new KnowledgeFragment();
    }

}

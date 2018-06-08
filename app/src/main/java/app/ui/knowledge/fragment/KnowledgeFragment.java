package app.ui.knowledge.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.xy.wanandroid.R;

import app.base.BaseFragment;
import butterknife.BindView;

public class KnowledgeFragment extends BaseFragment {
    @BindView(R.id.text)
    TextView text;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        int position = bundle.getInt("key");
        text.setText("knowledge" + position);
    }


    public static KnowledgeFragment getInstance(int position) {
        KnowledgeFragment fragment = new KnowledgeFragment();
        Bundle args = new Bundle();
        args.putInt("key", position);
        fragment.setArguments(args);
        return fragment;
    }

}

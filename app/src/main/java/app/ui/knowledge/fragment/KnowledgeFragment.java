package app.ui.knowledge.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xy.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import app.base.fragment.BaseRootFragment;
import app.ui.knowledge.activity.MyAdapter;
import butterknife.BindView;
import butterknife.OnClick;

public class KnowledgeFragment extends BaseRootFragment {
    @BindView(R.id.normal_view)
    View text;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    private RecyclerView mRv;
    private List<String> list;
    private MyAdapter adapter;
    private Activity activity;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_knowledge;
    }

    @Override
    protected void initUI() {
        super.initUI();
    }

    @Override
    protected void initData() {
//        Bundle bundle = getArguments();
//        int position = bundle.getInt("key");
//        text.setText("knowledge" + position);
        activity = getActivity();
        mRv = activity.findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(activity));
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("");
        }

        adapter = new MyAdapter(list,activity);
        mRv.setAdapter(adapter);
    }

    public static KnowledgeFragment getInstance(int position) {
        KnowledgeFragment fragment = new KnowledgeFragment();
        Bundle args = new Bundle();
        args.putInt("key", position);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3})
    void click(View view) {
        switch (view.getId()) {
            case R.id.button1:
                showLoading();
                break;
            case R.id.button2:
                showError();
                break;
            case R.id.button3:
                showNormal();
                break;
        }
    }
}

package app.ui.mine.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.xy.wanandroid.R;

import app.base.fragment.BaseFragment;
import butterknife.BindView;

public class PersonalFragment extends BaseFragment {
    @BindView(R.id.text)
    TextView text;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initUI() {
        Bundle bundle = getArguments();
        int position = bundle.getInt("key");
        text.setText("person" + position);
    }

    @Override
    protected void initData() {

    }

    public static PersonalFragment getInstance(int position) {
        PersonalFragment fragment = new PersonalFragment();
        Bundle args = new Bundle();
        args.putInt("key", position);
        fragment.setArguments(args);
        return fragment;
    }
}

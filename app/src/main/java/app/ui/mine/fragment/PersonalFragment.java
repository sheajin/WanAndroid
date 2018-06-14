package app.ui.mine.fragment;

import android.view.View;

import com.xy.wanandroid.R;

import app.base.fragment.BaseFragment;
import app.ui.login.LoginActivity;
import app.util.app.JumpUtil;
import app.util.glide.GlideUtil;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalFragment extends BaseFragment {
    @BindView(R.id.image_head)
    CircleImageView imageHead;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initUI() {
        GlideUtil.loadImage(context, R.drawable.image_head, imageHead);
    }

    @Override
    protected void initData() {

    }

    public static PersonalFragment getInstance() {
        return new PersonalFragment();
    }

    @OnClick(R.id.image_head)
    void click(View view) {
        switch (view.getId()) {
            case R.id.image_head:
                JumpUtil.overlay(context, LoginActivity.class);
                break;
        }
    }
}

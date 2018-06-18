package app.ui.mine.fragment;

import android.view.View;

import com.xy.wanandroid.R;

import app.base.fragment.BaseFragment;
import app.ui.login.LoginActivity;
import app.ui.mine.activity.AboutUsActivity;
import app.ui.mine.activity.MyCollectActivity;
import app.ui.project.fragment.ProjectListFragment;
import app.util.app.JumpUtil;
import app.util.glide.GlideUtil;
import app.util.widget.CommonAlertDialog;
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

    @OnClick({R.id.image_head, R.id.view_collect,R.id.view_label, R.id.view_about, R.id.tv_logout})
    void click(View view) {
        switch (view.getId()) {
            case R.id.image_head:
                JumpUtil.overlay(context, LoginActivity.class);
                break;
            case R.id.view_collect:
                JumpUtil.overlay(context, MyCollectActivity.class);
                break;
            case R.id.view_label:

                break;
            case R.id.view_about:
                JumpUtil.overlay(context, AboutUsActivity.class);
                break;
            case R.id.tv_logout:
                CommonAlertDialog.newInstance().showDialog(activity, getString(R.string.logout_sure), getString(R.string.sure), getString(R.string.cancel), v -> logout(), v -> CommonAlertDialog.newInstance().cancelDialog(true));
                break;
        }
    }

    private void logout() {

    }
}

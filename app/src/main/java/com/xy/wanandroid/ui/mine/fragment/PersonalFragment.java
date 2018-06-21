package com.xy.wanandroid.ui.mine.fragment;

import android.view.View;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.ui.login.LoginActivity;
import com.xy.wanandroid.ui.mine.activity.AboutUsActivity;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.glide.GlideUtil;
import com.xy.wanandroid.util.widget.CommonAlertDialog;

import com.xy.wanandroid.ui.mine.activity.MyCollectActivity;

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

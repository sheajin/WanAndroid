package com.xy.wanandroid.ui.mine.fragment;

import android.view.View;
import android.widget.TextView;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.fragment.BaseFragment;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.model.cookie.CookiesManager;
import com.xy.wanandroid.ui.login.LoginActivity;
import com.xy.wanandroid.ui.mine.activity.AboutUsActivity;
import com.xy.wanandroid.ui.mine.activity.MyCollectActivity;
import com.xy.wanandroid.ui.mine.activity.MyLabelActivity;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.app.ToastUtil;
import com.xy.wanandroid.util.glide.GlideUtil;
import com.xy.wanandroid.util.widget.CommonAlertDialog;
import com.xy.wanandroid.util.widget.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalFragment extends BaseFragment {
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.tv_username)
    TextView mTvName;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;
    private boolean isLogin;
    private CommonDialog dialog;

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
        isLogin = (Boolean) SharedPreferenceUtil.get(activity, Constant.ISLOGIN, Constant.FALSE);
        String username = (String) SharedPreferenceUtil.get(activity, Constant.USERNAME, Constant.DEFAULT);
        mTvLogout.setVisibility(isLogin ? View.VISIBLE : View.GONE);
        mTvName.setText(isLogin ? username : getString(R.string.click_head_login));
        imageHead.setEnabled(!isLogin);
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
                if (isLogin) {
                    JumpUtil.overlay(context, MyCollectActivity.class);
                } else {
                    ToastUtil.show(activity,getString(R.string.please_login));
                }
                break;
            case R.id.view_label:
                if (isLogin) {
                    JumpUtil.overlay(context, MyLabelActivity.class);
                } else {
                    ToastUtil.show(activity,getString(R.string.please_login));
                }
                break;
            case R.id.view_about:
                JumpUtil.overlay(context, AboutUsActivity.class);
                break;
            case R.id.tv_logout:
                dialog = new CommonDialog.Builder(activity)
                        .setTitle(getString(R.string.logout))
                        .setMessage(getString(R.string.logout_sure))
                        .setPositiveButton(getString(R.string.sure), v -> logout())
                        .setNegativeButton(getString(R.string.cancel), v -> dialog.dismiss())
                        .setCancelable(false)
                        .create();
                dialog.show();
                break;
        }
    }

    @Override
    public void onMessageEvent(MessageEvent event) {
        super.onMessageEvent(event);
        if (event.getCode() == EventConstant.LOGINSUCCESS)
            initData();
    }

    private void logout() {
        dialog.dismiss();
        ToastUtil.show(activity, getString(R.string.logout_ok));
        CommonAlertDialog.newInstance().cancelDialog(true);
        SharedPreferenceUtil.clear(activity);
        initData();
        CookiesManager.clearAllCookies();
    }
}

package com.xy.wanandroid.ui.login;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.contract.login.LoginContract;
import com.xy.wanandroid.data.login.UserInfo;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;
import com.xy.wanandroid.presenter.login.LoginPresenter;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.SharedPreferenceUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.toolbar_login)
    Toolbar mToolbarLogin;
    @BindView(R.id.et_ensure_username)
    EditText mEtEnsureUsername;
    @BindView(R.id.et_ensure_password)
    EditText mEtEnsurePassword;

    private String username, password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbarLogin);
        getSupportActionBar().setTitle(getString(R.string.login));
        mToolbarLogin.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initUI() {
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_register, R.id.btn_login})
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                JumpUtil.overlay(context, RegisterActivity.class);
                break;
            case R.id.btn_login:
                if (check())
                    mPresenter.login(username, password);
                break;
        }
    }

    private boolean check() {
        username = mEtEnsureUsername.getText().toString().trim();
        password = mEtEnsurePassword.getText().toString().trim();
        if (username.length() < 6 || password.length() < 6) {
            ToastUtil.show(context, getString(R.string.username_incorrect));
            return false;
        }
        return true;
    }

    @Override
    public void loginOk(UserInfo userInfo) {
        ToastUtil.show(activity, getString(R.string.login_ok));
        SharedPreferenceUtil.put(activity, Constant.USERNAME, userInfo.getUsername());
        SharedPreferenceUtil.put(activity, Constant.PASSWORD, userInfo.getPassword());
        SharedPreferenceUtil.put(activity, Constant.ISLOGIN, true);
        EventBus.getDefault().post(new MessageEvent(EventConstant.LOGINSUCCESS, ""));
        finish();
    }

    @Override
    public void loginErr(String info) {
        ToastUtil.show(activity, info);
    }
}

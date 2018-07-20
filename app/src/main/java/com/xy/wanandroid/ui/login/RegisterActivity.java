package com.xy.wanandroid.ui.login;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.contract.login.RegisterContract;
import com.xy.wanandroid.data.login.UserInfo;
import com.xy.wanandroid.presenter.login.RegisterPresenter;
import com.xy.wanandroid.util.app.JumpUtil;
import com.xy.wanandroid.util.app.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.toolbar_register)
    Toolbar mToolbarRegister;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_ensure_password)
    EditText mEtEnsurePassword;

    private String username,password,ensurePassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbarRegister);
        getSupportActionBar().setTitle(getString(R.string.register));
        mToolbarRegister.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_register)
    void click(View view){
        switch (view.getId()){
            case R.id.btn_register:
                if (check())
                    mPresenter.register(username, password, ensurePassword);
                break;
        }
    }

    private boolean check() {
        username = mEtUsername.getText().toString().trim();
        password = mEtPassword.getText().toString().trim();
        ensurePassword = mEtEnsurePassword.getText().toString().trim();
        if (username.length() < 6 || password.length() < 6) {
            ToastUtil.show(context, getString(R.string.username_incorrect));
            return false;
        } else if (!password.equals(ensurePassword)) {
            ToastUtil.show(context, getString(R.string.password_incorrect));
            return false;
        }
        return true;
    }

    @Override
    public void registerOk(UserInfo register) {
        ToastUtil.show(context, getString(R.string.register_ok));
        JumpUtil.overlay(activity, LoginActivity.class);
        finish();
    }

    @Override
    public void registerErr(String info) {
        ToastUtil.show(context, info);
    }
}

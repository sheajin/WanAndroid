package com.xy.wanandroid.ui.login;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;
import com.xy.wanandroid.util.app.JumpUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar_login)
    Toolbar mToolbarLogin;
    @BindView(R.id.et_ensure_username)
    EditText mEtEnsureUsername;
    @BindView(R.id.et_ensure_password)
    EditText mEtEnsurePassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
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

    @OnClick(R.id.tv_register)
    void click(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                JumpUtil.overlay(context, RegisterActivity.class);
                break;
        }
    }
}

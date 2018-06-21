package com.xy.wanandroid.ui.login;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.xy.wanandroid.R;
import com.xy.wanandroid.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

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
    protected void initUI() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_register)
    void click(View view){
        switch (view.getId()){
            case R.id.btn_register:
                username = mEtUsername.getText().toString().trim();
                password = mEtPassword.getText().toString().trim();
                ensurePassword = mEtEnsurePassword.getText().toString().trim();
                break;
        }
    }
}

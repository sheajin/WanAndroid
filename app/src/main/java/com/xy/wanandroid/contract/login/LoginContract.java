package com.xy.wanandroid.contract.login;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.login.UserInfo;

/**
 * Created by jxy on 2018/6/22.
 */

public class LoginContract {

    public interface View extends AbstractView {

        void loginOk(UserInfo userInfo);

        void loginErr(String info);

    }

    public interface Presenter extends AbsPresenter<LoginContract.View> {

        void login(String name, String password);

    }
}

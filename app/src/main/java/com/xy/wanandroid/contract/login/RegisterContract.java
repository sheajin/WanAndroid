package com.xy.wanandroid.contract.login;

import com.xy.wanandroid.base.presenter.AbsPresenter;
import com.xy.wanandroid.base.view.AbstractView;
import com.xy.wanandroid.data.login.UserInfo;

/**
 * Created by jxy on 2018/6/22.
 */

public class RegisterContract {

    public interface View extends AbstractView {

        void registerOk(UserInfo userInfo);

        void registerErr(String info);

    }

    public interface Presenter extends AbsPresenter<RegisterContract.View> {

        void register(String name, String password, String rePassword);

    }
}

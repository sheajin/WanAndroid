package com.xy.wanandroid.presenter.login;

import com.xy.wanandroid.base.presenter.BasePresenter;
import com.xy.wanandroid.contract.LoginContract;
import com.xy.wanandroid.data.login.UserInfo;
import com.xy.wanandroid.model.api.ApiService;
import com.xy.wanandroid.model.api.ApiStore;
import com.xy.wanandroid.model.api.BaseResp;
import com.xy.wanandroid.model.api.HttpObserver;
import com.xy.wanandroid.model.constant.Constant;
import com.xy.wanandroid.model.constant.EventConstant;
import com.xy.wanandroid.model.constant.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jxy on 2018/6/22.
 */

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void login(String name, String password) {
        ApiStore.createApi(ApiService.class)
                .login(name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpObserver<BaseResp<UserInfo>>() {
                    @Override
                    public void onNext(BaseResp<UserInfo> baseResp) {
                        if (baseResp.getErrorCode() == Constant.REQUEST_SUCCESS) {
                            EventBus.getDefault().post(new MessageEvent(EventConstant.LOGINSUCCESS, ""));
                            view.loginOk(baseResp.getData());
                        } else if (baseResp.getErrorCode() == Constant.REQUEST_ERROR) {
                            view.loginErr(baseResp.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.loginErr(e.getMessage());
                    }
                });
    }
}

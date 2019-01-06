package com.xy.wanandroid.model.api;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Observer基类
 * 
 * @param <T>
 */
public abstract class HttpObserver<T> implements Observer<T> {

    /**
     * 可以统一处理返回值及相关操作
     *
     * @param t
     */
    @Override
    public abstract void onNext(T t);

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * 可以统一处理请求失败的错误
     *
     * @param e
     */
    @Override
    public abstract void onError(Throwable e);

}

